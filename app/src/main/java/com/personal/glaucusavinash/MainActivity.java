package com.personal.glaucusavinash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.personal.glaucusavinash.Interface.ApiInterface;
import com.personal.glaucusavinash.NewModel.Exampli;
import com.personal.glaucusavinash.Singleton.APIClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText etLocation;
    TextView tvTemp, tvFeellike, tvHumidity, tvDesc;
    ImageView ivSearch;
    //    AppLocationService appLocationService;
    double lat;
    double lon;
//    Location gpsLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationWala();

//        AutoLocation();
//        locationChanged(lat,lon);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherData(etLocation.getText().toString());
//                weatherData("Lucknow");
            }
        });

    }

    void locationWala() {
        //permission check
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //since granted
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //initiate location
                    Location location = task.getResult();
                    if (location != null) {
                        //initi geocoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //address list
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                    location.getLongitude(), 1);
                            //here u have lat long now
                            lat=addresses.get(0).getLatitude();
                            lon=addresses.get(0).getLongitude();
                            locationChanged(lat,lon);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            //when denied
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


//   public void AutoLocation() {
//         gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
//        if (gpsLocation != null) {
//             lat = String.valueOf(gpsLocation.getLatitude());
//            lon= String.valueOf(gpsLocation.getLongitude());
//            Toast.makeText(
//                    getApplicationContext(),
//                    "Mobile Location (GPS): \nLatitude: " + lat
//                            + "\nLongitude: " + lon,
//                    Toast.LENGTH_LONG).show();
//        } else {
//            showSettingsAlert("GPS");
//        }
//    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainActivity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    void init() {
        etLocation = findViewById(R.id.etLocation);
        tvTemp = findViewById(R.id.tvTemp);
        tvFeellike = findViewById(R.id.tvFeellike);
        tvDesc = findViewById(R.id.tvDesc);
        tvHumidity = findViewById(R.id.tvHumidity);
        ivSearch = findViewById(R.id.ivSearch);
    }

    private void locationChanged(Double lat,Double lon){
    ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
    Call<Exampli> ccall=apiInterface.getLatitude(lat,lon);
    ccall.enqueue(new Callback<Exampli>() {
        @Override
        public void onResponse(Call<Exampli> call, Response<Exampli> response) {
            Exampli ex = response.body();
            Double temp = ex.getMain().getTemp() - 273.0;
            tvTemp.setText("Temp=" + temp.toString());
            tvFeellike.setText(ex.getSys().getCountry());
            tvDesc.setText("Working auto location");
        }

        @Override
        public void onFailure(Call<Exampli> call, Throwable t) {

        }
    });
}
    private void weatherData(String name) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);

        Call<Exampli> call = apiInterface.getExampli(name);
        call.enqueue(new Callback<Exampli>() {
            @Override
            public void onResponse(Call<Exampli> call, Response<Exampli> response) {
                Exampli ex = response.body();
                Double temp = Double.parseDouble(String.valueOf(ex.getMain().getTemp())) - 273.0;
                tvTemp.setText("Temp=" + temp.toString());
                tvHumidity.setText("Humidity = " + ex.getMain().getHumidity());
                tvDesc.setText("Description =" + ex.getWeather().get(0).getDescription());
                tvFeellike.setText(ex.getSys().getCountry());
            }

            @Override
            public void onFailure(Call<Exampli> call, Throwable t) {

            }
        });
    }
}
