import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIApp extends JFrame implements ActionListener {

    private int size;
    private JButton[][] buttons;
    private boolean firstPlayer;
    private int stepCounter;
    private int toWin;

    public GUIApp(int size, int toWin) {

        if (size < 4) {
            size = 3;
            toWin = 3;
        }

        this.size = size;
        this.toWin = toWin;
        stepCounter = 0;
        firstPlayer = true;
        buttons = new JButton[size][size];

        setTitle("Tic tac toe");
        setSize(100 + size * 50, 120 + size * 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(size, size));

        generateButtons();
    }

    private void generateButtons() {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 50);
        Insets m = new Insets(0, 0, 0, 0);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                JButton button = new JButton();
                button.setText("");
                button.setFont(font);
                button.setMargin(m);

                button.setBackground(Color.yellow);
                button.setOpaque(true);

                button.setActionCommand(y + " " + x);
                button.addActionListener(this);
                this.add(button);
                buttons[y][x] = button;
            }
        }
    }

    private boolean amIwinner(int x, int y, String player) {

        int count = 1 +
                count(x, y, player, -1, 1) +
                count(x, y, player, 1, -1);

        if (count >= toWin) {
            return true;
        }
        count = 1 +
                count(x, y, player, -1, -1) +
                count(x, y, player, 1, 1);

        if (count >= toWin) {
            return true;
        }

        count = 1 +
                count(x, y, player, 1, 0) +
                count(x, y, player, -1, 0);

        if (count >= toWin) {
            return true;
        }
        count = 1 +
                count(x, y, player, 0, 1) +
                count(x, y, player, 0, -1);

        if (count >= toWin) {
            return true;
        }

        return false;
    }

    int count(int x, int y, String player, int stepX, int stepY) {
        int j = x + stepX;
        int i = y + stepY;
        int counter = 0;

        while (coordsInBounds(i, j) && buttons[i][j].getText().equals(player)) {
            ++counter;
            i += stepY;
            j += stepX;
        }

        return counter;
    }

    private boolean coordsInBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private void clear() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                buttons[y][x].setText("");
                firstPlayer = true;
            }
        }
        stepCounter = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String[] coordinates = e.getActionCommand().split(" ");
        int y = Integer.valueOf(coordinates[0]);
        int x = Integer.valueOf(coordinates[1]);

        if (buttons[y][x].getText().equals("")) {
            if (firstPlayer) {
                buttons[y][x].setText("X");
                firstPlayer = false;
            } else {
                buttons[y][x].setText("O");
                firstPlayer = true;
            }
            stepCounter++;
        }
        if (amIwinner(x, y, buttons[y][x].getText())) {
            JOptionPane.showMessageDialog(this, "You win!", "Win!", JOptionPane.WARNING_MESSAGE);
            clear();
        } else if (stepCounter == size * size) {
            JOptionPane.showMessageDialog(this, "Nobody wins!", "End!", JOptionPane.WARNING_MESSAGE);
            clear();
        }
    }
}