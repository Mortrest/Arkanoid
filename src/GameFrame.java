import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;

public class GameFrame extends JFrame {
    ModelLoader ml = new ModelLoader();
    GameFrame() throws IOException {

        this.add(new GamePanel());
        this.setTitle("Arkanoid");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    GameFrame(int a){
        this.add(new MainPanel());
        this.setTitle("Arkanoid");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    GameFrame(String str) throws IOException {

        this.add(new GamePanel(str));
        this.setTitle("Arkanoid");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
