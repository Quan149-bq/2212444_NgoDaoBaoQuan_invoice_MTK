Sinh viên thực hiện: Ngô Đào Bảo Quân
MSSV: 2212444
Lớp: CTK46 – Công nghệ Thông tin
Môn: Mẫu thiết kế & Lập trình hướng đối tượng
Đề tài: Ứng dụng tạo và lưu hóa đơn bán hàng (Invoice System)

1.Thư mục source(src)
Chứa toàn bộ mã nguồn

DatabaseManager.java : quản lý kết nối sql, singleton pattern
Invoice.java : đại diện cho hóa đơn
OrderItem : đại diện cho từng sản phẩm
InvoicePrinter : interface cho các kiểu in khác nhau
TextInvoicePrinter, JsonInvoicePrinter : hai kiểu in hóa đơn
Main: điểm khởi chạy chương trình, nhập thông tin và lưu CSDL

2.Thư mục Database
invoice.db : file dữ liệu SQLite (được sinh sau khi chạy)
script.sql : chứa lệnh tạo bảng và dữ liệu mẫu

3.Thư mục Executable
invoice-system.jar : file chạy chính (đã build)
run_invoice.bat : file nhấn vô để chạy nhanh
