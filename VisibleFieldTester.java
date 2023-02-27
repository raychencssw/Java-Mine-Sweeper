// Name:wei-chieh, chen
// USC NetID:weichieh
// CS 455 PA3
// Fall 2022
// create by my own

public class VisibleFieldTester{

   private static boolean[][] smallMineField = 
      {{false, false, false, false}, 
      {true, false, false, false}, 
      {false, true, true, false},
      {false, true, false, true}};
   
   
   
   public static void main(String[] args){
      
      MineField mineField = new MineField(smallMineField);
      VisibleField visField = new VisibleField(mineField);
      
      System.out.println((visField.getMineField()).toString());
      System.out.println("Expected getStatus[0][0]: -1, Actual getStatus[0][0]: " + visField.getStatus(0,0) + ".");
      System.out.println("Expected numMines: 5, Actual numMines: " + visField.getMineField().numMines() + ".");
      
      System.out.print("Status 2-D array constructed, expected: all -1, Actual: ");
      for (int i = 0; i < smallMineField.length; i++) {
         for (int j = 0; j < smallMineField.length; j++){
            System.out.print(visField.getStatus(i,j) + ", ");
         }
      }
      System.out.println("");
      
      
      visField.cycleGuess(1,1);
      System.out.println("Expected getStatus[1][1] after 1st rightClick: -2, Acutal getStatus[1][1]: " + visField.getStatus(1,1) + ".");
      visField.cycleGuess(1,1);
      System.out.println("Expected getStatus[1][1] after 2nd rightClick: -3, Acutal getStatus[1][1]: " + visField.getStatus(1,1) + ".");
      visField.cycleGuess(1,1);
      System.out.println("Expected getStatus[1][1] after 3rd rightClick: -1, Acutal getStatus[1][1]: " + visField.getStatus(1,1) + ".");
      
      System.out.println("Expected isGameOver: false, Actual: " + visField.isGameOver() + ".");
      
   }





}