package dev.alexengrig.socnetgraphanalysis.randomizer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LastNameRandomizer {

    private final static List<String> MALE_LAST_NAMES;
    private final static List<String> FEMALE_LAST_NAMES;

    static {
        MALE_LAST_NAMES = Arrays.asList(
                "Александр",
                "Дмитрий",
                "Максим",
                "Сергей",
                "Андрей",
                "Алексей",
                "Артём",
                "Илья",
                "Кирилл",
                "Михаил",
                "Никита",
                "Георгий",
                "Матвей",
                "Роман",
                "Егор",
                "Григорий",
                "Арсений",
                "Иван",
                "Денис",
                "Евгений",
                "Тимофей",
                "Владислав",
                "Игорь",
                "Владимир",
                "Павел",
                "Руслан",
                "Марк",
                "Константин",
                "Тимур",
                "Олег",
                "Ярослав",
                "Антон",
                "Николай",
                "Данил"
        );
    }

    static {
        FEMALE_LAST_NAMES = Arrays.asList(
                "Анастасия",
                "Анна",
                "Екатерина",
                "София",
                "Дарья",
                "Ксения",
                "Александра",
                "Алиса",
                "Алина",
                "Виктория",
                "Вероника",
                "Елена",
                "Марина",
                "Жанна",
                "Снежана",
                "Светлана",
                "Ольга",
                "Полина",
                "Рената",
                "Кристина",
                "Наталья",
                "Эльвира",
                "Мария"
        );
    }

    private final Randomizer randomizer;

    public String randomMale() {
        return MALE_LAST_NAMES.get(randomizer.randomInt(0, MALE_LAST_NAMES.size() - 1));
    }

    public String randomFemale() {
        return FEMALE_LAST_NAMES.get(randomizer.randomInt(0, FEMALE_LAST_NAMES.size() - 1));
    }
}
