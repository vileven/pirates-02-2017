package services;

import api.Application;
import api.model.User;
import api.repository.UserRepository;
import api.services.DbUserService;
import api.services.generic.UserService;
import api.utils.WebConfiguration;
import api.utils.info.UserCreationInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Vileven on 24.03.17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class DataBasePosgresTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Before
    public void setup() {
        userRepository.deleteAll();
        userRepository.save(new User("sergey", "email@mail.ru",
                passwordEncoder.encode("qwerty123"), LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    public void createUser() {
        final User newUser = userService.createUser(new UserCreationInfo("sergey1", "email1@mail.ru",
                "qwerty123"));
        assertNotEquals(null, newUser);
        assertEquals("sergey1", newUser.getLogin());
    }

    @Test
    public void changeEmail(){
        final User user = userRepository.findUserByLogin("sergey");
        userService.changeEmail(user.getId(), "e@yandex.ru");
        final User changedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getEmail(), changedUser.getEmail());
        assertEquals("e@yandex.ru", changedUser.getEmail());
    }

    @Test
    public void changeLoginl(){
        final User user = userRepository.findUserByLogin("sergey");
        userService.changeLogin(user.getId(), "maxim");
        final User changedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getLogin(), changedUser.getLogin());
        assertEquals("maxim", changedUser.getLogin());
    }

    @Test
    public void changePassword(){
        final User user = userRepository.findUserByLogin("sergey");
        userService.changePassword(user.getId(), "123qwerty");
        final User changedUser = userRepository.findOne(user.getId());
        assertNotEquals(user.getPassword(), changedUser.getPassword());
        assertEquals(true,
                passwordEncoder.matches("123qwerty", changedUser.getPassword()));
    }

    @Test
    public void deleteUser() {
        User user = userRepository.findUserByLogin("sergey");
        assertNotEquals(null, user);
        userService.deleteUserbyId(user.getId());
        user = userRepository.findUserByLogin("sergey");
        assertEquals(null,user);
    }

    @Test
    public void authenticateUserSuccess() {
        final User user = userRepository.findUserByLogin("sergey");

        User authUser = userService.authenticateUser(user.getLogin(), "qwerty123");
        assertNotEquals(null, authUser);
        assertEquals(user, authUser);

        authUser = userService.authenticateUser(user.getEmail(), "qwerty123");
        assertNotEquals(null, authUser);
        assertEquals(user, authUser);
    }

    @Test
    public void authenticateUserError() {
        final User user = userRepository.findUserByLogin("sergey");

        User authUser = userService.authenticateUser(user.getLogin(), "wrong password");
        assertNull(authUser);

        authUser = userService.authenticateUser("Unexpected login", "qwerty123");
        assertNull(authUser);
    }




//    @Configuration
//    static class UserServiceTestContextConfiguration {
//
//        @Bean
//        public UserService userService() {
//           return new DbUserService(userRepository(), passwordEncoder());
//        }
//
//        @Bean
//        public UserRepository userRepository() {
//            return Mockito.mock(UserRepository.class);
//        }
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//    }
}