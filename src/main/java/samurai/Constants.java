package samurai;

public class Constants {
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants{
        public static final int ATTACK_1 = 0;
        public static final int ATTACK_2 = 9;

        public static final int ATTACK_3 = 2;
        public static final int DEAD = 3;
        public static final int HURT = 4;
        public static final int IDLE = 5;
        public static final int JUMP = 6;
        public static final int PROTECTION = 7;
        public static final int WALK = 8;
        public static final int FALL = 1;



        public static int GetSpriteAmount(int player_action){
            return switch (player_action) {
                case ATTACK_3, ATTACK_1, FALL  -> 4;
                case ATTACK_2,JUMP -> 5;
                case DEAD, IDLE -> 6;
                case HURT -> 3;
                case WALK -> 9;
                case PROTECTION -> 2;
                default -> 1;
            };
        }
    }
}
