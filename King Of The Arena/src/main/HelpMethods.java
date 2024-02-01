package main;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean canMoveHere(float x, float y, float width, float height){
        if(!isSolid(x, y))
            if(!isSolid(x+width, y+height))
                if(!isSolid(x+width, y))
                    if(!isSolid(x, y+height))
                        return true;
        return false;
    }

    public static boolean isSolid(float x, float y){
        if(x< 0 || x> Game.gameWidth) return true;
        if(y<0 || y> 890) return true;

        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile = (int) (hitbox.x / Game.tilesSize)+1;
        if(xSpeed > 0){
            int tileXPos = currentTile * Game.tilesSize;
            int xOffSet = (int) (Game.tilesSize - hitbox.width);
            return tileXPos + xOffSet -1;
        }
        else {
            return (currentTile-1) * Game.tilesSize;
        }
    }

    public static float GetEntityYPosWithinFrame(Rectangle2D.Float hitbox, float airSpeed){
        int currentTile = (int) (hitbox.y / Game.tilesSize);
        if (airSpeed > 0) {
            // falling
            int tileYPos = (currentTile+4) * Game.tilesSize;
            int yOffSet = (int) (Game.tilesSize - hitbox.height);
            return tileYPos + yOffSet-1;
        }
        else {
            // jumping
            return currentTile * Game.tilesSize;
        }
    }

    public static boolean EntityOnFloor(Rectangle2D.Float hitbox){
        if(!isSolid(hitbox.x, hitbox.y + hitbox.height+1)){
            if(!isSolid(hitbox.x+ hitbox.width, hitbox.y+ hitbox.height+1)){
                return false;
            }
        }
        return true;
    }

}
