package com.example.firebasecrud.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasecrud.view.activity.EditActivity;
import com.example.firebasecrud.R;
import com.example.firebasecrud.databinding.ItemUserBinding;
import com.example.firebasecrud.model.pojo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ItemUserBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_user, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final User user = userList.get(position);
        holder.binding.nameTV.setText(user.getName());
        holder.binding.ageTv.setText(String.valueOf(user.getAge()));

        // popupMenu, edit & delete
        holder.binding.moreVertIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showPopUpMenu();
                PopupMenu popupMenu = new PopupMenu(context, holder.binding.moreVertIV);
                popupMenu.inflate(R.menu.menu_item);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {

                            case R.id.editId:
                                Bundle bundle = new Bundle();
                                bundle.putString("name",user.getName());
                                bundle.putString("age",String.valueOf(user.getAge()));
                                bundle.putString("userId",user.getId());
                                Intent intent = new Intent(context, EditActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                return true;

                            case R.id.deleteId:
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FirebaseCRUD");
                                databaseReference.child("userInfo").child(user.getId())
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                userList.remove(position);
                                notifyDataSetChanged();

                                return true;

                            default:
                                return false;

                        }
                    }
                });
                //displaying the popup
                popupMenu.show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemUserBinding binding;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
