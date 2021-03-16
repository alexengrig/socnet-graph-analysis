package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VkUser {
    private Integer id;
    private String firstName;
    private String lastName;
    private String birthday;

    private Integer age;

    private boolean accessed;

    private Integer audiosCount;
}
