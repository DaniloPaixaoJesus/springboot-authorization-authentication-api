package br.com.danilopaixao.ws.core.api.security;

import org.springframework.security.core.GrantedAuthority;

public class RoleGrantedAuthority  implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    
    private String authority;
    
    public RoleGrantedAuthority(){}
    
    public RoleGrantedAuthority(String authority){
    	this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return "ADMIN";
    }
}
