package org.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Класс для склада магазина
class Store {
    private Map<Product, Integer> inventory = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        inventory.put(product, inventory.getOrDefault(product, 0) + quantity);
    }

    public boolean hasProduct(Product product, int quantity) {
        return inventory.getOrDefault(product, 0) >= quantity;
    }

    public void removeProduct(Product product, int quantity) {
        if (hasProduct(product, quantity)) {
            inventory.put(product, inventory.get(product) - quantity);
        } else {
            throw new IllegalArgumentException("Not enough product in stock.");
        }
    }

    public void printAvailableProducts() {
        System.out.println("Available Products:");
        for (Map.Entry<Product, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " - Quantity: " + entry.getValue());
        }
    }

    public List<Product> filterProducts(String keyword, Double minPrice, Double maxPrice, String manufacturer) {
        return inventory.keySet().stream()
                .filter(p -> (keyword == null || p.getName().toLowerCase().contains(keyword.toLowerCase())) &&
                        (minPrice == null || p.getPrice() >= minPrice) &&
                        (maxPrice == null || p.getPrice() <= maxPrice))
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Store Inventory:\n");
        for (Map.Entry<Product, Integer> entry : inventory.entrySet()) {
            sb.append(entry.getKey()).append(" - Quantity: ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}