package model.services;

import model.entities.Car;

import java.util.ArrayList;
import java.util.List;

public class PrintService<T>{

    private List<T> list = new ArrayList<>();

    public void addValue(T value) {
        list.add(value);
    }

    public T first() {
        return list.get(0);
    }

    public  void printMyList(List<T> list) {
        list.add(list.get(0));
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static double total(List<? extends Car> list) {
        double sum = 0.0;
        for (Car car : list) {
            sum += car.getValue();
        }
        return sum;
    }
}
