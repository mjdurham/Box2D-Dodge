import java.awt.Graphics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class Player {
	
	private static final float RATE = 30;
	private Body body;
	private int x;
	private int y;
	private World world;
	
	Player(World world)
	{
		this.world = world;
		
		BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.STATIC;
	    bodyDef.fixedRotation = true;
	    bodyDef.position.set(20.0f/RATE, 375.0f/RATE);
	    body = world.createBody(bodyDef);
	    
	    PolygonShape box = new PolygonShape();
	    box.setAsBox(10.0f/RATE, 25.0f/RATE);
	    
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = box;
	    fixtureDef.restitution = 0.8f;
	    fixtureDef.density = 2.0f;
	    fixtureDef.friction = 0.3f;
	    body.createFixture(fixtureDef);
		
		
	}
	
	public void draw (Graphics g)
	{
		g.drawRect(10, 370, 20, 30);
		g.drawOval (10, 350, 20, 20);
	}

}
