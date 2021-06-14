package dev.alexengrig.socnetgraphanalysis.randomizer;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class Randomizer {

    private final static Random RANDOM = new Random();

    public int randomInt(int from, int to) {
        return from + RANDOM.nextInt(to - from);
    }

    public <T> T randomOfList(List<T> list) {
        return list.get(randomInt(0, list.size()));
    }

    public boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }
}
