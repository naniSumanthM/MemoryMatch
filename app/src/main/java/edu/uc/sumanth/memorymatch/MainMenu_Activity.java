package edu.uc.sumanth.memorymatch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu_Activity extends AppCompatActivity {

    //Class is used to dictate what the button clicks do on the mainActivity

    //GameBoard size options
    private Button btn2x2;
    private Button btn4x4;
    private Button btn6x6;
    private Button btnStart;
    private Button btnExit;
    private Button btnReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_);

        //set reference & Listener : 2X2

        btn2x2 = (Button)findViewById(R.id.button_2x2);
        btn2x2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent twoBytwo = new Intent(MainMenu_Activity.this, Grid2x2_Activity.class );
                startActivity(twoBytwo);
            }
        });

        //set reference & Listener : 4X4
        btn4x4 = (Button) findViewById(R.id.button_4x4);
        btn4x4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent fourByfour = new Intent(MainMenu_Activity.this, Grid4x4_Activity.class);
                startActivity(fourByfour);
            }
        });


        //set reference & Listener : 6X6
        btn6x6 = (Button) findViewById(R.id.button_6x6);
        btn6x6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent sixBysix = new Intent(MainMenu_Activity.this, Grid6x6_Activity.class);
                startActivity(sixBysix);
            }
        });

        //set reference to start game
        btnStart = (Button)findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check to see which grid button was clicked
                switch(v.getId())
                {
                    case R.id.button_2x2:
                        //do
                        break;
                    case R.id.button_4x4:
                        //do
                        break;
                    case R.id.button_6x6:
                        //do
                        break;
                } //sharedPreferences from old labs
            }
        });

        //set reference to the reset button and define the logic of game reset
        btnReset = (Button) findViewById(R.id.button_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //set reference to exit button
        btnExit = (Button)findViewById(R.id.button_exit);
        btnExit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                System.exit(0);
            }
        });
    }
}
