package com.example.tugas2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner itemSpinner;
    private EditText quantityEditText;
    private CheckBox membershipCheckBox;
    private Button processButton;
    private TextView totalPriceTextView;

    private Map<String, Integer> itemPrices;
    private Map<String, Integer> adminFees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemSpinner = findViewById(R.id.itemSpinner);
        quantityEditText = findViewById(R.id.quantityEditText);
        membershipCheckBox = findViewById(R.id.membershipCheckBox);
        processButton = findViewById(R.id.processButton);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        // Initialize item prices and admin fees
        itemPrices = new HashMap<>();
        itemPrices.put("Pulsa", 10000);
        itemPrices.put("Token", 20000);
        itemPrices.put("Emoney", 30000);

        adminFees = new HashMap<>();
        adminFees.put("Pulsa", 2000);
        adminFees.put("Token", 2500);
        adminFees.put("Emoney", 3000);

        // Set up the item spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Pulsa", "Token", "Emoney"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);

        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTransaction();
            }
        });
    }

    private void processTransaction() {
        String selectedItem = itemSpinner.getSelectedItem().toString();
        String quantityText = quantityEditText.getText().toString();
        boolean isMember = membershipCheckBox.isChecked();

        if (quantityText.isEmpty()) {
            Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        int basePrice = itemPrices.get(selectedItem) * quantity;
        int adminFee = adminFees.get(selectedItem);
        double total = basePrice + adminFee;

        if (isMember) {
            total *= 0.95; // Apply 5% discount
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        totalPriceTextView.setText(currencyFormat.format(total));
    }
}
