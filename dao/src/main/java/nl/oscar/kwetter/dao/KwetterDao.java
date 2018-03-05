package nl.oscar.kwetter.dao;

import nl.oscar.kwetter.domain.Kwetter;

import java.util.Collection;

public interface KwetterDao extends Dao<Long, Kwetter> {

    Collection<Kwetter> getKwettersForAuthor(long author);
}
