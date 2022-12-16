package com.ecore.squad.repository.memberRole;

import com.ecore.squad.model.memberRole.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, String> {

    Optional<MemberRole> findByTeamIdAndUserId(@NonNull String teamId, @NonNull String userId);

}