package org.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Класс для склада магазина
class Stock {
    private static List<Product> products = new ArrayList<>();
    private static List<ProductInfo> productInfos = new ArrayList<>();
    private static Map<Product, Integer> stock = new HashMap<>();

    public List<Product> getProducts() {
        return products;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public static Stock createStock() {
        // Создаем продукты
        ProductInfo apple = new ProductInfo("Яблоки", 250, "Пятерочка", Rating.GOOD);
        ProductInfo bread = new ProductInfo("Хлеб", 80, "Пятерочка", Rating.AVERAGE);
        ProductInfo milk = new ProductInfo("Молоко", 150, "Веселый молочник", Rating.GOOD);
        ProductInfo cheese = new ProductInfo("Сыр", 350, "БрестЛитовск", Rating.EXCELLENT);
        ProductInfo sosage = new ProductInfo("Колбаса", 420, "Мясной дом", Rating.GOOD);
        productInfos.add(apple);
        productInfos.add(bread);
        productInfos.add(milk);
        productInfos.add(cheese);
        productInfos.add(sosage);

        products = productInfos.stream()
                .map(info -> new Product(info.getName(), info.getPrice()))
                .collect(Collectors.toList());


        // Создаем склад и добавляем продукты
        Stock stock = new Stock();
        stock.addProduct(apple, 10);
        stock.addProduct(bread, 5);
        stock.addProduct(milk, 7);
        stock.addProduct(cheese, 3);
        stock.addProduct(sosage, 6);
        return stock;
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательно!");
        }
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
    }

    public boolean hasProduct(Product product, int quantity) {
        return stock.getOrDefault(product, 0) >= quantity;
    }

    public void removeProduct(Product product, int quantity) throws OutOfStockException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательно!");
        }
        if (hasProduct(product, quantity)) {
            stock.put(product, stock.get(product) - quantity);
            if (stock.get(product) == 0) {
                stock.remove(product);
            }
        } else {
            throw new OutOfStockException(product + " недостаточно на складе!");
        }
    }

    public void listAvailableProducts() {
        System.out.println("Продукты в наличии:");
        if (stock.isEmpty()) {
            System.out.println("Продуктов нет. Склад пуст.");
        } else {
            stock.forEach((product, quantity) ->
                    System.out.println(product + " - Количество: " + quantity));
        }
    }

    public void shortList() {
        System.out.print("Продукты в наличии: ");
        if (stock.isEmpty()) {
            System.out.println("Продуктов нет. Склад пуст.");
        } else {
            stock.forEach((product, quantity) ->
                    System.out.print(product.getName() + "(" + quantity + ")" + " "));
        }
        System.out.println();
    }

    public List<Product> filterProducts(String keyword, Double minPrice, Double maxPrice) {
        return stock.keySet().stream()
                .filter(p -> (keyword == null || p.getName().toLowerCase().contains(keyword.toLowerCase())) &&
                        (minPrice == null || p.getPrice() >= minPrice) &&
                        (maxPrice == null || p.getPrice() <= maxPrice))
                .collect(Collectors.toList());
    }
}