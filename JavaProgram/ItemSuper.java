package spacewar;

public class ItemSuper extends Character{
	private int num = 0;
	private int patern =0;
	private double degree = 0;
	private double PI = 3.14159;
	public ItemSuper(int px, int py, int pxSize, int pySize){
		x = px;
		y = py;
		xSize = pxSize;
		ySize = pySize;
		xSpeed = 10;
		ySpeed = 10;
	}
	//乱数によるアイテム生成と動きの定義
	public void move(){
		/////////アイテム生成部////////////
		if(isAppear() == false){
			num = (int)(Math.random() * 2000);
			if(num == 1 || num == 2 || num == 3 || num == 4){
				patern = num ;
				x = (int)(500 * Math.random()) + 150;
				y = (int)(300 * Math.random()) + 150;
				appear();
			}
		}
		///////////アイテム動作部////////////
		//動き方１
		if(appearFlag == true && patern == 1){
			xSpeed = (int)(8 * Math.sin(degree));
			ySpeed = (int)(8 * Math.sin(degree-(PI/2)));
			degree += PI / 32;
			x += xSpeed;
			y += ySpeed;
		//動き方２
		}else if(appearFlag == true && patern == 2){
			x += xSpeed;
			y += ySpeed;
		//動き方３
		}else if(appearFlag == true && patern == 3){
			ySpeed = (int)(5 * Math.sin(degree));
			degree += PI / 32;
			y += ySpeed;
		//動き方４
		}else if(appearFlag == true && patern == 4){
		}
		//画面端で跳ね返る
		if(x >= 780 || x <= 0){
			xSpeed = -xSpeed;
		}
		if(y >= 440 || y <= 20){
			ySpeed = -ySpeed;
		}
	}
}

