package mobil.ev.socarpetroleum;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistantStorage {
    public static final String STORAGE_NAME = "StorageName";
    private static SharedPreferences setting = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init(Context cntxt){
        context = cntxt;
    }
    private  static void init(){
        setting = context.getSharedPreferences(STORAGE_NAME,Context.MODE_PRIVATE);
        editor = setting.edit();
    }
    public static void addProperty(String name,String value){
        if(setting == null){
            init();
        }
        editor.putString(name,value);
        editor.apply();
    }
    public boolean cont(String key){
        if(setting == null){
            init();
        }
        return setting.contains(key);
    }



    public static String getProperty( String name){
        if(setting == null){
            init();
        }
        return setting.getString(name,"555");
    }


}

