package download;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		String s = "abcd, 		w2jfew, 1jkfew,    3reere";
		Scanner sc = new Scanner(s);
		String answer = "";
		while ((answer = sc.findInLine("\\b\\d[a-z]*\\s")) != null) {
			// 试图在忽略分隔符的情况下查找下一个从指定字符串构造的模式。
			// System.out.println(answer);
		}
		sc = new Scanner(s);
		String ss = sc.findWithinHorizon("[a-z]*", 10);
		// 试图在忽略分隔符的情况下查找下一个从指定字符串构造的模式。
		System.out.println(ss);

		sc = new Scanner(s);
		sc.useDelimiter("[,]\\s*");
		// 将此扫描器的分隔模式设置为从指定 String 构造的模式。
		while (sc.hasNext()) {
			System.out.println(sc.next());
		}
	}

}
