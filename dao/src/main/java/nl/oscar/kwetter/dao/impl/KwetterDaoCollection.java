package nl.oscar.kwetter.dao.impl;

import nl.oscar.kwetter.dao.KwetterDao;
import nl.oscar.kwetter.dao.qualifier.InMemCollection;
import nl.oscar.kwetter.domain.Kwetter;

import javax.ejb.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@InMemCollection
public class KwetterDaoCollection implements KwetterDao {

    private static long id;
    private Map<Long, Kwetter> map = new HashMap<>();

    @Override
    public void create(Kwetter object) {
        object.setId(id++);
        map.put(object.getId(), object);
    }

    @Override
    public void edit(Kwetter object) {
        map.put(object.getId(), object);
    }

    @Override
    public Kwetter find(Long id) {
        return map.get(id);
    }

    @Override
    public Collection<Kwetter> findAll() {
        return map.values();
    }

    @Override
    public void remove(Kwetter object) {
        map.remove(object.getId());
    }

    @Override
    public Collection<Kwetter> findWithAuthor(long author) {
        return findAll().stream()
                .filter(k -> k.getAuthor() == author)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Kwetter> findWithMention(long mention) {
        return findAll().stream()
                .filter(k -> k.getMentions().contains(mention))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Kwetter> findWithTopic(String topic) {
        return findAll().stream()
                .filter(k -> k.getTopics().contains(topic))
                .collect(Collectors.toSet());
    }
}
