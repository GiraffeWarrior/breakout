import processing.core.PApplet;

public class timer {
	public static void timerX(PApplet Main, float interval, boolean setTime) {
		int savedTime = 0;
		if(setTime) {
		savedTime = Main.millis();
		setTime = false;
		}
		int passedTime = Main.millis() - savedTime;
		if (passedTime >= interval) {
			setTime = true;
			Main.exit();
		}
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
