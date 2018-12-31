package com.betsegaw.tacos.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


@Data
@Entity
@Table(name = "user")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @NotBlank(message = "Please provide a username")
    private String username;
   
    @Size(min = 5, message = "Your password must have at least 5 characters")
    @NotBlank(message = "Please provide your password")
    private String password;
   
    @Column(name = "first_name")
    @NotBlank(message = "Please provide your first name")
    private String firstName;
    
    @Column(name = "last_name")
    @NotBlank(message = "Please provide your last name")
    private String lastName;
    
    @NotBlank(message="Delivery Street is required")
    private String deliveryStreet;
    
    @NotBlank(message="Delivery City is required")
    private String deliveryCity;
    
    @NotBlank(message="Delivery State is required")
    private String deliveryState;
    
    @NotBlank(message="Delivery Zip is required")
    private String deliveryZip;
    
    @Pattern(regexp="^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message="Must be formatted XXX-XXX-XXXX")
    @NotBlank(message="Phone number is required")
    private String phoneNumber;
    
    @Column(name = "enabled")
    private int enabled;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="user_role", 
    			joinColumns= {@JoinColumn(name="user_id")},
    			inverseJoinColumns= {@JoinColumn(name="role_id")})
    private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<GrantedAuthority> authorities = roles
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toSet());
		
		return authorities;
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

   