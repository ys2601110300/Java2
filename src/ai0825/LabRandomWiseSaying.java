package ai0825;

import java.util.Random;

public class LabRandomWiseSaying {
    public static void main(String[] args) {
        String[] wiseSay =
        {
                "종종 작은 기회로부터 위대한 업적이 시작된다.",
                "시작이 반이다.",
                "배움에는 끝이 없다.",
                "나 자신을 이기는 사람이 가장 강한 사람이다.",
                "어둠이 깊을수록 별은 더 빛난다.",
                "심연을 오래바라보면, 심연 또한 나를 바라본다.",
                "기회는 준비된 사람에게 찾아온다."
        };
        Random random = new Random();
        int randomIndex = random.nextInt(wiseSay.length);
        System.out.println("오늘의 명언: " + wiseSay[randomIndex]);
    }
}
