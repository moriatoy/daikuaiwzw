package com.commonlib.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 图片工具
 *
 * @author Lanyan
 */
public class BitmapUtil {

    /**
     * 将Bitmap转成二进制
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 保存图片到sd卡
     *
     * @param bmp
     * @param filename
     * @return
     */
    public static boolean saveBitmap2file(Bitmap bmp, String filename) {
        CompressFormat format = CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

    /**
     * 从SD卡里面读取图片
     *
     * @param path 图片的完整路径，包含文件名
     * @return Bitmap
     * @since v 1.0
     */
    public static Bitmap getBitmapBySD(String path, String fileName) {
        Bitmap b = null;
        try {
            File f = null;
            if (fileName != null)
                f = new File(path, fileName);
            else
                f = new File(path);
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();

            int scale = 1;
            int IMAGE_MAX_SIZE = 200;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.5)));
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (Exception e) {
            return null;
        }
        return b;
    }

    /**
     * 保存成本地图片
     *
     * @param picUrl
     * @param name
     */
    public static void savePictureFromNet(final String picUrl, final String name) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    File dir = new File(name);
                    if (!dir.exists()) {// Launch camera to take photo for
                        dir.mkdirs();// 创建照片的存储目录
                    }
                    URL url = new URL(picUrl);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setConnectTimeout(5000);
                    // 获取到文件的大小
                    InputStream is = conn.getInputStream();
                    File file = new File(name);
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    // int total = 0;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        // total += len;
                    }
                    fos.close();
                    bis.close();
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static String fileToBase64(String filePath) {
        File file = new File(filePath);
        if (file.length() > 1024 * 1024 * 2) {
            System.out.println("企业图标限制大小在2M以内");
            return null;
        }
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new String(Base64.encodeBase64(buffer));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap base64ToBitmap(String base64) {
        try {
            byte[] buffer = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static String base64ToFile(String base64Code, String filePath) {
        byte[] buffer = Base64.decodeBase64(base64Code);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            out.write(buffer);
            out.close();
            return filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将图片的网络地址转化为唯一的文件名
     *
     * @param url
     * @return
     */
    public static String urlToUuid(String url) {
        return UUID.nameUUIDFromBytes(url.getBytes()).toString();
    }


    public static Bitmap imageoperation(Bitmap mbitmap, float saturation) {
        //传入的Bitmap默认不可修改，需要创建新的Bitmap
        Bitmap mbitmap_fu = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //创建画布，在新的bitmap上绘制
        Canvas canvas = new Canvas(mbitmap_fu);
        //设置画笔抗锯齿，后面在Bitmap上绘制需要使用到画笔
        Paint mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mpaint.setColor(Color.WHITE);
//        mpaint.setStyle(Paint.Style.FILL);
//        mpaint.setShader(new BitmapShader(mbitmap_fu, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        ColorMatrix saturationmatrix = new ColorMatrix();
        saturationmatrix.setSaturation(saturation);

        mpaint.setColorFilter(new ColorMatrixColorFilter(saturationmatrix));

        canvas.drawBitmap(mbitmap, 0, 0, mpaint);
//        canvas.drawCircle(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2, mbitmap.getHeight() / 2, mpaint);
//        canvas.drawRect(0, 0, mbitmap.getWidth(), mbitmap.getWidth(), mpaint);
        return mbitmap_fu;
    }

    //使用Bitmap加Matrix来缩放
    public static Drawable resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    //使用BitmapFactory.Options的inSampleSize参数来缩放
    public static Drawable resizeImage2(String path,
                                        int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载bitmap到内存中
        BitmapFactory.decodeFile(path, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 1;

        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
            int sampleSize = (outWidth / width + outHeight / height) / 2;
            Log.d("tag", "sampleSize = " + sampleSize);
            options.inSampleSize = sampleSize;
        }

        options.inJustDecodeBounds = false;
        return new BitmapDrawable(BitmapFactory.decodeFile(path, options));
    }
}
