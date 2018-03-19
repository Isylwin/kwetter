package nl.oscar.kwetter.web.bean;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.domain.Role;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.kwetter.KwetterService;
import nl.oscar.kwetter.service.user.UserService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Named(value = "userDetailBean")
@ViewScoped
public class UserDetailBean implements Serializable {

    @Inject
    private UserService service;

    @Inject
    private KwetterService kwetterService;

    private long id;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Kwetter> getKwetters() {
        return kwetterService.getKwettersForAuthor(id).recover(s -> null);
    }

    public Collection<String> getRoleNames() {
        return Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void loadUser() {
        user = service.getUser(id).or(null);
    }

    public void saveUser() {
        service.editUser(user);
    }

    public String selectUser() {
        return "user?faces-redirect=true";
    }

    public void removeKwetter(Kwetter kwetter) {
        kwetterService.removeKwetter(kwetter.getId());
    }
}
