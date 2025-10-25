public class Customer {
    private Long id;
    private final String name;
    private final String phone;

    public Customer(String name, String phone) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        this.name = name;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
}