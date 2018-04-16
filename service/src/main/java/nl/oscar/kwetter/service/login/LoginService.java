package nl.oscar.kwetter.service.login;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.Credentials;
import nl.oscar.kwetter.domain.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class LoginService {

    @Inject
    private UserDao dao;

    public User authenticate(Credentials credentials) {
        return dao.findUserByCredentials(credentials);
    }

    public void signUp(User user) {
        dao.create(user);
    }
}
