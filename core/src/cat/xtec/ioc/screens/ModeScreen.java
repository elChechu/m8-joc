package cat.xtec.ioc.screens;

/* GDX Libraries */
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

/* Custom */
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.ModeHandler;
import cat.xtec.ioc.utils.Settings;
import cat.xtec.ioc.SpaceRace;

public class ModeScreen implements Screen {

   // Resources
   private SpaceRace game;
   private Stage stage;

   // Labels
   private Label[] labels = new Label[3];
   private Container[] containers = new Container[labels.length + 1];
   private Label.LabelStyle labelStyle;
   private Label headingLbl;

   public ModeScreen(SpaceRace game) {
      this.game = game;

      // Generating camera
      OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
      camera.setToOrtho(true);

      // Generating stage
      StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT,
         camera);
      stage = new Stage(viewport);

      // Styling
      Actor background = new Image(AssetManager.background);
      background.setName("background");
      stage.addActor(background);

      // Generating labels
      labelStyle = new Label.LabelStyle(AssetManager.font, null);
      headingLbl = new Label("Select difficulty", labelStyle);

      for (int i = 0; i < labels.length; i++) {
         labels[i] = new Label("Level " + (i + 1), labelStyle);
         labels[i].setName("level-" + (i + 1));
      }

      // Wrapping labels
      containers[0] = new Container(headingLbl);
      containers[0].setPosition(Settings.GAME_WIDTH / 2, 20);
      containers[0].setTransform(true);
      containers[0].center();
      float y = 0;
      for (int i = 0; i < labels.length; i++) {
         y = (20 + containers[i].getHeight()) * (i + 2);
         containers[i + 1] = new Container(labels[i]);
         containers[i + 1].setPosition(Settings.GAME_WIDTH / 2, y);
         containers[i + 1].setBounds(Settings.GAME_WIDTH / 2, y, containers[i + 1].getWidth(),
            containers[i + 1].getHeight());
         containers[i + 1].setTouchable(Touchable.enabled);
         containers[i + 1].setTransform(true);
         containers[i + 1].center();
      }

      // Putting containers on stage
      for (Container c : containers)
         stage.addActor(c);

      Gdx.input.setInputProcessor(new ModeHandler(this, game));
   }

   @Override
   public void render(float delta) {
      stage.draw();
      stage.act(delta);
   }

   @Override
   public void show() {

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

   public Stage getStage() {
      return stage;
   }
}
