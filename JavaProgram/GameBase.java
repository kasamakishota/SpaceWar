package spacewar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class GameBase extends TimerTask {

	protected static String TITLE = "GameTest";
	protected static int X_LENGTH = 800;
	protected static int Y_LENGTH = 500;
	protected static int Y_INSETS = 0;

	protected static int WORLD_X_LENGTH=1350;
	protected static int WORLD_Y_LENGTH=400;

	protected static int SCROLL_X_POS = 250;
	protected static int SCROLL_Y_POS = 50;

	protected JFrame frame;
	protected BufferStrategy bs;

	protected Key key;
	protected Mouse mouse;
	protected CG cg;

	// ======================================
	// 初期設定
	// ======================================
	public GameBase() {
		key = new Key();
		mouse = new Mouse();
		cg = new CG();

		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setVisible(true);
		Insets insets = frame.getInsets();
		Y_INSETS = insets.top;
		frame.setSize(X_LENGTH + insets.left + insets.right, Y_LENGTH
				+ insets.top + insets.bottom);
		frame.setLocationRelativeTo(null);
		frame.setIgnoreRepaint(true);

		frame.addKeyListener(key);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);

		frame.createBufferStrategy(2);
		bs = frame.getBufferStrategy();
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Timer t = new Timer();
		t.scheduleAtFixedRate(this, 0, 30);
	}

	// ======================================
	// タイマーによる繰り返し
	// ======================================
	public void run() {
		mouse.clear();

		cg.g = bs.getDrawGraphics();

		try {
			play();
		} catch (Exception e) {
			e.printStackTrace();
		}

		cg.g.dispose();
		bs.show();
	}

	// ======================================
	// ゲーム用オーバーライド関数
	// ======================================
	protected void initialize() throws Exception {
	}

	protected void play() throws Exception {
	}

	// ======================================
	// スクロール用関数
	// ======================================
	public int getScrollX(int x,int px) {
		int shiftx = px - SCROLL_X_POS;
		if (shiftx < 0) {
			shiftx = 0;
		}
		if (shiftx > WORLD_X_LENGTH-X_LENGTH) {
			shiftx = WORLD_X_LENGTH-X_LENGTH;
		}
		return x - shiftx;
	}

	public int getScrollY(int y,int py) {
		int shifty = py - SCROLL_Y_POS;
		if (shifty < 0) {
			shifty = 0;
		}
		if (shifty > WORLD_Y_LENGTH-Y_LENGTH) {
			shifty = WORLD_Y_LENGTH-Y_LENGTH;
		}
		return y - shifty;
	}

	// #######################################################
	// VIEW: KEY クラス
	// #######################################################
	public class Key implements KeyListener {
		private final int KEY_SIZE = 256;
		private boolean pressFlag = false;

		private int code = 0;
		private boolean[] codes = new boolean[KEY_SIZE];

		private void press(KeyEvent ev) {
			code = ev.getKeyCode();
			codes[ev.getKeyCode()] = true;
			if (!pressFlag) {
				pressFlag = true;
			}
		}

		private void release(KeyEvent ev) {
			pressFlag = false;
			codes[ev.getKeyCode()] = false;
			for (int i = 0; i < codes.length; i++) {
				if (codes[i]) {
					code = i;
					return;
				}
			}
			code = 0;
			return;
		}

		public int getCode() {
			return code;
		}

		public boolean isPress(int code) {
			return codes[code];
		}

		// --------------------------------------
		// 以下、リスナーインタフェース用
		// --------------------------------------
		public void keyPressed(KeyEvent ev) {
			press(ev);
		}

		public void keyReleased(KeyEvent ev) {
			release(ev);
		}

		public void keyTyped(KeyEvent ev) {
		}
	}

	// #######################################################
	// VIEW: MOUSE クラス
	// #######################################################
	protected class Mouse implements MouseListener, MouseMotionListener {
		private int rcount = 0;
		private int lcount = 0;
		private int x = 0;
		private int y = 0;
		private int r = 0;
		private int l = 0;

		private void clear() {
			rcount--;
			if (rcount < 0) {
				r = 0;
			}
			lcount--;
			if (lcount < 0) {
				l = 0;
			}
		}

		private void move(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}

		private void click(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				lcount = 1;
				l = e.getClickCount();
				break;
			case MouseEvent.BUTTON3:
				rcount = 1;
				r = e.getClickCount();
				break;
			}
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getRightClick() {
			return r;
		}

		public int getLeftClick() {
			return l;
		}

		// --------------------------------------
		// 以下、リスナーインタフェース用
		// --------------------------------------
		public void mouseDragged(MouseEvent e) {
			mouse.move(e);
		}

		public void mouseMoved(MouseEvent e) {
			mouse.move(e);
		}

		public void mouseClicked(MouseEvent e) {
			mouse.click(e);
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {

		}

	}

	// #######################################################
	// VIEW: CGクラス
	// #######################################################
	public class CG {
		protected Graphics g;

		// ======================================
		// 画像読み込み用関数
		// ======================================
		public BufferedImage loadImage(String fileName) {
			try {
				return ImageIO.read(getClass().getResource(fileName));
			} catch (IOException e) {
				return null;
			}
		}

		// ======================================
		// 描画用ラップ関数
		// ======================================

		// 色
		protected void setColor(Color color) {
			g.setColor(color);
		}

		protected Color getColor() {
			return g.getColor();
		}

		// フォント
		protected void setFont(Font font) {
			g.setFont(font);
		}

		protected Font getFont() {
			return g.getFont();
		}

		// y座標変換
		protected int translate(int y, int yLength) {
			return Y_LENGTH + Y_INSETS - yLength - y;
		}

		// 直線
		protected void drawLine(int x, int y, int xLength, int yLength) {
			g.drawLine(x, translate(y, yLength), xLength, yLength);
		}

		// 曲線
		protected void drawArc(int x, int y, int xLength, int yLength,
				int startAngle, int arcAngle) {
			g.drawArc(x, translate(y, yLength), xLength, yLength, startAngle,
					arcAngle);
		}

		protected void fillArc(int x, int y, int xLength, int yLength,
				int startAngle, int arcAngle) {
			g.fillArc(x, translate(y, yLength), xLength, yLength, startAngle,
					arcAngle);
		}

		// 長方形
		protected void drawRect(int x, int y, int xLength, int yLength) {
			g.drawRect(x, translate(y, yLength), xLength, yLength);
		}

		protected void fillRect(int x, int y, int xLength, int yLength) {
			g.fillRect(x, translate(y, yLength), xLength, yLength);
		}

		protected void clearRect(int x, int y, int xLength, int yLength) {
			g.clearRect(x, translate(y, yLength), xLength, yLength);
		}

		// （角の丸い）長方形
		protected void drawRoundRect(int x, int y, int xLength, int yLength,
				int arcxLength, int arcyLength) {
			g.drawRoundRect(x, translate(y, yLength), xLength, yLength,
					arcxLength, arcyLength);
		}

		protected void fillRoundRect(int x, int y, int xLength, int yLength,
				int arcxLength, int arcyLength) {
			g.fillRoundRect(x, translate(y, yLength), xLength, yLength,
					arcxLength, arcyLength);
		}

		// 円
		protected void drawOval(int x, int y, int xLength, int yLength) {
			g.drawOval(x, translate(y, yLength), xLength, yLength);
		}

		protected void fillOval(int x, int y, int xLength, int yLength) {
			g.fillOval(x, translate(y, yLength), xLength, yLength);
		}

		// 文字
		protected void drawStringLeft(int x, int y, String str) {
			g.drawString(str, x, translate(y, 0));
		}

		protected void drawStringCenter(int x, int y, String str) {
			g.drawString(str, x - getStringWidth(str) / 2, translate(y, 0));
		}

		protected void drawStringRight(int x, int y, String str) {
			g.drawString(str, x - getStringWidth(str), translate(y, 0));
		}

		protected void drawStringCenter(int x, int y, int xLength, String str) {
			g.drawString(str, x + (xLength - getStringWidth(str)) / 2,
					translate(y, 0));
		}

		protected void drawStringRight(int x, int y, int xLength, String str) {
			g.drawString(str, x + (xLength - getStringWidth(str)),
					translate(y, 0));
		}

		protected int getStringWidth(String str) {
			return g.getFontMetrics().stringWidth(str);
		}

		protected int getStringHeight(String str) {
			return g.getFontMetrics().getHeight();
		}

		// 画像
		protected void drawImage(int x, int y, BufferedImage img) {
			g.drawImage(img, x, translate(y, img.getHeight()), frame);
		}

		protected void drawImage(int x, int y, int xLength, int yLength,
				BufferedImage img) {
			g.drawImage(img, x, translate(y, yLength), xLength, yLength, frame);
		}

		// ======================================
		// スクロール描画用ラップ関数
		// ======================================

		// 直線
		protected void drawLine(int x, int y, int xLength, int yLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawLine(sx, translate(sy, yLength), xLength, yLength);
		}

		// 曲線
		protected void drawArc(int x, int y, int xLength, int yLength,
				int startAngle, int arcAngle,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawArc(sx, translate(sy, yLength), xLength, yLength, startAngle,
					arcAngle);
		}

		protected void fillArc(int x, int y, int xLength, int yLength,
				int startAngle, int arcAngle,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.fillArc(sx, translate(sy, yLength), xLength, yLength, startAngle,
					arcAngle);
		}

		// 長方形
		protected void drawRect(int x, int y, int xLength, int yLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawRect(sx, translate(sy, yLength), xLength, yLength);
		}

		protected void fillRect(int x, int y, int xLength, int yLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.fillRect(sx, translate(sy, yLength), xLength, yLength);
		}

		protected void clearRect(int x, int y, int xLength, int yLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.clearRect(sx, translate(sy, yLength), xLength, yLength);
		}

		// （角の丸い）長方形
		protected void drawRoundRect(int x, int y, int xLength, int yLength,
				int arcxLength, int arcyLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawRoundRect(sx, translate(sy, yLength), xLength, yLength,
					arcxLength, arcyLength);
		}

		protected void fillRoundRect(int x, int y, int xLength, int yLength,
				int arcxLength, int arcyLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.fillRoundRect(sx, translate(sy, yLength), xLength, yLength,
					arcxLength, arcyLength);
		}

		// 円
		protected void drawOval(int x, int y, int xLength, int yLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawOval(sx, translate(sy, yLength), xLength, yLength);
		}

		protected void fillOval(int x, int y, int xLength, int yLength,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.fillOval(sx, translate(sy, yLength), xLength, yLength);
		}

		// 文字
		protected void drawStringLeft(int x, int y, String str,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawString(str, sx, translate(sy, 0));
		}

		protected void drawStringCenter(int x, int y, String str,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawString(str, sx - getStringWidth(str) / 2, translate(sy, 0));
		}

		protected void drawStringRight(int x, int y, String str,int px,int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			g.drawString(str, sx - getStringWidth(str), translate(sy, 0));
		}

		// 画像
		protected void drawImage(int x, int y, BufferedImage img, int px, int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			drawImage(sx, sy, img);
		}

		protected void drawImage(int x, int y, int xLength, int yLength,
				BufferedImage img, int px, int py) {
			int sx = getScrollX(x, px);
			int sy = getScrollY(y, py);
			drawImage(sx, sy, xLength, yLength, img);
		}
	}

}
