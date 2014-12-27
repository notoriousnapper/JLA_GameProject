package com.example.jj.jla_project;

/**
 * Created by jj on 12/22/14.
 */
public class Tuple {

   // Starting Fields on StartPoint
   private int chapter = 1;
   private int scene = 1;
   private int path = 1;
   private volatile static Tuple uniqueInstance = null;
   private Tuple()   {
   }

   public static synchronized Tuple getInstance()
   {
       if (uniqueInstance == null)
        {
            uniqueInstance = new Tuple();
        }
        return uniqueInstance;
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode()
    {
        int start = 0;
        return this.getTupleInt();

    }

   public Tuple updateTuple (int newchap, int newscene, int newpath)
   {

       chapter = newchap;
       scene = newscene;
       path = newpath;
       return this;
   }

    public Tuple updateTuple (String newTupleString)
    {
        if(newTupleString.length()!=5) {
            System.err.println("ERROR IN UPDATING TUPLE, LENGTH OF STRING INPUT NOT ENOUGH");

        }
        else
        {
            String temp[] = newTupleString.split(",");
            int tempInts[] = {
                        Integer.parseInt(temp[0]),
                        Integer.parseInt(temp[1]),
                        Integer.parseInt(temp[2]),

            };
        chapter = tempInts[0];
        scene = tempInts[1];
        path = tempInts[2];

        }

        return this;
    }

   public String getTupleString ()
   {
       return "" + chapter +","+ scene + "," + path;
   }
    public int getTupleInt(){           // THIS ONLY RETURNS SCENE AND PATH, WHERE PATH WILL < 10

        int tens = this.scene*10;
        int ones = this.path*1;

        return tens + ones;

    }

   // Normal Update without args = Increment +1
    public void genericUpdate()
    {
        scene = scene + 1;
    }
    // Normal Update without args = For Buttons/ Stuff
    /*
    public void jumpUpdate(int sceneIncrement, int pathIncrement)
    {
        scene = scene + sceneIncrement;
        path = path + pathIncrement;
    }
    */


}
