package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;

public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(new FileReader("db.properties"))) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getDate("created").toLocalDate()));
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getString("photo")));
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return candidates;
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            updatePost(post);
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name,description,created) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setObject(3, post.getCreated());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return post;
    }

    private void updatePost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE post SET name = ?, description = ?, created = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setObject(3, post.getCreated());
            ps.setInt(4, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
    }

    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO candidate(name,photo) VALUES (?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return candidate;
    }

    private void updateCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE candidate SET name = ?, photo = ? WHERE id = ?")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhoto());
            ps.setInt(3, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
    }

    @Override
    public Post findPostById(int id) {
        Post result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("select * from post where id = " + id)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Post(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDate("created").toLocalDate());
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return result;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("select * from candidate where id = " + id)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new Candidate(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("photo"));
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return result;
    }

    @Override
    public User saveUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO siteuser(email,name,password) VALUES (?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String userEmail) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "select * from siteuser where email = '" + userEmail + "'")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = new User();
                    result.setId(resultSet.getInt("id"));
                    result.setName(resultSet.getString("name"));
                    result.setEmail(resultSet.getString("email"));
                    result.setPassword(resultSet.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("EXCEPTION: ", e);
        }
        return result;
    }
}