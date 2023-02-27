/**
 *  VisibleField class
 *  This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
 *  user can see about the minefield). Client can call getStatus(row, col) for any square.
 *  It actually has data about the whole current state of the game, including  
 *  the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
 *  It also has mutators related to actions the player could do (resetGameDisplay(), cycleGuess(), uncover()),
 *  and changes the game state accordingly.
 * 
 *  It, along with the MineField (accessible in mineField instance variable), forms
 *  the Model for the game application, whereas GameBoardPanel is the View and Controller in the MVC design pattern.
 *  It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from 
 *  outside this class via the getMineField accessor.  
 */

public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method 
   // getStatus(row, col)).
   
   // The following are the covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // The following are the uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this opened square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------   
  
   
   /**
    *  Representation invariant:
    *
    *  <put rep. invar. comment here>
    *   totalNumMines records the total number of mines of mine field and must > 0.
    *
    */
   
   
   // <put instance variables here>
   private MineField mineField;
   private int totalNumMines = 0;
   private int numMinesGuessed = 0;
   private int [][] status; 
   private int numNotMineOpened = 0; //for keep tracking of the number of non-min SquareViews we've opend.
   private boolean gameOver = false;


   /**
    *  Create a visible field that has the given underlying mineField.
    *  The initial state will have all the locations covered, no mines guessed, and the game
    *  not over.
    *
    *  @param mineField  the minefield to use for for this VisibleField
    *
    */
   public VisibleField(MineField mineField) {
      
      this.mineField = mineField;
      //the "may-have" number of mines of fixed field: call getTotalNumMines() to check the true counts; 
      //random field: used the parameter passed in directly. Use this parameter to keep track of the number
      //of mines left(by our guessing). May not represent the real mines left.
      this.totalNumMines = mineField.numMines();
      this.numMinesGuessed = numMinesGuessed;
      //create a 2D array to store status for each cell of mineField, all initialized to COVERED.
      this.status = new int [mineField.numRows()][mineField.numCols()];      
      statusInitialization(status);
      //gameOver is set to false as default, it will be changed accordingly every time uncover later.
      this.gameOver = gameOver;
      this.numNotMineOpened = numNotMineOpened;
      
   }
   
   
   /**
    *   Reset the object to its initial state (see constructor comments), using the same underlying
    *   MineField. 
    *   Procedure:(1)set number of mine we've guessed to 0. (2) set the number of non-mine cell we've opened to 0.
    *   (3)set the 2D int array status to all COVERED. (4)reset the mine field: random, set all cell to false;
    *   fixed, do nothing(handled in MineField class). (5) set gameOver flag to false.
    *
    */     
   public void resetGameDisplay() {
      
      numMinesGuessed = 0;
      numNotMineOpened = 0;
      statusInitialization(status);
      mineField.resetEmpty();        //if fixed mine field, do nothing.
      gameOver = false;      
   }
  
   
   /**
    *  Returns a reference to the mineField that this VisibleField "covers".
    *
    *  @return the minefield.
    *
    */
   public MineField getMineField() {
      
      return mineField;            
   }
   
   
   /**
    *   Returns the visible status of the square indicated.
    *   
    *   @param row  row of the square
    *   @param col  col of the square
    *   @return the status of the square at location (row, col).  See the public constants at the beginning of the class
    *   for the possible values that may be returned, and their meanings.
    *   PRE: getMineField().inRange(row, col)
    *
    */
   public int getStatus(int row, int col) {
      
      return status[row][col];  
   }

   
   /**
    *   Returns the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
    *   or not.  Just gives the user an indication of how many more mines the user might want to guess.  This value will
    *   be negative if they have guessed more than the number of mines in the minefield.     
    *
    *   @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      
      int numMinesLeft = totalNumMines - numMinesGuessed;
      return numMinesLeft;       

   }
 
   
   /**
    *   Every time we guess(mouse right-clicked and call changeGuessStatus() of the inner calss SquareListener in the GameBoardPanel,
    *   it will call this method to update the current SquareView and the numMinesLeft.
    *   Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
    *   changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
    *   changes it to COVERED again; call on an uncovered square has no effect.  
    *   @param row  row of the square
    *   @param col  col of the square
    *   PRE: getMineField().inRange(row, col)
    *
    */
   public void cycleGuess(int row, int col) {
      
      int status = getStatus(row, col);
      
      //If previous state is COVERED, then change it to MINE_GUESS and increment numMinesGuessed by 1.           
      if (status == COVERED){
         this.status[row][col] = MINE_GUESS;
         numMinesGuessed++;
      }
      
      //If previous state is MINE_GUESS, then change it to QUESTION and decrement numMinesGuessed by 1.
      else if (status == MINE_GUESS){
         this.status[row][col] = QUESTION;
         numMinesGuessed--;
      }
      
      //If previous state is QUESTION, then change it to COVERED.
      else if (status == QUESTION){
         this.status[row][col] = COVERED; 
      }
      
      //Do nothing if current cell in uncovered(positive number).
      else if (isUncovered(row,col)){
         return;
      }
            
   }

   
   /**
    *  Uncovers this square and returns false iff you uncover a mine here.
    *  If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in 
    *  the neighboring area that are also not next to any mines, possibly uncovering a large region.
    *  Any mine-adjacent squares you reach will also be uncovered, and form 
    *  (possibly along with parts of the edge of the whole field) the boundary of this region.
    *  Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
    *  Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
    *  or a loss (opened a mine).
    *  
    *  Supplementary concept: uncover method in my design ensures that, if the SquareView being opened is a mine,
    *  then game is over, update every SquareView of the board(correct guess, incorrect guess...), return false directly,
    *  which means it is a mine. If it is not a mine, then call the private method openMine in a recursive way, 
    *  then reutrn true. Whether the game is over(all the non-mine SquareViews are opened) or not will be handled in the
    *  openMine method.
    *  
    *  @param row  of the square
    *  @param col  of the square
    *  @return false   iff you uncover a mine at (row, col)
    *  PRE: getMineField().inRange(row, col)
    *
    */
   public boolean uncover(int row, int col) {
      
      //If the cell being clicked on is a mine, update the status of it to EXPLODED_MINE.
      //Then set gameOver flag to true, upate the 2D int array status for showing the final results.
      //Finally return false, so the gameStatusLable in the BoardGamePanel class will adjusted accordingly(You lost!).
      if(mineField.hasMine(row, col)){
            status[row][col] = EXPLODED_MINE;
            gameOver = true;
            updateAllVisibleField();
            return false;        
      }
      
      //If the cell being clicked is not a mine, call openMine method in a recursive way.
      //Then return true, so the gameStatusLable in the BoardGamePanel class will adjusted accordingly(You won! if game is over).
      else{
         openMine(row, col);
         return true; 
      }     
      
   }
 
   
   /**
    *  Returns whether the game is over.
    *  (Note: This is not a mutator.)
    *
    *  @return whether game has ended
    *
    */
   public boolean isGameOver() {
      
      //This flag will be updated(default false) when calling uncover method.
      return gameOver;
             
   }
 
   
   /**
    *  Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
    *  vs. any one of the covered states). Used when cycleGuess(right click) or uncover(left click).
    *
    *  @param row of the square
    *  @param col of the square
    *  @return whether the square is uncovered
    *  PRE: getMineField().inRange(row, col)
    *
    */
   public boolean isUncovered(int row, int col) {
      
      int status = getStatus(row, col);
      //It is covered either it is COVERED, MINI_GUESS, QUESTION. Uncovered, i.e., including 
      //open an SquareView that is not a mine and denoting the number of mines adjacent.
      if (status == COVERED || status == MINE_GUESS || status == QUESTION){
         return false;
      }
      else{
         return true;       
      }            
   }
   
 
   // <put private methods here>
   
   /**
    *  A 2D array representing current status of each SquareView of VisibleField and is set to COVERED in default.
    *  Iterate the whole 2D array and update the whole array to be COVERED.
    *  Used when restting the game to initial state or constructing the VisibleField.
    * 
    *  @param status 2D array representing current status of each SquareView of VisibleField.
    * 
    */
   
   private void statusInitialization(int[][] status){
   
      for(int row = 0; row < status.length; row++ ){
         for ( int col = 0; col < status[0].length; col++ ){
           status[row][col] = COVERED;
         }
      }
   }
   
   /**
    *  Iterate the whole 2D array status to update the current status of each SquareView of VisibleField.
    *  The one marked as MINE_GUESS, but not a mine will be changed to INCORRECT_GUESS
    *  The one marked as QUESTION, and it IS a mine in the end, will be changed to MINE for later display.
    *  The one marked as QUESTION, but it is NOT a mine actually, we will do nothing here.
    *  The one not being opened, but it is actually a mine, will be changed to MINE.
    *  Used when losing a game.
    *  
    *  PRE: MUST be used in a lost game.
    */
   
   private void updateAllVisibleField() {
      
      for (int row = 0; row < status.length; row++) {
         for (int col = 0; col < status[0].length; col++) {
            
            //Mark as MINE_GUESS but actually not a mine, change its status to INCORRECT_GUESS.
            //Later when game is over, will call updateAllSquaresViews to show the final status.
            if (status[row][col] == MINE_GUESS){
               if (!mineField.hasMine(row, col)){
                  status[row][col] = INCORRECT_GUESS;
               } 
            } 
            
            //When losing, cells that are marked QUESTION but are mines will be changed to MINE.
            else if(status[row][col] == QUESTION && mineField.hasMine(row, col)){
               status[row][col] = MINE;
            }
            
            //WHen losing, cells that are still covered will be changed to MINE
            else if (status[row][col] == COVERED){
               if (mineField.hasMine(row, col)){
                  status[row][col] = MINE;
               }
               
            }
         }
      }
      
   }
   
   /**
    *  Similar version with updateAllVisible, triumph here stands for won the game.
    *  Hence the way to show SquareViews that has a mine will differ from the way of updateAllVisible.
    *  Here, the SquareViews that has a mine beneath it and being either marked as QUESTION or uncovered
    *  will be changed to status of MINE_GUESS(yellow color).
    *  Used when winning the game.
    * 
    *  PRE:MUST be used in a won game.
    */
   
   private void updateTriumphAllVisibleField() {
      
      for (int row = 0; row < status.length; row++) {
         for (int col = 0; col < status[0].length; col++) {
            
            if(status[row][col] == QUESTION && mineField.hasMine(row, col)){
               status[row][col] = MINE_GUESS;
            }
            
            //When winning the game, the cells that are marked as COVERED will be changed to MINE_GUESS.
            else if (status[row][col] == COVERED){
               if (mineField.hasMine(row, col)){
                  status[row][col] = MINE_GUESS;
               }              
            }
         }
      }
      
   }  
      
   /**
    *  Used when recursively uncover the cells. Check the current cell being uncovered if has adjacent mines.
    *  If so, stop uncover. Otherwise, keep uncover cells recursively.
    *
    *  @ param row the row of current SquareView being checked.
    *  @ param col the column of current SquareView being checked.
    *  @ return whether current SquareView has mine or not.
    */ 
   
   
   private boolean hasAdjacent(int row, int col){
      
      if(mineField.numAdjacentMines(row, col) > 0){
         return true; 
      }
      else{ return false;}
   }
   
   /**
    *  This method recursively open current and neighboring SquareViews until it reaches the SquareView that 
    *  has adjacent mines. It will also keep track of the number of opened non-mine SquareViews, and if all
    *  the non-mine SquareViews are opened then the game is over.
    *
    *  @ param row the row of SquareView being opened.
    *  @ param col the column of SquareView being opened.
    *  PRE: The SquareView being opened can't be a mine, this situation is handled in the uncover() method in advance.
    */
   
   private void openMine(int row, int col){      

      int numSquares = mineField.numRows() * mineField.numCols();
      
      //Every time visit current SquareView, check if it is in range or is covered, if not, return directly.
      //Also return directly if current SquareView visited is marked as MINE_GUES(when doing recursion).
      //If it is already uncovered, so we don't need to imcrement numNotMineOpened. 
      //We won't reach game over condiditon if we don't keep track of the numNotMineOpened correctly otherwise.
      if (!mineField.inRange(row, col) || isUncovered(row, col) || getStatus(row, col) == MINE_GUESS){
         return;
      }
      
      int numAdj = mineField.numAdjacentMines(row, col);
      
      //Every time visit current SquareView that is in range, check the number of adjacent mines first.
      //Then store it into status, and increment numNotMineOpened by 1.
      //There are 2 reasons:
      //First, next time we visit this already uncovered SquareView when triggered by neighboring SquareView, 
      //the recursion will stop at first if statement(line 398).
      //The other one is that we must always know the number of adjacent mines of current SquareView, so we 
      //can check if it has adjacent mines in the next if statement(line 419) to prevent the program from infinite recursion.
      status[row][col] = numAdj;
      numNotMineOpened++;
      
      //If current SquareView has adjacent mines, then stop uncover neighboring SquareViews.
      //It not until we reach the boudary of cells having adjacent mines, the game board panel update won't update it's 
      //view(all the cells that we uncover in this left click, either number 1-8 or an unvered cell with no number) to the user.
      //Finally we check if all the non-mine cells are opened after this left click(i.e. if it is the last click), 
      //if so, set gameOver to true.
      if (hasAdjacent(row, col)){
         if(numSquares - numNotMineOpened == totalNumMines){
         gameOver = true;
         updateTriumphAllVisibleField();
         }
         return;
      }
      
      
      //Check if all the non-mine SquareViews are opened after current uncover, if so, game is over.
      if(numSquares - numNotMineOpened == totalNumMines){
         gameOver = true;
         updateTriumphAllVisibleField();
         return;
      }
      
      //If current SquareView has no adjacent mines, then keep recursion.     
      openMine(row - 1, col - 1);
      openMine(row - 1, col);
      openMine(row - 1, col + 1);
      openMine(row, col - 1);
      openMine(row, col + 1);
      openMine(row + 1, col - 1);
      openMine(row + 1, col);
      openMine(row + 1, col + 1);
   }
   
}
