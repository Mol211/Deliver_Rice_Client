package com.mol21.cliente_deliveryrice.ui.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mol21.cliente_deliveryrice.R;

public class ForgetPassActivity extends AppCompatActivity {
    TextInputLayout tx;
    TextInputEditText et;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init() {
        tx = findViewById(R.id.txt);
        et = findViewById(R.id.et);
        txt = findViewById(R.id.textView4);
        txt.setOnClickListener(view -> {
            if(!et.getText().toString().isEmpty()){
                Toast.makeText(this, "El campo debe estar vacio", Toast.LENGTH_SHORT).show();
            } else{

            }
        });


    }

}