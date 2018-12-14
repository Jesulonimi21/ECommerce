package com.example.jesulonimi.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewActivity extends AppCompatActivity {
String CategoryName,description,price,pName,saveCurrentDate,saveCurrentTime;
ImageView InputProductImage;
Button AddNewProduct;
EditText InputProductName,InputProductPrice,InputProductDescription;
private static final int GALLERYPICK=1;
private Uri ImageUri;
private String productRandomName,downloadImageUrl;
private StorageReference productImageRef;
private DatabaseReference ProductRef;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new);

        CategoryName=getIntent().getExtras().get("category").toString();
            loading=new ProgressDialog(this);
        InputProductImage=(ImageView) findViewById(R.id.select_product_image);
        AddNewProduct=(Button)findViewById(R.id.add_new_product);
        InputProductName=(EditText) findViewById(R.id.product_name);
        InputProductDescription=(EditText) findViewById(R.id.product_description);
        InputProductPrice=(EditText) findViewById(R.id.product_price);
        productImageRef= FirebaseStorage.getInstance().getReference().child("product image");
        ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        AddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            validateProductData();
            }
        });

    }

    public void openGallery(){
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERYPICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERYPICK&&resultCode==RESULT_OK&&data!=null){
            ImageUri=data.getData();
          InputProductImage.setImageURI(ImageUri);


        }
    }

    public void validateProductData(){
        description=InputProductDescription.getText().toString();
        price=InputProductPrice.getText().toString();
        pName=InputProductName.getText().toString();

        if(ImageUri==null){
            StyleableToast.makeText(AdminAddNewActivity.this,"SELECTING A PRODUCT IMAGE IS MANDATORY",R.style.resultNotOk).show();
        }
        else if(TextUtils.isEmpty(description)){
            InputProductDescription.setError("this field is required");
        }     else if(TextUtils.isEmpty(price)){
            InputProductPrice.setError("this field is required");
        }     else if(TextUtils.isEmpty(pName)){
            InputProductName.setError("this field is required");
        }else{
            storeProductInformation();
        }
    }
    public void storeProductInformation(){

        loading.setTitle("Adding New Product");
        loading.setMessage("please wait while new products are being added");
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMMM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomName=saveCurrentDate+" "+saveCurrentTime;
        final StorageReference filePath=productImageRef.child(ImageUri.getLastPathSegment()+productRandomName);

        final UploadTask  uploadTask=filePath.putFile(ImageUri);
// ChHECK IF IMAGE UPLOAD FAILED
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.dismiss();
                String error=e.toString();
                StyleableToast.makeText(AdminAddNewActivity.this,"Error: "+error,R.style.resultNotOk).show();
            }
        })//CHECK  IF IMAGE WAS UPLOADED SUCCESSFULL AND SAVE IMAGE URL
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StyleableToast.makeText(AdminAddNewActivity.this,"upload image successfull",R.style.resultOk).show();
                //SAVE IMAGE URL
                Task<Uri> taskUrl=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    downloadImageUrl=filePath.getDownloadUrl().toString();
                    return  filePath.getDownloadUrl();
                    }
                }).//CHECK IF SAVING IMAGE URL TO STRING VARIABLE WAS SUCCESSFUL
                        addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl=task.getResult().toString();
                            StyleableToast.makeText(AdminAddNewActivity.this,"got uploaded image url",R.style.resultOk).show();
                            saveImageInfoToDatabase();
                        }
                    }
                });//END OF CHECKING GETTING IMAGE URL SUCCESS
            }
        });//END OF CHECKING IF IMAGE WAS UPLOADED SUCCESSFULLY


    }
    public void saveImageInfoToDatabase(){
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",productRandomName);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",CategoryName);
        productMap.put("price",price);
        productMap.put("pName",pName);
        ProductRef.child(productRandomName).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    loading.dismiss();
                    StyleableToast.makeText(AdminAddNewActivity.this,"everything added successfully",R.style.resultOk).show();
                }else{
                    loading.dismiss();
                    StyleableToast.makeText(AdminAddNewActivity.this,"Error: "+task.getException().toString(),R.style.resultNotOk).show();
                }
            }
        });


    }
}
