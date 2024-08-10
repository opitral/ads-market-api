package com.opitral.ads.market.api.utils;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.repositories.SubjectRepository;
import static com.opitral.ads.market.api.utils.UtilsForTests.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseTest {

    @Autowired
    public MockMvc mockMvc;

    public static final String ADMIN_TELEGRAM_ID = "12345678";
    public static final String VENDOR_TELEGRAM_ID = "12345678";
    public static final String CLIENT_TELEGRAM_ID = "12345678";

    public final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected SubjectRepository subjectRepository;

    @BeforeEach
    public void beforeTests() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Autowired
    protected EntityManagerFactory factory;

    @AfterEach
    public void cleaning() {
        subjectRepository.deleteAll();
    }

    public Integer createSubject() {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setNameUa(getRandomString(10));
        subjectEntity.setNameRu(getRandomString(10));
        subjectEntity.setNameEn(getRandomString(10));
        subjectRepository.save(subjectEntity);
        return subjectEntity.getId();
    }
}