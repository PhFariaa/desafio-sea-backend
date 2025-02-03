package io.seasolutions.bookstore.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class UserForm {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    public UserForm() {
    }

    public UserForm(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserForm(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserForm userForm = (UserForm) o;
        return Objects.equals(id, userForm.id) && Objects.equals(name, userForm.name) && Objects.equals(email, userForm.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
