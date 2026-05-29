package com.artheriom.acnhcollector.api.repository;

import com.artheriom.acnhcollector.api.domain.APIMusic;

import java.util.Optional;

public interface APIMusicRepository extends SearchablePagingAndSortingRepository<APIMusic, String> {


    Optional<APIMusic> findByName(String name);
}