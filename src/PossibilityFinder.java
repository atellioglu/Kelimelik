import java.util.*;

/**
 * Created by abdullahtellioglu on 12/09/17.
 * Finds the possible keywords into the matrix!
 *
 */
public class PossibilityFinder {
    private Dictionary mDictionary;
    private Stack<StackObject> mainStack;
    public static final int SCORE = 50;
    private HashSet<String> foundKeys;
    private HashMap<String,List<CellIndex>> foundKeyIndexes;
    public PossibilityFinder(){
        mainStack = new Stack<>();
        foundKeys = new HashSet<>();
        foundKeyIndexes = new HashMap<>();
    }
    public void setDictionary(Dictionary dictionary){
        mDictionary = dictionary;
    }
    public Iterator<String> findPossibilities(char[][] charMatrix) throws TreeMultipleKeywordException{
		Util.writeMatrix(charMatrix);
        Cell[][] cells = new Cell[Tree.ROW_COLUMN][Tree.ROW_COLUMN];
        for(int i =0;i<Tree.ROW_COLUMN;i++){
            for(int j=0;j<Tree.ROW_COLUMN;j++){
                Cell tmp = new Cell();
                tmp.cr = charMatrix[i][j];
                tmp.marked = false;
                cells[i][j] = tmp;
            }
        }
        for(int i =0;i<Tree.ROW_COLUMN;i++){
            for(int j=0;j<Tree.ROW_COLUMN;j++){
                StackObject stackObject =new StackObject(copy(cells),i,j,"");
                stackObject.cellIndexList.add(new CellIndex(i,j));
                mainStack.push(stackObject);
            }
        }
        while(!mainStack.isEmpty()){
            StackObject stackObject = mainStack.pop();
            int i = stackObject.i;
            int j = stackObject.j;
            String before = stackObject.before;
            Cell[][] matrix = stackObject.cells;
            if(!(i < 0 || j < 0 || i >=Tree.ROW_COLUMN || j >= Tree.ROW_COLUMN || matrix[i][j].marked)){
                String str = before + matrix[i][j].cr;
                matrix[i][j].marked = true;
                if(mDictionary.isKey(str)){
                    //ayni kelime birden fazla bulunamaz!
                    if(foundKeys.contains(str)) {
                    		//throw new TreeMultipleKeywordException(str+" founded more than once!");
                    }
                    foundKeys.add(str);
                    foundKeyIndexes.put(str,stackObject.cellIndexList);
                    //System.out.println("Buldum :" + str);
                }else if(mDictionary.isSubKey(str)){
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i-1,j,str));
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i,j+1,str));
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i,j-1,str));
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i+1,j+1,str));
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i+1,j-1,str));
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i-1,j+1,str));
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i-1,j-1,str));
                    mainStack.push(new StackObject(stackObject.cellIndexList,copy(matrix),i+1,j,str));
                }else{
                    //System.out.println("Kitlendim : "+str);
                	 
                }
            }

        }
        /*
        boolean valid =areFoundKeysValid(foundKeys);
        if(!valid){
            return false;
        }else{
            return true;
        }*/
        return getKeywords();
    }

    private boolean areFoundKeysValid(HashSet<String> keys){
        int ucHarf = 0,dortHarf = 0,besHarf =0,altiHarf =0,yediHarf = 0,yediUzeri = 0;
        Iterator<String> iterator = keys.iterator();
        String writer = "";
        while(iterator.hasNext()){
            String kk = iterator.next();
            writer += kk + ", ";
            switch (kk.length()){
                case 2:
                    break;
                case 3:
                    ucHarf++;
                    break;
                case 4:
                    dortHarf++;
                    break;
                case 5:
                    besHarf++;
                    break;
                case 6:
                    altiHarf++;
                    break;
                case 7:
                    yediHarf++;
                    break;
                default:
                    yediUzeri++;
                    break;
            }
        }
        System.out.println(writer);
        int score = ucHarf * 1 + dortHarf * 2 + besHarf * 4 + altiHarf * 10 + yediHarf * 25 + yediUzeri * 75;
        System.out.println(score);
        if(score > SCORE){
            return true;
        }
        return false;
    }
    public Iterator<String> getKeywords(){
        return foundKeys.iterator();
    }
    public List<CellIndex> getKeywordIndexes(String str){
        return foundKeyIndexes.get(str);
    }
    public Cell[][] copy(Cell[][] origin){
        Cell[][] cells = new Cell[origin.length][origin[0].length];
        for(int i =0;i<origin.length;i++){
            for(int j=0;j<origin[0].length;j++){
                cells[i][j] = new Cell();
                cells[i][j].cr = origin[i][j].cr;
                cells[i][j].marked = origin[i][j].marked;
            }
        }
        return cells;
    }
    public void write(Cell[][] arr) {
    		String str1="",str2="";
    		for(int i =0;i<arr.length;i++) {
    			for(int j=0;j<arr[0].length;j++) {
    				str1+=arr[i][j].cr;
    				str2+=arr[i][j].marked ? '+' : '-';
    			}
    		}
    		System.out.println(str1);
    		System.out.println(str2);
    }
    public class Cell{
        char cr;
        boolean marked;
        @Override
        public String toString() {
        	// TODO Auto-generated method stub
        	return cr + (marked ? "+" : "-");
        }
    }
    public class CellIndex{
        int i,j;

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

    private class StackObject{
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
            cellIndexList.add(new CellIndex(i,j));
        }
        private List<CellIndex> cellIndexList;
        Cell[][] cells;
        private int i,j;
        private String before;
    }
}
