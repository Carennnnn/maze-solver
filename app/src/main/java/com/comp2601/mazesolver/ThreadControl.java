package com.comp2601.mazesolver;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ThreadControl extends Thread{

    //buttons from orininal maze
    private Button[][] mOriginalButtons;
    private Maze mMaze;
    private boolean mHasPath;
    private ArrayList<Button> mRouteButtonArrayList;
    private boolean mIsComputing;

    //constructor for ThreadControl
    public ThreadControl(Maze maze, Button[][] buttons){
        this.mMaze = maze;
        this.mOriginalButtons = buttons;
        this.mIsComputing = false;
        this.mHasPath = false;
    }

    public void run(){

        mIsComputing = true;

        //while it is computing, keep finding the path
        while(mIsComputing){
            //boolean variable represent if it has path
            mHasPath = mMaze.findPath();
            if(mHasPath){

                //get final buttons arraylist on route
                mRouteButtonArrayList = mMaze.getmRouteButtonArrayList();

                //for each button on the route, set text and color of the button, and sleep for 80 milliseconds
                for(int i = mRouteButtonArrayList.size()-1; i >= 0; i--){
                    //if it is the last button on the route, set isComputing to false
                    if(i == 0){
                        mIsComputing = false;
                    }
                    mRouteButtonArrayList.get(i).setBackgroundColor(Color.parseColor("#00ccff"));
                    mRouteButtonArrayList.get(i).setText(R.string.path);
                    try{
                        Thread.sleep(80);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //set buttons to clickable or not by boolean variable
    public void setBtnEnabled(boolean b){
        for(int r = 0; r < mMaze.getmNumRow(); r++){
            for(int c = 0; c < mMaze.getmNumCol(); c++){
                mOriginalButtons[r][c].setEnabled(b);
            }
        }
    }

}
