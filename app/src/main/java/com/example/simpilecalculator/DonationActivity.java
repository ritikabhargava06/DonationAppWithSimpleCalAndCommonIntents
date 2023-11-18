package com.example.simpilecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
;


public class DonationActivity extends AppCompatActivity
        implements View.OnClickListener{

    EditText amountText;
    RadioButton paypal_but;
    RadioButton credit_but;
    Button toListOfDonationsActivity_but;
    Button donation_but;
    int selectedPayment = 0;
    double amount;
    Spinner currencySpinner;
    ArrayList<Donation> listOfDonations ;
    int selectedCurrencyIndex = 0;
    ArrayList<String> currencyList;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        listOfDonations = ((MyApp)getApplication()).allDonations;
        int or = getResources().getConfiguration().orientation;
        if (or == Configuration.ORIENTATION_LANDSCAPE){
            Log.d("donation", "landscapge");
        }else {
            Log.d("donation", "portrait");
        }


        amountText = findViewById(R.id.donation_amount);
        paypal_but = findViewById(R.id.paypal);
        credit_but = findViewById(R.id.creditCard);
        donation_but = findViewById(R.id.donate_button);
        paypal_but.setOnClickListener(this);
        credit_but.setOnClickListener(this);
        donation_but.setOnClickListener(this);
        toListOfDonationsActivity_but = findViewById(R.id.toListOfDonation);
        toListOfDonationsActivity_but.setOnClickListener(this);

        currencySpinner = findViewById(R.id.currency_spinner);

        currencyList = new ArrayList<>(4);
        currencyList.add("CAD");
        currencyList.add("USD");
        currencyList.add("EUR");
        currencyList.add("AUD");

        ArrayAdapter spinnerAdapter =
                new ArrayAdapter(this,
                        R.layout.spinner_row,
                        R.id.currency_text,
                        currencyList);

        currencySpinner.setAdapter(spinnerAdapter);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedCurrencyIndex = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    boolean validate(){
        boolean flag = false;
        if (!amountText.getText().toString().isEmpty() && selectedPayment != 0){
            amount = Double.parseDouble(amountText.getText().toString());
            flag = true;
        }
        return flag;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.donate_button:

                if (validate()){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    Donation newDonation = new Donation(selectedPayment,
                            amount,
                            dtf.format(now),
                            currencyList.get(selectedCurrencyIndex));
                    listOfDonations.add(newDonation);
                    // toast to thank the user.
                   // Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
                    Intent toDonationReportIntent = new Intent(this, DonationReportActivity.class);
                    // Bundle

                    toDonationReportIntent.putExtra("donationObject",newDonation);
                    startActivity(toDonationReportIntent);


                    paypal_but.setChecked(false);
                    credit_but.setChecked(false);
                    amountText.setText("");
                }
                else {
                    // make toast.
                    Toast.makeText(this,R.string.error_msg,Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.creditCard:
                selectedPayment = 1;
                break;

            case R.id.paypal:
                selectedPayment = 2;
                break;

            case R.id.toListOfDonation:
                Intent tolistIntent = new Intent(this, DonationList.class);
                tolistIntent.putExtra("list",listOfDonations);
                startActivity(tolistIntent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.donation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.simpleCalculator:
               Intent intent = new Intent(this,MainActivity.class);
               startActivity(intent);
                return true;
            case R.id.commonIntent:
                Intent i = new Intent(this,commonIntents.class);
                startActivity(i);
                return true;
            case R.id.donationactivity:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks whether a keyboard is available
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Keyboard available", Toast.LENGTH_SHORT).show();
        } else if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO){
            Toast.makeText(this, "No keyboard", Toast.LENGTH_SHORT).show();
        }
    }
}