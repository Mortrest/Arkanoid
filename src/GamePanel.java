import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.Arrays;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int shipX[] = new int[GAME_UNITS];
    final int shipY[] = new int[GAME_UNITS];
    int ballX = UNIT_SIZE*3;
    int ballY = SCREEN_WIDTH-UNIT_SIZE*2;
    int shipSize = 4;
    int wallsBroken;
    int collisions = 0;
    int gradientX = 1;
    int gradientY = -1;
    int BALL_UNIT = 30;
    boolean running = false;
    char direction = 'R';
    Timer timer;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());

        startGame();
        move(1);
        move(1);
        move(1);
    }

    public void startGame() {
        newRowWalls();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void newRowWalls() {
        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {

        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        for (int i = 0; i < shipSize; i++) {
            g.setColor(Color.BLUE);
            g.fillRect(shipX[i]+UNIT_SIZE, SCREEN_WIDTH-UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        g.setColor(Color.white);
        g.fillOval(ballX,ballY,UNIT_SIZE,UNIT_SIZE);
    }

    public void moveBall(){
        switch (collisions % 4) {
            case 0 -> {
                gradientX = 1;
                gradientY = -1;
            }
            case 1 -> {
                gradientX = -1;
                gradientY = -1;
            }
            case 2 -> {
                gradientX = -1;
                gradientY = 1;
            }
            case 3 -> {
                gradientX = 1;
                gradientY = 1;
            }
        }
        ballX += gradientX*BALL_UNIT;
        ballY += gradientY*BALL_UNIT;
    }

    public void move(int type) {
        for (int i = shipSize; i > 0; i--) {
            shipX[i] = shipX[i - 1];
        }
        switch (type) {
            case 1:
                shipX[0] = shipX[0] + UNIT_SIZE;
                break;
            case 2:
                shipX[0] = shipX[0] - UNIT_SIZE;
        }
    }

    public void ballCollision(){
        if (collisions>= 1){
            for (int i = 0; i < shipSize; i++) {
                if (shipX[i] == ballX && SCREEN_WIDTH-UNIT_SIZE == ballY){
                    System.out.println("oh");
                    collisions++;
                }
            }
        }
        if (ballX >= SCREEN_WIDTH || ballX <= UNIT_SIZE){
            collisions++;
        }
        if (ballY <= UNIT_SIZE || ballY >= SCREEN_HEIGHT - UNIT_SIZE*2){
            collisions++;
        }


    }

    public boolean checkCollisions1() {
        for (int i = 0; i < shipSize; i++) {
            if (shipX[i] == SCREEN_WIDTH-UNIT_SIZE*2) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCollisions2() {
        for (int i = 0; i < shipSize; i++) {
            if (shipX[i] == 0 && i != 0) {
                return false;
            }

        }
        return true;
    }

    public void gameOver(Graphics g) {

    }

    public class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (checkCollisions2() ) {
                        if (direction == 'R') {
                            for (int i = 0; i < shipSize-2; i++) {
                                move(2);
                            }
                        }
                        direction = 'L';
                        move(2);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (checkCollisions1()) {
                        if (direction == 'L') {
                            for (int i = 0; i < shipSize-2; i++) {
                                move(1);
                            }                        }
                        direction = 'R';
                        move(1);
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            moveBall();
            ballCollision();
        }
        repaint();
    }
}
