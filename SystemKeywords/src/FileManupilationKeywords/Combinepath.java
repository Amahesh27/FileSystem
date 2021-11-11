package FileManupilationKeywords;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;

//jar is named as CombinePath_Util_WithoutArg
public class Combinepath implements KeywordLibrary{
	public static FunctionResult combinePath(String path1,String path2,String path3,String path4,String path5,String path6,String path7,String path8,String path9,String path10){
		
		if(FileManupilationUtil.isStringNullOrBlank(path1) || FileManupilationUtil.isStringNullOrBlank(path2)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(Path1;Path2) is/are blank").setOutput("").make();
		}
		
		List<String> validPaths = new ArrayList<>();
		String paths[]=new String[10];
		paths[0]=path1;
		paths[1]=path2;
		paths[2]=path3;
		paths[3]=path4;
		paths[4]=path5;
		paths[5]=path6;
		paths[6]=path7;
		paths[7]=path8;
		paths[8]=path9;
		paths[9]=path10;
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
			return  Result.PASS().setOutput(combinedPath.toString()).setMessage("Path combined successfully - "+combinedPath.toString()).make();
		} else {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("There are no valid paths to combine").setOutput("").make();
		}
	}
}
