import java.awt.Color;

import javax.swing.JButton;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

//新建线程类，判断按钮周围的按钮的生存情况并进行演化
public class GameThread extends Thread{
	JButton btn[][];
	int stateNow[][];//存放当前帧按钮状态的数组
	int stateNext[][];//存放下一帧帧按钮状态的数组
	int N;
	int M;
	int sum;//定义当前按钮周围生存的个数
	
	//构造方法
	public GameThread(JButton btn[][],int stateNow[][],int N,int M) {
		this.btn = btn;
		this.stateNow = stateNow;
		this.N = N;
		this.M = M;
		this.stateNext = new int[N][M];
	}
	
	@Override
	public void run() {
		super.run();
		while(true) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(Global.CONTROL) {
				transfrom();
				refresh();
			}
		}//end while
	}//end run

	//获取下一帧每个按钮的状态方法
	void transfrom() {
		
		//获取下一帧每个按钮的状态
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				sum = 0;
				//判断周围按钮生存个数
				/*if(i == 0 && j != 0 && j+1 != M) {//判断上方
					sum = state[i][j-1] + state[i][j+1] + state[i+1][j-1] + state[i+1][j] + state[i+1][j+1];
				}else if(i == 0 && j == 0) {//判断左上
					sum = state[i][j+1] + state[i+1][j] + state[i+1][j+1];
				}else if(i == 0&& j+1 == M) {//判断右上
					sum = state[i][j-1] + state[i+1][j-1] + state[i+1][j];
				}else if(i+1 == N && j != 0 && j+1 != M) {//判断下方
					sum = state[i][j-1] + state[i][j+1] + state[i-1][j-1] + state[i-1][j] + state[i-1][j+1];
				}else if(i+1 == N && j == 0) {//判断左下
					sum =state[i][j+1] + state[i-1][j] + state[i-1][j+1];
				}else if(i+1 == N && j+1 == M) {//判断右下
					sum = state[i][j-1] + state[i-1][j-1] + state[i-1][j];
				}else if(j == 0 && i != 0 && i+1 != N) {//判断左边
					sum = state[i][j+1] + state[i+1][j] + state[i+1][j+1] + state[i-1][j] + state[i-1][j+1];
				}else if(j+1 == M && i != 0 && i+1 != N) {//判断右边
					sum = state[i][j-1] + state[i+1][j] + state[i+1][j-1] + state[i-1][j] + state[i-1][j-1];
				}else{
					sum = state[i][j-1] + state[i][j+1] + state[i+1][j-1] + state[i+1][j] + state[i+1][j+1] + state[i-1][j] + state[i-1][j-1] + state[i-1][j+1];
				}*/
				if(i != 0) {//上方
					sum += stateNow[i-1][j];
				}
				if(i != 0 && j != 0) {//左上
					sum += stateNow[i-1][j-1];
				}
				if(i != 0 && j+1 != M) {//右上
					sum += stateNow[i-1][j+1];
				}
				if(i+1 != N) {//下方
					sum += stateNow[i+1][j];
				}
				if(i+1 != N && j != 0 ) {//左下
					sum += stateNow[i+1][j-1];
				}
				if(i+1 != N && j+1 != M) {//右下
					sum += stateNow[i+1][j+1];
				}
				if(j != 0) {//左边
					sum += stateNow[i][j-1];
				}
				if(j+1 != M) {//右边
					sum += stateNow[i][j+1];
				}
				
				
				/*************生命游戏定律*****************/
				//(1)当前细胞为死亡状态时，当周围有3个存活细胞时，则迭代后该细胞变成存活状态(模拟繁殖)；若原先为生，则保持不变。
				//(2)当前细胞为存活状态时，当周围的邻居细胞低于两个(不包含两个)存活时，该细胞变成死亡状态(模拟生命数量稀少)。
				//(3)当前细胞为存活状态时，当周围有两个或3个存活细胞时，该细胞保持原样。
				//(4)当前细胞为存活状态时，当周围有3个以上的存活细胞时，该细胞变成死亡状态(模拟生命数量过多)。
				
				if(sum == 3) {
					stateNext[i][j] = 1;
				}else if(sum == 2) {
					stateNext[i][j] = stateNow[i][j];
				}else {
					stateNext[i][j] = 0;
				}
					
			}
		}//end for
	}//end tansform
	
	void refresh() {
		//刷新按钮状态
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				stateNow[i][j] = stateNext[i][j];
			}
		}
		
		//刷新页面
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				if(stateNow[i][j] == 1) {
					btn[i][j].setBackground(Color.green);//如果为生，显示为绿色
				}else {
					btn[i][j].setBackground(Color.GRAY);//如果为死，显示为灰色
				}
			}
		}
	}
}
