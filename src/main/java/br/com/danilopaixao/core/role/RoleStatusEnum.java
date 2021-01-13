package br.com.danilopaixao.core.role;


public enum RoleStatusEnum {
	ATIVO,
	INATIVO;
	public static RoleStatusEnum getInstance(String strEnum) {
		for (int i = 0; i < RoleStatusEnum.values().length; i++) {
			if(RoleStatusEnum.values()[i].toString().equals(strEnum)){
				return RoleStatusEnum.values()[i]; 
			}
		}
		return null;
	}
}
