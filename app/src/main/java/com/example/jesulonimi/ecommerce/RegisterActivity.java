package com.example.jesulonimi.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
private Button CreateAccounButton;
private EditText InputName,InputNumber,InputPassword;
ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccounButton=(Button) findViewById(R.id.register_btn);
        InputName=(EditText) findViewById(R.id.register_username_input);
        InputNumber=(EditText) findViewById(R.id.register_phone_number_input);
        InputPassword=(EditText) findViewById(R.id.register_password_input);
        loading=new ProgressDialog(this);
        CreateAccounButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String name=InputName.getText().toString();
        String password=InputPassword.getText().toString();
        String number=InputNumber.getText().toString();

        if(TextUtils.isEmpty(name)){
            InputName.setError("this field is required");
        }else if(TextUtils.isEmpty(password)){
            InputPassword.setError("this field  is required");
        }else if(TextUtils.isEmpty(number)){
            InputNumber.setError("this field is required");
        }else{
            loading.setTitle("Create Account");
            loading.setMessage("please wait while credentials are checked");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            validatePhone(name,password,number);

        }


    }

    private void validatePhone(final String name, final String password, final String number) {
        final DatabaseReference RootReference= FirebaseDatabase.getInstance().getReference();

        RootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(number)).exists()){

                    HashMap<String, Object> userMap=new HashMap<>();
                    userMap.put("PhoneNumber",number);
                    userMap.put("Name",name);
                    userMap.put("Password",password);

                    RootReference.child("Users").child(number).updateChildren(userMap).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    loading.dismiss();
                                    StyleableToast.makeText(RegisterActivity.this,"Congratulations Account created Successfully",R.style.resultOk).show();
                                    Intent i =new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(i);
                                }else{
                                    loading.dismiss();
                                    StyleableToast.makeText(RegisterActivity.this,"Check InternetConection and try again",R.style.resultNotOk).show();
                                }
                                }
                            });

                }else{
                    StyleableToast.makeText(RegisterActivity.this,"Phone Number Already Exists",R.style.resultNotOk).show();
                    loading.dismiss();
                    StyleableToast.makeText(RegisterActivity.this,"You can try again with another phone number",R.style.resultNotOk).show();
                    Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
