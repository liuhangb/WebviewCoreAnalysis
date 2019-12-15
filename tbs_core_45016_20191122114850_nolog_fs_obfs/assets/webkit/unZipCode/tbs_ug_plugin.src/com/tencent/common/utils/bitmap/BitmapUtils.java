package com.tencent.common.utils.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

public class BitmapUtils
{
  public static int CROP_MODE_NORMAL = 0;
  public static final int CROP_MODE_RETAIN_MAX = 1;
  @Deprecated
  public static int GL_MAX_TEXTURE_SIZE_UNDETERMINED = 4096;
  public static final int IMAGETYPE_BMP = 6;
  public static final int IMAGETYPE_GIF = 4;
  public static final int IMAGETYPE_JPEG = 1;
  public static final int IMAGETYPE_PNG = 3;
  public static final int IMAGETYPE_SHARPP = 7;
  public static final int IMAGETYPE_TIFF = 5;
  public static final int IMAGETYPE_UNKOWN = 0;
  public static final int IMAGETYPE_WEBP = 2;
  public static final int LAYER_FLAGS = 31;
  public static final int SCALE_CENTER = 0;
  public static final int SCALE_FILL = 1;
  public static final String TAG = "BitmapUtils";
  public static final int UPLOAD_MAX_DIMEN = 480;
  private static int jdField_a_of_type_Int;
  private static Constructor jdField_a_of_type_JavaLangReflectConstructor;
  private static Constructor b;
  public static BitmapFactory.Options defaultOptions;
  public static final PorterDuffXfermode sPorterDuffXfermodeSrcIn = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
  
  static
  {
    defaultOptions = new BitmapFactory.Options();
    defaultOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    BitmapFactory.Options localOptions = defaultOptions;
    localOptions.inDither = true;
    localOptions.inSampleSize = 1;
    jdField_a_of_type_Int = 0;
  }
  
  public static byte[] Bitmap2Bytes(Bitmap paramBitmap)
  {
    if (!isAvailable(paramBitmap)) {
      return null;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
      paramBitmap = localByteArrayOutputStream.toByteArray();
      return paramBitmap;
    }
    catch (OutOfMemoryError paramBitmap) {}
    return null;
  }
  
  public static String Bitmap2String(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    return Base64.encode(localByteArrayOutputStream.toByteArray());
  }
  
  private static int a(int paramInt)
  {
    int i = 1;
    while (i <= paramInt) {
      i *= 2;
    }
    paramInt = i;
    if (i > 1) {
      paramInt = i / 2;
    }
    return paramInt;
  }
  
  /* Error */
  private static Bitmap a(a parama, BitmapFactory.Options paramOptions)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokeinterface 134 1 0
    //   6: astore_2
    //   7: aload_2
    //   8: astore_0
    //   9: aload_2
    //   10: aconst_null
    //   11: aload_1
    //   12: invokestatic 140	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   15: astore_1
    //   16: aload_2
    //   17: ifnull +7 -> 24
    //   20: aload_2
    //   21: invokevirtual 145	java/io/InputStream:close	()V
    //   24: aload_1
    //   25: areturn
    //   26: astore_1
    //   27: goto +46 -> 73
    //   30: astore_1
    //   31: goto +24 -> 55
    //   34: astore_1
    //   35: aconst_null
    //   36: astore_0
    //   37: goto +36 -> 73
    //   40: aconst_null
    //   41: astore_2
    //   42: aload_2
    //   43: ifnull +7 -> 50
    //   46: aload_2
    //   47: invokevirtual 145	java/io/InputStream:close	()V
    //   50: aconst_null
    //   51: areturn
    //   52: astore_1
    //   53: aconst_null
    //   54: astore_2
    //   55: aload_2
    //   56: astore_0
    //   57: ldc 40
    //   59: aload_1
    //   60: invokestatic 151	com/tencent/basesupport/FLogger:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   63: aload_2
    //   64: ifnull +7 -> 71
    //   67: aload_2
    //   68: invokevirtual 145	java/io/InputStream:close	()V
    //   71: aconst_null
    //   72: areturn
    //   73: aload_0
    //   74: ifnull +7 -> 81
    //   77: aload_0
    //   78: invokevirtual 145	java/io/InputStream:close	()V
    //   81: aload_1
    //   82: athrow
    //   83: astore_0
    //   84: goto -44 -> 40
    //   87: astore_0
    //   88: goto -46 -> 42
    //   91: astore_0
    //   92: aload_1
    //   93: areturn
    //   94: astore_0
    //   95: aconst_null
    //   96: areturn
    //   97: astore_0
    //   98: aconst_null
    //   99: areturn
    //   100: astore_0
    //   101: goto -20 -> 81
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	104	0	parama	a
    //   0	104	1	paramOptions	BitmapFactory.Options
    //   6	62	2	localInputStream	InputStream
    // Exception table:
    //   from	to	target	type
    //   9	16	26	finally
    //   57	63	26	finally
    //   9	16	30	java/io/IOException
    //   0	7	34	finally
    //   0	7	52	java/io/IOException
    //   0	7	83	java/lang/OutOfMemoryError
    //   9	16	87	java/lang/OutOfMemoryError
    //   20	24	91	java/io/IOException
    //   46	50	94	java/io/IOException
    //   67	71	97	java/io/IOException
    //   77	81	100	java/io/IOException
  }
  
  private static Bitmap a(a parama, QImageParams paramQImageParams)
  {
    BitmapFactory.Options localOptions = newOptions();
    if ((paramQImageParams != null) && (paramQImageParams.mWidth > 0) && (paramQImageParams.mHeight > 0))
    {
      boolean bool = paramQImageParams.mNeedCrop;
      int k = 1;
      localOptions.inJustDecodeBounds = true;
      Object localObject = a(parama, localOptions);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("BMP : ");
      localStringBuilder.append(localObject);
      FLogger.d("BitmapUtils", localStringBuilder.toString());
      int n = localOptions.outWidth;
      int i = localOptions.outHeight;
      int i3 = paramQImageParams.mWidth;
      int i4 = paramQImageParams.mHeight;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("cropBitmap raw=");
      ((StringBuilder)localObject).append(n);
      ((StringBuilder)localObject).append("x");
      ((StringBuilder)localObject).append(i);
      ((StringBuilder)localObject).append(", dst=");
      ((StringBuilder)localObject).append(i3);
      ((StringBuilder)localObject).append("x");
      ((StringBuilder)localObject).append(i4);
      FLogger.d("BitmapUtils", ((StringBuilder)localObject).toString());
      if ((n > i3) && (i > i4) && (paramQImageParams.mCropMode == 1)) {
        paramQImageParams.mCropMode = CROP_MODE_NORMAL;
      }
      float f1 = 0.0F;
      int m = 0;
      float f2;
      int j;
      if ((n <= i3) && (i <= i4))
      {
        i = 0;
        f2 = 0.0F;
        j = 1;
      }
      else
      {
        f1 = n * 1.0F / (i * 1.0F);
        f2 = i3 * 1.0F / (i4 * 1.0F);
        if (f1 > f2)
        {
          if (i > i4)
          {
            j = a(i / i4);
            if (bool)
            {
              n /= j;
              i /= j;
              if (paramQImageParams.mCropMode == CROP_MODE_NORMAL)
              {
                i = n - i * i3 / i4;
                break label588;
              }
              if (paramQImageParams.mCropMode == 1)
              {
                i = n - i3;
                break label588;
              }
              i = 0;
              break label588;
            }
            i = 0;
            break label588;
          }
          if (bool)
          {
            if (paramQImageParams.mCropMode == CROP_MODE_NORMAL)
            {
              i = n - i * i3 / i4;
              j = 1;
              break label588;
            }
            if (paramQImageParams.mCropMode == 1)
            {
              i = n - i3;
              j = 1;
              break label588;
            }
          }
        }
        else if (f1 <= f2)
        {
          if (n > i3)
          {
            j = a(n / i3);
            if (bool)
            {
              n /= j;
              i /= j;
              if (paramQImageParams.mCropMode == CROP_MODE_NORMAL) {
                i -= n * i4 / i3;
              } else if (paramQImageParams.mCropMode == 1) {
                i -= i4;
              } else {
                i = 0;
              }
              break label588;
            }
            i = 0;
            break label588;
          }
          if (bool)
          {
            if (paramQImageParams.mCropMode == CROP_MODE_NORMAL)
            {
              i -= n * i4 / i3;
              j = 1;
              break label588;
            }
            if (paramQImageParams.mCropMode == 1)
            {
              i -= i4;
              j = 1;
              break label588;
            }
          }
        }
        i = 0;
        j = 1;
      }
      label588:
      if (j <= 1) {
        j = k;
      }
      localOptions.inSampleSize = j;
      localOptions.inJustDecodeBounds = false;
      localObject = a(parama, localOptions);
      parama = new StringBuilder();
      parama.append("inSampleSize");
      parama.append(localOptions.inSampleSize);
      parama.append(", scaleRate=");
      parama.append(j);
      parama.append(", cropSize=");
      parama.append(i);
      parama.append(", tmpBitmap=");
      parama.append(localObject);
      FLogger.d("BitmapUtils", parama.toString());
      if (paramQImageParams != null) {
        bool = paramQImageParams.mNeedAlign;
      } else {
        bool = false;
      }
      paramQImageParams = null;
      if (i > 0)
      {
        if (f1 > f2)
        {
          i /= 2;
          n = i * 2;
          k = 0;
        }
        else
        {
          i /= 2;
          k = i;
          m = i * 2;
          i = 0;
          n = 0;
        }
        if (localObject != null)
        {
          int i1 = ((Bitmap)localObject).getWidth() - n;
          int i2 = ((Bitmap)localObject).getHeight() - m;
          if (bool)
          {
            i1 = (((Bitmap)localObject).getWidth() - n) / 2 * 2;
            i2 = (((Bitmap)localObject).getHeight() - m) / 2 * 2;
          }
          parama = Bitmap.createBitmap((Bitmap)localObject, i, k, i1, i2);
        }
        else
        {
          parama = null;
        }
        if ((parama != null) && (parama != localObject)) {
          ((Bitmap)localObject).recycle();
        }
      }
      else
      {
        parama = (a)localObject;
      }
      if (parama != null)
      {
        i = parama.getWidth();
        k = parama.getHeight();
        if ((i > i3) && (k > i4)) {
          parama = createScaleBitmap(parama, i3, i4, 0, true, bool);
        }
        paramQImageParams = new StringBuilder();
        paramQImageParams.append("scale rate : ");
        paramQImageParams.append(j);
        FLogger.d("BitmapUtils", paramQImageParams.toString());
        paramQImageParams = new StringBuilder();
        paramQImageParams.append("retBitmap w:");
        paramQImageParams.append(parama.getWidth());
        paramQImageParams.append(" h:");
        paramQImageParams.append(parama.getHeight());
        FLogger.d("BitmapUtils", paramQImageParams.toString());
        paramQImageParams = parama;
      }
      return paramQImageParams;
    }
    return a(parama, localOptions);
  }
  
  private static void a(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
  {
    float[] arrayOfFloat = new float[3];
    float f1 = 1 / 3.0F;
    float f2 = 2.0F * f1 * f1;
    double d = f2;
    Double.isNaN(d);
    float f3 = (float)Math.sqrt(d * 3.141592653589793D);
    int i = -1;
    for (f1 = 0.0F; i <= 1; f1 += arrayOfFloat[i])
    {
      float f4 = i * i;
      i += 1;
      arrayOfFloat[i] = ((float)Math.exp(-f4 / f2) / f3);
    }
    i = 0;
    while (i < arrayOfFloat.length)
    {
      arrayOfFloat[i] /= f1;
      i += 1;
    }
    i = 0;
    int j = 0;
    while (i < paramInt2)
    {
      int m = i;
      int k = 0;
      while (k < paramInt1)
      {
        int i3 = -1;
        int i2 = 0;
        int i1 = 0;
        int n = 0;
        while (i3 <= 1)
        {
          int i4 = paramArrayOfInt1[((k + i3 & paramInt1 - 1) + j)];
          i3 += 1;
          int i5 = new int[] { 60, 136, 60 }[i3];
          i2 += ((i4 & 0xFF0000) >> 16) * i5;
          i1 += ((i4 & 0xFF00) >> 8) * i5;
          n += i5 * (i4 & 0xFF);
        }
        paramArrayOfInt2[m] = (0xFF000000 | i2 >> 8 << 16 | i1 >> 8 << 8 | n >> 8);
        m += paramInt2;
        k += 1;
      }
      j += paramInt1;
      i += 1;
    }
  }
  
  public static Bitmap compressBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((paramBitmap != null) && (paramInt1 > 0) && (paramInt2 > 0))
    {
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      if ((i <= paramInt1) && (j <= paramInt2))
      {
        FLogger.d("BitmapUtils", "原图比要求的图小，走了");
        if ((paramBitmap != null) && (Bitmap.Config.ARGB_8888.equals(paramBitmap.getConfig()))) {
          return convertBmpFormat(paramBitmap, Bitmap.Config.RGB_565);
        }
        return paramBitmap;
      }
      Object localObject1 = paramBitmap;
      if (paramBoolean) {
        localObject1 = cropBitmapCenter(paramBitmap, i, j, paramInt1, paramInt2);
      }
      paramBitmap = (Bitmap)localObject1;
      if (localObject1 != null)
      {
        paramBitmap = (Bitmap)localObject1;
        if (Bitmap.Config.ARGB_8888.equals(((Bitmap)localObject1).getConfig())) {
          paramBitmap = convertBmpFormat((Bitmap)localObject1, Bitmap.Config.RGB_565);
        }
      }
      Object localObject2 = paramBitmap;
      Object localObject3;
      if (i > paramInt1)
      {
        localObject2 = paramBitmap;
        if (j > paramInt2)
        {
          localObject1 = new Matrix();
          ((Matrix)localObject1).setRectToRect(new RectF(0.0F, 0.0F, paramBitmap.getWidth(), paramBitmap.getHeight()), new RectF(0.0F, 0.0F, paramInt1, paramInt2), Matrix.ScaleToFit.CENTER);
          localObject2 = new float[9];
          ((Matrix)localObject1).getValues((float[])localObject2);
          localObject1 = null;
          try
          {
            localObject2 = Bitmap.createScaledBitmap(paramBitmap, (int)(paramBitmap.getWidth() * localObject2[0]), (int)(paramBitmap.getHeight() * localObject2[4]), true);
            localObject1 = localObject2;
          }
          catch (Exception localException)
          {
            FLogger.e("BitmapUtils", localException);
          }
          catch (OutOfMemoryError localOutOfMemoryError)
          {
            FLogger.e("BitmapUtils", localOutOfMemoryError);
          }
          localObject3 = paramBitmap;
          if (localObject1 != null)
          {
            localObject3 = paramBitmap;
            if (!localObject1.equals(paramBitmap))
            {
              paramBitmap.recycle();
              localObject3 = localObject1;
            }
          }
        }
      }
      return (Bitmap)localObject3;
    }
    FLogger.d("BitmapUtils", "参数非法哦！");
    return paramBitmap;
  }
  
  public static Bitmap convertBmpFormat(Bitmap paramBitmap, Bitmap.Config paramConfig)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.isRecycled()) {
        return paramBitmap;
      }
      try
      {
        paramConfig = paramBitmap.copy(paramConfig, false);
      }
      catch (OutOfMemoryError paramConfig)
      {
        FLogger.e("BitmapUtils", paramConfig);
        paramConfig = null;
      }
      Object localObject = paramBitmap;
      if (paramConfig != null)
      {
        paramBitmap.recycle();
        localObject = paramConfig;
      }
      return (Bitmap)localObject;
    }
    return paramBitmap;
  }
  
  public static Bitmap createCropedAndScaledBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if ((paramBitmap != null) && (paramInt1 > 0))
    {
      if (paramInt2 <= 0) {
        return paramBitmap;
      }
      int i = paramBitmap.getWidth();
      int k = paramBitmap.getHeight();
      float f1 = paramInt1 / paramInt2;
      float f2 = i / k;
      int m = 0;
      Bitmap localBitmap4 = null;
      int j;
      Bitmap localBitmap1;
      if (f1 > f2)
      {
        j = (paramInt1 * k - i * paramInt2) / (paramInt1 * 2);
        k -= j;
        localBitmap1 = null;
      }
      else if (f1 < f2)
      {
        int n = (i * paramInt2 - paramInt1 * k) / (paramInt2 * 2);
        m = n;
        j = 0;
        i -= n;
        localBitmap1 = null;
      }
      else
      {
        j = 0;
        localBitmap1 = paramBitmap;
      }
      Bitmap localBitmap3 = localBitmap1;
      if (localBitmap1 == null) {
        try
        {
          localBitmap3 = Bitmap.createBitmap(paramBitmap, m, j, i - m, k - j);
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          FLogger.e("BitmapUtils", localOutOfMemoryError);
          localBitmap3 = null;
        }
      }
      Bitmap localBitmap2 = localBitmap4;
      if (localBitmap3 != null)
      {
        localBitmap4 = createScaleBitmap(localBitmap3, paramInt1, paramInt2, 1);
        if ((localBitmap4 == null) && (localBitmap3 != paramBitmap)) {
          localBitmap3.recycle();
        }
        localBitmap2 = localBitmap4;
        if (localBitmap4 != null)
        {
          localBitmap2 = localBitmap4;
          if (!localBitmap4.equals(paramBitmap))
          {
            paramBitmap.recycle();
            localBitmap2 = localBitmap4;
          }
        }
      }
      return localBitmap2;
    }
    return paramBitmap;
  }
  
  public static Bitmap createScaleBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3)
  {
    return createScaleBitmap(paramBitmap, paramInt1, paramInt2, paramInt3, true, false);
  }
  
  public static Bitmap createScaleBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    return createScaleBitmap(paramBitmap, paramInt1, paramInt2, paramInt3, paramBoolean, false);
  }
  
  public static Bitmap createScaleBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Object localObject1 = new Matrix();
    float f1 = i;
    float f2 = j;
    Object localObject2 = new RectF(0.0F, 0.0F, f1, f2);
    RectF localRectF = new RectF(0.0F, 0.0F, paramInt1, paramInt2);
    if (paramInt3 == 0) {
      ((Matrix)localObject1).setRectToRect((RectF)localObject2, localRectF, Matrix.ScaleToFit.START);
    } else if (paramInt3 == 1) {
      ((Matrix)localObject1).setRectToRect((RectF)localObject2, localRectF, Matrix.ScaleToFit.FILL);
    }
    localObject2 = new float[9];
    ((Matrix)localObject1).getValues((float[])localObject2);
    paramInt1 = (int)(localObject2[0] * f1);
    paramInt2 = (int)(localObject2[4] * f2);
    if (paramBoolean2) {}
    try
    {
      paramInt1 = (int)(f1 * localObject2[0]) / 2 * 2;
      paramInt2 = (int)(f2 * localObject2[4]) / 2 * 2;
      localObject1 = Bitmap.createScaledBitmap(paramBitmap, paramInt1, paramInt2, true);
    }
    catch (Exception localException)
    {
      FLogger.e("BitmapUtils", localException);
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localOutOfMemoryError.printStackTrace();
    }
    Bitmap localBitmap = null;
    if ((paramBoolean1) && (localBitmap != null) && (localBitmap != paramBitmap)) {
      paramBitmap.recycle();
    }
    return localBitmap;
  }
  
  public static Bitmap cropBitmapCenter(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramBitmap != null) && (paramInt1 > 0) && (paramInt2 > 0) && (paramInt3 > 0))
    {
      if (paramInt4 <= 0) {
        return paramBitmap;
      }
      int i = 0;
      int j;
      if ((paramInt1 > paramInt3) && (paramInt2 > paramInt4))
      {
        float f1 = paramInt3 / paramInt4;
        float f2 = paramInt1 / paramInt2;
        if (f1 > f2)
        {
          paramInt3 = (paramInt3 * paramInt2 - paramInt4 * paramInt1) / (paramInt3 * 2);
          paramInt4 = paramInt2 - paramInt3;
          paramInt2 = 0;
        }
        else if (f1 < f2)
        {
          j = (paramInt1 * paramInt4 - paramInt3 * paramInt2) / (paramInt4 * 2);
          paramInt1 -= j;
          paramInt3 = i;
          paramInt4 = paramInt2;
          paramInt2 = j;
        }
        else
        {
          return paramBitmap;
        }
      }
      else if ((paramInt1 > paramInt3) && (paramInt2 < paramInt4))
      {
        j = (paramInt1 - paramInt3) / 2;
        paramInt1 -= j;
        paramInt3 = i;
        paramInt4 = paramInt2;
        paramInt2 = j;
      }
      else
      {
        if ((paramInt1 >= paramInt3) || (paramInt2 <= paramInt4)) {
          break label246;
        }
        paramInt3 = (paramInt2 - paramInt4) / 2;
        paramInt4 = paramInt2 - paramInt3;
        paramInt2 = 0;
      }
      Object localObject1;
      try
      {
        Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, paramInt2, paramInt3, paramInt1 - paramInt2, paramInt4 - paramInt3);
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        FLogger.e("BitmapUtils", localOutOfMemoryError);
        localObject1 = null;
      }
      Object localObject2 = paramBitmap;
      if (localObject1 != null)
      {
        localObject2 = paramBitmap;
        if (!localObject1.equals(paramBitmap))
        {
          paramBitmap.recycle();
          localObject2 = localObject1;
        }
      }
      return (Bitmap)localObject2;
      label246:
      return paramBitmap;
    }
    return paramBitmap;
  }
  
  public static Bitmap decodeBitmap(File paramFile, QImageParams paramQImageParams)
  {
    if (paramFile == null) {
      return null;
    }
    a(new a()
    {
      public InputStream a()
        throws IOException
      {
        return new FileInputStream(this.a);
      }
    }, paramQImageParams);
  }
  
  public static Bitmap decodeBitmap(byte[] paramArrayOfByte, QImageParams paramQImageParams)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    a(new a()
    {
      public InputStream a()
        throws IOException
      {
        return new ByteArrayInputStream(this.a);
      }
    }, paramQImageParams);
  }
  
  public static Bitmap drawableToBitmap(Drawable paramDrawable)
  {
    Object localObject = null;
    Bitmap localBitmap = null;
    if (paramDrawable != null)
    {
      localObject = localBitmap;
      try
      {
        int i = paramDrawable.getIntrinsicWidth();
        localObject = localBitmap;
        int j = paramDrawable.getIntrinsicHeight();
        localObject = localBitmap;
        paramDrawable.setBounds(0, 0, i, j);
        localObject = localBitmap;
        localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_4444);
        localObject = localBitmap;
        paramDrawable.draw(new Canvas(localBitmap));
        return localBitmap;
      }
      catch (Throwable paramDrawable)
      {
        FLogger.e("BitmapUtils", paramDrawable);
      }
    }
    return (Bitmap)localObject;
  }
  
  public static Bitmap gaussianBlur(Bitmap paramBitmap)
  {
    if (paramBitmap == null) {
      return null;
    }
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int k = i * j;
    try
    {
      int[] arrayOfInt1 = new int[k];
      int[] arrayOfInt2 = new int[k];
      paramBitmap.getPixels(arrayOfInt1, 0, i, 0, 0, i, j);
      a(arrayOfInt1, arrayOfInt2, i, j);
      a(arrayOfInt2, arrayOfInt1, i, j);
      try
      {
        paramBitmap = Bitmap.createBitmap(arrayOfInt1, i, j, Bitmap.Config.ARGB_8888);
        return paramBitmap;
      }
      catch (OutOfMemoryError paramBitmap)
      {
        paramBitmap.printStackTrace();
        return null;
      }
      return null;
    }
    catch (OutOfMemoryError paramBitmap)
    {
      paramBitmap.printStackTrace();
    }
  }
  
  public static int getAllocationByteCount(Bitmap paramBitmap)
  {
    if ((paramBitmap != null) && (Build.VERSION.SDK_INT >= 19)) {
      return paramBitmap.getAllocationByteCount();
    }
    return 0;
  }
  
  /* Error */
  public static Bitmap getBitmap(InputStream paramInputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore_3
    //   5: aload_0
    //   6: ifnull +113 -> 119
    //   9: aload_3
    //   10: astore_1
    //   11: aload 4
    //   13: astore_2
    //   14: aload_0
    //   15: iconst_0
    //   16: iconst_m1
    //   17: invokestatic 415	com/tencent/common/utils/FileUtilsF:read	(Ljava/io/InputStream;II)Ljava/nio/ByteBuffer;
    //   20: astore 5
    //   22: aload 5
    //   24: ifnull +61 -> 85
    //   27: aload_3
    //   28: astore_1
    //   29: aload 4
    //   31: astore_2
    //   32: aload 5
    //   34: invokevirtual 420	java/nio/ByteBuffer:position	()I
    //   37: iconst_1
    //   38: if_icmpge +6 -> 44
    //   41: goto +44 -> 85
    //   44: aload_3
    //   45: astore_1
    //   46: aload 4
    //   48: astore_2
    //   49: aload 5
    //   51: invokevirtual 423	java/nio/ByteBuffer:array	()[B
    //   54: iconst_0
    //   55: aload 5
    //   57: invokevirtual 420	java/nio/ByteBuffer:position	()I
    //   60: invokestatic 427	android/graphics/BitmapFactory:decodeByteArray	([BII)Landroid/graphics/Bitmap;
    //   63: astore_3
    //   64: aload_3
    //   65: astore_1
    //   66: aload_3
    //   67: astore_2
    //   68: invokestatic 431	com/tencent/common/utils/FileUtilsF:getInstance	()Lcom/tencent/common/utils/FileUtilsF;
    //   71: aload 5
    //   73: invokevirtual 435	com/tencent/common/utils/FileUtilsF:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   76: pop
    //   77: aload_3
    //   78: astore_1
    //   79: aload_0
    //   80: invokevirtual 145	java/io/InputStream:close	()V
    //   83: aload_1
    //   84: areturn
    //   85: aload_0
    //   86: invokevirtual 145	java/io/InputStream:close	()V
    //   89: aconst_null
    //   90: areturn
    //   91: astore_1
    //   92: goto +21 -> 113
    //   95: astore_2
    //   96: aload_2
    //   97: invokevirtual 345	java/lang/OutOfMemoryError:printStackTrace	()V
    //   100: goto -21 -> 79
    //   103: astore_1
    //   104: aload_1
    //   105: invokevirtual 436	java/lang/Exception:printStackTrace	()V
    //   108: aload_2
    //   109: astore_1
    //   110: goto -31 -> 79
    //   113: aload_0
    //   114: invokevirtual 145	java/io/InputStream:close	()V
    //   117: aload_1
    //   118: athrow
    //   119: aconst_null
    //   120: areturn
    //   121: astore_0
    //   122: aload_1
    //   123: areturn
    //   124: astore_0
    //   125: aconst_null
    //   126: areturn
    //   127: astore_0
    //   128: goto -11 -> 117
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	paramInputStream	InputStream
    //   10	74	1	localBitmap1	Bitmap
    //   91	1	1	localObject1	Object
    //   103	2	1	localException	Exception
    //   109	14	1	localObject2	Object
    //   13	55	2	localObject3	Object
    //   95	14	2	localOutOfMemoryError	OutOfMemoryError
    //   4	74	3	localBitmap2	Bitmap
    //   1	46	4	localObject4	Object
    //   20	52	5	localByteBuffer	java.nio.ByteBuffer
    // Exception table:
    //   from	to	target	type
    //   14	22	91	finally
    //   32	41	91	finally
    //   49	64	91	finally
    //   68	77	91	finally
    //   96	100	91	finally
    //   104	108	91	finally
    //   14	22	95	java/lang/OutOfMemoryError
    //   32	41	95	java/lang/OutOfMemoryError
    //   49	64	95	java/lang/OutOfMemoryError
    //   68	77	95	java/lang/OutOfMemoryError
    //   14	22	103	java/lang/Exception
    //   32	41	103	java/lang/Exception
    //   49	64	103	java/lang/Exception
    //   68	77	103	java/lang/Exception
    //   79	83	121	java/io/IOException
    //   85	89	124	java/io/IOException
    //   113	117	127	java/io/IOException
  }
  
  public static int getBitmapSize(Bitmap paramBitmap)
  {
    if (paramBitmap == null) {
      return 0;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 85, localByteArrayOutputStream);
    try
    {
      localByteArrayOutputStream.close();
    }
    catch (IOException paramBitmap)
    {
      paramBitmap.printStackTrace();
    }
    return localByteArrayOutputStream.size();
  }
  
  public static Bitmap getBitmaptemp(byte[] paramArrayOfByte)
    throws OutOfMemoryError
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0)) {
      try
      {
        paramArrayOfByte = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
        return paramArrayOfByte;
      }
      catch (OutOfMemoryError paramArrayOfByte)
      {
        paramArrayOfByte.printStackTrace();
        throw paramArrayOfByte;
      }
    }
    return null;
  }
  
  public static BitmapDrawable getBmpDrawable(Resources paramResources, Bitmap paramBitmap)
  {
    try
    {
      if (b == null) {
        b = BitmapDrawable.class.getConstructor(new Class[] { Resources.class, Bitmap.class });
      }
      paramResources = (BitmapDrawable)b.newInstance(new Object[] { paramResources, paramBitmap });
      return paramResources;
    }
    catch (Exception paramResources)
    {
      for (;;) {}
    }
    return new BitmapDrawable(paramBitmap);
  }
  
  public static int getGlMaxTextureSize()
  {
    try
    {
      if (jdField_a_of_type_Int == 0) {
        if (Build.VERSION.SDK_INT < 17) {
          jdField_a_of_type_Int = GL_MAX_TEXTURE_SIZE_UNDETERMINED;
        } else {
          try
          {
            EGLDisplay localEGLDisplay = EGL14.eglGetDisplay(0);
            Object localObject2 = new int[2];
            EGL14.eglInitialize(localEGLDisplay, (int[])localObject2, 0, (int[])localObject2, 1);
            localObject2 = new EGLConfig[1];
            Object localObject3 = new int[1];
            EGL14.eglChooseConfig(localEGLDisplay, new int[] { 12351, 12430, 12329, 0, 12352, 4, 12339, 1, 12344 }, 0, (EGLConfig[])localObject2, 0, 1, (int[])localObject3, 0);
            i = localObject3[0];
            localObject3 = localObject2[0];
            localObject2 = EGL14.eglCreatePbufferSurface(localEGLDisplay, (EGLConfig)localObject3, new int[] { 12375, 64, 12374, 64, 12344 }, 0);
            localObject3 = EGL14.eglCreateContext(localEGLDisplay, (EGLConfig)localObject3, EGL14.EGL_NO_CONTEXT, new int[] { 12440, 2, 12344 }, 0);
            EGL14.eglMakeCurrent(localEGLDisplay, (EGLSurface)localObject2, (EGLSurface)localObject2, (EGLContext)localObject3);
            int[] arrayOfInt = new int[1];
            GLES20.glGetIntegerv(3379, arrayOfInt, 0);
            jdField_a_of_type_Int = arrayOfInt[0];
            EGL14.eglMakeCurrent(localEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(localEGLDisplay, (EGLSurface)localObject2);
            EGL14.eglDestroyContext(localEGLDisplay, (EGLContext)localObject3);
            EGL14.eglTerminate(localEGLDisplay);
          }
          catch (Throwable localThrowable)
          {
            localThrowable.printStackTrace();
          }
        }
      }
      int i = jdField_a_of_type_Int;
      return i;
    }
    finally {}
  }
  
  /* Error */
  public static int getImageType(String paramString)
  {
    // Byte code:
    //   0: bipush 20
    //   2: newarray <illegal type>
    //   4: astore 6
    //   6: iconst_0
    //   7: istore_1
    //   8: aconst_null
    //   9: astore 4
    //   11: aconst_null
    //   12: astore 5
    //   14: new 529	java/io/FileInputStream
    //   17: dup
    //   18: aload_0
    //   19: invokespecial 532	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   22: astore_0
    //   23: aload_0
    //   24: aload 6
    //   26: invokevirtual 535	java/io/FileInputStream:read	([B)I
    //   29: istore_3
    //   30: aload_0
    //   31: invokevirtual 536	java/io/FileInputStream:close	()V
    //   34: iload_3
    //   35: bipush 20
    //   37: if_icmpne +111 -> 148
    //   40: aload 6
    //   42: iconst_0
    //   43: baload
    //   44: bipush 82
    //   46: if_icmpne +102 -> 148
    //   49: aload 6
    //   51: iconst_1
    //   52: baload
    //   53: bipush 73
    //   55: if_icmpne +93 -> 148
    //   58: aload 6
    //   60: iconst_2
    //   61: baload
    //   62: bipush 70
    //   64: if_icmpne +84 -> 148
    //   67: aload 6
    //   69: iconst_3
    //   70: baload
    //   71: bipush 70
    //   73: if_icmpne +75 -> 148
    //   76: aload 6
    //   78: bipush 8
    //   80: baload
    //   81: bipush 87
    //   83: if_icmpne +65 -> 148
    //   86: aload 6
    //   88: bipush 9
    //   90: baload
    //   91: bipush 69
    //   93: if_icmpne +55 -> 148
    //   96: aload 6
    //   98: bipush 10
    //   100: baload
    //   101: bipush 66
    //   103: if_icmpne +45 -> 148
    //   106: aload 6
    //   108: bipush 11
    //   110: baload
    //   111: bipush 80
    //   113: if_icmpne +35 -> 148
    //   116: aload 6
    //   118: bipush 12
    //   120: baload
    //   121: bipush 86
    //   123: if_icmpne +25 -> 148
    //   126: aload 6
    //   128: bipush 13
    //   130: baload
    //   131: bipush 80
    //   133: if_icmpne +15 -> 148
    //   136: aload 6
    //   138: bipush 14
    //   140: baload
    //   141: bipush 56
    //   143: if_icmpne +5 -> 148
    //   146: iconst_2
    //   147: ireturn
    //   148: iconst_4
    //   149: istore_2
    //   150: iload_3
    //   151: bipush 6
    //   153: if_icmplt +60 -> 213
    //   156: aload 6
    //   158: iconst_0
    //   159: baload
    //   160: bipush 83
    //   162: if_icmpne +51 -> 213
    //   165: aload 6
    //   167: iconst_1
    //   168: baload
    //   169: bipush 72
    //   171: if_icmpne +42 -> 213
    //   174: aload 6
    //   176: iconst_2
    //   177: baload
    //   178: bipush 65
    //   180: if_icmpne +33 -> 213
    //   183: aload 6
    //   185: iconst_3
    //   186: baload
    //   187: bipush 82
    //   189: if_icmpne +24 -> 213
    //   192: aload 6
    //   194: iconst_4
    //   195: baload
    //   196: bipush 80
    //   198: if_icmpne +15 -> 213
    //   201: aload 6
    //   203: iconst_5
    //   204: baload
    //   205: bipush 80
    //   207: if_icmpne +6 -> 213
    //   210: bipush 7
    //   212: ireturn
    //   213: iload_3
    //   214: iconst_3
    //   215: if_icmplt +274 -> 489
    //   218: aload 6
    //   220: iconst_0
    //   221: baload
    //   222: bipush 71
    //   224: if_icmpne +26 -> 250
    //   227: aload 6
    //   229: iconst_1
    //   230: baload
    //   231: bipush 73
    //   233: if_icmpne +17 -> 250
    //   236: aload 6
    //   238: iconst_2
    //   239: baload
    //   240: bipush 70
    //   242: if_icmpne +8 -> 250
    //   245: iload_2
    //   246: istore_1
    //   247: goto +5 -> 252
    //   250: iconst_0
    //   251: istore_1
    //   252: aload 6
    //   254: iconst_0
    //   255: baload
    //   256: sipush 255
    //   259: iand
    //   260: sipush 255
    //   263: if_icmpne +33 -> 296
    //   266: aload 6
    //   268: iconst_1
    //   269: baload
    //   270: sipush 255
    //   273: iand
    //   274: sipush 216
    //   277: if_icmpne +19 -> 296
    //   280: aload 6
    //   282: iconst_2
    //   283: baload
    //   284: sipush 255
    //   287: iand
    //   288: sipush 255
    //   291: if_icmpne +5 -> 296
    //   294: iconst_1
    //   295: ireturn
    //   296: iload_3
    //   297: iconst_3
    //   298: if_icmple +58 -> 356
    //   301: aload 6
    //   303: iconst_0
    //   304: baload
    //   305: sipush 255
    //   308: iand
    //   309: sipush 137
    //   312: if_icmpne +44 -> 356
    //   315: aload 6
    //   317: iconst_1
    //   318: baload
    //   319: sipush 255
    //   322: iand
    //   323: bipush 80
    //   325: if_icmpne +31 -> 356
    //   328: aload 6
    //   330: iconst_2
    //   331: baload
    //   332: sipush 255
    //   335: iand
    //   336: bipush 78
    //   338: if_icmpne +18 -> 356
    //   341: aload 6
    //   343: iconst_3
    //   344: baload
    //   345: sipush 255
    //   348: iand
    //   349: bipush 71
    //   351: if_icmpne +5 -> 356
    //   354: iconst_3
    //   355: ireturn
    //   356: iload_3
    //   357: iconst_3
    //   358: if_icmple +53 -> 411
    //   361: aload 6
    //   363: iconst_0
    //   364: baload
    //   365: sipush 255
    //   368: iand
    //   369: bipush 73
    //   371: if_icmpne +40 -> 411
    //   374: aload 6
    //   376: iconst_1
    //   377: baload
    //   378: sipush 255
    //   381: iand
    //   382: bipush 73
    //   384: if_icmpne +27 -> 411
    //   387: aload 6
    //   389: iconst_2
    //   390: baload
    //   391: sipush 255
    //   394: iand
    //   395: bipush 42
    //   397: if_icmpne +14 -> 411
    //   400: aload 6
    //   402: iconst_3
    //   403: baload
    //   404: sipush 255
    //   407: iand
    //   408: ifeq +58 -> 466
    //   411: iload_3
    //   412: iconst_3
    //   413: if_icmple +55 -> 468
    //   416: aload 6
    //   418: iconst_0
    //   419: baload
    //   420: sipush 255
    //   423: iand
    //   424: bipush 77
    //   426: if_icmpne +42 -> 468
    //   429: aload 6
    //   431: iconst_1
    //   432: baload
    //   433: sipush 255
    //   436: iand
    //   437: bipush 77
    //   439: if_icmpne +29 -> 468
    //   442: aload 6
    //   444: iconst_2
    //   445: baload
    //   446: sipush 255
    //   449: iand
    //   450: ifne +18 -> 468
    //   453: aload 6
    //   455: iconst_3
    //   456: baload
    //   457: sipush 255
    //   460: iand
    //   461: bipush 42
    //   463: if_icmpne +5 -> 468
    //   466: iconst_5
    //   467: ireturn
    //   468: aload 6
    //   470: iconst_0
    //   471: baload
    //   472: bipush 66
    //   474: if_icmpne +15 -> 489
    //   477: aload 6
    //   479: iconst_1
    //   480: baload
    //   481: bipush 77
    //   483: if_icmpne +6 -> 489
    //   486: bipush 6
    //   488: ireturn
    //   489: iload_1
    //   490: ireturn
    //   491: astore 4
    //   493: goto +11 -> 504
    //   496: goto +22 -> 518
    //   499: astore 4
    //   501: aload 5
    //   503: astore_0
    //   504: aload_0
    //   505: ifnull +10 -> 515
    //   508: aload_0
    //   509: invokevirtual 536	java/io/FileInputStream:close	()V
    //   512: goto +3 -> 515
    //   515: aload 4
    //   517: athrow
    //   518: aload_0
    //   519: ifnull +9 -> 528
    //   522: aload_0
    //   523: invokevirtual 536	java/io/FileInputStream:close	()V
    //   526: iconst_0
    //   527: ireturn
    //   528: iconst_0
    //   529: ireturn
    //   530: astore_0
    //   531: aload 4
    //   533: astore_0
    //   534: goto -16 -> 518
    //   537: astore 4
    //   539: goto -43 -> 496
    //   542: astore_0
    //   543: iconst_0
    //   544: ireturn
    //   545: astore_0
    //   546: iconst_0
    //   547: ireturn
    //   548: astore_0
    //   549: iconst_0
    //   550: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	551	0	paramString	String
    //   7	483	1	i	int
    //   149	97	2	j	int
    //   29	385	3	k	int
    //   9	1	4	localObject1	Object
    //   491	1	4	localObject2	Object
    //   499	33	4	localObject3	Object
    //   537	1	4	localException	Exception
    //   12	490	5	localObject4	Object
    //   4	474	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   23	30	491	finally
    //   14	23	499	finally
    //   14	23	530	java/lang/Exception
    //   23	30	537	java/lang/Exception
    //   30	34	542	java/lang/Exception
    //   508	512	545	java/lang/Exception
    //   522	526	548	java/lang/Exception
  }
  
  public static int getImageType(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    int j = 5;
    if ((i >= 20) && (paramArrayOfByte[0] == 82) && (paramArrayOfByte[1] == 73) && (paramArrayOfByte[2] == 70) && (paramArrayOfByte[3] == 70) && (paramArrayOfByte[8] == 87) && (paramArrayOfByte[9] == 69) && (paramArrayOfByte[10] == 66) && (paramArrayOfByte[11] == 80) && (paramArrayOfByte[12] == 86) && (paramArrayOfByte[13] == 80) && (paramArrayOfByte[14] == 56)) {
      return 2;
    }
    int k = paramArrayOfByte.length;
    i = 4;
    if ((k >= 6) && (paramArrayOfByte[0] == 83) && (paramArrayOfByte[1] == 72) && (paramArrayOfByte[2] == 65) && (paramArrayOfByte[3] == 82) && (paramArrayOfByte[4] == 80) && (paramArrayOfByte[5] == 80)) {
      return 7;
    }
    if (paramArrayOfByte.length >= 3)
    {
      if ((paramArrayOfByte[0] != 71) || (paramArrayOfByte[1] != 73) || (paramArrayOfByte[2] != 70)) {
        i = 0;
      }
      if (((paramArrayOfByte[0] & 0xFF) == 255) && ((paramArrayOfByte[1] & 0xFF) == 216) && ((paramArrayOfByte[2] & 0xFF) == 255)) {
        return 1;
      }
      if ((paramArrayOfByte.length > 3) && ((paramArrayOfByte[0] & 0xFF) == 137) && ((paramArrayOfByte[1] & 0xFF) == 80) && ((paramArrayOfByte[2] & 0xFF) == 78) && ((paramArrayOfByte[3] & 0xFF) == 71)) {
        return 3;
      }
      if ((paramArrayOfByte.length <= 3) || ((paramArrayOfByte[0] & 0xFF) != 73) || ((paramArrayOfByte[1] & 0xFF) != 73) || ((paramArrayOfByte[2] & 0xFF) != 42) || ((paramArrayOfByte[3] & 0xFF) != 0))
      {
        if ((paramArrayOfByte.length > 3) && ((paramArrayOfByte[0] & 0xFF) == 77) && ((paramArrayOfByte[1] & 0xFF) == 77) && ((paramArrayOfByte[2] & 0xFF) == 0) && ((paramArrayOfByte[3] & 0xFF) == 42)) {
          return 5;
        }
        if ((paramArrayOfByte[0] == 66) && (paramArrayOfByte[1] == 77)) {
          return 6;
        }
        return i;
      }
    }
    else
    {
      j = 0;
    }
    return j;
  }
  
  public static BitmapDrawable getMirrorDrawable(Context paramContext, BitmapDrawable paramBitmapDrawable)
  {
    if ((paramBitmapDrawable != null) && (paramBitmapDrawable.getBitmap() != null))
    {
      if (paramBitmapDrawable.getBitmap().isRecycled()) {
        return null;
      }
      paramBitmapDrawable = paramBitmapDrawable.getBitmap();
      Matrix localMatrix = new Matrix();
      localMatrix.postRotate(180.0F);
      try
      {
        paramBitmapDrawable = Bitmap.createBitmap(paramBitmapDrawable, 0, 0, paramBitmapDrawable.getWidth(), paramBitmapDrawable.getHeight(), localMatrix, true);
        return new BitmapDrawable(paramContext.getResources(), paramBitmapDrawable);
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static NinePatchDrawable getNinePatchDrawable(Resources paramResources, Bitmap paramBitmap, byte[] paramArrayOfByte, Rect paramRect)
  {
    try
    {
      if (jdField_a_of_type_JavaLangReflectConstructor == null) {
        jdField_a_of_type_JavaLangReflectConstructor = NinePatchDrawable.class.getConstructor(new Class[] { Resources.class, Bitmap.class, byte[].class, Rect.class, String.class });
      }
      paramResources = (NinePatchDrawable)jdField_a_of_type_JavaLangReflectConstructor.newInstance(new Object[] { paramResources, paramBitmap, paramArrayOfByte, paramRect, null });
      return paramResources;
    }
    catch (Exception paramResources)
    {
      for (;;) {}
    }
    return new NinePatchDrawable(paramBitmap, paramArrayOfByte, paramRect, null);
  }
  
  public static int getPicOrientation(FileDescriptor paramFileDescriptor)
  {
    if (paramFileDescriptor == null) {
      return 0;
    }
    try
    {
      if (Build.VERSION.SDK_INT >= 24)
      {
        int i = new ExifInterface(paramFileDescriptor).getAttributeInt("Orientation", 1);
        if (i != 3)
        {
          if (i != 6)
          {
            if (i != 8) {
              return 0;
            }
            return 270;
          }
          return 90;
        }
        return 180;
      }
      return 0;
    }
    catch (IOException paramFileDescriptor)
    {
      paramFileDescriptor.printStackTrace();
    }
    return 0;
  }
  
  public static int getPicOrientation(InputStream paramInputStream)
  {
    if (paramInputStream == null) {
      return 0;
    }
    try
    {
      if (Build.VERSION.SDK_INT >= 24)
      {
        int i = new ExifInterface(paramInputStream).getAttributeInt("Orientation", 1);
        if (i != 3)
        {
          if (i != 6)
          {
            if (i != 8) {
              return 0;
            }
            return 270;
          }
          return 90;
        }
        return 180;
      }
      return 0;
    }
    catch (Throwable paramInputStream)
    {
      paramInputStream.printStackTrace();
    }
    return 0;
  }
  
  public static int getPicOrientation(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return 0;
    }
    try
    {
      int i = new ExifInterface(paramString).getAttributeInt("Orientation", 1);
      if (i != 3)
      {
        if (i != 6)
        {
          if (i != 8) {
            return 0;
          }
          return 270;
        }
        return 90;
      }
      return 180;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return 0;
  }
  
  public static Bitmap getZoomImage(Bitmap paramBitmap, int paramInt, String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    Object localObject = null;
    if (!bool)
    {
      if (paramBitmap == null) {
        return null;
      }
      int i = 0;
      try
      {
        int j = new ExifInterface(paramString).getAttributeInt("Orientation", 1);
        if (j != 3)
        {
          if (j != 6)
          {
            if (j == 8) {
              i = 270;
            }
          }
          else {
            i = 90;
          }
        }
        else {
          i = 180;
        }
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      if (paramBitmap.getWidth() <= paramInt)
      {
        paramString = (String)localObject;
        if (paramBitmap.getHeight() <= paramInt) {}
      }
      else
      {
        float f1 = paramInt;
        float f2 = f1 / paramBitmap.getWidth();
        float f3 = f1 / paramBitmap.getHeight();
        f1 = f3;
        if (f2 < f3) {
          f1 = f2;
        }
        paramString = new Matrix();
        if (i != 0) {
          paramString.postRotate(i);
        }
        paramString.postScale(f1, f1);
      }
      try
      {
        paramString = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), paramString, true);
        return paramString;
      }
      catch (Exception paramBitmap)
      {
        paramBitmap.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static boolean isAvailable(Bitmap paramBitmap)
  {
    return (paramBitmap != null) && (!paramBitmap.isRecycled());
  }
  
  public static boolean isBlackWhite(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      int k = paramBitmap.getPixel(0, 0) & 0xE0E0E0;
      if ((k != 14737632) && (k != 0)) {
        return false;
      }
      int m = paramBitmap.getWidth();
      int n = paramBitmap.getHeight();
      int i = 0;
      while (i < n)
      {
        int[] arrayOfInt = new int[m];
        try
        {
          paramBitmap.getPixels(arrayOfInt, 0, m, 0, i, m, 1);
          int j = 0;
          while (j < m)
          {
            if ((arrayOfInt[j] & 0xE0E0E0) != k) {
              return false;
            }
            j += 1;
          }
          i += 1;
        }
        catch (Exception paramBitmap)
        {
          FLogger.d("BitmapUtils", "isTransparent error");
          paramBitmap.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }
  
  /* Error */
  public static boolean isGif(String paramString)
  {
    // Byte code:
    //   0: iconst_3
    //   1: newarray <illegal type>
    //   3: astore 6
    //   5: aconst_null
    //   6: astore 4
    //   8: aconst_null
    //   9: astore 5
    //   11: new 529	java/io/FileInputStream
    //   14: dup
    //   15: aload_0
    //   16: invokespecial 532	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   19: astore_0
    //   20: aload_0
    //   21: aload 6
    //   23: invokevirtual 535	java/io/FileInputStream:read	([B)I
    //   26: istore_1
    //   27: aload_0
    //   28: invokevirtual 536	java/io/FileInputStream:close	()V
    //   31: iload_1
    //   32: iconst_3
    //   33: if_icmpne +40 -> 73
    //   36: aload 6
    //   38: iconst_0
    //   39: baload
    //   40: istore_1
    //   41: aload 6
    //   43: iconst_1
    //   44: baload
    //   45: istore_2
    //   46: aload 6
    //   48: iconst_2
    //   49: baload
    //   50: istore_3
    //   51: iload_1
    //   52: bipush 71
    //   54: if_icmpne +17 -> 71
    //   57: iload_2
    //   58: bipush 73
    //   60: if_icmpne +11 -> 71
    //   63: iload_3
    //   64: bipush 70
    //   66: if_icmpne +5 -> 71
    //   69: iconst_1
    //   70: ireturn
    //   71: iconst_0
    //   72: ireturn
    //   73: iconst_0
    //   74: ireturn
    //   75: astore 4
    //   77: goto +11 -> 88
    //   80: goto +22 -> 102
    //   83: astore 4
    //   85: aload 5
    //   87: astore_0
    //   88: aload_0
    //   89: ifnull +10 -> 99
    //   92: aload_0
    //   93: invokevirtual 536	java/io/FileInputStream:close	()V
    //   96: goto +3 -> 99
    //   99: aload 4
    //   101: athrow
    //   102: aload_0
    //   103: ifnull +9 -> 112
    //   106: aload_0
    //   107: invokevirtual 536	java/io/FileInputStream:close	()V
    //   110: iconst_0
    //   111: ireturn
    //   112: iconst_0
    //   113: ireturn
    //   114: astore_0
    //   115: aload 4
    //   117: astore_0
    //   118: goto -16 -> 102
    //   121: astore 4
    //   123: goto -43 -> 80
    //   126: astore_0
    //   127: iconst_0
    //   128: ireturn
    //   129: astore_0
    //   130: iconst_0
    //   131: ireturn
    //   132: astore_0
    //   133: iconst_0
    //   134: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	135	0	paramString	String
    //   26	29	1	i	int
    //   45	16	2	j	int
    //   50	17	3	k	int
    //   6	1	4	localObject1	Object
    //   75	1	4	localObject2	Object
    //   83	33	4	localObject3	Object
    //   121	1	4	localException	Exception
    //   9	77	5	localObject4	Object
    //   3	44	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   20	27	75	finally
    //   11	20	83	finally
    //   11	20	114	java/lang/Exception
    //   20	27	121	java/lang/Exception
    //   27	31	126	java/lang/Exception
    //   92	96	129	java/lang/Exception
    //   106	110	132	java/lang/Exception
  }
  
  public static boolean isHighlightWallPaper(Bitmap paramBitmap)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int[] arrayOfInt1 = new int[9];
    arrayOfInt1[0] = 0;
    arrayOfInt1[1] = (j / 8);
    arrayOfInt1[2] = (j * 2 / 8);
    arrayOfInt1[3] = (j * 3 / 8);
    arrayOfInt1[4] = (j * 4 / 8);
    arrayOfInt1[5] = (j * 5 / 8);
    arrayOfInt1[6] = (j * 6 / 8);
    arrayOfInt1[7] = (j * 7 / 8);
    arrayOfInt1[8] = (j - 1);
    int[] arrayOfInt2 = new int[9];
    arrayOfInt2[0] = 0;
    arrayOfInt2[1] = (i / 8);
    arrayOfInt2[2] = (i * 2 / 8);
    arrayOfInt2[3] = (i * 3 / 8);
    arrayOfInt2[4] = (i * 4 / 8);
    arrayOfInt2[5] = (i * 5 / 8);
    arrayOfInt2[6] = (i * 6 / 8);
    arrayOfInt2[7] = (i * 7 / 8);
    arrayOfInt2[8] = (i - 1);
    double d1 = 0.0D;
    j = 0;
    i = 0;
    while (j < arrayOfInt2.length)
    {
      int k = 0;
      while (k < arrayOfInt1.length)
      {
        Integer localInteger = Integer.valueOf(paramBitmap.getPixel(arrayOfInt2[j], arrayOfInt1[k]));
        int m = localInteger.intValue();
        int n = localInteger.intValue();
        int i1 = localInteger.intValue();
        d2 = (m | 0xFF00FFFF) >> 16 & 0xFF;
        Double.isNaN(d2);
        double d3 = (n | 0xFFFF00FF) >> 8 & 0xFF;
        Double.isNaN(d3);
        double d4 = (i1 | 0xFF00) & 0xFF;
        Double.isNaN(d4);
        d1 = d1 + d2 * 0.299D + d3 * 0.587D + d4 * 0.114D;
        k += 1;
        i += 1;
      }
      j += 1;
    }
    double d2 = i;
    Double.isNaN(d2);
    return (int)(d1 / d2) > 180.0D;
  }
  
  public static boolean isTransparent(Bitmap paramBitmap, int paramInt)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.hasAlpha())
      {
        if (paramBitmap.getConfig() == Bitmap.Config.RGB_565) {
          return false;
        }
        int i;
        if (paramInt >= 0)
        {
          i = paramInt;
          if (paramInt <= 255) {}
        }
        else
        {
          i = 0;
        }
        int k = paramBitmap.getWidth();
        int m = paramBitmap.getHeight();
        paramInt = 0;
        for (;;)
        {
          if (paramInt >= m) {
            break label130;
          }
          int[] arrayOfInt = new int[k];
          try
          {
            paramBitmap.getPixels(arrayOfInt, 0, k, 0, paramInt, k, 1);
            int j = 0;
            while (j < k)
            {
              if (arrayOfInt[j] >>> 24 > i) {
                return false;
              }
              j += 1;
            }
            paramInt += 1;
          }
          catch (Exception paramBitmap)
          {
            FLogger.d("BitmapUtils", "isTransparent error");
            paramBitmap.printStackTrace();
            return false;
          }
        }
      }
      return false;
    }
    label130:
    return true;
  }
  
  /* Error */
  public static boolean isWebP(File paramFile)
  {
    // Byte code:
    //   0: bipush 20
    //   2: newarray <illegal type>
    //   4: astore 4
    //   6: aconst_null
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: new 529	java/io/FileInputStream
    //   13: dup
    //   14: aload_0
    //   15: invokespecial 641	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   18: astore_0
    //   19: aload_0
    //   20: aload 4
    //   22: invokevirtual 535	java/io/FileInputStream:read	([B)I
    //   25: istore_1
    //   26: aload_0
    //   27: invokevirtual 536	java/io/FileInputStream:close	()V
    //   30: iload_1
    //   31: bipush 20
    //   33: if_icmpne +123 -> 156
    //   36: aload 4
    //   38: iconst_0
    //   39: baload
    //   40: bipush 82
    //   42: if_icmpne +112 -> 154
    //   45: aload 4
    //   47: iconst_1
    //   48: baload
    //   49: bipush 73
    //   51: if_icmpne +103 -> 154
    //   54: aload 4
    //   56: iconst_2
    //   57: baload
    //   58: bipush 70
    //   60: if_icmpne +94 -> 154
    //   63: aload 4
    //   65: iconst_3
    //   66: baload
    //   67: bipush 70
    //   69: if_icmpeq +5 -> 74
    //   72: iconst_0
    //   73: ireturn
    //   74: aload 4
    //   76: bipush 8
    //   78: baload
    //   79: bipush 87
    //   81: if_icmpne +71 -> 152
    //   84: aload 4
    //   86: bipush 9
    //   88: baload
    //   89: bipush 69
    //   91: if_icmpne +61 -> 152
    //   94: aload 4
    //   96: bipush 10
    //   98: baload
    //   99: bipush 66
    //   101: if_icmpne +51 -> 152
    //   104: aload 4
    //   106: bipush 11
    //   108: baload
    //   109: bipush 80
    //   111: if_icmpeq +5 -> 116
    //   114: iconst_0
    //   115: ireturn
    //   116: aload 4
    //   118: bipush 12
    //   120: baload
    //   121: bipush 86
    //   123: if_icmpne +27 -> 150
    //   126: aload 4
    //   128: bipush 13
    //   130: baload
    //   131: bipush 80
    //   133: if_icmpne +17 -> 150
    //   136: aload 4
    //   138: bipush 14
    //   140: baload
    //   141: bipush 56
    //   143: if_icmpeq +5 -> 148
    //   146: iconst_0
    //   147: ireturn
    //   148: iconst_1
    //   149: ireturn
    //   150: iconst_0
    //   151: ireturn
    //   152: iconst_0
    //   153: ireturn
    //   154: iconst_0
    //   155: ireturn
    //   156: iconst_0
    //   157: ireturn
    //   158: astore_2
    //   159: goto +9 -> 168
    //   162: goto +19 -> 181
    //   165: astore_2
    //   166: aload_3
    //   167: astore_0
    //   168: aload_0
    //   169: ifnull +10 -> 179
    //   172: aload_0
    //   173: invokevirtual 536	java/io/FileInputStream:close	()V
    //   176: goto +3 -> 179
    //   179: aload_2
    //   180: athrow
    //   181: aload_0
    //   182: ifnull +9 -> 191
    //   185: aload_0
    //   186: invokevirtual 536	java/io/FileInputStream:close	()V
    //   189: iconst_0
    //   190: ireturn
    //   191: iconst_0
    //   192: ireturn
    //   193: astore_0
    //   194: aload_2
    //   195: astore_0
    //   196: goto -15 -> 181
    //   199: astore_2
    //   200: goto -38 -> 162
    //   203: astore_0
    //   204: iconst_0
    //   205: ireturn
    //   206: astore_0
    //   207: iconst_0
    //   208: ireturn
    //   209: astore_0
    //   210: iconst_0
    //   211: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	212	0	paramFile	File
    //   25	9	1	i	int
    //   7	1	2	localObject1	Object
    //   158	1	2	localObject2	Object
    //   165	30	2	localObject3	Object
    //   199	1	2	localException	Exception
    //   9	158	3	localObject4	Object
    //   4	133	4	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   19	26	158	finally
    //   10	19	165	finally
    //   10	19	193	java/lang/Exception
    //   19	26	199	java/lang/Exception
    //   26	30	203	java/lang/Exception
    //   172	176	206	java/lang/Exception
    //   185	189	209	java/lang/Exception
  }
  
  /* Error */
  public static boolean isWebP(String paramString)
  {
    // Byte code:
    //   0: bipush 20
    //   2: newarray <illegal type>
    //   4: astore 4
    //   6: aconst_null
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: new 529	java/io/FileInputStream
    //   13: dup
    //   14: aload_0
    //   15: invokespecial 532	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   18: astore_0
    //   19: aload_0
    //   20: aload 4
    //   22: invokevirtual 535	java/io/FileInputStream:read	([B)I
    //   25: istore_1
    //   26: aload_0
    //   27: invokevirtual 536	java/io/FileInputStream:close	()V
    //   30: iload_1
    //   31: bipush 20
    //   33: if_icmpne +123 -> 156
    //   36: aload 4
    //   38: iconst_0
    //   39: baload
    //   40: bipush 82
    //   42: if_icmpne +112 -> 154
    //   45: aload 4
    //   47: iconst_1
    //   48: baload
    //   49: bipush 73
    //   51: if_icmpne +103 -> 154
    //   54: aload 4
    //   56: iconst_2
    //   57: baload
    //   58: bipush 70
    //   60: if_icmpne +94 -> 154
    //   63: aload 4
    //   65: iconst_3
    //   66: baload
    //   67: bipush 70
    //   69: if_icmpeq +5 -> 74
    //   72: iconst_0
    //   73: ireturn
    //   74: aload 4
    //   76: bipush 8
    //   78: baload
    //   79: bipush 87
    //   81: if_icmpne +71 -> 152
    //   84: aload 4
    //   86: bipush 9
    //   88: baload
    //   89: bipush 69
    //   91: if_icmpne +61 -> 152
    //   94: aload 4
    //   96: bipush 10
    //   98: baload
    //   99: bipush 66
    //   101: if_icmpne +51 -> 152
    //   104: aload 4
    //   106: bipush 11
    //   108: baload
    //   109: bipush 80
    //   111: if_icmpeq +5 -> 116
    //   114: iconst_0
    //   115: ireturn
    //   116: aload 4
    //   118: bipush 12
    //   120: baload
    //   121: bipush 86
    //   123: if_icmpne +27 -> 150
    //   126: aload 4
    //   128: bipush 13
    //   130: baload
    //   131: bipush 80
    //   133: if_icmpne +17 -> 150
    //   136: aload 4
    //   138: bipush 14
    //   140: baload
    //   141: bipush 56
    //   143: if_icmpeq +5 -> 148
    //   146: iconst_0
    //   147: ireturn
    //   148: iconst_1
    //   149: ireturn
    //   150: iconst_0
    //   151: ireturn
    //   152: iconst_0
    //   153: ireturn
    //   154: iconst_0
    //   155: ireturn
    //   156: iconst_0
    //   157: ireturn
    //   158: astore_2
    //   159: goto +9 -> 168
    //   162: goto +19 -> 181
    //   165: astore_2
    //   166: aload_3
    //   167: astore_0
    //   168: aload_0
    //   169: ifnull +10 -> 179
    //   172: aload_0
    //   173: invokevirtual 536	java/io/FileInputStream:close	()V
    //   176: goto +3 -> 179
    //   179: aload_2
    //   180: athrow
    //   181: aload_0
    //   182: ifnull +9 -> 191
    //   185: aload_0
    //   186: invokevirtual 536	java/io/FileInputStream:close	()V
    //   189: iconst_0
    //   190: ireturn
    //   191: iconst_0
    //   192: ireturn
    //   193: astore_0
    //   194: aload_2
    //   195: astore_0
    //   196: goto -15 -> 181
    //   199: astore_2
    //   200: goto -38 -> 162
    //   203: astore_0
    //   204: iconst_0
    //   205: ireturn
    //   206: astore_0
    //   207: iconst_0
    //   208: ireturn
    //   209: astore_0
    //   210: iconst_0
    //   211: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	212	0	paramString	String
    //   25	9	1	i	int
    //   7	1	2	localObject1	Object
    //   158	1	2	localObject2	Object
    //   165	30	2	localObject3	Object
    //   199	1	2	localException	Exception
    //   9	158	3	localObject4	Object
    //   4	133	4	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   19	26	158	finally
    //   10	19	165	finally
    //   10	19	193	java/lang/Exception
    //   19	26	199	java/lang/Exception
    //   26	30	203	java/lang/Exception
    //   172	176	206	java/lang/Exception
    //   185	189	209	java/lang/Exception
  }
  
  public static boolean isWebP(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length <= 20) {
      return false;
    }
    if ((paramArrayOfByte[0] == 82) && (paramArrayOfByte[1] == 73) && (paramArrayOfByte[2] == 70))
    {
      if (paramArrayOfByte[3] != 70) {
        return false;
      }
      if ((paramArrayOfByte[8] == 87) && (paramArrayOfByte[9] == 69) && (paramArrayOfByte[10] == 66))
      {
        if (paramArrayOfByte[11] != 80) {
          return false;
        }
        if ((paramArrayOfByte[12] == 86) && (paramArrayOfByte[13] == 80)) {
          return paramArrayOfByte[14] == 56;
        }
        return false;
      }
      return false;
    }
    return false;
  }
  
  public static BitmapFactory.Options newOptions()
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    localOptions.inDither = true;
    localOptions.inSampleSize = 1;
    return localOptions;
  }
  
  public static void reConfigureBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    if ((paramBitmap != null) && (Build.VERSION.SDK_INT >= 19)) {
      paramBitmap.reconfigure(paramInt1, paramInt2, paramConfig);
    }
  }
  
  public static Bitmap safeDecodeStreamTemp(InputStream paramInputStream)
    throws OutOfMemoryError
  {
    if (paramInputStream == null) {
      return null;
    }
    try
    {
      paramInputStream = BitmapFactory.decodeStream(paramInputStream);
      return paramInputStream;
    }
    catch (OutOfMemoryError paramInputStream)
    {
      paramInputStream.printStackTrace();
      throw paramInputStream;
    }
  }
  
  /* Error */
  public static Bitmap scale(Context paramContext, android.net.Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 659	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: aload_1
    //   5: invokevirtual 665	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   8: astore_1
    //   9: aload_1
    //   10: ifnull +67 -> 77
    //   13: aload_1
    //   14: invokevirtual 668	java/io/InputStream:available	()I
    //   17: newarray <illegal type>
    //   19: astore_0
    //   20: aload_1
    //   21: aload_0
    //   22: invokevirtual 669	java/io/InputStream:read	([B)I
    //   25: pop
    //   26: aload_1
    //   27: invokevirtual 145	java/io/InputStream:close	()V
    //   30: goto +49 -> 79
    //   33: astore_1
    //   34: aload_1
    //   35: invokevirtual 442	java/io/IOException:printStackTrace	()V
    //   38: goto +41 -> 79
    //   41: astore_0
    //   42: goto +21 -> 63
    //   45: astore_0
    //   46: aload_0
    //   47: invokevirtual 442	java/io/IOException:printStackTrace	()V
    //   50: aload_1
    //   51: invokevirtual 145	java/io/InputStream:close	()V
    //   54: aconst_null
    //   55: areturn
    //   56: astore_0
    //   57: aload_0
    //   58: invokevirtual 442	java/io/IOException:printStackTrace	()V
    //   61: aconst_null
    //   62: areturn
    //   63: aload_1
    //   64: invokevirtual 145	java/io/InputStream:close	()V
    //   67: goto +8 -> 75
    //   70: astore_1
    //   71: aload_1
    //   72: invokevirtual 442	java/io/IOException:printStackTrace	()V
    //   75: aload_0
    //   76: athrow
    //   77: aconst_null
    //   78: astore_0
    //   79: aload_0
    //   80: ifnonnull +5 -> 85
    //   83: aconst_null
    //   84: areturn
    //   85: new 66	android/graphics/BitmapFactory$Options
    //   88: dup
    //   89: invokespecial 68	android/graphics/BitmapFactory$Options:<init>	()V
    //   92: astore_1
    //   93: aload_1
    //   94: iconst_1
    //   95: putfield 170	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   98: aload_0
    //   99: iconst_0
    //   100: aload_0
    //   101: arraylength
    //   102: aload_1
    //   103: invokestatic 672	android/graphics/BitmapFactory:decodeByteArray	([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   106: pop
    //   107: aload_1
    //   108: getfield 195	android/graphics/BitmapFactory$Options:outWidth	I
    //   111: istore 5
    //   113: aload_1
    //   114: getfield 198	android/graphics/BitmapFactory$Options:outHeight	I
    //   117: istore 4
    //   119: ldc_w 673
    //   122: fstore_2
    //   123: fload_2
    //   124: fconst_2
    //   125: fmul
    //   126: fstore_3
    //   127: iload 5
    //   129: i2f
    //   130: fload_3
    //   131: fdiv
    //   132: f2i
    //   133: istore 6
    //   135: iload 4
    //   137: i2f
    //   138: fload_3
    //   139: fdiv
    //   140: f2i
    //   141: istore 7
    //   143: iload 6
    //   145: istore 5
    //   147: fload_3
    //   148: fstore_2
    //   149: iload 7
    //   151: istore 4
    //   153: iload 6
    //   155: sipush 700
    //   158: if_icmpge -35 -> 123
    //   161: iload 6
    //   163: istore 5
    //   165: fload_3
    //   166: fstore_2
    //   167: iload 7
    //   169: istore 4
    //   171: iload 7
    //   173: sipush 700
    //   176: if_icmpge -53 -> 123
    //   179: new 66	android/graphics/BitmapFactory$Options
    //   182: dup
    //   183: invokespecial 68	android/graphics/BitmapFactory$Options:<init>	()V
    //   186: astore_1
    //   187: aload_1
    //   188: fload_3
    //   189: f2i
    //   190: putfield 86	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   193: aload_0
    //   194: iconst_0
    //   195: aload_0
    //   196: arraylength
    //   197: aload_1
    //   198: invokestatic 672	android/graphics/BitmapFactory:decodeByteArray	([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   201: astore_1
    //   202: invokestatic 678	java/lang/System:gc	()V
    //   205: aload_1
    //   206: invokevirtual 228	android/graphics/Bitmap:getWidth	()I
    //   209: istore 5
    //   211: sipush 480
    //   214: istore 4
    //   216: iload 5
    //   218: sipush 480
    //   221: if_icmpgt +15 -> 236
    //   224: aload_1
    //   225: astore_0
    //   226: aload_1
    //   227: invokevirtual 231	android/graphics/Bitmap:getHeight	()I
    //   230: sipush 480
    //   233: if_icmple +73 -> 306
    //   236: ldc_w 679
    //   239: aload_1
    //   240: invokevirtual 228	android/graphics/Bitmap:getWidth	()I
    //   243: i2f
    //   244: fdiv
    //   245: ldc_w 679
    //   248: aload_1
    //   249: invokevirtual 231	android/graphics/Bitmap:getHeight	()I
    //   252: i2f
    //   253: fdiv
    //   254: fcmpg
    //   255: ifgt +26 -> 281
    //   258: aload_1
    //   259: invokevirtual 231	android/graphics/Bitmap:getHeight	()I
    //   262: sipush 480
    //   265: imul
    //   266: aload_1
    //   267: invokevirtual 228	android/graphics/Bitmap:getWidth	()I
    //   270: idiv
    //   271: istore 4
    //   273: sipush 480
    //   276: istore 5
    //   278: goto +18 -> 296
    //   281: aload_1
    //   282: invokevirtual 228	android/graphics/Bitmap:getWidth	()I
    //   285: sipush 480
    //   288: imul
    //   289: aload_1
    //   290: invokevirtual 231	android/graphics/Bitmap:getHeight	()I
    //   293: idiv
    //   294: istore 5
    //   296: aload_1
    //   297: iload 5
    //   299: iload 4
    //   301: iconst_1
    //   302: invokestatic 319	android/graphics/Bitmap:createScaledBitmap	(Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;
    //   305: astore_0
    //   306: aload_0
    //   307: areturn
    //   308: astore_0
    //   309: aload_0
    //   310: invokevirtual 680	java/io/FileNotFoundException:printStackTrace	()V
    //   313: aconst_null
    //   314: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	315	0	paramContext	Context
    //   0	315	1	paramUri	android.net.Uri
    //   122	45	2	f1	float
    //   126	63	3	f2	float
    //   117	183	4	i	int
    //   111	187	5	j	int
    //   133	29	6	k	int
    //   141	36	7	m	int
    // Exception table:
    //   from	to	target	type
    //   26	30	33	java/io/IOException
    //   13	26	41	finally
    //   46	50	41	finally
    //   13	26	45	java/io/IOException
    //   50	54	56	java/io/IOException
    //   63	67	70	java/io/IOException
    //   0	9	308	java/io/FileNotFoundException
  }
  
  private static abstract interface a
  {
    public abstract InputStream a()
      throws IOException;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\bitmap\BitmapUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */