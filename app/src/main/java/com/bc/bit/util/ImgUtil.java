package com.bc.bit.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bc.bit.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import Decoder.BASE64Encoder;


/**
 * 图片工具类
 */
public final class ImgUtil {

    public static class ImageFileBean {

        private boolean hasCompressed;
        private String path;


        public ImageFileBean() {
        }

        public boolean hasCompressed() {
            return hasCompressed;
        }

        public void setHasCompressed(boolean hasCompressed) {
            this.hasCompressed = hasCompressed;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }



    /**
     * 释放图片
     *
     * @param des
     */
    public static void recycleBitmap(Bitmap des) {

        try {
            if (des != null && !des.isRecycled()) {
                des.recycle();
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 最合适的比例创建图片
     *
     * @param src
     * @param width
     * @param height
     * @return
     */
    public static Bitmap cropBitmap(Bitmap src, int width, int height) {

        try {

            int w = src.getWidth();
            int h = src.getHeight();

            int x = 0;
            int y = 0;
            int nw = 0;
            int nh = 0;

            float f = (float) w / h;
            float nf = (float) width / height;
            if (f > nf) {
                nw = (int) (h * nf);
                nh = h;
            } else {
                nw = w;
                nh = (int) (w / nf);
            }

            // 居中裁剪
            Bitmap temp = Bitmap.createBitmap(src, (w - nw) >> 1, (h - nh) >> 1, nw, nh);
            Bitmap des = createScaledBitmap(width, height, temp);

            if (des != null && des != temp) {
                recycleBitmap(temp);
                return des;
            }
        } catch (Throwable e) {
            //
        }

        return null;
    }

    /**
     * 创建缩放图片
     *
     * @param width
     * @param height
     * @param src
     * @return
     */
    public static Bitmap createScaledBitmap(int width, int height, Bitmap src) {

        try {
            Bitmap des = Bitmap.createScaledBitmap(src, width, height, true);

            if (des != null && src != des) {
                // src.recycle() ;
                return des;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            // out of memory
        }

        return src;
    }




    public static Bitmap decode(Context context, int resId) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            //opts.inJustDecodeBounds     = true;
            //BitmapFactory.decodeResource(context.getResources(), resId ,opts);

            // set parameter
            // int minSideLength 	 	 = Math.min(desWidth, desHeight);
            // opts.inSampleSize 		 = computeSampleSize(opts, minSideLength, desWidth * desHeight);
            opts.inJustDecodeBounds = false;
            opts.inInputShareable = true;
            opts.inPurgeable = true;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            //opts.outWidth            = desWidth;
            //opts.outHeight           = desHeight;

            // decode bitmap
            return BitmapFactory.decodeResource(context.getResources(), resId, opts);
        } catch (Exception var7) {
            return null;
        } catch (OutOfMemoryError var8) {
            var8.printStackTrace();
            return null;
        }
    }

    private static final int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            for (roundedSize = 1; roundedSize < initialSize; roundedSize <<= 1) {

            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static final int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = (double) options.outWidth;
        double h = (double) options.outHeight;
        int lowerBound = maxNumOfPixels == -1 ? 1 : (int) Math.ceil(Math.sqrt(w * h / (double) maxNumOfPixels));
        int upperBound = minSideLength == -1 ? 128 : (int) Math.min(Math.floor(w / (double) minSideLength), Math.floor(h / (double) minSideLength));
        return upperBound < lowerBound ? lowerBound : (maxNumOfPixels == -1 && minSideLength == -1 ? 1 : (minSideLength == -1 ? lowerBound : upperBound));
    }


    /**
     * 图片文件转Base64字符串
     * * @param path 文件所在的绝对路径加文件名
     * * @return
     */

    public static String fileBase64String(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }
            fis.close();
            String uploadBuffer = new String(Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP));
            return uploadBuffer;

        } catch (Exception e) {
            return null;
        }

    }


    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }



    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }



    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }




    public static Bitmap compressScale(Bitmap image, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为800f
        // float ww = 480f;// 这里设置宽度为480f
        float hh = 512f;
        float ww = 512f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
//        compressImages(bitmap,file);// 压缩好比例大小后再进行质量压缩
        return bitmap;
    }


    /**
     * 3.质量压缩
     * 设置bitmap options属性，降低图片的质量，像素不会减少
     * 第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置
     * 设置options 属性0-100，来实现压缩
     *
     * @param bmp
     * @param file
     */
    public static void compressImages(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int quality =100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static Bitmap compressImages(Bitmap image,File file) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 90;
//        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset(); // 重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;// 每次都减少10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }

    public static String fileToBase64(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }
    /**
     * 获取公共配置
     *
     * @return
     */
    public static RequestOptions getCommonRequestOptions() {
        //创建配置选项
        RequestOptions options = new RequestOptions();

        //占位图
        options.placeholder(R.drawable.placeholder);

        //出错后显示的图片
        //包括：图片不存在等情况
        options.error(R.drawable.placeholder);

        //从中心裁剪
        options.centerCrop();

        return options;
    }


    /**
     * 显示资源图片
     *
     * @param context
     * @param view
     * @param resourceId
     */
    public static void showLocalImage(Context context, ImageView view, int resourceId) {
        //获取通用配置
        RequestOptions options = getCommonRequestOptions();

        //显示图片
        Glide.with(context)
                .load(resourceId)
                .apply(options)
                .into(view);
    }
}
