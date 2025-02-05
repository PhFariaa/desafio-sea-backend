package io.seasolutions.bookstore.domain.user;


import io.seasolutions.bookstore.domain.loan.Loan;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "users")
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();

    public User() {}

    public User(UserForm userForm) {
        this.name = userForm.getName();
        this.email = userForm.getEmail();
    }

    public void update(UserForm userForm) {
        this.name = userForm.getName();
        this.email = userForm.getEmail();
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
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(loans, user.loans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, loans);
    }
}
