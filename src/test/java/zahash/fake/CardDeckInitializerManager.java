package zahash.fake;

import zahash.Card;
import zahash.Game;
import zahash.Player;
import zahash.manager.Manager;

import java.util.List;

public class CardDeckInitializerManager implements Manager {
    private final List<Card> cards;

    public CardDeckInitializerManager(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void initialize108Deck(Game game) {
        for (Card card : cards)
            game.deck.push(card);
    }

    @Override
    public void draw(Player player, Game game) {

    }

    @Override
    public void discard(Player player, Game game, int cardIdx) {

    }

    @Override
    public void initializeDiscardPile(Game game) {

    }

    @Override
    public List<Integer> discardables(Player player, Game game) {
        return null;
    }

    @Override
    public Integer nextPlayer(List<Player> players, Game game, int currentPlayerIdx) {
        return null;
    }
}
