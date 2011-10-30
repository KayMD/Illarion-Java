package illarion.client.gui.controller;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import illarion.client.Login;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public final class LoadScreenController implements ScreenController {

	private Nifty nifty;
	private ProgressbarControl progress;
	
	private final StateBasedGame game;
	
    public LoadScreenController(StateBasedGame game) {
        this.game = game;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
    	this.nifty = nifty;
    	progress = screen.findControl("loading", ProgressbarControl.class); 
    }

    @Override
    public void onStartScreen() {
        setProgress(0.f);
    }
    
    private boolean loadingDoneCalled = false;
    
    public void loadingDone() {
        if (loadingDoneCalled) {
            return;
        }
        loadingDoneCalled = true;
        
        Login.getInstance().login();
        
        game.enterState(illarion.client.Game.STATE_PLAYING, new FadeOutTransition(), new FadeInTransition());
    }
    
    public void setProgress(final float progressValue) {
    	progress.setProgress(progressValue);
    }

    @Override
    public void onEndScreen() {
        // nothing to do
    }
}
