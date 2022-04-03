import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

public class BlackJack {
    int numDecks;
    Deck deck;
    int cardsBeforeShuffle;
    int stackSize;

    Scanner scan;

    private BJHand player;
    private BJHand dealer;

    public static void main(String[] args) {
        int decks = Integer.parseInt(args[0]);
        float shuffle = Float.parseFloat(args[1]);
        int ss = Integer.parseInt(args[2]);

        BlackJack game = new BlackJack(decks, shuffle, ss);
        
        while (true) {
            game.newRound();
            if (game.deck.getSize() < game.cardsBeforeShuffle) {
                game.freshDeck();
            }
        }
    }

    public BlackJack(int decks, float shuffle, int ss) {
        numDecks = decks;
        freshDeck();
        cardsBeforeShuffle = (int) (shuffle * numDecks * Deck.CARDS_PER_DECK);
        stackSize = ss;
        scan = new Scanner(System.in);
    }

    private void freshDeck() {
        deck = new Deck(numDecks);
        deck.shuffle();
    }

    private void newRound() {

        player = new BJHand();
        dealer = new BJHand();

        System.out.print("\n\n   New round. Current stack size: $" + stackSize + "\n   Place your bet => $");
        player.setBet(scan.nextInt());
        scan.nextLine();

        player.add(deck.deal());
        dealer.add(deck.deal());
        player.add(deck.deal());
        dealer.add(deck.deal());

        player.calcValue();
        dealer.calcValue();

        { //checking for blackjacks
            boolean playerBJ = player.getValue() == 21;
            boolean dealerBJ = dealer.getValue() == 21;
            boolean doubleBJ = playerBJ && dealerBJ;

            if (doubleBJ) {
                System.out.println("\n\n    Dealer dealt " + dealer + "for blackjack,");
                System.out.println("    Player dealt " + player + "for blackjack.");
                System.out.println("      Push...");
                return;
            }
            if (playerBJ) {
                System.out.println("\n\n     Player has " + player + "for blackjack!");
                int payment = player.getBet() * 3 / 2;
                System.out.println("     Player is paid $" + payment);
                stackSize += payment;
                return;
            }
            if (dealerBJ) {
                System.out.println("\n\n   Dealer dealt " + dealer + "for blackjack.");
                System.out.println("   Player loses bet of " + player.getBet());
                stackSize -= player.getBet();
                return;
            }
        }

        System.out.println("\n\n     Dealer shows: " + dealer.get(0));
        System.out.println("     Player has: " + player);

        boolean splitPossible = Card.valEquals(player.get(0), player.get(1));
        boolean stand = false;

        int action = getAction(true, splitPossible);

        if (action == 3) {
            BJHand split1 = new BJHand();
            BJHand split2 = new BJHand();
            split1.add(player.get(0));
            split2.add(player.get(1));
            split1.setBet(player.getBet());
            split2.setBet(player.getBet());

            split1.add(deck.deal());
            split2.add(deck.deal());
            split1.calcValue();
            split2.calcValue();

            System.out.println("\n     Player hand 1 has: " + split1);
            System.out.println("     Player hand 2 has: " + split2);

            boolean bust1 = false;
            boolean bust2 = false;
//split hand 1
            System.out.println("\n   For hand 1: " + split1);
            action = getAction(true, false);

            if (action == 2) {
                split1.doubleDown();
                split1.add(deck.deal());

                System.out.println("     Player hand 1 now has bet of $" + split1.getBet());
                System.out.println("     Player hand 1 has: " + split1);

                split1.calcValue();
                if (split1.getValue() > 21) {
                    System.out.println("     Player hand 1 busts! Player loses bet of $" + split1.getBet());
                    stackSize -= split1.getBet();
                    bust1 = true;
                }

                stand = true;
            } else if (action == 1) {
                split1.add(deck.deal());
                System.out.println("     Player hand 1 has: " + split1);

                split1.calcValue();
                if (split1.getValue() > 21) {
                    System.out.println("     Player hand 1 busts! Player loses bet of $" + split1.getBet());
                    stackSize -= split1.getBet();
                    bust1 = true;
                    stand = true;
                }
            } else {
                stand = true;
            }

            while (!stand) {
                action = getAction(false, false);
                if (action == 1) {
                    split1.add(deck.deal());
                    System.out.println("     Player hand 1 has: " + split1);

                    split1.calcValue();
                    if (split1.getValue() > 21) {
                        System.out.println("     Player hand 1 busts! Player loses bet of $" + split1.getBet());
                        stackSize -= split1.getBet();
                        bust1 = true;
                        stand = true;
                    }
                } else {
                    stand = true;
                }
            }
//split hand 2
            stand = false;
            System.out.println("\n   For hand 2: " + split2);
            action = getAction(true, false);

            if (action == 2) {
                split2.doubleDown();
                split2.add(deck.deal());

                System.out.println("     Player hand 2 now has bet of $" + split2.getBet());
                System.out.println("     Player hand 2 has: " + split2);

                split2.calcValue();
                if (split2.getValue() > 21) {
                    System.out.println("     Player hand 2 busts! Player loses bet of $" + split2.getBet());
                    stackSize -= split2.getBet();
                    bust2 = true;
                }

                stand = true;
            } else if (action == 1) {
                split2.add(deck.deal());
                System.out.println("     Player hand 2 has: " + split2);

                split2.calcValue();
                if (split2.getValue() > 21) {
                    System.out.println("     Player hand 2 busts! Player loses bet of $" + split2.getBet());
                    stackSize -= split2.getBet();
                    bust2 = true;
                    stand = true;
                }
            } else {
                stand = true;
            }

            while (!stand) {
                action = getAction(false, false);
                if (action == 1) {
                    split2.add(deck.deal());
                    System.out.println("     Player hand 2 has: " + split2);

                    split2.calcValue();
                    if (split2.getValue() > 21) {
                        System.out.println("     Player hand 2 busts! Player loses bet of $" + split2.getBet());
                        stackSize -= split2.getBet();
                        bust2 = true;
                        stand = true;
                    }
                } else {
                    stand = true;
                }
            }

            boolean doubleBust = bust1 && bust2;
            if (doubleBust)
                return;
            if (bust1) {
                System.out.println("\n     Player hand 2 stands at: " + split2);
            } else if (bust2) {
                System.out.println("\n     Player hand 1 stands at: " + split1);
            } else {
                System.out.println("\n     Player hand 1 stands at: " + split1);
                System.out.println("     Player hand 2 stands at: " + split2);
            }

            System.out.println("\n     Dealer has: " + dealer);
            while (dealer.getValue() < 17) {
                System.out.println("       Dealer must hit...");
                dealer.add(deck.deal());
                System.out.println("     Dealer has: " + dealer);
                dealer.calcValue();
            }
            System.out.println();

            if (dealer.getValue() > 21) {
                System.out.println("     Dealer busts!");
                if (!bust1) {
                    System.out.println("       Player is paid $" + split1.getBet());
                    stackSize += split1.getBet();
                }
                if (!bust2) {
                    System.out.println("       Player is paid $" + split2.getBet());
                    stackSize += split2.getBet();
                }
                return;
            }

            if (!bust1) {
                if (split1.getValue() > dealer.getValue()) {
                    System.out.println("     Player hand 1's " + split1.getValue() + " beats dealer's " + dealer.getValue());
                    System.out.println("       Player is paid $" + split1.getBet());
                    stackSize += split1.getBet();
                } else if (split1.getValue() < dealer.getValue()) {
                    System.out.println("     Dealer's " + dealer.getValue() + " beats player hand 1's " + split1.getValue());
                    System.out.println("       Player loses bet of $" + split1.getBet());
                    stackSize -= split1.getBet();
                } else {
                    System.out.println("     Player hand 1's " + player.getValue() + " ties dealer's " + dealer.getValue());
                    System.out.println("     Push...");
                }
            }
            if (!bust2) {
                if (split2.getValue() > dealer.getValue()) {
                    System.out.println("     Player hand 2's " + split2.getValue() + " beats dealer's " + dealer.getValue());
                    System.out.println("       Player is paid $" + split2.getBet());
                    stackSize += split2.getBet();
                } else if (split2.getValue() < dealer.getValue()) {
                    System.out.println("     Dealer's " + dealer.getValue() + " beats player hand 2's " + split2.getValue());
                    System.out.println("       Player loses bet of $" + split2.getBet());
                    stackSize -= split2.getBet();
                } else {
                    System.out.println("     Player hand 2's " + player.getValue() + " ties dealer's " + dealer.getValue());
                    System.out.println("     Push...");
                }
            }
            return;
        } else if (action == 2) {
            player.doubleDown();
            player.add(deck.deal());
            stand = true;

            System.out.println("     Player now has bet of $" + player.getBet());
            System.out.println("     Player has: " + player);

            player.calcValue();
            if (player.getValue() > 21) {
                System.out.println("     Player busts! Player loses bet of $" + player.getBet());
                stackSize -= player.getBet();
                return;
            }

        } else if (action == 1) {
            player.add(deck.deal());
            System.out.println("     Player has: " + player);

            player.calcValue();
            if (player.getValue() > 21) {
                System.out.println("     Player busts! Player loses bet of $" + player.getBet());
                stackSize -= player.getBet();
                return;
            }
        } else {
            stand = true;
        }

        while (!stand) {
            action = getAction(false, false);
            if (action == 1) {
                player.add(deck.deal());
                System.out.println("     Player has: " + player);

                player.calcValue();
                if (player.getValue() > 21) {
                    System.out.println("     Player busts! Player loses bet of $" + player.getBet());
                    stackSize -= player.getBet();
                    return;
                }
            } else {
                stand = true;
            }
        }

        System.out.println("\n     Player stands at " + player);

        System.out.println("\n     Dealer has: " + dealer);
        while (dealer.getValue() < 17) {
            System.out.println("       Dealer must hit...");
            dealer.add(deck.deal());
            System.out.println("     Dealer has: " + dealer);
            dealer.calcValue();
        }

        if (dealer.getValue() > 21) {
            System.out.println("      Dealer busts! Player is paid $" + player.getBet());
            stackSize += player.getBet();
            return;
        }

        if (player.getValue() > dealer.getValue()) {
            System.out.println("     Player's " + player.getValue() + " beats dealer's " + dealer.getValue());
            System.out.println("       Player is paid $" + player.getBet());
            stackSize += player.getBet();
        } else if (player.getValue() < dealer.getValue()) {
            System.out.println("     Dealer's " + dealer.getValue() + " beats player's " + player.getValue());
            System.out.println("       Player loses bet of $" + player.getBet());
            stackSize -= player.getBet();
        } else {
            System.out.println("     Player's " + player.getValue() + " ties dealer's " + dealer.getValue());
            System.out.println("     Push...");
        }

    }   

    private int getAction(boolean dd, boolean split) {
        boolean inputNeeded = true;
        String input = "";
        int action = -1;
        while (inputNeeded) {
            if (split)
                System.out.print("\n   Would you like to stand, hit, double-down, or split? => ");
            else if (dd)
                System.out.print("\n   Would you like to stand, hit, or double-down? => ");
            else
                System.out.print("\n   Would you like to stand or hit? => ");

            input = scan.nextLine();
            input.toLowerCase();
            switch (input) {
                case "stand": case "s":
                    inputNeeded = false;
                    action = 0;
                    break;
                case "hit": case "h":
                    inputNeeded = false;
                    action = 1;
                    break;
                case "double-down": case "double": case "dd": case "d":
                    if (dd) {
                        inputNeeded = false;
                        action = 2;
                        break;
                    }
                case "split":
                    if (split) {
                        inputNeeded = false;
                        action = 3;
                        break;
                    }
                default:
                    System.out.println("     I didn't get that...");
            }
        }
        return action;
    }
}