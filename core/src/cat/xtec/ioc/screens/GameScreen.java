package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.utils.Settings;

public class GameScreen implements Screen {

   public enum GameState {
      READY, RUNNING, OVER
   }

   private ScrollHandler scrollHandler;
   private GameState currentState;
   private Spacecraft spacecraft;
   private int difficulty;
   private Stage stage;
   private Batch batch;

   private float explosionTime;
   private float seconds;
   private long points;

   public static BitmapFont pointsFont;
   private GlyphLayout textMissiles;
   private GlyphLayout textLayout;
   private GlyphLayout textPoints;

   public GameScreen(Batch prevBatch, Viewport prevViewport) {

      stage = new Stage(prevViewport, prevBatch);
      AssetManager.music.play();
      batch = stage.getBatch();

      scrollHandler = new ScrollHandler();
      spacecraft = new Spacecraft(
         Settings.SPACECRAFT_START_X,
         Settings.SPACECRAFT_START_Y,
         Settings.SPACECRAFT_WIDTH,
         Settings.SPACECRAFT_HEIGHT,
         stage
      );


      spacecraft.setName("spacecraft");
      stage.addActor(scrollHandler);
      stage.addActor(spacecraft);

      textMissiles = new GlyphLayout();
      textPoints = new GlyphLayout();
      textLayout = new GlyphLayout();


      pointsFont = new BitmapFont(Gdx.files.internal("fonts/space.fnt"), true);
      pointsFont.getData().setScale(0.4f, 0.1f);

      textLayout.setText(AssetManager.font, "Are you\nready?");
      textPoints.setText(pointsFont, "0");
      currentState = GameState.READY;

      Gdx.input.setInputProcessor(new InputHandler(this));
   }

   private String rechargeIndicators() {
      return new String(new char[(int) spacecraft.getRecharge()]).replace("\0", "-");
   }

   @Override
   public void show() {

   }

   @Override
   public void render(float delta) {

      // Dibuixem tots els actors de l'stage
      stage.draw();

      // Depenent de l'estat del joc farem unes accions o unes altres
      switch (currentState) {

         case OVER:
            updateGameOver(delta);
            break;
         case RUNNING:
            updateRunning(delta);
            break;
         case READY:
            updateReady();
            break;
      }
   }

   private void updateReady() {

      // Dibuixem el text al centre de la pantalla
      batch.begin();
      AssetManager.font.draw(
         batch,
         textLayout,
         (Settings.GAME_WIDTH / 2) - textLayout.width / 2,
         (Settings.GAME_HEIGHT / 2) - textLayout.height / 2
      );
      //stage.addActor(textLbl);
      batch.end();

   }

   private void updateRunning(float delta) {
      stage.act(delta);

      if ((seconds += delta) >= 1) {
         points += 10 * (difficulty + 1);
         seconds = 0;
      }

      batch.begin();
      textPoints.setText(pointsFont, points + "");
      AssetManager.font.draw(
         batch,
         textPoints,
         5,
         5
      );
      batch.end();

      batch.begin();
      textMissiles.setText(pointsFont, rechargeIndicators());
      AssetManager.font.draw(
         batch,
         textMissiles,
         5,
         Settings.GAME_HEIGHT - 10
      );
      batch.end();

      if (scrollHandler.collides(spacecraft)) {
         AssetManager.explosionSound.play();
         stage.getRoot().findActor("spacecraft").remove();
         textLayout.setText(AssetManager.font, "Game Over :'(");
         currentState = GameState.OVER;
      }
   }

   private void updateGameOver(float delta) {
      stage.act(delta);

      batch.begin();
      AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2,
         (Settings.GAME_HEIGHT - textLayout.height) / 2);
      // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
      batch.draw(AssetManager.explosionAnim.getKeyFrame(explosionTime, false),
         (spacecraft.getX() + spacecraft.getWidth() / 2) - 32,
         spacecraft.getY() + spacecraft.getHeight() / 2 - 32, 64, 64);
      batch.end();

      explosionTime += delta;

   }

   public void reset() {

      textLayout.setText(AssetManager.font, "Are you\nready?");
      scrollHandler.reset();
      spacecraft.reset();

      currentState = GameState.READY;
      stage.addActor(spacecraft);
      explosionTime = 0.0f;
      points = 0;
   }


   @Override
   public void resize(int width, int height) {

   }

   @Override
   public void pause() {

   }

   @Override
   public void resume() {

   }

   @Override
   public void hide() {

   }

   @Override
   public void dispose() {

   }

   public Spacecraft getSpacecraft() {
      return spacecraft;
   }

   public Stage getStage() {
      return stage;
   }

   public ScrollHandler getScrollHandler() {
      return scrollHandler;
   }

   public GameState getCurrentState() {
      return currentState;
   }

   public void setCurrentState(GameState currentState) {
      this.currentState = currentState;
   }
}
