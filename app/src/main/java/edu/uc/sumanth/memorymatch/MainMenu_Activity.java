package edu.uc.sumanth.memorymatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainMenu_Activity extends AppCompatActivity {
    //Class is used to dictate what the button clicks do on the mainActivity
    //GameBoard size options

    private RadioGroup gridGroup;
    private RadioButton rb_2x2, rb_4x4, rb_6x6;
    private Button btnStart, btnExit, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_);

        //References to the radio group widgets
        gridGroup = (RadioGroup) findViewById(R.id.GridGroup);
        rb_2x2 = (RadioButton) findViewById(R.id.rb_2x2);
        rb_4x4 = (RadioButton) findViewById(R.id.rb_4x4);
        rb_6x6 = (RadioButton) findViewById(R.id.rb_6x6);

        gridGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_2x2) {
                    toast2x2();
                } else if (checkedId == R.id.rb_4x4) {
                    toast4x4();
                } else {
                    toast6x6();
                }
            }
        });

        btnStart = (Button) findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGridSize = gridGroup.getCheckedRadioButtonId();

                if (selectedGridSize == rb_2x2.getId()) {
                    Intent twoByTwo = new Intent(MainMenu_Activity.this, Grid2x2_Activity.class);
                    startActivity(twoByTwo);
                } else if (selectedGridSize == rb_4x4.getId()) {
                    Intent fourByFour = new Intent(MainMenu_Activity.this, Grid4x4_Activity.class);
                    startActivity(fourByFour);
                } else {
                    Intent sixBySix = new Intent(MainMenu_Activity.this, Grid6x6_Activity.class);
                    startActivity(sixBySix);
                }
            }
        });

        //set reference to the reset button and define the logic of game reset
        btnReset = (Button) findViewById(R.id.button_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDO: Logic to reset the game board
            }
        });

        //set reference to exit button
        btnExit = (Button) findViewById(R.id.button_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    //Toast methods
    public void toast2x2() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Come On", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.END | Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void toast4x4() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Ok!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.END | Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void toast6x6() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Dayum!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.END | Gravity.CENTER, 0, 0);
        toast.show();
    }
}
