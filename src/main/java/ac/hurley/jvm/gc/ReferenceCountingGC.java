package ac.hurley.jvm.gc;

public class ReferenceCountingGC {

    public Object instance = null;
    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];


    /**
     * 因为objA.instance = objB, objB.instance = objA;
     * 之后令objA = null, objB = null，这两个对象就不可能再被访问了
     * 但因为它们互相引用着对方，导致引用计数都不为0，导致无法通知GC收集器回收它们
     */
    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();
    }
}
