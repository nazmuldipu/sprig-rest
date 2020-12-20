package com.unololtd.nazmul.springrest.repository;

import com.unololtd.nazmul.springrest.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static junit.framework.TestCase.assertNull;

@ActiveProfiles("test") //active bean definition profiles
@DataJpaTest // test JPA repositories
@RunWith(SpringRunner.class) //provide a bridge between Spring Boot test features and JUnit
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createReadUpdateReadDelete(){
        //Given
        create();

        // when
        Optional<User> found = userRepository.findUserByUsername("01912239643");

        // then
        Assertions.assertThat(found.get().getId().longValue());
        Assertions.assertThat(found.get().getUsername().equals("akib"));

        String newEmail = "akib@yahoo.com";
        //update
        User oldUser = found.get();
        oldUser.setEmail(newEmail);
        entityManager.persist(oldUser);
        entityManager.flush();

        //Again when
        found = userRepository.findUserByEmail(newEmail);

        // then
        Assertions.assertThat(found.get().getId().longValue());
        Assertions.assertThat(found.get().getId().equals(oldUser.getId()));
        Assertions.assertThat(found.get().getUsername().equals("akib"));

        //finally remove and check
        userRepository.deleteById(found.get().getId());
        assertNull(userRepository.findUserByEmail(newEmail).orElse(null));
    }


    public void create(){
        User user = new User("akib", "01912239643","akib@gmail.com" );
        entityManager.persist(user);
        entityManager.flush();
    }
}
