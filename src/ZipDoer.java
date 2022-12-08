import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *  This class has method for zipping specific folder or file.
 *  Use classes of standard library.
 */
public class ZipDoer {
    /**
     * <p>Demonstration: how it works.</p>
     * <b>Params</b>:
     * <ul>
     *     <li>pathname - your folder for zipping</li>
     *     <li>destiny - your zip of folder</li>
     * </ul>
     * <p>Use getZOS for getting stream with zip and use base `pack` method for zipping.</p>
     *
     * @param args - have nothing
     */
    public static void main(String[] args) {
        final String pathname = "E:\\projects\\javafx-core\\ZipZap\\files\\poiu";
        final File source = new File(pathname);
        final File destiny = new File(pathname + ".zip");

        try {
            final ZipOutputStream zos = getZipOutputStream(destiny);
            pack(source, zos, null);
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get stream with your zip file.
     *
     * @param destiny zip file
     * @return ZipOutputStream for base method in next usage.
     * @throws FileNotFoundException trouble with file system
     */
    private static ZipOutputStream getZipOutputStream(File destiny) throws FileNotFoundException {
        final FileOutputStream fos = new FileOutputStream(destiny);
        return new ZipOutputStream(fos);
    }

    /**
     * Check folder or file it is. And pack by utilize method. If source is folder method call itself recursively
     * with updated pathname.
     * @param sourceFolder file we want to zip
     * @param zos ZipOutputStream for base method in next usage.
     * @param prevPath path of parent directory
     * @throws IOException trouble with file system
     */
    private static void pack(File sourceFolder, ZipOutputStream zos, String prevPath) throws IOException {
        String parentDir = "";
        if (prevPath != null) {
            parentDir = prevPath + "\\";
        }
        if (sourceFolder.isFile()) {
            packFile(sourceFolder, zos, parentDir);
        } else {
            final File[] files = sourceFolder.listFiles();
            assert files != null;
            for (File next :
                    files) {
                pack(next, zos, parentDir + sourceFolder.getName());
            }
        }
    }

    /**
     * Pack source file to zip.
     * @param source source file
     * @param zos ZipOutputStream for base method in next usage.
     * @param prevPath path of parent directory
     * @throws IOException trouble with file system
     */
    public static void packFile(File source, ZipOutputStream zos, String prevPath) throws IOException {
        String parentDir = "";
        if (prevPath != null) {
            parentDir = prevPath;
        }
        final ZipEntry zipEntry = new ZipEntry(parentDir + source.getName());

        zos.putNextEntry(zipEntry);

        final FileInputStream fis = new FileInputStream(source);

        byte[] buffer = new byte[1024];
        int byteQuantity;

        while ((byteQuantity = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, byteQuantity);
        }

        zos.closeEntry();
        fis.close();

        System.out.println(source.getName() + " was zipped.");
    }
}
