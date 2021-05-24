package dev.alexengrig.socnetgraphanalysis.randomizer;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Randomizer {

    private final static Random RANDOM = new Random();

    public int randomInt(int from, int to) {
        return from + RANDOM.nextInt(to - from);
    }

    public boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }
}
