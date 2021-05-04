package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.clustering.Parameters;
import dev.alexengrig.socnetgraphanalysis.clustering.Record;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RecordConverter implements Converter<VkUser, Record> {

    @NonNull
    @Override
    public Record convert(@NonNull VkUser source) {
        return Record.builder()
                .label(source.getFirstName() + " " + source.getLastName())
                .parameters(Parameters.builder()
                        .parameter("Accessed", source.isAccessed() ? 1d : 0d)
                        .parameter("Age", source.getAge().doubleValue())
                        .parameter("Sex", source.getSex().doubleValue())
                        .parameter("City", source.getCity().doubleValue())
                        .parameter("Country", source.getCountry().doubleValue())
                        .parameter("Friend status", source.getFriendStatus().doubleValue())
                        .parameter("Relation", source.getRelation().doubleValue())
                        .parameter("Political", source.getPolitical().doubleValue())
                        .parameter("Religion", source.getReligion().doubleValue())
                        .parameter("Life main", source.getLifeMain().doubleValue())
                        .parameter("People main", source.getPeopleMain().doubleValue())
                        .parameter("Smoking", source.getSmoking().doubleValue())
                        .parameter("Alcohol", source.getAlcohol().doubleValue())
                        .parameter("Common friends count", source.getCommonFriendsCount().doubleValue())
                        .parameter("Friends count", source.getFriendsCount().doubleValue())
                        .parameter("Followers count", source.getFollowersCount().doubleValue())
                        .parameter("Audios count", source.getAudiosCount().doubleValue())
                        .parameter("Videos count", source.getVideosCount().doubleValue())
                        .parameter("Photos count", source.getPhotosCount().doubleValue())
                        .parameter("Groups count", source.getGroupsCount().doubleValue())
                        .parameter("Albums count", source.getAlbumsCount().doubleValue())
                        .parameter("Notes count", source.getNotesCount().doubleValue())
                        .parameter("Pages count", source.getPagesCount().doubleValue())
                        .build())
                .build();
    }
}
