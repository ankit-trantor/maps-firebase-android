package firbase.test.com.firebasechat;

/**
 * Created by kraiba on 19/08/16.
 */
public class UserLocation  implements Comparable<UserLocation>{
    private double latitude;
    private double longitude;
    private String timestamp;

    public UserLocation(){

    }
    public UserLocation(double latitude, double longitude, String timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public int compareTo(UserLocation o) {
        if (getTimestamp() == null || o.getTimestamp() == null)
            return 0;
        return getTimestamp().compareTo(o.getTimestamp());
    }
}
