import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

/**
 * Created by abdullahtellioglu on 11/09/17.
 */
public class Dictionary {
    public static final char[] VOWELS = {'A','E','I','İ','O','Ö','U','Ü'};
    public static final char[] NON_VOWELS = {'B','C','Ç','D','F','G','Ğ','H','J','K','L','M','N','P','R','S','Ş','T','V','Y','Z'};
    private HashSet<String> subKeys;
    private HashSet<String> keys;
    public Dictionary(){
        subKeys = new HashSet<>();
        keys = new HashSet<>();
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
            reader = new BufferedReader(new FileReader(file));
            String str;
            while((str = reader.readLine())!=null){
                handleKeyword(str);
            }
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
    
    private void handleKeyword(String key){
        if(key!=null){
            if(key.split(" ").length == 1){
                for(int i =1;i<=key.length();i++){
                    subKeys.add(key.substring(0,i));
                }
                keys.add(key);
            }
        }
    }
}
