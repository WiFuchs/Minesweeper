import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.bezier.guido.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Minesweeper extends PApplet {




public final static int NUM_ROWS = 20;
public final static int NUM_COLS = 20;
public final static int TOTAL_BOMBS = 1;
private MSButton[][] buttons; //2d array of minesweeper buttons
private ArrayList <MSButton> bombs; //ArrayList of just the minesweeper buttons that are mined

public void setup ()
{
    size(400, 400);
    textAlign(CENTER,CENTER);
    
    // make the manager
    Interactive.make( this );
    
    bombs = new ArrayList<MSButton>();
    buttons = new MSButton[NUM_ROWS][NUM_COLS];
    for(int r = 0; r<NUM_ROWS; r++){
        for(int c = 0; c<NUM_COLS; c++){
            buttons[r][c] = new MSButton(r, c);
        }
    }

    //declare and initialize buttons
    setBombs();
}
public void setBombs()
{
    for(int i=0; i<TOTAL_BOMBS; i++){
        int row = (int)(Math.random()*NUM_ROWS);
        int col = (int)(Math.random()*NUM_COLS);
        if(bombs.contains(buttons[row][col]) != true){
            bombs.add(buttons[row][col]);
        }
    }
}

public void draw ()
{
    background( 0 );
    if(isWon())
        displayWinningMessage();
}
public boolean isWon()
{
    for(int i=0; i<TOTAL_BOMBS; i++){
        MSButton bomb = bombs.get(i);
        if(!bomb.isMarked()){
            return false;
        }
    }
    return true;
}
public void displayLosingMessage()
{
    //buttons[][].setMarked();
}
public void displayWinningMessage()
{
    println("Won");
}

public class MSButton
{
    private int r, c;
    private float x,y, width, height;
    private boolean clicked, marked;
    private String label;
    
    public MSButton ( int rr, int cc )
    {
        width = 400/NUM_COLS;
        height = 400/NUM_ROWS;
        r = rr;
        c = cc; 
        x = c*width;
        y = r*height;
        label = "";
        marked = clicked = false;
        Interactive.add( this ); // register it with the manager
    }
    public boolean isMarked()
    {
        return marked;
    }
    public boolean isClicked()
    {
        return clicked;
    }
    public void setMarked(){
        marked = true;
    }
    public void mousePressed () 
    {
        clicked = true;
        if(keyPressed==true){
            if(marked==true){
                marked=false;
                System.out.println(r + ", " + c);
            } else {
                marked=true;
            }
        }
        else if(bombs.contains(this)){
            displayLosingMessage();
        }
        else if(countBombs(r,c)>0){
            setLabel(str(countBombs(r,c)));
        }
        else{
            //Loop through surrounding bombs
            if(isValid(r-1, c) && !(buttons[r-1][c].isClicked())){
                buttons[r-1][c].mousePressed();
            }
            if(isValid(r-1, c-1) && !(buttons[r-1][c-1].isClicked())){
                buttons[r-1][c-1].mousePressed();
            }
            if(isValid(r-1, c+1) && !(buttons[r-1][c+1].isClicked())){
                buttons[r-1][c+1].mousePressed();
            }
            if(isValid(r, c-1) && !(buttons[r][c-1].isClicked())){
                buttons[r][c-1].mousePressed();
            }
            if(isValid(r, c+1) && !(buttons[r][c+1].isClicked())){
                buttons[r][c+1].mousePressed();
            }
            if(isValid(r+1, c-1) && !(buttons[r+1][c-1].isClicked())){
                buttons[r+1][c-1].mousePressed();
            }
            if(isValid(r+1, c) && !(buttons[r+1][c].isClicked())){
                buttons[r+1][c].mousePressed();
            }
            if(isValid(r+1, c+1) && !(buttons[r+1][c+1].isClicked())){
                buttons[r+1][c+1].mousePressed();
            }
        }
    }

    public void draw () 
    {    
        if (marked)
            fill(0);
         else if( clicked && bombs.contains(this) ) 
             fill(255,0,0);
        else if(clicked)
            fill( 200 );
        else 
            fill( 100 );

        rect(x, y, width, height);
        fill((125*PApplet.parseInt(label))%255, 0, 0);
        text(label,x+width/2,y+height/2);
        fill(0);
    }
    public void setLabel(String newLabel)
    {
        label = newLabel;
    } 
    public boolean isValid(int r, int c)
    {
        if(r<20 && r>=0 && c<20 && c>=0){
            return true;
        }
        return false;
    }
    public int countBombs(int row, int col)
    {
        int numBombs = 0;
        if(isValid(row-1, col-1)){
            if(bombs.contains(buttons[row-1][col-1])){numBombs+=1;}
        }
        if(isValid(row-1, col)){
            if(bombs.contains(buttons[row-1][col])){numBombs+=1;}
        }
        if(isValid(row-1, col+1)){
            if(bombs.contains(buttons[row-1][col+1])){numBombs+=1;}
        }
        if(isValid(row, col-1)){
            if(bombs.contains(buttons[row][col-1])){numBombs+=1;}
        }
        if(isValid(row, col+1)){
            if(bombs.contains(buttons[row][col+1])){numBombs+=1;}
        }
        if(isValid(row+1, col-1)){
            if(bombs.contains(buttons[row+1][col-1])){numBombs+=1;}
        }
        if(isValid(row+1, col)){
            if(bombs.contains(buttons[row+1][col])){numBombs+=1;}
        }
        if(isValid(row+1, col+1)){
            if(bombs.contains(buttons[row+1][col+1])){numBombs+=1;}
        }
        return numBombs;
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
