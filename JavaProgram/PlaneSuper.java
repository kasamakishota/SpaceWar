package spacewar;

import java.util.LinkedList;

public class PlaneSuper extends Character{
	private int yy = 13;
	private int HP = 350;
	private int beamD = 10;
	private int beamL = 20;
	private int beamW = 5;
	private int beamQ = 6;
	private int beamS = 10;
	private int invisibleCount = 0;
	private boolean launchedFlag = false;
	private boolean HPFlag = false;
	private boolean BDFlag = false;
	private boolean BLFlag = false;
	private boolean BWFlag = false;
	private boolean BSFlag = false;
	private boolean BQFlag = false;
	private boolean PSFlag = false;
	private boolean IFlag = false;
	private  LinkedList<BeamSuper> myBlueBeams = new  LinkedList<BeamSuper>( );
	private  LinkedList<BeamSuper> myRedBeams = new  LinkedList<BeamSuper>( );

	public PlaneSuper(int px, int py, int pxSize, int pySize) {
		x = px;
		y = py;
		xSize = pxSize;
		ySize = pySize;
		appear();
	}
	//オブジェクト関連付け
	public void setredBeam(BeamSuper redbeam){
		myRedBeams.add(redbeam);
	}
	public void setblueBeam(BeamSuper bluebeam){
		myBlueBeams.add(bluebeam);
	}
	//////////////////////////
	//		基本動作		//
	//////////////////////////
	//上昇
	public void raise(){
		y += ySpeed;
	}
	//下降
	public void descent(){
		y -= ySpeed;
	}
	//ダメージ（受ける側）
	public void damage(int Damage){
		if(invisibleCount == 0){
			HP -= Damage;
		}else{
			invisibleCount -= 1;
		}
	}
	//ビーム発射
	public void launch(int whichPlane){
		for (int i =0;i<beamQ;i++) {
			BeamSuper beam = new BeamSuper(0, 0, 0, 0);
			if(whichPlane == 1){
				beam = myRedBeams.get(i);
			}else{
				beam = myBlueBeams.get(i);
			}
			if (beam.isAppear() == false && launchedFlag == false) {
					beam.active(x, y + yy);
					Launch();
					break;
			}
		}

	}
	//ゲット関数
	public int getYSpeed() {
		return ySpeed;
	}
	public int getHP() {
		return HP;
	}
	public int getBeamL() {
		return beamL;
	}
	public int getBeamW() {
		return beamW;
	}
	public int getBeamQ() {
		return beamQ;
	}
	public int getBeamD() {
		return beamD;
	}
	public int getBeamS() {
		return beamS;
	}
	public int getInvisibleCount() {
		return invisibleCount;
	}

	//アイテム効果関数
	public void upHP() {
		HP += 100;
		if(HP > 350){
			HP = 350;
		}
	}
	public void upBeamD() {
		beamD += 10;
	}
	public void upBeamL() {
		beamL += 10;
	}
	public void upBeamW() {
		beamW += 5;
		yy -= 2.5;
	}
	public void upBeamS() {
		if(beamS > 0){
			beamS += 3;
		}else{
			beamS -= 3;
		}
	}
	public void upPlaneB() {
		xSize += 10;
		ySize += 10;
		yy += 5;
	}
	public void upPlaneS() {
		ySpeed += 5;
	}
	public void invicible(){
		invisibleCount += 3;
	}

	//////////////////////////////
	//			フラグ			//
	//////////////////////////////

	//発射フラグ
	public void Launch() {
		launchedFlag = true;
	}
	public void notLaunch() {
		launchedFlag = false;
	}
	public boolean isLaunch() {
		return launchedFlag;
	}

	//アイテムフラグ
	//HPアップ
	public void getHPup(){
		HPFlag = true;
	}
	public void notGetHPup(){
		HPFlag = false;
	}
	public boolean isGetHPup(){
		return HPFlag;
	}
	//ダメージアップ
	public void getBeamDF(){
		BDFlag = true;
	}
	public void notGetBeamDF(){
		BDFlag = false;
	}
	public boolean isGetBeamDF(){
		return BDFlag;
	}
	//弾長アップ
	public void getBeamLF(){
		BLFlag = true;
	}
	public void notGetBeamLF(){
		BLFlag = false;
	}
	public boolean isGetBeamLF(){
		return BLFlag;
	}
	//弾幅アップ
	public void getBeamWF(){
		BWFlag = true;
	}
	public void notGetBeamWF(){
		BWFlag = false;
	}
	public boolean isGetBeamWF(){
		return BWFlag;
	}
	//弾速アップ
	public void getBeamSF(){
		BSFlag = true;
	}
	public void notGetBeamSF(){
		BSFlag = false;
	}
	public boolean isGetBeamSF(){
		return BSFlag;
	}
	//命中度アップ
	public void getPlaneBF(){
		BQFlag = true;
	}
	public void notGetPlaneBF(){
		BQFlag = false;
	}
	public boolean isGetPlaneBF(){
		return BQFlag;
	}
	//飛行機速度アップ
	public void getPlaneSF(){
		PSFlag = true;
	}
	public void notGetPlaneSF(){
		PSFlag = false;
	}
	public boolean isGetPlaneSF(){
		return PSFlag;
	}
	//無敵アイテム
	public void getIF(){
		IFlag = true;
	}
	public void notGetIF(){
		IFlag = false;
	}
	public boolean isGetIF(){
		return IFlag;
	}
}
