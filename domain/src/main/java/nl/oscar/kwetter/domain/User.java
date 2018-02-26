package nl.oscar.kwetter.domain;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@Builder
@EqualsAndHashCode(exclude = {"photo", "bio", "location", "website", "credentials", "following", "followers", "kwetters"})
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private byte[] photo;

    private String bio;

    private String location;

    private String website;

    private Credentials credentials;
    @Builder.Default
    private HashSet<Long> following = new HashSet<>();
    @Builder.Default
    private HashSet<Long> followers = new HashSet<>();
    @Singular
    private Collection<Kwetter> kwetters;

    public User() {
        following = new HashSet<>();
        followers = new HashSet<>();
        kwetters = new HashSet<>();
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
