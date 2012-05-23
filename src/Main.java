
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
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
    int[] landscape = new int[13];
    
    
	
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

	    // Make another Body that is dynamic, and will be subject to forces.
	    //
//	    BodyDef bodyDef = new BodyDef();
//	    bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
//	    bodyDef.position.set(225.0f/RATE, 400.0f/RATE);
//	    body = world.createBody(bodyDef);
//	    PolygonShape dynamicBox = new PolygonShape();
//	    dynamicBox.setAsBox(25.0f/RATE, 25.0f/RATE);
//	    FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
//	    fixtureDef.shape = dynamicBox; // ... its shape is the dynamic box (2x2 rectangle)
//	    fixtureDef.density = 1.0f; // ... its density is 1 (default is zero)
//	    fixtureDef.friction = 0.3f; // ... its surface has some friction coefficient
//	    fixtureDef.restitution = 0.4f;
//	    body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
	    

	    
	    landscape[0] = (int) (Math.random() * 200) + 200;
	    for (int i=1; i<landscape.length; i++){
	    	do
	    		landscape[i] =  landscape[i-1] + (int) (Math.random() * 100) - 50;
	    	while (landscape[i] <= 200 || landscape[i] >= 400);
	    }
	    
	    
	    BodyDef floor = new BodyDef();
	    floor.position.set(0, 0);
	    
	    Body floorBody = world.createBody(floor);
	 
	    PolygonShape shape = new PolygonShape();
	 
	    Vec2 p1; 
	    Vec2 p2;
	    for (int i=0; i< landscape.length -1; i++) {
	    	
	    	
	    	p1 = new Vec2(i*50/RATE, landscape[i]/RATE);
	    	p2 = new Vec2((i+1)*50/RATE, landscape[i+1]/RATE);
	    	shape.setAsEdge(p1, p2);
	    	FixtureDef fixtureDef = new FixtureDef();
		    fixtureDef.shape = shape;
	    	floorBody.createFixture(fixtureDef);
	    }

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
		
		for (int i=0; i<landscape.length-1; i++){
			g.drawLine(i*50, landscape[i], (i+1)*50, landscape[i+1]);
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
