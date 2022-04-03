import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class CardSet {
    ArrayList<Card> cards = new ArrayList<Card>();

    public CardSet() {}
    public CardSet(Card c) {
        cards.add(c);
    }
    public CardSet(Card ... cardArray) {
        for (Card c: cardArray) {
            cards.add(c);
        }
    }
    public CardSet(List<Card> cardArray) {
        for (Card c: cardArray) {
            cards.add(c);
        }
    }
    public CardSet(CardSet cs) {
        for (int i = 0; i < cs.getSize(); i++) {
            cards.add(cs.cards.get(i));
        }
    }

    public void add(Card c) {
        cards.add(c);
    }

    public void remove(Card c) {
        cards.remove(c);
    }

    public void remove(int index) {
        cards.remove(index);
    }

    public Card pull(Card c) {
        if (cards.remove(c)) {
            return c;
        }
        return null;
    }

    public Card pull(int index) {
        Card toReturn = cards.get(index);
        cards.remove(index);
        return toReturn;
    }

    public Card get(int index) {
        return cards.get(index);
    }
    
    public int getSize() {
        return cards.size();
    }

    public void shuffle() {
        LinkedList<Card> tempSet = new LinkedList<>();
        tempSet.addAll(cards);
        cards.clear();
        int index;
        while (!tempSet.isEmpty()) {
            index = (int) (Math.random() * tempSet.size());
            cards.add(tempSet.remove(index));
        }
    }

    public void sort() {
        int cap = cards.size();
        for (int i = 1; i < cap; i++) {
            for (int j = i; j > 0; j--) {
                if (cards.get(j).getValue().compareTo(cards.get(j - 1).getValue()) > 0) {
                    swap(j, j - 1);
                }
            }
        }
    }
    private void swap(int index1, int index2) {
        Card temp = new Card(cards.get(index1).getValue(), cards.get(index1).getSuit());
        cards.set(index1, cards.get(index2));
        cards.set(index2, temp);
    }

    @Override
    public String toString() {
        String toReturn = "";
        for (Card c: cards) {
            toReturn += c.toString() + ", ";
        }
        return toReturn;
    }
}
