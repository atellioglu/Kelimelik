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
			try {
				tree.create();
				finished = true;
			} catch (NoMorePathException e) {
				// e.printStackTrace();
				System.err.println("Baska bolum bulamadim");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
