import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTextField;

public class Disciplina
{
	private String nume;
	private int prezente;
	private int nrTeste;
	private int procentTeste;
	private int procentExamen;
	private boolean bonus;
	
	public Disciplina()
	{
		
	}

	public String getNume()
	{
		return nume;
	}
	public void setareInformatii(List<JTextField> ltf, PreparedStatement ps, boolean numeNeschimbat) throws ExceptieDisciplina
	{
		setNume(ltf.get(0).getText(), ps, numeNeschimbat);
		setPrezente(ltf.get(1).getText());
		setNrTeste(ltf.get(2).getText());
		setProcentTeste(ltf.get(3).getText());
		setProcentExamen(ltf.get(4).getText());
	}
	public void setNume(String nume,PreparedStatement ps,boolean numeNeschimbat) throws ExceptieDisciplina
	{
		nume = nume.trim();
		try
		{
			ps.setString(1, nume);
			ResultSet rs = ps.executeQuery();
			if(numeNeschimbat)
				rs.next();
			if(rs.next())
				throw new ExceptieDisciplina("Deja există o disciplină cu acest nume!");
			if(!nume.matches("[a-zA-Z]+[a-zA-Z0-9]*"))
				throw new ExceptieDisciplina("Numele disciplinei nu are un format valid!");
			this.nume = nume;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getPrezente()
	{
		return prezente;
	}

	public void setPrezente(String pr) throws ExceptieDisciplina
	{
		pr = pr.trim();
		try
		{
			int prez = Integer.parseInt(pr);
				if(prez<0 || prez > 14)
					throw new ExceptieDisciplina("Numarul de prezente minime trebuie sa fie între 0 și 14!");
			this.prezente = prez;
		}
		catch(ExceptieDisciplina ed)
		{
			throw ed;
		}
		catch(Exception e)
		{
			throw new ExceptieDisciplina("Numărul de prezențe trebuie să fie un întreg!");
		}

	}

	public int getNrTeste()
	{
		return nrTeste;
	}
	
	public void setNrTeste(String nrT) throws ExceptieDisciplina
	{
		nrT= nrT.trim();
		try
		{
			int nrTes = Integer.parseInt(nrT);
				if(nrTes<0)
					throw new ExceptieDisciplina("Numarul de teste nu poate fi negativ");
			this.nrTeste = nrTes;
		}
		catch(ExceptieDisciplina ed)
		{
			throw ed;
		}
		catch(Exception e)
		{
			throw new ExceptieDisciplina("Numărul de teste trebuie să fie un întreg!");
			
		}
		
	}

	public int getProcentTeste()
	{
		return procentTeste;
	}

	public void setProcentTeste(String pt) throws ExceptieDisciplina
	{
		pt = pt.trim();
		try 
		{
			int procentTes = Integer.parseInt(pt);
				if(procentTes<0 || procentTes > 100)
					throw new ExceptieDisciplina("Procentul testelor trebuie să fie între 0 și 100");
			this.procentTeste = procentTes;
		}
		catch(ExceptieDisciplina ed)
		{
			throw ed;
		}
		catch(Exception e)
		{
			throw new ExceptieDisciplina("Procentul testelor trebuie să fie un întreg!");
		}
	}

	public int getProcentExamen()
	{
		return procentExamen;
	}

	public void setProcentExamen(String pe) throws ExceptieDisciplina
	{
		pe = pe.trim();
		try
		{
			int procentEx = Integer.parseInt(pe);
				if(procentEx<0 || procentEx > 100)
					throw new ExceptieDisciplina("Procentul examenului trebuie să fie între 0 și 100");
				if(procentEx+this.procentTeste!=100)
					throw new ExceptieDisciplina("Procentul examenului trebuie să fie "+ (100-procentTeste));
			this.procentExamen = procentEx;
		}
		catch(ExceptieDisciplina ed)
		{
			throw ed;
		}
		catch(Exception e)
		{
			throw new ExceptieDisciplina("Procentul examenului trebuie să fie un întreg!");
		}
	}

	public boolean isBonus()
	{
		return bonus;
	}

	public void setBonus(boolean bonus)
	{
		this.bonus = bonus;
	}
	
}
