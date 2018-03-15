package nl.oscar.kwetter.web.bean;

import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.user.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Named(value = "userBean")
@RequestScoped
public class UserBean implements Serializable {

    @Inject
    private UserService service;

    private String filter;

    public Collection<User> getUsers() {

        Collection<User> users = service.getAllUsers().recover(s -> null);

        if (Objects.nonNull(filter) && !filter.trim().isEmpty()) {
            users = users
                    .stream()
                    .filter(u -> u.getCredentials().getUsername().startsWith(filter))
                    .collect(Collectors.toSet());
        }

        return users;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void removeUser(User user) {

    }
}
