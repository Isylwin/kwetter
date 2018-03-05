package nl.oscar.kwetter.service.kwetter.persistence;

import nl.oscar.kwetter.domain.Kwetter;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KwetterPersisterDefault implements KwetterPersister {

    @Inject
    private AuthorPersister authorPersister;

    @Inject
    private TopicPersister topicPersister;

    @Override
    public void persist(Kwetter kwetter) {
        authorPersister.addKwetterToAuthor(kwetter.getId(), kwetter.getAuthor());
        topicPersister.addKwetterToTopics(kwetter.getId(), kwetter.getTopics());
    }
}
