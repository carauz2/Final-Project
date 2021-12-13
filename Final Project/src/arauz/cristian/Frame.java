package arauz.cristian;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import animal.Animal;
import animal.carnivore.Lion;
import animal.herbivore.Giraffe;

public class Frame extends JFrame implements ActionListener {

	private static int input;
	private static Scanner fileReader;
	private static JButton inputBut, outputBut, doneBut;
	private static JTextField lionInput, giraffeInput, threadsInput;
	private static File inputFile, outputFolder;
	private static ArrayList<Animal> animalList = new ArrayList<Animal>();
	private static ArrayList<Run> threadList = new ArrayList<Run>();
	private static boolean isThreadInput, isLionInput, isGiraffeInput, isInputFile, isOutputFolder = false;

	Frame() {
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("AG Final Project");
		this.setLayout(new GridLayout(0, 1));
		inputBut = new JButton("Select File");
		inputBut.addActionListener(this);
		outputBut = new JButton("Select Output Folder");
		outputBut.addActionListener(this);
		doneBut = new JButton("Done");
		doneBut.addActionListener(this);

		lionInput = new JTextField("How many Lions?");
		lionInput.setHorizontalAlignment(JTextField.CENTER);
		lionInput.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (lionInput.getText().equalsIgnoreCase("How many Lions?")) {
					lionInput.setText(null);
				}
			}

			public void focusLost(FocusEvent e) {
				if (lionInput.getText().equals("")) {
					lionInput.setText("How many Lions?");
					isLionInput = false;
				} else {
					isLionInput = true;
				}
			}
		});
		lionInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
		});

		giraffeInput = new JTextField("How many Giraffes?");
		giraffeInput.setHorizontalAlignment(JTextField.CENTER);
		giraffeInput.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (giraffeInput.getText().equalsIgnoreCase("How many Giraffes?")) {
					giraffeInput.setText(null);
				}
			}

			public void focusLost(FocusEvent e) {
				if (giraffeInput.getText().equals("")) {
					giraffeInput.setText("How many Giraffes?");
					isGiraffeInput = false;
				} else {
					isGiraffeInput = true;
				}
			}
		});
		giraffeInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
		});

		threadsInput = new JTextField("How many Threads?");
		threadsInput.setHorizontalAlignment(JTextField.CENTER);
		threadsInput.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (threadsInput.getText().equalsIgnoreCase("How many Threads?")) {
					threadsInput.setText(null);
				}
			}

			public void focusLost(FocusEvent e) {
				if (threadsInput.getText().equals("")) {
					threadsInput.setText("How many Threads?");
					isThreadInput = false;
				} else {
					isThreadInput = true;
				}
			}
		});
		threadsInput.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
					e.consume();
				}
			}
		});
		this.add(inputBut);
		this.add(outputBut);
		this.add(giraffeInput);
		this.add(lionInput);
		this.add(threadsInput);
		this.add(doneBut);
		this.setSize(400, 400);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputBut) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				inputFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
				inputBut.setText(inputFile.getPath());
				isInputFile = true;
			}
			else {
				isInputFile = false;
				inputBut.setText("Select File");
			}
		}
		if (e.getSource() == outputBut) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int response = fileChooser.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				outputFolder = new File(fileChooser.getSelectedFile().getAbsolutePath());
				outputBut.setText(outputFolder.getPath());
				isOutputFolder = true;
			}
			else {
				isOutputFolder = false;
				outputBut.setText("Select Output Folder");
			}
		}

		if (e.getSource() == doneBut) {
			animalList.clear();
			if (!(isThreadInput == false || isLionInput == false || isGiraffeInput == false || isInputFile == false
					|| isOutputFolder == false)) {
				input = Integer.parseInt(giraffeInput.getText());
				createAnimal("Giraffe");

				input = Integer.parseInt(lionInput.getText());
				createAnimal("Lion");

				input = Integer.parseInt(threadsInput.getText());
				startThreads();
			}
			else {
				System.out.println("Nothing happened, please fill out all fields!");
			}
		}
	}

	private static void createAnimal(String animal) {
		try {
			fileReader = new Scanner(inputFile);
			if (animal.equalsIgnoreCase("Giraffe")) {
				for (int i = input; i > 0; i--) {
					animalList.add(new Giraffe(fileReader.nextLine()));
				}
			} else if (animal.equalsIgnoreCase("Lion")) {
				for (int i = input; i > 0; i--) {
					animalList.add(new Lion(fileReader.nextLine()));
				}
			}
			fileReader.close();
		} catch (Exception e) {
			System.out.println("Error creating an animal!");
		}
	}

	private static void startThreads() {
		for (int i = 0; i < input; i++) {
			Run run = Run.createAndStart("thread" + i, outputFolder.getAbsolutePath() + "/output" + i + ".txt",
					animalList);
			threadList.add(run);
		}

		for (int i = 0; i < input; i++) {
			try {
				threadList.get(i).getThread().join();
			} catch (Exception e) {
				System.out.println("Error creating, running or finishing threads!");
			}
		}
	}

}
