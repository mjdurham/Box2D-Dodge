import java.awt.Graphics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class Player {
	
	private static final float RATE = 30;
	private Body body;
	
	Player(World world)
	{
		
		BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DYNAMIC;
	    bodyDef.fixedRotation = true;
	    bodyDef.position.set(20.0f/RATE, 375.0f/RATE);
	    body = world.createBody(bodyDef);
	    
	    PolygonShape box = new PolygonShape();
	    box.setAsBox(10.0f/RATE, 25.0f/RATE);
	    
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = box;
	    fixtureDef.density = 2.0f;
	    fixtureDef.friction = 0.3f;
	    body.createFixture(fixtureDef);
		
	    body.setLinearVelocity(new Vec2(5.0f,0.0f));
	}
	
	public void draw (Graphics g)
	{
		Vec2 position = body.getPosition();
		g.drawRect((int) (position.x*RATE - 10), 370, 20, 30);
		g.drawOval ((int) (position.x*RATE - 10), 350, 20, 20);
	}

}
