PRAGMA foreign_keys = ON;

-- Bảng khách hàng
CREATE TABLE IF NOT EXISTS customers (
  id     INTEGER PRIMARY KEY AUTOINCREMENT,
  name   TEXT NOT NULL,
  phone  TEXT
);

-- Bảng hóa đơn
CREATE TABLE IF NOT EXISTS invoices (
  id          INTEGER PRIMARY KEY AUTOINCREMENT,
  date        TEXT NOT NULL,
  customer    TEXT NOT NULL,          -- snapshot tên KH để in nhanh
  total       REAL NOT NULL,
  customer_id INTEGER,                -- liên kết customers
  FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Bảng chi tiết hóa đơn
CREATE TABLE IF NOT EXISTS invoice_items (
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  invoice_id INTEGER NOT NULL,
  name       TEXT NOT NULL,
  quantity   INTEGER NOT NULL,
  price      REAL NOT NULL,
  line_total REAL NOT NULL,
  FOREIGN KEY (invoice_id) REFERENCES invoices(id)
);