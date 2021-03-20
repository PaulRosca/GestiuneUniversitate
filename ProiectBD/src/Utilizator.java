import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JTextField;

public class Utilizator {
	protected int id;
	/**
	 * Numele de familie al utilizatorului
	 */
	protected String nume;
	/**
	 * Prenumele utilizatorului
	 */
	protected String prenume;
	/**
	 * CNP-ul utilizatorului
	 */
	protected String cnp;
	/**
	 * Numarul de mobil al utilizatorului
	 */
	protected String nrTelefon;
	/**
	 * Adresa de email a utilizatorului
	 */
	protected String email;
	/**
	 * Adresa fizica a utilizatorului
	 */
	protected String adresa;
	
	
	public void setareInformatii(List<JTextField> ltf,PreparedStatement CNPUnic,boolean cnpNeschimbat) throws ExceptieUtilizator
	{
		setCNP(ltf.get(0).getText(),CNPUnic,cnpNeschimbat);
		setNume(ltf.get(1).getText());
		setPrenume(ltf.get(2).getText());
		setNrTelefon(ltf.get(3).getText());
		setEmail(ltf.get(4).getText());
		setAdresa(ltf.get(5).getText());
	}
	public void setID(int id)
	{
		this.id = id;
	}
	public void setNume(String nume) throws ExceptieUtilizator
	{
		nume = nume.trim();
		if(!nume.matches("^([a-zăîâșțA-ZĂÎÂȘȚ]{3,}-{0,1})+$"))
			throw new ExceptieUtilizator("Numele este invalid!");
		this.nume=nume;
	}
	public void setPrenume(String prenume) throws ExceptieUtilizator
	{
		prenume = prenume.trim();
		if(!prenume.matches("^([a-zăîâșțA-ZĂÎÂȘȚ]{3,}[-\\s]{0,1})+$"))
			throw new ExceptieUtilizator("Preumele este invalid!");
		this.prenume=prenume;
	}
	public void setNrTelefon(String nrTel) throws ExceptieUtilizator
	{
		nrTel = nrTel.trim();
		if (nrTel.length() != 10)// Daca numarul nu contine 10 caractere
			throw new ExceptieUtilizator("Numarul de telefon trebuie sa contina 10 caractere!");// Aruncam o
																								// exceptie
		if (!nrTel.matches("[0-9]+"))// Daca numarul contine alte caractere decat cifre
			throw new ExceptieUtilizator("Numarul de telefon poate contine doar cifre!");// Aruncam o exceptie
		if (!nrTel.substring(0, 3).matches("07[2-7]"))// Daca prefixul numarului nu este valid
			throw new ExceptieUtilizator("Prefixul numarului de telefon este invalid!");// Aruncam o exceptie
		this.nrTelefon=nrTel;
	}
	public void setCNP(String cnp,PreparedStatement ps,boolean cnpNeschimbat) throws ExceptieUtilizator
	{
		cnp = cnp.trim();
			try
			{
				ps.setString(1, cnp);
				ResultSet rs = ps.executeQuery();
				if(cnpNeschimbat)
					rs.next();
				if(rs.next())
					throw new ExceptieUtilizator("Deja exista un utilizator înregistrat cu acest CNP!");
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		final String c = "279146358279";// Constanta pentru validarea oricarui CNP
		int s = 0, r = 0;// Variabile auxiliare pentru validarea CNP-ului
		int a, l, z;// Anul, luna si ziua nasterii, extrase din CNP
		String icnp;// Variabila auxiliara in care retinem primul caracter din CNP
		s = r = 0;// Reinitializam variabilele auxiliare pentru validare
		if (cnp.length() != 13)// Daca CNP-ul nu are lungimea corecta
			throw new ExceptieUtilizator("Cnp-ul trebuie să conțină 13 caractere!");// Aruncam o exceptie
		if (!cnp.matches("[0-9]+"))// Daca CNP-ul contine alte caractere decat cifre
			throw new ExceptieUtilizator("Cnp-ul poate conține decât cifre!");// Aruncam o exceptie

		// Validam corectitudinea CNP-ului
		for (int i = 0; i < 12; i++)
			s += (Integer.parseInt(cnp.substring(i, i + 1)) * Integer.parseInt(c.substring(i, i + 1)));
		r = s % 11;
		if (r == 10)
			r = 1;
		if (r != Integer.parseInt(cnp.substring(12, 13)))// Daca CNP-ul nu este corect
			throw new ExceptieUtilizator("Datele din cnp nu sunt valide!");// Aruncam o exceptie

		// Extragem data nasterii din CNP
		z = Integer.parseInt(cnp.substring(5, 7));
		l = Integer.parseInt(cnp.substring(3, 5));
		a = Integer.parseInt(cnp.substring(1, 3));
		if (l < 1 || l > 12)
			throw new ExceptieUtilizator("Datele din cnp nu sunt valide!");
		
		
		icnp = cnp.substring(0, 1);
		// Extragem anul din CNP
		if (icnp.matches("[1-2]"))
			a = 1900 + a;
		else if (icnp.matches("[3-4]"))
			a = 1800 + a;
		else if (icnp.matches("[5-6]"))
			a = 2000 + a;
		/*else// Daca s-a introdus un CNP de rezident
		{
			System.out.println(
					"Studentul este rezident in Romania, va rugam introduceti anul nasterii al utilizatorului : ");
			String aux = Main.sc.nextLine().strip();// Citim anul nasterii
			a = Integer.parseInt(aux);// Il convertim in int
		}*/

		if (l < 1 || l > 12)// Daca luna extransa din CNP nu este valida
			throw new ExceptieUtilizator("Datele din cnp nu sunt valide");
		LocalDate auxLD = LocalDate.of(a, l, 1);// Variabila auxiliara pentru validarea zilei extrase din CNP
		if (z < 1 || z > auxLD.lengthOfMonth())// Daca ziua extransa din CNP nu este valida
			throw new ExceptieUtilizator("Datele din cnp nu sunt valide");
		
		
		
		this.cnp=cnp;
	}
	public void setEmail(String email) throws ExceptieUtilizator
	{
		email = email.trim();
		if(!email.matches("^([a-zA-Z0-9]+(-|\\.|\\_)*[a-zA-Z0-9]+)+@([a-zA-Z0-9]+(-|\\.|\\_)*[a-zA-Z0-9]+)+\\.[a-z]{2,4}$"))
			throw new ExceptieUtilizator("Email invalid!");
		this.email=email;
	}
	public void setAdresa(String a)
	{
		this.adresa=a;
	}
	public int getID()
	{
		return id;
	}
	public String getNume()
	{
		return nume;
	}
	public String getPrenume()
	{
		return prenume;
	}
	public String getCnp()
	{
		return cnp;
	}
	public String getNrTelefon()
	{
		return nrTelefon;
	}
	public String getEmail()
	{
		return email;
	}
	public String getAdresa()
	{
		return adresa;
	}
}
