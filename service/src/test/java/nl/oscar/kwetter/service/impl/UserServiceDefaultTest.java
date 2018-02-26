package nl.oscar.kwetter.service.impl;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.dao.UserDao;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.EitherUtil;
import nl.oscar.kwetter.service.ServerError;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceDefaultTest {

    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserServiceDefault service;

    @Before
    public void setUp() {
        service = new UserServiceDefault();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsers_withEmptyCollection_shouldCallDao() {
        when(userDaoMock.findAll()).thenReturn(new ArrayList<>());

        service.getAllUsers();

        verify(userDaoMock).findAll();
    }

    @Test
    public void getAllUsers_withCollection_returnUsers() {
        HashSet<User> users = new HashSet<>();
        users.add(User.builder().id(1L).build());
        users.add(User.builder().id(2L).build());

        when(userDaoMock.findAll()).thenReturn(users);

        Either<ServerError, Collection<User>> result = service.getAllUsers();

        Collection<User> response = result.or(null);

        assertTrue(EitherUtil.hasRight(result));
        assertTrue(response.size() == 2);
    }

    @Test
    public void getAllUsers_withException_returnsServerError() {
        when(userDaoMock.findAll()).thenThrow(new IllegalArgumentException());

        Either<ServerError, Collection<User>> result = service.getAllUsers();

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void getAllUsers_withNullCollection_returnsServerError() {
        when(userDaoMock.findAll()).thenReturn(null);

        Either<ServerError, Collection<User>> result = service.getAllUsers();

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void getUsers_withEmptyCollection_shouldCallDao() {
        when(userDaoMock.findAll()).thenReturn(new ArrayList<>());

        service.getUsers(new ArrayList<>());

        verify(userDaoMock).findAll();
    }

    @Test
    public void getUsers_withEmptyCollection_returnUsers() {
        Collection<Long> ids = new HashSet<>();
        when(userDaoMock.findAll()).thenReturn(new ArrayList<>());

        Either<ServerError, Collection<User>> result = service.getUsers(ids);

        assertTrue(EitherUtil.hasRight(result));
    }

    @Test
    public void getUsers_withFilledCollection_returnUsers() {
        Collection<Long> ids = new HashSet<>();
        Collection<User> users = new HashSet<>();
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();

        ids.add(1L);
        users.add(user1);
        users.add(user2);

        when(userDaoMock.findAll()).thenReturn(users);

        Either<ServerError, Collection<User>> result = service.getUsers(ids);

        Collection<User> response = result.or(null);

        assertTrue(EitherUtil.hasRight(result));
        assertTrue(response.contains(user1));
        assertFalse(response.contains(user2));
    }

    @Test
    public void getUsers_withException_returnsServerError() {
        Collection<Long> ids = new HashSet<>();
        when(userDaoMock.findAll()).thenThrow(new IllegalArgumentException());

        Either<ServerError, Collection<User>> result = service.getUsers(ids);

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void getUsers_withNullCollection_returnsServerError() {
        Collection<Long> ids = new HashSet<>();
        when(userDaoMock.findAll()).thenReturn(null);

        Either<ServerError, Collection<User>> result = service.getUsers(ids);

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void getFollowersOfUser_withValidUser_returnsFollowers() {
        long id = 1L;

        User follower = User.builder().id(2L).build();
        HashSet<Long> followers = new HashSet<>();
        followers.add(2L);

        User user = User.builder().id(id).followers(followers).build();

        HashSet<User> users = new HashSet<>();
        users.add(user);
        users.add(follower);

        when(userDaoMock.find(id)).thenReturn(user);
        when(userDaoMock.findAll()).thenReturn(users);

        Either<ServerError, Collection<User>> result = service.getFollowersOfUser(id);

        Collection<User> response = result.or(null);

        assertTrue(EitherUtil.hasRight(result));
        assertTrue(response.contains(follower));
    }

    @Test
    public void getFollowersOfUser_withInvalidUser_returnsFollowers() {
        long id = 1L;

        User follower = User.builder().id(2L).build();
        HashSet<Long> followers = new HashSet<>();
        followers.add(2L);

        User user = User.builder().id(id).followers(followers).build();

        HashSet<User> users = new HashSet<>();
        users.add(user);
        users.add(follower);

        when(userDaoMock.find(id)).thenReturn(null);
        when(userDaoMock.findAll()).thenReturn(users);

        Either<ServerError, Collection<User>> result = service.getFollowersOfUser(id);

        Collection<User> response = result.or(null);

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void getFollowingsOfUser_withValidUser_returnsFollowings() {
        long id = 1L;

        User follower = User.builder().id(2L).build();
        HashSet<Long> followings = new HashSet<>();
        followings.add(2L);

        User user = User.builder().id(id).following(followings).build();

        HashSet<User> users = new HashSet<>();
        users.add(user);
        users.add(follower);

        when(userDaoMock.find(id)).thenReturn(user);
        when(userDaoMock.findAll()).thenReturn(users);

        Either<ServerError, Collection<User>> result = service.getFollowingOfUser(id);

        Collection<User> response = result.or(null);

        assertTrue(EitherUtil.hasRight(result));
        assertTrue(response.contains(follower));
    }

    @Test
    public void getFollowingsOfUser_withInvalidUser_returnsFollowings() {
        long id = 1L;

        User follower = User.builder().id(2L).build();
        HashSet<Long> following = new HashSet<>();
        following.add(2L);

        User user = User.builder().id(id).following(following).build();

        HashSet<User> users = new HashSet<>();
        users.add(user);
        users.add(follower);

        when(userDaoMock.find(id)).thenReturn(null);
        when(userDaoMock.findAll()).thenReturn(users);

        Either<ServerError, Collection<User>> result = service.getFollowingOfUser(id);

        Collection<User> response = result.or(null);

        assertTrue(EitherUtil.hasLeft(result));
    }
    
    @Test
    public void getUser_withNullUser_shouldCallDao() {
        long id = 1;
        when(userDaoMock.find(id)).thenReturn(null);

        Either<ServerError, User> result = service.getUser(id);

        verify(userDaoMock).find(id);
    }

    @Test
    public void getUser_withUser_returnsUser() {
        long id = 1;
        when(userDaoMock.find(id)).thenReturn(User.builder().build());

        Either<ServerError, User> result = service.getUser(id);

        assertTrue(EitherUtil.hasRight(result));
    }

    @Test
    public void getUser_withNullUser_returnsServerError() {
        long id = 1;
        when(userDaoMock.find(id)).thenReturn(null);

        Either<ServerError, User> result = service.getUser(id);

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void getUser_withException_returnsServerError() {
        long id = 1;
        when(userDaoMock.find(id)).thenThrow(new RuntimeException());

        Either<ServerError, User> result = service.getUser(id);

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void addUser_withValidUser_returnsUser() {
        User user = User.builder().name("Henk").bio("Ik ben Henk.").build();

        Either<ServerError, User> result = service.addUser(user);

        User response = result.or(null);

        verify(userDaoMock).create(user);
        assertThat(response, is(user));
    }

    @Test
    public void addUser_withNullUser_returnsServiceError() {
        Either<ServerError, User> result = service.addUser(null);

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void followUser_withUsers_shouldCallDao() {
        long followerId = 1;
        long followeeId = 2;
        User follower = User.builder().id(followerId).build();
        User followee = User.builder().id(followeeId).build();

        when(userDaoMock.find(followerId)).thenReturn(follower);
        when(userDaoMock.find(followeeId)).thenReturn(followee);

        Either<ServerError, User> result = service.followUser(followerId, followeeId);

        verify(userDaoMock).edit(followee);
        verify(userDaoMock).edit(follower);
    }

    @Test
    public void followUser_withUsers_returnsUser() {
        long followerId = 1;
        long followeeId = 2;
        User follower = User.builder().id(followerId).build();
        User followee = User.builder().id(followeeId).build();

        when(userDaoMock.find(followerId)).thenReturn(follower);
        when(userDaoMock.find(followeeId)).thenReturn(followee);

        Either<ServerError, User> result = service.followUser(followerId, followeeId);

        assertTrue(follower.getFollowing().contains(followee.getId()));
        assertTrue(followee.getFollowers().contains(follower.getId()));
        assertTrue(EitherUtil.hasRight(result));
    }

    @Test
    public void followUser_withInvalidFollowee_returnsUser() {
        long followerId = 1;
        long followeeId = 2;
        User follower = User.builder().id(followerId).build();
        User followee = User.builder().id(followeeId).build();

        when(userDaoMock.find(followerId)).thenReturn(follower);
        when(userDaoMock.find(followeeId)).thenReturn(null);

        Either<ServerError, User> result = service.followUser(followerId, followeeId);

        assertFalse(follower.getFollowing().contains(followee.getId()));
        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void followUser_withInvalidFollower_returnsUser() {
        long followerId = 1;
        long followeeId = 2;
        User follower = User.builder().id(followerId).build();
        User followee = User.builder().id(followeeId).build();

        when(userDaoMock.find(followerId)).thenReturn(null);
        when(userDaoMock.find(followeeId)).thenReturn(followee);

        Either<ServerError, User> result = service.followUser(followerId, followeeId);

        assertFalse(followee.getFollowers().contains(follower.getId()));
        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void followUser_withSameId_returnsServerError() {
        long followerId = 1;
        long followeeId = 2;

        Either<ServerError, User> result = service.followUser(followerId, followeeId);

        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void followUser_withFolloweeUpdateFail_returnsServerError() {
        long followerId = 1;
        long followeeId = 2;
        User follower = User.builder().id(followerId).build();
        User followee = User.builder().id(followeeId).build();

        when(userDaoMock.find(followerId)).thenReturn(follower);
        when(userDaoMock.find(followeeId)).thenReturn(followee);
        doThrow(new IllegalArgumentException()).when(userDaoMock).edit(followee);

        Either<ServerError, User> result = service.followUser(followerId, followeeId);

        assertFalse(followee.getFollowers().contains(follower.getId()));
        assertFalse(follower.getFollowing().contains(followee.getId()));
        assertTrue(EitherUtil.hasLeft(result));
    }

    @Test
    public void followUser_withFollowerUpdateFail_returnsServerError() {
        long followerId = 1;
        long followeeId = 2;
        User follower = User.builder().id(followerId).build();
        User followee = User.builder().id(followeeId).build();

        when(userDaoMock.find(followerId)).thenReturn(follower);
        when(userDaoMock.find(followeeId)).thenReturn(followee);
        doThrow(new IllegalArgumentException()).when(userDaoMock).edit(follower);

        Either<ServerError, User> result = service.followUser(followerId, followeeId);

        assertFalse(followee.getFollowers().contains(follower.getId()));
        assertFalse(follower.getFollowing().contains(followee.getId()));
        assertTrue(EitherUtil.hasLeft(result));
    }
}