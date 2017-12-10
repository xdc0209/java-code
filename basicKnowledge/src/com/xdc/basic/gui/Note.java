package com.xdc.basic.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class Note extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 8083529212319106721L;

    JMenuBar                  menuBar          = new JMenuBar();
    JMenu                     file             = new JMenu("文件");
    JMenu                     font             = new JMenu("字体");
    JMenuItem                 open             = new JMenuItem("打开");
    JMenuItem                 close            = new JMenuItem("关闭");
    JMenuItem                 kaiTi            = new JMenuItem("楷体");
    JMenuItem                 songTi           = new JMenuItem("宋体");
    JTextArea                 text             = new JTextArea("我的家乡！");
    Font                      fonter1          = new Font("华文行楷", Font.BOLD, 30);
    Font                      fonter2          = new Font("宋体", Font.BOLD, 30);

    public Note()
    {
        super("记事本");
        Container comtainer = this.getContentPane();
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(menuBar);
        menuBar.add(file);
        file.add(open);
        file.add(close);
        close.addActionListener(this);
        menuBar.add(font);
        font.add(kaiTi);
        font.add(songTi);
        kaiTi.addActionListener(this);
        songTi.addActionListener(this);
        this.setLocationRelativeTo(null);
        comtainer.add(text);
    }

    public static void main(String[] args)
    {
        new Note().setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("楷体"))
        {
            text.setFont(fonter1);
            System.out.println("楷体");
        }
        else if (e.getActionCommand().equals("宋体"))
        {
            text.setFont(fonter2);
        }
        else if (e.getActionCommand().equals("关闭"))
        {
            System.exit(0);
        }
        else if (e.getActionCommand().equals("自动换行"))
        {
            text.setLineWrap(true);
        }
        else if (e.getActionCommand().equals("取消自动换行"))
        {
            text.setLineWrap(false);
        }
    }
}
