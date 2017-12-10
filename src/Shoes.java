import java.awt.*;
import javax.swing.*;

// Cloth클래스를 상속받음
public class Shoes extends Cloth {
	
	private int score;
	// Cloth클래스의 생성자 호출
	public Shoes( int s , String n, ImageIcon img ) { 
		super(n,img); score = s;
		}
	// 스코어 set,get함수
	void setScore(int s) { score = s; }
	int getScore() { return score; }
}
