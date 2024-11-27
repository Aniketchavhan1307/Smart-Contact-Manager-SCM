package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User 
{
	@Id
	private String userId;
	
	@Column(name = "user_name", nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	private String password;
	
	@Column(length = 1000)
	private String about;
	
	@Column(length = 1000)
	private String profilePic;
	
	@Column()
	private String phoneNumber;
	
	// information
	
	private boolean enabled = false;
	private boolean emailVarified = false;
	private boolean phoneVerified = false;
	
	
	// self , google , facebook , twitter, linkedin, github
	@Enumerated(value = EnumType.STRING)
	private Providers provider = Providers.SELF;
	private Providers providerUserId;
	
	
	// add more fields if needed

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();
	
	

}
