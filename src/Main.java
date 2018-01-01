import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import exception.NotEnoughDepth;

/**
 * Created by abdullahtellioglu on 11/09/17.
 */
public class Main {
	private static int successIndex = 0;
	private static int failIndex = 0;

	public static void main(String[] args) {
		/*
		 * Dictionary dictionary = new Dictionary("trkelimeler"); Matrix matrix = new
		 * Matrix(); matrix.setDictionary(dictionary); matrix =
		 * PossibilityFinder.findMatrix(matrix,dictionary);
		 */
		System.out.println("Default file encoding :"+System.getProperty("file.encoding"));
		System.setProperty("file.encoding", "UTF-8");
		System.out.println("Changed file encoding :"+System.getProperty("file.encoding"));
		File successFolder = new File("success");
		if (!successFolder.exists())
			successFolder.mkdir();
		File failFolder = new File("fail");
		if (!failFolder.exists()) {
			failFolder.mkdir();
		}
		Dictionary dictionary = new Dictionary("sozluk");
		MapCreator mapCreator = new MapCreator(dictionary);
		while (true) {
			boolean finished = false;
			Tree tree;
			PrintWriter writer = null;
			while (!finished) {
				tree = new Tree();
				tree.setMapCreator(mapCreator);
				tree.setDictionary(dictionary);
				long agacOlusturulmaSuresi = 0;
				long agacBitisSuresi = 0;
				try {
					System.out.println("Agac olusturuluyor");
					agacOlusturulmaSuresi = System.currentTimeMillis();
					tree.create();
					System.out.println("Agac basariyla olusturuldu");
					finished = true;
					File file = new File("success/" + successIndex);
					successIndex++;
					writer = new PrintWriter(new FileWriter(file));
					writer.write(treeMatrixString(tree));

				} catch (NoMorePathException | NotEnoughDepth e) {
					e.printStackTrace();
					
					File file = new File("fail/" + failIndex);
					failIndex++;
					try {
						writer = new PrintWriter(new FileWriter(file));
						writer.write(treeMatrixString(tree));
					}catch(IOException ex) {
						ex.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					agacBitisSuresi = System.currentTimeMillis();
					long toplamAgacSuresi = agacBitisSuresi - agacOlusturulmaSuresi;
					if (writer != null) {
						writer.write("Bitis Suresi :" + String.valueOf(toplamAgacSuresi));
						writer.flush();
						try {
							writer.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}

				}
			}
		}

	}

	public static String treeMatrixString(Tree tree) {
		String str = "";
		for (int i = 0; i < Tree.ROW_COLUMN; i++) {
			for (int j = 0; j < Tree.LENGTH; j++) {
				str += tree.getMatrix()[i][j];
			}
			str += "\n";
		}
		return str;
	}

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {

		}
	}
}
