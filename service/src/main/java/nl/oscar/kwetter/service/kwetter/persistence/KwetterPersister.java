package nl.oscar.kwetter.service.kwetter.persistence;

import nl.oscar.kwetter.domain.Kwetter;

public interface KwetterPersister {

    void persist(Kwetter kwetter);
}
