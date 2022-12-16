package com.ecore.squad.model.memberRole;

import com.ecore.squad.model.role.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "member_role")
@AllArgsConstructor
@NoArgsConstructor
public class MemberRole {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "team_id", nullable = false)
    private String teamId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "role_name", nullable = false)
    private Role role;

    public MemberRole(String teamId, String userId) {
        this.teamId = teamId;
        this.userId = userId;
        this.role = new Role("Developer");
    }
}
