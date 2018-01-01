import java.util.*;

/**
 * Created by abdullahtellioglu on 12/09/17. Finds the possible keywords into
 * the matrix!
 *
 */
public class PossibilityFinder {
	private Dictionary mDictionary;
	private Stack<StackObject> mainStack;
	public static final int SCORE = 50;
	private HashSet<String> foundKeys;
	private HashMap<String, List<CellIndex>> foundKeyIndexes;

	public PossibilityFinder() {
		mainStack = new Stack<>();
		foundKeys = new HashSet<>();
		foundKeyIndexes = new HashMap<>();
	}

	public void setDictionary(Dictionary dictionary) {
		mDictionary = dictionary;
	}

	public Iterator<String> findPossibilitiesRecursive(char[][] matrix) {
		int row = matrix.length;
		int col = matrix[0].length;
		markedMatrix = new boolean[row][col];
		char[][] recMatrix = Util.copy(matrix);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j <col; j++) {
				List<CellIndex> cellIndexList = new ArrayList<>();
				cellIndexList.add(new CellIndex(i, j));
				mFindPossibilitiesRecursive(i, j,cellIndexList,recMatrix,"");
			}
		}
		return foundKeys.iterator();
	}
	
	private boolean[][] markedMatrix;
	
	
	private void mFindPossibilitiesRecursive(int i ,int j,List<CellIndex> recList,char[][] mtr,String recBefore) {
		if (i < 0 || j < 0 || i >= Tree.ROW_COLUMN || j >= Tree.ROW_COLUMN || markedMatrix[i][j]) {
			return;
		}
		
		markedMatrix[i][j] = true;
		String str = recBefore + mtr[i][j];
		
		if (mDictionary.isKey(str)) {
			//System.out.println(str);
			// ayni kelime birden fazla bulunamaz!
			if (foundKeys.contains(str)) {
				// throw new TreeMultipleKeywordException(str+" founded more than once!");
			}
			
			foundKeys.add(str);
			foundKeyIndexes.put(str,recList);
		} else if (mDictionary.isSubKey(str)) {
			
			mFindPossibilitiesRecursive(i+1, j, getNewCellIndex(recList,new CellIndex(i+1, j)),mtr,str);
			mFindPossibilitiesRecursive(i-1, j, getNewCellIndex(recList,new CellIndex(i-1, j)),mtr,str);
			mFindPossibilitiesRecursive(i, j+1, getNewCellIndex(recList,new CellIndex(i, j+1)),mtr,str);
			mFindPossibilitiesRecursive(i, j -1, getNewCellIndex(recList,new CellIndex(i, j-1)),mtr,str);
			mFindPossibilitiesRecursive(i+1, j+1, getNewCellIndex(recList,new CellIndex(i+1, j+1)),mtr,str);
			mFindPossibilitiesRecursive(i+1, j-1, getNewCellIndex(recList,new CellIndex(i+1, j-1)),mtr,str);
			mFindPossibilitiesRecursive(i-1, j+1, getNewCellIndex(recList,new CellIndex(i-1, j+1)),mtr,str);
			mFindPossibilitiesRecursive(i-1, j-1, getNewCellIndex(recList,new CellIndex(i-1, j-1)),mtr,str);

		}
		markedMatrix[i][j] = false;
	}

	public Iterator<String> findPossibilities(char[][] charMatrix) throws TreeMultipleKeywordException {
		// Util.writeMatrix(charMatrix);

		Cell[][] cells = new Cell[Tree.ROW_COLUMN][Tree.ROW_COLUMN];
		for (int i = 0; i < Tree.ROW_COLUMN; i++) {
			for (int j = 0; j < Tree.ROW_COLUMN; j++) {
				Cell tmp = new Cell();
				tmp.cr = charMatrix[i][j];
				tmp.marked = false;
				cells[i][j] = tmp;
			}
		}
		for (int i = 0; i < Tree.ROW_COLUMN; i++) {
			for (int j = 0; j < Tree.ROW_COLUMN; j++) {
				StackObject stackObject = new StackObject(copy(cells), i, j, "");
				stackObject.cellIndexList.add(new CellIndex(i, j));
				mainStack.push(stackObject);
			}
		}
		while (!mainStack.isEmpty()) {
			StackObject stackObject = mainStack.pop();
			int i = stackObject.i;
			int j = stackObject.j;
			String before = stackObject.before;
			Cell[][] matrix = stackObject.cells;
			if (!(i < 0 || j < 0 || i >= Tree.ROW_COLUMN || j >= Tree.ROW_COLUMN || matrix[i][j].marked)) {
				String str = before + matrix[i][j].cr;
				matrix[i][j].marked = true;
				if (mDictionary.isKey(str)) {
					//System.out.println(str);
					// ayni kelime birden fazla bulunamaz!
					if (foundKeys.contains(str)) {
						// throw new TreeMultipleKeywordException(str+" founded more than once!");
					}

					foundKeys.add(str);
					foundKeyIndexes.put(str, stackObject.cellIndexList);
					// System.out.println("Buldum :" + str);
				} else if (mDictionary.isSubKey(str)) {
					
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i - 1, j, str));
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i, j + 1, str));
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i, j - 1, str));
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i + 1, j + 1, str));
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i + 1, j - 1, str));
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i - 1, j + 1, str));
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i - 1, j - 1, str));
					mainStack.push(new StackObject(stackObject.cellIndexList, copy(matrix), i + 1, j, str));

				}
				// matrix[i][j].marked = false;
				// write(matrix);
			}

		}

		/*
		 * boolean valid =areFoundKeysValid(foundKeys); if(!valid){ return false; }else{
		 * return true; }
		 */
		return getKeywords();
	}

	public Iterator<String> getKeywords() {
		return foundKeys.iterator();
	}

	public List<CellIndex> getKeywordIndexes(String str) {
		return foundKeyIndexes.get(str);
	}

	public Cell[][] copy(Cell[][] origin) {

		Cell[][] cells = new Cell[origin.length][origin[0].length];
		for (int i = 0; i < origin.length; i++) {
			for (int j = 0; j < origin[0].length; j++) {
				cells[i][j] = new Cell();
				cells[i][j].cr = origin[i][j].cr;
				cells[i][j].marked = origin[i][j].marked;
			}
		}
		return cells;
	}

	public void write(Cell[][] arr) {
		String str1 = "", str2 = "";
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				str1 += arr[i][j].cr;
				str2 += arr[i][j].marked ? '+' : '-';
			}
			str1 += "\n";
			str2 += "\n";
		}
		System.out.println(str1);
		System.out.println(str2);
	}

	public class Cell {
		char cr;
		boolean marked;

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return cr + (marked ? "+" : "-");
		}
	}

	public class CellIndex {
		int i, j;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public int getJ() {
			return j;
		}

		public void setJ(int j) {
			this.j = j;
		}

		public CellIndex(int i, int j) {
			this.i = i;
			this.j = j;
		}

	}
	public List<CellIndex> getNewCellIndex(List<CellIndex> list,CellIndex newC){
		List<CellIndex> newList = new ArrayList<>();
		newList.addAll(list);
		newList.add(newC);
		return newList;
		
	}
	private class StackObject {
		public StackObject(Cell[][] cells, int i, int j, String before) {
			this.cells = cells;
			this.i = i;
			this.j = j;
			this.before = before;
			cellIndexList = new ArrayList<>();
		}

		public StackObject(List<CellIndex> origin, Cell[][] cells, int i, int j, String before) {
			this.cells = cells;
			this.i = i;
			this.j = j;
			this.before = before;
			cellIndexList = new ArrayList<>();
			cellIndexList.addAll(origin);
			cellIndexList.add(new CellIndex(i, j));
		}

		private List<CellIndex> cellIndexList;
		Cell[][] cells;
		private int i, j;
		private String before;
	}
}
