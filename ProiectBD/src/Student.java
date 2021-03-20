import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JTextField;

public class Student extends Utilizator
{
	
	/**
	 * Faptul ca studentul este sau nu angajat
	 */
	private boolean angajat;
	/**
	 * Soldul anului curent al studentului
	 */
	private double soldAnCurent;
	
	public void setareInformatii(List<JTextField> ltf,PreparedStatement CNPUnic,boolean cnpNeschimbat) throws ExceptieUtilizator
	{
		super.setareInformatii(ltf, CNPUnic, cnpNeschimbat);
		try {
			setSold(ltf.get(6).getText());
		} catch (ExceptieStudent e) {
			throw new ExceptieUtilizator("Sold invalid!");
		}
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
	
	public boolean isAngajat()
	{
		return angajat;
	}
	public double getSoldAnCurent()
	{
		return soldAnCurent;
	}
}
