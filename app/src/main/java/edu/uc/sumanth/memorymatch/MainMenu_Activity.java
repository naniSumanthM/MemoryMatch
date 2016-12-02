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

/**
 * Sources: https://www.youtube.com/watch?v=NS5VzDUrS7A - Shared Preferences
 * Sources: https://www.youtube.com/watch?v=n04qMWGzqQU - Shared Preferences
 * Sources: https://www.youtube.com/watch?v=8NoHxfIu0MQ - Grid Layout
 * Sources: http://stackoverflow.com/questions/7165830/what-is-the-size-of-actionbar-in-pixels
 * Sources: http://stackoverflow.com/questions/3407256/height-of-status-bar-in-android
 */


public class MainMenu_Activity extends AppCompatActivity {
    //Class is used to dictate what the button clicks do on the mainActivity
    //GameBoard size options
    //Start, Exit, Save Btn's

    //Widget Variables
    private RadioGroup gridGroup;
    private RadioButton rb_selectedGrid;
    private Button btnStart, btnExit, btnSave;
    SharedPreferences saveGridSize, getSavedGridSize;
    String userGridChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_);

        saveGridSize = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        gridGroup = (RadioGroup)findViewById(R.id.GridGroup);
        btnSave = (Button)findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User is expected to save hsi grid choice before starting the game, if the user starts the game without making a choice
                // "THE LAST SAVED PREFERENCE IS AUTOMATICALLY FIRED".
                int selectedRadioButtonId = gridGroup.getCheckedRadioButtonId();
                rb_selectedGrid = (RadioButton)findViewById(selectedRadioButtonId);
                String selectedRadioButtonText = rb_selectedGrid.getText().toString();
                SharedPreferences.Editor editor = saveGridSize.edit();
                editor.putString("gridKey",selectedRadioButtonText);
                editor.commit();
            }
        });

        //Start Game button and reference - Inspects the saved preference and fires the respective grid size activity
        btnStart=(Button)findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSharedPreferenceGridSize();
            }
        });

        //Exit Button Reference - used to exit the game
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
                R.string.toastA, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toast4x4() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.toastB, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toast6x6() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.toastC, Toast.LENGTH_SHORT);
        toast.show();
    }

    //Reads the last saved radioButtonPreference
    public void getSharedPreferenceGridSize()
    {
        //(key, defaultValue) - 4x4 grid size is chosen for the first time if the user did not make a choice
        //If the app is not installed for the first time the last saved grid size pref will be started assuming the user did not make a choice
        getSavedGridSize = getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        userGridChoice = getSavedGridSize.getString("gridKey","4X4 GRID");

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

}
