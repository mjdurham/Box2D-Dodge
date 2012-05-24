
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
	
	// serial UID for java applet
	private static final long serialVersionUID = 1L;
	
	// rate to convert meters to pixels in the physics engine
	public static final float RATE = 30;
	
	// counts how many loops are made in the main thread
	private int counter = 49;
	
	// image and graphics used for double buffering
	private Image dbImage;
	private Graphics dbg;
	
	
	// variables for the Box2D world
    Vec2 gravity = new Vec2(0.0f, 10.0f);
    boolean doSleep = true;
    World world = new World(gravity, doSleep);
    
    // new array list to hold Ball references
    ArrayList<Ball> balls = new ArrayList<Ball>();
    
    
	
	public void init()
	{
		
		// define size for applet
		resize(600,400);
		
		setBackground (Color.black);


	    // add a ground floor to our Box2D world
	    BodyDef groundBodyDef = new BodyDef();
	    groundBodyDef.position.set(300.0f/RATE, 400.0f/RATE);
	    Body groundBody = world.createBody(groundBodyDef);
	    PolygonShape groundBox = new PolygonShape();
	    groundBox.setAsBox(300.0f/RATE, 0);
	    groundBody.createFixture(groundBox, 0.0f);

	}

	public void start ()
	{
		// starts a new thread
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
			counter ++;
			
		    // Simulate the world
		    float timeStep = 1.0f / 60.0f;
		    int velocityIterations = 6;
		    int positionIterations = 2;
		    world.step(timeStep, velocityIterations, positionIterations);
		    
		    // add new balls to the world every 50th loop
		    if (counter % 50 == 0)
		    	balls.add(new Ball(world));
		    
		    repaint();

		    // pause for 10 milliseconds
			try
			{
				Thread.sleep(10);
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

		// loop through each ball and call it's draw method
		Iterator<Ball> itr = balls.iterator();
		while (itr.hasNext()) {
		   Ball b = itr.next();
		   b.DrawBall(g);
		   
		   // if the ball should be removed then remove it
		   if (b.shouldDelete())
			   itr.remove();
		}
		
	}
	
	// sets up double buffering for graphics
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
