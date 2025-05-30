package kuit.baemin.repository;

import kuit.baemin.domain.User;

public interface UserRepository {

    public User save(User user);

    User find(User build);
}
