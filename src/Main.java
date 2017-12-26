import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by abdullahtellioglu on 11/09/17.
 */
public class Main {
	public static void main(String[] args) {
		/*
		 * Dictionary dictionary = new Dictionary("trkelimeler"); Matrix matrix = new
		 * Matrix(); matrix.setDictionary(dictionary); matrix =
		 * PossibilityFinder.findMatrix(matrix,dictionary);
		 */

		Dictionary dictionary = new Dictionary("sozluk");
		MapCreator mapCreator = new MapCreator(dictionary);
		boolean finished = false;
		while (!finished) {
			Tree tree = new Tree();
			tree.setMapCreator(mapCreator);
			tree.setDictionary(dictionary);
			long agacOlusturulmaSuresi =0;
			long agacBitisSuresi = 0;
			try {
				System.out.println("Agac olusturuluyor");
				agacOlusturulmaSuresi = System.currentTimeMillis();
				tree.create();
				System.out.println("Agac basariyla olusturuldu");
				finished = true;
			} catch (NoMorePathException e) {
				// e.printStackTrace();
				System.err.println("Baska bolum bulamadim");
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				agacBitisSuresi = System.currentTimeMillis();
				Date date = new Date(agacBitisSuresi - agacOlusturulmaSuresi);
				System.out.println(new SimpleDateFormat("dd:ss").format(date));
				
				System.err.println("Maximum derinlik :"+tree.getMaxDepth());
				System.err.println("Minimum derinlik :"+tree.getMinDepth());
				
			}
		}

	}
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		}catch(InterruptedException ex) {
			
		}
	}
}
