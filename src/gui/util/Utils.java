package gui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
}
