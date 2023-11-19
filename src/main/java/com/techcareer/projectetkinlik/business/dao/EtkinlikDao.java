package com.techcareer.projectetkinlik.business.dao;

import com.techcareer.projectetkinlik.business.data.entity.EtkinlikEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtkinlikDao extends JpaRepository<EtkinlikEntity, Long> {
}
