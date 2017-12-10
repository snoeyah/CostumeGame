import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class StartPanel extends JPanel{

	private JPanel				under;
	private JLabel 				title,userName;
	private JTextField 			txtinput;
	private JRadioButton 		mode1, mode2;
	private JButton 			nxtbtn,soundOff;
	private String 				name;
	private ImageIcon 			icon,volume;
	private int					mode = -1;
	private SelectItemListener 	modeCheck;
	
	public StartPanel(MainFrame frame,UserData User)
	{
		// 프레임의 크기 : 1200x900, 배경생상 : 흰색, 레이아웃매니저 : disabled
		setPreferredSize(new Dimension(1200, 900));
		setBackground(Color.WHITE);
		setLayout(null);
		
		// 게임모드 체크 내부 클래스 생성
		modeCheck = new SelectItemListener();
		
		// 위치 : x=0 y=650, 크기 : 1200x250, 배경생상 : 분홍, 레이아웃매니저 : disabled
		// 현재 패널에 add
		under = new JPanel();
		under.setBackground(Color.pink);
		under.setBounds(0, 650, 1200, 250);
		under.setLayout(null);
		add(under);
		
		// 타이틀 문구 : 초기화면에서 입력받은 사용자 이름을 User에서 불러옴
		// 위치 : x=0 y=100, 크기 : 1200x200
		// 글자 색상 : 검정 , 폰트 설정, 수평적 가운데 정렬
		// 현재 패널에 add
		name = User.getName();
		JLabel userName = new JLabel(name+"의");
		userName.setFont(new Font("1훈하얀고양이 Regular",Font.BOLD,200));
		userName.setForeground(Color.BLACK);
		userName.setBounds(0, 100, 1200, 200);
		userName.setHorizontalAlignment(SwingConstants.CENTER);
		add(userName);
		
		// 게임의 타이틀 이미지 라벨 셍성 , 가운데 정렬
		// 위치 : x=0 y=300, 크기 : 1200x300
		// 현재패널에 add
		icon = new ImageIcon("Images/introDesign.png");
		JLabel title
		= new JLabel("",icon,SwingConstants.CENTER);
		title.setBounds(0, 300, 1200, 300);
		add(title);
	
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
		
		// 라디오 버튼 : mode1과 mode2
		// 폰트 설정 및 아이템 리스너 add : 모드 선택 여부
		// 크기 : 180x40 , 수평적 가운데 정렬 
		// 클릭시 색상 없음, 백그라운드색상 없음
		JRadioButton mode1 = new JRadioButton("일반모드");
		mode1.setFont(new Font("LG PC", Font.BOLD, 25));
		mode1.addItemListener(modeCheck);
		mode1.setSize(180, 40);
		mode1.setHorizontalAlignment(SwingConstants.CENTER);
		mode1.setFocusPainted(false);
		mode1.setBackground(null);
		
		JRadioButton mode2 = new JRadioButton("챌린지모드");
		mode2.setFont(new Font("LG PC", Font.BOLD, 25));
		mode2.addItemListener(modeCheck);
		mode2.setSize(180, 40);
		mode2.setHorizontalAlignment(SwingConstants.CENTER);
		mode2.setFocusPainted(false);
		mode2.setBackground(null);
		
		// 버튼 그룹 생성 및 add
		ButtonGroup buttonGrp = new ButtonGroup();
		buttonGrp.add(mode1);
		buttonGrp.add(mode2);

		// 라디오버튼 패널 생성 및 add
		// 위치 : x=400 y=0, 크기 : 400x100
		// 배경 없음 , 그리드 레이아웃 생성 ( 1행 2열 )
		// under패널에 add
		JPanel radibtn = new JPanel();
		radibtn.add(mode1);
		radibtn.add(mode2);
		radibtn.setBounds(400, 0, 400, 100);
		radibtn.setBackground(null);
		radibtn.setLayout(new GridLayout(1,2));
		under.add(radibtn);


		// 다음화면 이동 버튼
		// 폰트 설정 , 위치 : x=500 y=100, 크기 : 200x100
		// 테두리 없엄, 클릭시 태두리 없음 , 배경 없음
		// 마우스 리스너 이벤트 처리 : 마우스가 버튼위에 올라가면 폰트 크게, 나가면 다시 작게 설정
		// 액션 리스너 이벤트 처리 : 모드 미선택 시 경고 알림창이 뜸 
		// 모드 선택 한 겅우 User에 사용자 이름 저장하고, 프레임의 화면전환 메소드 호출
		// 라디오 버튼 삭제
		// under패널에 add
		nxtbtn = new JButton("NEXT");
		nxtbtn.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
		nxtbtn.setBounds(500, 100, 200, 100);
		nxtbtn.setHorizontalAlignment(SwingConstants.CENTER);
		nxtbtn.setBorderPainted(false);
		nxtbtn.setContentAreaFilled(false);
		nxtbtn.setFocusPainted(false);
		nxtbtn.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent sevent) {
				nxtbtn.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 40));
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				nxtbtn.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		nxtbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();
				
				if( obj == nxtbtn ) {
					if( mode == -1 )
						JOptionPane.showMessageDialog(title,"모드를 선택해주세요.");
					else{
						User.setMode(mode);
						radibtn.remove(mode1);
						radibtn.remove(mode2);
						under.remove(nxtbtn);
						frame.Change("Makeup");
					}
				}

			}
		});
		under.add(nxtbtn);
	}

	// 모드 체크하는 내부 클래스
	// 버튼의 텍스트를 비교하여 모드를 저장
	class SelectItemListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e) {
			AbstractButton sel = (AbstractButton)e.getItemSelectable();
			
			if( e.getStateChange() == ItemEvent.SELECTED )
			{
				if( sel.getText().equals("일반모드"))
					mode = 1;
				else if( sel.getText().equals("챌린지모드")) {
					mode = 2;
				}
			}
		}
	}
}
