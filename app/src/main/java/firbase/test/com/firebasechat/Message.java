package firbase.test.com.firebasechat;

/**
 * Created by kraiba on 17/08/16.
 */
public class Message {
    private int id;
    private String text;
    private String time;
    private String user;

    public Message(){

    }
    public Message(int id, String text, String time, String user) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
