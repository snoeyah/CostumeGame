import java.awt.*;
import javax.swing.*;

public class UserData {
	
	private String 	name;
	private int 	mode;
	private Shoes 	UserShoes;
	private Top 	UserTop;
	private Bottom	UserBottom;
	private int		eyebrow,shadow,chick,lips;
	private int		totalScore;
	private int		volumeFlag=1;
	private int		concept;
	
	public UserData() {
		UserShoes = null;
		UserTop = null;
		UserBottom = null;
		totalScore = 0;
		mode = 0;
	}
	
	// set 함수
	public void setName( String n ) { name = n; }
	public void setMode( int m ) { mode = m; }
	public void setUserShoes( Shoes userShoes ) { 
		UserShoes = new Shoes(userShoes.getScore(), userShoes.getName(), userShoes.getImage());
	}
	public void setUserTop( Top userTop ) { 
		UserTop = new Top(userTop.getScore(), userTop.getName(), userTop.getImage());
	}
	public void setUserBottom( Bottom userBottom ) { 
		UserBottom = new Bottom(userBottom.getScore(), userBottom.getName(), userBottom.getImage());
	}
	public void setMakeup(int b,int s,int c,int l) {
		eyebrow = b;
		shadow = s;
		chick = c;
		lips = l;
	}
	public void setVolume( int v ) 		{ volumeFlag = v; }
	public void setConcept( int c ) 	{ concept = c;    }
	public void setScore()				{ totalScore = UserTop.getScore()+UserShoes.getScore()+UserBottom.getScore(); }
	
	// get함수
	public int getConcept()		{ return concept; }
	public String getName() 	{ return name; }
	public int getMode() 		{ return mode; }
	public Shoes getShoe() 		{ return UserShoes; }
	public Top getTop() 		{ return UserTop; }
	public Bottom getBtm()	 	{ return UserBottom; }
	public int getEyebrow()		{ return eyebrow; }
	public int getShadow()		{ return shadow; }
	public int getChick()		{ return chick; }
	public int getLips()		{ return lips; }
	public int getTotalScore()	{ return totalScore; }
	public int getVolume()		{ return volumeFlag; }
	
	// reset 함수 : 주요 유저 데이타 초기화
	public void reset() {
	      UserShoes = null;
	      UserTop = null;
	      UserBottom = null;
	      totalScore = 0;
	      mode = 0;
	}
}
