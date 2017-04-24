package com.javaengine.game.entities;

import com.javaengine.game.entities.creatures.Player;
import com.javaengine.game.entities.creatures.PlayerMP;
import com.javaengine.game.handlers.Handler;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author leonardo
 */
public final class EntityManager {

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesMP;
    
    private final Comparator<Entity> renderSorter = (Entity a, Entity b) -> {
        if (a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
            return -1;
        }
        return 1;
    };

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        entitiesMP = new ArrayList<>();
        addEntity(player);
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
            if (!e.isActive()) {
                entities.remove(e);
            }
        }

        entities.sort(renderSorter);
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public void addEntity(Entity e) {
        entities.add(e);
        if (e instanceof PlayerMP) {
            entitiesMP.add(e);
        }
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
        if (e instanceof PlayerMP) {
            entitiesMP.remove(e);
        }
    }

    // MP
    public synchronized void removePlayerMP(String userId) {
        int index = 0;
        Entity p;
        for (Entity e : entitiesMP) {
            if (e instanceof PlayerMP && ((PlayerMP) e).getUniqueId().equals(userId)) {
                break;
            }
            index++;
        }
        p = entitiesMP.get(index);
        entitiesMP.remove(index);
        entities.remove(p);
    }

    private int getPlayerMPIndex(String userId) {
        int index = 0;
        for (Entity e : entitiesMP) {
            if (e instanceof PlayerMP && ((PlayerMP) e).getUniqueId().equals(userId)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void movePlayer(String userId, int x, int y, int numSteps, boolean isMoving, int movingDir, boolean isAttacking) {
        int index = getPlayerMPIndex(userId);

        PlayerMP player = (PlayerMP) entitiesMP.get(index);

        player.setX(x);
        player.setY(y);
        player.setIsMoving(isMoving);
        player.setNumSteps(numSteps);
        player.setMovingDir(movingDir);
        player.setIsAttacking(isAttacking);
    }

    // getters and setters 
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

}
