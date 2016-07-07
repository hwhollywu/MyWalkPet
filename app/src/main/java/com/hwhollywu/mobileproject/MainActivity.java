package com.hwhollywu.mobileproject;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hwhollywu.mobileproject.model.PetModel;
import com.hwhollywu.mobileproject.start.Personalize2Activity;
import com.hwhollywu.mobileproject.start.PersonalizeActivity;
import com.hwhollywu.mobileproject.start.StartActivity;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position ==0){
                    if(StartActivity.isPetInitiated ==true) {
                        PetFragment petFragment = (PetFragment) getSupportFragmentManager().findFragmentById(R.id.pet_fragment);
                        petFragment.setLevelAndCoins();
                        //petFragment.setAttributes();
                        petFragment.setPetImage();
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        PetFragment.mPet.save();
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, StartActivity.class);
        startActivity(intent);
    }
}
