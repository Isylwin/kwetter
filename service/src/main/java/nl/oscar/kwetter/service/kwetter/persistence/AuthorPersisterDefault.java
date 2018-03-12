package nl.oscar.kwetter.service.kwetter.persistence;

import nl.oscar.kwetter.dao.UserDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthorPersisterDefault implements AuthorPersister {

    @Inject
    private UserDao userDao;

    @Override
    public void addKwetterToAuthor(long id, long author) {

    }

    @Override
    public void removeKwetterFromAuthor(long id, long author) {

    }
}
