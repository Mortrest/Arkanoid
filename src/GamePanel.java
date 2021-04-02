import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 50;
    static final int BLOCK_WIDTH = 40;
    static final int BLOCK_HEIGHT = 25;
    final int[] shipX = new int[7];
    final int BALL_WIDTH = 15;
    final int BALL_HEIGHT = 15;
    ModelLoader ml = new ModelLoader();
    Ball ball1 = new Ball(1);
    Ball ball2 = new Ball(1);
    Ball ball3 = new Ball(-1);
    int shipSize = 4;
    int BALL_UNIT = 15;
    int points = 0;
    int health = 3;
    int time = 0;
    int shipTime = 0;
    int dizzyTime = 0;
    int speedTime = 0;
    LinkedList<Block> prizes = new LinkedList<>();
    LinkedList<Block> blocks = new LinkedList<>();
    boolean running = false;
    boolean dizzy = false;
    boolean gameOver = false;
    boolean paused = false;
    char direction = 'R';
    Timer timer;
    boolean savePage = false;
    JButton saveBtn = new JButton("Save");
    JButton[] loads = new JButton[5];
    JButton sBTN = new JButton("New save");
    GamePanel(String str){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());
        saveBtn.addActionListener(this);
        this.add(saveBtn);
        saveBtn.setVisible(false);
        this.setLayout(null);
        sBTN.addActionListener(this);
        sBTN.setFocusable(false);
        sBTN.setBackground(Color.white);
        sBTN.setFont(new Font("Ink Free",Font.BOLD, 20));
        sBTN.setBounds(300-145/2,600-150,160,50);
        sBTN.setVisible(false);
        this.add(sBTN);
        System.out.println(ml.howManyFiles());
        String[] names = ml.names();
        System.out.println(Arrays.toString(names));
        for (int i = 0; i < ml.howManyFiles(); i++) {
            loads[i] = new JButton(names[i]);
            loads[i].setName(names[i]);
            loads[i].addActionListener(this);
            loads[i].setFocusable(false);
            loads[i].setBackground(Color.white);
            loads[i].setFont(new Font("Ink Free",Font.BOLD, 20));
            loads[i].setBounds(300-145/2,i*100+70,160,50);
            loads[i].setVisible(false);
            this.add(loads[i]);
        }
        Scanner ss = new Scanner(str);
        ss.next();points = ss.nextInt();
        ss.next();shipSize = ss.nextInt();
        ss.next();BALL_UNIT = ss.nextInt();
        ss.next();health = ss.nextInt();
        ss.next();time = ss.nextInt();
        ss.next();shipTime =ss.nextInt();
        ss.next();dizzyTime = ss.nextInt();
        ss.next();speedTime = ss.nextInt();
        ss.next();running = ss.nextBoolean();
        ss.next();dizzy = ss.nextBoolean();
        ss.next();paused = ss.nextBoolean();
        paused = false;
        ss.next();direction = ss.nextLine().toCharArray()[0];
        ss.next();
        for (int i = 0; i < ss.nextInt()+1; i++) {
            shipX[i] = ss.nextInt();
        }
        ss.next();
//        System.out.println(ss.next());
        ss.next();
        int a = ss.nextInt();
        for (int i = 0; i < a; i++) {
            ss.next();
            int x = ss.nextInt(); ss.next();
            int y = ss.nextInt(); ss.next();
            int power = ss.nextInt(); ss.next();
            int type = ss.nextInt();ss.next();
            boolean visible = ss.nextBoolean();
            Block block = new Block(x,y,power,type,visible);
            blocks.add(block);
        }
        ss.next();
        ss.next();
        ball1.x = ss.nextInt();ss.next();
        ball1.y = ss.nextInt();ss.next();
        ball1.gradientX = ss.nextInt();ss.next();
        ball1.gradientY = ss.nextInt();ss.next();
        ball1.fireTime = ss.nextInt();ss.next();
        ball1.isAlive = ss.nextBoolean();ss.next();
        ball1.isFireBall = ss.nextBoolean();
        ss.next();
        ball2.x = ss.nextInt();ss.next();
        ball2.y = ss.nextInt();ss.next();
        ball2.gradientX = ss.nextInt();ss.next();
        ball2.gradientY = ss.nextInt();ss.next();
        ball2.fireTime = ss.nextInt();ss.next();
        ball2.isAlive = ss.nextBoolean();ss.next();
        ball2.isFireBall = ss.nextBoolean();
        ss.next();
        ball3.x = ss.nextInt();ss.next();
        ball3.y = ss.nextInt();ss.next();
        ball3.gradientX = ss.nextInt();ss.next();
        ball3.gradientY = ss.nextInt();ss.next();
        ball3.fireTime = ss.nextInt();ss.next();
        ball3.isAlive = ss.nextBoolean();ss.next();
        ball3.isFireBall = ss.nextBoolean();

        startGame(0);


    }


    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());
        saveBtn.addActionListener(this);
        this.add(saveBtn);
        saveBtn.setVisible(false);
        this.setLayout(null);
        sBTN.addActionListener(this);
        sBTN.setFocusable(false);
        sBTN.setBackground(Color.white);
        sBTN.setFont(new Font("Ink Free",Font.BOLD, 20));
        sBTN.setBounds(300-145/2,600-150,160,50);
        sBTN.setVisible(false);
        this.add(sBTN);
        System.out.println(ml.howManyFiles());
        String[] names = ml.names();
        System.out.println(Arrays.toString(names));
        for (int i = 0; i < ml.howManyFiles(); i++) {
            loads[i] = new JButton(names[i]);
            loads[i].setName(names[i]);
            loads[i].addActionListener(this);
            loads[i].setFocusable(false);
            loads[i].setBackground(Color.white);
            loads[i].setFont(new Font("Ink Free",Font.BOLD, 20));
            loads[i].setBounds(300-145/2,i*100+70,160,50);
            loads[i].setVisible(false);
            this.add(loads[i]);
        }
        ball2.isAlive = false;
        ball3.isAlive = false;
        startGame(1);
        move(1);
        move(1);
        move(1);
    }

    public String saveGame(){
        StringBuilder str = new StringBuilder();
        str.append("points " + points +"\nShipSize " + shipSize + "\nBallUNIT " + BALL_UNIT + "\nHealth "+
                health + "\ntime " + time + "\nshipTime " + shipTime + "\ndizzTime " + dizzyTime + "\nspeedTime " + speedTime
                + "\nrunning " + running + "\ndizzy " + dizzy + "\npaused " + !paused + "\ndirection " + direction );
        str.append("\nshipX " + shipX.length + "\n");
        for (int s : shipX){
            str.append(s + " ");
        }
        str.append("\nBlocks " + blocks.size() + "\n");
        for (Block block: blocks){
            str.append("x " + block.blockX + "\ny " + block.blockY + "\npower " + block.power +
                    "\ntype " + block.type + "\nvisible " + block.visible + "\n");
        }
        str.append("\nBalls ");
        str.append("\nx " + ball1.x + "\ny " + ball1.y + "\ngradientX " + ball1.gradientX
            + "\ngradientY " + ball1.gradientY + "\nfireTime " + ball1.fireTime +
                "\nisAlive " + ball1.isAlive + "\nisFireBall " + ball1.isFireBall);
        str.append("\nx " + ball2.x + "\ny " + ball2.y + "\ngradientX " + ball2.gradientX
                + "\ngradientY " + ball2.gradientY + "\nfireTime " + ball2.fireTime +
                "\nisAlive " + ball2.isAlive + "\nisFireBall " + ball2.isFireBall);
        str.append("\nx " + ball3.x + "\ny " + ball3.y + "\ngradientX " + ball3.gradientX
                + "\ngradientY " + ball3.gradientY + "\nfireTime " + ball3.fireTime +
                "\nisAlive " + ball3.isAlive + "\nisFireBall " + ball3.isFireBall);
        return str.toString();
    }

    public void startGame(int type) {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        if (type == 1){
        newRowBlocks();
    }}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (gameOver){
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Gameover", (SCREEN_WIDTH - metrics1.stringWidth("Gameover")) / 2, g.getFont().getSize() + 400);
            Scanner ss = new Scanner(System.in);
            ss.next();
        }
        else if (!paused) {
            saveBtn.setVisible(false);
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Points: " + points + "    Health: " + health, (SCREEN_WIDTH - metrics1.stringWidth("Points: " + points + " Health: " + health)) / 2, g.getFont().getSize() + 400);

            for (int i = 0; i < shipSize; i++) {
                g.setColor(Color.BLUE);
                g.fillRect(shipX[i] + UNIT_SIZE, SCREEN_WIDTH - UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
            if (running) {
                for (Block block : blocks) {
                    switch (block.type) {
                        case 1 -> g.setColor(Color.CYAN);
                        case 2 -> g.setColor(Color.magenta);
                        case 3 -> {
                            if (block.visible) {
                                g.setColor(Color.YELLOW);
                            } else {
                                g.setColor(Color.black);
                            }
                        }
                        case 4 -> g.setColor(Color.BLACK);
                        case 0 -> g.setColor(Color.RED);
                    }
                    g.fillRect(block.blockX, block.blockY, BLOCK_WIDTH, BLOCK_HEIGHT);
                }
                for (Block block : prizes) {
                    switch (block.power) {
                        case 0 -> g.setColor(Color.LIGHT_GRAY);
                        case 1 -> g.setColor(Color.GREEN);
                        case 2 -> g.setColor(Color.BLUE);
                        case 3 -> g.setColor(Color.getHSBColor(180, 100, 100));
                        case 4 -> g.setColor(Color.orange);
                        case 5 -> g.setColor(Color.pink);
                        case 6 -> g.setColor(Color.gray);
                        case 7 -> g.setColor(Color.getHSBColor(0, 100, 50));
                    }
                    g.fillRect(block.blockX, block.blockY, BLOCK_WIDTH, BLOCK_HEIGHT);
                }
                g.setColor(Color.white);
                if (ball1.isAlive) {
                    if (ball1.isFireBall) {
                        g.setColor(Color.ORANGE);
                    }
                    g.fillOval(ball1.x, ball1.y, BALL_WIDTH, BALL_HEIGHT);
                    g.setColor(Color.white);
                }
                if (ball2.isAlive) {
                    if (ball2.isFireBall) {
                        g.setColor(Color.orange);
                    }
                    g.fillOval(ball2.x, ball2.y, BALL_WIDTH, BALL_HEIGHT);
                    g.setColor(Color.white);
                }
                if (ball3.isAlive) {
                    if (ball3.isFireBall) {
                        g.setColor(Color.orange);
                    }
                    g.fillOval(ball3.x, ball3.y, BALL_WIDTH, BALL_HEIGHT);
                    g.setColor(Color.white);
                }
            } else {
                if (!savePage) {
                    g.setColor(Color.red);
                    g.setFont(Font.getFont(Font.SANS_SERIF));
                    g.drawString("Game Over", 250, 300);
                } else {
                    for (int i = 0; i < ml.howManyFiles(); i++) {
                        if (loads[i] != null) {
                            loads[i].setVisible(true);
                        }
                    }
                }
            }
        } else {
            if (!savePage) {
                sBTN.setVisible(false);
                for (int i = 0; i < ml.howManyFiles(); i++) {
                    if (loads[i] != null) {
                        loads[i].setVisible(false);
                    }
                }
                g.setColor(Color.white);
                g.setFont(new Font("Ink Free", Font.BOLD, 50));
                FontMetrics metrics1 = getFontMetrics(g.getFont());
                g.drawString("Paused", (SCREEN_WIDTH - metrics1.stringWidth("Paused")) / 2, g.getFont().getSize() + 200);
                saveBtn.setFont(new Font("Ink Free", Font.BOLD, 20));
                saveBtn.setBackground(Color.white);
                saveBtn.setFocusable(false);
                saveBtn.setBounds(300 - 145 / 2, 400, 160, 50);
                saveBtn.setVisible(true);
            } else {
                saveBtn.setVisible(false);
                sBTN.setVisible(true);
                for (int i = 0; i < ml.howManyFiles(); i++) {
                    if (loads[i] != null) {
                        loads[i].setVisible(true);
                    }
                }
            }
        }

    }

    public void newRowBlocks() {
        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            blocks.add(new Block(i * UNIT_SIZE * 2, 0));
            if (time <= 100) {
                blocks.add(new Block(i * UNIT_SIZE * 2, BLOCK_HEIGHT * 2));
                blocks.add(new Block(i * UNIT_SIZE * 2, BLOCK_HEIGHT * 4));
            }
        }
    }

    public void moveBall(Ball ball) {
        int max = 0;
        int min = 600;
        for (int i = 0; i < shipSize; i++) {
            max = Math.max(shipX[i], max);
            min = Math.min(shipX[i], min);
        }
        if (min - BALL_UNIT <= ball.x && ball.x <= max + BALL_UNIT && ball.y >= SCREEN_HEIGHT - UNIT_SIZE * 2 && ball.y <= SCREEN_HEIGHT) {
            ball.gradientY = -1;
        }
        for (Block block : prizes) {
            Rectangle blockrec = new Rectangle(block.blockX, block.blockY, BLOCK_WIDTH, BLOCK_HEIGHT);
            Rectangle balls = new Rectangle(ball.x, ball.y, BALL_WIDTH, BALL_HEIGHT);
            if (blockrec.intersects(balls)) {
                points += 10;
                if (block.power == 0) {
                    ball.isFireBall = true;
                    ball.fireTime = 50;
                    System.out.println("Fireball");
                } else {
                    if (block.power == 7) {
                        Random random = new Random();
                        dropDown(random.nextInt(5) + 1);
                    } else {
                        dropDown(block.power);
                    }
                }
                prizes.remove(block);
                break;
            }
        }
        for (Block block : blocks) {
            if (block.blockY >= SCREEN_HEIGHT - UNIT_SIZE * 2){
                running = false;
            }
            Rectangle blockrec = new Rectangle(block.blockX, block.blockY, BLOCK_WIDTH, BLOCK_HEIGHT);
            Rectangle balls = new Rectangle(ball.x, ball.y, BALL_WIDTH, BALL_HEIGHT);
            if (blockrec.intersects(balls) && block.visible) {
                points ++;
                if (!ball.isFireBall) {
                    ball.gradientY = -ball.gradientY;
                }
                if (block.type == 0) {
                    prizes.add(block);
                }
                if (block.type != 2) {
                    blocks.remove(block);
                }
                if (block.type == 2 && ball.isFireBall){
                    blocks.remove(block);
                }
                else if (block.type == 2) {
                    block.type = 1;
                }
                break;
            }
        }

        if (ball.x >= SCREEN_WIDTH - UNIT_SIZE - 15) {
            ball.gradientX = -1;
        }
        if (ball.x <= UNIT_SIZE) {
            ball.gradientX = 1;
        }
        if (ball.y <= 20) {
            ball.gradientY = 1;
        }
        if (ball.y >= SCREEN_HEIGHT) {
            ball.isAlive = false;
        }
        ball.x += ball.gradientX * BALL_UNIT;
        ball.y += ball.gradientY * BALL_UNIT;
        for (Block block : prizes) {
            block.blockY += 1;
        }
    }

    public void dropDown(int type) {
        switch (type) {
            case 1 -> {
                if (!ball2.isAlive) {
                    ball2.live(1);
                }
                ball3.live(-1);
                System.out.println("3 toope");
            }
            case 2 -> {
                shipSize = 6;
                shipTime = 50 * 5;
                System.out.println("size bala");
            }
            case 3 -> {
                shipSize = 3;
                shipTime = 50 * 5;
                System.out.println("size payin");
            }
            case 4 -> {
                BALL_UNIT = 20;
                speedTime = 50;
                System.out.println("tond");
            }
            case 5 -> {
                BALL_UNIT = 10;
                speedTime = 50;
                System.out.println("kond");
            }
            case 6 -> {
                dizzy = true;
                dizzyTime = 50;
                System.out.println("dizzy");
            }
        }
    }

    public void move(int type) {
        for (int i = shipSize; i > 0; i--) {
            shipX[i] = shipX[i - 1];
        }
        switch (type) {
            case 1 -> shipX[0] = shipX[0] + UNIT_SIZE;
            case 2 -> shipX[0] = shipX[0] - UNIT_SIZE;
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
        g.drawString("Game Over", 300, 300);

    }

    public class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            final int a = KeyEvent.VK_RIGHT;
            final int b = KeyEvent.VK_LEFT;
            if (dizzy) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        paused = !paused;
                        saveGame();
                        break;
                    case a:
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
                    case b:
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
            } else {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        if (!savePage) {
                            paused = !paused;
                        } else {
                            savePage = !savePage;
                        }
                        break;
                    case b:
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
                    case a:
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn){
            savePage = true;
        }
        if (e.getSource() == sBTN){
            Date localDateTime = new Date();
            ml.savePlayer(saveGame(),Long.toString(localDateTime.getTime()));
        }
        for (JButton load : loads) {
            if (e.getSource() == load) {
                System.out.println(load.getName());
                ml.savePlayer(saveGame(), load.getName());

            }
        }
        if (!paused) {
            time++;
            if (running) {
                if (dizzyTime > 0){
                    dizzyTime --;
                }
                if (shipTime > 0){
                    shipTime --;
                }
                if (speedTime > 0){
                    speedTime --;
                }
                if (ball1.fireTime > 0){
                    ball1.fireTime --;
                }
                if (ball2.fireTime > 0){
                    ball2.fireTime --;
                }
                if (ball3.fireTime > 0){
                    ball3.fireTime --;
                }
                if (dizzyTime == 0){
                    dizzy = false;
                    dizzyTime = 0;
                }
                if (speedTime == 0){
                    BALL_UNIT = 15;
                }
                if (shipTime == 0){
                    shipSize = 4;
                }
                if (ball1.fireTime == 0){
                    ball1.isFireBall = false;
                }
                if (ball2.fireTime == 0){
                    ball2.isFireBall = false;
                }
                if (ball3.fireTime == 0) {
                    ball3.isFireBall = false;
                }
                if (time % 100 == 0) {
                    for (Block block : blocks) {
                        if (block.type == 3){
                            block.visible = !block.visible;
                        }
                        block.blockY += BALL_UNIT;
                    }
                }
                if (time % 600 == 0) {
                    newRowBlocks();
                }
                if (ball1.isAlive) {
                    moveBall(ball1);
                }
                if (ball2.isAlive) {
                    moveBall(ball2);
                }
                if (ball3.isAlive) {
                    moveBall(ball3);
                }
                if (!ball1.isAlive && !ball2.isAlive && !ball3.isAlive){
                    if (health != 1){
                        health--;
                        ball1.live(1);
                    } else {
                        gameOver = true;
                    }
                }
            }
        }
        else {
        }
        repaint();

    }
}
