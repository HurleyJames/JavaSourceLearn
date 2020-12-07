package ac.hurley.designpattern.interpret;

public class Repetition implements Expression {

    /**
     * 循环次数
     */
    private int loopCount;
    /**
     * 循环体表达式
     */
    private Expression expression;

    public Repetition(Expression expression, int loopCount) {
        this.expression = expression;
        this.loopCount = loopCount;
    }

    @Override
    public void interpret() {
        while (loopCount > 0) {
            expression.interpret();
            loopCount--;
        }
    }
}
