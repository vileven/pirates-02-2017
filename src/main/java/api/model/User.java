package api.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@PersistenceContext
@Table(name = "users")
public class User extends Model<Long> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected User(){};

    public User(String login, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public Long getId() {
        return id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "User [id=" + id +
                ",login=" + login +
                ",email=" + email +
                ",password=" + password +
                ",createdAt=" + createdAt +
                ",updatedAt=" + updatedAt + ']';
    }
}
