package fr.eni.projetEnchere.bo;

public class Retrait {

	//Declare
	private String rue;
	private String codePostal;
	private String ville;
	private Article articleRetire;
	
	
	//Constructors
	public Retrait() {
	}
	
    public Retrait(Article articleRetire, String rue, String codePostal, String ville) {
        this.articleRetire = articleRetire;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }
	
	
	//Getters & Setters
    public Article getArticleRetire() {
        return articleRetire;
    }

    public void setArticleRetire(Article articleRetire) {
        this.articleRetire = articleRetire;
    }

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

}

