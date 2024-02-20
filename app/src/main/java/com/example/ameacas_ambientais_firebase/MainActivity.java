package com.example.ameacas_ambientais_firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String THREATS_KEY = "threats";
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference root     = database.getReference();
    DatabaseReference threats = root.child(THREATS_KEY);
    FirebaseListAdapter<Threat> listAdapter;
    ListView listThreat;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        listThreat = findViewById(R.id.listThreat);

        FirebaseListOptions<Threat> options = new FirebaseListOptions.Builder<Threat>()
                .setQuery(threats, Threat.class)
                .setLayout(R.layout.threat_list_item)
                .build();

        listAdapter = new FirebaseListAdapter<Threat>(options) {
            @Override
            protected void populateView(View v, Threat model, int position) {
                TextView txtThreatDescription = v.findViewById(R.id.txtThreatDescription);
                TextView txtThreatDate = v.findViewById(R.id.txtThreatDate);
                ImageView imageItem = v.findViewById(R.id.imageItem);
                txtThreatDescription.setText(model.getDescription());
                txtThreatDate.setText(model.getDate());
                if(model.getImage() != null){
                    byte imageData[] = Base64.decode(model.getImage(), Base64.DEFAULT);
                    Bitmap img = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    imageItem.setImageBitmap(img);
                    imageItem.setVisibility(View.VISIBLE);
                } else {
                    imageItem.setVisibility(View.INVISIBLE);
                }
            }
        };

        listThreat.setAdapter(listAdapter);
        listThreat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                DatabaseReference item = listAdapter.getRef(position);
                item.removeValue();
                return false;
            }
        });

        listThreat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                DatabaseReference item = listAdapter.getRef(position);
                changeToUpdate(item.getKey(), listAdapter.getItem(position));
            }
        });
        listAdapter.startListening();
    }

    public void changeToAdd(View v){
        Intent it = new Intent(getBaseContext(), AddThreat.class);
        startActivity(it);
    }

    public void changeToUpdate(String key, Threat t){
        Intent it = new Intent(getBaseContext(), EditThreat.class);
        it.putExtra("KEY", key);
        it.putExtra("THR", t);
        startActivity(it);
    }
}
