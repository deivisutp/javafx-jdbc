package model.entities;

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
}
