import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KelimeOkumaScript {
	public static final char[] VOWELS = {'A','E','I','İ','O','Ö','U','Ü'};
    public static final char[] NON_VOWELS = {'B','C','Ç','D','F','G','Ğ','H','J','K','L','M','N','P','R','S','Ş','T','V','Y','Z'};
	public static void main(String[] args) throws IOException {
		File file = new File("sozluk");
		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		writer = new PrintWriter(new FileWriter(file, true));
		String baseUrl = "http://www.kelimeler.net/%s-ile-baslayan-%d-harfli-kelimeler";
		for(int i =0;i<VOWELS.length;i++) {
			for(int j=15;j>=3;j--) {
				get(baseUrl, VOWELS[i],j);
			}
		}
		for(int i =0;i<NON_VOWELS.length;i++) {
			for(int j=15;j>=3;j--) {
				get(baseUrl, NON_VOWELS[i],j);
			}
		}
		//
		writer.flush();
		writer.close();
	}
	public static void get(String baseUrl,char cr, int index) throws IOException {
		String url = String.format(baseUrl,cr,index);
		Document document = Jsoup.connect(url).userAgent("Mozilla").get();
		Elements elements = document.getElementsByClass("WordList");
		for(Element element : elements) {
			Elements aElements = element.select("a");
			for(Element singleA : aElements) {
				String value = singleA.text();
				System.out.println(value);	
				write(value);
			}
			
			
		}
	}
	private static PrintWriter writer;
	public static void write(String str) {
		writer.write(str+"\n");
	}
}
