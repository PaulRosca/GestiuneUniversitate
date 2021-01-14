import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JTextField;

public class Student
{
	private int nrMatricol;
	/**
	 * Numele de familie al studentului
	 */
	private String nume;
	/**
	 * Prenumele studentului
	 */
	private String prenume;
	/**
	 * CNP-ul studentului
	 */
	private String cnp;
	/**
	 * Numarul de mobil al studentului
	 */
	private String nrTelefon;
	/**
	 * Adresa de email a studentului
	 */
	private String email;
	/**
	 * Adresa fizica a studentului
	 */
	private String adresa;
	
	/**
	 * Faptul ca studentul este sau nu angajat
	 */
	private boolean angajat;
	/**
	 * Soldul anului curent al studentului
	 */
	private double soldAnCurent;
	
	public void setareInformatii(List<JTextField> ltf,PreparedStatement CNPUnic,boolean cnpNeschimbat) throws ExceptieStudent
	{
		setCNP(ltf.get(0).getText(),CNPUnic,cnpNeschimbat);
		setNume(ltf.get(1).getText());
		setPrenume(ltf.get(2).getText());
		setNrTelefon(ltf.get(3).getText());
		setEmail(ltf.get(4).getText());
		setSold(ltf.get(6).getText());
		setAdresa(ltf.get(5).getText());
	}
	public void setNrMatricol(int nrm)
	{
		nrMatricol = nrm;
	}
	public void setNume(String nume) throws ExceptieStudent
	{
		nume = nume.trim();
		if(!nume.matches("^([a-zăîâșțA-ZĂÎÂȘȚ]{3,}-{0,1})+$"))
			throw new ExceptieStudent("Numele este invalid!");
		this.nume=nume;
	}
	public void setPrenume(String prenume) throws ExceptieStudent
	{
		prenume = prenume.trim();
		if(!prenume.matches("^([a-zăîâșțA-ZĂÎÂȘȚ]{3,}[-\\s]{0,1})+$"))
			throw new ExceptieStudent("Preumele este invalid!");
		this.prenume=prenume;
	}
	public void setNrTelefon(String nrTel) throws ExceptieStudent
	{
		nrTel = nrTel.trim();
		if (nrTel.length() != 10)// Daca numarul nu contine 10 caractere
			throw new ExceptieStudent("Numarul de telefon trebuie sa contina 10 caractere!");// Aruncam o
																								// exceptie
		if (!nrTel.matches("[0-9]+"))// Daca numarul contine alte caractere decat cifre
			throw new ExceptieStudent("Numarul de telefon poate contine doar cifre!");// Aruncam o exceptie
		if (!nrTel.substring(0, 3).matches("07[2-7]"))// Daca prefixul numarului nu este valid
			throw new ExceptieStudent("Prefixul numarului de telefon este invalid!");// Aruncam o exceptie
		this.nrTelefon=nrTel;
	}
	public void setCNP(String cnp,PreparedStatement ps,boolean cnpNeschimbat) throws ExceptieStudent
	{
		cnp = cnp.trim();
			try
			{
				ps.setString(1, cnp);
				ResultSet rs = ps.executeQuery();
				if(cnpNeschimbat)
					rs.next();
				if(rs.next())
					throw new ExceptieStudent("Deja exista un student înregistrat cu acest CNP!");
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
			throw new ExceptieStudent("Cnp-ul trebuie să conțină 13 caractere!");// Aruncam o exceptie
		if (!cnp.matches("[0-9]+"))// Daca CNP-ul contine alte caractere decat cifre
			throw new ExceptieStudent("Cnp-ul poate conține decât cifre!");// Aruncam o exceptie

		// Validam corectitudinea CNP-ului
		for (int i = 0; i < 12; i++)
			s += (Integer.parseInt(cnp.substring(i, i + 1)) * Integer.parseInt(c.substring(i, i + 1)));
		r = s % 11;
		if (r == 10)
			r = 1;
		if (r != Integer.parseInt(cnp.substring(12, 13)))// Daca CNP-ul nu este corect
			throw new ExceptieStudent("Datele din cnp nu sunt valide!");// Aruncam o exceptie

		// Extragem data nasterii din CNP
		z = Integer.parseInt(cnp.substring(5, 7));
		l = Integer.parseInt(cnp.substring(3, 5));
		a = Integer.parseInt(cnp.substring(1, 3));
		if (l < 1 || l > 12)
			throw new ExceptieStudent("Datele din cnp nu sunt valide!");
		
		
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
					"Studentul este rezident in Romania, va rugam introduceti anul nasterii al studentului : ");
			String aux = Main.sc.nextLine().strip();// Citim anul nasterii
			a = Integer.parseInt(aux);// Il convertim in int
		}*/

		if (l < 1 || l > 12)// Daca luna extransa din CNP nu este valida
			throw new ExceptieStudent("Datele din cnp nu sunt valide");
		LocalDate auxLD = LocalDate.of(a, l, 1);// Variabila auxiliara pentru validarea zilei extrase din CNP
		if (z < 1 || z > auxLD.lengthOfMonth())// Daca ziua extransa din CNP nu este valida
			throw new ExceptieStudent("Datele din cnp nu sunt valide");
		
		
		
		this.cnp=cnp;
	}
	public void setEmail(String email) throws ExceptieStudent
	{
		email = email.trim();
		if(!email.matches("^([a-zA-Z0-9]+(-|\\.|\\_)*[a-zA-Z0-9]+)+@([a-zA-Z0-9]+(-|\\.|\\_)*[a-zA-Z0-9]+)+\\.[a-z]{2,4}$"))
			throw new ExceptieStudent("Email invalid!");
		this.email=email;
	}
	public void setAngajat(boolean a)
	{
		this.angajat=a;
	}
	public void setSold(String s) throws ExceptieStudent
	{
		s = s.trim();
		try
		{
		float sold = Float.parseFloat(s);
		if(sold<0)
			throw new ExceptieStudent("Soldul nu poate fi negativ!");
		this.soldAnCurent = sold;
		}
		catch(Exception e)
		{
			System.out.println(s);
			throw new ExceptieStudent("Soldul trebuie să fie un număr zecimal!");
		}

	}
	public void setAdresa(String a)
	{
		this.adresa=a;
	}
	public int getNrMatricol()
	{
		return nrMatricol;
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
	public boolean isAngajat()
	{
		return angajat;
	}
	public double getSoldAnCurent()
	{
		return soldAnCurent;
	}
}
