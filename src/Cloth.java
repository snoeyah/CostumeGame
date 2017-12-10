import java.awt.*;
import javax.swing.*;

public class Cloth {
	
	private String name;
	private ImageIcon image;
	
	public Cloth( String n , ImageIcon img ){
		name = n;
		image = img;
	} // default constructor

	// set함수 : 이름 , 이미지
	void setName( String n ) {	name = n;  }
	void setImage( ImageIcon img ) { image = img; }
	
	// get함수 : 이름 , 이미지
	String getName() { return name; }
	ImageIcon getImage() { return image; }
}
