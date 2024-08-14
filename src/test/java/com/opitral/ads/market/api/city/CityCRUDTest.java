package com.opitral.ads.market.api.city;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static com.jayway.jsonpath.JsonPath.read;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.opitral.ads.market.api.domain.entity.CityEntity;
import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.model.view.CityView;
import com.opitral.ads.market.api.utils.BaseTest;
import com.opitral.ads.market.api.utils.UtilsForTests;
import static com.opitral.ads.market.api.utils.ApiUrls.*;
import static com.opitral.ads.market.api.utils.UtilsForTests.getRandomCityView;

@AutoConfigureMockMvc
public class CityCRUDTest extends BaseTest {

    @Autowired
    protected UtilsForTests utilsForTests;

    @Test
    public void adminCanCreateCityTest() throws Exception {
        SubjectEntity subject = createSubject();
        CityView view = getRandomCityView(subject.getId());

        MvcResult result = mockMvc.perform(post(CITY_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(view)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isNumber())
                .andExpect(jsonPath("$.error").isEmpty())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Integer cityId = read(responseBody, "$.result");

        assertTrue(cityRepository.findById(cityId).isPresent());
    }

    @Test
    public void adminCanUpdateCityTest() throws Exception {
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        CityView newView = getRandomCityView(subject.getId());
        newView.setId(city.getId());

        mockMvc.perform(put(CITY_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(newView)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        Optional<CityEntity> updatedEntity = cityRepository.findById(city.getId());
        assertTrue(updatedEntity.isPresent());
        assertEquals(updatedEntity.get().getNameUa(), newView.getNameUa());
    }

    @Test
    public void adminCanDeleteCityTest() throws Exception {
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());

        mockMvc.perform(delete(CITY_API + "/" + city.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        assertFalse(cityRepository.findById(city.getId()).isPresent());
    }

    @Test
    public void allCanViewCityTest() throws Exception {
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());

        mockMvc.perform(get(CITY_API + "/" + city.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value(city.getId()))
                .andExpect(jsonPath("$.result.nameUa").value(city.getNameUa()))
                .andExpect(jsonPath("$.result.nameRu").value(city.getNameRu()))
                .andExpect(jsonPath("$.result.nameEn").value(city.getNameEn()))
                .andExpect(jsonPath("$.result.subject.id").value(city.getSubject().getId()))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void allCanViewAllCitiesTest() throws Exception {
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", city.getNameRu());

        mockMvc.perform(get(CITY_API).param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.total").value(1))
                .andExpect(jsonPath("$.result.responseList[0].id").value(city.getId()))
                .andExpect(jsonPath("$.result.responseList[0].nameUa").value(city.getNameUa()))
                .andExpect(jsonPath("$.result.responseList[0].nameRu").value(city.getNameRu()))
                .andExpect(jsonPath("$.result.responseList[0].nameEn").value(city.getNameEn()))
                .andExpect(jsonPath("$.result.responseList[0].subject.id").value(city.getSubject().getId()))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void allCanGetCountCitiesTest() throws Exception {
        SubjectEntity subject = createSubject();
        for (int i = 0; i < 10; i++) {
            createCity(subject.getId());
        }

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("subjectId", subject.getId());

        mockMvc.perform(get(CITY_API + "/count").param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(10));
    }
    
}
