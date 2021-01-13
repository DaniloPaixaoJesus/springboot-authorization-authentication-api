package br.com.danilopaixao.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetail implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String senha;
    private String nome;
    private String role;

    private List<GrantedAuthorityImpl> roles = new ArrayList<>();
    
    public UserDetail() {}
    
    public UserDetail(Long id, String username, String password, String role){
    	this.id = id;
    	this.email = username;
    	this.senha = password;
    	this.role = role;
    	this.roles.add(new GrantedAuthorityImpl(role));
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<GrantedAuthorityImpl> getRoles() {
        return roles;
    }

    public void setRoles(List<GrantedAuthorityImpl> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}