package com.gtek.testtaskbbukva;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView appTitle;
    private EditText inputName, inputDescription, inputURL;
    private Button btnSave;
    private ImageView btnClose;
    private DatabaseReference mFirebaseDatabase;
    private String modelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_add);

        _init_ids();
        _init_actions();
        _init_firebase();
    }

    private void _init_ids()
    {
        appTitle = (TextView) findViewById(R.id.title);
        inputName = (EditText) findViewById(R.id.name);
        inputDescription = (EditText) findViewById(R.id.description);
        inputURL = (EditText) findViewById(R.id.url);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnClose = (ImageView) findViewById(R.id.btnClose);
    }

    private void _init_firebase()
    {
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("bbukva");
        mFirebaseInstance.getReference("app_title").setValue("Realtime Test Task Database");
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title = dataSnapshot.getValue(String.class);
                appTitle.setText(title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "error.", error.toException());
            }
        });
    }

    private void _init_actions()
    {
        btnSave.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            String description = inputDescription.getText().toString();
            String url = inputURL.getText().toString();

            createItem(name, description, url);
        });
        btnClose.setOnClickListener(v -> {
            finish();
        });
    }

    private void createItem(String name, String description, String url)
    {
        if (TextUtils.isEmpty(modelId)) {
            modelId = mFirebaseDatabase.push().getKey();
        }
        Model model = new Model(name, description, url);
        mFirebaseDatabase.child(modelId).setValue(model);
        addModelChangeListener();
    }

    private void addModelChangeListener() {
        mFirebaseDatabase.child(modelId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Toast.makeText(AddActivity.this,
                        "Newly data added. Check it in Main page!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "error", error.toException());
            }
        });
    }
}