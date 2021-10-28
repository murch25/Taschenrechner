package rechner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
//import plotter.Linestyle ;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import plotter.Graphic;
import plotter.Plotter;;

/**
 * <b> Datum: </b> 7.11.2017 <br>
 * <b> Titel: </b> Java Doc Klasse <br>
 * <b> Beschreibung: </b> Benutzeroberfläche1 beinhaltet einen Tachenrechner mit
 * grafischer Benutzeroberfläche, welcher mit "Swing Designer" erstellt wurde.
 * Der Taschenrechner besteht aus einem Standardrechner, einem
 * Wissenschaftlichen Rechner, einem kleinen Plotter und einem Bruchrechner. Der
 * Plotter wurde aus PG2 von Herr Euler übernommen und angepasst. Der
 * Bruchrechner wurde von einem anderen Autor (Lukas Joisten) aus Github
 * übernommen, angepasst und in unseren bereits bestehenden Taschenrechner
 * eingebaut. Er besteht aus 4 Klassen (Steuerung, Gui, Bruch, Start) , welche
 * in der Klasse Benutzeroberfläche als innere Klassen implementiert wurden.
 * 
 * @author Philipp Merchel
 * @author Shabira Taie
 * @version 8.5
 * 
 * 
 */
public class Benutzeroberfläche implements ItemListener {

	/**
	 * Diese Klasse beinhaltet die Intanzvariablen eines Bruches sowie die Methoden,
	 * die benötigt werden um mit zwei Brüchhen zu rechen. Model
	 *
	 * @author Lukas Joisten
	 */

	public class Bruch {

		int zaehler;
		int nenner;

		/**
		 * Diese Methode addiert zwei Brüche.
		 *
		 * @param bruch1
		 *            1. Summand - Bruch 1
		 * @param bruch2
		 *            2. Summand - Bruch 2
		 * @return Summe der beiden Brüche
		 */
		public Bruch addieren(Bruch bruch1, Bruch bruch2) {
			Bruch ergebnis;
			ergebnis = new Bruch();
			ergebnis.zaehler = bruch1.zaehler * bruch2.nenner + bruch2.zaehler * bruch1.nenner;
			ergebnis.nenner = bruch1.nenner * bruch2.nenner;
			return ergebnis;
		}

		/**
		 * Diese Methode subtrahiert zwei Brüche.
		 *
		 * @param bruch1
		 *            Minuend - Bruch 1
		 * @param bruch2
		 *            Subtrahend - Bruch 2
		 * @return Differenz der beiden Brüche
		 */
		public Bruch subtrahieren(Bruch bruch1, Bruch bruch2) {
			Bruch ergebnis;
			ergebnis = new Bruch();
			ergebnis.zaehler = bruch1.zaehler * bruch2.nenner - bruch2.zaehler * bruch1.nenner;
			ergebnis.nenner = bruch1.nenner * bruch2.nenner;
			return ergebnis;
		}

		

		/**
		 * Diese Methode wandelt zwei Zahlen in einen Bruch um.
		 *
		 * @param zahl1
		 *            Zähler
		 * @param zahl2
		 *            Nenner
		 * @return Bruch
		 */
		public Bruch umwandelnInBruch(int zahl1, int zahl2) {
			Bruch ergebnis;
			ergebnis = new Bruch();
			ergebnis.zaehler = zahl1;
			ergebnis.nenner = zahl2;
			return ergebnis;
		}
	}

	/**
	 * Diese Klasse steuert das Projekt. Controller
	 *
	 * @author Lukas Joisten
	 */

	public class Steuerung implements ActionListener {

		// Der Controller kennt Model und View
		Bruch bruch = new Bruch();
		Gui gui = new Gui();

		/**
		 * Der Konstruktor wird von der
		 * 
		 * 
		 * -Methode ausgeführt und erzeugt die ActionListener.
		 */
		public Steuerung() {
			gui.setActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				// Umwandeln der Eingaben in Brüche
				Bruch bruch1 = new Bruch();
				Bruch bruch2 = new Bruch();
				bruch1 = bruch.umwandelnInBruch(gui.getBruchzahlen()[0], gui.getBruchzahlen()[1]);
				bruch2 = bruch.umwandelnInBruch(gui.getBruchzahlen()[2], gui.getBruchzahlen()[3]);

				Bruch ausgabeBruch = new Bruch();
				// Prüfen, welcher Button geklickt wurde.
				if (e.getSource() == gui.bAddieren) {
					ausgabeBruch = bruch.addieren(bruch1, bruch2);
				} else if (e.getSource() == gui.bSubtrahieren) {
					ausgabeBruch = bruch.subtrahieren(bruch1, bruch2);
				}
				gui.ergebnisZäehler = ausgabeBruch.zaehler;
				gui.ergebnisNenner = ausgabeBruch.nenner;
				gui.ergebnisZ = gui.umwandelnDesErgebnisses(gui.ergebnisZäehler, gui.ergebnisNenner)[0];
				gui.ergebnisN = gui.umwandelnDesErgebnisses(gui.ergebnisZäehler, gui.ergebnisNenner)[1];

				gui.rechnung(gui.ergebnisZ, gui.ergebnisN);

				if (gui.getBruchzahlen()[1] == 0 || gui.getBruchzahlen()[3] == 0) {
					gui.NullimNenner();
				}
			} catch (Exception ex) {
				gui.add(gui.lFehlermeldung);
				gui.repaint();
			}
		}
	}

	/**
	 * Läd die Applikation.
	 */
	public static void main(String[] args) {

		String html = "<div> <h1> This is test HTML</h1> <p> This ist a test paragraph " + "our html page </p> </div> ";
		File f = new File("C:\\Hilfedatei1.htm");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(html);
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Benutzeroberfläche window = new Benutzeroberfläche();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellt die Applikation
	 */
	public Benutzeroberfläche() {
		initialize();
		new Steuerung();
	}

	/**
	 * Deklariert die Variablen bezüglich des Frames, der Gui-Komponenten und der
	 * Variablen für den Taschenrechner
	 */
	public JFrame frame;
	public ImageIcon icon;
	private JTextField textField;
	public JTextField textField1;

	public JLabel label1;
	public JLabel label2;
	// public JLabel label2;
	// public JLabel label2;
	// public JFrame extraFenster;
	// public Icon icon;
	// public JLabel bild;
	// public JTextArea textarea;
	// public JScrollPane scroll;
	// public JPanel panel1;
	public boolean shifttaste;
	public boolean rad;
	public int zufallszahl;
	public double zahl;
	public double zahl1;
	public int zahl2;
	// public int zahl3;
	// public boolean zahl4;
	// public String zahl5;
	// public long zahl6;
	// public long zahl7;
	// public double zahlen[] = new double[2];
	public double ergebnis;
	public boolean ergebnis1;
	public String ergebnis2;
	// public double ergebnis3;
	// public int ergebnis4;
	public int rechnen;
	// public int rechnen1;
	// int rechnen2;
	// int rechnen3;
	private JTextField Speicherdisplay;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	JComboBox box2;
	JLabel myImage;

	/**
	 * Hier werden die Zugriffsmethoden für die Funktionen des Taschenrechners
	 * 
	 * verwaltet. <br>
	 * Folgende sind weiter unten verlinkt. Weiterhin wurden Methoden der Klasse
	 * Math benutzt, <br>
	 * welche sich direkt unterhalb in den cases befinden.
	 * 
	 * @see Benutzeroberfläche1#Runde(double)
	 * @see Benutzeroberfläche1#Hex(double)
	 * @see Benutzeroberfläche1#Bin(double)
	 * @see Benutzeroberfläche1#Okt(double)
	 * @see Benutzeroberfläche1#Potenz(double)
	 * @see Benutzeroberfläche1#Quersumme(int)
	 * @see Benutzeroberfläche1#Fakultät(double)
	 * @see Benutzeroberfläche1#Prim(double)
	 * @see Benutzeroberfläche1#Binomialkoeffizient(double, double)
	 * @see Benutzeroberfläche1#Negiere(double)
	 * @see Benutzeroberfläche1#Kehrwert(double)
	 * 
	 */
	public void Funktionen() {
		switch (rechnen) {
		// Addition
		case 1:
			ergebnis = zahl + Double.parseDouble(textField.getText());
			textField.setText(Double.toString(ergebnis));

			break;
		// Subtraktion
		case 2:
			ergebnis = zahl - Double.parseDouble(textField.getText());
			textField.setText(Double.toString(ergebnis));
			break;
		
		
		// Multiplikation
		case 3:
			ergebnis = zahl * Double.parseDouble(textField.getText());
			textField.setText(Double.toString(ergebnis));
			break;
		
		// Dezimalzahl zu Hexadezimalzahl
		case 8:

			ergebnis2 = Hex(zahl);
			textField.setText(ergebnis2);
			break;
		
		// arcsin
		case 29:
			ergebnis = Math.asin(zahl);
			textField.setText(Double.toString(ergebnis));
			break;
			
		// Logarithmus zur Basis e
		case 11:
			ergebnis = Math.log(zahl);
			textField.setText(Double.toString(ergebnis));
			break;
		

		}

	}

	private double ErhöheUm1(double zahl) {
		double NeueZahl = zahl + 1;

		return NeueZahl;
	}

	/**
	 * Diese Methode rundet eine Zahl auf 2 Nachkommastellen.
	 * 
	 * 
	 * 
	 * @param zahl
	 *            die gerundet werden soll.
	 * 
	 * @return gibt die gerundete Zahl zurück.
	 * 
	 */




	/**
	 * Diese Methode wandelt eine Dezimalzahl in eine Hexadezimalzahl um.
	 * 
	 * @param zahl
	 *            im Dezimalsystem.
	 * @return gibt eine Hexdezimalzahl zurück.
	 */
	public String Hex(double zahl) {

		int erg = (int) zahl;

		String hex = Integer.toHexString(erg);
		return hex;

	}

	
	/**
	 * Initialisiert den Inhalt des Frames. Die grafische Benutzeroberfläche wird
	 * hier gebaut. Auch alle Action/MouseListener befinden sich hier.
	 *
	 * @see Benutzeroberfläche1#actionPerformed() <br>
	 *      <br>
	 * @see Benutzeroberfläche1#actionPerformed()
	 *      {@link taschenrechner.Benutzeroberfläche1#actionPerformed}
	 * 
	 */
	private void initialize() {

		frame = new JFrame();

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				frame.setResizable(true);
				// frame.setSize(200, 310);
				frame.setBounds(0, 0, 210, 321);

			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// Textfeld für die Eingabe
		textField = new JTextField();
		textField.setBounds(0, 37, 194, 23);

		// textField.setColumns(10);

		frame.getContentPane().add(textField);

		frame.getContentPane().setBackground(Color.decode("#0A0A2A"));
		// label über Ausgabefeld/Standard
		label1 = new JLabel("");
		label1.setBounds(0, 11, 191, 23);
		label1.setForeground(Color.WHITE);
		frame.getContentPane().add(label1);

		// Label über Graphik

		label2 = new JLabel("");
		label2.setBounds(700, 100, 150, 150);
		label2.setForeground(Color.WHITE);
		// Rahmen um Label
		// Border border = label2.getBorder();
		Border margin1 = new LineBorder(Color.gray, 6);
		// label2.setBorder(new CompoundBorder(border, margin1));
		frame.getContentPane().add(label2);
		// Plotter
		// Graphic graphic = new Graphic("demo2");
		// Plotter plotter = new Graphic("Sinus").getPlotter();
		Plotter plotter = new Plotter();
		plotter.setXrange(-2 * Math.PI, 2 * Math.PI);
		plotter.setYrange(-Math.PI, Math.PI);
		plotter.setXLine(0);
		plotter.setYLine(0);

		double[] xgrid = { -2 * Math.PI, -Math.PI, -Math.PI / 2, 0, Math.PI / 2, Math.PI, 2 * Math.PI };
		double[] ygrid = { -3.14, -3.1 / 2, -1, 0, 1, 3.14 / 2, 3.14 };
		plotter.setXLabelFormat("%.1f");
		plotter.setYLabelFormat("%f.2%%");
		plotter.setXGrid(xgrid);
		plotter.setYGrid(ygrid);
		plotter.setBounds(485, 122, 292, 138);

		Border border1 = plotter.getBorder();
		Border margin = new LineBorder(Color.decode("#2E2E2E"), 6);
		plotter.setBorder(new CompoundBorder(border1, margin));

		frame.add(plotter);
		// ende plotter

		// JList für Auswahl der Funktionen
		String[] items = { "Sin(x)", "Cos(x)", "Tan(x)", "exp(x)", "exp(-x)", "arcsin(x)", "arccos(x)", "arctan(x)",
				"cosh(x)", "sinh(x)", "tanh(x)" };
		JComboBox c = new JComboBox(items);
		c.setBounds(697, 96, 80, 25);
		frame.getContentPane().add(c);
		frame.add(c);
		// Actionevent für ComboBox
		c.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				// Unterscheidung der Funktionen
				if (e.getSource() == c) {
					
					if (c.getSelectedItem().equals("arcsin(x)")) {

						for (double x = -2 * Math.PI; x <= 2 * Math.PI; x += 0.05) {
							plotter.add(x, Math.asin(x));
							plotter.repaint();
						}

					}
				

				}
			}
		});

		// Informationen über Funktionen zum Graphen
		/*
		 * ImageIcon icon = new ImageIcon(
		 * "C:/Users/Phil/eclipse-workspace/MathPro2/src/taschenrechner/bild1.png");
		 * label2 = new JLabel(icon); JPanel feld = new JPanel(); feld.add(label2);
		 * feld.setBounds(800, 150, 100, 100);
		 * 
		 * frame.add(feld);
		 * 
		 */
		// Label hinzufügen
		// Button für Clearen von Graphik
		JButton btnNewButton12 = new JButton("Clear");

		btnNewButton12.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				plotter.clearPlotVector();
				plotter.repaint();
			}
		});
		btnNewButton12.setBounds(485, 96, 210, 24);
		btnNewButton12.setForeground(Color.WHITE);
		btnNewButton12.setBackground(Color.decode("#2E2E2E"));
		frame.getContentPane().add(btnNewButton12);

		
		// Button für "="
		JButton btnNewButton = new JButton("=");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Funktionen();

				label1.setText(""); // löscht das label nach Rechnung
			}
		});
		btnNewButton.setBounds(144, 214, 48, 45);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		frame.getContentPane().add(btnNewButton);
		// Button für "+"
		JButton btnNewButton_1 = new JButton("+");

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				zahl = Double.parseDouble(textField.getText());
				rechnen = 1; // ruft case 1 von funktionen() auf
				textField.setText("");
				label1.setText(zahl + "+"); // zahl erscheint mit operation oben im label
			}
		});
		btnNewButton_1.setBounds(0, 122, 47, 23);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.BLACK);

		frame.getContentPane().add(btnNewButton_1);
		// Button für "-"
		JButton button = new JButton("-");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zahl = Double.parseDouble(textField.getText()); // zahl wird vom Benutzer von Zahlen gewählt
				rechnen = 2; // ruft case 2 von funktionen() auf
				textField.setText("");
				label1.setText(zahl + "-"); // zahl erscheint mit operation oben im label
			}
		});
		button.setBounds(49, 122, 47, 23);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		frame.getContentPane().add(button);
		
		
		
		
		// Button für "*"
				JButton button_22 = new JButton("*");
				button_22.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						zahl = Double.parseDouble(textField.getText()); // zahl wird vom Benutzer gewählt
						rechnen = 3; // ruft case 3 von funktionen() auf
						textField.setText("");
						label1.setText(zahl + "*"); // zahl erscheint mit operation oben im label
					}
				});
				button_22.setBounds(98, 122, 47, 23);
				button_22.setForeground(Color.WHITE);
				button_22.setBackground(Color.BLACK);
				frame.getContentPane().add(button_22);
				
		// Button für "5"
		JButton button_1 = new JButton("5");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText() + "5");
			}
		});
		button_1.setBounds(49, 169, 47, 23);
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(Color.BLACK);
		frame.getContentPane().add(button_1);
		// Button für "2"
		JButton button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText() + "2");
			}
		});
		button_2.setBounds(49, 191, 47, 23);
		button_2.setForeground(Color.WHITE);
		button_2.setBackground(Color.BLACK);
		frame.getContentPane().add(button_2);
		
		
		
		// Button für "."
		JButton button_9 = new JButton(".");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(textField.getText() + ".");
			}
		});
		button_9.setBounds(98, 214, 47, 23);
		button_9.setForeground(Color.WHITE);
		button_9.setBackground(Color.BLACK);
		frame.getContentPane().add(button_9);
		
		
		
		// Button für "Prozentrechnen"
		JButton button_40 = new JButton("%");
		button_40.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zahl = Double.parseDouble(textField.getText());
				rechnen = 5;
				textField.setText("");
				label1.setText("Wie viel Prozent von " + zahl + "?");

			}
		});
		button_40.setBounds(144, 146, 48, 23);
		button_40.setForeground(Color.WHITE);
		button_40.setBackground(Color.decode("#2E2E2E"));
		frame.getContentPane().add(button_40);
		
		
		
		// Button für "Logarithmus"
		JButton button_33 = new JButton("ln");
		button_33.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zahl = Double.parseDouble(textField.getText()); // zahl wird vom Benutzer gewählt
				rechnen = 11; // ruft case 1 von weitereFunktionen auf
				textField.setText("");
				label1.setText("ln(" + zahl + ")"); // zahl erscheint mit operation oben im label
					}
				});
				button_33.setBounds(269, 214, 71, 23);
				button_33.setForeground(Color.WHITE);
				button_33.setBackground(Color.decode("#2E2E2E"));
				frame.getContentPane().add(button_33);
		// RadioButton für "On"
		JRadioButton rdbtnNewRadioButton = new JRadioButton("On");
		buttonGroup_2.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Entsperren(); // ruft Methode Entsperren auf

			}

			// entsperrt die Tasten

			/**
			 * Diese Methode entsperrt den Display des Taschenrechners <br>
			 * <b> Problem: </b> <br>
			 * Es werden aber nur die Tasten 0-9 entsperrt. <br>
			 * Beim Versuch alle Gui-Komponenten zu entsperren, schlug die Methode fehl.<br>
			 * Bisher konnte keine Lösung gefunden werden.
			 */
			private void Entsperren() {
				// On.setEnabled(false);
				// Off.setEnabled(true);
				btnNewButton.setEnabled(true);
				// btnNewButton_1.setEnabled(true);
				// btnNewButton_2.setEnabled(true);
				textField.setEnabled(true);
				button_1.setEnabled(true);
				button_2.setEnabled(true);
				button_9.setEnabled(true);
				
			}
		});

		rdbtnNewRadioButton.setBounds(91, 0, 46, 23);
		frame.getContentPane().add(rdbtnNewRadioButton);
		// RadioButton für Off
		JRadioButton radioButton = new JRadioButton("Off");
		buttonGroup_2.add(radioButton);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * @see Benutzeroberfläche1#Sperren()
				 */
				Sperren(); // ruft Methode Speren() auf

			}

			/**
			 * Diese Methode sperrt den Display des Taschenrechners. <br>
			 * <b>Problem: </b> <br>
			 * Es werden aber nur die Tasten 0-9 gesperrt. <br>
			 * Beim Versuch alle Gui-Komponenten zu sperren, schlug die Methode fehl.<br>
			 * Bisher konnte keine Lösung gefunden werden.
			 */
			// sperrt die Tasten
			private void Sperren() {
				// On.setEnabled(true);
				// Off.setEnabled(false);
				btnNewButton.setEnabled(false);
				// btnNewButton_1.setEnabled(false);
				// btnNewButton_2.setEnabled(false);
				textField.setEnabled(false);
				button_1.setEnabled(false);
				button_2.setEnabled(false);
				button_9.setEnabled(false);
			

				
				// alle restlichen Buttons auch

			}
		});
		radioButton.setBounds(139, 0, 53, 23);
		frame.getContentPane().add(radioButton);

		// Radiobutton für "Hexdezimalzahl"
		JRadioButton radioButton_1 = new JRadioButton("Hex");
		buttonGroup.add(radioButton_1);
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zahl = Double.parseDouble(textField.getText());
				rechnen = 8; // ruft case 1 von trigonometrischeOperationen auf
				textField.setText("");
				label1.setText("Hex(" + zahl + ")"); // zahl erscheint mit operation oben im label
			}
		});
		radioButton_1.setBounds(379, 97, 55, 23);
		radioButton_1.setForeground(Color.WHITE);
		radioButton_1.setBackground(Color.decode("#2E2E2E"));
		frame.getContentPane().add(radioButton_1);

		
		// Button für "Memory Save"
		// Speichert eine Zahl im Speicher
		JButton button_53 = new JButton("MS");
		button_53.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Speicherdisplay.setText(textField.getText());

			}
		});
		button_53.setBounds(0, 97, 62, 23);
		frame.getContentPane().add(button_53);
		button_53.setBackground(Color.decode(("#7B68EE")));
		button_53.setForeground(Color.WHITE);

		// Button für "Memory Clear"
		// Löscht die gespeicherte Zahl
		JButton button_54 = new JButton("MC");
		button_54.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Speicherdisplay.setText("");

			}
		});
		button_54.setBounds(66, 97, 61, 23);
		button_54.setBackground(Color.decode(("#7B68EE")));
		button_54.setForeground(Color.WHITE);
		frame.getContentPane().add(button_54);

		// Button für "Memory Read"
		// Gibt die gespeicherte Zahl im Display aus
		JButton button_55 = new JButton("MR");
		button_55.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(Speicherdisplay.getText());

			}
		});
		button_55.setBounds(131, 97, 61, 23);
		button_55.setBackground(Color.decode(("#7B68EE")));
		button_55.setForeground(Color.WHITE);
		frame.getContentPane().add(button_55);
		// Button für Löschfunktion
		JButton button_56 = new JButton("C");
		button_56.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				label1.setText("");
			}
		});
		button_56.setBounds(144, 65, 48, 23);
		button_56.setBackground(Color.decode("#610B21"));
		button_56.setForeground(Color.WHITE);
		// button_56.setBackground(Color.RED);
		frame.getContentPane().add(button_56);
		// Button für "einzelne Zeichen" Löschen
		JButton button_57 = new JButton("<--");
		button_57.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int Länge = textField.getText().length(); // Länge der Zeichenkette im Textfeld
				int Nummer = textField.getText().length() - 1; // Nummer des letzen Zeichens, dass gelöscht
																// werden soll
				String Speicher; // speichert das korrigierte Wort

				if (Länge > 0) {
					StringBuilder zurück = new StringBuilder(textField.getText()); // Zeichenkette im
																					// Textfeld-->String-Objekt
					zurück.deleteCharAt(Nummer); // löscht das letzte zeichen der Zeichenkette an der Stelle
													// "Nummer"
					Speicher = zurück.toString(); // speichert das korrigierte Wort in Speicher
					textField.setText(Speicher); // setzt den Text im Textfeld neu
				}
			}
		});
		button_57.setBounds(92, 65, 53, 23);
		button_57.setForeground(Color.WHITE);
		button_57.setBackground(Color.decode("#610B21"));
		// button_57.setBackground(Color.RED);
		frame.getContentPane().add(button_57);
		// Textfeld zur Ausgabe von gespeicherten Daten
		Speicherdisplay = new JTextField();
		Speicherdisplay.setBounds(0, 65, 84, 23);
		frame.getContentPane().add(Speicherdisplay);
		Speicherdisplay.setColumns(10);

		/**
		 * Aktiviert und deaktiviert den Shiftbutton per Klick
		 */

		// ToggleButton für "Shift"
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Shift");
		tglbtnNewToggleButton.addMouseListener(new MouseAdapter() {
			@Override

			/**
			 * Diese Methode setzt den Wahrheitswert der Shifttaste
			 *
			 * 
			 * @param MouseEvent
			 *
			 */
			public void mouseClicked(MouseEvent e) {

				if (!shifttaste) { // wenn shifttaste nicht gedrückt ist
					shifttaste = true;

				} else { // wenn shifttaste gedrückt ist
					shifttaste = false;
				}
			}
		});
		tglbtnNewToggleButton.setBounds(196, 97, 70, 23);
		tglbtnNewToggleButton.setBackground(Color.decode(("#7B68EE")));
		tglbtnNewToggleButton.setForeground(Color.WHITE);
		frame.getContentPane().add(tglbtnNewToggleButton);
		// tglbtnNewToggleButton.setBackground(Color.YELLOW);

		// Gradmaß: 1Einheit=360°
		// setzt rad auf false

		// Radiobutton für "Degree" /Gradmaß
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Deg");
		buttonGroup_1.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rad = false;
			}
		});
		rdbtnNewRadioButton_1.setBounds(324, 97, 55, 23);
		rdbtnNewRadioButton_1.setForeground(Color.WHITE);
		rdbtnNewRadioButton_1.setBackground(Color.decode("#2E2E2E"));
		frame.getContentPane().add(rdbtnNewRadioButton_1);

		// Bogenmaß: 1 Einheit=2*PI
		// setzt rad auf true
		// Radiobutton für"Radiant" /Bogenmaß
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Rad");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rad = true;
			}
		});

		buttonGroup_1.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(269, 97, 55, 23);
		rdbtnNewRadioButton_2.setForeground(Color.WHITE);
		rdbtnNewRadioButton_2.setBackground(Color.decode("#2E2E2E"));
		frame.getContentPane().add(rdbtnNewRadioButton_2);
		
		// Menüstruktur
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// Menüpunkt "Ansicht"
		JMenu mnNewMenu = new JMenu("Ansicht");
		menuBar.add(mnNewMenu);
		// Menüunterpunkt "Standard" von Menüpunkt "Ansicht"
		JMenuItem mntmNewMenuItem = new JMenuItem("Standard");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setResizable(false);
				frame.setSize(200, 320);

			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		// Menüunterpunkt "Wissenschaftlich" von Menühpunkt "Ansicht"
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Wissenschaftlich");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setResizable(false);
				frame.setSize(490, 320);
				textField.setSize(192, 23);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		// Menüunterpunkt "Grafikrechner"
		JMenuItem mntmNewMenuItem_21 = new JMenuItem("Graphisch");
		mntmNewMenuItem_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setResizable(false);
				frame.setSize(795, 320);
				textField.setSize(192, 23);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_21);

		// Menüunterpunkt "Bruchrechner" von Menüpunkt "Ansicht"
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Bruchrechner");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setResizable(false);
				frame.setSize(200, 375);

			}
		});
		mnNewMenu.add(mntmNewMenuItem_6);
		// Menüpunkt "Bearbeiten"
		JMenu mnNewMenu_1 = new JMenu("Bearbeiten");
		menuBar.add(mnNewMenu_1);

		// Hintergrundfarbe kann vom Benutzer über Menü mit dem Menüitem "Farbe ändern"
		// gewählt werden
		// Menüunterpunkt "Farbe ändern" von Meünpunkt "Bearbeiten"
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Farbe ändern");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color ausgewaehlteFarbe = JColorChooser.showDialog(null, "Farbauswahl", null);

				frame.getContentPane().setBackground(ausgewaehlteFarbe);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		// Menüunterpunkt "Schließen" von Menüpunkt "Bearbeiten"
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Schließen");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();

			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);
		// Menüpunkt "Hilfe"
		JMenu mnNewMenu_2 = new JMenu("Hilfe");
		mnNewMenu_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuBar.add(mnNewMenu_2);
		// Menüunterpunkt "Bedienungsanleitung" von Menüpunkt "Hilfe"
		// Hier soll einmal eine kurze Bedienungsanleitung mit Scrollfunktion eingebaut
		// werden
		// Problem: Ganzer Rechner wird geschlossen, wenn Bedienungsanleitung schließt.
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Bedienungsanleitung");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Bedienungsanleitung
				String location = new File(
						"C:\\Users\\Phil\\eclipse-workspace\\MathPro8.5\\src\\taschenrechner\\Hilfedatei1.htm").toURI()
								.toString();
				Desktop desktop = Desktop.getDesktop();

				// Adresse mit Standardbrowser anzeigen
				URI uri;
				try {
					uri = new URI(location);
					desktop.browse(uri);
				} catch (Exception oError) {
					// Hier Fehler abfangen
					System.out.println("Seite kann nicht geöffnet werden.");
				}

				JFrame extraFenster = new JFrame("Text Area Examples");
				extraFenster.setBounds(100, 100, 300, 300);

				extraFenster.addWindowListener(new WindowAdapter() {
					@Override
					public void windowActivated(WindowEvent e) {

						extraFenster.addWindowListener(new WindowAdapter() {
							@Override
							public void windowActivated(WindowEvent e) {

							}
						});

					}
				});

			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);
		// Menüunterpunkt "Kontakt" von Menupunkt "Hilfe"
		// Kontaktdaten der Entwickler für Fragen
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Kontakt");
		mnNewMenu_2.add(mntmNewMenuItem_5);
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Bedienungsanleitung
				String location1 = new File(
						"C:\\Users\\Phil\\eclipse-workspace\\MathPro8.5\\src\\taschenrechner\\Kontakt.htm").toURI()
								.toString();
				Desktop desktop1 = Desktop.getDesktop();

				// Adresse mit Standardbrowser anzeigen
				URI uri1;
				try {
					uri1 = new URI(location1);
					desktop1.browse(uri1);
				} catch (Exception oError) {
					// Hier Fehler abfangen
					System.out.println("Seite kann nicht geöffnet werden.");
				}

				JFrame extraFenster1 = new JFrame("Text Area Examples");
				extraFenster1.setBounds(100, 100, 300, 300);

				// ImageIcon icon = new
				// ImageIcon("C:/Users/Phil/eclipse-workspace/MathPro2/src/taschenrechner/bild1.png");
				// label1 = new JLabel(icon);
				// JPanel panel1 = new JPanel();
				// panel1.add(label1);
				// extraFenster.getContentPane().add(panel1);

				// extraFenster.setVisible(true);

				extraFenster1.addWindowListener(new WindowAdapter() {
					@Override
					public void windowActivated(WindowEvent e) {

						extraFenster1.addWindowListener(new WindowAdapter() {
							@Override
							public void windowActivated(WindowEvent e) {

							}
						});

					}
				});

			}
		});
	}

	/**
	 * In dieser Klasse wird die Grafik erstellt. View
	 *
	 * @author Lukas Joisten
	 */

	public class Gui extends JFrame {

		// Ergebnisvariablen
		int ergebnisZäehler, ergebnisNenner;
		String ergebnisZ, ergebnisN;

		// Schriftarten
		Font UeberschriftFont = new Font("default", Font.BOLD, 40);
		Font buttonFont = new Font("default", Font.PLAIN, 20);
		Font ergebnisFont = new Font("default", Font.BOLD, 25);

		// Beschriftungen
		// JLabel lBruch1 = new JLabel("Bruch 1");
		// JLabel lBruch2 = new JLabel("Bruch 2");
		JLabel gleich = new JLabel("=");
		// JLabel lErgebnis = new JLabel("Das Ergebnis betrÃ¤gt: ", JLabel.CENTER);
		JLabel lErgebnis = new JLabel("=");

		// JLabel lüberschrift = new JLabel("Bruchrechen");
		JLabel lBruchstrich1 = new JLabel("");
		JLabel lBruchstrich2 = new JLabel("");
		// JLabel lBruchstrich3 = new JLabel("________", JLabel.CENTER);
		JLabel lBruchstrich3 = new JLabel("");
		JLabel lFehlermeldung = new JLabel("", JLabel.CENTER);
		// JLabel lAnleitung = new JLabel(
		// "<html><center>Bitte geben Sie hier ihre 2 " + "Brüche als ganze Zahlen ein
		// und klicken Sie auf die "
		// + "Rechenoperation, welche Sie anwenden möchten.</center></html>");
		JLabel lNullImNenner = new JLabel("", JLabel.CENTER);
		// Im Nenner darf keine '0' stehen.

		// JLabel lErgebnisBruchZÃ¤hler = new JLabel(ergebnisZ, JLabel.CENTER);
		// JLabel lErgebnisBruchNenner = new JLabel(ergebnisN, JLabel.CENTER);

		// JLabel lErgebnisBruchZÃ¤hler = new JLabel(ergebnisZ, JLabel.CENTER);
		// JLabel lErgebnisBruchNenner = new JLabel(ergebnisN, JLabel.CENTER);

		// JLabel lErgebnisBruchZaehler = new JLabel(ergebnisZ);
		// JLabel lErgebnisBruchNenner = new JLabel(ergebnisN);

		JTextField lErgebnisBruchZaehler = new JTextField(ergebnisZ);
		JTextField lErgebnisBruchNenner = new JTextField(ergebnisN);

		// Textfelder
		JFormattedTextField tBruch1zaehler = new JFormattedTextField(new DecimalFormat(""));
		JFormattedTextField tBruch1nenner = new JFormattedTextField(new DecimalFormat(""));
		JFormattedTextField tBruch2zaehler = new JFormattedTextField(new DecimalFormat(""));
		JFormattedTextField tBruch2nenner = new JFormattedTextField(new DecimalFormat(""));
		// ######
		// Buttons
		JButton bAddieren = new JButton("+");
		JButton bSubtrahieren = new JButton("-");
		JButton bMultiplizieren = new JButton("*");
		JButton bDividieren = new JButton("/");

		/**
		 * Zugriffsmethode für die Steuerung auf die Textfelder. Liefert den Inhalt der
		 * Textfelder.
		 *
		 * @return Ein Integer-Array mit den Bruchzahlen
		 */
		public int[] getBruchzahlen() {
			int[] inhalte = new int[4];
			inhalte[0] = Integer.parseInt(tBruch1zaehler.getText());
			inhalte[1] = Integer.parseInt(tBruch1nenner.getText());
			inhalte[2] = Integer.parseInt(tBruch2zaehler.getText());
			inhalte[3] = Integer.parseInt(tBruch2nenner.getText());
			return inhalte;
		}

		/**
		 * Diese Methode macht die Elemente des Ergebnisses sichtbar und entfernt die
		 * Fehlermeldungen.
		 *
		 * @param ergebnisZaehler
		 *            Zaehler des Ergebnisbruchs
		 * @param ergebnisNenner
		 *            Nenner des ErgebnisBruchs
		 */
		public void rechnung(String ergebnisZÃ¤hler, String ergebnisNenner) {
			lErgebnisBruchZaehler.setText(ergebnisZÃ¤hler);
			lErgebnisBruchNenner.setText(ergebnisNenner);
			frame.add(lBruchstrich3);
			frame.add(lErgebnisBruchNenner);
			frame.add(lErgebnisBruchZaehler);
			frame.add(lErgebnis);
			remove(lFehlermeldung);
			remove(lNullImNenner);
			repaint();
			// validate();

		}

		/**
		 * Diese Methode entfernt alle störenden Elemente für den Fall, dass im Nenner
		 * eines Bruchs eine '0' steht und eine Fehlermeldung angezeigt werden muss. Sie
		 * macht natürlich auch die Fehlermeldung sichtbar.
		 */
		public void NullimNenner() {
			add(lNullImNenner);
			remove(lErgebnis);
			remove(lErgebnisBruchNenner);
			remove(lErgebnisBruchZaehler);
			remove(lBruchstrich3);
			repaint();
			// validate();

		}

		/**
		 * Diese Methode wandelt zwei Integer-Zahlen zu String-Objekten in einem
		 * String-Array um.
		 *
		 * @param zaehler
		 *            Zaehler
		 * @param nenner
		 *            Nenner
		 * @return String-Array mit Zaehler und Nenner des Bruches
		 */
		public String[] umwandelnDesErgebnisses(int zÃ¤hler, int nenner) {
			String[] ergebnisse = new String[2];
			ergebnisse[0] = String.valueOf(zÃ¤hler);
			ergebnisse[1] = String.valueOf(nenner);
			return ergebnisse;
		}

		/**
		 * gibt den Actionlistener für die Buttons.
		 *
		 * @param listener
		 *            ActionListener
		 */
		public void setActionListener(ActionListener listener) {
			bAddieren.addActionListener(listener);
			bSubtrahieren.addActionListener(listener);
			bDividieren.addActionListener(listener);
			bMultiplizieren.addActionListener(listener);
		}

		/**
		 * Dieser Konstruktor baut die Grafische Benutzeroberflaeche.
		 */
		public Gui() {
			super();

			frame.setTitle("");
			frame.setLocation(100, 100);
			frame.setSize(600, 500);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(null);
			frame.setResizable(true);

			// lBruch1.setBounds(2, 257, 50, 20);
			// lBruch1.setForeground(Color.WHITE);
			// lBruch2.setBounds(70, 257, 50, 20);
			// lBruch2.setForeground(Color.WHITE);
			lBruchstrich1.setBounds(0, 282, 40, 20);
			lBruchstrich2.setBounds(70, 282, 40, 20);
			lBruchstrich3.setBounds(125, 282, 60, 20);
			// lAnleitung.setFont(buttonFont);
			// lAnleitung.setBounds(5, 700, 590, 55);

			lFehlermeldung.setForeground(Color.red);
			lFehlermeldung.setFont(ergebnisFont);
			lFehlermeldung.setBounds(50, 550, 500, 35);
			lNullImNenner.setForeground(Color.red);
			lNullImNenner.setFont(ergebnisFont);
			lNullImNenner.setBounds(50, 550, 500, 35);

			lErgebnis.setFont(ergebnisFont);
			lErgebnis.setBounds(0, 330, 600, 50);
			lErgebnis.setForeground(Color.WHITE);
			lErgebnisBruchZaehler.setFont(ergebnisFont);
			lErgebnisBruchZaehler.setBounds(130, 272, 61, 20);
			// lBruchstrich3.setFont(ergebnisFont);

			lErgebnisBruchNenner.setFont(ergebnisFont);
			lErgebnisBruchNenner.setBounds(130, 296, 61, 20);
			gleich.setBounds(110, 283, 40, 20);
			gleich.setForeground(Color.WHITE);
			gleich.setFont(gleich.getFont().deriveFont(18f));

			// tBruch1zaehler.setBounds(0, 277, 50, 20);
			// tBruch1nenner.setBounds(0, 301, 50, 20);

			tBruch1zaehler.setBounds(0, 272, 30, 20);
			tBruch1nenner.setBounds(0, 296, 30, 20);
			tBruch2zaehler.setBounds(70, 272, 30, 20);
			tBruch2nenner.setBounds(70, 296, 30, 20);

			bAddieren.setFont(buttonFont);
			bAddieren.setMargin(new Insets(0, 0, 0, 0));
			bAddieren.setBounds(30, 272, 20, 20);
			bAddieren.setForeground(Color.WHITE);
			bAddieren.setBackground(Color.decode("#2E2E2E"));

			bSubtrahieren.setFont(buttonFont);
			bSubtrahieren.setMargin(new Insets(0, 0, 0, 0));
			bSubtrahieren.setBounds(50, 272, 20, 20);
			bSubtrahieren.setForeground(Color.WHITE);
			bSubtrahieren.setBackground(Color.decode("#2E2E2E"));

			bMultiplizieren.setFont(buttonFont);
			bMultiplizieren.setMargin(new Insets(0, 0, 0, 0));
			bMultiplizieren.setBounds(30, 296, 20, 20);
			bMultiplizieren.setForeground(Color.WHITE);
			bMultiplizieren.setBackground(Color.decode("#2E2E2E"));

			bDividieren.setFont(buttonFont);
			bDividieren.setMargin(new Insets(0, 0, 0, 0));
			bDividieren.setBounds(50, 296, 20, 20);
			bDividieren.setForeground(Color.WHITE);
			bDividieren.setBackground(Color.decode("#2E2E2E"));

			// Hinzufügen der einzelnen Elemente
			// frame.add(lüberschrift);
			// frame.add(lBruch1);
			// frame.add(lBruch2);
			frame.add(lBruchstrich1);
			frame.add(lBruchstrich2);
			frame.add(lBruchstrich3);
			// frame.add(lAnleitung);

			frame.add(tBruch1nenner);
			frame.add(tBruch1zaehler);
			frame.add(tBruch2zaehler);
			frame.add(tBruch2nenner);

			frame.add(bAddieren);
			frame.add(bDividieren);
			frame.add(bMultiplizieren);
			frame.add(bSubtrahieren);
			frame.add(gleich);
			// Update
			this.pack();
			lErgebnis.validate();
			lErgebnisBruchZaehler.validate();
			lErgebnisBruchNenner.validate();
			// lErgebnis.paintComponents(lErgebnis.getGraphics());
			// lErgebnisBruchZÃ¤hler.paintComponents(lErgebnis.getGraphics());
			// lErgebnisBruchNenner.paintComponents(lErgebnis.getGraphics());

			frame.setVisible(true);

			// icon für JFrame Symbol

			icon = new ImageIcon("Bilder/fertig123.png");
			frame.setIconImage(icon.getImage());

			// icon für dekstopverknüpfung
			frame.setIconImage(createImage("Bilder/fertig123.png").getImage());

		}

		private ImageIcon createImage(String path) {
			// TODO Auto-generated method stub
			return new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getClass().getResource(path));
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

}
