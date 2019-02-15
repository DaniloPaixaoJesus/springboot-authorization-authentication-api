package br.com.danilopaixao.ws.profile;


public enum ProfileStatusEnum {
	ATIVO,
	INATIVO;
	public static ProfileStatusEnum getInstance(String strEnum) {
		for (int i = 0; i < ProfileStatusEnum.values().length; i++) {
			if(ProfileStatusEnum.values()[i].toString().equals(strEnum)){
				return ProfileStatusEnum.values()[i]; 
			}
		}
		return null;
	}
}
