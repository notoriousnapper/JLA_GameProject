package com.example.jj.jla_project;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TestMod extends Activity {

    // Declare Buttons
    TextView testView;
    Button testButton;
    BufferedReader B;
    InputStream I;
    String currentLine;
    Boolean conditionMet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mod);

        I = getResources().openRawResource(R.raw.script);
        B = new BufferedReader(new InputStreamReader(I));
        testButton = (Button) findViewById(R.id.testButton);


        testView = (TextView) findViewById(R.id.testView);
        try{currentLine = B.readLine();}
        catch(IOException e){ e.printStackTrace();}
        // Button OnSetListeners
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize Views
                /* Methods & Classes Needed:
                 * 1. Databases (2) + Class
                 *      i. Very First row is for temporary running memory
                 * 2. StoreCurrentState
                 * 3. Singleton
                 *      i. nextState
                 *      ii. currentState
                 *      iii. updateState( );   //Else, no parameter == just goes up
                 *      iv.
                 * 4. loadState ();  Loads A given saved state --> Pulled from database, or recent
                 * 5. Classes for handling database Updating/ Reading?  !!!
                 * 6. Should have invisible textbox & Views
                 */
                String savedState = "1,5,1";
                Boolean stateLoaded = currentLine.contains(savedState);

                try {

                    // LoadScene Method

                    String searchLine = "$$"+ savedState;
                    System.out.println("Flag set!");



                    if (!stateLoaded && !conditionMet) {
                        System.err.println("Inside While Loop");
                    // If Line doesn't contain State, enter loop
                        while (!currentLine.contains(searchLine)) {
                            currentLine = B.readLine();
                        }
                        testView.setText(currentLine);
                        conditionMet = true;
                    }

                    else {
                        System.err.println("Flag set!");
                        B.readLine();
                        // Commented Out Method = Normal Update
                        while (!currentLine.contains("$$")) {
                            System.out.println(currentLine);
                            currentLine = B.readLine();
                        }
                        // IF you reach main lines, parse
                        currentLine = currentLine.replaceAll("\\$", "");
                        // Regexp requires escape chars "\\"
                        // Write Mini-Parsing Method for commas
                        try {
                            testView.setText(currentLine);
                            System.out.println(currentLine);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }



                }catch(NullPointerException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    Log.e("message", e.getMessage());
                    e.printStackTrace();
                }
                try {
                    I.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                        }
                    });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_mod, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
