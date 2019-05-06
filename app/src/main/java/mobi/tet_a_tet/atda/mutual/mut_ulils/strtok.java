package mobi.tet_a_tet.atda.mutual.mut_ulils;

import java.util.StringTokenizer;

/**
 * Created by oleg on 02.08.15.
 */
public class strtok {
    public static void main(String[] args) {

        String Demo = "This is a string that we want to tokenize";

        StringTokenizer Tok = new StringTokenizer(Demo);
        int n = 0;

        while (Tok.hasMoreElements())
            System.out.println("" + ++n + ": " + Tok.nextElement());
    }
}
