package app.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import app.util.AppPath;
import app.util.MapRBean;
import app.util.WebUtils;



@Controller
public class RootWeb  {
	
	private static Logger logger = Logger.getLogger(RootWeb.class);
	
	@RequestMapping("/")
	public void buildxx(HttpServletRequest request,HttpServletResponse response){
		
		MapRBean mrb = new MapRBean(WebUtils.build(request));
		
		int width = mrb.getInt("width")/2 + 100;
		int rowSpace = mrb.getInt("row_space");
        //int height = mrb.getInt("height")/5;
        int height = mrb.getInt("font_size");
		//图型缓冲
		BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = image.createGraphics();
        
        //获取文字及类型
        String str =  mrb.getStr("str");
        String font = mrb.getStr("font");
        String color = mrb.getStr("color");
        Integer font_size = mrb.getInt("font_size")/2;
        try {
        	logger.info("str:"+str);
        	logger.info("str:"+font);
        	logger.info(mrb.getMap());
        	
        	logger.info(URLDecoder.decode(str,"gb2312"));
        	logger.info(URLDecoder.decode(str,"utf-8"));
        	logger.info(new String(str.getBytes(),"gb2312"));
        	logger.info(new String(str.getBytes(),"utf-8"));

        	logger.info(new String(str.getBytes("ISO-8859-1"),"GB2312"));
        	logger.info(new String(font.getBytes("ISO-8859-1"),"UTF-8"));
        	
        	//str = URLDecoder.decode(str,"gb2312");
        	//font = URLDecoder.decode(font,"gb2312");
        	
        	str = reBuildStr(new String(str.getBytes("ISO-8859-1"),"GB2312"));
        	font = new String(font.getBytes("ISO-8859-1"),"GB2312");
        	
        	//str +=reBuildStr(new String(str.getBytes("ISO-8859-1"),"UTF-8"));
        	//height3 = height5 * strLen + rowspace3 * (strLen - 1);
        	
        	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        /*boolean isItalic = (mrb.getInt("italic") == 1) ? true : false;
		boolean isBold =  (mrb.getInt("bold") == 1) ? true : false;
		if(isItalic) g2d.setFont(new Font(font, Font.ITALIC, font_size));
		if(isBold) g2d.setFont(new Font(font, Font.BOLD, font_size));*/
        
        try {
        	logger.info(AppPath.APPath()+"fzyh.TTF");
        	Font fonttype = Font.createFont(Font.TRUETYPE_FONT,new File(AppPath.APPath()+"fzyh.TTF"));
			g2d.setFont(fonttype.deriveFont(Font.BOLD,new Float(font_size)));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //g2d.setFont(new Font(font, Font.BOLD, font_size));
        
        g2d.setPaint(new Color(Integer.parseInt(color, 16)));
		g2d.drawString(str,0,font_size);
        
        //String bg_color = (String) mrb.getObj("bg_color");
        //g.setColor(new Color(61, 157, 2));
        
        //g.fillRect(0, 0, width, height);
        //g2d.fillOval(0,0, width, height);
        
        try {
            ImageIO.write(image, "PNG", response.getOutputStream());
        } catch (IOException ex) {
        }
		
	}
	
	    public void drawImage(int width,int height,HttpServletResponse response){
	        
	        
	    }
	    
	    public static void main(String[] args)   
	    {  
	        // TODO Auto-generated method stub  
	        String urlEncodeUserName = new String("192.168.0.56:8080/zprocard/?str=%D0%D5%20%C3%FBsccxx&color=000000&font=%B7%BD%D5%FD%D0%D0%BF%AC%BC%F2%CC%E5&font_size=52&spacing=0&width=158&height=1768&bold=0&italic=0&bg_color=FFFFFF&b_version=NaN&n=0.7721423499751836&row_space=17");  
	        try {  
	            String name=URLDecoder.decode(urlEncodeUserName,"gb2312");  
	            System.out.println(name);  
	        } catch (UnsupportedEncodingException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    } 
	    
	    
	    private String reBuildStr(String str)
        {	
	    	//恢复原字符串
	    	str = str.replace("[]({", "+");
	    	str = str.replace("{[](", "#");
	    	str = str.replace("([{]", "&");
	    	str = str.replace("([]{", "~");
	    	str = str.replace("[({]", ")");
	    	str = str.replace("{([}", "'");
            return str = str.replace("{[(}", "\"");
        }

	
}
