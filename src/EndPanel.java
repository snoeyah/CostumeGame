import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class EndPanel extends JPanel {
	
	private int			nRandom;
	private int 		totalScore;
	private String[] 	evalGood = {"우와 멋지다~","우와 정말 예쁘다~","우와 진짜 핵간지~","혹시 남자친구 있으세요?","언니 옷 너무 이쁘게 입었다~","PERFECT STYLE"};
	private String[] 	evalBad = {"윽 진짜 못생겼다!","으 안구테러~","윽 눈갱당해버렸어~","너 옷 다시 입고 와라..","안본 눈 사요~","언니 스타일이 영 아니다~"};
	private ImageIcon 	mIcon,volume;
	private ImageIcon 	mAvatar;
	private JLabel 		mTop,mBottom,mShoes;
	private JLabel		mBrow,mShadow,mChick,mLips;
	private JLabel 		aLabel,imgLabel,evalLabel,rankTitle,rankLabel[];
	private JButton 	btnRst,btnFsh,btnRank,soundOff,btnPrev;
	
	private JPanel 		leftPanel, rightPanel,rankPanel;
	
	public EndPanel(MainFrame frame, UserData User, ArrayList<RankData> datalist) {
		
		// 프레임의 크기 : 1200x900, 배경생상 : 흰색, 레이아웃매니저 : disabled
		setPreferredSize(new Dimension(1200,900));
		setBackground(Color.white);
		setLayout(null);
		
		// 음악 재생여부에 따른 이미지 아이콘 생성
		if( User.getVolume() == 1 )
			volume = new ImageIcon("Images/volume_on.png");
		else
			volume = new ImageIcon("Images/volume_off.png");
		
		// 재생 및 정지 버튼 생성 
		// 위치 : x=1100 y=50, 크기 : 50x50
		// 테두리 없음 , 버튼 내부 채우기없음, 선택시 테두리 없음
		// 클릭 시 이벤트 처리 : 재생 중 정지 버튼을 누를 경우 프레임의 사운드객체의 stop메소드 호출
		//				  버튼의 이미지 아이콘 재 설정, 유저 데이타의 사운드플래그 재설정
		//				  반대의 경우 play메소드 호출 및 이미지 아아콘과 사운드 플래그 재설정
		// 현재패널에 add
		soundOff = new JButton(volume);
		soundOff.setBounds(1100,50,50,50);
		soundOff.setBorderPainted(false);
		soundOff.setContentAreaFilled(false);
		soundOff.setFocusPainted(false);
		soundOff.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {}
			public void mouseClicked(MouseEvent e) {
				if( User.getVolume() == 1) {
					frame.mSound.stop();
					volume = new ImageIcon("Images/volume_off.png");
					User.setVolume(0);
					}
				else if( User.getVolume() == 0) {
					frame.mSound.play();
					volume = new ImageIcon("Images/volume_on.png");
					User.setVolume(1);
					}
				soundOff.setIcon(volume);
				}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		add(soundOff);
		
		// 왼쪽 패널 : leftPanel 객체 생성 , 위치 : x=0 y=0, 크기 : 400x900
		// 배경색상 : 흰색, 레이아웃 매니저 : disabled
		// 현재 패널에 add
		leftPanel = new JPanel();
		leftPanel.setBounds(0,0,400,900);
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setLayout(null);
		add(leftPanel);
	
		// 랭크 패널 : rankPanel 객체 생성 , 위치 : x=600 y=100, 크기 : 400x700
		// 배경색상 : 분홍, 레이아웃 매니저 : disabled
		// 화면에 보이지 않도록 함
		// 현재 패널에 add
		rankPanel = new JPanel();
		rankPanel.setBounds(600, 100, 400, 700);
		rankPanel.setBackground(Color.PINK);
		rankPanel.setLayout(null);
		rankPanel.setVisible(false);
		add(rankPanel);
		
		// 오른쪽 패널 : rightPanel 객체 생성 , 위치 : x=400 y=0, 크기 : 800x900
		// 배경색상 : 흰색, 레이아웃 매니저 : disabled
		// 현재 패널에 add
		rightPanel = new JPanel();
		rightPanel.setBounds(400, 0, 800, 900);
		rightPanel.setBackground(Color.white);
		rightPanel.setLayout(null);
		add(rightPanel);

		// 옷
		// 하의라벨 생성 : User에 저장된 하의 이미지 아이콘 가져옴
		// 위치 : x=50, y=50, 크기 : 400x800
		// leftPanel에 add
		mBottom = new JLabel(User.getBtm().getImage());
		mBottom.setBounds(50, 50, 400, 800);
		leftPanel.add(mBottom);
		
		// 상의라벨 생성 : User에 저장된 상의 이미지 아이콘 가져옴
		// 위치 : x=50, y=50, 크기 : 400x800
		// leftPanel에 add
		mTop = new JLabel(User.getTop().getImage());
		mTop.setBounds(50, 50, 400, 800);
		leftPanel.add(mTop);
		
		// 신발라벨 생성 : User에 저장된 신발 이미지 아이콘 가져옴
		// 위치 : x=50, y=50, 크기 : 400x850
		// leftPanel에 add
		mShoes = new JLabel(User.getShoe().getImage());
		mShoes.setBounds(50, 50, 400, 850);
		leftPanel.add(mShoes);
		
		// 얼굴 
		// 눈썹라벨 생성 : User에 저장된 눈썹 번호를 이용해 파일경로로 이미지를 불러옴
		// 위치 : x=50, y=50, 크기 : 400x130
		// leftPanel에 add
		mBrow = new JLabel(new ImageIcon("Images/endMU/eyebrow" + User.getEyebrow() + ".png"));
		mBrow.setBounds(50, 50, 400, 130);
		leftPanel.add(mBrow);
		
		// 셰도우라벨 생성 : User에 저장된 셰도우 번호를 이용해 파일경로로 이미지를 불러옴
		// 위치 : x=50, y=50, 크기 : 400x130
		// leftPanel에 add
		mShadow = new JLabel(new ImageIcon("Images/endMU/shadow" + User.getShadow() + ".png"));
		mShadow.setBounds(50, 50, 400, 130);
		leftPanel.add(mShadow);
		
		// 볼터치라벨 생성 : User에 저장된 볼터치 번호를 이용해 파일경로로 이미지를 불러옴
		// 위치 : x=50, y=50, 크기 : 400x130
		// leftPanel에 add
		mChick = new JLabel(new ImageIcon("Images/endMU/chick" + User.getChick() + ".png"));
		mChick.setBounds(50, 50, 400, 130);
		leftPanel.add(mChick);
		
		// 립라벨 생성 : User에 저장된 립 번호를 이용해 파일경로로 이미지를 불러옴
		// 위치 : x=50, y=50, 크기 : 400x130
		// leftPanel에 add
		mLips = new JLabel(new ImageIcon("Images/endMU/lip" + User.getLips() + ".png"));
		mLips.setBounds(50, 50, 400, 130);
		leftPanel.add(mLips);
		
		// 아바타 : 이미지를 아바타 위에 입히기 위해 아바타를 밑에서 생성
		// 아바타 라벨 생성(이미지아이콘), 위치 : x=50, y=50, 크기 : 400x800
		// leftPanel에 add
		mAvatar = new ImageIcon("images/Avatar.png");
		aLabel = new JLabel(mAvatar);
		aLabel.setBounds(50, 50, 400, 800);
		leftPanel.add(aLabel);

		// 말풍선 : 말풍선라벨 생성(이미지아이콘)
		// 위치 : x=150, y=50, 크기 : 500x400
		// rightPanel에 add
		mIcon = new ImageIcon("images/eval2.png");
		imgLabel = new JLabel(mIcon);
		imgLabel.setBounds(150, 50, 500, 400);
		rightPanel.add(imgLabel);
		
		// 0~5 랜덤숫자 생성
		// 사용자 점수에 따른 라벨을 생성하는 함수 호출
		// 위치 : x=0, y=0, 크기 : 500x400
		// 수평으로 중심, 수직으로 중심, 폰트 설정
		// 말풍선인 imgLabel에 add
		nRandom = (int)(Math.random()*6);
		setTotalScore(User);
		evalLabel.setBounds(0,0,500,400);
		evalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		evalLabel.setVerticalAlignment(SwingConstants.CENTER);
		evalLabel.setFont(new Font("1훈하얀고양이 Regular",Font.BOLD,30));
		imgLabel.add(evalLabel);
		
		// btnPrev : 랭크 패널에서 이전으로 돌아가는 버튼
		// 위치 : x=3500 y=5, 크기 : 40x40
		// 테두리 없음 , 버튼 내부 채우기없음, 선택시 테두리 없음
		// 클릭시 이벤트 처리 : 랭크 패널이 보이지 않도록 함, rightPanel이 보이도록 함
		// rankPanel에 add
		btnPrev = new JButton(new ImageIcon("images/prev.png"));
		btnPrev.setBounds(350, 5, 40, 40);
		btnPrev.setBorderPainted(false);
		btnPrev.setContentAreaFilled(false);
		btnPrev.setFocusPainted(false);
		btnPrev.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {}
			public void mouseClicked(MouseEvent e) {
				rankPanel.setVisible(false);
				rightPanel.setVisible(true);
			}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		rankPanel.add(btnPrev);
		
		// rankTitle 라벨 생성(이미지아이콘)
		// 위치 : x=0 y=50, 크기 : 400x100
		// rankPanel에 add
		rankTitle = new JLabel(new ImageIcon("images/rankimg.png"));
		rankTitle.setBounds(0, 50, 400, 100);
		rankPanel.add(rankTitle);
		
		// rankLabel : 최대 10개의 랭킹 라벨
		// 데이타 리스트의 크기보다 작으면 랭킹을 표시, 그렇지 않으면 라벨에 아무것도 표시하지 않음
		// 위치 : x=0 y=한줄씩 밑으로, 크기 : 400x50
		// 폰트 설정 , 폰트 컬러 : 흰색 / 수직으로 중심, 수평으로 중심
		// rankPanel에 라벨 모두 add
		rankLabel = new JLabel[10];
		for( int i=0; i<10; i++ ) {
			if( i < datalist.size())
				rankLabel[i] = new JLabel("RANK "+(i+1)+" : "+datalist.get(i).getName() + " 	" + datalist.get(i).getScore());
			else
				rankLabel[i] = new JLabel();
			rankLabel[i].setBounds(0, 175+i*50, 400, 50);
			rankLabel[i].setFont(new Font("DX로고B",Font.BOLD,20));
			rankLabel[i].setForeground(Color.WHITE);
			rankLabel[i].setVerticalAlignment(SwingConstants.CENTER);
			rankLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			rankPanel.add(rankLabel[i]);
		}
		
		// replay button : 다시시작 버튼
		// 폰트 설정 : 흰색 , 위치 : x=300 y=450, 크기 : 200x50 
		// 테두리 없엄, 클릭시 태두리 없음 , 배경 분홍색
		// 마우스 리스너 이벤트처리 : 마우스가 버튼위에 올라가면 폰트 크게, 나가면 다시 작게, 클릭시 프레임의 화면전환 메소드 호출
		// rightPanel에 add
		btnRst = new JButton("REPLAY");
		btnRst.setFont(new Font("1훈하얀고양이 Regular",Font.BOLD,25));
		btnRst.setBounds(300,450,200,50);
		btnRst.setForeground(Color.WHITE);
		btnRst.setBorderPainted(false);
		btnRst.setFocusPainted(false);
		btnRst.setBackground(Color.pink);
		btnRst.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {
				btnRst.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
			}
			public void mouseClicked(MouseEvent e) {
				frame.Change("reStart");
			}
			public void mouseExited(MouseEvent e) {
				btnRst.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 25));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		rightPanel.add(btnRst);
		
		// exit button : 종료버튼
		// 폰트 설정 : 흰색 , 위치 : x=300 y=550, 크기 : 200x50 
		// 테두리 없엄, 클릭시 태두리 없음 , 배경 분홍색
		// 마우스 리스너 이벤트 처리 : 마우스가 버튼위에 올라가면 폰트 크게, 나가면 다시 작게, 클릭시 프로그램 종료
		// rightPanel에 add
		btnFsh = new JButton("EXIT");
		btnFsh.setFont(new Font("1훈하얀고양이 Regular",Font.BOLD,25));
		btnFsh.setBounds(300,550,200,50);
		btnFsh.setForeground(Color.WHITE);
		btnFsh.setBorderPainted(false);
		btnFsh.setFocusPainted(false);
		btnFsh.setBackground(Color.pink);
		btnFsh.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {
				btnFsh.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
			}
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			public void mouseExited(MouseEvent e) {
				btnFsh.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 25));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		rightPanel.add(btnFsh);	
		
		// Rank button : 랭킹보기 버튼
		// 폰트 설정 : 흰색 , 위치 : x=300 y=650, 크기 : 200x50 
		// 테두리 없엄, 클릭시 태두리 없음 , 배경 분홍색
		// 마우스 리스너 이벤트 처리 : 마우스가 버튼위에 올라가면 폰트 크게, 나가면 다시 작게
		// 액션 리스너 이벤트 처리 : 클릭하면 랭크패널이 보이도록 함, rhigtPanel이 보이지 않도록 함
		// rightPanel에 add		
		btnRank = new JButton("랭킹보기");
		btnRank.setFont(new Font("1훈하얀고양이 Regular",Font.BOLD,25));
		btnRank.setBounds(300,650,200,50);
		btnRank.setForeground(Color.WHITE);
		btnRank.setBorderPainted(false);
		btnRank.setFocusPainted(false);
		btnRank.setBackground(Color.pink);
		btnRank.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {
				btnRank.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				btnRank.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 25));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		btnRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();
				
				if (obj == btnRank) {
					rightPanel.setVisible(false);
					rankPanel.setVisible(true);
				}
			}
		});
		rightPanel.add(btnRank);	
		
		
	}
	
	// 종합 점수를 User로 부터 받아 일반모드이거나 점수가 15점 이상인 경우 좋은 평가를
	// 15점 이하인 경우 우스꽝스런 평가를 라벨에 생성
	public void setTotalScore(UserData User) {
		totalScore = User.getTotalScore();
		if( totalScore  > 15 || User.getMode() == 1 )
			evalLabel = new JLabel(evalGood[nRandom]);
		else
			evalLabel = new JLabel(evalBad[nRandom]);
		
	}

}
