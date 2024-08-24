package org.shop;

import java.util.HashMap;
import java.util.Map;

// Класс для заказа
public class Order {
    private Map<Product,Integer> products = new HashMap<>();
    private boolean isCompleted = false;
    private boolean isReturned = false;
    private static int count;

    public Order(){
        count++;
    }

    public Map<Product,Integer> getProducts() {
        return products;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public void addProduct(Product product, int quantity) {
        if (!isCompleted && !isReturned) {
            products.put(product,quantity);
        } else {
            throw new IllegalStateException("Cannot modify an order that is completed or returned.");
        }
    }

    public void completeOrder() {
        isCompleted = true;
    }

    @Override
    public String toString() {
        double sum = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Заказ №"+count+":\n");
        if (products.isEmpty()) {
            sb.append("Заказ пуст!\n");
        } else {
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                sb.append(entry.getKey()).append(" - Количество: ").append(entry.getValue()).append("\n");
                sum = sum + entry.getKey().getPrice()* entry.getValue();
            }
        }
        sb.append("Общая сумма: "+ sum).append("\n");
        sb.append("Completed: ").append(isCompleted).append("\n");
        sb.append("Returned: ").append(isReturned).append("\n");
        return sb.toString();
    }
}