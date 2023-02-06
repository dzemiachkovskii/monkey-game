package org.phgdzlk.monkey_game;

public class Monke {
    public Hand[] hands = new Hand[2];

    public Monke() {
        hands[0] = new Hand();
        hands[0].isClenched = true;
        hands[1] = new Hand();
    }

    public int getBodyX() {
        return (hands[0].x + hands[1].x) >> 1; // bit-shift is a faster equivalent of division by two
    }

    public int getBodyY() {
        return (hands[0].y + hands[1].y + 100) >> 1; // bit-shift is a faster equivalent of division by two
    }

    public void checkDeath() {
        for (Hand hand : hands) {
            if (hand.x < 2 || hand.y < 2 || hand.x > 1148 || hand.y > 548) {
                throw new RuntimeException("GAME OVER");
            }
        }
    }
}

class Hand {
    public int x = 600;
    public int y = 300;
    public boolean isClenched = false;
}
