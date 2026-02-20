package ua.pro.barynova;

import ua.pro.barynova.entity.Post;
import ua.pro.barynova.entity.User;
import ua.pro.barynova.repository.PostRepository;
import ua.pro.barynova.repository.UserRepository;
import ua.pro.barynova.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();

        //CREATE USER
        System.out.println("--- CREATE USER ---");
        User user1 = new User("alice", "alice@mail.com");
        User savedUser = userRepository.save(user1);

        //CREATE POSTS
        System.out.println("\n--- CREATE POSTS ---");
        if (savedUser != null) {
            Post post1 = new Post("My First Post", "Hello World!", savedUser);
            postRepository.save(post1);

            Post post2 = new Post("Java and Hibernate", "Learning Hibernate is fun!", savedUser);
            postRepository.save(post2);
        }

        //READ POST WITH USER
        System.out.println("--- READ POST WITH USER ---");
        Post foundPost = postRepository.findById(1);
        if (foundPost != null) {
            System.out.println("Post: " + foundPost);
            System.out.println("Author: " + foundPost.getUser().getUsername());
        }

        //READ USER WITH POSTS
        System.out.println("--- READ USER WITH POSTS ---");
        User userWithPosts = userRepository.findById(1);
        if (userWithPosts != null) {
            System.out.println("User: " + userWithPosts.getUsername());
            System.out.println("Posts count: " + userWithPosts.getPosts().size());
            for (Post post : userWithPosts.getPosts()) {
                System.out.println("  - " + post.getTitle());
            }
        }
        HibernateUtil.shutdown();
    }
}
