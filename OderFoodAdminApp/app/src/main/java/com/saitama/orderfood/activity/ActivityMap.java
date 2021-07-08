package com.saitama.orderfood.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saitama.orderfood.R;
import com.saitama.orderfood.service.LocationService;

public class ActivityMap extends AppCompatActivity {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    private GoogleMap mGoogleMap;
    private LocationService locationService;
    private MarkerOptions markerOptions;
    private Marker marker;

    private FloatingActionButton btnFloatLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initViews();

        btnFloatLocation = findViewById(R.id.btn_float_location);
        btnFloatLocation.setOnClickListener(btnLocationOnClick);
    }

    private void initViews() {
        locationService = new LocationService(this);
        Location gpsLocation = locationService.getLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
            supportMapFragment.getMapAsync(googleMap -> {
                mGoogleMap = googleMap;
                mGoogleMap.setOnMapLoadedCallback(() -> {
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                });
                LatLng lng = new LatLng(gpsLocation.getLatitude(), gpsLocation.getLongitude());
                marker = mGoogleMap.addMarker(new MarkerOptions().position(lng).title("Vị trí quán ăn"));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lng, 15));

                mGoogleMap.setOnMapClickListener(latLng -> {
                    if (marker != null && marker.isVisible())
                        marker.remove();
                    markerOptions = new MarkerOptions().position(latLng);
                    marker = mGoogleMap.addMarker(markerOptions);
                });
            });
        } else {
            showSettingsAlert();
        }
    }

    View.OnClickListener btnLocationOnClick = v -> {
        final Intent data = new Intent();
        data.putExtra(LATITUDE, String.valueOf(marker.getPosition().latitude));
        data.putExtra(LONGITUDE, String.valueOf(marker.getPosition().longitude));
        setResult(ActivityRegRestaurant.REQUEST_CODE_LOCATION, data);
        finish();
    };

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    /**
     * Yêu cầu quyền truy cập vị trí
     */
    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("CÀI ĐẶT");
        alertDialog.setMessage("Yêu cầu cấp quyển truy cập vị trí cho ứng dụng");
        alertDialog.setPositiveButton("Cấp quyền", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.startActivity(intent);
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }
}