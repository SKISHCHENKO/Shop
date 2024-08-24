package org.shop;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String STATUS = "1";
    public static final String ORDER = "2";
    public static final String CHECK = "3";
    public static final String INFORMATION = "4";
    public static final String FILTER = "5";
    public static final String EXIT = "6";

    public static void main(String[] args) throws OutOfStockException {
        Shop shop = Shop.createShop("SuperMart");
        Order order = null;
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        printCommands();

        while (running) {
            System.out.println("\nВведите команду (введите 4 для списка команд):");
            String input = scanner.nextLine();

            switch (input) {
                case STATUS:
                    // Состояние магазина
                    System.out.println("Список имеющихся продуктов на складе магазина:");
                    shop.checkStockStatus();
                    break;

                case ORDER:
                    // Создание заказа и добавление продуктов
                    order = new Order();
                    boolean addingProducts = true;

                    while (addingProducts) {
                        shop.getStock().shortList();
                        System.out.println("Введите название продукта для добавления в заказ (или 'done' для завершения):");
                        String productName = scanner.nextLine();

                        if (productName.equalsIgnoreCase("done")) {
                            addingProducts = false;
                        } else {
                            // Поиск продукта в складе магазина
                            Product product = findProductByName(shop.getStock(), productName);
                            if (product != null) {
                                System.out.println("Введите количество:");
                                try {
                                    int quantity = Integer.parseInt(scanner.nextLine());
                                    if (quantity > 0) {
                                        if (shop.getStock().hasProduct(product, quantity)) {
                                            order.addProduct(product, quantity);
                                            System.out.println("Продукт " + productName + " добавлен в заказ.");
                                        } else {
                                            System.out.println("Недостаточно товара на складе.");
                                        }
                                    } else {
                                        System.out.println("Количество должно быть положительным числом.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Введено некорректное количество. Пожалуйста, введите число.");
                                }
                            } else {
                                System.out.println("Продукт не найден.");
                            }
                        }
                    }
                    System.out.println("Ваш заказ:");
                    System.out.println(order);
                    break;

                case CHECK:
                    if (order == null) {
                        System.out.println("Ваш заказ пуст");
                        break;
                    }
                    System.out.println("Ваш заказ:");
                    System.out.println(order);
                    System.out.println("Переходим к оформлению заказа:");
                    System.out.println("Введите адрес доставки:");
                    System.out.println("Возможные адресса: " + shop.getAddressDelivery());
                    String address = scanner.nextLine();
                    if (!shop.getAddressDelivery().contains(address)) {
                        System.out.println("Адрес введен неверно или доставки нет");
                        System.out.println("В доставке отказано!");
                        System.out.println("Оформите заказ заново!");
                        break;
                    }
                    Delivery delivery = new Delivery(order, address, DeliveryStatus.PENDING);
                    System.out.println("Заказ оформлен и отправлен на доставку.");
                    System.out.println(delivery);
                    delivery.setAddress(address);
                    if (delivery.confirmDelivery()) {
                        System.out.println("Доставка подтверждена.");
                    } else {
                        System.out.println("Доставка уже завершена.");
                    }
                    System.out.println(delivery);
                    shop.processOrder(order);
                    order = null;
                    break;

                case INFORMATION:
                    // Список команд
                    printCommands();
                    break;
                case FILTER:
                    // Фильтрация продуктов и информации о продуктах
                    System.out.println("Выберите тип фильтрации:");
                    System.out.println("1 - Фильтр продуктов");
                    System.out.println("2 - Фильтр информации о продуктах");
                    String filterType = scanner.nextLine();

                    switch (filterType) {
                        case "1":
                            System.out.println("Введите ключевое слово для поиска по названию (или оставьте пустым):");
                            String nameKeyword = scanner.nextLine();
                            System.out.println("Введите минимальную цену (или оставьте пустым):");
                            Double minPrice = parseDouble(scanner.nextLine());
                            System.out.println("Введите максимальную цену (или оставьте пустым):");
                            Double maxPrice = parseDouble(scanner.nextLine());

                            List<Product> filteredProducts = shop.filterProducts(nameKeyword, minPrice, maxPrice);
                            System.out.println("Найденные продукты, которые соответствуют критериям:");
                            filteredProducts.forEach(System.out::println);
                            break;

                        case "2":
                            System.out.println("Введите ключевое слово для поиска по названию (или оставьте пустым):");
                            nameKeyword = scanner.nextLine();
                            System.out.println("Введите минимальную цену (или оставьте пустым):");
                            minPrice = parseDouble(scanner.nextLine());
                            System.out.println("Введите максимальную цену (или оставьте пустым):");
                            maxPrice = parseDouble(scanner.nextLine());
                            System.out.println("Введите компанию (или оставьте пустым):");
                            String company = scanner.nextLine();
                            System.out.println("Введите рейтинг (или оставьте пустым):");
                            Rating rating = parseRating(scanner.nextLine());

                            List<ProductInfo> filteredProductInfos = shop.filterProductInfos(nameKeyword, minPrice, maxPrice, company, rating);
                            System.out.println("Найденные продукты, которые соответствуют критериям:");
                            filteredProductInfos.forEach(System.out::println);
                            break;

                        default:
                            System.out.println("Неверный выбор.");
                            break;
                    }
                    break;

                case EXIT:
                    // Выход
                    System.out.println("Выход из программы.");
                    running = false;
                    break;
                default:
                    System.out.println("Неизвестная команда. Введите 4 для списка команд.");
                    break;
            }
        }
        scanner.close();
    }

    private static Product findProductByName(Stock stock, String name) {
        return stock.filterProducts(name, null, null).stream().findFirst().orElse(null);
    }

    private static void printCommands() {
        System.out.println();
        System.out.println("Список команд:");
        System.out.println("1 - Состояние магазина");
        System.out.println("2 - Создание заказа и добавление в заказ продуктов");
        System.out.println("3 - Проверка заказа и оформление заказа с отправкой на адрес");
        System.out.println("4 - Список команд");
        System.out.println("5 - Фильтрация продуктов и информации о продуктах");
        System.out.println("6 - Выход");
    }

    private static Double parseDouble(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Некорректное число, будет использоваться значение null.");
            return null;
        }
    }

    private static Rating parseRating(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        try {
            return Rating.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный рейтинг, будет использован average рейтинг.");
            return Rating.AVERAGE;
        }
    }
}