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
@EqualsAndHashCode(exclude = {"information", "credentials", "following", "followers"})
@NamedQueries({
        @NamedQuery(name = "User.findWithName", query = "SELECT a FROM User AS a WHERE a.information.name = :name"),
        @NamedQuery(name = "User.findWithUsernames", query = "SELECT a FROM User AS a WHERE a.credentials.username IN :names"),
        @NamedQuery(name = "User.findWithIds", query = "SELECT a FROM User AS a WHERE a.id IN :ids"),
        @NamedQuery(name = "User.findWithCredentials", query = "SELECT a FROM User AS a WHERE a.credentials.username = :username AND a.credentials.password = :password")
})
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Embedded
    private UserInformation information;
    @Embedded
    private Credentials credentials;
    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> following = new HashSet<>();
    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> followers = new HashSet<>();

    private String userRole;

    private String token;
    @Transient
    private Set<Link> links;

    public User() {
        following = new HashSet<>();
        followers = new HashSet<>();
        information = new UserInformation();
        credentials = new Credentials();
        links = new HashSet<>();
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

    public void addLink(String url, String rel) {
        Link link = new Link();
        link.setLink(url);
        link.setRel(rel);
        links.add(link);
    }
}
