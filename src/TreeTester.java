
public class TreeTester {
	/*

	@Test
	public void shifTest() {
		char[][] myArray = { { 'a', 'b', ' ', 'c' }, { 'k', ' ', 'l', 'm' }, { 'x', 'y', 'z', 't' } };
		char[][] tmp = Util.shift(myArray);
		char[][] expected = { { 'a', 'b', 'c', ' ' }, { 'k', 'l', 'm', ' ' }, { 'x', 'y', 'z', 't' } };
		for (int i = 0; i < expected.length; i++) {
			assertArrayEquals(expected[i], tmp[i]);
		}

	}

	@Test
	public void possibiltyFinderZam() {
		String str = "EBPUPD\n" + "ĞNHFPG\n" + "ICÇZAM\n" + "ĞFÖIII\n" + "ŞOUZIİ\n" + "YŞİKFJ";
		char[][] arr = new char[6][6];
		for (int i = 0; i < arr.length; i++) {
			String rowStr = str.split("\n")[i];
			for (int j = 0; j < rowStr.length(); j++) {
				arr[i][j] = rowStr.charAt(j);
			}
		}
		Util.writeMatrix(arr);
		PossibilityFinder finder = new PossibilityFinder();
		finder.setDictionary(new Dictionary("sozluk"));
		try {
			Iterator<String> it = finder.findPossibilities(arr);
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} catch (TreeMultipleKeywordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void possibilityFinderRecursive() {
		String str = "EBPUPD\n" + "ĞNHFPG\n" + "ICÇZAM\n" + "ĞFÖIII\n" + "ŞOUZIİ\n" + "YŞİKFJ";
		char[][] arr = new char[6][6];
		for (int i = 0; i < arr.length; i++) {
			String rowStr = str.split("\n")[i];
			for (int j = 0; j < rowStr.length(); j++) {
				arr[i][j] = rowStr.charAt(j);
			}
		}
		Util.writeMatrix(arr);
		PossibilityFinder finder = new PossibilityFinder();
		finder.setDictionary(new Dictionary("sozluk"));
		try {
			Iterator<String> it = finder.findPossibilitiesRecursive(arr);
			List<String> recList = new ArrayList<>();

			while (it.hasNext()) {
				String str1 = it.next();
				recList.add(str1);
				System.out.println(str1);
			}
			
			Iterator<String> it2 = finder.findPossibilities(arr);
			int index = 0;
			while (it2.hasNext()) {
				if (!recList.contains(it2.next())) {
					System.out.println("HATA");
					fail("Not found!");

				} else {
					index++;
				}
			}
			if (index != recList.size()) {
				fail("Size did not match!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
