import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import exception.NotEnoughDepth;

public class Tree {
	private Dictionary mDictionary;
	private MapCreator mMapCreator;
	private Node head;
	private Stack<Node> nodes;
	private int maxDepth = 0;
	private int minDepth = Integer.MAX_VALUE;
	private boolean initialized = false;
	public static final int ENOUGH_DEPTH = 25;
	public static final int ROW_COLUMN = 6;
	public static final int LENGTH = 40;
	private char[][] totalMatrix = new char[ROW_COLUMN][LENGTH];
	
	public Tree() {
		
		nodes = new Stack<>();
		
	}
	public void setMapCreator(MapCreator mapCreator) {
		mMapCreator = mapCreator;
	}
	public char[][] getMatrix(){
		return totalMatrix;
	}
	private void gameMatrixCreate() {
		totalMatrix = mMapCreator.generate(ROW_COLUMN, LENGTH);
		head = new Node(totalMatrix);
		initialized = true;
		Util.writeMatrix(totalMatrix);
		//Main.sleep(1000);
	}
	
	/*
	 * Bolumleri olusturacak olan yer
	 */
	public void create() throws TreeMultipleKeywordException,NoMorePathException,NotEnoughDepth{
		if(!initialized) {
			gameMatrixCreate();
		}
		nodes.push(head);
		
		int idx = 0;
		
		while(!nodes.isEmpty()) {
			idx++;
			if(idx==1000) {
				System.gc();
				idx=0;
			}
			Node node = nodes.pop();
			removeProcessedSis(node);
			//System.out.println("Stack size : "+nodes.size());
			//System.out.println("Removed from last matrix : "+node.removedKeyFromLastMatrix);
			//System.out.println("Current depth : " + node.mDepth);
			char[][] subMatrix = Util.subMatrix(node.mtr,ROW_COLUMN);
			if(containsSpace(subMatrix) || node.mDepth > ENOUGH_DEPTH) {
				//for(int i =0;i<node.olderKeys.size();i++) {
					//System.out.print(node.olderKeys.get(i)+" ");
				//}
				//System.out.println(node.key);
				//System.err.println("OYUN SONU");
			}else {
				PossibilityFinder finder = new PossibilityFinder();
				finder.setDictionary(mDictionary);
				Iterator<String> keys = finder.findPossibilitiesRecursive(subMatrix);
				if(keys==null) {
					throw new TreeMultipleKeywordException();
				}
				if(keys.hasNext()) {
					while(keys.hasNext()) {
						String key = keys.next();
						//System.out.println(key);
						char[][] childMtr = Util.copy(node.mtr);//yeni matrisi kopyaliyo
						List<PossibilityFinder.CellIndex> indexes = finder.getKeywordIndexes(key);
						for(int i =0;i<indexes.size();i++) {
							PossibilityFinder.CellIndex cellIndex = indexes.get(i);
							childMtr[cellIndex.i][cellIndex.j] = ' ';//hangi indexlerde kelime bulunduysa kelimeyi temizliyor
						}
						char[][] childMtrShifted = Util.shift(childMtr);
						Node child = new Node(childMtrShifted);
						child.key = key;
						child.olderKeys.addAll(node.olderKeys);
						child.olderKeys.add(node.key);
						child.removedKeyFromLastMatrix = key;
						child.setDepth(node.mDepth+1);//her birinin derinligi bir artiyor
						node.addChild(child);
						//Util.writeMatrix(childMtrShifted);
					}

					
				}else {
					if(node.mDepth <ENOUGH_DEPTH) {
						throw new NotEnoughDepth();
					}
					//throw new NoMorePathException();
				}
				
			}
			if(node.mDepth<ENOUGH_DEPTH) {
				for(int i =0;i<node.children.size();i++) {
					nodes.add(node.children.get(i));
				}	
			}
		}
	}
	
	public void removeProcessedSis(Node node) {
		Node parent = node.getParent();
		if(parent== null)
			return;
		int nodeIndex = parent.children.indexOf(node);
		if(parent.children.size()!=nodeIndex+1) {
			parent.children.remove(nodeIndex+1);
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
		Node mParent;
		List<Node> children;
		int mBeginColumnIndex = 0; 
		int mDepth;
		char[][] mtr;
		private String key;
		private String removedKeyFromLastMatrix;
		private ArrayList<String> olderKeys;
		public Node(char[][] mtr) {
			children = new ArrayList<>();
			this.mtr = mtr;
			olderKeys = new ArrayList<>();
		}
		public void setDepth(int depth) {
			mDepth = depth;
		}
		public Node getParent() {
			return mParent;
		}
		public void setParent(Node parent) {
			mParent = parent;
		}
		public boolean addChild(Node child) {
			if(child == null) {
				return false;
			}
			children.add(child);
			child.setParent(this);
			return true;
		}
		
	}
	public void setDictionary(Dictionary dictionary) {
		mDictionary = dictionary;
	}
}
