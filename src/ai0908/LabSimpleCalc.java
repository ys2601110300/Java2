package ai0908;

import java.util.Scanner;

public class LabSimpleCalc {

    public static int calc (int n1, int n2, String operator){
        int result = 0;

        switch(operator){
            case "+" : result = n1 + n2;
                    break;
            case "-" : result = n1 - n2;
                    break;
            case "*" : result = n1 * n2;
                    break;
            case "/" : result = n1 / n2;
                    break;
        }

        return result;
    }
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);    //  정수 입력용
        Scanner s2 = new Scanner(System.in);    //  문자 입력용

        System.out.println("--------- 사칙연산 계산기 ----------");
        while (true) {
            System.out.print("1. 정수 입력: ");
            int n1 = s1.nextInt();

            System.out.print("2. 정수 입력: ");
            int n2 = s1.nextInt();

            System.out.println("프로그램 종료를 원하면 end를 입력하세요.");

            System.out.print("3. 연산자 입력: ");
            String operator = s2.nextLine();
            String nothingCase = operator.toLowerCase();
            if (nothingCase.equals("end")) {
                System.out.println("프로그램이 종료됩니다.");
                s1.close();
                s2.close();
                return;
            }
            /* else if (operator.equals("End"))
            {
                System.out.println("프로그램이 종료됩니다.");
                s1.close();
                s2.close();
                return;
            }
            else if (operator.equals("END")) {
                System.out.println("프로그램이 종료됩니다.");
                s1.close();
                s2.close();
                return;
            }*/
            int result = calc(n1, n2, operator);

            System.out.printf("%d %s %d = %d\n", n1, operator, n2, result);
        }
    }
}
