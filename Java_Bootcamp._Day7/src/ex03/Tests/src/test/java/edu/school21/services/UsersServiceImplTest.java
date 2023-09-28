package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UsersServiceImplTest {
    @Test
    public void correctLoginPasswordTest() {
        UsersRepository usersRepositoryByMock = Mockito.mock(UsersRepository.class);

        User user = new User(1L, "aramil", "admin123", false);
        when(usersRepositoryByMock.findByLogin("aramil")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepositoryByMock);

        assertTrue(usersService.authenticate("aramil", "admin123"));
    }

    @Test
    public void authWithCorrectLogAndPassTest() {
        UsersRepository usersRepositoryByMock = Mockito.mock(UsersRepository.class);

        User user = new User(1L, "aramil", "admin123", false);
        when(usersRepositoryByMock.findByLogin("aramil")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepositoryByMock);
        usersService.authenticate("aramil", "admin123");

        assertTrue(user.isAuthenticationStatus());
    }

    @Test
    public void alreadyAuthenticated() {
        UsersRepository usersRepositoryByMock = Mockito.mock(UsersRepository.class);

        User user = new User(1L, "aramil", "admin123", true);
        when(usersRepositoryByMock.findByLogin("aramil")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepositoryByMock);

        AlreadyAuthenticatedException exception = assertThrows(
                AlreadyAuthenticatedException.class, () -> usersService.authenticate("aramil", "admin123"), "The user is already authenticated");
        assertEquals("The user is already authenticated", exception.getMessage());
    }

    @Test
    public void incorrectLoginTest() {
        UsersRepository usersRepositoryByMock = Mockito.mock(UsersRepository.class);

        when(usersRepositoryByMock.findByLogin("aramilf")).thenThrow(EntityNotFoundException.class);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepositoryByMock);

        assertThrows(EntityNotFoundException.class, () -> usersService.authenticate("aramilf", "admin123"));
    }

    @Test
    public void incorrectPasswordTest() {
        UsersRepository usersRepositoryByMock = Mockito.mock(UsersRepository.class);

        User user = new User(1L, "aramil", "admin123", false);
        when(usersRepositoryByMock.findByLogin("aramil")).thenReturn(user);
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepositoryByMock);

        assertFalse(usersService.authenticate("aramil", "admin1234"));
    }
}
