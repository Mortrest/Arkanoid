import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Leaderboard extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    ModelLoader ml = new ModelLoader();
    Leaderboard(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
