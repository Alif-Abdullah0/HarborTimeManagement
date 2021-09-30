package site.java;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class BlockAndUnblock {

    public static void block(String hostsFile, String blockedUrls) throws IOException {
        Files.writeString(Paths.get(hostsFile), 
                    "\n# Harbor Block Start Here\n" + Files.readString(Paths.get(blockedUrls)) + "# Harbor Block Ends Here\n",
                    StandardOpenOption.APPEND);
    }

    public static void block() throws IOException {
        block(getHostsFile(),getBlockedUrls());
    }

    public static void blockSite(String hostsFile, String blockedUrls, String url, boolean inSession) throws IOException {
        if (inSession) unBlock(hostsFile);
        Files.writeString(Paths.get(blockedUrls), "0.0.0.0 " + url.substring(url.indexOf("//") + 2,url.length()-1) +"\n", StandardOpenOption.APPEND);
        Files.writeString(Paths.get(blockedUrls), "::0 " + url.substring(url.indexOf("//") + 2,url.length()-1) +"\n", StandardOpenOption.APPEND);
        if (inSession) block(hostsFile, blockedUrls);
    }

    public static void unBlock(String hostsFile) throws IOException {
        // removes blockedUrls string from String file. 
        String file = Files.readString(Paths.get(hostsFile));
        int start = file.indexOf("# Harbor Block Start Here");
        if (start != -1) {
            int end = file.lastIndexOf("# Harbor Block Ends Here") + 25;

            file = file.replace(file.substring(start, end), "");

            // empties hostsFile then writes the String file to it.
            Files.writeString(Paths.get(hostsFile), file, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public static void unBlock() throws IOException {
        unBlock(getHostsFile());
    }

    public static void unBlockSite(String hostsFile, String blockedUrls, String url, boolean inSession) throws IOException {
        String file = Files.readString(Paths.get(blockedUrls));
        file = file.replace("0.0.0.0 " + url.substring(url.indexOf("//") + 2,url.length()-1) +"\n","");
        file = file.replace("::0 " + url.substring(url.indexOf("//") + 2,url.length()-1) +"\n","");
        Files.writeString(Paths.get(blockedUrls), file, StandardOpenOption.TRUNCATE_EXISTING);
        if (inSession) {
            unBlock(hostsFile);
            block(hostsFile, blockedUrls);
        }
    }

    public static String getHostsFile() {
        // Get OS name
        String OS = System.getProperty("os.name").toLowerCase();
        // Use OS name to indexOf correct location of hosts file
        String hostsFile = null;
        if ((OS.contains("win"))) {
            // Doesn't work before Windows 2000
            hostsFile = "C:\\Windows\\System32\\drivers\\etc\\hosts";
        } else if ((OS.contains("mac"))) {
            // Doesn't work before OS X 10.2
            hostsFile = "etc/hosts";
        } else if ((OS.contains("nux"))) {
            hostsFile = "/etc/hosts";
        } else {
            // Handle error when platform is not Windows, Mac, or Linux
            System.err.println("Sorry, but your OS doesn't support blocking.");
            System.exit(0);
        }
        return hostsFile;
    }

    public static String getBlockedUrls() {
        return System.getProperty("user.dir") + "\\site\\java\\blockedUrls.txt";
    }
}
