package nl.oscar.kwetter.service.kwetter.parsing;

import nl.oscar.kwetter.service.user.UserService;

import javax.inject.Inject;
import java.util.Collection;

public class MentionParserDefault implements MentionParser {
    @Inject
    UserService userService;

    @Override
    public Collection<Long> parse(String text) {
        return null;
    }
}
