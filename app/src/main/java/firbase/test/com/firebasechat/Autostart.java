package firbase.test.com.firebasechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by kraiba on 17/08/16.
 */
public class Autostart extends BroadcastReceiver
{
    public void onReceive(Context arg0, Intent arg1)
    {
        Intent intent = new Intent(arg0,LocationService.class);
        arg0.startService(intent);
        Log.i("Autostart", "started");
    }
}
