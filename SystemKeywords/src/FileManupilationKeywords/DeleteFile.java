package FileManupilationKeywords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;


public class DeleteFile implements KeywordLibrary{

	public FunctionResult deleteFile(String fileName, String sourceDir) {
		
		if(FileManupilationUtil.isStringNullOrBlank(fileName) || FileManupilationUtil.isStringNullOrBlank(sourceDir)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(File Name;Source Directory) is/are blank.").setOutput(false).make();
		}
		
		Path sourceDirPath = FileManupilationUtil.getPathForFileOrDirectory(sourceDir);//get Path Object for source directory if it exists.
		if(sourceDirPath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument:Source Directory is invalid.").setOutput(false).make();
		}
		
		String sourceDirWithFileName = FileManupilationUtil.getAbsoluteFilePath(sourceDir, fileName); 
		return deleteFile(sourceDirWithFileName);
	}
	
	public FunctionResult deleteFile(String sourceDirWithFileName) {
		
		if(FileManupilationUtil.isStringNullOrBlank(sourceDirWithFileName)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(File Name;Source Directory) is/are blank").setOutput(false).make();
		}
		
		Path sourceDirWithFileNamePath = FileManupilationUtil.getPathForFileOrDirectory(sourceDirWithFileName);//checking source file does exist or not
		if(sourceDirWithFileNamePath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(File Name;Source Directory) is/are invalid.").setOutput(false).make();
		}
		try {
			Files.delete(sourceDirWithFileNamePath);//delete file from source directory
		} catch (IOException e) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("Exception occured while copying file - "+e.getMessage()).setOutput(false).make();
		}
		return  Result.PASS().setOutput(true).setMessage("File has been deleted sucessfully").make();
  }
}
