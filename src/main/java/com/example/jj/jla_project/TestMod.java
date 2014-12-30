package com.example.jj.jla_project;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


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

    //Hashtable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mod);

        FrameLayout rLayout = (FrameLayout) findViewById(R.id.FrameLayout);
        Resources res = getResources(); //resource handle
        Drawable drawable = res.getDrawable(R.drawable.bedroom); //new Image that was added to the res folder

        rLayout.setBackgroundDrawable(drawable);

        ImageView left = (ImageView) findViewById(R.id.imageLeft);
        ImageView right = (ImageView) findViewById(R.id.rightImage);
        ImageView middle = (ImageView) findViewById(R.id.middleImage);
        ImageView background = (ImageView) findViewById(R.id.testBackGround);

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
        DB.insertRow(2, 1, 1, 1.0, 1.0, 1.0);
        // DB.updateRow(1,1 ,5,1,1.0,1.0,1.9);
        Cursor c = DB.queryAll(2, 1, 1);
        c.moveToNext();

        TextView t = (TextView) findViewById(R.id.savedStateView);
        try {
            savedState = "" + c.getInt(1) + "," + c.getInt(2) + "," + c.getInt(3);
            t.setText(savedState);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

        currentTuple.updateTuple(1, 5, 1);
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


        } catch (IOException e) {
            e.printStackTrace();
        }


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


                HashMap<Integer, String> cache2 = new HashMap<Integer, String>();


                Tuple testTuple = Tuple.getInstance();
                testTuple.updateTuple(1, 12, 1);
                cache2.put(testTuple.hashCode(), "Jesse");

                System.err.println("This versus this is " + testTuple.getTupleInt());
                testTuple.genericUpdate();
                testTuple.updateTuple(1, 12, 1);

                testView.setText("HEY LOOKIE HERE " + cache2.get(testTuple.getTupleInt()));
                // Need to override Tuples ---> To describe instance of classes, rather than
                // Pointing to
                System.err.println("This versus this is " + testTuple.getTupleInt());


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
