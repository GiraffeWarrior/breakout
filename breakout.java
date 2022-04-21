import processing.core.PApplet;
import processing.data.FloatList;



public class breakout extends PApplet {

	public void settings() {
		size(1000, 800);
	}
	
	//variable declarations
	//ball coordinate set
	float randH = 500;
	float randW = 400;
	
	//x direction chance variable
	float minusChance = random(2);
	
	//ball angle/speed set
	/*trig to keep the angle within (-+) 30-75 degrees (and maybe keep the speed the same? I'm too tired to check my work)*/
	float xDir = random(6 * (float) 0.25, 6 * (float) Math.sqrt(3)/ 2);
	float yDir = random(6 * (float) 0.96, 6 * (float) 0.5);
	
	//background color set
	float rVal = random(150);
	float gVal = random(100);
	float bVal = random(100);
	
	//luminance formula to maintain high contrast between colors
	double lumin = (0.2126*rVal + 0.7152*gVal + 0.0722*bVal);
	
	//brick color set
	float r = random(255);
	float g = random(255);
	double db = (10* lumin - (r + g))/0.0722;
	float b = (float) db;
	
	//score set
	int score = 0;
	
	//brick coordinate list declarations
	FloatList BricksX = new FloatList();
	FloatList BricksY = new FloatList();
	
	//loop stopping variables
	boolean firstIt = true;
	boolean setTime = true;
	
	//time storing variable declaration
	int savedTime = 0;
	
	public void setup() {
		//sets the x direction (not speed) randomly
		if(minusChance < 1) {
			xDir *= -1;
		}
		//brick coordinate calculator and storage loops
		int x = 1;
		int x1 = 1;
		for(int n = 0; n< 5; n ++) {
			for(int i = 0; i<= 8; i++) {
				if(firstIt) {
				//random brick color
				fill(r, g, b);
				
				}
				//coordinate storage in floatlists
				BricksX.append(10*x + 100*i);
				BricksY.append(10 * x1 + 50*n);
				x ++;
				
			}
			x = 1;
			x1 ++;
			firstIt = false;
		}
		textSize(32);
	}
	
	public void reset() {
		//reset variables
		setTime = true;
		randH = 500;
		randW = 400;
		xDir = random(6 * (float) 0.25, 6 * (float) Math.sqrt(3)/ 2);
		yDir = random(6 * (float) 0.96, 6 * (float) 0.5);
		minusChance = random(2);
		rVal = random(150);
		gVal = random(100);
		bVal = random(100);
		lumin = (0.2126*rVal + 0.7152*gVal + 0.0722*bVal);
		r = random(255);
		g = random(255);
		db = (10* lumin - (r + g))/0.0722;
		b = (float) db;
		score = 0;
		firstIt = true;
		setTime = true;
		savedTime = 0;   
		BricksX = new FloatList();
		BricksY = new FloatList();
		//redraw bricks and rerun setup
		setup();
	}
	
	// row check function to determine score increase
	public void check(int i) {
		if(BricksY.get(i) == 250) {
			score += 1;
		}
		if(BricksY.get(i) == 190) {
			score += 2;
		}
		if(BricksY.get(i) == 130) {
			score += 3;
		}
		if(BricksY.get(i) == 70) {
			score += 4;
}
		if(BricksY.get(i) == 10) {
			score += 5;
}

	}
	
	public void draw() {
		
		//random background color
		background(rVal, gVal, bVal);
		//brick draw code
	for (int s= 0; s< BricksX.size(); s ++) {
		rect(BricksX.get(s), BricksY.get(s), 100, 50);
	}
	
	//ball wall and paddle collisions and movement code
	//ball move code
	randH += yDir;
	randW += xDir;
	//wall bounce code
	if(randH - 15 < 0 ) {
		yDir *= -1;
	}
	if(randW - 15 < 0 || randW + 15 > width) {
		xDir *= -1;
}	//paddle bounce code
	if(randH + 15 >= height - 20 && randW > mouseX && randW < mouseX + 150) {
		xDir *= 1;
		yDir *= -1;
	}
	//lose code
	if(randH + 15> height + 50 ) {
		text("You lose!", 450, 600);
		//stop ball movement
		xDir = 0;
		yDir =0;
		//set initial time for timer
		if(setTime) {
			savedTime = millis();
			setTime = false;
		}
		//check if 3 seconds has passed
		int passedTime = millis() - savedTime;
		if (passedTime >= 3000) {
			reset();
		}
	}
	
	//brick collision code
	if(randH < 500) {
		for(int i = 0; i< BricksX.size(); i ++) {
			//SIDE BOUNCES
			//bottom bounce
			if(randH <= BricksY.get(i) + 65 && randW >= BricksX.get(i) && randW<= BricksX.get(i) + 100 ) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				yDir *= -1.025;
				
			}
			//left side bounce
			else if(randH >= BricksY.get(i) && randH <= BricksY.get(i) + 65 && randW <= BricksX.get(i) && randW >= BricksX.get(i) - 15) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				xDir *= -1.025;
				
			}
			//right side bounce
			else if(randH >= BricksY.get(i)&& randH <= BricksY.get(i) + 50 && randW>= BricksX.get(i) + 100 && randW <= BricksX.get(i) + 115 ) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				xDir *= -1.025;
				
			}
			//top bounce
			else if (randH <= BricksY.get(i) - 15 && randW > BricksX.get(i) && randW <= BricksX.get(i) + 100) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				yDir *= -1.025;
				
			}
			//CORNER BOUNCES
			//bot left corner bounce
			else if(randW<= BricksX.get(i) && randW >= BricksX.get(i) - 15 && randH <= BricksY.get(i) + 65 && randH >= BricksY.get(i)+50) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				yDir*= -1.025;
				xDir *= -1.025;
				
			}
			//bot right corner bounce
			else if(randW >= BricksX.get(i) + 100 && randW <= BricksX.get(i) + 115 && randH<= BricksY.get(i) + 65 && randH >= BricksY.get(i) + 50) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				yDir*= -1.025;
				xDir *= -1.025;
				
			}
			//top left corner bounce
			else if(randW<= BricksX.get(i) && randW >= BricksX.get(i) - 15 && randH <= BricksY.get(i) && randH >= BricksY.get(i) - 15 ) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				yDir*= -1.025;
				xDir *= -1.025;
				
			}
			// top right corner bounce
			else if (randW >= BricksX.get(i) + 100 && randW <= BricksX.get(i) + 115 && randH <= BricksY.get(i) && randH >= BricksY.get(i) - 15) {
				check(i);
				BricksX.remove(i);
				BricksY.remove(i);
				yDir*= -1.025;
				xDir *= -1.025;
				
			}
		}
	}
	//ball draw call
	Ball.drawBall(this, randW, randH, 30, 30);
	
	

	//paddle draw call
	Paddle.drawPaddle(this, mouseX, height - 20, 150, 20);
	

	//score draw
	text("Score: " + score, 450, 500);
	if(BricksX.size() == 0) {
		text("You win!", 450, 600);
		if(setTime) {
			savedTime = millis();
			setTime = false;
		}
		//check if 3 seconds has passed
		int passedTime = millis() - savedTime;
		if (passedTime >= 3000) {
			reset();
		}                                      
	}
	
	}
	public static void main(String[] args) {
		// main PApplet call
		PApplet.main("breakout");
	}

}
