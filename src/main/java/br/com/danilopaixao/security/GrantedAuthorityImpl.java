package br.com.danilopaixao.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    
    private String authority;
    
    public GrantedAuthorityImpl(){}
    
    public GrantedAuthorityImpl(String authority){
    	this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return "ADMIN";
    }
}
