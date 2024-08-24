package org.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Shop {
    private String name;
    private Stock stock;
    //забитые адреса доставки
    private static List<String> addressDelivery = new ArrayList<>(Arrays.asList("Москва", "Питер", "Новгород"));

    public Shop(String name, Stock stock) {
        this.name = name;
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public List<String> getAddressDelivery() {
        return addressDelivery;
    }

    public static Shop createShop(String name) {

        Stock stock = Stock.createStock();
        Shop shop = new Shop(name, stock);
        return shop;
    }

    // Обработка заказа
    public void processOrder(Order order) throws OutOfStockException {
        // Удаление товаров со склада
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            stock.removeProduct(product, quantity);
        }

        // Завершение заказа
        order.completeOrder();
    }

    // Проверка состояния склада
    public void checkStockStatus() {
        System.out.println("Магазин " + name + ":");
        stock.listAvailableProducts();
    }

    public List<Product> filterProducts(String nameKeyword, Double minPrice, Double maxPrice) {
        Filter<Product> filter = Product.createFilter(nameKeyword, minPrice, maxPrice);
        return stock.getProducts().stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }

    public List<ProductInfo> filterProductInfos(String nameKeyword, Double minPrice, Double maxPrice, String company, Rating rating) {
        Filter<ProductInfo> filter = ProductInfo.createFilter(nameKeyword, minPrice, maxPrice, company, rating);
        return stock.getProductInfos().stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }

}

