
//all imports used and needed for this project
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;




public class GUI implements ActionListener {
    
    //player symbols
    String player1 = "X";
    String player2 = "O";
    String currentPlayer = player1;



    //keeping track of players wins and games played
    int player1Score = 0;
    int player2Score = 0;
    int gamesPlayed = 0;


    //gui components
    boolean game = false;
    JButton[] buttons = new JButton[9];
    JLabel scoreLabel;
    JButton restartButton;

    //initializing the restart button to false
    boolean restartEnabled = false; 
    


    public GUI() {

        //creating the main gui and its components
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(); 
        JPanel scorePanel = new JPanel();
        JPanel buttonPanel = new JPanel();


        //setting the propertys of the jframe, size,title,closing ect.
        frame.setTitle("Nolen's Tic|Tac|Toe game!");
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //background color and layout 
        panel.setBackground(new Color(75, 75, 75));
        panel.setLayout(new GridLayout(3, 3, 10, 10));

        //the font for the butttons
        Font font = new Font("Serif", Font.BOLD, 100);


        //creating all of the buttons and adding them to the panel

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(font);
            buttons[i].addActionListener(this); 
            buttons[i].setPreferredSize(new Dimension(200, 200));
            panel.add(buttons[i]); 
        }


        //creating the scorelabel
        scoreLabel = new JLabel("Score: Player1 - " + player1Score + "| Player2 - " + player2Score);
        Font scoreFont = new Font("Serif", Font.BOLD, 24);  
        scoreLabel.setFont(scoreFont);
        scorePanel.add(scoreLabel);

        
        //creating the restart button and its actionlistener
        restartButton = new JButton("Restart Game");
        restartButton.setEnabled(false);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreLabel.setText("Score: Player1 - " + player1Score + " | Player2 - " + player2Score);
                resetGame();
                
                player1Score = 0;
                player2Score = 0;
                gamesPlayed = 0;
            }
        });

        buttonPanel.add(restartButton);

       //adding these components to the framw
        frame.add(panel);
        frame.add(scorePanel);
        frame.add(buttonPanel);

        //setting the layout of the frame, making it visible as wlel
        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }



    
    @Override
    public void actionPerformed(ActionEvent e) {
        try 
        {
            JButton button = (JButton) e.getSource();

            if (button.getText().isEmpty()) {
                button.setText(currentPlayer);
        
                //checking for win and disabling buttons and updating score if win is true
                if (checkWin()) {

                    updateScore();
                    disableButtons();
                    

                    //setting a timer to resart the game so you can see how the person won
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)
                         {
                            resetGame();
                        }
                    });


                    timer.setRepeats(false); 
                    timer.start();


                } else if (checkDraw()) //checking fro draw
                {
                    resetGame();

                } else 
                {
                    currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    //method for checking for win
    public boolean checkWin() {
        // Rows
        for(int i = 0; i < 3; i++) {
            if (checkLine(i * 3, i * 3 + 1, i * 3 + 2)) {
                
                return true;
            }
        }

        // Columns
        for (int i = 0; i < 3; i++) {
            if (checkLine(i, i + 3, i + 6)) {
                
                return true;
            }
        }

        // Diagonals
        if (checkLine(0, 4, 8)) {
           
            return true;
        }
        if (checkLine(2, 4, 6)) {
            
            return true;
        }
        return false;
    }

    //method for schecking a line for a win
    private boolean checkLine(int a, int b, int c) {
        try {
            return buttons[a].getText().equals(currentPlayer) && 
                   buttons[b].getText().equals(currentPlayer) &&
                   buttons[c].getText().equals(currentPlayer);
        } catch (Exception array) {
            array.printStackTrace();
            return false;
        }
    }

    //method for checking for a draw
    private boolean checkDraw() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    //restart game method
    private void resetGame() {
        for(JButton button : buttons) 
        {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(null);
        }

        currentPlayer = player1;
        gamesPlayed++;
        
        if (gamesPlayed >= 3) {
            if (player1Score > player2Score) {
                scoreLabel.setText("Player 1 wins!!!");
            } else if (player2Score > player1Score) {
                scoreLabel.setText("Player 2 wins!!!");
            } else {
                scoreLabel.setText("Draw!");
            }

            for (JButton btn: buttons) {
                btn.setEnabled(false);
            }

            player1Score = 0;
            player2Score = 0;
            gamesPlayed = 0;
            restartButton.setEnabled(true); // Enable restart button

        } else {
            gamesPlayed++;
        }
    }

    //updating score method
    private void updateScore() {
        if (currentPlayer.equals(player1)) {
            player1Score++;
        } else {
            player2Score++;
        }
        scoreLabel.setText("Score: Player1 - " + player1Score + " | Player2 - " + player2Score);
    }

    //disabling buttom method
    private void disableButtons() {

        for (JButton btn: buttons) 
        {
            btn.setEnabled(false);
        }

    }
}
