package nl.oscar.kwetter.service.impl;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class DatabaseFiller {

    @Inject
    UserDao dao;

    @PostConstruct
    public void init() {
        User user1 = User.builder().id(1L).name("Henk").build();
        User user2 = User.builder().id(2L).name("Piet").build();
        User user3 = User.builder().id(3L).name("Gert").build();
        User user4 = User.builder().id(4L).name("Jan").build();
        User user5 = User.builder().id(5L).name("Kim").build();
        User user6 = User.builder().id(6L).name("Harry").build();
        User user7 = User.builder().id(7L).name("Simon").build();
        User user8 = User.builder().id(8L).name("Fred").build();
        User user9 = User.builder().id(9L).name("Manon").build();
        User user12 = User.builder().id(10L).name("Riem").build();
        User user11 = User.builder().id(11L).name("Yolanda").build();
        User user10 = User.builder().id(12L).name("Quentin").build();


        dao.create(user1);
        dao.create(user2);
        dao.create(user3);
        dao.create(user4);
        dao.create(user5);
        dao.create(user6);
        dao.create(user7);
        dao.create(user8);
        dao.create(user9);
        dao.create(user10);
        dao.create(user11);
        dao.create(user12);
    }
}
