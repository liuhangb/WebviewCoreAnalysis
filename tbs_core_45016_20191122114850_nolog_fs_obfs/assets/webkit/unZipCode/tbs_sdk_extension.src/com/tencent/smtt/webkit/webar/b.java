package com.tencent.smtt.webkit.webar;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import org.chromium.base.Log;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("webar")
public class b
  extends WebARVideoCapture
  implements IWebARAutoFocusProvider
{
  private long jdField_a_of_type_Long = 0L;
  private Camera jdField_a_of_type_AndroidHardwareCamera;
  private a jdField_a_of_type_ComTencentSmttWebkitWebarA = null;
  private ReentrantLock jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock = new ReentrantLock();
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int = 0;
  private long jdField_b_of_type_Long = 0L;
  private boolean jdField_b_of_type_Boolean = false;
  private boolean c = false;
  private boolean d = false;
  
  b(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  private static Camera.CameraInfo a(int paramInt)
  {
    Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
    try
    {
      Camera.getCameraInfo(paramInt, localCameraInfo);
      return localCameraInfo;
    }
    catch (RuntimeException localRuntimeException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getCameraInfo: Camera.getCameraInfo: ");
      localStringBuilder.append(localRuntimeException);
      Log.e("WebARVideoCaptureCamera", localStringBuilder.toString(), new Object[0]);
    }
    return null;
  }
  
  private static Camera.Parameters a(Camera paramCamera)
  {
    try
    {
      Camera.Parameters localParameters = paramCamera.getParameters();
      return localParameters;
    }
    catch (RuntimeException localRuntimeException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getCameraParameters: android.hardware.Camera.getParameters: ");
      localStringBuilder.append(localRuntimeException);
      Log.e("WebARVideoCaptureCamera", localStringBuilder.toString(), new Object[0]);
      if (paramCamera != null) {
        paramCamera.release();
      }
    }
    return null;
  }
  
  /* Error */
  @android.annotation.SuppressLint({"InlinedApi"})
  public boolean allocate(int paramInt1, int paramInt2, android.graphics.SurfaceTexture paramSurfaceTexture)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_0
    //   2: getfield 101	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_Int	I
    //   5: invokestatic 105	android/hardware/Camera:open	(I)Landroid/hardware/Camera;
    //   8: putfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   11: aload_0
    //   12: getfield 101	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_Int	I
    //   15: invokestatic 109	com/tencent/smtt/webkit/webar/b:a	(I)Landroid/hardware/Camera$CameraInfo;
    //   18: ifnonnull +17 -> 35
    //   21: aload_0
    //   22: getfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   25: invokevirtual 93	android/hardware/Camera:release	()V
    //   28: aload_0
    //   29: aconst_null
    //   30: putfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   33: iconst_0
    //   34: ireturn
    //   35: aload_0
    //   36: getfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   39: invokestatic 111	com/tencent/smtt/webkit/webar/b:a	(Landroid/hardware/Camera;)Landroid/hardware/Camera$Parameters;
    //   42: astore 8
    //   44: aload 8
    //   46: ifnonnull +10 -> 56
    //   49: aload_0
    //   50: aconst_null
    //   51: putfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   54: iconst_0
    //   55: ireturn
    //   56: aload 8
    //   58: invokevirtual 117	android/hardware/Camera$Parameters:getSupportedPreviewFpsRange	()Ljava/util/List;
    //   61: astore 9
    //   63: iconst_0
    //   64: istore 4
    //   66: iload 4
    //   68: aload 9
    //   70: invokeinterface 123 1 0
    //   75: if_icmpge +62 -> 137
    //   78: aload 9
    //   80: iload 4
    //   82: invokeinterface 127 2 0
    //   87: checkcast 129	[I
    //   90: astore 10
    //   92: aload 10
    //   94: iconst_0
    //   95: iaload
    //   96: sipush 25000
    //   99: if_icmplt +29 -> 128
    //   102: aload 10
    //   104: iconst_1
    //   105: iaload
    //   106: sipush 25000
    //   109: if_icmplt +19 -> 128
    //   112: aload 8
    //   114: aload 10
    //   116: iconst_0
    //   117: iaload
    //   118: aload 10
    //   120: iconst_1
    //   121: iaload
    //   122: invokevirtual 133	android/hardware/Camera$Parameters:setPreviewFpsRange	(II)V
    //   125: goto +12 -> 137
    //   128: iload 4
    //   130: iconst_1
    //   131: iadd
    //   132: istore 4
    //   134: goto -68 -> 66
    //   137: aload 8
    //   139: invokevirtual 136	android/hardware/Camera$Parameters:getSupportedPreviewSizes	()Ljava/util/List;
    //   142: invokeinterface 140 1 0
    //   147: astore 9
    //   149: iload_1
    //   150: istore 5
    //   152: iload_2
    //   153: istore 6
    //   155: ldc -115
    //   157: istore 4
    //   159: aload 9
    //   161: invokeinterface 147 1 0
    //   166: ifeq +77 -> 243
    //   169: aload 9
    //   171: invokeinterface 151 1 0
    //   176: checkcast 153	android/hardware/Camera$Size
    //   179: astore 10
    //   181: aload 10
    //   183: getfield 156	android/hardware/Camera$Size:width	I
    //   186: iload_1
    //   187: isub
    //   188: invokestatic 162	java/lang/Math:abs	(I)I
    //   191: aload 10
    //   193: getfield 165	android/hardware/Camera$Size:height	I
    //   196: iload_2
    //   197: isub
    //   198: invokestatic 162	java/lang/Math:abs	(I)I
    //   201: iadd
    //   202: istore 7
    //   204: iload 7
    //   206: iload 4
    //   208: if_icmpge -49 -> 159
    //   211: aload 10
    //   213: getfield 156	android/hardware/Camera$Size:width	I
    //   216: bipush 32
    //   218: irem
    //   219: ifne -60 -> 159
    //   222: aload 10
    //   224: getfield 156	android/hardware/Camera$Size:width	I
    //   227: istore 5
    //   229: aload 10
    //   231: getfield 165	android/hardware/Camera$Size:height	I
    //   234: istore 6
    //   236: iload 7
    //   238: istore 4
    //   240: goto -81 -> 159
    //   243: iload 4
    //   245: ldc -115
    //   247: if_icmpne +16 -> 263
    //   250: ldc 71
    //   252: ldc -89
    //   254: iconst_0
    //   255: anewarray 77	java/lang/Object
    //   258: invokestatic 83	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   261: iconst_0
    //   262: ireturn
    //   263: aload 8
    //   265: invokevirtual 170	android/hardware/Camera$Parameters:isVideoStabilizationSupported	()Z
    //   268: ifeq +9 -> 277
    //   271: aload 8
    //   273: iconst_1
    //   274: invokevirtual 174	android/hardware/Camera$Parameters:setVideoStabilization	(Z)V
    //   277: aload 8
    //   279: invokevirtual 177	android/hardware/Camera$Parameters:getSupportedFocusModes	()Ljava/util/List;
    //   282: astore 9
    //   284: aload 9
    //   286: ldc -77
    //   288: invokeinterface 183 2 0
    //   293: ifeq +13 -> 306
    //   296: aload 8
    //   298: ldc -77
    //   300: invokevirtual 187	android/hardware/Camera$Parameters:setFocusMode	(Ljava/lang/String;)V
    //   303: goto +22 -> 325
    //   306: aload 9
    //   308: ldc -67
    //   310: invokeinterface 183 2 0
    //   315: ifeq +10 -> 325
    //   318: aload 8
    //   320: ldc -67
    //   322: invokevirtual 187	android/hardware/Camera$Parameters:setFocusMode	(Ljava/lang/String;)V
    //   325: aload 8
    //   327: iload 5
    //   329: iload 6
    //   331: invokevirtual 192	android/hardware/Camera$Parameters:setPreviewSize	(II)V
    //   334: aload 8
    //   336: bipush 17
    //   338: invokevirtual 196	android/hardware/Camera$Parameters:setPreviewFormat	(I)V
    //   341: aload 8
    //   343: invokevirtual 199	android/hardware/Camera$Parameters:getSupportedPictureSizes	()Ljava/util/List;
    //   346: invokeinterface 140 1 0
    //   351: astore 9
    //   353: iload_1
    //   354: istore 5
    //   356: iload_2
    //   357: istore 6
    //   359: ldc -115
    //   361: istore 4
    //   363: aload 9
    //   365: invokeinterface 147 1 0
    //   370: ifeq +66 -> 436
    //   373: aload 9
    //   375: invokeinterface 151 1 0
    //   380: checkcast 153	android/hardware/Camera$Size
    //   383: astore 10
    //   385: aload 10
    //   387: getfield 156	android/hardware/Camera$Size:width	I
    //   390: iload_1
    //   391: isub
    //   392: invokestatic 162	java/lang/Math:abs	(I)I
    //   395: aload 10
    //   397: getfield 165	android/hardware/Camera$Size:height	I
    //   400: iload_2
    //   401: isub
    //   402: invokestatic 162	java/lang/Math:abs	(I)I
    //   405: iadd
    //   406: istore 7
    //   408: iload 7
    //   410: iload 4
    //   412: if_icmpge -49 -> 363
    //   415: aload 10
    //   417: getfield 156	android/hardware/Camera$Size:width	I
    //   420: istore 5
    //   422: aload 10
    //   424: getfield 165	android/hardware/Camera$Size:height	I
    //   427: istore 6
    //   429: iload 7
    //   431: istore 4
    //   433: goto -70 -> 363
    //   436: iload 4
    //   438: ldc -115
    //   440: if_icmpeq +12 -> 452
    //   443: aload 8
    //   445: iload 5
    //   447: iload 6
    //   449: invokevirtual 202	android/hardware/Camera$Parameters:setPictureSize	(II)V
    //   452: aload 8
    //   454: iconst_1
    //   455: invokevirtual 205	android/hardware/Camera$Parameters:setRecordingHint	(Z)V
    //   458: aload_0
    //   459: getfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   462: aload 8
    //   464: invokevirtual 209	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   467: aload_0
    //   468: getfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   471: aload_3
    //   472: invokevirtual 213	android/hardware/Camera:setPreviewTexture	(Landroid/graphics/SurfaceTexture;)V
    //   475: aload_0
    //   476: new 215	com/tencent/smtt/webkit/webar/a
    //   479: dup
    //   480: aload_0
    //   481: aload_0
    //   482: getfield 218	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   485: invokespecial 221	com/tencent/smtt/webkit/webar/a:<init>	(Lcom/tencent/smtt/webkit/webar/IWebARAutoFocusProvider;Landroid/content/Context;)V
    //   488: putfield 32	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_ComTencentSmttWebkitWebarA	Lcom/tencent/smtt/webkit/webar/a;
    //   491: iconst_1
    //   492: ireturn
    //   493: astore_3
    //   494: new 59	java/lang/StringBuilder
    //   497: dup
    //   498: invokespecial 60	java/lang/StringBuilder:<init>	()V
    //   501: astore 8
    //   503: aload 8
    //   505: ldc -33
    //   507: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   510: pop
    //   511: aload 8
    //   513: aload_3
    //   514: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   517: pop
    //   518: ldc 71
    //   520: aload 8
    //   522: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   525: iconst_0
    //   526: anewarray 77	java/lang/Object
    //   529: invokestatic 83	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   532: iconst_0
    //   533: ireturn
    //   534: astore_3
    //   535: new 59	java/lang/StringBuilder
    //   538: dup
    //   539: invokespecial 60	java/lang/StringBuilder:<init>	()V
    //   542: astore 8
    //   544: aload 8
    //   546: ldc -31
    //   548: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   551: pop
    //   552: aload 8
    //   554: aload_3
    //   555: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   558: pop
    //   559: ldc 71
    //   561: aload 8
    //   563: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   566: iconst_0
    //   567: anewarray 77	java/lang/Object
    //   570: invokestatic 83	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   573: iconst_0
    //   574: ireturn
    //   575: astore_3
    //   576: new 59	java/lang/StringBuilder
    //   579: dup
    //   580: invokespecial 60	java/lang/StringBuilder:<init>	()V
    //   583: astore 8
    //   585: aload 8
    //   587: ldc -29
    //   589: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   592: pop
    //   593: aload 8
    //   595: aload_3
    //   596: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   599: pop
    //   600: ldc 71
    //   602: aload 8
    //   604: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   607: iconst_0
    //   608: anewarray 77	java/lang/Object
    //   611: invokestatic 83	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   614: iconst_0
    //   615: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	616	0	this	b
    //   0	616	1	paramInt1	int
    //   0	616	2	paramInt2	int
    //   0	616	3	paramSurfaceTexture	android.graphics.SurfaceTexture
    //   64	377	4	i	int
    //   150	296	5	j	int
    //   153	295	6	k	int
    //   202	228	7	m	int
    //   42	561	8	localObject1	Object
    //   61	313	9	localObject2	Object
    //   90	333	10	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   467	475	493	java/io/IOException
    //   458	467	534	java/lang/RuntimeException
    //   0	11	575	java/lang/RuntimeException
  }
  
  public void autoFocus(boolean paramBoolean)
  {
    try
    {
      if (this.jdField_a_of_type_AndroidHardwareCamera != null)
      {
        if (paramBoolean)
        {
          this.jdField_a_of_type_AndroidHardwareCamera.autoFocus(this.jdField_a_of_type_ComTencentSmttWebkitWebarA);
          return;
        }
        this.jdField_a_of_type_AndroidHardwareCamera.cancelAutoFocus();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  protected boolean canUploadCameraPicture()
  {
    return this.d;
  }
  
  public void deallocate()
  {
    if (this.jdField_a_of_type_AndroidHardwareCamera == null) {
      return;
    }
    stopCapture();
    try
    {
      this.jdField_a_of_type_AndroidHardwareCamera.setPreviewTexture(null);
      this.jdField_a_of_type_AndroidHardwareCamera.release();
      this.jdField_a_of_type_AndroidHardwareCamera = null;
      this.jdField_a_of_type_ComTencentSmttWebkitWebarA = null;
      return;
    }
    catch (IOException localIOException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("deallocate: failed to deallocate camera, ");
      localStringBuilder.append(localIOException);
      Log.e("WebARVideoCaptureCamera", localStringBuilder.toString(), new Object[0]);
    }
  }
  
  public void onFocusChanged(boolean paramBoolean)
  {
    this.jdField_b_of_type_Boolean = paramBoolean;
  }
  
  protected void onFrameBegin()
  {
    this.d = false;
    if (this.c) {
      return;
    }
    long l = System.currentTimeMillis();
    if (l - this.jdField_b_of_type_Long >= 1000L)
    {
      if (l - this.jdField_a_of_type_Long < 500L) {
        return;
      }
      if ((this.jdField_b_of_type_Boolean) && (!this.c))
      {
        int i = this.jdField_b_of_type_Int;
        if (i < 2)
        {
          this.jdField_b_of_type_Int = (i + 1);
          this.jdField_b_of_type_Long = l;
          this.d = true;
          return;
        }
      }
      if ((!this.c) && (l - this.jdField_b_of_type_Long > 3000L) && (l - this.jdField_a_of_type_Long > 3000L))
      {
        this.jdField_b_of_type_Int += 1;
        this.jdField_b_of_type_Long = l;
        this.d = true;
      }
      return;
    }
  }
  
  public void onMovingStatusChanged(boolean paramBoolean)
  {
    if (this.c == paramBoolean) {
      return;
    }
    this.c = paramBoolean;
    if (!this.c)
    {
      this.jdField_a_of_type_Long = System.currentTimeMillis();
      this.jdField_b_of_type_Int = 0;
    }
  }
  
  /* Error */
  public boolean startCapture()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   4: ifnonnull +17 -> 21
    //   7: ldc 71
    //   9: ldc_w 265
    //   12: iconst_0
    //   13: anewarray 77	java/lang/Object
    //   16: invokestatic 83	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   19: iconst_0
    //   20: ireturn
    //   21: aload_0
    //   22: getfield 30	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   25: invokevirtual 268	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   28: aload_0
    //   29: getfield 270	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_Boolean	Z
    //   32: istore_1
    //   33: iload_1
    //   34: ifeq +12 -> 46
    //   37: aload_0
    //   38: getfield 30	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   41: invokevirtual 273	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   44: iconst_1
    //   45: ireturn
    //   46: aload_0
    //   47: iconst_1
    //   48: putfield 270	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_Boolean	Z
    //   51: aload_0
    //   52: getfield 30	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   55: invokevirtual 273	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   58: aload_0
    //   59: getfield 107	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   62: invokevirtual 276	android/hardware/Camera:startPreview	()V
    //   65: aload_0
    //   66: getfield 32	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_ComTencentSmttWebkitWebarA	Lcom/tencent/smtt/webkit/webar/a;
    //   69: astore_2
    //   70: aload_2
    //   71: ifnull +7 -> 78
    //   74: aload_2
    //   75: invokevirtual 278	com/tencent/smtt/webkit/webar/a:a	()V
    //   78: iconst_1
    //   79: ireturn
    //   80: astore_2
    //   81: new 59	java/lang/StringBuilder
    //   84: dup
    //   85: invokespecial 60	java/lang/StringBuilder:<init>	()V
    //   88: astore_3
    //   89: aload_3
    //   90: ldc_w 280
    //   93: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload_3
    //   98: aload_2
    //   99: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: ldc 71
    //   105: aload_3
    //   106: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   109: iconst_0
    //   110: anewarray 77	java/lang/Object
    //   113: invokestatic 83	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   116: iconst_0
    //   117: ireturn
    //   118: astore_2
    //   119: aload_0
    //   120: getfield 30	com/tencent/smtt/webkit/webar/b:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   123: invokevirtual 273	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   126: aload_2
    //   127: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	128	0	this	b
    //   32	2	1	bool	boolean
    //   69	6	2	locala	a
    //   80	19	2	localRuntimeException	RuntimeException
    //   118	9	2	localObject	Object
    //   88	18	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   58	65	80	java/lang/RuntimeException
    //   28	33	118	finally
    //   46	51	118	finally
  }
  
  protected boolean stopCapture()
  {
    if (this.jdField_a_of_type_AndroidHardwareCamera == null)
    {
      Log.e("WebARVideoCaptureCamera", "stopCapture: mCamera is null", new Object[0]);
      return true;
    }
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
    try
    {
      boolean bool = this.jdField_a_of_type_Boolean;
      if (!bool) {
        return true;
      }
      this.jdField_a_of_type_Boolean = false;
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
      this.jdField_a_of_type_AndroidHardwareCamera.stopPreview();
      a locala = this.jdField_a_of_type_ComTencentSmttWebkitWebarA;
      if (locala != null) {
        locala.b();
      }
      return true;
    }
    finally
    {
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\webar\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */