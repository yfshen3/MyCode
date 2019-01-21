package design_pattern;

public final class Product {
    private final String no;

    private final String name;

    private final double price;

    public Product(String no, String name, double price) {
        super();
        this.no = no;
        this.name = name;
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static void main(String[] args) {
        Product a = new Product("1", "syf", 12.0);
        Product b = new Product("2", "s", 13.0);
    }
}
