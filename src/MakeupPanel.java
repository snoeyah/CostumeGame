import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
// 다음 버튼에서 선택못하면 안넘어가게하는거 설정하기!
public class MakeupPanel extends JPanel{

	private JLabel tool1,tool2,tool3,tool4,label1,lip00,lip01,lip02,lip03,shadow00,shadow01,shadow02,shadow03,chick00,chick01,chick02,chick03,eye000,eye001,eye002,eye003;
	private JLabel	msg;
	private JPanel left,right;
	private JButton shadow0,shadow1,shadow2,shadow3,chick0,chick1,chick2,chick3,lip0,lip1,lip2,lip3;
	private JButton eyebrow0,eyebrow1,eyebrow2,eyebrow3;
	private Color[] color = {new Color(216,195,196), new Color(217,153,159), new Color(255,222,213), new Color(213,168,144),
						 	 new Color(251,225,228), new Color(249,234,237), new Color(252,236,244), new Color(255,233,226), 
							 new Color(247,190,202), new Color(238,148,137), new Color(159,120,127), new Color(220,84,100)};
	private GameListener gameL;
	private JButton 	nxtbtn,soundOff;
	private ImageIcon	volume;
	// private JLabel 4가지 다 선언
	private int			sFlag=-1, eFlag=-1, cFlag=-1, lFlag=-1; 

	

	
	public MakeupPanel( MainFrame frame, UserData User ) {
		
		setPreferredSize(new Dimension(1200,900)); //1200*900 사이즈
		setBackground(Color.WHITE); // 배경화면 색상 - 흰색
		setLayout(null); 

		gameL = new GameListener(); // GameListener 생성
		
		msg = new JLabel("");
		msg.setLocation(600, 450);
		add(msg);
		
		nxtbtn = new JButton("♡ ♡ ♡ 옷 입히러 가기 ♡ ♡ ♡"); // 다음버튼 생성
		nxtbtn.setFont(new Font("LG PC", Font.PLAIN, 20)); // 글꼴, 굵기, 크기 지정
		nxtbtn.setForeground(Color.white); // 글자 색깔 지정
		nxtbtn.setBackground(Color.pink); // 글자 배경화면 색 지정
		nxtbtn.setBounds(0, 850, 1200, 40); // 글자 위치 지정
		nxtbtn.setBorderPainted(false); // JButton의 테두리를 지워준다.
		nxtbtn.setFocusPainted(false); // JButton이 선택되었을때, 생기는 테두리를 지워준다.
		
		nxtbtn.addMouseListener(new MouseListener() { 
			public void mouseEntered(MouseEvent event) {
				nxtbtn.setFont(new Font("LG PC", Font.BOLD, 20)); // 다음 버튼에 마우스가 entered됬을 때의 굵기 지정
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				nxtbtn.setFont(new Font("LG PC", Font.PLAIN, 20));// 다음 버튼에 마우스가 exited됬을 때의 굵기 지정
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		nxtbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();
				
				if (obj == nxtbtn) { // 각각의 옵션을 선택하지 않고 nxtbtn 버튼을 눌렀을시에 나오는 안내창 설정
					if( eFlag == -1 )
						JOptionPane.showMessageDialog(msg,"눈썹모양을 고르세요.");
					else if( sFlag == -1 )
						JOptionPane.showMessageDialog(msg,"쉐도우를 고르세요.");
					else if( cFlag == -1 )
						JOptionPane.showMessageDialog(msg, "블러셔를 고르세요.");
					else if( lFlag == -1 )
						JOptionPane.showMessageDialog(msg,"입술색을 고르세요.");
					else {
					
						User.setMakeup(lFlag,sFlag,cFlag,eFlag); // User에 각각의 flag값을 넣어준다.
						frame.Change("Closet"); // 다음 화면으로의 이동.
					}
				}
				
			}
		});
		add(nxtbtn);
		
		// soundControl
		if( User.getVolume() == 1 ) // 음악 음소거 이미지 설정
			volume = new ImageIcon("Images/volume_on.png");
		else
			volume = new ImageIcon("Images/volume_off.png");
				
		soundOff = new JButton(volume); // 음악의 재생 및 정지 버튼 생성
		soundOff.setBounds(1100,50,50,50); // 버튼의 위치및 크기지정
		soundOff.setBorderPainted(false); // JButton 테두리를 지워준다.
		soundOff.setContentAreaFilled(false); // JButton의 내용영역 채우기 안함.
		soundOff.setFocusPainted(false); // JButton이 선택되었을 때 생기는 테두리를 지워준다.
		soundOff.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {}
			public void mouseClicked(MouseEvent e) { // MouseListener로 음악의 재생 및 정지 설정
				if( User.getVolume() == 1) { // User에서 사용자가 선택한 getVolume값이 1일 때,
					frame.mSound.stop(); // 음악을 정지시키고
					volume = new ImageIcon("Images/volume_off.png"); // off이미지로 바꿔주며,
					User.setVolume(0); // setVolume값을 다시 0으로 바꿔준후 다시 User값에 넣어준다.
				}
				else if( User.getVolume() == 0) {// User에서 사용자가 선택한 getVolume값이 0일 때,
					frame.mSound.play(); // 음악을 재생시키고
					volume = new ImageIcon("Images/volume_on.png"); // on 이미지로 바꿔주며,
					User.setVolume(1); // setVolume값을 다시 1으로 바꿔준후 다시 User값에 넣어준다.
				}
				soundOff.setIcon(volume);
			}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		add(soundOff);
				
		// leftPanel 생성
		left = new JPanel(); 
		left.setBounds(0, 50, 600, 850); // 위치 및 크기 설정
		left.setBackground(Color.white); // 배경 색상 지정
		left.setLayout(null);
		add(left);
	
		// rightPanel 생성
		right = new JPanel();
		right.setBounds(600, 0, 600, 900); // 위치 및 크기 설정
		right.setBackground(Color.white); // 배경 색상 지정 
		right.setLayout(null);
		add(right);
		
		ImageIcon icon2 = new ImageIcon("Images/makeup/lip0.png"); // face이미지 위에 각각의 메이크업 이미지들 setVisible을 사용해 쌓아 놓기
		lip00 = new JLabel("",icon2,SwingConstants.CENTER); // 이미지를 중심에 배열.
		lip00.setBounds(0, 0, 600, 800); // 위치 및 크기 설정
		left.add(lip00); // left패널에 넣어준다.
		lip00.setVisible(false); // setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon3 = new ImageIcon("Images/makeup/lip1.png");
		lip01 = new JLabel("",icon3,SwingConstants.CENTER);// 이미지를 중심에 배열.
		lip01.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(lip01);// left패널에 넣어준다.
		lip01.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon4 = new ImageIcon("Images/makeup/lip2.png");
		lip02 = new JLabel("",icon4,SwingConstants.CENTER);// 이미지를 중심에 배열.
		lip02.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(lip02);// left패널에 넣어준다.
		lip02.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon5 = new ImageIcon("Images/makeup/lip3.png");
		lip03 = new JLabel("",icon5,SwingConstants.CENTER);// 이미지를 중심에 배열.
		lip03.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(lip03);// left패널에 넣어준다.
		lip03.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon6 = new ImageIcon("Images/makeup/shadow0.png");
		shadow00 = new JLabel("",icon6,SwingConstants.CENTER);// 이미지를 중심에 배열.
		shadow00.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(shadow00);// left패널에 넣어준다.
		shadow00.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon7 = new ImageIcon("Images/makeup/shadow1.png");
		shadow01 = new JLabel("",icon7,SwingConstants.CENTER);// 이미지를 중심에 배열.
		shadow01.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(shadow01);// left패널에 넣어준다.
		shadow01.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon8 = new ImageIcon("Images/makeup/shadow2.png");
		shadow02 = new JLabel("",icon8,SwingConstants.CENTER);// 이미지를 중심에 배열.
		shadow02.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(shadow02);// left패널에 넣어준다.
		shadow02.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon9 = new ImageIcon("Images/makeup/shadow3.png");
		shadow03 = new JLabel("",icon9,SwingConstants.CENTER);// 이미지를 중심에 배열.
		shadow03.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(shadow03);// left패널에 넣어준다.
		shadow03.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon10 = new ImageIcon("Images/makeup/chick0.png");
		chick00 = new JLabel("",icon10,SwingConstants.CENTER);// 이미지를 중심에 배열.
		chick00.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(chick00);// left패널에 넣어준다.
		chick00.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon11 = new ImageIcon("Images/makeup/chick1.png");
		chick01 = new JLabel("",icon11,SwingConstants.CENTER);// 이미지를 중심에 배열.
		chick01.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(chick01);// left패널에 넣어준다.
		chick01.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon12 = new ImageIcon("Images/makeup/chick2.png");
		chick02 = new JLabel("",icon12,SwingConstants.CENTER);// 이미지를 중심에 배열.
		chick02.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(chick02);// left패널에 넣어준다.
		chick02.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon13 = new ImageIcon("Images/makeup/chick3.png");
		chick03 = new JLabel("",icon13,SwingConstants.CENTER);// 이미지를 중심에 배열.
		chick03.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(chick03);// left패널에 넣어준다.
		chick03.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon14 = new ImageIcon("Images/makeup/eyebrow0.png");
		eye000 = new JLabel("",icon14,SwingConstants.CENTER);// 이미지를 중심에 배열.
		eye000.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(eye000);// left패널에 넣어준다.
		eye000.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon15 = new ImageIcon("Images/makeup/eyebrow1.png");
		eye001 = new JLabel("",icon15,SwingConstants.CENTER);// 이미지를 중심에 배열.
		eye001.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(eye001);// left패널에 넣어준다.
		eye001.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon16 = new ImageIcon("Images/makeup/eyebrow2.png");
		eye002 = new JLabel("",icon16,SwingConstants.CENTER);// 이미지를 중심에 배열.
		eye002.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(eye002);// left패널에 넣어준다.
		eye002.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		ImageIcon icon17 = new ImageIcon("Images/makeup/eyebrow3.png");
		eye003 = new JLabel("",icon17,SwingConstants.CENTER);// 이미지를 중심에 배열.
		eye003.setBounds(0, 0, 600, 800);// 위치 및 크기 설정
		left.add(eye003);// left패널에 넣어준다.
		eye003.setVisible(false);// setVisible 을 false로 지정해 보이지 않게 해준다.
		
		
		ImageIcon icon = new ImageIcon("Images/makeup/defFace.png"); // face이미지 출력
		label1 = new JLabel("",icon,SwingConstants.CENTER); // 이미지를 중심에 배열.
		label1.setBounds(0, 0, 600, 800); // 이미지 위치 및 크기 설정
		left.add(label1); // left패널에 넣어준다.

		
		tool1 = new JLabel("Eyebrow"); //메이크업 옵션들의 이름을 JLabel을 사용해 출력
		tool1.setBounds(50,100,600,50); // JLabel의 위치 및 크기 설정
		tool1.setFont(new Font("LG PC",Font.BOLD,30)); // 글꼴, 굵기, 크기 설정
		tool1.setHorizontalAlignment(SwingConstants.LEFT); // 좌측정렬
		right.add(tool1); // right패널에 넣어준다.
		

		//각각의 메이크업 옵션 이미지버튼 생성
		eyebrow0 = new JButton(new ImageIcon("Images/makeup/eyebrow00.png"));
	    eyebrow0.setBounds(50,170,80,80); // 위치및 크기 설정
	    right.add(eyebrow0); //right패널에 넣어준다.
	   
	    eyebrow1 = new JButton(new ImageIcon("Images/makeup/eyebrow01.png"));
	    eyebrow1.setBounds(150,170,80,80);
	    right.add(eyebrow1);
	    
	    eyebrow2 = new JButton(new ImageIcon("Images/makeup/eyebrow02.png"));
	    eyebrow2.setBounds(250,170,80,80);
	    right.add(eyebrow2);
	    
	    
	    eyebrow3 = new JButton(new ImageIcon("Images/makeup/eyebrow03.png"));
	    eyebrow3.setBounds(350,170,80,80);
	    right.add(eyebrow3);

		//메이크업 옵션들의 이름을 JLabel을 사용해 출력
		tool2 = new JLabel("Eyeshadow");
		tool2.setBounds(50,280,600,50);// JLabel의 위치 및 크기 설정
		tool2.setFont(new Font("LG PC",Font.BOLD,30)); // 글꼴, 굵기, 크기 설정
 		tool2.setHorizontalAlignment(SwingConstants.LEFT); // 좌측정렬
		right.add(tool2); // right패널에 넣어준다.
		
		//각각의 메이크업 옵션 버튼 생성
		shadow0 = new JButton("");
		shadow0.setBounds(50,350,80,80); // 위치및 크기 설정
		shadow0.setBackground(color[0]); // 배열로 지정해준 색상을 버튼에 지정.
		right.add(shadow0); //right패널에 넣어준다.
		
		shadow1 = new JButton("");
		shadow1.setBounds(150,350,80,80);
		shadow1.setBackground(color[1]);
		right.add(shadow1);
		
		shadow2 = new JButton("");
		shadow2.setBounds(250,350,80,80);
		shadow2.setBackground(color[2]);
		right.add(shadow2);
		
		shadow3 = new JButton("");
		shadow3.setBounds(350,350,80,80);
		shadow3.setBackground(color[3]);
		right.add(shadow3);
		
		//메이크업 옵션들의 이름을 JLabel을 사용해 출력
		tool3 = new JLabel("Chick");
		tool3.setBounds(50,460,600,50);// JLabel의 위치 및 크기 설정
		tool3.setFont(new Font("LG PC",Font.BOLD,30));// 글꼴, 굵기, 크기 설정
		tool3.setHorizontalAlignment(SwingConstants.LEFT);// 좌측정렬
		right.add(tool3);// right패널에 넣어준다.
		
		//각각의 메이크업 옵션 버튼 생성
		chick0 = new JButton("");
		chick0.setBounds(50,530,80,80); // 위치 및 크기 설정
		chick0.setBackground(color[4]); // 배열에 지정해준 색상을 버튼에 지정
		right.add(chick0); // right패널에 넣어준다.
		
		chick1 = new JButton("");
		chick1.setBounds(150,530,80,80);
		chick1.setBackground(color[5]);
		right.add(chick1);
		
		chick2 = new JButton("");
		chick2.setBounds(250,530,80,80);
		chick2.setBackground(color[6]);
		right.add(chick2);
		
		chick3 = new JButton("");
		chick3.setBounds(350,530,80,80);
		chick3.setBackground(color[7]);
		right.add(chick3);
		
		//메이크업 옵션들의 이름을 JLabel을 사용해 출력
		tool4 = new JLabel("Lip");
		tool4.setBounds(50,640,600,50); // JLabel의 위치 및 크기 설정
		tool4.setFont(new Font("LG PC",Font.BOLD,30)); // 글꼴, 굵기, 크기 설정
		tool4.setHorizontalAlignment(SwingConstants.LEFT);// 좌측정렬
		right.add(tool4); // right패널에 넣어준다.
		
		//각각의 메이크업 옵션 버튼 생성
		lip0 = new JButton("");
		lip0.setBounds(50,710,80,80); // 위치 및 크기 설정
		lip0.setBackground(color[8]); // 배열에 지정해준 색상을 버튼에 지정
		right.add(lip0); // right패널에 넣어준다.
		
		lip1 = new JButton("");
		lip1.setBounds(150,710,80,80);
		lip1.setBackground(color[9]);
		right.add(lip1);
		
		lip2 = new JButton("");
		lip2.setBounds(250,710,80,80);
		lip2.setBackground(color[10]);
		right.add(lip2);
		
		lip3 = new JButton("");
		lip3.setBounds(350,710,80,80);
		lip3.setBackground(color[11]);
		right.add(lip3);
		
		eyebrow0.addActionListener(gameL);
		eyebrow1.addActionListener(gameL);
		eyebrow2.addActionListener(gameL);
		eyebrow3.addActionListener(gameL);
		shadow0.addActionListener(gameL);
		shadow1.addActionListener(gameL);
		shadow2.addActionListener(gameL);
		shadow3.addActionListener(gameL);
		chick0.addActionListener(gameL);
		chick1.addActionListener(gameL);
		chick2.addActionListener(gameL);
		chick3.addActionListener(gameL);
		lip0.addActionListener(gameL);
		lip1.addActionListener(gameL);
		lip2.addActionListener(gameL);
		lip3.addActionListener(gameL);	
		
	}


private class GameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object obj = event.getSource(); // 사용자가 선택한 obj를 받아온다
			if(obj == lip0){ // 사용자가 각각의 옵션들을 선택했을 때,
				if( lFlag == 0 ) { lip00.setVisible(false); lFlag = -1; } // flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else { // 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				lFlag = 0;
				lip00.setVisible(true);
				lip01.setVisible(false);
				lip02.setVisible(false);
				lip03.setVisible(false);
				}
			}
			else if(obj == lip1 ) {
				if( lFlag == 1 ) { lip01.setVisible(false); lFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				lFlag = 1;
				lip00.setVisible(false);
				lip01.setVisible(true);
				lip02.setVisible(false);
				lip03.setVisible(false);
				}
			}
			else if(obj == lip2 ) {
				if( lFlag == 2 ) { lip02.setVisible(false); lFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				lFlag = 2;
				lip00.setVisible(false);
				lip01.setVisible(false);
				lip02.setVisible(true);
				lip03.setVisible(false);
				}
			}
			else if(obj == lip3 ) {
				if( lFlag == 3 ) { lip03.setVisible(false); lFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {
				lFlag = 3;
				lip00.setVisible(false);
				lip01.setVisible(false);
				lip02.setVisible(false);
				lip03.setVisible(true);
				}
			}
			if(obj == shadow0) {
				if( sFlag == 0 ) { shadow00.setVisible(false); sFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				sFlag = 0;
				shadow00.setVisible(true);
				shadow01.setVisible(false);
				shadow02.setVisible(false);
				shadow03.setVisible(false);
				}
			}
			else if(obj == shadow1) {
				if( sFlag == 1 ) { shadow01.setVisible(false); sFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				sFlag = 1;
				shadow00.setVisible(false);
				shadow01.setVisible(true);
				shadow02.setVisible(false);
				shadow03.setVisible(false);
				}
			}
			else if(obj == shadow2) {
				if( sFlag == 2 ) { shadow02.setVisible(false); sFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				sFlag = 2;
				shadow00.setVisible(false);
				shadow01.setVisible(false);
				shadow02.setVisible(true);
				shadow03.setVisible(false);
				}
			}
			else if(obj == shadow3) {
				if( sFlag == 3 ) { shadow03.setVisible(false); sFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				sFlag = 3;
				shadow00.setVisible(false);
				shadow01.setVisible(false);
				shadow02.setVisible(false);
				shadow03.setVisible(true);
				}
			}
			if(obj == chick0) {
				if( cFlag == 0 ) { chick00.setVisible(false); cFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				cFlag = 0;
				chick00.setVisible(true);
				chick01.setVisible(false);
				chick02.setVisible(false);
				chick03.setVisible(false);
				}
			}
			else if(obj == chick1) {
				if( cFlag == 1 ) { chick01.setVisible(false); cFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				cFlag = 1;
				chick00.setVisible(false);
				chick01.setVisible(true);
				chick02.setVisible(false);
				chick03.setVisible(false);	
				}
			}
			else if(obj == chick2) {
				if( cFlag == 2 ) { chick02.setVisible(false); cFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				cFlag = 2;
				chick00.setVisible(false);
				chick01.setVisible(false);
				chick02.setVisible(true);
				chick03.setVisible(false);	
				}
			}
			else if(obj == chick3) {
				if( cFlag == 3 ) { chick03.setVisible(false); cFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				cFlag = 3;
				chick00.setVisible(false);
				chick01.setVisible(false);
				chick02.setVisible(false);
				chick03.setVisible(true);	
				}
			}
			if(obj == eyebrow0) {
				if( eFlag == 0 ) { eye000.setVisible(false); eFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				eFlag = 0;
				eye000.setVisible(true);
				eye001.setVisible(false);
				eye002.setVisible(false);
				eye003.setVisible(false);
				}
			}
			else if(obj == eyebrow1) {
				if( eFlag == 1 ) { eye001.setVisible(false); eFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				eFlag = 1;
				eye000.setVisible(false);
				eye001.setVisible(true);
				eye002.setVisible(false);
				eye003.setVisible(false);
				}
			}
			else if(obj == eyebrow2) {
				if( eFlag == 2 ) { eye002.setVisible(false); eFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				eFlag = 2;
				eye000.setVisible(false);
				eye001.setVisible(false);
				eye002.setVisible(true);
				eye003.setVisible(false);	
				}
			}
			else if(obj == eyebrow3) {
				if( eFlag == 3 ) { eye003.setVisible(false); eFlag = -1; }// flag가 0일때, 그에 해당하는 이미지를 false로 바꿔준뒤, flag값을 -1로 바꿔준다.
				else {// 그외에는 해당하는 이미지를 true로 보여지게 한다. flag값도 0으로 바꿔준다.
				eFlag = 3;
				eye000.setVisible(false);
				eye001.setVisible(false);
				eye002.setVisible(false);
				eye003.setVisible(true);
				}
			}

		}
	}

}


