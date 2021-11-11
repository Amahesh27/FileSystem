package FileManupilationKeywords;

import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;
//jar is named as isMac
public class IsMac implements KeywordLibrary{
	private static String OS = System.getProperty("os.name").toLowerCase();
	public FunctionResult checkOs() {
		if (isMac(OS)) {
            return  Result.PASS().setOutput(true).setMessage("Operating System is Mac").make();
        }
		return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("Operating System is not Mac").setOutput(false).make();
	}
	
	public static boolean isMac(String osName) {
        return (OS.indexOf("mac") >= 0);
    }
}
