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

   public Tuple updateTuple (int newchap, int newscene, int newpath)
   {
       chapter = newchap;
       scene = newscene;
       path = newpath;
       return this;
   }

   public String getTupleString ()
   {
       return "" + chapter +","+ scene + "," + path;
   }


   // Normal Update without args = Increment +1
    public void genericUpdate()
    {
        scene = scene + 1;
    }
    // Normal Update without args = For Buttons/ Stuff
    public void jumpUpdate(int sceneIncrement, int pathIncrement)
    {
        scene = scene + sceneIncrement;
        path = path + pathIncrement;
    }


}
