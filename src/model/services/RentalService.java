package model.services;

import model.entities.Invoice;
import model.entities.Rental;

import java.time.Duration;

public class RentalService {

    private Double priceDay;
    private Double priceHour;

    private TaxService tax;

    public RentalService(Double priceDay, Double priceHour, TaxService tax) {
        this.priceDay = priceDay;
        this.priceHour = priceHour;
        this.tax = tax;
    }

    private void processInvoice(Rental rent) {

        double min = Duration.between(rent.getStart(), rent.getFinish()).toMinutes();
        double hours = min / 60.0;
        double basicPayment;

        if (hours <= 12.0){
            basicPayment = priceHour * Math.ceil(hours);
        } else {
            basicPayment = priceDay * Math.ceil(hours / 24.0);
        }

        rent.setInvoice(new Invoice(basicPayment,tax.tax(basicPayment)));
    }
}
