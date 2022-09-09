package com.narsm.web.app.modules.zone.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.narsm.web.app.modules.zone.domain.entity.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Optional<Zone> findByCityAndProvince(String cityName, String provinceName);
}
