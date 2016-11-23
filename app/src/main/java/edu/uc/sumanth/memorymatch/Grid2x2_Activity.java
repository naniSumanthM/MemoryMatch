package edu.uc.sumanth.memorymatch;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;


import java.util.Random;

public class Grid2x2_Activity extends AppCompatActivity
implements View.OnClickListener {

    public int numberOfElements;
    public MemoryButton[] buttonCollection;

    public int[] buttonGraphicLocations;
    public int[] buttonGraphics;

    public MemoryButton selectedButton1;
    public MemoryButton selectedButton2;

    public boolean isProcessing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid2x2_);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.activity_grid2x2_);
        int totalColumns = gridLayout.getColumnCount();
        int totalRows = gridLayout.getRowCount();

        numberOfElements = (totalColumns * totalRows);
        buttonCollection = new MemoryButton[numberOfElements];

        buttonGraphics = new int[(numberOfElements / 2)];
        buttonGraphics[0] = R.drawable.front_27;
        buttonGraphics[1] = R.drawable.front_28;

        buttonGraphicLocations = new int[numberOfElements];
        randomizeButtonGraphics();

        for (int r = 0; r < totalRows; r++) {
            for (int c = 0; c < totalColumns; c++) {
                MemoryButton tempButton = new MemoryButton(this, r, c, buttonGraphics[buttonGraphicLocations[r * totalColumns + c]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttonCollection[r * totalColumns + c] = tempButton; //Storing the references
                gridLayout.addView(tempButton);
            }
        }
    }

    public void randomizeButtonGraphics() {
        Random rnd = new Random();
        for (int i = 0; i < numberOfElements; i++) {
            buttonGraphicLocations[i] = (i % (numberOfElements / 2));
        }

        for (int i = 0; i < numberOfElements; i++) {
            int temp = buttonGraphicLocations[i];
            int swapIndex = rnd.nextInt(4);
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
        MemoryButton button = (MemoryButton) view;
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
