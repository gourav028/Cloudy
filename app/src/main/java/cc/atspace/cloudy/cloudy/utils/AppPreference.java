package cc.atspace.cloudy.cloudy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by darshna on 2/22/2017.
 */
public class AppPreference {
    private static AppPreference appPreference;
    private Context context;
    private SharedPreferences sharedPreferences;

    private final static String PREFERENCE_NAME = "AppPreference";

    private final static String USER = null;
    private final static String SECRET_KEY = "SECRET_KEY";
    private final static String USER_ID = "USER_ID";
    private final static String PHONE = "PHONE";
    private final static String NAME = "NAME";
    private final static String PROFILE_PIC = "PROFILE_PIC";
    private final static String COUNTER = "CART_COUNTER";
    private final static String CURRENT_TASK = "CURRENT_TASK";


    public AppPreference(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreference getInstance(Context context) {

        return new AppPreference(context);
    }

    public boolean isLoggedIn() {
        if (sharedPreferences.contains(USER_ID)) {
            return true;
        }
        return false;
    }

    public String getUser()
    {
        return sharedPreferences.getString(USER, null);
    }

    public void setUser(String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER, userType);
        editor.commit();
    }

    public int getCounter()
    {
        return sharedPreferences.getInt(COUNTER, 0);
    }


    public void setCounter(int counter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNTER, counter);
        editor.commit();
    }

    public String getSecretKey() {
        String userType = sharedPreferences.getString(SECRET_KEY, null);
        return userType;
    }

    public void setSecretKey(String secretKey) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SECRET_KEY, secretKey);
        editor.commit();
    }


    public String getPhone() {
        String userType = sharedPreferences.getString(PHONE, null);
        return userType;
    }


    public void setPhone(String mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE, mobile);
        editor.commit();
    }

    public String getName() {
        String userType = sharedPreferences.getString(NAME, null);
        return userType;
    }

    public void setName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getUserId() {
        String userType = sharedPreferences.getString(USER_ID, null);
        return userType;
    }


    public void setUserId(String mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, mobile);
        editor.commit();
    }

    public String getCurrentTask() {
        String userType = sharedPreferences.getString(CURRENT_TASK, "default");
        return userType;
    }


    public void setCurrentTask(String currentTask) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_TASK, currentTask);
        editor.commit();
    }

    /*--------------------------------------------------------------*/
    public void setProfilePic(String profilePic) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_PIC, profilePic);
        editor.commit();
    }

    public String getProfilePic()
    {
        return sharedPreferences.getString(PROFILE_PIC, "");
    }
}
