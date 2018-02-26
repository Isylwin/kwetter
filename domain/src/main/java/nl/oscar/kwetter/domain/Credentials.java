package nl.oscar.kwetter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.Collection;

@Embeddable
@Data
@NoArgsConstructor
public class Credentials {
    @Id
    private String username;

    private String password;

    private Collection<Role> roles;
}
