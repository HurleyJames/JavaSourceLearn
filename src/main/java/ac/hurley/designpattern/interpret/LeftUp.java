package ac.hurley.designpattern.interpret;

public class LeftUp implements Expression {

    @Override
    public void interpret() {
        System.out.println("松开鼠标：左键");
    }
}
