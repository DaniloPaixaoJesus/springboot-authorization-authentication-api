package br.com.danilopaixao.ws.user;

import org.apache.commons.lang3.StringUtils;

public enum UserStatusEnum {
	ATIVO,
	INATIVO;

	public static UserStatusEnum getInstance(String strEnum) {
		if (StringUtils.isEmpty(strEnum)){
			return null;
		}

		for (int i = 0; i < UserStatusEnum.values().length; i++) {
			if(UserStatusEnum.values()[i].toString().equals(strEnum)){
				return UserStatusEnum.values()[i]; 
			}
		}

		return null;
	}
}
