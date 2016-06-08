package Server;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.SimpleAttributeSet;


/**
 * @author lushengpeng
 * 实现LogInterface接口。分别输出各种级别日志，输出到控制台；
 */
public class  LogOutput implements LogInterface{
	
	
	public void DoLogWithInfo(String logMessage){
		System.out.println("[Info:]"+GetCurrentTime()+logMessage);
	}
	
	
	public void DoLogWithWarn(String logMessage){
		System.out.println("[Warn:]"+GetCurrentTime()+logMessage);
	}
	
	
	public void DoLogWithError(String logMessage){
		System.out.println("[Error:]"+GetCurrentTime()+logMessage);
	}
	
	public void DoLogWithDebug(String logMessage){
		System.out.println("[Debug:]"+GetCurrentTime()+logMessage);
	}
	
	
	
	private  String GetCurrentTime(){
		Date time = new Date();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		return "["+dateFormate.format(time)+"]";
		
	}
	

}
