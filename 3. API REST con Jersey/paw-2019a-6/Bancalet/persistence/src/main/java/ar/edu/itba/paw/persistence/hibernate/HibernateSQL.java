package ar.edu.itba.paw.persistence.hibernate;

public abstract class HibernateSQL {

	public String formatSQL(String text) {
		text = text.toLowerCase();
		text = text.replaceAll("'","''");
		text = text.replaceAll("_", "/_");
		text = text.replaceAll("%", "/%");
		text = "%" + text + "%";
		return text;
	}
	
}