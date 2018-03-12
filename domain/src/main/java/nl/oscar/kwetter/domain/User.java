package nl.oscar.kwetter.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "KwetterUser")
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"information", "credentials", "following", "followers", "kwetters"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private UserInformation information;
    @Embedded
    private Credentials credentials;
    @Builder.Default
    @ElementCollection
    private Set<Long> following = new HashSet<>();
    @Builder.Default
    @ElementCollection
    private Set<Long> followers = new HashSet<>();
    @Builder.Default
    @ElementCollection
    private Set<Long> kwetters = new HashSet<>();

    public User() {
        following = new HashSet<>();
        followers = new HashSet<>();
        kwetters = new HashSet<>();
        information = new UserInformation();
        credentials = new Credentials();
    }

    public void beFollowed(long follower) {
        followers.add(follower);
    }

    public void follow(long followee) {
        following.add(followee);
    }

    public void beUnfollowed(long follower) {
        followers.remove(follower);
    }

    public void unfollow(long followee) {
        following.remove(followee);
    }

    public void addKwetter(long kwetter) {
        kwetters.add(kwetter);
    }

    public void removeKwetter(long kwetter) {
        kwetters.remove(kwetter);
    }
}
