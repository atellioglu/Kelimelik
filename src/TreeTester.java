import static org.junit.Assert.assertArrayEquals;

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
}
