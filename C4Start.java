//Dan Imbimbo - COMS115 Final

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class C4Start extends JFrame{
    private C4Board connectFourGUI;
    
    private JLabel instructions;
    
    private JPanel buttonPanel;
    private ImageIcon redImage;
    private ImageIcon blackImage;
    private JButton chooseRed;
    private JButton chooseBlack;
    
    public C4Start(){
        setTitle("Color Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        connectFourGUI = new C4Board();
        
        //instructionsPanel used for GUI positioning
        JPanel instructionsPanel = new JPanel();
        instructions = new JLabel("Player 1, choose your color.");
        instructionsPanel.add(instructions);
        
        createButtonPanel();
        
        add(instructionsPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null); //centers frame upon start
        setVisible(true);
    }
    
    /**
     * Builds the JPanel containing the buttons for player 1 to choose their color
     */
    public void createButtonPanel(){
        buttonPanel = new JPanel();
        
        redImage = new ImageIcon("red.png");
        chooseRed = new JButton(redImage);
        chooseRed.setText("Red");
        chooseRed.addActionListener(new ButtonListener());
        
        blackImage = new ImageIcon("black.png");
        chooseBlack = new JButton(blackImage);
        chooseBlack.setText("Black");
        chooseBlack.addActionListener(new ButtonListener());
        
        buttonPanel.add(chooseRed);
        buttonPanel.add(chooseBlack);
    }
    
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String chosenColor;
            if(e.getSource() == chooseRed){
                JOptionPane.showMessageDialog(null, "Player 2 will be Black.");
                chosenColor = "red";
            }
            else{
                JOptionPane.showMessageDialog(null, "Player 2 will be Red.");
                chosenColor = "black";
            }
            JOptionPane.showMessageDialog(null, "Player 1 will go first.");
            
            //closes the current JFrame
            setVisible(false);
            dispose();
            
            connectFourGUI.setPlayerColors(chosenColor);
            connectFourGUI.setVisible(true);//"Starts" Connect Four after color selection
        }
    }
    
    public static void main(String[] args){
        new C4Start();
    }
}
