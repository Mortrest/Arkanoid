import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Leaderboard extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    Leaderboard(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont( new Font("Ink Free",Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Leaderboard", (SCREEN_WIDTH - metrics1.stringWidth("Leaderboard"))/2, g.getFont().getSize() );
        g.setFont( new Font("Ink Free",Font.BOLD, 30));
        metrics1 = getFontMetrics(g.getFont());

        g.drawString("Ali: 300", (SCREEN_WIDTH - metrics1.stringWidth("Ali: 300"))/2, g.getFont().getSize() +150);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
