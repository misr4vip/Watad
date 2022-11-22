package com.example.watad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class BuyerBooksAdapter extends ArrayAdapter {
    private static final  String TAG = "recyclerview";
    private ArrayList<ProductModel> mProducts;
    private Context mcontext;

    public BuyerBooksAdapter(Context context,int resource , ArrayList<ProductModel> products )
    {
        super(context,resource,products);
        mcontext = context;
        mProducts = products;

    }

    @Override public int getCount()
    {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.adminbooksitem,parent,false);
        ImageView image ;
        TextView name;
        image = view.findViewById(R.id.ivAdminBook);
        name = view.findViewById(R.id.tvAdminBookName);
        Task<Uri> ImagesRef = FirebaseStorage.getInstance().getReference().child(mProducts.get(position).getProductPic()).getDownloadUrl();
        ImagesRef.addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Log.e(TAG, "onBindViewHolder: uri " + task.getResult() );
                Glide.with(image.getContext()).load(task.getResult()).into(image);
            }
        });

        name.setText(mProducts.get(position).getProductName());

        return view;
    }




}
