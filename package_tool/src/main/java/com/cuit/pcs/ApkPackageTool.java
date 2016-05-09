package com.cuit.pcs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import sun.security.tools.JarSigner;


public class ApkPackageTool {

    public static void main(String[] args) {
        System.out.print("start build apk...");

        System.out.println(System.getProperty("user.dir"));
        try {
            Process aaptProcess = Runtime.getRuntime().exec("aapt package -f -M AndroidManifest.xml -S res -A assets -I android.jar -F resources.ap_");
            InputStreamReader inputStreamReader = new InputStreamReader(aaptProcess.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            System.out.println(reader.readLine());
            aaptProcess.waitFor();
            aaptProcess.getInputStream().close();
            inputStreamReader.close();
            reader.close();
            String buildCmd = "app.apk -v -u -z resources.ap_ -f classes.dex";
            buildApk(buildCmd.split(" "));

            String signCmd = "-verbose -keystore debug.keystore -storepass android -signedjar app_signed.apk app.apk androiddebugkey";
            signApk(signCmd.split(" "));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int buildApk(String[] args) {
        return com.android.sdklib.build.ApkBuilderMain.main(args);
    }

    public static void signApk(String[] args) {
        try {
            JarSigner.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}