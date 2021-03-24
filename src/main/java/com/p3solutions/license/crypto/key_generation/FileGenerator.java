package com.p3solutions.license.crypto.key_generation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileGenerator {

	public static void writeByteToFile(byte[] bytes, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			os.write(bytes);
			os.close();
		}

		catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

}
