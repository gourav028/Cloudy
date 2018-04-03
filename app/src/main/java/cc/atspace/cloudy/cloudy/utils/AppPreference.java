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

    private final static String USER_TYPE = "USER_TYPE";
    private final static String USER = null;
    private final static String SECRET_KEY = "SECRET_KEY";
    private final static String USER_ID = "USER_ID";
    private final static String ADDRESS = "ADDRESS";
    private final static String CITY = "CITY";
    private final static String ZIP_CODE = "ZIP";
    private final static String DEGREE = "DEGREE";
    private final static String STATE = "STATE";
    private final static String COUNTRY = "COUNTRY";
    private final static String MOBILE = "MOBILE";
    private final static String EMAIL = "EMAIL";
    private final static String FIRSTNAME = "FIRSTNAME";
    private final static String LASTNAME = "LASTNAME";
    private final static String PROFILE_PIC = "PROFILE_PIC";
    private final static String CERTIFICATE = "CERTIFICATE";
    private final static String SHIPPING_NAME = "SHIPPING_NAME";
    private final static String SHIPPING_ADDRESS = "SHIPPING_ADDRESS";
    private final static String ADDRESS_ID = "ADDRESS_ID";
    private final static String CART_COUNTER = "CART_COUNTER";


    public AppPreference(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreference getInstance(Context context) {

        return new AppPreference(context);
    }


    public String getUser() {
        return sharedPreferences.getString(USER, null);
    }

    public void setUser(String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER, userType);
        editor.commit();
    }

    public int getCounter() {
        return sharedPreferences.getInt(CART_COUNTER, 0);
    }


    public void setCounter(int counter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CART_COUNTER, counter);
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

    public boolean isLoggedIn() {
        if (sharedPreferences.contains(USER_ID)) {
            return true;
        }

        return false;
    }

    public void setFirstName(String firstName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIRSTNAME, firstName);
        editor.commit();
    }

    public void setLastName(String lastName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LASTNAME, lastName);
        editor.commit();
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public void setMobile(String mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILE, mobile);
        editor.commit();
    }
    public String getFirstName() {
        String userType = sharedPreferences.getString(FIRSTNAME, null);
        return userType;
    }

    public void setUserId(String mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, mobile);
        editor.commit();
    }
    public String getUserId() {
        String userType = sharedPreferences.getString(USER_ID, null);
        return userType;
    }

    public String getLastName() {
        String userType = sharedPreferences.getString(LASTNAME, null);
        return userType;
    }

    public String getEmail() {
        String userType = sharedPreferences.getString(EMAIL, null);
        return userType;
    }

    public String getMobile() {
        String userType = sharedPreferences.getString(MOBILE, null);
        return userType;
    }


    /*--------------------------------------------------------------*/
    public void setProfilePic(String profilePic) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_PIC, profilePic);
        editor.commit();
    }

    public void setCertificate(String certificate) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CERTIFICATE, certificate);
        editor.commit();
    }

    public String getProfilePic() {
        return sharedPreferences.getString(PROFILE_PIC, "");
    }

    public String getCertificate() {
        return sharedPreferences.getString(CERTIFICATE, "");
    }
}
