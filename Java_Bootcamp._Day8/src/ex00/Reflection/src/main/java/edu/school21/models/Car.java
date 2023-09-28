package edu.school21.models;

public class Car {
    private String brand;
    private String model;
    private Integer price;
    private Boolean isInStock;

    public Car() {
        brand = "defaultBrand";
        model = "defaultModel";
        price = 0;
        isInStock = false;
    }

    public Car(String brand, String model, Integer price, Boolean isInStock) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.isInStock = isInStock;
    }

    public int addToPrice(Integer num) {
        price += num;
        return price;
    }

    public void toggleInStock() {
        isInStock = !isInStock;
    }

    @Override
    public String toString() {
        return "Car[" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", isInStock=" + isInStock +
                ']';
    }
}
