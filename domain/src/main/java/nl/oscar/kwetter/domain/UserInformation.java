package nl.oscar.kwetter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class UserInformation {

    private String name;

    private String photo;

    private String bio;

    private String location;

    private String website;
}
