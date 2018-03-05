package nl.oscar.kwetter.service.kwetter.parsing;

import java.util.Collection;

public interface MentionParser {

    Collection<String> parse(String text);
}
