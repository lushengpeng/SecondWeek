package Server;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;


/**
 * @author lushengpeng
 *
 */
public class PictureHandler implements Runnable{
	private Socket socket;
	private String newName;      
	String tmpName = "tmp";
	
	
	
    /**
     *  构造函数，传递参数 
     * @param  socket 客户端传进来的socket
     * @param  newName 在服务端生成新文件的名称
     */
	public PictureHandler(Socket socket, String newName){
		this.socket = socket;
		this.newName = newName;
	}
	
	
	
    /**
     *  接受客户端传来的图片 
     * @param  socket 客户端连进来的socket
     */
	
	public void receiveFile(Socket socket){
		byte[] inputByte = null;
		int length = 0;
		int sum = 0;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		
		try{
			try{
				dis = new DataInputStream(socket.getInputStream());
				fos = new FileOutputStream(tmpName);
				inputByte = new byte[1024];
				System.out.println(" 开始接收数据");
				while((length = dis.read(inputByte, 0, inputByte.length))>0){
					fos.write(inputByte, 0, length);
					sum += length;
					fos.flush();
				}
				System.out.println("接受数据完成！");
				
			}finally{
				   if(dis != null){
					   dis.close();
				
				   }
				   if(fos != null){
					   fos.close();
				   }
				   if(socket != null ){
					   socket.close();
				   }
				  
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
    /**
     * 彩色转为黑白 
     * @param srcName 源图像地址
     * @param desName 目标图像地址
     */
	
	public void ConvertImage(String srcName, String desName){
		try{
			BufferedImage bufImage = ImageIO.read(new File(srcName));
			ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(colorSpace, null);
            bufImage = op.filter(bufImage, null);
            ImageIO.write(bufImage, "PNG", new File(desName));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void run(){
		try{
			System.out.println("新连接："+socket.getInetAddress()+":"+socket.getPort());
			receiveFile(socket);
			
			ConvertImage(tmpName,newName);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				System.out.println("关闭连接："+socket.getInetAddress()+":"+socket.getPort());
				if(socket != null)
				{
					socket.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
	}
}
