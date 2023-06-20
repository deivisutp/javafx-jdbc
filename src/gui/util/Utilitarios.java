package gui.util;

import gui.interfaces.function.UpperCaseName;
import model.entities.Product;
import model.services.ProductService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utilitarios {
    /*
     Intermidiate
      - filter;
      - map;
      - flatmap;
      - peek;
      - distinct;
      - sorted;
      - skip;
      - limit;

      Terminal
       - foreach;
       - foreachordered;
       - toArray;
       - reduce;
       - collect;
       - anyMatch;
       - allMatch;
       - noneMatch;
       - findFirst;
       - findAny;
     */
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

    public void streaming() {
        Stream<Integer> st = Stream.iterate(0, x -> x + 2);
        System.out.println(Arrays.toString(st.limit(10).toArray()));

        //fibonacci
        Stream<Long> st2 = Stream.iterate(new Long[] {0L, 1L}, p -> new Long[] {p[1], p[0]+p[1]}).map(p -> p[0]);
        System.out.println(Arrays.toString(st2.limit(10).toArray()));
    }

    public List<String> readCsvProductsName(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<Product> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Product(fields[0],Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            double avg = list.stream()
                            .map(p -> p.getPrice())
                            .reduce(0.0,(x,y) -> x+y) / list.size();

            Comparator<String> comp = (s1,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
            List<String> names = list.stream()
                                    .filter(p -> p.getPrice() < avg)
                                    .map(p -> p.getName())
                                    .sorted(comp.reversed())
                                    .collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
