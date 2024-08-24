package org.shop;

// Класс для товара
public class Product {
    private String name;
    private double price;


    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return name + " (" + price + " руб.)";
    }


    public static Filter<Product> createFilter(String nameKeyword, Double minPrice, Double maxPrice) {
        return product ->
                (nameKeyword == null || product.getName().toLowerCase().contains(nameKeyword.toLowerCase())) &&
                        (minPrice == null || product.getPrice() >= minPrice) &&
                        (maxPrice == null || product.getPrice() <= maxPrice);
    }

}
