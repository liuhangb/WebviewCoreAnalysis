package org.chromium.media;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjection.Callback;
import android.media.projection.MediaProjectionManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import java.nio.ByteBuffer;
import org.chromium.base.ApplicationStatus;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
@TargetApi(21)
public class ScreenCapture
  extends Fragment
{
  private int jdField_a_of_type_Int;
  private final long jdField_a_of_type_Long;
  private Intent jdField_a_of_type_AndroidContentIntent;
  private VirtualDisplay jdField_a_of_type_AndroidHardwareDisplayVirtualDisplay;
  private ImageReader jdField_a_of_type_AndroidMediaImageReader;
  private MediaProjection jdField_a_of_type_AndroidMediaProjectionMediaProjection;
  private MediaProjectionManager jdField_a_of_type_AndroidMediaProjectionMediaProjectionManager;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private Display jdField_a_of_type_AndroidViewDisplay;
  private Surface jdField_a_of_type_AndroidViewSurface;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private CaptureState jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState = CaptureState.e;
  private DeviceOrientation jdField_a_of_type_OrgChromiumMediaScreenCapture$DeviceOrientation;
  private int b;
  private int c;
  private int d;
  private int e;
  
  ScreenCapture(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
  }
  
  private int a()
  {
    switch (this.jdField_a_of_type_AndroidViewDisplay.getRotation())
    {
    default: 
      if (jdField_a_of_type_Boolean) {
        return 0;
      }
      break;
    case 3: 
      return 270;
    case 2: 
      return 180;
    case 1: 
      return 90;
    case 0: 
      return 0;
    }
    throw new AssertionError();
  }
  
  private DeviceOrientation a(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != 90)
      {
        if (paramInt == 180) {
          break label46;
        }
        if (paramInt != 270)
        {
          if (jdField_a_of_type_Boolean) {
            return DeviceOrientation.b;
          }
          throw new AssertionError();
        }
      }
      return DeviceOrientation.b;
    }
    label46:
    return DeviceOrientation.jdField_a_of_type_OrgChromiumMediaScreenCapture$DeviceOrientation;
  }
  
  private void a()
  {
    Object localObject = this.jdField_a_of_type_AndroidMediaImageReader;
    if (localObject != null) {
      ((ImageReader)localObject).close();
    }
    this.jdField_a_of_type_AndroidMediaImageReader = ImageReader.newInstance(this.b, this.c, this.d, 2);
    this.jdField_a_of_type_AndroidViewSurface = this.jdField_a_of_type_AndroidMediaImageReader.getSurface();
    localObject = new CrImageReaderListener(null);
    this.jdField_a_of_type_AndroidMediaImageReader.setOnImageAvailableListener((ImageReader.OnImageAvailableListener)localObject, this.jdField_a_of_type_AndroidOsHandler);
  }
  
  private void a(CaptureState paramCaptureState)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState = paramCaptureState;
      this.jdField_a_of_type_JavaLangObject.notifyAll();
      return;
    }
  }
  
  private void a(DeviceOrientation paramDeviceOrientation)
  {
    if (((paramDeviceOrientation == DeviceOrientation.b) && (this.b < this.c)) || ((paramDeviceOrientation == DeviceOrientation.jdField_a_of_type_OrgChromiumMediaScreenCapture$DeviceOrientation) && (this.c < this.b)))
    {
      int i = this.b;
      int j = this.c;
      this.c = i;
      this.b = (i + (j - i));
    }
  }
  
  private boolean a()
  {
    int i = a();
    DeviceOrientation localDeviceOrientation = a(i);
    if (localDeviceOrientation == this.jdField_a_of_type_OrgChromiumMediaScreenCapture$DeviceOrientation) {
      return false;
    }
    this.jdField_a_of_type_OrgChromiumMediaScreenCapture$DeviceOrientation = localDeviceOrientation;
    a(localDeviceOrientation);
    nativeOnOrientationChange(this.jdField_a_of_type_Long, i);
    return true;
  }
  
  private void b()
  {
    VirtualDisplay localVirtualDisplay = this.jdField_a_of_type_AndroidHardwareDisplayVirtualDisplay;
    if (localVirtualDisplay != null) {
      localVirtualDisplay.release();
    }
    this.jdField_a_of_type_AndroidHardwareDisplayVirtualDisplay = this.jdField_a_of_type_AndroidMediaProjectionMediaProjection.createVirtualDisplay("ScreenCapture", this.b, this.c, this.jdField_a_of_type_Int, 16, this.jdField_a_of_type_AndroidViewSurface, null, null);
  }
  
  @CalledByNative
  static ScreenCapture createScreenCaptureMachine(long paramLong)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return new ScreenCapture(paramLong);
    }
    return null;
  }
  
  private native void nativeOnActivityResult(long paramLong, boolean paramBoolean);
  
  private native void nativeOnI420FrameAvailable(long paramLong1, ByteBuffer paramByteBuffer1, int paramInt1, ByteBuffer paramByteBuffer2, ByteBuffer paramByteBuffer3, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong2);
  
  private native void nativeOnOrientationChange(long paramLong, int paramInt);
  
  private native void nativeOnRGBAFrameAvailable(long paramLong1, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong2);
  
  @CalledByNative
  public boolean allocate(int paramInt1, int paramInt2)
  {
    this.b = paramInt1;
    this.c = paramInt2;
    this.jdField_a_of_type_AndroidMediaProjectionMediaProjectionManager = ((MediaProjectionManager)ContextUtils.getApplicationContext().getSystemService("media_projection"));
    if (this.jdField_a_of_type_AndroidMediaProjectionMediaProjectionManager == null)
    {
      Log.e("cr_ScreenCapture", "mMediaProjectionManager is null", new Object[0]);
      return false;
    }
    this.jdField_a_of_type_AndroidViewDisplay = ((WindowManager)ContextUtils.getApplicationContext().getSystemService("window")).getDefaultDisplay();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    this.jdField_a_of_type_AndroidViewDisplay.getMetrics(localDisplayMetrics);
    this.jdField_a_of_type_Int = localDisplayMetrics.densityDpi;
    return true;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    boolean bool = true;
    if (paramInt1 != 1) {
      return;
    }
    if (paramInt2 == -1)
    {
      this.e = paramInt2;
      this.jdField_a_of_type_AndroidContentIntent = paramIntent;
      a(CaptureState.b);
    }
    long l = this.jdField_a_of_type_Long;
    if (paramInt2 != -1) {
      bool = false;
    }
    nativeOnActivityResult(l, bool);
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    a(CaptureState.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState);
  }
  
  public void onAttach(Context paramContext)
  {
    super.onAttach(paramContext);
    a(CaptureState.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState);
  }
  
  public void onDetach()
  {
    super.onDetach();
    stopCapture();
  }
  
  @CalledByNative
  public boolean startCapture()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState != CaptureState.b)
      {
        Log.e("cr_ScreenCapture", "startCapture() invoked without user permission.", new Object[0]);
        return false;
      }
      this.jdField_a_of_type_AndroidMediaProjectionMediaProjection = this.jdField_a_of_type_AndroidMediaProjectionMediaProjectionManager.getMediaProjection(this.e, this.jdField_a_of_type_AndroidContentIntent);
      ??? = this.jdField_a_of_type_AndroidMediaProjectionMediaProjection;
      if (??? == null)
      {
        Log.e("cr_ScreenCapture", "mMediaProjection is null", new Object[0]);
        return false;
      }
      ((MediaProjection)???).registerCallback(new MediaProjectionCallback(null), null);
      this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("ScreenCapture");
      this.jdField_a_of_type_AndroidOsHandlerThread.start();
      this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper());
      this.d = 1;
      a();
      a();
      b();
      a(CaptureState.c);
      return true;
    }
  }
  
  @CalledByNative
  public boolean startPrompt()
  {
    ??? = ApplicationStatus.getLastTrackedFocusedActivity();
    if (??? == null)
    {
      Log.e("cr_ScreenCapture", "activity is null", new Object[0]);
      return false;
    }
    ??? = ((Activity)???).getFragmentManager().beginTransaction();
    ((FragmentTransaction)???).add(this, "screencapture");
    try
    {
      ((FragmentTransaction)???).commit();
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        for (;;)
        {
          CaptureState localCaptureState = this.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState;
          Object localObject3 = CaptureState.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState;
          if (localCaptureState == localObject3) {
            break;
          }
          try
          {
            this.jdField_a_of_type_JavaLangObject.wait();
          }
          catch (InterruptedException localInterruptedException)
          {
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("ScreenCaptureException: ");
            ((StringBuilder)localObject3).append(localInterruptedException);
            Log.e("cr_ScreenCapture", ((StringBuilder)localObject3).toString(), new Object[0]);
          }
        }
        try
        {
          startActivityForResult(this.jdField_a_of_type_AndroidMediaProjectionMediaProjectionManager.createScreenCaptureIntent(), 1);
          return true;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          StringBuilder localStringBuilder1 = new StringBuilder();
          localStringBuilder1.append("ScreenCaptureException ");
          localStringBuilder1.append(localActivityNotFoundException);
          Log.e("cr_ScreenCapture", localStringBuilder1.toString(), new Object[0]);
          return false;
        }
      }
      StringBuilder localStringBuilder2;
      return false;
    }
    catch (RuntimeException localRuntimeException)
    {
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("ScreenCaptureExcaption ");
      localStringBuilder2.append(localRuntimeException);
      Log.e("cr_ScreenCapture", localStringBuilder2.toString(), new Object[0]);
    }
  }
  
  @CalledByNative
  public void stopCapture()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if ((this.jdField_a_of_type_AndroidMediaProjectionMediaProjection != null) && (this.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState == CaptureState.c))
      {
        this.jdField_a_of_type_AndroidMediaProjectionMediaProjection.stop();
        a(CaptureState.d);
        for (;;)
        {
          CaptureState localCaptureState = this.jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState;
          Object localObject3 = CaptureState.e;
          if (localCaptureState == localObject3) {
            break;
          }
          try
          {
            this.jdField_a_of_type_JavaLangObject.wait();
          }
          catch (InterruptedException localInterruptedException)
          {
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("ScreenCaptureEvent: ");
            ((StringBuilder)localObject3).append(localInterruptedException);
            Log.e("cr_ScreenCapture", ((StringBuilder)localObject3).toString(), new Object[0]);
          }
        }
      }
      a(CaptureState.e);
      return;
    }
  }
  
  private static enum CaptureState
  {
    static
    {
      jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState = new CaptureState("ATTACHED", 0);
      b = new CaptureState("ALLOWED", 1);
      c = new CaptureState("STARTED", 2);
      d = new CaptureState("STOPPING", 3);
      e = new CaptureState("STOPPED", 4);
      jdField_a_of_type_ArrayOfOrgChromiumMediaScreenCapture$CaptureState = new CaptureState[] { jdField_a_of_type_OrgChromiumMediaScreenCapture$CaptureState, b, c, d, e };
    }
    
    private CaptureState() {}
  }
  
  private class CrImageReaderListener
    implements ImageReader.OnImageAvailableListener
  {
    private CrImageReaderListener() {}
    
    /* Error */
    public void onImageAvailable(ImageReader paramImageReader)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   4: invokestatic 33	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)Ljava/lang/Object;
      //   7: astore_3
      //   8: aload_3
      //   9: monitorenter
      //   10: aload_0
      //   11: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   14: invokestatic 36	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)Lorg/chromium/media/ScreenCapture$CaptureState;
      //   17: getstatic 42	org/chromium/media/ScreenCapture$CaptureState:c	Lorg/chromium/media/ScreenCapture$CaptureState;
      //   20: if_acmpeq +17 -> 37
      //   23: ldc 44
      //   25: ldc 46
      //   27: iconst_0
      //   28: anewarray 4	java/lang/Object
      //   31: invokestatic 52	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   34: aload_3
      //   35: monitorexit
      //   36: return
      //   37: aload_3
      //   38: monitorexit
      //   39: aload_0
      //   40: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   43: invokestatic 55	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)Z
      //   46: ifeq +18 -> 64
      //   49: aload_0
      //   50: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   53: invokestatic 57	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)V
      //   56: aload_0
      //   57: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   60: invokestatic 60	org/chromium/media/ScreenCapture:b	(Lorg/chromium/media/ScreenCapture;)V
      //   63: return
      //   64: aload_1
      //   65: invokevirtual 66	android/media/ImageReader:acquireLatestImage	()Landroid/media/Image;
      //   68: astore 5
      //   70: aconst_null
      //   71: astore 4
      //   73: aload 5
      //   75: ifnonnull +14 -> 89
      //   78: aload 5
      //   80: ifnull +8 -> 88
      //   83: aload 5
      //   85: invokevirtual 71	android/media/Image:close	()V
      //   88: return
      //   89: aload 4
      //   91: astore_3
      //   92: aload_1
      //   93: invokevirtual 75	android/media/ImageReader:getWidth	()I
      //   96: aload 5
      //   98: invokevirtual 76	android/media/Image:getWidth	()I
      //   101: if_icmpne +451 -> 552
      //   104: aload 4
      //   106: astore_3
      //   107: aload_1
      //   108: invokevirtual 79	android/media/ImageReader:getHeight	()I
      //   111: aload 5
      //   113: invokevirtual 80	android/media/Image:getHeight	()I
      //   116: if_icmpne +436 -> 552
      //   119: aload 4
      //   121: astore_3
      //   122: aload 5
      //   124: invokevirtual 83	android/media/Image:getFormat	()I
      //   127: istore_2
      //   128: iload_2
      //   129: iconst_1
      //   130: if_icmpeq +262 -> 392
      //   133: iload_2
      //   134: bipush 35
      //   136: if_icmpne +195 -> 331
      //   139: aload 4
      //   141: astore_3
      //   142: aload 5
      //   144: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   147: arraylength
      //   148: iconst_3
      //   149: if_icmpne +120 -> 269
      //   152: aload 4
      //   154: astore_3
      //   155: aload_0
      //   156: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   159: aload_0
      //   160: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   163: invokestatic 90	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)J
      //   166: aload 5
      //   168: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   171: iconst_0
      //   172: aaload
      //   173: invokevirtual 96	android/media/Image$Plane:getBuffer	()Ljava/nio/ByteBuffer;
      //   176: aload 5
      //   178: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   181: iconst_0
      //   182: aaload
      //   183: invokevirtual 99	android/media/Image$Plane:getRowStride	()I
      //   186: aload 5
      //   188: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   191: iconst_1
      //   192: aaload
      //   193: invokevirtual 96	android/media/Image$Plane:getBuffer	()Ljava/nio/ByteBuffer;
      //   196: aload 5
      //   198: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   201: iconst_2
      //   202: aaload
      //   203: invokevirtual 96	android/media/Image$Plane:getBuffer	()Ljava/nio/ByteBuffer;
      //   206: aload 5
      //   208: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   211: iconst_1
      //   212: aaload
      //   213: invokevirtual 99	android/media/Image$Plane:getRowStride	()I
      //   216: aload 5
      //   218: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   221: iconst_1
      //   222: aaload
      //   223: invokevirtual 102	android/media/Image$Plane:getPixelStride	()I
      //   226: aload 5
      //   228: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   231: getfield 112	android/graphics/Rect:left	I
      //   234: aload 5
      //   236: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   239: getfield 115	android/graphics/Rect:top	I
      //   242: aload 5
      //   244: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   247: invokevirtual 118	android/graphics/Rect:width	()I
      //   250: aload 5
      //   252: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   255: invokevirtual 121	android/graphics/Rect:height	()I
      //   258: aload 5
      //   260: invokevirtual 125	android/media/Image:getTimestamp	()J
      //   263: invokestatic 128	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;JLjava/nio/ByteBuffer;ILjava/nio/ByteBuffer;Ljava/nio/ByteBuffer;IIIIIIJ)V
      //   266: goto +213 -> 479
      //   269: aload 4
      //   271: astore_3
      //   272: new 130	java/lang/StringBuilder
      //   275: dup
      //   276: invokespecial 131	java/lang/StringBuilder:<init>	()V
      //   279: astore_1
      //   280: aload 4
      //   282: astore_3
      //   283: aload_1
      //   284: ldc -123
      //   286: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   289: pop
      //   290: aload 4
      //   292: astore_3
      //   293: aload_1
      //   294: aload 5
      //   296: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   299: arraylength
      //   300: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   303: pop
      //   304: aload 4
      //   306: astore_3
      //   307: ldc 44
      //   309: aload_1
      //   310: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   313: iconst_0
      //   314: anewarray 4	java/lang/Object
      //   317: invokestatic 52	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   320: aload 4
      //   322: astore_3
      //   323: new 26	java/lang/IllegalStateException
      //   326: dup
      //   327: invokespecial 145	java/lang/IllegalStateException:<init>	()V
      //   330: athrow
      //   331: aload 4
      //   333: astore_3
      //   334: new 130	java/lang/StringBuilder
      //   337: dup
      //   338: invokespecial 131	java/lang/StringBuilder:<init>	()V
      //   341: astore_1
      //   342: aload 4
      //   344: astore_3
      //   345: aload_1
      //   346: ldc -109
      //   348: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   351: pop
      //   352: aload 4
      //   354: astore_3
      //   355: aload_1
      //   356: aload 5
      //   358: invokevirtual 83	android/media/Image:getFormat	()I
      //   361: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   364: pop
      //   365: aload 4
      //   367: astore_3
      //   368: ldc 44
      //   370: aload_1
      //   371: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   374: iconst_0
      //   375: anewarray 4	java/lang/Object
      //   378: invokestatic 52	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   381: aload 4
      //   383: astore_3
      //   384: new 26	java/lang/IllegalStateException
      //   387: dup
      //   388: invokespecial 145	java/lang/IllegalStateException:<init>	()V
      //   391: athrow
      //   392: aload 4
      //   394: astore_3
      //   395: aload 5
      //   397: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   400: arraylength
      //   401: iconst_1
      //   402: if_icmpne +88 -> 490
      //   405: aload 4
      //   407: astore_3
      //   408: aload_0
      //   409: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   412: aload_0
      //   413: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   416: invokestatic 90	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)J
      //   419: aload 5
      //   421: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   424: iconst_0
      //   425: aaload
      //   426: invokevirtual 96	android/media/Image$Plane:getBuffer	()Ljava/nio/ByteBuffer;
      //   429: aload 5
      //   431: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   434: iconst_0
      //   435: aaload
      //   436: invokevirtual 99	android/media/Image$Plane:getRowStride	()I
      //   439: aload 5
      //   441: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   444: getfield 112	android/graphics/Rect:left	I
      //   447: aload 5
      //   449: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   452: getfield 115	android/graphics/Rect:top	I
      //   455: aload 5
      //   457: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   460: invokevirtual 118	android/graphics/Rect:width	()I
      //   463: aload 5
      //   465: invokevirtual 106	android/media/Image:getCropRect	()Landroid/graphics/Rect;
      //   468: invokevirtual 121	android/graphics/Rect:height	()I
      //   471: aload 5
      //   473: invokevirtual 125	android/media/Image:getTimestamp	()J
      //   476: invokestatic 150	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;JLjava/nio/ByteBuffer;IIIIIJ)V
      //   479: aload 5
      //   481: ifnull +335 -> 816
      //   484: aload 5
      //   486: invokevirtual 71	android/media/Image:close	()V
      //   489: return
      //   490: aload 4
      //   492: astore_3
      //   493: new 130	java/lang/StringBuilder
      //   496: dup
      //   497: invokespecial 131	java/lang/StringBuilder:<init>	()V
      //   500: astore_1
      //   501: aload 4
      //   503: astore_3
      //   504: aload_1
      //   505: ldc -104
      //   507: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   510: pop
      //   511: aload 4
      //   513: astore_3
      //   514: aload_1
      //   515: aload 5
      //   517: invokevirtual 87	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   520: arraylength
      //   521: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   524: pop
      //   525: aload 4
      //   527: astore_3
      //   528: ldc 44
      //   530: aload_1
      //   531: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   534: iconst_0
      //   535: anewarray 4	java/lang/Object
      //   538: invokestatic 52	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   541: aload 4
      //   543: astore_3
      //   544: new 26	java/lang/IllegalStateException
      //   547: dup
      //   548: invokespecial 145	java/lang/IllegalStateException:<init>	()V
      //   551: athrow
      //   552: aload 4
      //   554: astore_3
      //   555: new 130	java/lang/StringBuilder
      //   558: dup
      //   559: invokespecial 131	java/lang/StringBuilder:<init>	()V
      //   562: astore 6
      //   564: aload 4
      //   566: astore_3
      //   567: aload 6
      //   569: ldc -102
      //   571: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   574: pop
      //   575: aload 4
      //   577: astore_3
      //   578: aload 6
      //   580: aload_1
      //   581: invokevirtual 75	android/media/ImageReader:getWidth	()I
      //   584: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   587: pop
      //   588: aload 4
      //   590: astore_3
      //   591: aload 6
      //   593: ldc -100
      //   595: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   598: pop
      //   599: aload 4
      //   601: astore_3
      //   602: aload 6
      //   604: aload_1
      //   605: invokevirtual 79	android/media/ImageReader:getHeight	()I
      //   608: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   611: pop
      //   612: aload 4
      //   614: astore_3
      //   615: aload 6
      //   617: ldc -98
      //   619: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   622: pop
      //   623: aload 4
      //   625: astore_3
      //   626: aload 6
      //   628: aload 5
      //   630: invokevirtual 76	android/media/Image:getWidth	()I
      //   633: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   636: pop
      //   637: aload 4
      //   639: astore_3
      //   640: aload 6
      //   642: ldc -100
      //   644: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   647: pop
      //   648: aload 4
      //   650: astore_3
      //   651: aload 6
      //   653: aload 5
      //   655: invokevirtual 80	android/media/Image:getHeight	()I
      //   658: invokevirtual 140	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   661: pop
      //   662: aload 4
      //   664: astore_3
      //   665: aload 6
      //   667: ldc -96
      //   669: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   672: pop
      //   673: aload 4
      //   675: astore_3
      //   676: ldc 44
      //   678: aload 6
      //   680: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   683: iconst_0
      //   684: anewarray 4	java/lang/Object
      //   687: invokestatic 52	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   690: aload 4
      //   692: astore_3
      //   693: new 26	java/lang/IllegalStateException
      //   696: dup
      //   697: invokespecial 145	java/lang/IllegalStateException:<init>	()V
      //   700: athrow
      //   701: astore_1
      //   702: goto +8 -> 710
      //   705: astore_1
      //   706: aload_1
      //   707: astore_3
      //   708: aload_1
      //   709: athrow
      //   710: aload 5
      //   712: ifnull +31 -> 743
      //   715: aload_3
      //   716: ifnull +22 -> 738
      //   719: aload 5
      //   721: invokevirtual 71	android/media/Image:close	()V
      //   724: goto +19 -> 743
      //   727: astore 4
      //   729: aload_3
      //   730: aload 4
      //   732: invokevirtual 164	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
      //   735: goto +8 -> 743
      //   738: aload 5
      //   740: invokevirtual 71	android/media/Image:close	()V
      //   743: aload_1
      //   744: athrow
      //   745: aload_0
      //   746: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   749: invokestatic 167	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)I
      //   752: bipush 35
      //   754: if_icmpne +62 -> 816
      //   757: aload_0
      //   758: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   761: iconst_1
      //   762: invokestatic 170	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;I)I
      //   765: pop
      //   766: aload_0
      //   767: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   770: invokestatic 57	org/chromium/media/ScreenCapture:a	(Lorg/chromium/media/ScreenCapture;)V
      //   773: aload_0
      //   774: getfield 15	org/chromium/media/ScreenCapture$CrImageReaderListener:a	Lorg/chromium/media/ScreenCapture;
      //   777: invokestatic 60	org/chromium/media/ScreenCapture:b	(Lorg/chromium/media/ScreenCapture;)V
      //   780: return
      //   781: astore_1
      //   782: new 130	java/lang/StringBuilder
      //   785: dup
      //   786: invokespecial 131	java/lang/StringBuilder:<init>	()V
      //   789: astore_3
      //   790: aload_3
      //   791: ldc -84
      //   793: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   796: pop
      //   797: aload_3
      //   798: aload_1
      //   799: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   802: pop
      //   803: ldc 44
      //   805: aload_3
      //   806: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   809: iconst_0
      //   810: anewarray 4	java/lang/Object
      //   813: invokestatic 52	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   816: return
      //   817: astore_1
      //   818: aload_3
      //   819: monitorexit
      //   820: aload_1
      //   821: athrow
      //   822: astore_1
      //   823: goto -78 -> 745
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	826	0	this	CrImageReaderListener
      //   0	826	1	paramImageReader	ImageReader
      //   127	10	2	i	int
      //   7	812	3	localObject1	Object
      //   71	620	4	localObject2	Object
      //   727	4	4	localThrowable	Throwable
      //   68	671	5	localImage	android.media.Image
      //   562	117	6	localStringBuilder	StringBuilder
      // Exception table:
      //   from	to	target	type
      //   92	104	701	finally
      //   107	119	701	finally
      //   122	128	701	finally
      //   142	152	701	finally
      //   155	266	701	finally
      //   272	280	701	finally
      //   283	290	701	finally
      //   293	304	701	finally
      //   307	320	701	finally
      //   323	331	701	finally
      //   334	342	701	finally
      //   345	352	701	finally
      //   355	365	701	finally
      //   368	381	701	finally
      //   384	392	701	finally
      //   395	405	701	finally
      //   408	479	701	finally
      //   493	501	701	finally
      //   504	511	701	finally
      //   514	525	701	finally
      //   528	541	701	finally
      //   544	552	701	finally
      //   555	564	701	finally
      //   567	575	701	finally
      //   578	588	701	finally
      //   591	599	701	finally
      //   602	612	701	finally
      //   615	623	701	finally
      //   626	637	701	finally
      //   640	648	701	finally
      //   651	662	701	finally
      //   665	673	701	finally
      //   676	690	701	finally
      //   693	701	701	finally
      //   708	710	701	finally
      //   92	104	705	java/lang/Throwable
      //   107	119	705	java/lang/Throwable
      //   122	128	705	java/lang/Throwable
      //   142	152	705	java/lang/Throwable
      //   155	266	705	java/lang/Throwable
      //   272	280	705	java/lang/Throwable
      //   283	290	705	java/lang/Throwable
      //   293	304	705	java/lang/Throwable
      //   307	320	705	java/lang/Throwable
      //   323	331	705	java/lang/Throwable
      //   334	342	705	java/lang/Throwable
      //   345	352	705	java/lang/Throwable
      //   355	365	705	java/lang/Throwable
      //   368	381	705	java/lang/Throwable
      //   384	392	705	java/lang/Throwable
      //   395	405	705	java/lang/Throwable
      //   408	479	705	java/lang/Throwable
      //   493	501	705	java/lang/Throwable
      //   504	511	705	java/lang/Throwable
      //   514	525	705	java/lang/Throwable
      //   528	541	705	java/lang/Throwable
      //   544	552	705	java/lang/Throwable
      //   555	564	705	java/lang/Throwable
      //   567	575	705	java/lang/Throwable
      //   578	588	705	java/lang/Throwable
      //   591	599	705	java/lang/Throwable
      //   602	612	705	java/lang/Throwable
      //   615	623	705	java/lang/Throwable
      //   626	637	705	java/lang/Throwable
      //   640	648	705	java/lang/Throwable
      //   651	662	705	java/lang/Throwable
      //   665	673	705	java/lang/Throwable
      //   676	690	705	java/lang/Throwable
      //   693	701	705	java/lang/Throwable
      //   719	724	727	java/lang/Throwable
      //   64	70	781	java/lang/IllegalStateException
      //   83	88	781	java/lang/IllegalStateException
      //   484	489	781	java/lang/IllegalStateException
      //   719	724	781	java/lang/IllegalStateException
      //   729	735	781	java/lang/IllegalStateException
      //   738	743	781	java/lang/IllegalStateException
      //   743	745	781	java/lang/IllegalStateException
      //   10	36	817	finally
      //   37	39	817	finally
      //   818	820	817	finally
      //   64	70	822	java/lang/UnsupportedOperationException
      //   83	88	822	java/lang/UnsupportedOperationException
      //   484	489	822	java/lang/UnsupportedOperationException
      //   719	724	822	java/lang/UnsupportedOperationException
      //   729	735	822	java/lang/UnsupportedOperationException
      //   738	743	822	java/lang/UnsupportedOperationException
      //   743	745	822	java/lang/UnsupportedOperationException
    }
  }
  
  private static enum DeviceOrientation
  {
    static
    {
      jdField_a_of_type_OrgChromiumMediaScreenCapture$DeviceOrientation = new DeviceOrientation("PORTRAIT", 0);
      b = new DeviceOrientation("LANDSCAPE", 1);
      jdField_a_of_type_ArrayOfOrgChromiumMediaScreenCapture$DeviceOrientation = new DeviceOrientation[] { jdField_a_of_type_OrgChromiumMediaScreenCapture$DeviceOrientation, b };
    }
    
    private DeviceOrientation() {}
  }
  
  private class MediaProjectionCallback
    extends MediaProjection.Callback
  {
    private MediaProjectionCallback() {}
    
    public void onStop()
    {
      ScreenCapture.a(ScreenCapture.this, ScreenCapture.CaptureState.e);
      ScreenCapture.a(ScreenCapture.this, null);
      if (ScreenCapture.a(ScreenCapture.this) == null) {
        return;
      }
      ScreenCapture.a(ScreenCapture.this).release();
      ScreenCapture.a(ScreenCapture.this, null);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\ScreenCapture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */