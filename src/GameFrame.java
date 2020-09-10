import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{
	//定义界面组件
	int N = 50;//定义行数
	int M = 100;//定义列数
	JButton btn[][] = new JButton[N][M];//定义N*N个按钮
	int stateNow[][] = new int[N][M];//存放上一帧按钮状态的数组
	int liveNum = 500;//定义初始化生命种子个数
	
	JButton btnStart = new JButton("开始演化");//开始按钮
	JButton btnStop = new JButton("暂停");//暂停按钮
	JButton rdInit = new JButton("随机初始化");//随机初始化生命种子
	JButton btnReset = new JButton("重置");
	
	//定义构造方法
	public GameFrame() {
		setTitle("生命游戏");
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);//设置窗口最大化
		setDefaultCloseOperation(EXIT_ON_CLOSE);//设置点击关闭按钮退出程序
		initFrame();
	}//end 构造方法
	
	//窗口初始化
	void initFrame() {
		//创建监视器对象，给每个按钮设置监听
		ButtonListener listener = new ButtonListener();
		
		//定义JPanel容器，将组件放到容器中
		//1.顶部
		JPanel pTop = new JPanel();
				pTop.add(btnStart);
				pTop.add(btnStop);
				pTop.add(rdInit);
				pTop.add(btnReset);
		this.add(pTop,BorderLayout.NORTH);
		//给start和rdInit按钮设置监听
		btnStart.addActionListener(listener);
		btnStop.addActionListener(listener);
		rdInit.addActionListener(listener);
		btnReset.addActionListener(listener);
		
		//2.中间
		JPanel pCenter = new JPanel(new GridLayout(N, M));
				//pCenter.setLayout(new GridLayout(N, N));//设置布局为N*N的Grid Layout
				
				//将按钮添加到容器中，并将生死状态显示出来
				//给每个按钮设置监听
				for(int i = 0;i < N;i++) {
					for(int j = 0;j < M;j++) {
						btn[i][j] = new JButton();
						pCenter.add(btn[i][j]);//将按钮加入到容器中
						btn[i][j].addActionListener(listener);
						if(stateNow[i][j] == 1) {
							btn[i][j].setBackground(Color.green);//如果为生，显示为绿色
						}else {
							btn[i][j].setBackground(Color.GRAY);//如果为死，显示为灰色
						}
					}
				}//end for
		this.add(pCenter,BorderLayout.CENTER);
		
		/*//创建线程对象并启动
		new GameThread(btn, state, N).start();*/
		
	}//end initFrame
	
	/*****初始化生命种子方法****/
	void initLifeBtn() {
		Random rd = new Random();//定义随机数对象
		
		//初始化种子
		for(int i = 0;i < liveNum;i++) {
			int rdNum = rd.nextInt(N*M);//存放随机数
			int R = rdNum / M;//定位种子的行数
			int C = rdNum % M;//定位种子的列数
			rdNum = rd.nextInt(N*M);//得到(0,N*N]的随机数
			if(stateNow[R][C] == 0) {//判断当前这个按钮状态是否被设置，保证有liveNum个种子数
				stateNow[R][C] = 1;//将当前按钮状态设置为生，1 -- 生，0 -- 死
			}else {
				i--;
			}
		}
		
		//将随机产生的生命种子显示出来
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				if(stateNow[i][j] == 1) {
					btn[i][j].setBackground(Color.green);//如果为生，显示为绿色
				}else {
					btn[i][j].setBackground(Color.GRAY);//如果为死，显示为灰色
				}
			}
		}//end for
		
	}//end initLifeBtn
	
	/****重置页面的方法****/
	void reSetBtn() {
		
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				btn[i][j].setBackground(Color.GRAY);
				stateNow[i][j] = 0;
			}
		}
	}//end reSetBtn
	
	/*********按钮监听类**********/
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();	//获取被点击按钮的事件源
			if(obj == btnStart) {	//判断开始按钮被点击
				
				//创建线程对象并启动
				new GameThread(btn, stateNow, N, M).start();
				btnStart.setEnabled(false);//设置按钮不可点击
			}else if(obj == btnStop) {//判断暂停按钮被点击
				if(Global.CONTROL) {
					Global.CONTROL = false;
					btnStop.setText("开始");
				}else {
					Global.CONTROL = true;
					btnStop.setText("暂停");
				}
			}else if(obj == rdInit) {	//判断随机初始化按钮被点击
				
				initLifeBtn();	//调用随机初始化种子方法
				
			}else if(obj == btnReset) {	//判断重置按钮被点击
				
				reSetBtn();	//调用重置方法
				
			}else {
				for(int i = 0;i < N;i++) {
					for(int j = 0;j < M;j++) {
						if(obj == btn[i][j]) {
							btn[i][j].setBackground(Color.green);
							stateNow[i][j] = 1;
						}
					}
				}//end for
			}
			
		}//end actionPerformed
		
	}//end ButtonListener

	public static void main(String[] args) {
		new GameFrame().setVisible(true);
	}
}
