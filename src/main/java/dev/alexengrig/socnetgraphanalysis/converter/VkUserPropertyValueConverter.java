package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class VkUserPropertyValueConverter {

    private static final Map<VkUserProperty, Function<VkUser, String>> MAPPER_BY_PROPERTY;

    static {
        MAPPER_BY_PROPERTY = new HashMap<>(VkUserProperty.values().length);
        MAPPER_BY_PROPERTY.put(VkUserProperty.ACCESSED, user -> user.isAccessed() ? "Доступен" : "Не доступен");
        MAPPER_BY_PROPERTY.put(VkUserProperty.AGE, user -> String.valueOf(user.getAge()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.SEX, user -> switch (user.getSex()) {
            case 1 -> "Женский";
            case 2 -> "Мужской";
            default -> "Неизвестно";
        });
        MAPPER_BY_PROPERTY.put(VkUserProperty.CITY, VkUser::getCityName);
        MAPPER_BY_PROPERTY.put(VkUserProperty.COUNTRY, VkUser::getCountryName);
        MAPPER_BY_PROPERTY.put(VkUserProperty.FRIEND_STATUS, user -> switch (user.getFriendStatus()) {
            case 1 -> "Исходящий запрос";
            case 2 -> "Входящий запрос";
            case 3 -> "Друг";
            default -> "Нет";
        });
        MAPPER_BY_PROPERTY.put(VkUserProperty.RELATION, user -> switch (user.getRelation()) {
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
        MAPPER_BY_PROPERTY.put(VkUserProperty.POLITICAL, user -> switch (user.getPolitical()) {
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
        MAPPER_BY_PROPERTY.put(VkUserProperty.RELIGION, user -> switch (user.getReligion()) {
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
        MAPPER_BY_PROPERTY.put(VkUserProperty.LIFE_MAIN, user -> switch (user.getLifeMain()) {
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
        MAPPER_BY_PROPERTY.put(VkUserProperty.PEOPLE_MAIN, user -> switch (user.getPeopleMain()) {
            case 1 -> "Ум и креативность";
            case 2 -> "Доброта и честность";
            case 3 -> "Красота и здоровье";
            case 4 -> "Власть и богатство";
            case 5 -> "Смелость и упорство";
            case 6 -> "Юмор и жизнелюбие";
            default -> "Не выбрано";
        });
        MAPPER_BY_PROPERTY.put(VkUserProperty.SMOKING, user -> switch (user.getSmoking()) {
            case 1 -> "Резко негативное";
            case 2 -> "Негативное";
            case 3 -> "Компромиссное";
            case 4 -> "Нейтральное";
            case 5 -> "Положительное";
            default -> "Не выбрано";
        });
        MAPPER_BY_PROPERTY.put(VkUserProperty.ALCOHOL, user -> switch (user.getAlcohol()) {
            case 1 -> "Резко негативное";
            case 2 -> "Негативное";
            case 3 -> "Компромиссное";
            case 4 -> "Нейтральное";
            case 5 -> "Положительное";
            default -> "Не выбрано";
        });
        MAPPER_BY_PROPERTY.put(VkUserProperty.COMMON_FRIENDS_COUNT, user -> String.valueOf(user.getCommonFriendsCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.FRIENDS_COUNT, user -> String.valueOf(user.getFriendsCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.FOLLOWERS_COUNT, user -> String.valueOf(user.getFollowersCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.AUDIOS_COUNT, user -> String.valueOf(user.getAudiosCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.VIDEOS_COUNT, user -> String.valueOf(user.getVideosCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.PHOTOS_COUNT, user -> String.valueOf(user.getPhotosCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.GROUPS_COUNT, user -> String.valueOf(user.getGroupsCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.ALBUMS_COUNT, user -> String.valueOf(user.getAlbumsCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.NOTES_COUNT, user -> String.valueOf(user.getNotesCount()));
        MAPPER_BY_PROPERTY.put(VkUserProperty.PAGES_COUNT, user -> String.valueOf(user.getPagesCount()));
        for (VkUserProperty property : VkUserProperty.values()) {
            if (!MAPPER_BY_PROPERTY.containsKey(property)) {
                throw new IllegalStateException("No mapper for property: " + property);
            }
        }
    }

    public String convert(VkUserProperty property, VkUser user) {
        return MAPPER_BY_PROPERTY.get(property).apply(user);
    }
}
