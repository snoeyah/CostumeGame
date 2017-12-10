import java.awt.*;
import javax.swing.JFrame;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MainFrame extends JFrame {
	
	private IntroPanel 	mIntroPanel;
	private StartPanel	mStartPanel,reStartPanel;
	private MakeupPanel mMakeupPanel;
	private Closet	 	mClosetPanel;
	private EndPanel 	mEndPanel;
	private UserData 	userdata = new UserData();
	public static Sound		mSound;
	private static DBManager db;
	private ArrayList<RankData> dataList = new ArrayList<RankData>();;
	private int tempscore;
	private String tempname;
	
	public static void main(String[] args) {
		new MainFrame(); // 메인프레임 생성
	}
	
	public MainFrame() {
		
		setTitle("COUSTUME GAME"); // 프레임 타이틀 "코스튬 게임"
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x누를 시 프로그램 종료
		setResizable(false); // 화면 크기 고정
		mIntroPanel = new IntroPanel(this,userdata); // 초기화면 객체 생성 
													 // 메인프레임과 유저데이타를 인자로 넣어줌
		getContentPane().add(mIntroPanel); // 프레임에 초기화면 패널 add
		

		File clap = new File("Bgm.wav"); // 음악 파일을 불러와 선언 및 생성
		mSound = new Sound(clap); // 사운드클래스의 객체 생성 
		mSound.play(); // 사운드 클래스의 play메쏘드 호출 
		pack(); // 크기 조정
		setVisible(true); // 화면에 보이도록 함
	}
	
	// 화면 전환 함수
	// 각 패널에서 화면 전환 할 다음 패널의 키워드를 인자로 넘겨줌
	public void Change(String PanelName) {
		// 초기화면 → 시작화면
		if( PanelName == "Start") {
			this.remove(mIntroPanel); // 현재 프레임으로부터 초기화면을 삭제
			mStartPanel = new StartPanel(this,userdata); // 시작화면 생성 
														 // 프레임과 유저데이타를 인자로 넘겨줌
			getContentPane().add(mStartPanel); // 프레임에 시작화면 패널 add
			pack(); // 크기 조정
			setVisible(true); // 화면에 보이도록 함
		}
		// 시작화면 → 메이크업화면
		else if( PanelName == "Makeup") {
			this.remove(mStartPanel); // 현재 프레임으로부터 시작화면을 삭제
			mMakeupPanel = new MakeupPanel(this,userdata); // 메이크업 화면 생성 
														   // 프레임과 유저데이타를 인자로 넘겨줌
			getContentPane().add(mMakeupPanel); // 프레임에 메이크업화면 패널 add
			pack(); // 크기 조정
			setVisible(true); // 화면에 보이도록 함
		}
		// 메이크업화면 → 옷입히기화면
		else if( PanelName == "Closet") {
			this.remove(mMakeupPanel); // 현재 프레임으로부터 메이크업화면을 삭제
			mClosetPanel = new Closet(this,userdata); // 옷입히기화면 생성 
			 										  // 프레임과 유저데이타를 인자로 넘겨줌
			getContentPane().add(mClosetPanel); // 프레임에 옷입히기화면 패널 add
			pack(); // 크기 조정
			setVisible(true); // 화면에 보이도록 함
		}
		// 옷입히기화면 → 종료화면
		else if( PanelName == "Finish") {
			
			// 챌린지 모드인 경우 DB에 사용자 점수 정보를 저장하고 가져옴
			if( userdata.getMode() == 2)
			{
				db = new DBManager(); // 객체 생성
				db.setMember(userdata.getName(), userdata.getTotalScore()); // setMember메쏘드 호출
				db.getAll(); // getAll()메쏘드 호출
				dataList = db.getList(); // getList()메쏘드 호출하여 ArrayList에 저장

				// 점수를 내림차순으로 재정렬
				for( int i=0; i<dataList.size(); i++ ) {
					for( int j=i; j<dataList.size(); j++ )
						if( dataList.get(i).getScore() < dataList.get(j).getScore() ) {
							tempscore = dataList.get(i).getScore();
							tempname = dataList.get(i).getName();
							
							dataList.get(i).setName(dataList.get(j).getName());
							dataList.get(i).setScore(dataList.get(j).getScore());
							
							dataList.get(j).setName(tempname);
							dataList.get(j).setScore(tempscore);
						}
				}
			}
			this.remove(mClosetPanel); // 현재 프레임으로부터 옷입히기화면을 삭제
			mEndPanel = new EndPanel(this,userdata,dataList); // 종료 화면 생성 
			 												  // 프레임과 유저데이타를 인자로 넘겨줌
															  // 랭킹을 매기기 위해 데이타리스트도 인자로 넘겨줌
			getContentPane().add(mEndPanel); // 프레임에 종료화면 패널 add
			pack(); // 크기 조정
			setVisible(true); // 화면에 보이도록 함
		}
		// replay버튼을 누를 시, 종료화면 → 시작화면
		else if( PanelName == "reStart") {
			this.remove(mEndPanel); // 현재 프레임으로부터 종료화면을 삭제
			userdata.reset();
			reStartPanel = new StartPanel(this,userdata); // 재시작 화면 생성 
														  // 프레임과 유저데이타를 인자로 넘겨줌
			getContentPane().add(reStartPanel); // 프레임에 재시작화면 패널 add
			pack(); // 크기 조정
			setVisible(true); // 화면에 보이도록 함
		}
	}
}
