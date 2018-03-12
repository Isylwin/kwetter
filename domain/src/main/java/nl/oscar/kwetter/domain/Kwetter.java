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
                query = "SELECT a FROM Kwetter AS a LEFT JOIN a.mentions m WHERE :mention IN m"),
        @NamedQuery(name = "Kwetter.findWithTopic",
                query = "SELECT a FROM Kwetter AS a LEFT JOIN a.topics t WHERE :topic in t")
})
public class Kwetter {
    @Id
    @TableGenerator(name = "TABLE_GEN", table = "T_GENERATOR", pkColumnName = "GEN_KEY", pkColumnValue = "TEST", valueColumnName = "GEN_VALUE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private long id;

    private long author;

    private LocalDateTime timestamp;

    private String text;
    @Singular
    @JoinTable(name = "Kwetter_mentions")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> mentions;
    @Singular
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> topics;
    @Singular
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> likes;
}
