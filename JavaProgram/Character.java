package spacewar;

public class Character {
	protected int x = 0;
	protected int y = 0;
	protected int xSize = 32;
	protected int ySize = 32;
	protected int xSpeed = 10;
	protected int ySpeed = 5;
	protected boolean appearFlag = false;

	//ゲット関数
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getXSize() {
		return xSize;
	}
	public int getYSize() {
		return ySize;
	}

	//接触判定
	public boolean overlapWith(Character character) {
		int cx = character.getX();
		int cy = character.getY();
		int cxSize = character.getXSize();
		int cySize = character.getYSize();
		if (x <= cx + cxSize && cx <= x + xSize) {
			if (y <= cy + cySize && cy <= y + ySize) {
				return true;
			}
		}
		return false;
	}
	//画面の縦方向から出ないようにする
	public void window(){
		if(y <= 20){
			y = 20;
		}else if(y >= 440){
			y = 440;
		}
	}
	//フラグ
	public void appear() {
		appearFlag = true;
	}
	public void notAppear() {
		appearFlag = false;
	}
	public boolean isAppear() {
		return appearFlag;
	}
}

