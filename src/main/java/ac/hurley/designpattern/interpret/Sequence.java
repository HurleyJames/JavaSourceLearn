package ac.hurley.designpattern.interpret;

import java.util.List;

public class Sequence implements Expression {

    /**
     * 指令集序列
     */
    private List<Expression> expressionList;

    public Sequence(List<Expression> expressions) {
        this.expressionList = expressions;
    }

    @Override
    public void interpret() {
        // 循环解析每条指令
        expressionList.forEach(expression -> expression.interpret());
    }
}
