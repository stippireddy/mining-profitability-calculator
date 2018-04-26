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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KnowCoinActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_coin);

        Intent i = getIntent();
        HashMap<String, CurrencyData> hashMap = (HashMap<String, CurrencyData>) i.getSerializableExtra("map");

        List<String> coinNames = new ArrayList<>();
        coinNames.add("Select a coin you want to mine.");
        for (String s : hashMap.keySet()) {
            coinNames.add(hashMap.get(s).tag + "");
        }
        String[] hashRate = new String[]{
                "H/s", "KH/s", "MH/s", "GH/s", "TH/s", "PH/s"
        };

        Spinner coinSpinner = (Spinner) findViewById(R.id.coinSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, coinNames);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        coinSpinner.setAdapter(adapter);


        EditText et_HashRate = (EditText) findViewById(R.id.et_hashRate);
        EditText et_HardWareCost = (EditText) findViewById(R.id.et_hardWareCost);
        EditText et_Power = (EditText) findViewById(R.id.et_power);
        EditText et_PowerCost = (EditText) findViewById(R.id.et_powerRate);

        Button calculate = (Button) findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_HashRate.getText())) {
                    et_HashRate.setError("Hash rate is required!");
                } else if (TextUtils.isEmpty(et_HardWareCost.getText())) {
                    et_HardWareCost.setError("Hardware cost is required!");
                } else if (TextUtils.isEmpty(et_Power.getText())) {
                    et_Power.setError("Power is required!");
                } else if (TextUtils.isEmpty(et_PowerCost.getText())) {
                    et_PowerCost.setError("Power cost is required!");
                } else {
                    int selectedItemOfMySpinner = coinSpinner.getSelectedItemPosition();
                    String actualPositionOfMySpinner = (String) coinSpinner.getItemAtPosition(selectedItemOfMySpinner);
                    CurrencyData currencyData = hashMap.get(actualPositionOfMySpinner);
                    calculate(currencyData, Double.parseDouble(et_HashRate.getText().toString()), Double.parseDouble(et_HardWareCost.getText().toString()), Double.parseDouble(et_Power.getText().toString()), Double.parseDouble(et_PowerCost.getText().toString()));
                }
            }
        });
    }

    private void calculate(CurrencyData currencyData, double hashRate, double hardwareCost, double hardwarePowerInWatts, double powerCostInKWH) {
        double difficultyFactor = currencyData.getDifficulty();
        double hashRateStep = 1_000_000;
        double exchangeRate = currencyData.getExchangeRate();
        double blockReward = currencyData.getBlockReward();
        double calcHashRate = hashRate * hashRateStep;
        int numberOfDays = 1;
        double profitCoin = calcHashRate / difficultyFactor * blockReward * 3600 * 24;
        double profitUsd = profitCoin * exchangeRate;
        double powerCost = (hardwarePowerInWatts * 24 * numberOfDays * powerCostInKWH) / 1000;
        double profitPerDay = profitUsd - powerCost;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        if (profitPerDay < 0) {
            builder.setMessage("Oh oh! You will never break-even!");
        } else {
            builder.setMessage("You will break even in " + hardwareCost / profitPerDay + " days");
        }
        AlertDialog alert = builder.create();
        alert.show();
    }
}
