import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * author:kent on 2019-05-24
 */
public class ImageHandler {
    public static void main(String[] args) {
        BufferedImage tag = reduceImg("1.jpeg");
        String targetPath = "output/";
        copyImage(tag, targetPath);
    }

    /***
     * 创建缩略图（压缩图片）
     * @throws InterruptedException
     */
    public static BufferedImage reduceImg(String imagePath) {
        int widthdist = 270;          //自定义压缩
        int heightdist = 270;
        Float rate = 0.0f;          //按原图片比例压缩（默认）
        try {
            File srcfile = new File(imagePath);
            // 检查图片文件是否存在
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
            }
            // 如果比例不为空则说明是按比例压缩
            if (rate != null && rate > 0) {
                //获得源图片的宽高存入数组中
                int[] results = getImgWidthHeight(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return null;
                } else {
                    //按比例缩放或扩大图片大小，将浮点型转为整型
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            java.awt.Image src = ImageIO.read(srcfile);
            // 构造一个类型为预定义图像类型之一的 BufferedImage
            BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);
            //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
            //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            return tag;

            //创建文件输出流
            /*String newImagePath = "/Users/kent/IdeaProjects/mytest/Image/output/" + System.currentTimeMillis() + ".jpeg";
            FileOutputStream out = new FileOutputStream(newImagePath);
            //将图片按JPEG压缩，保存到out中
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            //关闭文件输出流
            out.close();*/
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return null;
    }

    /**
     * 将图片进行合成
     */
    public static void copyImage(BufferedImage mainImage, String targetPath) {
        int row = 2;
        int col = 5;
        int mainWidth = mainImage.getWidth();
        int mainHeigh = mainImage.getHeight();

        try {
            int width = mainWidth * col;
            int height = mainHeigh * row;
            Image image = mainImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics2D g = bufferedImage2.createGraphics();

            int x = 707;
            int y = 268;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    g.drawImage(image, mainWidth * j, mainHeigh * i, mainWidth, mainHeigh, null);
                }
            }

            g.dispose();
            ImageIO.write(bufferedImage2, "jpg", new File(targetPath + System.currentTimeMillis() + ".jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片宽度和高度
     * @param
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            // 获得文件输入流
            is = new FileInputStream(file);
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            result[0] =src.getWidth(null); // 得到源图片宽
            result[1] =src.getHeight(null);// 得到源图片高
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return result;
    }
}
