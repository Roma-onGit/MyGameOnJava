import java.awt.Button;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Dimension;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import javax.swing.Timer;
import java.awt.Font;

public class MyGame1 extends JFrame  {
	
	Color[] colors = {Color.red, Color.orange, Color.YELLOW, Color.green,
			Color.blue, Color.pink, Color.cyan, Color.magenta};
    JPanel buttonBar;
    JButton red, orange, yellow, green, blue, pink, cyan, magenta;
    JButton[] buttons = {red, orange, yellow, green, blue, pink, cyan, magenta}; 
	
	JButton startButton;
	Color chosenColor;  //it for convey chosen color to back of center panel
	int colorShift;   //correct position of color for any chosen color
	int angle;        //shows position of circle
    int shift;        //it for making circle rotate by shifting start angle
    int timerDelay = 5;
    AnimatedCircle ac = new AnimatedCircle();
    Timer timer = new Timer(timerDelay, ac);
    
    boolean isSlowing;   //it is for making circle stop in "slower" method
    
     MyGame1()  {
    	 initUI();
     }
     
     private void initUI()  {
    	 makeButtonBar();
    	 makeStartButton();
    	 add(BorderLayout.EAST, buttonBar);
    	 add(BorderLayout.CENTER, ac);
    	 setTitle("My rough game (Alin edition)");
    	 setSize(500, 400);
    	 setLocationRelativeTo(null);
    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     
     void makeFlash(int a)  {
    	 if(a == 1)
        ac.setBackground(chosenColor);
    	 if(a == 0)
    		 ac.setBackground(Color.white);
     }
     
     void makeButtonBar()  {
    	 buttonBar = new JPanel();
    	 //buttonBar.setBackground(new Color(150, 250, 50));
    	 buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.Y_AXIS));
    	 //buttonBar.add(Box.createRigidArea(new Dimension(20, 0)));
    	 buttonBar.add(Box.createRigidArea(new Dimension(0, 10)));  //makes gap between top of frame
    	 SideButtonListener listener = new SideButtonListener();      //and first button
    	 for(int i = 0; i < 8; i ++)  {
    		 buttons[i] = new JButton("                          ");
    		 buttons[i].setBackground(colors[i]);
    		 buttons[i].addActionListener(listener);
    		 buttonBar.add(buttons[i]);
    		 if(i < 7)
    		 buttonBar.add(Box.createRigidArea(new Dimension(0, 15)));
    	 }
    	 buttonBar.add(Box.createRigidArea(new Dimension(15, 0)));
     }
     
     void makeStartButton()  {    //start button
    	 startButton = new JButton("Start");
    	 startButton.setFont(new Font("Purisa", Font.BOLD, 20));
    	 startButton.addActionListener(new StartButtonListener());
    	 add(BorderLayout.SOUTH, startButton);
     }
     
     private void slower()  {
    	 if(isSlowing)  {
    		 timerDelay += 10;
    		 if(timerDelay > 1100) {
    			 timer.stop();
    			 timerDelay = 5;
    		 }
    	 }
     }
     
     class SideButtonListener implements ActionListener  { //listener for colored buttons
    	 @Override                                         //inner class
    	 public void actionPerformed(ActionEvent event)  {
    		 for(int i = 0; i < 8; i ++)  {
    			 if(event.getSource() == buttons[i])  {
    				 startButton.setBackground(colors[i]);
    				 startButton.setForeground(Color.white);
    				 chosenColor = colors[i];
    				 colorShift = (i) * 45;
    			 }
    		 }
    	 }
     }
     
     class StartButtonListener implements ActionListener  {  //actionListener for start button
    	 
    	 @Override
    	 public void actionPerformed(ActionEvent e)  {
    		if(timer.isRunning())  {
    			 isSlowing = true;  //timer.stop();
    			 startButton.setFont(new Font("Purisa", Font.BOLD, 20));
    			 startButton.setText("Start" + angle);
    			 if(angle > 205 && angle < 250)  {      //if angle is right 205 - 250
    				 Font bigFont = new Font("Purisa", Font.PLAIN, 30);
    				 startButton.setFont(bigFont);
    				 startButton.setText("Masha win!");
    				 makeFlash(1);
    			 }
    		 } else {
    			 timer.start();
    			 startButton.setText("Stop");
    			 isSlowing = false;
    			 makeFlash(0);
    		 }
    		 
    	 }
     }
                                                                       //inner class
     class AnimatedCircle extends JPanel implements ActionListener  {  //making animated circle here
    	 
    	 AnimatedCircle()  {
    		
    	 }
    	 
    	 private void doDrawing(Graphics g)  {
    	     Graphics2D g2d = (Graphics2D)g.create();
    	     
    	     RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
    	    		 RenderingHints.VALUE_ANTIALIAS_ON);
    	     rh.put(RenderingHints.KEY_RENDERING,
    	    		 RenderingHints.VALUE_RENDER_QUALITY);
    	     
    	     for(int i = 0, j = 0; i < 8; i ++)  {
    	    	 
    	    	 g2d.setPaint(colors[i]);       //color[i]
    	    	 g2d.fillArc(35, 15, 300, 300, j + shift, 45);
    	    	 j +=45;
    	    	 angle = (j + shift + colorShift) % 360;   //it shows position of circle
    	     }
    	     g2d.dispose();
    	 }
    	 
    	 @Override
    	 public void actionPerformed(ActionEvent e)  {
    		 doStep();
    		 slower();
    		 MyGame1.this.repaint(); //this calls repaint on outer class !!
    	 }
    	 
    	 @Override
    	 public void paintComponent(Graphics g)  {
    		 super.paintComponent(g);
    		 doDrawing(g);
    	 }
    	 
    	 private void doStep()  {
    		 shift += 10;
    	 }
     }
     
     public static void main(String[] args)  {
    	 EventQueue.invokeLater(new Runnable()  {
    		 @Override
    		 public void run()  {
    			 MyGame1 m = new MyGame1();
    			 m.setVisible(true);
    		 }
    	 });
     }
         
}
