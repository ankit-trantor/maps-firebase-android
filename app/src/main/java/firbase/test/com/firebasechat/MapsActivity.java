package firbase.test.com.firebasechat;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    public HashMap<String,Marker> markerHashMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        markerHashMap=new HashMap<>();

        if(!LocationService.isRunning){
            Intent intent = new Intent(this, LocationService.class);
            startService(intent);


        }
        mapFragment.getMapAsync(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("location");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                add_marker(dataSnapshot.getKey(),getLastLocation(dataSnapshot));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                add_marker(dataSnapshot.getKey(),getLastLocation(dataSnapshot));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
    public UserLocation getLastLocation(DataSnapshot dataSnapshot){

        GenericTypeIndicator<HashMap<String,UserLocation>> t = new GenericTypeIndicator<HashMap<String,UserLocation>>() {};
        HashMap<String,UserLocation> userLocations = dataSnapshot.getValue(t);

        List<UserLocation> list=new ArrayList<UserLocation>(userLocations.values());


        Collections.sort(list, new Comparator<UserLocation>() {
            public int compare(UserLocation o1, UserLocation o2) {
                if (o1.getTimestamp() == null || o2.getTimestamp() == null)
                    return 0;
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });


        return list.get(list.size()-1);
    }
    public void add_marker(String username,UserLocation location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        if (markerHashMap.get(username) != null) {

            markerHashMap.get(username).setPosition(position);
        }
        else{
            String url = "https://yt3.ggpht.com/-oiRlQjkYh08/AAAAAAAAAAI/AAAAAAAAAAA/PacDm-F0yPw/s900-c-k-no-rj-c0xffffff/photo.jpg";
            Marker marker= mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(username)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(url)))
            );
            markerHashMap.put(username,marker);
        }
    }

    private Bitmap getMarkerBitmapFromView(String url) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
//        markerImageView.setImageResource(resId);
        Picasso.with(MapsActivity.this).load(url).into(markerImageView);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


}
