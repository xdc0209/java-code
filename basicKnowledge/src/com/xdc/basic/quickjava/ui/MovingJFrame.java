package com.xdc.basic.quickjava.ui;

/**
 * @author xiaopeng1
 * @version 1.0
 */
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MovingJFrame extends JFrame
{
    private static final long serialVersionUID = 1363625893429832291L;

    MovingJFrame()
    {
        super("Swing应用程序");
        setSize(150, 250);
    }

    private void start()
    {
        new moveJFrame(new MovingJFrame()).start();
    }

    class moveJFrame extends Thread
    {
        MovingJFrame tjf;
        Dimension    dim     = Toolkit.getDefaultToolkit().getScreenSize();
        /*
         * Dimension 类封装单个对象中组件的宽度和高度（精确到整数）。 getScreenSize()获取屏幕的大小。
         */
        int          widthX  = 0;
        int          heightY = (int) (dim.getHeight() / 2) - 75;

        public moveJFrame(MovingJFrame tjf)
        {
            this.tjf = tjf;
        }

        public void run()
        {
            while (true)
            {
                if (widthX >= (int) (dim.getWidth()))
                {
                    widthX = 0;
                }
                widthX += 5;
                tjf.setLocation(widthX, heightY);
                try
                {
                    Thread.sleep(20);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                tjf.setVisible(true);
                tjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                System.out.println("当前窗口位置：" + widthX + "*" + heightY);
            }
        }
    }

    public static void main(String[] args)
    {
        MovingJFrame object = new MovingJFrame();
        object.start();
        // System.out.println("当前窗口位置："+X()+"*"+Y());
    }
}
