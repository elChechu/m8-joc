package cat.xtec.ioc.helpers;

/* GDX Libraries */

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

/* Custom */
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.screens.ModeScreen;
import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.utils.Settings;

public class ModeHandler implements InputProcessor {

   private Vector2 stageCoordinates;
   private SpaceRace game;
   private Stage stage;

   public ModeHandler(ModeScreen screen, SpaceRace game) {
      this.stage = screen.getStage();
      this.game = game;
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button) {

      // Catching coordinates & actor
      stageCoordinates = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
      Actor hit = stage.hit(stageCoordinates.x, stageCoordinates.y, true);
      String name;

      if (hit != null && (name = hit.getName()) != null && name.matches("(level-)[0-9]")) {
         try {
            int difficulty = Integer.parseInt(name.replaceAll("[^\\d]", "")) + 1;
            Settings.asteroidGap = (Settings.ASTEROID_GAP / difficulty) + Settings.ASTEROID_GAP / 10;
            Settings.asteroidSpeed = Settings.ASTEROID_SPEED + (-25 * difficulty);
            Settings.missileCount = Settings.MISSILE_COUNT / difficulty;
            Settings.asteroidCount = 3 * difficulty;
            Gdx.app.log("hardness", hit.getName());
         } catch (NumberFormatException ignore) {
         }
         game.setScreen(
            new GameScreen(
               stage.getBatch(),
               stage.getViewport()
            )
         );
         return true;
      }
      return false;
   }

   @Override
   public boolean keyDown(int keycode) {
      return false;
   }

   @Override
   public boolean keyUp(int keycode) {
      return false;
   }

   @Override
   public boolean keyTyped(char character) {
      return false;
   }

   @Override
   public boolean touchUp(int screenX, int screenY, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchDragged(int screenX, int screenY, int pointer) {
      return false;
   }

   @Override
   public boolean mouseMoved(int screenX, int screenY) {
      return false;
   }

   @Override
   public boolean scrolled(int amount) {
      return false;
   }
}
