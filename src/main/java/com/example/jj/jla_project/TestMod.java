package com.example.jj.jla_project;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.transition.Scene;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    String savedState;    // The parsed combo of 3 numbers from DB
    Boolean conditionMet = false;
    SceneSingleton Bob;
    Tuple currentTuple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mod);

        FrameLayout rLayout = (FrameLayout) findViewById (R.id.FrameLayout);
        Resources res = getResources(); //resource handle
        Drawable drawable = res.getDrawable(R.drawable.bedroom); //new Image that was added to the res folder

        rLayout.setBackgroundDrawable(drawable);

        ImageView left = (ImageView) findViewById(R.id.leftImage);
        ImageView right = (ImageView) findViewById(R.id.rightImage);
        ImageView middle = (ImageView) findViewById(R.id.middleImage);
        ImageView background = (ImageView)findViewById(R.id.testBackGround);

        left.setImageResource(R.drawable.pw1);
        right.setImageResource(R.drawable.pw1);
        middle.setImageResource(R.drawable.pw1);
        //background.setImageResource(R.drawable.bedroom);




        currentTuple = Tuple.getInstance();
        //currentTuple.updateTuple(1, 1, 1);
        System.err.println("THERE IS A TUPLE HERE: " + currentTuple.getTupleString());


        // Testing Databases
        DBAdapter DB = new DBAdapter(this);
        DB.open();
        DB.insertRow (2, 1 ,1, 1.0, 1.0, 1.0);
       // DB.updateRow(1,1 ,5,1,1.0,1.0,1.9);
        Cursor c = DB.queryAll(2,1,1);
                c.moveToNext();

        TextView t = (TextView) findViewById(R.id.savedStateView);
        try {
            savedState = ""+c.getInt(1) + "," + c.getInt(2) + "," + c.getInt(3);
            t.setText(savedState);

        }
        catch(NullPointerException e)
        {e.printStackTrace();}
        //Cursor tempC = DB.queryStuff(1,1,1);


        I = getResources().openRawResource(R.raw.script2);
        B = new BufferedReader(new InputStreamReader(I));
        testButton = (Button) findViewById(R.id.testButton);

        testView = (TextView) findViewById(R.id.testView);

        /*
        try{currentLine = B.readLine();}
        catch(IOException e) {
            e.printStackTrace();
        }
        */


        currentTuple = Tuple.getInstance();
        /* NEW TEST */

        currentTuple.updateTuple(1,5,1);
        Bob = SceneSingleton.getInstance();
        Bob.setcurrentTuple(currentTuple);
        //BufferedReader test = new BufferedReader(new InputStreamReader(I));
        Bob.connectBufferedReader(B);
        Bob.pointToNext();
        Bob.popSceneSingleton(); // This automatically sets new pointer

        try {
            System.err.println("Alex IS " + Bob.toString() +
                    "next lines are:  " + Bob.bufferPtr.readLine());

            Bob.pointToNext();
            Bob.popSceneSingleton();
            Bob.pointToNext();
            System.err.println("DANLI IS " + Bob.toString() +
                    " and the next lines are:  " + Bob.bufferPtr.readLine()
                    + " and " + Bob.bufferPtr.readLine() + " and " + Bob.bufferPtr.readLine());




        }

       catch (IOException e)
       {e.printStackTrace();}



        System.err.println("TUPLE TESTING" + currentTuple.getTupleString());
        currentTuple.updateTuple("2,4,1");
        System.err.println("TUPLE TESTING AGAIN" + currentTuple.getTupleString());
        Bob.setcurrentTuple(currentTuple);
        Bob.pointToNext();
        Bob.popSceneSingleton();
        System.err.println("WALA HERE IS " + Bob.toString());


        //Bob.setbtnExists(true);

        /* END TEST */

        // Button OnSetListeners
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Bob.getbtnExists())
                {
                    System.err.println("BUTTON EXISTS STUFF MWAHAHAHAH");
                    testView.setText("SOMETHING HAPPENED");
                }
                else {
                    Bob.pointToNext();
                    Bob.popSceneSingleton();
                    testView.setText(Bob.toString());
                }




                /*

                currentTuple.updateTuple(1, 5, 1);
                System.err.println("ROUNDS, CHECK IT OUT");
                System.err.println("CurrentLine is: " + currentLine);
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
                //String savedState = "1,5,1";

                /*
                Boolean stateLoaded = currentLine.contains("1,5,1");


                try {

                    // LoadScene Method

                    String searchLine = "$$"+ currentTuple.getTupleString();
                    System.out.println("Flag set!");
                    System.err.println("CURRENT SEARCH LINE IS" +searchLine);


                    if (!stateLoaded && !conditionMet) {
                        System.err.println("Inside While Loop");
                    // If Line doesn't contain State, enter loop
                        while (!currentLine.contains(searchLine)) {
                            currentLine = B.readLine();
                            //System.err.println(currentLine + "is the CURRENT LINE FOOL");
                        }
                        testView.setText(currentLine);

                        conditionMet = true;
                    }

                    else {
                        System.err.println("Flag set!");
                        B.readLine();
                        // Commented Out Method = Normal Update
                        while (!currentLine.contains("$$")) {
                            System.err.println(currentLine);
                            currentLine = B.readLine();
                        }
                        // IF you reach main lines, parse
                        currentLine = currentLine.replaceAll("\\$", "");
                        // Regexp requires escape chars "\\"
                        // Write Mini-Parsing Method for commas
                        try {
                            testView.setText(currentLine);
                            System.out.println(currentLine);


                            // Update to NextTuple
                            currentTuple.genericUpdate();
                            currentLine = currentTuple.getTupleString();
                                    System.err.println("New Tuple is " + currentTuple.getTupleString());




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
                */
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
