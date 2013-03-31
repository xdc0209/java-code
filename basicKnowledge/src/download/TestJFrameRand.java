package download;

import java.awt.*;
import javax.swing.*;

public class TestJFrameRand extends JFrame {
	TestJFrameRand() {
		this.setTitle("简单的Java Swing应用程序");
		this.setSize(150, 150);
	}

	private void start() {
		new moveJFrame(new TestJFrameRand()).start();
	}

	class moveJFrame extends Thread {
		TestJFrameRand tjf;

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		int widthX = 0;

		int heightY = (int) (dim.getHeight() / 2) - 75;

		public moveJFrame(TestJFrameRand tjf) {
			this.tjf = tjf;
		}

		public void run() {
			while (true) {
				widthX = (int) (Math.random() * 1000);
				if (widthX >= (int) (dim.getWidth()) || (widthX <= 0)) {
					continue;
				}
				widthX += 10;
				tjf.setLocation(widthX, heightY);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tjf.setVisible(true);
				tjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				System.out.println(widthX);

			}
		}
	}

	public static void main(String[] args) {
		TestJFrameRand object = new TestJFrameRand();
		object.start();
		// System.out.println("当前窗口位置："+X()+"*"+Y());
	}
}
