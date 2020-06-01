package cat.xtec.ioc.utils;

public class Settings {

   // SPEED
   public static final int BG_SPEED = -100;
   public static final int ASTEROID_SPEED = -100;
   public static final float SPACECRAFT_VELOCITY = 50;

   // SIZES
   public static final int GAME_WIDTH = 240;
   public static final int GAME_HEIGHT = 135;
   public static final int SPACECRAFT_WIDTH = 36;
   public static final int SPACECRAFT_HEIGHT = 15;

   // DISTANCE
   public static final int ASTEROID_GAP = 75;
   public static final float SPACECRAFT_START_X = 20;
   public static final float SPACECRAFT_START_Y = GAME_HEIGHT / 2 - SPACECRAFT_HEIGHT / 2;

   // COUNT
   public static final float MAX_ASTEROID = 1.5f;
   public static final float MIN_ASTEROID = 0.5f;
   public static final int MISSILE_COUNT = 12;

   // Variables
   public static int asteroidSpeed = ASTEROID_SPEED;
   public static int missileCount = MISSILE_COUNT;
   public static int asteroidGap = ASTEROID_GAP;
   public static int asteroidCount = 3;

}
