package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.screens.GameScreen;

public class InputHandler implements InputProcessor {

   private Vector2 stageCoordinates;
   private Spacecraft spacecraft;
   private GameScreen screen;
   private Stage stage;
   int previousY = 0;

   public InputHandler(GameScreen screen) {
      spacecraft = screen.getSpacecraft();
      stage = screen.getStage();
      this.screen = screen;
   }

   @Override
   public boolean keyUp(int keycode) {
      return false;
   }

   @Override
   public boolean keyDown(int keycode) {
      return false;
   }

   @Override
   public boolean keyTyped(char character) {
      return false;
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button) {

      switch (screen.getCurrentState()) {

         case READY:
            screen.setCurrentState(GameScreen.GameState.RUNNING);
            break;

         case RUNNING:
            previousY = screenY;
            stageCoordinates = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
            Actor actorHit = stage.hit(stageCoordinates.x, stageCoordinates.y, true);

            if (actorHit != null)
               Gdx.app.log("HIT", actorHit.getName());

            spacecraft.shoot(screen.getScrollHandler());
            break;

         case OVER:
            screen.reset();
            break;
      }

      return true;
   }

   @Override
   public boolean touchUp(int screenX, int screenY, int pointer, int button) {

      // Quan deixem anar el dit acabem un moviment
      // i posem la nau en l'estat normal
      spacecraft.goStraight();
      return true;
   }


   @Override
   public boolean touchDragged(int screenX, int screenY, int pointer) {
      // Posem un umbral per evitar gestionar events quan el dit està quiet
      if (Math.abs(previousY - screenY) > 2)

         // Si la Y és major que la que tenim
         // guardada és que va cap avall
         if (previousY < screenY) {
            spacecraft.goDown();
         } else {
            // En cas contrari cap a dalt
            spacecraft.goUp();
         }
      // Guardem la posició de la Y
      previousY = screenY;
      return true;
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
