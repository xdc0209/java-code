package download;

/**
 * @author xiaopeng1
 * @version 1.0
 */
import java.awt.*;
import javax.swing.*;

public class TestJFrame extends JFrame {
	TestJFrame() {
		super("Swing应用程序");
		setSize(150, 250);
	}

	private void start() {
		new moveJFrame(new TestJFrame()).start();
	}

	class moveJFrame extends Thread {
		TestJFrame tjf;

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		/*
		 * Dimension 类封装单个对象中组件的宽度和高度（精确到整数）。 getScreenSize()获取屏幕的大小。
		 */

		int widthX = 0;

		int heightY = (int) (dim.getHeight() / 2) - 75;

		public moveJFrame(TestJFrame tjf) {
			this.tjf = tjf;
		}

		public void run() {
			while (true) {
				if (widthX >= (int) (dim.getWidth())) {
					widthX = 0;
				}
				widthX += 5;
				tjf.setLocation(widthX, heightY);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tjf.setVisible(true);
				tjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				System.out.println("当前窗口位置：" + widthX + "*" + heightY);
			}
		}
	}

	public static void main(String[] args) {
		TestJFrame object = new TestJFrame();
		object.start();
		// System.out.println("当前窗口位置："+X()+"*"+Y());
	}
}
