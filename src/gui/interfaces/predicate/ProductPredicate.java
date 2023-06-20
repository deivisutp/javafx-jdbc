package gui.interfaces.predicate;

import model.entities.Product;

import java.util.function.Predicate;

public class ProductPredicate implements Predicate<Product> {

    @Override
    public boolean test(Product product) {
        return !product.getName().isEmpty();
    }
}
