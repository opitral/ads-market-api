package com.opitral.ads.market.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.opitral.ads.market.api.domain.entity.*;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.opitral.ads.market.api.model.view.GroupView;
import com.opitral.ads.market.api.utils.BaseTest;
import com.opitral.ads.market.api.utils.UtilsForTests;

import static org.junit.jupiter.api.Assertions.*;

import static com.jayway.jsonpath.JsonPath.read;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.opitral.ads.market.api.utils.ApiUrls.GROUP_API;
import static com.opitral.ads.market.api.utils.UtilsForTests.getRandomGroupView;

@AutoConfigureMockMvc
public class GroupCRUDTest extends BaseTest {

    @Autowired
    protected UtilsForTests utilsForTests;

    @Test
    public void createGroupTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupView view = getRandomGroupView(user.getTelegramId(), city.getId());

        MvcResult result = mockMvc.perform(post(GROUP_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(view)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isNumber())
                .andExpect(jsonPath("$.error").isEmpty())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Integer groupId = read(responseBody, "$.result");

        assertTrue(groupRepository.findById(groupId).isPresent());
    }

    @Test
    public void updateGroupTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        GroupView newView = getRandomGroupView(user.getTelegramId(), city.getId());
        newView.setId(group.getId());

        mockMvc.perform(put(GROUP_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(newView)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        Optional<GroupEntity> updatedEntity = groupRepository.findById(group.getId());
        assertTrue(updatedEntity.isPresent());
        assertEquals(updatedEntity.get().getName(), newView.getName());
    }

    @Test
    public void deleteGroupTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());

        mockMvc.perform(delete(GROUP_API + "/" + group.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        assertFalse(groupRepository.findById(group.getId()).isPresent());
    }

    @Test
    public void viewGroupTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());

        mockMvc.perform(get(GROUP_API + "/" + group.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value(group.getId()))

                .andExpect(jsonPath("$.result.name").value(group.getName()))
                .andExpect(jsonPath("$.result.link").value(group.getLink()))
                .andExpect(jsonPath("$.result.groupTelegramId").value(group.getGroupTelegramId()))

                .andExpect(jsonPath("$.result.userTelegramId").value(group.getUser().getTelegramId()))
                .andExpect(jsonPath("$.result.cityId").value(group.getCity().getId()))

                .andExpect(jsonPath("$.result.workingHoursStart").value(group.getWorkingHoursStart().toString()))
                .andExpect(jsonPath("$.result.workingHoursEnd").value(group.getWorkingHoursEnd().toString()))
                .andExpect(jsonPath("$.result.postIntervalInMinutes").value(group.getPostIntervalInMinutes()))

                .andExpect(jsonPath("$.result.priceForOneDay").value(group.getPriceForOneDay()))
                .andExpect(jsonPath("$.result.priceForOneWeek").value(group.getPriceForOneWeek()))
                .andExpect(jsonPath("$.result.priceForTwoWeeks").value(group.getPriceForTwoWeeks()))
                .andExpect(jsonPath("$.result.priceForOneMonth").value(group.getPriceForOneMonth()))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void viewAllGroupsTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        for (int i = 0; i < 5; i++) {
            createGroup(user.getTelegramId(), city.getId());
        }

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", group.getName());

        mockMvc.perform(get(GROUP_API).param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.total").value(1))
                .andExpect(jsonPath("$.result.responseList[0].id").value(group.getId()))

                .andExpect(jsonPath("$.result.responseList[0].name").value(group.getName()))
                .andExpect(jsonPath("$.result.responseList[0].link").value(group.getLink()))
                .andExpect(jsonPath("$.result.responseList[0].groupTelegramId").value(group.getGroupTelegramId()))

                .andExpect(jsonPath("$.result.responseList[0].userTelegramId").value(group.getUser().getTelegramId()))
                .andExpect(jsonPath("$.result.responseList[0].cityId").value(group.getCity().getId()))

                .andExpect(jsonPath("$.result.responseList[0].workingHoursStart").value(group.getWorkingHoursStart().toString()))
                .andExpect(jsonPath("$.result.responseList[0].workingHoursEnd").value(group.getWorkingHoursEnd().toString()))
                .andExpect(jsonPath("$.result.responseList[0].postIntervalInMinutes").value(group.getPostIntervalInMinutes()))

                .andExpect(jsonPath("$.result.responseList[0].priceForOneDay").value(group.getPriceForOneDay()))
                .andExpect(jsonPath("$.result.responseList[0].priceForOneWeek").value(group.getPriceForOneWeek()))
                .andExpect(jsonPath("$.result.responseList[0].priceForTwoWeeks").value(group.getPriceForTwoWeeks()))
                .andExpect(jsonPath("$.result.responseList[0].priceForOneMonth").value(group.getPriceForOneMonth()))

                .andExpect(jsonPath("$.result.responseList[0].averagePostViews").value(group.getAveragePostViews()))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void allCanGetCountGroupsTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        createGroup(user.getTelegramId(), city.getId());

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("userTelegramId", user.getTelegramId());

        mockMvc.perform(get(GROUP_API + "/count").param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(1));
    }
}
