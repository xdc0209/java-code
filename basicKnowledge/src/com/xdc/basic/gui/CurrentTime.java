package com.xdc.basic.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * 1.利用所学的Java语言知识，完成一个实现秒表功能的Applet程序。
 * 它的GUI界面如下所示： 利用所学的Java语言知识，完成一个实现
 * 秒表功能的Applet程序。它的GUI界面如下所示
 */
public class CurrentTime extends JFrame implements ActionListener, Refreshable
{
    private static final long serialVersionUID = -8408350066705935156L;

    Date                      date;
    static TextField          time;
    TextField                 copytime;

    CurrentTime()
    {
        super("计时时钟");
        setLayout(new GridLayout(3, 5, 1, 10));
        JButton currentTime = new JButton("CurrentTime");
        /* currentTime.setBackground(Color.GREEN); */
        currentTime.addActionListener(this);
        time = new TextField(5);
        copytime = new TextField(5);

        add(time, BorderLayout.NORTH);
        add(currentTime, BorderLayout.CENTER);
        add(copytime, BorderLayout.SOUTH);
        setVisible(true);
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("deprecation")
    String refreshTime()
    {
        date = new Date();
        String time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        return time;
    }

    public static void main(String[] args)
    {
        CurrentTime curTime = new CurrentTime();
        while (true)
        {
            try
            {
                time.setText(curTime.refreshTime());
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            if (curTime.isCurrent())
            {
                try
                {
                    curTime.refresh();
                }
                catch (RefreshFailedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("CurrentTime"))
        {
            copytime.setText(time.getText());
        }
    }

    @Override
    public boolean isCurrent()
    {
        return true;
    }

    @Override
    public void refresh() throws RefreshFailedException
    {
    }
}
