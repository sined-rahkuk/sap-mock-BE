package com.mwaysolution.sapMock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mwaysolution.sapMock.model.User;
import com.mwaysolution.sapMock.model.UserRegistrationStatus;
import com.mwaysolution.sapMock.service.UserServiceImpl;

@SpringBootTest(classes = SapMockApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private final String SAP_USERNAME = "SapUsername";
    private final String EXCHANGE_USERNAME = "ExchangeUsername";
    private final String EXCHANGE_DOMAIN = "ExchangeDomain";
    private final String TIME_ZONE = "+02:00";
    private final UserRegistrationStatus REGISTRATION_STATUS = UserRegistrationStatus.INIT;
    private final String FIRST_NAME = "John";
    private final String LAST_NAME = "Doe";

    @Autowired
    UserServiceImpl userServiceImpl;

    @After
    public void tearDown() {
        userServiceImpl.get().forEach(user -> {
            userServiceImpl.deleteById(user.getId());
        });
    }

    @Test
    public void testCreateValidUser() {
        User userReference = userServiceImpl.save(createInitialUser());

        assertThat(userReference.getId()).isNotNull();
        assertThat(userReference.getSapUsername()).isEqualTo(SAP_USERNAME);
        assertThat(userReference.getExchangeUsername()).isEqualTo(EXCHANGE_USERNAME);
        assertThat(userReference.getExchangeDomain()).isEqualTo(EXCHANGE_DOMAIN);
        assertThat(userReference.getTimeZone()).isEqualTo(TIME_ZONE);
        assertThat(userReference.getCreationDate()).isNotNull();
        assertThat(userReference.getModificationDate()).isNotNull();
        assertThat(userReference.getRegistrationStatus()).isEqualTo(REGISTRATION_STATUS);
        assertThat(userReference.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(userReference.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    public void testCreateUserWithExistingSapUsername() {
        userServiceImpl.save(createInitialUser());
        User user = createUser(SAP_USERNAME, EXCHANGE_USERNAME + "updated", EXCHANGE_DOMAIN + "updated",
                TIME_ZONE + "updated", UserRegistrationStatus.UNREGISTERED, FIRST_NAME + "updated",
                LAST_NAME + "updated");

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            userServiceImpl.save(user);
        }).withMessage("Current user " + SAP_USERNAME + " already created");
    }

    @Test
    public void testUpdateUser() {
        User user = userServiceImpl.save(createInitialUser());
        user.setTimeZone("+10:00");
        user.setFirstName("Jackie");
        user.setLastName("Alto");
        User updatedUserReference = userServiceImpl.save(user);

        assertThat(updatedUserReference.getSapUsername()).isEqualTo(SAP_USERNAME);
        assertThat(updatedUserReference.getExchangeUsername()).isEqualTo(EXCHANGE_USERNAME);
        assertThat(updatedUserReference.getExchangeDomain()).isEqualTo(EXCHANGE_DOMAIN);
        assertThat(updatedUserReference.getTimeZone()).isEqualTo("+10:00");
        assertThat(updatedUserReference.getRegistrationStatus()).isEqualTo(REGISTRATION_STATUS);
        assertThat(updatedUserReference.getFirstName()).isEqualTo("Jackie");
        assertThat(updatedUserReference.getLastName()).isEqualTo("Alto");
    }

    @Test
    public void testDeleteUser() {
        User user = userServiceImpl.save(createInitialUser());
        userServiceImpl.deleteById(user.getId());

        assertThat(userServiceImpl.getById(user.getId())).isNull();
    }

    private User createUser(String sapUsername, String exchangeUsername, String exchangeDomain, String timeZone,
            UserRegistrationStatus registrationStatus, String firstName, String lastName) {
        User user = new User();
        user.setSapUsername(sapUsername);
        user.setExchangeUsername(exchangeUsername);
        user.setExchangeDomain(exchangeDomain);
        user.setTimeZone(timeZone);
        user.setRegistrationStatus(registrationStatus);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    private User createInitialUser() {
        return createUser(SAP_USERNAME, EXCHANGE_USERNAME, EXCHANGE_DOMAIN, TIME_ZONE, REGISTRATION_STATUS, FIRST_NAME,
                LAST_NAME);
    }
}
