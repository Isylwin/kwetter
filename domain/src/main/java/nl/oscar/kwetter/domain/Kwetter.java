package nl.oscar.kwetter.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Builder
public class Kwetter {
    @Id
    @GeneratedValue
    private long id;

    private User author;

    private LocalDateTime timestamp;

    private String text;
    @Singular
    private Collection<User> mentions;
    @Singular
    private Collection<Topic> topics;
    @Singular
    private Collection<User> likes;
}
