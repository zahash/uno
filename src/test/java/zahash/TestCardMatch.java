package zahash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCardMatch {

    @Test
    void whenColorMatches_shouldPass() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.FIVE),
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void whenSymbolMatches_shouldPass() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.BLUE, Card.Symbol.REVERSE, Card.Number.NONUMBER),
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void whenNumberMatches_shouldPass() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.BLUE, Card.Symbol.NOSYMBOL, Card.Number.TWO),
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.TWO)
                )
        );
    }

    @Test
    void whenNumberCardsDontMatch_shouldFail() {
        Assertions.assertFalse(
                Card.match(
                        new Card(Card.Color.BLUE, Card.Symbol.NOSYMBOL, Card.Number.THREE),
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.TWO)
                )
        );
    }

    @Test
    void whenSymbolCardsDontMatch_shouldFail() {
        Assertions.assertFalse(
                Card.match(
                        new Card(Card.Color.BLUE, Card.Symbol.SKIP, Card.Number.NONUMBER),
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void whenSymbolCardColorDontMatchWithNumberCardColor_shouldFail() {
        Assertions.assertFalse(
                Card.match(
                        new Card(Card.Color.BLUE, Card.Symbol.NOSYMBOL, Card.Number.ONE),
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void wildCardShouldMatchWithSymbolCard() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER),
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER)
                )
        );
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER),
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void wildCardShouldMatchWithNumberCard() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER),
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.ONE)
                )
        );
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.ONE),
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void wildFourCardShouldMatchWithSymbolCard() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD_DRAW_FOUR, Card.Number.NONUMBER),
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER)
                )
        );
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.RED, Card.Symbol.REVERSE, Card.Number.NONUMBER),
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD_DRAW_FOUR, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void wildFourCardShouldMatchWithNumberCard() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD_DRAW_FOUR, Card.Number.NONUMBER),
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.ONE)
                )
        );
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.ONE),
                        new Card(Card.Color.NOCOLOR, Card.Symbol.WILD_DRAW_FOUR, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void imaginaryCardWithMisMatchingColorAndNoSymbolNoNumber_shouldFail() {
        Assertions.assertFalse(
                Card.match(
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.NONUMBER),
                        new Card(Card.Color.BLUE, Card.Symbol.NOSYMBOL, Card.Number.NONUMBER)
                )
        );
    }

    @Test
    void imaginaryCardWithMatchingColorAndNoSymbolNoNumber_shouldPass() {
        Assertions.assertTrue(
                Card.match(
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.NONUMBER),
                        new Card(Card.Color.RED, Card.Symbol.NOSYMBOL, Card.Number.NONUMBER)
                )
        );
    }
}
