import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.Arrays;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 50;
    static final int BLOCK_WIDTH = 40;
    static final int BLOCK_HEIGHT = 25;
    final int shipX[] = new int[GAME_UNITS];
    final int shipY[] = new int[GAME_UNITS];
    int ballX = UNIT_SIZE * 6;
    int ballY = SCREEN_WIDTH - UNIT_SIZE * 2;
    int shipSize = 25;
    int wallsBroken;
    int collisions = 0;
    int gradientX = 1;
    int gradientY = -1;
    int BALL_UNIT = 20;
    int time = 0;
    LinkedList<Block> blocks = new LinkedList<>();
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
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        newRowBlocks();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
//
        for (int i = 0; i < shipSize; i++) {
            g.setColor(Color.BLUE);
            g.fillRect(shipX[i] + UNIT_SIZE, SCREEN_WIDTH - UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }
        if (running) {
            g.setColor(Color.BLACK);
            for (Block block : blocks){
                switch (block.type){
                    case 1:
                        g.setColor(Color.cyan);
                        break;
                    case 2:
                        g.setColor(Color.magenta);
                        break;
                    case 3:
                        g.setColor(Color.YELLOW);
                        break;
                    case 4:
                        g.setColor(Color.white);
                        break;
                    case 5:
                        g.setColor(Color.RED);
                        break;
                }
                g.fillRect(block.blockX,block.blockY,40,25);
            }
            g.drawLine(0, SCREEN_WIDTH - UNIT_SIZE, SCREEN_HEIGHT, SCREEN_WIDTH - UNIT_SIZE);
            g.setColor(Color.white);
            g.fillOval(ballX, ballY, UNIT_SIZE, UNIT_SIZE);
        } else {
            g.setColor(Color.red);
            g.setFont(Font.getFont(Font.SANS_SERIF));

            g.drawString("Game Over",250,300);

        }
    }


    public void newRowBlocks(){
        for (int i = 0; i < 1; i++) {
            blocks.add(new Block(i*UNIT_SIZE*2,0));
            blocks.add(new Block(i*UNIT_SIZE*2,BLOCK_HEIGHT*2));
            blocks.add(new Block(i*UNIT_SIZE*2,BLOCK_HEIGHT*4));

        }
    }

    public void moveBall() {

        int max = 0;
        int min = 600;
        for (int i = 0; i < shipSize; i++) {
            max = Math.max(shipX[i],max);
            min = Math.min(shipX[i],min);
        }
        if ( min-20 <= ballX && ballX <= max+60 && ballY == SCREEN_HEIGHT - UNIT_SIZE * 2){
            gradientY = -1;
        }
        int i = 0;
        for (Block block : blocks){
            if (block.blockY + BALL_UNIT >= ballY && ballY >= block.blockY  && block.blockX - BALL_UNIT <= ballX && block.blockX + BALL_UNIT >= ballX){
                System.out.println(ballY + " " + block.blockY);
                if (ballY <= block.blockY){
                    gradientY = -1;
                } else {
                    gradientY = 1;
                }
                if (block.type == 2){
                    block.type = 1;
                } else {
                    blocks.remove(block);
                }
                break;
            }
        }

        if (ballX >= SCREEN_WIDTH - UNIT_SIZE - 15) {
            gradientX = -1;
        }
        if (ballX <= UNIT_SIZE) {
            gradientX = 1;
        }
        if (ballY <= 20) {
            gradientY = 1;
        }
        if (ballY >= SCREEN_HEIGHT) {
            running = false;
        }
        ballX += gradientX * BALL_UNIT;
        ballY += gradientY * BALL_UNIT;
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


    public boolean checkCollisions1() {
        for (int i = 0; i < shipSize; i++) {
            if (shipX[i] == SCREEN_WIDTH - UNIT_SIZE * 2) {
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
        g.drawString("Game Over",300,300);

    }

    public class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (checkCollisions2()) {
                        if (direction == 'R') {
                            for (int i = 0; i < shipSize - 2; i++) {
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
                            for (int i = 0; i < shipSize - 2; i++) {
                                move(1);
                            }
                        }
                        direction = 'R';
                        move(1);
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time++;
        if (running) {
            if (time % 100 == 0){
                for (Block block : blocks){
                    block.blockY += BALL_UNIT;
                }
            }
            moveBall();
        }
        repaint();
    }
}
