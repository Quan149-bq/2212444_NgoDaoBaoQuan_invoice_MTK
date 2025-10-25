import java.nio.file.*;
import java.sql.*;

public class DatabaseManager {
    private static volatile DatabaseManager INSTANCE;
    private Connection conn;

    private DatabaseManager() { }

    public static DatabaseManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                if (INSTANCE == null) INSTANCE = new DatabaseManager();
            }
        }
        return INSTANCE;
    }


    public void init(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC"); // nạp driver

            Path abs = Paths.get(dbPath).toAbsolutePath();
            Files.createDirectories(abs.getParent());
            System.out.println("SQLite DB path: " + abs);

            conn = DriverManager.getConnection("jdbc:sqlite:" + abs);

            try (Statement st = conn.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON;");

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS customers(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        phone TEXT
                    )
                    """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS invoices(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        date TEXT NOT NULL,
                        customer TEXT NOT NULL,
                        total REAL NOT NULL,
                        customer_id INTEGER,
                        FOREIGN KEY(customer_id) REFERENCES customers(id)
                    )
                    """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS invoice_items(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        invoice_id INTEGER NOT NULL,
                        name TEXT NOT NULL,
                        quantity INTEGER NOT NULL,
                        price REAL NOT NULL,
                        line_total REAL NOT NULL,
                        FOREIGN KEY(invoice_id) REFERENCES invoices(id)
                    )
                    """);
            }
        } catch (Exception e) {
            throw new RuntimeException("Init DB failed: " + e.getMessage(), e);
        }
    }

    public Connection getConnection() { return conn; }


    public long saveCustomer(Customer c) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO customers(name, phone) VALUES(?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Cannot insert customer");
    }


    public void saveInvoice(Invoice invoice) {
        if (conn == null) throw new IllegalStateException("DB chưa init");
        try {
            conn.setAutoCommit(false);

            long customerId = (invoice.getCustomerObj().getId() == null)
                    ? saveCustomer(invoice.getCustomerObj())
                    : invoice.getCustomerObj().getId();
            invoice.getCustomerObj().setId(customerId);

            long newId;

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO invoices(date, customer, total, customer_id) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, invoice.getDate().toString());
                ps.setString(2, invoice.getCustomer());
                ps.setDouble(3, invoice.getTotal());
                ps.setLong(4, customerId);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) newId = rs.getLong(1);
                    else throw new SQLException("No generated id");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO invoice_items(invoice_id,name,quantity,price,line_total) VALUES(?,?,?,?,?)")) {
                for (OrderItem it : invoice.getItems()) {
                    ps.setLong(1, newId);
                    ps.setString(2, it.name());
                    ps.setInt(3, it.quantity());
                    ps.setDouble(4, it.price());
                    ps.setDouble(5, it.getLineTotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            invoice.setId(newId);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            throw new RuntimeException("Save invoice failed: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
    }
}