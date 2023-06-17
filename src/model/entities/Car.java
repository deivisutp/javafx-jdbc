package model.entities;

import java.util.Objects;

public class Car implements Comparable<Car> {

    private String model;
    private Double value;

    public Car(String model) {
        this.model = model;
    }

    public Car(String model,Double value) {
        this.model = model;
        this.value = value;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Car o) {
        return model.compareTo(o.getModel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return model.equals(car.model) && value.equals(car.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, value);
    }
}
