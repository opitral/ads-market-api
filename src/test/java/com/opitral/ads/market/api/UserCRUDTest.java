package com.opitral.ads.market.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import org.junit.jupiter.api.Test;

import com.opitral.ads.market.api.domain.entity.UserEntity;
import com.opitral.ads.market.api.model.view.UserView;
import com.opitral.ads.market.api.utils.BaseTest;
import com.opitral.ads.market.api.utils.UtilsForTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.jayway.jsonpath.JsonPath.read;

import static org.junit.jupiter.api.Assertions.*;

import static com.opitral.ads.market.api.utils.ApiUrls.USER_API;
import static com.opitral.ads.market.api.utils.UtilsForTests.getRandomUserView;

@AutoConfigureMockMvc
public class UserCRUDTest extends BaseTest {

    @Autowired
    protected UtilsForTests utilsForTests;

    @Test
    public void createUserTest() throws Exception {
        UserView view = getRandomUserView();

        MvcResult result = mockMvc.perform(post(USER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(view)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isNumber())
                .andExpect(jsonPath("$.error").isEmpty())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Integer userId = read(responseBody, "$.result");

        assertTrue(userRepository.findById(userId).isPresent());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserEntity user = createUser();
        UserView newView = getRandomUserView();
        newView.setId(user.getId());

        mockMvc.perform(put(USER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(newView)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        Optional<UserEntity> updatedEntity = userRepository.findById(user.getId());
        assertTrue(updatedEntity.isPresent());
        assertEquals(updatedEntity.get().getTelegramId(), newView.getTelegramId());
    }

    @Test
    public void deleteUserTest() throws Exception {
        UserEntity user = createUser();

        mockMvc.perform(delete(USER_API + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    public void viewUserTest() throws Exception {
        UserEntity user = createUser();

        mockMvc.perform(get(USER_API + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value(user.getId()))
                .andExpect(jsonPath("$.result.telegramId").value(user.getTelegramId()))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void viewAllUsersTest() throws Exception {
        UserEntity user = createUser();
        for (int i = 0; i < 10; i++) {
            createUser();
        }

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", user.getTelegramId());

        mockMvc.perform(get(USER_API).param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.total").value(1))
                .andExpect(jsonPath("$.result.responseList[0].id").value(user.getId()))
                .andExpect(jsonPath("$.result.responseList[0].telegramId").value(user.getTelegramId()))
                .andExpect(jsonPath("$.error").isEmpty());
    }
}
