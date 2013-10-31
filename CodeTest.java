/**
 * @author Joshua Henn
 * @date Wed, April 24, 2013
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CodeTest extends JFrame implements ActionListener {

    Integer buttonsClicked = 0; 
    Integer hexagonLetters = 20;

    /**
     * Create parent GUI frame
     */
    public CodeTest() {

        // Setup the layout and window behavior
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(new HexLayout(3, false));

        // Create the buttons
        for (int i = 0; i < hexagonLetters; i++) {
            HexButton button = new HexButton((char)(65 + (new Random()).nextInt(26)));
            this.add(button);
            button.addActionListener(this);            
        }

        // Show the GUI
        this.setVisible(true);
        this.setMinimumSize(new Dimension(500, 500));
        this.setMaximumSize(new Dimension(500, 500));
        this.setSize(500, 500);

    }

    /**
     * Handle actions performed on any of the buttons, will perform
     * program termination if all the buttons have been clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if (source instanceof HexButton) {
            HexButton hexButton = (HexButton) source;
            if (!hexButton.isClicked()) {
                hexButton.click();
                this.buttonsClicked++;
            }

            // If all the buttons have been clicked, terminate the program
            if (this.buttonsClicked == this.hexagonLetters) {
                setVisible(false);
                dispose();
            }
        }

    }

    /**
     * Creates the GUI
     */
    public static void main(String[] args) {        
        new CodeTest();
    }

}