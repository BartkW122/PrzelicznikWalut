package com.example.przelicznikwalut;

import android.os.Bundle;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private EditText amountInput;
    private Button convertButton;

    private final String[] currencies = {"PLN", "EUR", "USD", "GBP"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        convertButton = findViewById(R.id.convertButton);

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

        String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrency = toCurrencySpinner.getSelectedItem().toString();

        double amountdbl = Double.parseDouble(amountStr);

        double result = 0.0;
    }
}