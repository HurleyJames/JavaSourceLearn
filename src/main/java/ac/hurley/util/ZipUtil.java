package ac.hurley.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {
    private static final int buffer = 2048;

    public static void unZip(String path) {
        int count = -1;
        String savePath = "";

        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        // 作为装饰器子类，使用它们可以防止每次读取、发送数据时进行实际的写操作，代表使用缓冲区
        BufferedOutputStream bos = null;

        // 保存解压文件的目录
        savePath = path.substring(0, path.lastIndexOf(".")) + File.separator;
        // 创建保存的目录
        new File(savePath).mkdir();
        ZipFile zipFile = null;
        try {
            // 解决中文乱码问题
            zipFile = new ZipFile(path, Charset.forName("GBK"));
            // 枚举zip中的所有文件
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                byte[] buf = new byte[buffer];
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                // 获取文件名
                String fileName = zipEntry.getName();
                fileName = savePath + fileName;
                // 是否已创建，默认为false
                boolean isMkdir = false;
                // 检查此文件是否带有文件夹
                if (fileName.lastIndexOf("/") != -1) {
                    isMkdir = true;
                }
                // 如果此枚举文件是文件夹，就创建，并且遍历下一个
                if (zipEntry.isDirectory()) {
                    file = new File(fileName);
                    file.mkdirs();
                    continue;
                }

                file = new File(fileName);
                // 如果文件不存在
                if (!file.exists()) {
                    if (isMkdir) {
                        // 创建目录
                        new File(fileName.substring(0, fileName.lastIndexOf("/"))).mkdirs();
                    }
                }
                // 再创建文件
                file.createNewFile();

                is = zipFile.getInputStream(zipEntry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);

                while ((count = is.read(buf)) > -1) {
                    bos.write(buf, 0, count);
                }
                // 因为实现了缓冲功能，所以调用flush()和close()方法，强行将缓冲区中的数据写出，否则可能无法写出数据
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
