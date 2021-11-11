package FileManupilationKeywords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;

//jar is named as MoveFile_Util
public class MoveFile implements KeywordLibrary {
	
	public FunctionResult moveFile(String fileName, String sourceDir, String destDir) {
		
		if(FileManupilationUtil.isStringNullOrBlank(fileName) || FileManupilationUtil.isStringNullOrBlank(sourceDir) || FileManupilationUtil.isStringNullOrBlank(destDir)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(File Name;Source Directory;Destination Directory) is/are blank").setOutput("").make();
		}
	
		
		Path sourceDirPath = FileManupilationUtil.getPathForFileOrDirectory(sourceDir);//get Path Object for source directory if it exists.
		if(sourceDirPath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument:Source Directory path is invalid").setOutput("").make();
		} 
		String sourceDirWithFileName = FileManupilationUtil.getAbsoluteFilePath(sourceDir, fileName); 
		return moveFile(sourceDirWithFileName, destDir);
	}
	
	public FunctionResult moveFile(String sourceDirWithFileName, String destDir) {
		
		if( FileManupilationUtil.isStringNullOrBlank(sourceDirWithFileName) || FileManupilationUtil.isStringNullOrBlank(destDir)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(File Name;Source Directory;Destination Directory) is/are blank").setOutput("").make();
		}
				
		Path destDirPath = FileManupilationUtil.getPathForFileOrDirectory(destDir);//get Path Object for destination directory if it exists.
		if(destDirPath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument:Destination Directory path is invalid").setOutput("").make();
		}
		
		Path sourceDirWithFileNamePath = FileManupilationUtil.getPathForFileOrDirectory(sourceDirWithFileName);//checking source file does exist or not
		if(sourceDirWithFileNamePath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(Source Directory;File Name) path is/are invalid").setOutput("").make();
		}
		
		String fileName = sourceDirWithFileNamePath.getFileName().toString();//get file name from source directory
		String destDirWithFileName = FileManupilationUtil.getAbsoluteFilePath(destDir, fileName);//append file name to destination directory

		Path destDirWithFileNamePath = Paths.get(destDirWithFileName);
		
		try {
			Files.move(sourceDirWithFileNamePath, destDirWithFileNamePath, StandardCopyOption.REPLACE_EXISTING);//copy file from source to destination directory
		} catch (IOException e) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("Exception occured while copying file - "+e.getMessage()).setOutput("").make();
		}
		return  Result.PASS().setOutput(destDirPath.toString()).setMessage("File has been moved sucessfully at - "+destDirPath).make();
	}
}
