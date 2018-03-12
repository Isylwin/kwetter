package nl.oscar.kwetter.dao.impl;

import nl.oscar.kwetter.dao.KwetterDao;
import nl.oscar.kwetter.domain.Kwetter;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@Default
@Stateless
public class KwetterDaoJpa implements KwetterDao {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    public Collection<Kwetter> findWithAuthor(long author) {
        TypedQuery<Kwetter> tq = em.createNamedQuery("Kwetter.findWithAuthor", Kwetter.class);
        tq.setParameter("author", author);

        return tq.getResultList();
    }

    @Override
    public Collection<Kwetter> findWithMention(long mention) {
        TypedQuery<Kwetter> tq = em.createNamedQuery("Kwetter.findWithMention", Kwetter.class);
        tq.setParameter("mention", mention);

        return tq.getResultList();
    }

    @Override
    public Collection<Kwetter> findWithTopic(String topic) {
        TypedQuery<Kwetter> tq = em.createNamedQuery("Kwetter.findWithTopic", Kwetter.class);
        tq.setParameter("topic", topic);

        return tq.getResultList();
    }

    @Override
    public void create(Kwetter object) {
        em.persist(object);
    }

    @Override
    public void edit(Kwetter object) {
        em.merge(object);
    }

    @Override
    public Kwetter find(Long aLong) {
        return em.find(Kwetter.class, aLong);
    }

    @Override
    public Collection<Kwetter> findAll() {
        CriteriaQuery<Kwetter> cq = em.getCriteriaBuilder().createQuery(Kwetter.class);
        cq.select(cq.from(Kwetter.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public void remove(Kwetter object) {
        em.remove(object);
    }
}
