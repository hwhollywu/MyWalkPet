package com.hwhollywu.mobileproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hwhollywu.mobileproject.dialog.FragmentWalkSummary;
import com.hwhollywu.mobileproject.model.WalkModel;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

public class MapsFragment extends Fragment implements LocationListener, SensorEventListener {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Context context;
    private Button btnStart;
    private Button btnEnd;
    private TextView tvStepCounter;
    private TextView tvMileCounter;
    private TextView tvCoinCounter;
    private TextView tvPercentCounter;
    private LatLng currentLatLng;
    private Location currentLocation;
    private LocationManager locationManager;
    private Float distanceInMeters;
    private PolylineOptions polylineOptions;
    private boolean isStart;
    public static long timeWalkStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.maps_main, null);
        context = getActivity();
        btnStart = (Button) rootView.findViewById(R.id.btnStart);
        btnEnd = (Button) rootView.findViewById(R.id.btnEnd);
        tvStepCounter = (TextView) rootView.findViewById(R.id.tvStepCounter);
        tvMileCounter = (TextView) rootView.findViewById(R.id.tvMileCounter);
        tvCoinCounter = (TextView) rootView.findViewById(R.id.tvCoinCounter);
        tvPercentCounter = (TextView) rootView.findViewById(R.id.tvPercentCounter);
        polylineOptions = new PolylineOptions();
        distanceInMeters = 0.0f;
        isStart = false;


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWalk();
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endWalk();
            }
        });


        return rootView;
    }

    private void startWalk() {
        if (!isStart) {
            isStart = true;
            setStepSensor();
            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            requestNeededPermission();
            initMap();
            timeWalkStart = System.currentTimeMillis();
        }
    }

    private void endWalk() {
        if (isStart) {
            isStart = false;
            stopGPS();
            distanceInMeters = 0.0f;
            currentLatLng = null;
            currentLocation = null;
            mMap.clear();
            tvStepCounter.setText("0");
            tvMileCounter.setText("0");
            tvCoinCounter.setText("0");
            tvPercentCounter.setText("0");
            showWalkSummary();
            PetFragment.mPet.addToTotalCoins(WalkModel.getInstance().getTodayCoins());
            PetFragment.setLevelAndCoins();
        }
    }


    private void showWalkSummary() {
        FragmentWalkSummary dialog = new FragmentWalkSummary();
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    private void setStepSensor() {
        final SensorManager sensorManager = (SensorManager) this.getActivity().getSystemService(SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        if (!sensorList.contains(Sensor.TYPE_STEP_COUNTER)) {
            tvStepCounter.setText(R.string.no_step_sensor);
            WalkModel.getInstance().setTodaySteps(-1);
        }

        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this,
                stepSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (googleMap == null) {
                    Toast.makeText(context, R.string.sorry_toast, Toast.LENGTH_SHORT).show();
                } else {
                    mMap = googleMap;

                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.getUiSettings().setAllGesturesEnabled(true);
                    try {
                        mMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        Toast.makeText(context, R.string.location_fail, Toast.LENGTH_SHORT)
                                .show();
                        ;
                    }

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        ((SensorManager) this.getActivity().getSystemService(SENSOR_SERVICE)).unregisterListener(this);
        super.onDestroy();
        endWalk();
    }

    public void stopGPS() {
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        locationManager = null;
    }


    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(context,
                        R.string.permission_request, Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        } else {
            // TODO - location
            // enable both network and gps, so in wi-fi situation faster, without wi-fi won't work
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (isStart) {

            if (currentLocation == null) {
                currentLocation = location;
            } else {
                setMiles(location);

                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));

                //draw path
                polylineOptions.add(
                        new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                        .width(5).color(Color.YELLOW);
                mMap.addPolyline(polylineOptions);
            }
        }
    }

    private void setMiles(Location location) {
        Location oldLocation = currentLocation;
        currentLocation = location;
        distanceInMeters += oldLocation.distanceTo(currentLocation);
        //transfer number to miles;
        WalkModel.getInstance().setTodayMiles((39.370078 * distanceInMeters) / 63360);
        DecimalFormat numberFormat = new DecimalFormat("#0.000");
        tvMileCounter.setText(numberFormat.format(WalkModel.getInstance().getTodayMiles()));

        //set Percentage and coins
        DecimalFormat df = new DecimalFormat("#0.00");
        WalkModel.getInstance().calculatePercentGoal();
        tvPercentCounter.setText(df.format(WalkModel.getInstance().getTodaypercentGoal()));
        WalkModel.getInstance().calculateCoinsEarned();
        tvCoinCounter.setText(String.valueOf(WalkModel.getInstance().getTodayCoins()));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float stepInFloat = sensorEvent.values[0];
        WalkModel.getInstance().setTodaySteps((int) stepInFloat);
        tvStepCounter.setText(WalkModel.getInstance().getTodaySteps());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
