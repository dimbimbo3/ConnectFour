//Dan Imbimbo - COMS115 Final

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class C4Board extends JFrame{
    private C4GameLogic connectFour;
    
    private JPanel buttonPanel;
    private JButton[] dropButton;
    
    private JPanel playersPanel;
    private JLabel player1;
    private JLabel player2;
    private JTextField turn;
    private int turnCount = 1;
    
    private JPanel gamePanel;
    private JLabel[][] gameArray;
    private ImageIcon emptyImage;
    private ImageIcon redImage;
    private ImageIcon blackImage;
    
    private ImageIcon player1Color;
    private ImageIcon player2Color;
    
    private final int ROWS = 6; //Length of rows
    private final int COLUMNS = 7; //Length of columns
    private final int MAX_TURNS = 42; //Maximum possible turns on 6x7 board
    
    public C4Board(){
        setTitle("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        connectFour = new C4GameLogic();
        
        createButtonPanel();
        add(buttonPanel, BorderLayout.NORTH);
        
        createPlayersPanel();
        add(playersPanel, BorderLayout.SOUTH);
        
        createGamePanel();
        add(gamePanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null); //centers frame upon start
        setVisible(false);
    }
    
    /**
     * Builds the JPanel containing the buttons used to place pieces into each column
     */
    public void createButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,7));
        
        dropButton = new JButton[COLUMNS];
        for(int i = 0; i < dropButton.length; i++){
            dropButton[i] = new JButton("Drop");
            dropButton[i].addActionListener(new ButtonListener());
            buttonPanel.add(dropButton[i]);
        }
    }
    
    /**
     * Builds the JPanel containing the color of each player and the turn counter
     */
    public void createPlayersPanel(){
        playersPanel = new JPanel();
        playersPanel.setLayout(new BorderLayout());
        playersPanel.setBorder(BorderFactory.createEtchedBorder());
        
        player1 = new JLabel("Player 1");
        
        //turnPanel used for GUI positioning
        JPanel turnPanel = new JPanel();
        turn = new JTextField("Turn: " + turnCount, 5);
        turn.setEditable(false);
        turnPanel.add(turn);
        
        //player2Panel used for GUI positioning
        JPanel player2Panel = new JPanel();
        player2 = new JLabel("Player 2");
        player2Panel.add(player2);
        
        playersPanel.add(player1, BorderLayout.WEST);
        playersPanel.add(turnPanel, BorderLayout.CENTER);
        playersPanel.add(player2Panel, BorderLayout.EAST);
    }
    
    /**
     * Determines if player 1 is red or black, and sets player 2 to the opposite color
     * @param chosenColor A String indicating which color player 1 wants to be
     */
    public void setPlayerColors(String chosenColor){
        if(chosenColor == "red"){
            player1.setForeground(Color.red);
            player1Color = redImage;
            player2Color = blackImage;
        }
        else{
            player2.setForeground(Color.red);
            player1Color = blackImage;
            player2Color = redImage;
        }
    }
    
    /**
     * Builds the JPanel comprising of the Connect Four board 
     */
    public void createGamePanel(){
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(6,7));
        
        emptyImage = new ImageIcon("empty.png");
        redImage = new ImageIcon("red.png");
        blackImage = new ImageIcon("black.png");
        
        gameArray = new JLabel[ROWS][COLUMNS];
        //loop sets the emptyImage icons to the elements of the gameArray and places them in the gamePanel's Grid
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                gameArray[row][col] = new JLabel(emptyImage);
                gamePanel.add(gameArray[row][col]);
            }
        }
    }
    
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int col = 0; //starts column check from the lowest array index
            boolean foundColumn = false; //will end the loop when the column is found
            //Checks for the selected button's source to determine which column it is in
            do{
                if(e.getSource() == dropButton[col]){
                    foundColumn = true;
                }
                else
                    col++;
            }while(col < COLUMNS && !foundColumn);
            
            //Debug message
            System.out.println("Column:" + col);
            
            int row = ROWS - 1; //starts row check from the maximum array index
            boolean available = false; //determines whether there is an available slot in the chosen column
            
            //Checks if selected column has an available slot and which slot that is
            //If the loop completes with no slots found, then the "available" flag remains false
            do{
                if(gameArray[row][col].getIcon() == emptyImage){
                    available = true;
                }
                else
                    row--;
            }while(row >= 0 && !available);
            
            //Debug messages
            System.out.println("Row:" + row);
            System.out.println("Availability:" + available + "\n");
            
            if(available){
                //Checks which color piece to drop into the selected column
                //Player1 goes on odd turns, Player2 goes on even turns
                if(turnCount%2 == 0){
                    gameArray[row][col].setIcon(player2Color);
                }
                else
                    gameArray[row][col].setIcon(player1Color);
                
                //places the current player's piece in the C4GameLogic object's array
                connectFour.setPosition(row, col, turnCount);
                
                //Debug messages
                connectFour.getPosition();
                System.out.println("\nPlayer:" + connectFour.getPlayer());
                
                //checks if the current player has won
                if(connectFour.winCheck(row,col)){
                    //displays the winner
                    JOptionPane.showMessageDialog(null, "Player " + connectFour.getPlayer() + " Wins!");
                    endGame();
                }
                
                turnCount++; //advances to the next turn
                turn.setText("Turn: " + turnCount);
                
                //checks if maximum turns have been exceeded, indicating a tie
                if(turnCount > MAX_TURNS){
                    JOptionPane.showMessageDialog(null, "Tie Game! There are no winners.");
                    endGame();
                }
            }
            else
                //If no available slots were found in the selected column,
                //then a message is displayed telling the player to pick another
                JOptionPane.showMessageDialog(null, "There are no slots available in this column. Please pick another column.");
        }
    }
    
    /**
     * Used to close the C4Board JFrame and prompt the players as to if they want to play again
     */
    public void endGame(){      
        //asks the player whether they want to play again
        int confirmDialog = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
        
        //closes the current JFrame
        setVisible(false);
        dispose();
        
        //if yes is chosen, create a new instance of C4Start
        if(confirmDialog == JOptionPane.YES_OPTION)
            new C4Start();
    }
}