package nl.oscar.kwetter.service.kwetter.parsing;

import javax.ejb.Stateless;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class TopicParserDefault implements TopicParser {
    @Override
    public Collection<String> parse(String text) {
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(text);

        Collection<String> topics = new HashSet<>();

        while (matcher.find()) {
            topics.add(matcher.group(1).toLowerCase());
        }

        return topics;
    }
}
