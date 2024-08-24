package org.shop;


public class ProductInfo extends Product {

    private String company;
    private Rating rating;

    public ProductInfo(String name, double price, String company, Rating rating) {
        super(name, price);
        this.company = company;
        this.rating = rating;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public static Filter<ProductInfo> createFilter(String nameKeyword, Double minPrice, Double maxPrice, String company, Rating rating) {
        return productInfo ->
                (nameKeyword == null || productInfo.getName().toLowerCase().contains(nameKeyword.toLowerCase())) &&
                        (minPrice == null || productInfo.getPrice() >= minPrice) &&
                        (maxPrice == null || productInfo.getPrice() <= maxPrice) &&
                        (company == null || productInfo.getCompany().equalsIgnoreCase(company)) &&
                        (rating == null || productInfo.getRating().equals(rating));
    }

    @Override
    public String toString() {
        return super.toString() + " | Компания: " + company + " | Рейтинг: " + rating;
    }

}