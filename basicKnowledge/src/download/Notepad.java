package download;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad { /*
						 * implements ActionListener , MouseListener ,
						 * MouseMotionListener , WindowListener , ItemListener ,
						 * KeyListener, TextListener
						 */
	public static void main(String[] args) {
		new Notepad();
	}

	// ��Ա����
	private Frame mainFrame;// �����

	private MenuBar mb; // �˵���

	private Menu mFile, mEdit, mFormat, mHelp; // �˵����ļ����༭����ʽ������

	private MenuItem miNew, miOpen, miSave, miSaveAs, miExit;// �ļ��˵���½����򿪣����棬���Ϊ���˳�

	private MenuItem miCut, miCopy, miPaste, miDelete;// �༭�˵�����У����ƣ�ճ��ɾ��

	private MenuItem miFont, miLowtoCapital, miCapitaltoLow, miEncrypt,
			miDisencrypt;// ��ʽ�˵������

	private MenuItem miAboutNotepad;// ����˵�����ڼ��±�

	private TextArea ta;// �ı���

	private String tempString;// ��ʱ�ַ�,���ڴ洢��Ҫ����ճ����ַ�

	private boolean textValueChanged = false;

	private int id_font;// ����

	String fileName = " ";// �ϴα������ļ���͵�ַ

	// ���캯��
	public Notepad() {

		// ���
		mainFrame = new Frame("Notepad v0.99       by Launching");
		mb = new MenuBar();
		ta = new TextArea(30, 60);
		ta.setFont(new Font("Times New Rome", Font.PLAIN, 15));
		// ta.setBackground(new Color(0 , 250 , 200));

		// �˵���
		mFile = new Menu("File");
		mEdit = new Menu("Edit");
		mFormat = new Menu("Format");
		mHelp = new Menu("Help");

		// "�ļ�"
		miNew = new MenuItem("New");
		miOpen = new MenuItem("Open");
		miSave = new MenuItem("Save");
		miSaveAs = new MenuItem("Save as");
		miExit = new MenuItem("Exit");

		// "�༭"
		miCut = new MenuItem("Cut");
		miCopy = new MenuItem("Copy");
		miPaste = new MenuItem("Paste");
		miDelete = new MenuItem("Delete");

		// "��ʽ"
		miFont = new MenuItem("Font");
		miLowtoCapital = new MenuItem("Low to Capital");
		miCapitaltoLow = new MenuItem("Capital to Low");
		miEncrypt = new MenuItem("Encrypt");
		miDisencrypt = new MenuItem("Disencrypt");

		// "����"
		miAboutNotepad = new MenuItem("About Notepad");

		// ����ļ��˵���
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.add(miSaveAs);
		mFile.add(miExit);

		// ��ӱ༭�˵���
		mEdit.add(miCut);
		mEdit.add(miCopy);
		mEdit.add(miPaste);
		mEdit.add(miDelete);

		// ��Ӹ�ʽ�˵���
		mFormat.add(miFont);
		mFormat.add(miLowtoCapital);
		mFormat.add(miCapitaltoLow);
		mFormat.add(miEncrypt);
		mFormat.add(miDisencrypt);

		// ��Ӱ���˵���
		mHelp.add(miAboutNotepad);

		// �˵�����Ӳ˵�
		mb.add(mFile);
		mb.add(mEdit);
		mb.add(mFormat);
		mb.add(mHelp);

		// �����Ӳ˵���
		mainFrame.setMenuBar(mb);

		// ��ʼ�ַ�Ϊ��
		tempString = "";

		// ����ı���
		mainFrame.add(ta, BorderLayout.CENTER);

		mainFrame.setSize(800, 500);
		mainFrame.setLocation(100, 100);// ��ʼλ��
		mainFrame.setResizable(true);// ���ɸ�Ĵ�С
		mainFrame.setVisible(true);
		// mainFrame.pack();

		// ////////////////////////////////////////////////////////////////////////////////
		// //////////////////////////////���Ӽ�����////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////

		// �����
		mainFrame.addWindowListener(new WindowAdapter() { // �رմ���
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});

		// �ı���
		ta.addKeyListener(new KeyAdapter() {
			public void KeyTyped(KeyEvent e) {
				textValueChanged = true; // ���̰����¼������ı��޸�
			}
		});
	}
}