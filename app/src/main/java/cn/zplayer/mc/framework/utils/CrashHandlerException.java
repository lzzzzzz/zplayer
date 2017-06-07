package cn.zplayer.mc.framework.utils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrashHandlerException implements UncaughtExceptionHandler {

	/** Debug Log tag*/ 
	public static final String TAG = "CrashHandler"; 
	/** 系统默认的UncaughtException处理类 */
	private UncaughtExceptionHandler mDefaultHandler;
	/** CrashHandler实例 */ 
	private static CrashHandlerException INSTANCE;
	
	/** 保证只有一个CrashHandler实例 */ 
	private CrashHandlerException() {}
	
	/** 获取CrashHandler实例 ,单例模式*/ 
	public static CrashHandlerException getInstance() {
		if (INSTANCE == null) { 
			INSTANCE = new CrashHandlerException();
		} 
		return INSTANCE; 
	} 
	
	/** 
	* 初始化,注册Context对象, 
	* 获取系统默认的UncaughtException处理器, 
	* 设置该CrashHandler为程序的默认处理器 
	*/ 
	public void init() { 
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler(); 
		Thread.setDefaultUncaughtExceptionHandler(this); 
	} 
	
	/** 
	* 当UncaughtException发生时会转入该函数来处理 
	*/ 
	@Override 
	public void uncaughtException(Thread thread, Throwable ex) { 
		if (!handleException(ex) && mDefaultHandler != null) {
			//如果用户没有处理则让系统默认的异常处理器来处理 
			mDefaultHandler.uncaughtException(thread, ex); 
		}  
	}
	
	/** 
	* 自定义错误处理,收集错误信息 
	* 发送错误报告等操作均在此完成. 
	* 开发者可以根据自己的情况来自定义异常处理逻辑 
	* @param ex
	* @return true:如果处理了该异常信息;否则返回false 
	*/ 
	private boolean handleException(Throwable ex) { 
		if (ex == null) { 
			return true;
		} 
		//写到文件里面 准备上传服务器
		return true;
	} 
	
	

	
	/**
	 * 去除换行符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
}
