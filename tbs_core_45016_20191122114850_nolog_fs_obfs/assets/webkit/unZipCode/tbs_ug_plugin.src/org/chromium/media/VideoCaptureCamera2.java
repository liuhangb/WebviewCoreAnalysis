package org.chromium.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureRequest.Key;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.OrientationEventListener;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.SmttServiceClientProxy;

@JNINamespace("media")
@TargetApi(23)
public class VideoCaptureCamera2
  extends VideoCapture
{
  private static final SparseIntArray jdField_a_of_type_AndroidUtilSparseIntArray;
  private float jdField_a_of_type_Float = 1.0F;
  private Rect jdField_a_of_type_AndroidGraphicsRect = new Rect();
  private CameraCaptureSession jdField_a_of_type_AndroidHardwareCamera2CameraCaptureSession;
  private CameraDevice jdField_a_of_type_AndroidHardwareCamera2CameraDevice;
  private CaptureRequest.Builder jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder;
  private CaptureRequest jdField_a_of_type_AndroidHardwareCamera2CaptureRequest;
  private MeteringRectangle jdField_a_of_type_AndroidHardwareCamera2ParamsMeteringRectangle;
  private ImageReader jdField_a_of_type_AndroidMediaImageReader = null;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private final Looper jdField_a_of_type_AndroidOsLooper = Looper.myLooper();
  private Range<Integer> jdField_a_of_type_AndroidUtilRange;
  private OrientationEventListener jdField_a_of_type_AndroidViewOrientationEventListener;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private final Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      
      if ((!jdField_a_of_type_Boolean) && (VideoCaptureCamera2.a(VideoCaptureCamera2.this) == null)) {
        throw new AssertionError("preview request builder");
      }
      if ((!jdField_a_of_type_Boolean) && (VideoCaptureCamera2.a(VideoCaptureCamera2.this) == null)) {
        throw new AssertionError("preview session");
      }
      VideoCaptureCamera2 localVideoCaptureCamera2 = VideoCaptureCamera2.this;
      VideoCaptureCamera2.a(localVideoCaptureCamera2, VideoCaptureCamera2.a(localVideoCaptureCamera2));
      try
      {
        VideoCaptureCamera2.a(VideoCaptureCamera2.this).setRepeatingRequest(VideoCaptureCamera2.a(VideoCaptureCamera2.this).build(), null, null);
        return;
      }
      catch (Throwable localThrowable)
      {
        Log.e("VideoCapture", "setRepeatingRequest: ", new Object[] { localThrowable });
      }
    }
  };
  private CameraState jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState = CameraState.d;
  private byte[] jdField_a_of_type_ArrayOfByte;
  private long b;
  private int jdField_d_of_type_Int;
  private boolean jdField_d_of_type_Boolean;
  private int jdField_e_of_type_Int;
  private boolean jdField_e_of_type_Boolean;
  private int jdField_f_of_type_Int = 4;
  private boolean jdField_f_of_type_Boolean = false;
  private int g = 4;
  private int h;
  private int i = 4;
  private int j = -1;
  private int k;
  private int l = 1;
  private int m;
  
  static
  {
    jdField_c_of_type_Boolean = VideoCaptureCamera2.class.desiredAssertionStatus() ^ true;
    jdField_a_of_type_AndroidUtilSparseIntArray = new SparseIntArray();
    jdField_a_of_type_AndroidUtilSparseIntArray.append(2850, 2);
    jdField_a_of_type_AndroidUtilSparseIntArray.append(2950, 4);
    jdField_a_of_type_AndroidUtilSparseIntArray.append(4250, 3);
    jdField_a_of_type_AndroidUtilSparseIntArray.append(4600, 7);
    jdField_a_of_type_AndroidUtilSparseIntArray.append(5000, 5);
    jdField_a_of_type_AndroidUtilSparseIntArray.append(6000, 6);
    jdField_a_of_type_AndroidUtilSparseIntArray.append(7000, 8);
  }
  
  VideoCaptureCamera2(int paramInt, long paramLong)
  {
    super(paramInt, paramLong);
    CameraCharacteristics localCameraCharacteristics = a(paramInt);
    if (localCameraCharacteristics != null) {
      this.jdField_a_of_type_Float = ((Float)localCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue();
    }
  }
  
  static int a()
  {
    try
    {
      CameraManager localCameraManager = (CameraManager)ContextUtils.getApplicationContext().getSystemService("camera");
      if (localCameraManager == null) {
        return 0;
      }
      try
      {
        int n = localCameraManager.getCameraIdList().length;
        return n;
      }
      catch (Throwable localThrowable1)
      {
        Log.e("VideoCapture", "getNumberOfCameras: getCameraIdList(): ", new Object[] { localThrowable1 });
        return 0;
      }
      return 0;
    }
    catch (Throwable localThrowable2)
    {
      Log.e("VideoCapture", "getSystemService(Context.CAMERA_SERVICE): ", new Object[] { localThrowable2 });
    }
  }
  
  static int a(int paramInt)
  {
    CameraCharacteristics localCameraCharacteristics = a(paramInt);
    if (localCameraCharacteristics == null) {
      return 9;
    }
    switch (((Integer)localCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue())
    {
    default: 
      return 6;
    case 2: 
      return 6;
    case 1: 
      return 7;
    }
    return 8;
  }
  
  private int a(int paramInt, int[] paramArrayOfInt)
  {
    int n = 0;
    int i2 = -1;
    int i1 = Integer.MAX_VALUE;
    while (n < jdField_a_of_type_AndroidUtilSparseIntArray.size())
    {
      if (a(paramArrayOfInt, jdField_a_of_type_AndroidUtilSparseIntArray.valueAt(n)) != -1)
      {
        int i3 = Math.abs(paramInt - jdField_a_of_type_AndroidUtilSparseIntArray.keyAt(n));
        if (i3 < i1)
        {
          i2 = jdField_a_of_type_AndroidUtilSparseIntArray.valueAt(n);
          i1 = i3;
        }
      }
      n += 1;
    }
    return i2;
  }
  
  private static int a(int[] paramArrayOfInt, int paramInt)
  {
    int n = 0;
    while (n < paramArrayOfInt.length)
    {
      if (paramInt == paramArrayOfInt[n]) {
        return n;
      }
      n += 1;
    }
    return -1;
  }
  
  private static CameraCharacteristics a(int paramInt)
  {
    Object localObject = (CameraManager)ContextUtils.getApplicationContext().getSystemService("camera");
    try
    {
      localObject = ((CameraManager)localObject).getCameraCharacteristics(Integer.toString(paramInt));
      return (CameraCharacteristics)localObject;
    }
    catch (Throwable localThrowable)
    {
      Log.e("VideoCapture", "getCameraCharacteristics: ", new Object[] { localThrowable });
    }
    return null;
  }
  
  private static Size a(Size[] paramArrayOfSize, int paramInt1, int paramInt2)
  {
    if (paramArrayOfSize == null) {
      return null;
    }
    int i4 = paramArrayOfSize.length;
    Object localObject = null;
    int n = 0;
    int i2;
    for (int i1 = Integer.MAX_VALUE; n < i4; i1 = i2)
    {
      Size localSize = paramArrayOfSize[n];
      if (paramInt1 > 0) {
        i2 = Math.abs(localSize.getWidth() - paramInt1);
      } else {
        i2 = 0;
      }
      if (paramInt2 > 0) {
        i3 = Math.abs(localSize.getHeight() - paramInt2);
      } else {
        i3 = 0;
      }
      int i3 = i2 + i3;
      i2 = i1;
      if (i3 < i1)
      {
        localObject = localSize;
        i2 = i3;
      }
      n += 1;
    }
    if (i1 == Integer.MAX_VALUE)
    {
      Log.e("VideoCapture", "Couldn't find resolution close to (%dx%d)", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
      return null;
    }
    return (Size)localObject;
  }
  
  static String a(int paramInt)
  {
    Object localObject = a(paramInt);
    if (localObject == null) {
      return null;
    }
    int n = ((Integer)((CameraCharacteristics)localObject).get(CameraCharacteristics.LENS_FACING)).intValue();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("camera2 ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", facing ");
    if (n == 0) {
      localObject = "front";
    } else {
      localObject = "back";
    }
    localStringBuilder.append((String)localObject);
    return localStringBuilder.toString();
  }
  
  private void a()
  {
    this.jdField_a_of_type_AndroidViewOrientationEventListener = new OrientationEventListener(ContextUtils.getApplicationContext())
    {
      public void onOrientationChanged(int paramAnonymousInt)
      {
        if (paramAnonymousInt == -1) {
          return;
        }
        VideoCaptureCamera2.a(VideoCaptureCamera2.this, paramAnonymousInt);
      }
    };
    this.jdField_a_of_type_AndroidViewOrientationEventListener.enable();
  }
  
  private void a(long paramLong)
  {
    nativeOnPhotoTaken(this.jdField_a_of_type_Long, paramLong, new byte[0]);
  }
  
  private void a(CaptureRequest.Builder paramBuilder)
  {
    Object localObject1 = a(this.jdField_c_of_type_Int);
    int n = this.jdField_f_of_type_Int;
    if (n == 4)
    {
      paramBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(4));
      paramBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(0));
    }
    else if (n == 2)
    {
      paramBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(0));
      paramBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(0));
    }
    n = this.g;
    Object localObject2;
    if ((n != 1) && (n != 2))
    {
      paramBuilder.set(CaptureRequest.CONTROL_MODE, Integer.valueOf(1));
      paramBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
      paramBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, this.jdField_a_of_type_AndroidUtilRange);
    }
    else
    {
      paramBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(0));
      if (this.jdField_b_of_type_Long != 0L)
      {
        paramBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, Long.valueOf(this.jdField_b_of_type_Long));
      }
      else
      {
        localObject2 = (Range)((CameraCharacteristics)localObject1).get(CameraCharacteristics.SENSOR_INFO_EXPOSURE_TIME_RANGE);
        paramBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, Long.valueOf(((Long)((Range)localObject2).getLower()).longValue() + (((Long)((Range)localObject2).getUpper()).longValue() + ((Long)((Range)localObject2).getLower()).longValue()) / 2L));
      }
    }
    if (this.jdField_e_of_type_Boolean)
    {
      localObject2 = CaptureRequest.CONTROL_AE_MODE;
      if (this.g == 4) {
        n = 1;
      } else {
        n = 0;
      }
      paramBuilder.set((CaptureRequest.Key)localObject2, Integer.valueOf(n));
      paramBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(2));
    }
    else
    {
      switch (this.l)
      {
      default: 
        break;
      case 3: 
        paramBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(3));
        paramBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(1));
        break;
      case 2: 
        localObject2 = CaptureRequest.CONTROL_AE_MODE;
        if (this.jdField_d_of_type_Boolean) {
          n = 4;
        } else {
          n = 2;
        }
        paramBuilder.set((CaptureRequest.Key)localObject2, Integer.valueOf(n));
        break;
      case 1: 
        paramBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
      }
      paramBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(0));
    }
    paramBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(this.h));
    n = this.i;
    if (n == 4)
    {
      paramBuilder.set(CaptureRequest.CONTROL_AWB_LOCK, Boolean.valueOf(false));
      paramBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(1));
    }
    else if (n == 1)
    {
      paramBuilder.set(CaptureRequest.CONTROL_AWB_LOCK, Boolean.valueOf(false));
      paramBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(0));
    }
    else if (n == 2)
    {
      paramBuilder.set(CaptureRequest.CONTROL_AWB_LOCK, Boolean.valueOf(true));
    }
    n = this.j;
    if (n > 0)
    {
      n = a(n, (int[])((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES));
      if (n != -1) {
        paramBuilder.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(n));
      }
    }
    localObject1 = this.jdField_a_of_type_AndroidHardwareCamera2ParamsMeteringRectangle;
    if (localObject1 != null)
    {
      localObject2 = new MeteringRectangle[1];
      localObject2[0] = localObject1;
      paramBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, localObject2);
      paramBuilder.set(CaptureRequest.CONTROL_AE_REGIONS, localObject2);
      paramBuilder.set(CaptureRequest.CONTROL_AWB_REGIONS, localObject2);
    }
    if (!this.jdField_a_of_type_AndroidGraphicsRect.isEmpty()) {
      paramBuilder.set(CaptureRequest.SCALER_CROP_REGION, this.jdField_a_of_type_AndroidGraphicsRect);
    }
    if (this.k > 0) {
      paramBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, Integer.valueOf(this.k));
    }
  }
  
  private void a(CameraState paramCameraState)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState = paramCameraState;
      this.jdField_a_of_type_JavaLangObject.notifyAll();
      return;
    }
  }
  
  private boolean a()
  {
    if (this.jdField_a_of_type_AndroidHardwareCamera2CameraDevice == null) {
      return false;
    }
    this.jdField_a_of_type_AndroidMediaImageReader = ImageReader.newInstance(this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.a(), this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.b(), this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.d(), 2);
    Object localObject = new HandlerThread("CameraPreview");
    ((HandlerThread)localObject).start();
    localObject = new Handler(((HandlerThread)localObject).getLooper());
    CrPreviewReaderListener localCrPreviewReaderListener = new CrPreviewReaderListener(null);
    this.jdField_a_of_type_AndroidMediaImageReader.setOnImageAvailableListener(localCrPreviewReaderListener, (Handler)localObject);
    try
    {
      this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder = this.jdField_a_of_type_AndroidHardwareCamera2CameraDevice.createCaptureRequest(1);
      localObject = this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder;
      if (localObject == null)
      {
        Log.e("VideoCapture", "mPreviewRequestBuilder error", new Object[0]);
        return false;
      }
      ((CaptureRequest.Builder)localObject).addTarget(this.jdField_a_of_type_AndroidMediaImageReader.getSurface());
      this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder.set(CaptureRequest.CONTROL_MODE, Integer.valueOf(1));
      this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder.set(CaptureRequest.NOISE_REDUCTION_MODE, Integer.valueOf(1));
      this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder.set(CaptureRequest.EDGE_MODE, Integer.valueOf(1));
      localObject = (int[])a(this.jdField_c_of_type_Int).get(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES);
      int i1 = localObject.length;
      int n = 0;
      while (n < i1)
      {
        if (localObject[n] == 1)
        {
          this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, Integer.valueOf(1));
          break;
        }
        n += 1;
      }
      a(this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder);
      localObject = new ArrayList(1);
      ((List)localObject).add(this.jdField_a_of_type_AndroidMediaImageReader.getSurface());
      this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest = this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest$Builder.build();
      try
      {
        this.jdField_a_of_type_AndroidHardwareCamera2CameraDevice.createCaptureSession((List)localObject, new CrPreviewSessionListener(this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest), null);
        return true;
      }
      catch (Throwable localThrowable1)
      {
        Log.e("VideoCapture", "createCaptureSession: ", new Object[] { localThrowable1 });
        return false;
      }
      return false;
    }
    catch (Throwable localThrowable2)
    {
      Log.e("VideoCapture", "createCaptureRequest: ", new Object[] { localThrowable2 });
    }
  }
  
  static boolean a(int paramInt)
  {
    if (SmttServiceClientProxy.getInstance().isWebRTCinCamera1WhiteList(Build.MODEL)) {
      return true;
    }
    return b(paramInt);
  }
  
  static VideoCaptureFormat[] a(int paramInt)
  {
    Object localObject2 = a(paramInt);
    if (localObject2 == null) {
      return null;
    }
    Object localObject1 = (int[])((CameraCharacteristics)localObject2).get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
    int i1 = localObject1.length;
    paramInt = 0;
    for (;;)
    {
      n = 1;
      if (paramInt >= i1) {
        break;
      }
      if (localObject1[paramInt] == 1)
      {
        paramInt = n;
        break label65;
      }
      paramInt += 1;
    }
    paramInt = 0;
    label65:
    localObject1 = new ArrayList();
    localObject2 = (StreamConfigurationMap)((CameraCharacteristics)localObject2).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
    int[] arrayOfInt = ((StreamConfigurationMap)localObject2).getOutputFormats();
    int i2 = arrayOfInt.length;
    int n = 0;
    while (n < i2)
    {
      int i3 = arrayOfInt[n];
      Size[] arrayOfSize = ((StreamConfigurationMap)localObject2).getOutputSizes(i3);
      if (arrayOfSize != null)
      {
        int i4 = arrayOfSize.length;
        i1 = 0;
        while (i1 < i4)
        {
          Size localSize = arrayOfSize[i1];
          double d2 = 0.0D;
          double d1 = d2;
          if (paramInt != 0)
          {
            long l1 = ((StreamConfigurationMap)localObject2).getOutputMinFrameDuration(i3, localSize);
            if (l1 == 0L)
            {
              d1 = d2;
            }
            else
            {
              d1 = l1;
              Double.isNaN(d1);
              d1 = 1.0E9D / d1;
            }
          }
          ((ArrayList)localObject1).add(new VideoCaptureFormat(localSize.getWidth(), localSize.getHeight(), (int)d1, i3));
          i1 += 1;
        }
      }
      n += 1;
    }
    return (VideoCaptureFormat[])((ArrayList)localObject1).toArray(new VideoCaptureFormat[((ArrayList)localObject1).size()]);
  }
  
  private void b()
  {
    OrientationEventListener localOrientationEventListener = this.jdField_a_of_type_AndroidViewOrientationEventListener;
    if (localOrientationEventListener != null)
    {
      localOrientationEventListener.disable();
      this.jdField_a_of_type_AndroidViewOrientationEventListener = null;
    }
  }
  
  static boolean b(int paramInt)
  {
    CameraCharacteristics localCameraCharacteristics = a(paramInt);
    return (localCameraCharacteristics != null) && (((Integer)localCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2);
  }
  
  public boolean allocate(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((!jdField_c_of_type_Boolean) && (this.jdField_a_of_type_AndroidOsLooper != Looper.myLooper())) {
      throw new AssertionError("called on wrong thread");
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Object localObject2 = this.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState;
      Object localObject4 = CameraState.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState;
      boolean bool = false;
      if ((localObject2 != localObject4) && (this.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState != CameraState.b))
      {
        ??? = a(this.jdField_c_of_type_Int);
        localObject2 = a(((StreamConfigurationMap)((CameraCharacteristics)???).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(35), paramInt1, paramInt2);
        if (localObject2 == null)
        {
          Log.e("VideoCapture", "No supported resolutions.", new Object[0]);
          return false;
        }
        Object localObject5 = Arrays.asList((Object[])((CameraCharacteristics)???).get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES));
        if (((List)localObject5).isEmpty())
        {
          Log.e("VideoCapture", "No supported framerate ranges.", new Object[0]);
          return false;
        }
        localObject4 = new ArrayList(((List)localObject5).size());
        paramInt2 = ((Integer)((Range)((List)localObject5).get(0)).getUpper()).intValue();
        paramInt1 = 1000;
        if (paramInt2 > 1000) {
          paramInt1 = 1;
        }
        localObject5 = ((List)localObject5).iterator();
        while (((Iterator)localObject5).hasNext())
        {
          Range localRange = (Range)((Iterator)localObject5).next();
          ((List)localObject4).add(new VideoCapture.FramerateRange(((Integer)localRange.getLower()).intValue() * paramInt1, ((Integer)localRange.getUpper()).intValue() * paramInt1));
        }
        localObject4 = a((List)localObject4, paramInt3 * 1000);
        this.jdField_a_of_type_AndroidUtilRange = new Range(Integer.valueOf(((VideoCapture.FramerateRange)localObject4).jdField_a_of_type_Int / paramInt1), Integer.valueOf(((VideoCapture.FramerateRange)localObject4).b / paramInt1));
        this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat = new VideoCaptureFormat(((Size)localObject2).getWidth(), ((Size)localObject2).getHeight(), paramInt3, 35);
        this.jdField_a_of_type_Int = ((Integer)((CameraCharacteristics)???).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        if (((Integer)((CameraCharacteristics)???).get(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
          bool = true;
        }
        this.jdField_a_of_type_Boolean = bool;
        this.jdField_a_of_type_ArrayOfByte = new byte[this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_a_of_type_Int * this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.b * ImageFormat.getBitsPerPixel(this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_d_of_type_Int) / 8];
        return true;
      }
      Log.e("VideoCapture", "allocate() invoked while Camera is busy opening/configuring.", new Object[0]);
      return false;
    }
  }
  
  public void deallocate() {}
  
  public PhotoCapabilities getPhotoCapabilities()
  {
    if ((!jdField_c_of_type_Boolean) && (this.jdField_a_of_type_AndroidOsLooper != Looper.myLooper())) {
      throw new AssertionError("called on wrong thread");
    }
    Object localObject1 = a(this.jdField_c_of_type_Int);
    PhotoCapabilities.Builder localBuilder = new PhotoCapabilities.Builder();
    if (localObject1 == null) {
      return localBuilder.a();
    }
    Object localObject2 = (Range)((CameraCharacteristics)localObject1).get(CameraCharacteristics.SENSOR_INFO_SENSITIVITY_RANGE);
    int i7 = 0;
    if (localObject2 != null)
    {
      i1 = ((Integer)((Range)localObject2).getLower()).intValue();
      n = ((Integer)((Range)localObject2).getUpper()).intValue();
    }
    else
    {
      n = 0;
      i1 = 0;
    }
    localBuilder.b(i1).a(n).d(1);
    if (this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.SENSOR_SENSITIVITY) != null) {
      localBuilder.c(((Integer)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.SENSOR_SENSITIVITY)).intValue());
    }
    localObject2 = ((StreamConfigurationMap)((CameraCharacteristics)localObject1).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(256);
    int i8 = localObject2.length;
    int i2 = 0;
    int i4 = Integer.MAX_VALUE;
    int n = 0;
    int i5 = Integer.MAX_VALUE;
    int i6;
    for (int i1 = 0; i2 < i8; i1 = i6)
    {
      localObject3 = localObject2[i2];
      int i3 = i5;
      if (((Size)localObject3).getWidth() < i5) {
        i3 = ((Size)localObject3).getWidth();
      }
      i5 = i4;
      if (((Size)localObject3).getHeight() < i4) {
        i5 = ((Size)localObject3).getHeight();
      }
      i6 = i1;
      if (((Size)localObject3).getWidth() > i1) {
        i6 = ((Size)localObject3).getWidth();
      }
      i1 = n;
      if (((Size)localObject3).getHeight() > n) {
        i1 = ((Size)localObject3).getHeight();
      }
      i2 += 1;
      i4 = i5;
      n = i1;
      i5 = i3;
    }
    localBuilder.f(i4).e(n).h(1);
    localBuilder.j(i5).i(i1).l(1);
    n = this.jdField_e_of_type_Int;
    if (n <= 0) {
      n = this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.b();
    }
    localBuilder.g(n);
    n = this.jdField_d_of_type_Int;
    if (n <= 0) {
      n = this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.a();
    }
    localBuilder.k(n);
    float f2 = 1.0F;
    float f1 = f2;
    if (((CameraCharacteristics)localObject1).get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE) != null)
    {
      f1 = f2;
      if (this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.SCALER_CROP_REGION) != null) {
        f1 = ((Rect)((CameraCharacteristics)localObject1).get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)).width() / ((Rect)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.SCALER_CROP_REGION)).width();
      }
    }
    localBuilder.b(1.0D).a(this.jdField_a_of_type_Float);
    localBuilder.c(f1).d(0.1D);
    localObject2 = (int[])((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
    Object localObject3 = new ArrayList(3);
    i1 = localObject2.length;
    n = 0;
    while (n < i1)
    {
      i2 = localObject2[n];
      if (i2 == 0) {
        ((ArrayList)localObject3).add(Integer.valueOf(2));
      } else if ((i2 != 1) && (i2 != 2))
      {
        if (((i2 == 3) || (i2 == 4) || (i2 == 5)) && (!((ArrayList)localObject3).contains(Integer.valueOf(4)))) {
          ((ArrayList)localObject3).add(Integer.valueOf(4));
        }
      }
      else if (!((ArrayList)localObject3).contains(Integer.valueOf(3))) {
        ((ArrayList)localObject3).add(Integer.valueOf(3));
      }
      n += 1;
    }
    localBuilder.a(a((ArrayList)localObject3));
    if (this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AF_MODE) != null)
    {
      n = ((Integer)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AF_MODE)).intValue();
      if ((n != 3) && (n != 4))
      {
        if ((n != 1) && (n != 2))
        {
          if (n == 0)
          {
            n = 2;
            break label753;
          }
          if (!jdField_c_of_type_Boolean) {
            throw new AssertionError();
          }
        }
        else
        {
          n = 3;
          break label753;
        }
      }
      else
      {
        n = 4;
        break label753;
      }
    }
    n = 1;
    label753:
    localBuilder.m(n);
    localObject3 = (int[])((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
    localObject2 = new ArrayList(1);
    i1 = localObject3.length;
    n = 0;
    while (n < i1)
    {
      i2 = localObject3[n];
      if ((i2 != 1) && (i2 != 2) && (i2 != 3) && (i2 != 4)) {
        n += 1;
      } else {
        ((ArrayList)localObject2).add(Integer.valueOf(4));
      }
    }
    try
    {
      if (((Boolean)((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE)).booleanValue()) {
        ((ArrayList)localObject2).add(Integer.valueOf(2));
      }
      localBuilder.b(a((ArrayList)localObject2));
      if ((this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AE_MODE) != null) && (((Integer)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AE_MODE)).intValue() == 0)) {
        n = 1;
      } else {
        n = 4;
      }
      if (((Boolean)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AE_LOCK)).booleanValue()) {
        n = 2;
      }
      localBuilder.n(n);
      f1 = ((Rational)((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP)).floatValue();
      localBuilder.h(f1);
      localObject2 = (Range)((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
      localBuilder.f(((Integer)((Range)localObject2).getLower()).intValue() * f1);
      localBuilder.e(((Integer)((Range)localObject2).getUpper()).intValue() * f1);
      if (this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION) != null) {
        localBuilder.g(((Integer)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION)).intValue() * f1);
      }
      localObject3 = (int[])((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES);
      localObject2 = new ArrayList(1);
      i1 = localObject3.length;
      n = 0;
      while (n < i1)
      {
        if (localObject3[n] == 1)
        {
          ((ArrayList)localObject2).add(Integer.valueOf(4));
          break;
        }
        n += 1;
      }
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        if (((Boolean)((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE)).booleanValue()) {
          ((ArrayList)localObject2).add(Integer.valueOf(2));
        }
        localBuilder.c(a((ArrayList)localObject2));
        if (this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AWB_MODE) != null)
        {
          n = ((Integer)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.CONTROL_AWB_MODE)).intValue();
          if (n == 0) {
            localBuilder.o(1);
          } else if (n == 1) {
            localBuilder.o(4);
          } else {
            localBuilder.o(2);
          }
        }
        else
        {
          n = 1;
        }
        localBuilder.q(jdField_a_of_type_AndroidUtilSparseIntArray.keyAt(0));
        localObject2 = jdField_a_of_type_AndroidUtilSparseIntArray;
        localBuilder.p(((SparseIntArray)localObject2).keyAt(((SparseIntArray)localObject2).size() - 1));
        n = jdField_a_of_type_AndroidUtilSparseIntArray.indexOfValue(n);
        if (n >= 0) {
          localBuilder.r(jdField_a_of_type_AndroidUtilSparseIntArray.keyAt(n));
        }
        localBuilder.s(50);
        if (!((Boolean)((CameraCharacteristics)localObject1).get(CameraCharacteristics.FLASH_INFO_AVAILABLE)).booleanValue())
        {
          localBuilder.a(false);
          localBuilder.c(false);
        }
        else
        {
          localBuilder.a(true);
          if (this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.FLASH_MODE) != null)
          {
            boolean bool;
            if (((Integer)this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest.get(CaptureRequest.FLASH_MODE)).intValue() == 2) {
              bool = true;
            } else {
              bool = false;
            }
            localBuilder.b(bool);
          }
          localBuilder.c(true);
          localObject1 = (int[])((CameraCharacteristics)localObject1).get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
          localObject2 = new ArrayList(0);
          i1 = localObject1.length;
          n = i7;
          while (n < i1)
          {
            i2 = localObject1[n];
            if (i2 == 0) {
              ((ArrayList)localObject2).add(Integer.valueOf(1));
            } else if (i2 == 2) {
              ((ArrayList)localObject2).add(Integer.valueOf(2));
            } else if (i2 == 3) {
              ((ArrayList)localObject2).add(Integer.valueOf(3));
            }
            n += 1;
          }
          localBuilder.d(a((ArrayList)localObject2));
        }
        return localBuilder.a();
        localThrowable1 = localThrowable1;
      }
      catch (Throwable localThrowable2)
      {
        for (;;) {}
      }
    }
  }
  
  public void setPhotoOptions(double paramDouble1, int paramInt1, int paramInt2, double paramDouble2, double paramDouble3, float[] paramArrayOfFloat, boolean paramBoolean1, double paramDouble4, int paramInt3, double paramDouble5, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, boolean paramBoolean4, boolean paramBoolean5, double paramDouble6)
  {
    if ((!jdField_c_of_type_Boolean) && (this.jdField_a_of_type_AndroidOsLooper != Looper.myLooper())) {
      throw new AssertionError("called on wrong thread");
    }
    CameraCharacteristics localCameraCharacteristics = a(this.jdField_c_of_type_Int);
    Rect localRect = (Rect)localCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
    int n;
    if (paramDouble1 != 0.0D)
    {
      float f1 = Math.max(1.0F, Math.min((float)paramDouble1, this.jdField_a_of_type_Float));
      float f2 = (f1 - 1.0F) / (f1 * 2.0F);
      n = Math.round(localRect.width() * f2);
      int i1 = Math.round(localRect.height() * f2);
      f1 = localRect.width();
      f2 = 1.0F - f2;
      this.jdField_a_of_type_AndroidGraphicsRect = new Rect(n, i1, Math.round(f1 * f2), Math.round(localRect.height() * f2));
    }
    if (paramInt1 != 0) {
      this.jdField_f_of_type_Int = paramInt1;
    }
    if (paramInt2 != 0) {
      this.g = paramInt2;
    }
    if (paramInt3 != 0) {
      this.i = paramInt3;
    }
    if (paramDouble2 > 0.0D) {
      this.jdField_d_of_type_Int = ((int)Math.round(paramDouble2));
    }
    if (paramDouble3 > 0.0D) {
      this.jdField_e_of_type_Int = ((int)Math.round(paramDouble3));
    }
    Object localObject = this.jdField_a_of_type_AndroidHardwareCamera2ParamsMeteringRectangle;
    if ((localObject != null) && (!((MeteringRectangle)localObject).getRect().isEmpty()) && (paramDouble1 > 0.0D)) {
      this.jdField_a_of_type_AndroidHardwareCamera2ParamsMeteringRectangle = null;
    }
    if ((this.jdField_f_of_type_Int == 1) || (this.g == 1)) {
      this.jdField_a_of_type_AndroidHardwareCamera2ParamsMeteringRectangle = null;
    }
    if ((((Integer)localCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() <= 0) && (((Integer)localCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE)).intValue() <= 0) && (((Integer)localCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB)).intValue() <= 0)) {
      paramInt1 = 0;
    } else {
      paramInt1 = 1;
    }
    if ((paramInt1 != 0) && (paramArrayOfFloat.length > 0))
    {
      if ((!jdField_c_of_type_Boolean) && (paramArrayOfFloat.length != 2)) {
        throw new AssertionError("Only 1 point of interest supported");
      }
      if ((!jdField_c_of_type_Boolean) && ((paramArrayOfFloat[0] > 1.0D) || (paramArrayOfFloat[0] < 0.0D))) {
        throw new AssertionError();
      }
      if ((!jdField_c_of_type_Boolean) && ((paramArrayOfFloat[1] > 1.0D) || (paramArrayOfFloat[1] < 0.0D))) {
        throw new AssertionError();
      }
      if (this.jdField_a_of_type_AndroidGraphicsRect.isEmpty()) {
        localObject = localRect;
      } else {
        localObject = this.jdField_a_of_type_AndroidGraphicsRect;
      }
      n = Math.round(paramArrayOfFloat[0] * ((Rect)localObject).width());
      paramInt3 = Math.round(paramArrayOfFloat[1] * ((Rect)localObject).height());
      paramInt2 = paramInt3;
      paramInt1 = n;
      if (((Rect)localObject).equals(this.jdField_a_of_type_AndroidGraphicsRect))
      {
        paramInt1 = n + (localRect.width() - ((Rect)localObject).width()) / 2;
        paramInt2 = paramInt3 + (localRect.height() - ((Rect)localObject).height()) / 2;
      }
      paramInt3 = ((Rect)localObject).width() / 8;
      n = ((Rect)localObject).height() / 8;
      this.jdField_a_of_type_AndroidHardwareCamera2ParamsMeteringRectangle = new MeteringRectangle(Math.max(0, paramInt1 - paramInt3 / 2), Math.max(0, paramInt2 - n / 2), paramInt3, n, 1000);
    }
    if (paramBoolean1)
    {
      paramDouble1 = ((Rational)localCameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP)).floatValue();
      Double.isNaN(paramDouble1);
      this.h = ((int)Math.round(paramDouble4 / paramDouble1));
    }
    if (paramDouble5 > 0.0D) {
      this.k = ((int)Math.round(paramDouble5));
    }
    if (paramDouble6 > 0.0D) {
      this.j = ((int)Math.round(paramDouble6));
    }
    if (paramBoolean2) {
      this.jdField_d_of_type_Boolean = paramBoolean3;
    }
    if (paramInt4 != 0) {
      this.l = paramInt4;
    }
    if (paramBoolean4) {
      this.jdField_e_of_type_Boolean = paramBoolean5;
    }
    paramArrayOfFloat = new Handler(ContextUtils.getApplicationContext().getMainLooper());
    paramArrayOfFloat.removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
    paramArrayOfFloat.post(this.jdField_a_of_type_JavaLangRunnable);
  }
  
  public boolean startCapture()
  {
    if ((!jdField_c_of_type_Boolean) && (this.jdField_a_of_type_AndroidOsLooper != Looper.myLooper())) {
      throw new AssertionError("called on wrong thread");
    }
    a(CameraState.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState);
    CameraManager localCameraManager = (CameraManager)ContextUtils.getApplicationContext().getSystemService("camera");
    if (!this.jdField_b_of_type_Boolean)
    {
      this.jdField_a_of_type_AndroidOsHandler = new Handler(ContextUtils.getApplicationContext().getMainLooper());
    }
    else
    {
      localObject = new HandlerThread("CameraPicture");
      ((HandlerThread)localObject).start();
      this.jdField_a_of_type_AndroidOsHandler = new Handler(((HandlerThread)localObject).getLooper());
    }
    Object localObject = new CrStateListener(null);
    try
    {
      localCameraManager.openCamera(Integer.toString(this.jdField_c_of_type_Int), (CameraDevice.StateCallback)localObject, this.jdField_a_of_type_AndroidOsHandler);
      a();
      return true;
    }
    catch (Throwable localThrowable)
    {
      Log.e("VideoCapture", "allocate: manager.openCamera: ", new Object[] { localThrowable });
    }
    return false;
  }
  
  public boolean stopCapture()
  {
    if ((!jdField_c_of_type_Boolean) && (this.jdField_a_of_type_AndroidOsLooper != Looper.myLooper())) {
      throw new AssertionError("called on wrong thread");
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      while (this.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState != CameraState.c)
      {
        CameraState localCameraState1 = this.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState;
        CameraState localCameraState2 = CameraState.d;
        if (localCameraState1 == localCameraState2) {
          break;
        }
        try
        {
          this.jdField_a_of_type_JavaLangObject.wait();
        }
        catch (Throwable localThrowable2)
        {
          Log.e("VideoCapture", "CaptureStartedEvent: ", new Object[] { localThrowable2 });
        }
      }
      if (this.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState == CameraState.d) {
        return true;
      }
    }
    try
    {
      this.jdField_a_of_type_AndroidHardwareCamera2CameraCaptureSession.abortCaptures();
      ??? = this.jdField_a_of_type_AndroidHardwareCamera2CameraDevice;
      if (??? == null) {
        return false;
      }
      ((CameraDevice)???).close();
      if (this.jdField_b_of_type_Boolean) {
        this.jdField_a_of_type_AndroidOsHandler.getLooper().quit();
      }
      a(CameraState.d);
      this.jdField_a_of_type_AndroidGraphicsRect = new Rect();
      b();
      return true;
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
    catch (Throwable localThrowable1)
    {
      for (;;) {}
    }
  }
  
  public boolean takePhoto(long paramLong)
  {
    if ((!jdField_c_of_type_Boolean) && (this.jdField_a_of_type_AndroidOsLooper != Looper.myLooper())) {
      throw new AssertionError("called on wrong thread");
    }
    if (this.jdField_a_of_type_AndroidHardwareCamera2CameraDevice != null)
    {
      if (this.jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState != CameraState.c) {
        return false;
      }
      Object localObject1 = a(((StreamConfigurationMap)a(this.jdField_c_of_type_Int).get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(256), this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
      int n;
      if (localObject1 != null) {
        n = ((Size)localObject1).getWidth();
      } else {
        n = this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.a();
      }
      int i1;
      if (localObject1 != null) {
        i1 = ((Size)localObject1).getHeight();
      } else {
        i1 = this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.b();
      }
      localObject1 = ImageReader.newInstance(n, i1, 256, 1);
      Object localObject2 = new HandlerThread("CameraPicture");
      ((HandlerThread)localObject2).start();
      localObject2 = new Handler(((HandlerThread)localObject2).getLooper());
      ((ImageReader)localObject1).setOnImageAvailableListener(new CrPhotoReaderListener(paramLong), (Handler)localObject2);
      ArrayList localArrayList = new ArrayList(1);
      localArrayList.add(((ImageReader)localObject1).getSurface());
      try
      {
        CaptureRequest.Builder localBuilder = this.jdField_a_of_type_AndroidHardwareCamera2CameraDevice.createCaptureRequest(2);
        if (localBuilder == null)
        {
          Log.e("VideoCapture", "photoRequestBuilder error", new Object[0]);
          return false;
        }
        localBuilder.addTarget(((ImageReader)localObject1).getSurface());
        localBuilder.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(getCameraRotation()));
        a(localBuilder);
        localObject1 = new CrPhotoSessionListener(localBuilder.build(), paramLong);
        try
        {
          this.jdField_a_of_type_AndroidHardwareCamera2CameraDevice.createCaptureSession(localArrayList, (CameraCaptureSession.StateCallback)localObject1, (Handler)localObject2);
          return true;
        }
        catch (Throwable localThrowable1)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("createCaptureSession: ");
          ((StringBuilder)localObject2).append(localThrowable1);
          Log.e("VideoCapture", ((StringBuilder)localObject2).toString(), new Object[0]);
          return false;
        }
        return false;
      }
      catch (Throwable localThrowable2)
      {
        Log.e("VideoCapture", "createCaptureRequest() error ", new Object[] { localThrowable2 });
        return false;
      }
    }
  }
  
  private static enum CameraState
  {
    static
    {
      jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState = new CameraState("OPENING", 0);
      b = new CameraState("CONFIGURING", 1);
      c = new CameraState("STARTED", 2);
      d = new CameraState("STOPPED", 3);
      jdField_a_of_type_ArrayOfOrgChromiumMediaVideoCaptureCamera2$CameraState = new CameraState[] { jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2$CameraState, b, c, d };
    }
    
    private CameraState() {}
  }
  
  private class CrPhotoReaderListener
    implements ImageReader.OnImageAvailableListener
  {
    private final long jdField_a_of_type_Long;
    
    CrPhotoReaderListener(long paramLong)
    {
      this.jdField_a_of_type_Long = paramLong;
    }
    
    private byte[] a(Image paramImage)
    {
      Object localObject2 = null;
      Object localObject1 = localObject2;
      try
      {
        localObject3 = paramImage.getPlanes()[0].getBuffer().array();
        return (byte[])localObject3;
      }
      catch (Throwable localThrowable)
      {
        for (;;)
        {
          Object localObject3;
          localThrowable = localThrowable;
        }
      }
      finally {}
      localObject1 = localObject2;
      localObject3 = paramImage.getPlanes()[0].getBuffer();
      localObject1 = localObject2;
      paramImage = new byte[((ByteBuffer)localObject3).remaining()];
      localObject1 = paramImage;
      ((ByteBuffer)localObject3).get(paramImage);
      return paramImage;
      return localThrowable;
    }
    
    /* Error */
    public void onImageAvailable(ImageReader paramImageReader)
    {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual 59	android/media/ImageReader:acquireLatestImage	()Landroid/media/Image;
      //   4: astore_3
      //   5: aconst_null
      //   6: astore_2
      //   7: aload_3
      //   8: ifnull +114 -> 122
      //   11: aload_2
      //   12: astore_1
      //   13: aload_3
      //   14: invokevirtual 62	android/media/Image:getFormat	()I
      //   17: sipush 256
      //   20: if_icmpne +69 -> 89
      //   23: aload_2
      //   24: astore_1
      //   25: aload_0
      //   26: aload_3
      //   27: invokespecial 64	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:a	(Landroid/media/Image;)[B
      //   30: astore 4
      //   32: aload_2
      //   33: astore_1
      //   34: aload_0
      //   35: getfield 16	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2	Lorg/chromium/media/VideoCaptureCamera2;
      //   38: aload_0
      //   39: getfield 16	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2	Lorg/chromium/media/VideoCaptureCamera2;
      //   42: getfield 65	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_Long	J
      //   45: aload_0
      //   46: getfield 21	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:jdField_a_of_type_Long	J
      //   49: aload 4
      //   51: invokevirtual 69	org/chromium/media/VideoCaptureCamera2:nativeOnPhotoTaken	(JJ[B)V
      //   54: aload_3
      //   55: ifnull +7 -> 62
      //   58: aload_3
      //   59: invokevirtual 72	android/media/Image:close	()V
      //   62: aload_0
      //   63: getfield 16	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2	Lorg/chromium/media/VideoCaptureCamera2;
      //   66: invokestatic 75	org/chromium/media/VideoCaptureCamera2:a	(Lorg/chromium/media/VideoCaptureCamera2;)Z
      //   69: ifeq +4 -> 73
      //   72: return
      //   73: aload_0
      //   74: getfield 16	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2	Lorg/chromium/media/VideoCaptureCamera2;
      //   77: astore_1
      //   78: aload_1
      //   79: aload_1
      //   80: getfield 65	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_Long	J
      //   83: ldc 77
      //   85: invokevirtual 81	org/chromium/media/VideoCaptureCamera2:nativeOnError	(JLjava/lang/String;)V
      //   88: return
      //   89: aload_2
      //   90: astore_1
      //   91: ldc 83
      //   93: ldc 85
      //   95: iconst_1
      //   96: anewarray 4	java/lang/Object
      //   99: dup
      //   100: iconst_0
      //   101: aload_3
      //   102: invokevirtual 62	android/media/Image:getFormat	()I
      //   105: invokestatic 91	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   108: aastore
      //   109: invokestatic 97	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   112: aload_2
      //   113: astore_1
      //   114: new 99	java/lang/IllegalStateException
      //   117: dup
      //   118: invokespecial 100	java/lang/IllegalStateException:<init>	()V
      //   121: athrow
      //   122: aload_2
      //   123: astore_1
      //   124: new 99	java/lang/IllegalStateException
      //   127: dup
      //   128: invokespecial 100	java/lang/IllegalStateException:<init>	()V
      //   131: athrow
      //   132: aload_2
      //   133: astore_1
      //   134: aload_2
      //   135: athrow
      //   136: aload_3
      //   137: ifnull +27 -> 164
      //   140: aload_1
      //   141: ifnull +19 -> 160
      //   144: aload_3
      //   145: invokevirtual 72	android/media/Image:close	()V
      //   148: goto +16 -> 164
      //   151: astore_3
      //   152: aload_1
      //   153: aload_3
      //   154: invokevirtual 104	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
      //   157: goto +7 -> 164
      //   160: aload_3
      //   161: invokevirtual 72	android/media/Image:close	()V
      //   164: aload_2
      //   165: athrow
      //   166: aload_0
      //   167: getfield 16	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:jdField_a_of_type_OrgChromiumMediaVideoCaptureCamera2	Lorg/chromium/media/VideoCaptureCamera2;
      //   170: aload_0
      //   171: getfield 21	org/chromium/media/VideoCaptureCamera2$CrPhotoReaderListener:jdField_a_of_type_Long	J
      //   174: invokestatic 106	org/chromium/media/VideoCaptureCamera2:a	(Lorg/chromium/media/VideoCaptureCamera2;J)V
      //   177: return
      //   178: astore_1
      //   179: goto -13 -> 166
      //   182: astore_2
      //   183: goto -47 -> 136
      //   186: astore_2
      //   187: goto -55 -> 132
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	190	0	this	CrPhotoReaderListener
      //   0	190	1	paramImageReader	ImageReader
      //   6	159	2	localObject1	Object
      //   182	1	2	localObject2	Object
      //   186	1	2	localThrowable1	Throwable
      //   4	141	3	localImage	Image
      //   151	10	3	localThrowable2	Throwable
      //   30	20	4	arrayOfByte	byte[]
      // Exception table:
      //   from	to	target	type
      //   144	148	151	java/lang/Throwable
      //   0	5	178	java/lang/Throwable
      //   58	62	178	java/lang/Throwable
      //   152	157	178	java/lang/Throwable
      //   160	164	178	java/lang/Throwable
      //   164	166	178	java/lang/Throwable
      //   13	23	182	finally
      //   25	32	182	finally
      //   34	54	182	finally
      //   91	112	182	finally
      //   114	122	182	finally
      //   124	132	182	finally
      //   134	136	182	finally
      //   13	23	186	java/lang/Throwable
      //   25	32	186	java/lang/Throwable
      //   34	54	186	java/lang/Throwable
      //   91	112	186	java/lang/Throwable
      //   114	122	186	java/lang/Throwable
      //   124	132	186	java/lang/Throwable
    }
  }
  
  private class CrPhotoSessionListener
    extends CameraCaptureSession.StateCallback
  {
    private final long jdField_a_of_type_Long;
    private final CaptureRequest jdField_a_of_type_AndroidHardwareCamera2CaptureRequest;
    
    CrPhotoSessionListener(CaptureRequest paramCaptureRequest, long paramLong)
    {
      this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest = paramCaptureRequest;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void onConfigureFailed(CameraCaptureSession paramCameraCaptureSession)
    {
      Log.e("VideoCapture", "failed configuring capture session", new Object[0]);
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, this.jdField_a_of_type_Long);
    }
    
    public void onConfigured(CameraCaptureSession paramCameraCaptureSession)
    {
      try
      {
        paramCameraCaptureSession.capture(this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest, null, null);
        return;
      }
      catch (Throwable paramCameraCaptureSession)
      {
        for (;;) {}
      }
      Log.e("VideoCapture", "capture() error", new Object[0]);
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, this.jdField_a_of_type_Long);
    }
  }
  
  private class CrPreviewReaderListener
    implements ImageReader.OnImageAvailableListener
  {
    private CrPreviewReaderListener() {}
    
    /* Error */
    public void onImageAvailable(ImageReader paramImageReader)
    {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual 32	android/media/ImageReader:acquireLatestImage	()Landroid/media/Image;
      //   4: astore 8
      //   6: aconst_null
      //   7: astore 7
      //   9: aload 8
      //   11: ifnonnull +14 -> 25
      //   14: aload 8
      //   16: ifnull +8 -> 24
      //   19: aload 8
      //   21: invokevirtual 37	android/media/Image:close	()V
      //   24: return
      //   25: aload 7
      //   27: astore 6
      //   29: aload 8
      //   31: invokevirtual 41	android/media/Image:getFormat	()I
      //   34: bipush 35
      //   36: if_icmpne +439 -> 475
      //   39: aload 7
      //   41: astore 6
      //   43: aload 8
      //   45: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   48: arraylength
      //   49: iconst_3
      //   50: if_icmpne +425 -> 475
      //   53: aload 7
      //   55: astore 6
      //   57: aload_1
      //   58: invokevirtual 48	android/media/ImageReader:getWidth	()I
      //   61: aload 8
      //   63: invokevirtual 49	android/media/Image:getWidth	()I
      //   66: if_icmpne +227 -> 293
      //   69: aload 7
      //   71: astore 6
      //   73: aload_1
      //   74: invokevirtual 52	android/media/ImageReader:getHeight	()I
      //   77: aload 8
      //   79: invokevirtual 53	android/media/Image:getHeight	()I
      //   82: if_icmpne +211 -> 293
      //   85: aload 7
      //   87: astore 6
      //   89: aload_0
      //   90: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   93: getfield 56	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_Boolean	Z
      //   96: ifne +565 -> 661
      //   99: aload 7
      //   101: astore 6
      //   103: aload_0
      //   104: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   107: getfield 59	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat	Lorg/chromium/media/VideoCaptureFormat;
      //   110: getfield 65	org/chromium/media/VideoCaptureFormat:d	I
      //   113: ldc 66
      //   115: if_icmpeq +541 -> 656
      //   118: aload 7
      //   120: astore 6
      //   122: aload_0
      //   123: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   126: getfield 59	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat	Lorg/chromium/media/VideoCaptureFormat;
      //   129: getfield 65	org/chromium/media/VideoCaptureFormat:d	I
      //   132: bipush 35
      //   134: if_icmpne +527 -> 661
      //   137: goto +519 -> 656
      //   140: aload 7
      //   142: astore 6
      //   144: aload_0
      //   145: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   148: invokestatic 69	org/chromium/media/VideoCaptureCamera2:a	(Lorg/chromium/media/VideoCaptureCamera2;)I
      //   151: aload_0
      //   152: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   155: invokevirtual 72	org/chromium/media/VideoCaptureCamera2:getDeviceRotation	()I
      //   158: iadd
      //   159: sipush 360
      //   162: irem
      //   163: istore_2
      //   164: iload_2
      //   165: sipush 285
      //   168: if_icmpge +562 -> 730
      //   171: iload_2
      //   172: bipush 75
      //   174: if_icmpge +492 -> 666
      //   177: goto +553 -> 730
      //   180: aload 7
      //   182: astore 6
      //   184: aload_0
      //   185: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   188: aload_0
      //   189: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   192: getfield 75	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_Long	J
      //   195: aload 8
      //   197: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   200: iconst_0
      //   201: aaload
      //   202: invokevirtual 81	android/media/Image$Plane:getBuffer	()Ljava/nio/ByteBuffer;
      //   205: aload 8
      //   207: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   210: iconst_0
      //   211: aaload
      //   212: invokevirtual 84	android/media/Image$Plane:getRowStride	()I
      //   215: aload 8
      //   217: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   220: iconst_1
      //   221: aaload
      //   222: invokevirtual 81	android/media/Image$Plane:getBuffer	()Ljava/nio/ByteBuffer;
      //   225: aload 8
      //   227: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   230: iconst_2
      //   231: aaload
      //   232: invokevirtual 81	android/media/Image$Plane:getBuffer	()Ljava/nio/ByteBuffer;
      //   235: aload 8
      //   237: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   240: iconst_1
      //   241: aaload
      //   242: invokevirtual 84	android/media/Image$Plane:getRowStride	()I
      //   245: aload 8
      //   247: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   250: iconst_1
      //   251: aaload
      //   252: invokevirtual 87	android/media/Image$Plane:getPixelStride	()I
      //   255: aload 8
      //   257: invokevirtual 49	android/media/Image:getWidth	()I
      //   260: aload 8
      //   262: invokevirtual 53	android/media/Image:getHeight	()I
      //   265: aload_0
      //   266: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   269: invokevirtual 90	org/chromium/media/VideoCaptureCamera2:getCameraRotation	()I
      //   272: aload 8
      //   274: invokevirtual 94	android/media/Image:getTimestamp	()J
      //   277: iload_3
      //   278: iload_2
      //   279: invokevirtual 98	org/chromium/media/VideoCaptureCamera2:nativeOnI420FrameAvailable	(JLjava/nio/ByteBuffer;ILjava/nio/ByteBuffer;Ljava/nio/ByteBuffer;IIIIIJZI)V
      //   282: aload 8
      //   284: ifnull +371 -> 655
      //   287: aload 8
      //   289: invokevirtual 37	android/media/Image:close	()V
      //   292: return
      //   293: aload 7
      //   295: astore 6
      //   297: aload_0
      //   298: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   301: astore 9
      //   303: aload 7
      //   305: astore 6
      //   307: aload_0
      //   308: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   311: getfield 75	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_Long	J
      //   314: lstore 4
      //   316: aload 7
      //   318: astore 6
      //   320: new 100	java/lang/StringBuilder
      //   323: dup
      //   324: invokespecial 101	java/lang/StringBuilder:<init>	()V
      //   327: astore 10
      //   329: aload 7
      //   331: astore 6
      //   333: aload 10
      //   335: ldc 103
      //   337: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   340: pop
      //   341: aload 7
      //   343: astore 6
      //   345: aload 10
      //   347: aload_1
      //   348: invokevirtual 48	android/media/ImageReader:getWidth	()I
      //   351: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   354: pop
      //   355: aload 7
      //   357: astore 6
      //   359: aload 10
      //   361: ldc 112
      //   363: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   366: pop
      //   367: aload 7
      //   369: astore 6
      //   371: aload 10
      //   373: aload_1
      //   374: invokevirtual 52	android/media/ImageReader:getHeight	()I
      //   377: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   380: pop
      //   381: aload 7
      //   383: astore 6
      //   385: aload 10
      //   387: ldc 114
      //   389: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   392: pop
      //   393: aload 7
      //   395: astore 6
      //   397: aload 10
      //   399: aload 8
      //   401: invokevirtual 49	android/media/Image:getWidth	()I
      //   404: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   407: pop
      //   408: aload 7
      //   410: astore 6
      //   412: aload 10
      //   414: ldc 112
      //   416: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   419: pop
      //   420: aload 7
      //   422: astore 6
      //   424: aload 10
      //   426: aload 8
      //   428: invokevirtual 53	android/media/Image:getHeight	()I
      //   431: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   434: pop
      //   435: aload 7
      //   437: astore 6
      //   439: aload 10
      //   441: ldc 116
      //   443: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   446: pop
      //   447: aload 7
      //   449: astore 6
      //   451: aload 9
      //   453: lload 4
      //   455: aload 10
      //   457: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   460: invokevirtual 124	org/chromium/media/VideoCaptureCamera2:nativeOnError	(JLjava/lang/String;)V
      //   463: aload 7
      //   465: astore 6
      //   467: new 126	java/lang/IllegalStateException
      //   470: dup
      //   471: invokespecial 127	java/lang/IllegalStateException:<init>	()V
      //   474: athrow
      //   475: aload 7
      //   477: astore 6
      //   479: aload_0
      //   480: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   483: astore_1
      //   484: aload 7
      //   486: astore 6
      //   488: aload_0
      //   489: getfield 15	org/chromium/media/VideoCaptureCamera2$CrPreviewReaderListener:a	Lorg/chromium/media/VideoCaptureCamera2;
      //   492: getfield 75	org/chromium/media/VideoCaptureCamera2:jdField_a_of_type_Long	J
      //   495: lstore 4
      //   497: aload 7
      //   499: astore 6
      //   501: new 100	java/lang/StringBuilder
      //   504: dup
      //   505: invokespecial 101	java/lang/StringBuilder:<init>	()V
      //   508: astore 9
      //   510: aload 7
      //   512: astore 6
      //   514: aload 9
      //   516: ldc -127
      //   518: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   521: pop
      //   522: aload 7
      //   524: astore 6
      //   526: aload 9
      //   528: aload 8
      //   530: invokevirtual 41	android/media/Image:getFormat	()I
      //   533: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   536: pop
      //   537: aload 7
      //   539: astore 6
      //   541: aload 9
      //   543: ldc -125
      //   545: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   548: pop
      //   549: aload 7
      //   551: astore 6
      //   553: aload 9
      //   555: aload 8
      //   557: invokevirtual 45	android/media/Image:getPlanes	()[Landroid/media/Image$Plane;
      //   560: arraylength
      //   561: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   564: pop
      //   565: aload 7
      //   567: astore 6
      //   569: aload_1
      //   570: lload 4
      //   572: aload 9
      //   574: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   577: invokevirtual 124	org/chromium/media/VideoCaptureCamera2:nativeOnError	(JLjava/lang/String;)V
      //   580: aload 7
      //   582: astore 6
      //   584: new 126	java/lang/IllegalStateException
      //   587: dup
      //   588: invokespecial 127	java/lang/IllegalStateException:<init>	()V
      //   591: athrow
      //   592: astore_1
      //   593: goto +9 -> 602
      //   596: astore_1
      //   597: aload_1
      //   598: astore 6
      //   600: aload_1
      //   601: athrow
      //   602: aload 8
      //   604: ifnull +33 -> 637
      //   607: aload 6
      //   609: ifnull +23 -> 632
      //   612: aload 8
      //   614: invokevirtual 37	android/media/Image:close	()V
      //   617: goto +20 -> 637
      //   620: astore 7
      //   622: aload 6
      //   624: aload 7
      //   626: invokevirtual 135	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
      //   629: goto +8 -> 637
      //   632: aload 8
      //   634: invokevirtual 37	android/media/Image:close	()V
      //   637: aload_1
      //   638: athrow
      //   639: astore_1
      //   640: ldc -119
      //   642: ldc -117
      //   644: iconst_1
      //   645: anewarray 4	java/lang/Object
      //   648: dup
      //   649: iconst_0
      //   650: aload_1
      //   651: aastore
      //   652: invokestatic 145	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
      //   655: return
      //   656: iconst_1
      //   657: istore_3
      //   658: goto -518 -> 140
      //   661: iconst_0
      //   662: istore_3
      //   663: goto -523 -> 140
      //   666: iload_2
      //   667: bipush 75
      //   669: if_icmplt +16 -> 685
      //   672: iload_2
      //   673: sipush 135
      //   676: if_icmpge +9 -> 685
      //   679: bipush 90
      //   681: istore_2
      //   682: goto -502 -> 180
      //   685: iload_2
      //   686: sipush 135
      //   689: if_icmplt +17 -> 706
      //   692: iload_2
      //   693: sipush 225
      //   696: if_icmpge +10 -> 706
      //   699: sipush 180
      //   702: istore_2
      //   703: goto -523 -> 180
      //   706: iload_2
      //   707: sipush 225
      //   710: if_icmplt +17 -> 727
      //   713: iload_2
      //   714: sipush 285
      //   717: if_icmpge +10 -> 727
      //   720: sipush 270
      //   723: istore_2
      //   724: goto -544 -> 180
      //   727: goto -547 -> 180
      //   730: iconst_0
      //   731: istore_2
      //   732: goto -552 -> 180
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	735	0	this	CrPreviewReaderListener
      //   0	735	1	paramImageReader	ImageReader
      //   163	569	2	i	int
      //   277	386	3	bool	boolean
      //   314	257	4	l	long
      //   27	596	6	localObject1	Object
      //   7	574	7	localObject2	Object
      //   620	5	7	localThrowable	Throwable
      //   4	629	8	localImage	Image
      //   301	272	9	localObject3	Object
      //   327	129	10	localStringBuilder	StringBuilder
      // Exception table:
      //   from	to	target	type
      //   29	39	592	finally
      //   43	53	592	finally
      //   57	69	592	finally
      //   73	85	592	finally
      //   89	99	592	finally
      //   103	118	592	finally
      //   122	137	592	finally
      //   144	164	592	finally
      //   184	282	592	finally
      //   297	303	592	finally
      //   307	316	592	finally
      //   320	329	592	finally
      //   333	341	592	finally
      //   345	355	592	finally
      //   359	367	592	finally
      //   371	381	592	finally
      //   385	393	592	finally
      //   397	408	592	finally
      //   412	420	592	finally
      //   424	435	592	finally
      //   439	447	592	finally
      //   451	463	592	finally
      //   467	475	592	finally
      //   479	484	592	finally
      //   488	497	592	finally
      //   501	510	592	finally
      //   514	522	592	finally
      //   526	537	592	finally
      //   541	549	592	finally
      //   553	565	592	finally
      //   569	580	592	finally
      //   584	592	592	finally
      //   600	602	592	finally
      //   29	39	596	java/lang/Throwable
      //   43	53	596	java/lang/Throwable
      //   57	69	596	java/lang/Throwable
      //   73	85	596	java/lang/Throwable
      //   89	99	596	java/lang/Throwable
      //   103	118	596	java/lang/Throwable
      //   122	137	596	java/lang/Throwable
      //   144	164	596	java/lang/Throwable
      //   184	282	596	java/lang/Throwable
      //   297	303	596	java/lang/Throwable
      //   307	316	596	java/lang/Throwable
      //   320	329	596	java/lang/Throwable
      //   333	341	596	java/lang/Throwable
      //   345	355	596	java/lang/Throwable
      //   359	367	596	java/lang/Throwable
      //   371	381	596	java/lang/Throwable
      //   385	393	596	java/lang/Throwable
      //   397	408	596	java/lang/Throwable
      //   412	420	596	java/lang/Throwable
      //   424	435	596	java/lang/Throwable
      //   439	447	596	java/lang/Throwable
      //   451	463	596	java/lang/Throwable
      //   467	475	596	java/lang/Throwable
      //   479	484	596	java/lang/Throwable
      //   488	497	596	java/lang/Throwable
      //   501	510	596	java/lang/Throwable
      //   514	522	596	java/lang/Throwable
      //   526	537	596	java/lang/Throwable
      //   541	549	596	java/lang/Throwable
      //   553	565	596	java/lang/Throwable
      //   569	580	596	java/lang/Throwable
      //   584	592	596	java/lang/Throwable
      //   612	617	620	java/lang/Throwable
      //   0	6	639	java/lang/Throwable
      //   19	24	639	java/lang/Throwable
      //   287	292	639	java/lang/Throwable
      //   622	629	639	java/lang/Throwable
      //   632	637	639	java/lang/Throwable
      //   637	639	639	java/lang/Throwable
    }
  }
  
  private class CrPreviewSessionListener
    extends CameraCaptureSession.StateCallback
  {
    private final CaptureRequest jdField_a_of_type_AndroidHardwareCamera2CaptureRequest;
    
    CrPreviewSessionListener(CaptureRequest paramCaptureRequest)
    {
      this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest = paramCaptureRequest;
    }
    
    public void onConfigureFailed(CameraCaptureSession paramCameraCaptureSession)
    {
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, VideoCaptureCamera2.CameraState.d);
      paramCameraCaptureSession = VideoCaptureCamera2.this;
      paramCameraCaptureSession.nativeOnError(paramCameraCaptureSession.a, "Camera session configuration error");
    }
    
    public void onConfigured(CameraCaptureSession paramCameraCaptureSession)
    {
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, paramCameraCaptureSession);
      try
      {
        VideoCaptureCamera2.a(VideoCaptureCamera2.this).setRepeatingRequest(this.jdField_a_of_type_AndroidHardwareCamera2CaptureRequest, new CameraCaptureSession.CaptureCallback()
        {
          public void onCaptureCompleted(CameraCaptureSession paramAnonymousCameraCaptureSession, CaptureRequest paramAnonymousCaptureRequest, TotalCaptureResult paramAnonymousTotalCaptureResult)
          {
            paramAnonymousCameraCaptureSession = (Long)paramAnonymousTotalCaptureResult.get(CaptureResult.SENSOR_EXPOSURE_TIME);
            if (paramAnonymousCameraCaptureSession == null) {
              return;
            }
            VideoCaptureCamera2.a(VideoCaptureCamera2.this, paramAnonymousCameraCaptureSession.longValue());
          }
        }, null);
        paramCameraCaptureSession = VideoCaptureCamera2.this;
        paramCameraCaptureSession.nativeOnStarted(paramCameraCaptureSession.a);
        VideoCaptureCamera2.a(VideoCaptureCamera2.this, VideoCaptureCamera2.CameraState.c);
        return;
      }
      catch (Throwable paramCameraCaptureSession)
      {
        Log.e("VideoCapture", "setRepeatingRequest: ", new Object[] { paramCameraCaptureSession });
      }
    }
  }
  
  private class CrStateListener
    extends CameraDevice.StateCallback
  {
    private CrStateListener() {}
    
    public void onDisconnected(CameraDevice paramCameraDevice)
    {
      paramCameraDevice.close();
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, null);
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, VideoCaptureCamera2.CameraState.d);
    }
    
    public void onError(CameraDevice paramCameraDevice, int paramInt)
    {
      paramCameraDevice.close();
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, null);
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, VideoCaptureCamera2.CameraState.d);
      paramCameraDevice = VideoCaptureCamera2.this;
      long l = paramCameraDevice.a;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Camera device error ");
      localStringBuilder.append(Integer.toString(paramInt));
      paramCameraDevice.nativeOnError(l, localStringBuilder.toString());
    }
    
    public void onOpened(CameraDevice paramCameraDevice)
    {
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, paramCameraDevice);
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, VideoCaptureCamera2.CameraState.b);
      if (VideoCaptureCamera2.a(VideoCaptureCamera2.this)) {
        return;
      }
      VideoCaptureCamera2.a(VideoCaptureCamera2.this, VideoCaptureCamera2.CameraState.d);
      paramCameraDevice = VideoCaptureCamera2.this;
      paramCameraDevice.nativeOnError(paramCameraDevice.a, "Error configuring camera");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\VideoCaptureCamera2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */