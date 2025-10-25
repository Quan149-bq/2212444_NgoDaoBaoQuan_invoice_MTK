public record OrderItem(String name, int quantity, double price) {
    public OrderItem {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity > 0");
        if (price < 0) throw new IllegalArgumentException("price >= 0");
    }

    public double getLineTotal() {
        return quantity * price;
    }
}