import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.*;

public class GamePinBall {
    public static void main(String[] args) {
        JFrame f = new JFrame("PinBall Game");
        GamePanel p = new GamePanel();
        f.add(p);
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);
        //f.add(p);
    }
}
class GamePanel extends JPanel implements KeyListener{
    Ball ball;
    Paddle paddle;
    int lives = 3;
    int score = 0;
    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        ball = new Ball(100,100,10,this);
        paddle = new Paddle(350,550,100,20,this);
        new Thread(ball).start();
        new Thread(paddle).start();
        addKeyListener(this);
        
    }
    public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT)   paddle.movingLeft=true;
            if(key == KeyEvent.VK_RIGHT)   paddle.movingRight=true;
            

        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT)   paddle.movingLeft=false;
            if(key == KeyEvent.VK_RIGHT)   paddle.movingRight=false;
            
        }
        public void keyTyped(KeyEvent e) {
    // You can leave this empty too
        }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ball.draw(g);
        paddle.draw(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Lives: " + lives, 10, 20);
        g.drawString("Score: " + score, 10, 40);
    }
    
}
class Ball implements Runnable{
    int x,y,radius;
    int dx,dy;
    private Random random ;
    GamePanel panel;

    public Ball(int x,int y ,int radius,GamePanel panel){
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.random = new Random();
        dx=random.nextInt(21) -10;
        dy=random.nextInt(21) -10;
        if (dx==0) dx=1;
        if(dy==0) dy=1;
        
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x, y, radius * 2, radius * 2);
    }
    public void run(){
        while(true){
            x+=dx;
            y+=dy;

            if(x<=0 || x + radius*2 >=800) dx = -dx;
            if(y<=0) dy=-dy;
            Paddle paddle =panel.paddle;
            //collision
            if(y+radius*2 >= paddle.y &&
                x+radius*2 >= paddle.x &&
                x <= paddle.x + paddle.width ){
                    dy=-dy;
                    y= paddle.y -radius*2;
                    panel.score +=10;
                }            
            //missed
            if(y+radius*2 >= 600){
                panel.lives--;
                if(panel.lives <=0){
                    JOptionPane.showMessageDialog(panel,"Game Over");
                    System.exit(0);
                }else{
                    x=100;
                    y=100;
                    dx=new Random().nextInt(21)-10;
                    dy=new Random().nextInt(21)-10;
                    if(dx==0) dx=1;
                    if(dy==0) dy=1;  
                    
                }
                
            }
            panel.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Paddle implements Runnable {
    GamePanel panel;
    int x,y,width,height;
    int moveAmount = 5;
    boolean movingLeft = false , movingRight = false;
    public Paddle(int x,int y ,int width,int height,GamePanel panel){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.panel = panel;
        
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }
    public void moveLeft(){
        x-= moveAmount;
        if(x<0) x=0;
    }
    public void moveRight(){
        x+= moveAmount;
        if(x+width > 800 ) x=800-width;
    }
    public void run(){
        while (true) { 
            if(movingLeft) moveLeft();
            if(movingRight) moveRight();
            panel.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

