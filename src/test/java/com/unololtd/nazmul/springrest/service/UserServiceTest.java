package com.unololtd.nazmul.springrest.service;

import com.unololtd.nazmul.springrest.entity.User;
import com.unololtd.nazmul.springrest.repository.UserRepository;
import com.unololtd.nazmul.springrest.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    /*
     * The Service that we want to test
     */
    @InjectMocks
    private UserServiceImpl userService;

    /*
     * A Mock version of UserRepository for use in our test
     */
    @Mock
    UserRepository userRepository;

    @Test
    public void test_getById() {
        //Setup our mock
        long id = 1;
        User mockUser = new User("akib","01912239643", "akib@gmail.com");
        mockUser.setId(id);
        Mockito.when(userRepository.count()).thenReturn(123L);
        Mockito.when(userRepository.existsById(id)).thenReturn(true);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        //Execute all Service call
        long userCount = userRepository.count();
        boolean exist = userService.exists(id);
        Optional<User> returnShip = userService.getById(id);

        //Assert to the response
        Assert.assertEquals(123L, userCount);
        Assert.assertTrue(exist);
        Assertions.assertTrue(returnShip.isPresent(), "User was not found");
        Assertions.assertSame(returnShip.get(), mockUser, "User should be the same");
    }

    @Test
    public void test_getAll() {
        //Setup our mock
        User mockUser1 = new User("akib", "01912239643", "akib@gmail.com");
        mockUser1.setId(1L);
        User mockUser2 = new User("javed", "01912239644","javed@gmail.com");
        mockUser2.setId(2L);
        User mockUser3 = new User("sohel", "01912239645","sohel@gmail.com");
        mockUser3.setId(3L);
        List<User> userList = new ArrayList<>();
        userList.add(mockUser1);
        userList.add(mockUser2);
        userList.add(mockUser3);

        Mockito.when(userRepository.count()).thenReturn(3L);
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //Execute all Service call

        //Assert to the response
        Assert.assertEquals(3L, userService.count());
        Assert.assertEquals(3L, userService.getAll().size());
    }

    @Test
    public void test_searchUser() {
        //Setup our mock
        User mockUser1 = new User("akib", "01912239643", "akib@gmail.com");
        mockUser1.setId(1L);
        User mockUser2 = new User("javed", "01912239644", "javed@gmail.com");
        mockUser2.setId(2L);
        User mockUser3 = new User("sohel", "01912239645", "sohel@gmail.com");
        mockUser3.setId(3L);
        List<User> userList = new ArrayList<>();
        userList.add(mockUser1);
        userList.add(mockUser2);
        userList.add(mockUser3);
        Page<User> userPage = new PageImpl(userList);

        Mockito.when(userRepository.findDistinctByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(any(), any(), any())).thenReturn(userPage);

        //Execute all Service call
        Page<User> response = userService.searchUser("", 1);

        //Assert to the response
        Assert.assertEquals(3, response.getTotalElements());
        Assert.assertEquals(1, response.getTotalPages());
    }

    @Test
    public void test_saveUser() {
        //Setup our mock
        User mockUser1 = new User("akib", "01912239643", "akib@gmail.com");
        mockUser1.setId(1L);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(mockUser1);

        //Execute all Service call
        User response = userService.save(mockUser1);

        //Assert to the response
        Assert.assertEquals("User id didn't matched", 1L, response.getId().longValue());
        Assert.assertEquals("01912239643", response.getUsername());

    }
}
