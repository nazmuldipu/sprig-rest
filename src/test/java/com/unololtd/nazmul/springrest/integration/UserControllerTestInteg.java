package com.unololtd.nazmul.springrest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unololtd.nazmul.springrest.entity.User;
import com.unololtd.nazmul.springrest.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTestInteg {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static String BASE_URL = "http://localhost";
    private final String BASE_PATH = "/api/v1/users";
    List<User> userList = new ArrayList<>();
    User user;

    @Autowired
    private UserRepository repository;

    @AfterEach
    public void clear() {
        this.repository.deleteAll();
        System.out.println("CLEARED >>>> " + this.repository.findAll().size());
    }

    @Test
    @DisplayName("POST /api/v1/users")
    public void should_create_a_user() throws Exception {
        //setup our mock service
        User postUser = new User("akib", "01912239643", "akib@gmail.com");

        // Execute the get request
        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postUser)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(postUser.getName())))
                .andExpect(jsonPath("$.phone", is(postUser.getPhone())))
                .andExpect(jsonPath("$.email", is(postUser.getEmail())));
    }

    @Test
    @DisplayName("PUT /api/v1/users")
    public void should_update_a_user() throws Exception {
        //setup our mock service
        User postUser = new User("akib", "01912239643", "akib@gmail.com");

        // Execute the get request
        final MvcResult result1 = mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postUser)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(postUser.getName())))
                .andExpect(jsonPath("$.phone", is(postUser.getPhone())))
                .andExpect(jsonPath("$.email", is(postUser.getEmail())))
                .andExpect(jsonPath("$.version", is(1)))
                .andReturn();
        User oldUser = mapper.readValue(result1.getResponse().getContentAsString(), User.class);
        String newName = "Javed";
        String newEmail = "akibul@gmail.com";
        postUser.setName(newName);
        postUser.setEmail(newEmail);

        final MvcResult result2 = mockMvc.perform(put(BASE_PATH + "/" + oldUser.getId())
                .content(mapper.writeValueAsBytes(postUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(oldUser.getId().intValue())))
                .andExpect(jsonPath("$.name", is(newName)))
                .andExpect(jsonPath("$.email", is(newEmail)))
                .andExpect(jsonPath("$.version", is(2)))
                .andReturn();
    }

    @Test
    @DisplayName("GET /api/v1/users/list")
    public void should_get_user_list() throws Exception {
        this.store_some_user();

        final ResultActions result = mockMvc.perform(get(BASE_PATH + "/list")).andExpect(status().isOk());
        result
                .andExpect(jsonPath("_links.self.href", is(BASE_URL + BASE_PATH + "/list")))

                .andExpect(jsonPath("_embedded.userList[0].id", is(this.userList.get(0).getId().intValue())))
                .andExpect(jsonPath("_embedded.userList[0].name", is(this.userList.get(0).getName())))
                .andExpect(jsonPath("_embedded.userList[0].phone", is(this.userList.get(0).getPhone())))
                .andExpect(jsonPath("_embedded.userList[0].email", is(this.userList.get(0).getEmail())))
                .andExpect(jsonPath("_embedded.userList[0].version", is(1)))

                .andExpect(jsonPath("_embedded.userList[1].id", is(this.userList.get(1).getId().intValue())))
                .andExpect(jsonPath("_embedded.userList[1].name", is(this.userList.get(1).getName())))
                .andExpect(jsonPath("_embedded.userList[1].phone", is(this.userList.get(1).getPhone())))
                .andExpect(jsonPath("_embedded.userList[1].email", is(this.userList.get(1).getEmail())))
                .andExpect(jsonPath("_embedded.userList[1].version", is(1)))

                .andExpect(jsonPath("_embedded.userList[2].id", is(this.userList.get(2).getId().intValue())))
                .andExpect(jsonPath("_embedded.userList[2].name", is(this.userList.get(2).getName())))
                .andExpect(jsonPath("_embedded.userList[2].phone", is(this.userList.get(2).getPhone())))
                .andExpect(jsonPath("_embedded.userList[2].email", is(this.userList.get(2).getEmail())))
                .andExpect(jsonPath("_embedded.userList[2].version", is(1)));
    }

    @Test
    @DisplayName("GET /api/v1/users")
    public void should_get_user_page() throws Exception {
        this.store_some_user();


        final ResultActions result = mockMvc.perform(get(BASE_PATH + "?size=3&page=0")).andExpect(status().isOk());

        result
                .andExpect(jsonPath("_links.self.href", is(BASE_URL + BASE_PATH)))
                .andExpect(jsonPath("page.size", is(3)))
                .andExpect(jsonPath("page.totalElements", is(3)))
                .andExpect(jsonPath("page.totalPages", is(1)))
                .andExpect(jsonPath("page.number", is(0)))

                .andExpect(jsonPath("_embedded.userList[2].id", is(this.userList.get(0).getId().intValue())))
                .andExpect(jsonPath("_embedded.userList[2].name", is(this.userList.get(0).getName())))
                .andExpect(jsonPath("_embedded.userList[2].phone", is(this.userList.get(0).getPhone())))
                .andExpect(jsonPath("_embedded.userList[2].email", is(this.userList.get(0).getEmail())))
                .andExpect(jsonPath("_embedded.userList[2].version", is(1)))

                .andExpect(jsonPath("_embedded.userList[1].id", is(this.userList.get(1).getId().intValue())))
                .andExpect(jsonPath("_embedded.userList[1].name", is(this.userList.get(1).getName())))
                .andExpect(jsonPath("_embedded.userList[1].phone", is(this.userList.get(1).getPhone())))
                .andExpect(jsonPath("_embedded.userList[1].email", is(this.userList.get(1).getEmail())))
                .andExpect(jsonPath("_embedded.userList[1].version", is(1)))

                .andExpect(jsonPath("_embedded.userList[0].id", is(this.userList.get(2).getId().intValue())))
                .andExpect(jsonPath("_embedded.userList[0].name", is(this.userList.get(2).getName())))
                .andExpect(jsonPath("_embedded.userList[0].phone", is(this.userList.get(2).getPhone())))
                .andExpect(jsonPath("_embedded.userList[0].email", is(this.userList.get(2).getEmail())))
                .andExpect(jsonPath("_embedded.userList[0].version", is(1)));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id}")
    public void should_get_a_user() throws Exception {
        this.store_some_user();
        User user = this.userList.get(0);
        final ResultActions result = mockMvc.perform(get(BASE_PATH + "/" + user.getId())).andExpect(status().isOk());

        result
                .andExpect(jsonPath("_links.self.href", is(BASE_URL + BASE_PATH + "/" + user.getId())))
                .andExpect(jsonPath("_links.users.href", is(BASE_URL + BASE_PATH + "/list")))

                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.phone", is(user.getPhone())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.version", is(1)));

    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id}")
    public void should_delete_a_user() throws Exception {
        this.store_some_user();
        final ResultActions result2 = mockMvc.perform(delete(BASE_PATH + "/" + this.userList.get(0).getId())).andExpect(status().isNoContent());

        final MvcResult result = mockMvc.perform(get(BASE_PATH + "/list")).andExpect(status().isOk()).andReturn();

        User oldUser = mapper.readValue(result.getResponse().getContentAsString(), User.class);

    }

    public void store_some_user() throws Exception {
        User user1 = new User("roton", "01882398798", "roton@gmail.com");
        User user2 = new User("Himel", "01978564789", "himel@gmail.com");
        User user3 = new User("rashel", "01567497856", "rashel@gmail.com");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        for (User user : users) {
            final MvcResult result1 = mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(user)))
                    .andExpect(status().isCreated()).andReturn();
            userList.add(mapper.readValue(result1.getResponse().getContentAsString(), User.class));
        }
    }

}
