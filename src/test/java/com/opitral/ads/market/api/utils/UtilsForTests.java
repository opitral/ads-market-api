package com.opitral.ads.market.api.utils;

import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.opitral.ads.market.api.model.view.SubjectView;

@Service
@RequiredArgsConstructor
public class UtilsForTests {
    private final ObjectMapper objectMapper;
    private static final Random random = new Random();

    @SneakyThrows
    public String toJson(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    public static String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder res = new StringBuilder();
        Random rnd = new Random();
        while (res.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            res.append(SALTCHARS.charAt(index));
        }
        return res.toString();
    }

    public static SubjectView getRandomSubjectView() {
        SubjectView subjectView = new SubjectView();
        subjectView.setNameUa(getRandomString(10));
        subjectView.setNameRu(getRandomString(10));
        subjectView.setNameEn(getRandomString(10));
        return subjectView;
    }
}
