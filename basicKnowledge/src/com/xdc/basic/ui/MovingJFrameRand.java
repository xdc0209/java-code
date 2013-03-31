package com.xdc.basic.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MovingJFrameRand extends JFrame
{
	MovingJFrameRand()
	{
		this.setTitle("简单的Java Swing应用程序");
		this.setSize(150, 150);
	}

	private void start()
	{
		new moveJFrame(new MovingJFrameRand()).start();
	}

	class moveJFrame extends Thread
	{

		MovingJFrameRand	tjf;

		Dimension		dim		= Toolkit.getDefaultToolkit().getScreenSize();

		int		       widthX	= 0;

		int		       heightY	= (int) (dim.getHeight() / 2) - 75;

		public moveJFrame(MovingJFrameRand tjf)
		{
			this.tjf = tjf;
		}

		public void run()
		{
			while (true)
			{
				widthX = (int) (Math.random() * 1000);
				if (widthX >= (int) (dim.getWidth()) || (widthX <= 0))
				{
					continue;
				}
				widthX += 10;
				tjf.setLocation(widthX, heightY);
				try
				{
					Thread.sleep(300);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				tjf.setVisible(true);
				tjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				System.out.println(widthX);

			}
		}
	}

	public static void main(String[] args)
	{
		MovingJFrameRand object = new MovingJFrameRand();
		object.start();
		// System.out.println("当前窗口位置："+X()+"*"+Y());
	}
}
