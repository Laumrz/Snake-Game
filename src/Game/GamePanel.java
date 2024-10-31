package Game;
// src/Game/GamePanel.java
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer; // Temporizador del juego
    public GamePanel() {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        Constants.running = true;
        timer = new Timer(Constants.DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (Constants.running) {
            g.setColor(Color.ORANGE);
            g.fillOval(Constants.appleX, Constants.appleY, Constants.UNIT_SIZE, Constants.UNIT_SIZE);

            for (int i = 0; i < Constants.bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(Constants.x[i], Constants.y[i], Constants.UNIT_SIZE, Constants.UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(Constants.x[i], Constants.y[i], Constants.UNIT_SIZE, Constants.UNIT_SIZE);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + Constants.applesEaten, (Constants.SCREEN_WIDTH - metrics.stringWidth("Score: " + Constants.applesEaten)) / 2, g.getFont().getSize() + 50);
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        Constants.appleX = Constants.random.nextInt((int)(Constants.SCREEN_WIDTH / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
        Constants.appleY = Constants.random.nextInt((int)(Constants.SCREEN_HEIGHT / Constants.UNIT_SIZE)) * Constants.UNIT_SIZE;
    }

    public void move() {
        for (int i = Constants.bodyParts; i > 0; i--) {
            Constants.x[i] = Constants.x[i - 1];
            Constants.y[i] = Constants.y[i - 1];
        }

        switch (Constants.direction) {
            case 'U' -> Constants.y[0] -= Constants.UNIT_SIZE;
            case 'D' -> Constants.y[0] += Constants.UNIT_SIZE;
            case 'L' -> Constants.x[0] -= Constants.UNIT_SIZE;
            case 'R' -> Constants.x[0] += Constants.UNIT_SIZE;
        }
    }

    public void checkApple() {
        if (Constants.x[0] == Constants.appleX && Constants.y[0] == Constants.appleY) {
            Constants.bodyParts++;
            Constants.applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = Constants.bodyParts; i > 0; i--) {
            if (Constants.x[0] == Constants.x[i] && Constants.y[0] == Constants.y[i]) {
                Constants.running = false; // Colisión con el cuerpo
            }
        }

        // Comprobar si colisiona con los bordes
        if (Constants.x[0] < 0 || Constants.x[0] >= Constants.SCREEN_WIDTH || Constants.y[0] < 0 || Constants.y[0] >= Constants.SCREEN_HEIGHT) {
            Constants.running = false; // Cambiar la condición para finalizar el juego correctamente
        }

        if (!Constants.running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (Constants.SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, Constants.SCREEN_HEIGHT / 2);
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + Constants.applesEaten, (Constants.SCREEN_WIDTH - metrics2.stringWidth("Score: " + Constants.applesEaten)) / 2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Constants.running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (Constants.direction != 'R') Constants.direction = 'L';
                }
                case KeyEvent.VK_RIGHT -> {
                    if (Constants.direction != 'L') Constants.direction = 'R';
                }
                case KeyEvent.VK_UP -> {
                    if (Constants.direction != 'D') Constants.direction = 'U';
                }
                case KeyEvent.VK_DOWN -> {
                    if (Constants.direction != 'U') Constants.direction = 'D';
                }
            }
        }
    }
}
