import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Tree {
	private Dictionary mDictionary;
	private MapCreator mMapCreator;
	private Node head;
	private Stack<Node> nodes;
	private int maxDepth = 0;
	private int minDepth = Integer.MAX_VALUE;
	private boolean initialized = false;
	
	public static final int ROW_COLUMN = 6;
	public static final int LENGTH = 100;
	private char[][] totalMatrix = new char[ROW_COLUMN][LENGTH];
	
	public Tree() {
		
		nodes = new Stack<>();
		
	}
	public void setMapCreator(MapCreator mapCreator) {
		mMapCreator = mapCreator;
	}
	private void gameMatrixCreate() {
		totalMatrix = mMapCreator.generate(ROW_COLUMN, LENGTH);
		head = new Node(totalMatrix);
		initialized = true;
	}
	
	/*
	 * Bolumleri olusturacak olan yer
	 */
	public void create() throws TreeMultipleKeywordException,NoMorePathException{
		if(!initialized) {
			gameMatrixCreate();
		}
		nodes.push(head);
		while(!nodes.isEmpty()) {
			Node node = nodes.pop();
			System.out.println("Stack size : "+nodes.size());
			System.out.println("Removed from last matrix : "+node.removedKeyFromLastMatrix);
			if(node.mDepth > maxDepth) {
				maxDepth = node.mDepth;
			}
			char[][] subMatrix = Util.subMatrix(node.mtr,ROW_COLUMN);
			if(containsSpace(subMatrix)) {
				System.err.println("OYUN SONU");
				System.out.println();
				System.out.println(node.mDepth);
				Util.writeMatrix(subMatrix);
				System.out.println();
				Main.sleep(5000);
			}else {

				PossibilityFinder finder = new PossibilityFinder();
				finder.setDictionary(mDictionary);
				Iterator<String> keys = finder.findPossibilities(subMatrix);
				if(keys==null) {
					throw new TreeMultipleKeywordException();
				}
				if(keys.hasNext()) {
					while(keys.hasNext()) {
						String key = keys.next();
						System.out.println(key);
						char[][] childMtr = Util.copy(node.mtr);//yeni matrisi kopyaliyo
						List<PossibilityFinder.CellIndex> indexes = finder.getKeywordIndexes(key);
						for(int i =0;i<indexes.size();i++) {
							PossibilityFinder.CellIndex cellIndex = indexes.get(i);
							childMtr[cellIndex.i][cellIndex.j] = ' ';//hangi indexlerde kelime bulunduysa kelimeyi temizliyor
						}
						char[][] childMtrShifted = Util.shift(childMtr);
						Node child = new Node(childMtrShifted);
						child.removedKeyFromLastMatrix = key;
						child.setDepth(node.mDepth+1);//her birinin derinligi bir artiyor
						node.children.add(child);
					}
				}else {
					if(node.mDepth < minDepth) {
						minDepth = node.mDepth;
					}
					//throw new NoMorePathException();
				}
			}
			
			
			for(int i =0;i<node.children.size();i++) {
				nodes.add(node.children.get(i));
			}
		}
	}
	public int getMinDepth() {
		return minDepth;
	}
	public int getMaxDepth() {
		return maxDepth;
	}
	private boolean containsSpace(char[][] mtr) {
		for(int i =0;i<mtr.length;i++) {
			for(int j =0;j<mtr[0].length;j++) {
				if(mtr[i][j]==' ') {
					return true;
				}
			}
		}
		return false;
	}
	public class Node{
		List<Node> children;
		int mBeginColumnIndex = 0; 
		int mDepth;
		char[][] mtr;
		private String removedKeyFromLastMatrix;
		public Node(char[][] mtr) {
			children = new ArrayList<>();
			this.mtr = mtr;
		}
		public void setDepth(int depth) {
			mDepth = depth;
		}
		
		
	}
	public void setDictionary(Dictionary dictionary) {
		mDictionary = dictionary;
	}
}
