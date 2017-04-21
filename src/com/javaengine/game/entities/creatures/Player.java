package com.javaengine.game.entities.creatures;

import com.javaengine.game.handlers.Handler;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.animations.AnimationPlayer;
import com.javaengine.game.level.tiles.Tile;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    protected boolean isSwimming = false;
    protected boolean hasInput = true;

    private int tickCount = 0;

    private final String USERNAME;
    private final int[][] DIRECTIONS = new int[8][2];

    private AnimationPlayer walkingAnimation;

    public Player(Handler handler, int x, int y, String username) {
        super(handler, username, x, y, Creature.DEFAULT_CRETURE_WIDTH, Creature.DEFAULT_CRETURE_HEIGHT);
        this.USERNAME = username;

        bounds.x = 8 * 2;
        bounds.y = 20 * 2;
        bounds.width = 15 * 2;
        bounds.height = 8 * 2;

        // animation
        walkingAnimation = new AnimationPlayer(speed * 100, Assets.player);

        DIRECTIONS[UP] = new int[]{0, 1};
        DIRECTIONS[DOWN] = new int[]{2, 3};
        DIRECTIONS[LEFT] = new int[]{4, 5};
        DIRECTIONS[RIGHT] = new int[]{6, 7};
        // swiming
        DIRECTIONS[UP + 4] = new int[]{8, 9};
        DIRECTIONS[DOWN + 4] = new int[]{10, 11};
        DIRECTIONS[LEFT + 4] = new int[]{12};
        DIRECTIONS[RIGHT + 4] = new int[]{13};

        //handler.setPlayer(this);
    }

    public void checkMove() {
        if (hasInput) {
            xMove = 0;
            yMove = 0;
            isMoving = false;

            if (handler.getInput().up.isPressed()) {
                yMove += -speed;
            }
            if (handler.getInput().down.isPressed()) {
                yMove += speed;
            }
            if (handler.getInput().left.isPressed()) {
                xMove += -speed;
            }
            if (handler.getInput().right.isPressed()) {
                xMove += speed;
            }
        }
    }

    private void setAnimation(int movingDir) {
        if (isSwimming) {
            walkingAnimation.setIndexes(DIRECTIONS[movingDir + +4]);
        } else {
            walkingAnimation.setIndexes(DIRECTIONS[movingDir]);
        }

        if (isMoving) {
            walkingAnimation.tick();
        } else {
            isMoving = false;
        }
    }

    private void checkSwimming() {
        // PODE DAR ERRO NA POSIÇAO        
        if (handler.getLevel().getTile((x + Tile.TILE_WIDTH) / Tile.TILE_WIDTH,
                (y + height) / Tile.TILE_HEIGHT).getId() == Tile.WATER.getId()) {
            isSwimming = true;
        }

        if (isSwimming && handler.getLevel().getTile((x + Tile.TILE_WIDTH) / Tile.TILE_WIDTH,
                (y + height) / Tile.TILE_HEIGHT).getId() != Tile.WATER.getId()) {
            isSwimming = false;
        }

        if (isSwimming && 15 <= tickCount % 60 && tickCount % 60 < 30) {
            y += -1;
        } else if (isSwimming && 30 <= tickCount % 60 && tickCount % 60 < 45) {
            y += 1;
        }
    }

    @Override
    public void tick() {
        // move
        checkSwimming();
        checkMove();
        move();
        setAnimation(movingDir);
        if (hasInput) {
            handler.getGameCamera().centerOnEntity(this);
        }
        tickCount++;

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), x - handler.getGameCamera().getxOffset(),
                y - handler.getGameCamera().getyOffset(), width, height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        return walkingAnimation.getCurrentFrame(isMoving);
    }

    public String getUsername() {
        return this.USERNAME;
    }

    public boolean isIsSwimming() {
        return isSwimming;
    }

}
