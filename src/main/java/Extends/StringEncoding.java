package Extends;


import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class StringEncoding {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        System.out.println(s.length());
        char[] chars = s.toCharArray();
        System.out.println(s.charAt(1));
        byte[] bytes = s.getBytes();
        String gb2312 = new String(s.getBytes(), "GBK");
        System.out.println(gb2312.length());
        char[] chars1 = s.toCharArray();
        System.out.println(gb2312.charAt(1));
    }
}
