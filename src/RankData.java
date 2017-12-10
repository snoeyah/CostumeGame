//사용자의 이름과 스코어를 저장할 량크데이타 클래스
public class RankData {

	private String 	username;
	private int		score;
	
	// set함수
	public void setName(String s) { username = s; }
	public void setScore(int scr) { score = scr;  }
	// get함수
	public String getName()	{ return username; }
	public int getScore()	{ return score;	   }	
}
