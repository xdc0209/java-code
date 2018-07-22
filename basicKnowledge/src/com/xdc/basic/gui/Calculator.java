package com.xdc.basic.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * 设计一个简单计算器，
 * 在“操作数”标签右侧的两个文本框输入操作数，
 * 当单击操作符＋，－，×，÷按钮时，
 * 对两个操作数进行运算并将结果填入到“结果”标签右侧的文本框中
 */
public class Calculator extends JFrame implements ActionListener
{
    private static final long serialVersionUID = -1717806291593500805L;

    JLabel                    l1, l2, l3;
    JTextField                t1, t2, t3;
    JButton                   b1, b2, b3, b4;

    public Calculator()
    {
        setTitle("简单计算器");
        l1 = new JLabel("操作数");
        l2 = new JLabel("操作数");
        l3 = new JLabel("结  果");
        t1 = new JTextField(10);
        t2 = new JTextField(10);
        t3 = new JTextField(10);
        // 将窗口物体放入窗口
        GridBagLayout layout = new GridBagLayout();
        getContentPane().setLayout(layout);
        addComponent(layout, l1, 1, 0, 1, 1);
        addComponent(layout, l2, 2, 0, 1, 1);
        addComponent(layout, l3, 3, 0, 1, 1);
        addComponent(layout, t1, 1, 1, 1, 1);
        addComponent(layout, t2, 2, 1, 1, 1);
        addComponent(layout, t3, 3, 1, 1, 1);

        b1 = new JButton("+");
        b2 = new JButton("-");
        b3 = new JButton("*");
        b4 = new JButton("/");
        addComponent(layout, b1, 1, 2, 1, 1);
        addComponent(layout, b2, 1, 3, 1, 1);
        addComponent(layout, b3, 2, 2, 1, 1);
        addComponent(layout, b4, 2, 3, 1, 1);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        this.pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent j)
    {
        double n;
        try
        {
            if (j.getSource() == b1)
            {
                double n1, n2;
                n1 = Double.parseDouble(t1.getText());
                n2 = Double.parseDouble(t2.getText());
                n = n1 + n2;
                t3.setText(String.valueOf(n));
            }
            if (j.getSource() == b2)
            {
                double n1, n2;
                n1 = Double.parseDouble(t1.getText());
                n2 = Double.parseDouble(t2.getText());
                n = n1 - n2;
                t3.setText(String.valueOf(n));
            }
            if (j.getSource() == b3)
            {
                double n1, n2;
                n1 = Double.parseDouble(t1.getText());
                n2 = Double.parseDouble(t2.getText());
                n = n1 * n2;
                t3.setText(String.valueOf(n));
            }
            if (j.getSource() == b4)
            {
                double n1, n2;
                n1 = Double.parseDouble(t1.getText());
                n2 = Double.parseDouble(t2.getText());
                n = n1 / n2;
                t3.setText(String.valueOf(n));
            }
        }
        catch (Exception e)
        {
        }
    }

    // 快捷使用GridBagLayout的方法
    private void addComponent(GridBagLayout layout, Component component, int row, int col, int width, int height)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 2, 10, 2);
        constraints.weightx = 100;
        constraints.weighty = 100;
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        layout.setConstraints(component, constraints);
        if (component instanceof JButton)
        {
            ((JButton) component).addActionListener(this);
        }
        getContentPane().add(component);
    }

    // 主方法初始化并显示窗口
    public static void main(String[] args)
    {
        Calculator calc = new Calculator();
        calc.setVisible(true);
    }
}
