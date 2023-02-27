// Name:wei-chieh, chen
// USC NetID:weichieh
// CS 455 PA3
// Fall 2022

import java.util.Random;

/** 
 *  MineField
 *  class with locations of mines for a game.
 *  This class is mutable, because we sometimes need to change it once it's created.
 *  mutators: populateMineField, resetEmpty
 *  includes convenience method to tell the number of mines adjacent to a location.
 */

public class MineField {
   
  /**
   *   Representation invariant:
   *
   *   <put rep. invar. comment here>
   *   numRows records the number of rows of mine field and must > 0.
   *   numCols records the number of columns of mine field and must > 0.
   *   numMines records the total number of mines of mine field and must > 0.
   */
   
   // <put instance variables here>
   private boolean [][] mineData;
   private int numRows = 0;
   private int numCols = 0;
   private int numMines = 0;
   private int numRam = 0;             //keeps track of number of mines we add in random mine field when calling populateMineField() method
   private Random ran = new Random();     
   
   /**
    *  Create a minefield with same dimensions as the given array, and populate it with the mines in the array
    *  such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
    *  this minefield will corresponds to the number of 'true' values in mineData.
    *
    *  @param mineData  the data for the mines; must have at least one row and one col,
    *                   and must be rectangular (i.e., every row is the same length)
    *
    */
   
   public MineField(boolean[][] mineData) {
                      
      this.numRows = mineData.length;
      this.numCols = mineData[0].length;
      this.mineData = defensiveCopy(mineData); // make a defensive copy from the 2D array passed in.
      this.numMines = getTotalNumMines();      // call private method to check number of mines(iterate and check true counts).
   }
   
   
   /**
    *  Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
    *  populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
    *  numMines() will not correspond to the number of mines currently in the MineField.
    *
    *  @param numRows  number of rows this minefield will have, must be positive
    *  @param numCols  number of columns this minefield will have, must be positive
    *  @param numMines   number of mines this minefield will have,  once we populate it.
    *  PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
                      
      this.numRows = numRows;
      this.numCols = numCols;
      this.numMines = numMines;               //use the parameter numMInes passed in as the "may-have" number of mines.
      this.mineData = createEmptyMineField(); //default set to mine field full of false, can be populated later.     
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col) and numMines() < (1/3 * numRows() * numCols())
    */
   public void populateMineField(int row, int col) {
            
      resetEmpty();
      
      //Avoid putting a mine in the position that it is being clicked on, also avoid a position that already has a mine.
      //Here we use the random generator to give out the position we want to add a mine at.
      while(numRam < numMines()){
         int ranRow = ran.nextInt(numRows());
         int ranCol = ran.nextInt(numCols());
         
         //add a mine in random mine field by random number generated if (1) it is not the cell we are uncovering, and
         //(2) the cell we are going to put a mine does NOT have a mine. Keep counting the number of mines we added and
         //keep add new mine until the number of mines added reach the maximum number of mines we can have.
         if ((ranRow != row && ranCol != col) && (!hasMine(ranRow, ranCol))){ 
            mineData[ranRow][ranCol] = true;
            numRam ++;     
         }
         else{
            continue;
         }      
      }
   }
   
   
   /**
    *   Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
    *   Thus, after this call, the actual number of mines in the minefield does not match numMines().  
    *   Note: This is the state a minefield created with the three-arg constructor is in 
    *         at the beginning of a game.
    */
   public void resetEmpty() {
      
      numRam = 0;
      for(int row = 0; row < numRows(); row++){
         for(int col = 0; col < numCols(); col++){
            mineData[row][col] = false;
         }
      }     
   }

   
  /**
   *  Returns the number of mines adjacent to the specified mine location (not counting a possible 
   *  mine at (row, col) itself).
   *  Diagonals are also considered adjacent, so the return value will be in the range [0,8]
   *
   *  @param row  row of the location to check
   *  @param col  column of the location to check
   *  @return  the number of mines adjacent to the square at (row, col)
   *  PRE: inRange(row, col)
   */
   
   public int numAdjacentMines(int row, int col) {
      
      int adjMines = countAdjacent(row, col);     
      return adjMines;     
   }
   
   
  /**
   *   Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
   *   start from 0.
   *
   *   @param row  row of the location to consider
   *   @param col  column of the location to consider
   *   @return whether (row, col) is a valid field location
   *
   */
   
   public boolean inRange(int row, int col) {
      
      if (row < numRows() && col < numCols() && row > -1  && col > -1){
         return true;
      }
      else{
         return false;
      }      
   }
   
   
  /**
   *   Returns the number of rows in the field.
   *
   *   @return number of rows in the field
   *
   */  
   public int numRows() {
      
      return numRows;      
   }
   
   
  /**
   *   Returns the number of columns in the field.
   *   
   *   @return number of columns in the field
   *
   */    
   public int numCols() {
      
      return numCols;    
   }
   
   
   /**
    *  Returns whether there is a mine in this square.
    * 
    *  @param row  row of the location to check
    *  @param col  column of the location to check
    *  @return whether there is a mine in this square
    *  PRE: inRange(row, col)   
    *
    */    
   public boolean hasMine(int row, int col) {
      
      if (mineData[row][col] == true){
         return true;
      }
      else{
         return false;
      }            
   }
   
   
   /**
    *   Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
    *   some of the time this value does not match the actual number of mines currently on the field.  See doc for that
    *   constructor, resetEmpty, and populateMineField for more details.
    *  
    *   Concepts added: 2 constructor are handled in different way. For fixed mine field: call private method to check number 
    *   of mines(iterate and check true counts) when constructing the object; for random mine field: return the parameter passed
    *   to the constructor.
    *
    *   @return the number of mines in the mine field.
    */
   public int numMines() {
      
      return numMines;
   }
   
  /** 
   *   The only-allower public toString interface to check if every instance variable is iniated correctly.
   *   Can be called inside MineField class or outside(e.g. VisibleField class).
   *
   *   @ return the string that contains info about mine field: numnber of rows, columns, mines
   *
   */
   
   public String toString(){
      
      return "MineField created with numRows: " + numRows() 
         + ", numCols: " + numCols() + ", and numMines(): " + numMines() 
         + System.lineSeparator() + showMine();
   }

   
   // <put private methods here>
   
  /**
   *  Ever time the used left-clicked the cell, the program uncover the the cell. How large the area will be opened
   *  depends on the number of adjacent mines that cell has. This private method iterate the whole area containing 
   *  9 cells and return the number of mines.
   *  
   *  @ param row the number of row that the user is clicking on.
   *  @ param col the number of column that the user is clicking on.
   *  @ return the number of adjacent mines for current cell the user is clicking on.
   *
   */
   
   private int countAdjacent(int row, int col){
      
      int num = 0;
      int maxRow = row + 1;
      int maxCol = col + 1;
      int curRow = row - 1;
      int curCol = col - 1;
      
      //iterate the area which constains 8 cells(include the cell the user is clicking on, but
      //not include the bottom-right cell, we will handle that cell after this try-catch clause.
      //(1)do nothing for current cell being clicked. (2) if the cell we are visiting is true,
      //then increment the number of adjacent mines by 1. (3) if the cell we are visiting is
      //out of bound, do nothing. (4)after each try-cath clause(one cell we checked), move the
      //position that we will visit: in the way of left to right, go to next row if reach the
      //end of the current row.
      while (curRow < maxRow || curCol < maxCol){
         try{    
            if(curRow == row && curCol == col){
               
            }            
            else{
               if (mineData[curRow][curCol] == true){
               num += 1;
               }
            }
         }
         
         catch (ArrayIndexOutOfBoundsException e){
            continue;
         }
         
         finally{
            if (curCol < maxCol){
               curCol += 1;
            }
            else{
               curRow +=1;
               curCol = col - 1;
            }
         }        
      }
      
      //check the bottom-right cell, if it is inRange and is true, increment the number of adjacent mines.
      try{
         if (mineData[curRow][curCol] == true){
         num += 1;
         }
      }
      
      catch (ArrayIndexOutOfBoundsException e){
         num = num;
      }     
      return num;     
   }
   
  /** 
   *  Iterate the 2D boolean array passed in and copy the value of each cell to the new array.
   *
   *  @ param  mineData 2D boolean array which stands for fixed mine field.
   *  @ return the copy of array passed in.
   *
   */
   
   private boolean[][] defensiveCopy(boolean[][] mineData){
      
      boolean[][] defensiveMineData = new boolean[numRows][numCols];
      for (int row = 0; row < numRows(); row++){
         for (int col = 0; col < numCols; col++){
            defensiveMineData[row][col] = mineData[row][col];
         }
      }   
      return defensiveMineData;        
   }
   
  /** 
   *   Used when resetting the random mine field to empty, the mine field will then be populated 
   *   again after clicking the first uncovered cell.   
   *
   *   return the 2D boolean array that all cells are set to false(empty).
   *
   */
   private boolean[][] createEmptyMineField(){
      
      
      boolean[][] emptyMineField = new boolean[numRows()][numCols()];
      for(int row = 0; row < numRows(); row++){
         for(int col = 0; col < numCols(); col++){
            emptyMineField[row][col] = false;
         }
      }
      return emptyMineField;
   }
   
  /**
   *   Used for counting the number of mines in fixed mine field.
   *   Iterate the whole mine field and count the number of cells that are true.
   *
   *   @ return the number of mines in the fixed mine field.  
   *
   */
   
   private int getTotalNumMines(){
      
      //use for-each loop iterate the whole 2D-array mineData, increment numMines by one when seeing true
      //used when facing fixed mine field.
      int numMines = 0;
      for(boolean [] row : mineData){
         for (boolean element : row){
            if (element == true){
               numMines ++;
            }
         } 
      }
      return numMines;    
   }
   
   
   /**
    *   Used for toString only, make it easier for debug.  
    *   Iterate the whole mine field(fixed or random) and show their true or false status.
    *
    */
   private String showMine(){
      String str = "";
      for (int row = 0; row < numRows(); row++){
         for (int col = 0; col < numCols(); col++){
            str = str + mineData[row][col] + ",";
         }
         str = str + System.lineSeparator();
      }
      return str;
   }
          
}

