package com.tencent.smtt.webkit.ui.zoomImg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TileBitmapDrawable
  extends Drawable
{
  public static a a;
  public static final Object a;
  private static ThreadPoolExecutor jdField_a_of_type_JavaUtilConcurrentThreadPoolExecutor = new ThreadPoolExecutor(15, 200, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
  public static final AtomicInteger a;
  public static int e;
  final int jdField_a_of_type_Int;
  Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  Matrix jdField_a_of_type_AndroidGraphicsMatrix;
  final Paint jdField_a_of_type_AndroidGraphicsPaint;
  private Point jdField_a_of_type_AndroidGraphicsPoint;
  final Rect jdField_a_of_type_AndroidGraphicsRect;
  final BitmapRegionDecodeInterface jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface;
  final b jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$b;
  final e jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$e;
  final WeakReference<ImageView> jdField_a_of_type_JavaLangRefWeakReference;
  final BlockingQueue<f> jdField_a_of_type_JavaUtilConcurrentBlockingQueue;
  final float[] jdField_a_of_type_ArrayOfFloat;
  final int jdField_b_of_type_Int;
  final Rect jdField_b_of_type_AndroidGraphicsRect;
  float[] jdField_b_of_type_ArrayOfFloat;
  final int jdField_c_of_type_Int;
  final Rect jdField_c_of_type_AndroidGraphicsRect;
  final int d;
  int f;
  
  static
  {
    jdField_a_of_type_JavaLangObject = new Object();
    jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger = new AtomicInteger(1);
    e = Integer.MAX_VALUE;
  }
  
  /* Error */
  TileBitmapDrawable(ImageView arg1, BitmapRegionDecodeInterface paramBitmapRegionDecodeInterface, Bitmap paramBitmap, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 86	android/graphics/drawable/Drawable:<init>	()V
    //   4: aload_0
    //   5: getstatic 62	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger	Ljava/util/concurrent/atomic/AtomicInteger;
    //   8: invokevirtual 90	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
    //   11: putfield 92	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_Int	I
    //   14: aload_0
    //   15: aconst_null
    //   16: putfield 94	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$e	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$e;
    //   19: aload_0
    //   20: new 77	java/util/concurrent/LinkedBlockingQueue
    //   23: dup
    //   24: invokespecial 78	java/util/concurrent/LinkedBlockingQueue:<init>	()V
    //   27: putfield 96	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_JavaUtilConcurrentBlockingQueue	Ljava/util/concurrent/BlockingQueue;
    //   30: aload_0
    //   31: new 98	android/graphics/Paint
    //   34: dup
    //   35: iconst_2
    //   36: invokespecial 99	android/graphics/Paint:<init>	(I)V
    //   39: putfield 101	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_AndroidGraphicsPaint	Landroid/graphics/Paint;
    //   42: aload_0
    //   43: bipush 9
    //   45: newarray <illegal type>
    //   47: putfield 103	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ArrayOfFloat	[F
    //   50: aload_0
    //   51: bipush 9
    //   53: newarray <illegal type>
    //   55: putfield 105	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_b_of_type_ArrayOfFloat	[F
    //   58: aload_0
    //   59: new 107	android/graphics/Rect
    //   62: dup
    //   63: invokespecial 108	android/graphics/Rect:<init>	()V
    //   66: putfield 110	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_AndroidGraphicsRect	Landroid/graphics/Rect;
    //   69: aload_0
    //   70: new 107	android/graphics/Rect
    //   73: dup
    //   74: invokespecial 108	android/graphics/Rect:<init>	()V
    //   77: putfield 112	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_b_of_type_AndroidGraphicsRect	Landroid/graphics/Rect;
    //   80: aload_0
    //   81: new 107	android/graphics/Rect
    //   84: dup
    //   85: invokespecial 108	android/graphics/Rect:<init>	()V
    //   88: putfield 114	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_c_of_type_AndroidGraphicsRect	Landroid/graphics/Rect;
    //   91: aload_0
    //   92: aconst_null
    //   93: putfield 116	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_AndroidGraphicsPoint	Landroid/graphics/Point;
    //   96: aload_0
    //   97: new 118	java/lang/ref/WeakReference
    //   100: dup
    //   101: aload_1
    //   102: invokespecial 121	java/lang/ref/WeakReference:<init>	(Ljava/lang/Object;)V
    //   105: putfield 123	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_JavaLangRefWeakReference	Ljava/lang/ref/WeakReference;
    //   108: aload_0
    //   109: iload 4
    //   111: putfield 125	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:f	I
    //   114: aload_2
    //   115: monitorenter
    //   116: aload_0
    //   117: aload_2
    //   118: putfield 127	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$BitmapRegionDecodeInterface;
    //   121: aload_0
    //   122: aload_0
    //   123: getfield 127	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$BitmapRegionDecodeInterface;
    //   126: invokeinterface 130 1 0
    //   131: putfield 132	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_b_of_type_Int	I
    //   134: aload_0
    //   135: aload_0
    //   136: getfield 127	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$BitmapRegionDecodeInterface;
    //   139: invokeinterface 135 1 0
    //   144: putfield 137	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_c_of_type_Int	I
    //   147: aload_2
    //   148: monitorexit
    //   149: new 139	android/util/DisplayMetrics
    //   152: dup
    //   153: invokespecial 140	android/util/DisplayMetrics:<init>	()V
    //   156: astore_2
    //   157: aload_1
    //   158: invokevirtual 146	android/widget/ImageView:getContext	()Landroid/content/Context;
    //   161: aload_2
    //   162: invokestatic 149	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:a	(Landroid/content/Context;Landroid/util/DisplayMetrics;)V
    //   165: aload_2
    //   166: getfield 152	android/util/DisplayMetrics:densityDpi	I
    //   169: sipush 240
    //   172: if_icmplt +11 -> 183
    //   175: sipush 256
    //   178: istore 4
    //   180: goto +8 -> 188
    //   183: sipush 128
    //   186: istore 4
    //   188: aload_0
    //   189: iload 4
    //   191: putfield 154	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:d	I
    //   194: aload_0
    //   195: aload_3
    //   196: putfield 156	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_AndroidGraphicsBitmap	Landroid/graphics/Bitmap;
    //   199: getstatic 55	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   202: astore_1
    //   203: aload_1
    //   204: monitorenter
    //   205: getstatic 158	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$a	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$a;
    //   208: ifnonnull +66 -> 274
    //   211: new 12	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$a
    //   214: dup
    //   215: aload_2
    //   216: getfield 161	android/util/DisplayMetrics:widthPixels	I
    //   219: iconst_2
    //   220: imul
    //   221: i2f
    //   222: aload_0
    //   223: getfield 154	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:d	I
    //   226: i2f
    //   227: fdiv
    //   228: f2d
    //   229: invokestatic 167	java/lang/Math:ceil	(D)D
    //   232: d2i
    //   233: iconst_1
    //   234: iadd
    //   235: iconst_4
    //   236: imul
    //   237: aload_2
    //   238: getfield 170	android/util/DisplayMetrics:heightPixels	I
    //   241: iconst_2
    //   242: imul
    //   243: i2f
    //   244: aload_0
    //   245: getfield 154	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:d	I
    //   248: i2f
    //   249: fdiv
    //   250: f2d
    //   251: invokestatic 167	java/lang/Math:ceil	(D)D
    //   254: d2i
    //   255: iconst_1
    //   256: iadd
    //   257: imul
    //   258: aload_0
    //   259: getfield 154	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:d	I
    //   262: imul
    //   263: aload_0
    //   264: getfield 154	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:d	I
    //   267: imul
    //   268: invokespecial 171	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$a:<init>	(I)V
    //   271: putstatic 158	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$a	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$a;
    //   274: aload_1
    //   275: monitorexit
    //   276: aload_0
    //   277: new 15	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$b
    //   280: dup
    //   281: aload_0
    //   282: aload_0
    //   283: getfield 127	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$BitmapRegionDecodeInterface;
    //   286: aload_0
    //   287: getfield 96	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_JavaUtilConcurrentBlockingQueue	Ljava/util/concurrent/BlockingQueue;
    //   290: invokespecial 174	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$b:<init>	(Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable;Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$BitmapRegionDecodeInterface;Ljava/util/concurrent/BlockingQueue;)V
    //   293: putfield 176	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$b	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$b;
    //   296: aload_0
    //   297: getfield 176	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$b	Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$b;
    //   300: invokevirtual 179	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$b:start	()V
    //   303: return
    //   304: astore_2
    //   305: aload_1
    //   306: monitorexit
    //   307: aload_2
    //   308: athrow
    //   309: astore_1
    //   310: aload_2
    //   311: monitorexit
    //   312: aload_1
    //   313: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	314	0	this	TileBitmapDrawable
    //   0	314	2	paramBitmapRegionDecodeInterface	BitmapRegionDecodeInterface
    //   0	314	3	paramBitmap	Bitmap
    //   0	314	4	paramInt	int
    // Exception table:
    //   from	to	target	type
    //   205	274	304	finally
    //   274	276	304	finally
    //   305	307	304	finally
    //   116	149	309	finally
    //   310	312	309	finally
  }
  
  protected static Point a(Canvas paramCanvas)
  {
    int j = 2048;
    if (paramCanvas != null) {}
    for (;;)
    {
      try
      {
        i = ((Integer)Canvas.class.getMethod("getMaximumBitmapWidth", new Class[0]).invoke(paramCanvas, new Object[0])).intValue();
      }
      catch (Exception paramCanvas)
      {
        int i;
        int k;
        continue;
      }
      try
      {
        k = ((Integer)Canvas.class.getMethod("getMaximumBitmapHeight", new Class[0]).invoke(paramCanvas, new Object[0])).intValue();
        j = k;
      }
      catch (Exception paramCanvas) {}
    }
    i = 2048;
    break label76;
    i = 2048;
    label76:
    return new Point(Math.min(i, e), Math.min(j, e));
  }
  
  public static OnDestroyCallback a(ImageView paramImageView, String paramString, Drawable paramDrawable, OnInitializeListener paramOnInitializeListener)
  {
    paramImageView = new c(paramImageView, paramDrawable, paramOnInitializeListener);
    paramImageView.executeOnExecutor(jdField_a_of_type_JavaUtilConcurrentThreadPoolExecutor, new Object[] { paramString });
    return paramImageView;
  }
  
  static void a(Context paramContext, DisplayMetrics paramDisplayMetrics)
  {
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(paramDisplayMetrics);
  }
  
  public void a()
  {
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$b;
    if (localObject != null) {
      ((b)localObject).a();
    }
    localObject = this.jdField_a_of_type_AndroidGraphicsBitmap;
    if (localObject != null)
    {
      ((Bitmap)localObject).recycle();
      this.jdField_a_of_type_AndroidGraphicsBitmap = null;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    Object localObject1 = (ImageView)this.jdField_a_of_type_JavaLangRefWeakReference.get();
    if (localObject1 == null) {
      return;
    }
    int i1 = ((ImageView)localObject1).getWidth();
    int m = ((ImageView)localObject1).getHeight();
    this.jdField_a_of_type_AndroidGraphicsMatrix = ((ImageView)localObject1).getImageMatrix();
    this.jdField_a_of_type_AndroidGraphicsMatrix.getValues(this.jdField_a_of_type_ArrayOfFloat);
    localObject1 = this.jdField_a_of_type_ArrayOfFloat;
    float f5 = localObject1[2];
    float f4 = localObject1[5];
    float f1 = localObject1[0];
    localObject1 = this.jdField_b_of_type_ArrayOfFloat;
    if ((f5 != localObject1[2]) || (f4 != localObject1[5]) || (f1 != localObject1[0])) {
      this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue.clear();
    }
    localObject1 = this.jdField_a_of_type_ArrayOfFloat;
    this.jdField_b_of_type_ArrayOfFloat = Arrays.copyOf((float[])localObject1, localObject1.length);
    float f2 = i1;
    float f6 = f2 / this.jdField_b_of_type_Int;
    float f3 = m;
    f6 = Math.min(f6, f3 / this.jdField_c_of_type_Int);
    int i = this.jdField_b_of_type_Int;
    i = Math.max(1, b.a(i / (i * f6)));
    int i12 = b.a(b.b(1.0F / f1), 0, i - 1);
    int n;
    if (this.f == 1 << i12) {
      n = 1;
    } else {
      n = 0;
    }
    if (this.jdField_a_of_type_AndroidGraphicsPoint == null) {
      this.jdField_a_of_type_AndroidGraphicsPoint = a(paramCanvas);
    }
    i = this.jdField_b_of_type_Int;
    int j = this.jdField_c_of_type_Int;
    j = i / 1;
    int k = i / 1;
    i = 1;
    for (;;)
    {
      int i2 = j + 1;
      if ((i2 <= this.jdField_a_of_type_AndroidGraphicsPoint.x) && (i2 <= i1))
      {
        i = k;
        k = 1;
        for (;;)
        {
          i1 = i + 1;
          if ((i1 <= this.jdField_a_of_type_AndroidGraphicsPoint.y) && (i1 <= m))
          {
            int i3 = (int)Math.ceil(this.jdField_b_of_type_Int / j);
            i2 = (int)Math.ceil(this.jdField_c_of_type_Int / i);
            f5 = -f5;
            k = Math.max(0, (int)(f5 / f1));
            f4 = -f4;
            m = Math.max(0, (int)(f4 / f1));
            i1 = Math.min(this.jdField_b_of_type_Int, Math.round((f5 + f2) / f1));
            int i4 = Math.min(this.jdField_c_of_type_Int, Math.round((f4 + f3) / f1));
            this.jdField_b_of_type_AndroidGraphicsRect.set(k, m, i1, i4);
            i1 = 0;
            m = 0;
            k = j;
            j = m;
            i4 = i;
            while (i1 < i3)
            {
              int i5 = 0;
              i = k;
              while (i5 < i2)
              {
                int i8 = i1 * i;
                int i9 = i5 * i4;
                m = (i1 + 1) * i;
                k = this.jdField_b_of_type_Int;
                if (m > k) {
                  m = k;
                }
                int i11 = i5 + 1;
                int i6 = i11 * i4;
                int i7 = this.jdField_c_of_type_Int;
                k = i7;
                if (i6 <= i7) {
                  k = i6;
                }
                i7 = i8;
                i6 = m;
                if (i1 == i3 - 1)
                {
                  i7 = i8;
                  i6 = m;
                  if (i1 > 0)
                  {
                    i6 = this.jdField_b_of_type_Int;
                    i7 = i6 - i;
                  }
                }
                i8 = i9;
                m = k;
                if (i5 == i2 - 1)
                {
                  i8 = i9;
                  m = k;
                  if (i5 > 0)
                  {
                    m = this.jdField_c_of_type_Int;
                    i8 = m - i4;
                  }
                }
                this.jdField_a_of_type_AndroidGraphicsRect.set(i7, i8, i6, m);
                if (Rect.intersects(this.jdField_b_of_type_AndroidGraphicsRect, this.jdField_a_of_type_AndroidGraphicsRect))
                {
                  int i13 = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface.getRotation();
                  localObject1 = new Rect(this.jdField_a_of_type_AndroidGraphicsRect);
                  Object localObject3;
                  if (i13 != 0)
                  {
                    ??? = new RectF();
                    localObject3 = new Matrix();
                    f1 = -i13;
                    ((Matrix)localObject3).setRotate(f1);
                    ((RectF)???).set((Rect)localObject1);
                    if (i13 == 90) {
                      ((Matrix)localObject3).postTranslate(0.0F, this.jdField_b_of_type_Int);
                    } else if (i13 == 180) {
                      ((Matrix)localObject3).postTranslate(this.jdField_b_of_type_Int, this.jdField_c_of_type_Int);
                    } else if (i13 == 270) {
                      ((Matrix)localObject3).postTranslate(this.jdField_c_of_type_Int, 0.0F);
                    }
                    ((Matrix)localObject3).mapRect((RectF)???);
                    ((RectF)???).round((Rect)localObject1);
                    ((Matrix)localObject3).setRotate(f1);
                    ((RectF)???).set(this.jdField_a_of_type_AndroidGraphicsRect);
                    ((Matrix)localObject3).mapRect((RectF)???);
                    ((RectF)???).round(this.jdField_a_of_type_AndroidGraphicsRect);
                  }
                  i9 = this.jdField_a_of_type_Int;
                  k = i;
                  localObject1 = new f(i9, (Rect)localObject1, i1, i5, i12);
                  synchronized (jdField_a_of_type_JavaLangObject)
                  {
                    localObject3 = (Bitmap)jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$a.get(((f)localObject1).a());
                    this.jdField_a_of_type_AndroidGraphicsPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                    int i14 = paramCanvas.save();
                    paramCanvas.rotate(i13);
                    if ((localObject3 != null) && (n == 0))
                    {
                      paramCanvas.drawBitmap((Bitmap)localObject3, null, this.jdField_a_of_type_AndroidGraphicsRect, this.jdField_a_of_type_AndroidGraphicsPaint);
                      i = j;
                    }
                    else
                    {
                      i = j;
                      if (n == 0) {
                        synchronized (this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue)
                        {
                          if (!this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue.contains(localObject1)) {
                            this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue.add(localObject1);
                          }
                          i = 1;
                        }
                      }
                      int i10 = this.jdField_a_of_type_AndroidGraphicsBitmap.getWidth();
                      i9 = this.jdField_a_of_type_AndroidGraphicsBitmap.getHeight();
                      if (i13 != 90)
                      {
                        i5 = i10;
                        j = i9;
                        if (i13 != 270) {}
                      }
                      else
                      {
                        j = i10;
                        i5 = i9;
                      }
                      i7 = Math.round(i7 * i5 / this.jdField_b_of_type_Int);
                      i8 = Math.round(i8 * j / this.jdField_c_of_type_Int);
                      i6 = Math.round(i6 * i5 / this.jdField_b_of_type_Int);
                      m = Math.round(m * j / this.jdField_c_of_type_Int);
                      this.jdField_c_of_type_AndroidGraphicsRect.set(i7, i8, i6, m);
                      if (i13 != 0)
                      {
                        localObject1 = new RectF(this.jdField_c_of_type_AndroidGraphicsRect);
                        ??? = new Matrix();
                        ((Matrix)???).setRotate(-i13);
                        if (i13 == 90) {
                          ((Matrix)???).postTranslate(0.0F, i5);
                        } else if (i13 == 180) {
                          ((Matrix)???).postTranslate(i5, j);
                        } else if (i13 == 270) {
                          ((Matrix)???).postTranslate(j, 0.0F);
                        }
                        ((Matrix)???).mapRect((RectF)localObject1);
                        ((RectF)localObject1).round(this.jdField_c_of_type_AndroidGraphicsRect);
                      }
                      paramCanvas.drawBitmap(this.jdField_a_of_type_AndroidGraphicsBitmap, this.jdField_c_of_type_AndroidGraphicsRect, this.jdField_a_of_type_AndroidGraphicsRect, this.jdField_a_of_type_AndroidGraphicsPaint);
                    }
                    paramCanvas.restoreToCount(i14);
                    j = i;
                    i = k;
                  }
                }
                i5 = i11;
              }
              i1 += 1;
              k = i;
            }
            if (j != 0) {
              invalidateSelf();
            }
            return;
          }
          k += 1;
          i = this.jdField_b_of_type_Int / k;
        }
      }
      i += 1;
      j = this.jdField_b_of_type_Int / i;
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$b.a();
  }
  
  public int getIntrinsicHeight()
  {
    return this.jdField_c_of_type_Int;
  }
  
  public int getIntrinsicWidth()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public int getOpacity()
  {
    Bitmap localBitmap = this.jdField_a_of_type_AndroidGraphicsBitmap;
    if ((localBitmap != null) && (!localBitmap.hasAlpha()) && (this.jdField_a_of_type_AndroidGraphicsPaint.getAlpha() >= 255)) {
      return -1;
    }
    return -3;
  }
  
  public void setAlpha(int paramInt)
  {
    if (paramInt != this.jdField_a_of_type_AndroidGraphicsPaint.getAlpha())
    {
      this.jdField_a_of_type_AndroidGraphicsPaint.setAlpha(paramInt);
      invalidateSelf();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  static abstract interface BitmapRegionDecodeInterface
  {
    public abstract Bitmap decodeRegion(Rect paramRect, BitmapFactory.Options paramOptions);
    
    public abstract int getHeight();
    
    public abstract int getOrigHeight();
    
    public abstract int getOrigWidth();
    
    public abstract int getRotation();
    
    public abstract int getWidth();
    
    public abstract boolean isCanUse();
  }
  
  public static abstract interface OnInitializeListener
  {
    public abstract void onEndInitialization();
    
    public abstract void onInitializationFailed();
    
    public abstract void onStartInitialization();
  }
  
  public static final class a
    extends LruCache<String, Bitmap>
  {
    public a(int paramInt)
    {
      super();
    }
    
    static int a(Bitmap paramBitmap)
    {
      return paramBitmap.getByteCount();
    }
    
    protected int a(String paramString, Bitmap paramBitmap)
    {
      return a(paramBitmap);
    }
  }
  
  static final class b
    extends Thread
  {
    final TileBitmapDrawable.BitmapRegionDecodeInterface jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface;
    final WeakReference<TileBitmapDrawable> jdField_a_of_type_JavaLangRefWeakReference;
    final BlockingQueue<TileBitmapDrawable.f> jdField_a_of_type_JavaUtilConcurrentBlockingQueue;
    boolean jdField_a_of_type_Boolean;
    
    b(TileBitmapDrawable paramTileBitmapDrawable, TileBitmapDrawable.BitmapRegionDecodeInterface paramBitmapRegionDecodeInterface, BlockingQueue<TileBitmapDrawable.f> paramBlockingQueue)
    {
      setPriority(1);
      this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramTileBitmapDrawable);
      this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface = paramBitmapRegionDecodeInterface;
      this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue = paramBlockingQueue;
    }
    
    public void a()
    {
      this.jdField_a_of_type_Boolean = true;
      interrupt();
    }
    
    public void run()
    {
      Bitmap.Config localConfig;
      if (Build.VERSION.SDK_INT >= 21) {
        localConfig = Bitmap.Config.RGB_565;
      } else {
        localConfig = Bitmap.Config.ARGB_8888;
      }
      while (!this.jdField_a_of_type_Boolean)
      {
        if (this.jdField_a_of_type_JavaLangRefWeakReference.get() == null) {
          return;
        }
        try
        {
          TileBitmapDrawable.f localf = (TileBitmapDrawable.f)this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue.take();
          synchronized (TileBitmapDrawable.jdField_a_of_type_JavaLangObject)
          {
            if (TileBitmapDrawable.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$a.get(localf.a()) != null) {
              continue;
            }
            BitmapFactory.Options localOptions = new BitmapFactory.Options();
            localOptions.inPreferredConfig = localConfig;
            localOptions.inPreferQualityOverSpeed = true;
            localOptions.inSampleSize = (1 << localf.d);
            Object localObject6 = null;
            TileBitmapDrawable.BitmapRegionDecodeInterface localBitmapRegionDecodeInterface = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface;
            ??? = localConfig;
            try
            {
              try
              {
                Bitmap localBitmap = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface.decodeRegion(localf.a, localOptions);
                ??? = localConfig;
                localObject6 = localBitmap;
                if (localBitmap == null)
                {
                  ??? = localConfig;
                  localObject6 = localBitmap;
                  if (localOptions.inPreferredConfig == Bitmap.Config.ARGB_8888)
                  {
                    ??? = localConfig;
                    localObject6 = localBitmap;
                    localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
                  }
                  else
                  {
                    ??? = localConfig;
                    localObject6 = localBitmap;
                    localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
                  }
                  ??? = localConfig;
                  localObject6 = localBitmap;
                  localConfig = localOptions.inPreferredConfig;
                  ??? = localConfig;
                  localObject6 = localBitmap;
                  localBitmap = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$BitmapRegionDecodeInterface.decodeRegion(localf.a, localOptions);
                  ??? = localConfig;
                  localObject6 = localBitmap;
                }
              }
              finally
              {
                break label286;
              }
            }
            catch (OutOfMemoryError localOutOfMemoryError)
            {
              label286:
              for (;;) {}
            }
            if (localObject6 == null)
            {
              ??? = ???;
              continue;
            }
            synchronized (TileBitmapDrawable.jdField_a_of_type_JavaLangObject)
            {
              TileBitmapDrawable.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$a.put(localf.a(), localObject6);
              ??? = ???;
            }
            throw ((Throwable)???);
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;) {}
        }
        catch (Throwable localThrowable)
        {
          for (;;) {}
        }
        if (this.jdField_a_of_type_Boolean)
        {
          return;
          if (this.jdField_a_of_type_Boolean) {
            return;
          }
        }
      }
    }
  }
  
  static final class c
    extends AsyncTask<Object, Void, TileBitmapDrawable>
    implements OnDestroyCallback
  {
    ImageView jdField_a_of_type_AndroidWidgetImageView;
    TileBitmapDrawable.OnInitializeListener jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$OnInitializeListener;
    TileBitmapDrawable jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable;
    boolean jdField_a_of_type_Boolean;
    
    c(ImageView paramImageView, Drawable paramDrawable, TileBitmapDrawable.OnInitializeListener paramOnInitializeListener)
    {
      this.jdField_a_of_type_AndroidWidgetImageView = paramImageView;
      this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$OnInitializeListener = paramOnInitializeListener;
      paramImageView = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$OnInitializeListener;
      if (paramImageView != null) {
        paramImageView.onStartInitialization();
      }
      if (paramDrawable != null) {
        this.jdField_a_of_type_AndroidWidgetImageView.setImageDrawable(paramDrawable);
      }
    }
    
    /* Error */
    protected TileBitmapDrawable a(Object... paramVarArgs)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 43	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$c:jdField_a_of_type_Boolean	Z
      //   4: ifne +396 -> 400
      //   7: aload_1
      //   8: iconst_0
      //   9: aaload
      //   10: ifnonnull +5 -> 15
      //   13: aconst_null
      //   14: areturn
      //   15: aload_1
      //   16: iconst_0
      //   17: aaload
      //   18: instanceof 45
      //   21: ifeq +21 -> 42
      //   24: new 47	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d
      //   27: dup
      //   28: aload_1
      //   29: iconst_0
      //   30: aaload
      //   31: checkcast 45	java/lang/String
      //   34: iconst_0
      //   35: invokespecial 50	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d:<init>	(Ljava/lang/String;Z)V
      //   38: astore_1
      //   39: goto +115 -> 154
      //   42: aload_1
      //   43: iconst_0
      //   44: aaload
      //   45: instanceof 52
      //   48: ifeq +21 -> 69
      //   51: new 47	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d
      //   54: dup
      //   55: aload_1
      //   56: iconst_0
      //   57: aaload
      //   58: checkcast 52	java/io/FileDescriptor
      //   61: iconst_0
      //   62: invokespecial 55	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d:<init>	(Ljava/io/FileDescriptor;Z)V
      //   65: astore_1
      //   66: goto +88 -> 154
      //   69: aload_1
      //   70: iconst_0
      //   71: aaload
      //   72: instanceof 57
      //   75: ifeq +24 -> 99
      //   78: new 47	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d
      //   81: dup
      //   82: aload_1
      //   83: iconst_0
      //   84: aaload
      //   85: checkcast 57	[B
      //   88: checkcast 57	[B
      //   91: iconst_0
      //   92: invokespecial 60	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d:<init>	([BZ)V
      //   95: astore_1
      //   96: goto +58 -> 154
      //   99: aload_1
      //   100: iconst_0
      //   101: aaload
      //   102: instanceof 62
      //   105: ifeq +20 -> 125
      //   108: new 64	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$e
      //   111: dup
      //   112: aload_1
      //   113: iconst_0
      //   114: aaload
      //   115: checkcast 62	android/graphics/Bitmap
      //   118: invokespecial 67	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$e:<init>	(Landroid/graphics/Bitmap;)V
      //   121: astore_1
      //   122: goto +32 -> 154
      //   125: aload_1
      //   126: iconst_0
      //   127: aaload
      //   128: instanceof 69
      //   131: ifeq +21 -> 152
      //   134: new 47	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d
      //   137: dup
      //   138: aload_1
      //   139: iconst_0
      //   140: aaload
      //   141: checkcast 69	java/io/InputStream
      //   144: iconst_0
      //   145: invokespecial 72	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$d:<init>	(Ljava/io/InputStream;Z)V
      //   148: astore_1
      //   149: goto +5 -> 154
      //   152: aconst_null
      //   153: astore_1
      //   154: aload_1
      //   155: ifnull +243 -> 398
      //   158: aload_1
      //   159: invokeinterface 78 1 0
      //   164: ifne +5 -> 169
      //   167: aconst_null
      //   168: areturn
      //   169: new 80	android/util/DisplayMetrics
      //   172: dup
      //   173: invokespecial 81	android/util/DisplayMetrics:<init>	()V
      //   176: astore 4
      //   178: aload_0
      //   179: getfield 22	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$c:jdField_a_of_type_AndroidWidgetImageView	Landroid/widget/ImageView;
      //   182: invokevirtual 85	android/widget/ImageView:getContext	()Landroid/content/Context;
      //   185: ldc 87
      //   187: invokevirtual 93	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
      //   190: checkcast 95	android/view/WindowManager
      //   193: invokeinterface 99 1 0
      //   198: aload 4
      //   200: invokevirtual 105	android/view/Display:getMetrics	(Landroid/util/DisplayMetrics;)V
      //   203: aload 4
      //   205: getfield 109	android/util/DisplayMetrics:widthPixels	I
      //   208: i2f
      //   209: aload_1
      //   210: invokeinterface 113 1 0
      //   215: i2f
      //   216: fdiv
      //   217: aload 4
      //   219: getfield 116	android/util/DisplayMetrics:heightPixels	I
      //   222: i2f
      //   223: aload_1
      //   224: invokeinterface 119 1 0
      //   229: i2f
      //   230: fdiv
      //   231: invokestatic 125	java/lang/Math:min	(FF)F
      //   234: fstore_2
      //   235: iconst_1
      //   236: aload_1
      //   237: invokeinterface 113 1 0
      //   242: i2f
      //   243: aload_1
      //   244: invokeinterface 113 1 0
      //   249: i2f
      //   250: fload_2
      //   251: fmul
      //   252: fdiv
      //   253: invokestatic 130	com/tencent/smtt/webkit/ui/zoomImg/b:a	(F)I
      //   256: invokestatic 134	java/lang/Math:max	(II)I
      //   259: istore_3
      //   260: new 136	android/graphics/Rect
      //   263: dup
      //   264: iconst_0
      //   265: iconst_0
      //   266: aload_1
      //   267: invokeinterface 139 1 0
      //   272: aload_1
      //   273: invokeinterface 142 1 0
      //   278: invokespecial 145	android/graphics/Rect:<init>	(IIII)V
      //   281: astore 4
      //   283: new 147	android/graphics/BitmapFactory$Options
      //   286: dup
      //   287: invokespecial 148	android/graphics/BitmapFactory$Options:<init>	()V
      //   290: astore 6
      //   292: getstatic 153	android/os/Build$VERSION:SDK_INT	I
      //   295: bipush 21
      //   297: if_icmplt +14 -> 311
      //   300: aload 6
      //   302: getstatic 159	android/graphics/Bitmap$Config:RGB_565	Landroid/graphics/Bitmap$Config;
      //   305: putfield 162	android/graphics/BitmapFactory$Options:inPreferredConfig	Landroid/graphics/Bitmap$Config;
      //   308: goto +11 -> 319
      //   311: aload 6
      //   313: getstatic 165	android/graphics/Bitmap$Config:ARGB_8888	Landroid/graphics/Bitmap$Config;
      //   316: putfield 162	android/graphics/BitmapFactory$Options:inPreferredConfig	Landroid/graphics/Bitmap$Config;
      //   319: aload 6
      //   321: iconst_1
      //   322: putfield 168	android/graphics/BitmapFactory$Options:inPreferQualityOverSpeed	Z
      //   325: iconst_1
      //   326: iload_3
      //   327: iconst_1
      //   328: isub
      //   329: ishl
      //   330: istore_3
      //   331: aload 6
      //   333: iload_3
      //   334: putfield 171	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   337: aload_1
      //   338: aload 4
      //   340: aload 6
      //   342: invokeinterface 175 3 0
      //   347: astore 5
      //   349: aload 5
      //   351: astore 4
      //   353: aload 5
      //   355: ifnonnull +27 -> 382
      //   358: aconst_null
      //   359: areturn
      //   360: iload_3
      //   361: iconst_1
      //   362: ishl
      //   363: istore_3
      //   364: aload 6
      //   366: iload_3
      //   367: putfield 171	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   370: aload_1
      //   371: aload 4
      //   373: aload 6
      //   375: invokeinterface 175 3 0
      //   380: astore 4
      //   382: new 9	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable
      //   385: dup
      //   386: aload_0
      //   387: getfield 22	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$c:jdField_a_of_type_AndroidWidgetImageView	Landroid/widget/ImageView;
      //   390: aload_1
      //   391: aload 4
      //   393: iload_3
      //   394: invokespecial 178	com/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable:<init>	(Landroid/widget/ImageView;Lcom/tencent/smtt/webkit/ui/zoomImg/TileBitmapDrawable$BitmapRegionDecodeInterface;Landroid/graphics/Bitmap;I)V
      //   397: areturn
      //   398: aconst_null
      //   399: areturn
      //   400: aconst_null
      //   401: areturn
      //   402: astore_1
      //   403: aconst_null
      //   404: areturn
      //   405: astore 5
      //   407: goto -47 -> 360
      //   410: astore_1
      //   411: aconst_null
      //   412: areturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	413	0	this	c
      //   0	413	1	paramVarArgs	Object[]
      //   234	17	2	f	float
      //   259	135	3	i	int
      //   176	216	4	localObject	Object
      //   347	7	5	localBitmap	Bitmap
      //   405	1	5	localOutOfMemoryError	OutOfMemoryError
      //   290	84	6	localOptions	BitmapFactory.Options
      // Exception table:
      //   from	to	target	type
      //   15	39	402	java/lang/Exception
      //   42	66	402	java/lang/Exception
      //   69	96	402	java/lang/Exception
      //   99	122	402	java/lang/Exception
      //   125	149	402	java/lang/Exception
      //   337	349	405	java/lang/OutOfMemoryError
      //   364	382	410	java/lang/OutOfMemoryError
    }
    
    protected void a(TileBitmapDrawable paramTileBitmapDrawable)
    {
      if (this.jdField_a_of_type_Boolean)
      {
        this.jdField_a_of_type_AndroidWidgetImageView = null;
        if (paramTileBitmapDrawable != null) {
          paramTileBitmapDrawable.a();
        }
      }
      else
      {
        TileBitmapDrawable.OnInitializeListener localOnInitializeListener = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$OnInitializeListener;
        if (localOnInitializeListener != null)
        {
          if (paramTileBitmapDrawable == null) {
            localOnInitializeListener.onInitializationFailed();
          } else {
            localOnInitializeListener.onEndInitialization();
          }
          this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable$OnInitializeListener = null;
        }
        if (paramTileBitmapDrawable != null)
        {
          this.jdField_a_of_type_AndroidWidgetImageView.setImageDrawable(paramTileBitmapDrawable);
          this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable = paramTileBitmapDrawable;
        }
      }
    }
    
    public void destroy()
    {
      this.jdField_a_of_type_Boolean = true;
      Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable;
      if (localObject != null)
      {
        ((TileBitmapDrawable)localObject).a();
        this.jdField_a_of_type_ComTencentSmttWebkitUiZoomImgTileBitmapDrawable = null;
        localObject = this.jdField_a_of_type_AndroidWidgetImageView;
        if (localObject != null)
        {
          ((ImageView)localObject).setImageDrawable(null);
          this.jdField_a_of_type_AndroidWidgetImageView = null;
        }
      }
    }
  }
  
  static class d
    implements TileBitmapDrawable.BitmapRegionDecodeInterface
  {
    private int jdField_a_of_type_Int = 0;
    private BitmapRegionDecoder jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder = null;
    private int b;
    private int c;
    
    public d(FileDescriptor paramFileDescriptor, boolean paramBoolean)
    {
      try
      {
        this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder = BitmapRegionDecoder.newInstance(paramFileDescriptor, paramBoolean);
        this.jdField_a_of_type_Int = d.a(paramFileDescriptor);
        a();
        return;
      }
      catch (IOException paramFileDescriptor)
      {
        paramFileDescriptor.printStackTrace();
      }
    }
    
    public d(InputStream paramInputStream, boolean paramBoolean)
    {
      try
      {
        this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder = BitmapRegionDecoder.newInstance(paramInputStream, paramBoolean);
        this.jdField_a_of_type_Int = d.a(paramInputStream);
        a();
        return;
      }
      catch (IOException paramInputStream)
      {
        paramInputStream.printStackTrace();
      }
    }
    
    public d(String paramString, boolean paramBoolean)
    {
      try
      {
        this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder = BitmapRegionDecoder.newInstance(paramString, paramBoolean);
        this.jdField_a_of_type_Int = d.a(paramString);
        a();
        return;
      }
      catch (IOException paramString)
      {
        paramString.printStackTrace();
      }
    }
    
    public d(byte[] paramArrayOfByte, boolean paramBoolean)
    {
      try
      {
        this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder = BitmapRegionDecoder.newInstance(paramArrayOfByte, 0, paramArrayOfByte.length, paramBoolean);
        paramArrayOfByte = new ByteArrayInputStream(paramArrayOfByte);
        this.jdField_a_of_type_Int = d.a(paramArrayOfByte);
        a();
        paramArrayOfByte.close();
        return;
      }
      catch (IOException paramArrayOfByte)
      {
        paramArrayOfByte.printStackTrace();
      }
    }
    
    private void a()
    {
      this.b = this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder.getWidth();
      this.c = this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder.getHeight();
    }
    
    public Bitmap decodeRegion(Rect paramRect, BitmapFactory.Options paramOptions)
    {
      return this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder.decodeRegion(paramRect, paramOptions);
    }
    
    public int getHeight()
    {
      if (this.jdField_a_of_type_Int != 0)
      {
        Matrix localMatrix = new Matrix();
        localMatrix.setRotate(this.jdField_a_of_type_Int);
        RectF localRectF = new RectF(0.0F, 0.0F, this.b, this.c);
        localMatrix.mapRect(localRectF);
        return (int)localRectF.height();
      }
      return this.c;
    }
    
    public int getOrigHeight()
    {
      return this.c;
    }
    
    public int getOrigWidth()
    {
      return this.b;
    }
    
    public int getRotation()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public int getWidth()
    {
      if (this.jdField_a_of_type_Int != 0)
      {
        Matrix localMatrix = new Matrix();
        localMatrix.setRotate(this.jdField_a_of_type_Int);
        RectF localRectF = new RectF(0.0F, 0.0F, this.b, this.c);
        localMatrix.mapRect(localRectF);
        return (int)localRectF.width();
      }
      return this.b;
    }
    
    public boolean isCanUse()
    {
      return this.jdField_a_of_type_AndroidGraphicsBitmapRegionDecoder != null;
    }
  }
  
  static class e
    implements TileBitmapDrawable.BitmapRegionDecodeInterface
  {
    public Bitmap a;
    
    public e(Bitmap paramBitmap)
    {
      this.a = paramBitmap;
    }
    
    public Bitmap decodeRegion(Rect paramRect, BitmapFactory.Options paramOptions)
    {
      try
      {
        if ((this.a != null) && (!this.a.isRecycled()))
        {
          Matrix localMatrix = new Matrix();
          float f2 = 1.0F / paramOptions.inSampleSize;
          float f1 = f2;
          if (f2 <= 0.0F) {
            f1 = 1.0F;
          }
          localMatrix.postScale(f1, f1);
          paramRect = Bitmap.createBitmap(this.a, paramRect.left, paramRect.top, paramRect.right - paramRect.left, paramRect.bottom - paramRect.top, localMatrix, false);
          return paramRect;
        }
      }
      catch (Exception paramRect)
      {
        for (;;) {}
      }
      return null;
    }
    
    public int getHeight()
    {
      Bitmap localBitmap = this.a;
      if (localBitmap != null) {
        return localBitmap.getHeight();
      }
      return 0;
    }
    
    public int getOrigHeight()
    {
      return getHeight();
    }
    
    public int getOrigWidth()
    {
      return getWidth();
    }
    
    public int getRotation()
    {
      return 0;
    }
    
    public int getWidth()
    {
      Bitmap localBitmap = this.a;
      if (localBitmap != null) {
        return localBitmap.getWidth();
      }
      return 0;
    }
    
    public boolean isCanUse()
    {
      Bitmap localBitmap = this.a;
      return (localBitmap != null) && (!localBitmap.isRecycled());
    }
  }
  
  static final class f
  {
    final int jdField_a_of_type_Int;
    final Rect jdField_a_of_type_AndroidGraphicsRect;
    final int b;
    final int c;
    final int d;
    
    f(int paramInt1, Rect paramRect, int paramInt2, int paramInt3, int paramInt4)
    {
      this.jdField_a_of_type_Int = paramInt1;
      this.jdField_a_of_type_AndroidGraphicsRect = new Rect();
      this.jdField_a_of_type_AndroidGraphicsRect.set(paramRect);
      this.b = paramInt2;
      this.c = paramInt3;
      this.d = paramInt4;
    }
    
    public String a()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("#");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append("#");
      localStringBuilder.append(this.b);
      localStringBuilder.append("#");
      localStringBuilder.append(this.c);
      localStringBuilder.append("#");
      localStringBuilder.append(this.d);
      localStringBuilder.append("#");
      localStringBuilder.append(this.jdField_a_of_type_AndroidGraphicsRect.width());
      localStringBuilder.append("#");
      localStringBuilder.append(this.jdField_a_of_type_AndroidGraphicsRect.height());
      return localStringBuilder.toString();
    }
    
    public boolean equals(Object paramObject)
    {
      if ((paramObject instanceof f)) {
        return a().equals(((f)paramObject).a());
      }
      return false;
    }
    
    public int hashCode()
    {
      return a().hashCode();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\zoomImg\TileBitmapDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */