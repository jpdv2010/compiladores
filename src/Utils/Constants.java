package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joaop on 05/03/2018.
 */
public class Constants {
    public static String[] letters = {"A","B","C","D","E",
            "F","G","H","I",
            "J","K","L","M",
            "N","O","P","Q",
            "R","S","T","U",
            "V","W","X","Y",
            "Z"};
    public static List<String> numbers = Arrays.asList("0","1","2","3","4","5","6","7","8","9");
    public static List<String> LiteralConstants = Arrays.asList("CLI","CLS","CLR","CLL","Identifier");
    public static List<String> Operators = Arrays.asList("+","-","/","&","*");
    public static List<String> Types = Arrays.asList("int","real","char","bool","string");

    public static String PLACEHOLD_CODE = "( int a b c x y z )\n" +
            "( :>> \"entre com o valor de x:\")\n" +
            "( :<< x )\n" +
            "( = z ( * x y ) )\n" +
            "( = c ( + a b ) )\n" +
            "( ... ( <= a b ) ( = z ( * x y ) ) )\n" +
            "( ? ( <= a b ) ( :>> \"Valor Invalido\" ) )\n" +
            "( ... ( <= a b ) \n" +
            "( ... ( <= a b ) \n" +
            "( = z ( * x y ) ) ) )";

}
