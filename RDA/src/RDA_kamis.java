
//import com.sun.org.apache.bcel.internal.generic.NEW;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RDA_kamis {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATER = "\n";

	public static void main(String args[]) throws IOException {

		// TODO: TO CREATE BufferedWriter FOR WRITTING TO NEW CSV FILE

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int year = 2010; year <= 2017; year++) {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(year + "s_cham.csv")));
			bufferedWriter.append("Date");
			bufferedWriter.append(COMMA_DELIMITER);
			bufferedWriter.append("Price");
			bufferedWriter.append(NEW_LINE_SEPARATER);
			for (int month = 1; month <= 12; month++) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date(year + "/" + month + "/00"));

				for (int day = 0; day < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
					calendar.add(Calendar.DATE, 1);
					String date = sdf.format(calendar.getTime());

					System.out.print(date + ",");

					bufferedWriter.append(date);
					bufferedWriter.append(COMMA_DELIMITER);

					Document document = Jsoup
							.connect("https://www.kamis.or.kr/customer/price/retail/period.do?action=daily&startday="+date+"&endday="+date+"&countycode=&itemcategorycode=200&itemcode=222&kindcode=00&productrankcode=0&convert_kg_yn=Y")
							.get();

					try {
						System.out.println(
								"\"" + document.select("td:contains(평균)").first().nextElementSibling().text() + "\"");
						bufferedWriter.append(
								"\"" + document.select("td:contains(평균)").first().nextElementSibling().text() + "\"");
					} catch (Exception ex) {
						System.out.println();
						bufferedWriter.append("N/A");
					}
					bufferedWriter.append(NEW_LINE_SEPARATER);
				}
			}
			// TODO: TO SAVE
			bufferedWriter.flush();
			bufferedWriter.close();
		}

		// test
		// Document document =
		// Jsoup.connect("https://www.kamis.or.kr/customer/price/retail/period.do?action=daily&startday=2017-08-14&endday=2017-08-14&countycode=&itemcategorycode=200&itemcode=225&kindcode=&productrankcode=1&convert_kg_yn=N").get();
		//
		// System.out.println("jsoup성공");
		// System.out.println(document.text());
		//
		// System.out.println(document.select("td:contains(평균)").first().nextElementSibling().text());
	}
}
