package Utils;

import Java.Entitys.Cls;

import java.util.List;
import java.util.Objects;

/**
 * Created by joaop on 05/03/2018.
 */
public class CommonUtils {
    public static boolean isNumber(String symbol){
        for(int i = 0; i < Constants.numbers.length; i++){
            if(Constants.numbers[i].equals(symbol)) return true;
        }
        return false;
    }

    public static boolean isLetter(String symbol){
        for(int i = 0; i < Constants.letters.length; i++){
            if(Constants.letters[i].equals(symbol)
                    || Constants.letters[i].toLowerCase().equals(symbol)) return true;
        }
        return false;
    }

    public static String clearCls(String code, List<Cls> clss){
        StringBuilder newCode = new StringBuilder();

        for(Cls cls : clss) {
            String[] perCharCode = code.split("");
            String[] perCharCls = cls.getText().split("");
            int lenght = cls.getText().length();
            int equality = 0;
            for (int i = 0; i < code.length() - lenght; i++) {
                for (int j = 0; j < lenght; j++) {
                    if(Objects.equals(perCharCode[i],perCharCls[j + i]))
                    {
                        equality++;
                    }
                    else equality = 0;
                }
                if(equality != lenght)
                {
                    newCode.append(perCharCode[i]);
                }else{
                    newCode.append(" cls ");
                }
            }

        }
        return newCode.toString();
    }
}
