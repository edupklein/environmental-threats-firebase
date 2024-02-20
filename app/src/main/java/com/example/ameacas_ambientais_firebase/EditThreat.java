package com.example.ameacas_ambientais_firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditThreat extends AppCompatActivity {
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference root     = database.getReference();
    DatabaseReference threats = root.child(MainActivity.THREATS_KEY);

    EditText txtDescription, txtDate, txtAddress;
    Threat current;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_threat);

        txtDescription = findViewById(R.id.txtDescription);
        txtDate = findViewById(R.id.txtDate);
        txtAddress = findViewById(R.id.txtAddress);

        key = getIntent().getStringExtra("KEY");
        current = (Threat) getIntent().getSerializableExtra("THR");
        txtDescription.setText(current.getDescription());
        txtDate.setText(current.getDate());
        txtAddress.setText(current.getAddress());
    }

    public void updateThreat(View v){
        current.setDescription(txtDescription.getText().toString());
        current.setDate(txtDate.getText().toString());
        current.setAddress(txtAddress.getText().toString());
        threats.child(key).setValue(current);

        finish();
    }

}
