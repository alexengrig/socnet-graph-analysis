package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class VkUserPropertyValueConverter {

    private static final Map<VkUserProperty, Function<VkUser, String>> USER_MAPPER_BY_PROPERTY;
    private static final Map<VkUserProperty, Function<Integer, String>> VALUE_MAPPER_BY_PROPERTY;

    static {
        USER_MAPPER_BY_PROPERTY = new HashMap<>(VkUserProperty.values().length);
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.ACCESSED, user -> user.isAccessed() ? "Доступен" : "Не доступен");
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.AGE, user -> String.valueOf(user.getAge()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.SEX, user -> switch (user.getSex()) {
            case 1 -> "Женский";
            case 2 -> "Мужской";
            default -> "Неизвестно";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.CITY, VkUser::getCityName);
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.COUNTRY, VkUser::getCountryName);
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.FRIEND_STATUS, user -> switch (user.getFriendStatus()) {
            case 1 -> "Исходящий запрос";
            case 2 -> "Входящий запрос";
            case 3 -> "Друг";
            default -> "Нет";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.RELATION, user -> switch (user.getRelation()) {
            case 1 -> "Одинок";
            case 2 -> "Встречается";
            case 3 -> "Увлечение";
            case 4 -> "Брак";
            case 5 -> "Все сложно";
            case 6 -> "В активном поиске";
            case 7 -> "Любовь";
            case 8 -> "Гражданский брак";
            default -> "Не выбрано";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.POLITICAL, user -> switch (user.getPolitical()) {
            case 1 -> "Коммунистические";
            case 2 -> "Социалистические";
            case 3 -> "Умеренные";
            case 4 -> "Либеральные";
            case 5 -> "Консервативные";
            case 6 -> "Монархические";
            case 7 -> "Ультраконсервативные";
            case 8 -> "Индифферентные";
            case 9 -> "Либертарианские";
            default -> "Не выбраны";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.RELIGION, user -> switch (user.getReligion()) {
            case 167 -> "Иудаизм";
            case 102 -> "Православие";
            case 101 -> "Католицизм";
            case 107 -> "Протестантизм";
            case 124 -> "Ислам";
            case 129 -> "Буддизм";
            case 139 -> "Конфуцианство";
            case 200 -> "Светский гуманизм";
            case 201 -> "Пастафарианство";
            default -> "Не выбрано";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.LIFE_MAIN, user -> switch (user.getLifeMain()) {
            case 1 -> "Семья и дети";
            case 2 -> "Карьера и деньги";
            case 3 -> "Развлечения и отдых";
            case 4 -> "Наука и исследования";
            case 5 -> "Совершенствование мира";
            case 6 -> "Саморазвитие";
            case 7 -> "Красота и искусство";
            case 8 -> "Слава и влияние";
            default -> "Не выбрано";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.PEOPLE_MAIN, user -> switch (user.getPeopleMain()) {
            case 1 -> "Ум и креативность";
            case 2 -> "Доброта и честность";
            case 3 -> "Красота и здоровье";
            case 4 -> "Власть и богатство";
            case 5 -> "Смелость и упорство";
            case 6 -> "Юмор и жизнелюбие";
            default -> "Не выбрано";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.SMOKING, user -> switch (user.getSmoking()) {
            case 1 -> "Резко негативное";
            case 2 -> "Негативное";
            case 3 -> "Компромиссное";
            case 4 -> "Нейтральное";
            case 5 -> "Положительное";
            default -> "Не выбрано";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.ALCOHOL, user -> switch (user.getAlcohol()) {
            case 1 -> "Резко негативное";
            case 2 -> "Негативное";
            case 3 -> "Компромиссное";
            case 4 -> "Нейтральное";
            case 5 -> "Положительное";
            default -> "Не выбрано";
        });
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.COMMON_FRIENDS_COUNT, user -> String.valueOf(user.getCommonFriendsCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.FRIENDS_COUNT, user -> String.valueOf(user.getFriendsCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.FOLLOWERS_COUNT, user -> String.valueOf(user.getFollowersCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.AUDIOS_COUNT, user -> String.valueOf(user.getAudiosCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.VIDEOS_COUNT, user -> String.valueOf(user.getVideosCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.PHOTOS_COUNT, user -> String.valueOf(user.getPhotosCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.GROUPS_COUNT, user -> String.valueOf(user.getGroupsCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.ALBUMS_COUNT, user -> String.valueOf(user.getAlbumsCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.NOTES_COUNT, user -> String.valueOf(user.getNotesCount()));
        USER_MAPPER_BY_PROPERTY.put(VkUserProperty.PAGES_COUNT, user -> String.valueOf(user.getPagesCount()));
        for (VkUserProperty property : VkUserProperty.values()) {
            if (!USER_MAPPER_BY_PROPERTY.containsKey(property)) {
                throw new IllegalStateException("No user mapper for property: " + property);
            }
        }
    }

    static {
        VALUE_MAPPER_BY_PROPERTY = new HashMap<>(VkUserProperty.values().length);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.ACCESSED, value -> value == 1 ? "Доступен" : "Не доступен");
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.AGE, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.SEX, value -> switch (value) {
            case 1 -> "Женский";
            case 2 -> "Мужской";
            default -> "Неизвестно";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.FRIEND_STATUS, value -> switch (value) {
            case 1 -> "Исходящий запрос";
            case 2 -> "Входящий запрос";
            case 3 -> "Друг";
            default -> "Нет";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.RELATION, value -> switch (value) {
            case 1 -> "Одинок";
            case 2 -> "Встречается";
            case 3 -> "Увлечение";
            case 4 -> "Брак";
            case 5 -> "Все сложно";
            case 6 -> "В активном поиске";
            case 7 -> "Любовь";
            case 8 -> "Гражданский брак";
            default -> "Не выбрано";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.POLITICAL, value -> switch (value) {
            case 1 -> "Коммунистические";
            case 2 -> "Социалистические";
            case 3 -> "Умеренные";
            case 4 -> "Либеральные";
            case 5 -> "Консервативные";
            case 6 -> "Монархические";
            case 7 -> "Ультраконсервативные";
            case 8 -> "Индифферентные";
            case 9 -> "Либертарианские";
            default -> "Не выбраны";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.RELIGION, value -> switch (value) {
            case 167 -> "Иудаизм";
            case 102 -> "Православие";
            case 101 -> "Католицизм";
            case 107 -> "Протестантизм";
            case 124 -> "Ислам";
            case 129 -> "Буддизм";
            case 139 -> "Конфуцианство";
            case 200 -> "Светский гуманизм";
            case 201 -> "Пастафарианство";
            default -> "Не выбрано";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.LIFE_MAIN, value -> switch (value) {
            case 1 -> "Семья и дети";
            case 2 -> "Карьера и деньги";
            case 3 -> "Развлечения и отдых";
            case 4 -> "Наука и исследования";
            case 5 -> "Совершенствование мира";
            case 6 -> "Саморазвитие";
            case 7 -> "Красота и искусство";
            case 8 -> "Слава и влияние";
            default -> "Не выбрано";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.PEOPLE_MAIN, value -> switch (value) {
            case 1 -> "Ум и креативность";
            case 2 -> "Доброта и честность";
            case 3 -> "Красота и здоровье";
            case 4 -> "Власть и богатство";
            case 5 -> "Смелость и упорство";
            case 6 -> "Юмор и жизнелюбие";
            default -> "Не выбрано";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.SMOKING, value -> switch (value) {
            case 1 -> "Резко негативное";
            case 2 -> "Негативное";
            case 3 -> "Компромиссное";
            case 4 -> "Нейтральное";
            case 5 -> "Положительное";
            default -> "Не выбрано";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.ALCOHOL, value -> switch (value) {
            case 1 -> "Резко негативное";
            case 2 -> "Негативное";
            case 3 -> "Компромиссное";
            case 4 -> "Нейтральное";
            case 5 -> "Положительное";
            default -> "Не выбрано";
        });
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.COMMON_FRIENDS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.FRIENDS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.FOLLOWERS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.AUDIOS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.VIDEOS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.PHOTOS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.GROUPS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.ALBUMS_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.NOTES_COUNT, String::valueOf);
        VALUE_MAPPER_BY_PROPERTY.put(VkUserProperty.PAGES_COUNT, String::valueOf);
        for (VkUserProperty property : VkUserProperty.values()) {
            if (VkUserProperty.CITY == property || VkUserProperty.COUNTRY == property) {
                continue;
            }
            if (!VALUE_MAPPER_BY_PROPERTY.containsKey(property)) {
                throw new IllegalStateException("No value mapper for property: " + property);
            }
        }
    }

    public String convert(VkUserProperty property, VkUser user) {
        Function<VkUser, String> mapper = USER_MAPPER_BY_PROPERTY.get(property);
        return mapper.apply(user);
    }

    public String convert(String propertyLabel, Double userValue,
                          Map<Integer, String> countryMap, Map<Integer, String> cityMap) {
        VkUserProperty property = VkUserProperty.valueByLabel(propertyLabel);
        int value = (int) Math.round(userValue);
        if (VkUserProperty.COUNTRY == property) {
            String countryName = countryMap.get(value);
            if (Objects.nonNull(countryName)) {
                return countryName;
            }
            return "<..>";
        } else if (VkUserProperty.CITY == property) {
            String cityName = cityMap.get(value);
            if (Objects.nonNull(cityName)) {
                return cityName;
            }
            return "<..>";
        }
        Function<Integer, String> mapper = VALUE_MAPPER_BY_PROPERTY.get(property);
        return mapper.apply(value);
    }
}
