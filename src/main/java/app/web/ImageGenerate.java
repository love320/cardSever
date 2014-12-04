package app.web;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

public class ImageGenerate {
	private static final int BOARD_HEIGHT=51; //  牌子高度
	private static final int BOARD_SPACE=10; // 文字两边留白宽度
	private static final int FULL_BOARD_HEIGHT=95; // 总高度
	private static final int BORDER_WIDTH=3; // 边框宽度
	private static final int TEXT_HEIGHT=30; // 字体大小
	private static final int BOARD_ARC_WIDTH=12; // 弧的宽度
	private static final Font TEXT_FONT= new Font("黑体",Font.BOLD,TEXT_HEIGHT);

	/**
	 * text : 长度 2-6
	 * font : 
	 ＊ imagefile : 输出文件
	 */
	public static void generate(String text,Font font,File imagefile) throws IOException {
		
		// 3*2 + 10*2 + 30*6 = 206 + 字间距 < 300 ?
		BufferedImage image = new BufferedImage(300,FULL_BOARD_HEIGHT,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		int text_width = g2d.getFontMetrics(font).stringWidth(text);
		// 牌子宽度 
		int width = BORDER_WIDTH * 2 + BOARD_SPACE*2 + text_width;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setFont(font);
		g2d.setPaint(Color.WHITE);
		g2d.fill(new Rectangle2D.Double(0,0,width,FULL_BOARD_HEIGHT));

		RoundRectangle2D.Double outer = new RoundRectangle2D.Double(0,0,width,BOARD_HEIGHT+BORDER_WIDTH*2,BOARD_ARC_WIDTH,BOARD_ARC_WIDTH);
		Color board_color = new Color(110,110,110);
		g2d.setPaint(board_color);
		g2d.fill(outer);

		RoundRectangle2D.Double board = new RoundRectangle2D.Double(BORDER_WIDTH,BORDER_WIDTH,width-BORDER_WIDTH*2,BOARD_HEIGHT,BOARD_ARC_WIDTH,BOARD_ARC_WIDTH);
		g2d.setPaint(new Color(206,6,0));
		g2d.fill(board);
		
		g2d.setPaint(Color.WHITE);
		g2d.drawString(text,11,40);

		Ellipse2D.Double shadow = new Ellipse2D.Double(width/2-8,88,16,7);
		g2d.setPaint(Color.BLACK);
		g2d.fill(shadow);

		// 柱子 宽度 8 
		GeneralPath column = new GeneralPath();
		column.moveTo(width/2-4,57);
		column.lineTo(width/2+4,57);
		column.lineTo(width/2+4,93);
		column.curveTo(width/2+4,93,width/2,95,width/2-4,93);
		column.lineTo(width/2-4,57);
		column.closePath();
		//Rectangle2D.Double column = new Rectangle2D.Double(width/2 -4, 57,8,36);
		float[] fractions = {0,0.3f,1};
		Color[] colors = {new Color(150,150,150),new Color(221,221,221),new Color(159,159,159)};
		g2d.setPaint(new LinearGradientPaint(width/2-4,57,width/2+4,57,fractions,colors));
		g2d.fill(column);

		javax.imageio.ImageIO.write(image.getSubimage(0,0,width,FULL_BOARD_HEIGHT),"PNG",imagefile);
	}

	public static void main(final String[] args) throws Exception {
		generate("我喜欢麦当劳",TEXT_FONT,new File("/tmp/image.png"));
		Font droid = Font.createFont(Font.TRUETYPE_FONT,new File("/usr/share/fonts/truetype/freefont/FreeMonoBold.ttf"));
		generate("我喜欢麦当劳",droid.deriveFont(30f),new File("/tmp/image2.png"));
		Font lihei = Font.createFont(Font.TRUETYPE_FONT,new File("/usr/share/fonts/truetype/freefont/FreeMonoBold.ttf"));
		generate("我喜欢麦当劳",lihei.deriveFont(30f),new File("/tmp/image3.png"));
	}
	
	
}