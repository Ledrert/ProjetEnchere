package fr.eni.projetEnchere.bo;

public class Retrait {

	//Declare
	private Article article;
	private String rue;
	private String codePostal;
	private String ville;
	
	
	//Constructors
	public Retrait() {
	}
	
    public Retrait(Article article, String rue, String codePostal, String ville) {
    	this.article = article;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }
	
	
	//Getters & Setters
    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}    
	
	@Override
	public String toString() {
		return "Retrait [rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}
}
