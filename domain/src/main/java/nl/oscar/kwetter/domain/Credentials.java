package nl.oscar.kwetter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.Collection;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credentials {
    @Id
    private String username;

    private String password;

    private Collection<Role> roles;
}
