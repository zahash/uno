package zahash.manager;

import zahash.Card;
import zahash.Game;
import zahash.Player;
import zahash.exceptions.NoMatchDiscardException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Manager {
    public static void initialize108Deck(Game game) {
        List<Card> cards = Card.all();
        Collections.shuffle(cards);

        for (Card card : cards)
            game.deck.push(card);
    }

    public static void draw(Player player, Game game) {
        player.cards.add(game.deck.pop());
    }

    public static void discard(Player player, Game game, int cardIdx) {
        Card cardToBeDiscarded = player.cards.get(cardIdx);
        if (!Card.match(cardToBeDiscarded, game.discarded.peek()))
            throw new NoMatchDiscardException();

        if (cardToBeDiscarded.symbol.equals(Card.Symbol.REVERSE))
            game.direction = game.direction.opposite();

        player.cards.remove(cardIdx);
        game.discarded.push(cardToBeDiscarded);
    }

    public static void initializeDiscardPile(Game game) {
        Card card;
        do {
            card = game.deck.pop();
            game.discarded.push(card);
        } while (card.symbol.equals(Card.Symbol.WILD) || card.symbol.equals(Card.Symbol.WILD_DRAW_FOUR));
    }

    public static List<Integer> discardables(Player player, Game game) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < player.cards.size(); i++)
            if (Card.match(player.cards.get(i), game.discarded.peek()))
                indices.add(i);
        return indices;
    }

    public static Integer nextPlayer(List<Player> players, Game game, int currentPlayerIdx) {
        if (game.direction.equals(Game.Direction.CLOCKWISE))
            return Math.floorMod(currentPlayerIdx + 1, players.size());
        return Math.floorMod(currentPlayerIdx - 1, players.size());
    }
}
