package com.hwhollywu.mobileproject.model;

import android.widget.ImageView;
import android.widget.Toast;

import com.hwhollywu.mobileproject.PetFragment;
import com.orm.SugarRecord;
import java.lang.Math;

import java.io.Serializable;

/**
 * Created by hwhollywu on 7/3/16.
 */

public class PetModel extends SugarRecord implements Serializable {

    public static final short KIND_C1 = 0; //charmander
    public static final short KIND_C2 = 1; //charmeleon
    public static final short KIND_C3 = 2; //charizard
    public static final short KIND_S1 = 3; //squirtle
    public static final short KIND_S2 = 4; //wartortle
    public static final short KIND_S3 = 5; //blastoise
    public static final short KIND_B1 = 6; //bulbasaur
    public static final short KIND_B2 = 7; //ivysaur
    public static final short KIND_B3 = 8; //venusaur

    public static final short GENDER_FEMALE =1;
    public static final short GENDER_MALE =2;


    public String name;
    public short gender; // 0 for female, 1 for male
    public short kind; // 3 basic kinds
    public int hunger; // between 0-100
    public int hygiene; // between 0-100
    public int fun; // between 0-100
    public int level; //between 1-10
    public double expForNextLevel; //change according to level
    public double exp; //0-expForNextLevel
    public int totalCoins; // 0-infinity


    public PetModel(){

    }

    public PetModel(String name, short gender, short kind) {
        this.name = name;
        this.gender = gender;
        this.kind = kind;
        hunger = 100;
        hygiene = 100;
        fun = 100;
        level = 1;
        exp = 0;
        expForNextLevel= 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public short getKind() {
        return kind;
    }

    public void setKind(short kind) {
        this.kind = kind;
    }

    public int getHunger() {
        return hunger;
    }

    public void updateHunger(){
        int elapsedMinutes = calculateElapsedTime();
        hunger -= elapsedMinutes;
        if(hunger<0){
            hunger =0;
        }
    }

    private int calculateElapsedTime() {
        long timePetEnd = System.currentTimeMillis();
        long timePetPassed =timePetEnd - PetFragment.timePetStart;
        PetFragment.timePetStart=timePetEnd;
        return (int) timePetPassed / (1000);// should be 1000*60, test with 1000
    }

    public int getHygiene() {
        return hygiene;
    }

    public void updateHygiene(){
        int elapsedMinutes = calculateElapsedTime();
        hygiene -= elapsedMinutes;
        if(hygiene<0){
            hygiene =0;
        }
    }

    public int getFun() {
        return fun;
    }

    public void updateFun(){
        int elapsedMinutes = calculateElapsedTime();
        fun -= elapsedMinutes;
        if(fun<0){
            fun =0;
        }
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        //show "levelup" message
        level++;
        setExpForNextLevel();
        exp=0;
        //set max level
        if (level>=10){
            level = 10;
        }
        if (level==10){
            //show "reach max level" message
        }else if (level ==3){
            //show "Evolve" message
            kind ++;
        }else if (level ==7){
            //show "Evolve" message
            kind ++;
        }
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void addToTotalCoins(int todayCoins) {
        totalCoins += todayCoins;
    }

    public double getExpForNextLevel() {
        return expForNextLevel;
    }

    public void setExpForNextLevel() {
        expForNextLevel = level*level*100;
    }

    public double getExp() {
        return exp;
    }

    public void updateExp(){
        int elapsedMinutes = calculateElapsedTime();
        if ( 70 <= getMinHHF()&& getMinHHF() <100){
            exp += elapsedMinutes*5;
        }else if (30<= getMinHHF() && getMinHHF()<70){
            exp += elapsedMinutes*3;
        }else if (getMinHHF()<30){
            exp += elapsedMinutes*1;
        }
        if (exp >= expForNextLevel){
            levelUp();
        }
    }

    public int getMinHHF(){
        if (hunger >= hygiene && hunger >=fun){
            return hunger;
        }else if (hygiene >=hunger && hygiene >= fun){
            return hygiene;
        }else {return fun;}
    }


}
