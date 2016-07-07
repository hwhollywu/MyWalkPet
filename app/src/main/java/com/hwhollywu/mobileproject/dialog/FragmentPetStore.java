package com.hwhollywu.mobileproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hwhollywu.mobileproject.PetFragment;
import com.hwhollywu.mobileproject.R;

public class FragmentPetStore extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootview = inflater.inflate(R.layout.pet_store, null);
        ImageButton imToyStore = (ImageButton) rootview.findViewById(R.id.imToyStore);
        ImageButton imFoodStore = (ImageButton) rootview.findViewById(R.id.imFoodStore);
        ImageButton imClean = (ImageButton) rootview.findViewById(R.id.imClean);
        imToyStore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //open toy store dialog
                FragmentPetStoreToy food_dialog = new FragmentPetStoreToy();
                food_dialog.setCancelable(false);
                food_dialog.show(getActivity().getSupportFragmentManager(), "food store");
            }
        });
        imFoodStore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //open food store dialog
                FragmentPetStoreFood toy_dialog = new FragmentPetStoreFood();
                toy_dialog.setCancelable(false);
                toy_dialog.show(getActivity().getSupportFragmentManager(), "toy store");
            }
        });
        imClean.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (checkCoins(50) ==true){
                    PetFragment.mPet.hygiene += 50;
                    PetFragment.mPet.totalCoins -= 20;
                }
            }
        });


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle("Pet Store")
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

    public boolean checkCoins(int price){
        boolean isMoneyEnough =true;
        if (price> PetFragment.mPet.getTotalCoins()){
            isMoneyEnough = false;
            Toast.makeText(getActivity(), R.string.toaast_moneyEnough, Toast.LENGTH_SHORT).show();
        }
        return isMoneyEnough;
    }

}