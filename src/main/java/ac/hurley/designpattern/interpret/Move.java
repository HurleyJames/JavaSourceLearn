package ac.hurley.designpattern.interpret;

public class Move implements Expression {

    /**
     * 鼠标位置的横纵坐标
     */
    private int x, y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void interpret() {
        System.out.println("移动鼠标：【" + x + ", " + y + "】");
    }
}
