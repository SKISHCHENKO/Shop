package org.shop;

//класс для доставки
public class Delivery {
    private Order order;
    private String address;
    private DeliveryStatus deliverystatus;

    public Delivery(Order order, String address, DeliveryStatus deliverystatus) {
        if (order == null || address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Order and address cannot be null or empty.");
        }
        this.order = order;
        this.address = address;
        this.deliverystatus = deliverystatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        this.address = address;
    }

    public DeliveryStatus getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(DeliveryStatus deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public boolean confirmDelivery() {
        if (deliverystatus == DeliveryStatus.COMPLETED) {
            return false;
        }
        this.deliverystatus = DeliveryStatus.COMPLETED;
        return true;
    }

    public DeliveryStatus checkDelivery() {
        return this.deliverystatus;
    }

    @Override
    public String toString() {
        return "Доставка на адрес: " + address + " - Статус: " + deliverystatus;
    }
}