
public class MineFieldTester{
   
   private static boolean[][] smallMineField = 
      {{false, false, false, false}, 
      {true, false, false, false}, 
      {false, true, true, false},
      {false, true, false, true}};
   

   public static void main(String[] args){
   
      MineField testMine = new MineField(smallMineField);
      
      System.out.println("Test 1-arg MineField:");
      System.out.println("Expected MineField: MineField created with numRows: 4, numCols: 4, and numMines(): 5");
      System.out.println("Acutal MineField: " + testMine.toString());
      
      System.out.println("");
      System.out.println("Test numRows & numCols.........");
      System.out.println("Expected numRows: 4, Actual numRows: " + testMine.numRows() + ".");
      System.out.println("Expected numCols: 4, Actual numCols: " + testMine.numCols() + ".");
      System.out.println("Expected numMiness: 5, Actual numMiness: " + testMine.numMines() + ".");
      
      System.out.println("");
      System.out.println("Test hasMine & inRange.........");
      System.out.println("Expected [1][0] hasMines: true, Actual [1][0] hasMines : " + testMine.hasMine(1,0) + ".");
      System.out.println("Expected [0][0] hasMines: false, Actual [0][0] hasMines : " + testMine.hasMine(0,0) + ".");
      System.out.println("Expected [3][3] inRange: true, Actual [3][3] inRange: " + testMine.inRange(3,3) + ".");
      System.out.println("Expected [4][0] inRange: false, Actual [4][0] inRange: " + testMine.inRange(4,0) + ".");
      System.out.println("Expected [0][4] inRange: false, Actual [0][4] inRange: " + testMine.inRange(0,4) + ".");
      
      System.out.println("");
      System.out.println("Test numAdjacentMines.........");
      System.out.println("Expected [0][0] numAdjacentMines: 1, Actual [0][0] numAdjacentMines: " + testMine.numAdjacentMines(0,0) + ".");
      System.out.println("Expected [0][3] numAdjacentMines: 0, Actual [0][3] numAdjacentMines: " + testMine.numAdjacentMines(0,3) + ".");
      System.out.println("Expected [3][0] numAdjacentMines: 2, Actual [3][0] numAdjacentMines: " + testMine.numAdjacentMines(3,0) + ".");
      System.out.println("Expected [3][3] numAdjacentMines: 1, Actual [3][3] numAdjacentMines: " + testMine.numAdjacentMines(3,3) + ".");
      System.out.println("Expected [2][2] numAdjacentMines: 3, Actual [2][2] numAdjacentMines: " + testMine.numAdjacentMines(2,2) + ".");      
      
      System.out.println("");
      System.out.println("Test resetEmpty.........");
      testMine.resetEmpty();
      System.out.println("Expected MineField(after resetEmpty) created with numRows: 4, numCols: 4, and numMines: 0");
      System.out.println("Acutal(Should be no change): " + testMine.toString());
      
      System.out.println("");
      System.out.println("Populate(on non-mine cell) 1-arg MineField.......");
      testMine.populateMineField(1,1);
      System.out.println("1-arg MineField after populated: " + testMine.toString());
      
      System.out.println("");
      System.out.println("Populate(on mine cell) 1-arg MineField.......");
      testMine.populateMineField(1,0);
      System.out.println("1-arg MineField after populated: " + testMine.toString());     
      
      System.out.println("");
      System.out.println("");
      System.out.println("Test 3-arg MineField:");
      MineField test3Mine = new MineField(4,4,3);
      System.out.println("Expected MineField(before populated) created with numRows: 4, numCols: 4, and numMines(): 3.");
      System.out.println("Acutal: " + test3Mine.toString());
      
      test3Mine.populateMineField(1,1);
      System.out.println("Expected MineField(after populated[1][1]) created with numRows: 4, numCols: 4, and numMines(): 3.");
      System.out.println("Acutal: " + test3Mine.toString());
      test3Mine.resetEmpty();
      System.out.println("Expected MineField(after resetEmpty) created with numRows: 4, numCols: 4, and numMines(): 3.");
      System.out.println("Acutal: " + test3Mine.toString());
      test3Mine.populateMineField(3,3);
      System.out.println("Expected MineField(after populated[3][3]) created with numRows: 4, numCols: 4, and numMines(): 3.");
      System.out.println("Acutal: " + test3Mine.toString());
      
   }

}
