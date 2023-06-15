package model.entities;

import java.time.LocalDateTime;

public class Rental {

    private LocalDateTime start;
    private LocalDateTime finish;

    private Car car;
    private Invoice invoice;

    public Rental() {
    }

    public Rental(LocalDateTime start, LocalDateTime finish, Car car, Invoice invoice) {
        this.start = start;
        this.finish = finish;
        this.car = car;
        this.invoice = invoice;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
