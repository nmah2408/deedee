package com.deedee.profile.repository;

import com.deedee.profile.entity.ProfileChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileChangeLogRepository extends JpaRepository<ProfileChangeLog, String> {
}
