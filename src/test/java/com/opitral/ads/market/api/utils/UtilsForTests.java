package com.opitral.ads.market.api.utils;

import java.time.LocalTime;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.opitral.ads.market.api.model.view.*;

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

    public static CityView getRandomCityView(int subjectId) {
        CityView cityView = new CityView();
        cityView.setNameUa(getRandomString(10));
        cityView.setNameRu(getRandomString(10));
        cityView.setNameEn(getRandomString(10));
        cityView.setSubjectId(subjectId);
        return cityView;
    }

    public static UserView getRandomUserView() {
        UserView userView = new UserView();
        userView.setTelegramId(getRandomString(10));
        userView.setFirstName(getRandomString(10));
        userView.setLastName(getRandomString(10));
        userView.setAllowedGroupsCount(5);
        return userView;
    }

    public static GroupView getRandomGroupView(String userTelegramId, int cityId) {
        GroupView groupView = new GroupView();
        groupView.setName(getRandomString(10));
        groupView.setLink(getRandomString(10));
        groupView.setGroupTelegramId(getRandomString(10));

        groupView.setUserTelegramId(userTelegramId);
        groupView.setCityId(cityId);

        groupView.setWorkingHoursStart(LocalTime.of(11, 0));
        groupView.setWorkingHoursEnd(LocalTime.of(20, 0));
        groupView.setPostIntervalInMinutes(60);

        groupView.setPriceForOneDay(new PriceView(10, 15));
        groupView.setPriceForOneWeek(new PriceView(50, 75));
        groupView.setPriceForTwoWeeks(new PriceView(90, 140));
        groupView.setPriceForOneMonth(new PriceView(170, 270));

        groupView.setAveragePostViews(5);

        return groupView;
    }

}
