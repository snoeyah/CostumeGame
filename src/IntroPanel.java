import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntroPanel extends JPanel{

	private JLabel Enter;
	private JButton strbtn,soundOff;
	private JTextField nameInput;
	private String name;
	private ImageIcon icon,volume;
	
	public IntroPanel(MainFrame frame, UserData userdata)
	{
		// 프레임의 크기 : 1200x900, 배경생상 : 흰색, 레이아웃매니저 : disabled
		setPreferredSize(new Dimension(1200,900));
		setBackground(Color.WHITE);
		setLayout(null);
		
		// 게임의 타이틀 이미지 라벨 셍성 , 가운데 정렬
		// 위치 : x=0 y=200, 크기 : 1200x300
		// 현재패널에 add
		icon = new ImageIcon("Images/introDesign.png");
		JLabel title
		= new JLabel("",icon,SwingConstants.CENTER);
		title.setBounds(0, 200, 1200, 300);
		add(title);

		volume = new ImageIcon("Images/volume_on.png");
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
				if( userdata.getVolume() == 1) {
					frame.mSound.stop();
					volume = new ImageIcon("Images/volume_off.png");
					userdata.setVolume(0);
				}
				else if( userdata.getVolume() == 0) {
					frame.mSound.play();
					volume = new ImageIcon("Images/volume_on.png");
					userdata.setVolume(1);
				}
				soundOff.setIcon(volume);
			}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		add(soundOff);
		
		// 사용자 입력을 요구하는 라벨 
		// 위치 : x=0 y=550, 크기 : 1200x30
		// 수평으로 중심, 폰트 설정, 폰트색상 : 검정
		// 현재 패널에 add
		Enter = new JLabel("사용자 이름을 입력하시오 ( 최대 7글자 )");
		Enter.setBounds(0, 550, 1200, 30);
		Enter.setHorizontalAlignment(SwingConstants.CENTER);
		Enter.setFont(new Font("1훈하얀고양이 Regular",Font.PLAIN,30));
		Enter.setForeground(Color.BLACK);
		add(Enter);
		
		// 사용자 이름 입력하는 텍스트 필드 생성
		// 7글자로 글자수 제한 , 수평으로 가운데 정렬
		// 폰트 설정, 폰트 색상: 흰색
		// 위치 : x=400 y=600, 크기 : 400x50
		// 텍스트 필드 배경색상 : 분홍 , 테두리 흰색 
		// 현재 패널에 add
		nameInput = new JTextField();
		nameInput.setDocument((new JTextFieldLimit(7)));
		nameInput.setHorizontalAlignment(JTextField.CENTER);
		nameInput.setFont(new Font("1훈하얀고양이 Regular",Font.BOLD,30));
		nameInput.setForeground(Color.WHITE);
		nameInput.setBounds(400, 600, 400, 50);
		nameInput.setBackground(Color.PINK);
		nameInput.setBorder(new LineBorder(Color.WHITE, 2));	
		add(nameInput);

		// 게임시작버튼 생성 
		// 폰트 설정 , 위치 : x=500 y=700, 크기 : 200x50
		// 테두리 없엄, 클릭시 태두리 없음 , 배경 없음
		// 마우스 리스너 이벤트 처리 : 마우스가 버튼위에 올라가면 폰트 크게, 나가면 다시 작게 설정
		// 액션 리스너 이벤트 처리 : 텍스트를 입력하지 않았을 시 경고알림창 뜨게 함
		// 입력 했을 겅우 userdata에 사용자 이름 저장하고, 프레임의 화면전환 메소드 호출
		// 현재 패널에 add
		strbtn = new JButton("START");
		strbtn.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
		strbtn.setBounds(500, 700, 200, 50);
		strbtn.setBorderPainted(false);
		strbtn.setContentAreaFilled(false);
		strbtn.setFocusPainted(false);
		strbtn.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent event) {
				strbtn.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 40));
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				strbtn.setFont(new Font("1훈하얀고양이 Regular", Font.BOLD, 30));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		strbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Object obj = event.getSource();
				
				if( obj == strbtn ) {
					name = nameInput.getText();
					
					if( name.length() == 0 ){
						JOptionPane.showMessageDialog(title,"이름을 입력하시요.");
					}
					else{
						userdata.setName(name);
						frame.Change("Start");
					}
				}

			}
		});
		add(strbtn);

	}
	
	// 텍스트필드 글자수 제한 내부 클래스
	// 제한 글자수를 넘을 경우 마지막 글자만 계속 바뀌도록 함
	class JTextFieldLimit extends PlainDocument {
		   private int limit;
		   private boolean toUppercase = false;
		    JTextFieldLimit( int limit) {
		      super();
		      this.limit = limit;
		   }
		    JTextFieldLimit(int limit, boolean upper) {
		      super();
		      this.limit = limit;
		      this.toUppercase = upper;
		   }
		 
		   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		      if (str == null) {
		         return;
		      }
		      if ( (getLength() + str.length()) <= limit) {
		         if (toUppercase) {
		            str = str.toUpperCase();
		         }
		         super.insertString(offset, str, attr);
		      }
		   }
		}
}
