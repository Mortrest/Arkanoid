public class Ball {
    int x;
    int y;
    final int UNIT_SIZE = 25;
    final int SCREEN_WIDTH = 600;
    double gradientX;
    int fireTime = 0;
    double gradientY;
    boolean isAlive;
    boolean isFireBall;


    Ball(int gradientX){
        this.x = UNIT_SIZE * 6;
        this.y = SCREEN_WIDTH - UNIT_SIZE * 2;
        this.gradientX = gradientX;
        this.gradientY = -1;
        this.isAlive = true;
        this.isFireBall = false;
    }
    public void live(int gradientX){
        this.isAlive = true;
        this.x = UNIT_SIZE * 6;
        this.y = SCREEN_WIDTH - UNIT_SIZE * 2;
        this.gradientX = gradientX;
        this.gradientY = -1;

    }
}
