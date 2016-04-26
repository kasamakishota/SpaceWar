package spacewar;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class GameSuper extends GameBase{

	public static void main(String[] args) {
			new GameSuper();
	}
	//変数定義
	BufferedImage backImage;
	BufferedImage blueplaneImage;
	BufferedImage redplaneImage;
	BufferedImage bluebeamImage;
	BufferedImage redbeamImage;
	BufferedImage potionImage;
	BufferedImage reditemImage;
	BufferedImage blueitemImage;
	BufferedImage yellowitemImage;
	BufferedImage greenitemImage;
	BufferedImage purpleitemImage;
	BufferedImage monoitemImage;
	BufferedImage starImage;

	//文字表示座標変数
	private int bluex = 180;
	private int redx = 600;
	private int y = 220;

	//タイマー変数
	int[] count = new int[16];

	//キャラクター変数定義
	PlaneSuper blueplane;
	PlaneSuper redplane;

	LinkedList<BeamSuper> bluebeams;
	LinkedList<BeamSuper> redbeams;
	LinkedList<ItemSuper> items;

	ItemSuper potion;
	ItemSuper BDItem;
	ItemSuper BLItem;
	ItemSuper BWItem;
	ItemSuper BSItem;
	ItemSuper PBItem;
	ItemSuper PSItem;
	ItemSuper IItem;

	public void initialize(){
		//画像ファイル読み込み
		backImage = cg.loadImage("/img/space.jpg");
		blueplaneImage = cg.loadImage("/img/blueplane.png");
		redplaneImage = cg.loadImage("/img/redplane.png");
		bluebeamImage = cg.loadImage("/img/bluebeam.png");
		redbeamImage = cg.loadImage("/img/redbeam.png");
		potionImage = cg.loadImage("/img/potion.png");
		reditemImage = cg.loadImage("/img/reditem.png");
		blueitemImage = cg.loadImage("/img/blueitem.png");
		yellowitemImage = cg.loadImage("/img/yellowitem.png");
		greenitemImage = cg.loadImage("/img/greenitem.png");
		purpleitemImage = cg.loadImage("/img/purpleitem.png");
		monoitemImage = cg.loadImage("/img/monoitem.png");
		starImage = cg.loadImage("/img/star.png");

		//キャラクターオブジェクト生成
		blueplane = new PlaneSuper(20, 220, 32, 32);
		redplane = new PlaneSuper(750, 220, 32, 32);

		bluebeams = new LinkedList<BeamSuper>();
		redbeams = new LinkedList<BeamSuper>();

		items = new LinkedList<ItemSuper>();
		potion = new ItemSuper(250, 0, 24, 24);
		BDItem = new ItemSuper(250, 0, 24, 24);
		BLItem = new ItemSuper(250, 0, 24, 24);
		BWItem = new ItemSuper(250, 0, 24, 24);
		BSItem = new ItemSuper(250, 0, 24, 24);
		PBItem = new ItemSuper(250, 0, 24, 24);
		PSItem = new ItemSuper(250, 0, 24, 24);
		IItem = new ItemSuper(250, 0, 24, 24);
		items.add(potion);
		items.add(BDItem);
		items.add(BLItem);
		items.add(BWItem);
		items.add(BSItem);
		items.add(PBItem);
		items.add(PSItem);
		items.add(IItem);

		//ビーム生成
		for (int i=0; i<blueplane.getBeamQ(); i++) {
			BeamSuper beam = new BeamSuper(0, 0, 0, 0);
			bluebeams.add(beam);
		}
		for (int i=0; i<redplane.getBeamQ(); i++) {
			BeamSuper beam = new BeamSuper(0, 0, 0, 0);
			redbeams.add(beam);
		}
		// Model オブジェクト関連付け
		for(BeamSuper bluebeam:bluebeams){
			setBlueChar(bluebeam);
		}
		for(BeamSuper redbeam:redbeams){
			setRedChar(redbeam);
		}
	}

	public void play(){
		///////////////キー入力/////////////////
		//青い飛行機
		if (key.isPress(KeyEvent.VK_W)) {
			blueplane.raise();
		}
		if (key.isPress(KeyEvent.VK_A)) {
			blueplane.descent();
		}
		if (key.isPress(KeyEvent.VK_Q)) {
			blueplane.launch(2);
		}else{
			blueplane.notLaunch();
		}
		//赤い飛行機
		if (key.isPress(KeyEvent.VK_P)) {
			redplane.raise();
		}
		if (key.isPress(KeyEvent.VK_L)) {
			redplane.descent();
		}
		if (key.isPress(KeyEvent.VK_O)) {
			redplane.launch(1);
		}else{
			redplane.notLaunch();
		}

		//キャラクターの動作
		blueplane.window();
		redplane.window();

		for(BeamSuper bluebeam:bluebeams){
			bluebeam.move();
			bluebeam.size(blueplane);
			bluebeam.hit(blueplane, redplane);
		}

		for(BeamSuper redbeam:redbeams){
			redbeam.move();
			redbeam.size(redplane);
			redbeam.hit(redplane, blueplane);
		}

		for(ItemSuper item:items){
			item.window();
			item.move();
		}

		//View画面表示
		drawView();
	}

	//表示のまとめ
	private void drawView() {
		drawBack();
		drawChar(blueplane, blueplaneImage);
		drawChar(redplane, redplaneImage);

		for(BeamSuper bluebeam:bluebeams){
			drawChar(bluebeam, bluebeamImage);
		}
		for(BeamSuper redbeam:redbeams){
			drawChar(redbeam, redbeamImage);
		}
		drawChar(potion, potionImage);
		drawChar(BDItem, reditemImage);
		drawChar(BLItem, blueitemImage);
		drawChar(BWItem, yellowitemImage);
		drawChar(PBItem, greenitemImage);
		drawChar(BSItem, monoitemImage);
		drawChar(PSItem, purpleitemImage);
		drawChar(IItem, starImage);
		drawHP();
		drawCommand();
		drawItemEffect();
		drawGameFinish();
	}
	//背景表示
	private void drawBack() {
		cg.drawImage(0, 0, 810, 510, backImage);
	}
	//キャラクターの表示
	private void drawChar(Character character, BufferedImage characterImage){
		if(character.isAppear() == true){
			int x = character.getX();
			int y = character.getY();
			int xSize = character.getXSize();
			int ySize = character.getYSize();
			cg.drawImage(x, y, xSize, ySize, characterImage);
		}
	}
	//体力ゲージ表示
	private void drawHP(){
		cg.setColor(new Color(0,0,255));						//HPカラーバー（青）
		cg.fillRect(10, 470, blueplane.getHP(), 20);
		cg.setColor(new Color(255,0,0));						//HPカラーバー（赤）
		cg.fillRect(795-redplane.getHP(), 470, redplane.getHP(), 20);
	}
	//操作方法表示
	private void drawCommand(){
		cg.setFont(new Font("MS明朝", Font.PLAIN, 20));
		cg.setColor(Color.BLUE);
		cg.drawStringCenter(180, 3, "上：W   下：A   発射：Q");
		cg.setColor(Color.RED);
		cg.drawStringCenter(620, 3, "上：P   下：L   発射：O");
	}
	//アイテム取得時の文字表示
	private void drawItemEffect(){
		cg.setFont(new Font("MS明朝", Font.PLAIN, 30));
		if(redplane.isGetHPup() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "HPアップ!");
			count[0]++;
			if(count[0] >= 30){
				redplane.notGetHPup();
				count[0] = 0;
			}
		}
		if(blueplane.isGetHPup() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "HPアップ!");
			count[1]++;
			if(count[1] >= 30){
				blueplane.notGetHPup();
				count[1] = 0;
			}
		}if(redplane.isGetBeamDF() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "ダメージアップ!");
			count[2]++;
			if(count[2] >= 30){
				redplane.notGetBeamDF();
				count[2] = 0;
			}
		}if(blueplane.isGetBeamDF() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "ダメージアップ!");
			count[3]++;
			if(count[3] >= 30){
				blueplane.notGetBeamDF();
				count[3] = 0;
			}
		}if(redplane.isGetBeamLF() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "弾長アップ!");
			count[4]++;
			if(count[4] >= 30){
				redplane.notGetBeamLF();
				count[4] = 0;
			}
		}if(blueplane.isGetBeamLF() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "弾長アップ!!");
			count[5]++;
			if(count[5] >= 30){
				blueplane.notGetBeamLF();
				count[5] = 0;
			}
		}if(redplane.isGetBeamWF() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "弾幅アップ!");
			count[6]++;
			if(count[6] >= 30){
				redplane.notGetBeamWF();
				count[6] = 0;
			}
		}if(blueplane.isGetBeamWF() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "弾幅アップ!");
			count[7]++;
			if(count[7] >= 30){
				blueplane.notGetBeamWF();
				count[7] = 0;
			}
		}if(redplane.isGetBeamSF() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "弾速アップ!");
			count[8]++;
			if(count[8] >= 30){
				redplane.notGetBeamSF();
				count[8] = 0;
			}
		}if(blueplane.isGetBeamSF() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "弾速アップ!");
			count[9]++;
			if(count[9] >= 30){
				blueplane.notGetBeamSF();
				count[9] = 0;
			}
		}if(redplane.isGetPlaneBF() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "命中度アップ!");
			count[10]++;
			if(count[10] >= 30){
				redplane.notGetPlaneBF();
				count[10] = 0;
			}
		}if(blueplane.isGetPlaneBF() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "命中度アップ!");
			count[11]++;
			if(count[11] >= 30){
				blueplane.notGetPlaneBF();
				count[11] = 0;
			}
		}if(redplane.isGetPlaneSF() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "機体速度アップ!");
			count[12]++;
			if(count[12] >= 30){
				redplane.notGetPlaneSF();
				count[12] = 0;
			}
		}if(blueplane.isGetPlaneSF() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "機体速度アップ!!");
			count[13]++;
			if(count[13] >= 30){
				blueplane.notGetPlaneSF();
				count[13] = 0;
			}
		}if(redplane.isGetIF() == true){
			cg.setColor(Color.RED);
			cg.drawStringCenter(redx, y, "3回無敵!");
			count[14]++;
			if(count[14] >= 30){
				redplane.notGetIF();
				count[14] = 0;
			}
		}if(blueplane.isGetIF() == true){
			cg.setColor(Color.BLUE);
			cg.drawStringCenter(bluex, y, "3回無敵!");
			count[15]++;
			if(count[15] >= 30){
				blueplane.notGetIF();
				count[15] = 0;
			}
		}
	}
	//ゲーム終了時の表示
	private void drawGameFinish(){
		if(redplane.getHP() <= 0) {
			// Game Clear表示
			cg.setColor(Color.BLUE);
			cg.setFont(new Font("MS明朝", Font.PLAIN, 100));
			cg.drawStringCenter(bluex, y, "WIN!!");
			// Game Over表示
			cg.setColor(Color.RED);
			cg.setFont(new Font("MS明朝", Font.PLAIN, 100));
			cg.drawStringCenter(redx, y, "LOSE");
		}else if (blueplane.getHP() <= 0){
			// Game Clear表示
			cg.setColor(Color.RED);
			cg.setFont(new Font("MS明朝", Font.PLAIN, 100));
			cg.drawStringCenter(redx, y, "WIN!!");
			// Game Over表示
			cg.setColor(Color.BLUE);
			cg.setFont(new Font("MS明朝", Font.PLAIN, 100));
			cg.drawStringCenter(bluex, y, "LOSE");
		}
	}
	///////////オブジェクトの関連付け////////////
	//青い飛行機
	private void setBlueChar(BeamSuper bluebeam) {
		blueplane.setblueBeam(bluebeam);
		bluebeam.setRedPlane(redplane);
		bluebeam.setBluePlane(blueplane);
		for(ItemSuper item:items){
			bluebeam.setItems(item);
		}
	}
	//赤い飛行機
	private void setRedChar(BeamSuper redbeam) {
		redplane.setredBeam(redbeam);
		redbeam.setRedPlane(redplane);
		redbeam.setBluePlane(blueplane);
		for(ItemSuper item:items){
			redbeam.setItems(item);
		}
	}
}