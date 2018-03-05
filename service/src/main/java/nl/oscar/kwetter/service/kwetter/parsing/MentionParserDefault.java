package nl.oscar.kwetter.service.kwetter.parsing;

import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MentionParserDefault implements MentionParser {
    @Inject
    private UserDao userDao;

    @Override
    public Collection<Long> parse(String text) {
        Objects.requireNonNull(text, "Text cannot be null");

        Pattern MY_PATTERN = Pattern.compile("@(\\w+)");
        Matcher mat = MY_PATTERN.matcher(text);

        List<String> strs = new ArrayList<>();

        while (mat.find()) {
            strs.add(mat.group(1));
        }

        return userDao.findUsersByUsername(strs)
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
