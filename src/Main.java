import java.util.Scanner;

public class Main {

    // ===== Helpers: ép nhập hợp lệ =====
    private static String requireNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Trường này bắt buộc, vui lòng nhập lại!");
        }
    }

    private static int requirePositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v > 0) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Vui lòng nhập số nguyên dương!");
        }
    }

    private static double requireNonNegativeDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v >= 0) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Vui lòng nhập số hợp lệ ≥ 0!");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Khởi tạo DB
        DatabaseManager.getInstance().init("Database/invoice.db");

        // ===== Nhập 1 khách hàng và xuất hóa đơn ngay =====
        String name  = requireNonEmpty(sc, "Tên khách hàng: ");
        String phone = requireNonEmpty(sc, "Số điện thoại: ");

        Customer customer = new Customer(name, phone);
        Invoice invoice = new Invoice(customer);

        // Nhập nhiều mặt hàng (mỗi trường đều bắt buộc)
        while (true) {
            String itemName = requireNonEmpty(sc, "Tên hàng: ");
            int qty         = requirePositiveInt(sc, "Số lượng: ");
            double price    = requireNonNegativeDouble(sc, "Đơn giá: ");

            invoice.addItem(new OrderItem(itemName, qty, price));

            // hỏi có muốn thêm mặt hàng nữa không
            System.out.print("Tiếp tục thêm mặt hàng? y/n: ");
            String more = sc.nextLine().trim().toLowerCase();
            if (!more.equals("y")) break;
        }

        // Lưu DB & in hóa đơn ngay
        DatabaseManager.getInstance().saveInvoice(invoice);

        invoice.setPrinter(new TextInvoicePrinter());
        invoice.print();

        invoice.setPrinter(new JsonInvoicePrinter());
        invoice.print();

        sc.close();
    }
}