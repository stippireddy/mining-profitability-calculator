
package stippireddy.ufl.edu.miningcalculator;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class KnowCoinActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_coin);

        Intent i = getIntent();
        HashMap<String, CurrencyData> hashMap = (HashMap<String, CurrencyData>) i.getSerializableExtra("map");

        Log.d("Test","" + hashMap.get("BTC").getExchangeRate());
        List<String> coinNames = new ArrayList<>();
        for(String s: hashMap.keySet()){
            coinNames.add(hashMap.get(s).tag);
        }
        String[] hashRate = new String[] {
                "H/s", "KH/s", "MH/s", "GH/s", "TH/s", "PH/s"
        };

        Spinner coinSpinner = (Spinner) findViewById(R.id.coinSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, coinNames);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        coinSpinner.setAdapter(adapter);


        Spinner hashRateSpinner = (Spinner) findViewById(R.id.hashspinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, hashRate);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        coinSpinner.setAdapter(adapter);


        final EditText et_HashRate = (EditText) findViewById(R.id.et_hashRate);
        EditText et_HardWareCost = (EditText) findViewById(R.id.et_hardWareCost);
        EditText et_Power = (EditText) findViewById(R.id.et_power);
        EditText et_PowerCost = (EditText) findViewById(R.id.et_powerRate);

        Button calculate = (Button) findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedHashRate = Integer.parseInt(et_HashRate.getText().toString());
            }
        });
    }
}
