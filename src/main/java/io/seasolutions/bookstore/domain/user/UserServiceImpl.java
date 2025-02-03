package io.seasolutions.bookstore.domain.user;


import io.seasolutions.bookstore.common.exception.ConflictException;
import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserForm createUser(UserForm userForm) {


        if (!userRepository.findByEmail(userForm.getEmail()).isEmpty()) {
            throw new ConflictException(MessageError.USER_ALREADY_EXIST);
        }

        User user = new User(userForm);
        this.userRepository.save(user);
        return new UserForm(user);
    }

    @Override
    public List<UserForm> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable).stream().map(UserForm::new).toList();
    }

    @Override
    public UserForm findById(Long id) {
        return new UserForm(this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage())));
    }

    @Override
    public UserForm update(Long id, UserForm userForm) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));

        user.update(userForm);
        return new UserForm(this.userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));

        this.userRepository.delete(user);
    }
}
