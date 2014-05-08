import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

import com.sun.image.*;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//FileImageOutputStream fios = new FileImageOutputStream(new File("result.png"));
			//DataInputStream dis = new DataInputStream(new FileInputStream(new File("input.txt")));
			

			BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
			BufferedImage image = new BufferedImage(2048,2048,BufferedImage.TYPE_INT_ARGB);
			Graphics2D drawableSurface = image.createGraphics();
			byte[] buf = new byte[8];
			float minH = 0,maxH = 0;
			int stopat = 0;
			String line;
			while((line = bufferedReader.readLine()) != null)
			{
				stopat++;
				String currentN = "";
				int xyz = 0;
				int x = 0,y = 0;
				float h = 0;
				
				for(int i = 0; i < line.length();i++)
				{
					if(line.charAt(i) == ' ' || i == line.length()-1)
					{
						if(!currentN.equals(""))
						{
							float value = Float.parseFloat(currentN);
							//System.out.println("? : "+Float.parseFloat(currentN));
							switch(xyz)
							{
							case 0:
								x = (int) (value/6.25); // cherna = 7.5
								//System.out.println("X : "+x);
								break;
							case 1:
								y = (int) (value/6.25);
								//System.out.println("Y : "+y);
								break;
							case 2:
								value+= 40;
								if(value < minH)
									minH = value;
								if(value > maxH)
									maxH = value;
								h = (float) ((value+0)*0.58);
								if(h < 0 || h > 255)
									h = 0;
								//System.out.println("Height : "+Float.parseFloat(currentN)+"max :"+maxH);
								break;
							default:
								System.out.println("ERROR");
								break;
							}
							xyz++;
							currentN = "";
						}
					}
					else
					{
						currentN = currentN + line.charAt(i);
					}
				}
				
				/*if(x == 0)
					System.out.println("Line read : x = "+x+" y = "+y+" height : "+h+" meters.");
				*/
				drawableSurface.setColor(new Color((int)h,(int)h,(int)h));
				//drawableSurface.
				drawableSurface.fillRect(x, y, 1,1);
				//System.out.println(dis.readLine());
			}
			System.out.println("Done, minH = "+minH+" maxH = "+maxH);
			
			ImageIO.write(image, "PNG",new File("result.png"));
		}
		catch (Exception e) {
			System.out.println("Erreur : ");
			e.printStackTrace();
		}
	}
	
}
