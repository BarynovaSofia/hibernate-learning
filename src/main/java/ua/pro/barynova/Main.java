package ua.pro.barynova;

import ua.pro.barynova.entity.Comment;
import ua.pro.barynova.entity.Post;
import ua.pro.barynova.entity.User;
import ua.pro.barynova.repository.CommentRepository;
import ua.pro.barynova.repository.PostRepository;
import ua.pro.barynova.repository.UserRepository;
import ua.pro.barynova.util.HibernateUtil;

/**
 * Main - основной класс для тестирования Hibernate
 * Демонстрирует CRUD операции и отношения между Entity
 */
public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();
        CommentRepository commentRepository = new CommentRepository();

        //CREATE USER
        System.out.println("-> CREATE USER <-");
        User user1 = new User("alice", "alice@mail.com");
        User savedUser = userRepository.save(user1);

        //CREATE POSTS
        System.out.println("\n-> CREATE POSTS <-");
        if (savedUser != null) {
            Post post1 = new Post("My First Post", "Hello World!", savedUser);
            Post savedPost1 = postRepository.save(post1);

            Post post2 = new Post("Java and Hibernate", "Learning Hibernate is fun!", savedUser);
            Post savedPost2 = postRepository.save(post2);

            //CREATE COMMENTS
            System.out.println("\n-> CREATE COMMENTS <-");
            if (savedPost1 != null && savedPost2 != null) {
                Comment comment1 = new Comment("Great post!", savedPost1, savedUser);
                commentRepository.save(comment1);

                Comment comment2 = new Comment("Very helpful!", savedPost2, savedUser);
                commentRepository.save(comment2);

                Comment comment3 = new Comment("Thanks for sharing!", savedPost1, savedUser);
                commentRepository.save(comment3);
            }
        }

        //READ POST WITH USER AND COMMENTS
        System.out.println("\n-> READ POST WITH USER AND COMMENTS <-");
        Post foundPost = postRepository.findById(1);
        if (foundPost != null) {
            System.out.println("Post: " + foundPost);
            System.out.println("Author: " + foundPost.getUser().getUsername());
            System.out.println("Comments count: " + foundPost.getComments().size());
            for (Comment comment : foundPost.getComments()) {
                System.out.println("  - " + comment.getUser().getUsername() + ": " + comment.getContent());
            }
        }

        //READ USER WITH POSTS AND COMMENTS
        System.out.println("\n-> READ USER WITH POSTS AND COMMENTS <-");
        User userWithData = userRepository.findById(savedUser.getId());
        if (userWithData != null) {
            System.out.println("User: " + userWithData.getUsername());
            System.out.println("Posts count: " + userWithData.getPosts().size());
            System.out.println("Comments count: " + userWithData.getComments().size());

            for (Post post : userWithData.getPosts()) {
                System.out.println("  Post: " + post.getTitle() + " (" + post.getComments().size() + " comments)");
            }
        }
        HibernateUtil.shutdown();
    }
}
