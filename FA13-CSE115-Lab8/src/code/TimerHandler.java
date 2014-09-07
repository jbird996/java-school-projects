package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int x = 1;
		while(x < 1000000){
		System.out.println(x);
		x = (x+1)/1000;
		}
	}

}
