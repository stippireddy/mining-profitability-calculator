
package stippireddy.ufl.edu.miningcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

public class KnowCoinActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_coin);

        Intent i = getIntent();
        HashMap<String, CurrencyData> hashMap = (HashMap<String, CurrencyData>) i.getSerializableExtra("map");

        Log.d("Test",""+hashMap.get("BTC").getExchangeRate());


        String[] coins = new String[] {
                "BITCOIN", "2", "3", "4", "5"
        };

        String[] hashRate = new String[] {
                "1", "2", "3", "4", "5"
        };

        Spinner coinSpinner = (Spinner) findViewById(R.id.coinSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, coins);
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
