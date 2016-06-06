package Server;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class PictureHandler implements Runnable{
	private Socket socket;
	public PictureHandler(Socket socket){
		this.socket = socket;
	}
	
	public void receiveFile(Socket socket){
		byte[] inputByte = null;
		int length = 0;
		int sum =0;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		
		try{
			try{
				dis = new DataInputStream(socket.getInputStream());
				fos = new FileOutputStream("3.png");
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
	
	
	
	public void run(){
		try{
			System.out.println("新连接："+socket.getInetAddress()+":"+socket.getPort());
			receiveFile(socket);
			
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
