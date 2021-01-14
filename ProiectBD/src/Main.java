import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.CardLayout;
import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;

public class Main extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ResultSet rs;
	private static Connection con;
	private JPanel contentPane;
	private JTextField textField_Email;
	private JTextField textField_CNP;
	private JTextField textField_Nume;
	private JTextField textField_Prenume;
	private JTextField textField_Telefon;
	private JTextField textField_Adresa;
	private JTextField textField_Sold;
	private DefaultListModel<String> disciplinePosibileAdaugareDLM = new DefaultListModel<String>();
	private DefaultListModel<String> disciplinePosibileStergereDLM = new DefaultListModel<String>();
	private Map<String, Integer> disciplinePosibileAdaugareID = new HashMap<String, Integer>();
	private Map<String, Integer> disciplinePosibileStergereID = new HashMap<String, Integer>();

	private static PreparedStatement stergereNota;
	private static PreparedStatement stergerePrezenta;
	private static PreparedStatement dataCurenta;
	private static PreparedStatement inserareStudent;
	private static PreparedStatement CNPUnic;
	private static PreparedStatement disciplinaUnica;
	private static PreparedStatement stergereStudent;
	private static PreparedStatement modificareStudent;
	private static PreparedStatement modificareDisciplina;
	private static PreparedStatement inserareDisciplina;
	private static PreparedStatement stergereDisciplina;
	private static PreparedStatement cautareStudentCNP;
	private static PreparedStatement cautareStudentNRM;
	private static PreparedStatement cautareDisciplina;
	private static PreparedStatement disciplinePosibileAdaugareStudent;
	private static PreparedStatement disciplinePosibileStergereStudent;
	private static PreparedStatement inscriereStudentDisciplina;
	private static PreparedStatement stergereStudentDisciplina;
	private static PreparedStatement studentInscrisDisciplina;
	private static PreparedStatement inserarePrezenta;
	private static PreparedStatement numarPrezenteStudentDisciplina;
	private static PreparedStatement numarTesteStudentDisciplina;
	private static PreparedStatement numarTesteDisciplina;
	private static PreparedStatement bonusDisciplina;
	private static PreparedStatement examenStudentDisciplina;
	private static PreparedStatement inserareNota;
	private static PreparedStatement noteStudent;
	private static PreparedStatement prezenteStudent;
	private static PreparedStatement prezentaUnica;
	private static PreparedStatement notaUnica;
	private static PreparedStatement disciplineStudent;

	private static int nrMatricolCautat;
	private static String cnpCautat;
	private static String numeDCautat;
	private static int idDCautat;
	private static Student studentCautat = new Student();
	private static Disciplina disciplinaCautata = new Disciplina();
	private static LocalDate dataSistem;

	private JTextField textField_NumeDisc;
	private JTextField textField_PrezenteMin;
	private JTextField textField_NrTeste;
	private JTextField textField_ProcentTeste;
	private JTextField textField_ProcentExamen;
	private JTextField textField_StringC;
	private JTextField textField_Email_SC;
	private JTextField textField_CNP_SC;
	private JTextField textField_Nume_SC;
	private JTextField textField_Prenume_SC;
	private JTextField textField_Telefon_SC;
	private JTextField textField_Adresa_SC;
	private JTextField textField_Sold_SC;
	private JTextField textField_DiscCaut;
	private JTextField textField_NumeDisc_DC;
	private JTextField textField_PrezenteMin_DC;
	private JTextField textField_NrTeste_DC;
	private JTextField textField_ProcentTeste_DC;
	private JTextField textField_ProcentExamen_DC;
	private JTextField textField_CNP_Prezente;
	private JTextField textField_NumeDisc_Prezente;
	private JTextField textField_CNP_Note;
	private JTextField textField_NumeDisc_Note;
	private JTextField textField_Nota_Note;
	private JTable tabelNoteStudent;
	private JTextField textField_StringCN;
	private JTextField textField_StringCP;
	private JTable tabelPrezenteStudent;

	private String informatiiStudent(Student s)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("######################################################\nNr.Matricol : " + s.getNrMatricol()
				+ "\nCNP : " + s.getCnp() + "\nNume : " + s.getNume() + "\nPrenume : " + s.getPrenume()
				+ "\nNr.Telefon : " + s.getNrTelefon() + "\nEmail : " + s.getEmail() + "\nAdresă : " + s.getAdresa()
				+ "\nSold an curent : " + s.getSoldAnCurent() + "\nAngajat : " + (s.isAngajat() ? "Da" : "Nu"));
		try
		{
			disciplineStudent.setInt(1, s.getNrMatricol());
			ResultSet discipline = disciplineStudent.executeQuery();
			sb.append("\n----------------------Discipline----------------------");
			while (discipline.next())
			{
				sb.append("\n******************************************************");
				sb.append("\nNume disciplină : " + discipline.getString(1) + "\nPrezențe minime necesare : "
						+ discipline.getInt(2) + "\nPrezențe student : " + discipline.getInt(3)
						+ "\nMedia studentului : " + discipline.getInt(4));
			}

		} catch (SQLException e)
		{
			sb.append("Eroare la căutare discipline!");
		}
		return sb.toString();
	}

	private String informatiiDisiplina(Disciplina d)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("######################################################\nNume disciplină : " + d.getNume()
				+ "\nPrezențe minime : " + d.getPrezente() + "\nNumăr teste : " + d.getNrTeste() + "\nProcent teste : "
				+ d.getProcentTeste()+"\nProcent examen : "+d.getProcentExamen()+"\nPuncte bonus : "+(d.isBonus()?"Da":"Nu"));
		return sb.toString();
	}

	private void noteTabelStudent(JTable table, int nrm)
	{

		table.setModel(new DefaultTableModel()
		{
			Class[] columnTypes = new Class[]
			{ Integer.class, LocalDate.class, String.class, Float.class, TipNota.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("ID_Nota");
		dfm.addColumn("Data Notei");
		dfm.addColumn("Nume Disciplină");
		dfm.addColumn("Nota");
		dfm.addColumn("Tip");
		try
		{
			noteStudent.setInt(1, nrm);
			ResultSet rs = noteStudent.executeQuery();
			String tn;
			while (rs.next())
			{
				tn = rs.getString(5);
				dfm.addRow(new Object[]
				{ rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getFloat(4),
						tn.equals("T") ? TipNota.Test : tn.equals("E") ? TipNota.Examen : TipNota.Bonus });
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(76);
		table.getColumnModel().getColumn(2).setPreferredWidth(112);
		table.getColumnModel().getColumn(3).setPreferredWidth(34);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Float.class, centerRenderer);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.setDefaultRenderer(Object.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
	}

	private void prezenteTabelStudent(JTable table, int nrm)
	{

		table.setModel(new DefaultTableModel()
		{
			Class[] columnTypes = new Class[]
			{ Integer.class, LocalDate.class, String.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("ID_Prezență");
		dfm.addColumn("Data Prezenței");
		dfm.addColumn("Nume Disciplină");
		try
		{
			prezenteStudent.setInt(1, nrm);
			ResultSet rs = prezenteStudent.executeQuery();
			while (rs.next())
			{
				dfm.addRow(new Object[]
				{ rs.getInt(1), rs.getDate(2), rs.getString(3) });
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(76);
		table.getColumnModel().getColumn(2).setPreferredWidth(112);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.setDefaultRenderer(Object.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
	}

	private void setareComboData(JComboBox<Integer> cbA, JComboBox<Integer> cbL, JComboBox<Integer> cbZ)
	{
		cbA.setModel(dataCurenta());
		cbL.setSelectedIndex(dataSistem.getMonthValue() - 1);
		cbZ.setModel(actualizareZile(cbA.getModel().getElementAt(0),
				cbL.getModel().getElementAt(dataSistem.getMonthValue() - 1)));
		cbZ.setSelectedIndex(dataSistem.getDayOfMonth() - 1);
		cbZ.setSelectedIndex(dataSistem.getDayOfMonth() - 1);
	}

	/**
	 * Create the frame.
	 */
	public Main()
	{
		setTitle("Aplica\u021Bie gestiune studen\u021Bi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0, 0);
		contentPane.setLayout(cl);

		JPanel MeniuPrincipal = new JPanel();
		contentPane.add(MeniuPrincipal, "MeniuPrincipal");
		MeniuPrincipal.setLayout(null);

		JButton btnNewButton = new JButton("Meniu Studen\u021Bi");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton.setBounds(225, 64, 150, 30);
		btnNewButton.setFocusable(false);
		MeniuPrincipal.add(btnNewButton);

		JButton btnMeniuDiscipline = new JButton("Meniu Discipline");
		btnMeniuDiscipline.setFont(new Font("Arial", Font.PLAIN, 15));
		btnMeniuDiscipline.setFocusable(false);
		btnMeniuDiscipline.setBounds(225, 124, 150, 30);
		MeniuPrincipal.add(btnMeniuDiscipline);

		JButton btnCatalog = new JButton("Catalog");

		btnCatalog.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCatalog.setFocusable(false);
		btnCatalog.setBounds(225, 184, 150, 30);
		MeniuPrincipal.add(btnCatalog);

		JPanel MeniuStudenti = new JPanel();
		contentPane.add(MeniuStudenti, "MeniuStudenți");
		MeniuStudenti.setLayout(null);

		JPanel Catalog = new JPanel();
		contentPane.add(Catalog, "Catalog");
		Catalog.setLayout(null);

		JPanel MeniuNote = new JPanel();
		contentPane.add(MeniuNote, "MeniuNote");
		MeniuNote.setLayout(null);

		JButton btnNewButton_1_4 = new JButton("Înapoi");
		btnNewButton_1_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "Catalog");
			}
		});
		btnNewButton_1_4.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_4.setFocusable(false);
		btnNewButton_1_4.setBounds(10, 413, 85, 30);
		MeniuNote.add(btnNewButton_1_4);

		JButton btnAdugareNota = new JButton("Adăugare Notă");
		btnAdugareNota.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "AdăugareNotă");
			}
		});
		btnAdugareNota.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareNota.setFocusable(false);
		btnAdugareNota.setBounds(215, 66, 170, 30);
		MeniuNote.add(btnAdugareNota);

		JButton btnNoteStudent = new JButton("Note Student");
		btnNoteStudent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutareNoteStudent");
			}
		});
		btnNoteStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNoteStudent.setFocusable(false);
		btnNoteStudent.setBounds(215, 116, 170, 30);
		MeniuNote.add(btnNoteStudent);

		JPanel AdaugareNota = new JPanel();
		contentPane.add(AdaugareNota, "AdăugareNotă");
		AdaugareNota.setLayout(null);

		JPanel PrezenteStudent = new JPanel();
		PrezenteStudent.setLayout(null);
		contentPane.add(PrezenteStudent, "PrezențeStudent");

		JScrollPane scrollPane_TabelPrezente = new JScrollPane();
		scrollPane_TabelPrezente.setBounds(31, 50, 507, 298);
		PrezenteStudent.add(scrollPane_TabelPrezente);

		tabelPrezenteStudent = new JTable();
		scrollPane_TabelPrezente.setViewportView(tabelPrezenteStudent);

		JButton btnNewButton_1_3_2_1_1 = new JButton("Înapoi");
		btnNewButton_1_3_2_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutarePrezențeStudent");
			}
		});
		btnNewButton_1_3_2_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_3_2_1_1.setFocusable(false);
		btnNewButton_1_3_2_1_1.setBounds(10, 413, 85, 30);
		PrezenteStudent.add(btnNewButton_1_3_2_1_1);

		JButton btnNewButton_1_1_1_5_1 = new JButton("Ștergere Prezențe");
		btnNewButton_1_1_1_5_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_5_1.setFocusable(false);
		btnNewButton_1_1_1_5_1.setBounds(409, 413, 157, 30);
		PrezenteStudent.add(btnNewButton_1_1_1_5_1);

		JPanel NoteStudent = new JPanel();
		contentPane.add(NoteStudent, "NoteStudent");
		NoteStudent.setLayout(null);

		JScrollPane scrollPane_TabelNote = new JScrollPane();
		scrollPane_TabelNote.setBounds(31, 50, 507, 298);

		tabelNoteStudent = new JTable();
		NoteStudent.add(scrollPane_TabelNote);

		scrollPane_TabelNote.setViewportView(tabelNoteStudent);

		btnNewButton_1_1_1_5_1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int[] randuri = tabelPrezenteStudent.getSelectedRows();
				List<Integer> idPrezSterse = new ArrayList<Integer>();
				for (int rand : randuri)
					idPrezSterse.add((Integer) tabelPrezenteStudent.getValueAt(rand, 0));
				idPrezSterse.forEach(idp ->
				{
					stergerePrezenta(idp);
				});
				JOptionPane.showInternalMessageDialog(null, "Prezențe șterse cu succes", "Ștergere prezențe",
						JOptionPane.INFORMATION_MESSAGE, null);
				cl.show(contentPane, "CăutarePrezențeStudent");

			}
		});

		JButton btnNewButton_1_3_2_1 = new JButton("Înapoi");
		btnNewButton_1_3_2_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutareNoteStudent");
			}
		});
		btnNewButton_1_3_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_3_2_1.setFocusable(false);
		btnNewButton_1_3_2_1.setBounds(10, 413, 85, 30);
		NoteStudent.add(btnNewButton_1_3_2_1);

		JButton btnNewButton_1_1_1_5_1_2 = new JButton("Ștergere Note");
		btnNewButton_1_1_1_5_1_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int[] randuri = tabelNoteStudent.getSelectedRows();
				List<Integer> idNoteSterse = new ArrayList<Integer>();
				for (int rand : randuri)
					idNoteSterse.add((Integer) tabelNoteStudent.getValueAt(rand, 0));
				idNoteSterse.forEach(idn ->
				{
					stergereNota(idn);
				});
				JOptionPane.showInternalMessageDialog(null, "Note șterse cu succes", "Ștergere note",
						JOptionPane.INFORMATION_MESSAGE, null);
				cl.show(contentPane, "CăutareNoteStudent");

			}
		});
		btnNewButton_1_1_1_5_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_5_1_2.setFocusable(false);
		btnNewButton_1_1_1_5_1_2.setBounds(429, 413, 137, 30);
		NoteStudent.add(btnNewButton_1_1_1_5_1_2);

		JButton btnNewButton_1_3_2 = new JButton("Înapoi");
		btnNewButton_1_3_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuNote");
			}
		});
		btnNewButton_1_3_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_3_2.setFocusable(false);
		btnNewButton_1_3_2.setBounds(10, 413, 85, 30);
		AdaugareNota.add(btnNewButton_1_3_2);

		JLabel lblNewLabel_6_2 = new JLabel("Data Notei");
		lblNewLabel_6_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_6_2.setBounds(435, 53, 85, 21);
		AdaugareNota.add(lblNewLabel_6_2);

		JComboBox<Integer> comboBox_AnPrezenta_Nota = new JComboBox<Integer>();
		comboBox_AnPrezenta_Nota.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_AnPrezenta_Nota.setBounds(465, 92, 78, 21);
		AdaugareNota.add(comboBox_AnPrezenta_Nota);

		JLabel lblNewLabel_6_1_2 = new JLabel("Anul");
		lblNewLabel_6_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_2.setBounds(413, 96, 30, 13);
		AdaugareNota.add(lblNewLabel_6_1_2);

		JComboBox<Integer> comboBox_LunaPrezenta_Nota = new JComboBox<Integer>();
		comboBox_LunaPrezenta_Nota.setModel(new DefaultComboBoxModel<Integer>(new Integer[]
		{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));
		comboBox_LunaPrezenta_Nota.setSelectedIndex(0);
		comboBox_LunaPrezenta_Nota.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_LunaPrezenta_Nota.setBounds(494, 139, 48, 21);
		AdaugareNota.add(comboBox_LunaPrezenta_Nota);

		JLabel lblNewLabel_6_1_1_2 = new JLabel("Luna");
		lblNewLabel_6_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_1_2.setBounds(413, 143, 42, 13);
		AdaugareNota.add(lblNewLabel_6_1_1_2);

		JComboBox<Integer> comboBox_ZiPrezenta_Nota = new JComboBox<Integer>();
		comboBox_ZiPrezenta_Nota.setSelectedIndex(-1);
		comboBox_ZiPrezenta_Nota.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_ZiPrezenta_Nota.setBounds(494, 185, 48, 21);
		AdaugareNota.add(comboBox_ZiPrezenta_Nota);

		JLabel lblNewLabel_6_1_1_1_1 = new JLabel("Ziua");
		lblNewLabel_6_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_1_1_1.setBounds(413, 189, 42, 13);
		AdaugareNota.add(lblNewLabel_6_1_1_1_1);

		textField_CNP_Note = new JTextField();
		textField_CNP_Note.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_CNP_Note.setColumns(10);
		textField_CNP_Note.setBounds(206, 90, 180, 25);
		AdaugareNota.add(textField_CNP_Note);

		JLabel lblCnpStudent_1 = new JLabel("Student");
		lblCnpStudent_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCnpStudent_1.setBounds(137, 87, 52, 30);
		AdaugareNota.add(lblCnpStudent_1);

		textField_NumeDisc_Note = new JTextField();
		textField_NumeDisc_Note.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_NumeDisc_Note.setColumns(10);
		textField_NumeDisc_Note.setBounds(206, 147, 180, 25);
		AdaugareNota.add(textField_NumeDisc_Note);

		JLabel lblDisciplin_1 = new JLabel("Disciplină");
		lblDisciplin_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisciplin_1.setBounds(131, 144, 65, 30);
		AdaugareNota.add(lblDisciplin_1);

		JButton btnNewButton_1_1_1_5_2 = new JButton("Adăugare");

		btnNewButton_1_1_1_5_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_5_2.setFocusable(false);
		btnNewButton_1_1_1_5_2.setBounds(454, 413, 112, 30);
		AdaugareNota.add(btnNewButton_1_1_1_5_2);

		textField_Nota_Note = new JTextField();
		textField_Nota_Note.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Nota_Note.setColumns(10);
		textField_Nota_Note.setBounds(206, 204, 180, 25);
		AdaugareNota.add(textField_Nota_Note);

		JLabel lblDisciplin_1_1 = new JLabel("Nota");
		lblDisciplin_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisciplin_1_1.setBounds(147, 201, 42, 30);
		AdaugareNota.add(lblDisciplin_1_1);

		JComboBox<String> comboBox_TipNota = new JComboBox<String>();
		comboBox_TipNota.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "Test", "Examen", "Bonus" }));
		comboBox_TipNota.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_TipNota.setBounds(206, 267, 90, 21);
		AdaugareNota.add(comboBox_TipNota);

		setareComboData(comboBox_AnPrezenta_Nota, comboBox_LunaPrezenta_Nota, comboBox_ZiPrezenta_Nota);

		comboBox_LunaPrezenta_Nota.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				comboBox_ZiPrezenta_Nota.setModel(actualizareZile((Integer) comboBox_AnPrezenta_Nota.getSelectedItem(),
						(Integer) comboBox_LunaPrezenta_Nota.getSelectedItem()));

			}

		});
		comboBox_AnPrezenta_Nota.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				comboBox_ZiPrezenta_Nota.setModel(actualizareZile((Integer) comboBox_AnPrezenta_Nota.getSelectedItem(),
						(Integer) comboBox_LunaPrezenta_Nota.getSelectedItem()));

			}

		});
		JComboBox<String> comboBox_CautareStudent_TIPC_N = new JComboBox<String>();
		comboBox_CautareStudent_TIPC_N.setModel(new DefaultComboBoxModel(new String[]
		{ "CNP", "Nr.Matricol" }));
		comboBox_CautareStudent_TIPC_N.setSelectedIndex(1);
		comboBox_CautareStudent_TIPC_N.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_CautareStudent_TIPC_N.setBounds(28, 92, 99, 21);
		AdaugareNota.add(comboBox_CautareStudent_TIPC_N);
		btnNewButton_1_1_1_5_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				String stringCautat = textField_CNP_Note.getText().trim();
				String numeDisc = textField_NumeDisc_Note.getText().trim().toUpperCase();
				float punctaj = Float.parseFloat(textField_Nota_Note.getText().trim());
				String tip = (String) comboBox_TipNota.getSelectedItem().toString();
				tip = tip.substring(0, 1);
				String optiuneC = (String) comboBox_CautareStudent_TIPC_N.getSelectedItem();
				boolean cnp = optiuneC.equals("CNP");
				try
				{
					if (!cautareStudent(stringCautat, cnp))
						JOptionPane.showInternalMessageDialog(null,
								"Nu am găsit studentul cu "
										+ (cnp ? "CNP-ul " + stringCautat : "Nr.Matricol " + stringCautat),
								"Eroare căutare student", JOptionPane.WARNING_MESSAGE, null);
					else if (!cautareDisciplina(numeDisc))
						JOptionPane.showInternalMessageDialog(null, "Nu am găsit disciplina cu numele " + numeDisc,
								"Eroare căutare disciplină", JOptionPane.WARNING_MESSAGE, null);
					else if (!studentInscrisDisciplina(nrMatricolCautat, idDCautat))
						JOptionPane.showInternalMessageDialog(null,
								"Studentul cu " + (cnp ? "cnp-ul " + cnpCautat : "Nr.Matricol " + nrMatricolCautat)
										+ " nu este înscris la disciplina " + numeDisc,
								"Eroare căutare înscriere", JOptionPane.WARNING_MESSAGE, null);
					else if (punctaj < 0 || punctaj > 10)
						throw new Exception("Nota poate fi doar între 0 și 10");
					else
					{
						switch (tip)
						{
						case "E":
							if (areExamen(nrMatricolCautat, idDCautat))
								throw new ExceptieStudent("Studentul are deja un examen la această materie!");
							break;
						case "T":
							if (maximTeste(nrMatricolCautat, idDCautat))
								throw new ExceptieStudent("Studentul are deja note la toate testele materiei!");
							break;
						case "B":
							if (!bonusDisciplina(idDCautat))
								throw new ExceptieDisciplina("Disciplina nu acceptă puncte bonus!");
							break;
						}
						LocalDate data = LocalDate.of((Integer) comboBox_AnPrezenta_Nota.getSelectedItem(),
								(Integer) comboBox_LunaPrezenta_Nota.getSelectedItem(),
								(Integer) comboBox_ZiPrezenta_Nota.getSelectedItem());

						inserareNota(data, nrMatricolCautat, idDCautat, punctaj, tip);

						textField_CNP_Note.setText("");
						textField_NumeDisc_Note.setText("");
						textField_Nota_Note.setText("");
						setareComboData(comboBox_AnPrezenta_Nota, comboBox_LunaPrezenta_Nota, comboBox_ZiPrezenta_Nota);
						comboBox_TipNota.setSelectedIndex(0);
						JOptionPane.showInternalMessageDialog(null, "Notă adăugată cu succes", "Adăugare notă",
								JOptionPane.INFORMATION_MESSAGE, null);
					}

				} catch (NumberFormatException nfe)
				{
					JOptionPane.showInternalMessageDialog(null, "Nota trebuie să fie un număr zecimal!",
							"Eroare adăugare notă", JOptionPane.WARNING_MESSAGE, null);
				} catch (NullPointerException npe)
				{
					JOptionPane.showInternalMessageDialog(null, "Câmpul cu nota este gol", "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (ExceptieDisciplina e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (Exception e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				}

			}
		});

		JLabel lblDisciplin_1_1_1 = new JLabel("Tipul notei");
		lblDisciplin_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisciplin_1_1_1.setBounds(119, 262, 70, 30);
		AdaugareNota.add(lblDisciplin_1_1_1);

		JPanel MeniuPrezente = new JPanel();
		contentPane.add(MeniuPrezente, "MeniuPrezențe");
		MeniuPrezente.setLayout(null);

		JButton btnAdugarePrezene = new JButton("Adăugare Prezență");
		btnAdugarePrezene.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "AdăugarePrezență");
			}
		});
		btnAdugarePrezene.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugarePrezene.setFocusable(false);
		btnAdugarePrezene.setBounds(215, 66, 170, 30);
		MeniuPrezente.add(btnAdugarePrezene);

		JButton btnNewButton_1_4_1 = new JButton("Înapoi");
		btnNewButton_1_4_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "Catalog");
			}
		});
		btnNewButton_1_4_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_4_1.setFocusable(false);
		btnNewButton_1_4_1.setBounds(10, 413, 85, 30);
		MeniuPrezente.add(btnNewButton_1_4_1);

		JButton btnPrezeneStudent = new JButton("Prezențe Student");
		btnPrezeneStudent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutarePrezențeStudent");
			}
		});
		btnPrezeneStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPrezeneStudent.setFocusable(false);
		btnPrezeneStudent.setBounds(215, 116, 170, 30);
		MeniuPrezente.add(btnPrezeneStudent);

		JPanel AdaugarePrezenta = new JPanel();
		contentPane.add(AdaugarePrezenta, "AdăugarePrezență");
		AdaugarePrezenta.setLayout(null);

		JButton btnNewButton_1_3_1 = new JButton("Înapoi");
		btnNewButton_1_3_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuPrezențe");
			}
		});
		btnNewButton_1_3_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_3_1.setFocusable(false);
		btnNewButton_1_3_1.setBounds(10, 413, 85, 30);
		AdaugarePrezenta.add(btnNewButton_1_3_1);

		JLabel lblNewLabel_6 = new JLabel("Data Prezenței");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(425, 53, 117, 21);
		AdaugarePrezenta.add(lblNewLabel_6);

		JComboBox<Integer> comboBox_AnPrezenta = new JComboBox<Integer>();
		comboBox_AnPrezenta.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_AnPrezenta.setBounds(465, 92, 78, 21);

		AdaugarePrezenta.add(comboBox_AnPrezenta);

		JLabel lblNewLabel_6_1 = new JLabel("Anul");
		lblNewLabel_6_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1.setBounds(413, 96, 30, 13);
		AdaugarePrezenta.add(lblNewLabel_6_1);

		JComboBox<Integer> comboBox_LunaPrezenta = new JComboBox<Integer>();
		comboBox_LunaPrezenta.setModel(new DefaultComboBoxModel<Integer>(new Integer[]
		{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));
		comboBox_LunaPrezenta.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_LunaPrezenta.setBounds(494, 139, 48, 21);
		AdaugarePrezenta.add(comboBox_LunaPrezenta);

		JLabel lblNewLabel_6_1_1 = new JLabel("Luna");
		lblNewLabel_6_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_1.setBounds(413, 143, 42, 13);
		AdaugarePrezenta.add(lblNewLabel_6_1_1);

		JComboBox<Integer> comboBox_ZiPrezenta = new JComboBox<Integer>();
		comboBox_ZiPrezenta.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_ZiPrezenta.setBounds(494, 185, 48, 21);
		AdaugarePrezenta.add(comboBox_ZiPrezenta);

		setareComboData(comboBox_AnPrezenta, comboBox_LunaPrezenta, comboBox_ZiPrezenta);

		comboBox_LunaPrezenta.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				comboBox_ZiPrezenta.setModel(actualizareZile((Integer) comboBox_AnPrezenta.getSelectedItem(),
						(Integer) comboBox_LunaPrezenta.getSelectedItem()));

			}

		});
		comboBox_AnPrezenta.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				comboBox_ZiPrezenta.setModel(actualizareZile((Integer) comboBox_AnPrezenta.getSelectedItem(),
						(Integer) comboBox_LunaPrezenta.getSelectedItem()));

			}

		});
		JLabel lblNewLabel_6_1_1_1 = new JLabel("Ziua");
		lblNewLabel_6_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_1_1.setBounds(413, 189, 42, 13);
		AdaugarePrezenta.add(lblNewLabel_6_1_1_1);

		btnCatalog.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "Catalog");
			}
		});
		textField_CNP_Prezente = new JTextField();
		textField_CNP_Prezente.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_CNP_Prezente.setBounds(206, 90, 180, 25);
		AdaugarePrezenta.add(textField_CNP_Prezente);
		textField_CNP_Prezente.setColumns(10);

		JLabel lblCnpStudent = new JLabel("Student");
		lblCnpStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCnpStudent.setBounds(137, 87, 59, 30);
		AdaugarePrezenta.add(lblCnpStudent);

		textField_NumeDisc_Prezente = new JTextField();
		textField_NumeDisc_Prezente.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_NumeDisc_Prezente.setColumns(10);
		textField_NumeDisc_Prezente.setBounds(206, 147, 180, 25);
		AdaugarePrezenta.add(textField_NumeDisc_Prezente);

		JLabel lblDisciplin = new JLabel("Disciplină");
		lblDisciplin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisciplin.setBounds(131, 144, 65, 30);
		AdaugarePrezenta.add(lblDisciplin);

		JComboBox<String> comboBox_CautareStudent_TIPC = new JComboBox<String>();
		comboBox_CautareStudent_TIPC.setModel(new DefaultComboBoxModel(new String[]
		{ "CNP", "Nr.Matricol" }));
		comboBox_CautareStudent_TIPC.setSelectedIndex(0);
		comboBox_CautareStudent_TIPC.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_CautareStudent_TIPC.setBounds(28, 92, 99, 21);
		AdaugarePrezenta.add(comboBox_CautareStudent_TIPC);

		JButton btnNewButton_1_1_1_5 = new JButton("Adăugare");
		btnNewButton_1_1_1_5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String stringCautat = textField_CNP_Prezente.getText().trim();
				String optiuneC = (String) comboBox_CautareStudent_TIPC.getSelectedItem();
				boolean cnp = optiuneC.equals("CNP");
				String numeDisc = textField_NumeDisc_Prezente.getText().trim().toUpperCase();

				try
				{
					if (!cautareStudent(stringCautat, cnp))
						JOptionPane.showInternalMessageDialog(null,
								"Nu am găsit studentul cu "
										+ (cnp ? "CNP-ul " + stringCautat : "Nr.Matricol " + stringCautat),
								"Eroare căutare student", JOptionPane.WARNING_MESSAGE, null);
					else if (!cautareDisciplina(numeDisc))
						JOptionPane.showInternalMessageDialog(null, "Nu am găsit disciplina cu numele " + numeDisc,
								"Eroare căutare disciplină", JOptionPane.WARNING_MESSAGE, null);
					else if (!studentInscrisDisciplina(nrMatricolCautat, idDCautat))
						JOptionPane.showInternalMessageDialog(null,
								"Studentul cu " + (cnp ? "cnp-ul " + cnpCautat : "Nr.Matricol " + nrMatricolCautat)
										+ " nu este înscris la disciplina " + numeDisc,
								"Eroare căutare înscriere", JOptionPane.WARNING_MESSAGE, null);
					else if (!maximPrezente(idDCautat, nrMatricolCautat))
					{
						LocalDate data = LocalDate.of((Integer) comboBox_AnPrezenta.getSelectedItem(),
								(Integer) comboBox_LunaPrezenta.getSelectedItem(),
								(Integer) comboBox_ZiPrezenta.getSelectedItem());
						inserarePrezenta(data, nrMatricolCautat, idDCautat);

						textField_CNP_Prezente.setText("");
						textField_NumeDisc_Prezente.setText("");
						setareComboData(comboBox_AnPrezenta, comboBox_LunaPrezenta, comboBox_ZiPrezenta);
						JOptionPane.showInternalMessageDialog(null, "Prezență adăugată cu succes", "Adăugare prezență",
								JOptionPane.INFORMATION_MESSAGE, null);
					}

				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare prezență",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (ExceptieDisciplina e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare prezență",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (Exception e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare prezență",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1_1_5.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_5.setFocusable(false);
		btnNewButton_1_1_1_5.setBounds(454, 413, 112, 30);
		AdaugarePrezenta.add(btnNewButton_1_1_1_5);

		JButton btnNewButton_1_3 = new JButton("Înapoi");
		btnNewButton_1_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuPrincipal");
			}
		});
		btnNewButton_1_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_3.setFocusable(false);
		btnNewButton_1_3.setBounds(10, 413, 85, 30);
		Catalog.add(btnNewButton_1_3);

		JButton btnAdugarePrezen = new JButton("Prezențe");
		btnAdugarePrezen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuPrezențe");
			}
		});
		btnAdugarePrezen.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugarePrezen.setFocusable(false);
		btnAdugarePrezen.setBounds(215, 66, 170, 30);
		Catalog.add(btnAdugarePrezen);

		JButton btnNote = new JButton("Note");
		btnNote.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuNote");
			}
		});
		btnNote.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNote.setFocusable(false);
		btnNote.setBounds(215, 116, 170, 30);
		Catalog.add(btnNote);

		JButton btnNewButton_1 = new JButton("\u00CEnapoi");
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1.setBounds(10, 413, 85, 30);
		btnNewButton_1.setFocusable(false);
		MeniuStudenti.add(btnNewButton_1);

		JButton btnAdugareStudent = new JButton("Adăugare Student");
		btnAdugareStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareStudent.setFocusable(false);
		btnAdugareStudent.setBounds(215, 66, 170, 30);
		MeniuStudenti.add(btnAdugareStudent);

		JButton btnDisciplineStudent = new JButton("Căutare Student");

		btnDisciplineStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		btnDisciplineStudent.setFocusable(false);
		btnDisciplineStudent.setBounds(215, 116, 170, 30);
		MeniuStudenti.add(btnDisciplineStudent);

		cl.show(contentPane, "MeniuPrincipal");

		JPanel AdaugareStudent = new JPanel();
		contentPane.add(AdaugareStudent, "AdăugareStudent");
		AdaugareStudent.setLayout(null);

		JButton btnNewButton_1_1 = new JButton("Înapoi");
		btnNewButton_1_1.setSize(85, 30);
		btnNewButton_1_1.setLocation(10, 413);
		btnNewButton_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1.setFocusable(false);
		AdaugareStudent.add(btnNewButton_1_1);

		JLabel lblNewLabel = new JLabel("CNP");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(57, 44, 37, 30);
		AdaugareStudent.add(lblNewLabel);

		JLabel lblNume = new JLabel("Nume");
		lblNume.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNume.setBounds(57, 84, 52, 30);
		AdaugareStudent.add(lblNume);

		JLabel lblPreume = new JLabel("Preume");
		lblPreume.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreume.setBounds(57, 124, 63, 30);
		AdaugareStudent.add(lblPreume);

		JLabel lblTelefon = new JLabel("Telefon");
		lblTelefon.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefon.setBounds(57, 164, 63, 30);
		AdaugareStudent.add(lblTelefon);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmail.setBounds(57, 204, 52, 30);
		AdaugareStudent.add(lblEmail);

		JLabel lblAdres = new JLabel("Adresă");
		lblAdres.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAdres.setBounds(57, 244, 63, 30);
		AdaugareStudent.add(lblAdres);

		JLabel lblAngajat = new JLabel("Sold an curent");
		lblAngajat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAngajat.setBounds(57, 284, 105, 30);
		AdaugareStudent.add(lblAngajat);

		textField_Email = new JTextField();
		textField_Email.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Email.setColumns(10);
		textField_Email.setBounds(168, 207, 180, 24);
		AdaugareStudent.add(textField_Email);

		textField_CNP = new JTextField();
		textField_CNP.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_CNP.setColumns(10);
		textField_CNP.setBounds(168, 47, 180, 24);
		AdaugareStudent.add(textField_CNP);

		textField_Nume = new JTextField();
		textField_Nume.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Nume.setColumns(10);
		textField_Nume.setBounds(168, 87, 180, 24);
		AdaugareStudent.add(textField_Nume);

		textField_Prenume = new JTextField();
		textField_Prenume.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Prenume.setColumns(10);
		textField_Prenume.setBounds(168, 127, 180, 24);
		AdaugareStudent.add(textField_Prenume);

		textField_Telefon = new JTextField();
		textField_Telefon.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Telefon.setColumns(10);
		textField_Telefon.setBounds(168, 167, 180, 24);
		AdaugareStudent.add(textField_Telefon);

		textField_Adresa = new JTextField();
		textField_Adresa.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Adresa.setColumns(10);
		textField_Adresa.setBounds(168, 247, 180, 24);
		AdaugareStudent.add(textField_Adresa);

		textField_Sold = new JTextField();
		textField_Sold.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Sold.setColumns(10);
		textField_Sold.setBounds(168, 287, 180, 24);
		AdaugareStudent.add(textField_Sold);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "Da", "Nu" }));
		comboBox.setSelectedIndex(1);
		comboBox.setBounds(168, 329, 45, 21);
		AdaugareStudent.add(comboBox);

		List<JTextField> listaInformatiiStudentAdaugare = new ArrayList<JTextField>();
		listaInformatiiStudentAdaugare.add(textField_CNP);
		listaInformatiiStudentAdaugare.add(textField_Nume);
		listaInformatiiStudentAdaugare.add(textField_Prenume);
		listaInformatiiStudentAdaugare.add(textField_Telefon);
		listaInformatiiStudentAdaugare.add(textField_Email);
		listaInformatiiStudentAdaugare.add(textField_Adresa);
		listaInformatiiStudentAdaugare.add(textField_Sold);

		JButton btnNewButton_1_1_1 = new JButton("Adăugare");
		btnNewButton_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Student p = new Student();
				try
				{
					p.setareInformatii(listaInformatiiStudentAdaugare, CNPUnic, false);
					p.setAngajat(comboBox.getSelectedIndex() == 1 ? false : true);
					inserareStudent(p);
					clearTextFields(listaInformatiiStudentAdaugare);
					comboBox.setSelectedIndex(1);
					JOptionPane.showInternalMessageDialog(null, "Student adăugat cu succes", "Adăugare student",
							JOptionPane.INFORMATION_MESSAGE, null);

				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare student",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1.setFocusable(false);
		btnNewButton_1_1_1.setBounds(454, 413, 112, 30);
		AdaugareStudent.add(btnNewButton_1_1_1);

		JLabel lblAngajat_2 = new JLabel("Angajat ?");
		lblAngajat_2.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAngajat_2.setBounds(57, 324, 74, 30);
		AdaugareStudent.add(lblAngajat_2);

		JPanel MeniuDiscipline = new JPanel();
		contentPane.add(MeniuDiscipline, "MeniuDiscipline");
		MeniuDiscipline.setLayout(null);

		JPanel CautareDisciplina = new JPanel();
		contentPane.add(CautareDisciplina, "CăutareDisciplină");
		CautareDisciplina.setLayout(null);

		JPanel MeniuDisciplinaCautata = new JPanel();
		contentPane.add(MeniuDisciplinaCautata, "MeniuDisciplinăCăutată");
		MeniuDisciplinaCautata.setLayout(null);

		JPanel ModificareDisciplinaCautata = new JPanel();
		contentPane.add(ModificareDisciplinaCautata, "ModificareDisciplinăCăutată");
		ModificareDisciplinaCautata.setLayout(null);

		JButton btnNewButton_1_2_1_1_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_2_1_1_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuDisciplinăCăutată");
			}
		});
		btnNewButton_1_2_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_2_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_2_1_1_1_1_1.setBounds(10, 413, 85, 30);
		ModificareDisciplinaCautata.add(btnNewButton_1_2_1_1_1_1_1);

		textField_NumeDisc_DC = new JTextField();
		textField_NumeDisc_DC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_NumeDisc_DC.setColumns(10);
		textField_NumeDisc_DC.setBounds(168, 47, 180, 24);
		ModificareDisciplinaCautata.add(textField_NumeDisc_DC);

		JLabel lblNumeDisciplin_1 = new JLabel("Nume disciplină");
		lblNumeDisciplin_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeDisciplin_1.setBounds(44, 44, 114, 30);
		ModificareDisciplinaCautata.add(lblNumeDisciplin_1);

		textField_PrezenteMin_DC = new JTextField();
		textField_PrezenteMin_DC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_PrezenteMin_DC.setColumns(10);
		textField_PrezenteMin_DC.setBounds(168, 87, 180, 24);
		ModificareDisciplinaCautata.add(textField_PrezenteMin_DC);

		JLabel lblPrezeneMinime_1 = new JLabel("Prezențe minime");
		lblPrezeneMinime_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPrezeneMinime_1.setBounds(44, 84, 114, 30);
		ModificareDisciplinaCautata.add(lblPrezeneMinime_1);

		textField_NrTeste_DC = new JTextField();
		textField_NrTeste_DC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_NrTeste_DC.setColumns(10);
		textField_NrTeste_DC.setBounds(168, 127, 180, 24);
		ModificareDisciplinaCautata.add(textField_NrTeste_DC);

		JLabel lblNumrDeTeste_1 = new JLabel("Număr de teste");
		lblNumrDeTeste_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumrDeTeste_1.setBounds(44, 124, 114, 30);
		ModificareDisciplinaCautata.add(lblNumrDeTeste_1);

		textField_ProcentTeste_DC = new JTextField();
		textField_ProcentTeste_DC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_ProcentTeste_DC.setColumns(10);
		textField_ProcentTeste_DC.setBounds(168, 167, 180, 24);
		ModificareDisciplinaCautata.add(textField_ProcentTeste_DC);

		JLabel lblProcentTeste_1 = new JLabel("Procent teste");
		lblProcentTeste_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProcentTeste_1.setBounds(44, 164, 101, 30);
		ModificareDisciplinaCautata.add(lblProcentTeste_1);

		textField_ProcentExamen_DC = new JTextField();
		textField_ProcentExamen_DC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_ProcentExamen_DC.setColumns(10);
		textField_ProcentExamen_DC.setBounds(168, 207, 180, 24);
		ModificareDisciplinaCautata.add(textField_ProcentExamen_DC);

		JLabel lblProcentExamen_1 = new JLabel("Procent examen");
		lblProcentExamen_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProcentExamen_1.setBounds(44, 204, 114, 30);
		ModificareDisciplinaCautata.add(lblProcentExamen_1);

		JComboBox<String> comboBox_1_1 = new JComboBox<String>();
		comboBox_1_1.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "Da", "Nu" }));
		comboBox_1_1.setSelectedIndex(1);
		comboBox_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_1_1.setBounds(168, 247, 45, 21);
		ModificareDisciplinaCautata.add(comboBox_1_1);

		JLabel lblPuncteBonus_1 = new JLabel("Puncte bonus ?");
		lblPuncteBonus_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPuncteBonus_1.setBounds(44, 244, 114, 30);
		ModificareDisciplinaCautata.add(lblPuncteBonus_1);

		JTextArea textArea_DisciplinaCautata = new JTextArea();
		textArea_DisciplinaCautata.setEditable(false);
		
		List<JTextField> listaInformatiiDisciplinaModificare = new ArrayList<JTextField>();
		listaInformatiiDisciplinaModificare.add(textField_NumeDisc_DC);
		listaInformatiiDisciplinaModificare.add(textField_PrezenteMin_DC);
		listaInformatiiDisciplinaModificare.add(textField_NrTeste_DC);
		listaInformatiiDisciplinaModificare.add(textField_ProcentTeste_DC);
		listaInformatiiDisciplinaModificare.add(textField_ProcentExamen_DC);
		JButton btnNewButton_1_1_1_4_1 = new JButton("Salvare Modificări");
		btnNewButton_1_1_1_4_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{

					disciplinaCautata.setareInformatii(listaInformatiiDisciplinaModificare, disciplinaUnica,
							Objects.equals(listaInformatiiDisciplinaModificare.get(0).getText().trim(), numeDCautat));
					disciplinaCautata.setBonus(comboBox_1_1.getSelectedIndex() == 0 ? true : false);
					modificareDisciplinaCautata();
					textArea_DisciplinaCautata.setText(informatiiDisiplina(disciplinaCautata));
					cl.show(contentPane, "MeniuDisciplinăCăutată");
					clearTextFields(listaInformatiiDisciplinaModificare);
					comboBox_1_1.setSelectedIndex(1);
					JOptionPane.showInternalMessageDialog(null, "Disciplină modificată success",
							"Modificare disciplină", JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ExceptieDisciplina e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare modificare disciplină",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_4_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_4_1.setFocusable(false);
		btnNewButton_1_1_1_4_1.setBounds(409, 413, 157, 30);
		ModificareDisciplinaCautata.add(btnNewButton_1_1_1_4_1);

		JButton btnNewButton_1_2_1_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_2_1_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutareDisciplină");
			}
		});
		btnNewButton_1_2_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_2_1_1_1_1.setFocusable(false);
		btnNewButton_1_2_1_1_1_1.setBounds(10, 413, 85, 30);
		MeniuDisciplinaCautata.add(btnNewButton_1_2_1_1_1_1);

		JButton btnNewButton_1_1_1_2_1_1 = new JButton("Ștergere");
		btnNewButton_1_1_1_2_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					stergereDisciplina(numeDCautat);
					cl.show(contentPane, "CăutareDisciplină");
					JOptionPane.showInternalMessageDialog(null, "Disciplină ștearsă cu succes", "Ștergere disciplină",
							JOptionPane.INFORMATION_MESSAGE, null);

				} catch (ExceptieDisciplina e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare student",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnNewButton_1_1_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_2_1_1.setFocusable(false);
		btnNewButton_1_1_1_2_1_1.setBounds(454, 413, 112, 30);
		MeniuDisciplinaCautata.add(btnNewButton_1_1_1_2_1_1);

		JButton btnNewButton_1_1_1_2_1_1_1 = new JButton("Modificare disciplină");
		btnNewButton_1_1_1_2_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				textField_NumeDisc_DC.setText(disciplinaCautata.getNume());
				textField_PrezenteMin_DC.setText(String.valueOf(disciplinaCautata.getPrezente()));
				textField_NrTeste_DC.setText(String.valueOf(disciplinaCautata.getNrTeste()));
				textField_ProcentTeste_DC.setText(String.valueOf(disciplinaCautata.getProcentTeste()));
				textField_ProcentExamen_DC.setText(String.valueOf(disciplinaCautata.getProcentExamen()));
				comboBox_1_1.setSelectedIndex(disciplinaCautata.isBonus() ? 0 : 1);
				cl.show(contentPane, "ModificareDisciplinăCăutată");
			}
		});
		btnNewButton_1_1_1_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_2_1_1_1.setFocusable(false);
		btnNewButton_1_1_1_2_1_1_1.setBounds(275, 413, 169, 30);
		MeniuDisciplinaCautata.add(btnNewButton_1_1_1_2_1_1_1);

		JScrollPane scrollPane_DisciplinaCautata = new JScrollPane();
		scrollPane_DisciplinaCautata.setBounds(38, 35, 500, 340);
		MeniuDisciplinaCautata.add(scrollPane_DisciplinaCautata);

		
		scrollPane_DisciplinaCautata.setViewportView(textArea_DisciplinaCautata);

		JButton btnNewButton_1_2_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_2_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuDiscipline");
			}
		});
		btnNewButton_1_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_2_1_1_1.setFocusable(false);
		btnNewButton_1_2_1_1_1.setBounds(10, 413, 85, 30);
		CautareDisciplina.add(btnNewButton_1_2_1_1_1);

		textField_DiscCaut = new JTextField();
		textField_DiscCaut.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_DiscCaut.setColumns(10);
		textField_DiscCaut.setBounds(168, 47, 180, 24);
		CautareDisciplina.add(textField_DiscCaut);

		JLabel lblNumeDisciplin_1_1 = new JLabel("Nume disciplină");
		lblNumeDisciplin_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeDisciplin_1_1.setBounds(44, 44, 114, 30);
		CautareDisciplina.add(lblNumeDisciplin_1_1);

		JButton btnNewButton_1_1_1_3_1 = new JButton("Căutare");
		btnNewButton_1_1_1_3_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String nume = textField_DiscCaut.getText();
				try
				{
					if (!cautareDisciplina(nume))
						JOptionPane.showInternalMessageDialog(null, "Nu am găsit disciplina cu numele " + nume,
								"Eroare căutare disciplină", JOptionPane.WARNING_MESSAGE, null);
					else
					{
						textArea_DisciplinaCautata.setText(informatiiDisiplina(disciplinaCautata));
						cl.show(contentPane, "MeniuDisciplinăCăutată");
					}
				} catch (ExceptieDisciplina e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare disciplină",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_3_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_3_1.setFocusable(false);
		btnNewButton_1_1_1_3_1.setBounds(454, 413, 112, 30);
		CautareDisciplina.add(btnNewButton_1_1_1_3_1);

		JButton btnNewButton_1_2 = new JButton("Înapoi");

		btnNewButton_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_2.setFocusable(false);
		btnNewButton_1_2.setBounds(10, 413, 85, 30);
		MeniuDiscipline.add(btnNewButton_1_2);

		JButton btnAdugareDisciplin = new JButton("Adăugare Disciplină");
		btnAdugareDisciplin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "AdăugareDisciplină");
			}
		});
		btnAdugareDisciplin.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareDisciplin.setFocusable(false);
		btnAdugareDisciplin.setBounds(215, 66, 170, 30);
		MeniuDiscipline.add(btnAdugareDisciplin);

		JButton btnCutareDisciplin = new JButton("Căutare Disciplină");
		btnCutareDisciplin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutareDisciplină");
			}
		});
		btnCutareDisciplin.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCutareDisciplin.setFocusable(false);
		btnCutareDisciplin.setBounds(215, 116, 170, 30);
		MeniuDiscipline.add(btnCutareDisciplin);

		JPanel AdaugareDisciplina = new JPanel();
		contentPane.add(AdaugareDisciplina, "AdăugareDisciplină");
		AdaugareDisciplina.setLayout(null);

		textField_NumeDisc = new JTextField();
		textField_NumeDisc.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_NumeDisc.setColumns(10);
		textField_NumeDisc.setBounds(168, 47, 180, 24);
		AdaugareDisciplina.add(textField_NumeDisc);

		JButton btnNewButton_1_2_1 = new JButton("Înapoi");
		btnNewButton_1_2_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuDiscipline");
			}
		});
		btnNewButton_1_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_2_1.setFocusable(false);
		btnNewButton_1_2_1.setBounds(10, 413, 85, 30);
		AdaugareDisciplina.add(btnNewButton_1_2_1);

		JLabel lblNumeDisciplin = new JLabel("Nume disciplină");
		lblNumeDisciplin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeDisciplin.setBounds(44, 44, 114, 30);
		AdaugareDisciplina.add(lblNumeDisciplin);

		textField_PrezenteMin = new JTextField();
		textField_PrezenteMin.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_PrezenteMin.setColumns(10);
		textField_PrezenteMin.setBounds(168, 87, 180, 24);
		AdaugareDisciplina.add(textField_PrezenteMin);

		JLabel lblPrezeneMinime = new JLabel("Prezențe minime");
		lblPrezeneMinime.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPrezeneMinime.setBounds(44, 84, 114, 30);
		AdaugareDisciplina.add(lblPrezeneMinime);

		textField_NrTeste = new JTextField();
		textField_NrTeste.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_NrTeste.setColumns(10);
		textField_NrTeste.setBounds(168, 127, 180, 24);
		AdaugareDisciplina.add(textField_NrTeste);

		JLabel lblNumrDeTeste = new JLabel("Număr de teste");
		lblNumrDeTeste.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumrDeTeste.setBounds(44, 124, 114, 30);
		AdaugareDisciplina.add(lblNumrDeTeste);

		textField_ProcentTeste = new JTextField();
		textField_ProcentTeste.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_ProcentTeste.setColumns(10);
		textField_ProcentTeste.setBounds(168, 167, 180, 24);
		AdaugareDisciplina.add(textField_ProcentTeste);

		JLabel lblProcentTeste = new JLabel("Procent teste");
		lblProcentTeste.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProcentTeste.setBounds(44, 164, 101, 30);
		AdaugareDisciplina.add(lblProcentTeste);

		textField_ProcentExamen = new JTextField();
		textField_ProcentExamen.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_ProcentExamen.setColumns(10);
		textField_ProcentExamen.setBounds(168, 207, 180, 24);
		AdaugareDisciplina.add(textField_ProcentExamen);

		JLabel lblProcentExamen = new JLabel("Procent examen");
		lblProcentExamen.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProcentExamen.setBounds(44, 204, 114, 30);
		AdaugareDisciplina.add(lblProcentExamen);

		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "Da", "Nu" }));
		comboBox_1.setSelectedIndex(1);
		comboBox_1.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_1.setBounds(168, 247, 45, 21);
		AdaugareDisciplina.add(comboBox_1);

		JLabel lblPuncteBonus = new JLabel("Puncte bonus ?");
		lblPuncteBonus.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPuncteBonus.setBounds(44, 244, 114, 30);
		AdaugareDisciplina.add(lblPuncteBonus);

		List<JTextField> listaInformatiiDisciplinaAdaugare = new ArrayList<JTextField>();
		listaInformatiiDisciplinaAdaugare.add(textField_NumeDisc);
		listaInformatiiDisciplinaAdaugare.add(textField_PrezenteMin);
		listaInformatiiDisciplinaAdaugare.add(textField_NrTeste);
		listaInformatiiDisciplinaAdaugare.add(textField_ProcentTeste);
		listaInformatiiDisciplinaAdaugare.add(textField_ProcentExamen);
		JButton btnNewButton_1_1_1_2 = new JButton("Adăugare");
		btnNewButton_1_1_1_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Disciplina d = new Disciplina();
				try
				{
					d.setareInformatii(listaInformatiiDisciplinaAdaugare, disciplinaUnica, false);
					d.setBonus(comboBox_1.getSelectedIndex() == 1 ? false : true);
					inserareDisciplina(d);
					clearTextFields(listaInformatiiDisciplinaAdaugare);
					comboBox_1.setSelectedIndex(1);
					JOptionPane.showInternalMessageDialog(null, "Disciplină adăugată cu success", "Adăugare disciplină",
							JOptionPane.INFORMATION_MESSAGE, null);

				} catch (ExceptieDisciplina e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare disciplină",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnNewButton_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_2.setFocusable(false);
		btnNewButton_1_1_1_2.setBounds(454, 413, 112, 30);
		AdaugareDisciplina.add(btnNewButton_1_1_1_2);

		JPanel CautarePrezenteStudent = new JPanel();
		CautarePrezenteStudent.setLayout(null);
		contentPane.add(CautarePrezenteStudent, "CăutarePrezențeStudent");

		JButton btnNewButton_1_1_3_2_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_2_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuPrezențe");
			}
		});
		btnNewButton_1_1_3_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_2_1.setFocusable(false);
		btnNewButton_1_1_3_2_1.setBounds(10, 413, 85, 30);
		CautarePrezenteStudent.add(btnNewButton_1_1_3_2_1);

		textField_StringCP = new JTextField();
		textField_StringCP.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_StringCP.setColumns(10);
		textField_StringCP.setBounds(168, 47, 180, 24);
		CautarePrezenteStudent.add(textField_StringCP);

		JComboBox<String> comboBox_CautarePrezenteStudent = new JComboBox<String>();
		comboBox_CautarePrezenteStudent.setModel(new DefaultComboBoxModel(new String[]
		{ "CNP", "Nr.Matricol" }));
		comboBox_CautarePrezenteStudent.setSelectedIndex(0);
		comboBox_CautarePrezenteStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_CautarePrezenteStudent.setBounds(40, 49, 105, 21);
		CautarePrezenteStudent.add(comboBox_CautarePrezenteStudent);

		JButton btnNewButton_1_1_1_3_2_1 = new JButton("Prezențe Student");
		btnNewButton_1_1_1_3_2_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String stringCautat = textField_StringCP.getText();
				String optiuneC = ((String) comboBox_CautarePrezenteStudent.getSelectedItem()).trim();
				boolean cnpO = optiuneC.equals("CNP");
				try
				{
					if (!cautareStudent(stringCautat, cnpO))
						JOptionPane.showInternalMessageDialog(null,
								"Nu am găsit studentul cu " + (cnpO ? "CNP-ul " : "Nr.Matricol ") + stringCautat,
								"Eroare căutare student", JOptionPane.WARNING_MESSAGE, null);
					else
					{
						prezenteTabelStudent(tabelPrezenteStudent, nrMatricolCautat);
						cl.show(contentPane, "PrezențeStudent");
					}
				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare student",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_3_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_3_2_1.setFocusable(false);
		btnNewButton_1_1_1_3_2_1.setBounds(407, 413, 159, 30);
		CautarePrezenteStudent.add(btnNewButton_1_1_1_3_2_1);

		JPanel CautareNoteStudent = new JPanel();
		CautareNoteStudent.setLayout(null);
		contentPane.add(CautareNoteStudent, "CăutareNoteStudent");

		JButton btnNewButton_1_1_3_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuNote");
			}
		});
		btnNewButton_1_1_3_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_2.setFocusable(false);
		btnNewButton_1_1_3_2.setBounds(10, 413, 85, 30);
		CautareNoteStudent.add(btnNewButton_1_1_3_2);

		textField_StringCN = new JTextField();
		textField_StringCN.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_StringCN.setColumns(10);
		textField_StringCN.setBounds(168, 47, 180, 24);
		CautareNoteStudent.add(textField_StringCN);

		JComboBox<String> comboBox_CautareNoteStudent = new JComboBox<String>();
		comboBox_CautareNoteStudent.setModel(new DefaultComboBoxModel(new String[]
		{ "CNP", "Nr.Matricol" }));
		comboBox_CautareNoteStudent.setSelectedIndex(0);
		comboBox_CautareNoteStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_CautareNoteStudent.setBounds(40, 49, 105, 21);
		CautareNoteStudent.add(comboBox_CautareNoteStudent);

		JButton btnNewButton_1_1_1_3_2 = new JButton("Note Student");
		btnNewButton_1_1_1_3_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String stringCautat = textField_StringCN.getText();
				String optiuneC = ((String) comboBox_CautareNoteStudent.getSelectedItem()).trim();
				boolean cnpO = optiuneC.equals("CNP");
				try
				{
					if (!cautareStudent(stringCautat, cnpO))
						JOptionPane.showInternalMessageDialog(null,
								"Nu am găsit studentul cu " + (cnpO ? "CNP-ul " : "Nr.Matricol ") + stringCautat,
								"Eroare căutare student", JOptionPane.WARNING_MESSAGE, null);
					else
					{
						noteTabelStudent(tabelNoteStudent, nrMatricolCautat);
						cl.show(contentPane, "NoteStudent");
					}
				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare student",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_3_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_3_2.setFocusable(false);
		btnNewButton_1_1_1_3_2.setBounds(436, 413, 130, 30);
		CautareNoteStudent.add(btnNewButton_1_1_1_3_2);

		JPanel CautareStudent = new JPanel();
		contentPane.add(CautareStudent, "CăutareStudent");
		CautareStudent.setLayout(null);

		JPanel MeniuStudentCautat = new JPanel();
		contentPane.add(MeniuStudentCautat, "MeniuStudentCăutat");
		MeniuStudentCautat.setLayout(null);

		JPanel ModificareStudentCautat = new JPanel();
		contentPane.add(ModificareStudentCautat, "DatePStudentCăutat");
		ModificareStudentCautat.setLayout(null);

		JButton btnNewButton_1_1_3_1_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuStudentCăutat");
			}
		});
		btnNewButton_1_1_3_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_2.setFocusable(false);
		btnNewButton_1_1_3_1_2.setBounds(10, 413, 85, 30);
		ModificareStudentCautat.add(btnNewButton_1_1_3_1_2);

		JLabel lblNewLabel_1 = new JLabel("CNP");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(57, 44, 37, 30);
		ModificareStudentCautat.add(lblNewLabel_1);

		JLabel lblNume_1 = new JLabel("Nume");
		lblNume_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNume_1.setBounds(57, 84, 52, 30);
		ModificareStudentCautat.add(lblNume_1);

		JLabel lblPreume_1 = new JLabel("Preume");
		lblPreume_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreume_1.setBounds(57, 124, 63, 30);
		ModificareStudentCautat.add(lblPreume_1);

		JLabel lblTelefon_1 = new JLabel("Telefon");
		lblTelefon_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefon_1.setBounds(57, 164, 63, 30);
		ModificareStudentCautat.add(lblTelefon_1);

		JLabel lblEmail_1 = new JLabel("Email");
		lblEmail_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmail_1.setBounds(57, 204, 52, 30);
		ModificareStudentCautat.add(lblEmail_1);

		JLabel lblAdres_1 = new JLabel("Adresă");
		lblAdres_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAdres_1.setBounds(57, 244, 63, 30);
		ModificareStudentCautat.add(lblAdres_1);

		JLabel lblAngajat_1 = new JLabel("Sold an curent");
		lblAngajat_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAngajat_1.setBounds(57, 284, 105, 30);
		ModificareStudentCautat.add(lblAngajat_1);

		textField_Email_SC = new JTextField();
		textField_Email_SC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Email_SC.setColumns(10);
		textField_Email_SC.setBounds(168, 207, 180, 24);
		ModificareStudentCautat.add(textField_Email_SC);

		textField_CNP_SC = new JTextField();
		textField_CNP_SC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_CNP_SC.setColumns(10);
		textField_CNP_SC.setBounds(168, 47, 180, 24);
		ModificareStudentCautat.add(textField_CNP_SC);

		textField_Nume_SC = new JTextField();
		textField_Nume_SC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Nume_SC.setColumns(10);
		textField_Nume_SC.setBounds(168, 87, 180, 24);
		ModificareStudentCautat.add(textField_Nume_SC);

		textField_Prenume_SC = new JTextField();
		textField_Prenume_SC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Prenume_SC.setColumns(10);
		textField_Prenume_SC.setBounds(168, 127, 180, 24);
		ModificareStudentCautat.add(textField_Prenume_SC);

		textField_Telefon_SC = new JTextField();
		textField_Telefon_SC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Telefon_SC.setColumns(10);
		textField_Telefon_SC.setBounds(168, 167, 180, 24);
		ModificareStudentCautat.add(textField_Telefon_SC);

		textField_Adresa_SC = new JTextField();
		textField_Adresa_SC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Adresa_SC.setColumns(10);
		textField_Adresa_SC.setBounds(168, 247, 180, 24);
		ModificareStudentCautat.add(textField_Adresa_SC);

		textField_Sold_SC = new JTextField();
		textField_Sold_SC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_Sold_SC.setColumns(10);
		textField_Sold_SC.setBounds(168, 287, 180, 24);
		ModificareStudentCautat.add(textField_Sold_SC);

		JComboBox<String> comboBox_2 = new JComboBox<String>();
		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "Da", "Nu" }));
		comboBox_2.setSelectedIndex(1);
		comboBox_2.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_2.setBounds(168, 329, 45, 21);
		ModificareStudentCautat.add(comboBox_2);

		JLabel lblAngajat_2_1 = new JLabel("Angajat ?");
		lblAngajat_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAngajat_2_1.setBounds(57, 324, 74, 30);
		ModificareStudentCautat.add(lblAngajat_2_1);

		JButton btnNewButton_1_1_1_4 = new JButton("Salvare Modificări");

		btnNewButton_1_1_1_4.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_4.setFocusable(false);
		btnNewButton_1_1_1_4.setBounds(409, 413, 157, 30);
		ModificareStudentCautat.add(btnNewButton_1_1_1_4);

		JPanel MeniuDisciplineStudent = new JPanel();
		contentPane.add(MeniuDisciplineStudent, "MeniuDisciplineStudent");
		MeniuDisciplineStudent.setLayout(null);

		JPanel AdaugareDisciplineStudent = new JPanel();
		contentPane.add(AdaugareDisciplineStudent, "AdăugareDisciplineStudent");
		AdaugareDisciplineStudent.setLayout(null);

		JPanel StergereDisciplineStudent = new JPanel();
		contentPane.add(StergereDisciplineStudent, "ȘtergereDisciplineStudent");
		StergereDisciplineStudent.setLayout(null);

		JButton btnNewButton_1_1_3_1_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuDisciplineStudent");
			}
		});
		btnNewButton_1_1_3_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_1_1_1.setBounds(10, 413, 85, 30);
		StergereDisciplineStudent.add(btnNewButton_1_1_3_1_1_1_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(68, 54, 111, 170);
		StergereDisciplineStudent.add(scrollPane_1);

		JList<String> list_1 = new JList<String>();
		scrollPane_1.setViewportView(list_1);
		list_1.setFont(new Font("Arial", Font.PLAIN, 15));

		JLabel lblNewLabel_3_1 = new JLabel("Discipline");
		lblNewLabel_3_1.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_1.setColumnHeaderView(lblNewLabel_3_1);

		JButton btnNewButton_1_1_1_2_2_1 = new JButton("Ștergere Discipline");
		btnNewButton_1_1_1_2_2_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				List<String> disciplineSterse = list_1.getSelectedValuesList();
				disciplineSterse.forEach(d ->
				{
					try
					{
						stergereStudentDisciplina(nrMatricolCautat, disciplinePosibileStergereID.get(d));
					} catch (ExceptieDisciplina e1)
					{
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare ștergere disciplină",
								JOptionPane.WARNING_MESSAGE, null);
					}
				});
				JOptionPane.showInternalMessageDialog(null, "Discipline înlăturate cu success", "Înlăturare discipline",
						JOptionPane.INFORMATION_MESSAGE, null);
				cl.show(contentPane, "MeniuDisciplineStudent");
			}
		});
		btnNewButton_1_1_1_2_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_2_2_1.setFocusable(false);
		btnNewButton_1_1_1_2_2_1.setBounds(399, 413, 167, 30);
		StergereDisciplineStudent.add(btnNewButton_1_1_1_2_2_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(68, 60, 112, 170);
		AdaugareDisciplineStudent.add(scrollPane);

		JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setFont(new Font("Arial", Font.PLAIN, 15));

		JLabel lblNewLabel_3 = new JLabel("Discipline");
		scrollPane.setColumnHeaderView(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 15));

		JButton btnNewButton_1_1_3_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuDisciplineStudent");
			}
		});
		btnNewButton_1_1_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_1_1.setBounds(10, 413, 85, 30);
		AdaugareDisciplineStudent.add(btnNewButton_1_1_3_1_1_1);
		JTextArea textArea_StudentCautat = new JTextArea();
		JButton btnNewButton_1_1_1_2_2 = new JButton("Adăugare Discipline");
		btnNewButton_1_1_1_2_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				List<String> disciplineNoi = list.getSelectedValuesList();
				disciplineNoi.forEach(d ->
				{
					try
					{
						inscriereStudentDisciplina(nrMatricolCautat, disciplinePosibileAdaugareID.get(d));
					} catch (ExceptieStudent e1)
					{
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare disciplină",
								JOptionPane.WARNING_MESSAGE, null);
					}
				});
				JOptionPane.showInternalMessageDialog(null, "Discipline adăugate cu success", "Adăugare discipline",
						JOptionPane.INFORMATION_MESSAGE, null);
				cl.show(contentPane, "MeniuDisciplineStudent");
			}
		});
		btnNewButton_1_1_1_2_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_2_2.setFocusable(false);
		btnNewButton_1_1_1_2_2.setBounds(399, 413, 167, 30);
		AdaugareDisciplineStudent.add(btnNewButton_1_1_1_2_2);

		JButton btnNewButton_1_1_3_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				textArea_StudentCautat.setText(informatiiStudent(studentCautat));
				cl.show(contentPane, "MeniuStudentCăutat");
			}
		});
		btnNewButton_1_1_3_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_1.setBounds(10, 413, 85, 30);
		MeniuDisciplineStudent.add(btnNewButton_1_1_3_1_1);

		JButton btnAdugareDiscipline = new JButton("Adăugare Discipline");
		btnAdugareDiscipline.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					actualizareDisciplinePosibileAdaugareStudent();
					list.setModel(disciplinePosibileAdaugareDLM);
					list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					cl.show(contentPane, "AdăugareDisciplineStudent");

				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare discipline",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnAdugareDiscipline.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareDiscipline.setFocusable(false);
		btnAdugareDiscipline.setBounds(215, 66, 170, 30);
		MeniuDisciplineStudent.add(btnAdugareDiscipline);

		JButton btntergereDiscipline = new JButton("Ștergere Discipline");
		btntergereDiscipline.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					actualizareDisciplinePosibileStergereStudent();
					list_1.setModel(disciplinePosibileStergereDLM);
					list_1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					cl.show(contentPane, "ȘtergereDisciplineStudent");

				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare discipline",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btntergereDiscipline.setFont(new Font("Arial", Font.PLAIN, 15));
		btntergereDiscipline.setFocusable(false);
		btntergereDiscipline.setBounds(215, 116, 170, 30);
		MeniuDisciplineStudent.add(btntergereDiscipline);

		JButton btnNewButton_1_1_3_1 = new JButton("Înapoi");

		btnNewButton_1_1_3_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1.setFocusable(false);
		btnNewButton_1_1_3_1.setBounds(10, 413, 85, 30);
		MeniuStudentCautat.add(btnNewButton_1_1_3_1);

		JButton btnNewButton_1_1_1_1 = new JButton("Ștergere");
		btnNewButton_1_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					stergereStudent(nrMatricolCautat);
					cl.show(contentPane, "CăutareStudent");
					JOptionPane.showInternalMessageDialog(null, "Student șters cu success", "Ștergere student",
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare ștergere student",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnNewButton_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_1_1.setBounds(475, 413, 91, 30);
		MeniuStudentCautat.add(btnNewButton_1_1_1_1);

		JButton btnNewButton_1_1_1_1_1 = new JButton("Discipline");
		btnNewButton_1_1_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuDisciplineStudent");
			}
		});
		btnNewButton_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_1_1_1.setBounds(366, 413, 99, 30);
		MeniuStudentCautat.add(btnNewButton_1_1_1_1_1);

		JButton btnNewButton_1_1_1_1_1_1 = new JButton("Date personale");

		btnNewButton_1_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_1_1_1_1.setBounds(221, 413, 135, 30);
		MeniuStudentCautat.add(btnNewButton_1_1_1_1_1_1);

		JScrollPane scrollPane_StudentCautat = new JScrollPane();
		scrollPane_StudentCautat.setBounds(38, 35, 500, 340);
		MeniuStudentCautat.add(scrollPane_StudentCautat);


		textArea_StudentCautat.setFont(new Font("Monospaced", Font.PLAIN, 14));
		textArea_StudentCautat.setEditable(false);
		scrollPane_StudentCautat.setViewportView(textArea_StudentCautat);

		JButton btnNewButton_1_1_3 = new JButton("Înapoi");

		btnNewButton_1_1_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3.setFocusable(false);
		btnNewButton_1_1_3.setBounds(10, 413, 85, 30);
		CautareStudent.add(btnNewButton_1_1_3);

		textField_StringC = new JTextField();
		textField_StringC.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_StringC.setColumns(10);
		textField_StringC.setBounds(168, 47, 180, 24);
		CautareStudent.add(textField_StringC);

		JComboBox<String> comboBox_CautareStudent = new JComboBox<String>();
		comboBox_CautareStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_CautareStudent.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "CNP", "Nr. Matricol" }));
		comboBox_CautareStudent.setSelectedIndex(0);
		comboBox_CautareStudent.setBounds(40, 49, 105, 21);
		CautareStudent.add(comboBox_CautareStudent);

		JButton btnNewButton_1_1_1_3 = new JButton("Căutare");
		btnNewButton_1_1_1_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String stringCautat = textField_StringC.getText();
				String optiuneC = ((String) comboBox_CautareStudent.getSelectedItem()).trim();
				boolean cnpO = optiuneC.equals("CNP");
				try
				{
					if (!cautareStudent(stringCautat, cnpO))
						JOptionPane.showInternalMessageDialog(null,
								"Nu am găsit studentul cu " + (cnpO ? "CNP-ul " : "Nr.Matricol ") + stringCautat,
								"Eroare căutare student", JOptionPane.WARNING_MESSAGE, null);
					else
					{
						textArea_StudentCautat.setText(informatiiStudent(studentCautat));
						cl.show(contentPane, "MeniuStudentCăutat");
					}
				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare student",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_3.setFocusable(false);
		btnNewButton_1_1_1_3.setBounds(454, 413, 112, 30);
		CautareStudent.add(btnNewButton_1_1_1_3);

		btnNewButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuStudenți");
			}

		});

		btnNewButton_1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuPrincipal");
			}

		});

		btnAdugareStudent.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "AdăugareStudent");

			}
		});

		btnNewButton_1_1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuStudenți");

			}
		});
		btnMeniuDiscipline.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuDiscipline");
			}
		});
		btnNewButton_1_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuPrincipal");
			}
		});
		btnNewButton_1_1_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "MeniuStudenți");
			}
		});
		btnDisciplineStudent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutareStudent");
			}
		});
		btnNewButton_1_1_3_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cl.show(contentPane, "CăutareStudent");
			}
		});

		List<JTextField> listaInformatiiStudentModificare = new ArrayList<JTextField>();
		listaInformatiiStudentModificare.add(textField_CNP_SC);
		listaInformatiiStudentModificare.add(textField_Nume_SC);
		listaInformatiiStudentModificare.add(textField_Prenume_SC);
		listaInformatiiStudentModificare.add(textField_Telefon_SC);
		listaInformatiiStudentModificare.add(textField_Email_SC);
		listaInformatiiStudentModificare.add(textField_Adresa_SC);
		listaInformatiiStudentModificare.add(textField_Sold_SC);
		btnNewButton_1_1_1_1_1_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				textField_CNP_SC.setText(studentCautat.getCnp());
				textField_Nume_SC.setText(studentCautat.getNume());
				textField_Prenume_SC.setText(studentCautat.getPrenume());
				textField_Telefon_SC.setText(studentCautat.getNrTelefon());
				textField_Email_SC.setText(studentCautat.getEmail());
				textField_Adresa_SC.setText(studentCautat.getAdresa());
				textField_Sold_SC.setText(String.valueOf(studentCautat.getSoldAnCurent()));
				comboBox_2.setSelectedIndex(studentCautat.isAngajat() ? 0 : 1);
				cl.show(contentPane, "DatePStudentCăutat");
			}
		});
		btnNewButton_1_1_1_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					studentCautat.setareInformatii(listaInformatiiStudentModificare, CNPUnic,
							Objects.equals(listaInformatiiStudentModificare.get(0).getText(), cnpCautat));
					studentCautat.setAngajat(comboBox_2.getSelectedIndex() == 0 ? true : false);
					modificareStudentCautat();
					textArea_StudentCautat.setText(informatiiStudent(studentCautat));
					cl.show(contentPane, "MeniuStudentCăutat");
					clearTextFields(listaInformatiiStudentModificare);
					comboBox_2.setSelectedIndex(1);
					JOptionPane.showInternalMessageDialog(null, "Student modificat cu success", "Modificare student",
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ExceptieStudent e1)
				{
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare modificare date student",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
	}

	public static void main(String[] args)
	{
		try
		{
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		try
		{
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "root");
			String sqlinss = "INSERT INTO STUDENTI" + " VALUES (null,?,?,?,?,?,?,?,?)";
			inserareStudent = con.prepareStatement(sqlinss);
			String sqlselcs = "SELECT * FROM STUDENTI WHERE CNP = TRIM(?) ";
			String sqlselnrms = "SELECT * FROM STUDENTI WHERE NR_MATRICOL = ?";
			cautareStudentNRM = con.prepareStatement(sqlselnrms);
			CNPUnic = con.prepareStatement(sqlselcs);
			String sqldelst = "DELETE FROM STUDENTI WHERE NR_MATRICOL = ?";
			stergereStudent = con.prepareStatement(sqldelst);
			String sqlinsd = "INSERT INTO DISCIPLINE" + " VALUES (null,?,?,null,?,?,?,?)";
			inserareDisciplina = con.prepareStatement(sqlinsd);
			String sqlselnd = "SELECT * FROM DISCIPLINE WHERE NUME_DISCIPLINA = UPPER(TRIM(?))";
			disciplinaUnica = con.prepareStatement(sqlselnd);
			String sqldeld = "DELETE FROM DISCIPLINE WHERE NUME_DISCIPLINA = UPPER(TRIM(?))";
			stergereDisciplina = con.prepareStatement(sqldeld);
			cautareStudentCNP = con.prepareStatement(sqlselcs);
			String sqlseldiscposa = "SELECT ID, NUME_DISCIPLINA " + "FROM DISCIPLINE " + "WHERE ID NOT IN " + "("
					+ "SELECT ID_D " + "FROM DISCIPLINE_STUDENTI " + "WHERE NR_MATRICOL = ?)";
			disciplinePosibileAdaugareStudent = con.prepareStatement(sqlseldiscposa);
			String sqlseldiscposs = "SELECT ID_D,NUME_DISCIPLINA FROM DISCIPLINE_STUDENTI_VIEW WHERE NR_MATRICOL = ?";
			disciplinePosibileStergereStudent = con.prepareStatement(sqlseldiscposs);
			String sqlinscrs = "INSERT INTO DISCIPLINE_STUDENTI VALUES(?,?)";
			inscriereStudentDisciplina = con.prepareStatement(sqlinscrs);
			String sqldelds = "DELETE FROM DISCIPLINE_STUDENTI WHERE NR_MATRICOL = ? AND ID_D = ?";
			stergereStudentDisciplina = con.prepareStatement(sqldelds);
			String sqlupds = "UPDATE STUDENTI SET CNP = ?, NUME = ?, PRENUME = ?, NRTEL = ?, EMAIL = ?, ANGAJAT = ?, SOLD = ?, ADRESA = ? "
					+ "WHERE NR_MATRICOL = ?";
			modificareStudent = con.prepareStatement(sqlupds);
			cautareDisciplina = con.prepareStatement(sqlselnd);
			String sqlupdd = "UPDATE DISCIPLINE SET NUME_DISCIPLINA = ?, NR_TESTE = ?, PROCENT_TESTE = ?, PROCENT_EXAMEN = ?, BONUS = ?, PREZENTE_MINIME_NANG = ?"
					+ " WHERE NUME_DISCIPLINA = TRIM(UPPER(?))";
			modificareDisciplina = con.prepareStatement(sqlupdd);
			String sqlancr = "SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL";
			dataCurenta = con.prepareStatement(sqlancr);
			String sqlselstdisc = "SELECT COUNT(*) FROM DISCIPLINE_STUDENTI WHERE NR_MATRICOL = ? AND ID_D = ?";
			studentInscrisDisciplina = con.prepareStatement(sqlselstdisc);
			String sqlinsrprez = "INSERT INTO PREZENTE VALUES(null,?,?,?)";
			inserarePrezenta = con.prepareStatement(sqlinsrprez);
			String sqlselprezsdc = "SELECT COUNT(*) FROM PREZENTE WHERE NR_MATRICOL = ? AND ID_D = ?";
			numarPrezenteStudentDisciplina = con.prepareStatement(sqlselprezsdc);
			String sqlseltsd = "SELECT COUNT(*) FROM CATALOG_UNIVERSITATE WHERE NR_MATRICOL = ? AND ID_D = ? AND TIP = 'T'";
			numarTesteStudentDisciplina = con.prepareStatement(sqlseltsd);
			String sqlselntd = "SELECT NR_TESTE FROM DISCIPLINE WHERE ID = ?";
			numarTesteDisciplina = con.prepareStatement(sqlselntd);
			String sqlselbd = "SELECT BONUS FROM DISCIPLINE WHERE ID = ?";
			bonusDisciplina = con.prepareStatement(sqlselbd);
			String sqlselesd = "SELECT COUNT(*) FROM CATALOG_UNIVERSITATE WHERE NR_MATRICOL = ? AND ID_D = ? AND TIP = 'E'";
			examenStudentDisciplina = con.prepareStatement(sqlselesd);
			String sqlinsnsd = "INSERT INTO CATALOG_UNIVERSITATE VALUES (null,?,?,?,?,?)";
			inserareNota = con.prepareStatement(sqlinsnsd);
			String sqlselns = "SELECT ID_NOTA, DATA, NUME_DISCIPLINA, PUNCTAJ, TIP FROM NOTE_VIEW WHERE NR_MATRICOL = ?";
			noteStudent = con.prepareStatement(sqlselns);
			String sqlselps = "SELECT ID_PREZ, DATA, NUME_DISCIPLINA FROM PREZENTE_VIEW WHERE NR_MATRICOL = ?";
			prezenteStudent = con.prepareStatement(sqlselps);
			String sqldelp = "DELETE FROM PREZENTE WHERE ID_PREZ = ?";
			stergerePrezenta = con.prepareStatement(sqldelp);
			String sqldeln = "DELETE FROM CATALOG_UNIVERSITATE WHERE ID_NOTA = ?";
			stergereNota = con.prepareStatement(sqldeln);
			String sqlselpsd = "SELECT COUNT(*) FROM PREZENTE WHERE DATA = ? AND NR_MATRICOL = ? AND ID_D = ?";
			prezentaUnica = con.prepareStatement(sqlselpsd);
			String sqlselnsd = "SELECT COUNT (*) FROM CATALOG_UNIVERSITATE WHERE DATA = ? AND NR_MATRICOL = ? AND ID_D = ? AND TIP = ?";
			notaUnica = con.prepareStatement(sqlselnsd);
			String sqlseldsv = "SELECT NUME_DISCIPLINA, PREZENTE_MINIME, PREZENTE_STUDENT, MEDIA FROM DISCIPLINE_STUDENTI_VIEW WHERE NR_MATRICOL = ?";
			disciplineStudent = con.prepareStatement(sqlseldsv);
		} catch (SQLException e2)
		{
			e2.printStackTrace();
			System.exit(1);
		}

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1)
		{
			e1.printStackTrace();
			JOptionPane.showInternalMessageDialog(null, "Nu s-a putut seta tema sistemului", "Eroare la setare temă",
					JOptionPane.WARNING_MESSAGE, null);
		}
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void inserareStudent(Student s) throws ExceptieStudent
	{
		try
		{
			inserareStudent.setString(1, s.getCnp());
			inserareStudent.setString(2, s.getNume());
			inserareStudent.setString(3, s.getPrenume());
			inserareStudent.setString(4, s.getNrTelefon());
			inserareStudent.setString(5, s.getEmail());
			inserareStudent.setString(6, s.getAdresa());
			inserareStudent.setDouble(7, s.getSoldAnCurent());
			inserareStudent.setInt(8, s.isAngajat() == true ? 1 : 0);
			inserareStudent.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la inserarea în baza de date!");
		}
	}

	private void modificareStudentCautat() throws ExceptieStudent
	{
		try
		{
			modificareStudent.setString(1, studentCautat.getCnp());
			modificareStudent.setString(2, studentCautat.getNume());
			modificareStudent.setString(3, studentCautat.getPrenume());
			modificareStudent.setString(4, studentCautat.getNrTelefon());
			modificareStudent.setString(5, studentCautat.getEmail());
			modificareStudent.setInt(6, studentCautat.isAngajat() ? 1 : 0);
			modificareStudent.setDouble(7, studentCautat.getSoldAnCurent());
			modificareStudent.setString(8, studentCautat.getAdresa());
			modificareStudent.setInt(9, nrMatricolCautat);

			modificareStudent.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la modificarea în baza de date!");
		}
	}

	private void modificareDisciplinaCautata() throws ExceptieDisciplina
	{
		try
		{
			modificareDisciplina.setString(1, disciplinaCautata.getNume());
			modificareDisciplina.setInt(2, disciplinaCautata.getNrTeste());
			modificareDisciplina.setInt(3, disciplinaCautata.getProcentTeste());
			modificareDisciplina.setInt(4, disciplinaCautata.getProcentExamen());
			modificareDisciplina.setInt(5, disciplinaCautata.isBonus() ? 1 : 0);
			modificareDisciplina.setInt(6, disciplinaCautata.getPrezente());
			modificareDisciplina.setString(7, numeDCautat);

			modificareDisciplina.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la modificarea în baza de date!");
		}
	}

	private void stergereStudent(int nrm) throws ExceptieStudent
	{
		try
		{
			stergereStudent.setInt(1, nrm);
			stergereStudent.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la ștergerea din baza de date!");
		}
	}

	private void stergereDisciplina(String nume) throws ExceptieDisciplina
	{
		try
		{
			stergereDisciplina.setString(1, nume);
			stergereDisciplina.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la ștergerea din baza de date!");
		}
	}

	private void inserareDisciplina(Disciplina d) throws ExceptieDisciplina
	{
		try
		{
			inserareDisciplina.setString(1, d.getNume());
			inserareDisciplina.setInt(2, d.getPrezente());
			inserareDisciplina.setInt(3, d.getNrTeste());
			inserareDisciplina.setInt(4, d.getProcentTeste());
			inserareDisciplina.setInt(5, d.getProcentExamen());
			inserareDisciplina.setInt(6, d.isBonus() == true ? 1 : 0);

			inserareDisciplina.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la inserarea în baza de date!");
		}
	}

	private boolean cautareStudent(String sc, boolean cnp) throws ExceptieStudent
	{
		sc = sc.trim();
		try
		{
			if (cnp)
			{
				cautareStudentCNP.setString(1, sc);
				rs = cautareStudentCNP.executeQuery();
			} else
			{
				cautareStudentNRM.setInt(1, Integer.parseInt(sc));
				rs = cautareStudentNRM.executeQuery();
			}
			if (!rs.isBeforeFirst())
				return false;
			rs.next();
			nrMatricolCautat = rs.getInt(1);
			cnpCautat = rs.getString(2);

			resultSetToStudent(studentCautat, rs);
			return true;

		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		} catch (NumberFormatException | NullPointerException e)
		{
			throw new ExceptieStudent("Nr.Matricol este invalid!");
		}
	}

	private boolean cautareDisciplina(String nume) throws ExceptieDisciplina
	{
		try
		{
			cautareDisciplina.setString(1, nume);
			rs = cautareDisciplina.executeQuery();
			if (!rs.isBeforeFirst())
				return false;
			rs.next();
			idDCautat = rs.getInt(1);
			numeDCautat = rs.getString(2).toUpperCase().trim();
			resultSetToDisciplina(disciplinaCautata, rs);
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la căutarea în baza de date!");
		}
	}

	private void actualizareDisciplinePosibileAdaugareStudent() throws ExceptieStudent
	{
		try
		{
			disciplinePosibileAdaugareDLM = new DefaultListModel<String>();
			disciplinePosibileAdaugareStudent.setInt(1, nrMatricolCautat);
			rs = disciplinePosibileAdaugareStudent.executeQuery();
			disciplinePosibileAdaugareID.clear();
			String nume;
			int id;
			while (rs.next())
			{
				id = rs.getInt(1);
				nume = rs.getString(2);
				disciplinePosibileAdaugareDLM.addElement(nume);
				disciplinePosibileAdaugareID.put(nume, id);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		}
	}

	private void actualizareDisciplinePosibileStergereStudent() throws ExceptieStudent
	{
		try
		{
			disciplinePosibileStergereDLM = new DefaultListModel<String>();
			disciplinePosibileStergereStudent.setInt(1, nrMatricolCautat);
			rs = disciplinePosibileStergereStudent.executeQuery();
			disciplinePosibileStergereID.clear();
			String nume;
			int id;
			while (rs.next())
			{
				id = rs.getInt(1);
				nume = rs.getString(2);
				disciplinePosibileStergereDLM.addElement(nume);
				disciplinePosibileStergereID.put(nume, id);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		}
	}

	private void inscriereStudentDisciplina(int nrm, int idd) throws ExceptieStudent
	{
		try
		{
			inscriereStudentDisciplina.setInt(1, nrm);
			inscriereStudentDisciplina.setInt(2, idd);
			inscriereStudentDisciplina.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la inserarea în baza de date!");
		}
	}

	private void stergereStudentDisciplina(int nrm, int idd) throws ExceptieDisciplina
	{
		try
		{
			stergereStudentDisciplina.setInt(1, nrm);
			stergereStudentDisciplina.setInt(2, idd);
			stergereStudentDisciplina.execute();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la ștergerea din baza de date!");
		}
	}

	private void resultSetToStudent(Student s, ResultSet rs)
	{
		try
		{
			s.setNrMatricol(rs.getInt(1));
			s.setCNP(rs.getString(2), CNPUnic, true);
			s.setNume(rs.getString(3));
			s.setPrenume(rs.getString(4));
			s.setNrTelefon(rs.getString(5));
			s.setEmail(rs.getString(6));
			s.setAdresa(rs.getString(7));
			s.setSold(String.valueOf(rs.getDouble(8)));
			s.setAngajat(rs.getInt(9) == 0 ? false : true);

		} catch (SQLException | ExceptieStudent e)
		{
			e.printStackTrace();
		}
	}

	private void resultSetToDisciplina(Disciplina d, ResultSet rs)
	{
		try
		{

			d.setNume(rs.getString(2), disciplinaUnica, true);
			d.setNrTeste(String.valueOf(rs.getInt(5)));
			d.setProcentTeste(String.valueOf(rs.getInt(6)));
			d.setProcentExamen(String.valueOf(rs.getInt(7)));
			d.setBonus(rs.getInt(8) == 0 ? false : true);
			d.setPrezente(String.valueOf(rs.getInt(3)));
		} catch (SQLException | ExceptieDisciplina e)
		{

			e.printStackTrace();
		}
	}

	private void clearTextFields(List<JTextField> ltf)
	{
		ltf.forEach(tf ->
		{
			tf.setText("");
		});
	}

	private DefaultComboBoxModel<Integer> dataCurenta()
	{
		DefaultComboBoxModel<Integer> listaAni = new DefaultComboBoxModel<Integer>();
		try
		{
			rs = dataCurenta.executeQuery();
			rs.next();
			String[] data = rs.getString(1).split("-");
			int an = Integer.parseInt(data[0]);
			int luna = Integer.parseInt(data[1]);
			int zi = Integer.parseInt(data[2]);
			dataSistem = LocalDate.of(an, luna, zi);
			for (int i = an; i >= 1980; i--)
				listaAni.addElement(i);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaAni;
	}

	private DefaultComboBoxModel<Integer> actualizareZile(int y, int m)
	{
		DefaultComboBoxModel<Integer> zile = new DefaultComboBoxModel<Integer>();
		int ultimaZi = YearMonth.of(y, m).lengthOfMonth();
		for (int i = 1; i <= ultimaZi; i++)
			zile.addElement(i);
		return zile;
	}

	private boolean studentInscrisDisciplina(int nrm, int idd)
	{
		try
		{
			studentInscrisDisciplina.setInt(1, nrm);
			studentInscrisDisciplina.setInt(2, idd);
			rs = studentInscrisDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	private void inserarePrezenta(LocalDate data, int nrm, int idd) throws Exception
	{
		try
		{
			if (data.isAfter(dataSistem))
				throw new Exception("Data prezenței nu poate fi din viitor!");
			prezentaUnica.setObject(1, data);
			prezentaUnica.setInt(2, nrm);
			prezentaUnica.setInt(3, idd);
			rs = prezentaUnica.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0)
				throw new Exception("Studentul are deja o prezență la disciplina respectivă în acea dată!");
			inserarePrezenta.setObject(1, data);
			inserarePrezenta.setInt(2, nrm);
			inserarePrezenta.setInt(3, idd);

			inserarePrezenta.execute();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean areExamen(int nrm, int idd)
	{
		try
		{
			examenStudentDisciplina.setInt(1, nrm);
			examenStudentDisciplina.setInt(2, idd);
			rs = examenStudentDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private boolean bonusDisciplina(int idd)
	{
		try
		{
			bonusDisciplina.setInt(1, idd);
			rs = bonusDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) == 1 ? true : false;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean maximTeste(int nrm, int idd)
	{
		try
		{
			int nrTeste, nrMaxTeste;
			numarTesteStudentDisciplina.setInt(1, nrm);
			numarTesteStudentDisciplina.setInt(2, idd);
			rs = numarTesteStudentDisciplina.executeQuery();
			rs.next();
			nrTeste = rs.getInt(1);
			numarTesteDisciplina.setInt(1, idd);
			rs = numarTesteDisciplina.executeQuery();
			rs.next();
			nrMaxTeste = rs.getInt(1);
			return nrTeste == nrMaxTeste;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private boolean maximPrezente(int nrm, int idd)
	{
		try
		{
			numarPrezenteStudentDisciplina.setInt(1, nrm);
			numarPrezenteStudentDisciplina.setInt(2, idd);

			rs = numarPrezenteStudentDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) >= 14;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void inserareNota(LocalDate data, int nrm, int idd, float punctaj, String tip) throws Exception
	{
		try
		{
			if (data.isAfter(dataSistem))
				throw new Exception("Data prezenței nu poate fi din viitor!");

			notaUnica.setObject(1, data);
			notaUnica.setInt(2, nrm);
			notaUnica.setInt(3, idd);
			notaUnica.setString(4, tip);
			rs = notaUnica.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0)
				throw new Exception("Studentul are deja o astfel de notă la disciplina respectivă în acea dată!");

			inserareNota.setObject(1, data);
			inserareNota.setInt(2, nrm);
			inserareNota.setInt(3, idd);
			inserareNota.setDouble(4, punctaj);
			inserareNota.setString(5, tip);
			inserareNota.execute();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void stergerePrezenta(int idp)
	{
		try
		{
			stergerePrezenta.setInt(1, idp);
			stergerePrezenta.execute();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void stergereNota(int idn)
	{
		try
		{
			stergereNota.setInt(1, idn);
			stergereNota.execute();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
