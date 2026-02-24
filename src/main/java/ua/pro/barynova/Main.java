package ua.pro.barynova;

import ua.pro.barynova.entity.Comment;
import ua.pro.barynova.entity.Like;
import ua.pro.barynova.entity.Post;
import ua.pro.barynova.entity.User;
import ua.pro.barynova.repository.CommentRepository;
import ua.pro.barynova.repository.LikeRepository;
import ua.pro.barynova.repository.PostRepository;
import ua.pro.barynova.repository.UserRepository;
import ua.pro.barynova.util.HibernateUtil;

/**
 * Демонстрирует CRUD операции и отношения между Entity
 * (User, Post, Comment, Like)
 */
public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();
        CommentRepository commentRepository = new CommentRepository();
        LikeRepository likeRepository = new LikeRepository();

        //CREATE USER
        System.out.println("==== CREATE USER ====");
        User user1 = new User("alice", "alice@mail.com");
        User savedUser = userRepository.save(user1);

        //CREATE POSTS
        System.out.println("\n==== CREATE POSTS ====");
        if (savedUser != null) {
            Post post1 = new Post("My First Post", "Hello World!", savedUser);
            Post savedPost1 = postRepository.save(post1);

            Post post2 = new Post("Java and Hibernate", "Learning Hibernate is fun!", savedUser);
            Post savedPost2 = postRepository.save(post2);

            //CREATE COMMENTS
            System.out.println("\n==== CREATE COMMENTS ====");
            if (savedPost1 != null && savedPost2 != null) {
                Comment comment1 = new Comment("Great post!", savedPost1, savedUser);
                commentRepository.save(comment1);

                Comment comment2 = new Comment("Very helpful!", savedPost2, savedUser);
                commentRepository.save(comment2);

                Comment comment3 = new Comment("Thanks for sharing!", savedPost1, savedUser);
                commentRepository.save(comment3);

                //CREATE LIKES
                System.out.println("\n==== CREATE LIKES ====");
                Like like1 = new Like(savedPost1, savedUser);
                likeRepository.save(like1);

                Like like2 = new Like(savedPost2, savedUser);
                likeRepository.save(like2);
            }
        }

        //READ POST WITH USER, COMMENTS AND LIKES
        System.out.println("\n==== READ POST WITH USER, COMMENTS AND LIKES ====");
        Post foundPost = postRepository.findById(1);
        if (foundPost != null) {
            System.out.println("Post: " + foundPost);
            System.out.println("Author: " + foundPost.getUser().getUsername());
            System.out.println("Likes count: " + foundPost.getLikes().size());
            System.out.println("Comments count: " + foundPost.getComments().size());

            System.out.println("Comments:");
            for (Comment comment : foundPost.getComments()) {
                System.out.println("  - " + comment.getUser().getUsername() + ": " + comment.getContent());
            }

            System.out.println("Likes by:");
            for (Like like : foundPost.getLikes()) {
                System.out.println("  - " + like.getUser().getUsername());
            }
        }

        //READ USER WITH POSTS, COMMENTS AND LIKES
        System.out.println("\n==== READ USER WITH POSTS, COMMENTS AND LIKES ====");
        User userWithData = userRepository.findById(savedUser.getId());
        if (userWithData != null) {
            System.out.println("User: " + userWithData.getUsername());
            System.out.println("Posts count: " + userWithData.getPosts().size());
            System.out.println("Comments count: " + userWithData.getComments().size());
            System.out.println("Likes count: " + userWithData.getLikes().size());

            System.out.println("\nPosts created:");
            for (Post post : userWithData.getPosts()) {
                System.out.println("  - " + post.getTitle() + " (" + post.getComments().size() + " comments, " + post.getLikes().size() + " likes)");
            }
        }
        HibernateUtil.shutdown();
    }
}