package ru.mikaev.blogstar.services;

public interface CaptchaService {
    boolean validate(String captchaResponse);
}
