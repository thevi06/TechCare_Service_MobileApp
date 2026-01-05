package com.slt.devops.techcareservice;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SupportActivity extends AppCompatActivity {

    TextView txtFaq, txtContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        txtFaq = findViewById(R.id.txtFaq);
        txtContact = findViewById(R.id.txtContact);

        txtFaq.setText(
                "FAQs:\n\n" +
                        "Q: How long does a repair take?\n" +
                        "A: 1–3 working days\n\n" +
                        "Q: Home pickup available?\n" +
                        "A: Yes"
        );

        txtContact.setText(
                "Contact Us:\n\n" +
                        "Phone: 011-2345678\n" +
                        "Email: support@techcare.com"
        );
    }
}
