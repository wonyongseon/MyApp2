package com.example.a510.myapp2;
//위치정보 알아와서 지도에 표시하고,말로 알려줌
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    protected Button btMap,btLocation;
    protected LocationManager locationMan;
    protected MyLocationLx myLocationLx;
    protected Geocoder geocoder;
    protected TextToSpeech tts;//텍스트 말로

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            tts.setLanguage(Locale.KOREAN);//한국말 설정
            tts.setPitch(0.5f);
            tts.setSpeechRate(1.0f);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btMap = (Button) findViewById(R.id.btMap);
        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:36.321609,127.337957?z=20"));
                startActivity(intent);
            }
        });

        btLocation = (Button) findViewById(R.id.btLocation);
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double altitude = myLocationLx.altitude;
                double latitude = myLocationLx.latitude;
                double longitude = myLocationLx.longitude;

                List<Address> IsAddress;
                try {
                    IsAddress = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = IsAddress.get(0).getAddressLine(0);
                    String city = IsAddress.get(0).getLocality();
                    tts.speak(address,TextToSpeech.QUEUE_FLUSH,null,null);
                    Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();

                }catch (IOException e){
                    //에러처리부분
                }
            }
        });

        locationMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocationLx = new MyLocationLx();
        long minTime = 1000;
        float minDistance = 0;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, myLocationLx);
        geocoder = new Geocoder(this, Locale.KOREAN);
        tts = new TextToSpeech(this, this);
    }
}



















