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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class FindCoinActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_coin);

        Intent i = getIntent();
        HashMap<String, CurrencyData> hashMap = (HashMap<String, CurrencyData>) i.getSerializableExtra("map");

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
                    calculate(hashMap, Double.parseDouble(et_HashRate.getText().toString()), Double.parseDouble(et_HardWareCost.getText().toString()), Double.parseDouble(et_Power.getText().toString()), Double.parseDouble(et_PowerCost.getText().toString()));
                }
            }
        });
    }

    private void calculate(HashMap<String, CurrencyData> hashMap, double hashRate, double hardwareCost, double hardwarePowerInWatts, double powerCostInKWH) {
        double profitDays = Double.MAX_VALUE;
        String mostProfitableCurrency = "";
        for (CurrencyData currencyData : hashMap.values()) {
            double currentCurrencyBreakEvenDays = calculate(currencyData, hashRate, hardwareCost, hardwarePowerInWatts, powerCostInKWH);
            if (profitDays > currentCurrencyBreakEvenDays) {
                profitDays = currentCurrencyBreakEvenDays;
                mostProfitableCurrency = currencyData.getCurrencyName();
            }
        }
        Log.d("Inside FindActivity", mostProfitableCurrency);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mostProfitableCurrency + " is the most profitable currency to mine currently!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private double calculate(CurrencyData currencyData, double hashRate, double hardwareCost, double hardwarePowerInWatts, double powerCostInKWH) {
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
        if (profitPerDay < 0) {
            return -1;
        } else {
            return hardwareCost / profitPerDay;
        }
    }
}
