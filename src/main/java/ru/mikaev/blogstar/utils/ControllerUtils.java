package ru.mikaev.blogstar.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import ru.mikaev.blogstar.dto.FeedPostDto;
import ru.mikaev.blogstar.dto.UserDto;
import ru.mikaev.blogstar.entities.FeedPost;
import ru.mikaev.blogstar.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerUtils {
    public static Map<String, List<String>> getErrors(BindingResult bindingResult){
        Map<String, List<String>> errorsMap = new HashMap<>();

        bindingResult.getFieldErrors().stream().forEach(fieldError -> {
            String key = fieldError.getField() + "Errors";
            List<String> errors = errorsMap.get(key);
            if(errors == null) {
                errors = new ArrayList<>();
                errorsMap.put(key, errors);
            }
            errors.add(fieldError.getDefaultMessage());
        });
        return errorsMap;
    }

    public static List<UserDto> transformToUserDto(List<User> users){
        return users
                .stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public static Page<FeedPostDto> transformToPageOfFeedPostDto(Page<FeedPost> page){
        return page.map(feedPost -> FeedPostDto.fromFeedPost(feedPost));
    }

    public static Page<UserDto> transformToUserDto(Page<User> users){
        return users.map(UserDto::fromUser);
    }
}
