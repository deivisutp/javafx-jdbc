package model.entities;

public class Car implements Comparable<Car> {

    private String model;

    public Car(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public int compareTo(Car o) {
        return model.compareTo(o.getModel());
    }
}
