package pore;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.undo.*; //UndoManager
import javax.swing.text.*;//Document
import java.text.*;
import java.util.*;
import javax.swing.Timer;

class Notepad {
	public static void main(String[] args) {
		NotepadFrame frame = new NotepadFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class NotepadFrame extends JFrame {
	private JMenuBar menuBar;//主菜单

	//文件项
	private JMenu menuFile = new JMenu("文件(F)");

	private JMenuItem menuFileNew = new JMenuItem("新建(N)");

	private JMenuItem menuFileOpen = new JMenuItem("打开(O)...");

	private JMenuItem menuFileSave = new JMenuItem("保存(S)");

	private JMenuItem menuFileSaveAs = new JMenuItem("另存为(A)...");

	private JMenuItem menuFileSetPage = new JMenuItem("页面设置(U)...");

	private JMenuItem menuFilePrint = new JMenuItem("打印(P)...");

	private JMenuItem menuFileExit = new JMenuItem("退出(X)");

	//编辑  
	private JMenu menuEdit = new JMenu("编辑(E)");

	private JMenuItem menuFileUndo = new JMenuItem("撤销(U)");

	private JMenuItem menuFileCut = new JMenuItem("剪切(T)");

	private JMenuItem menuFileCopy = new JMenuItem("复制(C)");

	private JMenuItem menuFilePoste = new JMenuItem("粘贴(P)");

	private JMenuItem menuFileDel = new JMenuItem("删除(L)");

	private JMenuItem menuFileFind = new JMenuItem("查找(F)");

	private JMenuItem menuFileFindNext = new JMenuItem("查找下一个(N)");

	private JMenuItem menuFileReplace = new JMenuItem("替换(R)");

	private JMenuItem menuFileTransfer = new JMenuItem("转到(G)");

	private JMenuItem menuFileSelectAll = new JMenuItem("全选(A)");

	private JMenuItem menuFileTime = new JMenuItem("时间/日期(D)");

	//格式
	private JMenu menuFormat = new JMenu("格式(O)");

	private JMenuItem menuFileAuto = new JMenuItem("  自动换行(W)");

	private JMenuItem menuFileFont = new JMenuItem("  字体(F)...");

	//查看
	private JMenu menuView = new JMenu("查看(V)");

	private JMenuItem menuFileState = new JMenuItem("状态栏(S)");

	//帮助
	private JMenu menuHelp = new JMenu("帮助(H)");

	private JMenuItem menuFileHelp = new JMenuItem("帮助主题(H)");

	private JMenuItem menuFileAbout = new JMenuItem("关于记事本(A)");

	private JPopupMenu popup;//弹出菜单   

	private JMenuItem popupmenuFileUndo = new JMenuItem("撤销(U)");;

	private JMenuItem popupmenuFileCut = new JMenuItem("剪切(T)");;

	private JMenuItem popupmenuFileCopy = new JMenuItem("复制(C)");;

	private JMenuItem popupmenuFilePoste = new JMenuItem("粘贴(P)");;

	private JMenuItem popupmenuFileDel = new JMenuItem("删除(L)");;

	private JMenuItem popupmenuFileSelectAll = new JMenuItem("全选(A)");;

	private static String fileName = "无标题";

	private String fileContext = "";//文件的内容

	private String fileDirection; //文件的路径

	private String copy;//复制的内容

	private String searchWord;//查找的内容

	private int status = 1;

	private static int auto = 0;//计数器 

	private JFrame frame = new JFrame();//窗体

	private JTextArea textarea; //文本域

	private JScrollPane scrollPane;

	private JLabel row, col;//状态栏的行和列

	private int r = 0, c = 1;//光标的位置

	private final UndoManager undo = new UndoManager();

	private int directionStatus = 0;//查找的方向

	private int start = 0, checkStart = 0;

	private Dimension getsize = new Dimension();

	public NotepadFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension location = getServiceSetting();
		setLocation(location.width, location.height);//设置位置 		
		setSize(getsize.width, getsize.height);//设置大小 
		setLayout(null);////无边框布局	 		
		Image img = kit.getImage("pore//1.jpg");//图标设置 
		setIconImage(img);
		setTitle(fileName + "-记事本");
		row = new JLabel();
		col = new JLabel();
		//建立菜单
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//添加主菜单	 
		menuBar.add(menuFile);
		menuFile.setMnemonic('F');
		menuFile.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuBar.add(menuEdit);
		menuEdit.setMnemonic('E');
		menuEdit.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuBar.add(menuFormat);
		menuFormat.setMnemonic('O');
		menuFormat.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuBar.add(menuView);
		menuView.setMnemonic('V');
		menuView.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuBar.add(menuHelp);
		menuHelp.setMnemonic('H');
		menuHelp.setFont(new Font("Monospaced", Font.PLAIN, 12));//主菜单
		//子菜单
		//文件
		menuFile.add(menuFileNew);
		menuFileNew.setMnemonic('N');
		menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));//快捷健; 
		menuFileNew.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileNew.setBackground(Color.white);
		menuFile.add(menuFileOpen);
		menuFileOpen.setMnemonic('O');//访问健; 
		menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));//快捷健; 
		menuFileOpen.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileOpen.setBackground(Color.white);
		menuFile.add(menuFileSave);
		menuFileSave.setMnemonic('S');
		menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		menuFile.add(menuFileSaveAs);
		menuFileSave.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileSave.setBackground(Color.white);
		menuFileSaveAs.setMnemonic('A');
		menuFileSaveAs.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileSaveAs.setBackground(Color.white);

		menuFile.addSeparator();
		menuFile.add(menuFileSetPage);
		menuFileSetPage.setMnemonic('U');
		menuFileSetPage.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileSetPage.setBackground(Color.white);
		menuFile.add(menuFilePrint);
		menuFilePrint.setMnemonic('P');
		menuFilePrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.CTRL_MASK));
		menuFilePrint.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFilePrint.setBackground(Color.white);

		menuFile.addSeparator(); //添加水平线
		menuFile.add(menuFileExit);
		menuFileExit.setMnemonic('X');
		menuFileExit.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileExit.setBackground(Color.white);
		//编辑
		menuEdit.add(menuFileUndo);
		menuFileUndo.setMnemonic('U');
		menuFileUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				InputEvent.CTRL_MASK));
		menuFileUndo.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileUndo.setBackground(Color.white);
		menuEdit.addSeparator();
		menuEdit.add(menuFileCut);
		menuFileCut.setMnemonic('T');
		menuFileCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		menuFileCut.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileCut.setBackground(Color.white);
		menuEdit.add(menuFileCopy);
		menuFileCopy.setMnemonic('C');
		menuFileCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_MASK));
		menuFileCopy.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileCopy.setBackground(Color.white);
		menuEdit.add(menuFilePoste);
		menuFilePoste.setMnemonic('P');
		menuFilePoste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_MASK));
		menuFilePoste.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFilePoste.setBackground(Color.white);
		menuEdit.add(menuFileDel);
		menuFileDel.setMnemonic('L');
		menuFileDel.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_DELETE, 0)); //不要ctrl
		menuFileDel.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileDel.setBackground(Color.white);

		menuEdit.addSeparator();
		menuEdit.add(menuFileFind);
		menuFileFind.setMnemonic('F');
		menuFileFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				InputEvent.CTRL_MASK));
		menuFileFind.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileFind.setBackground(Color.white);
		menuEdit.add(menuFileFindNext);
		menuFileFindNext.setMnemonic('N');
		menuFileFindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
				0));
		menuFileFindNext.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileFindNext.setBackground(Color.white);
		menuEdit.add(menuFileReplace);
		menuFileReplace.setMnemonic('R');
		menuFileReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				InputEvent.CTRL_MASK));
		menuFileReplace.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileReplace.setBackground(Color.white);
		menuEdit.add(menuFileTransfer);
		menuFileTransfer.setMnemonic('G');
		menuFileTransfer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				InputEvent.CTRL_MASK));
		menuFileTransfer.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileTransfer.setBackground(Color.white);

		menuEdit.addSeparator();
		menuEdit.add(menuFileSelectAll);
		menuFileSelectAll.setMnemonic('A');
		menuFileSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				InputEvent.CTRL_MASK));
		menuFileSelectAll.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileSelectAll.setBackground(Color.white);
		menuEdit.add(menuFileTime);
		menuFileTime.setMnemonic('D');
		menuFileTime.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		menuFileTime.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileTime.setBackground(Color.white);
		//格式

		menuFormat.add(menuFileAuto);
		menuFileAuto.setMnemonic('C');
		menuFileAuto.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileAuto.setBackground(Color.white);
		menuFormat.add(menuFileFont);
		menuFileFont.setMnemonic('N');
		menuFileFont.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileFont.setBackground(Color.white);
		//查看
		menuView.add(menuFileState);
		menuFileState.setMnemonic('S');
		menuFileState.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileState.setBackground(Color.white);
		//帮助
		menuHelp.add(menuFileHelp);
		menuFileHelp.setMnemonic('H');
		menuFileHelp.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileHelp.setBackground(Color.white);
		menuHelp.addSeparator();
		menuHelp.add(menuFileAbout);
		menuFileAbout.setMnemonic('A');
		menuFileAbout.setFont(new Font("Monospaced", Font.PLAIN, 12));
		menuFileAbout.setBackground(Color.white);

		//弹出菜单
		popup = new JPopupMenu();

		popup.add(popupmenuFileUndo);
		popupmenuFileUndo.setMnemonic('U');
		popupmenuFileUndo.setFont(new Font("Monospaced", Font.PLAIN, 12));
		popupmenuFileUndo.setBackground(Color.white);
		popup.addSeparator();
		popup.add(popupmenuFileCut);
		popupmenuFileCut.setMnemonic('T');
		popupmenuFileCut.setFont(new Font("Monospaced", Font.PLAIN, 12));
		popupmenuFileCut.setBackground(Color.white);
		popup.add(popupmenuFileCopy);
		popupmenuFileCopy.setMnemonic('C');
		popupmenuFileCopy.setFont(new Font("Monospaced", Font.PLAIN, 12));
		popupmenuFileCopy.setBackground(Color.white);
		popup.add(popupmenuFilePoste);
		popupmenuFilePoste.setMnemonic('P');
		popupmenuFilePoste.setFont(new Font("Monospaced", Font.PLAIN, 12));
		popupmenuFilePoste.setBackground(Color.white);
		popup.add(popupmenuFileDel);
		popupmenuFileDel.setMnemonic('L');
		popupmenuFileDel.setFont(new Font("Monospaced", Font.PLAIN, 12));
		popupmenuFileDel.setBackground(Color.white);
		popup.addSeparator();
		popup.add(popupmenuFileSelectAll);
		popupmenuFileSelectAll.setMnemonic('A');
		popupmenuFileSelectAll.setFont(new Font("Monospaced", Font.PLAIN, 12));
		popupmenuFileSelectAll.setBackground(Color.white);

		///////////////////////////////////////////////////////////////
		//添加监听器
		//////////////////////////////////////////////////////////////////
		//文件
		menuFileNew.addActionListener(new newActionListener());
		menuFileOpen.addActionListener(new openActionListener());
		menuFileSave.addActionListener(new saveActionListener());
		menuFileSaveAs.addActionListener(new saveasActionListener());
		menuFilePrint.addActionListener(new printActionListener());
		menuFileSetPage.addActionListener(new setpageActionListener());
		menuFileExit.addActionListener(new exitActionListener());
		//编辑
		menuFileUndo.addActionListener(new undoActionListener());
		menuFileCut.addActionListener(new cutActionListener());
		menuFileCopy.addActionListener(new copyActionListener());
		menuFilePoste.addActionListener(new posteActionListener());
		menuFileDel.addActionListener(new delActionListener());
		menuFileFind.addActionListener(new findActionListener());
		menuFileFindNext.addActionListener(new findnextActionListener());
		menuFileReplace.addActionListener(new replaceActionListener());
		menuFileTransfer.addActionListener(new transferActionListener());
		menuFileSelectAll.addActionListener(new selectallActionListener());
		menuFileTime.addActionListener(new timeActionListener());
		/////////////
		///格式

		menuFileAuto.addActionListener(new autoActionListener());
		menuFileFont.addActionListener(new fontActionListener());

		//////////////
		//查看
		menuFileState.addActionListener(new stateActionListener());

		/////
		//帮助
		menuFileHelp.addActionListener(new helpActionListener());
		menuFileAbout.addActionListener(new aboutActionListener());
		///////////////////////////////////////////
		//弹出菜单
		popupmenuFileUndo.addActionListener(new undoActionListener());
		popupmenuFileCut.addActionListener(new cutActionListener());
		popupmenuFileCopy.addActionListener(new copyActionListener());
		popupmenuFilePoste.addActionListener(new posteActionListener());
		popupmenuFileDel.addActionListener(new delActionListener());
		popupmenuFileSelectAll.addActionListener(new selectallActionListener());
		//监控
		Timer t = new Timer(100, new TimePrinterActionListener());
		t.start();

		textarea = new JTextArea();
		textarea.setFont(new Font("Monospaced", Font.PLAIN, 14));//设置文本字体 
		scrollPane = new JScrollPane(textarea); //添加文本  
		Dimension frameSize = getSize();
		scrollPane.setBounds(0, 0, frameSize.width - 7, frameSize.height - 57);
		add(scrollPane);
		textarea.addMouseListener( //鼠标监听器
				new MouseAdapter() {
					public void mousePressed(MouseEvent event)//鼠标按下是弹出菜单
					{
						if (event.getButton() == event.BUTTON3)
							checkForTriggerEvent(event);
					}

					public void mouseReleased(MouseEvent event) {
						if (event.getButton() == event.BUTTON3)
							checkForTriggerEvent(event);
						if (textarea.getSelectedText() != null) {
							if (!textarea.getSelectedText().equals("")) {
								menuFileCut.setEnabled(true);
								menuFileCopy.setEnabled(true);
								menuFileDel.setEnabled(true);
								menuFileState.setEnabled(true);
								popupmenuFileUndo.setEnabled(true);
								popupmenuFileCut.setEnabled(true);
								popupmenuFileCopy.setEnabled(true);
								popupmenuFileDel.setEnabled(true);
								if (textarea.getSelectedText().equals(
										textarea.getText()))
									popupmenuFileSelectAll.setEnabled(false);
								else
									popupmenuFileSelectAll.setEnabled(true);
							} else {
								menuFileCut.setEnabled(false);
								menuFileCopy.setEnabled(false);
								menuFileDel.setEnabled(false);
								menuFileState.setEnabled(false);
								popupmenuFileUndo.setEnabled(false);
								popupmenuFileCut.setEnabled(false);
								popupmenuFileCopy.setEnabled(false);
								popupmenuFileDel.setEnabled(false);
								popupmenuFileSelectAll.setEnabled(false);
							}

						} else {
							menuFileCut.setEnabled(false);
							menuFileCopy.setEnabled(false);
							menuFileDel.setEnabled(false);
							popupmenuFileCut.setEnabled(false);
							popupmenuFileCopy.setEnabled(false);
							popupmenuFileDel.setEnabled(false);
						}
						if (auto % 2 != 0 && auto > 0) {
							getCaretLocation();
						}
					}

					private void checkForTriggerEvent(MouseEvent event) {
						if (event.isPopupTrigger())
							popup.show(textarea, event.getX(), event.getY());//弹出菜单
					}
				});
		textarea.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				if (auto % 2 != 0 && auto > 0) {
					getCaretLocation();
				}
			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (!textarea.getText().equals("")) {
					menuFileUndo.setEnabled(true);
					menuFileFind.setEnabled(true);
					menuFileFindNext.setEnabled(true);
					menuFileState.setEnabled(true);
					popupmenuFileUndo.setEnabled(true);
					popupmenuFileSelectAll.setEnabled(true);
					menuFileTransfer.setEnabled(true);
				} else {
					menuFileUndo.setEnabled(false);
					menuFileCut.setEnabled(false);
					menuFileCopy.setEnabled(false);
					menuFileDel.setEnabled(false);
					menuFileFind.setEnabled(false);
					menuFileFindNext.setEnabled(false);
					menuFileTransfer.setEnabled(false);
					menuFileState.setEnabled(false);

					popupmenuFileUndo.setEnabled(false);
					popupmenuFileCut.setEnabled(false);
					popupmenuFileCopy.setEnabled(false);
					popupmenuFileDel.setEnabled(false);
					popupmenuFileSelectAll.setEnabled(false);
				}
				if (auto % 2 != 0 && auto > 0) {
					getCaretLocation();
				}
			}
		});
		textarea.addMouseListener(new MouseAdapter() {
			public void MousePressed(MouseEvent event) {
			}

			public void MouseClicked(MouseEvent event) {
			}

			public void MouseReleased(MouseEvent event) {
			}

			public void MouseEntered(MouseEvent event) {
			}

			public void MouseExited(MouseEvent event) {
			}
		});

		Document doc = textarea.getDocument(); //撤销
		doc.addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent evt) {
				undo.addEdit(evt.getEdit());
			}
		});

		menuFileUndo.setEnabled(false);
		menuFileCut.setEnabled(false);
		menuFileCopy.setEnabled(false);
		menuFileDel.setEnabled(false);
		menuFileFind.setEnabled(false);
		menuFileFindNext.setEnabled(false);
		menuFileTransfer.setEnabled(false);
		menuFileState.setEnabled(false);

		popupmenuFileUndo.setEnabled(false);
		popupmenuFileCut.setEnabled(false);
		popupmenuFileCopy.setEnabled(false);
		popupmenuFileDel.setEnabled(false);
		popupmenuFileSelectAll.setEnabled(false);

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				Dimension frameSize = getSize();
				if (auto % 2 == 0) {
					scrollPane.setBounds(0, 0, frameSize.width - 7,
							frameSize.height - 57);
					textarea.setBounds(0, 0, frameSize.width - 7,
							frameSize.height - 57);
				} else {
					scrollPane.setBounds(0, 0, frameSize.width - 7,
							frameSize.height - 80);
					textarea.setBounds(0, 0, frameSize.width - 7,
							frameSize.height - 80);
					row.setBounds(frameSize.width - 180, frameSize.height - 95,
							40, 50);
					col.setBounds(frameSize.width - 100, frameSize.height - 95,
							40, 50);
					getCaretLocation();
				}

			}
		});

		addWindowListener( //关闭窗口弹出提示
		new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				if (textarea.getText().equals(fileContext)) {
					setServiceSetting();
					System.exit(0);
				} else {
					JOptionPane panel = new JOptionPane();

					JOptionPane p = new JOptionPane();
					p.setFont(new Font("Monospaced", Font.PLAIN, 12));
					String message = "文件  " + fileName + "  的文字已经改变。"
							+ "\n 想保存文件吗？";
					int check = p.showConfirmDialog(null, message, "警告",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (check == 0) {
						int isOk = saveFile();
						if (isOk == 1) {
							setServiceSetting();
							System.exit(0);
						}
					}
					if (check == 1) {
						setServiceSetting();
						System.exit(0);
					}
					if (check == 2) {
						setServiceSetting();
						return;
					}

				}
			}

		});
		menuFilePrint.enable(false);
		menuFileSetPage.enable(false);
	}

	/////////////////////////////////
	//////监听器 注册//////////////////
	////////////////////////////////

	///////////文件//////////////
	private class newActionListener implements ActionListener////新建
	{
		public void actionPerformed(ActionEvent event) {
			if (textarea.getText().equals(fileContext)) {
				textarea.setText("");
				fileName = "无标题";
				fileContext = textarea.getText();
				setFileName();
			} else {
				JOptionPane p = new JOptionPane();
				p.setFont(new Font("Monospaced", Font.PLAIN, 12));
				String message = "文件  " + fileName + "  的文字已经改变。"
						+ "\n 想保存文件吗？";
				int check = p.showConfirmDialog(null, message, "警告",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (check == 0) {
					int isOk = saveFile();
					if (isOk == 1) {
						textarea.setText("");
						fileName = "无标题";
						fileContext = textarea.getText();
						setFileName();
					}
				}
				if (check == 1) {
					textarea.setText("");
					fileName = "无标题";
					fileContext = textarea.getText();
					setFileName();
				}
				if (check == 2) {
					dispose();
				}
			}
		}
	}

	private class openActionListener implements ActionListener/////打开
	{
		public void actionPerformed(ActionEvent event) {
			if (textarea.getText().equals(fileContext)) {
				int isOk = openFile();
				if (isOk == 1) {
					setFileName();
				}
			} else {
				JOptionPane p = new JOptionPane();
				p.setFont(new Font("Monospaced", Font.PLAIN, 12));
				String message = "文件  " + fileName + "  的文字已经改变。"
						+ "\n 想保存文件吗？";
				int check = p.showConfirmDialog(null, message, "警告",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (check == 0) {
					int isOk = saveFile();
					if (isOk == 1) {
						int isOk1 = openFile();
						if (isOk1 == 1) {
							setFileName();
						}
					}
				}
				if (check == 1) {
					int isOk1 = openFile();
					if (isOk1 == 1) {
						setFileName();
					}
				}
				if (check == 2) {
					return;
				}
			}
		}
	}

	private class saveActionListener implements ActionListener//////保存
	{
		public void actionPerformed(ActionEvent event) {
			int isOk = saveFile();
			if (isOk == 1) {
				setFileName();
			}
		}
	}

	private class saveasActionListener implements ActionListener////另存为
	{
		public void actionPerformed(ActionEvent event) {
			int isOk = save_asFile();
			if (isOk == 1) {
				setFileName();
			}
		}
	}

	private class printActionListener implements ActionListener///打印
	{
		public void actionPerformed(ActionEvent event) {

		}
	}

	private class setpageActionListener implements ActionListener//页面设置
	{
		public void actionPerformed(ActionEvent event) {

		}
	}

	private class exitActionListener implements ActionListener////退出
	{
		public void actionPerformed(ActionEvent event) {
			if (textarea.getText().equals(fileContext)) {
				setServiceSetting();
				System.exit(0);
			} else {
				JOptionPane p = new JOptionPane();
				p.setFont(new Font("Monospaced", Font.PLAIN, 12));
				String message = "文件  " + fileName + "  的文字已经改变。"
						+ "\n 想保存文件吗？";
				int check = p.showConfirmDialog(null, message, "警告",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (check == 0) {
					int isOk = saveFile();
					if (isOk == 1) {
						setServiceSetting();
						System.exit(0);
					}
				}
				if (check == 1) {
					setServiceSetting();
					System.exit(0);
				}
				if (check == 2) {
					//  dispose();
					setServiceSetting();
					return;
				}
			}
		}
	}

	///////////////////////编辑////////////

	private class undoActionListener implements ActionListener///撤销
	{
		public void actionPerformed(ActionEvent event) {
			try {
				if (undo.canUndo()) {
					undo.undo();
				}
			} catch (CannotUndoException e) {
			}
		}
	}

	private class cutActionListener implements ActionListener///剪切
	{
		public void actionPerformed(ActionEvent event) {
			copy = textarea.getSelectedText();
			textarea.cut();
			menuFileCut.setEnabled(false);
			menuFileCopy.setEnabled(false);
			menuFileDel.setEnabled(false);
		}
	}

	private class copyActionListener implements ActionListener///复制
	{
		public void actionPerformed(ActionEvent event) {
			copy = textarea.getSelectedText();

		}
	}

	private class posteActionListener implements ActionListener///粘贴
	{
		public void actionPerformed(ActionEvent event) {
			textarea.insert(copy, textarea.getCaretPosition());
		}
	}

	private class delActionListener implements ActionListener//删除
	{
		public void actionPerformed(ActionEvent event) {
			textarea.cut();
			textarea.cut();
			menuFileCut.setEnabled(false);
			menuFileCopy.setEnabled(false);
			menuFileDel.setEnabled(false);
		}
	}

	private class findActionListener implements ActionListener//查找
	{
		public void actionPerformed(ActionEvent event) {
			SearchDialog search = new SearchDialog(frame);
			search.setVisible(true);
		}
	}

	private class findnextActionListener implements ActionListener//查找下一个
	{
		public void actionPerformed(ActionEvent event) {
			findNext();
		}
	}

	private class replaceActionListener implements ActionListener//替换
	{
		public void actionPerformed(ActionEvent event) {
			ReplaceDialog replace = new ReplaceDialog(frame);
			replace.setVisible(true);
		}
	}

	private class transferActionListener implements ActionListener//转到
	{
		public void actionPerformed(ActionEvent event) {
			TransferDialog transfer = new TransferDialog(frame);
			transfer.setVisible(true);
		}
	}

	private class selectallActionListener implements ActionListener//全选
	{
		public void actionPerformed(ActionEvent event) {
			copy = selectAll();
		}
	}

	private class timeActionListener implements ActionListener//时间/日期
	{
		public void actionPerformed(ActionEvent event) {
			getTime();
		}
	}

	//////////////格式/////////////////
	private class autoActionListener implements ActionListener//自动换行
	{
		public void actionPerformed(ActionEvent event) {
			if (auto % 2 == 0) {
				textarea.setLineWrap(true);
				menuFileAuto.setText("√自动换行(W)");
			} else {
				textarea.setLineWrap(false);
				menuFileAuto.setText("  自动换行(W)");
			}
			auto++;
		}
	}

	private class fontActionListener implements ActionListener//字体
	{
		public void actionPerformed(ActionEvent event) {
			FontDialog style = new FontDialog(frame);

			style.setVisible(true);
		}
	}

	/////////////////查看/////////////

	private class stateActionListener implements ActionListener///状态栏
	{
		public void actionPerformed(ActionEvent event) {
			Dimension frameSize = getSize();
			if (auto % 2 == 0) {
				try {
					String temp = textarea.getText(0,
							textarea.getCaretPosition()).replace("\r\n", "");
					temp.replace("\r", "");
					temp.replace("\t", "");
					int index = 0;
					int start = 0;
					for (int i = 0; i < temp.length(); i++) {
						index = temp.indexOf('\n', start);
						if (index == -1) {
							break;
						} else {
							start = index + 1;
							r = temp.length() - index - 1;
							c++;
						}
					}
					if (c == 1) {
						r = temp.length();
					}

				} catch (BadLocationException e) {
				}

				scrollPane.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 80);
				textarea.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 80);
				row.setText("行 :" + c);
				row.setBounds(frameSize.width - 180, frameSize.height - 95, 40,
						50);
				col.setText("列 :" + r);
				col.setBounds(frameSize.width - 100, frameSize.height - 95, 40,
						50);
				add(row);
				add(col);
			} else {
				scrollPane.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 57);
				textarea.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 57);
			}
			auto++;

		}
	}

	///////////////帮助/////////////////////////
	private class helpActionListener implements ActionListener///帮助
	{
		public void actionPerformed(ActionEvent event) {
			try {
				Runtime.getRuntime().exec("cmd   /c start  pore\\notepad.chm");
			} catch (IOException e) {
			}

		}
	}

	private class aboutActionListener implements ActionListener///关于
	{
		public void actionPerformed(ActionEvent event) {
			AboutDialog about = new AboutDialog(frame);
			about.setVisible(true);
		}
	}

	private class TimePrinterActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Dimension frameSize = getSize();
			if (auto % 2 == 0) {
				scrollPane.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 57);
				textarea.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 57);
			} else {
				scrollPane.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 80);
				textarea.setBounds(0, 0, frameSize.width - 7,
						frameSize.height - 80);
				row.setBounds(frameSize.width - 180, frameSize.height - 95, 40,
						50);
				col.setBounds(frameSize.width - 100, frameSize.height - 95, 40,
						50);
				getCaretLocation();
			}

		}
	}

	public int openFile()///打开文件
	{

		FileDialog s = new FileDialog(frame);
		s.setMode(FileDialog.LOAD);
		s.setTitle("打开");
		s.setVisible(true);
		fileDirection = s.getDirectory();
		if (s.getFile() == null || s.getFile().equals("")) {
			return -1;
		} else {
			try {
				fileName = s.getFile();
				FileReader fr = new FileReader(fileDirection + fileName);
				BufferedReader br = new BufferedReader(fr);
				String str = br.readLine();
				textarea.setText("");
				while (str != null) {
					textarea.append(str + "\n");
					str = br.readLine();
				}
				fileContext = textarea.getText();
				return 1;
			} catch (IOException ioexception) {
				JOptionPane.showMessageDialog(this, "文件打开出错", "警告",
						JOptionPane.ERROR_MESSAGE);
			}
			return -1;
		}
	}

	public int saveFile()//保存文件
	{
		String name, remenberName;
		remenberName = fileName;
		FileDialog s = new FileDialog(frame);
		s.setMode(FileDialog.SAVE);
		s.setFile("*.txt");
		s.setDirectory("D:\\");
		s.setTitle("保存");
		if (fileName.equals(getTitle().replace("-记事本", "") + ".txt")) {
			s.setVisible(false);
			name = fileDirection + fileName;
		} else {
			s.setVisible(true);
			fileDirection = s.getDirectory();
			fileName = s.getFile();
			name = fileDirection + fileName;
		}

		if (fileName == null || fileName.equals("")) {
			fileName = remenberName;
			return -1;
		} else {
			try {
				FileOutputStream fos = new FileOutputStream(name);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				String saveString = textarea.getText().replaceAll("\n", "\r\n");
				osw.write(saveString);
				osw.flush();
				osw.close();
				fileContext = textarea.getText();
				return 1;
			} catch (IOException ioexception) {
				JOptionPane.showMessageDialog(this, "文件打开出错", "警告",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return -1;
	}

	public int save_asFile() {
		FileDialog s = new FileDialog(frame);
		s.setMode(FileDialog.SAVE);
		s.setTitle("另存为");
		s.setFile("*.txt");
		s.setDirectory("D:\\");
		s.setVisible(true);
		fileDirection = s.getDirectory();
		if (s.getFile() == null || s.getFile().equals("")) {
			return -1;
		} else {
			fileName = s.getFile();
			try {
				FileOutputStream fos = new FileOutputStream(fileDirection
						+ fileName);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				String saveString = textarea.getText().replaceAll("\n", "\r\n");
				osw.write(saveString);
				osw.flush();
				osw.close();
				fileContext = textarea.getText();
				return 1;
			} catch (IOException ioexception) {
				JOptionPane.showMessageDialog(this, "文件打开出错", "警告",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return -1;
	}

	public String selectAll()//全选
	{
		textarea.selectAll();
		return textarea.getSelectedText();
	}

	public void getTime()//添加时间
	{
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm yyyy-M-dd ",
				Locale.getDefault());
		String time = formatter.format(currentDate);
		textarea.insert(time, textarea.getCaretPosition());
	}

	public Point getFrameLocation() {
		Point location = this.getLocation();
		return location;
	}

	public void setFileName() {
		setTitle(fileName.replace(".txt", "") + "-记事本");
	}

	public void findNext() {
		int index;
		int length;
		try {
			length = searchWord.length();
		} catch (Exception e) {
			SearchDialog search = new SearchDialog(frame);
			frame.setSize(300, 200);
			search.setVisible(true);
			return;
		}

		String tmp = textarea.getText().replaceAll("\r\n", "");
		tmp = tmp.replaceAll("\r", "");
		tmp = tmp.replaceAll("\t", "");

		try {
			if (directionStatus == 0)
				index = tmp.indexOf(searchWord, start + length);
			else if (directionStatus == 1)
				index = tmp.lastIndexOf(searchWord, start - length);
			else
				index = -1;
			if (index == -1)
				JOptionPane.showMessageDialog(null, "找不到" + searchWord, "温馨提示",
						JOptionPane.INFORMATION_MESSAGE);
			else {
				start = index;
				textarea.setSelectedTextColor(Color.blue);
				textarea.select(index, index + length);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "找不到您想要查找的词！", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void getCaretLocation() {
		Dimension p = getColumns();
		r = p.width;
		c = p.height;
		row.setText("行 :" + c);
		col.setText("列 :" + r);
	}

	public Dimension getColumns() {
		Dimension p = new Dimension();
		c = 1;
		try {
			String temp = textarea.getText(0, textarea.getCaretPosition())
					.replace("\r\n", "");
			temp.replace("\r", "");
			temp.replace("\t", "");
			int index = 0;
			int start = 0;
			for (int i = 0; i < temp.length(); i++) {
				index = temp.indexOf('\n', start);
				if (index == -1) {
					break;
				} else {
					start = index + 1;
					r = temp.length() - index - 1;
					c++;
				}
			}
			if (c == 1) {
				r = temp.length();
			}
			p.width = r;
			p.height = c;
			return p;
		} catch (BadLocationException et) {
			p.width = 0;
			p.height = 0;
			return p;
		}

	}

	public Dimension getServiceSetting() {
		Dimension p = new Dimension();
		FileDialog s = new FileDialog(frame);
		s.setMode(FileDialog.LOAD);
		try {
			FileReader fr = new FileReader("pore\\Setting.ini");
			BufferedReader br = new BufferedReader(fr);
			String str = br.readLine();
			int[] size = new int[4];
			for (int i = 0; str != null; i++) {
				size[i] = Integer.parseInt(str);
				str = br.readLine();
			}
			p.width = size[0];
			p.height = size[1];
			getsize.width = size[2];
			getsize.height = size[3];
			return p;
		} catch (IOException ioexception) {
			Toolkit kit = Toolkit.getDefaultToolkit();
			p = kit.getScreenSize();
			getsize = p;
			return p;
		}

	}

	public void setServiceSetting() {
		FileDialog s = new FileDialog(frame);
		s.setMode(FileDialog.SAVE);
		try {
			FileOutputStream fos = new FileOutputStream("pore\\Setting.ini");
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			osw.write((int) Math.round(getLocation().getX()) + "\r\n"
					+ (int) Math.round(getLocation().getY()) + "\r\n"
					+ getSize().width + "\r\n" + getSize().height);
			osw.flush();
			osw.close();
		} catch (IOException ioexception) {
		}

	}

	private class AboutDialog extends JDialog///关于记事本
	{
		private JTextArea about;

		private JPanel panel;

		private JButton ok;

		public AboutDialog(JFrame frame) {
			//  super(frame,"关于记事本",false);
			setTitle("关于记事本");
			setResizable(false);//窗体大小不可变

			String author = "\n \t  作者：05141_38 刘煌：1.0\n\n\n \n\n\n\t\t 版本：1.0";
			String contact = "\t  liuhuang007@yahoo.com.cn\n\t  QQ:79858908\n\t  时间：2006年12月9日\n  ";
			about = new JTextArea();
			about.setEditable(false);//不可修改
			about.setLineWrap(true);//自动换行
			about.setColumns(20);//设置列数
			about.setFont(new Font("宋体", Font.PLAIN, 12));
			about.setBounds(5, 5, 290, 200);
			about.append(author + "\n\n\n" + contact);
			panel = new JPanel();
			panel.setBackground(Color.white);
			panel.setLayout(null);
			panel.setBounds(0, 0, 300, 300);
			panel.add(about);

			ok = new JButton("确定(Y)");
			ok.setFont(new Font("Monospaced", Font.PLAIN, 12));
			ok.setBounds(110, 230, 80, 20);
			ok.setMnemonic('Y');
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			panel.add(ok);
			add(panel);
			Point location = getFrameLocation();
			setLocation((int) location.getX() + 200,
					(int) location.getY() + 150);
			setSize(300, 300);
		}
	}

	private class FontDialog extends JDialog {
		private JLabel fontname, fontstyle, fontsize;

		private JPanel style, myview;

		private JTextArea view;

		private JButton ok, cancel;

		private JList fontnameList, fontstyleList, fontsizeList;

		private JTextField myfontname, myfontstyle, myfontsize;

		private String fontNames[] = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//获得电脑支持的字体

		private String fontStyles[] = { "常规", "斜体", "粗体", "粗斜体" };

		private String fontSizes[] = { "9", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
				"26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
				"36", "37" };

		private String fontsize_check, style_check, fontname_check;

		public FontDialog(JFrame frame) {
			//super(frame,"字体",false);
			setTitle("字体");
			setResizable(false);
			setBackground(Color.white);
			setLayout(null);
			////////////////字体//////////////////// 
			fontname = new JLabel("字体");
			fontname.setFont(new Font("Monospaced", Font.PLAIN, 12));
			fontname.setBounds(10, 10, 50, 20);
			myfontname = new JTextField("宋体");
			myfontname.setEditable(false);
			myfontname.setBackground(Color.white);
			myfontname.setBounds(10, 35, 100, 20);
			fontnameList = new JList(fontNames);
			fontnameList.setSelectedIndex(129);
			fontnameList.setFont(new Font("Monospaced", Font.PLAIN, 12));
			fontnameList.setVisibleRowCount(129);
			fontnameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//单选形式
			fontnameList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					myfontname.setText(fontNames[fontnameList
							.getSelectedIndex()]);//重新设置字体名称
					if (fontnameList.getSelectedIndex() < 129) {
						view.setText("LiuHuang Eidt");
					} else {
						view.setText("刘煌制作");
					}
					fontname_check = myfontname.getText();//获得字体，格式，大小
					style_check = myfontstyle.getText();
					fontsize_check = myfontsize.getText();
					int fontnamefontsize = Integer.parseInt(fontsize_check);//类型转换
					int fontnamestyle = Font.PLAIN;
					if (style_check.equals("常规"))
						fontnamestyle = Font.PLAIN;
					if (style_check.equals("斜体"))
						fontnamestyle = Font.ITALIC;
					if (style_check.equals("粗体"))
						fontnamestyle = Font.BOLD;
					if (style_check.equals("粗斜体"))
						fontnamestyle = Font.BOLD + Font.ITALIC;
					view.setFont(new Font(fontname_check, fontnamestyle,
							fontnamefontsize));
				}
			});
			JScrollPane scroll = new JScrollPane(fontnameList);
			scroll.setBounds(10, 60, 100, 100);
			///////////////字形设置////////////////////////
			fontstyle = new JLabel("字形");
			fontstyle.setFont(new Font("Monospaced", Font.PLAIN, 12));
			fontstyle.setBounds(120, 10, 50, 20);
			myfontstyle = new JTextField("常规");
			myfontstyle.setEditable(false);
			myfontstyle.setBackground(Color.white);
			myfontstyle.setBounds(120, 35, 100, 20);
			fontstyleList = new JList(fontStyles);
			fontstyleList.setSelectedIndex(0);
			fontstyleList.setFont(new Font("Monospaced", Font.PLAIN, 12));
			fontstyleList.setVisibleRowCount(5);
			fontstyleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			fontstyleList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					myfontstyle.setText(fontStyles[fontstyleList
							.getSelectedIndex()]);
					fontname_check = myfontname.getText();
					style_check = myfontstyle.getText();
					fontsize_check = myfontsize.getText();
					int fontnamefontsize = Integer.parseInt(fontsize_check);
					int fontnamestyle = Font.PLAIN;
					if (style_check.equals("常规"))
						fontnamestyle = Font.PLAIN;
					if (style_check.equals("斜体"))
						fontnamestyle = Font.ITALIC;
					if (style_check.equals("粗体"))
						fontnamestyle = Font.BOLD;
					if (style_check.equals("粗斜体"))
						fontnamestyle = Font.BOLD + Font.ITALIC;
					view.setFont(new Font(fontname_check, fontnamestyle,
							fontnamefontsize));

				}
			});
			JScrollPane scroll2 = new JScrollPane(fontstyleList);
			scroll2.setBounds(120, 60, 100, 80);
			////////////////大小///////////////////
			fontsize = new JLabel("大小");
			fontsize.setFont(new Font("Monospaced", Font.PLAIN, 12));
			fontsize.setBounds(230, 10, 50, 20);
			myfontsize = new JTextField("12");
			myfontsize.setEditable(false);
			myfontsize.setBackground(Color.white);
			myfontsize.setBounds(230, 35, 50, 20);
			fontsizeList = new JList(fontSizes);
			fontsizeList.setSelectedIndex(3);
			fontsizeList.setFont(new Font("Monospaced", Font.PLAIN, 12));
			fontsizeList.setVisibleRowCount(5);
			fontsizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			fontsizeList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					myfontsize.setText(fontSizes[fontsizeList
							.getSelectedIndex()]);
					fontname_check = myfontname.getText();
					style_check = myfontstyle.getText();
					fontsize_check = myfontsize.getText();
					int fontnamefontsize = Integer.parseInt(fontsize_check);
					int fontnamestyle = Font.PLAIN;
					if (style_check.equals("常规"))
						fontnamestyle = Font.PLAIN;
					if (style_check.equals("斜体"))
						fontnamestyle = Font.ITALIC;
					if (style_check.equals("粗体"))
						fontnamestyle = Font.BOLD;
					if (style_check.equals("粗斜体"))
						fontnamestyle = Font.BOLD + Font.ITALIC;
					view.setFont(new Font(fontname_check, fontnamestyle,
							fontnamefontsize));
				}
			});
			JScrollPane scroll3 = new JScrollPane(fontsizeList);
			scroll3.setBounds(230, 60, 50, 80);

			ok = new JButton("确定");
			cancel = new JButton("取消");
			ok.setFont(new Font("Monospaced", Font.PLAIN, 12));
			cancel.setFont(new Font("Monospaced", Font.PLAIN, 12));
			ok.setBounds(290, 35, 60, 20);
			cancel.setBounds(290, 60, 60, 20);
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fontname_check = myfontname.getText();
					style_check = myfontstyle.getText();
					fontsize_check = myfontsize.getText();
					int fontnamefontsize = Integer.parseInt(fontsize_check);
					int fontnamestyle = Font.PLAIN;
					if (style.equals("常规"))
						fontnamestyle = Font.PLAIN;
					if (style.equals("斜体"))
						fontnamestyle = Font.ITALIC;
					if (style.equals("粗体"))
						fontnamestyle = Font.BOLD;
					if (style.equals("粗斜体"))
						fontnamestyle = Font.BOLD + Font.ITALIC;
					textarea.setFont(new Font(fontname_check, fontnamestyle,
							fontnamefontsize));
					dispose();
				}
			});
			//////////////示例框架///////////////
			view = new JTextArea("刘煌制作");
			view.setEditable(false);
			view.setFont(new Font("Monospaced", Font.PLAIN, 12));
			view.setBorder(BorderFactory.createLoweredBevelBorder());//内嵌边框
			view.setBounds(0, 0, 160, 60);
			style = new JPanel();
			style.setLayout(null);
			style.setBounds(20, 20, 160, 60);
			style.setBorder(BorderFactory.createLoweredBevelBorder());
			style.add(view);

			myview = new JPanel();
			myview.setLayout(null);
			myview.setFont(new Font("Monospaced", Font.PLAIN, 12));
			myview.setBorder(BorderFactory.createTitledBorder("示例")); ///外围框架
			myview.setBounds(105, 165, 200, 100);
			myview.add(style);
			////////////添加主件////////////////
			add(fontname);
			add(myfontname);
			add(scroll);
			add(fontstyle);
			add(myfontstyle);
			add(scroll2);
			add(fontsize);
			add(myfontsize);
			add(scroll3);
			add(ok);
			add(cancel);
			add(myview);
			////////////设置位置////////////////////    
			Point location = getFrameLocation();
			setLocation((int) location.getX() + 200,
					(int) location.getY() + 150);
			setSize(360, 300);
		}
	}

	private class SearchDialog extends JDialog {
		private JLabel label;

		private JButton search, cancel;

		private JTextField word;

		private JCheckBox upperlower;

		private JPanel direction;

		private JRadioButton up, down;

		private ButtonGroup radioGroup;

		private int upperlowerStatus = 1;

		private int index = 0;

		private int length = 0;

		public SearchDialog(JFrame frame) {
			setTitle("查找");
			setLayout(null);
			setResizable(false);
			CheckBox check = new CheckBox();
			RadioButtonHandler radio = new RadioButtonHandler();

			label = new JLabel("查找内容(N)");
			label.setFont(new Font("宋体", Font.PLAIN, 12));
			label.setBounds(10, 10, 80, 20);

			word = new JTextField();
			word.setBackground(Color.white);
			word.setFont(new Font("宋体", Font.PLAIN, 12));
			word.setBounds(90, 10, 190, 20);
			word.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e)//监视是否输入了内容
				{
					if (word.getText().length() >= 0
							|| !word.getText().equals(""))
						search.setEnabled(true);
					else
						search.setEnabled(false);
				}

				public void keyPressed(KeyEvent e)//监视内容是否改变
				{
					if (word.getText().length() > 0
							|| !word.getText().equals(""))
						search.setEnabled(true);
					else
						search.setEnabled(false);
				}

				public void keyReleased(KeyEvent e) {
					if (word.getText().length() > 0
							|| !word.getText().equals(""))
						search.setEnabled(true);
					else
						search.setEnabled(false);
				}
			});
			addWindowListener( //关闭窗口弹出释放资源
			new WindowAdapter() {
				public void windowClosing(WindowEvent event) {
					dispose();
				}

			});

			search = new JButton("查找下一个(F)");
			search.setEnabled(false);
			search.setFont(new Font("宋体", Font.PLAIN, 12));
			search.setBounds(290, 10, 120, 20);
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String tmp;
					if (upperlowerStatus == 1) {
						searchWord = word.getText().toUpperCase();
						tmp = textarea.getText().replaceAll("\r\n", "")
								.toUpperCase();
						tmp = tmp.replaceAll("\r", "");
						tmp = tmp.replaceAll("\t", "");
						tmp.toUpperCase();
					} else {
						tmp = textarea.getText().replaceAll("\r\n", "");
						searchWord = word.getText();
						tmp = tmp.replaceAll("\r", "");
						tmp = tmp.replaceAll("\t", "");
					}

					try {

						if (directionStatus == 0)
							index = tmp.indexOf(searchWord, start + length);
						if (directionStatus == 1)
							index = tmp.lastIndexOf(searchWord, start - length);

						if (index == -1)
							JOptionPane.showMessageDialog(null, "找不到"
									+ word.getText(), "温馨提示",
									JOptionPane.INFORMATION_MESSAGE);
						else {
							start = index;
							length = searchWord.length();
							textarea.setSelectedTextColor(Color.blue);//选中              
							textarea.select(index, index + length);
						}
					} catch (Exception event) {
						JOptionPane.showMessageDialog(null, "找不到"
								+ word.getText(), "温馨提示",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			});

			cancel = new JButton("取消");
			cancel.setFont(new Font("宋体", Font.PLAIN, 12));
			cancel.setBounds(290, 40, 120, 20);
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			//////////////////复选框//////////////////
			upperlower = new JCheckBox("区分大小写(C)");
			upperlower.setSelected(false);
			upperlower.setMnemonic('C');
			upperlower.setFont(new Font("宋体", Font.PLAIN, 12));
			upperlower.setBounds(10, 70, 110, 20);
			upperlower.addItemListener(check);

			direction = new JPanel();
			direction.setLayout(new BorderLayout());
			direction.setBorder(BorderFactory.createTitledBorder("方向"));
			direction.setBounds(130, 40, 150, 50);
			///////////////单选按钮组合框/////////////
			///////////////添加单选按钮///////////////
			radioGroup = new ButtonGroup();
			up = new JRadioButton("向上(U)");
			up.setMnemonic('U');
			up.addItemListener(radio);
			down = new JRadioButton("向下(D)");
			down.setMnemonic('D');
			down.setSelected(true);
			down.addItemListener(radio);
			up.setFont(new Font("宋体", Font.PLAIN, 12));
			down.setFont(new Font("宋体", Font.PLAIN, 12));
			direction.add(up, BorderLayout.WEST);
			direction.add(down, BorderLayout.CENTER);
			radioGroup.add(up);
			radioGroup.add(down);
			//////////添加组件////////////
			add(label);
			add(word);
			add(search);
			add(upperlower);
			add(direction);
			add(cancel);
			if (textarea.getSelectedText() != null) {
				if (!textarea.getSelectedText().equals("")) {
					word.setText(textarea.getSelectedText());
					start = textarea.getCaretPosition();
					search.setEnabled(true);
				}
			}
			////////初始化位置，大小////////////////////////  
			Point location = getFrameLocation();
			setLocation((int) location.getX() + 200,
					(int) location.getY() + 150);
			setSize(430, 130);
		}

		private class RadioButtonHandler implements ItemListener//单选按钮监听器
		{
			public void itemStateChanged(ItemEvent event) {
				if (event.getSource() == up) {
					directionStatus = 1;
					start = textarea.getCaretPosition();
					length = 0;
				}
				if (event.getSource() == down) {
					directionStatus = 0;
					start = textarea.getCaretPosition();
					length = 0;
				}
			}
		}

		private class CheckBox implements ItemListener//复选按钮监听器
		{
			public void itemStateChanged(ItemEvent event) {
				if (event.getSource() == upperlower) {
					if (event.getStateChange() == ItemEvent.SELECTED)
						upperlowerStatus = 0;
					else
						upperlowerStatus = 1;
				}
			}
		}

	}

	private class ReplaceDialog extends JDialog {
		private JLabel context, replaceContext;

		private JTextField word, myReplace;

		private JButton search, replace, replaceAll, cancel;

		private JCheckBox UpperLower;

		private int length1 = 0, length2 = 0;

		private int start1 = 0, start2 = 0;

		private int index = 0;

		private int upperlowerStatus = 1;

		public ReplaceDialog(JFrame frame) {
			setTitle("查找");
			setLayout(null);
			setResizable(false);
			context = new JLabel("查找内容(N):");
			replaceContext = new JLabel("替换为(P)");
			context.setFont(new Font("宋体", Font.PLAIN, 12));
			replaceContext.setFont(new Font("宋体", Font.PLAIN, 12));
			context.setBounds(10, 10, 80, 20);
			replaceContext.setBounds(10, 40, 80, 20);

			word = new JTextField();
			word.setFont(new Font("宋体", Font.PLAIN, 12));
			myReplace = new JTextField();
			myReplace.setFont(new Font("宋体", Font.PLAIN, 12));
			word.setBounds(90, 10, 130, 20);
			myReplace.setBounds(90, 40, 130, 20);

			word.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {

					if (word.getText().length() >= 0
							|| !word.getText().equals("")) {
						search.setEnabled(true);
						replace.setEnabled(true);
						replaceAll.setEnabled(true);
					} else {
						search.setEnabled(false);
						replace.setEnabled(false);
						replaceAll.setEnabled(false);
					}

				}

				public void keyPressed(KeyEvent e) {
					if (word.getText().length() > 0
							|| !word.getText().equals("")) {
						search.setEnabled(true);
						replace.setEnabled(true);
						replaceAll.setEnabled(true);
					} else {
						search.setEnabled(false);
						replace.setEnabled(false);
						replaceAll.setEnabled(false);
					}

				}

				public void keyReleased(KeyEvent e) {
					if (word.getText().length() > 0
							|| !word.getText().equals("")) {
						search.setEnabled(true);
						replace.setEnabled(true);
						replaceAll.setEnabled(true);
					} else {
						search.setEnabled(false);
						replace.setEnabled(false);
						replaceAll.setEnabled(false);
					}

				}
			});

			search = new JButton("查找下一个(F)");
			replace = new JButton("替换(R)");
			replaceAll = new JButton("替换全部(A)");
			search.setEnabled(false);
			replace.setEnabled(false);
			replaceAll.setEnabled(false);
			cancel = new JButton("取消");
			search.setMnemonic('F');
			replace.setMnemonic('R');
			replaceAll.setMnemonic('A');

			search.setBounds(240, 10, 130, 20);
			replace.setBounds(240, 40, 130, 20);
			replaceAll.setBounds(240, 70, 130, 20);
			cancel.setBounds(240, 100, 130, 20);

			UpperLower = new JCheckBox("区分大小写(C)");
			UpperLower.setMnemonic('C');
			UpperLower.setSelected(false);
			UpperLower.setBounds(10, 100, 130, 20);

			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					start2 = 1;
					replaceText(false);
				}
			});

			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					dispose();
				}
			});

			replace.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (start2 == 1) {
						start1 = start1 - 1 - length1
								+ myReplace.getText().length();
						start2 = 0;
					}

					replaceText(true);

				}
			});
			replaceAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String tmp = textarea.getText();
					try {
						tmp = tmp.replaceAll(word.getText(), myReplace
								.getText());
						textarea.setText(tmp);
					} catch (Exception event) {
					}

				}
			});
			add(context);
			add(replaceContext);
			add(word);
			add(myReplace);
			add(search);
			add(replace);
			add(replaceAll);
			add(cancel);
			add(UpperLower);
			if (textarea.getSelectedText() != null) {
				if (!textarea.getSelectedText().equals("")) {
					word.setText(textarea.getSelectedText());
					start1 = textarea.getCaretPosition();
					search.setEnabled(true);
					replace.setEnabled(true);
					replaceAll.setEnabled(true);
				}
			}
			Point location = getFrameLocation();
			setLocation((int) location.getX() + 200,
					(int) location.getY() + 150);
			setSize(390, 170);
			setVisible(true);
		}

		public void replaceText(boolean bool) {
			/// int index=0;  
			String tmp;
			if (upperlowerStatus == 1) {
				searchWord = word.getText().toUpperCase();
				tmp = textarea.getText().replaceAll("\r\n", "").toUpperCase();
				tmp = tmp.replaceAll("\r", "");
				tmp = tmp.replaceAll("\t", "");
				tmp.toUpperCase();
			} else {
				tmp = textarea.getText().replaceAll("\r\n", "");
				searchWord = word.getText();
				tmp = tmp.replaceAll("\r", "");
				tmp = tmp.replaceAll("\t", "");
			}

			/*
			 *tmp=tmp.replaceAll('\r',"");
			 tmp=tmp.replaceAll('\t',"");
			 **/
			try {
				index = tmp.indexOf(searchWord, start1 + length1);
				if (index == -1) {
					if (bool) {
						JOptionPane.showMessageDialog(null, "已经搜索到末尾了", "温馨提示",
								JOptionPane.INFORMATION_MESSAGE);

						return;
					} else {
						index = 0;
						start1 = 0;
						length1 = 0;
					}

				} else {
					start1 = index;
					textarea.setSelectedTextColor(Color.blue);
					length1 = searchWord.length();
					textarea.select(index, index + length1);
				}
			} catch (Exception event) {
				JOptionPane.showMessageDialog(null, "找不到您想要查找的词！", "温馨提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
			if (bool) {
				try {
					if (index < tmp.length()) {
						textarea.replaceSelection(myReplace.getText());
						start1 = index
								- (length1 - myReplace.getText().length());
					}
				} catch (Exception event) {
				}
			}

		}

		private class CheckBox implements ItemListener//复选按钮监听器
		{
			public void itemStateChanged(ItemEvent event) {
				if (event.getSource() == UpperLower) {
					if (event.getStateChange() == ItemEvent.SELECTED)
						upperlowerStatus = 0;
					else
						upperlowerStatus = 1;
				}
			}
		}
	}

	private class TransferDialog extends JDialog {
		public TransferDialog(JFrame frame) {
			setTitle("转到下列行");
			setLayout(null);
			setResizable(false);
			Point location = getFrameLocation();
			setLocation((int) location.getX() + 200,
					(int) location.getY() + 150);
			setSize(300, 130);

			label = new JLabel();
			label.setBounds(35, 15, 60, 40);
			label.setText("行数(L) :");
			add(label);
			buttonOk = new JButton("确定");
			buttonCancel = new JButton("取消");
			buttonOk.setBounds(60, 60, 80, 30);
			buttonCancel.setBounds(160, 60, 80, 30);
			add(buttonOk);
			add(buttonCancel);
			text = new JTextField();
			text.setBounds(95, 18, 140, 25);
			text.setText(getColumn() + "");
			text.selectAll();
			add(text);

			buttonOk.addActionListener(new okActionListener());
			buttonCancel.addActionListener(new cancelActionListener());
		}

		private class okActionListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				int getcolumn = Integer.parseInt(text.getText());
				if (getColumn() < getcolumn) {
					JOptionPane.showMessageDialog(null, "你输入的行数过大或者格式不正确",
							"温馨提示", JOptionPane.INFORMATION_MESSAGE);

				} else {
					int cl = 1;
					String temp = textarea.getText().replace("\r\n", "");
					temp.replace("\r", "");
					temp.replace("\t", "");
					int index = 0;
					int start = 0;
					for (int i = 0; i < temp.length(); i++) {
						index = temp.indexOf('\n', start);
						if (index == -1) {
							break;
						} else {
							start = index + 1;
							cl++;
							if (cl == getcolumn) {
								break;
							}
						}
					}
					textarea.select(start, start);
					dispose();
				}

			}
		}

		public int getColumn() {
			int cl = 1;
			String temp = textarea.getText().replace("\r\n", "");
			temp.replace("\r", "");
			temp.replace("\t", "");
			int index = 0;
			int start = 0;
			for (int i = 0; i < temp.length(); i++) {
				index = temp.indexOf('\n', start);
				if (index == -1) {
					break;
				} else {
					start = index + 1;
					cl++;
				}
			}
			return cl;
		}

		private class cancelActionListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		}

		private JLabel label;

		private JTextField text;

		private JButton buttonOk, buttonCancel;

	}
}
