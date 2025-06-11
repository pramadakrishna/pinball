import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class pinball extends JFrame{
    public pinball(){
        setTitle("PinBall Game");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        setVisible(true);
    }
    public static void main(String[] args) {
        new pinball();
    }
}

 class GamePanel extends JPanel implements KeyListener{
    Ball ball;
    Paddle paddle;
    int lives = 3;
    GamePanel() {
        setBackground(Color.BLACK);
        ball = new Ball(100,100,10);
        ball.setPanel(this);
        paddle = new Paddle(350,550,100,20);
        setFocusable(true);
        addKeyListener(this);
        ball.setPaddle(paddle);
        new Thread(ball).start();
        new Thread(paddle).start();


    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ball.draw(g);
        paddle.draw(g);
        g.setColor(Color.WHITE);
        g.drawString("Lives : "+lives,10, 20);
    }
    public void keyPressed(KeyEvent e) {
        paddle.keyPressed(e); 
    }
    
    public void keyReleased(KeyEvent e) {
        paddle.keyReleased(e); 
    }
    public void keyTyped(KeyEvent e) {
        // Empty implementation, as keyTyped is not needed for paddle movement
    }
}

 class Ball implements Runnable{
    int x,y,radius;
    int dx,dy;
    GamePanel panel;
    private Random random;
    private Paddle paddle;
    public Ball(int x,int y,int radius){
        this.x=x;
        this.y=y;
        this.radius = radius;
        this.random = new Random();
        dx=random.nextInt(21) - 10;
        dy=random.nextInt(21) - 10;
        if (dx==0) dx=1;
        if(dy==0) dy=1;
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x, y, radius*2, radius*2);
    }
    public void setPaddle(Paddle paddle){
        this.paddle = paddle;
    }
    public void run(){
        while (true) { 
            x+=dx;
            y+=dy;

            if(x<=0 || x+radius * 2 >=800) dx= -dx;
            if(y<=0 ) dy= -dy;
            if( dy>0 && y+radius * 2 >=paddle.y && 
                x+radius *2 >= paddle.x && x<=paddle.x +paddle.width ){
                    dy=-dy;
                    y=paddle.y - radius*2;
            } 
            if(y+radius*2>=600){
                panel.lives--;
                if(panel.lives <=0){
                    JOptionPane.showMessageDialog(panel,"Game Over");
                    System.exit(0);
                }else{
                    resetPosition();
                }
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(()->{
                if(panel != null) panel.repaint();
            });
        }
    }
    public void setPanel(GamePanel panel){
        this.panel=panel;
    }

    private void resetPosition(){
        x=100;
        y=100;
        dx=random.nextInt(21)-10;
        dy=random.nextInt(21)-10;
        if(dx==0) dx=1;
        if(dy==0) dy=1;
    }
}

class Paddle implements Runnable{
    int x,y,width,height;
    private boolean leftPressed = false,rightPressed=false;
    public static final int SPEED = 5;
    private GamePanel panel;
    public Paddle(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width,height);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_LEFT) leftPressed = true;
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) rightPressed = true;
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_LEFT) leftPressed = false;
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) rightPressed = false;
    }
    public void setPanel(GamePanel panel) {
        this.panel = panel;
    }
    public void run(){
        while (true) { 
            if(leftPressed && x>0) x-=SPEED;
            if (rightPressed && x + width < 800) x += SPEED;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(()->{
                if(panel != null) panel.repaint();
            });
        }
    }
} 