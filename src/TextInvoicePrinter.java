import java.text.DecimalFormat;

public class TextInvoicePrinter implements InvoicePrinter {
    private static final DecimalFormat df = new DecimalFormat("#,###");

    @Override
    public void print(Invoice invoice) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        HÓA ĐƠN BÁN HÀNG                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.printf("Mã HD: %-5s  Ngày: %-12s%n",
                invoice.getId() == null ? "(chưa lưu)" : invoice.getId(),
                invoice.getDate());
        System.out.printf("Khách hàng: %-20s  |  SĐT: %s%n",
                invoice.getCustomerObj().getName(),
                invoice.getCustomerObj().getPhone());
        System.out.println("───────────────────────────────────────────────────────────────");
        System.out.printf("%-25s %5s %10s %12s%n", "Sản phẩm", "SL", "Đơn giá", "Thành tiền");
        System.out.println("───────────────────────────────────────────────────────────────");

        for (OrderItem item : invoice.getItems()) {
            System.out.printf("%-25s %5d %10s %12s%n",
                    capitalize(item.name()),
                    item.quantity(),
                    df.format(item.price()),
                    df.format(item.getLineTotal()));
        }

        System.out.println("───────────────────────────────────────────────────────────────");
        System.out.printf("%-30s %20s%n", "TỔNG CỘNG:", df.format(invoice.getTotal()));
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println();
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
}