package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecordParameters;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClusterRecordConverter implements Converter<VkUser, ClusterRecord> {

    @NonNull
    @Override
    public ClusterRecord convert(@NonNull VkUser source) {
        return ClusterRecord.builder()
                .id(source.getId())
                .label(String.join(" ", source.getFirstName(), source.getLastName()))
                .parameters(new ClusterRecordParameters(Map.ofEntries(
                        Map.entry("Accessed", source.isAccessed() ? 1d : 0d),
                        Map.entry("Age", source.getAge().doubleValue()),
                        Map.entry("Sex", source.getSex().doubleValue()),
                        Map.entry("City", source.getCity().doubleValue()),
                        Map.entry("Country", source.getCountry().doubleValue()),
                        Map.entry("Friend status", source.getFriendStatus().doubleValue()),
                        Map.entry("Relation", source.getRelation().doubleValue()),
                        Map.entry("Political", source.getPolitical().doubleValue()),
                        Map.entry("Religion", source.getReligion().doubleValue()),
                        Map.entry("Life main", source.getLifeMain().doubleValue()),
                        Map.entry("People main", source.getPeopleMain().doubleValue()),
                        Map.entry("Smoking", source.getSmoking().doubleValue()),
                        Map.entry("Alcohol", source.getAlcohol().doubleValue()),
                        Map.entry("Common friends count", source.getCommonFriendsCount().doubleValue()),
                        Map.entry("Friends count", source.getFriendsCount().doubleValue()),
                        Map.entry("Followers count", source.getFollowersCount().doubleValue()),
                        Map.entry("Audios count", source.getAudiosCount().doubleValue()),
                        Map.entry("Videos count", source.getVideosCount().doubleValue()),
                        Map.entry("Photos count", source.getPhotosCount().doubleValue()),
                        Map.entry("Groups count", source.getGroupsCount().doubleValue()),
                        Map.entry("Albums count", source.getAlbumsCount().doubleValue()),
                        Map.entry("Notes count", source.getNotesCount().doubleValue()),
                        Map.entry("Pages count", source.getPagesCount().doubleValue()))))
                .build();
    }
}
