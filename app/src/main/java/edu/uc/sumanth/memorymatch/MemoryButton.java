package edu.uc.sumanth.memorymatch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

/**
 * Created by Sumanth on 11/19/2016.
 */

public class MemoryButton extends Button{

    //variables to reference the row and column, and the id of the faced down card
    public int row;
    public int column;
    public int frontDrawableValue;

    //Reference to the drawable objects()
    public Drawable frontOfCard;
    public Drawable backOfCard;

    //variables to define the Actions a user can encounter
    public boolean isRotated = false;
    public boolean isMatched = false;

    //Default Constructor
    public MemoryButton(Context context, int Row, int Column, int FrontDrawableValue)
    {
        //Parent Class Constructor
        super(context);

        //This= MemoryButton
        this.row = Row;
        this.column = Column;
        this.frontDrawableValue = FrontDrawableValue;

        //initialize the front & back of the card
        frontOfCard = AppCompatDrawableManager.get().getDrawable(context, frontDrawableValue);
        backOfCard = AppCompatDrawableManager.get().getDrawable(context, R.drawable.back);
        setBackground(backOfCard);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(Row),GridLayout.spec(Column));
        params.width = (int) getResources().getDisplayMetrics().density * 110;
        params.height = (int) getResources().getDisplayMetrics().density * 145;
        //LayoutInflator



        //params.setGravity(100); - Need to strech children in grid layout to the entire screen
        setLayoutParams(params);
    }

    //setters and getters for the primitives

    //get: isMatched()
    public boolean isMatched(){
        return isMatched;
    }

    //set: isMatched()
    public void setMatched(boolean matched){
        isMatched = matched;
    }

    //get: frontDrawableValue (ID)
    public int getFrontDrawableValue(){
        return frontDrawableValue;
    }

    //Logic: Rotate
    public void rotate()
    {
        if(isMatched){
            //doNothing since the user did not match.
            //In this case the user revealed two cards that are an incorrect match.
            return;
        }
        else{
            //We assume the user matched
        }

        if(isRotated){
            //doNothing if the user did not rotate any card and keep the card faced down.
            setBackground(backOfCard);
            isRotated = false;
        }
        else{
            //if the userRotated the card then set the isRotated to true for a duration of Time
            //Reveal the card
            setBackground(frontOfCard);
            isRotated = true;
        }
    }
}
