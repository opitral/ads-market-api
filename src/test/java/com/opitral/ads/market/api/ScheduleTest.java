package com.opitral.ads.market.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;

import com.opitral.ads.market.api.domain.entity.*;
import com.opitral.ads.market.api.utils.BaseTest;
import com.opitral.ads.market.api.utils.UtilsForTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static com.opitral.ads.market.api.utils.ApiUrls.SCHEDULE_API;

@AutoConfigureMockMvc
public class ScheduleTest extends BaseTest {

    @Autowired
    protected UtilsForTests utilsForTests;

    @Test
    public void viewScheduleTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        createPost(group.getId());

        mockMvc.perform(get(SCHEDULE_API + "/" + group.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.group.id").value(group.getId()))
                .andExpect(jsonPath("$.result.dateTimes").isArray())
                .andExpect(jsonPath("$.error").isEmpty());
    }
}
