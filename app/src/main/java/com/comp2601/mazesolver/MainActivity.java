package com.comp2601.mazesolver;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

//For R1.10, to change the number of cells in a row,
//simply change the final int NUM_COLS to any number you want
public class MainActivity extends AppCompatActivity {

    // Scale of the game (number of rows and columns)
    private  static final int NUM_ROWS = 15;

    //R1.10, change the number assigned by NUM_COLS
    private  static final int NUM_COLS = 10;

    // Initial Start and Destination locations
    private  static final int INITIAL_START_ROW = 0;
    private  static final int INITIAL_START_COL = 0;
    private  static final int INITIAL_DESTINATION_ROW = NUM_ROWS - 1;
    private  static final int INITIAL_DESTINATION_COL = NUM_COLS - 1;

    //final Start and Destination locations
    private int startRow = 0;
    private int startCol = 0;
    private int destinationRow = NUM_ROWS - 1;
    private int destinationCol = NUM_COLS - 1;

    //set boolean variable to represent if the button is going to be start or destination
    private Boolean isStart = false;
    private Boolean isDestination = false;

    //button that represents the solve button
    private static Button solveMazeButton;

    //private Maze model;
    private Maze maze;

    //thread of ThreadControl
    private ThreadControl mThread;

    //all buttons of the maze
    private Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding buttons with UI Threads
        TableLayout gameLayout = findViewById(R.id.gameTable);
        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(MainActivity.this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            gameLayout.addView(tableRow);
            for (int col = 0; col < NUM_COLS; col++){
                //set to final in order to access it later on
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                final Button button = new Button(MainActivity.this);

                //set onclicklistener for each single button
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button b = (Button) v;
                        //if the cell is empty, change it to wall
                        if(b.getText().equals("E")){
                            //if start button is toggled off, change cell to start
                            if(isStart){
                                b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.start));
                                b.setText(R.string.start);
                                startRow = FINAL_ROW;
                                startCol = FINAL_COL;
                                isStart = false;
                            }
                            //if destination button is toggled off, change cell to destination
                            else if(isDestination){
                                b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.destination));
                                b.setText(R.string.destination);
                                destinationRow = FINAL_ROW;
                                destinationCol = FINAL_COL;
                                isDestination = false;
                            }
                            //if button is empty, change it to wall
                            else{
                                b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.wall));
                                b.setText(R.string.wall);
                            }
                        }
                        //if the button is wall, change it to empty
                        else if(b.getText().equals("W")){
                            //if start button is toggled off, change cell to start
                            if(isStart){
                                b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.start));
                                b.setText(R.string.start);
                                isStart = false;
                            }
                            //if destination button is toggled off, change cell to destination
                            else if(isDestination){
                                b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.destination));
                                b.setText(R.string.destination);
                                isDestination = false;
                            }
                            //if the button is wall, change it to empty
                            else{
                                b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.empty));
                                b.setText(R.string.empty);
                            }
                        }
                        //if the cell is start, change it to empty
                        else if(b.getText().equals("S")){
                            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.empty));
                            b.setText(R.string.empty);
                            isStart = true;
                        }
                        //if the cell is destination, change it to empty
                        else if(b.getText().equals("D")){
                            b.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.empty));
                            b.setText(R.string.empty);
                            isDestination = true;
                        }
                    }

                });

                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f
                );

                params.setMargins(1,1,1,1);
                button.setLayoutParams(params);
                button.setPadding(2,2,2,2);
                button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.empty));
                button.setText(R.string.empty);

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }

        //set start button
        buttons[INITIAL_START_ROW][INITIAL_START_COL].setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.start));
        buttons[INITIAL_START_ROW][INITIAL_START_COL].setText(R.string.start);

        //set destination button
        buttons[INITIAL_DESTINATION_ROW][INITIAL_DESTINATION_COL].setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.destination));
        buttons[INITIAL_DESTINATION_ROW][INITIAL_DESTINATION_COL].setText(R.string.destination);

        //handle solve button
        solveMazeButton = (Button) findViewById(R.id.button_solve_maze);
        solveMazeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //create new maze
                maze = new Maze(buttons, NUM_ROWS, NUM_COLS);
                //create new thread
                mThread = new ThreadControl(maze, buttons);
                mThread.start();
                //set all buttons to disabled when computing
                mThread.setBtnEnabled(false);
            }
        });
    }

}
