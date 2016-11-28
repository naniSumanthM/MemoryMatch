package edu.uc.sumanth.memorymatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.Random;

public class Grid6x6_Activity extends AppCompatActivity
        implements View.OnClickListener {

    public int numberOfElements;
    public MemoryButtonC[] buttonCollection;

    //buttonGraphicLocations - unique combinations of cards we have
    public int[] buttonGraphicLocations;
    //buttonGraphics - The original R.ID.Drawable id's for the individual pictures we have
    public int[] buttonGraphics;

    public MemoryButtonC selectedButton1;
    public MemoryButtonC selectedButton2;

    //Used for creating the delay
    public boolean isProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid6x6_);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.activity_grid6x6_);

        //#of Columns & Rows
        int totalColumns = gridLayout.getColumnCount();
        int totalRows = gridLayout.getRowCount();

        numberOfElements = (totalColumns * totalRows);
        buttonCollection = new MemoryButtonC[numberOfElements];

        //only stores the unique cards
        buttonGraphics = new int[(numberOfElements / 2)];

        buttonGraphics[0] = R.drawable.front_1;
        buttonGraphics[1] = R.drawable.front_2;
        buttonGraphics[2] = R.drawable.front_3;
        buttonGraphics[3] = R.drawable.front_4;
        buttonGraphics[4] = R.drawable.front_5;
        buttonGraphics[5] = R.drawable.front_6;
        buttonGraphics[6] = R.drawable.front_7;
        buttonGraphics[7] = R.drawable.front_8;
        buttonGraphics[8] = R.drawable.front_9;
        buttonGraphics[9] = R.drawable.front_10;
        buttonGraphics[10] = R.drawable.front_11;
        buttonGraphics[11] = R.drawable.front_12;
        buttonGraphics[12] = R.drawable.front_13;
        buttonGraphics[13] = R.drawable.front_14;
        buttonGraphics[14] = R.drawable.front_15;
        buttonGraphics[15] = R.drawable.front_16;
        buttonGraphics[16] = R.drawable.front_17;
        buttonGraphics[17] = R.drawable.front_18;

        buttonGraphicLocations = new int[numberOfElements];
        randomizeButtonGraphics();

        for (int r = 0; r < totalRows; r++) {
            for (int c = 0; c < totalColumns; c++) {
                MemoryButtonC tempButton = new MemoryButtonC(this, r, c, buttonGraphics[buttonGraphicLocations[r * totalColumns + c]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttonCollection[r * totalColumns + c] = tempButton; //Storing the references
                gridLayout.addView(tempButton);
            }
        }
    }

    //Menu Bar

    //Inflate the Menu widgets
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_6x6,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Logic for the home and the reset btn
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.it_home6x6: goHome();

            case R.id.it_refresh6x6: reset6x6Grid();

            default: return super.onOptionsItemSelected(item);
        }
    }

    public void goHome()
    {
        Intent mainMenu= new Intent(Grid6x6_Activity.this, MainMenu_Activity.class);
        startActivity(mainMenu);
    }

    //Launch a new Grid2x2_Activity since it contains the logic to randomize and rotate the cards
    public void reset6x6Grid()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }

    public void randomizeButtonGraphics() {
        Random rnd = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            buttonGraphicLocations[i] = (i % (numberOfElements / 2));
        }

        for (int i = 0; i < numberOfElements; i++) {
            int temp = buttonGraphicLocations[i];
            int swapIndex = rnd.nextInt(36);
            buttonGraphicLocations[i] = buttonGraphicLocations[swapIndex];
            buttonGraphicLocations[swapIndex] = temp;
        }
    }

    public void matchToast() {
        Context match = getApplicationContext();
        String matchText = "MATCHED!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(match, matchText, duration);
        toast.show();
    }

    public void misMatchToast() {
        Context mismatch = getApplicationContext();
        String mismatchText = "TRY-AGAIN!";
        int duration = Toast.LENGTH_SHORT;

        Toast mismatchToast = Toast.makeText(mismatch, mismatchText, duration);
        mismatchToast.show();
    }


    @Override
    public void onClick(View view) {
        MemoryButtonC button = (MemoryButtonC) view;
        Handler handler = new Handler();

        if (isProcessing) {
            //doNothing when there is a process running - this prevents crashes
            return;
        }

        if (button.isMatched) {
            //if Button has a match then do nothing
            return;
        }

        if (selectedButton1 == null) {
            selectedButton1 = button;
            selectedButton1.rotate();
            return;
        }

        if (selectedButton1.getId() == button.getId()) {
            return;
        }

        if (selectedButton1.getFrontDrawableValue() == button.getFrontDrawableValue()) {
            //User matches two cards
            button.rotate();
            button.setMatched(true);
            selectedButton1.setEnabled(false);
            button.setEnabled(false);
            selectedButton1 = null;
            matchToast();

            return;
        } else {
            //User fails to match two cards
            selectedButton2 = button;
            selectedButton2.rotate();
            isProcessing = true;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.rotate();
                    selectedButton1.rotate();
                    selectedButton1 = null;
                    selectedButton2 = null;
                    isProcessing = false;
                }
            }, 400);
            misMatchToast();
        }
    }
}

class MemoryButtonC extends Button {

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
    public MemoryButtonC(Context context, int Row, int Column, int FrontDrawableValue) {
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

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(Row), GridLayout.spec(Column));
        params.width = (int) getResources().getDisplayMetrics().density * 80;
        params.height = (int) getResources().getDisplayMetrics().density * 116;

        setLayoutParams(params);
    }

    //setters and getters for the primitives

    //get: isMatched()
    public boolean isMatched() {
        return isMatched;
    }

    //set: isMatched()
    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    //get: frontDrawableValue (ID)
    public int getFrontDrawableValue() {
        return frontDrawableValue;
    }

    //Logic: Rotate
    public void rotate() {
        if (isMatched) {
            //doNothing since the user did not match.
            //In this case the user revealed two cards that are an incorrect match.
            return;
        } else {
            //We assume the user matched
        }

        if (isRotated) {
            //doNothing if the user did not rotate any card and keep the card faced down.
            setBackground(backOfCard);
            isRotated = false;
        } else {
            //if the userRotated the card then set the isRotated to true for a duration of Time
            //Reveal the card
            setBackground(frontOfCard);
            isRotated = true;
        }
    }
}