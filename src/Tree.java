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
	private Queue<Node> nodes;
	private int maxDepth = 0;
	private boolean initialized = false;
	
	public static final int ROW_COLUMN = 6;
	public static final int LENGTH = 500;
	private char[][] totalMatrix = new char[ROW_COLUMN][LENGTH];
	
	public Tree() {
		
		nodes = new LinkedList<>();
		
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
		nodes.add(head);
		while(!nodes.isEmpty()) {
			System.out.println("Stack size : "+nodes.size());
			Node node = nodes.poll();
			PossibilityFinder finder = new PossibilityFinder();
			finder.setDictionary(mDictionary);
			Iterator<String> keys = finder.findPossibilities(Util.subMatrix(node.mtr,ROW_COLUMN));
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
					child.setDepth(node.mDepth+1);//her birinin derinligi bir artiyor
					node.children.add(child);
				}
			}else {
				
				throw new NoMorePathException();
				
			}
			
			for(int i =0;i<node.children.size();i++) {
				nodes.add(node.children.get(i));
			}
		}
	}

	public class Node{
		List<Node> children;
		int mBeginColumnIndex = 0; 
		int mDepth;
		char[][] mtr;
		
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
