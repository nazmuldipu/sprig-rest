package com.unololtd.nazmul.springrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unololtd.nazmul.springrest.assembler.UserModelAssembler;
import com.unololtd.nazmul.springrest.entity.User;
import com.unololtd.nazmul.springrest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(controllers = UserControllerTestInteg.class)
//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Import({UserModelAssembler.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper mapper;
    private static String BASE_URL = "http://localhost";
    private static String BASE_PATH = "/api/v1/users";
    private static final long ID = 1;
    private List<User> userList;

    @BeforeEach
    public void should_set_data() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(1L, "User1", "01954564871", "user1@gmail.com"));
        this.userList.add(new User(2L, "User2", "01954564872", "user2@gmail.com"));
        this.userList.add(new User(3L, "User3", "01954564873", "user3@gmail.com"));
    }

    @Test
    public void should_save_a_user() throws Exception {
        User user = new User(4L, "Ahmed", "01880005611", "ahmed@gmail.com");
        given(service.save(any(User.class))).willReturn(user);

        final ResultActions result =
                mockMvc.perform(
                        post(BASE_PATH)
                                .content(mapper.writeValueAsBytes(new User("Ahmed", "01880005611", "ahmed@gmail.com")))
                                .contentType(MediaTypes.HAL_JSON_VALUE));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(user.getId().intValue())))
                .andExpect(jsonPath("name", is(user.getName())))
                .andExpect(jsonPath("phone", is(user.getPhone())))
                .andExpect(jsonPath("email", is(user.getEmail())))
                .andExpect(jsonPath("_links.self.href", is(BASE_URL + BASE_PATH + "/" + user.getId())))
                .andExpect(jsonPath("_links.users.href", is(BASE_URL + BASE_PATH + "/list")));

    }

//    @Test
//    public void should_update_a_user() {
//
//    }

    @Test
    public void should_get_list() throws Exception {
        given(service.getAll()).willReturn(this.userList);

        mockMvc.perform(get(BASE_PATH + "/list").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.userList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.userList[1].id", is(2)))
                .andExpect(jsonPath("$._embedded.userList[2].id", is(3)))
                .andExpect(jsonPath("$._embedded.userList[0].name", is("User1")))
                .andExpect(jsonPath("$._embedded.userList[1].name", is("User2")))
                .andExpect(jsonPath("$._embedded.userList[2].name", is("User3")))
                .andExpect(jsonPath("$._embedded.userList[0].phone", is("01954564871")))
                .andExpect(jsonPath("$._embedded.userList[1].phone", is("01954564872")))
                .andExpect(jsonPath("$._embedded.userList[2].phone", is("01954564873")))
                .andExpect(jsonPath("$._embedded.userList[0].email", is("user1@gmail.com")))
                .andExpect(jsonPath("$._embedded.userList[1].email", is("user2@gmail.com")))
                .andExpect(jsonPath("$._embedded.userList[2].email", is("user3@gmail.com")))
                .andExpect(jsonPath("$._embedded.userList[0]._links.self.href", is("http://localhost/api/v1/users/1")))
                .andExpect(jsonPath("$._embedded.userList[1]._links.self.href", is("http://localhost/api/v1/users/2")))
                .andExpect(jsonPath("$._embedded.userList[2]._links.self.href", is("http://localhost/api/v1/users/3")))
                .andReturn();
    }

    @Test
    public void should_get_page() throws Exception {
        final Page<User> page = new PageImpl<>(this.userList);
        given(service.getAll(PageRequest.of(0, 2))).willReturn(page);

        mockMvc.perform(get(BASE_PATH + "?size=2&page=0").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.userList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.userList[1].id", is(2)))
                .andExpect(jsonPath("$._embedded.userList[2].id", is(3)))
                .andExpect(jsonPath("$._embedded.userList[0].name", is("User1")))
                .andExpect(jsonPath("$._embedded.userList[1].name", is("User2")))
                .andExpect(jsonPath("$._embedded.userList[2].name", is("User3")))
                .andExpect(jsonPath("$._embedded.userList[0].phone", is("01954564871")))
                .andExpect(jsonPath("$._embedded.userList[1].phone", is("01954564872")))
                .andExpect(jsonPath("$._embedded.userList[2].phone", is("01954564873")))
                .andExpect(jsonPath("$._embedded.userList[0].email", is("user1@gmail.com")))
                .andExpect(jsonPath("$._embedded.userList[1].email", is("user2@gmail.com")))
                .andExpect(jsonPath("$._embedded.userList[2].email", is("user3@gmail.com")))
                .andExpect(jsonPath("$._embedded.userList[0]._links.self.href", is("http://localhost/api/v1/users/1")))
                .andExpect(jsonPath("$._embedded.userList[1]._links.self.href", is("http://localhost/api/v1/users/2")))
                .andExpect(jsonPath("$._embedded.userList[2]._links.self.href", is("http://localhost/api/v1/users/3")))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/v1/users")))
                .andReturn();
    }

}
