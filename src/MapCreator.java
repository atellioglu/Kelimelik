import java.util.Random;

public class MapCreator {
	private Dictionary mDictionary;
	public MapCreator(Dictionary dictionary) {
		mDictionary = dictionary;
	}
	public char[][] generate(int row,int column){
		char[][] totalMatrix = new char[row][column];
		for (int i = 0; i < totalMatrix.length; i++) {
			for (int j = 0; j < totalMatrix[0].length; j++) {
				totalMatrix[i][j] = ' ';
			}
		}
		Random rnd = new Random();
		int vowelCount = (rnd.nextInt(column/2) + column/3) * 3;
		
		for (int i = 0; i < vowelCount; i++) {
			int x = rnd.nextInt(row);
			int y = rnd.nextInt(column);
			while (totalMatrix[x][y] != ' ') {
				x = rnd.nextInt(row);
				y = rnd.nextInt(column);
			}
			totalMatrix[x][y] = Dictionary.VOWELS[new Random().nextInt(Dictionary.VOWELS.length)];
		}
		for (int i = 0; i < totalMatrix.length; i++) {
			for (int j = 0; j < totalMatrix[i].length; j++) {
				if (totalMatrix[i][j] == ' ') {
					totalMatrix[i][j] = Dictionary.NON_VOWELS[new Random().nextInt(Dictionary.NON_VOWELS.length)];
				}
			}
		}
		
		System.out.println("Sesli harf sayisi : "+vowelCount);
		System.out.println("Sessiz harf sayisi :"+ String.valueOf((row * column) - vowelCount));
		appendSuffixes(totalMatrix);
		Util.writeMatrix(totalMatrix);
		
		return totalMatrix;
	}
	private static final String[] SUFFIXES = {"MAK","MA","MEK","ME","LUK","CI","CIK","LIK","ÇI","MAZ","Cİ","ÇE","TİK","MAK","MEK"};
	private int appendSuffixes(char[][] mtr) {
		int row = mtr.length;
		int col = mtr[0].length;
		Random rnd = new Random();
		int count = rnd.nextInt(row * col  / 4) + 100;
		for(int i =0;i<count;i++) {
			int x = rnd.nextInt(row -4) + 2;
			int y = rnd.nextInt(col - 6) + 3;
			int suffixIndex = rnd.nextInt(SUFFIXES.length);
			int suffixLength = SUFFIXES[suffixIndex].length();
			String suffix = SUFFIXES[suffixIndex];
			int mod =rnd.nextInt(7);
			//modlar ekleri nasil yazdiracagini belirtiyo.
			switch(mod) {
			case 0:
				//yatay olarak
				mtr[x][y++] = suffix.charAt(0);
				mtr[x][y++] = suffix.charAt(1);
				if(suffixLength == 3) {
					mtr[x][y] = suffix.charAt(2);
				}
				break;
			case 1:
				//dikey olarak
				mtr[x++][y] = suffix.charAt(0);
				mtr[x++][y] = suffix.charAt(1);
				if(suffixLength == 3) {
					mtr[x][y] = suffix.charAt(2);
				}
				break;
			case 2:
				//capraz olarak
				mtr[x++][y++] = suffix.charAt(0);
				mtr[x++][y++] = suffix.charAt(1);
				if(suffixLength == 3) {
					mtr[x][y] = suffix.charAt(2);
				}
				break;
			case 3:
				//sag alt ve ust
				mtr[x++][y++] = suffix.charAt(0);
				mtr[x--][y] = suffix.charAt(1);
				if(suffixLength == 3) {
					mtr[x][y] = suffix.charAt(2);
				}
				break;
			case 4:
				mtr[x][y--] = suffix.charAt(0);
				mtr[x][y--] = suffix.charAt(1);
				if(suffixLength == 3) {
					mtr[x][y] = suffix.charAt(2);
				}
				break;
			case 5:
				mtr[x--][y] = suffix.charAt(0);
				mtr[x--][y] = suffix.charAt(1);
				if(suffixLength == 3) {
					mtr[x][y] = suffix.charAt(2);
				}
				break;
			case 6:
				//ters capraz
				mtr[x--][y--] = suffix.charAt(0);
				mtr[x--][y--] = suffix.charAt(1);
				if(suffixLength == 3) {
					mtr[x][y] = suffix.charAt(2);
				}
				break;
				
			}
		}
		
		return count;
	}
}
