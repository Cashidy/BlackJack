public class Card {
    public enum Suit {
        DIAMONDS, CLUBS, HEARTS, SPADES;
    }
    public enum Value {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
        NINE, TEN, JACK, QUEEN, KING, ACE;
    }

    private Value value;
    private Suit suit;

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Card(Suit suit, Value value) {
        this.value = value;
        this.suit = suit;
    }

    public Card(String str) {
        IllegalArgumentException wackStr = new IllegalArgumentException("Tried to create a card with an invalid String");
        if (str.length() != 2) {
            throw wackStr;
        }
        str = str.toUpperCase();
        char v = str.charAt(0);
        char s = str.charAt(1);
        switch (v) {
            case '2': value = Value.TWO; break;
            case '3': value = Value.THREE; break;
            case '4': value = Value.FOUR; break;
            case '5': value = Value.FIVE; break;
            case '6': value = Value.SIX; break;
            case '7': value = Value.SEVEN; break;
            case '8': value = Value.EIGHT; break;
            case '9': value = Value.NINE; break;
            case 'T': value = Value.TEN; break;
            case 'J': value = Value.JACK; break;
            case 'Q': value = Value.QUEEN; break;
            case 'K': value = Value.KING; break;
            case 'A': value = Value.ACE; break;
            default: throw wackStr;
        }
        switch (s) {
            case 'D': suit = Suit.DIAMONDS; break;
            case 'C': suit = Suit.CLUBS; break;
            case 'H': suit = Suit.HEARTS; break;
            case 'S': suit = Suit.SPADES; break;
            default: throw wackStr;
        }
    }

    public Value getValue() {
        return value;
    }
    public Suit getSuit() {
        return suit;
    }

    public static Value randomValue() {
        switch ((int)(Math.random() * 13)) {
            case 0: return Value.TWO;
            case 1: return Value.THREE;
            case 2: return Value.FOUR;
            case 3: return Value.FIVE;
            case 4: return Value.SIX;
            case 5: return Value.SEVEN;
            case 6: return Value.EIGHT;
            case 7: return Value.NINE;
            case 8: return Value.TEN;
            case 9: return Value.JACK;
            case 10: return Value.QUEEN;
            case 11: return Value.KING;
            default: return Value.ACE;
        }
    }

    public static Suit randomSuit() {
        switch ((int)(Math.random() * 4)) {
            case 0: return Suit.DIAMONDS;
            case 1: return Suit.CLUBS;
            case 2: return Suit.HEARTS;
            default: return Suit.SPADES;
        }
    }

    public static Card random() {
        return new Card(randomValue(), randomSuit());
    }
    
    public static boolean equals(Card c1, Card c2) {
        return c1.getValue() == c2.getValue() && c1.getSuit() == c2.getSuit();
    }

    public static boolean valEquals(Card c1, Card c2) {
        return c1.getValue() == c2.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == this.getClass()) {
            Card c = (Card)o;
            return c.getValue() == this.value && c.getSuit() == this.suit;
        }
        return false;
    }
    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
