package dev.alexengrig.socnetgraphanalysis.randomizer;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CountryRandomizer {

    public static final Country RUSSIA = new Country(1, "Россия");
    public static final Country UKRAINE = new Country(10, "Украина");
    public static final Country BELARUS = new Country(20, "Беларусь");

    private static final List<Country> COUNTRIES = List.of(
            RUSSIA,
            UKRAINE,
            BELARUS
    );

    private final Randomizer randomizer;

    public Country randomCountry() {
        return randomizer.randomOfList(COUNTRIES);
    }

    @Value
    public static class Country {

        int id;
        String name;
    }
}
