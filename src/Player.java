import java.awt.Graphics;
import java.awt.event.KeyEvent;

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
	private boolean playerMoveLeft = false;
	private boolean playerMoveRight = false;
	
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
	    
	}
	
	public void draw (Graphics g)
	{
		Vec2 position = body.getPosition();
		g.drawRect((int) (position.x*RATE - 10), 370, 20, 30);
		g.drawOval ((int) (position.x*RATE - 10), 350, 20, 20);
		
		// move the character in this method because it gets call every time
		Vec2 vel = body.getLinearVelocity();
		if (playerMoveLeft == true){
			vel.x = -5;
		}else if (playerMoveRight == true){
			vel.x = 5;
		}else{
			vel.x = 0;
		}
		body.setLinearVelocity(vel);
	}
	
	public void keyPress (KeyEvent key){
		
		if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			playerMoveLeft = true;

		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			playerMoveRight = true;
		}
		  
	}
	
	public void keyRelease (KeyEvent key){

		if (key.getKeyCode() == KeyEvent.VK_LEFT) {
			playerMoveLeft = false;

		} else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
			playerMoveRight = false;
		}
		
	}

}
