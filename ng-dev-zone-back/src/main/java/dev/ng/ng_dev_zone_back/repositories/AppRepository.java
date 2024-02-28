package dev.ng.ng_dev_zone_back.repositories;

import dev.ng.ng_dev_zone_back.entities.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {
}
