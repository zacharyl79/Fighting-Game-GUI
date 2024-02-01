package lightning_mage;

import main.Game;

public class Constants {
    // a1 = 10, a2 = 4, charge = 9, deaf = 5, hurt = 3, idle = 7, jump = 8, light_ball = 7,  light_charge = 12, run = 8, walk = 7
    public static class Projectiles {
        public static final int CHARGE_IMAGE_WIDTH = 64;
        public static final int CHARGE_IMAGE_HEIGHT = 64;
        public static final int CHARGE_WIDTH = (int)(CHARGE_IMAGE_WIDTH * Game.scale);
        public static final int CHARGE_HEIGHT = (int)(CHARGE_IMAGE_HEIGHT * Game.scale);
        public static final float SPEED = 0.5f * Game.scale;
    }
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int JUMP = 2;
        public static final int HURT = 3;
        public static final int ATTACK_1 = 4;
        public static final int ATTACK_2 = 5;
        public static final int LIGHT_BALL = 6;
        public static final int LIGHT_CHARGE = 7;
        public static final int DEAD = 8;
        public static final int FALL = 9;
        public static final int LIGHTNING = 10;

        public static int GetSpriteAmount(int player_action) {
            return switch (player_action) {
                case ATTACK_1 -> 10;
                case ATTACK_2 -> 4;
                case LIGHTNING -> 9;
                case DEAD, JUMP -> 5;
                case HURT, FALL -> 3;
                case IDLE, LIGHT_BALL, WALK -> 7;
                case LIGHT_CHARGE -> 12;
                default -> 1;
            };
        }
    }
}
