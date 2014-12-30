package com.example.jj.jla_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

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
    final String SYM_JMP = "_jmp:";
    final String SYM_BTNTEXTMARK = "_^:";
    final String JMP_NUM = "&";
    final String SYM_BTNTEXT = "_";
    final String SYM_NAMETEXTLEFT = "_namel:";
    final String ANIM_BG = "_animBG:";
    final String ANIM_IMGL = "_animIMGL:";
    final String ANIM_IMGR = "_animIMGR:";
    final String ANIM_IMGC = "_animIMGC:";


    BufferedReader bufferPtr;
    private Tuple currentTuple;  // Only get newest
    //private EventHandler; // For events/ moving on
    //private SceneDetails; // To encapsulate all the Extra scene Info, including text speed/

    public String themeMusic;    // _m:
    private String soundFile;     // _s:
    private String backgroundIMG; // _bgImg:
    private String imgL;          // _imgL:
    private String imgR;          // _imgR:
    private String imgC;          // _imgC:
    private String text;          // _txt:
    private boolean btnExists = false;
    private String btnTxt[];
    private String btnAdd[];
    private String bganimation;
    private String imglanimation;
    private String imgranimation;
    private String imgcanimation;

    private String nameTxtLeft;
    private String currentLine;


    private volatile static SceneSingleton uniqueInstance = null;

    public void popSceneSingleton()  // Must have the currentLine
    {
        // Given the proper Ptr at location
        // Take Everything, and leave nothing!
        try {
            System.err.println("*");
            System.err.println("*");
            System.err.println("Starting Tuple is " + this.currentTuple.getTupleString());
            System.err.println("Starting CurrentLine is " + this.currentLine);
            System.err.println("FLAG1");
            currentLine = bufferPtr.readLine(); // Requires space between all segments
            System.err.println("2nd CurrentLine is " + this.currentLine);


            if (currentLine.contains(SYM_TM)) {

                System.err.println("currentline is" + getcurrentLine());
                themeMusic = substringAfter(currentLine, SYM_TM);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(SYM_S)) {
                System.err.println("currentline is" + getcurrentLine());
                soundFile = substringAfter(currentLine, SYM_S);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(SYM_BGIMG)) {
                System.err.println("currentline is" + getcurrentLine());
                backgroundIMG = substringAfter(currentLine, SYM_BGIMG);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(SYM_IMGL)) {
                System.err.println("currentline is" + getcurrentLine());
                imgL = substringAfter(currentLine, SYM_IMGL);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(SYM_IMGR)) {
                System.err.println("currentline is" + getcurrentLine());
                imgR = substringAfter(currentLine, SYM_IMGR);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(SYM_IMGC)) {
                System.err.println("currentline is" + getcurrentLine());
                imgC = substringAfter(currentLine, SYM_IMGC);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(SYM_NAMETEXTLEFT)) {
                System.err.println("currentline is" + getcurrentLine());
                nameTxtLeft = substringAfter(currentLine, SYM_NAMETEXTLEFT);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(ANIM_BG)) {
                System.err.println("currentline is" + getcurrentLine());
                bganimation = substringAfter(currentLine, ANIM_BG);
                System.err.println("current bg is" + this.getBGAnimation());
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(ANIM_IMGL)) {
                System.err.println("currentline is" + getcurrentLine());
                imglanimation = substringAfter(currentLine, ANIM_IMGL);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(ANIM_IMGR)) {
                System.err.println("currentline is" + getcurrentLine());
                imgranimation = substringAfter(currentLine, ANIM_IMGR);
                currentLine = bufferPtr.readLine();
            }
            if (currentLine.contains(ANIM_IMGC)) {
                System.err.println("currentline is" + getcurrentLine());
                imgcanimation = substringAfter(currentLine, ANIM_IMGC);
                currentLine = bufferPtr.readLine();
            }

            if (currentLine.contains(SYM_TXT)) {
                System.err.println("currentline is " + getcurrentLine());
                boolean temp = false;


                text = substringAfter(currentLine, SYM_TXT);
                currentLine = bufferPtr.readLine();
                //System.err.println("Line before Symbol Check Exists!!!!");
                if (currentLine.contains(SYM_BTN)) {
                    System.err.println("currentline is and button is true" + getcurrentLine());
                    temp = true;
                } else { //System.err.println("Middle Current Line is = " + currentLine);
                }
                if (temp) {
                    System.err.println("Button Text Parsing Reached!");
                    setbtnExists(true);
                    // TESTING
                    currentLine = substringAfter(currentLine, SYM_BTN);
                    System.err.println("currentline is " + getcurrentLine());
                    //String temp = substringAfter(currentLine, SYM_BTN);
                    btnAdd = currentLine.split(JMP_NUM); // Gets 3 Address
                    System.err.println("First Add is " + btnAdd[0]);
                    System.err.println("Second Add is " + btnAdd[1]);
                    System.err.println("Third Add is " + btnAdd[2]);

                    // GRAB BUTTON TEXTS
                    // FIRST, SUBSTRING THEM
                    currentLine = bufferPtr.readLine();
                    currentLine = substringAfter(currentLine, SYM_BTNTEXTMARK);
                    System.err.println("currentline is" + getcurrentLine());
                    //temp = substringAfter(currentLine,SYM_BTNTEXT);
                    btnTxt = currentLine.split(SYM_BTNTEXT);
                    System.err.println("First BtnText is " + btnTxt[0]);
                    System.err.println("Second BtnText is " + btnTxt[1]);
                    System.err.println("Third BtnText is " + btnTxt[2]);

                    // RESET POINTERS
                    //currentLine = bufferPtr.readLine(); // In the case of button, need to manually change tuple.
                } else {
                    setbtnExists(false);
                } // CHECK TO MAKE SURE THIS FALLS THROUGH

            }
            if ((currentLine.contains(SYM_JMP)) && (btnExists != true)) {
                System.err.println("currentline is" + getcurrentLine());
                String parsedString = substringAfter(currentLine, JMP_NUM);
                currentTuple.updateTuple(parsedString); // PARSING ----> IPDATETIPLE
                System.err.println("Ending currentLine is " + currentLine);
                //currentLine = bufferPtr.readLine();
            }
            //else if (there are button options)
            else {
                currentTuple.genericUpdate();
                System.err.println("Generic Ending currentLine is " + currentLine);
            }





        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Make sure that the script starts with fluff,


    public static synchronized SceneSingleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SceneSingleton();
        }
        return uniqueInstance;
    }

    public void pointToNext() //Points Buffr Ptr to correct line, given
    {

        // While the next line has not been reached
        try {

            while (!(currentLine.contains(
                    "$$" + currentTuple.getTupleString()))) {
                currentLine = bufferPtr.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public void connectBufferedReader(BufferedReader bPtr)

    {
        bufferPtr = bPtr;
        try {
            currentLine = bufferPtr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //catch(NullPointerException e)
        //{e.printStackTrace();}


    }

    public void wipeOtherInfo() {

    }


    public String toString() {
        //String temp = "not your stuff";
        // try {temp = bufferPtr.readLine();} catch(IOException e) { e.printStackTrace();}


        String scene =
                "The SceneSingleton has "
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

      /*
        if (btnExists)

        {
            scene = scene + " the buttons are: " + btnTxt[0] + " " +btnTxt[1];
        }


        */
        return scene;
    }


    /*
     * Getter/SetterMethods
     */


    public String getthemeMusic() {
        return this.themeMusic;
    }

    public String getSoundFile() {
        return this.soundFile;
    }

    public String getbackgroundImg() {
        return this.backgroundIMG;
    }

    public String getimgL() {
        return this.imgL;
    }

    public String getimgR() {
        return this.imgR;
    }

    public String getimgC() {
        return this.imgC;
    }

    public String gettext() {
        return this.text;
    }

    public String getbtnAdd(int position) // starts from 0
    {
        return btnAdd[position];
    }

    public String getbtnTxt(int position) // starts from 0
    {
        return btnTxt[position];
    }

    public boolean getbtnExists() {
        return this.btnExists;
    }

    public String getcurrentLine() {
        return this.currentLine;
    }

    public Tuple getcurrentTuple() {
        return this.currentTuple;
    }
    public String getNameLeft(){return this.nameTxtLeft;}

    public String getBGAnimation() {
        return this.bganimation;
    }
    public String getIMGLAnimation() {
        return this.imglanimation;
    }
    public String getIMGRAnimation() {
        return this.imgranimation;
    }
    public String getIMGCAnimation() {
        return this.imgcanimation;
    }


    public void setcurrentTuple(Tuple newTuple) {
        currentTuple = newTuple;
    }

    public void setbtnExists(boolean b) {
        btnExists = b;
    }
    public void setIMGC(String center)
    {
        this.imgC = center;
    }
    public void setBGAnimation(String input){bganimation = input;}
    public void setIMGLAnimation(String input){imglanimation = input;}
    public void setIMGRAnimation(String input){imgranimation = input;}
    public void setIMGCAnimation(String input){imgcanimation = input;}
    public void setNameLeft(String input){this.nameTxtLeft = input;}
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
