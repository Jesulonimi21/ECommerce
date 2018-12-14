package com.example.jesulonimi.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
private ImageView tShirts, sportsTshirt, femaleDresses,hats;
private ImageView glass, hatCaps,walletBagPurses,shoes;
private ImageView headPhonesHandFree,laptops,watches,mobilePhones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        tShirts=(ImageView) findViewById(R.id.t_shirts);
        sportsTshirt=(ImageView) findViewById(R.id.sport_t_shirts);
        femaleDresses=(ImageView) findViewById(R.id.female_dresses);
        hats=(ImageView) findViewById(R.id.hats_caps);
        glass=(ImageView) findViewById(R.id.glasses);
        hatCaps=(ImageView) findViewById(R.id.hats_caps);
        walletBagPurses=(ImageView) findViewById(R.id.purses_bags_wallet);
        headPhonesHandFree=(ImageView) findViewById(R.id.headphones_handfree);
        laptops=(ImageView) findViewById(R.id.laptops_pc);
        watches=(ImageView) findViewById(R.id.watches);
        mobilePhones=(ImageView) findViewById(R.id.mobilePhones);
        shoes=(ImageView) findViewById(R.id.shoes);




        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","tShirts");
                startActivity(i);
            }
        });

        sportsTshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","sportsTshirt");
                startActivity(i);
            }
        });

        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","femaleDresses");
                startActivity(i);
            }
        });


        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","hats");
                startActivity(i);
            }
        });



        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","glass");
                startActivity(i);
            }
        });

        hatCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","hatCaps");
                startActivity(i);
            }
        });

        walletBagPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","walletBagPurses");
                startActivity(i);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","shoes");
                startActivity(i);
            }
        });



        headPhonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","headPhonesHandFree");
                startActivity(i);
            }
        });



        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","watches");
                startActivity(i);
            }
        });



        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","laptops");
                startActivity(i);
            }
        });



        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoryActivity.this,AdminAddNewActivity.class);
                i.putExtra("category","mobilePhones");
                startActivity(i);
            }
        });
    }
}
