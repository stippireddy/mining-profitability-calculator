/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stippireddy.ufl.edu.miningcalculator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button b_Know_Coin, b_Suggest_Coin;
    HashMap<String, CurrencyData> serverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_Know_Coin = (Button) findViewById(R.id.knowCoin);
        b_Suggest_Coin = (Button) findViewById(R.id.button2);

        loadWeatherData();
        b_Know_Coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent knowCoin = new Intent(MainActivity.this, KnowCoinActivity.class);
                knowCoin.putExtra("map", serverData);
                startActivity(knowCoin);
            }
        });

        b_Suggest_Coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findCoin = new Intent(MainActivity.this, FindCoinActivity.class);
                findCoin.putExtra("map", serverData);
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
            String[] inputs = new String[]{"162", "151", "178", "101", "15", "147", "4", "1", "193"};
            for (String input : inputs) {
                URL weatherRequestUrl = NetworkUtils.buildUrl(input);
                try {
                    String jsonWeatherResponse = NetworkUtils
                            .getResponseFromHttpUrl(weatherRequestUrl);

                    JSONObject forecastJson = new JSONObject(jsonWeatherResponse);
                    CurrencyData data = new CurrencyData();
                    data.setCurrencyID(input);
                    data.setCurrencyName((String) forecastJson.get("name"));
                    data.setDifficulty((double) forecastJson.get("difficulty"));
                    data.setExchangeRate((double) forecastJson.get("exchange_rate"));
                    data.setBlockReward(Double.valueOf("" + forecastJson.get("block_reward")));
                    data.setTag((String) forecastJson.get("tag"));
                    map.put(data.getTag(), data);
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
