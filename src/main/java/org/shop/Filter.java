package org.shop;

@FunctionalInterface
public interface Filter<T> {
    boolean test(T item);
}