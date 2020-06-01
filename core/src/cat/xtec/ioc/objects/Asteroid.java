package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;
import cat.xtec.ioc.utils.Methods;

public class Asteroid extends Scrollable {

   private Circle collisionCircle;
   int assetAsteroid;
   boolean contact;
   Random random;

   public Asteroid(float x, float y, float width, float height, float velocity) {
      super(x, y, width, height, velocity);

      collisionCircle = new Circle();
      random = new Random();
      contact = true;

      assetAsteroid = random.nextInt(15);
      setOrigin();

      RotateByAction rotateAction = new RotateByAction();
      rotateAction.setDuration(0.2f);
      rotateAction.setAmount(-90f);

      RepeatAction repeat = new RepeatAction();
      repeat.setCount(RepeatAction.FOREVER);
      repeat.setAction(rotateAction);

      this.addAction(repeat);

   }

   @Override
   public void act(float delta) {
      super.act(delta);
      collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
   }

   @Override
   public void reset(float newX) {
      super.reset(newX);
      float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID);
      position.y = new Random().nextInt(Settings.GAME_HEIGHT - (int) height);
      assetAsteroid = random.nextInt(15);
      width = height = 34 * newSize;
      setVisible(true);
      contact = true;
      setOrigin();
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);
      batch.draw(AssetManager.asteroid[assetAsteroid], position.x, position.y, this.getOriginX(),
         this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
   }

   /* Setters ____________________________________________________________________________________*/

   public void setOrigin() {
      this.setOrigin(width / 2 + 1, height / 2);
   }

   /* Helpers ____________________________________________________________________________________*/

   public void destroy() {
      this.setVisible(false);
      contact = false;
   }

   public boolean collides(Spacecraft spacecraft) {
      return contact &&
         position.x <= spacecraft.getX() + spacecraft.getWidth() &&
         (Intersector.overlaps(collisionCircle, spacecraft.getCollisionRect()));
   }

   public boolean collides(Missile missile) {
      return contact && Intersector.overlaps(collisionCircle, missile.getCollisionArea());
   }
}
