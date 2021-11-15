package zahash;

import java.util.Stack;

public class Game {
    public enum Direction {
        CLOCKWISE, ANTICLOCKWISE;

        public Direction opposite() {
            switch (this) {
                case CLOCKWISE:
                    return ANTICLOCKWISE;
                case ANTICLOCKWISE:
                    return CLOCKWISE;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    public final Stack<Card> deck = new Stack<>();
    public final Stack<Card> discarded = new Stack<>();
    public Direction direction = Direction.CLOCKWISE;
}
