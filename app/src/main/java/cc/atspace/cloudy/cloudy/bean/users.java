package cc.atspace.cloudy.cloudy.bean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by garuav on 29-03-2018.
 */


public class users {

    public String name, phone, profile;

    public users(){}

    public users(String name, String phone, String profile) {
        this.name = name;
        this.phone = phone;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
