package com.p3solutions.license.archon_license.archon3_0;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p3solutions.license.archon_license.LicenseGenerator;
import com.p3solutions.license.crypto.key_generation.GenerateKeys;
import org.apache.commons.io.IOUtils;

/**
 * Archon 3 license generator
 * 
 * @author seelan
 *
 */
public class ArchonLicenseMain {

	public static void callLicenseGenerator() {
		String path, st, keyPath = "";
		Scanner input = new Scanner(System.in);
		StringBuffer sb = new StringBuffer();
		ArchonLicense license = new ArchonLicense();
		System.out.println("Enter license generator input file path : ");
		path = input.next();
		try {

			Map<String, List<String>> dbValue =new HashMap<>();
			Map<String,List<String>> datasourceValue=new LinkedHashMap<>();
			if (!path.endsWith(".txt")) {
				System.out.println("License generation failed : File is not of .txt format ");
				System.exit(0);
			}
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			int date = 0, db = 0, ws = 0, key = 0, wse=0, dbs=0, lc=0;

			while ((st = br.readLine()) != null) {
				st.strip();
				if (st.contains("DATE"))
					date += 1;

				else if (st.contains("Issue_Date") && date >= 1) {

					date++;
					license.setIssueDate(st.split("=")[1].strip());

				} else if (st.contains("Release_Date") && date >= 1) {
					date += 1;
					license.setReleaseDate(st.split("=")[1].strip());

				} else if (st.contains("Valid_Date") && date >= 1) {
					date += 1;
					license.setValidDate(st.split("=")[1].strip());

				}
				if(st.contains("LICENSED_TO"))
				{
					license.setLicensedTo(st.split("=")[1].strip());
					lc=1;
				}
				if (st.contains("DATASOURCE_CATEGORY"))
				{
					ws += 1;
					wse=1;
				}
				if (st.contains("_WORKSPACE=ENABLED"))  {
					if(ws >= 1 && wse==1)
					{	
					ws += 1;
					addValueToMap(datasourceValue, st.split("_WORKSPACE")[0].strip(),
							st.split("_WORKSPACE")[0].strip() + "_WORKSPACE");
					}
					else {
						System.out.println("License generation failed : File info is incorrect");
						System.exit(0);
					
					}
						
				}
				if (st.contains("DATABASE_SERVER"))
				{	db += 1;  
					dbs=1;
				
				}
				if (st.contains("_DB=ENABLED") ) {
					if( db >= 1 && dbs==1)
					{db += 1;
					addValueToMap(dbValue, st.split("_DB")[0].strip(), 
							st.split("_DB")[0].strip() + "_DATABASE");}
					else {
						System.out.println("License generation failed : File info is incorrect");
						System.exit(0);
					
					}
					
				}

				if (st.contains("PRIVATE_KEY"))
					key += 1;
				else if (st.contains("PATH") && key == 1) {
					key += 1;
					keyPath = st.split("=")[1].strip();
				}
			}
			br.close();
			if (date != 4 || key != 2  || lc !=1) {
				System.out.println("License generation failed : File info is incorrect");
				System.exit(0);
			}
			license.setDbValue(dbValue);
			license.setDatasourceValue(datasourceValue);
			String licenString = new ObjectMapper().writeValueAsString(license);
			System.out.println("licenseText  " + licenString);

			LicenseGenerator ac = new LicenseGenerator();
			PrivateKey privateKey = ac.getPrivate(keyPath);

			File f = new File("archon3license.lic");
			ac.encryptFile(licenString.getBytes(), f, privateKey);
			System.out.println("License file generated successfully at location : " + f.getAbsolutePath());

			LicenseGenerator ac1 = new LicenseGenerator();
			byte[] licenseArray =IOUtils.toByteArray(new FileInputStream(f.getAbsolutePath()));
			PublicKey publicKey=ac1.getPublic("/Users/apple/Documents/Archon3.1/archon-licence-new/KeyPair/publicKey.pk");
			System.out.println(ac1.decryptFileAsString(licenseArray, new File("output.txt"), publicKey))
			;
		} catch (Exception e) {
			System.out.println("License generation failed : " + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			input.close();
			sb = null;

		}

	}


	private static void getDates(Scanner input, ArchonLicense license) {
		Scanner sc = new Scanner(System.in);
		System.out.print("enter 2 numbers: ");
		String str = sc.nextLine();
		str = str.trim();
		int x1 = Integer.parseInt(str.substring(0, str.indexOf(' ')));
		int y1 = Integer.parseInt(str.substring(str.indexOf(' ')));
		String str2 = sc.nextLine();
		str2 = str2.trim();
		String dir1 = str2.substring(0, str2.indexOf(' '));
		int xd = Integer.parseInt(str2.substring(2, str.indexOf(',')));
		String dir2 = str2.substring(str2.indexOf(',') + 1, ' ');
		int yd = Integer.parseInt(str2.substring(str.indexOf(',') + 2));
		System.out.print(x1 + " " + y1 + " " + dir1 + " " + xd + " " + dir2 + " " + yd);

	}

	public static void main(String[] args) throws Exception {

		System.out.println("welcome to Archon 3.0 license generator");
		System.out.println("Please select your options:\n");
		System.out.println("1. Generate private/public keys\n" + "2. Generate license\n");
		Scanner mainoption = new Scanner(System.in);
		String option = mainoption.next();
		switch (option) {
		case "1":
			System.out.println("Generating private/public keys...");
			generateKeys();
			break;
		case "2":
			System.out.println("Generating license file for archon 3.0");
			callLicenseGenerator();
			break;
		default:
			System.out.println("Invalid selection");
			break;
		}
		mainoption.close();

	}

	public static void generateKeys() {
		GenerateKeys gk;
		try {
			gk = new GenerateKeys(4096);
			gk.createKeys();
			gk.writeToFile("KeyPair/publicKey.pk", gk.getPublicKey().getEncoded());
			gk.writeToFile("KeyPair/privateKey.pk", gk.getPrivateKey().getEncoded());
			System.out.println("Key files generated successfully");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void addValueToMap(Map<String, List<String>> value, String key, String valuePair) {
		List<String> list = new ArrayList<String>();
		list.add(valuePair);
		value.put(key, list);
	}
}
