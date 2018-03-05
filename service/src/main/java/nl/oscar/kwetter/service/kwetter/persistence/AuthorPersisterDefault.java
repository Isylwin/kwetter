package nl.oscar.kwetter.service.kwetter.persistence;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthorPersisterDefault implements AuthorPersister {

    @Inject
    private UserDao userDao;

    @Override
    public void addKwetterToAuthor(long id, long author) {
        User user = userDao.find(author);
        user.addKwetter(id);
        userDao.edit(user);
    }

    @Override
    public void removeKwetterFromAuthor(long id, long author) {
        User user = userDao.find(author);
        user.removeKwetter(id);
        userDao.edit(user);
    }
}
