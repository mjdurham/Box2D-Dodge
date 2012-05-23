import java.awt.Color;
import java.awt.Graphics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Ball {
	
	private static final float RATE = 30;
	private Body body;
	private int x;
	private int y;
	private World world;
	private int radius;
	
	Ball (World world){
		
		this.world = world;
		
		BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DYNAMIC;
	    bodyDef.position.set(0.0f/RATE, 0.0f/RATE);
	    body = world.createBody(bodyDef);
	    
	    CircleShape circle = new CircleShape();
	    radius = (int) (Math.random()*30+15);
	    circle.m_radius = radius/RATE;
	    
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = circle;
	    fixtureDef.restitution = 0.8f;
	    fixtureDef.density = 2.0f;
	    fixtureDef.friction = 0.3f;
	    body.createFixture(fixtureDef);
	    
		Vec2 ballVec = new Vec2((float) (Math.random()*10),0.0f);
		body.setLinearVelocity(ballVec);
	}
	
	public void DrawBall (Graphics g)
	{
		Vec2 position = body.getPosition();
		x = (int)(position.x*RATE-radius);
		y = (int)(position.y*RATE-radius);
	    g.setColor (Color.WHITE);
	    g.drawOval(x, y, radius*2, radius*2);
	    
	}
	
	public boolean shouldDelete(){
		if (y > 500){
			world.destroyBody(body);
			return true;
		}else{
			return false;
		}
	}
	

}