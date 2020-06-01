package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

import cat.xtec.ioc.helpers.AssetManager;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Missile extends Actor {

   private ScrollHandler scrollHandler;
   private Rectangle collisionArea;
   private int width, height;
   private Vector2 position;

   public Missile(float x, float y, int width, int height, ScrollHandler scrollHandler) {
      position = new Vector2(x, y);
      setBounds(position.x, position.y, width, height);
      this.scrollHandler = scrollHandler;
      collisionArea = new Rectangle();
      this.height = height;
      this.width = width;
   }

   /* Behavior ___________________________________________________________________________________*/

   @Override
   public void act(float delta) {
      super.act(delta);
      collisionArea.set(position.x += 400 * delta, position.y, width, height + 2);
      hits(scrollHandler.getAsteroids());
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);
      batch.draw(getMissileTexture(), position.x, position.y, width, height);
   }

   /* Helpers ____________________________________________________________________________________*/

   public boolean hits(List<Asteroid> asteroids) {

      for (Asteroid asteroid : asteroids)
         if (asteroid.collides(this)) {
            asteroid.destroy();
            this.remove();
            return true;
         }

      return false;
   }

   /* Getters ____________________________________________________________________________________*/

   public TextureRegion getMissileTexture() {
      return AssetManager.missile;
   }

   public Rectangle getCollisionArea() {
      return collisionArea;
   }
}

