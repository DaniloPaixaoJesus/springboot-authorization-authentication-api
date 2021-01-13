package br.com.danilopaixao.core.profile;


public enum ProfileStatusEnum {
	ATIVO("ATIVO"),
	INATIVO("INATIVO");

	private String value;

	ProfileStatusEnum(String value){
		this.value = value;
	}
	public static ProfileStatusEnum getInstance(String strEnum) {
		for (int i = 0; i < ProfileStatusEnum.values().length; i++) {
			if(ProfileStatusEnum.values()[i].toString().equals(strEnum)){
				return ProfileStatusEnum.values()[i]; 
			}
		}
		return null;
	}

	public String getValue(){
		return this.value;
	}
}
