package stippireddy.ufl.edu.miningcalculator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2;

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
                knowCoin.putExtra("data",name);
                startActivity(knowCoin);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void loadWeatherData() {
        new FetchWeatherTask().execute();
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, HashMap<String, CurrencyData>> {

        @Override
        protected HashMap<String, CurrencyData> doInBackground(String... params) {
            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }
            String location = "101";
            HashMap<String, CurrencyData> map = new HashMap<>();
            String[] inputs = new String[]{"162", "151", "178", "101","15", "147", "4", "1", "193"};
            for(String input: inputs){
                URL weatherRequestUrl = NetworkUtils.buildUrl(location);
                try {
                    String jsonWeatherResponse = NetworkUtils
                            .getResponseFromHttpUrl(weatherRequestUrl);

                    JSONObject forecastJson = new JSONObject(jsonWeatherResponse);
                    CurrencyData data = new CurrencyData();
                    data.setCurrencyID(input);
                    data.setCurrencyName((String)forecastJson.get("name"));
                    data.setDifficulty((double)forecastJson.get("difficulty"));
                    data.setExchangeRate((double) forecastJson.get("exchange_rate"));
                    data.setBlockReward((double) forecastJson.get("block_reward"));
                    data.setTag((String)forecastJson.get("tag"));
                    map.put(data.getTag(),data);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return map;
        }

        private void calculate(double[] userInput, double[] serviceInput){
            long[] HashRateSteps =
                    new long[] {1, 1000, 1_000_000, 1_000_000_000, 1_000_000_000_000l, 1_000_000_000_000_000l};
            parsedData[0] = Double.valueOf((String)forecastJson.get("block_time"));
            parsedData[1] = (double) forecastJson.get("block_reward");
            parsedData[2] = (double) forecastJson.get("difficulty");
            parsedData[3] = (double) forecastJson.get("exchange_rate");
            double difficultyFactor = serviceInput[2];
            double hashRate = 111d;
            double hashRateStep = 1_000_000;
            double exchangeRate = serviceInput[3];
            double blockReward = serviceInput[1];
            double poolFee = 0;
            double hardwareCost = 1000;
            double hardwarePowerInWatts = 200;
            double powerCostInKWH = 1;
            double calcHashRate = hashRate * hashRateStep;
            int numberOfDays = 1;
            double coin = calcHashRate / difficultyFactor * blockReward * 3600 * 24;
            double fee = coin / 100 * poolFee;
            double profitCoin = coin - fee;
            double profitUsd = profitCoin * exchangeRate;
            double powerCost = (hardwarePowerInWatts * 24 * numberOfDays * powerCostInKWH) / 1000;
            double profitPerDay = profitUsd - powerCost;
            if (profitPerDay < 0) {
                System.out.println("You will never break-even");
            } else {
                System.out.println("You will break even in " + hardwareCost / profitPerDay + " days");
            }
            System.out.println(profitUsd - powerCost);
        }
    }
}
