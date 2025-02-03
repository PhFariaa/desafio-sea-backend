package io.seasolutions.bookstore.domain.user;

import io.seasolutions.bookstore.common.exception.ConflictException;
import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserFactory userFactory;


    @InjectMocks
    private UserServiceImpl userService;


    @Test
    @DisplayName("User Service create method return Success")
    void userServiceCreateSuccess() {

        UserForm userForm = UserFactory.getUserForm();

        when(userRepository.save(any())).thenReturn(userFactory.getUser());
        UserForm result = userService.createUser(UserFactory.getUserForm());
        assertNotNull(result);
        assertEquals(userForm.getEmail(), result.getEmail());
        assertEquals(userForm.getName(), result.getName());

    }

    @Test
    @DisplayName("User Service create method return ConflictException")
    void userServiceCreateConflictException() {

        when(userRepository.findByEmail(any())).thenThrow(new ConflictException(MessageError.USER_ALREADY_EXIST));
        assertThrows(ConflictException.class, () -> userService.createUser(UserFactory.getUserForm()));

    }

    @Test
    @DisplayName("User Service findById method return Success")
    void userServiceFindByIdSuccess() {

        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(UserFactory.getUser()));
        UserForm result = userService.findById(1L);
        assertNotNull(result);
        assertEquals(UserFactory.getUserForm(), result);
    }

    @Test
    @DisplayName("User Service findById method return EntityNotFoundException")
    void userServiceFindByIdEntityNotFoundException() {
        when(userRepository.findById(any())).thenThrow(new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));
        assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    @DisplayName("User Service update method return Success")
    void userServiceUpdateSuccess() {

        when(userRepository.findById(any())).thenReturn(Optional.of(UserFactory.getUser()));
        when(userRepository.save(any())).thenReturn(UserFactory.getUser());
        UserForm result = userService.update(1L, UserFactory.getUserForm());
        assertNotNull(result);
        assertEquals(UserFactory.getUserForm(), result);
    }

    @Test
    @DisplayName("User Service update method return EntityNotFoundException")
    void userServiceUpdateEntityNotFoundException() {
        when(userRepository.findById(any())).thenThrow(new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));
        assertThrows(EntityNotFoundException.class, () -> userService.update(1L, UserFactory.getUserForm()));
    }

    @Test
    @DisplayName("User Service delete method return EntityNotFoundException")
    void userServiceDeleteEntityNotFoundException() {
        when(userRepository.findById(any())).thenThrow(new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    @DisplayName("User Service delete method return Success")
    void userServiceDeleteSuccess() {
        when(userRepository.findById(any())).thenReturn(Optional.of(UserFactory.getUser()));
        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }
}