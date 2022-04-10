package distribuido.projeto1;


public enum Estado {
	RELEASED("Released"),
	WANTED("Wanted"),
	HELD("Held");
	
	private String descricao;
	
	Estado(String descricao){
		this.descricao=descricao;
	}
	public String getDescricao(){
		return descricao;
	}
	
}
