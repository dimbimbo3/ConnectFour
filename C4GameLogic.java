//Dan Imbimbo - COMS115 Final

public class C4GameLogic {
    private int[][] gameBoard;
    private int currentPlayer;
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private final int WIN = 3;//3 pieces connecting to intital piece (4 total connecting)
    
    public C4GameLogic(){
        gameBoard = new int[][] {{0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0}};
        currentPlayer = 1;
    }
    
    public void setPosition(int row, int col, int turn){
        //Player1 goes on odd turns, Player2 goes on even turns
        if(turn%2 == 0)
            currentPlayer = 2;
        else
            currentPlayer = 1;
        
        //sets current player's piece within array
        gameBoard[row][col] = currentPlayer;
    }
    
    public void getPosition(){
        //loop steps through the array to display it to the console
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                System.out.print("|" + gameBoard[row][col]);
            }
            System.out.println("|");
        }
    }
    
    public int getPlayer(){
        return currentPlayer;
    }
    
    public boolean winCheck(int currentRow, int currentCol){
        boolean win = false; //determines if the player has won
        
        //checks horizontal, vertical and diagonal to see if the player has won
        if(horizontalCheck(currentRow, currentCol))
            win = true;
        else if(verticalCheck(currentRow, currentCol))
            win = true;
        else if(diagonalCheck(currentRow, currentCol))
            win = true;
        
        //Debug Message
        System.out.println("Win: " + win);
        System.out.println("");
        
        return win;
    }
    
    /**
     * Checks counters from left and right. Then, it is determined whether the player has won.
     * @param currentRow The row that the piece was dropped into
     * @param currentCol The column that the piece was dropped into
     * @return A boolean variable indicating whether the player has won
     */
    public boolean horizontalCheck(int currentRow, int currentCol){
        boolean win = false; //determines if the player has won
        
        //Debug Message
        System.out.println("-----Horizontal-----");
        
        if((horizontalLeft(currentRow, currentCol) + horizontalRight(currentRow, currentCol)) >= WIN)
            win = true;
        
        return win;
    }
    
    private int horizontalLeft(int currentRow, int currentCol){
        int matchingLeft = 0; //counter for matching player pieces
        
        boolean noMatch = false; //will end the loop if the next piece does not match that of the player
        //loop checkes for matching player pieces in the column to the left
        for(int col = currentCol - 1; col >= 0 && !noMatch; col--){
            //if the next piece matches that of the player, increments the counter for matches
            if(gameBoard[currentRow][col] == currentPlayer){
                 matchingLeft++;
                 //Debug Message
                 System.out.println("Left: Match Column[" + col + "]");
            }
            //otherwise, switches boolean flag to true and ends loop
            else{
                noMatch = true;
                //Debug Message
                System.out.println("Left: No Match Column[" + col + "]");
            }
        }
        //Debug Message
        System.out.println("Matches (Left):" + matchingLeft);
        
        return matchingLeft;
    }
    
    private int horizontalRight(int currentRow, int currentCol){
        int matchingRight = 0; //counter for matching player pieces
        
        boolean noMatch = false; //will end the loop if the next piece does not match that of the player
        //loop checkes for matching player pieces in the column to the right
        for(int col = currentCol + 1; col < COLUMNS && !noMatch; col++){
            //if the next piece matches that of the player, increments the counter for matches
            if(gameBoard[currentRow][col] == currentPlayer){
                 matchingRight++;
                 //Debug Message
                 System.out.println("Right: Match Column[" + col + "]");
            }
            //otherwise, switches boolean flag to true and ends loop
            else{
                noMatch = true;
                //Debug Message
                System.out.println("Right: No Match Column[" + col + "]");
            }
        }
        //Debug Message
        System.out.println("Matches (Right):" + matchingRight);
        
        return matchingRight;
    }
    
    /**
     * Checks for any matching pieces going down and then determines whether the player has won.
     * (There is no need to check for matches going up, due to checking from the most recently dropped piece's position.)
     * @param currentRow The row that the piece was dropped into
     * @param currentCol The column that the piece was dropped into
     * @return A boolean variable indicating whether the player has won
     */
    public boolean verticalCheck(int currentRow, int currentCol){
        boolean win = false; //determines if the player has won
        int matchingDown = 0; //counter for matching player pieces
        
        //Debug Message
        System.out.println("-----Vertical-----");
        
        boolean noMatch = false; //will end the loop if the next piece does not match that of the player
        //loop checkes for matching player pieces in the next row down
        for(int row = currentRow + 1; row < ROWS && !noMatch; row++){
            //if the next piece matches that of the player, increments the counter for matches
            if(gameBoard[row][currentCol] == currentPlayer){
                 matchingDown++;
                 //Debug Message
                 System.out.println("Down: Match Column[" + row + "]");
            }
            //otherwise, switches boolean flag to true and ends loop
            else{
                noMatch = true;
                //Debug Message
                System.out.println("Down: No Match Column[" + row + "]");
            }
        }
        //Debug Message
        System.out.println("Matches (Down):" + matchingDown);
        
        //if the counter is equal to or exceeds the required amount, then switch the boolean flag to true to indicate a win
        if(matchingDown >= WIN)
            win = true;
        
        return win;
    }
    
    /**
     * Checks counters from left to right diagonally, and counters from right to left diagonally. Then, it is determined whether the player has won.
     * @param currentRow The row that the piece was dropped into
     * @param currentCol The column that the piece was dropped into
     * @return A boolean variable indicating whether the player has won
     */
    public boolean diagonalCheck(int currentRow, int currentCol){
        boolean win = false; //determines if the player has won
        
        //if the counter from left to right is equal to or exceeds the required amount, then switch the boolean flag to true to indicate a win
        if((diagonalUpLeft(currentRow, currentCol) + diagonalDownRight(currentRow, currentCol)) >= WIN)
            win = true;
        //if the counter from right to left is equal to or exceeds the required amount, then switch the boolean flag to true to indicate a win
        else if((diagonalUpRight(currentRow, currentCol) + diagonalDownLeft(currentRow, currentCol)) >= WIN)
            win = true;
        
        return win;
    }
    
    private int diagonalUpLeft(int currentRow, int currentCol){
        int matchingUpLeft = 0; //counter for matching player pieces
        
        //Debug Message
        System.out.println("-----Diagonal Left to Right-----");
        
        boolean noMatch = false; //will end the loop if the next piece does not match that of the player
        int row = currentRow - 1; //row up 1 from the player's piece
        int col = currentCol - 1; //column to the left 1 of the player's piece
        //loop checkes for matching player pieces in the next row up and column to the left
        while(row >= 0 && col >= 0 && !noMatch){
            //if the next piece matches that of the player, increments the counter for matches
            if(gameBoard[row][col] == currentPlayer){
                 matchingUpLeft++;
                 //Debug Message
                 System.out.println("Diagonal Up-Left: Match Column[" + col + "]");
            }
            //otherwise, switches boolean flag to true and ends loop
            else{
                noMatch = true;
                //Debug Message
                System.out.println("Diagonal Up-Left: No Match Column[" + col + "]");
            }
            
            row--; //moves row up 1
            col--; //moves column left 1
        }
        //Debug Message
        System.out.println("Matches (Diagonal Up-Left):" + matchingUpLeft);
        
        return matchingUpLeft;
    }
    private int diagonalDownRight(int currentRow, int currentCol){
        int matchingDownRight = 0; //counter for matching player pieces
        
        boolean noMatch = false; //will end the loop if the next piece does not match that of the player
        int row = currentRow + 1; //row down 1 from the player's piece
        int col = currentCol + 1; //column to the right 1 of the player's piece
        //loop checkes for matching player pieces in the next row down and column to the right
        while(row < ROWS && col < COLUMNS && !noMatch){
            //if the next piece matches that of the player, increments the counter for matches
            if(gameBoard[row][col] == currentPlayer){
                 matchingDownRight++;
                 //Debug Message
                 System.out.println("Diagonal Down-Right: Match Column[" + col + "]");
            }
            //otherwise, switches boolean flag to true and ends loop
            else{
                noMatch = true;
                //Debug Message
                System.out.println("Diagonal Down-Right: No Match Column[" + col + "]");
            }
            
            row++; //moves row down 1
            col++; //moves column right 1
        }
        //Debug Message
        System.out.println("Matches (Diagonal Down-Right):" + matchingDownRight);
        
        return matchingDownRight;
    }
    
    private int diagonalUpRight(int currentRow, int currentCol){
        int matchingUpRight = 0; //counter for matching player pieces
        
        //Debug Message
        System.out.println("-----Diagonal Right to Left-----");
        
        boolean noMatch = false; //will end the loop if the next piece does not match that of the player
        int row = currentRow - 1; //row up 1 from the player's piece
        int col = currentCol + 1; //column to the right 1 of the player's piece
        //loop checkes for matching player pieces in the next row up and column to the right
        while(row >= 0 && col < COLUMNS && !noMatch){
            //if the next piece matches that of the player, increments the counter for matches
            if(gameBoard[row][col] == currentPlayer){
                 matchingUpRight++;
                 //Debug Message
                 System.out.println("Diagonal Up-Right: Match Column[" + col + "]");
            }
            //otherwise, switches boolean flag to true and ends loop
            else{
                noMatch = true;
                //Debug Message
                System.out.println("Diagonal Up-Right: No Match Column[" + col + "]");
            }
            
            row--; //moves row up 1
            col++; //moves column right 1
        }
        //Debug Message
        System.out.println("Matches (Diagonal Up-Right):" + matchingUpRight);
        
        return matchingUpRight;
    }
    
    private int diagonalDownLeft(int currentRow, int currentCol){
        int matchingDownLeft = 0; //counter for matching player pieces
        
        boolean noMatch = false; //will end the loop if the next piece does not match that of the player
        int row = currentRow + 1; //row down 1 from the player's piece
        int col = currentCol - 1; //column to the left 1 of the player's piece
        //loop checkes for matching player pieces in the next row down and column to the left
        while(row < ROWS && col >= 0 && !noMatch){
            //if the next piece matches that of the player, increments the counter for matches
            if(gameBoard[row][col] == currentPlayer){
                 matchingDownLeft++;
                 //Debug Message
                 System.out.println("Diagonal Down-Left: Match Column[" + col + "]");
            }
            //otherwise, switches boolean flag to true and ends loop
            else{
                noMatch = true;
                //Debug Message
                System.out.println("Diagonal Down-Left: No Match Column[" + col + "]");
            }
            
            row++; //moves row down 1
            col--; //moves column left 1
        }
        //Debug Message
        System.out.println("Matches (Diagonal Down-Left):" + matchingDownLeft);
        
        return matchingDownLeft;
    }
}
