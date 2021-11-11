package FileManupilationKeywords;

import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;
//jar is named as isLinux
public class IsLinux implements KeywordLibrary {
	private static String OS = System.getProperty("os.name").toLowerCase();
	public FunctionResult checkOs() {
		if (isLinux(OS)) {
            return  Result.PASS().setOutput(true).setMessage("Operating System is Linux").make();
        }
		return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("Operating System is not Linux").setOutput(false).make();
	}
	
	public static boolean isLinux(String osName) {
		 return (OS.indexOf("nix") >= 0
	                || OS.indexOf("nux") >= 0
	                || OS.indexOf("aix") > 0);
    }
}
