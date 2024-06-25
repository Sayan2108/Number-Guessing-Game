import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private int randomNumber;
    private int attemptsLeft;
    private JLabel statusLabel;
    private JTextField guessField;
    private JButton guessButton;
    private int minRange;
    private int maxRange;

    public NumberGuessingGame(int minRange, int maxRange, int maxAttempts) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.attemptsLeft = maxAttempts;

        setTitle("Number Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Guess a number between " + minRange + " and " + maxRange);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());

        guessField = new JTextField(10);
        centerPanel.add(guessField);

        guessButton = new JButton("Guess");
        centerPanel.add(guessButton);

        add(centerPanel, BorderLayout.CENTER);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        setVisible(true);

        // Generate random number within specified range
        Random random = new Random();
        randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            if (guess < minRange || guess > maxRange) {
                JOptionPane.showMessageDialog(this, "Please enter a number within the range " + minRange + " and " + maxRange);
                return;
            }

            attemptsLeft--;

            if (guess == randomNumber) {
                JOptionPane.showMessageDialog(this, "Congratulations! You guessed the number " + randomNumber + " correctly!", "You Win!", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            } else {
                if (attemptsLeft == 0) {
                    JOptionPane.showMessageDialog(this, "Sorry, you've run out of attempts. The number was " + randomNumber, "Game Over", JOptionPane.WARNING_MESSAGE);
                    resetGame();
                } else {
                    String message = (guess < randomNumber) ? "Too Low! Attempts left: " + attemptsLeft : "Too High! Attempts left: " + attemptsLeft;
                    JOptionPane.showMessageDialog(this, message, "Try Again", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } finally {
            guessField.setText("");
            guessField.requestFocus();
        }
    }

    private void resetGame() {
        attemptsLeft = 3; // Reset attempts
        Random random = new Random();
        randomNumber = random.nextInt(maxRange - minRange + 1) + minRange; // Generate new random number
        statusLabel.setText("Guess a number between " + minRange + " and " + maxRange);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Example game with range 1 to 100 and 3 attempts
                new NumberGuessingGame(1, 100, 3);
            }
        });
    }
}
