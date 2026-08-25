package ai0825;

import java.util.Arrays;
import java.util.Collections;

public class SortArray {
    public static void main(String[] args) {
        Integer [] numArr = {77, 33, 11, 99, 22, 55, 88};
        //  오름차순 정렬
        Arrays.sort(numArr);
        //  내림차순 정렬
    //  Arrays.sort(numArr, Collections.reverseOrder());
        for (int data : numArr)
        {
            System.out.print(data + "   ");
        }

        System.out.println();

        String [] nameArr = {"김유민", "도형준", "강석현", "유재화", "장영서"};
        Arrays.sort(nameArr, Collections.reverseOrder());
        for ( String name : nameArr)
        {
            System.out.print(name + "   ");
        }
    }
}
