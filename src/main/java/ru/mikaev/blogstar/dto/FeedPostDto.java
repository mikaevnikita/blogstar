package ru.mikaev.blogstar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.utils.FormatUtils;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedPostDto {
    private UserDto userDto;
    private String content;
    private String dateTime;

    public static FeedPostDto fromFeedPost(FeedPost feedPost){
        return FeedPostDto
                .builder()
                .userDto(UserDto.fromUser(feedPost.getUser()))
                .content(feedPost.getContent())
                .dateTime(FormatUtils.dateTimeToStr(feedPost.getDateTime()))
                .build();
    }
}
