import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
import java.util.Scanner;

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
import javax.swing.JPasswordField;

public class Main extends JFrame {

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
	private DefaultListModel<String> disciplinePosibileAtribuireDLM = new DefaultListModel<String>();
	private DefaultListModel<String> profesoriDisciplinePosibileAdaugareDLM = new DefaultListModel<String>();
	private DefaultListModel<String> disciplinePosibileStergereDLM = new DefaultListModel<String>();
	private DefaultListModel<String> disciplinePredateProfesorSelectatDLM = new DefaultListModel<String>();
	private Map<String, Integer> disciplinePosibileAdaugareID = new HashMap<String, Integer>();
	private Map<String, Integer> disciplinePosibileAtribuireID = new HashMap<String, Integer>();
	private Map<String, Integer> profesoriDisciplinePosibileAdaugareID = new HashMap<String, Integer>();
	private Map<String, Integer> disciplinePosibileStergereID = new HashMap<String, Integer>();
	private Map<String, Integer> disciplinePredateProfesorSelectatID = new HashMap<String, Integer>();

	private static PreparedStatement parolaProfAdaugat;
	private static PreparedStatement parolaStudentAdaugat;
	private static PreparedStatement parolaAdministatieOk;
	private static PreparedStatement parolaStudentCautat;
	private static PreparedStatement parolaProfesorSelectat;
	private static PreparedStatement disciplineInscriseStudent;
	private static PreparedStatement modificareParolaSecretariat;
	private static PreparedStatement modificareParolaStudent;
	private static PreparedStatement modificareParolaProfesor;
	private static PreparedStatement stergereNota;
	private static PreparedStatement stergerePrezenta;
	private static PreparedStatement dataCurenta;
	private static PreparedStatement inserareStudent;
	private static PreparedStatement inserareProfesor;
	private static PreparedStatement CNPUnicStudent;
	private static PreparedStatement CNPUnicProfesor;
	private static PreparedStatement disciplinaUnica;
	private static PreparedStatement stergereStudent;
	private static PreparedStatement stergereProfesor;
	private static PreparedStatement modificareStudent;
	private static PreparedStatement modificareProfesor;
	private static PreparedStatement modificareDisciplina;
	private static PreparedStatement inserareDisciplina;
	private static PreparedStatement stergereDisciplina;
	private static PreparedStatement cautareProfesorEmail;
	private static PreparedStatement cautareStudentCNP;
	private static PreparedStatement cautareStudentEMAIL;
	private static PreparedStatement cautareNrMatricolStudent;
	private static PreparedStatement cautareStudentNRM;
	private static PreparedStatement cautareDisciplina;
	private static PreparedStatement cautareIdProfesor;
	private static PreparedStatement cautareProfesorCNP;
	private static PreparedStatement cautareProfesorID;
	private static PreparedStatement disciplinePosibileAdaugareStudent;
	private static PreparedStatement disciplinePosibileAtribuireProfesor;
	private static PreparedStatement profesoriDisciplinePosibileAdaugareStudent;
	private static PreparedStatement disciplinePosibileStergereStudent;
	private static PreparedStatement disciplinePredateProfesorSelectat;
	private static PreparedStatement inscriereStudentDisciplina;
	private static PreparedStatement atribuireProfesorDisciplina;
	private static PreparedStatement stergereStudentDisciplina;
	private static PreparedStatement stergereDisciplinaProfesor;
	private static PreparedStatement studentInscrisDisciplina;
	private static PreparedStatement inserarePrezenta;
	private static PreparedStatement numarPrezenteStudentDisciplina;
	private static PreparedStatement numarTesteStudentDisciplina;
	private static PreparedStatement numarTesteDisciplina;
	private static PreparedStatement bonusDisciplina;
	private static PreparedStatement examenStudentDisciplina;
	private static PreparedStatement inserareNota;
	private static PreparedStatement disciplineProfesor;
	private static PreparedStatement studentiDisciplineProfesor;
	private static PreparedStatement noteStudent;
	private static PreparedStatement noteStudentGeneral;
	private static PreparedStatement prezenteStudent;
	private static PreparedStatement prezenteStudentDisciplinaProfesor;
	private static PreparedStatement prezentaUnica;
	private static PreparedStatement notaUnica;
	private static PreparedStatement disciplineStudent;
	private static PreparedStatement disciplinaStudentProfesor;
	private static PreparedStatement listaProfesori;

	private static int idProfesorCautat;
	private static int nrMatricolCautat;
	private static String cnpCautat;
	private static String numeDCautat;
	private static int idDCautat;
	private static Student studentCautat = new Student();
	private static Utilizator profesorCautat = new Utilizator();
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
	private JTextField textFieldEmailStudentLogin;
	private JPasswordField passwordFieldStudent;
	private JPasswordField passwordFieldParolaNouaConf;
	private JPasswordField passwordFieldParolaNoua;
	private JTextField EmailStudent;
	private JTextField CNPStudent;
	private JTextField NumeStudent;
	private JTextField PrenumeStudent;
	private JTextField TelefonStudent;
	private JTextField AdresaStudent;
	private JTextField SoldStudent;
	private JTable tableNoteStudent;
	private JTable tablePrezenteStudent;
	private JTextField textFieldEmailProfesorLogin;
	private JPasswordField passwordFieldProfesor;
	private JPasswordField passwordFieldParolaNouaProfesor;
	private JPasswordField passwordFieldParolaNouaProfesorConf;
	private JTextField CNPProfesor;
	private JTextField NumeProfesor;
	private JTextField PrenumeProfesor;
	private JTextField TelefonProfesor;
	private JTextField EmailProfesor;
	private JTextField AdresaProfesor;
	private JTable tableDisciplineProfesor;
	private JTextField NumeDisciplinaProf;
	private JTextField PrezenteDisciplinaProf;
	private JTextField NrTesteDisciplinaProf;
	private JTextField ProcentTesteDisciplinaProf;
	private JTextField ProcentExamenDisciplinaProf;
	private JTable tableStudentiDisciplinaProfesor;
	private JTextField NotaStudentDisciplinaProfesor;
	private JTable tableNoteStudentDisciplinaProfesor;
	private JTable tablePrezenteStudentDisciplinaProfesor;
	private JPasswordField passwordFieldSecretara;
	private JTextField textFieldParolaStudentCautat;
	private JPasswordField passwordFieldParolaNouaSecretariat;
	private JPasswordField passwordFieldConfirmareParolaNouaSecretariat;
	private JTextField textFieldCNPProfesorNou;
	private JTextField textFieldNumeProfesorNou;
	private JTextField textFieldPreumeProfesorNou;
	private JTextField textFieldTelefonProfesorNou;
	private JTextField textFieldEmailProfesorNou;
	private JTextField textFieldAdresaProfesorNou;
	private JTextArea parolaGenerata = new JTextArea();
	private JTable tableSelectareProfesori;
	private JTextField textFieldCNPProfesorSelectat;
	private JTextField textFieldNumeProfesorSelectat;
	private JTextField textFieldPrenumeProfesorSelectat;
	private JTextField textFieldTelefonProfesorSelectat;
	private JTextField textFieldEmailProfesorSelectat;
	private JTextField textFieldAdresaProfesorSelectat;
	private JTextField textFieldParolaProfesorSelectat;

	private String informatiiStudentDisciplinaProfesor() {
		StringBuilder sb = new StringBuilder();
		try {
			disciplinaStudentProfesor.setInt(1, nrMatricolCautat);
			disciplinaStudentProfesor.setInt(2, idDCautat);
			disciplinaStudentProfesor.setInt(3, idProfesorCautat);
			ResultSet student = disciplinaStudentProfesor.executeQuery();
			sb.append("----------------------Situație----------------------");
			while (student.next()) {
				sb.append("\n******************************************************");
				sb.append("\nNume student : " + student.getString(1) + " " + student.getString(2)
						+ "\nPrezențe minime necesare : " + student.getInt(3) + "\nPrezențe student : "
						+ student.getInt(4) + "\nMedia studentului : " + student.getInt(5));
				sb.append("\n##########Contact Student##########");
				sb.append("\nEmail : " + student.getString(6) + "\nTelefon : " + student.getString(7));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			sb.append("Eroare la căutare discipline!");
		}
		return sb.toString();
	}

	private String informatiiStudent(Student s) {
		StringBuilder sb = new StringBuilder();
		try {
			disciplineStudent.setInt(1, s.getID());
			ResultSet discipline = disciplineStudent.executeQuery();
			sb.append("----------------------Discipline----------------------");
			while (discipline.next()) {
				sb.append("\n******************************************************");
				sb.append("\nNume disciplină : " + discipline.getString(1) + "\nPrezențe minime necesare : "
						+ discipline.getInt(2) + "\nPrezențe student : " + discipline.getInt(3)
						+ "\nMedia studentului : " + discipline.getInt(4));
				sb.append("\n##########Contact Profesor##########");
				sb.append("\nProfesor : " + discipline.getString(5) + " " + discipline.getString(6) + "\nEmail : "
						+ discipline.getString(7) + "\nTelefon : " + discipline.getString(8));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			sb.append("Eroare la căutare discipline!");
		}
		return sb.toString();
	}

	private String informatiiDisiplina(Disciplina d) {
		StringBuilder sb = new StringBuilder();
		sb.append("######################################################\nNume disciplină : " + d.getNume()
				+ "\nPrezențe minime : " + d.getPrezente() + "\nNumăr teste : " + d.getNrTeste() + "\nProcent teste : "
				+ d.getProcentTeste() + "\nProcent examen : " + d.getProcentExamen() + "\nPuncte bonus : "
				+ (d.isBonus() ? "Da" : "Nu"));
		return sb.toString();
	}

	private void noteTabelStudent(JTable table) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { LocalDate.class, String.class, Float.class, TipNota.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("Data Notei");
		dfm.addColumn("Nume Disciplină");
		dfm.addColumn("Nota");
		dfm.addColumn("Tip");
		dfm.addColumn("Profesor");
		try {
			noteStudentGeneral.setInt(1, nrMatricolCautat);
			ResultSet rs = noteStudentGeneral.executeQuery();
			String tn;
			while (rs.next()) {
				tn = rs.getString(4);
				dfm.addRow(new Object[] { rs.getDate(1), rs.getString(2), rs.getFloat(3),
						tn.equals("T") ? TipNota.Test : tn.equals("E") ? TipNota.Examen : TipNota.Bonus,
						rs.getString(5) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(82);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Float.class, centerRenderer);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.setDefaultRenderer(Object.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
	}

	private void noteTabelStudentDisciplineProfesor(JTable table) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { Integer.class, LocalDate.class, String.class, Float.class,
					TipNota.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("ID_Nota");
		dfm.addColumn("Data Notei");
		dfm.addColumn("Nume Disciplină");
		dfm.addColumn("Nota");
		dfm.addColumn("Tip");
		try {
			noteStudent.setInt(1, nrMatricolCautat);
			noteStudent.setInt(2, idDCautat);
			noteStudent.setInt(3, idProfesorCautat);
			ResultSet rs = noteStudent.executeQuery();
			String tn;
			while (rs.next()) {
				tn = rs.getString(5);
				dfm.addRow(new Object[] { rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getFloat(4),
						tn.equals("T") ? TipNota.Test : tn.equals("E") ? TipNota.Examen : TipNota.Bonus });
			}
		} catch (SQLException e) {
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

	private void tabelProfesori(JTable table) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("ID_Profesor");
		dfm.addColumn("Nume_Profesor");
		dfm.addColumn("Email_Profesor");
		try {
			ResultSet rs = listaProfesori.executeQuery();
			String tn;
			while (rs.next()) {
				dfm.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(76);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Float.class, centerRenderer);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.setDefaultRenderer(Object.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
	}

	private void disciplineTabelStudent(JTable table, int nrm) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("Disciplină");
		dfm.addColumn("Profesor");
		try {
			disciplineInscriseStudent.setInt(1, nrm);
			ResultSet rs = disciplineInscriseStudent.executeQuery();
			while (rs.next()) {
				dfm.addRow(new Object[] { rs.getString(1), rs.getString(2) + " " + rs.getString(3) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(76);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
	}

	private void prezenteTabelStudentDisciplinaProfesor(JTable table, int nrm) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { Integer.class, LocalDate.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("ID_Prezență");
		dfm.addColumn("Data Prezenței");
		try {
			prezenteStudentDisciplinaProfesor.setInt(1, nrm);
			prezenteStudentDisciplinaProfesor.setInt(2, idProfesorCautat);
			prezenteStudentDisciplinaProfesor.setInt(3, idDCautat);
			ResultSet rs = prezenteStudentDisciplinaProfesor.executeQuery();
			while (rs.next()) {
				dfm.addRow(new Object[] { rs.getInt(1), rs.getDate(2) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(76);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.setDefaultRenderer(Object.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
	}

	private void prezenteTabelStudent(JTable table, int nrm) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { Integer.class, LocalDate.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("ID_Prezență");
		dfm.addColumn("Data Prezenței");
		dfm.addColumn("Nume Disciplină");
		try {
			prezenteStudent.setInt(1, nrm);
			ResultSet rs = prezenteStudent.executeQuery();
			while (rs.next()) {
				dfm.addRow(new Object[] { rs.getInt(1), rs.getDate(2), rs.getString(3) });
			}
		} catch (SQLException e) {
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

	private void disciplineTabelProfesor(JTable table, int idp) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { Integer.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("ID_Disciplină");
		dfm.addColumn("Nume Disciplină");
		try {
			disciplineProfesor.setInt(1, idp);
			ResultSet rs = disciplineProfesor.executeQuery();
			while (rs.next()) {
				dfm.addRow(new Object[] { rs.getInt(1), rs.getString(2) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(112);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.setDefaultRenderer(String.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void studentiDisciplinaTabelProfesor(JTable table) {

		table.setModel(new DefaultTableModel() {
			Class[] columnTypes = new Class[] { Integer.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableModel dfm = (DefaultTableModel) table.getModel();
		dfm.addColumn("Nr.Matricol");
		dfm.addColumn("Nume Student");
		try {
			studentiDisciplineProfesor.setInt(1, idDCautat);
			studentiDisciplineProfesor.setInt(2, idProfesorCautat);
			ResultSet rs = studentiDisciplineProfesor.executeQuery();
			while (rs.next()) {
				dfm.addRow(new Object[] { rs.getInt(1), rs.getString(2) + " " + rs.getString(3) });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(112);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Integer.class, centerRenderer);
		table.setDefaultRenderer(String.class, centerRenderer);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void setareComboData(JComboBox<Integer> cbA, JComboBox<Integer> cbL, JComboBox<Integer> cbZ) {
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
	public Main() {

		setTitle("Aplica\u021Bie gestiune studen\u021Bi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0, 0);
		contentPane.setLayout(cl);
		parolaGenerata.setEditable(false);
		JPanel MeniuInceput = new JPanel();
		contentPane.add(MeniuInceput, "Meniu Inceput");
		MeniuInceput.setLayout(null);

		JPanel MeniuModificareParolaProfesor = new JPanel();
		contentPane.add(MeniuModificareParolaProfesor, "Meniu Modificare Parola Profesor");
		MeniuModificareParolaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1.setBounds(10, 413, 85, 30);
		MeniuModificareParolaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1);

		JLabel lblParolaNouaProfesor = new JLabel("Parolă nouă");
		lblParolaNouaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolaNouaProfesor.setBounds(40, 56, 91, 30);
		MeniuModificareParolaProfesor.add(lblParolaNouaProfesor);

		passwordFieldParolaNouaProfesor = new JPasswordField();
		passwordFieldParolaNouaProfesor.setBounds(168, 59, 180, 24);
		MeniuModificareParolaProfesor.add(passwordFieldParolaNouaProfesor);

		JLabel lblConfirmareParolaProfesor = new JLabel("Confirmare Parolă");
		lblConfirmareParolaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblConfirmareParolaProfesor.setBounds(28, 115, 130, 30);
		MeniuModificareParolaProfesor.add(lblConfirmareParolaProfesor);

		passwordFieldParolaNouaProfesorConf = new JPasswordField();
		passwordFieldParolaNouaProfesorConf.setBounds(168, 118, 180, 24);
		MeniuModificareParolaProfesor.add(passwordFieldParolaNouaProfesorConf);

		JButton btnSalvareParStudProf = new JButton("Salvare");
		btnSalvareParStudProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String parolaNoua = new String(passwordFieldParolaNouaProfesor.getPassword());
				String parolaNouaConf = new String(passwordFieldParolaNouaProfesorConf.getPassword());
				if (parolaNoua.isBlank())
					JOptionPane.showInternalMessageDialog(null, "Introduceți o parolă!", "Eroare modifcare parolă",
							JOptionPane.WARNING_MESSAGE, null);
				else if (parolaNoua.compareTo(parolaNouaConf) != 0)
					JOptionPane.showInternalMessageDialog(null, "Câmpurile nu coincid!!", "Eroare modifcare parolă",
							JOptionPane.WARNING_MESSAGE, null);
				else {
					try {
						modificareParolaProfesor(parolaNoua);
						JOptionPane.showInternalMessageDialog(null, "Parolă modificată cu succes!", "Modificare parolă",
								JOptionPane.INFORMATION_MESSAGE, null);
						cl.show(contentPane, "Meniu Profesor");
					} catch (ExceptieUtilizator e1) {
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(),
								"Eroare la căutarea în baza de date!", JOptionPane.WARNING_MESSAGE, null);
						e1.printStackTrace();
					}
				}
			}
		});
		btnSalvareParStudProf.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSalvareParStudProf.setFocusable(false);
		btnSalvareParStudProf.setBounds(481, 413, 85, 30);
		MeniuModificareParolaProfesor.add(btnSalvareParStudProf);

		JPanel InformatiiDisciplinaProfesor = new JPanel();
		contentPane.add(InformatiiDisciplinaProfesor, "Informații Disciplină Profesor");
		InformatiiDisciplinaProfesor.setLayout(null);

		JLabel lblNumeDisciplinProf = new JLabel("Nume disciplină");
		lblNumeDisciplinProf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeDisciplinProf.setBounds(44, 44, 114, 30);
		InformatiiDisciplinaProfesor.add(lblNumeDisciplinProf);

		NumeDisciplinaProf = new JTextField();
		NumeDisciplinaProf.setFont(new Font("Arial", Font.PLAIN, 15));
		NumeDisciplinaProf.setColumns(10);
		NumeDisciplinaProf.setBounds(168, 47, 180, 24);
		InformatiiDisciplinaProfesor.add(NumeDisciplinaProf);

		JLabel lblPrezeneMinimeDisciplinProf = new JLabel("Prezențe minime");
		lblPrezeneMinimeDisciplinProf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPrezeneMinimeDisciplinProf.setBounds(44, 84, 114, 30);
		InformatiiDisciplinaProfesor.add(lblPrezeneMinimeDisciplinProf);

		PrezenteDisciplinaProf = new JTextField();
		PrezenteDisciplinaProf.setFont(new Font("Arial", Font.PLAIN, 15));
		PrezenteDisciplinaProf.setColumns(10);
		PrezenteDisciplinaProf.setBounds(168, 87, 180, 24);
		InformatiiDisciplinaProfesor.add(PrezenteDisciplinaProf);

		JLabel lblNumrDeTesteDisciplinProf = new JLabel("Număr de teste");
		lblNumrDeTesteDisciplinProf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumrDeTesteDisciplinProf.setBounds(44, 124, 114, 30);
		InformatiiDisciplinaProfesor.add(lblNumrDeTesteDisciplinProf);

		NrTesteDisciplinaProf = new JTextField();
		NrTesteDisciplinaProf.setFont(new Font("Arial", Font.PLAIN, 15));
		NrTesteDisciplinaProf.setColumns(10);
		NrTesteDisciplinaProf.setBounds(168, 127, 180, 24);
		InformatiiDisciplinaProfesor.add(NrTesteDisciplinaProf);

		JLabel lblProcentTesteDisciplinProf = new JLabel("Procent teste");
		lblProcentTesteDisciplinProf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProcentTesteDisciplinProf.setBounds(44, 164, 101, 30);
		InformatiiDisciplinaProfesor.add(lblProcentTesteDisciplinProf);

		ProcentTesteDisciplinaProf = new JTextField();
		ProcentTesteDisciplinaProf.setFont(new Font("Arial", Font.PLAIN, 15));
		ProcentTesteDisciplinaProf.setColumns(10);
		ProcentTesteDisciplinaProf.setBounds(168, 167, 180, 24);
		InformatiiDisciplinaProfesor.add(ProcentTesteDisciplinaProf);

		JLabel lblProcentExamenDisciplinProf = new JLabel("Procent examen");
		lblProcentExamenDisciplinProf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProcentExamenDisciplinProf.setBounds(44, 204, 114, 30);
		InformatiiDisciplinaProfesor.add(lblProcentExamenDisciplinProf);

		ProcentExamenDisciplinaProf = new JTextField();
		ProcentExamenDisciplinaProf.setFont(new Font("Arial", Font.PLAIN, 15));
		ProcentExamenDisciplinaProf.setColumns(10);
		ProcentExamenDisciplinaProf.setBounds(168, 207, 180, 24);
		InformatiiDisciplinaProfesor.add(ProcentExamenDisciplinaProf);

		JLabel lblPuncteBonusDisciplinaProf = new JLabel("Puncte bonus ?");
		lblPuncteBonusDisciplinaProf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPuncteBonusDisciplinaProf.setBounds(44, 244, 114, 30);
		InformatiiDisciplinaProfesor.add(lblPuncteBonusDisciplinaProf);

		JComboBox<String> PuncteBonusDisciplinăProf = new JComboBox<String>();
		PuncteBonusDisciplinăProf.setModel(new DefaultComboBoxModel(new String[] { "Da", "Nu" }));
		PuncteBonusDisciplinăProf.setSelectedIndex(1);
		PuncteBonusDisciplinăProf.setFont(new Font("Arial", Font.PLAIN, 15));
		PuncteBonusDisciplinăProf.setBounds(168, 247, 45, 21);
		InformatiiDisciplinaProfesor.add(PuncteBonusDisciplinăProf);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_1.setBounds(10, 413, 85, 30);
		InformatiiDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_1);

		JPanel SituatieStudentDisciplinaProfesor = new JPanel();
		contentPane.add(SituatieStudentDisciplinaProfesor, "Situație Student Disciplină Profesor");
		SituatieStudentDisciplinaProfesor.setLayout(null);

		JScrollPane scrollPane_StudentCautat_1_1 = new JScrollPane();
		scrollPane_StudentCautat_1_1.setBounds(38, 35, 500, 340);
		SituatieStudentDisciplinaProfesor.add(scrollPane_StudentCautat_1_1);

		JTextArea textAreaSiutatieStudentDisciplinaProfesor = new JTextArea();
		scrollPane_StudentCautat_1_1.setViewportView(textAreaSiutatieStudentDisciplinaProfesor);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_1.setBounds(10, 413, 85, 30);
		SituatieStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_1);

		JPanel AdaugareNotaStudentDisciplinaProfesor = new JPanel();
		contentPane.add(AdaugareNotaStudentDisciplinaProfesor, "Adăugare Notă Student Disciplină Profesor");
		AdaugareNotaStudentDisciplinaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_2.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_2.setBounds(10, 413, 85, 30);
		AdaugareNotaStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_2);

		JLabel lblNotaStudentDisciplinaProfesor = new JLabel("Nota");
		lblNotaStudentDisciplinaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNotaStudentDisciplinaProfesor.setBounds(53, 87, 42, 30);
		AdaugareNotaStudentDisciplinaProfesor.add(lblNotaStudentDisciplinaProfesor);

		NotaStudentDisciplinaProfesor = new JTextField();
		NotaStudentDisciplinaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		NotaStudentDisciplinaProfesor.setColumns(10);
		NotaStudentDisciplinaProfesor.setBounds(119, 90, 180, 25);
		AdaugareNotaStudentDisciplinaProfesor.add(NotaStudentDisciplinaProfesor);

		JLabel lblTipNotaStudentDisciplinaProfesor = new JLabel("Tipul notei");
		lblTipNotaStudentDisciplinaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTipNotaStudentDisciplinaProfesor.setBounds(36, 134, 70, 30);
		AdaugareNotaStudentDisciplinaProfesor.add(lblTipNotaStudentDisciplinaProfesor);

		JComboBox<String> comboBoxTipNotaStudentDisciplinaProfesor = new JComboBox<String>();
		comboBoxTipNotaStudentDisciplinaProfesor
				.setModel(new DefaultComboBoxModel(new String[] { "Test", "Examen", "Bonus" }));
		comboBoxTipNotaStudentDisciplinaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBoxTipNotaStudentDisciplinaProfesor.setBounds(119, 139, 90, 21);
		AdaugareNotaStudentDisciplinaProfesor.add(comboBoxTipNotaStudentDisciplinaProfesor);

		JLabel lblAnNota = new JLabel("Anul");
		lblAnNota.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAnNota.setBounds(413, 96, 30, 13);
		AdaugareNotaStudentDisciplinaProfesor.add(lblAnNota);

		JComboBox<Integer> comboBoxAnNotaStudentDisciplinaProfesor = new JComboBox<Integer>();
		comboBoxAnNotaStudentDisciplinaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBoxAnNotaStudentDisciplinaProfesor.setBounds(465, 92, 78, 21);
		AdaugareNotaStudentDisciplinaProfesor.add(comboBoxAnNotaStudentDisciplinaProfesor);

		JLabel lblLunaNota = new JLabel("Luna");
		lblLunaNota.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLunaNota.setBounds(413, 143, 42, 13);
		AdaugareNotaStudentDisciplinaProfesor.add(lblLunaNota);

		JComboBox<Integer> comboBoxLunaNotaStudentDisciplinaProfesor = new JComboBox<Integer>();
		comboBoxLunaNotaStudentDisciplinaProfesor
				.setModel(new DefaultComboBoxModel<Integer>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));
		comboBoxLunaNotaStudentDisciplinaProfesor.setSelectedIndex(0);
		comboBoxLunaNotaStudentDisciplinaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBoxLunaNotaStudentDisciplinaProfesor.setBounds(494, 139, 48, 21);
		AdaugareNotaStudentDisciplinaProfesor.add(comboBoxLunaNotaStudentDisciplinaProfesor);

		JLabel lblZiuaNota = new JLabel("Ziua");
		lblZiuaNota.setFont(new Font("Arial", Font.PLAIN, 15));
		lblZiuaNota.setBounds(413, 189, 42, 13);
		AdaugareNotaStudentDisciplinaProfesor.add(lblZiuaNota);

		JComboBox<Integer> comboBoxZiNotaStudentDisciplinaProfesor = new JComboBox<Integer>();
		comboBoxZiNotaStudentDisciplinaProfesor.setSelectedIndex(-1);
		comboBoxZiNotaStudentDisciplinaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBoxZiNotaStudentDisciplinaProfesor.setBounds(494, 185, 48, 21);
		AdaugareNotaStudentDisciplinaProfesor.add(comboBoxZiNotaStudentDisciplinaProfesor);

		JButton btnNewButton_1_1_1_5_2_1 = new JButton("Adăugare");
		btnNewButton_1_1_1_5_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					float punctaj = Float.parseFloat(NotaStudentDisciplinaProfesor.getText().trim());
					String tip = (String) comboBoxTipNotaStudentDisciplinaProfesor.getSelectedItem().toString();
					tip = tip.substring(0, 1);
					if (punctaj < 0 || punctaj > 10)
						throw new Exception("Nota poate fi doar între 0 și 10");
					else {
						switch (tip) {
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
						LocalDate data = LocalDate.of(
								(Integer) comboBoxAnNotaStudentDisciplinaProfesor.getSelectedItem(),
								(Integer) comboBoxLunaNotaStudentDisciplinaProfesor.getSelectedItem(),
								(Integer) comboBoxZiNotaStudentDisciplinaProfesor.getSelectedItem());

						inserareNota(data, nrMatricolCautat, idDCautat, punctaj, tip, idProfesorCautat);

						NotaStudentDisciplinaProfesor.setText("");
						setareComboData(comboBoxAnNotaStudentDisciplinaProfesor,
								comboBoxLunaNotaStudentDisciplinaProfesor, comboBoxZiNotaStudentDisciplinaProfesor);
						comboBoxTipNotaStudentDisciplinaProfesor.setSelectedIndex(0);
						JOptionPane.showInternalMessageDialog(null, "Notă adăugată cu succes", "Adăugare notă",
								JOptionPane.INFORMATION_MESSAGE, null);
					}

				} catch (NumberFormatException nfe) {
					JOptionPane.showInternalMessageDialog(null, "Nota trebuie să fie un număr zecimal!",
							"Eroare adăugare notă", JOptionPane.WARNING_MESSAGE, null);
				} catch (NullPointerException npe) {
					JOptionPane.showInternalMessageDialog(null, "Câmpul cu nota este gol", "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
				} catch (ExceptieStudent e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (ExceptieDisciplina e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (Exception e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare notă",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				}

			}
		});
		btnNewButton_1_1_1_5_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_5_2_1.setFocusable(false);
		btnNewButton_1_1_1_5_2_1.setBounds(454, 413, 112, 30);
		AdaugareNotaStudentDisciplinaProfesor.add(btnNewButton_1_1_1_5_2_1);
		setareComboData(comboBoxAnNotaStudentDisciplinaProfesor, comboBoxLunaNotaStudentDisciplinaProfesor,
				comboBoxZiNotaStudentDisciplinaProfesor);

		comboBoxLunaNotaStudentDisciplinaProfesor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxZiNotaStudentDisciplinaProfesor
						.setModel(actualizareZile((Integer) comboBoxAnNotaStudentDisciplinaProfesor.getSelectedItem(),
								(Integer) comboBoxLunaNotaStudentDisciplinaProfesor.getSelectedItem()));

			}

		});
		comboBoxAnNotaStudentDisciplinaProfesor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxZiNotaStudentDisciplinaProfesor
						.setModel(actualizareZile((Integer) comboBoxAnNotaStudentDisciplinaProfesor.getSelectedItem(),
								(Integer) comboBoxLunaNotaStudentDisciplinaProfesor.getSelectedItem()));

			}

		});

		JPanel StergeNoteStudentDisciplinaProfesor = new JPanel();
		contentPane.add(StergeNoteStudentDisciplinaProfesor, "Șterge Note Student Disciplină Profesor");
		StergeNoteStudentDisciplinaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_3 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_3.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_3.setBounds(10, 413, 85, 30);
		StergeNoteStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_3);

		JScrollPane scrollPane_TabelNoteStudentDisciplinaProfesor = new JScrollPane();
		scrollPane_TabelNoteStudentDisciplinaProfesor.setBounds(31, 50, 507, 298);
		StergeNoteStudentDisciplinaProfesor.add(scrollPane_TabelNoteStudentDisciplinaProfesor);

		tableNoteStudentDisciplinaProfesor = new JTable();
		scrollPane_TabelNoteStudentDisciplinaProfesor.setViewportView(tableNoteStudentDisciplinaProfesor);

		JButton btnNewButton_1_1_1_5_1_2_1 = new JButton("Ștergere Note");
		btnNewButton_1_1_1_5_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] randuri = tableNoteStudentDisciplinaProfesor.getSelectedRows();
				List<Integer> idNoteSterse = new ArrayList<Integer>();
				for (int rand : randuri)
					idNoteSterse.add((Integer) tableNoteStudentDisciplinaProfesor.getValueAt(rand, 0));
				idNoteSterse.forEach(idn -> {
					stergereNota(idn);
				});
				JOptionPane.showInternalMessageDialog(null, "Note șterse cu succes", "Ștergere note",
						JOptionPane.INFORMATION_MESSAGE, null);
				cl.show(contentPane, "Meniu Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_1_5_1_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_5_1_2_1.setFocusable(false);
		btnNewButton_1_1_1_5_1_2_1.setBounds(429, 413, 137, 30);
		StergeNoteStudentDisciplinaProfesor.add(btnNewButton_1_1_1_5_1_2_1);

		JPanel StergerePrezenteStudentDisciplinaProfesor = new JPanel();
		contentPane.add(StergerePrezenteStudentDisciplinaProfesor, "Ștergere Prezențe Student Disciplină Profesor");
		StergerePrezenteStudentDisciplinaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_5 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_5.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_5.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_5.setBounds(10, 413, 85, 30);
		StergerePrezenteStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_5);

		JScrollPane scrollPane_TabelPrezenteStudentDisciplinaProfesor = new JScrollPane();
		scrollPane_TabelPrezenteStudentDisciplinaProfesor.setBounds(31, 50, 507, 298);
		StergerePrezenteStudentDisciplinaProfesor.add(scrollPane_TabelPrezenteStudentDisciplinaProfesor);

		tablePrezenteStudentDisciplinaProfesor = new JTable();
		scrollPane_TabelPrezenteStudentDisciplinaProfesor.setViewportView(tablePrezenteStudentDisciplinaProfesor);

		JButton btnNewButton_1_1_1_5_1_1 = new JButton("Ștergere Prezențe");
		btnNewButton_1_1_1_5_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] randuri = tablePrezenteStudentDisciplinaProfesor.getSelectedRows();
				List<Integer> idPrezSterse = new ArrayList<Integer>();
				for (int rand : randuri)
					idPrezSterse.add((Integer) tablePrezenteStudentDisciplinaProfesor.getValueAt(rand, 0));
				idPrezSterse.forEach(idp -> {
					stergerePrezenta(idp);
				});
				JOptionPane.showInternalMessageDialog(null, "Prezențe șterse cu succes", "Ștergere prezențe",
						JOptionPane.INFORMATION_MESSAGE, null);
				cl.show(contentPane, "Meniu Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_1_5_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_5_1_1.setFocusable(false);
		btnNewButton_1_1_1_5_1_1.setBounds(409, 413, 157, 30);
		StergerePrezenteStudentDisciplinaProfesor.add(btnNewButton_1_1_1_5_1_1);

		JPanel AdaugarePrezentaStudentDisciplinaProfesor = new JPanel();
		contentPane.add(AdaugarePrezentaStudentDisciplinaProfesor, "Adăugare Prezență Student Disciplină Profesor");
		AdaugarePrezentaStudentDisciplinaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4.setBounds(10, 413, 85, 30);
		AdaugarePrezentaStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4);

		JLabel lblNewLabel_6_2 = new JLabel("Data Prezenței");
		lblNewLabel_6_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_6_2.setBounds(66, 54, 117, 21);
		AdaugarePrezentaStudentDisciplinaProfesor.add(lblNewLabel_6_2);

		JLabel lblNewLabel_6_1_2 = new JLabel("Anul");
		lblNewLabel_6_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_2.setBounds(54, 97, 30, 13);
		AdaugarePrezentaStudentDisciplinaProfesor.add(lblNewLabel_6_1_2);

		JComboBox<Integer> comboBox_AnPrezentaDisciplinaStudentProfesor = new JComboBox<Integer>();
		comboBox_AnPrezentaDisciplinaStudentProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_AnPrezentaDisciplinaStudentProfesor.setBounds(106, 93, 78, 21);
		AdaugarePrezentaStudentDisciplinaProfesor.add(comboBox_AnPrezentaDisciplinaStudentProfesor);

		JLabel lblNewLabel_6_1_1_2 = new JLabel("Luna");
		lblNewLabel_6_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_1_2.setBounds(54, 144, 42, 13);
		AdaugarePrezentaStudentDisciplinaProfesor.add(lblNewLabel_6_1_1_2);

		JComboBox<Integer> comboBox_LunaPrezentaDisciplinaStudentProfesor = new JComboBox<Integer>();
		comboBox_LunaPrezentaDisciplinaStudentProfesor
				.setModel(new DefaultComboBoxModel<Integer>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }));
		comboBox_LunaPrezentaDisciplinaStudentProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_LunaPrezentaDisciplinaStudentProfesor.setBounds(135, 140, 48, 21);
		AdaugarePrezentaStudentDisciplinaProfesor.add(comboBox_LunaPrezentaDisciplinaStudentProfesor);

		JLabel lblNewLabel_6_1_1_1_1 = new JLabel("Ziua");
		lblNewLabel_6_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_6_1_1_1_1.setBounds(54, 190, 42, 13);
		AdaugarePrezentaStudentDisciplinaProfesor.add(lblNewLabel_6_1_1_1_1);

		JComboBox<Integer> comboBox_ZiPrezentaDisciplinaStudentProfesor = new JComboBox<Integer>();
		comboBox_ZiPrezentaDisciplinaStudentProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox_ZiPrezentaDisciplinaStudentProfesor.setBounds(135, 186, 48, 21);
		AdaugarePrezentaStudentDisciplinaProfesor.add(comboBox_ZiPrezentaDisciplinaStudentProfesor);

		setareComboData(comboBox_AnPrezentaDisciplinaStudentProfesor, comboBox_LunaPrezentaDisciplinaStudentProfesor,
				comboBox_ZiPrezentaDisciplinaStudentProfesor);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4_1 = new JButton("Adăugare prezență");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (!maximPrezente(idDCautat, nrMatricolCautat)) {
						LocalDate data = LocalDate.of(
								(Integer) comboBox_AnPrezentaDisciplinaStudentProfesor.getSelectedItem(),
								(Integer) comboBox_LunaPrezentaDisciplinaStudentProfesor.getSelectedItem(),
								(Integer) comboBox_ZiPrezentaDisciplinaStudentProfesor.getSelectedItem());
						inserarePrezenta(data, nrMatricolCautat, idDCautat);
						setareComboData(comboBox_AnPrezentaDisciplinaStudentProfesor,
								comboBox_LunaPrezentaDisciplinaStudentProfesor,
								comboBox_ZiPrezentaDisciplinaStudentProfesor);
						JOptionPane.showInternalMessageDialog(null, "Prezență adăugată cu succes", "Adăugare prezență",
								JOptionPane.INFORMATION_MESSAGE, null);
					}

				} catch (ExceptieStudent e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare prezență",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (ExceptieDisciplina e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare prezență",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				} catch (Exception e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare prezență",
							JOptionPane.WARNING_MESSAGE, null);
					// e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4_1.setBounds(405, 413, 161, 30);
		AdaugarePrezentaStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1_4_1);

		comboBox_LunaPrezentaDisciplinaStudentProfesor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comboBox_ZiPrezentaDisciplinaStudentProfesor.setModel(
						actualizareZile((Integer) comboBox_AnPrezentaDisciplinaStudentProfesor.getSelectedItem(),
								(Integer) comboBox_LunaPrezentaDisciplinaStudentProfesor.getSelectedItem()));

			}

		});
		comboBox_AnPrezentaDisciplinaStudentProfesor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comboBox_ZiPrezentaDisciplinaStudentProfesor.setModel(
						actualizareZile((Integer) comboBox_AnPrezentaDisciplinaStudentProfesor.getSelectedItem(),
								(Integer) comboBox_LunaPrezentaDisciplinaStudentProfesor.getSelectedItem()));

			}

		});

		JPanel MeniuStudentDisciplinaProfesor = new JPanel();
		contentPane.add(MeniuStudentDisciplinaProfesor, "Meniu Student Disciplină Profesor");
		MeniuStudentDisciplinaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Selectare Student Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1.setBounds(10, 413, 85, 30);
		MeniuStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2_1);

		JButton btnSituaieDisciplin = new JButton("Situație disciplină");
		btnSituaieDisciplin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaSiutatieStudentDisciplinaProfesor.setText(informatiiStudentDisciplinaProfesor());
				cl.show(contentPane, "Situație Student Disciplină Profesor");
			}
		});
		btnSituaieDisciplin.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSituaieDisciplin.setFocusable(false);
		btnSituaieDisciplin.setBounds(218, 64, 164, 30);
		MeniuStudentDisciplinaProfesor.add(btnSituaieDisciplin);

		JButton btnAdugareNot = new JButton("Adăugare Notă");
		btnAdugareNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Adăugare Notă Student Disciplină Profesor");
			}
		});
		btnAdugareNot.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareNot.setFocusable(false);
		btnAdugareNot.setBounds(218, 124, 164, 30);
		MeniuStudentDisciplinaProfesor.add(btnAdugareNot);

		JButton btntergereNote = new JButton("Ștergere Note");
		btntergereNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				noteTabelStudentDisciplineProfesor(tableNoteStudentDisciplinaProfesor);
				cl.show(contentPane, "Șterge Note Student Disciplină Profesor");

			}
		});
		btntergereNote.setFont(new Font("Arial", Font.PLAIN, 15));
		btntergereNote.setFocusable(false);
		btntergereNote.setBounds(218, 184, 164, 30);
		MeniuStudentDisciplinaProfesor.add(btntergereNote);

		JButton btnAdugarePrezen_1 = new JButton("Adăugare Prezență");
		btnAdugarePrezen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Adăugare Prezență Student Disciplină Profesor");
			}
		});
		btnAdugarePrezen_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugarePrezen_1.setFocusable(false);
		btnAdugarePrezen_1.setBounds(218, 244, 164, 30);
		MeniuStudentDisciplinaProfesor.add(btnAdugarePrezen_1);

		JButton btntergerePrezene = new JButton("Ștergere Prezențe");
		btntergerePrezene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prezenteTabelStudentDisciplinaProfesor(tablePrezenteStudentDisciplinaProfesor, nrMatricolCautat);
				cl.show(contentPane, "Ștergere Prezențe Student Disciplină Profesor");
			}
		});
		btntergerePrezene.setFont(new Font("Arial", Font.PLAIN, 15));
		btntergerePrezene.setFocusable(false);
		btntergerePrezene.setBounds(218, 304, 164, 30);
		MeniuStudentDisciplinaProfesor.add(btntergerePrezene);

		JPanel SelectareStudentDisciplinaProfesor = new JPanel();
		contentPane.add(SelectareStudentDisciplinaProfesor, "Selectare Student Disciplină Profesor");
		SelectareStudentDisciplinaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Disciplină Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2.setBounds(10, 413, 85, 30);
		SelectareStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2_2);

		JScrollPane scrollPane_TabelStudentiDisciplinaProfesor = new JScrollPane();
		scrollPane_TabelStudentiDisciplinaProfesor.setBounds(27, 66, 507, 298);
		SelectareStudentDisciplinaProfesor.add(scrollPane_TabelStudentiDisciplinaProfesor);

		tableStudentiDisciplinaProfesor = new JTable();
		scrollPane_TabelStudentiDisciplinaProfesor.setViewportView(tableStudentiDisciplinaProfesor);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_1_1 = new JButton("Selectare");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rand = tableStudentiDisciplinaProfesor.getSelectedRow();
				if (rand == -1)
					JOptionPane.showInternalMessageDialog(null, "Selectați un student!", "Eroare selectare student",
							JOptionPane.WARNING_MESSAGE, null);
				else {
					nrMatricolCautat = (Integer) tableStudentiDisciplinaProfesor.getValueAt(rand, 0);
					cl.show(contentPane, "Meniu Student Disciplină Profesor");
				}
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1_1.setBounds(469, 413, 97, 30);
		SelectareStudentDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_1_1);

		JPanel MeniuDisciplinaProfesor = new JPanel();
		contentPane.add(MeniuDisciplinaProfesor, "Meniu Disciplină Profesor");
		MeniuDisciplinaProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Discipline Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_2.setBounds(10, 413, 85, 30);
		MeniuDisciplinaProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_2);

		JButton btnInformaiiDisciplin = new JButton("Informații disciplină");
		btnInformaiiDisciplin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumeDisciplinaProf.setText(disciplinaCautata.getNume());
				PrezenteDisciplinaProf.setText(String.valueOf(disciplinaCautata.getPrezente()));
				NrTesteDisciplinaProf.setText(String.valueOf(disciplinaCautata.getNrTeste()));
				ProcentTesteDisciplinaProf.setText(String.valueOf(disciplinaCautata.getProcentTeste()));
				ProcentExamenDisciplinaProf.setText(String.valueOf(disciplinaCautata.getProcentExamen()));
				PuncteBonusDisciplinăProf.setSelectedIndex(disciplinaCautata.isBonus() ? 0 : 1);
				NumeDisciplinaProf.setEditable(false);
				PrezenteDisciplinaProf.setEditable(false);
				NrTesteDisciplinaProf.setEditable(false);
				ProcentTesteDisciplinaProf.setEditable(false);
				ProcentExamenDisciplinaProf.setEditable(false);
				PuncteBonusDisciplinăProf.setEnabled(false);
				cl.show(contentPane, "Informații Disciplină Profesor");
			}
		});
		btnInformaiiDisciplin.setFont(new Font("Arial", Font.PLAIN, 15));
		btnInformaiiDisciplin.setFocusable(false);
		btnInformaiiDisciplin.setBounds(218, 64, 164, 30);
		MeniuDisciplinaProfesor.add(btnInformaiiDisciplin);

		JButton btnSelectareStudent = new JButton("Selectare student");
		btnSelectareStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentiDisciplinaTabelProfesor(tableStudentiDisciplinaProfesor);
				cl.show(contentPane, "Selectare Student Disciplină Profesor");
			}
		});
		btnSelectareStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSelectareStudent.setFocusable(false);
		btnSelectareStudent.setBounds(218, 124, 164, 30);
		MeniuDisciplinaProfesor.add(btnSelectareStudent);

		JPanel DisciplineProfesor = new JPanel();
		contentPane.add(DisciplineProfesor, "Discipline Profesor");
		DisciplineProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1.setBounds(10, 413, 85, 30);
		DisciplineProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1);

		JScrollPane scrollPane_TabelDisciplineProfesor = new JScrollPane();
		scrollPane_TabelDisciplineProfesor.setBounds(27, 66, 507, 298);
		DisciplineProfesor.add(scrollPane_TabelDisciplineProfesor);

		tableDisciplineProfesor = new JTable();
		scrollPane_TabelDisciplineProfesor.setViewportView(tableDisciplineProfesor);

		JLabel lblNewLabel_2 = new JLabel("Selectați o disciplină");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(190, 37, 196, 19);
		DisciplineProfesor.add(lblNewLabel_2);

		JButton btnNewButton_1_1_3_1_3_3_1_1_1_1_1 = new JButton("Selectare");
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rand = tableDisciplineProfesor.getSelectedRow();
				if (rand == -1)
					JOptionPane.showInternalMessageDialog(null, "Selectați o disciplină!",
							"Eroare selectare disciplină", JOptionPane.WARNING_MESSAGE, null);
				else {
					idDCautat = (Integer) tableDisciplineProfesor.getValueAt(rand, 0);
					String nume = (String) tableDisciplineProfesor.getValueAt(rand, 1);
					try {
						if (!cautareDisciplina(nume))
							JOptionPane.showInternalMessageDialog(null, "Nu am găsit disciplina cu numele " + nume,
									"Eroare căutare disciplină", JOptionPane.WARNING_MESSAGE, null);
						else
							cl.show(contentPane, "Meniu Disciplină Profesor");
					} catch (ExceptieDisciplina e1) {
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare disciplină",
								JOptionPane.WARNING_MESSAGE, null);
					}

				}

			}
		});
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1_1_1_1.setBounds(469, 413, 97, 30);
		DisciplineProfesor.add(btnNewButton_1_1_3_1_3_3_1_1_1_1_1);

		JPanel MeniuProfesor = new JPanel();
		contentPane.add(MeniuProfesor, "Meniu Profesor");
		MeniuProfesor.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_1_1_1 = new JButton("Deconectare");
		btnNewButton_1_1_3_1_3_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Login Profesor");
			}
		});
		btnNewButton_1_1_3_1_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_1_1_1.setBounds(10, 413, 119, 30);
		MeniuProfesor.add(btnNewButton_1_1_3_1_3_1_1_1);

		JButton btnNModParolaProf = new JButton("Modifcare parolă");
		btnNModParolaProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Modificare Parola Profesor");
			}
		});
		btnNModParolaProf.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNModParolaProf.setFocusable(false);
		btnNModParolaProf.setBounds(421, 413, 145, 30);
		MeniuProfesor.add(btnNModParolaProf);

		JButton btnDatePersonaleProf = new JButton("Date personale");
		btnDatePersonaleProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CNPProfesor.setText(profesorCautat.getCnp());
				NumeProfesor.setText(profesorCautat.getNume());
				PrenumeProfesor.setText(profesorCautat.getPrenume());
				TelefonProfesor.setText(profesorCautat.getNrTelefon());
				EmailProfesor.setText(profesorCautat.getEmail());
				AdresaProfesor.setText(profesorCautat.getAdresa());
				cl.show(contentPane, "Date Personale Profesor");
			}
		});
		btnDatePersonaleProf.setFont(new Font("Arial", Font.PLAIN, 15));
		btnDatePersonaleProf.setFocusable(false);
		btnDatePersonaleProf.setBounds(225, 64, 150, 30);
		MeniuProfesor.add(btnDatePersonaleProf);

		JButton btnDisciplineProfesor = new JButton("Discipline");
		btnDisciplineProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disciplineTabelProfesor(tableDisciplineProfesor, idProfesorCautat);
				cl.show(contentPane, "Discipline Profesor");
			}
		});
		btnDisciplineProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		btnDisciplineProfesor.setFocusable(false);
		btnDisciplineProfesor.setBounds(225, 124, 150, 30);
		MeniuProfesor.add(btnDisciplineProfesor);

		JPanel LoginProfesor = new JPanel();
		contentPane.add(LoginProfesor, "Login Profesor");
		LoginProfesor.setLayout(null);

		JLabel lblEmailProfesorLogin = new JLabel("Email");
		lblEmailProfesorLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmailProfesorLogin.setBounds(57, 60, 52, 30);
		LoginProfesor.add(lblEmailProfesorLogin);

		textFieldEmailProfesorLogin = new JTextField();
		textFieldEmailProfesorLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldEmailProfesorLogin.setColumns(10);
		textFieldEmailProfesorLogin.setBounds(168, 63, 180, 24);
		LoginProfesor.add(textFieldEmailProfesorLogin);

		JLabel lblParolaProfesor = new JLabel("Parolă");
		lblParolaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolaProfesor.setBounds(57, 115, 52, 30);
		LoginProfesor.add(lblParolaProfesor);

		passwordFieldProfesor = new JPasswordField();
		passwordFieldProfesor.setBounds(168, 118, 180, 24);
		LoginProfesor.add(passwordFieldProfesor);

		JButton btnNewButton_1_1_3_1_3_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Inceput");
			}
		});
		btnNewButton_1_1_3_1_3_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_1_1.setBounds(10, 413, 85, 30);
		LoginProfesor.add(btnNewButton_1_1_3_1_3_1_1);

		JButton btnLoginProfesor = new JButton("Log in");
		btnLoginProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldEmailProfesorLogin.getText().isBlank())
					JOptionPane.showInternalMessageDialog(null, "Introduceți adresa de email!",
							"Eroare la căutare profesor", JOptionPane.WARNING_MESSAGE, null);
				else if (passwordFieldProfesor.getPassword().length == 0)
					JOptionPane.showInternalMessageDialog(null, "Introduceți parola!", "Eroare la căutare profesor",
							JOptionPane.WARNING_MESSAGE, null);

				else
					try {
						if (cautareProfesorEmail(textFieldEmailProfesorLogin.getText())) {
							idProfesorCautat = idProfesor(textFieldEmailProfesorLogin.getText(),
									new String(passwordFieldProfesor.getPassword()));
							if (idProfesorCautat == -1)
								JOptionPane.showInternalMessageDialog(null, "Parolă incorectă!",
										"Eroare la autentificare student", JOptionPane.WARNING_MESSAGE, null);
							else {
								textFieldEmailProfesorLogin.setText("");
								passwordFieldProfesor.setText("");
								cautareProfesor(String.valueOf(idProfesorCautat), false);
								cl.show(contentPane, "Meniu Profesor");
							}
						} else {
							JOptionPane.showInternalMessageDialog(null, "Nu există nici un profesor cu acest email!",
									"Eroare la autentificare profesor", JOptionPane.WARNING_MESSAGE, null);
						}
					} catch (ExceptieUtilizator e1) {
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(),
								"Eroare la căutarea în baza de date!", JOptionPane.WARNING_MESSAGE, null);
						e1.printStackTrace();
					}
			}
		});
		btnLoginProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		btnLoginProfesor.setFocusable(false);
		btnLoginProfesor.setBounds(454, 413, 112, 30);
		LoginProfesor.add(btnLoginProfesor);

		JPanel PrezenteStudent = new JPanel();
		contentPane.add(PrezenteStudent, "Prezențe Student");
		PrezenteStudent.setLayout(null);

		JScrollPane scrollPane_TabelPrezenteStudent = new JScrollPane();
		scrollPane_TabelPrezenteStudent.setBounds(31, 50, 507, 298);
		PrezenteStudent.add(scrollPane_TabelPrezenteStudent);

		tablePrezenteStudent = new JTable();
		scrollPane_TabelPrezenteStudent.setViewportView(tablePrezenteStudent);

		JButton btnNewButton_1_1_3_1_3_3_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Discipline Student");
			}
		});
		btnNewButton_1_1_3_1_3_3_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1_1.setBounds(10, 413, 85, 30);
		PrezenteStudent.add(btnNewButton_1_1_3_1_3_3_1_1);

		JPanel NoteStudent = new JPanel();
		contentPane.add(NoteStudent, "Note Student");
		NoteStudent.setLayout(null);

		JScrollPane scrollPane_TabelNoteStudent = new JScrollPane();
		scrollPane_TabelNoteStudent.setBounds(31, 50, 507, 298);
		NoteStudent.add(scrollPane_TabelNoteStudent);

		tableNoteStudent = new JTable();
		scrollPane_TabelNoteStudent.setViewportView(tableNoteStudent);

		JButton btnNewButton_1_1_3_1_3_3_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Discipline Student");
			}
		});
		btnNewButton_1_1_3_1_3_3_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_3_1.setBounds(10, 413, 85, 30);
		NoteStudent.add(btnNewButton_1_1_3_1_3_3_1);

		JPanel DisciplineStudent = new JPanel();
		contentPane.add(DisciplineStudent, "Discipline Student");
		DisciplineStudent.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_3 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student");
			}
		});
		btnNewButton_1_1_3_1_3_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_3.setFocusable(false);
		btnNewButton_1_1_3_1_3_3.setBounds(10, 413, 85, 30);
		DisciplineStudent.add(btnNewButton_1_1_3_1_3_3);

		JScrollPane scrollPane_StudentCautat_1 = new JScrollPane();
		scrollPane_StudentCautat_1.setBounds(38, 35, 500, 340);
		DisciplineStudent.add(scrollPane_StudentCautat_1);

		JTextArea textAreaSituatieStudent = new JTextArea();
		scrollPane_StudentCautat_1.setViewportView(textAreaSituatieStudent);

		JButton btnNoteStudent_1 = new JButton("Note");
		btnNoteStudent_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteTabelStudent(tableNoteStudent);
				cl.show(contentPane, "Note Student");
			}
		});
		btnNoteStudent_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNoteStudent_1.setFocusable(false);
		btnNoteStudent_1.setBounds(481, 413, 85, 30);
		DisciplineStudent.add(btnNoteStudent_1);

		JButton btnNoteStudent_1_1 = new JButton("Prezențe");
		btnNoteStudent_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prezenteTabelStudent(tablePrezenteStudent, nrMatricolCautat);
				cl.show(contentPane, "Prezențe Student");
			}
		});
		btnNoteStudent_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNoteStudent_1_1.setFocusable(false);
		btnNoteStudent_1_1.setBounds(369, 413, 102, 30);
		DisciplineStudent.add(btnNoteStudent_1_1);

		JPanel LoginStudent = new JPanel();
		contentPane.add(LoginStudent, "Login Student");
		LoginStudent.setLayout(null);

		JLabel lblEmailStudentLogin = new JLabel("Email");
		lblEmailStudentLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmailStudentLogin.setBounds(57, 60, 52, 30);
		LoginStudent.add(lblEmailStudentLogin);

		textFieldEmailStudentLogin = new JTextField();
		textFieldEmailStudentLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldEmailStudentLogin.setColumns(10);
		textFieldEmailStudentLogin.setBounds(168, 63, 180, 24);
		LoginStudent.add(textFieldEmailStudentLogin);

		JLabel lblParolaStudent = new JLabel("Parolă");
		lblParolaStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolaStudent.setBounds(57, 115, 52, 30);
		LoginStudent.add(lblParolaStudent);

		passwordFieldStudent = new JPasswordField();
		passwordFieldStudent.setBounds(168, 118, 180, 24);
		LoginStudent.add(passwordFieldStudent);

		JButton btnLoginStudent = new JButton("Log in");
		btnLoginStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldEmailStudentLogin.getText().isBlank())
					JOptionPane.showInternalMessageDialog(null, "Introduceți adresa de email!",
							"Eroare la căutare student", JOptionPane.WARNING_MESSAGE, null);
				else if (passwordFieldStudent.getPassword().length == 0)
					JOptionPane.showInternalMessageDialog(null, "Introduceți parola!", "Eroare la căutare student",
							JOptionPane.WARNING_MESSAGE, null);

				else
					try {
						if (cautareStudentEmail(textFieldEmailStudentLogin.getText())) {
							nrMatricolCautat = nrMatricolStudent(textFieldEmailStudentLogin.getText(),
									new String(passwordFieldStudent.getPassword()));
							if (nrMatricolCautat == -1)
								JOptionPane.showInternalMessageDialog(null, "Parolă incorectă!",
										"Eroare la autentificare student", JOptionPane.WARNING_MESSAGE, null);
							else {
								textFieldEmailStudentLogin.setText("");
								passwordFieldStudent.setText("");
								cautareStudent(String.valueOf(nrMatricolCautat), false);
								cl.show(contentPane, "Meniu Student");
							}
						} else {
							JOptionPane.showInternalMessageDialog(null, "Nu există nici un student cu acest email!",
									"Eroare la autentificare student", JOptionPane.WARNING_MESSAGE, null);
						}
					} catch (ExceptieUtilizator e1) {
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(),
								"Eroare la căutarea în baza de date!", JOptionPane.WARNING_MESSAGE, null);
						e1.printStackTrace();
					}
			}
		});
		btnLoginStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		btnLoginStudent.setFocusable(false);
		btnLoginStudent.setBounds(454, 413, 112, 30);
		LoginStudent.add(btnLoginStudent);

		JButton btnNewButton_1_1_3_1_3_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Inceput");
			}
		});
		btnNewButton_1_1_3_1_3_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_1.setBounds(10, 413, 85, 30);
		LoginStudent.add(btnNewButton_1_1_3_1_3_1);

		JPanel MeniuModificareParolaStudent = new JPanel();
		contentPane.add(MeniuModificareParolaStudent, "Meniu Modificare Parolă Student");
		MeniuModificareParolaStudent.setLayout(null);

		JButton btnNewButton_1_1_3_1_3_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student");
			}
		});
		btnNewButton_1_1_3_1_3_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_2.setFocusable(false);
		btnNewButton_1_1_3_1_3_2.setBounds(10, 413, 85, 30);
		MeniuModificareParolaStudent.add(btnNewButton_1_1_3_1_3_2);

		passwordFieldParolaNouaConf = new JPasswordField();
		passwordFieldParolaNouaConf.setBounds(168, 118, 180, 24);
		MeniuModificareParolaStudent.add(passwordFieldParolaNouaConf);

		JLabel lblConfirmareParol = new JLabel("Confirmare Parolă");
		lblConfirmareParol.setFont(new Font("Arial", Font.PLAIN, 15));
		lblConfirmareParol.setBounds(28, 115, 130, 30);
		MeniuModificareParolaStudent.add(lblConfirmareParol);

		JLabel lblParolNou = new JLabel("Parolă nouă");
		lblParolNou.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolNou.setBounds(40, 56, 91, 30);
		MeniuModificareParolaStudent.add(lblParolNou);

		passwordFieldParolaNoua = new JPasswordField();
		passwordFieldParolaNoua.setBounds(168, 59, 180, 24);
		MeniuModificareParolaStudent.add(passwordFieldParolaNoua);

		JButton btnSalvareParStud = new JButton("Salvare");
		btnSalvareParStud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String parolaNoua = new String(passwordFieldParolaNoua.getPassword());
				String parolaNouaConf = new String(passwordFieldParolaNouaConf.getPassword());
				if (parolaNoua.isBlank())
					JOptionPane.showInternalMessageDialog(null, "Introduceți o parolă!", "Eroare modifcare parolă",
							JOptionPane.WARNING_MESSAGE, null);
				else if (parolaNoua.compareTo(parolaNouaConf) != 0)
					JOptionPane.showInternalMessageDialog(null, "Câmpurile nu coincid!!", "Eroare modifcare parolă",
							JOptionPane.WARNING_MESSAGE, null);
				else {
					try {
						modificareParolaStudent(parolaNoua);
						JOptionPane.showInternalMessageDialog(null, "Parolă modificată cu succes!", "Modificare parolă",
								JOptionPane.INFORMATION_MESSAGE, null);
						cl.show(contentPane, "Meniu Student");
					} catch (ExceptieStudent e1) {
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(),
								"Eroare la căutarea în baza de date!", JOptionPane.WARNING_MESSAGE, null);
						e1.printStackTrace();
					}
				}

			}
		});
		btnSalvareParStud.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSalvareParStud.setFocusable(false);
		btnSalvareParStud.setBounds(481, 413, 85, 30);
		MeniuModificareParolaStudent.add(btnSalvareParStud);

		JPanel MeniuStudent = new JPanel();
		contentPane.add(MeniuStudent, "Meniu Student");
		MeniuStudent.setLayout(null);

		JButton btnNewButton_1_1_3_1_3 = new JButton("Deconectare");
		btnNewButton_1_1_3_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Login Student");
			}
		});
		btnNewButton_1_1_3_1_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3.setFocusable(false);
		btnNewButton_1_1_3_1_3.setBounds(10, 413, 119, 30);
		MeniuStudent.add(btnNewButton_1_1_3_1_3);

		JButton btnNModParolaStud = new JButton("Modifcare parolă");
		btnNModParolaStud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Modificare Parolă Student");
			}
		});
		btnNModParolaStud.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNModParolaStud.setFocusable(false);
		btnNModParolaStud.setBounds(421, 413, 145, 30);
		MeniuStudent.add(btnNModParolaStud);

		JButton btnDatePersonale = new JButton("Date personale");
		btnDatePersonale.setFont(new Font("Arial", Font.PLAIN, 15));
		btnDatePersonale.setFocusable(false);
		btnDatePersonale.setBounds(225, 64, 150, 30);
		MeniuStudent.add(btnDatePersonale);

		JButton btnSituaieDiscipline = new JButton("Situație discipline");
		btnSituaieDiscipline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaSituatieStudent.setText(informatiiStudent(studentCautat));
				cl.show(contentPane, "Discipline Student");
			}
		});
		btnSituaieDiscipline.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSituaieDiscipline.setFocusable(false);
		btnSituaieDiscipline.setBounds(225, 124, 150, 30);
		MeniuStudent.add(btnSituaieDiscipline);

		JButton btnModStudent = new JButton("Student");
		btnModStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Login Student");
			}
		});
		btnModStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		btnModStudent.setFocusable(false);
		btnModStudent.setBounds(225, 64, 150, 30);
		MeniuInceput.add(btnModStudent);

		JButton btnModProfesor = new JButton("Profesor");
		btnModProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Login Profesor");
			}
		});
		btnModProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		btnModProfesor.setFocusable(false);
		btnModProfesor.setBounds(225, 124, 150, 30);
		MeniuInceput.add(btnModProfesor);

		JButton btnModAdministratie = new JButton("Secretară");
		btnModAdministratie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Login Secretară");
			}
		});
		btnModAdministratie.setFont(new Font("Arial", Font.PLAIN, 15));
		btnModAdministratie.setFocusable(false);
		btnModAdministratie.setBounds(225, 184, 150, 30);
		MeniuInceput.add(btnModAdministratie);

		JPanel LoginSecretara = new JPanel();
		contentPane.add(LoginSecretara, "Login Secretară");
		LoginSecretara.setLayout(null);

		JLabel lblParolaSecretariat = new JLabel("Parolă");
		lblParolaSecretariat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolaSecretariat.setBounds(57, 60, 52, 30);
		LoginSecretara.add(lblParolaSecretariat);

		passwordFieldSecretara = new JPasswordField();
		passwordFieldSecretara.setBounds(168, 63, 180, 24);
		LoginSecretara.add(passwordFieldSecretara);

		JButton btnNewButton_1_1_3_1_3_1_1_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_3_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Inceput");
			}
		});
		btnNewButton_1_1_3_1_3_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_1_1_2.setFocusable(false);
		btnNewButton_1_1_3_1_3_1_1_2.setBounds(10, 413, 85, 30);
		LoginSecretara.add(btnNewButton_1_1_3_1_3_1_1_2);

		JButton btnLoginStudent_1 = new JButton("Log in");
		btnLoginStudent_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (parolaAdministatrieOk(new String(passwordFieldSecretara.getPassword())))
				{
					passwordFieldSecretara.setText("");
					cl.show(contentPane, "Meniu Secretară");
				}
				else
					JOptionPane.showInternalMessageDialog(null, "Parolă incorectă!",
							"Eroare la autentificare secretariat", JOptionPane.WARNING_MESSAGE, null);
			}
		});
		btnLoginStudent_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnLoginStudent_1.setFocusable(false);
		btnLoginStudent_1.setBounds(454, 413, 112, 30);
		LoginSecretara.add(btnLoginStudent_1);

		JPanel ModificareParolaSecretariat = new JPanel();
		contentPane.add(ModificareParolaSecretariat, "Modificare Parolă Secretariat");
		ModificareParolaSecretariat.setLayout(null);

		JLabel lblParolaNouaSecretariat = new JLabel("Parolă nouă");
		lblParolaNouaSecretariat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolaNouaSecretariat.setBounds(40, 56, 91, 30);
		ModificareParolaSecretariat.add(lblParolaNouaSecretariat);

		passwordFieldParolaNouaSecretariat = new JPasswordField();
		passwordFieldParolaNouaSecretariat.setBounds(168, 59, 180, 24);
		ModificareParolaSecretariat.add(passwordFieldParolaNouaSecretariat);

		JLabel lblConfirmareParolaNouaSecretariat = new JLabel("Confirmare Parolă");
		lblConfirmareParolaNouaSecretariat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblConfirmareParolaNouaSecretariat.setBounds(28, 115, 130, 30);
		ModificareParolaSecretariat.add(lblConfirmareParolaNouaSecretariat);

		passwordFieldConfirmareParolaNouaSecretariat = new JPasswordField();
		passwordFieldConfirmareParolaNouaSecretariat.setBounds(168, 118, 180, 24);
		ModificareParolaSecretariat.add(passwordFieldConfirmareParolaNouaSecretariat);

		JButton btnSalvareParSecr = new JButton("Salvare");
		btnSalvareParSecr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String parolaNoua = new String(passwordFieldParolaNouaSecretariat.getPassword());
				String parolaNouaConf = new String(passwordFieldConfirmareParolaNouaSecretariat.getPassword());
				if (parolaNoua.isBlank())
					JOptionPane.showInternalMessageDialog(null, "Introduceți o parolă!", "Eroare modifcare parolă",
							JOptionPane.WARNING_MESSAGE, null);
				else if (parolaNoua.compareTo(parolaNouaConf) != 0)
					JOptionPane.showInternalMessageDialog(null, "Câmpurile nu coincid!!", "Eroare modifcare parolă",
							JOptionPane.WARNING_MESSAGE, null);
				else {
					try {
						modificareParolaSecretariat(parolaNoua);
						JOptionPane.showInternalMessageDialog(null, "Parolă modificată cu succes!", "Modificare parolă",
								JOptionPane.INFORMATION_MESSAGE, null);
						cl.show(contentPane, "Meniu Secretară");
					} catch (ExceptieUtilizator e1) {
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(),
								"Eroare la căutarea în baza de date!", JOptionPane.WARNING_MESSAGE, null);
						e1.printStackTrace();
					}
				}
			}
		});
		btnSalvareParSecr.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSalvareParSecr.setFocusable(false);
		btnSalvareParSecr.setBounds(481, 413, 85, 30);
		ModificareParolaSecretariat.add(btnSalvareParSecr);

		JButton btnNewButton_1_3 = new JButton("Înapoi");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Secretară");
			}
		});
		btnNewButton_1_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_3.setFocusable(false);
		btnNewButton_1_3.setBounds(10, 413, 85, 30);
		ModificareParolaSecretariat.add(btnNewButton_1_3);

		JPanel MeniuSecretara = new JPanel();
		contentPane.add(MeniuSecretara, "Meniu Secretară");
		MeniuSecretara.setLayout(null);

		JButton btnNewButton = new JButton("Meniu Studen\u021Bi");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton.setBounds(225, 64, 150, 30);
		btnNewButton.setFocusable(false);
		MeniuSecretara.add(btnNewButton);

		JButton btnMeniuDiscipline = new JButton("Meniu Discipline");
		btnMeniuDiscipline.setFont(new Font("Arial", Font.PLAIN, 15));
		btnMeniuDiscipline.setFocusable(false);
		btnMeniuDiscipline.setBounds(225, 124, 150, 30);
		MeniuSecretara.add(btnMeniuDiscipline);

		JButton btnMeniuProfesori = new JButton("Meniu profesori");

		btnMeniuProfesori.setFont(new Font("Arial", Font.PLAIN, 15));
		btnMeniuProfesori.setFocusable(false);
		btnMeniuProfesori.setBounds(225, 184, 150, 30);
		MeniuSecretara.add(btnMeniuProfesori);

		JButton btnNewButton_1_1_3_1_3_1_1_1_1 = new JButton("Deconectare");
		btnNewButton_1_1_3_1_3_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Login Secretară");
			}
		});
		btnNewButton_1_1_3_1_3_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_3_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_3_1_1_1_1.setBounds(10, 413, 119, 30);
		MeniuSecretara.add(btnNewButton_1_1_3_1_3_1_1_1_1);

		JButton btnNModParolaStud_1 = new JButton("Modifcare parolă");
		btnNModParolaStud_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Modificare Parolă Secretariat");
			}
		});
		btnNModParolaStud_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNModParolaStud_1.setFocusable(false);
		btnNModParolaStud_1.setBounds(421, 413, 145, 30);
		MeniuSecretara.add(btnNModParolaStud_1);

		JPanel AdaugareProfesor = new JPanel();
		contentPane.add(AdaugareProfesor, "Adăugare Profesor");
		AdaugareProfesor.setLayout(null);

		JLabel lblCNPProfesorNou = new JLabel("CNP");
		lblCNPProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCNPProfesorNou.setBounds(57, 44, 37, 30);
		AdaugareProfesor.add(lblCNPProfesorNou);

		textFieldCNPProfesorNou = new JTextField();
		textFieldCNPProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldCNPProfesorNou.setColumns(10);
		textFieldCNPProfesorNou.setBounds(168, 47, 180, 24);
		AdaugareProfesor.add(textFieldCNPProfesorNou);

		JLabel lblNumeProfesorNou = new JLabel("Nume");
		lblNumeProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeProfesorNou.setBounds(57, 84, 52, 30);
		AdaugareProfesor.add(lblNumeProfesorNou);

		textFieldNumeProfesorNou = new JTextField();
		textFieldNumeProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNumeProfesorNou.setColumns(10);
		textFieldNumeProfesorNou.setBounds(168, 87, 180, 24);
		AdaugareProfesor.add(textFieldNumeProfesorNou);

		JLabel lblPreumeProfesorNou = new JLabel("Preume");
		lblPreumeProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreumeProfesorNou.setBounds(57, 124, 63, 30);
		AdaugareProfesor.add(lblPreumeProfesorNou);

		textFieldPreumeProfesorNou = new JTextField();
		textFieldPreumeProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldPreumeProfesorNou.setColumns(10);
		textFieldPreumeProfesorNou.setBounds(168, 127, 180, 24);
		AdaugareProfesor.add(textFieldPreumeProfesorNou);

		JLabel lblTelefonProfesorNou = new JLabel("Telefon");
		lblTelefonProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefonProfesorNou.setBounds(57, 164, 63, 30);
		AdaugareProfesor.add(lblTelefonProfesorNou);

		textFieldTelefonProfesorNou = new JTextField();
		textFieldTelefonProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldTelefonProfesorNou.setColumns(10);
		textFieldTelefonProfesorNou.setBounds(168, 167, 180, 24);
		AdaugareProfesor.add(textFieldTelefonProfesorNou);

		JLabel lblEmailProfesorNou = new JLabel("Email");
		lblEmailProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmailProfesorNou.setBounds(57, 204, 52, 30);
		AdaugareProfesor.add(lblEmailProfesorNou);

		textFieldEmailProfesorNou = new JTextField();
		textFieldEmailProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldEmailProfesorNou.setColumns(10);
		textFieldEmailProfesorNou.setBounds(168, 207, 180, 24);
		AdaugareProfesor.add(textFieldEmailProfesorNou);

		JLabel lblAdresaProfesorNou = new JLabel("Adresă");
		lblAdresaProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAdresaProfesorNou.setBounds(57, 244, 63, 30);
		AdaugareProfesor.add(lblAdresaProfesorNou);

		textFieldAdresaProfesorNou = new JTextField();
		textFieldAdresaProfesorNou.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldAdresaProfesorNou.setColumns(10);
		textFieldAdresaProfesorNou.setBounds(168, 247, 180, 24);
		AdaugareProfesor.add(textFieldAdresaProfesorNou);

		JButton btnNewButton_1_1_3_1_2_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_2_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Profesori");
			}
		});
		btnNewButton_1_1_3_1_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_2_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_2_1_1_1.setBounds(10, 413, 85, 30);
		AdaugareProfesor.add(btnNewButton_1_1_3_1_2_1_1_1);

		List<JTextField> listaInformatiiProfesorAdaugare = new ArrayList<JTextField>();
		listaInformatiiProfesorAdaugare.add(textFieldCNPProfesorNou);
		listaInformatiiProfesorAdaugare.add(textFieldNumeProfesorNou);
		listaInformatiiProfesorAdaugare.add(textFieldPreumeProfesorNou);
		listaInformatiiProfesorAdaugare.add(textFieldTelefonProfesorNou);
		listaInformatiiProfesorAdaugare.add(textFieldEmailProfesorNou);
		listaInformatiiProfesorAdaugare.add(textFieldAdresaProfesorNou);

		JButton btnAdaugareProfesor = new JButton("Adăugare");
		btnAdaugareProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Utilizator p = new Utilizator();
				try {
					p.setareInformatii(listaInformatiiProfesorAdaugare, CNPUnicProfesor, false);
					inserareProfesor(p);

					JOptionPane.showInternalMessageDialog(null, "Profesor adăugat cu succes", "Adăugare profesor",
							JOptionPane.INFORMATION_MESSAGE, null);
					parolaGenerata.setText("Parolă : " + parolaProfAdaugat(textFieldCNPProfesorNou.getText()));
					JOptionPane.showMessageDialog(null, parolaGenerata, "Adăugare profesor",
							JOptionPane.INFORMATION_MESSAGE, null);
					clearTextFields(listaInformatiiProfesorAdaugare);

				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare profesor",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnAdaugareProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdaugareProfesor.setFocusable(false);
		btnAdaugareProfesor.setBounds(454, 413, 112, 30);
		AdaugareProfesor.add(btnAdaugareProfesor);

		JPanel ModificareProfesorSelectat = new JPanel();
		contentPane.add(ModificareProfesorSelectat, "Modificare Profesor Selectat");
		ModificareProfesorSelectat.setLayout(null);
		
		JLabel lblCNPProfesorSelectat = new JLabel("CNP");
		lblCNPProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCNPProfesorSelectat.setBounds(57, 44, 37, 30);
		ModificareProfesorSelectat.add(lblCNPProfesorSelectat);
		
		textFieldCNPProfesorSelectat = new JTextField();
		textFieldCNPProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldCNPProfesorSelectat.setColumns(10);
		textFieldCNPProfesorSelectat.setBounds(168, 47, 180, 24);
		ModificareProfesorSelectat.add(textFieldCNPProfesorSelectat);
		
		JLabel lblNumeProfesorSelectat = new JLabel("Nume");
		lblNumeProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeProfesorSelectat.setBounds(57, 84, 52, 30);
		ModificareProfesorSelectat.add(lblNumeProfesorSelectat);
		
		textFieldNumeProfesorSelectat = new JTextField();
		textFieldNumeProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNumeProfesorSelectat.setColumns(10);
		textFieldNumeProfesorSelectat.setBounds(168, 87, 180, 24);
		ModificareProfesorSelectat.add(textFieldNumeProfesorSelectat);
		
		JLabel lblPreumeProfesorSelectat = new JLabel("Preume");
		lblPreumeProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreumeProfesorSelectat.setBounds(57, 124, 63, 30);
		ModificareProfesorSelectat.add(lblPreumeProfesorSelectat);
		
		textFieldPrenumeProfesorSelectat = new JTextField();
		textFieldPrenumeProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldPrenumeProfesorSelectat.setColumns(10);
		textFieldPrenumeProfesorSelectat.setBounds(168, 127, 180, 24);
		ModificareProfesorSelectat.add(textFieldPrenumeProfesorSelectat);
		
		JLabel lblTelefonProfesorSelectat = new JLabel("Telefon");
		lblTelefonProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefonProfesorSelectat.setBounds(57, 164, 63, 30);
		ModificareProfesorSelectat.add(lblTelefonProfesorSelectat);
		
		textFieldTelefonProfesorSelectat = new JTextField();
		textFieldTelefonProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldTelefonProfesorSelectat.setColumns(10);
		textFieldTelefonProfesorSelectat.setBounds(168, 167, 180, 24);
		ModificareProfesorSelectat.add(textFieldTelefonProfesorSelectat);
		
		JLabel lblEmailProfesorSelectat = new JLabel("Email");
		lblEmailProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmailProfesorSelectat.setBounds(57, 204, 52, 30);
		ModificareProfesorSelectat.add(lblEmailProfesorSelectat);
		
		textFieldEmailProfesorSelectat = new JTextField();
		textFieldEmailProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldEmailProfesorSelectat.setColumns(10);
		textFieldEmailProfesorSelectat.setBounds(168, 207, 180, 24);
		ModificareProfesorSelectat.add(textFieldEmailProfesorSelectat);
		
		JLabel lblAdresProfesorSelectat = new JLabel("Adresă");
		lblAdresProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAdresProfesorSelectat.setBounds(57, 244, 63, 30);
		ModificareProfesorSelectat.add(lblAdresProfesorSelectat);
		
		textFieldAdresaProfesorSelectat = new JTextField();
		textFieldAdresaProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldAdresaProfesorSelectat.setColumns(10);
		textFieldAdresaProfesorSelectat.setBounds(168, 247, 180, 24);
		ModificareProfesorSelectat.add(textFieldAdresaProfesorSelectat);
		
		JButton btnNewButton_1_1_3_1_2_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cl.show(contentPane,"Meniu Profesor Selectat");
			}
		});
		btnNewButton_1_1_3_1_2_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_2_2.setFocusable(false);
		btnNewButton_1_1_3_1_2_2.setBounds(10, 413, 85, 30);
		ModificareProfesorSelectat.add(btnNewButton_1_1_3_1_2_2);
		
		List<JTextField> listaInformatiiProfesorModificare = new ArrayList<JTextField>();
		listaInformatiiProfesorModificare.add(textFieldCNPProfesorSelectat);
		listaInformatiiProfesorModificare.add(textFieldNumeProfesorSelectat);
		listaInformatiiProfesorModificare.add(textFieldPrenumeProfesorSelectat);
		listaInformatiiProfesorModificare.add(textFieldTelefonProfesorSelectat);
		listaInformatiiProfesorModificare.add(textFieldEmailProfesorSelectat);
		listaInformatiiProfesorModificare.add(textFieldAdresaProfesorSelectat);
		
		JButton btnSalvareModifcariProfesorSelectat = new JButton("Salvare Modificări");
		btnSalvareModifcariProfesorSelectat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					profesorCautat.setareInformatii(listaInformatiiProfesorModificare, CNPUnicProfesor,
							Objects.equals(listaInformatiiProfesorModificare.get(0).getText(), cnpCautat));
					modificareProfesorSelectat();
					cl.show(contentPane, "Meniu Profesor Selectat");
					clearTextFields(listaInformatiiProfesorModificare);
					JOptionPane.showInternalMessageDialog(null, "Profesor modificat cu success", "Modificare profesor",
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare modificare date profesor",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnSalvareModifcariProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSalvareModifcariProfesorSelectat.setFocusable(false);
		btnSalvareModifcariProfesorSelectat.setBounds(409, 413, 157, 30);
		ModificareProfesorSelectat.add(btnSalvareModifcariProfesorSelectat);
		
		JPanel ParolaProfesorSelectat = new JPanel();
		contentPane.add(ParolaProfesorSelectat, "Parolă Profesor Selectat");
		ParolaProfesorSelectat.setLayout(null);
		
		JLabel lblParolStudentProfesorSelectat = new JLabel("Parolă profesor");
		lblParolStudentProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolStudentProfesorSelectat.setBounds(37, 56, 105, 30);
		ParolaProfesorSelectat.add(lblParolStudentProfesorSelectat);
		
		textFieldParolaProfesorSelectat = new JTextField();
		textFieldParolaProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldParolaProfesorSelectat.setEditable(false);
		textFieldParolaProfesorSelectat.setColumns(10);
		textFieldParolaProfesorSelectat.setBounds(168, 59, 180, 24);
		ParolaProfesorSelectat.add(textFieldParolaProfesorSelectat);
		
		JButton btnNewButton_1_1_3_1_4_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cl.show(contentPane,"Meniu Profesor Selectat");
			}
		});
		btnNewButton_1_1_3_1_4_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_4_1.setFocusable(false);
		btnNewButton_1_1_3_1_4_1.setBounds(10, 413, 85, 30);
		ParolaProfesorSelectat.add(btnNewButton_1_1_3_1_4_1);
		
		JPanel AtribuireDisciplineProfesor = new JPanel();
		contentPane.add(AtribuireDisciplineProfesor, "Atribuire Discipline Profesor");
		AtribuireDisciplineProfesor.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(68, 54, 112, 170);
		AtribuireDisciplineProfesor.add(scrollPane_3);
		
		JList<String> listDisciplinePosibilePorf = new JList();
		listDisciplinePosibilePorf.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_3.setViewportView(listDisciplinePosibilePorf);
		
		JLabel lblNewLabel_5 = new JLabel("Discipline");
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_3.setColumnHeaderView(lblNewLabel_5);
		
		JButton btnNewButton_1_1_3_1_1_1_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cl.show(contentPane,"Meniu Profesor Selectat");
			}
		});
		btnNewButton_1_1_3_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1_1_2.setFocusable(false);
		btnNewButton_1_1_3_1_1_1_2.setBounds(10, 413, 85, 30);
		AtribuireDisciplineProfesor.add(btnNewButton_1_1_3_1_1_1_2);
		
		JButton btnAtribuireDiscipline_1 = new JButton("Atribuire Discipline");
		btnAtribuireDiscipline_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				List<String> disciplineNoi = listDisciplinePosibilePorf.getSelectedValuesList();
				try {
					if (listDisciplinePosibilePorf.getSelectedValue() == null)
						JOptionPane.showInternalMessageDialog(null, "Selectați o disciplină",
								"Eroare adăugare disciplină", JOptionPane.WARNING_MESSAGE, null);
					else {
						for(String d:disciplineNoi)
							atribuireProfesorDisciplina(idProfesorCautat,disciplinePosibileAtribuireID.get(d));
							
						JOptionPane.showInternalMessageDialog(null, "Discipline atribuite cu success",
								"Atribuire discipline", JOptionPane.INFORMATION_MESSAGE, null);
						cl.show(contentPane, "Meniu Profesor Selectat");
					}
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare atribuire disciplină",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}	
		});
		btnAtribuireDiscipline_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAtribuireDiscipline_1.setFocusable(false);
		btnAtribuireDiscipline_1.setBounds(399, 413, 167, 30);
		AtribuireDisciplineProfesor.add(btnAtribuireDiscipline_1);
		
		JPanel DisciplinePredateProfesorSelectat = new JPanel();
		contentPane.add(DisciplinePredateProfesorSelectat, "Discipline Predate Profesor Selectat");
		DisciplinePredateProfesorSelectat.setLayout(null);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(68, 54, 112, 170);
		DisciplinePredateProfesorSelectat.add(scrollPane_1_1);
		
		JList<String> listDisciplinePredateProfesorSelectat = new JList();
		listDisciplinePredateProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_1_1.setViewportView(listDisciplinePredateProfesorSelectat);
		
		JLabel lblNewLabel_6 = new JLabel("Discipline");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_1_1.setColumnHeaderView(lblNewLabel_6);
		
		JButton btnNewButton_1_1_3_1_1_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cl.show(contentPane,"Meniu Profesor Selectat");
			}
		});
		btnNewButton_1_1_3_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_1_1_1_1.setBounds(10, 413, 85, 30);
		DisciplinePredateProfesorSelectat.add(btnNewButton_1_1_3_1_1_1_1_1);
		
		JButton btnStergereDisciplineProfesorSelectat = new JButton("Ștergere Discipline");
		btnStergereDisciplineProfesorSelectat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> disciplineSterse = listDisciplinePredateProfesorSelectat.getSelectedValuesList();
				disciplineSterse.forEach(d -> {
					try {
						stergereDisciplinaProfesor(idProfesorCautat, disciplinePredateProfesorSelectatID.get(d));
					} catch (ExceptieDisciplina e1) {
						JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare ștergere disciplină",
								JOptionPane.WARNING_MESSAGE, null);
					}
				});
				JOptionPane.showInternalMessageDialog(null, "Discipline înlăturate cu success", "Înlăturare discipline",
						JOptionPane.INFORMATION_MESSAGE, null);
				cl.show(contentPane, "Meniu Profesor Selectat");
			}
		});
		btnStergereDisciplineProfesorSelectat.setFont(new Font("Arial", Font.PLAIN, 15));
		btnStergereDisciplineProfesorSelectat.setFocusable(false);
		btnStergereDisciplineProfesorSelectat.setBounds(399, 413, 167, 30);
		DisciplinePredateProfesorSelectat.add(btnStergereDisciplineProfesorSelectat);
		
		JPanel MeniuProfesorSelectat = new JPanel();
		contentPane.add(MeniuProfesorSelectat, "Meniu Profesor Selectat");
		MeniuProfesorSelectat.setLayout(null);

		JButton btnNewButton_1_1_3_1_5 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Selectare Profesor");
			}
		});
		btnNewButton_1_1_3_1_5.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_5.setFocusable(false);
		btnNewButton_1_1_3_1_5.setBounds(10, 413, 85, 30);
		MeniuProfesorSelectat.add(btnNewButton_1_1_3_1_5);

		JButton btnNewButton_1_1_1_1_1_1_1_1 = new JButton("Parolă");
		btnNewButton_1_1_1_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					textFieldParolaProfesorSelectat.setText(parolaProfesorSelectat());
					cl.show(contentPane, "Parolă Profesor Selectat");
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare parolă profesor",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_1_1_1_1_1_1.setBounds(245, 413, 75, 30);
		MeniuProfesorSelectat.add(btnNewButton_1_1_1_1_1_1_1_1);

		JButton btnNewButton_1_1_1_1_1_1_2 = new JButton("Date personale");
		btnNewButton_1_1_1_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				textFieldCNPProfesorSelectat.setText(profesorCautat.getCnp());
				textFieldNumeProfesorSelectat.setText(profesorCautat.getNume());
				textFieldPrenumeProfesorSelectat.setText(profesorCautat.getPrenume());
				textFieldTelefonProfesorSelectat.setText(profesorCautat.getNrTelefon());
				textFieldEmailProfesorSelectat.setText(profesorCautat.getEmail());
				textFieldAdresaProfesorSelectat.setText(profesorCautat.getAdresa());
				cl.show(contentPane,"Modificare Profesor Selectat");
			}
		});
		btnNewButton_1_1_1_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_1_1_1_2.setFocusable(false);
		btnNewButton_1_1_1_1_1_1_2.setBounds(330, 413, 135, 30);
		MeniuProfesorSelectat.add(btnNewButton_1_1_1_1_1_1_2);

		JButton btnNewButton_1_1_1_1_2 = new JButton("Ștergere");
		btnNewButton_1_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stergereProfesor();
					tabelProfesori(tableSelectareProfesori);
					cl.show(contentPane, "Selectare Profesor");
					JOptionPane.showInternalMessageDialog(null, "Profesor șters cu success", "Ștergere profesor",
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare ștergere profesor",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_1_2.setFocusable(false);
		btnNewButton_1_1_1_1_2.setBounds(475, 413, 91, 30);
		MeniuProfesorSelectat.add(btnNewButton_1_1_1_1_2);
		
		JButton btnAtribuireDiscipline = new JButton("Atribuire Discipline");
		btnAtribuireDiscipline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					actualizareDisciplinePosibileAtribuireProfesor();
					listDisciplinePosibilePorf.setModel(disciplinePosibileAtribuireDLM);
					cl.show(contentPane, "Atribuire Discipline Profesor");

				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare discipline",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnAtribuireDiscipline.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAtribuireDiscipline.setFocusable(false);
		btnAtribuireDiscipline.setBounds(215, 66, 170, 30);
		MeniuProfesorSelectat.add(btnAtribuireDiscipline);
		
		JButton btnDisciplinePredate = new JButton("Discipline Predate");
		btnDisciplinePredate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					actualizareDisciplinePredateProfesorSelectat();
					listDisciplinePredateProfesorSelectat.setModel(disciplinePredateProfesorSelectatDLM);
					listDisciplinePredateProfesorSelectat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					cl.show(contentPane, "Discipline Predate Profesor Selectat");
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare discipline",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnDisciplinePredate.setFont(new Font("Arial", Font.PLAIN, 15));
		btnDisciplinePredate.setFocusable(false);
		btnDisciplinePredate.setBounds(215, 116, 170, 30);
		MeniuProfesorSelectat.add(btnDisciplinePredate);

		JPanel SelectareProfesor = new JPanel();
		contentPane.add(SelectareProfesor, "Selectare Profesor");
		SelectareProfesor.setLayout(null);

		JButton btnNewButton_1_4_1 = new JButton("Înapoi");
		btnNewButton_1_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Profesori");
			}
		});
		btnNewButton_1_4_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_4_1.setFocusable(false);
		btnNewButton_1_4_1.setBounds(10, 413, 85, 30);
		SelectareProfesor.add(btnNewButton_1_4_1);

		JScrollPane scrollPane_SelectareProfesor = new JScrollPane();
		scrollPane_SelectareProfesor.setBounds(31, 50, 507, 298);
		SelectareProfesor.add(scrollPane_SelectareProfesor);

		tableSelectareProfesori = new JTable();
		tableSelectareProfesori.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_SelectareProfesor.setViewportView(tableSelectareProfesori);

		JButton btnSelectareProfesor_1 = new JButton("Selectare");
		btnSelectareProfesor_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableSelectareProfesori.getSelectedRow() == -1)
					JOptionPane.showInternalMessageDialog(null, "Selectați un profesor!", "Eroare selectare profesor",
							JOptionPane.WARNING_MESSAGE, null);
				else {
					idProfesorCautat = (int) tableSelectareProfesori
							.getValueAt(tableSelectareProfesori.getSelectedRow(), 0);
					try {
						cautareProfesor(Integer.toString(idProfesorCautat),false);
					} catch (ExceptieUtilizator e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					cl.show(contentPane, "Meniu Profesor Selectat");
				}
			}
		});
		btnSelectareProfesor_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSelectareProfesor_1.setFocusable(false);
		btnSelectareProfesor_1.setBounds(463, 413, 103, 30);
		SelectareProfesor.add(btnSelectareProfesor_1);

		JPanel MeniuProfesori = new JPanel();
		contentPane.add(MeniuProfesori, "Meniu Profesori");
		MeniuProfesori.setLayout(null);

		JButton btnNewButton_1_4 = new JButton("Înapoi");
		btnNewButton_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Secretară");
			}
		});
		btnNewButton_1_4.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_4.setFocusable(false);
		btnNewButton_1_4.setBounds(10, 413, 85, 30);
		MeniuProfesori.add(btnNewButton_1_4);

		JButton btnAdugareProfesor = new JButton("Adăugare Profesori");
		btnAdugareProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Adăugare Profesor");
			}
		});
		btnAdugareProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareProfesor.setFocusable(false);
		btnAdugareProfesor.setBounds(215, 66, 170, 30);
		MeniuProfesori.add(btnAdugareProfesor);

		JButton btnSelectareProfesor = new JButton("Selectare Profesor");
		btnSelectareProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabelProfesori(tableSelectareProfesori);
				cl.show(contentPane, "Selectare Profesor");
			}
		});
		btnSelectareProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSelectareProfesor.setFocusable(false);
		btnSelectareProfesor.setBounds(215, 116, 170, 30);
		MeniuProfesori.add(btnSelectareProfesor);

		JPanel MeniuStudenti = new JPanel();
		contentPane.add(MeniuStudenti, "MeniuStudenți");
		MeniuStudenti.setLayout(null);

		btnMeniuProfesori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Profesori");
			}
		});

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

		cl.show(contentPane, "Meniu Inceput");

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
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Da", "Nu" }));
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
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Student p = new Student();
				try {
					p.setareInformatii(listaInformatiiStudentAdaugare, CNPUnicStudent, false);
					p.setAngajat(comboBox.getSelectedIndex() == 1 ? false : true);
					inserareStudent(p);

					JOptionPane.showInternalMessageDialog(null, "Student adăugat cu succes", "Adăugare student",
							JOptionPane.INFORMATION_MESSAGE, null);
					parolaGenerata.setText("Parolă : " + parolaStudentAdaugat(textField_CNP.getText()));
					JOptionPane.showMessageDialog(null, parolaGenerata, "Adăugare student",
							JOptionPane.INFORMATION_MESSAGE, null);
					clearTextFields(listaInformatiiStudentAdaugare);
					comboBox.setSelectedIndex(1);

				} catch (ExceptieUtilizator e1) {
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
		btnNewButton_1_2_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		comboBox_1_1.setModel(new DefaultComboBoxModel<String>(new String[] { "Da", "Nu" }));
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
		btnNewButton_1_1_1_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

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
				} catch (ExceptieDisciplina e1) {
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
		btnNewButton_1_2_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "CăutareDisciplină");
			}
		});
		btnNewButton_1_2_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_2_1_1_1_1.setFocusable(false);
		btnNewButton_1_2_1_1_1_1.setBounds(10, 413, 85, 30);
		MeniuDisciplinaCautata.add(btnNewButton_1_2_1_1_1_1);

		JButton btnNewButton_1_1_1_2_1_1 = new JButton("Ștergere");
		btnNewButton_1_1_1_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stergereDisciplina(numeDCautat);
					cl.show(contentPane, "CăutareDisciplină");
					JOptionPane.showInternalMessageDialog(null, "Disciplină ștearsă cu succes", "Ștergere disciplină",
							JOptionPane.INFORMATION_MESSAGE, null);

				} catch (ExceptieDisciplina e1) {
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
		btnNewButton_1_1_1_2_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnNewButton_1_2_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnNewButton_1_1_1_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nume = textField_DiscCaut.getText();
				try {
					if (!cautareDisciplina(nume))
						JOptionPane.showInternalMessageDialog(null, "Nu am găsit disciplina cu numele " + nume,
								"Eroare căutare disciplină", JOptionPane.WARNING_MESSAGE, null);
					else {
						textArea_DisciplinaCautata.setText(informatiiDisiplina(disciplinaCautata));
						cl.show(contentPane, "MeniuDisciplinăCăutată");
					}
				} catch (ExceptieDisciplina e1) {
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
		btnAdugareDisciplin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "AdăugareDisciplină");
			}
		});
		btnAdugareDisciplin.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareDisciplin.setFocusable(false);
		btnAdugareDisciplin.setBounds(215, 66, 170, 30);
		MeniuDiscipline.add(btnAdugareDisciplin);

		JButton btnCutareDisciplin = new JButton("Căutare Disciplină");
		btnCutareDisciplin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnNewButton_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] { "Da", "Nu" }));
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
		btnNewButton_1_1_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Disciplina d = new Disciplina();
				try {
					d.setareInformatii(listaInformatiiDisciplinaAdaugare, disciplinaUnica, false);
					d.setBonus(comboBox_1.getSelectedIndex() == 1 ? false : true);
					inserareDisciplina(d);
					clearTextFields(listaInformatiiDisciplinaAdaugare);
					comboBox_1.setSelectedIndex(1);
					JOptionPane.showInternalMessageDialog(null, "Disciplină adăugată cu success", "Adăugare disciplină",
							JOptionPane.INFORMATION_MESSAGE, null);

				} catch (ExceptieDisciplina e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare disciplină",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnNewButton_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_2.setFocusable(false);
		btnNewButton_1_1_1_2.setBounds(454, 413, 112, 30);
		AdaugareDisciplina.add(btnNewButton_1_1_1_2);

		JPanel CautareStudent = new JPanel();
		contentPane.add(CautareStudent, "CăutareStudent");
		CautareStudent.setLayout(null);

		JPanel ParolaStudentCautat = new JPanel();
		contentPane.add(ParolaStudentCautat, "Parolă Student Căutat");
		ParolaStudentCautat.setLayout(null);

		JButton btnNewButton_1_1_3_1_4 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldParolaStudentCautat.setText("");
				cl.show(contentPane, "MeniuStudentCăutat");
			}
		});
		btnNewButton_1_1_3_1_4.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_4.setFocusable(false);
		btnNewButton_1_1_3_1_4.setBounds(10, 413, 85, 30);
		ParolaStudentCautat.add(btnNewButton_1_1_3_1_4);

		JLabel lblParolStudent = new JLabel("Parolă student");
		lblParolStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParolStudent.setBounds(40, 56, 102, 30);
		ParolaStudentCautat.add(lblParolStudent);

		textFieldParolaStudentCautat = new JTextField();
		textFieldParolaStudentCautat.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldParolaStudentCautat.setBounds(168, 59, 180, 24);
		ParolaStudentCautat.add(textFieldParolaStudentCautat);
		textFieldParolaStudentCautat.setColumns(10);
		textFieldParolaStudentCautat.setEditable(false);

		JPanel MeniuStudentCautat = new JPanel();
		contentPane.add(MeniuStudentCautat, "MeniuStudentCăutat");
		MeniuStudentCautat.setLayout(null);

		JPanel ModificareStudentCautat = new JPanel();
		contentPane.add(ModificareStudentCautat, "DatePStudentCăutat");
		ModificareStudentCautat.setLayout(null);

		JButton btnNewButton_1_1_3_1_2 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] { "Da", "Nu" }));
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
		btnNewButton_1_1_3_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "MeniuDisciplineStudent");
			}
		});
		btnNewButton_1_1_3_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_1_1_1.setBounds(10, 413, 85, 30);
		StergereDisciplineStudent.add(btnNewButton_1_1_3_1_1_1_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(68, 54, 112, 170);
		StergereDisciplineStudent.add(scrollPane_1);

		JList<String> list_1 = new JList<String>();
		scrollPane_1.setViewportView(list_1);
		list_1.setFont(new Font("Arial", Font.PLAIN, 15));

		JLabel lblNewLabel_3_1 = new JLabel("Discipline");
		lblNewLabel_3_1.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_1.setColumnHeaderView(lblNewLabel_3_1);

		JButton btnNewButton_1_1_1_2_2_1 = new JButton("Ștergere Discipline");
		btnNewButton_1_1_1_2_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> disciplineSterse = list_1.getSelectedValuesList();
				disciplineSterse.forEach(d -> {
					try {
						stergereStudentDisciplina(nrMatricolCautat, disciplinePosibileStergereID.get(d));
					} catch (ExceptieDisciplina e1) {
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
		scrollPane.setBounds(68, 54, 112, 170);
		AdaugareDisciplineStudent.add(scrollPane);

		JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setFont(new Font("Arial", Font.PLAIN, 15));

		JLabel lblNewLabel_3 = new JLabel("Discipline");
		scrollPane.setColumnHeaderView(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 15));

		JList listProfesoriDisciplina = new JList();
		JButton btnNewButton_1_1_3_1_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listProfesoriDisciplina.setModel(new DefaultListModel<String>());
				cl.show(contentPane, "MeniuDisciplineStudent");
			}
		});
		btnNewButton_1_1_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_1_1.setBounds(10, 413, 85, 30);
		AdaugareDisciplineStudent.add(btnNewButton_1_1_3_1_1_1);
		JTextArea textArea_StudentCautat = new JTextArea();
		JButton btnNewButton_1_1_1_2_2 = new JButton("Adăugare Discipline");
		btnNewButton_1_1_1_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> disciplineNoi = list.getSelectedValuesList();
				try {
					if (list.getSelectedValue() == null)
						JOptionPane.showInternalMessageDialog(null, "Selectați o disciplină",
								"Eroare adăugare disciplină", JOptionPane.WARNING_MESSAGE, null);
					else if (listProfesoriDisciplina.getSelectedValue() == null)
						JOptionPane.showInternalMessageDialog(null, "Selectați un profesor",
								"Eroare adăugare disciplină", JOptionPane.WARNING_MESSAGE, null);
					else {
						inscriereStudentDisciplina(nrMatricolCautat,
								disciplinePosibileAdaugareID.get(list.getSelectedValue()),
								profesoriDisciplinePosibileAdaugareID.get(listProfesoriDisciplina.getSelectedValue()));
						JOptionPane.showInternalMessageDialog(null, "Discipline adăugate cu success",
								"Adăugare discipline", JOptionPane.INFORMATION_MESSAGE, null);
						cl.show(contentPane, "MeniuDisciplineStudent");
					}
				} catch (ExceptieStudent e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare adăugare disciplină",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});
		btnNewButton_1_1_1_2_2.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_2_2.setFocusable(false);
		btnNewButton_1_1_1_2_2.setBounds(399, 413, 167, 30);
		AdaugareDisciplineStudent.add(btnNewButton_1_1_1_2_2);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(252, 54, 210, 170);
		AdaugareDisciplineStudent.add(scrollPane_2);

		listProfesoriDisciplina.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listProfesoriDisciplina.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_2.setViewportView(listProfesoriDisciplina);

		JLabel lblNewLabel_4 = new JLabel("Profesor");
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane_2.setColumnHeaderView(lblNewLabel_4);

		JButton btnNewButton_1_1_3_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_StudentCautat.setText(informatiiStudent(studentCautat));
				cl.show(contentPane, "MeniuStudentCăutat");
			}
		});
		btnNewButton_1_1_3_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_1.setBounds(10, 413, 85, 30);
		MeniuDisciplineStudent.add(btnNewButton_1_1_3_1_1);

		JButton btnAdugareDiscipline = new JButton("Adăugare Discipline");
		btnAdugareDiscipline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actualizareDisciplinePosibileAdaugareStudent();
					list.setModel(disciplinePosibileAdaugareDLM);
					listProfesoriDisciplina.setModel(new DefaultListModel<>());
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					cl.show(contentPane, "AdăugareDisciplineStudent");

				} catch (ExceptieStudent e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare discipline",
							JOptionPane.WARNING_MESSAGE, null);
				}

			}
		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				try {
					if (list.getSelectedValue() != null)
						listProfesoriDisciplina.setModel(actualizareProfesoriDisciplinePosibileAdaugareStudent(
								disciplinePosibileAdaugareID.get(list.getSelectedValue())));
				} catch (ExceptieStudent e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnAdugareDiscipline.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAdugareDiscipline.setFocusable(false);
		btnAdugareDiscipline.setBounds(215, 66, 170, 30);
		MeniuDisciplineStudent.add(btnAdugareDiscipline);

		JButton btntergereDiscipline = new JButton("Ștergere Discipline");
		btntergereDiscipline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actualizareDisciplinePosibileStergereStudent();
					list_1.setModel(disciplinePosibileStergereDLM);
					list_1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					cl.show(contentPane, "ȘtergereDisciplineStudent");

				} catch (ExceptieStudent e1) {
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
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stergereStudent(nrMatricolCautat);
					cl.show(contentPane, "CăutareStudent");
					JOptionPane.showInternalMessageDialog(null, "Student șters cu success", "Ștergere student",
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ExceptieStudent e1) {
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
		btnNewButton_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

		JButton btnNewButton_1_1_1_1_1_1_1 = new JButton("Parolă");
		btnNewButton_1_1_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textFieldParolaStudentCautat.setText(parolaStudentCautat());
					cl.show(contentPane, "Parolă Student Căutat");
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare parolă student",
							JOptionPane.WARNING_MESSAGE, null);
				}
				
			}
		});
		btnNewButton_1_1_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_1_1_1_1.setFocusable(false);
		btnNewButton_1_1_1_1_1_1_1.setBounds(136, 413, 75, 30);
		MeniuStudentCautat.add(btnNewButton_1_1_1_1_1_1_1);

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
		comboBox_CautareStudent.setModel(new DefaultComboBoxModel<String>(new String[] { "CNP", "Nr. Matricol" }));
		comboBox_CautareStudent.setSelectedIndex(0);
		comboBox_CautareStudent.setBounds(40, 49, 105, 21);
		CautareStudent.add(comboBox_CautareStudent);

		JButton btnNewButton_1_1_1_3 = new JButton("Căutare");
		btnNewButton_1_1_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringCautat = textField_StringC.getText();
				String optiuneC = ((String) comboBox_CautareStudent.getSelectedItem()).trim();
				boolean cnpO = optiuneC.equals("CNP");
				try {
					if (!cautareStudent(stringCautat, cnpO))
						JOptionPane.showInternalMessageDialog(null,
								"Nu am găsit studentul cu " + (cnpO ? "CNP-ul " : "Nr.Matricol ") + stringCautat,
								"Eroare căutare student", JOptionPane.WARNING_MESSAGE, null);
					else {
						textArea_StudentCautat.setText(informatiiStudent(studentCautat));
						cl.show(contentPane, "MeniuStudentCăutat");
					}
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare căutare student",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnNewButton_1_1_1_3.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_1_3.setFocusable(false);
		btnNewButton_1_1_1_3.setBounds(454, 413, 112, 30);
		CautareStudent.add(btnNewButton_1_1_1_3);

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "MeniuStudenți");
			}

		});

		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Secretară");
			}

		});

		btnAdugareStudent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "AdăugareStudent");

			}
		});

		btnNewButton_1_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "MeniuStudenți");

			}
		});
		btnMeniuDiscipline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "MeniuDiscipline");
			}
		});
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Secretară");
			}
		});
		btnNewButton_1_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "MeniuStudenți");
			}
		});
		btnDisciplineStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "CăutareStudent");
			}
		});
		btnNewButton_1_1_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

		JPanel DatePersonaleProfesor = new JPanel();
		DatePersonaleProfesor.setLayout(null);
		contentPane.add(DatePersonaleProfesor, "Date Personale Profesor");

		JLabel lblCNPProfesor = new JLabel("CNP");
		lblCNPProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCNPProfesor.setBounds(57, 44, 37, 30);
		DatePersonaleProfesor.add(lblCNPProfesor);

		CNPProfesor = new JTextField();
		CNPProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		CNPProfesor.setEditable(false);
		CNPProfesor.setColumns(10);
		CNPProfesor.setBounds(168, 47, 180, 24);
		DatePersonaleProfesor.add(CNPProfesor);

		JLabel lblNumeProfesor = new JLabel("Nume");
		lblNumeProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeProfesor.setBounds(57, 84, 52, 30);
		DatePersonaleProfesor.add(lblNumeProfesor);

		NumeProfesor = new JTextField();
		NumeProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		NumeProfesor.setEditable(false);
		NumeProfesor.setColumns(10);
		NumeProfesor.setBounds(168, 87, 180, 24);
		DatePersonaleProfesor.add(NumeProfesor);

		JLabel lblPreumeProfesor = new JLabel("Preume");
		lblPreumeProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreumeProfesor.setBounds(57, 124, 63, 30);
		DatePersonaleProfesor.add(lblPreumeProfesor);

		PrenumeProfesor = new JTextField();
		PrenumeProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		PrenumeProfesor.setEditable(false);
		PrenumeProfesor.setColumns(10);
		PrenumeProfesor.setBounds(168, 127, 180, 24);
		DatePersonaleProfesor.add(PrenumeProfesor);

		JLabel lblTelefonProfesor = new JLabel("Telefon");
		lblTelefonProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefonProfesor.setBounds(57, 164, 63, 30);
		DatePersonaleProfesor.add(lblTelefonProfesor);

		TelefonProfesor = new JTextField();
		TelefonProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		TelefonProfesor.setEditable(false);
		TelefonProfesor.setColumns(10);
		TelefonProfesor.setBounds(168, 167, 180, 24);
		DatePersonaleProfesor.add(TelefonProfesor);

		JLabel lblEmailProfesor = new JLabel("Email");
		lblEmailProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmailProfesor.setBounds(57, 204, 52, 30);
		DatePersonaleProfesor.add(lblEmailProfesor);

		EmailProfesor = new JTextField();
		EmailProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		EmailProfesor.setEditable(false);
		EmailProfesor.setColumns(10);
		EmailProfesor.setBounds(168, 207, 180, 24);
		DatePersonaleProfesor.add(EmailProfesor);

		JLabel lblAdresaProfesor = new JLabel("Adresă");
		lblAdresaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAdresaProfesor.setBounds(57, 244, 63, 30);
		DatePersonaleProfesor.add(lblAdresaProfesor);

		AdresaProfesor = new JTextField();
		AdresaProfesor.setFont(new Font("Arial", Font.PLAIN, 15));
		AdresaProfesor.setEditable(false);
		AdresaProfesor.setColumns(10);
		AdresaProfesor.setBounds(168, 247, 180, 24);
		DatePersonaleProfesor.add(AdresaProfesor);

		JButton btnNewButton_1_1_3_1_2_1_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Profesor");
			}
		});
		btnNewButton_1_1_3_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_2_1_1.setFocusable(false);
		btnNewButton_1_1_3_1_2_1_1.setBounds(10, 413, 85, 30);
		DatePersonaleProfesor.add(btnNewButton_1_1_3_1_2_1_1);

		JPanel DatePersonaleStudent = new JPanel();
		DatePersonaleStudent.setLayout(null);
		contentPane.add(DatePersonaleStudent, "Date Personale Student");

		JButton btnNewButton_1_1_3_1_2_1 = new JButton("Înapoi");
		btnNewButton_1_1_3_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Meniu Student");
			}
		});
		btnNewButton_1_1_3_1_2_1.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton_1_1_3_1_2_1.setFocusable(false);
		btnNewButton_1_1_3_1_2_1.setBounds(10, 413, 85, 30);
		DatePersonaleStudent.add(btnNewButton_1_1_3_1_2_1);

		JLabel lblCNPStudent = new JLabel("CNP");
		lblCNPStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCNPStudent.setBounds(57, 44, 37, 30);
		DatePersonaleStudent.add(lblCNPStudent);

		JLabel lblNumeStudent = new JLabel("Nume");
		lblNumeStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNumeStudent.setBounds(57, 84, 52, 30);
		DatePersonaleStudent.add(lblNumeStudent);

		JLabel lblPreumeStudent = new JLabel("Preume");
		lblPreumeStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreumeStudent.setBounds(57, 124, 63, 30);
		DatePersonaleStudent.add(lblPreumeStudent);

		JLabel lblTelefonStudent = new JLabel("Telefon");
		lblTelefonStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefonStudent.setBounds(57, 164, 63, 30);
		DatePersonaleStudent.add(lblTelefonStudent);

		JLabel lblEmailStudent = new JLabel("Email");
		lblEmailStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmailStudent.setBounds(57, 204, 52, 30);
		DatePersonaleStudent.add(lblEmailStudent);

		JLabel lblAdresStudent = new JLabel("Adresă");
		lblAdresStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAdresStudent.setBounds(57, 244, 63, 30);
		DatePersonaleStudent.add(lblAdresStudent);

		JLabel lblAngajatStudent = new JLabel("Sold an curent");
		lblAngajatStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAngajatStudent.setBounds(57, 284, 105, 30);
		DatePersonaleStudent.add(lblAngajatStudent);

		EmailStudent = new JTextField();
		EmailStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		EmailStudent.setColumns(10);
		EmailStudent.setBounds(168, 207, 180, 24);
		EmailStudent.setEditable(false);
		DatePersonaleStudent.add(EmailStudent);

		CNPStudent = new JTextField();
		CNPStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		CNPStudent.setColumns(10);
		CNPStudent.setBounds(168, 47, 180, 24);
		CNPStudent.setEditable(false);
		DatePersonaleStudent.add(CNPStudent);

		NumeStudent = new JTextField();
		NumeStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		NumeStudent.setColumns(10);
		NumeStudent.setBounds(168, 87, 180, 24);
		NumeStudent.setEditable(false);
		DatePersonaleStudent.add(NumeStudent);

		PrenumeStudent = new JTextField();
		PrenumeStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		PrenumeStudent.setColumns(10);
		PrenumeStudent.setBounds(168, 127, 180, 24);
		PrenumeStudent.setEditable(false);
		DatePersonaleStudent.add(PrenumeStudent);

		TelefonStudent = new JTextField();
		TelefonStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		TelefonStudent.setColumns(10);
		TelefonStudent.setBounds(168, 167, 180, 24);
		TelefonStudent.setEditable(false);
		DatePersonaleStudent.add(TelefonStudent);

		AdresaStudent = new JTextField();
		AdresaStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		AdresaStudent.setColumns(10);
		AdresaStudent.setBounds(168, 247, 180, 24);
		AdresaStudent.setEditable(false);
		DatePersonaleStudent.add(AdresaStudent);

		SoldStudent = new JTextField();
		SoldStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		SoldStudent.setColumns(10);
		SoldStudent.setBounds(168, 287, 180, 24);
		SoldStudent.setEditable(false);
		DatePersonaleStudent.add(SoldStudent);

		JComboBox<String> AngajatStudent = new JComboBox<String>();
		AngajatStudent.setModel(new DefaultComboBoxModel<String>(new String[] { "Da", "Nu" }));
		AngajatStudent.setSelectedIndex(1);
		AngajatStudent.setFont(new Font("Arial", Font.PLAIN, 15));
		AngajatStudent.setBounds(168, 329, 45, 21);
		AngajatStudent.setEnabled(false);
		DatePersonaleStudent.add(AngajatStudent);

		JLabel lblAngajatStudent_1 = new JLabel("Angajat ?");
		lblAngajatStudent_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAngajatStudent_1.setBounds(57, 324, 74, 30);
		DatePersonaleStudent.add(lblAngajatStudent_1);
		btnNewButton_1_1_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnNewButton_1_1_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					studentCautat.setareInformatii(listaInformatiiStudentModificare, CNPUnicStudent,
							Objects.equals(listaInformatiiStudentModificare.get(0).getText(), cnpCautat));
					studentCautat.setAngajat(comboBox_2.getSelectedIndex() == 0 ? true : false);
					modificareStudentCautat();
					textArea_StudentCautat.setText(informatiiStudent(studentCautat));
					cl.show(contentPane, "MeniuStudentCăutat");
					clearTextFields(listaInformatiiStudentModificare);
					comboBox_2.setSelectedIndex(1);
					JOptionPane.showInternalMessageDialog(null, "Student modificat cu success", "Modificare student",
							JOptionPane.INFORMATION_MESSAGE, null);
				} catch (ExceptieUtilizator e1) {
					JOptionPane.showInternalMessageDialog(null, e1.getMessage(), "Eroare modificare date student",
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnDatePersonale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CNPStudent.setText(studentCautat.getCnp());
				NumeStudent.setText(studentCautat.getNume());
				PrenumeStudent.setText(studentCautat.getPrenume());
				TelefonStudent.setText(studentCautat.getNrTelefon());
				EmailStudent.setText(studentCautat.getEmail());
				AdresaStudent.setText(studentCautat.getAdresa());
				SoldStudent.setText(String.valueOf(studentCautat.getSoldAnCurent()));
				AngajatStudent.setSelectedIndex(studentCautat.isAngajat() ? 0 : 1);
				cl.show(contentPane, "Date Personale Student");
			}
		});
	}

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			File error = new File("errors.log");
			FileOutputStream fosError;
			try {
				fosError = new FileOutputStream(error);
				PrintStream psError = new PrintStream(fosError);
				System.setErr(psError);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    File connectionFile = new File("connection.txt");
		    Scanner sc = new Scanner(connectionFile);
		    String url,usr,pswd;
		    url = sc.nextLine().split("=")[1];
		    usr = sc.nextLine().split("=")[1];
		    pswd = sc.nextLine().split("=")[1];
			con = DriverManager.getConnection("jdbc:oracle:thin:"+url,usr,pswd);
			String sqlinss = "INSERT INTO STUDENTI" + " VALUES (null,?,?,?,?,?,?,?,?,null)";
			inserareStudent = con.prepareStatement(sqlinss);
			String sqlinsp = "INSERT INTO PROFESORI VALUES (null,?,?,?,?,?,?,null)";
			inserareProfesor = con.prepareStatement(sqlinsp);
			String sqlselds = "SELECT NUME_DISCIPLINA, NUME_PROFESOR, PRENUME_PROFESOR FROM DISCIPLINE_STUDENTI_VIEW WHERE NR_MATRICOL = ?";
			disciplineInscriseStudent = con.prepareStatement(sqlselds);
			String sqlesv = "SELECT COUNT(*) FROM STUDENTI WHERE EMAIL = ?";
			String sqlselnrmlog = "SELECT NR_MATRICOL FROM STUDENTI WHERE EMAIL = ? AND PAROLA = ?";
			cautareStudentEMAIL = con.prepareStatement(sqlesv);
			cautareNrMatricolStudent = con.prepareStatement(sqlselnrmlog);
			String sqlepv = "SELECT COUNT(*) FROM PROFESORI WHERE EMAIL = ?";
			cautareProfesorEmail = con.prepareStatement(sqlepv);
			String sqlselidp = "SELECT ID FROM PROFESORI WHERE EMAIL = ? AND PAROLA = ?";
			cautareIdProfesor = con.prepareStatement(sqlselidp);
			String sqlselidprof = "SELECT * FROM PROFESORI WHERE ID = ?";
			cautareProfesorID = con.prepareStatement(sqlselidprof);
			String sqlupdpp = "UPDATE PROFESORI SET PAROLA = ? WHERE ID = ?";
			modificareParolaProfesor = con.prepareStatement(sqlupdpp);
			String sqlupdps = "UPDATE STUDENTI SET PAROLA = ? WHERE NR_MATRICOL = ?";
			modificareParolaStudent = con.prepareStatement(sqlupdps);
			String sqlupdpscr = "UPDATE PAROLA_SECRETARIAT SET PAROLA = ?";
			modificareParolaSecretariat = con.prepareStatement(sqlupdpscr);
			String sqlselcprf = "SELECT * FROM PROFESORI WHERE CNP = TRIM(?)";
			cautareProfesorCNP = con.prepareStatement(sqlselcprf);
			String sqlselcs = "SELECT * FROM STUDENTI WHERE CNP = TRIM(?) ";
			String sqlselnrms = "SELECT * FROM STUDENTI WHERE NR_MATRICOL = ?";
			cautareStudentNRM = con.prepareStatement(sqlselnrms);
			CNPUnicStudent = con.prepareStatement(sqlselcs);
			String sqlselcp = "SELECT * FROM PROFESORI WHERE CNP = TRIM(?)";
			CNPUnicProfesor = con.prepareStatement(sqlselcp);
			String sqldelst = "DELETE FROM STUDENTI WHERE NR_MATRICOL = ?";
			stergereStudent = con.prepareStatement(sqldelst);
			String sqldelpr = "DELETE FROM PROFESORI WHERE ID = ?";
			stergereProfesor = con.prepareStatement(sqldelpr);
			String sqlinsd = "INSERT INTO DISCIPLINE" + " VALUES (null,?,?,null,?,?,?,?)";
			inserareDisciplina = con.prepareStatement(sqlinsd);
			String sqlselnd = "SELECT * FROM DISCIPLINE WHERE NUME_DISCIPLINA = UPPER(TRIM(?))";
			disciplinaUnica = con.prepareStatement(sqlselnd);
			String sqlselpsc = "SELECT PAROLA FROM STUDENTI WHERE NR_MATRICOL = ?";
			parolaStudentCautat = con.prepareStatement(sqlselpsc);
			String sqlselppr = "SELECT PAROLA FROM PROFESORI WHERE ID = ?";
			parolaProfesorSelectat = con.prepareStatement(sqlselppr);
			String sqldeld = "DELETE FROM DISCIPLINE WHERE NUME_DISCIPLINA = UPPER(TRIM(?))";
			stergereDisciplina = con.prepareStatement(sqldeld);
			cautareStudentCNP = con.prepareStatement(sqlselcs);
			String sqlseldiscposa = "SELECT ID, NUME_DISCIPLINA " + "FROM DISCIPLINE " + "WHERE ID NOT IN " + "("
					+ "SELECT ID_D " + "FROM DISCIPLINE_STUDENTI " + "WHERE NR_MATRICOL = ?) ORDER BY NUME_DISCIPLINA";
			disciplinePosibileAdaugareStudent = con.prepareStatement(sqlseldiscposa);
			String sqlseldiscposap = "SELECT ID, NUME_DISCIPLINA FROM DISCIPLINE WHERE ID NOT IN (SELECT ID_D FROM DISCIPLINE_PROFESORI WHERE ID_P = ?) ORDER BY NUME_DISCIPLINA";
			disciplinePosibileAtribuireProfesor = con.prepareStatement(sqlseldiscposap);
			String sqlselpdiscposa = "SELECT ID_PROFESOR, NUME_PROFESOR, PRENUME_PROFESOR FROM DISCIPLINE_PROFESORI_VIEW WHERE ID_DISCIPLINA = ? ORDER BY NUME_PROFESOR";
			profesoriDisciplinePosibileAdaugareStudent = con.prepareStatement(sqlselpdiscposa);
			String sqlseldiscposs = "SELECT ID_D,NUME_DISCIPLINA FROM DISCIPLINE_STUDENTI_VIEW WHERE NR_MATRICOL = ? ORDER BY NUME_DISCIPLINA";
			disciplinePosibileStergereStudent = con.prepareStatement(sqlseldiscposs);
			String sqlseldiscpps = "SELECT ID_DISCIPLINA,NUME_DISCIPLINA FROM DISCIPLINE_PROFESORI_VIEW WHERE ID_PROFESOR = ? ORDER BY NUME_DISCIPLINA";
			disciplinePredateProfesorSelectat = con.prepareStatement(sqlseldiscpps);
			String sqlinscrs = "INSERT INTO DISCIPLINE_STUDENTI VALUES(?,?,?)";
			inscriereStudentDisciplina = con.prepareStatement(sqlinscrs);
			String sqlatrdp = "INSERT INTO DISCIPLINE_PROFESORI VALUES(?,?)";
			atribuireProfesorDisciplina = con.prepareStatement(sqlatrdp);
			String sqldelds = "DELETE FROM DISCIPLINE_STUDENTI WHERE NR_MATRICOL = ? AND ID_D = ?";
			stergereStudentDisciplina = con.prepareStatement(sqldelds);
			String sqldeldp = "DELETE FROM DISCIPLINE_PROFESORI WHERE ID_P = ? AND ID_D = ?";
			stergereDisciplinaProfesor = con.prepareStatement(sqldeldp);
			String sqlupds = "UPDATE STUDENTI SET CNP = ?, NUME = ?, PRENUME = ?, NRTEL = ?, EMAIL = ?, ANGAJAT = ?, SOLD = ?, ADRESA = ? "
					+ "WHERE NR_MATRICOL = ?";
			modificareStudent = con.prepareStatement(sqlupds);
			cautareDisciplina = con.prepareStatement(sqlselnd);
			String sqlupdp = "UPDATE PROFESORI SET CNP = ?, NUME = ?, PRENUME = ?, NRTEL = ?, EMAIL = ?, ADRESA = ? WHERE ID = ?";
			modificareProfesor = con.prepareStatement(sqlupdp);
			String sqlupdd = "UPDATE DISCIPLINE SET NUME_DISCIPLINA = ?, NR_TESTE = ?, PROCENT_TESTE = ?, PROCENT_EXAMEN = ?, BONUS = ?, PREZENTE_MINIME_NANG = ?"
					+ " WHERE NUME_DISCIPLINA = TRIM(UPPER(?))";
			modificareDisciplina = con.prepareStatement(sqlupdd);
			String sqlancr = "SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD') FROM DUAL";
			dataCurenta = con.prepareStatement(sqlancr);
			String sqlselstdisc = "SELECT COUNT(*) FROM DISCIPLINE_STUDENTI WHERE NR_MATRICOL = ? AND ID_D = ?";
			studentInscrisDisciplina = con.prepareStatement(sqlselstdisc);
			String sqlinsrprez = "INSERT INTO PREZENTE VALUES(null,?,?,?,?)";
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
			String sqlinsnsd = "INSERT INTO CATALOG_UNIVERSITATE VALUES (null,?,?,?,?,?,?)";
			inserareNota = con.prepareStatement(sqlinsnsd);
			String sqlselns = "SELECT ID_NOTA, DATA, NUME_DISCIPLINA, PUNCTAJ, TIP FROM NOTE_VIEW WHERE NR_MATRICOL = ? AND ID_D = ? AND ID_PROF = ?";
			noteStudent = con.prepareStatement(sqlselns);
			String sqlselnsg = "SELECT DATA, NUME_DISCIPLINA, PUNCTAJ, TIP, NUME_COMPLET_PROF FROM NOTE_VIEW WHERE NR_MATRICOL = ?";
			noteStudentGeneral = con.prepareStatement(sqlselnsg);
			String sqlseldp = "SELECT ID_DISCIPLINA,NUME_DISCIPLINA FROM DISCIPLINE_PROFESORI_VIEW WHERE ID_PROFESOR = ? ORDER BY NUME_DISCIPLINA";
			disciplineProfesor = con.prepareStatement(sqlseldp);
			String sqlselsdp = "SELECT NR_MATRICOL, NUME, PRENUME FROM DISCIPLINE_STUDENTI_VIEW WHERE ID_D = ? AND ID_PROFESOR = ? ORDER BY NUME,PRENUME";
			studentiDisciplineProfesor = con.prepareStatement(sqlselsdp);
			String sqlselpsdp = "SELECT ID_PREZ, DATA FROM PREZENTE_VIEW WHERE NR_MATRICOL = ? AND ID_PROF = ? AND ID_D = ? ORDER BY DATA";
			prezenteStudentDisciplinaProfesor = con.prepareStatement(sqlselpsdp);
			String sqlselps = "SELECT ID_PREZ, DATA, NUME_DISCIPLINA FROM PREZENTE_VIEW WHERE NR_MATRICOL = ? ORDER BY NUME_DISCIPLINA,DATA";
			prezenteStudent = con.prepareStatement(sqlselps);
			String sqldelp = "DELETE FROM PREZENTE WHERE ID_PREZ = ?";
			stergerePrezenta = con.prepareStatement(sqldelp);
			String sqldeln = "DELETE FROM CATALOG_UNIVERSITATE WHERE ID_NOTA = ?";
			stergereNota = con.prepareStatement(sqldeln);
			String sqlselpsd = "SELECT COUNT(*) FROM PREZENTE WHERE DATA = ? AND NR_MATRICOL = ? AND ID_D = ?";
			prezentaUnica = con.prepareStatement(sqlselpsd);
			String sqlselnsd = "SELECT COUNT (*) FROM CATALOG_UNIVERSITATE WHERE DATA = ? AND NR_MATRICOL = ? AND ID_D = ? AND TIP = ? AND ID_PROF = ?";
			notaUnica = con.prepareStatement(sqlselnsd);
			String sqlseldsv = "SELECT NUME_DISCIPLINA, PREZENTE_MINIME, PREZENTE_STUDENT, MEDIA, NUME_PROFESOR, PRENUME_PROFESOR, EMAIL_PROFESOR, NRTEL_PROFESOR FROM DISCIPLINE_STUDENTI_VIEW WHERE NR_MATRICOL = ?";
			disciplineStudent = con.prepareStatement(sqlseldsv);
			String sqlseldsps = "SELECT NUME, PRENUME, PREZENTE_MINIME, PREZENTE_STUDENT, MEDIA, EMAIL_STUDENT, NRTEL_STUDENT FROM DISCIPLINE_STUDENTI_VIEW WHERE NR_MATRICOL = ? AND ID_D = ? AND ID_PROFESOR = ?";
			disciplinaStudentProfesor = con.prepareStatement(sqlseldsps);
			String sqlselpad = "SELECT PAROLA FROM PAROLA_SECRETARIAT WHERE PAROLA = ?";
			parolaAdministatieOk = con.prepareStatement(sqlselpad);
			String sqlselparpa = "SELECT PAROLA FROM PROFESORI WHERE CNP = TRIM(?)";
			parolaProfAdaugat = con.prepareStatement(sqlselparpa);
			String sqlselparsa = "SELECT PAROLA FROM STUDENTI WHERE CNP = TRIM (?)";
			parolaStudentAdaugat = con.prepareStatement(sqlselparsa);
			String sqlselallp = "SELECT ID, NUME||' '||PRENUME, EMAIL FROM PROFESORI";
			listaProfesori = con.prepareStatement(sqlselallp);
		} catch (SQLException | FileNotFoundException e2) {
			e2.printStackTrace();
			System.exit(1);
		}

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
			JOptionPane.showInternalMessageDialog(null, "Nu s-a putut seta tema sistemului", "Eroare la setare temă",
					JOptionPane.WARNING_MESSAGE, null);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void inserareStudent(Student s) throws ExceptieStudent {
		try {
			inserareStudent.setString(1, s.getCnp());
			inserareStudent.setString(2, s.getNume());
			inserareStudent.setString(3, s.getPrenume());
			inserareStudent.setString(4, s.getNrTelefon());
			inserareStudent.setString(5, s.getEmail());
			inserareStudent.setString(6, s.getAdresa());
			inserareStudent.setDouble(7, s.getSoldAnCurent());
			inserareStudent.setInt(8, s.isAngajat() == true ? 1 : 0);
			inserareStudent.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la inserarea în baza de date!");
		}
	}

	private void inserareProfesor(Utilizator p) throws ExceptieUtilizator {
		try {
			inserareProfesor.setString(1, p.getCnp());
			inserareProfesor.setString(2, p.getNume());
			inserareProfesor.setString(3, p.getPrenume());
			inserareProfesor.setString(4, p.getNrTelefon());
			inserareProfesor.setString(5, p.getEmail());
			inserareProfesor.setString(6, p.getAdresa());
			inserareProfesor.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieUtilizator("Eroare la inserarea în baza de date!");
		}
	}

	private void modificareStudentCautat() throws ExceptieStudent {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la modificarea în baza de date!");
		}
	}
	
	private void modificareProfesorSelectat() throws ExceptieUtilizator {
		try {
			modificareProfesor.setString(1, profesorCautat.getCnp());
			modificareProfesor.setString(2, profesorCautat.getNume());
			modificareProfesor.setString(3, profesorCautat.getPrenume());
			modificareProfesor.setString(4, profesorCautat.getNrTelefon());
			modificareProfesor.setString(5, profesorCautat.getEmail());
			modificareProfesor.setString(6, profesorCautat.getAdresa());
			modificareProfesor.setInt(7, idProfesorCautat);

			modificareProfesor.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la modificarea în baza de date!");
		}
	}

	private void modificareDisciplinaCautata() throws ExceptieDisciplina {
		try {
			modificareDisciplina.setString(1, disciplinaCautata.getNume());
			modificareDisciplina.setInt(2, disciplinaCautata.getNrTeste());
			modificareDisciplina.setInt(3, disciplinaCautata.getProcentTeste());
			modificareDisciplina.setInt(4, disciplinaCautata.getProcentExamen());
			modificareDisciplina.setInt(5, disciplinaCautata.isBonus() ? 1 : 0);
			modificareDisciplina.setInt(6, disciplinaCautata.getPrezente());
			modificareDisciplina.setString(7, numeDCautat);

			modificareDisciplina.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la modificarea în baza de date!");
		}
	}

	private void stergereStudent(int nrm) throws ExceptieStudent {
		try {
			stergereStudent.setInt(1, nrm);
			stergereStudent.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la ștergerea din baza de date!");
		}
	}

	private void stergereProfesor() throws ExceptieUtilizator {
		try {
			stergereProfesor.setInt(1, idProfesorCautat);
			stergereProfesor.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieUtilizator("Eroare la ștergerea din baza de date!");
		}
	}

	private void stergereDisciplina(String nume) throws ExceptieDisciplina {
		try {
			stergereDisciplina.setString(1, nume);
			stergereDisciplina.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la ștergerea din baza de date!");
		}
	}

	private void inserareDisciplina(Disciplina d) throws ExceptieDisciplina {
		try {
			inserareDisciplina.setString(1, d.getNume());
			inserareDisciplina.setInt(2, d.getPrezente());
			inserareDisciplina.setInt(3, d.getNrTeste());
			inserareDisciplina.setInt(4, d.getProcentTeste());
			inserareDisciplina.setInt(5, d.getProcentExamen());
			inserareDisciplina.setInt(6, d.isBonus() == true ? 1 : 0);
			inserareDisciplina.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la inserarea în baza de date!");
		}
	}

	private boolean cautareStudent(String sc, boolean cnp) throws ExceptieUtilizator {
		sc = sc.trim();
		try {
			if (cnp) {
				cautareStudentCNP.setString(1, sc);
				rs = cautareStudentCNP.executeQuery();
			} else {
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

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		} catch (NumberFormatException | NullPointerException e) {
			throw new ExceptieStudent("Nr.Matricol este invalid!");
		}
	}

	private boolean cautareProfesor(String sc, boolean cnp) throws ExceptieUtilizator {
		sc = sc.trim();
		try {
			if (cnp) {
				cautareProfesorCNP.setString(1, sc);
				rs = cautareProfesorCNP.executeQuery();
			} else {
				cautareProfesorID.setInt(1, Integer.parseInt(sc));
				rs = cautareProfesorID.executeQuery();
			}
			if (!rs.isBeforeFirst())
				return false;
			rs.next();
			idProfesorCautat = rs.getInt(1);
			cnpCautat = rs.getString(2);

			resultSetToUtilizator(profesorCautat, rs, CNPUnicProfesor);
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieUtilizator("Eroare la căutarea în baza de date!");
		} catch (NumberFormatException | NullPointerException e) {
			throw new ExceptieUtilizator("Nr.Matricol este invalid!");
		}
	}

	private boolean cautareDisciplina(String nume) throws ExceptieDisciplina {
		try {
			cautareDisciplina.setString(1, nume);
			rs = cautareDisciplina.executeQuery();
			if (!rs.isBeforeFirst())
				return false;
			rs.next();
			idDCautat = rs.getInt(1);
			numeDCautat = rs.getString(2).toUpperCase().trim();
			resultSetToDisciplina(disciplinaCautata, rs);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la căutarea în baza de date!");
		}
	}

	private void actualizareDisciplinePosibileAdaugareStudent() throws ExceptieStudent {
		try {
			disciplinePosibileAdaugareDLM = new DefaultListModel<String>();
			disciplinePosibileAdaugareStudent.setInt(1, nrMatricolCautat);
			rs = disciplinePosibileAdaugareStudent.executeQuery();
			disciplinePosibileAdaugareID.clear();
			String nume;
			int id;
			while (rs.next()) {
				id = rs.getInt(1);
				nume = rs.getString(2);
				disciplinePosibileAdaugareDLM.addElement(nume);
				disciplinePosibileAdaugareID.put(nume, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		}
	}
	
	private void actualizareDisciplinePosibileAtribuireProfesor() throws ExceptieUtilizator {
		try {
			disciplinePosibileAtribuireDLM = new DefaultListModel<String>();
			disciplinePosibileAtribuireProfesor.setInt(1, idProfesorCautat);
			rs = disciplinePosibileAtribuireProfesor.executeQuery();
			disciplinePosibileAtribuireID.clear();
			String nume;
			int id;
			while (rs.next()) {
				id = rs.getInt(1);
				nume = rs.getString(2);
				disciplinePosibileAtribuireDLM.addElement(nume);
				disciplinePosibileAtribuireID.put(nume, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieUtilizator("Eroare la căutarea în baza de date!");
		}
	}

	private DefaultListModel<String> actualizareProfesoriDisciplinePosibileAdaugareStudent(int idd)
			throws ExceptieStudent {
		try {
			profesoriDisciplinePosibileAdaugareDLM = new DefaultListModel<String>();
			profesoriDisciplinePosibileAdaugareStudent.setInt(1, idd);
			rs = profesoriDisciplinePosibileAdaugareStudent.executeQuery();
			profesoriDisciplinePosibileAdaugareID.clear();
			String nume, prenume;
			int id;
			while (rs.next()) {
				id = rs.getInt(1);
				nume = rs.getString(2);
				prenume = rs.getString(3);
				profesoriDisciplinePosibileAdaugareDLM.addElement(nume + " " + prenume);
				profesoriDisciplinePosibileAdaugareID.put(nume + " " + prenume, id);
			}
			return profesoriDisciplinePosibileAdaugareDLM;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		}
	}

	private void actualizareDisciplinePosibileStergereStudent() throws ExceptieStudent {
		try {
			disciplinePosibileStergereDLM = new DefaultListModel<String>();
			disciplinePosibileStergereStudent.setInt(1, nrMatricolCautat);
			rs = disciplinePosibileStergereStudent.executeQuery();
			disciplinePosibileStergereID.clear();
			String nume;
			int id;
			while (rs.next()) {
				id = rs.getInt(1);
				nume = rs.getString(2);
				disciplinePosibileStergereDLM.addElement(nume);
				disciplinePosibileStergereID.put(nume, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		}
	}
	private void actualizareDisciplinePredateProfesorSelectat() throws ExceptieUtilizator {
		try {
			disciplinePredateProfesorSelectatDLM = new DefaultListModel<String>();
			disciplinePredateProfesorSelectat.setInt(1, idProfesorCautat);
			rs = disciplinePredateProfesorSelectat.executeQuery();
			disciplinePredateProfesorSelectatID.clear();
			String nume;
			int id;
			while (rs.next()) {
				id = rs.getInt(1);
				nume = rs.getString(2);
				disciplinePredateProfesorSelectatDLM.addElement(nume);
				disciplinePredateProfesorSelectatID.put(nume, id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieUtilizator("Eroare la căutarea în baza de date!");
		}
	}

	private void inscriereStudentDisciplina(int nrm, int idd, int idp) throws ExceptieStudent {
		try {
			inscriereStudentDisciplina.setInt(1, nrm);
			inscriereStudentDisciplina.setInt(2, idd);
			inscriereStudentDisciplina.setInt(3, idp);
			inscriereStudentDisciplina.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieStudent("Eroare la inserarea în baza de date!");
		}
	}
	private void atribuireProfesorDisciplina(int idp, int idd) throws ExceptieUtilizator {
		try {
			atribuireProfesorDisciplina.setInt(1, idp);
			atribuireProfesorDisciplina.setInt(2, idd);
			atribuireProfesorDisciplina.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieUtilizator("Eroare la inserarea în baza de date!");
		}
	}

	private void stergereStudentDisciplina(int nrm, int idd) throws ExceptieDisciplina {
		try {
			stergereStudentDisciplina.setInt(1, nrm);
			stergereStudentDisciplina.setInt(2, idd);
			stergereStudentDisciplina.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la ștergerea din baza de date!");
		}
	}
	private void stergereDisciplinaProfesor(int idp, int idd) throws ExceptieDisciplina {
		try {
			stergereDisciplinaProfesor.setInt(1, idp);
			stergereDisciplinaProfesor.setInt(2, idd);
			stergereDisciplinaProfesor.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptieDisciplina("Eroare la ștergerea din baza de date!");
		}
	}

	private void resultSetToUtilizator(Utilizator s, ResultSet rs, PreparedStatement cnp)
			throws ExceptieUtilizator, SQLException {
		s.setID(rs.getInt(1));
		s.setCNP(rs.getString(2), cnp, true);
		s.setNume(rs.getString(3));
		s.setPrenume(rs.getString(4));
		s.setNrTelefon(rs.getString(5));
		s.setEmail(rs.getString(6));
		s.setAdresa(rs.getString(7));

	}

	private void resultSetToStudent(Student s, ResultSet rs) throws ExceptieUtilizator {
		try {
			resultSetToUtilizator(s, rs, CNPUnicStudent);
			s.setSold(String.valueOf(rs.getDouble(8)));
			s.setAngajat(rs.getInt(9) == 0 ? false : true);

		} catch (SQLException | ExceptieStudent e) {
			e.printStackTrace();
		}
	}

	private void resultSetToDisciplina(Disciplina d, ResultSet rs) {
		try {

			d.setNume(rs.getString(2), disciplinaUnica, true);
			d.setNrTeste(String.valueOf(rs.getInt(5)));
			d.setProcentTeste(String.valueOf(rs.getInt(6)));
			d.setProcentExamen(String.valueOf(rs.getInt(7)));
			d.setBonus(rs.getInt(8) == 0 ? false : true);
			d.setPrezente(String.valueOf(rs.getInt(3)));
		} catch (SQLException | ExceptieDisciplina e) {

			e.printStackTrace();
		}
	}

	private void clearTextFields(List<JTextField> ltf) {
		ltf.forEach(tf -> {
			tf.setText("");
		});
	}

	private DefaultComboBoxModel<Integer> dataCurenta() {
		DefaultComboBoxModel<Integer> listaAni = new DefaultComboBoxModel<Integer>();
		try {
			rs = dataCurenta.executeQuery();
			rs.next();
			String[] data = rs.getString(1).split("-");
			int an = Integer.parseInt(data[0]);
			int luna = Integer.parseInt(data[1]);
			int zi = Integer.parseInt(data[2]);
			dataSistem = LocalDate.of(an, luna, zi);
			for (int i = an; i >= 1980; i--)
				listaAni.addElement(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaAni;
	}

	private DefaultComboBoxModel<Integer> actualizareZile(int y, int m) {
		DefaultComboBoxModel<Integer> zile = new DefaultComboBoxModel<Integer>();
		int ultimaZi = YearMonth.of(y, m).lengthOfMonth();
		for (int i = 1; i <= ultimaZi; i++)
			zile.addElement(i);
		return zile;
	}

	private boolean studentInscrisDisciplina(int nrm, int idd) {
		try {
			studentInscrisDisciplina.setInt(1, nrm);
			studentInscrisDisciplina.setInt(2, idd);
			rs = studentInscrisDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void inserarePrezenta(LocalDate data, int nrm, int idd) throws Exception {
		try {
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
			inserarePrezenta.setInt(4, idProfesorCautat);
			inserarePrezenta.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean areExamen(int nrm, int idd) {
		try {
			examenStudentDisciplina.setInt(1, nrm);
			examenStudentDisciplina.setInt(2, idd);
			rs = examenStudentDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private boolean bonusDisciplina(int idd) {
		try {
			bonusDisciplina.setInt(1, idd);
			rs = bonusDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) == 1 ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean maximTeste(int nrm, int idd) {
		try {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private boolean maximPrezente(int nrm, int idd) {
		try {
			numarPrezenteStudentDisciplina.setInt(1, nrm);
			numarPrezenteStudentDisciplina.setInt(2, idd);

			rs = numarPrezenteStudentDisciplina.executeQuery();
			rs.next();
			return rs.getInt(1) >= 14;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void inserareNota(LocalDate data, int nrm, int idd, float punctaj, String tip, int idp) throws Exception {
		try {
			if (data.isAfter(dataSistem))
				throw new Exception("Data prezenței nu poate fi din viitor!");

			notaUnica.setObject(1, data);
			notaUnica.setInt(2, nrm);
			notaUnica.setInt(3, idd);
			notaUnica.setString(4, tip);
			notaUnica.setInt(5, idp);
			rs = notaUnica.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0)
				throw new Exception("Studentul are deja o astfel de notă la disciplina respectivă în acea dată!");

			inserareNota.setObject(1, data);
			inserareNota.setInt(2, nrm);
			inserareNota.setInt(3, idd);
			inserareNota.setDouble(4, punctaj);
			inserareNota.setString(5, tip);
			inserareNota.setInt(6, idp);
			inserareNota.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void stergerePrezenta(int idp) {
		try {
			stergerePrezenta.setInt(1, idp);
			stergerePrezenta.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void stergereNota(int idn) {
		try {
			stergereNota.setInt(1, idn);
			stergereNota.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean cautareStudentEmail(String email) throws ExceptieStudent {
		email = email.trim();

		try {
			cautareStudentEMAIL.setString(1, email);
			rs = cautareStudentEMAIL.executeQuery();
			if (!rs.isBeforeFirst())
				return false;
			rs.next();
			return rs.getInt(1) > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		}
	}

	private boolean cautareProfesorEmail(String email) throws ExceptieProfesor {
		email = email.trim();

		try {
			cautareProfesorEmail.setString(1, email);
			rs = cautareProfesorEmail.executeQuery();
			if (!rs.isBeforeFirst())
				return false;
			rs.next();
			return rs.getInt(1) > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ExceptieProfesor("Eroare la căutarea în baza de date!");
		}
	}

	private int nrMatricolStudent(String email, String parola) throws ExceptieStudent {
		email = email.trim();
		parola = parola.trim();

		try {
			cautareNrMatricolStudent.setString(1, email);
			cautareNrMatricolStudent.setString(2, parola);
			rs = cautareNrMatricolStudent.executeQuery();
			if (!rs.isBeforeFirst())
				return -1;
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new ExceptieStudent("Eroare la căutarea în baza de date!");
		}

	}

	private int idProfesor(String email, String parola) throws ExceptieProfesor {
		email = email.trim();
		parola = parola.trim();

		try {
			cautareIdProfesor.setString(1, email);
			cautareIdProfesor.setString(2, parola);
			rs = cautareIdProfesor.executeQuery();
			if (!rs.isBeforeFirst())
				return -1;
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new ExceptieProfesor("Eroare la căutarea în baza de date!");
		}

	}

	private void modificareParolaStudent(String parola) throws ExceptieStudent {
		try {
			modificareParolaStudent.setString(1, parola);
			modificareParolaStudent.setInt(2, nrMatricolCautat);
			modificareParolaStudent.executeUpdate();
		} catch (SQLException e) {
			throw new ExceptieStudent("Eroare la accesarea bazei de date!");
		}
	}

	private void modificareParolaSecretariat(String parola) throws ExceptieUtilizator {
		try {
			modificareParolaSecretariat.setString(1, parola);
			modificareParolaSecretariat.executeUpdate();
		} catch (SQLException e) {
			throw new ExceptieUtilizator("Eroare la accesarea bazei de date!");
		}
	}

	private void modificareParolaProfesor(String parola) throws ExceptieUtilizator {
		try {
			modificareParolaProfesor.setString(1, parola);
			modificareParolaProfesor.setInt(2, idProfesorCautat);
			modificareParolaProfesor.executeUpdate();
		} catch (SQLException e) {
			throw new ExceptieUtilizator("Eroare la accesarea bazei de date!");
		}
	}

	private String parolaStudentCautat() throws ExceptieUtilizator {
		try {
			parolaStudentCautat.setInt(1, nrMatricolCautat);
			rs = parolaStudentCautat.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			throw new ExceptieUtilizator("Eroare la accesarea bazei de date!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	private String parolaProfesorSelectat() throws ExceptieUtilizator {
		try {
			parolaProfesorSelectat.setInt(1, idProfesorCautat);
			rs = parolaProfesorSelectat.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			throw new ExceptieUtilizator("Eroare la accesarea bazei de date!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private boolean parolaAdministatrieOk(String pswrd) {

		try {
			parolaAdministatieOk.setString(1, pswrd);
			rs = parolaAdministatieOk.executeQuery();
			return rs.isBeforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private String parolaProfAdaugat(String cnp) {
		try {
			parolaProfAdaugat.setString(1, cnp);
			rs = parolaProfAdaugat.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return "EROARE!";
		}

	}

	private String parolaStudentAdaugat(String cnp) {
		try {
			parolaStudentAdaugat.setString(1, cnp);
			rs = parolaStudentAdaugat.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return "EROARE!";
		}

	}
}
