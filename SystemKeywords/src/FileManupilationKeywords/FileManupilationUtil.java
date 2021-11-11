package FileManupilationKeywords;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManupilationUtil {

	// append given file name to given directory
	public static String getAbsoluteFilePath(String directory, String fileName) {
		if (isStringNullOrBlank(directory) || isStringNullOrBlank(fileName)) {
			return null;
		}

		StringBuilder absoluteFilePath = new StringBuilder();
		absoluteFilePath.append(directory);
		absoluteFilePath.append("\\");
		absoluteFilePath.append(fileName);
		return absoluteFilePath.toString();
	}

	// get Path Object for given directory or file location
	public static Path getPathForFileOrDirectory(String dirOrFilePath) {
		Path path = null;
		if (!isStringNullOrBlank(dirOrFilePath)) {
			path = Paths.get(dirOrFilePath);
			if (!Files.exists(path)) {
				path = null;
			}
		}
		return path;
	}

	// check if given input is null,empty or blank
	public static boolean isStringNullOrBlank(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

}
