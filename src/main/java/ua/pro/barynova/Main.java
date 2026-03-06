package ua.pro.barynova;

import ua.pro.barynova.entity.Comment;
import ua.pro.barynova.entity.Like;
import ua.pro.barynova.entity.Post;
import ua.pro.barynova.entity.User;
import ua.pro.barynova.repository.CommentRepository;
import ua.pro.barynova.repository.LikeRepository;
import ua.pro.barynova.repository.PostRepository;
import ua.pro.barynova.repository.QueryService;
import ua.pro.barynova.repository.UserRepository;
import ua.pro.barynova.util.HibernateUtil;
import java.util.List;

/**
 * Демонстрирует CRUD операции, отношения между Entity и HQL запросы
 */
public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();
        CommentRepository commentRepository = new CommentRepository();
        LikeRepository likeRepository = new LikeRepository();
        QueryService queryService = new QueryService();

        System.out.println("=== CREATE DATA ===");
        User user1 = new User("alice", "alice@mail.com");
        User savedUser = userRepository.save(user1);

        if (savedUser != null) {
            Post post1 = new Post("My First Post", "Hello World!", savedUser);
            Post savedPost1 = postRepository.save(post1);

            Post post2 = new Post("Java and Hibernate", "Learning Hibernate is fun!", savedUser);
            Post savedPost2 = postRepository.save(post2);

            if (savedPost1 != null && savedPost2 != null) {
                Comment comment1 = new Comment("Great post!", savedPost1, savedUser);
                commentRepository.save(comment1);

                Comment comment2 = new Comment("Very helpful!", savedPost2, savedUser);
                commentRepository.save(comment2);

                Comment comment3 = new Comment("Thanks for sharing!", savedPost1, savedUser);
                commentRepository.save(comment3);

                Like like1 = new Like(savedPost1, savedUser);
                likeRepository.save(like1);

                Like like2 = new Like(savedPost2, savedUser);
                likeRepository.save(like2);
            }
        }

        System.out.println("\n=== HQL QUERIES ===");

        System.out.println("\n1) Get all users:");
        List<User> allUsers = queryService.getAllUsers();
        System.out.println("Total users: " + allUsers.size());
        for (User u : allUsers) {
            System.out.println("  - " + u.getUsername() + " (" + u.getEmail() + ")");
        }

        System.out.println("\n2) Get user by email:");
        User foundByEmail = queryService.getUserByEmail("alice@mail.com");
        if (foundByEmail != null) {
            System.out.println("Found: " + foundByEmail.getUsername());
        }

        System.out.println("\n3) Count all users:");
        Long userCount = queryService.countAllUsers();
        System.out.println("Total users in DB: " + userCount);

        System.out.println("\n4) Get all users ordered by name:");
        List<User> usersByName = queryService.getAllUsersOrderedByName();
        System.out.println("Users (ordered by name): " + usersByName.size());
        for (User u : usersByName) {
            System.out.println("  - " + u.getUsername());
        }

        HibernateUtil.shutdown();
    }
}