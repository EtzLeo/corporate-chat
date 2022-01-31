package ru.simbirsoft.corporatechat.domain.dto;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeDto {
    String src;
    @Nullable
    String views;
    @Nullable
    String likes;

    @Override
    public String toString() {
        return "{" +
                "\"src\":" + "\"" + src + "\"" +
                ",\n \"views\":" + "\"" + views + "\"" +
                ",\n \"likes\":" + "\"" + likes + "\"" +
                '}';
    }
}
