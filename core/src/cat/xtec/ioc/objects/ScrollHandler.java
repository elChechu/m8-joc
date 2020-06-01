package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import cat.xtec.ioc.utils.Settings;
import cat.xtec.ioc.utils.Methods;

public class ScrollHandler extends Group {

   private List<Asteroid> asteroids;
   Background bg_front, bg_back;
   Random r;

   public ScrollHandler() {

      r = new Random();

      // Backgrounds
      addActor(bg_front = new Background(
         0,
         0,
         Settings.GAME_WIDTH * 2,
         Settings.GAME_HEIGHT,
         Settings.BG_SPEED
      ));
      addActor(bg_back = new Background(
         bg_front.getTailX(),
         0,
         Settings.GAME_WIDTH * 2,
         Settings.GAME_HEIGHT,
         Settings.BG_SPEED
      ));

      // Asteroids
      asteroids = new ArrayList<Asteroid>();

      float size = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
      Asteroid asteroid;
      asteroids.add(asteroid = new Asteroid(
         Settings.GAME_WIDTH,
         r.nextInt(Settings.GAME_HEIGHT - (int) size),
         size,
         size,
         Settings.asteroidSpeed
      ));
      addActor(asteroid);

      for (int i = 1; i < Settings.asteroidCount; i++) {
         size = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
         asteroids.add(
            asteroid = new Asteroid(
               asteroids.get(asteroids.size() - 1).getTailX() + Settings.asteroidGap,
               r.nextInt(Settings.GAME_HEIGHT - (int) size),
               size,
               size,
               Settings.asteroidSpeed
            )
         );
         addActor(asteroid);
      }
   }

   @Override
   public void act(float delta) {
      super.act(delta);

      if (bg_front.isLeftOfScreen())
         bg_front.reset(bg_back.getTailX());

      else if (bg_back.isLeftOfScreen())
         bg_back.reset(bg_front.getTailX());

      Asteroid asteroid;
      for (int i = 0; i < asteroids.size(); i++)
         if ((asteroid = asteroids.get(i)).isLeftOfScreen())
            asteroid.reset(asteroids.get((i == 0 ? asteroids.size() : i) - 1).getTailX() + Settings.asteroidGap);
   }

   public boolean collides(Spacecraft spacecraft) {

      for (Asteroid asteroid : asteroids)
         if (asteroid.collides(spacecraft))
            return true;

      return false;
   }

   public void reset() {
      asteroids.get(0).reset(Settings.GAME_WIDTH);
      for (int i = 1; i < asteroids.size(); i++)
         asteroids.get(i).reset(asteroids.get(i - 1).getTailX() + Settings.asteroidGap);
   }

   public List<Asteroid> getAsteroids() {
      return asteroids;
   }
}