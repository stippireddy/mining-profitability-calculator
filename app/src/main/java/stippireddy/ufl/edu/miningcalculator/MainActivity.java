package stippireddy.ufl.edu.miningcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        final String name = "Saugat";

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
}
