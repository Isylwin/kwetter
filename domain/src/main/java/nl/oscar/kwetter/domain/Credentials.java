package nl.oscar.kwetter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credentials {

    private String username;

    private String password;

    /*@ElementCollection
    private Set<Role> roles;*/
}
