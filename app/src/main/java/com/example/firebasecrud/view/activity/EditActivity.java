package com.example.firebasecrud.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasecrud.R;
import com.example.firebasecrud.databinding.ActivityEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    private String userId;
    private Bundle bundle;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit);


        databaseReference = FirebaseDatabase.getInstance().getReference("FirebaseCRUD");



        //Toast.makeText(this, "name :"+ name +"\n age: "+age, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {

        bundle = getIntent().getExtras();

        if (bundle != null) {
            String name = bundle.getString("name");

            userId = bundle.getString("userId");

            binding.editNameET.setText(name);

        }

    }

    public void updateUser(View view) {

        String name = binding.editNameET.getText().toString().trim();
//        int age = Integer.parseInt(binding.editAgeET.getText().toString().trim());

        DatabaseReference editRef = databaseReference.child("userInfo");
        editRef.child(userId).child("name").setValue(name)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });


    }
}
