package nl.oscar.kwetter.web.bean;

import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.user.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "userDetailBean")
@RequestScoped
public class UserDetailBean implements Serializable {

    @Inject
    private UserService service;

    private long id;
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
