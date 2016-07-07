package com.hwhollywu.mobileproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hwhollywu.mobileproject.PetFragment;
import com.hwhollywu.mobileproject.R;

/**
 * Created by hwhollywu on 7/7/16.
 */

public class FragmentPetStoreToy extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootview = inflater.inflate(R.layout.pet_store_toy, null);
        ImageButton imToy1 = (ImageButton) rootview.findViewById(R.id.imToy1);
        ImageButton imToy2 = (ImageButton) rootview.findViewById(R.id.imToy2);
        ImageButton imToy3 = (ImageButton) rootview.findViewById(R.id.imToy3);
        ImageButton imToy4 = (ImageButton) rootview.findViewById(R.id.imToy4);
        ImageButton imToy5 = (ImageButton) rootview.findViewById(R.id.imToy5);
        ImageButton imToy6 = (ImageButton) rootview.findViewById(R.id.imToy6);

        imToy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetFragment.mPet.fun += 10;
            }
        });
        imToy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(10) ==true){
                    PetFragment.mPet.fun += 20;
                    PetFragment.mPet.totalCoins -= 10;
                }
            }
        });
        imToy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(20) ==true){
                    PetFragment.mPet.fun += 30;
                    PetFragment.mPet.totalCoins -= 20;
                }
            }
        });
        imToy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(30) ==true){
                    PetFragment.mPet.fun += 50;
                    PetFragment.mPet.totalCoins -= 30;
                }
            }
        });
        imToy5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(50) ==true){
                    PetFragment.mPet.fun += 70;
                    PetFragment.mPet.totalCoins -= 50;
                }
            }
        });
        imToy6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(70) ==true){
                    PetFragment.mPet.fun += 100;
                    PetFragment.mPet.totalCoins -= 70;
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle("Toy Store")
                .setView(rootview)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        return alertDialogBuilder.create();
    }

    private boolean checkCoins(int price){
        boolean isMoneyEnough =true;
        if (price> PetFragment.mPet.getTotalCoins()){
            isMoneyEnough = false;
            Toast.makeText(getActivity(), R.string.toaast_moneyEnough, Toast.LENGTH_SHORT).show();
        }
        return isMoneyEnough;
    }
}
