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

    BufferedReader bufferPtr;
    Tuple currentTuple;  // Only get newest

    public String themeMusic;    // _m:
    private String soundFile;     // _s:
    private String backgroundIMG; // _bgImg:
    private String imgL;          // _imgL:
    private String imgR;          // _imgR:
    private String imgC;          // _imgC:
    private String text;          // _txt:
    private String button;        // _btn:
    private String currentLine;




    private volatile static SceneSingleton uniqueInstance = null;

    public void popSceneSingleton()  // Must have the currentLine
    {
            // Given the proper Ptr at location
            // Take Everything, and leave nothing!

            try {

                currentLine = bufferPtr.readLine(); // Requires space between all segments
                //System.out.println("Before ");

                if ( currentLine.contains( SYM_TM ) )
                {
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
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_IMGC ) ) {
                    imgC = substringAfter(currentLine, SYM_IMGC);
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_TXT) ) {
                    text = substringAfter(currentLine, SYM_TXT);
                    currentLine = bufferPtr.readLine();
                }
                if ( currentLine.contains( SYM_BTN ) ) {
                    button = substringAfter(currentLine, SYM_BTN);
                    currentLine = bufferPtr.readLine();
                }


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




    public String toString()
    {
        //String temp = "not your stuff";
       // try {temp = bufferPtr.readLine();} catch(IOException e) { e.printStackTrace();}
        return "The SceneSingleton has "
        + " " + themeMusic    // _m:
        + " " + soundFile     // _s:
        + " " + backgroundIMG// _bgImg:
        + " " + imgL          // _imgL:
        + " " + imgR          // _imgR:
        + " " + imgC          // _imgC:
        + " " + text          // _txt:
        + " " + button        // _btn: + ;
        + " currentTuple is " + currentTuple.getTupleString()
        + " and currentLine is " +
                currentLine;
    }





    /* Helper Methods for String Parsing */

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
