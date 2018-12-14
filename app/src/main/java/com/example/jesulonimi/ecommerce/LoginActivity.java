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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesulonimi.ecommerce.Model.Users;
import com.example.jesulonimi.ecommerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
private Button logInButton;
private EditText InputNumber, InputPassword;
ProgressDialog loading;
String ParentDbName;
private CheckBox chkBoxRememberMe;
private TextView AdminLink,NotAdminLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInButton=(Button) findViewById(R.id.login_btn);
        InputNumber=(EditText) findViewById(R.id.login_phone_number_input);
        InputPassword=(EditText) findViewById(R.id.login_password_input);
        loading=new ProgressDialog(this);
        AdminLink=(TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink=(TextView) findViewById(R.id.not_admin_panel_link);
        ParentDbName="Users";
        chkBoxRememberMe=(CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logInUser() ;
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInButton.setText("Admin Log in");
                NotAdminLink.setVisibility(View.VISIBLE);
                AdminLink.setVisibility(View.INVISIBLE);
                ParentDbName="Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInButton.setText("Log in");
                NotAdminLink.setVisibility(View.INVISIBLE);
                ParentDbName="Users";
                AdminLink.setVisibility(View.VISIBLE);
            }
        });
    }

    public void logInUser(){
        String password=InputPassword.getText().toString();
        String number=InputNumber.getText().toString();

     if(TextUtils.isEmpty(password)){
        InputPassword.setError("this field  is required");
    }else if(TextUtils.isEmpty(number)){
        InputNumber.setError("this field is required");
    }else{
        loading.setTitle("logging into Account");
        loading.setMessage("please wait while you are being logged in");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        validateAccount(password,number);

    }
    }

    public void validateAccount(final String password, final String number){

        if(chkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.userPasswordKey,password);
            Paper.book().write(Prevalent.userPhoneKey,number);
        }

        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ParentDbName).child(number).exists()){
                    Users usersData=dataSnapshot.child(ParentDbName).child(number).getValue(Users.class);
                    if(usersData.getPhoneNumber().equals(number)&&usersData.getPassword().equals(password)){
                    if(ParentDbName.equals("Admins")){
                        StyleableToast.makeText(LoginActivity.this,"Log in successfull ...",R.style.resultOk).show();
                        loading.dismiss();
                        Intent i =new Intent(LoginActivity.this,AdminCategoryActivity.class);
                        startActivity(i);
                    }else if(ParentDbName.equals("Users")){
                        StyleableToast.makeText(LoginActivity.this,"Log in successfull ...",R.style.resultOk).show();
                        loading.dismiss();
                        Intent i =new Intent(LoginActivity.this,HomeActivity.class);
                        Prevalent.currentOnlineUser=usersData;
                        startActivity(i);
                    }
                    }else{
                        loading.dismiss();
                        StyleableToast.makeText(LoginActivity.this,"Password or phone number is incorrect",R.style.resultNotOk).show();
                    }

                }else{
                    loading.dismiss();
                    StyleableToast.makeText(LoginActivity.this,"Account with "+number +" does not exist",R.style.resultNotOk).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
