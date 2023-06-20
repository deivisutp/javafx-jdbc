package gui.util;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gui.interfaces.consumer.PriceUpdate;
import gui.interfaces.predicate.ProductPredicate;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.entities.Car;
import model.entities.Product;
import model.services.CalculationService;
import model.services.ProductCompareImpl;

public class Utils {

	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Double tryParseToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}

	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + decimalPlaces + "f", item));
					}
				}
			};
			return cell;
		});
	}

	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
			{
				datePicker.setPromptText(format.toLowerCase());
			}

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	public static void removeItem(List<String> lista, String item) {
		//Ficar na lista apenas itens iniciando com A
		List<String> newList = lista.stream().filter(x -> x.charAt(0) == 'A').collect(Collectors.toList());

		//Apenas o primeiro elemento com letra A
		String name = lista.stream().filter(x -> x.charAt(0) == 'A').findFirst().orElse("");

		//Remove todos os itens com inicio M da lista utilizando Predicate
		lista.removeIf(x -> x.charAt(0) == 'M');
	}

	public static void setLocalDateTime(String dateTime) {
		DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter fmt2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		LocalDate dt = LocalDate.now();
		LocalDate dt2 = LocalDate.parse("2023-06-08");
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime tm2 = LocalDateTime.parse("2023-06-08T20:35:00");

		dt2 = LocalDate.parse("10/06/2023", fmt1);
		tm2 = LocalDateTime.parse("10/06/2023 20:35", fmt2);

		LocalDate separatedDate = LocalDate.of(2023, 6, 8);

		LocalDate newDate = separatedDate.minus(7, ChronoUnit.YEARS);
		newDate = separatedDate.plus(7, ChronoUnit.YEARS);

		System.out.println(dt2.format(fmt1));
	}

	public static void setGlobalDateTime() {
		DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
		DateTimeFormatter fmt2 = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());

		//Always using GMT 0 hour
		Instant time = Instant.now();

		Instant time2 = Instant.parse("2023-06-08T20:51:20Z");
		//GMT 0
		Instant tm2 = Instant.parse("2023-06-08T20:35:00Z");

		System.out.println(fmt1.format(time));
		System.out.println(fmt2.format(time));
		System.out.println(fmt1.format(time2));

		Duration t1 = Duration.between(time, time2);
		System.out.println(t1.toHours());
	}

	public static void getGlobalDate() {
		Instant inst = Instant.parse("2023-08-06T21:03:26Z");

		LocalDateTime dt = LocalDateTime.ofInstant(inst, ZoneId.systemDefault());
		LocalDateTime dt2 = LocalDateTime.ofInstant(inst, ZoneId.of("Portugal"));

		System.out.println(dt);
		System.out.println(dt2);
	}

	public static void oldDateTimetreatment() throws ParseException {
		//Before java 8
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		sdf3.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date d2 = new Date();
		Date y1 = sdf1.parse("08/06/2023");

		System.out.println(sdf2.format(d2));
		System.out.println(sdf3.format(d2));

		Calendar cal = Calendar.getInstance();
		cal.setTime(d2);
		cal.add(Calendar.DAY_OF_MONTH, 10);
		d2 = cal.getTime();

		int min = cal.get(Calendar.MINUTE);

		System.out.println(sdf2.format(d2));
		System.out.println(min);
	}

	public void fileRead(String path) {
		List<Car> list = new ArrayList<>();

	     try(BufferedReader br = new BufferedReader(new FileReader(path))) {
	          String line = br.readLine();
	          while (line != null) {
				  String[] fields = line.split(",");
	          	  list.add(new Car(fields[0]));
				  System.out.println(line);
				  line = br.readLine();
			  }

	          Collections.sort(list);
	          for (Car car : list) {
				  System.out.println(car.getModel());
			  }
         }
	     catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}

	public void fileWrite(String path) {
		String[] lines = new String[] {"Test", "Test2"};

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (String line : lines) {
				bw.write(line);
				bw.newLine();
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFolder(String path) {
		File file = new File(path);
		File[] folders = file.listFiles(File::isDirectory);

		//subdir
		System.out.println(new File(path + "\\").mkdir());

		return file.getParent();
	}

	public void importCar(String path) {
		List<Car> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))){

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Car(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}

			Car x = CalculationService.max(list);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copyList() {
		List<Integer> intList = Arrays.asList(1, 2, 3, 4);
		List<Double> dobList = Arrays.asList(10.0, 20.0, 30.0, 4.0);
		List<Object> objList = new ArrayList<>();

		copy(intList, objList);
		copy(dobList, objList);
	}

	public void copy(List<? extends Number> source, List<? super Number> destiny) {
		for (Number number : source) {
			destiny.add(number);
		}
	}

	public void copySetTree(){
		Set<Integer> a = new TreeSet<>(Arrays.asList(0,2,4,5,6,8,10));
		Set<Integer> b = new TreeSet<>(Arrays.asList(5,6,7,8,9,10));

		//union
		Set<Integer> c = new TreeSet<>(a);
		c.addAll(b);
		System.out.println(c);

		//intersection
		Set<Integer> d = new TreeSet<>(a);
		c.retainAll(b);
		System.out.println(d);

		//difference
		Set<Integer> e = new TreeSet<>(a);
		c.removeAll(b);
		System.out.println(e);
	}

	public void compareCar() {
		Set<Car> set = new HashSet<>();
		set.add(new Car("Sandero", 90000.00));
		Car car = new Car("Sandero", 90000.00);
		System.out.println(set.contains(car));
	}

	public void compareAndStore() {
		List<Product> list = new ArrayList<>();
		list.add(new Product("Prod 1"));
		list.add(new Product("Prod 2"));

		//1 - frst option
		list.sort(new ProductCompareImpl());

		//2 - scnd option
		Comparator<Product> comp = (p1,p2) ->  p1.getName().toUpperCase().compareTo(p2.getName().toUpperCase());
		list.sort(comp);

		//3 - thrd option
		list.sort(Utils::comparing);

		//predicate
		list.removeIf(new ProductPredicate());
		list.removeIf(Product::staticProductPredicate);

		Predicate<Product> pred = p -> !p.getName().isEmpty();
		list.removeIf(pred);

		//Consumer 1
		list.forEach(new PriceUpdate());
		//Consumer 2
		Consumer<Product> cons = p -> p.setPrice(p.getPrice() * 1.1);
		list.forEach(cons);
		//Consumer 3
		list.forEach(p -> p.setPrice(p.getPrice() * 1.1));
	}

	public static int comparing(Product p1, Product p2) {
		return p1.getName().toUpperCase().compareTo(p2.getName().toUpperCase());
	}
}
