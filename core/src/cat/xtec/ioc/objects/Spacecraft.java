package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Spacecraft extends Actor {

   public static final int SPACECRAFT_STRAIGHT = 0;
   public static final int SPACECRAFT_DOWN = 2;
   public static final int SPACECRAFT_UP = 1;

   private int width, height;
   private Vector2 position;
   private float recharge;
   private int direction;

   private Rectangle collisionRect;
   private Stage stage;

   public Spacecraft(float x, float y, int width, int height, Stage stage) {

      this.stage = stage;
      this.width = width;
      this.height = height;
      position = new Vector2(x, y);
      direction = SPACECRAFT_STRAIGHT;
      collisionRect = new Rectangle();
      setTouchable(Touchable.enabled);
      recharge = Settings.missileCount;
      setBounds(position.x, position.y, width, height);
   }

   @Override
   public void act(float delta) {

      switch (direction) {

         case SPACECRAFT_UP:
            if (position.y - Settings.SPACECRAFT_VELOCITY * delta >= 0)
               position.y -= Settings.SPACECRAFT_VELOCITY * delta;
            break;

         case SPACECRAFT_DOWN:
            if (position.y + height + Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_HEIGHT)
               position.y += Settings.SPACECRAFT_VELOCITY * delta;
            break;

         case SPACECRAFT_STRAIGHT:
            break;
      }

      collisionRect.set(position.x, position.y + 3, width, 10);
      setBounds(position.x, position.y, width, height);

      if (recharge < Settings.missileCount) recharge += delta;
   }

   public float getWidth() {
      return width;
   }

   public float getHeight() {
      return height;
   }

   public float getX() {
      return position.x;
   }

   public float getY() {
      return position.y;
   }

   public void goUp() {
      direction = SPACECRAFT_UP;
   }

   public void goDown() {
      direction = SPACECRAFT_DOWN;
   }

   public void goStraight() {
      direction = SPACECRAFT_STRAIGHT;
   }

   public TextureRegion getSpacecraftTexture() {

      switch (direction) {
         case SPACECRAFT_STRAIGHT:
            return AssetManager.spacecraft;
         case SPACECRAFT_UP:
            return AssetManager.spacecraftUp;
         case SPACECRAFT_DOWN:
            return AssetManager.spacecraftDown;
         default:
            return AssetManager.spacecraft;
      }
   }

   public void reset() {
      position.x = Settings.SPACECRAFT_START_X;
      position.y = Settings.SPACECRAFT_START_Y;
      recharge = Settings.missileCount;
      direction = SPACECRAFT_STRAIGHT;
      collisionRect = new Rectangle();
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);
      batch.draw(
         getSpacecraftTexture(),
         position.x,
         position.y,
         width,
         height
      );
   }

   public void shoot(ScrollHandler scrollHandler) {
      if (recharge >= 1) {
         stage.addActor(
            new Missile(
               this.getX() + this.getWidth(),
               (this.getY() + this.getHeight() / 2),
               52,
               20,
               scrollHandler
            )
         );
         --recharge;
      }
   }

   public Rectangle getCollisionRect() {
      return collisionRect;
   }

   public float getRecharge() {
      return recharge;
   }
}
