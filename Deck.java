public class Deck extends CardSet {
    public static final int CARDS_PER_DECK = 52;

    int numDecks;

    public Deck(int decks) {
        numDecks = decks;
        this.initDeck();
    }

    public Deck() {
        this(1);
    }

    public void initDeck() {
        cards.clear();
        for (int i = 0; i < numDecks; i++) {
            for (Card.Suit suit: Card.Suit.values()) {
                for (Card.Value value: Card.Value.values()) {
                    cards.add(new Card(value, suit));
                }
            }
        }
    }

    public Card deal() {
        return cards.remove(cards.size() - 1);
    }
    public void burn() {
        cards.remove(cards.size() - 1);
    }
    @Override
    public String toString() {
        String toReturn = "";
        for (Card card: cards) {
            toReturn += card.toString();
            toReturn += "\n";
        }
        return toReturn;
    }
}
