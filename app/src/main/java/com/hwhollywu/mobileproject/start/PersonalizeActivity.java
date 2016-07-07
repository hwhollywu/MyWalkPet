package com.hwhollywu.mobileproject.start;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hwhollywu.mobileproject.R;
import com.hwhollywu.mobileproject.model.PetModel;

public class PersonalizeActivity extends AppCompatActivity {

    public static final String KEY_PET = "KEY_PET";
    private ImageButton imKindC1;
    private ImageButton imKindS1;
    private ImageButton imKindB1;
    private EditText etKind;
    private EditText etName;
    private TextView tvGender;
    private ImageButton imMale;
    private ImageButton imFemale;
    private Button btnNext;
    private short newGender;
    private String newName;
    private short newKind;
    public static PetModel newPetModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalize);

        imKindC1 = (ImageButton)findViewById(R.id.imKindC1);
        imKindS1 = (ImageButton)findViewById(R.id.imKindS1);
        imKindB1 = (ImageButton)findViewById(R.id.imKindB1);
        etKind = (EditText)findViewById(R.id.etKind);
        etName = (EditText)findViewById(R.id.etName);
        tvGender= (TextView) findViewById(R.id.etGender);
        imMale = (ImageButton)findViewById(R.id.imMale);
        imFemale = (ImageButton)findViewById(R.id.imFemale);
        btnNext = (Button)findViewById(R.id.btnNext);


        etName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER )) {
                    // Perform action on key press
                    Toast.makeText(PersonalizeActivity.this, "Hello, "+etName.getText(), Toast.LENGTH_SHORT).show();
                    newName = etName.getText().toString();
                    return true;
                }
                return false;
            }
        });

        resizePictures();
        resizeGenderPictures();

        imKindC1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                etKind.setText(R.string.choosekind_c1);
                newKind = PetModel.KIND_C1;
            }
        });
        imKindS1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                etKind.setText(R.string.choosekind_s1);
                newKind = PetModel.KIND_S1;
            }
        });
        imKindB1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                etKind.setText(R.string.choosekind_b1);
                newKind = PetModel.KIND_B1;
            }
        });

        imFemale.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                newGender = PetModel.GENDER_FEMALE;
                tvGender.setText("Gener: F");
            }
        });

        imMale.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                newGender = PetModel.GENDER_MALE;
                tvGender.setText("Gener: M");
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newName = etName.getText().toString();
                //String thiskind = etKind.getText().toString();
                //if ((thiskind != "Charmander") && (thiskind != "Squirtle") && (thiskind != "Bulbasaur")) {
                //    Toast.makeText(PersonalizeActivity.this, R.string.toast_kind, Toast.LENGTH_SHORT).show();
                //} else {
                    if (newName != null && newGender != 0 && newKind != 0) {
                        Intent intent = new Intent();
                        intent.setClass(PersonalizeActivity.this, Personalize2Activity.class);
                        newPetModel = new PetModel(newName, newGender, newKind);
                        Intent key_pet = intent.putExtra(KEY_PET, newPetModel);
                        startActivity(key_pet);
                    } else {
                        Toast.makeText(PersonalizeActivity.this, R.string.toast_personalize, Toast.LENGTH_SHORT).show();
                    }
                }
            //}
        });

    }

    private void resizePictures() {
        setResizedbmp(imKindC1, 150);
        setResizedbmp(imKindB1, 150);
        setResizedbmp(imKindS1, 150);
    }

    private void resizeGenderPictures(){
        setResizedbmp(imMale, 16);
        setResizedbmp(imFemale,16);
    }

    private void setResizedbmp(ImageView image, int size){
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        Bitmap resizedbmp = Bitmap.createScaledBitmap(bmp, size, size, false);
        image.setImageBitmap(resizedbmp);
    }


}
