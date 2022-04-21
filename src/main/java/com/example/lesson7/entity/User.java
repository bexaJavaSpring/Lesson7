package com.example.lesson7.entity;

import com.example.lesson7.entity.enums.PermissionEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    @ManyToMany
    private Set<Role> roles;
    @Column(unique = true, nullable = false)
    private String userName;
    private String password;
    private boolean enabled = true;
    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;

    public User(String fullName, Set<Role> roles, String userName, String password,boolean enabled) {
        this.fullName = fullName;
        this.roles = roles;
        this.userName = userName;
        this.password = password;
        this.enabled=enabled;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : this.roles) {
            Set<PermissionEnum> permissionEnumSet1 = role.getPermissionEnumSet();
            for (PermissionEnum permissionEnum : permissionEnumSet1) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permissionEnum.name()));
            }
        }
        System.out.println(grantedAuthorities);
        return grantedAuthorities;
    }


    private boolean active;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }



}
