package util;

import java.util.Random;
import java.util.Scanner;

public class Util {

    private static String[] names = {"Ivan","Georgi","Pesho","Emo","Bendji","Qvor","Qnko","Dido"};
    private static String[] addresses = {"Sofia","Dobrich","Burgas","Varna","Plovdiv"};
    private static String[] womanNames = {"Ivana","Gergana","Petq","Dara","Violeta","Nikol","Spaska","Dimitrina"};

    public static String getRandomName(){
        return names[getRandomNumber(0, names.length)-1];
    }

    public static String getRandomWomanName(){
        return womanNames[getRandomNumber(0, womanNames.length)-1];
    }

    public static String getRandomAddress(){
        return addresses[getRandomNumber(0, addresses.length-1)];
    }

    public static String getRandomPhone(){
        StringBuilder s = new StringBuilder();
        s.append("08");
        for (int i = 0; i < 8; i++) {
            s.append(getRandomNumber(0,9));
        }
        return s.toString();
    }

    public static int getRandomNumber(int from,int to){ //1-5
        int max = Math.max(from,to);
        int min = Math.min(from,to);
        return new Random().nextInt(max - min + 1) + min;
    }

    public static boolean checkString(String string){
        if (string == null || string.equals("")){
            return false;
        }
        else return true;
    }

    public static String generateCorrectString(String string){
        String s = "";
        while (!checkString(s)){
            System.out.print("Enter correct " + string + ": ");
            s = new Scanner(System.in).nextLine();
        }
        return s;
    }

    public static double generateCorrectNumber(String string) {
        double n = 0;
        while (n <=0){
            System.out.println("Enter a correct " + string + ": ");
            n = new Scanner(System.in).nextDouble();
        }
        return n;
    }
}
