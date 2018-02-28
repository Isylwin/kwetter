package nl.oscar.kwetter.service.kwetter.parsing;

import java.util.Collection;

public interface TopicParser {

    Collection<String> parse(String text);
}
