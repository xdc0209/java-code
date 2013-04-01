package com.xdc.basic.java4android.thread.extendsthead;

class MyThread extends Thread{
	public void run(){
		for(int i = 0; i < 100;i++){
			System.out.println("MyThread-->" + i);
		}
	}			
}
