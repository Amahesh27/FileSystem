package FileManupilationKeywords;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;

public class CombinePath_Parameters implements KeywordLibrary{
	public FunctionResult combinePath(String... paths) {
		List<String> validPaths = new ArrayList<>();
		for(String path : paths) {
			if(!FileManupilationUtil.isStringNullOrBlank(path)) {
				validPaths.add(path);
			}
		}
		
		Path combinedPath = null;
		if (validPaths.size() > 0) {
			String first = validPaths.get(0); // first arugment of Paths.get() method
			List<String> moreList = validPaths.subList(1, validPaths.size()); // getting sublist of validPath to exclude validPaths.get(0)
			String[] more = moreList.toArray(new String[moreList.size()]); // converting list to array
			combinedPath = Paths.get(first, more);
			return  Result.PASS().setOutput(true).setMessage(combinedPath.toString()).make();
		} else {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("There are no valid paths to combine").setOutput(false).make();
		}
	}
}
