package firbase.test.com.firebasechat;

import java.util.List;

/**
 * Created by kraiba on 19/08/16.
 */
public class FireBaseUser {
    private String username;
    private List<UserLocation> locations;

    public FireBaseUser(String username, List<UserLocation> locations) {
        this.username = username;
        this.locations = locations;
    }

    public List<UserLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<UserLocation> locations) {
        this.locations = locations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
