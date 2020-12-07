package ac.hurley.designpattern.interpret;

public class LeftClick implements Expression {

    private Expression leftDown;
    private Expression leftUp;

    public LeftClick() {
        this.leftDown = new LeftDown();
        this.leftUp = new LeftUp();
    }

    @Override
    public void interpret() {
        // 单击的解释就是 = 按下 + 松开
        leftDown.interpret();
        leftUp.interpret();
    }
}
