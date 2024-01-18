package com.comp2601.mazesolver;


import android.widget.Button;
import java.util.ArrayList;

public class Maze {

    //buttons from maze
    private final Button[][] mOriginalButtons;
    //2d array of unvisited buttons
    private final boolean[][] mUnVisitedButtons;
    //arraylist of buttons found on the final route
    private final ArrayList<Button> mRouteButtonArrayList;
    private int mStartRow;
    private int mStartCol;
    private int mDestinationRow;
    private int mDestinationCol;
    private final int mNumRow;
    private final int mNumCol;

    //constructor of maze
    public Maze(Button[][] buttons, int numRow, int numCol){
        mOriginalButtons = buttons;
        mUnVisitedButtons = new boolean[numRow][numCol];
        mRouteButtonArrayList = new ArrayList<Button>();
        mNumRow = numRow;
        mNumCol = numCol;
        setmUnVisitedButtons();
    }

    //set initial unvisited buttons that don't include walls
    public void setmUnVisitedButtons(){
        //create a 2D array of unvisited buttons, including walls that cannot be visited
        for(int row = 0; row < mNumRow; row++){
            for(int col = 0; col < mNumCol; col++){
                //find start row and start column
                if(mOriginalButtons[row][col].getText().equals("S")){
                    mStartRow = row;
                    mStartCol = col;
                    mUnVisitedButtons[row][col] = true;
                }
                //find destination row and destination column
                else if(mOriginalButtons[row][col].getText().equals("D")){
                    mDestinationRow = row;
                    mDestinationCol = col;
                    mUnVisitedButtons[row][col] = true;
                }
                //if button is wall, set unvisited to false
                else if(mOriginalButtons[row][col].getText().equals("W")){
                    mUnVisitedButtons[row][col] = false;
                }
                //set empty buttons to unvisited
                else{
                    mUnVisitedButtons[row][col] = true;
                }

            }
        }
    }


    //find path from start to destination
    public boolean findPath(){
        return findPathFrom(mStartRow, mStartCol);
    }

    //find path from current coordinate
    public boolean findPathFrom(int r, int c){

        //the button is visited, set unvisited to false
        mUnVisitedButtons[r][c] = false;

        //base case: if button is destination button, path is found, return true
        if(r == mDestinationRow && c == mDestinationCol){
            mRouteButtonArrayList.add(mOriginalButtons[r][c]);
            return true;
        }

        //recursion
        //find next neighbour button that is unvisited
        int[] nextCoordinate = getUnvisitedButtonFrom(r, c);

        //while it has next button, find the path from next button
        while(nextCoordinate != null){
            //if path can be found on the next button, add the button to routeButtonArrayList
            if(findPathFrom(nextCoordinate[0], nextCoordinate[1])){
                mRouteButtonArrayList.add(mOriginalButtons[nextCoordinate[0]][nextCoordinate[1]]);
                return true;
            }
            //if path cannot be found on the next button, find another neighbour button instead
            nextCoordinate = getUnvisitedButtonFrom(r, c);
        }

        return false;

    }

    //return row and column number of unvisited button, null if none
    public int[] getUnvisitedButtonFrom(int r, int c){
        int[] newCoordinate = new int[2];

        //check if upper button (r-1, c) is empty and unvisited
        if(r-1>=0 && mUnVisitedButtons[r-1][c]){
            newCoordinate[0] = r-1;
            newCoordinate[1] = c;
            return newCoordinate;
        }

        //check if left button (r, c-1) is empty and unvisited
        if(c-1>=0 && mUnVisitedButtons[r][c-1]){
            newCoordinate[0] = r;
            newCoordinate[1] = c-1;
            return newCoordinate;
        }

        //check if bottom button (r+1, c) is empty and unvisited
        if(r+1<mNumRow && mUnVisitedButtons[r+1][c]){
            newCoordinate[0] = r+1;
            newCoordinate[1] = c;
            return newCoordinate;
        }

        //check if right button (r, c+1) is empty and unvisited
        if(c+1<mNumCol && mUnVisitedButtons[r][c+1]){
            newCoordinate[0] = r;
            newCoordinate[1] = c+1;
            return newCoordinate;
        }
        return null;
    }

    public ArrayList<Button> getmRouteButtonArrayList(){
        return mRouteButtonArrayList;
    }

    public int getmNumRow(){
        return mNumRow;
    }

    public int getmNumCol(){
        return mNumCol;
    }

}
