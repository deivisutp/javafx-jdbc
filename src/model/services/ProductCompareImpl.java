package model.services;

import model.entities.Product;

import java.util.Comparator;
import java.util.Locale;

public class ProductCompareImpl implements Comparator<Product> {


    @Override
    public int compare(Product o1, Product o2) {
        return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
    }
}
