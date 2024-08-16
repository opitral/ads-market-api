package com.opitral.ads.market.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import org.junit.jupiter.api.Test;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.model.view.SubjectView;
import com.opitral.ads.market.api.utils.BaseTest;
import com.opitral.ads.market.api.utils.UtilsForTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static com.jayway.jsonpath.JsonPath.read;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.opitral.ads.market.api.utils.UtilsForTests.getRandomSubjectView;
import static com.opitral.ads.market.api.utils.ApiUrls.SUBJECT_API;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class SubjectCRUDTest extends BaseTest {

    @Autowired
    protected UtilsForTests utilsForTests;

    @Test
    public void createSubjectTest() throws Exception {
        SubjectView view = getRandomSubjectView();

        MvcResult result = mockMvc.perform(post(SUBJECT_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(view)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isNumber())
                .andExpect(jsonPath("$.error").isEmpty())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Integer subjectId = read(responseBody, "$.result");

        assertTrue(subjectRepository.findById(subjectId).isPresent());
    }

    @Test
    public void updateSubjectTest() throws Exception {
        SubjectEntity subject = createSubject();
        SubjectView newView = getRandomSubjectView();
        newView.setId(subject.getId());

        mockMvc.perform(put(SUBJECT_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(newView)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        Optional<SubjectEntity> updatedEntity = subjectRepository.findById(subject.getId());
        assertTrue(updatedEntity.isPresent());
        assertEquals(updatedEntity.get().getNameUa(), newView.getNameUa());
    }

    @Test
    public void deleteSubjectTest() throws Exception {
        SubjectEntity subject = createSubject();

        mockMvc.perform(delete(SUBJECT_API + "/" + subject.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        assertFalse(subjectRepository.findById(subject.getId()).isPresent());
    }

    @Test
    public void viewSubjectTest() throws Exception {
        SubjectEntity subject = createSubject();

        mockMvc.perform(get(SUBJECT_API + "/" + subject.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value(subject.getId()))
                .andExpect(jsonPath("$.result.nameUa").value(subject.getNameUa()))
                .andExpect(jsonPath("$.result.nameRu").value(subject.getNameRu()))
                .andExpect(jsonPath("$.result.nameEn").value(subject.getNameEn()))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void viewAllSubjectsTest() throws Exception {
        SubjectEntity subject = createSubject();
        for (int i = 0; i < 10; i++) {
            createSubject();
        }

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", subject.getNameUa());

        mockMvc.perform(get(SUBJECT_API).param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.total").value(1))
                .andExpect(jsonPath("$.result.responseList[0].id").value(subject.getId()))
                .andExpect(jsonPath("$.result.responseList[0].nameUa").value(subject.getNameUa()))
                .andExpect(jsonPath("$.result.responseList[0].nameRu").value(subject.getNameRu()))
                .andExpect(jsonPath("$.result.responseList[0].nameEn").value(subject.getNameEn()))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void getCountSubjectsTest() throws Exception {
        SubjectEntity subject = createSubject();
        for (int i = 0; i < 10; i++) {
            createSubject();
        }

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", subject.getNameRu());

        mockMvc.perform(get(SUBJECT_API + "/count").param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(1));
    }

}
