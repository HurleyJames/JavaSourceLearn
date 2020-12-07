package ac.hurley.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * 当Java堆内存溢出时，会提示Java heap space
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
