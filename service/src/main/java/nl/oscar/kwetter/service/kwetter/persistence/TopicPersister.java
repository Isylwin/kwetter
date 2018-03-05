package nl.oscar.kwetter.service.kwetter.persistence;

import java.util.Collection;

public interface TopicPersister {

    void addKwetterToTopics(long kwetter, Collection<String> topics);

    void removeKwetterFromTopics(long kwetter, Collection<String> topics);
}
