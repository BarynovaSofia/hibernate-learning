package ua.pro.barynova.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Like -> сущность для представления лайка поста
 * Связывает пользователя и пост
 */
@Entity
@Table(name = "Likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Пост который был лайкнут
     */
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * Пользователь который лайкнул пост
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Like() {}

    /**
     * Конструктор с параметрами
     * @param post - пост который лайкнули
     * @param user - пользователь который лайкнул
     */
    public Like(Post post, User user) {
        this.post = post;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Получить ID лайка
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Установить ID лайка
     * @param id - новый ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Получить время создания лайка
     * @return дата и время
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Установить время создания
     * @param createdAt - новое время
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Получить пост который был лайкнут
     * @return объект Post
     */
    public Post getPost() {
        return post;
    }

    /**
     * Установить пост
     * @param post - объект Post
     */
    public void setPostId(Post post) {
        this.post = post;
    }

    /**
     * Получить пользователя который лайкнул
     * @return объект User
     */
    public User getUser() {
        return user;
    }

    /**
     * Установить пользователя
     * @param user - объект User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Строковое представление лайка
     * @return строка с информацией о лайке
     */
    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", post=" + post.getTitle() +
                ", user=" + user.getUsername() +
                '}';
    }
}
