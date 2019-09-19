package com.example.firebasecrud.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasecrud.R;
import com.example.firebasecrud.databinding.ActivityInputBinding;
import com.example.firebasecrud.model.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputActivity extends AppCompatActivity {

    private ActivityInputBinding binding;
    private DatabaseReference databaseReference;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_input);

        init();
    }

    private void init() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("FirebaseCRUD");



        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });

    }

    private void saveUserInfo() {

        String name = binding.nameEt.getText().toString().trim();
        int age = Integer.parseInt(binding.ageET.getText().toString().trim());
        user = new User(name,age);

        DatabaseReference userRef = databaseReference.child("userInfo");
        String userId = userRef.push().getKey();
        user.setId(userId);

        if (userId != null){
            userRef.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(InputActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InputActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }



    }
}
