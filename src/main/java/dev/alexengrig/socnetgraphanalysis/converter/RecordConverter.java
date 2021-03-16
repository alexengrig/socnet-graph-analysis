package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.clustering.Parameters;
import dev.alexengrig.socnetgraphanalysis.clustering.Record;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecordConverter implements Converter<VkUser, Record> {

    @Override
    public Record convert(VkUser source) {
        return Record.builder()
                .label(source.getFirstName() + " " + source.getLastName())
                .parameters(Parameters.builder()
                        .parameter("age", source.getAge().doubleValue())
                        .parameter("accessed", source.isAccessed() ? 1d : 0d)
                        .parameter("audios count", source.getAudiosCount().doubleValue())
                        .build())
                .build();
    }
}
