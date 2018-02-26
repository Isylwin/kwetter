package nl.oscar.kwetter.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"information", "credentials", "following", "followers", "kwetters"})
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Embedded
    private UserInformation information;
    @Embedded
    private Credentials credentials;
    @Builder.Default
    private HashSet<Long> following = new HashSet<>();
    @Builder.Default
    private HashSet<Long> followers = new HashSet<>();

    private Collection<Kwetter> kwetters;

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
}
