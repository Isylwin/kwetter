package nl.oscar.kwetter.service;

import nl.oscar.kwetter.domain.Credentials;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.domain.UserInformation;
import nl.oscar.kwetter.service.user.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class DatabaseFiller {

    @Inject
    private UserService service;

    @PostConstruct
    public void init() {
        User user1 = User.builder()
                .credentials(Credentials.builder().username("Henk").build())
                .information(UserInformation.builder().name("Henk").build())
                .userRole("USER")
                .build();
        User user2 = User.builder()
                .credentials(Credentials.builder().username("Piet").build())
                .information(UserInformation.builder().name("Piet").build())
                .build();
        User user3 = User.builder()
                .credentials(Credentials.builder().username("Gert").build())
                .information(UserInformation.builder().name("Gert").build())
                .build();
        User user4 = User.builder()
                .credentials(Credentials.builder().username("Jan").build())
                .information(UserInformation.builder().name("Jan").build())
                .build();
        User user5 = User.builder()
                .credentials(Credentials.builder().username("Kim").build())
                .information(UserInformation.builder().name("Kim").build())
                .build();
        User user6 = User.builder()
                .credentials(Credentials.builder().username("Harry").build())
                .information(UserInformation.builder().name("Harry").build())
                .build();
        User user7 = User.builder()
                .credentials(Credentials.builder().username("Simon").build())
                .information(UserInformation.builder().name("Simon").build())
                .build();
        User user8 = User.builder()
                .credentials(Credentials.builder().username("Fred").build())
                .information(UserInformation.builder().name("Fred").build())
                .build();
        User user9 = User.builder()
                .credentials(Credentials.builder().username("Manon").build())
                .information(UserInformation.builder().name("Manon").build())
                .build();
        User user12 = User.builder()
                .credentials(Credentials.builder().username("Riem").build())
                .information(UserInformation.builder().name("Riem").build())
                .build();
        User user11 = User.builder()
                .credentials(Credentials.builder().username("Yolanda").build())
                .information(UserInformation.builder().name("Yolanda").build())
                .build();
        User user10 = User.builder()
                .credentials(Credentials.builder().username("Quentin").build())
                .information(UserInformation.builder().name("Quentin").build())
                .build();

        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);
        service.addUser(user4);
        service.addUser(user5);
        service.addUser(user6);
        service.addUser(user7);
        service.addUser(user8);
        service.addUser(user9);
        service.addUser(user10);
        service.addUser(user11);
        service.addUser(user12);

        service.followUser(user1.getId(), user7.getId());
        service.followUser(user1.getId(), user9.getId());
        service.followUser(user1.getId(), user10.getId());
        service.followUser(user10.getId(), user1.getId());
    }
}
