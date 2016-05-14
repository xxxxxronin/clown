package com.clown.framework.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 图片验证码
 * @author allen.zhang
 *
 */

public class ValidaterCodeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ValidaterCodeUtil.class);
	
	// 图片宽度
	private int width;
	
	// 图片高度
	private int height;
	
	// 图片字体高度
	private int  fontHeight;
	
	// 图片验证码个数
	private int codeCount;
	
	// 图片干扰线条数
	private int lineSize;
	
	// 验证码字符宽度
	private int codeX;
	
	// 验证码字符中心高度
	private int codeY;
	
	// 验证码字符集
	private String[] codeSequence;
    
	private Random random = new Random();
	
	public ValidaterCodeUtil(int width, int height, int codeCount, int lineSize, String codeSequence){
		
		this.width=width;
		
		this.height=height;
		
		this.codeCount=codeCount;
		
		this.lineSize=lineSize;
		
		this.codeSequence=codeSequence.split(",");
		
		this.codeX = (width-20)/(codeCount+1);
		
		this.codeY = height*3/4;
		
		this.fontHeight = height*7/10;
	}
	
	/**
	 * 获取验证码
	 */
	public Map<String, Object> getValidaterCode(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D gd = bufferedImage.createGraphics();
		
		// 填充一个白色的正方形
		gd.setColor(Color.LIGHT_GRAY);
		gd.fillRect(0, 0, width, height);
		
		// 设置字体
		gd.setFont(new Font("Fixedsys", Font.ITALIC, fontHeight));
		
		// 为正方形加一个边框
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width-1, height-1);
		
		// 描绘干扰线
		drawLines(gd);
		
	    // 获取随机码
		StringBuffer randomCode = new StringBuffer();
		for (int i = 0; i < codeCount; i++) {
			String strCode = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			gd.setColor(getColor());
			gd.drawString(strCode, 10+i*codeX, codeY);
			randomCode.append(strCode);
		}
		
		map.put("image", bufferedImage);
		map.put("code", randomCode.toString());
		
		return map;
	}
	 
	// 获取干扰线
	private void drawLines(Graphics2D g){
		for(int i = 0; i < lineSize; i++){
			int x = random.nextInt(width);     
            int y = random.nextInt(height);     
            int xl = random.nextInt(12);     
            int yl = random.nextInt(12);  
            g.setColor(getColor());
            g.drawLine(x, y, x + xl, y + yl); 
		}
	}
	
	// 获取随机颜色
	private Color getColor(){
		int red=0, green=0, blue=0;
		red = random.nextInt(255);
		green = random.nextInt(255);
		blue = random.nextInt(255);
		return new Color(red, green, blue);
	}

}
