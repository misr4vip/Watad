package com.example.watad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class AdminUsersAdapter extends ArrayAdapter {
private static final  String TAG = "recyclerview";
private ArrayList<UserModel> mUsers ;
private Context mcontext;
public AdminUsersAdapter(Context context,int resource ,ArrayList<UserModel> users)
{
    super(context,resource,users);
    mcontext = context;
    mUsers = users;
}

    @Override public int getCount()
    {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.adminusersitem,parent,false);
        ImageView image ;
        TextView name;
        image = view.findViewById(R.id.ivAdminUser);
        name = view.findViewById(R.id.tvAdminUserName);
//        Task<Uri> ImagesRef = FirebaseStorage.getInstance().getReference().child(mProducts.get(position).getProductPic()).getDownloadUrl();
//        ImagesRef.addOnCompleteListener(task -> {
//            if(task.isSuccessful())
//            {
//                Log.e(TAG, "onBindViewHolder: uri " + task.getResult() );
//                Glide.with(image.getContext()).load(task.getResult()).into(image);
//            }
//        });

        name.setText(mUsers.get(position).getName());

        return view;
    }





}
