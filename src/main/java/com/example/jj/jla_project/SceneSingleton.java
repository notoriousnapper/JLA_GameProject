package com.example.jj.jla_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jj on 12/21/14.
 */
public class SceneSingleton {

    final String SYM_TM = "_m:";
    final String SYM_S = "_s:";
    final String SYM_BGIMG = "_bgImg:";
    final String SYM_IMGL = "_imgL:";
    final String SYM_IMGR = "_imgR:";
    final String SYM_IMGC = "_imgC:";
    final String SYM_TXT = "_txt:";
    final String SYM_BTN = "_btn:";
    final String SYM_JMP= "_jmp:";
    final String SYM_BTNEXISTS= "_*:";
    final String JMP_NUM = "&";
    final String SYM_BTNTEXT = "_";


    BufferedReader bufferPtr;
    private Tuple currentTuple;  // Only get newest

    public String themeMusic;    // _m:
    private String soundFile;     // _s:
    private String backgroundIMG; // _bgImg:
    private String imgL;          // _imgL:
    private String imgR;          // _imgR:
    private String imgC;          // _imgC:
    private String text;          // _txt:
    private boolean btnExists = false;
    private String  btnTxt[];
    private String  btnAdd[];

    private String currentLine;





    private volatile static SceneSingleton uniqueInstance = null;

    public void popSceneSingleton()  // Must have the currentLine
    {
            // Given the proper Ptr at location
            // Take Everything, and leave nothing!
            try {
                System.err.println("Starting CurrentLine is " + this.currentLine);
                System.err.println("FLAG1");
                currentLine = bufferPtr.readLine(); // Requires space between all segments



                if ( currentLine.contains( SYM_TM ) )
                {
                    //System.err.println("currentline is" + getcurrentLine() + "SUBSTRINGS ARE " + substringAfter(currentLine,SYM_TM));
                    themeMusic = substringAfter(currentLine,SYM_TM);
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_S ) ) {

                    soundFile = substringAfter(currentLine, SYM_S);
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_BGIMG ) ) {
                    backgroundIMG = substringAfter(currentLine, SYM_BGIMG);
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_IMGL) ) {
                    imgL = substringAfter(currentLine, SYM_IMGL);
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_IMGR ) ) {
                    imgR = substringAfter(currentLine, SYM_IMGR);
                    System.err.println("The imgR is " + this.imgR);

                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_IMGC ) ) {
                    imgC = substringAfter(currentLine, SYM_IMGC);
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_TXT) ) {
                    boolean temp = false;


                    text = substringAfter(currentLine, SYM_TXT);
                    currentLine = bufferPtr.readLine();
                    System.err.println("First Line  Exists!!!!");
                    if (currentLine.contains(SYM_BTN))
                    {temp = true;
                     currentLine = substringAfter(currentLine, SYM_BTN);}
                    else
                    { System.err.println("Middle Current Line is = " + currentLine);}
                    if (temp)  {
                        System.err.println("So does this one exists!!!!");
                        setbtnExists(true);
                        // TESTING
                        //String temp = substringAfter(currentLine, SYM_BTN);
                        btnAdd = currentLine.split(SYM_JMP); // Gets 3 Address
                        bufferPtr.readLine(); // MAKE SURE THERE IS A TRANSFER ADDRESS ON SCRIPT

                        // GRAB BUTTON TEXTS
                        currentLine = bufferPtr.readLine();
                        //temp = substringAfter(currentLine,SYM_BTNTEXT);
                        btnTxt = currentLine.split(SYM_BTNTEXT);

                        // RESET POINTERS
                        currentLine = bufferPtr.readLine(); // In the case of button, need to manually change tuple.
                    }
                    else{setbtnExists(false);} // CHECK TO MAKE SURE THIS FALLS THROUGH

                }
                if ( (currentLine.contains( SYM_JMP )) && (btnExists!= true ) ) {
                    String parsedString = substringAfter(currentLine, JMP_NUM);
                    currentTuple.updateTuple(parsedString); // PARSING ----> IPDATETIPLE
                    //currentLine = bufferPtr.readLine();
                }
                //else if (there are button options)
                else
                {
                    currentTuple.genericUpdate();
                }
                System.err.println("Ending currentLine is " + currentLine);
                /*
                {
                    String [] tokens = currentLine.split( "&" );
                    imgName = tokens[1];
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( "MUS" ) )
                {
                    String[] tokens = currentLine.split( "&" );
                    musicName = tokens[1];
                    currentLine = buffr.readLine();
                }
                */
            } catch ( Exception e ) { e.printStackTrace(); }

    }


            // Make sure that the script starts with fluff,


    public static synchronized SceneSingleton getInstance()
    {
        if (uniqueInstance == null)
        {
            uniqueInstance = new SceneSingleton();
        }
        return uniqueInstance;
    }

    public void pointToNext() //Points Buffr Ptr to correct line, given
    {

        // While the next line has not been reached
        try {

            while (!(currentLine.contains(
                    currentTuple.getTupleString())))
            {
                currentLine = bufferPtr.readLine();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }

    }




    public void connectBufferedReader(BufferedReader bPtr)

    {
        bufferPtr = bPtr;
        try{
        currentLine = bufferPtr.readLine();}
        catch(IOException e) { e.printStackTrace();}
        //catch(NullPointerException e)
        //{e.printStackTrace();}


    }

    public void wipeOtherInfo()
    {

    }




    public String toString()
    {
        //String temp = "not your stuff";
       // try {temp = bufferPtr.readLine();} catch(IOException e) { e.printStackTrace();}
        return "The SceneSingleton has "
        + " " + " THE BUTTON " + btnExists + " "
        + " " + themeMusic    // _m:
        + " " + soundFile     // _s:
        + " " + backgroundIMG// _bgImg:
        + " " + imgL          // _imgL:
        + " " + imgR          // _imgR:
        + " " + imgC          // _imgC:
        + " " + text          // _txt:
        + " currentTuple is " + currentTuple.getTupleString()
        + " and currentLine is " +
                currentLine;
    }


    /*
     * Getter/SetterMethods
     */


    public String getthemeMusic()
    {return this.themeMusic;}
    public String getSoundFile()
    {return this.soundFile;}
    public String getbackgroundImg()
    {return this.backgroundIMG;}
    public String getimgL()
    {return this.imgL;}
    public String getimgR()
    {return this.imgR;}
    public String getimgC()
    {return this.imgL;}

    public String gettext()
    {return this.text;}

    public String getbtnAdd(int position) // starts from 0
    {return btnAdd[position];}

    public String getbtnTxt(int position) // starts from 0
    {return btnTxt[position];}

    public boolean getbtnExists()
    {return this.btnExists;}

    public String getcurrentLine()
    {return this.currentLine;}

    public Tuple getcurrentTuple()
    {return this.currentTuple;}



    public void setcurrentTuple(Tuple newTuple)
    {currentTuple = newTuple;}

    public void setbtnExists(boolean b)
    {btnExists = b;}




    /*
     * Helper Methods for String Parsing
     *
     *
     */

    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}
