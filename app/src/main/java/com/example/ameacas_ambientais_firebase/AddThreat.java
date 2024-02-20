package com.example.ameacas_ambientais_firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AddThreat extends AppCompatActivity {
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference root     = database.getReference();
    DatabaseReference threats = root.child(MainActivity.THREATS_KEY);
    EditText txtDescription, txtDate, txtAddress;
    public static final int CAMERA_CALL  = 1022;
    Bitmap bmp;
    ImageView image;
    Boolean hasImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_threat);

        txtDescription = findViewById(R.id.txtDescription);
        txtDate = findViewById(R.id.txtDate);
        txtAddress = findViewById(R.id.txtAddress);
        image = findViewById(R.id.image);
    }

    public void addThreat(View v){
        Threat t = new Threat();
        t.setDescription(txtDescription.getText().toString());
        t.setDate(txtDate.getText().toString());
        t.setAddress(txtAddress.getText().toString());

        if(hasImage){
            String bmpEncoded = loadImage();
            hasImage = false;
            t.setImage(bmpEncoded);
        }

        String key = threats.push().getKey();
        threats.child(key).setValue(t);
        finish();
    }

    public String loadImage(){
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
        return Base64.encodeToString(byteOut.toByteArray(), Base64.DEFAULT);
    }

    public void takePicture(View v){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_CALL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CALL && resultCode == RESULT_OK) {
            bmp = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bmp);
            hasImage = true;
        }
    }
}
