package nl.oscar.kwetter.domain;

import lombok.Data;
import lombok.Singular;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Entity
@Data
public class Topic {
    @Id
    private String identifier;
    @Singular
    private Collection<Kwetter> kwetters;
}
