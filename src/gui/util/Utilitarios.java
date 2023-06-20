package gui.util;

import gui.interfaces.function.UpperCaseName;
import model.entities.Product;
import model.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utilitarios {

    public List<Product> convertProduct() {
        //map(function)
        List<Product> list = new ArrayList<>();

        list.add(new Product("Producto A", 10.0));

        //frst way
        List<String> names = list.stream().map(new UpperCaseName()).collect(Collectors.toList());
        //scnd way
        return list.stream().map(p -> {
            p.setName(p.getName().toLowerCase());
            p.setPrice(p.getPrice() * 1.1);
            return p;
        }).collect(Collectors.toList());
    }

    public List<Product> filter() {
        List<Product> list = new ArrayList<>();

        list.add(new Product("Prod a", 10.0));
        list.add(new Product("Prod b", 20.0));

        ProductService ps = new ProductService();

        double sum = ps.filteredSum(list, p -> p.getName().charAt(0) == 'T');

        return list.stream().filter(p -> p.getPrice() < 20.0).collect(Collectors.toList());
    }
}
