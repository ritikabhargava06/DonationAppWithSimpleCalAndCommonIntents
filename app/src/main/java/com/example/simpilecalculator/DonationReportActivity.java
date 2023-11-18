package com.example.simpilecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class DonationReportActivity extends AppCompatActivity {

    TextView reportText;
    SwitchCompat backgroundSwitch;
    int background_colourId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            background_colourId = savedInstanceState.getInt("backgroundColor");
            View v = this.getWindow().getDecorView();
            v.setBackgroundColor(getResources().getColor(background_colourId, null));
        }
        setContentView(R.layout.activity_donation_report);
       // String msg =  getIntent().getStringExtra("donation_report");
        Donation d = (Donation) getIntent().getSerializableExtra("donationObject");
        String paymentMethod = d.getPayment_method() == 1 ? "Credit Card" : "PayPal";
        String msg = "Thanks for your donation number " + (((MyApp)getApplication()).allDonations.size())  +
                " The amount is "+ d.getAmount() + d.getDonation_currency() +
                " The used payment method is " + paymentMethod + " On " + d.donation_date ;

        reportText = findViewById(R.id.reporttext);
        reportText.setText(msg);

        backgroundSwitch = findViewById(R.id.dark_light_switch);
        backgroundSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = DonationReportActivity.this.getWindow().getDecorView();
              //  (SwitchCompat)view.isChecked
               if ( backgroundSwitch.isChecked()) {// background is gray
                   v.setBackgroundColor(getResources().getColor(R.color.darkgray, null));
                   background_colourId = R.color.darkgray;
               }else {
                   v.setBackgroundColor(getResources().getColor(R.color.white, null));
                   background_colourId = R.color.white;
               }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {// before the activity distroied
        super.onSaveInstanceState(outState);
        outState.putInt("backgroundColor", background_colourId);
    }
}