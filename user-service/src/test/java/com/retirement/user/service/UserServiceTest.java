package com.retirement.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.retirement.user.entity.User;
import com.retirement.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();

        user.setUserId(1L);
        user.setName("Chethan");
        user.setEmail("chethan@gmail.com");
        user.setAge(25);
        user.setRetirementAge(60);
    }

    @Test
    void testSaveUser() {

        when(repository.save(user)).thenReturn(user);

        User savedUser = service.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Chethan", savedUser.getName());

        verify(repository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {

        List<User> users = Arrays.asList(user);

        when(repository.findAll()).thenReturn(users);

        List<User> result = service.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("Chethan", result.get(0).getName());

        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = service.getUserById(1L);

        assertNotNull(result);
        assertEquals("Chethan", result.getName());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        User result = service.getUserById(99L);

        assertNull(result);

        verify(repository, times(1)).findById(99L);
    }
}