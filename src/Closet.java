import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class Closet extends JPanel {

	private JButton 	nxtbtn,soundOff;
	private JLabel		msg;
	private ImageIcon 	mAvatar,volume;
	private JLabel 		aLabel, fLabel, mConcept;
	private JLabel[] 	concept = new JLabel[2];
	private JLabel[] 	mTop = new JLabel[4];
	private JLabel[] 	mBottom = new JLabel[6];
	private JLabel[] 	mShoes = new JLabel[4];
	
	private boolean 	isDragged = false;
	private int 		nRandom, offX, offY, imgX, imgY;
	private int			tFlag=0, bFlag=0, sFlag=0; 

	private Top[] tops = new Top[4];
	private Bottom[] bottoms = new Bottom[6];
	private Shoes[] shoes = new Shoes[4];

	public Closet(MainFrame frame, UserData User) {

		// 프레임의 크기 : 1200x900, 배경생상 : 흰색, 레이아웃매니저 : disabled
		setPreferredSize(new Dimension(1200, 900));
		setBackground(Color.WHITE);
		setLayout(null);

		// 두가지 컨셉 저장
		concept[0] = new JLabel("♡ 데이트룩 ♡");
		concept[1] = new JLabel("♡ 스쿨룩 ♡");
		
		// 챌린지 모드인 경우 컨셉을 랜덤으로 라벨에 생성
		// 위치 : x=500 y=50, 크기 : 200x30
		// 현재패널에 add
		if( User.getMode() == 2 ) {
			nRandom = (int)(Math.random()*2);
			User.setConcept(nRandom);
			mConcept = concept[nRandom];
			mConcept.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
			mConcept.setHorizontalAlignment(SwingConstants.CENTER);
			mConcept.setBounds(500, 50, 200, 30);
			add(mConcept);
		}

		// 다음화면 이동 버튼
		// 폰트 설정 , 위치 : x=0 y=850, 크기 : 1200x40
		// 테두리 없엄, 클릭시 태두리 없음 , 배경 : 분홍
		// 마우스 리스너 이벤트 처리 : 마우스가 버튼위에 올라가면 폰트 크게, 나가면 다시 작게 설정
		// 액션 리스너 이벤트 처리 : 옷 하나라도 안입힐 시 해당 옷을 입히라는 경고 알림창이 뜸
		// 모두 선택 한 겅우 프레임의 화면전환 메소드 호출
		// 현재패널에 add
		nxtbtn = new JButton("♡ ♡ ♡ 완성 ♡ ♡ ♡");
		nxtbtn.setFont(new Font("LG PC", Font.PLAIN, 20));
		nxtbtn.setForeground(Color.white);
		nxtbtn.setBackground(Color.pink);
		nxtbtn.setBounds(0, 850, 1200, 40);
		nxtbtn.setBorderPainted(false);
		nxtbtn.setFocusPainted(false);
		
		nxtbtn.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {
				nxtbtn.setFont(new Font("LG PC", Font.BOLD, 20));
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				nxtbtn.setFont(new Font("LG PC", Font.PLAIN, 20));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		nxtbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();
				
				if (obj == nxtbtn) {
					if( tFlag == 0 )
						JOptionPane.showMessageDialog(msg,"상의를 입혀주세요.");
					else if( bFlag == 0 )
						JOptionPane.showMessageDialog(msg,"하의를 입혀주세요.");
					else if( sFlag == 0)
						JOptionPane.showMessageDialog(msg,"신발을 신겨주세요.");
					else {
						User.setScore();
						frame.Change("Finish");
					}
						
				}
				
			}
		});
		add(nxtbtn);
		
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
				

		// Cloth ( 공통 사항 )
		// 상의 하의 신발 생성 
		// 마우스 리스너 이벤트 처리 : 마무스로 옷을 선택하면 isDragged 플래그를 1로 설정하고 움직이는 동안 로케이션을 계속 재설정
		// 					  마우스가 떼어지면 isDragged를 0으로 설정하고, 만약 옷이 특정 범위 안에 있으면,
		//					 옷을 착용한 것으로 간주하고 User의 set메소드를 호출하여 옷정보 저장,
		//					 가장 나중에 입힌 옷이 유저데이타에 저장
		// 현재 패널에 add
		tops[0] = new Top(0, "top1", new ImageIcon("Images/clothes/Top1.png"));
		tops[1] = new Top(0, "top2", new ImageIcon("Images/clothes/Top2.png"));
		tops[2] = new Top(0, "top3", new ImageIcon("Images/clothes/Top3.png"));
		tops[3] = new Top(0, "top4", new ImageIcon("Images/clothes/Top4.png"));
		
		bottoms[0] = new Bottom(0, "Bottom1", new ImageIcon("Images/clothes/Bottom1.png"));
		bottoms[1] = new Bottom(0, "Bottom2", new ImageIcon("Images/clothes/Bottom2.png"));
		bottoms[2] = new Bottom(0, "Bottom3", new ImageIcon("Images/clothes/Bottom3.png"));
		bottoms[3] = new Bottom(0, "Bottom4", new ImageIcon("Images/clothes/Bottom4.png"));
		bottoms[4] = new Bottom(0, "Bottom5", new ImageIcon("Images/clothes/Bottom5.png"));
		bottoms[5] = new Bottom(0, "Bottom6", new ImageIcon("Images/clothes/Bottom6.png"));

		shoes[0] = new Shoes(0, "shoes1", new ImageIcon("Images/clothes/Shoes1.png"));
		shoes[1] = new Shoes(0, "shoes2", new ImageIcon("Images/clothes/Shoes2.png"));
		shoes[2] = new Shoes(0, "shoes3", new ImageIcon("Images/clothes/Shoes3.png"));
		shoes[3] = new Shoes(0, "shoes4", new ImageIcon("Images/clothes/Shoes4.png"));

		// 컨셉에 따른 점수 조정
		conceptScore();
		
		// bottom
		mBottom[0] = new JLabel(new ImageIcon("Images/clothList/Bottom1.png"));
		mBottom[0].setBounds(500, 390, 190, 170);
		mBottom[0].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX() ;
				offY = event.getY() ;
				isDragged = true;
				//mCurrentCloset = mBottom[0];
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserBottom(bottoms[0]);
					bFlag = 1;
				}
				else 
					bFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mBottom[0].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX =   event.getX()   + mBottom[0].getX() -offX;
					imgY =   event.getY()   + mBottom[0].getY() -offY;
					mBottom[0].setLocation(imgX,imgY);
				}
				
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mBottom[0]);

		mBottom[1] = new JLabel(new ImageIcon("Images/clothList/Bottom2.png"));
		mBottom[1].setBounds(590, 390, 190, 170);
		mBottom[1].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserBottom(bottoms[1]);
					bFlag = 1;
				}
				else
					bFlag = 0;
			}
			public void mouseClicked(MouseEvent event) { }
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mBottom[1].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mBottom[1].getX() - offX;
					imgY = event.getY() + mBottom[1].getY() - offY;
				}
				mBottom[1].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mBottom[1]);

		mBottom[2] = new JLabel(new ImageIcon("Images/clothList/Bottom3.png"));
		mBottom[2].setBounds(660, 390, 190, 170);
		mBottom[2].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserBottom(bottoms[2]);
					bFlag = 1;
				}
				else
					bFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mBottom[2].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mBottom[2].getX() - offX;
					imgY = event.getY() + mBottom[2].getY() - offY;
				}
				mBottom[2].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mBottom[2]);

		mBottom[3] = new JLabel(new ImageIcon("Images/clothList/Bottom4.png"));
		mBottom[3].setBounds(750, 390, 180, 370);
		mBottom[3].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserBottom(bottoms[3]);
					bFlag = 1;
				}
				else
					bFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mBottom[3].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mBottom[3].getX() - offX;
					imgY = event.getY() + mBottom[3].getY() - offY;
				}
				mBottom[3].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mBottom[3]);

		mBottom[4] = new JLabel(new ImageIcon("Images/clothList/Bottom5.png"));
		mBottom[4].setBounds(820, 390, 150, 370);
		mBottom[4].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserBottom(bottoms[4]);
					bFlag = 1;
				}
				else
					bFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mBottom[4].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mBottom[4].getX() - offX;
					imgY = event.getY() + mBottom[4].getY() - offY;
				}
				mBottom[4].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mBottom[4]);

		mBottom[5] = new JLabel(new ImageIcon("Images/clothList/Bottom6.png"));
		mBottom[5].setBounds(890, 390, 150, 370);
		mBottom[5].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserBottom(bottoms[5]);
					bFlag = 1;
				}
				else
					bFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mBottom[5].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mBottom[5].getX() - offX;
					imgY = event.getY() + mBottom[5].getY() - offY;
				}
				mBottom[5].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mBottom[5]);

		mTop[0] = new JLabel( new ImageIcon("Images/clothList/Top1.png"));
		mTop[0].setBounds(500, 100, 220, 280);
		mTop[0].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+220)<=370 ) {
					User.setUserTop(tops[0]);
					tFlag = 1;
				}
				else
					tFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mTop[0].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mTop[0].getX() - offX;
					imgY = event.getY() + mTop[0].getY() - offY;
				}
				mTop[0].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mTop[0]);

		mTop[1] = new JLabel( new ImageIcon("Images/clothList/Top2.png"));
		mTop[1].setBounds(600, 100, 220, 280);
		mTop[1].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+220)<=370 ) {
					User.setUserTop(tops[1]);
					tFlag = 1;
				}
				else
					tFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mTop[1].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mTop[1].getX() - offX;
					imgY = event.getY() + mTop[1].getY() - offY;
				}
				mTop[1].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mTop[1]);

		mTop[2] = new JLabel( new ImageIcon("Images/clothList/Top3.png"));
		mTop[2].setBounds(700, 100, 220, 280);
		mTop[2].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+220)<=370 ) {
					User.setUserTop(tops[2]);
					tFlag = 1;
				}
				else
					tFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mTop[2].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mTop[2].getX() - offX;
					imgY = event.getY() + mTop[2].getY() - offY;
				}
				mTop[2].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mTop[2]);

		mTop[3] = new JLabel( new ImageIcon("Images/clothList/Top4.png"));
		mTop[3].setBounds(800, 100, 220, 280);
		mTop[3].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+220)<=370 ) {
					User.setUserTop(tops[3]);
					tFlag = 1;
				}
				else
					tFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mTop[3].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mTop[3].getX() - offX;
					imgY = event.getY() + mTop[3].getY() - offY;
				}
				mTop[3].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mTop[3]);

		// Shoes
		mShoes[0] = new JLabel(new ImageIcon("Images/clothList/Shoes1.png"));
		mShoes[0].setBounds(500, 575, 150, 130);
		mShoes[0].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserShoes(shoes[0]);
					sFlag = 1;
				}
				else
					sFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mShoes[0].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mShoes[0].getX() - offX;
					imgY = event.getY() + mShoes[0].getY() - offY;
				}
				mShoes[0].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mShoes[0]);

		mShoes[1] = new JLabel(new ImageIcon("Images/clothList/Shoes2.png"));
		mShoes[1].setBounds(640, 575, 150, 130);
		mShoes[1].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserShoes(shoes[1]);
					sFlag = 1;
				}
				else
					sFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mShoes[1].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mShoes[1].getX() - offX;
					imgY = event.getY() + mShoes[1].getY() - offY;
				}
				mShoes[1].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mShoes[1]);

		mShoes[2] = new JLabel(new ImageIcon("Images/clothList/Shoes3.png"));
		mShoes[2].setBounds(500, 710, 150, 130);
		mShoes[2].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserShoes(shoes[2]);
					sFlag = 1;
				}
				else
					sFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mShoes[2].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mShoes[2].getX() - offX;
					imgY = event.getY() + mShoes[2].getY() - offY;
				}
				mShoes[2].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mShoes[2]);

		mShoes[3] = new JLabel(new ImageIcon("Images/clothList/Shoes4.png"));
		mShoes[3].setBounds(650, 710, 150, 130);
		mShoes[3].addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent event) {
				// 이미지 안에서만 이벤트 작업 설정
				offX = event.getX();
				offY = event.getY();
				isDragged = true;
			}
			public void mouseReleased(MouseEvent event) {
				// 마우스 버튼이 릴리즈되면 드래그 모드 종료
				isDragged = false;
				if( imgX>=130 && (imgX+150)<=370 ) {
					User.setUserShoes(shoes[3]);
					sFlag = 1;
				}
				else
					sFlag = 0;
			}
			public void mouseClicked(MouseEvent event) {}
			public void mouseEntered(MouseEvent event) {}
			public void mouseExited(MouseEvent event) {}
		});
		mShoes[3].addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent event) {
				// 드래그 모드인 경우에만 이미지 이동시킴
				if (isDragged) {
					imgX = event.getX() + mShoes[3].getX() - offX;
					imgY = event.getY() + mShoes[3].getY() - offY;
				}
				mShoes[3].setLocation(imgX, imgY);
			}
			public void mouseMoved(MouseEvent event) {}
		});
		add(mShoes[3]);

		// 아바타 : 옷을 아바타 위에 입히기 위해 아바타를 마지막에 생성
		// 위치 : x=50 y=20, 크기 : 400x800
		// 현재 패널에 add
		mAvatar = new ImageIcon("images/Avatar.png");
		aLabel = new JLabel(mAvatar);
		aLabel.setBounds(50, 20, 400, 800);
		add(aLabel);
	}
	
	// 컨셈에 따른 점수를 재설정 하는 함수
	public void conceptScore() {
		if( nRandom == 0 ) {
			tops[0].setScore(10);
			bottoms[1].setScore(10);
			shoes[2].setScore(10);
		}
		else if( nRandom == 1 ) {
			tops[2].setScore(10);
			bottoms[2].setScore(10);
			shoes[1].setScore(10);
		}
	}
}
