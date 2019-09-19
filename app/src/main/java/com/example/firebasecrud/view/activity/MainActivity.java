package com.example.firebasecrud.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasecrud.R;
import com.example.firebasecrud.databinding.ActivityMainBinding;
import com.example.firebasecrud.model.pojo.User;
import com.example.firebasecrud.view.adapter.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private List<User> userList;
    private UserAdapter adapter;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

    }


    private void init() {
        userList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("FirebaseCRUD");

        binding.rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(MainActivity.this, userList);

        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllUser();
    }

    private void getAllUser() {

        DatabaseReference getUserRef = databaseReference.child("userInfo");

        getUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //userList = new ArrayList<>();
                    userList.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        User user = data.getValue(User.class);
                        userList.add(user);
                    }
                    binding.rView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void addUser(View view) {
        startActivity(new Intent(this, InputActivity.class));

    }
}
