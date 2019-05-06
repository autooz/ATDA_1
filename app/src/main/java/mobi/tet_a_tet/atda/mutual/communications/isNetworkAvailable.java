package mobi.tet_a_tet.atda.mutual.communications;


/**
 * Created by oleg on 04.07.15.
 */

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class isNetworkAvailable {

    public static void main(String[] args) {
        dumpNetworkAvailable();
    }

    static void printHeaderAddress() {
        System.out.printf("\t%s\t%s\t%s\t%s\t%s\n", "HostName", "HostAddress", "isAnyLocalAddress", "isLoopbackAddress", "isSiteLocalAddress");
    }

    static void printAddress(InetAddress inetAddress) {
        if (inetAddress == null) {
            System.out.println("\tno address");
        } else {
            String match = !inetAddress.getHostName().equals(inetAddress.getHostAddress()) ? "*" : "";
            System.out.printf("\t%s\t%s\t%s\t%s\t%s\t%s\n", match, inetAddress.getHostName(), inetAddress.getHostAddress(), inetAddress.isAnyLocalAddress(), inetAddress.isLoopbackAddress(),
                    inetAddress.isSiteLocalAddress());
        }
    }

    static void printInterface(NetworkInterface networkInterface) {
        System.out.printf("%s(%s)\n", networkInterface.getDisplayName(), networkInterface.getName());
    }

    public static void dumpNetworkAvailable() {
        Enumeration<NetworkInterface> networkInterfaces;
        InetAddress inetAddress;

        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();

            printHeaderAddress();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> internetAddresses = networkInterface.getInetAddresses();
                printInterface(networkInterface);

                while (internetAddresses.hasMoreElements()) {
                    inetAddress = internetAddresses.nextElement();
                    printAddress(inetAddress);
                }
            }
        } catch (SocketException e) {
            System.out.println(e);
        }
    }
}
