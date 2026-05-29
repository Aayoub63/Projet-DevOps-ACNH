package com.artheriom.acnhcollector.api.repository;

import com.artheriom.acnhcollector.api.domain.APIFossil;
import com.artheriom.acnhcollector.api.domain.APIMusic;

import java.util.Optional;

public interface APIFossilRepository extends SearchablePagingAndSortingRepository<APIFossil, String> {
}