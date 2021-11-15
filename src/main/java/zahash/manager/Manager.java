package zahash.manager;

import zahash.Game;
import zahash.Player;

import java.util.List;

public interface Manager {
    void initialize108Deck(Game game);

    void draw(Player player, Game game);

    void discard(Player player, Game game, int cardIdx);

    void initializeDiscardPile(Game game);

    List<Integer> discardables(Player player, Game game);
}
