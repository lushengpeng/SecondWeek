package Server;



/**
 * @author lushengpeng
 *	日志接口定义
 */
public interface LogInterface {
	public void DoLogWithInfo(String logMessage);
	public void DoLogWithWarn(String logMessage);
	public void DoLogWithError(String logMessage);
	public void DoLogWithDebug(String logMessage);

}
