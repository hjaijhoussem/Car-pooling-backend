package com.horizon.carpooling.entities;

import com.horizon.carpooling.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank()
    private String firstname;
    @NotBlank()
    private String lastname;
    @Column(unique = true)
    @NotBlank()
    @Max(9999999999L)
    @Positive()
    private long CIN;
    @Column(unique = true)
    private long phoneNumber;
    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    @NotBlank()
    private Role role;
    @OneToMany(mappedBy = "driver")
    private List<Ride> rides ;

    @OneToMany(mappedBy = "passenger")
    private List<RideRequest> myRideRequests;

    @OneToMany(mappedBy = "reviewer")
    private List<Review> myReviews;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}