package com.example.android.criminalintent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.android.criminalintent.controller.CrimeFragment;
import com.example.android.criminalintent.view.CrimePagerActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationistener;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationistener);

                Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                centerMapOnLocation(lastKnownLocation, "Your location");

            }
        }
    }

    public void centerMapOnLocation(Location location, String title) {

        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.clear();

        if (title != "Your location") {
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        mMap.setOnMapLongClickListener(this);

        Intent intent = getIntent();
        if (intent.getIntExtra("placeNumber", 0) == 0) {

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            mLocationistener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    centerMapOnLocation(location, "Your location");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (Build.VERSION.SDK_INT < 23) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationistener);
                }
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationistener);

                    Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    centerMapOnLocation(lastKnownLocation, "Your location");

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        } else {

            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
            placeLocation.setLatitude(CrimeFragment.locations.get(intent.getIntExtra("placeExtra", 0)).latitude);
            placeLocation.setLongitude(CrimeFragment.locations.get(intent.getIntExtra("placeExtra", 0)).longitude);

            centerMapOnLocation(placeLocation, CrimeFragment.places.get(intent.getIntExtra("placeNumber", 0)));
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());

        String address = "";

        try {
            List<Address> listAddresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (listAddresses != null && listAddresses.size() > 0) {

                if (listAddresses.get(0).getThoroughfare() != null) {

                    if (listAddresses.get(0).getSubThoroughfare() != null) {

                        address += listAddresses.get(0).getSubThoroughfare() + " ";
                    }

                    address += listAddresses.get(0).getThoroughfare();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (address == null || address.equalsIgnoreCase("")) {

//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
//            address = sdf.format(new Date());
            address = "lat: "+latLng.latitude+" lang: "+latLng.longitude;
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
//        getIntent().
        Intent intent = new Intent();
        intent.putExtra("LOCATION",address);
        intent.setData(Uri.EMPTY);
        setIntent(intent);
        setResult(RESULT_OK,intent);
        Toast.makeText(this, "Location saved. "+address, Toast.LENGTH_LONG).show();
        finish();
//        CrimeFragment.places.add(address);
//        CrimeFragment.locations.add(latLng);
//        CrimeFragment.adapter.notifyDataSetChanged();

    }
}
