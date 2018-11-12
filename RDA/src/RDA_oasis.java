
//import com.sun.org.apache.bcel.internal.generic.NEW;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RDA_oasis {
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATER = "\n";

	public static void main(String args[]) throws IOException {

		// TODO: TO CREATE BufferedWriter FOR WRITTING TO NEW CSV FILE

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int year = 2010; year <= 2017; year++) {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(year + "pberry.csv")));
			bufferedWriter.append("Date");
			bufferedWriter.append(COMMA_DELIMITER);
			bufferedWriter.append("Price");
			bufferedWriter.append(NEW_LINE_SEPARATER);
			for (int month = 1; month <= 12; month++) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date(year + "/" + month + "/00"));
				float kg = 0;
				float price = 0;
				float sum = 0;
				double r_sum = 0;
				double count = 0;
				double result = 0;

				for (int day = 0; day < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
					calendar.add(Calendar.DATE, 1);
					String date = sdf.format(calendar.getTime());

					System.out.print(date + ",");

					bufferedWriter.append(date);
					bufferedWriter.append(COMMA_DELIMITER);

					kg = 0;
					price = 0;
					sum = 0;
					r_sum = 0;
					count = 0;
					result = 0;

					Document document = Jsoup.connect(
							"http://oasis.krei.re.kr/basicInfo/wholesale/auctionPrice.do?cmd=service&gubun=0&noData=208135&excelMode=&checkType=true&dynamicCode=TB13&codeStr1=CS030&codeType=code4&pageIndex=1&fileName=&sCalendar="+date+"&eCalendar="+date+"&code1=08&code2=0804&code3=080413&code4=ALL&code4=380201&code4=370401&code4=110001&code4=350301&code4=250001&code4=370101&code4=210009&code4=310401&code4=320201&code4=350101&code4=310901&code4=5&code4=311201&code4=330201&code4=310101&code4=371501&code4=320301&code4=250003&code4=360201&code4=320101&code4=220001&code4=380101&code4=350402&code4=240001&code4=360301&code4=380401&code4=340101&code4=110008&code4=330101&code4=380303&code4=230003&code4=210001&code4=240004&code4=230001&searchKeyword=&searchKeyword3=&searchKeyword2=&searchStartDate=2016-04-19&pageUnit=1000")
							.post();

					try {
						Elements trElements = document.select(".data_table tbody tr");
						for (Element trElement : trElements) {
							

							kg = Float.parseFloat(trElement.select("td.left").get(1).text());
							price = Float.parseFloat(trElement.select("td.right").first().text().replaceAll(",", ""));
							sum = price / kg;
							count += 1;
							if (count == 1000) {
								System.out.println("1000넘었다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
							}
							r_sum += sum;
						}

						result = r_sum / count;
						System.out.println(result);
						bufferedWriter.append(String.valueOf(result));
						bufferedWriter.append(COMMA_DELIMITER);

					} catch (Exception ex) {
						// ex.printStackTrace();
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
	}

	// test
	// Document document =
	// Jsoup.connect("http://oasis.krei.re.kr/basicInfo/wholesale/auctionPrice.do?cmd=service&gubun=0&noData=317&excelMode=&checkType=true&dynamicCode=TB13&codeStr1=CS030&codeType=code4&pageIndex=1&fileName=&sCalendar="
	// + date + "&eCalendar=" + date +
	// "&code1=08&code2=0803&code3=080301&code4=311201&code4=380401&code4=320301&code4=210009&code4=210001&code4=240004&code4=380101&code4=110001&code4=340101&code4=370101&code4=230003&code4=320201&code4=350101&code4=240001&code4=310101&code4=250001&code4=380303&code4=320101&code4=230001&code4=350402&searchKeyword=&searchKeyword3=&searchKeyword2=&searchStartDate=2016-04-19&pageUnit=20").post();
	//
	// Elements trElements = document.select(".data_table tbody tr");
	//
	//
	// for(Element trElement: trElements) {
	// System.out.println(trElement.select("td.right").first().text());
	// }
	//
	// System.out.println("jsoup성공");
	//// System.out.println(document.text());
	//
	// //System.out.println(document.select("td.right").first());
	// }
	//
	// try 안에
	// System.out.println(trElement.select("td.left").get(1).text());
	// System.out.println(trElement.select("td.right").first().text());
	// bufferedWriter.append(trElement.select("td.right").first().text());
	// bufferedWriter.append(COMMA_DELIMITER);
}
