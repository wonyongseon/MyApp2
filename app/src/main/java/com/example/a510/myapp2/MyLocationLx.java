package com.example.a510.myapp2;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by 510 on 2017-03-15.
 */

public class MyLocationLx implements LocationListener {
    public double altitude,latitude,longitude;
    @Override
    public void onLocationChanged(Location location) {//여기가 중요 여기서 위치정보 잡아옴
        altitude = location.getAltitude();//고도
        latitude = location.getLatitude();//위도
        longitude = location.getLongitude();//경도
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
}
