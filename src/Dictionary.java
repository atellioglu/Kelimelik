import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by abdullahtellioglu on 11/09/17.
 */
public class Dictionary {
    public static final char[] VOWELS = {'A','E','I','İ','O','Ö','U','Ü'};
    public static final char[] NON_VOWELS = {'B','C','Ç','D','F','G','Ğ','H','J','K','L','M','N','P','R','S','Ş','T','V','Y','Z'};
    public static final char[] LETTERS = {'A','B','C','Ç','D','E','F','G','Ğ','H','I','İ','J','K','L','M','N','O','Ö','P','R','S','Ş','T','U','Ü','V','Y','Z'};
    private int frequency[];
    public double normalizeFrequency[];
    private static final int NORMALIZE_CONST = 1000;
    public char randomCharArray[];
    private HashSet<String> subKeys;
    private HashSet<String> keys;
    private List<String>[] keysArray;
    @SuppressWarnings("unchecked")
	public Dictionary(){
        subKeys = new HashSet<>();
        keys = new HashSet<>();
        frequency = new int[LETTERS.length];
        normalizeFrequency = new double[LETTERS.length];
        keysArray = ((List<String>[])new List[16]);
        for(int i =0;i<keysArray.length;i++) {
        		keysArray[i] = new ArrayList<String>();
        }
    }
    public Dictionary(String file){
        this();
        loadFile(file);
    }
    public boolean isKey(String key){
        if(keys.contains(key))
            return true;
        return false;
    }
    public boolean isSubKey(String key){
        if(subKeys.contains(key))
            return true;
        return false;
    }
    private void loadFile(String file){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String str;
            while((str = reader.readLine())!=null){
                handleKeyword(str);
            }
            getNormalizeFrequency();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (Exception ex){

                }
            }
        }
    }
    
    private void getNormalizeFrequency() {
    		long sum=0;
		for(int i=0;i<frequency.length;i++) {
			sum+=frequency[i];
		}

		for(int i=0;i<frequency.length;i++) {
			normalizeFrequency[i] = (double)frequency[i] / sum;
			normalizeFrequency[i]*=NORMALIZE_CONST;
		}
	}
    public String getRandomKey(int length) {
    		List<String> list = keysArray[length];
    		String str =list.get(new Random().nextInt(list.size()));
    		return str;
    }
	private void handleKeyword(String key){
        if(key!=null && key.length() > 4){
            if(key.split(" ").length == 1){
                for(int i =1;i<=key.length();i++){
                    subKeys.add(key.substring(0,i));
                }
                keys.add(key);
                //System.out.println("Dictionary : " + key);
            }
            
        }
        keysArray[key.length()].add(key);
        findCharFreq(key);
    }
    
    private void findCharFreq(String key) {
		for(int i=0;i<LETTERS.length;i++) {
			char ch = LETTERS[i];
			frequency[i] += (int)key.chars().filter(r -> r == ch).count();
		}
		
	}
    
}
