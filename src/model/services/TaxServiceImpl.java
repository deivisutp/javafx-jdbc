package model.services;

public class TaxServiceImpl implements TaxService {

    @Override
    public double tax(double amount) {
        return amount * 0.15;
    }
}
