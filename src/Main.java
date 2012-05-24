
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import java.applet.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Applet implements Runnable {
	
	/**
	 * 
	 */
	
	public static final float RATE = 30;
	
	private static final long serialVersionUID = 1L;
	
	int x_pos = 10;
	int y_pos = 100;
	int radius = 20;
	
	
	private Image dbImage;
	private Graphics dbg;
	
	
	
    Vec2 gravity = new Vec2(0.0f, 10.0f);
    boolean doSleep = true;
    World world = new World(gravity, doSleep);
    
    ArrayList<Ball> balls = new ArrayList<Ball>();
    
    
	
	public void init()
	{
		resize(600,400);
		
		setBackground (Color.black);


	    // Make a Body for the ground via definition and shape binding that gives it a boundary
	    // 
	    BodyDef groundBodyDef = new BodyDef(); // body definition
	    groundBodyDef.position.set(300.0f/RATE, 400.0f/RATE); // set bodydef position
	    Body groundBody = world.createBody(groundBodyDef); // create body based on definition
	    PolygonShape groundBox = new PolygonShape(); // make a shape representing ground
	    groundBox.setAsBox(300.0f/RATE, 0); // shape is a rect: 100 wide, 20 high
	    groundBody.createFixture(groundBox, 0.0f); // bind shape to ground body
	    

	}

	public void start ()
	{
		Thread th = new Thread (this);
		th.start ();
	}

	public void stop()
	{

	}

	public void destroy()
	{

	}

	public void run ()
	{
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		
		while (true)
		{
			x_pos ++;

			
			
		    // Simulate the world
		    //
		    float timeStep = 1.0f / 60.0f;
		    int velocityIterations = 6;
		    int positionIterations = 2;
		    
		    world.step(timeStep, velocityIterations, positionIterations);
		    
//		    System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle);
		    
		    if (x_pos % 50 == 0)
		    	balls.add(new Ball(world));

		    
		    repaint();

			try
			{
				Thread.sleep (10);
			}
			catch (InterruptedException ex)
			{
				// do nothing
			}

			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}


	public void paint (Graphics g)
	{


		Iterator<Ball> itr = balls.iterator();
		while (itr.hasNext()) {
		   Ball b = itr.next();
		   b.DrawBall(g);
	    	if (b.shouldDelete())
	    		itr.remove();
	    	
		}

		
	}
	
	
	public void update (Graphics g)
	{
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		dbg.setColor (getForeground());
		paint (dbg);

		g.drawImage (dbImage, 0, 0, this);
	}
	

}
