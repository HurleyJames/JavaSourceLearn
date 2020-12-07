package ac.hurley.jvm.classloader;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(name);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object object = myLoader.loadClass("ac.hurley.jvm.classloader.ClassLoaderTest").getDeclaredConstructor().newInstance();

        System.out.println(object.getClass());
        System.out.println(object instanceof ac.hurley.jvm.classloader.ClassLoaderTest);
    }
}
