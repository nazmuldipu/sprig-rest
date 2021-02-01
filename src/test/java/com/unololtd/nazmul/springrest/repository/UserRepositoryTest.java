package com.unololtd.nazmul.springrest.repository;

import com.unololtd.nazmul.springrest.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class) //provide a bridge between Spring Boot test features and JUnit
@DataJpaTest // test JPA repositories
@AutoConfigureTestDatabase(replace = NONE)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @After
    public void clear() {
        this.repository.deleteAll();
        System.out.println("CLEARED >>>> " + this.repository.findAll().size());
    }

    @Test
    public void should_find_no_user_if_repository_is_empty() {
        Iterable<User> users = repository.findAll();

        Assertions.assertThat(users).isEmpty();
    }

    @Test
    public void should_store_a_user() {
        User user = repository.save(new User("akib", "01912239643", "akib@gmail.com"));
        Assertions.assertThat(user).hasFieldOrProperty("id").isNotNull();
        Assertions.assertThat(user).hasFieldOrPropertyWithValue("name", "akib");
        Assertions.assertThat(user).hasFieldOrPropertyWithValue("phone", "01912239643");
        Assertions.assertThat(user).hasFieldOrPropertyWithValue("email", "akib@gmail.com");
    }

    @Test
    public void should_find_all_users() {
        User user1 = new User("roton", "01882398798", "roton@gmail.com");
        User user2 = new User("Himel", "01978564789","himel@gmail.com");
        User user3 = new User("rashel","01567497856", "rashel@gmail.com");
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);

        Iterable<User> users = repository.findAll();

        Assertions.assertThat(users).hasSize(3).contains(user1, user1, user1);
    }

    @Test
    public void should_find_user_by_id() {
        User user1 = new User("Tutul", "01534129315", "tutul@gmail.com");
        entityManager.persist(user1);

        User user2= new User("Karim", "01654987825", "karim@gmail.com");
        entityManager.persist(user2);

        User foundUser = repository.findById(user2.getId()).get();

        Assertions.assertThat(foundUser).isEqualTo(user2);
    }

    @Test
    public void should_update_user_by_id() {
        User user1 = new User("Jamil", "01534129316", "jamil@gmail.com");
        entityManager.persist(user1);

        User user2 = new User("Kabil", "01534129326", "kabil@gmail.com");
        entityManager.persist(user2);

        User updatedTut = new User("updated Jamil", "updated 01534129316", "jamil@gmail.com");

        User user = repository.findById(user2.getId()).get();
        user.setName(updatedTut.getName());
        user.setPhone(updatedTut.getPhone());
        user.setEmail(updatedTut.getEmail());
        repository.save(user);

        User checkTut = repository.findById(user2.getId()).get();

        Assertions.assertThat(checkTut.getId()).isEqualTo(user2.getId());
        Assertions.assertThat(checkTut.getName()).isEqualTo(updatedTut.getName());
        Assertions.assertThat(checkTut.getPhone()).isEqualTo(updatedTut.getPhone());
        Assertions.assertThat(checkTut.getEmail()).isEqualTo(updatedTut.getEmail());
    }

    @Test
    public void should_delete_user_by_id() {
        User user1 = new User("Hasan", "01956458974", "hasan@gmail.com");
        entityManager.persist(user1);

        User user2 = new User("Emran", "01956458975", "emran@gmail.com");
        entityManager.persist(user2);

        User user3 = new User("Tabiz", "01956454978", "tabiz@gmail.com");
        entityManager.persist(user3);

        repository.deleteById(user2.getId());

        Iterable<User> users = repository.findAll();

        Assertions.assertThat(users).hasSize(2).contains(user1, user3);
    }

    @Test
    public void should_delete_all_users() {
        entityManager.persist(new User("Hasem", "01966458974", "hasem@gmail.com"));
        entityManager.persist(new User("Jakir", "01957458974", "jakir@gmail.com"));

        repository.deleteAll();

        Assertions.assertThat(repository.findAll()).isEmpty();
    }


    @Test
    public void should_store() {
        //Given
        User user = repository.save(new User("shiva", "01819567484", "shiva@gmail.com"));

        //Then
        Assertions.assertThat(user).hasFieldOrProperty("id");
        Assertions.assertThat(user).hasFieldOrPropertyWithValue("name", "shiva");
        Assertions.assertThat(user).hasFieldOrPropertyWithValue("phone", "01819567484");
        Assertions.assertThat(user).hasFieldOrPropertyWithValue("email", "shiva@gmail.com");
    }

}
