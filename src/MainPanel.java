import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainPanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    boolean loadPage = false;
    JButton button = new JButton("Play");
    JButton button1 = new JButton("Load Game");
    JButton button2 = new JButton("Leaderboard");
    MainPanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        button.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button.setFocusable(false);
        button1.setFocusable(false);
        button2.setFocusable(false);
        button.setFont(new Font("Ink Free",Font.BOLD, 20));
        button2.setFont(new Font("Ink Free",Font.BOLD, 20));
        button1.setFont(new Font("Ink Free",Font.BOLD, 20));
        this.setLayout(null);
        button.setBackground(Color.white);
        button2.setBackground(Color.white);
        button.setBounds(300-145/2,250,160,50);
        button2.setBounds(300-145/2,450,160,50);
        button1.setBackground(Color.white);
        button1.setBounds(300-145/2,350,160,50);

        this.add(button);
        this.add(button1);
        this.add(button2);

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (!loadPage) {
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 60));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Arkanoid", (SCREEN_WIDTH - metrics1.stringWidth("Arkanoid")) / 2, g.getFont().getSize() + 100);
        } else {
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 60));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Arkanoid", (SCREEN_WIDTH - metrics1.stringWidth("Arkanoid")) / 2, g.getFont().getSize() + 100);

        }
    }
        @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            try {
                new GameFrame();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (e.getSource() == button2){
            new GameFrame("Str");
        }
    }
}
