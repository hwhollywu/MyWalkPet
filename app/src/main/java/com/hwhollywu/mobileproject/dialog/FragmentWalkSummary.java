package com.hwhollywu.mobileproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.hwhollywu.mobileproject.MapsFragment;
import com.hwhollywu.mobileproject.R;
import com.hwhollywu.mobileproject.model.WalkModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hwhollywu on 7/6/16.
 */


public class FragmentWalkSummary extends DialogFragment {

    public static final String KEY_MSG = "KEY_MSG";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle(R.string.walking_summary);

        //calculate walk time
        long timeWalkEnd = System.currentTimeMillis();
        long timeWalkDuration = timeWalkEnd - MapsFragment.timeWalkStart;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");;
        DecimalFormat numberFormat = new DecimalFormat("#0.000");

        alertDialogBuilder.setMessage("Total Miles: "+ numberFormat.format(WalkModel.getInstance().getTodayMiles())
        +"\n Coins earned: "+ WalkModel.getInstance().todayCoins
        +"\n Time: "+ dateFormat.format(new Date(timeWalkDuration)));
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder.create();
    }
}
