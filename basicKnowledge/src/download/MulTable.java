package download;
//编写Java Application程序：编写打印“九九乘法口诀表”。
public class MulTable {
	public static void main(String[] args) {
		int i,j;
		for(i=1;i<=9;i++){
			for(j=1;j<=i;j++){
				System.out.print("  "+j+"*"+i+"="+i*j);
				if(i==j){
					System.out.println();
				}
			}
		}
	}
}
