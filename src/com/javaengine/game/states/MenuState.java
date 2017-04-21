package com.javaengine.game.states;

import com.javaengine.game.gfx.Assets;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.level.tiles.Tile;
import com.javaengine.game.ui.ClickListener;
import com.javaengine.game.ui.UIImageButton;
import com.javaengine.game.ui.UIManager;
import java.awt.Graphics;

/**
 *
 * @author leonardo
 */
public class MenuState extends State {

    private UIManager uiManager;
    private int btnWidth = Tile.TILE_WIDTH * 4, btnHeight = Tile.TILE_HEIGHT * 2;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(handler.getWidth() / 2 - btnWidth / 2,
                handler.getHeight() / 2 - btnHeight * 2,
                btnWidth, btnHeight, Assets.btn_start, new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                
                State gameState = new GameState(handler);
                
                State.setState(gameState);
            }
        }));

        uiManager.addObject(new UIImageButton(handler.getWidth() / 2 - btnWidth / 2,
                handler.getHeight() / 2 - btnHeight * 1 + 10,
                btnWidth, btnHeight, Assets.btn_mp, new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                
                State mpGameState = new MPGameState(handler);
                
                State.setState(mpGameState);
            }
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }
}
