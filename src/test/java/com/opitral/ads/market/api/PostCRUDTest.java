package com.opitral.ads.market.api;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import org.junit.jupiter.api.Test;

import com.opitral.ads.market.api.domain.entity.*;
import com.opitral.ads.market.api.model.view.PostView;
import com.opitral.ads.market.api.utils.BaseTest;
import com.opitral.ads.market.api.utils.UtilsForTests;

import static org.junit.jupiter.api.Assertions.*;

import static com.jayway.jsonpath.JsonPath.read;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static com.opitral.ads.market.api.utils.ApiUrls.POST_API;
import static com.opitral.ads.market.api.utils.UtilsForTests.getRandomPostView;

@AutoConfigureMockMvc
public class PostCRUDTest extends BaseTest {

    @Autowired
    protected UtilsForTests utilsForTests;

    @Test
    public void createPostTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        PostView view = getRandomPostView(group.getId());

        MvcResult result = mockMvc.perform(post(POST_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(view)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").isNumber())
                .andExpect(jsonPath("$.error").isEmpty())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Integer postId = read(responseBody, "$.result");

        assertTrue(postRepository.findById(postId).isPresent());
    }

    @Test
    public void updatePostTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        PostEntity post = createPost(group.getId());
        PostView newPost = getRandomPostView(group.getId());
        newPost.setId(post.getId());

        mockMvc.perform(put(POST_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(utilsForTests.toJson(newPost)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        Optional<PostEntity> updatedEntity = postRepository.findById(post.getId());
        assertTrue(updatedEntity.isPresent());
        assertEquals(updatedEntity.get().getPublication().getText(), newPost.getPublication().getText());
    }

    @Test
    public void deletePostTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        PostEntity post = createPost(group.getId());

        mockMvc.perform(delete(POST_API + "/" + post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").isEmpty());

        assertFalse(postRepository.findById(post.getId()).isPresent());
    }

    @Test
    public void viewPostTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        PostEntity post = createPost(group.getId());

        mockMvc.perform(get(POST_API + "/" + post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value(post.getId()))
                .andExpect(jsonPath("$.result.groupId").value(post.getGroup().getId()))
                .andExpect(jsonPath("$.result.publication.type").value(post.getPublication().getType().toString()))
                .andExpect(jsonPath("$.result.publication.fileId").value(post.getPublication().getFileId()))
                .andExpect(jsonPath("$.result.publication.text").value(post.getPublication().getText()))
                .andExpect(jsonPath("$.result.publication.button.name").value(post.getPublication().getButton().getName()))
                .andExpect(jsonPath("$.result.publication.button.url").value(post.getPublication().getButton().getUrl()))
                .andExpect(jsonPath("$.result.publishDate").value(post.getPublishDate().toString()))
                .andExpect(jsonPath("$.result.publishTime").value(post.getPublishTime().format(DateTimeFormatter.ofPattern("HH:mm"))))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    public void viewAllPostsTest() throws Exception {
        UserEntity user = createUser();
        SubjectEntity subject = createSubject();
        CityEntity city = createCity(subject.getId());
        GroupEntity group = createGroup(user.getTelegramId(), city.getId());
        PostEntity post = createPost(group.getId());

        for (int i = 0; i < 5; i++) {
            createPost(group.getId());
        }

        Map<String, Object> restrict = new HashMap<>();
        restrict.put("ids", List.of(post.getId()));

        mockMvc.perform(get(POST_API).param("restrict", utilsForTests.toJson(restrict)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.total").value(1))
                .andExpect(jsonPath("$.result.responseList[0].id").value(post.getId()))
                .andExpect(jsonPath("$.result.responseList[0].groupId").value(post.getGroup().getId()))
                .andExpect(jsonPath("$.result.responseList[0].publication.type").value(post.getPublication().getType().toString()))
                .andExpect(jsonPath("$.result.responseList[0].publication.fileId").value(post.getPublication().getFileId()))
                .andExpect(jsonPath("$.result.responseList[0].publication.text").value(post.getPublication().getText()))
                .andExpect(jsonPath("$.result.responseList[0].publication.button.name").value(post.getPublication().getButton().getName()))
                .andExpect(jsonPath("$.result.responseList[0].publication.button.url").value(post.getPublication().getButton().getUrl()))
                .andExpect(jsonPath("$.result.responseList[0].publishDate").value(post.getPublishDate().toString()))
                .andExpect(jsonPath("$.result.responseList[0].publishTime").value(post.getPublishTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
    }
}