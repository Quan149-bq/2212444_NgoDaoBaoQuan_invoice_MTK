import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private Long id;
    private final LocalDate date;
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private InvoicePrinter printer;

    public Invoice(Customer customer) {
        this.date = LocalDate.now();
        this.customer = customer;
    }

    public void addItem(OrderItem item) { items.add(item); }
    public List<OrderItem> getItems() { return items; }
    public LocalDate getDate() { return date; }
    public Customer getCustomerObj() { return customer; }
    public String getCustomer() { return customer.getName(); }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::getLineTotal).sum();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public void setPrinter(InvoicePrinter printer) { this.printer = printer; }

    public void print() {
        if (printer == null) throw new IllegalStateException("No printer set");
        printer.print(this);
    }
}