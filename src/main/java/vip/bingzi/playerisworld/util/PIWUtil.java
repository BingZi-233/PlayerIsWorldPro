package vip.bingzi.playerisworld.util;

import java.util.Random;

public class PIWUtil {

    /**
     * 用于随机生成指定长度的字符串
     *
     * @param length 长度
     * @return 随机生成的字符串
     */
    // 随机字符串生成器，CV过来的。不要吐槽我，这个部分我没研究。
    @SuppressWarnings("SpellCheckingInspection")
    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiop1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(36);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }
}
