import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MainPanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    boolean loadPage = false;
    ModelLoader ml = new ModelLoader();
    JButton button = new JButton("Play");
    JButton button1 = new JButton("Load Game");
    JButton button2 = new JButton("Leaderboard");
    JButton[] loads = new JButton[5];
    Timer timer;
    boolean leaderboard = false;

    MainPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        button.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button.setFocusable(false);
        button1.setFocusable(false);
        button2.setFocusable(false);
        button.setFont(new Font("Ink Free", Font.BOLD, 20));
        button2.setFont(new Font("Ink Free", Font.BOLD, 20));
        button1.setFont(new Font("Ink Free", Font.BOLD, 20));
        this.setLayout(null);
        button.setBackground(Color.white);
        button2.setBackground(Color.white);
        button.setBounds(300 - 145 / 2, 250, 160, 50);
        button2.setBounds(300 - 145 / 2, 450, 160, 50);
        button1.setBackground(Color.white);
        button1.setBounds(300 - 145 / 2, 350, 160, 50);
        this.addKeyListener(new MyKey());
        this.add(button);
        this.add(button1);
        this.add(button2);


        String[] names = ml.names();
        for (int i = 0; i < ml.howManyFiles(); i++) {
            loads[i] = new JButton(names[i]);
            loads[i].setName(names[i]);
            loads[i].addActionListener(this);
            loads[i].setFocusable(false);
            loads[i].setBackground(Color.white);
            loads[i].setFont(new Font("Ink Free", Font.BOLD, 20));
            loads[i].setBounds(300 - 145 / 2, i * 100 + 70, 160, 50);
            loads[i].setVisible(false);
            this.add(loads[i]);
        }
        timer = new Timer(50, this);
        timer.start();


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) throws IOException {
        if (leaderboard){
            button.setVisible(false);
            button1.setVisible(false);
            button2.setVisible(false);
            g.setColor(Color.white);
            g.setFont( new Font("Ink Free",Font.BOLD, 50));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Leaderboard", (SCREEN_WIDTH - metrics1.stringWidth("Leaderboard"))/2, g.getFont().getSize() );
            g.setFont( new Font("Ink Free",Font.BOLD, 30));
            metrics1 = getFontMetrics(g.getFont());
            TreeMap<Integer,String> a = sort();
            int i = 0;
            for (Map.Entry<Integer, String> entry : a.entrySet()) {
                i++;
                Integer key = entry.getKey();
                String value = entry.getValue();
                g.drawString(value+ ": " + key, (SCREEN_WIDTH - metrics1.stringWidth(value+ ": " + key))/2, g.getFont().getSize() + i*50 + 100);

            }
        }
        else if (!loadPage) {
            button.setVisible(true);
            button1.setVisible(true);
            button2.setVisible(true);
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 60));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Arkanoid", (SCREEN_WIDTH - metrics1.stringWidth("Arkanoid")) / 2, g.getFont().getSize() + 100);

        }
        else if (loadPage){
            button1.setVisible(false);
            button2.setVisible(false);
            button.setVisible(false);
            for (int i = 0; i < ml.howManyFiles(); i++) {
                if (loads[i] != null) {
                    loads[i].setVisible(true);
                }
            }
        }
    }
    public TreeMap<Integer,String> sort() throws IOException {
        String str = ml.getUserFile("archive.txt");
        TreeMap<Integer,String> treeMap = new TreeMap();
        Scanner ss = new Scanner(str);
        while (ss.hasNext()) {
            String a = ss.next();
            Integer b = ss.nextInt();
            treeMap.put(b,a);
        }
        return treeMap;
    }


    public class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_ESCAPE:
                    leaderboard = false;
                    break;
            }
        }
        }

            @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            try {
                new GameFrame();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (e.getSource() == button1){
            loadPage = true;
        }
        if (e.getSource() == button2) {
            leaderboard = true;
        }
        for (JButton load : loads) {
            if (e.getSource() == load) {
                try {
                    new GameFrame(ml.getUserFile(load.getName()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        repaint();
    }
}
