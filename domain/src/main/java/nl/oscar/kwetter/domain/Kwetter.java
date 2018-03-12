package nl.oscar.kwetter.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Kwetter.findWithAuthor",
                query = "SELECT a FROM Kwetter AS a WHERE a.author = :author"),
        @NamedQuery(name = "Kwetter.findWithMention",
                query = "SELECT a FROM Kwetter AS a WHERE :mention in (a.mentions)"),
        @NamedQuery(name = "Kwetter.findWithTopic",
                query = "SELECT a FROM Kwetter AS a WHERE :topic in (a.topics)")
})
public class Kwetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long author;

    private LocalDateTime timestamp;

    private String text;
    @Singular
    @ElementCollection
    private Set<Long> mentions;
    @Singular
    @ElementCollection
    private Set<String> topics;
    @Singular
    @ElementCollection
    private Set<Long> likes;
}
