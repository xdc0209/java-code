package download;

//fontChooser1.setFrame(this);//设置字体,必须要加 
//这个不加就不能解决,记得加 

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

//import com.borland.dbswing.FontChooser;

public class Frame1 extends JFrame
{
	JPanel	    contentPane;

	JMenuBar	jMenuBar1	 = new JMenuBar();

	JMenu	    jMenu1	     = new JMenu();

	JMenu	    jMenu2	     = new JMenu();

	JMenu	    jMenu3	     = new JMenu();

	JMenu	    jMenu4	     = new JMenu();

	JMenu	    jMenu5	     = new JMenu();

	JMenuItem	jMenuItem1	 = new JMenuItem();

	JMenuItem	jMenuItem2	 = new JMenuItem();

	JMenuItem	jMenuItem3	 = new JMenuItem();

	JMenuItem	jMenuItem4	 = new JMenuItem();

	JMenuItem	jMenuItem5	 = new JMenuItem();

	JMenuItem	jMenuItem6	 = new JMenuItem();

	JMenuItem	jMenuItem7	 = new JMenuItem();

	JMenuItem	jMenuItem8	 = new JMenuItem();

	JMenuItem	jMenuItem9	 = new JMenuItem();

	JMenuItem	jMenuItem10	 = new JMenuItem();

	JMenuItem	jMenuItem11	 = new JMenuItem();

	JMenuItem	jMenuItem12	 = new JMenuItem();

	JMenuItem	jMenuItem13	 = new JMenuItem();

	JMenuItem	jMenuItem14	 = new JMenuItem();

	JMenuItem	jMenuItem15	 = new JMenuItem();

	JMenuItem	jMenuItem16	 = new JMenuItem();

	JTextArea	jTextArea1	 = new JTextArea();

	FontChooser	fontChooser1	= new FontChooser();

	JMenuItem	jMenuItem17	 = new JMenuItem();

	public Frame1()
	{
		try
		{
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			jbInit();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * Component initialization.
	 * 
	 * @throws java.lang.Exception
	 */
	private void jbInit() throws Exception
	{
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(null);
		this.setJMenuBar(jMenuBar1);
		setSize(new Dimension(381, 379));
		setTitle("Frame   Title");
		jMenu1.setText("文件");
		jMenu2.setText("编辑");
		jMenu3.setText("格式");
		jMenu4.setText("查看");
		jMenu5.setText("帮助");
		jMenuItem1.setText("新建");
		jMenuItem1.addActionListener(new Frame1_jMenuItem1_actionAdapter(this));
		jMenuItem2.setText("打开");
		jMenuItem2.addActionListener(new Frame1_jMenuItem2_actionAdapter(this));
		jMenuItem3.setText("保存");
		jMenuItem4.setText("另存为");
		jMenuItem5.setText("页面设置");
		jMenuItem6.setText("打印");
		jMenuItem7.setText("退出");
		jMenuItem7.addActionListener(new Frame1_jMenuItem7_actionAdapter(this));
		jMenuItem8.setText("撤销");
		jMenuItem9.setText("剪切");
		jMenuItem10.setText("复制");
		jMenuItem11.setText("粘贴");
		jMenuItem12.setText("删除");
		jMenuItem13.setText("自动换行");
		jMenuItem14.setText("字体");
		jMenuItem14.addActionListener(new Frame1_jMenuItem14_actionAdapter(this));
		jMenuItem15.setText("状态栏");
		jMenuItem16.setText("帮助主题");
		jTextArea1.setBounds(new Rectangle(1, 1, 550, 350));
		jMenuItem17.setText("关于计事本");
		jMenuBar1.add(jMenu1);
		jMenuBar1.add(jMenu2);
		jMenuBar1.add(jMenu3);
		jMenuBar1.add(jMenu4);
		jMenuBar1.add(jMenu5);
		jMenu1.add(jMenuItem1);
		jMenu1.add(jMenuItem2);
		jMenu1.add(jMenuItem3);
		jMenu1.add(jMenuItem4);
		jMenu1.add(jMenuItem5);
		jMenu1.add(jMenuItem6);
		jMenu1.add(jMenuItem7);
		jMenu2.add(jMenuItem8);
		jMenu2.add(jMenuItem9);
		jMenu2.add(jMenuItem10);
		jMenu2.add(jMenuItem11);
		jMenu2.add(jMenuItem12);
		jMenu3.add(jMenuItem13);
		jMenu3.add(jMenuItem14);
		jMenu4.add(jMenuItem15);
		jMenu5.add(jMenuItem16);
		jMenu5.add(jMenuItem17);
		contentPane.add(jTextArea1);
		fontChooser1.setFrame(this);// 设置字体,必须要加 设置字体对象
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args)
	{
		JFrame obj = new Frame1();
		obj.setTitle("计事本");
		obj.setSize(550, 350);
		obj.setVisible(true);
	}

	// 实现新建方法
	public void jMenuItem1_actionPerformed(ActionEvent e)
	{
		jTextArea1.setText("");
	}

	// 实现退出方法
	public void jMenuItem7_actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}

	// 实现打开方法
	public void jMenuItem2_actionPerformed(ActionEvent e)
	{
		JFileChooser open = new JFileChooser();
		open.showOpenDialog(this);
		File file;
		FileReader in = null;
		try
		{ // 得到路径
			file = new File(open.getSelectedFile().getPath());
			in = new FileReader(file); // 创建字符流以读取数据
			int size = (int) file.length(); // 检查文件大小
			char[] data = new char[size]; // 分配一个足够大的数组放置文件
			int chars_read = 0; // 至已读取多少个字符
			while (chars_read < size)
				// 持续循循环直到读守所有字符
				chars_read += in.read(data, chars_read, size - chars_read);
			jTextArea1.setText(new String(data));// 在文本区域中显示字符

		}
		catch (Exception ei)
		{ // 不能为e
			jTextArea1.setText(e.getClass().getName() + ":" + ei.getMessage());
		}
		finally
		{
			try
			{
				if (in != null)
					in.close();
			}
			catch (IOException ee)
			{

			}
		}
		this.repaint();
	}

	// 设置字体
	public void jMenuItem14_actionPerformed(ActionEvent e)
	{
		fontChooser1.showDialog();
		if (fontChooser1.showDialog())
		{
			jTextArea1.setFont(fontChooser1.getSelectedFont());
		}
	}
}

class Frame1_jMenuItem14_actionAdapter implements ActionListener
{
	private Frame1	adaptee;

	Frame1_jMenuItem14_actionAdapter(Frame1 adaptee)
	{
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent actionEvent)
	{
		ActionEvent e = null;
		adaptee.jMenuItem14_actionPerformed(e);
	}
}

class Frame1_jMenuItem1_actionAdapter implements ActionListener
{
	private Frame1	adaptee;

	Frame1_jMenuItem1_actionAdapter(Frame1 adaptee)
	{
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e)
	{
		adaptee.jMenuItem1_actionPerformed(e);
	}
}

class Frame1_jMenuItem2_actionAdapter implements ActionListener
{
	private Frame1	adaptee;

	Frame1_jMenuItem2_actionAdapter(Frame1 adaptee)
	{
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e)
	{
		adaptee.jMenuItem2_actionPerformed(e);
	}
}

class Frame1_jMenuItem7_actionAdapter implements ActionListener
{
	private Frame1	adaptee;

	Frame1_jMenuItem7_actionAdapter(Frame1 adaptee)
	{
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e)
	{
		adaptee.jMenuItem7_actionPerformed(e);
	}
}

class FontChooser
{
	public void setFrame(Frame1 frame1)
	{

	}

	public Font getSelectedFont()
	{
		return null;
	}

	public boolean showDialog()
	{
		return false;
	}
}
