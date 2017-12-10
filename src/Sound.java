import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	// 배경음악 MainFrame에서 반복 재생
			private Clip clip;
			private File clap;
			
			public Sound(File Sound) {
				clap = Sound; // 클립에 사운드 파일을 저장
				
				try {
					clip = AudioSystem.getClip(); // 파일재생에 사용할 클립을 가져옴
					clip.open(AudioSystem.getAudioInputStream(clap)); //제공된 오디오 형식으로 클립을 염			
				}
				catch(Exception e) {	
				}
			}
			// 사운드 실행 함수
			public void play() {
				clip.loop(Clip.LOOP_CONTINUOUSLY); // 반복재생 되도록 함
				clip.start(); // 재생
			}
			// 사운드 정지 함수
			public void stop() {
				clip.stop(); // 정지
			}
}
