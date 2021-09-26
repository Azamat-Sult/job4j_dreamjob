package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {

    Collection<Post> findAllPosts();

    Collection<Post> findLastDayPosts();

    Collection<Candidate> findAllCandidates();

    Collection<Candidate> findLastDayCandidates();

    Collection<City> findAllCities();

    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    User saveUser(User user);

    User findUserByEmail(String userEmail);
}