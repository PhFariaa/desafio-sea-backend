package io.seasolutions.bookstore.domain.user;

public class UserFactory {

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("email@email.com");
        return user;
    }
    public static UserForm getUserForm() {
        UserForm userForm = new UserForm();
        userForm.setId(1L);
        userForm.setName("name");
        userForm.setEmail("email@email.com");
        return userForm;
    }
}
