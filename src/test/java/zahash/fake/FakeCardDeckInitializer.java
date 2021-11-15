package zahash.fake;

import zahash.Card;
import zahash.Game;

import java.util.List;

public class FakeCardDeckInitializer {
    public static void initializeFakeDeck(Game game, List<Card> cards) {
        for (Card card : cards)
            game.deck.push(card);
    }
}
