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
	//����������
	int N = 50;//��������
	int M = 100;//��������
	JButton btn[][] = new JButton[N][M];//����N*N����ť
	int stateNow[][] = new int[N][M];//�����һ֡��ť״̬������
	int liveNum = 500;//�����ʼ���������Ӹ���
	
	JButton btnStart = new JButton("��ʼ�ݻ�");//��ʼ��ť
	JButton btnStop = new JButton("��ͣ");//��ͣ��ť
	JButton rdInit = new JButton("�����ʼ��");//�����ʼ����������
	JButton btnReset = new JButton("����");
	
	//���幹�췽��
	public GameFrame() {
		setTitle("������Ϸ");
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);//���ô������
		setDefaultCloseOperation(EXIT_ON_CLOSE);//���õ���رհ�ť�˳�����
		initFrame();
	}//end ���췽��
	
	//���ڳ�ʼ��
	void initFrame() {
		//�������������󣬸�ÿ����ť���ü���
		ButtonListener listener = new ButtonListener();
		
		//����JPanel������������ŵ�������
		//1.����
		JPanel pTop = new JPanel();
				pTop.add(btnStart);
				pTop.add(btnStop);
				pTop.add(rdInit);
				pTop.add(btnReset);
		this.add(pTop,BorderLayout.NORTH);
		//��start��rdInit��ť���ü���
		btnStart.addActionListener(listener);
		btnStop.addActionListener(listener);
		rdInit.addActionListener(listener);
		btnReset.addActionListener(listener);
		
		//2.�м�
		JPanel pCenter = new JPanel(new GridLayout(N, M));
				//pCenter.setLayout(new GridLayout(N, N));//���ò���ΪN*N��Grid Layout
				
				//����ť��ӵ������У���������״̬��ʾ����
				//��ÿ����ť���ü���
				for(int i = 0;i < N;i++) {
					for(int j = 0;j < M;j++) {
						btn[i][j] = new JButton();
						pCenter.add(btn[i][j]);//����ť���뵽������
						btn[i][j].addActionListener(listener);
						if(stateNow[i][j] == 1) {
							btn[i][j].setBackground(Color.green);//���Ϊ������ʾΪ��ɫ
						}else {
							btn[i][j].setBackground(Color.GRAY);//���Ϊ������ʾΪ��ɫ
						}
					}
				}//end for
		this.add(pCenter,BorderLayout.CENTER);
		
		/*//�����̶߳�������
		new GameThread(btn, state, N).start();*/
		
	}//end initFrame
	
	/*****��ʼ���������ӷ���****/
	void initLifeBtn() {
		Random rd = new Random();//�������������
		
		//��ʼ������
		for(int i = 0;i < liveNum;i++) {
			int rdNum = rd.nextInt(N*M);//��������
			int R = rdNum / M;//��λ���ӵ�����
			int C = rdNum % M;//��λ���ӵ�����
			rdNum = rd.nextInt(N*M);//�õ�(0,N*N]�������
			if(stateNow[R][C] == 0) {//�жϵ�ǰ�����ť״̬�Ƿ����ã���֤��liveNum��������
				stateNow[R][C] = 1;//����ǰ��ť״̬����Ϊ����1 -- ����0 -- ��
			}else {
				i--;
			}
		}
		
		//���������������������ʾ����
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				if(stateNow[i][j] == 1) {
					btn[i][j].setBackground(Color.green);//���Ϊ������ʾΪ��ɫ
				}else {
					btn[i][j].setBackground(Color.GRAY);//���Ϊ������ʾΪ��ɫ
				}
			}
		}//end for
		
	}//end initLifeBtn
	
	/****����ҳ��ķ���****/
	void reSetBtn() {
		
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				btn[i][j].setBackground(Color.GRAY);
				stateNow[i][j] = 0;
			}
		}
	}//end reSetBtn
	
	/*********��ť������**********/
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();	//��ȡ�������ť���¼�Դ
			if(obj == btnStart) {	//�жϿ�ʼ��ť�����
				
				//�����̶߳�������
				new GameThread(btn, stateNow, N, M).start();
				btnStart.setEnabled(false);//���ð�ť���ɵ��
			}else if(obj == btnStop) {//�ж���ͣ��ť�����
				if(Global.CONTROL) {
					Global.CONTROL = false;
					btnStop.setText("��ʼ");
				}else {
					Global.CONTROL = true;
					btnStop.setText("��ͣ");
				}
			}else if(obj == rdInit) {	//�ж������ʼ����ť�����
				
				initLifeBtn();	//���������ʼ�����ӷ���
				
			}else if(obj == btnReset) {	//�ж����ð�ť�����
				
				reSetBtn();	//�������÷���
				
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
