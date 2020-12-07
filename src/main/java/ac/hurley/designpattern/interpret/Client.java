package ac.hurley.designpattern.interpret;

import java.util.Arrays;

public class Client {

    public static void main(String[] args) {
        /*
         * BEGIN            // 脚本开始
         * MOVE 500,600;    // 鼠标移动到坐标(500, 600)
         * BEGIN LOOP 5     // 开始循环5次
         * LEFT_CLICK;      // 循环体内单击左键
         * DELAY 1;         // 每次延时1秒
         * END;             // 循环体结束
         * RIGHT_DOWN;      // 按下右键
         * DELAY 7200;      // 延时2小时
         * END;             // 脚本结束
         */

        // 构建指令集语义树，实际情况交给语法解析器
        Expression sequence = new Sequence(Arrays.asList(
                new Move(500, 600),
                new Repetition(
                        new Sequence(
                                Arrays.asList(new LeftClick(), new Delay(1))
                        ),
                        5
                ),
                new LeftDown(),
                new Delay(7200)
        ));

        // 循环解析
        sequence.interpret();
    }
}
