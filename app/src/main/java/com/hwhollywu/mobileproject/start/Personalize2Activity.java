package com.hwhollywu.mobileproject.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hwhollywu.mobileproject.MainActivity;
import com.hwhollywu.mobileproject.R;

public class Personalize2Activity extends AppCompatActivity {

    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalize2);

        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Personalize2Activity.this, MainActivity.class);
                Intent key_pet = intent.putExtra(PersonalizeActivity.KEY_PET, PersonalizeActivity.newPetModel);
                startActivity(key_pet);

            }
        });


    }
}
