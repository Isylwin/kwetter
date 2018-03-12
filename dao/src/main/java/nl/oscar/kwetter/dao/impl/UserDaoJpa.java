package nl.oscar.kwetter.dao.impl;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@Default
@Stateless
public class UserDaoJpa implements UserDao {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    public User findUserByName(String name) {
        TypedQuery<User> tq = em.createNamedQuery("User.findWithName", User.class);
        tq.setParameter("name", name);

        return tq.getSingleResult();
    }

    @Override
    public Collection<User> findUsersByUsername(Collection<String> names) {
        TypedQuery<User> tq = em.createNamedQuery("User.findWithUsernames", User.class);
        tq.setParameter("names", names);

        return tq.getResultList();
    }

    @Override
    public Collection<User> findUsersById(Collection<Long> ids) {
        TypedQuery<User> tq = em.createNamedQuery("User.findWithIds", User.class);
        tq.setParameter("ids", ids);

        return tq.getResultList();
    }

    @Override
    public void create(User object) {
        em.persist(object);
    }

    @Override
    public void edit(User object) {
        em.merge(object);
    }

    @Override
    public User find(Long aLong) {
        return em.find(User.class, aLong);
    }

    @Override
    public Collection<User> findAll() {
        CriteriaQuery<User> cq = em.getCriteriaBuilder().createQuery(User.class);
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public void remove(User object) {
        em.remove(object);
    }
}
