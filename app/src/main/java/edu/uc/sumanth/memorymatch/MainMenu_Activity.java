package edu.uc.sumanth.memorymatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private RadioButton rb_selectedGrid;
    private Button btnStart, btnExit, btnSave;
    SharedPreferences saveGridSize, getSavedGridSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_);

        saveGridSize = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        gridGroup = (RadioGroup)findViewById(R.id.GridGroup);
        btnSave = (Button)findViewById(R.id.button_save);

        //User is expected to save hsi grid choice before starting the game, if the user starts the game without making a choice
        // "THE LAST SAVED PREFERENCE IS AUTOMATICALLY FIRED".
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = gridGroup.getCheckedRadioButtonId();
                rb_selectedGrid = (RadioButton)findViewById(selectedRadioButtonId);
                String selectedRadioButtonText = rb_selectedGrid.getText().toString();
                SharedPreferences.Editor editor = saveGridSize.edit();
                editor.putString("gridKey",selectedRadioButtonText);
                editor.commit();
            }
        });

        //Start Game button and reference - Inspects the saved preference and fires the respective grid size
        btnStart=(Button)findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSavedGridSize = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
                String userGridChoice = getSavedGridSize.getString("gridKey","4X4 GRID"); //(key, defaultValue) - 2x2 grid size is chosen for the first time if the user did not make a choice
                if(userGridChoice.equals("2X2 GRID"))
                {
                    toast2x2();
                    Intent twoByTwo = new Intent(MainMenu_Activity.this, Grid2x2_Activity.class);
                    startActivity(twoByTwo);
                }
                else if(userGridChoice.equals("4X4 GRID"))
                {
                    toast4x4();
                    Intent fourByFour = new Intent(MainMenu_Activity.this, Grid4x4_Activity.class);
                    startActivity(fourByFour);
                }
                else
                {
                    toast6x6();
                    Intent sixBySix = new Intent(MainMenu_Activity.this, Grid6x6_Activity.class);
                    startActivity(sixBySix);
                }
            }
        });

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
                "Starting the baby gird! ", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toast4x4() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Starting an acceptable grid!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toast6x6() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Starting a too BIG gird!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
