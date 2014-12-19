package com.example.jj.jla_project;

/**
 * Created by admin on 11/22/14.
 */


    import android.content.Context;
    import android.content.res.AssetManager;

    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.net.URL;
    import java.util.Arrays;
    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.util.concurrent.TimeUnit;
    import java.util.Scanner;

    public class SingletonTest
    {

   /*
    * Singleton -> loop
    * loop does populateS first
    * takes reads the next node out of script.txt
    * puts current node in a 'glass'
    * readS takes the glass, line by line
    * jumps to new node if called
    */
        //public BufferedReader buffr;
        public FileReader filer;
        public String current;



        // TESTING
        public String background = "test";
        // TESTING
        public String filepath;

        public String[] decAddress = new String[3];
        public String[] dec;
        public String[] decStrings;

        public String userText = "usertext";
        public boolean tappable;
        public boolean decision;
        public boolean jump;

        public int ARRAY_SIZE = 100;
        public int CHOICES = 3;

        public int TEXT_DELAY = 50;

        public String currentTuple;
        public String nextTuple;
        //public String[] instructions;
        public String imgName = "apollo1";
        public String musicName;


        private volatile static SingletonTest uniqueInstance = null;



        private SingletonTest()   {
        }

        public String getuserText() {

            return userText;
        }

        public static synchronized SingletonTest getInstance()
        {
            if (uniqueInstance == null)
            {
                uniqueInstance = new SingletonTest();

            }
            return uniqueInstance;
        }


/*
        public void firstMethod()
        {
            //URL url = getClass().getResource("script.txt");
            filepath = "./properties/files/script.txt";
                    //url.getPath();
                    //"./lib/script.txt";
            try {
                //filer = new FileReader( filepath );
                buffr = new BufferedReader( new InputStreamReader(iS) );
                currentLine = buffr.readLine();

                //TEsting
                musicName = currentLine;
                //Testing
            } catch ( Exception e ) { e.printStackTrace(); }
            //loop();
        }
        */
/*
        public void loop()
        {
            try {
                currentLine = buffr.readLine();
                while ( currentLine != null )
                {
                    populateS();
                    readS();
             */
public void Jump (BufferedReader buffr, Boolean jumpBool, String currentL){
                    if ( jump )
                    {
                        while ( !currentL.contains( nextTuple ) || (currentL.contains( "JUMP" )))
                        {
                            try {
                                     currentL = buffr.readLine();
                                if ( currentL == null )

                                {
                                    System.out.println( "nextTuple not found" );
                                    System.exit(2);
                                }
                            } catch ( Exception e ) { e.printStackTrace(); }
                        }

                        currentTuple = currentL;
                        jump = false;
                    }
                }

           /*     //

            } catch ( Exception e ) { e.printStackTrace(); }
        }
*/
        public String[] populateS(BufferedReader buffr, String current)
        {
            tappable = true;
            decision = false;
            jump = false;


            while( current.contains( "#" ) )
            {
                try {
                    current = buffr.readLine();
                } catch ( Exception e ) { e.printStackTrace(); }
            }

            if ( !current.contains( "$$" ) )
            {
                System.out.println( "Error: No node selected on populateS" );
            }
            else
            {
                currentTuple = current.replaceAll( "$", "" );
                try {
                    current = buffr.readLine();

                    System.out.println("Before ");
                    if ( current.contains( "notap" ) )
                    {
                        tappable = false;
                        current = buffr.readLine();
                        System.out.println("Before ");
                    }
                    if ( current.contains( "IMG" ) )
                    {
                        String [] tokens = current.split( "&" );
                        imgName = tokens[1];
                        current = buffr.readLine();
                    }
                    if ( current.contains( "MUS" ) )
                    {
                        String[] tokens = current.split( "&" );
                        musicName = tokens[1];
                        current = buffr.readLine();
                    }
                } catch ( Exception e ) { e.printStackTrace(); }
            }

            String instructions[] = new String[ARRAY_SIZE];
            int counter = 0;
            while ( ( current != null ) && ( !current.contains( "$$" ) ) )
            {
                instructions[counter] = current;

                counter++;

                if ( counter > ARRAY_SIZE - 2 )
                {
                    String[] tmp = new String[ ARRAY_SIZE*2 ];
                    for ( int i = 0; i < ARRAY_SIZE; i++ )
                    {
                        tmp[i] = instructions[i];
                    }
                    instructions = tmp;
                }
                try {
                    current = buffr.readLine();

                    currentTuple = current;

                } catch ( Exception e ) { e.printStackTrace(); }

            }
                return instructions;

        }

        public void decision( String[] t ) //needs to be 1,2, or 3
        {
            int i = t.length;
            int counter = 0;
            String [] dec2 = new String[3];
            decAddress = new String[3];
            System.out.println("");
            while (counter < 6 ) {

                if ( counter == 0 || counter == 2 || counter == 4) {
                    System.out.println( t[counter] );
                    dec2[(counter/2)] = t[counter];
                }
                else if ( counter < 6 )
                {
                    int ind = (counter-1)/2;
                    decAddress[ind] = t[counter];
                }
                counter++;

            }
            for (String y : decAddress)
            {
                System.out.println( y );
            }
            decStrings = dec2;
        }


        public String waitingClick( String t, BufferedReader buffr ) {


            nextTuple = t;
            if ( !nextTuple.contains( "," ) )
            {
                jump = false;
            }
            else
            {
                jump = true;
            }
            return nextTuple; //will replace current tuple IF Decision == True
        }
        public boolean getDecision()
        {
            return decision;
        }
        public String[] getDec()
        {
            return dec;
        }
        public String[] getDecStrings()
        {
            return decStrings;
        }
        public String[] getDecAddress()
        {
            return decAddress;
        }
        public void readS(String instructions[] )
        {
            int counter = 0;
            while ( instructions[counter] != null )
            {
                String tmp = instructions[counter];
                String[] tokens = tmp.split( "&" );

                if ( tokens[ 0 ].contains( "#" ) )
                {
                }
                else if ( tokens[ 0 ].contains( "Decision" ) )  // If Decision Marker Exists
                {
                    //typewriter ( tokens[ 1 ], TEXT_DELAY );
                    counter++;
                    decision = true;

                    dec = instructions[counter].split( "&" );
                    //int i = dec.length;
                    //decision( tokens); // DECISIONS IS HERE
                    // QUICK FIXED FIX LATER
                }
                else if ( tokens[ 0 ].contains( "JUMP" ) ) // Some jump from end of screen
                {
                    nextTuple = tokens[ 1 ]; // For sure will just JUMP to this coordinate
                    jump = true;
                }
                else {
                    userText = tmp;

                }
                /*
                else
                {
                    for ( String s : tokens )
                    {
                        typewriter( s, TEXT_DELAY );
                    }
                }
                */
                counter++;
            }

        }

        public void typewriter( String s, int delay )
        {
            System.out.println("");
            char [] sarray = s.toCharArray();
            for ( int i = 0; i < s.length(); i++ )
            {
                System.out.print( sarray[ i ] );
                try {
                    TimeUnit.MILLISECONDS.sleep( delay );
                } catch ( Exception e ) { e.printStackTrace(); }
            }
            System.out.println("");
        }

        public String getBackground()
        {
            if (background == null)
              background = "new";
            return background;
        }

        public String getCurrent()
        {
            if (currentTuple == null)
                currentTuple= "1,2,1";
            return currentTuple;
        }

        public boolean getTappable()
    {
        if (tappable == true)
        {
           return true;
        }
            return false;

    }

        public void setCurrentTuple( String s )
        {
           currentTuple = s;
        }
        public String getMusicName()
        {
            if (musicName == null)
            {musicName = "null you dolt";}
            return musicName;
        }
        public String getImageName()
        {
            if (imgName == null)
            {imgName = "null you dolt";}
            return imgName;
        }
        public String getCurrentTuple()
        {
            return currentTuple;
        }
        public Boolean getJump()
        {
            return jump;
        }
        public String getNextTuple()
        {
            return nextTuple;
        }


    }


