package com.example.przelicznikwalut;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private EditText amountInput;
    private Button convertButton;
    private TextView resultText;

    private final String[] currencies = {"PLN", "EUR", "USD", "GBP"};

    private  static  final  double EUR_TO_PLN = 4.30;
    private  static  final  double USD_TO_PLN = 4.00;
    private  static  final  double GBP_TO_PLN = 4.82;

    private  static  final  double PLN_TO_EUR = 0.24;
    private  static  final  double PLN_TO_USD = 0.27;
    private  static  final  double PLN_TO_GBP = 0.21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        SharedPreferences sharedPreferences = getSharedPreferences("WALUTY",MODE_PRIVATE);

        fromCurrencySpinner.setSelection(sharedPreferences.getInt("Z_WALUTA",0));
        toCurrencySpinner.setSelection(sharedPreferences.getInt("DO_WALUTA",0));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v->{
            convertCurrency();
        });
    }

    private void convertCurrency(){
        amountInput = findViewById(R.id.amountInput);

        String amountStr = amountInput.getText().toString();

        if(amountStr.isEmpty()){
            Toast.makeText(this,"Wprowadz kwotę!!",Toast.LENGTH_LONG).show();
            return;
        }

        String toCurrencyInfo = toCurrencySpinner.getSelectedItem().toString();
        char[] fromCurrency = fromCurrencySpinner.getSelectedItem().toString().toCharArray();
        char[] toCurrency = toCurrencySpinner.getSelectedItem().toString().toCharArray();

        double amountdbl = Double.parseDouble(amountStr);

        double result = 0.0;
        double przeliczenieNaPLN = 0.0;
        char wyborFrom = fromCurrency[0];

        switch (wyborFrom){
            case 'P':
                przeliczenieNaPLN = amountdbl;
                break;
            case 'E':
                przeliczenieNaPLN = amountdbl*EUR_TO_PLN;
                break;
            case 'U':
                przeliczenieNaPLN = amountdbl*USD_TO_PLN;
                break;
            case 'G':
                przeliczenieNaPLN = amountdbl*GBP_TO_PLN;
                break;
        }

        String wyborTo =  "P,"+toCurrency[0];
        Log.d("wybor",String.valueOf(przeliczenieNaPLN));

        switch (wyborTo) {
            case "P,P":
                result = przeliczenieNaPLN;
                break;
            case "P,E":
                result = przeliczenieNaPLN * PLN_TO_EUR;
                break;
            case "P,U":
                result = przeliczenieNaPLN * PLN_TO_USD;
                break;
            case "P,G":
                result = przeliczenieNaPLN * PLN_TO_GBP;
                break;
        }

        resultText.setText(String.format("%.2f", result) + " " + toCurrencyInfo);

        SharedPreferences.Editor editor = getSharedPreferences("WALUTY",MODE_PRIVATE).edit();

        editor.putInt("Z_WALUTA",fromCurrencySpinner.getSelectedItemPosition());
        editor.putInt("DO_WALUTA",toCurrencySpinner.getSelectedItemPosition());

        editor.apply();
    }
}