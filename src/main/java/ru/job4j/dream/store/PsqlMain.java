package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDate;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();

        store.savePost(new Post(0, "Java Job 1", "Java Job 1", LocalDate.now()));
        store.savePost(new Post(0, "Java Job 2", "Java Job 2", LocalDate.now()));
        store.savePost(new Post(0, "Java Job 3", "Java Job 3", LocalDate.now()));

        store.saveCandidate(new Candidate(0, "Candidate 1", "noPhoto.jpg"));
        store.saveCandidate(new Candidate(0, "Candidate 2", "noPhoto.jpg"));
        store.saveCandidate(new Candidate(0, "Candidate 3", "noPhoto.jpg"));

        store.savePost(new Post(2, "Java Job 2 upd", "Java Job 2 upd", LocalDate.now()));
        store.saveCandidate(new Candidate(2, "Candidate 2 upd", "noPhoto.jpg"));

        System.out.println(store.findPostById(2));
        System.out.println(store.findCandidateById(2));

        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName() + " " + post.getDescription() + " " + post.getCreated());
        }

        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName() + " " + candidate.getPhoto());
        }
    }
}