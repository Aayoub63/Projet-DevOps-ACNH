package com.artheriom.acnhcollector.api.repository;

import com.artheriom.acnhcollector.api.domain.APIUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface APIUserRepository extends SearchablePagingAndSortingRepository<APIUser, String> {
    Optional<APIUser> findByUsername(String username);

    Page<APIUser> findAllByUsername(String user, Pageable page);

    Optional<APIUser> findByEmail(String email);

    Optional<APIUser> findByUsernameOrEmail(String username, String email);
}