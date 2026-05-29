package com.artheriom.acnhcollector.api.repository;

import com.artheriom.acnhcollector.api.domain.APIFlower;

import java.util.Optional;

public interface APIFlowerRepository extends SearchablePagingAndSortingRepository<APIFlower, String> {

    Optional<APIFlower> findByName(String name);
}