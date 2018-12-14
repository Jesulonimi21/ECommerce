package com.example.jesulonimi.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jesulonimi.ecommerce.Model.Users;
import com.example.jesulonimi.ecommerce.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
private Button logInButton, joinNowButton;
    String ParentDbName="Users";
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logInButton=(Button) findViewById(R.id.main_login_btn);
        joinNowButton=(Button) findViewById(R.id.main_join_now_btn);
        Paper.init(this);
        loading=new ProgressDialog(this);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        String userPhoneKey=Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey=Paper.book().read(Prevalent.userPasswordKey);
        if(userPhoneKey!=""&&userPasswordKey!=""){
            if(!TextUtils.isEmpty(userPhoneKey)&&!TextUtils.isEmpty(userPasswordKey)){
                loading.setTitle("Already Logged in ");
                loading.setMessage("please wait while you are being given access");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                AllowAccess(userPhoneKey,userPasswordKey);
            }
        }
    }

    private void AllowAccess(final String number, final String password) {
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ParentDbName).child(number).exists()){
                    Users usersData=dataSnapshot.child(ParentDbName).child(number).getValue(Users.class);
                    if(usersData.getPhoneNumber().equals(number)&&usersData.getPassword().equals(password)){
                        StyleableToast.makeText(MainActivity.this,"Logged in successfully ...",R.style.resultOk).show();
                        loading.dismiss();
                        Intent i =new Intent(MainActivity.this,HomeActivity.class);
                        Prevalent.currentOnlineUser=usersData;
                        startActivity(i);
                    }else{
                        loading.dismiss();
                        StyleableToast.makeText(MainActivity.this,"Password or phone number is incorrect",R.style.resultNotOk).show();
                    }

                }else{
                    loading.dismiss();
                    StyleableToast.makeText(MainActivity.this,"Account with "+number +" does not exist",R.style.resultNotOk).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
