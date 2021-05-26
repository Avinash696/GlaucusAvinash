package com.example.glaucusavinash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.glaucusavinash.Models.Example;
import com.example.glaucusavinash.Models.Example2;
import com.example.glaucusavinash.Models.Main;
import com.example.glaucusavinash.Interface.ApiInterface;
import com.example.glaucusavinash.NewModel.Exampli;
import com.example.glaucusavinash.Singleton.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText etLocation;
    TextView tvTemp, tvFeellike, tvHumidity, tvDesc;
    ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherData(etLocation.getText().toString());
//                weatherData("Lucknow");
            }
        });

    }

    void init() {
        etLocation = findViewById(R.id.etLocation);
        tvTemp = findViewById(R.id.tvTemp);
        tvFeellike = findViewById(R.id.tvFeellike);
        tvDesc = findViewById(R.id.tvDesc);
        tvHumidity = findViewById(R.id.tvHumidity);
        ivSearch = findViewById(R.id.ivSearch);
    }


    private void weatherData(String name) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);

    Call<Exampli> call=apiInterface.getExampli(name);
    call.enqueue(new Callback<Exampli>() {
        @Override
        public void onResponse(Call<Exampli> call, Response<Exampli> response) {
            Exampli ex=response.body();
            Double temp = Double.parseDouble(String.valueOf(ex.getMain().getTemp())) - 273.0;
            tvTemp.setText("Temp=" + temp.toString());
            tvHumidity.setText("Humidity = "+ex.getMain().getHumidity());
            tvDesc.setText("Description ="+ex.getWeather().get(0).getDescription());
            tvFeellike.setText(ex.getSys().getCountry());
        }

        @Override
        public void onFailure(Call<Exampli> call, Throwable t) {

        }
    });
    }
}
