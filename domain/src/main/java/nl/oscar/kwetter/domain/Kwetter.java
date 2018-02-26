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

    private long author;

    private LocalDateTime timestamp;

    private String text;
    @Singular
    private Collection<Long> mentions;
    @Singular
    private Collection<String> topics;
    @Singular
    private Collection<Long> likes;
}
