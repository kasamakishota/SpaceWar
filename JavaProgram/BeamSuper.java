package spacewar;

import java.util.LinkedList;

public class BeamSuper extends Character{
	private PlaneSuper myredplane;
	private PlaneSuper myblueplane;
	private  LinkedList<ItemSuper> myItems = new  LinkedList<ItemSuper>( );

	public BeamSuper(int bx, int by, int bxSize, int bySize) {
		x = bx;
		y = by;
		xSize = bySize;
		ySize = bySize;
	}
	//オブジェクト関連付け
	public void setBluePlane(PlaneSuper blueplane){
		myblueplane = blueplane;
	}
	public void setRedPlane(PlaneSuper redplane){
		myredplane = redplane;
	}
	public void setItems(ItemSuper item){
		myItems.add(item);
	}

	//飛行機からビームの能力を取得
	public void size(PlaneSuper plane){
		xSize = plane.getBeamL();
		ySize = plane.getBeamW();
		if(plane == myblueplane){
			xSpeed = plane.getBeamS();
		}else if(plane == myredplane){
			xSpeed = -plane.getBeamS();
		}
	}
	//ビームの移動＋画面外に出たら消える
	public void move() {
		if (appearFlag == true) {
			x += xSpeed;
			if (x > 800 || x < 0) {
				x = 0;
				appearFlag = false;
			}
		}
	}
	//フラグを立ててビーム位置の決定をする
	public void active(int bx, int by) {
		if (appearFlag == false) {
			x = bx;
			y = by;
			appearFlag = true;
		}
	}

	//ビームが当たった時の処理
	public void hit(PlaneSuper myplane, PlaneSuper yourplane) {
		if (appearFlag == true){
			//敵の飛行機に当たった時
			if(overlapWith(yourplane)){
				yourplane.damage(myplane.getBeamD());
				appearFlag = false;
			//回復アイテムに当たった時
			}else if(overlapWith(myItems.get(0))) {
				if (myItems.get(0).isAppear()) {
					myplane.upHP();
					myplane.getHPup();
					appearFlag = false;
					myItems.get(0).notAppear();
				}
			//ダメージアップアイテムに当たった時
			}else if(overlapWith(myItems.get(1))) {
				if (myItems.get(1).isAppear()) {
					myplane.upBeamD();
					myplane.getBeamDF();
					appearFlag = false;
					myItems.get(1).notAppear();
				}
			//ビーム長強化アイテムに当たった時
			}else if(overlapWith(myItems.get(2))) {
				if (myItems.get(2).isAppear()) {
					myplane.upBeamL();
					myplane.getBeamLF();
					appearFlag = false;
					myItems.get(2).notAppear();
				}
			//ビーム幅強化アイテムに当たった時
			}else if(overlapWith(myItems.get(3))) {
				if (myItems.get(3).isAppear()) {
					myplane.upBeamW();
					myplane.getBeamWF();
					appearFlag = false;
					myItems.get(3).notAppear();
				}
			//ビームスピード強化アイテムに当たった時
			}else if(overlapWith(myItems.get(4))) {
				if (myItems.get(4).isAppear()) {
					myplane.upBeamS();
					myplane.getBeamSF();
					appearFlag = false;
					myItems.get(4).notAppear();
				}
			//命中度アップ（敵機巨大化）アイテムを取得した時
			}else if(overlapWith(myItems.get(5))) {
				if (myItems.get(5).isAppear()) {
					yourplane.upPlaneB();
					myplane.getPlaneBF();
					appearFlag = false;
					myItems.get(5).notAppear();
				}
			//飛行機の移動スピードアップアイテムを取得した時
			}else if(overlapWith(myItems.get(6))) {
				if (myItems.get(6).isAppear()) {
					myplane.upPlaneS();
					myplane.getPlaneSF();
					appearFlag = false;
					myItems.get(6).notAppear();
				}
			//無敵アイテムを取得した時
			}else if(overlapWith(myItems.get(7))) {
				if (myItems.get(7).isAppear()) {
					myplane.invicible();
					myplane.getIF();
					appearFlag = false;
					myItems.get(7).notAppear();
				}
			}
		}
	}
}
