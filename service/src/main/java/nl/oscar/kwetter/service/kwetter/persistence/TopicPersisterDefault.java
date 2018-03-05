package nl.oscar.kwetter.service.kwetter.persistence;

import javax.ejb.Stateless;
import java.util.Collection;

@Stateless
public class TopicPersisterDefault implements TopicPersister {
    @Override
    public void addKwetterToTopics(long kwetter, Collection<String> topics) {

    }

    @Override
    public void removeKwetterFromTopics(long kwetter, Collection<String> topics) {

    }
}
