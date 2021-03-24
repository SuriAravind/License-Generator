package com.p3solutions.license.archon_license.archon3_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p3solutions.license.archon_license.LicenseGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Suriyanarayanan K
 * on 08/02/21 1:00 PM.
 */
public class Archon3_1LicenseMain {


    public static void main(String[] args) {
        try {
            String path="/Users/apple/Documents/Archon3.1/archon-licence-new/LicenseFormat.json";
            String content=Files.readString(Paths.get(path));
            Archon3_1InputLicenseBean archon31InputLicenseBean=new ObjectMapper().readValue(content , Archon3_1InputLicenseBean.class);
            LicenseGenerator ac=new LicenseGenerator();
            PrivateKey privateKey=ac.getPrivate(archon31InputLicenseBean.getKeyPath());
            PublicKey publicKey=ac.getPublic("/Users/apple/Documents/Archon3.1/archon-licence-new/KeyPair/publicKey.pk");
            byte[] licenseFileInputString=(archon31InputLicenseBean.getIssueDate() + ";" +    // 0
                    archon31InputLicenseBean.getReleaseDate() + ";" + //1
                    archon31InputLicenseBean.getValidDate() + ";" +    //2
                    archon31InputLicenseBean.getLicensedTo() + ";" +  //3
                    new ObjectMapper().writeValueAsString(archon31InputLicenseBean.getDsCategory()) + ";" +//4
                    new ObjectMapper().writeValueAsString(archon31InputLicenseBean.getRdbmsCategory())//5
            ).getBytes();


            byte[] licenseFileInputEncryptedString=ac.encryptContentString(licenseFileInputString , privateKey);
            FileOutputStream fileOutputStream=new FileOutputStream("archon3license.lic");
            fileOutputStream.write(licenseFileInputEncryptedString);
            fileOutputStream.flush();
            fileOutputStream.close();
            File f=new File("/Users/apple/Documents/Archon3.1/archon-licence-new/archon3license.lic");

            byte[] byteArray=getFileInBytes(f);
            String[] licenseFileInputDecryptedString=ac.decryptContentString(byteArray , publicKey).split(";");
            List<String> dsCategory=Arrays.asList(new ObjectMapper().readValue(licenseFileInputDecryptedString[4] , String[].class));
            List<String> rdbmsCategory=Arrays.asList(new ObjectMapper().readValue(licenseFileInputDecryptedString[5] , String[].class));
            Archon3_1LicenseBean archonLicenseBean31=Archon3_1LicenseBean.builder().issueDate(licenseFileInputDecryptedString[0]).releaseDate(licenseFileInputDecryptedString[1]).validDate(licenseFileInputDecryptedString[2]).licensedTo(licenseFileInputDecryptedString[3]).dsCategory(buildCategory(dsCategory)).rdbmsCategory(buildCategory(rdbmsCategory)).build();
            System.out.println(archonLicenseBean31);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Archon3_1CategoryLicenseBean> buildCategory(List<String> listArray) throws ParseException {
        List<Archon3_1CategoryLicenseBean> archon31CategoryLicenseBeanList=new ArrayList<>();
        for (String str : listArray) {
            archon31CategoryLicenseBeanList.add(Archon3_1CategoryLicenseBean.builder().name(str.split(",")[0]).validateDate(new SimpleDateFormat("yyyy-MM-dd").parse(str.split(",")[1])).build());
        }
        return archon31CategoryLicenseBeanList;
    }

    public static byte[] getFileInBytes(File f) throws IOException {
        FileInputStream fis=new FileInputStream(f);
        byte[] fbytes=new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }


}

