package com.hwhollywu.mobileproject.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by hwhollywu on 7/5/16.
 */

public class WalkModel extends SugarRecord implements Serializable {

    public static final int GOAL_STEP = 10000;
    public static final int GOAL_MILES = 5;

    public int stepGoal;
    public int mileGoal;
    public int todayCoins;
    public double todaypercentGoal;
    public int todaySteps;
    public double todayMiles;

    private static WalkModel instance = null;

    public static WalkModel getInstance() {
        if (instance == null) {
            instance = new WalkModel(GOAL_STEP, GOAL_MILES);
        }
        return instance;
    }

    public WalkModel(){

    }

    public WalkModel(int stepGoal, int mileGoal) {
        this.stepGoal = stepGoal;
        this.mileGoal = mileGoal;
    }

    public void calculatePercentGoal(){
        if(todaySteps!=-1){
            double percentGoalByStep = todaySteps/stepGoal;
            todaypercentGoal = percentGoalByStep;
        }else{
            double percentGoalByMiles =todayMiles/mileGoal;
            todaypercentGoal = percentGoalByMiles;
        }
    }

    public void calculateCoinsEarned(){
        if(todaySteps!=-1){
            double coinsByStep = (todaySteps/100)+todaypercentGoal*100;
            todayCoins=(int) coinsByStep;
        }else{
            if (todaypercentGoal>=1){
                double coinsByMiles = 20*todayMiles +100;
                todayCoins = (int) coinsByMiles;

            }else {
                double coinsByMiles = 20 * todayMiles + todaypercentGoal * 100;
                todayCoins = (int) coinsByMiles;
            }
        }
    }

    public int getStepGoal() {
        return stepGoal;
    }


    public int getMileGoal() {
        return mileGoal;
    }

    public double getTodayMiles() {
        return todayMiles;
    }

    public double getTodaypercentGoal() {
        return todaypercentGoal;
    }

    public int getTodaySteps() {
        return todaySteps;
    }

    public int getTodayCoins() {
        return todayCoins;
    }

    public void setTodayMiles(double todayMiles) {
        this.todayMiles = todayMiles;
    }

    public void setTodaySteps(int todaySteps) {
        this.todaySteps = todaySteps;
    }

    public void setTodaypercentGoal(double todaypercentGoal) {
        this.todaypercentGoal = todaypercentGoal;
    }

    public void setTodayCoins(int todayCoins) {
        this.todayCoins = todayCoins;
    }

    public void resetWalkModel(){
        todayMiles = 0.0;
        todayCoins = 0;
        todaypercentGoal =0.0;
        todaySteps =0;
    }
}
