package com.example.watad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class SellerAddProductActivity extends AppCompatActivity {

    EditText etProduct , etDisc;
    Spinner spPrice;
    Button btnNew , btnUsed ,btnSave;
    CheckBox ckIssued;
    ImageView ivPic;
    FirebaseDatabase database;
    FirebaseAuth auth ;
    DatabaseReference myRef;
    Uri contentUri;
    String imageFileName,price,condition;
    FirebaseStorage storage;
    StorageReference storageRef ;
    String type ="";
    public static final int Galary_perm_Code = 101;
    public static final int Galary_Request_Code = 102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_product);
        Intent intent = getIntent();
         type = intent.getStringExtra("type");
        etProduct = findViewById(R.id.et_nameOfProduct);
        etDisc = findViewById(R.id.et_disc);
        spPrice = findViewById(R.id.sp_price);
        btnNew = findViewById(R.id.btnNew);
        btnUsed = findViewById(R.id.btnOld);
        btnSave = findViewById(R.id.btnAddProductSave);
        ckIssued = findViewById(R.id.rbIssued);
        ivPic = findViewById(R.id.ivPic);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myRef = database.getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        btnNew.setOnClickListener(View->{ condition = "New";});
        btnUsed.setOnClickListener(View->{ condition = "Used";});
        ivPic.setOnClickListener(view -> askGalaryPermission());
        String[] prices = {"5 R.S","10 R.S","15 R.S","20 R.S","25 R.S","30 R.S","35 R.S","40 R.S","45 R.S","50 R.S","55 R.S","60 R.S","65 R.S","70 R.S","75 R.S"};
        price = prices[0];
        ArrayAdapter ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,prices);
        spPrice.setAdapter(ad);
        spPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                price = prices[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSave.setOnClickListener(View ->{

            this.SaveProduct();
        });



    }

    public void SaveProduct()
    {
        ProductModel product = new ProductModel();
        product.setSellerId(auth.getCurrentUser().getUid());
        product.setProductId(UUID.randomUUID().toString());
        product.setProductDisc(etDisc.getText().toString());
        product.setProductName(etProduct.getText().toString());
        product.setProductPrice(price);
        product.setProductPic(imageFileName);
        product.setCondition(condition);
        product.setConfirmed(true);
        product.setHaveAnIssues(ckIssued.isChecked());
        product.setProductType(type);
        storageRef.child(imageFileName).putFile(contentUri).addOnSuccessListener(taskSnapshot -> {
            myRef.child("products").child(product.getProductId()).setValue(product).addOnSuccessListener(unused -> {

                Toast.makeText(getApplicationContext(),"Product added Successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext() , DoneActivity.class);
                intent.putExtra("userType","seller");
                startActivity(intent);

            }).addOnFailureListener(ex->{
                Toast.makeText(getApplicationContext(),ex.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            });

        }).addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        });

    }

    private void askGalaryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, Galary_perm_Code);
        }else
        {
            Intent galary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galary, Galary_Request_Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Galary_perm_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galary, Galary_Request_Code);
            } else {
                Toast.makeText(getApplicationContext(), "Media Permission Required ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Galary_Request_Code) {
            if (resultCode == this.RESULT_OK) {
                contentUri = data.getData();
                Log.d("TAG", "onActivityResult: Content Uri " + contentUri.toString());
                ivPic.setImageURI(contentUri);
                // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = getFileName(contentUri);

            }


        }
    }
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        Log.d("TAG", "getFileName: " + result);
        return result;
    }
    private String getFileExt(Uri content)
    {
        ContentResolver c = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(content));
    }
}