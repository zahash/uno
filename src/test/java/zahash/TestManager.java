package zahash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import zahash.exceptions.NoMatchDiscardException;
import zahash.manager.GameManager;
import zahash.manager.Manager;
import zahash.fake.CardDeckInitializerManager;

import java.util.*;
import java.util.stream.Collectors;

public class TestManager {
    private static Manager underTest;

    @BeforeAll
    static void setup() {
        underTest = new GameManager();
    }

    @Test
    void canInitializeGameWith108Cards() {
        Game game = new Game();
        underTest.initialize108Deck(game);
        Assertions.assertEquals(game.deck.size(), 108);
    }

    @Test
    void canInitializeDiscardPileWhenFirstCardNotWild() {
        Game game = new Game();

        Card toBeDiscarded = new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.NINE);
        Manager fake = new CardDeckInitializerManager(Collections.singletonList(
                toBeDiscarded
        ));
        fake.initialize108Deck(game);

        underTest.initializeDiscardPile(game);
        Assertions.assertEquals(game.discarded.size(), 1);
        Assertions.assertEquals(game.discarded.peek(), toBeDiscarded);
    }

    @Test
    void canInitializeDiscardPileWhenFirstCardIsWild() {
        Game game = new Game();
        Card toBeDiscarded = new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.NINE);
        Manager fake = new CardDeckInitializerManager(Arrays.asList(
                toBeDiscarded,
                new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER)
        ));
        fake.initialize108Deck(game);

        underTest.initializeDiscardPile(game);
        Assertions.assertEquals(game.discarded.size(), 2);
        Assertions.assertEquals(game.discarded.peek(), toBeDiscarded);
    }

    @Test
    void canInitializeDiscardPileWhenFirstTwoCardsAreWild() {
        Game game = new Game();
        Card toBeDiscarded = new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.NINE);
        Manager fake = new CardDeckInitializerManager(Arrays.asList(
                toBeDiscarded,
                new Card(Card.Color.NOCOLOR, Card.Symbol.WILD_DRAW_FOUR, Card.Number.NONUMBER),
                new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER)
        ));
        fake.initialize108Deck(game);

        underTest.initializeDiscardPile(game);
        Assertions.assertEquals(game.discarded.size(), 3);
        Assertions.assertEquals(game.discarded.peek(), toBeDiscarded);
    }

    @Test
    void canInitializeDiscardPileWhenMiddleTwoCardsAreWild() {
        Game game = new Game();
        Card toBeDiscarded = new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.NINE);
        Manager fake = new CardDeckInitializerManager(Arrays.asList(
                new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER),
                toBeDiscarded,
                new Card(Card.Color.NOCOLOR, Card.Symbol.WILD_DRAW_FOUR, Card.Number.NONUMBER)

        ));
        fake.initialize108Deck(game);

        underTest.initializeDiscardPile(game);
        Assertions.assertEquals(game.discarded.size(), 2);
        Assertions.assertEquals(game.discarded.peek(), toBeDiscarded);
    }

    @Test
    void checkCorrectNumberOfEachTypeOfCardIn108Deck() {
        class Counter<T> {
            final Map<T, Integer> counts = new HashMap<>();

            public void add(T t) {
                counts.merge(t, 1, Integer::sum);
            }

            public int count(T t) {
                return counts.getOrDefault(t, 0);
            }
        }

        Game game = new Game();
        underTest.initialize108Deck(game);

        Counter<Card> counter = new Counter<>();
        for (Card card : game.deck)
            counter.add(card);

        List<Card.Color> validColors = Arrays.stream(Card.Color.values())
                .filter(color -> !color.equals(Card.Color.NOCOLOR))
                .collect(Collectors.toList());
        for (Card.Color color : validColors) {
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.ZERO)), 1, String.format("%s, NOSYMBOL, ZERO", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.ONE)), 2, String.format("%s, NOSYMBOL, ONE", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.TWO)), 2, String.format("%s, NOSYMBOL, TWO", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.THREE)), 2, String.format("%s, NOSYMBOL, THREE", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.FOUR)), 2, String.format("%s, NOSYMBOL, FOUR", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.FIVE)), 2, String.format("%s, NOSYMBOL, FIVE", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.SIX)), 2, String.format("%s, NOSYMBOL, SIX", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.SEVEN)), 2, String.format("%s, NOSYMBOL, SEVEN", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.EIGHT)), 2, String.format("%s, NOSYMBOL, EIGHT", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.NOSYMBOL, Card.Number.NINE)), 2, String.format("%s, NOSYMBOL, NINE", color.name()));

            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.SKIP, Card.Number.NONUMBER)), 2, String.format("%s, SKIP, NOSYMBOL", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.REVERSE, Card.Number.NONUMBER)), 2, String.format("%s, REVERSE, NOSYMBOL", color.name()));
            Assertions.assertEquals(counter.count(new Card(color, Card.Symbol.DRAW_TWO, Card.Number.NONUMBER)), 2, String.format("%s, DRAW_TWO, NOSYMBOL", color.name()));
        }

        Assertions.assertEquals(counter.count(new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER)), 4, "NOCOLOR, WILD, NONUMBER");
        Assertions.assertEquals(counter.count(new Card(Card.Color.NOCOLOR, Card.Symbol.WILD_DRAW_FOUR, Card.Number.NONUMBER)), 4, "NOCOLOR, WILD_DRAW_FOUR, NONUMBER");
    }

    @Test
    void afterInitializing108Deck_playerCanDrawOneCard() {
        Game game = new Game();
        Player player = new Player();
        underTest.initialize108Deck(game);

        Assertions.assertEquals(player.cards.size(), 0);
        Assertions.assertEquals(game.deck.size(), 108);
        underTest.draw(player, game);
        Assertions.assertEquals(game.deck.size(), 108 - 1);
        Assertions.assertEquals(player.cards.size(), 1);
    }

    @Test
    void afterInitializing108Deck_playerCanDrawMultipleCards() {
        Game game = new Game();
        Player player = new Player();
        underTest.initialize108Deck(game);

        Assertions.assertEquals(player.cards.size(), 0);
        Assertions.assertEquals(game.deck.size(), 108);

        int n = 10;
        for (int i = 0; i < n; i++)
            underTest.draw(player, game);

        Assertions.assertEquals(game.deck.size(), 108 - n);
        Assertions.assertEquals(player.cards.size(), n);
    }

    @Test
    void afterInitializing108Deck_cardsMustHaveBeenShuffled() {
        Game game1 = new Game();
        underTest.initialize108Deck(game1);

        Game game2 = new Game();
        underTest.initialize108Deck(game2);

        Iterator<Card> it1 = game1.deck.iterator();
        Iterator<Card> it2 = game2.deck.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            if (!it1.next().equals(it2.next())) {
                Assertions.assertTrue(true);
                return;
            }
        }
        Assertions.fail();
    }

    @Test
    void playerCannotDiscardCardIfNothingMatchesTopOfDiscardPile() {
        Game game = new Game();
        Manager fake = new CardDeckInitializerManager(Arrays.asList(
                new Card(Card.Color.BLUE, Card.Symbol.NOSYMBOL, Card.Number.THREE),
                new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.FIVE)
        ));
        fake.initialize108Deck(game);
        underTest.initializeDiscardPile(game);

        Player player = new Player();
        underTest.draw(player, game);

        Assertions.assertThrows(NoMatchDiscardException.class, () -> underTest.discard(player, game, 0));
    }

    @Test
    void playerCanDiscardCardIfCardMatchesTopOfDiscardPile() {
        Game game = new Game();
        Manager fake = new CardDeckInitializerManager(Arrays.asList(
                new Card(Card.Color.BLUE, Card.Symbol.NOSYMBOL, Card.Number.THREE),
                new Card(Card.Color.BLUE, Card.Symbol.REVERSE, Card.Number.NONUMBER)
        ));
        fake.initialize108Deck(game);
        underTest.initializeDiscardPile(game);

        Player player = new Player();
        underTest.draw(player, game);

        underTest.discard(player, game, 0);

        Assertions.assertEquals(game.discarded.size(), 2);
        Assertions.assertEquals(player.cards.size(), 0);
    }

    @Test
    void checkDiscardableCards() {
        Game game = new Game();
        Manager fake = new CardDeckInitializerManager(Arrays.asList(
                new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.TWO),        // 2
                new Card(Card.Color.GREEN, Card.Symbol.SKIP, Card.Number.NONUMBER),     // 1
                new Card(Card.Color.BLUE, Card.Symbol.REVERSE, Card.Number.NONUMBER),   // 0
                new Card(Card.Color.BLUE, Card.Symbol.NOSYMBOL, Card.Number.TWO)        // discarded
        ));
        fake.initialize108Deck(game);
        underTest.initializeDiscardPile(game);

        Player player = new Player();
        underTest.draw(player, game);
        underTest.draw(player, game);
        underTest.draw(player, game);

        List<Integer> discardableIndices = underTest.discardables(player, game);
        Assertions.assertArrayEquals(discardableIndices.toArray(), Arrays.asList(0, 2).toArray());
    }
}
