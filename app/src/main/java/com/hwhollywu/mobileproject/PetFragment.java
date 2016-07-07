package com.hwhollywu.mobileproject;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hwhollywu.mobileproject.dialog.FragmentPetStore;
import com.hwhollywu.mobileproject.dialog.FragmentPetStoreFood;
import com.hwhollywu.mobileproject.model.PetModel;
import com.hwhollywu.mobileproject.start.PersonalizeActivity;
import com.hwhollywu.mobileproject.start.StartActivity;


/**
 * Created by hwhollywu on 7/3/16.
 */

public class PetFragment extends Fragment {


    public static FloatingActionButton addButton;
    public static ImageView ivPet;
    public static PetModel mPet;
    public static TextView tvTotalCoins;
    public static TextView tvLevel;
    public static TextView tvExp;
    public static TextView tvHunger;
    public static TextView tvHygiene;
    public static TextView tvFun;
    public static ProgressBar barExp;
    public static ProgressBar barHunger;
    public static ProgressBar barHygiene;
    public static ProgressBar barFun;
    private Handler mHandler = new Handler();
    public static long timePetStart;
    private Context context;
    private boolean threadEnabled = false;

    public class barHungerThread extends Thread {
        public void run() {
            while (threadEnabled) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPet.updateHunger();
                        tvHunger.setText("Hunger: " + String.valueOf(mPet.getHunger()));
                            barHunger.setProgress(mPet.getHunger());
                    }
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class barHygieneThread extends Thread {
        public void run() {
            while (threadEnabled) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mPet.getHygiene() > 0) {
                            mPet.updateHygiene();
                            tvHygiene.setText("Hygiene: " + String.valueOf(mPet.getHygiene()));
                            barHygiene.setProgress(mPet.getHygiene());
                        }
                    }
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class barFunThread extends Thread {
        public void run() {
            while (threadEnabled) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mPet.getFun() > 0) {
                            mPet.updateFun();
                            tvFun.setText("Fun: " + String.valueOf(mPet.getFun()));
                            barFun.setProgress(mPet.getFun());
                        }
                    }
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class barExpThread extends Thread {
        public void run() {
            while (threadEnabled) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int expForNextLevelInInt = (int) mPet.getExpForNextLevel();
                        barExp.setMax(expForNextLevelInInt);
                        if (mPet.getExp() < mPet.getExpForNextLevel()) {
                            mPet.updateExp();
                            int expInInt = (int) mPet.getExp();
                            tvExp.setText("Exp: \n" + expInInt + " / " + expForNextLevelInInt);
                            barExp.setProgress(expInInt);
                        }
                        if (mPet.getExp() >= mPet.getExpForNextLevel()) {
                            mPet.levelUp();
                        }
                    }
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pet_main, null);

        addButton = (FloatingActionButton) rootView.findViewById(R.id.btnAdd);
        ivPet = (ImageView) rootView.findViewById(R.id.ivPet);
        tvTotalCoins = (TextView) rootView.findViewById(R.id.tvTotalCoins);
        tvLevel = (TextView) rootView.findViewById(R.id.tvLevel);
        tvExp = (TextView) rootView.findViewById(R.id.tvExp);
        tvHunger = (TextView) rootView.findViewById(R.id.tvHunger);
        tvHygiene = (TextView) rootView.findViewById(R.id.tvHygiene);
        tvFun = (TextView) rootView.findViewById(R.id.tvFun);
        barHunger = (ProgressBar) rootView.findViewById(R.id.barHunger);
        barHygiene = (ProgressBar) rootView.findViewById(R.id.barHygiene);
        barFun = (ProgressBar) rootView.findViewById(R.id.barFun);
        barExp = (ProgressBar) rootView.findViewById(R.id.barExp);

        context = getActivity();

        timePetStart = System.currentTimeMillis();

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentPetStore store_dialog = new FragmentPetStore();
                store_dialog.setCancelable(false);
                store_dialog.show(getActivity().getSupportFragmentManager(), "pet store");
            }
        });

        // if not the first time,  read from the sugar orm
        if (StartActivity.isPetInitiated == false) {
            mPet = (PetModel) getActivity().getIntent().getSerializableExtra(PersonalizeActivity.KEY_PET);
            StartActivity.isPetInitiated = true;
        }

        setPetImage();

        setLevelAndCoins();

        setAttributes();

        return rootView;
    }

    public void setAttributes() {

        threadEnabled =true;
        new barHungerThread().start();
        new barHygieneThread().start();
        new barFunThread().start();
        new barExpThread().start();

        //set Mood

    }


    public static void setLevelAndCoins() {
        tvTotalCoins.setText("Coins: \n" + String.valueOf(mPet.getTotalCoins()));
        tvLevel.setText("Level: \n" + String.valueOf(mPet.getLevel()));
    }

    public static void setPetImage() {
        switch (mPet.getKind()) {
            case PetModel.KIND_C1:
                ivPet.setImageResource(R.drawable.charmander);
                break;
            case PetModel.KIND_C2:
                ivPet.setImageResource(R.drawable.charmeleon);
                break;
            case PetModel.KIND_C3:
                ivPet.setImageResource(R.drawable.charizard);
                break;
            case PetModel.KIND_S1:
                ivPet.setImageResource(R.drawable.squirtle);
                break;
            case PetModel.KIND_S2:
                ivPet.setImageResource(R.drawable.wartortle);
                break;
            case PetModel.KIND_S3:
                ivPet.setImageResource(R.drawable.blastoise);
                break;
            case PetModel.KIND_B1:
                ivPet.setImageResource(R.drawable.bulbasaur);
                break;
            case PetModel.KIND_B2:
                ivPet.setImageResource(R.drawable.ivysaur);
                break;
            case PetModel.KIND_B3:
                ivPet.setImageResource(R.drawable.venusaur);
                break;
        }
    }

    public void showToastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
