
package stippireddy.ufl.edu.miningcalculator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                if(TextUtils.isEmpty(et_HashRate.getText())){
                    et_HashRate.setError( "Hash rate is required!" );
                }else if(TextUtils.isEmpty(et_HardWareCost.getText())){
                    et_HardWareCost.setError( "Hardware cost is required!" );
                }else if(TextUtils.isEmpty(et_Power.getText())){
                    et_Power.setError( "Power is required!" );
                }else if(TextUtils.isEmpty(et_PowerCost.getText())){
                    et_PowerCost.setError( "Power cost is required!" );
                }else{
                    calculate(hashMap, Double.parseDouble(et_HashRate.getText().toString()), Double.parseDouble(et_HardWareCost.getText().toString()),Double.parseDouble(et_Power.getText().toString()), Double.parseDouble(et_PowerCost.getText().toString())) ;
                }
            }
        });
    }

    private void calculate(HashMap<String, CurrencyData> hashMap, double hashRate, double hardwareCost, double hardwarePowerInWatts, double powerCostInKWH){
        double profitDays = Double.MAX_VALUE;
        String mostProfitableCurrency = "";
        for(CurrencyData currencyData : hashMap.values()){
            double currentCurrencyBreakEvenDays = calculate(currencyData, hashRate, hardwareCost, hardwarePowerInWatts, powerCostInKWH);
            if(profitDays>currentCurrencyBreakEvenDays){
                profitDays = currentCurrencyBreakEvenDays;
                mostProfitableCurrency = currencyData.getCurrencyName();
            }
        }
        Log.d("Inside FindActivity", mostProfitableCurrency);
        Toast.makeText(getApplicationContext(),mostProfitableCurrency + " is the most profitable currency to mine currently!", Toast.LENGTH_LONG);
    }
    private double calculate(CurrencyData currencyData, double hashRate, double hardwareCost, double hardwarePowerInWatts, double powerCostInKWH){
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