package com.opitral.ads.market.api.subject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.opitral.ads.market.api.domain.entity.SubjectEntity;
import com.opitral.ads.market.api.model.view.SubjectView;
import com.opitral.ads.market.api.utils.BaseTest;
import com.opitral.ads.market.api.utils.UtilsForTests;
import static com.opitral.ads.market.api.utils.UtilsForTests.getRandomSubjectView;
import static com.opitral.ads.market.api.utils.ApiUrls.SUBJECT_API;
import static com.opitral.ads.market.api.utils.ApiUrls.SUBJECT_OPEN_API;

@AutoConfigureMockMvc
public class SubjectCRUDTest extends BaseTest {

    @Autowired
    protected UtilsForTests utilsForTests;

    public JSONObject createSubjectRequest(String login, SubjectView view) throws Exception {
        MvcResult result = mockMvc.perform(post(SUBJECT_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                        .content(utilsForTests.toJson(view)))
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject updateSubjectRequest(String login, SubjectView view) throws Exception {
        MvcResult result = mockMvc.perform(put(SUBJECT_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                        .content(utilsForTests.toJson(view)))
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject deleteSubjectRequest(String login, Integer id) throws Exception {
        MvcResult result = mockMvc.perform(delete(SUBJECT_API + "/" + id)
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                        )
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject getSubjectByIdRequest(String login, Integer id) throws Exception {
        MvcResult result = mockMvc.perform(get(SUBJECT_API + "/" + id)
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                        )
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject getAllSubjectsRequest(String login, Map<String, Object> restrict) throws Exception {
        MvcResult result = mockMvc.perform(get(SUBJECT_API)
                                .param("restrict", utilsForTests.toJson(restrict))
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                        )
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject getCountSubjectsRequest(String login, Map<String, Object> restrict) throws Exception {
        MvcResult result = mockMvc.perform(get(SUBJECT_API + "/count")
                                .param("restrict", utilsForTests.toJson(restrict))
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                )
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject getSubjectByIdOpenRequest(String login, Integer id, String local) throws Exception {
        MvcResult result = mockMvc.perform(get(SUBJECT_OPEN_API + "/" + id)
                                .header("Accept-Language", local)
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                )
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject getAllSubjectsOpenRequest(String login, String local, Map<String, Object> restrict) throws Exception {
        MvcResult result = mockMvc.perform(get(SUBJECT_OPEN_API)
                                .param("restrict", utilsForTests.toJson(restrict))
                                .header("Accept-Language", local)
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                )
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    public JSONObject getCountSubjectsOpenRequest(String login, String local, Map<String, Object> restrict) throws Exception {
        MvcResult result = mockMvc.perform(get(SUBJECT_OPEN_API + "/count")
                                .param("restrict", utilsForTests.toJson(restrict))
                                .header("Accept-Language", local)
//                        .header(utilsForTests.headerString, utilsForTests.getAuthToken())
//                        .header(utilsForTests.behalfOn, login)
                )
                .andExpect(status().isOk())
                .andReturn();

        return new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    public void allCanGetCountSubjectsTest() throws Exception {
        SubjectView view = getRandomSubjectView();
        view.setNameUa("countCrudNameUa");
        createSubjectRequest(ADMIN_TELEGRAM_ID, view);

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", "countCrud");

        JSONObject countResponse = getCountSubjectsRequest(ADMIN_TELEGRAM_ID, restrict);
        Integer countSubjects = countResponse.getInt("result");

        assertEquals(countSubjects, 1);
    }

    @Test
    public void allCanViewAllSubjectsTest() throws Exception {
        SubjectView viewFirst = getRandomSubjectView();
        createSubjectRequest(ADMIN_TELEGRAM_ID, viewFirst);

        SubjectView viewSecond = getRandomSubjectView();
        viewSecond.setNameEn("testCrudNameEn");
        createSubjectRequest(ADMIN_TELEGRAM_ID, viewSecond);

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", "test");

        JSONObject allSubjectsResponse = getAllSubjectsRequest(ADMIN_TELEGRAM_ID, restrict);
        JSONObject allSubjectsResultResponse = allSubjectsResponse.getJSONObject("result");
        JSONArray allSubjectsListResponse = allSubjectsResultResponse.getJSONArray("responseList");
        JSONObject subjectObjectResponse = allSubjectsListResponse.getJSONObject(0);

        assertEquals(allSubjectsResultResponse.getInt("total"), 1);
        assertEquals(subjectObjectResponse.get("nameUa"), viewSecond.getNameUa());
        assertEquals(subjectObjectResponse.get("nameRu"), viewSecond.getNameRu());
        assertEquals(subjectObjectResponse.get("nameEn"), viewSecond.getNameEn());
    }

    @Test
    public void allCanViewSubjectTest() throws Exception {
        SubjectView view = getRandomSubjectView();
        JSONObject createResponse = createSubjectRequest(ADMIN_TELEGRAM_ID, view);
        Integer subjectId = createResponse.getInt("result");

        JSONObject subjectResponse = getSubjectByIdRequest(ADMIN_TELEGRAM_ID, subjectId);
        JSONObject subjectResultResponse = subjectResponse.getJSONObject("result");
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectId);

        assertTrue(subjectEntity.isPresent());
        assertEquals(view.getNameUa(), subjectResultResponse.get("nameUa"));
        assertEquals(view.getNameRu(), subjectResultResponse.get("nameRu"));
        assertEquals(view.getNameEn(), subjectResultResponse.get("nameEn"));
    }

    @Test
    public void adminCanCreateSubjectTest() throws Exception {
        SubjectView view = getRandomSubjectView();
        JSONObject createResponse = createSubjectRequest(ADMIN_TELEGRAM_ID, view);
        Integer subjectId = createResponse.getInt("result");
        assertTrue(subjectRepository.findById(subjectId).isPresent());
    }

    @Test
    public void adminCanUpdateSubjectTest() throws Exception {
        SubjectView view = getRandomSubjectView();
        JSONObject createResponse = createSubjectRequest(ADMIN_TELEGRAM_ID, view);
        Integer subjectId = createResponse.getInt("result");

        SubjectView newView = getRandomSubjectView();
        newView.setId(subjectId);

        JSONObject updateResponse = updateSubjectRequest(ADMIN_TELEGRAM_ID, newView);
        boolean status = updateResponse.getBoolean("result");
        assertTrue(status);

        Optional<SubjectEntity> updatedEntity = subjectRepository.findById(subjectId);
        assertTrue(updatedEntity.isPresent());
        assertEquals(updatedEntity.get().getNameUa(), newView.getNameUa());
    }

    @Test
    public void adminCanDeleteSubjectTest() throws Exception {
        SubjectView view = getRandomSubjectView();
        JSONObject createResponse = createSubjectRequest(ADMIN_TELEGRAM_ID, view);
        Integer subjectId = createResponse.getInt("result");

        JSONObject deleteResponse = deleteSubjectRequest(ADMIN_TELEGRAM_ID, subjectId);
        boolean status = deleteResponse.getBoolean("result");
        assertTrue(status);

        assertFalse(subjectRepository.findById(subjectId).isPresent());
    }

    @Test
    public void allCanGetCountSubjectsOpenTest() throws Exception {
        SubjectView view = getRandomSubjectView();
        view.setNameUa("countOpenNameUa");
        createSubjectRequest(ADMIN_TELEGRAM_ID, view);

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", "countOpen");

        JSONObject countResponse = getCountSubjectsOpenRequest(ADMIN_TELEGRAM_ID, "ua", restrict);
        Integer countSubjects = countResponse.getInt("result");

        assertEquals(countSubjects, 1);
    }

    @Test
    public void allCanViewAllSubjectsOpenTest() throws Exception {
        SubjectView viewFirst = getRandomSubjectView();
        createSubjectRequest(ADMIN_TELEGRAM_ID, viewFirst);

        SubjectView viewSecond = getRandomSubjectView();
        viewSecond.setNameEn("testOpenNameEn");
        createSubjectRequest(ADMIN_TELEGRAM_ID, viewSecond);

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("query", "testOpen");

        JSONObject allSubjectsResponse = getAllSubjectsOpenRequest(ADMIN_TELEGRAM_ID, "en", restrict);
        JSONObject allSubjectsResultResponse = allSubjectsResponse.getJSONObject("result");
        JSONArray allSubjectsListResponse = allSubjectsResultResponse.getJSONArray("responseList");
        JSONObject subjectObjectResponse = allSubjectsListResponse.getJSONObject(0);

        assertEquals(allSubjectsResultResponse.getInt("total"), 1);
        assertEquals(subjectObjectResponse.get("name"), viewSecond.getNameEn());
    }

    @Test
    public void allCanViewSubjectOpenTest() throws Exception {
        SubjectView view = getRandomSubjectView();
        JSONObject createResponse = createSubjectRequest(ADMIN_TELEGRAM_ID, view);
        Integer subjectId = createResponse.getInt("result");

        JSONObject subjectResponse = getSubjectByIdOpenRequest(ADMIN_TELEGRAM_ID, subjectId, "ru");
        JSONObject subjectResultResponse = subjectResponse.getJSONObject("result");
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(subjectId);

        assertTrue(subjectEntity.isPresent());
        assertEquals(view.getNameRu(), subjectResultResponse.get("name"));
    }
}
