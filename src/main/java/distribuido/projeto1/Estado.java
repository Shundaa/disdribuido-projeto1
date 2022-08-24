package distribuido.projeto1;


public enum Estado {
	NORMAL("Normal"),
	ELEICAO("Eleicao"),
	COORD("Coordenador");
	
	private String descricao;
	
	Estado(String descricao){
		this.descricao=descricao;
	}
	public String getDescricao(){
		return descricao;
	}
	
}
