package Server;

import java.awt.SystemColor;
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
public class PictureHandler implements Runnable,GlobalVarAndObjectInterface{
	private Socket socket;
	private String newName;      
	String tmpName = "tmp";
	String logMessage  = null;
	
	
	
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
//				GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo("test");
				
				GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo("开始接受数据。");
				
				while((length = dis.read(inputByte, 0, inputByte.length))>0){
					fos.write(inputByte, 0, length);
					sum += length;
					logMessage = "本次传输："+length +"字节"+";"+ "当前已接受："+sum+"字节。";
					fos.flush();
					GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo(logMessage);
				}
				logMessage = "接受数据完成！   文件大小为："+sum+"字节。";
				GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo(logMessage);
//				System.out.println("接受数据完成！");
				
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
            GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo("图片转换完成！");
            GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo("新文件名称为："+desName);
            
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			File tmpFile = new File(srcName);
			tmpFile.delete();
			
		}
		
	}
	
    /**
     * 获取新文件名称 
     * 格式为： newName＋当前时间
     */
	String GetNewFileName(){
		String currentTime = String.valueOf(System.currentTimeMillis());
		return GlobalVarAndObjectInterface.newName+currentTime;
	}
	
	public void run(){
		try{
			
			logMessage ="当前连接:"+socket.getInetAddress()+":"+socket.getPort();
			GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo(logMessage);
			
			receiveFile(socket);
			
			ConvertImage(tmpName,GetNewFileName());
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				logMessage = "关闭连接："+socket.getInetAddress()+":"+socket.getPort();
				GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo(logMessage);
				GlobalVarAndObjectInterface.doLogProxy.DoLogWithInfo("继续等待连接进入：");
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
