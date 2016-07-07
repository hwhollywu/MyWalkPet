package com.hwhollywu.mobileproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hwhollywu.mobileproject.PetFragment;
import com.hwhollywu.mobileproject.R;

/**
 * Created by hwhollywu on 7/7/16.
 */

public class FragmentPetStoreFood extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootview = inflater.inflate(R.layout.pet_store_food, null);
        ImageButton imFood1 = (ImageButton) rootview.findViewById(R.id.imFood1);
        ImageButton imFood2 = (ImageButton) rootview.findViewById(R.id.imFood2);
        ImageButton imFood3 = (ImageButton) rootview.findViewById(R.id.imFood3);
        ImageButton imFood4 = (ImageButton) rootview.findViewById(R.id.imFood4);
        ImageButton imFood5 = (ImageButton) rootview.findViewById(R.id.imFood5);
        ImageButton imFood6 = (ImageButton) rootview.findViewById(R.id.imFood6);

        imFood1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check coins, if enough, coins--, hunger++
                PetFragment.mPet.hunger += 10;
            }
        });
        imFood2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(10) ==true){
                    PetFragment.mPet.hunger += 20;
                    PetFragment.mPet.totalCoins -= 10;
                }
            }
        });
        imFood3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(20) ==true){
                    PetFragment.mPet.hunger += 30;
                    PetFragment.mPet.totalCoins -= 20;
                }
            }
        });
        imFood4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(30) ==true){
                    PetFragment.mPet.hunger += 50;
                    PetFragment.mPet.totalCoins -= 30;
                }
            }
        });
        imFood5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(50) ==true){
                    PetFragment.mPet.hunger += 70;
                    PetFragment.mPet.totalCoins -= 50;
                }
            }
        });
        imFood6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCoins(70) ==true){
                    PetFragment.mPet.hunger += 100;
                    PetFragment.mPet.totalCoins -= 70;
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle("Food Store")
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