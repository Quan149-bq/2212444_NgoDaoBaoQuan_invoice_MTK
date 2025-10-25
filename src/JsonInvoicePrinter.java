public class JsonInvoicePrinter implements InvoicePrinter {

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
    @Override
    public void print(Invoice invoice) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"id\": \"").append(invoice.getId()).append("\",\n");
        sb.append("  \"date\": \"").append(invoice.getDate()).append("\",\n");

        // Customer object
        sb.append("  \"customer\": {\n");
        sb.append("    \"id\": ").append(invoice.getCustomerObj().getId() == null ? "null" : invoice.getCustomerObj().getId()).append(",\n");
        sb.append("    \"name\": \"").append(escape(invoice.getCustomer())).append("\",\n");
        sb.append("    \"phone\": \"").append(escape(invoice.getCustomerObj().getPhone() == null ? "" : invoice.getCustomerObj().getPhone())).append("\"\n");
        sb.append("  },\n");

        sb.append("  \"items\": [\n");
        for (int i = 0; i < invoice.getItems().size(); i++) {
            OrderItem item = invoice.getItems().get(i);
            sb.append("    {")
                    .append("\"name\": \"").append(escape(item.name())).append("\", ")
                    .append("\"quantity\": ").append(item.quantity()).append(", ")
                    .append("\"price\": ").append(item.price()).append(", ")
                    .append("\"LineTotal\": ").append(item.getLineTotal())
                    .append("}");
            if (i < invoice.getItems().size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ],\n");
        sb.append("  \"total\": ").append(invoice.getTotal()).append("\n");
        sb.append("}\n");

        System.out.println(sb);
    }
}