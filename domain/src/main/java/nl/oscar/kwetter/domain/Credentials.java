package nl.oscar.kwetter.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Entity
@Data
@Builder
public class Credentials {
    @Id
    private String username;

    private String password;
    @Singular
    private Collection<Role> roles;
}
