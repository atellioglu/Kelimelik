import static org.junit.Assert.assertArrayEquals;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

class TreeTester {

	@Test
	void shifTest() {
		char[][] myArray = {{'a','b',' ','c'},{'k',' ','l','m'},{'x','y','z','t'}};
		char[][] tmp = Util.shift(myArray);
		char[][] expected = {{'a','b','c',' '},{'k','l','m',' '},{'x','y','z','t'}};
		for(int i =0;i<expected.length;i++) {
			assertArrayEquals(expected[i],tmp[i]);	
		}
		
	}
	@Test
	void possibiltyFinderZam() {
		String str = "EBPUPD\n" + 
				"ĞNHFPG\n" + 
				"ICÇZAM\n" + 
				"ĞFÖIII\n" + 
				"ŞOUZIİ\n" + 
				"YŞİKFJ";
		char[][] arr = new char[6][6];
		for(int i =0;i<arr.length;i++) {
			String rowStr =  str.split("\n")[i];
			for(int j=0;j<rowStr.length();j++) {
				arr[i][j] = rowStr.charAt(j);
			}
		}
		Util.writeMatrix(arr);
		PossibilityFinder finder = new PossibilityFinder();
		finder.setDictionary(new Dictionary("sozluk"));
		try {
			Iterator<String> it =finder.findPossibilities(arr);
			while(it.hasNext()) {
				System.out.println(it.next());
			}
		} catch (TreeMultipleKeywordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
