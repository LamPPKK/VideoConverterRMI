package YTDownloader;

import java.io.File;

/**
 *
 * @author Tuan Anh
 */
public class DeleteFile {
    public static void action(){
        String namefile = null;
        String file_path = "G:\\Java\\test5\\youtube_dl\\"; // nhap duong dan luu file youtubedl
        namefile = getFileNamePath.getFileName();
        File file1 = new File(file_path + namefile + ".mp4");
        File file2 = new File(file_path + "test.txt");
//        System.out.println(file1);
//        System.out.println(file2);
        file1.delete();
        file2.delete();
        
   }
}
