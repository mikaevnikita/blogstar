package ru.mikaev.blogstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mikaev.blogstar.dto.CaptchaResponseDto;

import java.util.Collections;

@Service
public class CaptchaServiceRecaptchaImpl implements CaptchaService
{
    private final static String CAPTCHA_URL
            = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Autowired
    private RestTemplate restTemplate;

    public boolean validate(String recaptchaResponse){
        String url = String.format(CAPTCHA_URL, recaptchaSecret, recaptchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        return response.isSuccess();
    }

}
