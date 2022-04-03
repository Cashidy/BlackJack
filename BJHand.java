public class BJHand extends CardSet {
    int bet;

    int soft = 0;
    int value = 0;

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public void doubleDown() {
        bet *= 2;
    }

    public int getValue() {
        return value;
    }

    public void calcValue() {
        value = 0;
        for (Card c : cards) {
            this.addValue(c);
        }
    }

    public void addValue(Card c) {
        Card.Value v = c.getValue();
        switch (v) {
            case TWO : value += 2;
                break;
            case THREE : value += 3;
                break;
            case FOUR : value += 4;
                break;
            case FIVE : value += 5;
                break;
            case SIX : value += 6;
                break;
            case SEVEN : value += 7;
                break;
            case EIGHT : value += 8;
                break;
            case NINE : value += 9;
                break;
            case ACE : value += 11;
                soft++;
                break;
            default : value += 10;
        }
        if (value > 21 && soft > 0) {
            value -= 10;
            soft--;
        }
    }
}