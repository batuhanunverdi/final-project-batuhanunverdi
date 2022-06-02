package org.patikadev.finalprojectbatuhanunverdi.entity;

import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.UserStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mert Batuhan UNVERDI
 * @since 16.05.2022
 */
@Entity
@Getter
@Setter
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String tc;
    private String password;
    @Column(unique = true)
    private String email;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSuccessfullyLoggedTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastFailureLoggedTime;
    private Date DOB;
    @Column(unique = true)
    private String phoneNumber;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Account> account = new HashSet<>();

    public void lockUser(){
        this.userStatus = UserStatus.LOCKED;
    }


}
