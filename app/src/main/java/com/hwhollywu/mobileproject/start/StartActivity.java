package com.hwhollywu.mobileproject.start;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hwhollywu.mobileproject.MainActivity;
import com.hwhollywu.mobileproject.PetFragment;
import com.hwhollywu.mobileproject.R;

public class StartActivity extends AppCompatActivity {

    private Button btnNew;
    private Button btnResume;
    private Button btnAbout;
    public static boolean isPetInitiated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnResume = (Button) findViewById(R.id.btnResume);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        final LinearLayout layoutRoot = (LinearLayout) findViewById(R.id.layoutStartRoot);

        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (isPetInitiated ==true) {
                    //show a reminder dialog
                    Snackbar.make(layoutRoot, R.string.snackbar_newpet, Snackbar.LENGTH_LONG).setAction("Yes",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(StartActivity.this, PersonalizeActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                } else{
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, PersonalizeActivity.class);
                    startActivity(intent);
                }

            }
        });


        btnResume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(isPetInitiated ==false){
                    Toast.makeText(StartActivity.this, R.string.toast_no_existing_pet, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(StartActivity.this,
                        R.string.about_info,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
