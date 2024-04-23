package com.example.task7_1;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Assuming you have a method to fetch items
        List<Item> items = fetchItems();  // Implement this method based on your database structure

        for (Item item : items) {
            String locationString = item.getLocation(); // This gets the location as a string
            String[] latLong = locationString.split(", "); // Split the string on comma and space
            double latitude = Double.parseDouble(latLong[0]);
            double longitude = Double.parseDouble(latLong[1]);
            Log.d("Dmm", Double.toString(latitude));
            Log.d("Dmm", Double.toString(longitude));
            LatLng location = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(location).title(item.getName()));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
        }
    }

    private List<Item> fetchItems() {
        // Retrieve items from your database
        DatabaseHelper db = new DatabaseHelper(this);
        return db.getAllItems(); // You would need to ensure that getAllItems() is implemented in DatabaseHelper
    }
}
