import java.awt.Color;

import javax.swing.JButton;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

//�½��߳��࣬�жϰ�ť��Χ�İ�ť����������������ݻ�
public class GameThread extends Thread{
	JButton btn[][];
	int stateNow[][];//��ŵ�ǰ֡��ť״̬������
	int stateNext[][];//�����һ֡֡��ť״̬������
	int N;
	int M;
	int sum;//���嵱ǰ��ť��Χ����ĸ���
	
	//���췽��
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

	//��ȡ��һ֡ÿ����ť��״̬����
	void transfrom() {
		
		//��ȡ��һ֡ÿ����ť��״̬
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				sum = 0;
				//�ж���Χ��ť�������
				/*if(i == 0 && j != 0 && j+1 != M) {//�ж��Ϸ�
					sum = state[i][j-1] + state[i][j+1] + state[i+1][j-1] + state[i+1][j] + state[i+1][j+1];
				}else if(i == 0 && j == 0) {//�ж�����
					sum = state[i][j+1] + state[i+1][j] + state[i+1][j+1];
				}else if(i == 0&& j+1 == M) {//�ж�����
					sum = state[i][j-1] + state[i+1][j-1] + state[i+1][j];
				}else if(i+1 == N && j != 0 && j+1 != M) {//�ж��·�
					sum = state[i][j-1] + state[i][j+1] + state[i-1][j-1] + state[i-1][j] + state[i-1][j+1];
				}else if(i+1 == N && j == 0) {//�ж�����
					sum =state[i][j+1] + state[i-1][j] + state[i-1][j+1];
				}else if(i+1 == N && j+1 == M) {//�ж�����
					sum = state[i][j-1] + state[i-1][j-1] + state[i-1][j];
				}else if(j == 0 && i != 0 && i+1 != N) {//�ж����
					sum = state[i][j+1] + state[i+1][j] + state[i+1][j+1] + state[i-1][j] + state[i-1][j+1];
				}else if(j+1 == M && i != 0 && i+1 != N) {//�ж��ұ�
					sum = state[i][j-1] + state[i+1][j] + state[i+1][j-1] + state[i-1][j] + state[i-1][j-1];
				}else{
					sum = state[i][j-1] + state[i][j+1] + state[i+1][j-1] + state[i+1][j] + state[i+1][j+1] + state[i-1][j] + state[i-1][j-1] + state[i-1][j+1];
				}*/
				if(i != 0) {//�Ϸ�
					sum += stateNow[i-1][j];
				}
				if(i != 0 && j != 0) {//����
					sum += stateNow[i-1][j-1];
				}
				if(i != 0 && j+1 != M) {//����
					sum += stateNow[i-1][j+1];
				}
				if(i+1 != N) {//�·�
					sum += stateNow[i+1][j];
				}
				if(i+1 != N && j != 0 ) {//����
					sum += stateNow[i+1][j-1];
				}
				if(i+1 != N && j+1 != M) {//����
					sum += stateNow[i+1][j+1];
				}
				if(j != 0) {//���
					sum += stateNow[i][j-1];
				}
				if(j+1 != M) {//�ұ�
					sum += stateNow[i][j+1];
				}
				
				
				/*************������Ϸ����*****************/
				//(1)��ǰϸ��Ϊ����״̬ʱ������Χ��3�����ϸ��ʱ����������ϸ����ɴ��״̬(ģ�ֳⷱ)����ԭ��Ϊ�����򱣳ֲ��䡣
				//(2)��ǰϸ��Ϊ���״̬ʱ������Χ���ھ�ϸ����������(����������)���ʱ����ϸ���������״̬(ģ����������ϡ��)��
				//(3)��ǰϸ��Ϊ���״̬ʱ������Χ��������3�����ϸ��ʱ����ϸ������ԭ����
				//(4)��ǰϸ��Ϊ���״̬ʱ������Χ��3�����ϵĴ��ϸ��ʱ����ϸ���������״̬(ģ��������������)��
				
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
		//ˢ�°�ť״̬
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				stateNow[i][j] = stateNext[i][j];
			}
		}
		
		//ˢ��ҳ��
		for(int i = 0;i < N;i++) {
			for(int j = 0;j < M;j++) {
				if(stateNow[i][j] == 1) {
					btn[i][j].setBackground(Color.green);//���Ϊ������ʾΪ��ɫ
				}else {
					btn[i][j].setBackground(Color.GRAY);//���Ϊ������ʾΪ��ɫ
				}
			}
		}
	}
}
