-- BẬT RÀNG BUỘC KHÓA NGOẠI (Foreign Key)
PRAGMA foreign_keys = ON;

-- ======================================
-- BẢNG KHÁCH HÀNG
-- ======================================
CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    phone TEXT
);

-- ======================================
-- BẢNG HÓA ĐƠN
-- ======================================
CREATE TABLE IF NOT EXISTS invoices (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    date TEXT NOT NULL,
    customer TEXT NOT NULL,         -- tên khách hàng snapshot (lưu lại cho tiện in)
    total REAL NOT NULL,
    customer_id INTEGER,            -- liên kết với bảng customers
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- ======================================
-- BẢNG CHI TIẾT HÓA ĐƠN
-- ======================================
CREATE TABLE IF NOT EXISTS invoice_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    invoice_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    price REAL NOT NULL,
    line_total REAL NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id)
);

-- ======================================
-- DỮ LIỆU MẪU (DÙNG KHI TEST NHANH)
-- ======================================

-- KHÁCH HÀNG MẪU
INSERT INTO customers(name, phone) VALUES
('Ngô Đào Bảo Quân', '0909123456'),
('Trần Văn Bánh', '0911222333');

-- HÓA ĐƠN MẪU
INSERT INTO invoices(date, customer, total, customer_id)
VALUES ('2025-10-24', 'Ngô Đào Bảo Quân', 116000, 1);

-- CHI TIẾT HÓA ĐƠN MẪU
INSERT INTO invoice_items(invoice_id, name, quantity, price, line_total) VALUES
(1, 'Bánh mì', 2, 15000, 30000),
(1, 'Sữa tươi', 1, 32000, 32000),
(1, 'Cà phê', 3, 18000, 54000);

-- ======================================
-- KẾT THÚC SCRIPT
-- ======================================