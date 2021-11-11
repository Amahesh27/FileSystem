package FileManupilationKeywords;

import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;
//jar is named as isWindows
public class IsWindow  implements KeywordLibrary{
	private static String OS = System.getProperty("os.name").toLowerCase();
	public FunctionResult checkOs() {
		if (isWindows(OS)) {
            return  Result.PASS().setOutput(true).setMessage("Operating System is Windows").make();
        }
		return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("Operating System is not Windows").setOutput(false).make();
	}
	
	public static boolean isWindows(String osName) {
        return (OS.indexOf("win") >= 0);
    }
}
