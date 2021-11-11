package FileManupilationKeywords;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.crestech.opkey.plugin.KeywordLibrary;
import com.crestech.opkey.plugin.ResultCodes;
import com.crestech.opkey.plugin.communication.contracts.functionresult.FunctionResult;
import com.crestech.opkey.plugin.communication.contracts.functionresult.Result;

//jar is named as ZipDirectory
public class ZipADirectory implements KeywordLibrary {
	private static final int BUFFER_SIZE = 1024;

	public FunctionResult zip(String sourceDirStr, String destZipFileStr) {

		if (FileManupilationUtil.isStringNullOrBlank(sourceDirStr)
				|| FileManupilationUtil.isStringNullOrBlank(destZipFileStr)) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING)
					.setMessage("Argument(s):(Source Directory;Destination Directory) is/are blank").setOutput("")
					.make();
		}

		Path sourceDirPath = FileManupilationUtil.getPathForFileOrDirectory(sourceDirStr);// get Path Object for source
																							// directory if it exists.
		if (sourceDirPath == null) {
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING)
					.setMessage("Argument(s):(Source Directory;file) not found").setOutput("").make();
		}

		File sourceDir = new File(sourceDirStr);
		FileOutputStream fos;
		ZipOutputStream zos;
		try {
			fos = new FileOutputStream(destZipFileStr);
			zos = new ZipOutputStream(fos);
			for (File file : sourceDir.listFiles()) {
				if (file.isDirectory()) {
					zipDirectory(file, file.getName(), zos);

				} else {
					zipFile(file, zos);
				}
			}
			zos.flush();
			zos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("File not found.").setOutput("")
					.make();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("File not found").setOutput("")
					.make();
		} catch (IOException e) {
			e.printStackTrace();
			return Result.FAIL(ResultCodes.ERROR_ARGUMENT_DATA_MISSING).setMessage("Zip failed.").setOutput("").make();
		}

		return Result.PASS().setOutput(destZipFileStr).setMessage("Zipped successfully at - " + destZipFileStr).make();
	}

	private static void zipDirectory(File folder, String parentFolder, ZipOutputStream zos)
			throws FileNotFoundException, IOException {
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				zipDirectory(file, parentFolder + "/" + file.getName(), zos);
				continue;
			}
			zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			byte[] bytesIn = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = bis.read(bytesIn)) != -1) {
				zos.write(bytesIn, 0, read);
			}
			bis.close();
			zos.closeEntry();
		}
	}

	private static void zipFile(File file, ZipOutputStream zos)
			throws FileNotFoundException, IOException, NullPointerException {
		zos.putNextEntry(new ZipEntry(file.getName()));
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = bis.read(bytesIn)) != -1) {
			zos.write(bytesIn, 0, read);
		}
		bis.close();
		zos.closeEntry();
	}

}
