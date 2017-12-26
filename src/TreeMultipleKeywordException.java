
public class TreeMultipleKeywordException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TreeMultipleKeywordException() {
		super("Multiple keywords found in matrix which is not allowed!");
	}
	public TreeMultipleKeywordException(String str) {
		super(str);
	}

}
