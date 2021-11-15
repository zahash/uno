package zahash.manager;

import zahash.Card;
import zahash.Game;
import zahash.Player;
import zahash.exceptions.NoMatchDiscardException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager implements Manager {
    @Override
    public void initialize108Deck(Game game) {
        List<Card> cards = Card.all();
        Collections.shuffle(cards);

        for (Card card : cards)
            game.deck.push(card);
    }

    @Override
    public void draw(Player player, Game game) {
        player.cards.add(game.deck.pop());
    }

    @Override
    public void discard(Player player, Game game, int cardIdx) {
        Card cardToBeDiscarded = player.cards.get(cardIdx);
        if (!Card.match(cardToBeDiscarded, game.discarded.peek()))
            throw new NoMatchDiscardException();
        player.cards.remove(cardIdx);
        game.discarded.push(cardToBeDiscarded);
    }

    @Override
    public void initializeDiscardPile(Game game) {
        Card card;
        do {
            card = game.deck.pop();
            game.discarded.push(card);
        } while (card.symbol.equals(Card.Symbol.WILD) || card.symbol.equals(Card.Symbol.WILD_DRAW_FOUR));
    }

    @Override
    public List<Integer> discardables(Player player, Game game) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < player.cards.size(); i++)
            if (Card.match(player.cards.get(i), game.discarded.peek()))
                indices.add(i);
        return indices;
    }
}
