package cc.atspace.cloudy.cloudy.bean;

/**
 * Created by garuav on 03-04-2018.
 */

public class chat {
    private String message, from;


    public chat() {
    }

    public chat(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
