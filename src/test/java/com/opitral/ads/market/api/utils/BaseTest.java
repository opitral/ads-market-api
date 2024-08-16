package com.opitral.ads.market.api.utils;

import java.time.LocalTime;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.opitral.ads.market.api.domain.entity.*;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.repositories.*;
import static com.opitral.ads.market.api.utils.UtilsForTests.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseTest {

    @Autowired
    public MockMvc mockMvc;

    public final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected SubjectRepository subjectRepository;
    @Autowired
    protected CityRepository cityRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected GroupRepository groupRepository;

    @BeforeEach
    public void beforeTests() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    protected EntityManagerFactory factory;

    @AfterEach
    public void cleaning() {
        subjectRepository.deleteAll();
        cityRepository.deleteAll();
        userRepository.deleteAll();
        groupRepository.deleteAll();
    }

    public SubjectEntity createSubject() {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setNameUa(getRandomString(10));
        subjectEntity.setNameRu(getRandomString(10));
        subjectEntity.setNameEn(getRandomString(10));
        subjectRepository.save(subjectEntity);
        return subjectEntity;
    }

    public CityEntity createCity(int subjectId) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setNameUa(getRandomString(10));
        cityEntity.setNameRu(getRandomString(10));
        cityEntity.setNameEn(getRandomString(10));
        cityEntity.setSubject(subjectRepository.findById(subjectId).orElseThrow(
                () -> new NoSuchEntityException(SubjectEntity.class.getName(), "by id: " + subjectId)
        ));
        cityRepository.save(cityEntity);
        return cityEntity;
    }

    public UserEntity createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setTelegramId(getRandomString(10));
        userEntity.setFirstName(getRandomString(10));
        userEntity.setLastName(getRandomString(10));
        userEntity.setAllowedGroupsCount(5);
        userRepository.save(userEntity);
        return userEntity;
    }

    public GroupEntity createGroup(String userTelegramId, int cityId) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(getRandomString(10));
        groupEntity.setLink(getRandomString(10));
        groupEntity.setGroupTelegramId(getRandomString(10));
        groupEntity.setUserTelegramId(userTelegramId);
        groupEntity.setUser(userRepository.findByTelegramId(userTelegramId).orElseThrow(
                () -> new NoSuchEntityException(UserEntity.class.getName(), "by telegram id: " + userTelegramId)
        ));
        groupEntity.setCityId(cityId);
        groupEntity.setCity(cityRepository.findById(cityId).orElseThrow(
                () -> new NoSuchEntityException(CityEntity.class.getName(), "by id: " + cityId)
        ));
        groupEntity.setWorkingHoursStart(LocalTime.of(11, 0));
        groupEntity.setWorkingHoursEnd(LocalTime.of(20, 0));
        groupEntity.setPostIntervalInMinutes(60);
        groupEntity.setPriceForOneDay(new Price(10, 15));
        groupEntity.setPriceForOneWeek(new Price(50, 75));
        groupEntity.setPriceForTwoWeeks(new Price(90, 140));
        groupEntity.setPriceForOneMonth(new Price(170, 270));
        groupEntity.setAveragePostViews(5);
        groupRepository.save(groupEntity);
        return groupEntity;
    }
}