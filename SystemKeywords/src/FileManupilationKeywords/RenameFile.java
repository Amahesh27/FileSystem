package FileManupilationKeywords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;

//jar is named as RenameFile_Util
public class RenameFile implements KeywordLibrary{
	public FunctionResult renameFile(String fileName, String sourceDir, String newFileName) {
		
		if(FileManupilationUtil.isStringNullOrBlank(fileName) || FileManupilationUtil.isStringNullOrBlank(sourceDir) || FileManupilationUtil.isStringNullOrBlank(newFileName)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(File Name;Source Directory;NewFileName) is/are blank").setOutput("").make();
		}
		
		
		Path sourceDirPath = FileManupilationUtil.getPathForFileOrDirectory(sourceDir);//get Path Object for source directory if it exists.
		if(sourceDirPath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument : Source Directory path is invalid").setOutput("").make();
		} 
		
		String sourceDirWithFileName = FileManupilationUtil.getAbsoluteFilePath(sourceDir, fileName); 
		return renameFile(sourceDirWithFileName,newFileName);
	}
	
	public FunctionResult renameFile(String sourceDirWithFileName, String newFileName) {
		 
		if( FileManupilationUtil.isStringNullOrBlank(sourceDirWithFileName) || FileManupilationUtil.isStringNullOrBlank(newFileName)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(File Name;Source Directory;NewFileName) is/are blank").setOutput("").make();
		}
		
		Path sourceDirWithFileNamePath = FileManupilationUtil.getPathForFileOrDirectory(sourceDirWithFileName);//checking source file does exist or not
		if(sourceDirWithFileNamePath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Argument(s):(Source Directory;File Name) path is/are invalid.").setOutput("").make();
		}
		
		
		
		String oldFileName = sourceDirWithFileNamePath.getFileName().toString();
		
		int oldFileIndex = oldFileName.lastIndexOf(".");
		String oldFileNameExtension = oldFileName.substring(oldFileIndex);
		String newFileNameExtension = "";
		
		StringBuilder newFileWithExtension = new StringBuilder();
		int newFileIndex = newFileName.lastIndexOf(".");
		if(newFileIndex == -1) {
			newFileWithExtension.append(newFileName);
			newFileWithExtension.append(oldFileNameExtension);
		} else {
				newFileNameExtension = newFileName.substring(newFileIndex);
				if(oldFileNameExtension.equals(newFileNameExtension)) {
					newFileWithExtension.append(newFileName);
				} else {
					return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("Cannot change file type").setOutput("").make();
				}
		}
		
		try {
			Files.move(sourceDirWithFileNamePath, sourceDirWithFileNamePath.resolveSibling(newFileWithExtension.toString()));//delete file from source directory
		} catch (IOException e) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_INVALID).setMessage("Exception occured while copying file - "+e.getMessage()).setOutput("").make();
		}
		return Result.PASS().setOutput(newFileWithExtension.toString()).setMessage("File has been renamed sucessfully as - "+newFileWithExtension).make();
  }
}
