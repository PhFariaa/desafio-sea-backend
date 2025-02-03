package io.seasolutions.bookstore.domain.user;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserForm createUser(UserForm userForm);

    List<UserForm> findAll(Pageable pageable);

    UserForm findById(Long id);

    UserForm update(Long id, UserForm userForm);

    void deleteUser(Long id);
}
