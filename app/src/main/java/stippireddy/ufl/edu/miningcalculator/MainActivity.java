package stippireddy.ufl.edu.miningcalculator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2;
    HashMap<String, CurrencyData> serverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.knowCoin);
        btn2 = (Button) findViewById(R.id.button2);

        // Make a network call here
        //
        loadWeatherData();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent knowCoin = new Intent(MainActivity.this, KnowCoinActivity.class);
                knowCoin.putExtra("map",serverData);
                startActivity(knowCoin);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent findCoin = new Intent(MainActivity.this, FindCoinActivity.class);
                findCoin.putExtra("map",serverData);
                startActivity(findCoin);
            }
        });

    }

    private void loadWeatherData() {
        try {
            serverData = new FetchWeatherTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, HashMap<String, CurrencyData>> {

        @Override
        protected HashMap<String, CurrencyData> doInBackground(String... params) {
            HashMap<String, CurrencyData> map = new HashMap<>();
            String[] inputs = new String[]{"162", "151", "178", "101","15", "147", "4", "1", "193"};
            for(String input: inputs){
                URL weatherRequestUrl = NetworkUtils.buildUrl(input);
                try {
                    String jsonWeatherResponse = NetworkUtils
                            .getResponseFromHttpUrl(weatherRequestUrl);

                    JSONObject forecastJson = new JSONObject(jsonWeatherResponse);
                    CurrencyData data = new CurrencyData();
                    data.setCurrencyID(input);
                    data.setCurrencyName((String)forecastJson.get("name"));
                    data.setDifficulty((double)forecastJson.get("difficulty"));
                    data.setExchangeRate((double) forecastJson.get("exchange_rate"));
                    data.setBlockReward(Double.valueOf(""+forecastJson.get("block_reward")));
                    data.setTag((String)forecastJson.get("tag"));
                    map.put(data.getTag(),data);
                    Log.d("In Fetch Weather Tsk", data.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return map;
        }

    }
}
