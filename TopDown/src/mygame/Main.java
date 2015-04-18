package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings newSetting = new AppSettings(true);
        newSetting.setFrameRate(60);
        app.setSettings(newSetting);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new GameManager(this));
        getFlyByCamera().setEnabled(false);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
