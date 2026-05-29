package com.artheriom.acnhcollector.api.repository;

import com.artheriom.acnhcollector.api.domain.APIArt;

import java.util.Optional;

public interface APIArtRepository extends SearchablePagingAndSortingRepository<APIArt, String> {

    Optional<APIArt> findByName(String name);
}