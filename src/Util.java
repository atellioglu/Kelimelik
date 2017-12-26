/**
 * Created by abdullahtellioglu on 11/09/17.
 */
public class Util {
	public static void writeMatrix(char[][] mtr) {
		System.out.println();
		for (int i = 0; i < mtr.length; i++) {
			for (int j = 0; j < mtr[i].length; j++) {
				System.out.print(mtr[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	/**
	 * Bosluklari yok edip matristeki elemanlari bir sola alicak...
	 * @param mtr
	 * @return yeni matrix
	 */
	public static char[][] shift(char[][] real){
		char[][] mtr = new char[real.length][real[0].length];
		for(int i =0;i<mtr.length;i++) {
			int mtrJ = 0;
			for(int j=0;j<mtr[0].length;j++) {
				if(real[i][j] != ' ') {
					mtr[i][mtrJ] = real[i][j];
					mtrJ++;
				}
			}
			for(int j = mtrJ;j<mtr[0].length;j++) {
				mtr[i][j] = ' ';
			}
		}
		return mtr;
	}
	/**
	 * Bosluklari yok edip matristeki elemanlari bir sola alicak...
	 * @param mtr
	 * @param columnLength bakilacak maximum kolon uzunlugu
	 * @return yeni matrix
	 */
	public static char[][] shift(char[][] real,int columnLength){
		
		char[][] mtr = new char[real.length][real[0].length];
		for(int i =0;i<mtr.length;i++) {
			int mtrJ = 0;
			for(int j=0;j<columnLength;j++) {
				if(real[i][j] != ' ') {
					mtr[i][mtrJ] = real[i][j];
					mtrJ++;
				}
			}
			for(int j = mtrJ;j<mtr[0].length;j++) {
				mtr[i][j] = ' ';
			}
		}
		return mtr;
	}
	public static char[][] subMatrix(char[][] real,int colLength){
		char[][] mtr = new char[real.length][colLength];
		for(int i =0;i<mtr.length;i++) {
			for(int j =0;j<colLength;j++) {
				mtr[i][j] = real[i][j];
			}
		}
		
		return mtr;
	}
	public static void empty(char[][] mtr) {
		for(int i =0;i<mtr.length;i++) {
			for(int j=0;j<mtr[0].length;j++) {
				mtr[i][j]= ' ';	
			}
		}
	}
	public static char[][] copy(char[][] mtr) {
		char[][] mMtr = new char[mtr.length][mtr[0].length];
		for(int i =0;i<mMtr.length;i++) {
			for(int j =0;j<mMtr[0].length;j++) {
				mMtr[i][j] = mtr[i][j];
			}
		}
		return mMtr;
	}
}
