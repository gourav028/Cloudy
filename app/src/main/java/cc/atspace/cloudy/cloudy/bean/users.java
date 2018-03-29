package cc.atspace.cloudy.cloudy.bean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by garuav on 29-03-2018.
 */


public class users {

    public String email;

    public users(){}

    public users(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
