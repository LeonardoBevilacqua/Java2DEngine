package com.javaengine.game.entities.creatures;

import com.javaengine.game.entities.Entity;
import com.javaengine.game.handlers.Handler;
import com.javaengine.game.gfx.Assets;
import com.javaengine.game.gfx.Text;
import com.javaengine.game.gfx.animations.AnimationEntity;
import com.javaengine.game.inventory.Inventory;
import com.javaengine.game.level.tiles.Tile;
import com.javaengine.game.net.packets.Packet02Move;
import com.javaengine.game.net.packets.Packet03LevelUpdate;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Creates a player.
 *
 * @author leonardo
 */
public class Player extends Creature {

    protected boolean isSwimming = false;
    protected boolean hasInput = true;

    private int tickCount = 0;

    private final String USERNAME;
    private final int[][] DIRECTIONS = new int[12][2];
    private BufferedImage[] texture;

// animation
    private AnimationEntity walkingAnimation;

    // attack timer
    private long lastAttackTimer, attackCooldown = 1000, attackTimer = attackCooldown;

    // Inventory
    private Inventory inventory;

    /**
     * Inializes the player.
     *
     * @param handler The handler of the game.
     * @param username The name of the player.
     * @param texture The texture.
     */
    public Player(Handler handler, String username, BufferedImage[] texture) {
        super(handler, username, Creature.DEFAULT_CRETURE_WIDTH, Creature.DEFAULT_CRETURE_HEIGHT);
        this.USERNAME = username;
        this.texture = texture;

        bounds.x = 8 * 2;
        bounds.y = 20 * 2;
        bounds.width = 15 * 2;
        bounds.height = 8 * 2;

        // animation
        walkingAnimation = new AnimationEntity(speed * 100, this.texture);

        inventory = new Inventory(handler);

        DIRECTIONS[UP] = new int[]{0, 1};
        DIRECTIONS[DOWN] = new int[]{2, 3};
        DIRECTIONS[LEFT] = new int[]{4, 5};
        DIRECTIONS[RIGHT] = new int[]{6, 7};
        // swiming
        DIRECTIONS[UP + 4] = new int[]{8, 9};
        DIRECTIONS[DOWN + 4] = new int[]{10, 11};
        DIRECTIONS[LEFT + 4] = new int[]{12};
        DIRECTIONS[RIGHT + 4] = new int[]{13};
        // attaking
        DIRECTIONS[UP + 8] = new int[]{14};
        DIRECTIONS[DOWN + 8] = new int[]{15};
        DIRECTIONS[LEFT + 8] = new int[]{16};
        DIRECTIONS[RIGHT + 8] = new int[]{17};

        //handler.setPlayer(this);
    }

    /**
     * Check if should move.
     */
    public void checkMove() {

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
        if (handler.getInput().meleeAtack.isPressed()) {
            isAttacking = true;
        } else {
            isAttacking = false;
        }

    }

    /**
     * Sets the animations.
     */
    private void setAnimation() {
        if (isSwimming) {
            walkingAnimation.setIndexes(DIRECTIONS[movingDir + 4]);
        } else if (isAttacking) {
            walkingAnimation.setIndexes(DIRECTIONS[movingDir + 8]);
        } else {
            walkingAnimation.setIndexes(DIRECTIONS[movingDir]);
        }

        if (isMoving || isAttacking) {
            walkingAnimation.tick();
        } else {
            isMoving = false;
        }
    }

    /**
     * Check if is swimming.
     */
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

    /**
     * Kill the player.
     */
    @Override
    public void die() {
        if (this instanceof PlayerMP && this.equals(handler.getLevel().getEntityManager().getPlayer())) {
            System.out.println("Voce morreu!");
        }
    }

    /**
     * Check if should attack.
     */
    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown) {
            return;
        }

        Rectangle collisionBounds = getCollisionBounds(0, 0);
        Rectangle attackRectangle = new Rectangle();
        int attackRectangleSize = 20;
        attackRectangle.width = attackRectangle.height = attackRectangleSize;

        if (handler.getInput().meleeAtack.isPressed()) {

            switch (movingDir) {
                case UP:
                    attackRectangle.x = collisionBounds.x + collisionBounds.width / 2 - attackRectangleSize / 2;
                    attackRectangle.y = collisionBounds.y - attackRectangleSize;
                    break;
                case DOWN:
                    attackRectangle.x = collisionBounds.x + collisionBounds.width / 2 - attackRectangleSize / 2;
                    attackRectangle.y = collisionBounds.y + collisionBounds.height;
                    break;
                case LEFT:
                    attackRectangle.x = collisionBounds.x - attackRectangleSize;
                    attackRectangle.y = collisionBounds.y + collisionBounds.height / 2 - attackRectangleSize;
                    break;
                case RIGHT:
                    attackRectangle.x = collisionBounds.x + collisionBounds.width;
                    attackRectangle.y = collisionBounds.y + collisionBounds.height / 2 - attackRectangleSize;
                    break;
            }
        } else {

            return;
        }
        attackTimer = 0;

        for (Entity e : handler.getLevel().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }
            if (e.getCollisionBounds(0, 0).intersects(attackRectangle)) {
                e.hurt(3);

                if (handler.getSocketClient() != null) {
                    Packet03LevelUpdate packet = new Packet03LevelUpdate(
                            e.getUniqueId(),
                            e.getHealth(),
                            e.isActive()
                    );
                    packet.writeData(handler.getSocketClient());
                }

                return;
            }
        }
    }

    /**
     * Updates the player.
     */
    @Override
    public void tick() {
        inventory.tick();
        checkSwimming();

        if (inventory.isActive()) {
            return;
        }

        if (hasInput) {
            handler.getGameCamera().centerOnEntity(this);
            checkAttacks();
            checkMove();
        }
        if (!isAttacking) {
            move();
        }
        sendPlayerData();
        setAnimation();

        tickCount++;
    }

    /**
     * Send the informations to the client.
     */
    private void sendPlayerData() {
        if (handler.getSocketClient() != null) {

            Packet02Move packet = new Packet02Move(
                    this.uniqueId,
                    this.x,
                    this.y,
                    this.numSteps,
                    this.isMoving,
                    this.movingDir,
                    this.isAttacking
            );
            packet.writeData(handler.getSocketClient());
        }
    }

    /**
     * Draw the player.
     *
     * @param g The graphic object.
     */
    @Override
    public void render(Graphics g) {
        super.render(g);

        g.drawImage(getCurrentAnimationFrame(), x - handler.getGameCamera().getxOffset(),
                y - handler.getGameCamera().getyOffset(), width, height, null);

        if (this instanceof PlayerMP && !this.equals(handler.getLevel().getEntityManager().getPlayer())) {
            Text.drawString(g, USERNAME, x - handler.getGameCamera().getxOffset() + width / 2,
                    y - handler.getGameCamera().getyOffset() - 16, true, Color.WHITE, Assets.font16);
        }
    }

    /**
     * Render above the entities.
     *
     * @param g The graphic object.
     */
    public void postRender(Graphics g) {
        inventory.render(g);
    }

    /**
     * Get the current frame.
     *
     * @return The current image.
     */
    private BufferedImage getCurrentAnimationFrame() {
        return walkingAnimation.getCurrentFrame(isMoving);
    }

    // getters and setters
    public String getUsername() {
        return this.USERNAME;
    }

    public boolean isIsSwimming() {
        return isSwimming;
    }

    public Inventory getInventory() {
        return inventory;
    }

}
