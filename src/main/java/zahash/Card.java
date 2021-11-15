package zahash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Card {
    public enum Color {RED, YELLOW, BLUE, GREEN, NOCOLOR}

    public enum Symbol {SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR, NOSYMBOL}

    public enum Number {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, NONUMBER}

    public final Color color;
    public final Symbol symbol;
    public final Number number;

    public Card(Color color, Symbol symbol, Number number) {
        this.color = color;
        this.symbol = symbol;
        this.number = number;
    }

    public static boolean match(Card card1, Card card2) {
        if (card1.symbol.equals(Symbol.WILD) || card1.symbol.equals(Symbol.WILD_DRAW_FOUR) ||
                card2.symbol.equals(Symbol.WILD) || card2.symbol.equals(Symbol.WILD_DRAW_FOUR))
            return true;

        if (card1.symbol.equals(Symbol.NOSYMBOL) && card2.symbol.equals(Symbol.NOSYMBOL) &&
                card1.number.equals(Number.NONUMBER) && card2.number.equals(Number.NONUMBER))
            return card1.color.equals(card2.color);

        if (card1.symbol.equals(Symbol.NOSYMBOL) && card2.symbol.equals(Symbol.NOSYMBOL))
            return card1.number.equals(card2.number);

        if (card1.number.equals(Number.NONUMBER) && card2.number.equals(Number.NONUMBER))
            return card1.symbol.equals(card2.symbol);

        return card1.color.equals(card2.color);
    }

    public static List<Card> all() {
        List<Number> numbersThatOccurTwiceInEachColor = Arrays.stream(Number.values())
                .filter(number -> !number.equals(Number.ZERO) && !number.equals(Number.NONUMBER))
                .collect(Collectors.toList());
        List<Symbol> symbolsThatOccurTwiceInEachColor = Arrays.stream(Symbol.values())
                .filter(symbol -> !symbol.equals(Symbol.WILD) && !symbol.equals(Symbol.WILD_DRAW_FOUR) && !symbol.equals(Symbol.NOSYMBOL))
                .collect(Collectors.toList());
        List<Color> validColors = Arrays.stream(Color.values())
                .filter(color -> !color.equals(Color.NOCOLOR))
                .collect(Collectors.toList());


        List<Card> cards = new ArrayList<>();
        for (Color color : validColors) {
            cards.add(new Card(color, Symbol.NOSYMBOL, Number.ZERO));
            for (Number number : numbersThatOccurTwiceInEachColor)
                for (int i = 0; i < 2; i++)
                    cards.add(new Card(color, Symbol.NOSYMBOL, number));

            for (Symbol symbol : symbolsThatOccurTwiceInEachColor)
                for (int i = 0; i < 2; i++)
                    cards.add(new Card(color, symbol, Number.NONUMBER));
        }

        for (int i = 0; i < 4; i++) {
            cards.add(new Card(Color.NOCOLOR, Symbol.WILD, Number.NONUMBER));
            cards.add(new Card(Color.NOCOLOR, Symbol.WILD_DRAW_FOUR, Number.NONUMBER));
        }

        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return color == card.color && symbol == card.symbol && number == card.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, symbol, number);
    }

    @Override
    public String toString() {
        return "Card{" +
                "color=" + color +
                ", symbol=" + symbol +
                ", number=" + number +
                '}';
    }
}
