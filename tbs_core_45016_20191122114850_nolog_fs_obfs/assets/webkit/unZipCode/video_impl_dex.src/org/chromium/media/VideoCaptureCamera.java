package org.chromium.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.opengl.GLES20;
import android.os.Build;
import android.util.SparseArray;
import android.view.OrientationEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
@TargetApi(15)
public class VideoCaptureCamera
  extends VideoCapture
  implements Camera.PreviewCallback
{
  private static final SparseArray<String> jdField_a_of_type_AndroidUtilSparseArray;
  private SurfaceTexture jdField_a_of_type_AndroidGraphicsSurfaceTexture;
  private Camera.Area jdField_a_of_type_AndroidHardwareCamera$Area;
  private Camera.Parameters jdField_a_of_type_AndroidHardwareCamera$Parameters;
  private Camera jdField_a_of_type_AndroidHardwareCamera;
  private OrientationEventListener jdField_a_of_type_AndroidViewOrientationEventListener;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private ReentrantLock jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock = new ReentrantLock();
  private int[] jdField_a_of_type_ArrayOfInt;
  private long b;
  private int jdField_d_of_type_Int;
  private boolean jdField_d_of_type_Boolean;
  private int e;
  private int f;
  private int g;
  
  static
  {
    jdField_c_of_type_Boolean = VideoCaptureCamera.class.desiredAssertionStatus() ^ true;
    jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
    jdField_a_of_type_AndroidUtilSparseArray.append(2850, "incandescent");
    jdField_a_of_type_AndroidUtilSparseArray.append(2950, "warm-fluorescent");
    jdField_a_of_type_AndroidUtilSparseArray.append(4250, "fluorescent");
    jdField_a_of_type_AndroidUtilSparseArray.append(4600, "twilight");
    jdField_a_of_type_AndroidUtilSparseArray.append(5500, "daylight");
    jdField_a_of_type_AndroidUtilSparseArray.append(6000, "cloudy-daylight");
    jdField_a_of_type_AndroidUtilSparseArray.append(7000, "shade");
  }
  
  VideoCaptureCamera(int paramInt, long paramLong)
  {
    super(paramInt, paramLong);
  }
  
  static int a()
  {
    return Camera.getNumberOfCameras();
  }
  
  static int a(int paramInt)
  {
    if (a(paramInt) == null) {
      return 9;
    }
    return 5;
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
      Log.e("VideoCapture", localStringBuilder.toString(), new Object[0]);
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
      Log.e("VideoCapture", localStringBuilder.toString(), new Object[0]);
      if (paramCamera != null) {
        paramCamera.release();
      }
    }
    return null;
  }
  
  static String a(int paramInt)
  {
    Object localObject = a(paramInt);
    if (localObject == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("camera ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", facing ");
    if (((Camera.CameraInfo)localObject).facing == 1) {
      localObject = "front";
    } else {
      localObject = "back";
    }
    localStringBuilder.append((String)localObject);
    return localStringBuilder.toString();
  }
  
  private String a(int paramInt, List<String> paramList)
  {
    int j = Integer.MAX_VALUE;
    String str = null;
    int i = 0;
    while (i < jdField_a_of_type_AndroidUtilSparseArray.size())
    {
      if (paramList.contains(jdField_a_of_type_AndroidUtilSparseArray.valueAt(i)))
      {
        int k = Math.abs(paramInt - jdField_a_of_type_AndroidUtilSparseArray.keyAt(i));
        if (k < j)
        {
          str = (String)jdField_a_of_type_AndroidUtilSparseArray.valueAt(i);
          j = k;
        }
      }
      i += 1;
    }
    return str;
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
        VideoCaptureCamera.a(VideoCaptureCamera.this, paramAnonymousInt);
      }
    };
    this.jdField_a_of_type_AndroidViewOrientationEventListener.enable();
  }
  
  private void a(Camera.PreviewCallback paramPreviewCallback)
  {
    this.jdField_a_of_type_AndroidHardwareCamera.setPreviewCallbackWithBuffer(paramPreviewCallback);
  }
  
  static VideoCaptureFormat[] a(int paramInt)
  {
    try
    {
      Camera localCamera = Camera.open(paramInt);
      Camera.Parameters localParameters = a(localCamera);
      if (localParameters == null) {
        return null;
      }
      ArrayList localArrayList = new ArrayList();
      Object localObject1;
      try
      {
        List localList = localParameters.getSupportedPreviewFormats();
      }
      catch (NullPointerException localNullPointerException)
      {
        Log.e("VideoCapture", "Camera.Parameters.getSupportedPreviewFormats: ", new Object[] { localNullPointerException });
        localObject1 = null;
      }
      Object localObject3 = localObject1;
      if (localObject1 == null) {
        localObject3 = new ArrayList();
      }
      if (((List)localObject3).size() == 0) {
        ((List)localObject3).add(Integer.valueOf(0));
      }
      Iterator localIterator1 = ((List)localObject3).iterator();
      while (localIterator1.hasNext())
      {
        localObject1 = (Integer)localIterator1.next();
        int i = ((Integer)localObject1).intValue();
        paramInt = 842094169;
        if (i != 842094169)
        {
          if (((Integer)localObject1).intValue() != 17) {
            paramInt = 0;
          }
        }
        else
        {
          Object localObject2;
          try
          {
            localObject1 = localParameters.getSupportedPreviewFpsRange();
          }
          catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
          {
            Log.e("VideoCapture", "Camera.Parameters.getSupportedPreviewFpsRange: ", new Object[] { localStringIndexOutOfBoundsException });
            localObject2 = null;
          }
          localObject3 = localObject2;
          if (localObject2 == null) {
            localObject3 = new ArrayList();
          }
          if (((List)localObject3).size() == 0) {
            ((List)localObject3).add(new int[] { 0, 0 });
          }
          Iterator localIterator2 = ((List)localObject3).iterator();
          while (localIterator2.hasNext())
          {
            int[] arrayOfInt = (int[])localIterator2.next();
            localObject3 = localParameters.getSupportedPreviewSizes();
            localObject2 = localObject3;
            if (localObject3 == null) {
              localObject2 = new ArrayList();
            }
            if (((List)localObject2).size() == 0)
            {
              localCamera.getClass();
              ((List)localObject2).add(new Camera.Size(localCamera, 0, 0));
            }
            localObject2 = ((List)localObject2).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localObject3 = (Camera.Size)((Iterator)localObject2).next();
              localArrayList.add(new VideoCaptureFormat(((Camera.Size)localObject3).width, ((Camera.Size)localObject3).height, (arrayOfInt[1] + 999) / 1000, paramInt));
            }
          }
        }
      }
      localCamera.release();
      return (VideoCaptureFormat[])localArrayList.toArray(new VideoCaptureFormat[localArrayList.size()]);
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.e("VideoCapture", "Camera.open: ", new Object[] { localRuntimeException });
    }
    return null;
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
  
  public boolean allocate(int paramInt1, int paramInt2, int paramInt3)
  {
    int k = 0;
    try
    {
      this.jdField_a_of_type_AndroidHardwareCamera = Camera.open(this.jdField_c_of_type_Int);
      Object localObject1 = a(this.jdField_c_of_type_Int);
      if (localObject1 == null)
      {
        this.jdField_a_of_type_AndroidHardwareCamera.release();
        this.jdField_a_of_type_AndroidHardwareCamera = null;
        return false;
      }
      this.jdField_a_of_type_Int = ((Camera.CameraInfo)localObject1).orientation;
      int i;
      if (((Camera.CameraInfo)localObject1).facing == 0) {
        i = 1;
      } else {
        i = 0;
      }
      this.jdField_b_of_type_Int = i;
      boolean bool;
      if (((Camera.CameraInfo)localObject1).facing == 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.jdField_a_of_type_Boolean = bool;
      localObject1 = a(this.jdField_a_of_type_AndroidHardwareCamera);
      if (localObject1 == null)
      {
        this.jdField_a_of_type_AndroidHardwareCamera = null;
        return false;
      }
      Object localObject3 = ((Camera.Parameters)localObject1).getSupportedPreviewFpsRange();
      if ((localObject3 != null) && (((List)localObject3).size() != 0))
      {
        localObject2 = new ArrayList(((List)localObject3).size());
        localObject3 = ((List)localObject3).iterator();
        Object localObject4;
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (int[])((Iterator)localObject3).next();
          ((ArrayList)localObject2).add(new VideoCapture.FramerateRange(localObject4[0], localObject4[1]));
        }
        localObject3 = a((List)localObject2, paramInt3 * 1000);
        localObject2 = new int[2];
        localObject2[0] = ((VideoCapture.FramerateRange)localObject3).jdField_a_of_type_Int;
        localObject2[1] = ((VideoCapture.FramerateRange)localObject3).jdField_b_of_type_Int;
        localObject3 = ((Camera.Parameters)localObject1).getSupportedPreviewSizes().iterator();
        int j = paramInt1;
        i = paramInt2;
        label270:
        int m;
        for (paramInt3 = Integer.MAX_VALUE; ((Iterator)localObject3).hasNext(); paramInt3 = m)
        {
          localObject4 = (Camera.Size)((Iterator)localObject3).next();
          m = Math.abs(((Camera.Size)localObject4).width - paramInt1) + Math.abs(((Camera.Size)localObject4).height - paramInt2);
          if ((m >= paramInt3) || (((Camera.Size)localObject4).width % 32 != 0)) {
            break label270;
          }
          j = ((Camera.Size)localObject4).width;
          i = ((Camera.Size)localObject4).height;
        }
        if (paramInt3 == Integer.MAX_VALUE)
        {
          Log.e("VideoCapture", "allocate: can not find a multiple-of-32 resolution", new Object[0]);
          return false;
        }
        if (((Camera.Parameters)localObject1).isVideoStabilizationSupported()) {
          ((Camera.Parameters)localObject1).setVideoStabilization(true);
        }
        if (((Camera.Parameters)localObject1).getSupportedFocusModes().contains("continuous-video")) {
          ((Camera.Parameters)localObject1).setFocusMode("continuous-video");
        }
        this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat = new VideoCaptureFormat(j, i, localObject2[1] / 1000, BuggyDeviceHack.a());
        ((Camera.Parameters)localObject1).setPictureSize(j, i);
        ((Camera.Parameters)localObject1).setPreviewSize(j, i);
        ((Camera.Parameters)localObject1).setPreviewFpsRange(localObject2[0], localObject2[1]);
        ((Camera.Parameters)localObject1).setPreviewFormat(this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_d_of_type_Int);
        try
        {
          this.jdField_a_of_type_AndroidHardwareCamera.setParameters((Camera.Parameters)localObject1);
          this.jdField_a_of_type_ArrayOfInt = new int[1];
          GLES20.glGenTextures(1, this.jdField_a_of_type_ArrayOfInt, 0);
          GLES20.glBindTexture(36197, this.jdField_a_of_type_ArrayOfInt[0]);
          GLES20.glTexParameterf(36197, 10241, 9729.0F);
          GLES20.glTexParameterf(36197, 10240, 9729.0F);
          GLES20.glTexParameteri(36197, 10242, 33071);
          GLES20.glTexParameteri(36197, 10243, 33071);
          this.jdField_a_of_type_AndroidGraphicsSurfaceTexture = new SurfaceTexture(this.jdField_a_of_type_ArrayOfInt[0]);
          this.jdField_a_of_type_AndroidGraphicsSurfaceTexture.setOnFrameAvailableListener(null);
          try
          {
            this.jdField_a_of_type_AndroidHardwareCamera.setPreviewTexture(this.jdField_a_of_type_AndroidGraphicsSurfaceTexture);
            this.jdField_a_of_type_AndroidHardwareCamera.setErrorCallback(new CrErrorCallback(null));
            this.jdField_d_of_type_Int = (this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_a_of_type_Int * this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_b_of_type_Int * ImageFormat.getBitsPerPixel(this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_d_of_type_Int) / 8);
            paramInt1 = k;
            while (paramInt1 < 3)
            {
              localObject1 = new byte[this.jdField_d_of_type_Int];
              this.jdField_a_of_type_AndroidHardwareCamera.addCallbackBuffer((byte[])localObject1);
              paramInt1 += 1;
            }
            return true;
          }
          catch (IOException localIOException)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("allocate: ");
            ((StringBuilder)localObject2).append(localIOException);
            Log.e("VideoCapture", ((StringBuilder)localObject2).toString(), new Object[0]);
            return false;
          }
          Log.e("VideoCapture", "allocate: no fps range found", new Object[0]);
        }
        catch (RuntimeException localRuntimeException1)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("setParameters: ");
          ((StringBuilder)localObject2).append(localRuntimeException1);
          Log.e("VideoCapture", ((StringBuilder)localObject2).toString(), new Object[0]);
          return false;
        }
      }
      return false;
    }
    catch (RuntimeException localRuntimeException2)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("allocate: Camera.open: ");
      ((StringBuilder)localObject2).append(localRuntimeException2);
      Log.e("VideoCapture", ((StringBuilder)localObject2).toString(), new Object[0]);
    }
    return false;
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
      if (this.jdField_a_of_type_ArrayOfInt != null) {
        GLES20.glDeleteTextures(1, this.jdField_a_of_type_ArrayOfInt, 0);
      }
      this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat = null;
      this.jdField_a_of_type_AndroidHardwareCamera.release();
      this.jdField_a_of_type_AndroidHardwareCamera = null;
      return;
    }
    catch (IOException localIOException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("deallocate: failed to deallocate camera, ");
      localStringBuilder.append(localIOException);
      Log.e("VideoCapture", localStringBuilder.toString(), new Object[0]);
    }
  }
  
  public PhotoCapabilities getPhotoCapabilities()
  {
    Object localObject1 = a(this.jdField_a_of_type_AndroidHardwareCamera);
    PhotoCapabilities.Builder localBuilder = new PhotoCapabilities.Builder();
    if (localObject1 == null) {
      return localBuilder.a();
    }
    localBuilder.b(0).a(0).c(0).d(0);
    Object localObject2 = ((Camera.Parameters)localObject1).getSupportedPictureSizes().iterator();
    int m = Integer.MAX_VALUE;
    int j = 0;
    int n = Integer.MAX_VALUE;
    int i = 0;
    int k;
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Camera.Size)((Iterator)localObject2).next();
      k = n;
      if (((Camera.Size)localObject3).width < n) {
        k = ((Camera.Size)localObject3).width;
      }
      int i1 = m;
      if (((Camera.Size)localObject3).height < m) {
        i1 = ((Camera.Size)localObject3).height;
      }
      int i2 = i;
      if (((Camera.Size)localObject3).width > i) {
        i2 = ((Camera.Size)localObject3).width;
      }
      m = i1;
      n = k;
      i = i2;
      if (((Camera.Size)localObject3).height > j)
      {
        j = ((Camera.Size)localObject3).height;
        m = i1;
        n = k;
        i = i2;
      }
    }
    localBuilder.f(m).e(j).h(1);
    localBuilder.j(n).i(i).l(1);
    localObject2 = ((Camera.Parameters)localObject1).getPreviewSize();
    localBuilder.g(((Camera.Size)localObject2).height).k(((Camera.Size)localObject2).width);
    if (((Camera.Parameters)localObject1).isZoomSupported())
    {
      j = ((Integer)((Camera.Parameters)localObject1).getZoomRatios().get(((Camera.Parameters)localObject1).getMaxZoom())).intValue();
      k = ((Integer)((Camera.Parameters)localObject1).getZoomRatios().get(((Camera.Parameters)localObject1).getZoom())).intValue();
      m = ((Integer)((Camera.Parameters)localObject1).getZoomRatios().get(0)).intValue();
      if (((Camera.Parameters)localObject1).getZoomRatios().size() > 1) {
        i = ((Integer)((Camera.Parameters)localObject1).getZoomRatios().get(1)).intValue() - ((Integer)((Camera.Parameters)localObject1).getZoomRatios().get(0)).intValue();
      } else {
        i = 0;
      }
    }
    else
    {
      j = 0;
      k = 0;
      m = 0;
      i = 0;
    }
    localBuilder.b(m).a(j);
    localBuilder.c(k).d(i);
    localObject2 = ((Camera.Parameters)localObject1).getSupportedFocusModes();
    if ((!jdField_c_of_type_Boolean) && (localObject2 == null)) {
      throw new AssertionError("getSupportedFocusModes() should never return null");
    }
    Object localObject3 = new ArrayList(3);
    boolean bool = ((List)localObject2).contains("continuous-video");
    j = 4;
    if ((bool) || (((List)localObject2).contains("continuous-picture")) || (((List)localObject2).contains("edof"))) {
      ((ArrayList)localObject3).add(Integer.valueOf(4));
    }
    if ((((List)localObject2).contains("auto")) || (((List)localObject2).contains("macro"))) {
      ((ArrayList)localObject3).add(Integer.valueOf(3));
    }
    if ((((List)localObject2).contains("infinity")) || (((List)localObject2).contains("fixed"))) {
      ((ArrayList)localObject3).add(Integer.valueOf(2));
    }
    localBuilder.a(a((ArrayList)localObject3));
    localObject2 = ((Camera.Parameters)localObject1).getFocusMode();
    if ((!((String)localObject2).equals("continuous-video")) && (!((String)localObject2).equals("continuous-picture")) && (!((String)localObject2).equals("edof")))
    {
      if ((!((String)localObject2).equals("auto")) && (!((String)localObject2).equals("macro")))
      {
        if ((!((String)localObject2).equals("infinity")) && (!((String)localObject2).equals("fixed"))) {
          i = 1;
        } else {
          i = 2;
        }
      }
      else {
        i = 3;
      }
    }
    else {
      i = 4;
    }
    localBuilder.m(i);
    localObject2 = new ArrayList(2);
    ((ArrayList)localObject2).add(Integer.valueOf(4));
    if (((Camera.Parameters)localObject1).isAutoExposureLockSupported()) {
      ((ArrayList)localObject2).add(Integer.valueOf(2));
    }
    localBuilder.b(a((ArrayList)localObject2));
    if ((((Camera.Parameters)localObject1).isAutoExposureLockSupported()) && (((Camera.Parameters)localObject1).getAutoExposureLock())) {
      i = 2;
    } else {
      i = 4;
    }
    localBuilder.n(i);
    float f1 = ((Camera.Parameters)localObject1).getExposureCompensationStep();
    localBuilder.h(f1);
    localBuilder.f(((Camera.Parameters)localObject1).getMinExposureCompensation() * f1);
    localBuilder.e(((Camera.Parameters)localObject1).getMaxExposureCompensation() * f1);
    localBuilder.g(((Camera.Parameters)localObject1).getExposureCompensation() * f1);
    localObject2 = new ArrayList(2);
    localObject3 = ((Camera.Parameters)localObject1).getSupportedWhiteBalance();
    if (localObject3 != null)
    {
      if (!((List)localObject3).isEmpty()) {
        ((ArrayList)localObject2).add(Integer.valueOf(4));
      }
      if (((Camera.Parameters)localObject1).isAutoWhiteBalanceLockSupported()) {
        ((ArrayList)localObject2).add(Integer.valueOf(2));
      }
    }
    localBuilder.c(a((ArrayList)localObject2));
    i = j;
    if (((Camera.Parameters)localObject1).isAutoWhiteBalanceLockSupported())
    {
      i = j;
      if (((Camera.Parameters)localObject1).getAutoWhiteBalanceLock()) {
        i = 2;
      }
    }
    localBuilder.o(i);
    localBuilder.q(jdField_a_of_type_AndroidUtilSparseArray.keyAt(0));
    localObject2 = jdField_a_of_type_AndroidUtilSparseArray;
    localBuilder.p(((SparseArray)localObject2).keyAt(((SparseArray)localObject2).size() - 1));
    if (i == 2)
    {
      i = jdField_a_of_type_AndroidUtilSparseArray.indexOfValue(((Camera.Parameters)localObject1).getWhiteBalance());
      if (i >= 0) {
        localBuilder.r(jdField_a_of_type_AndroidUtilSparseArray.keyAt(i));
      }
    }
    localBuilder.s(50);
    localObject2 = ((Camera.Parameters)localObject1).getSupportedFlashModes();
    if (localObject2 != null)
    {
      localBuilder.a(((List)localObject2).contains("torch"));
      if (((Camera.Parameters)localObject1).getFlashMode() == "torch") {
        bool = true;
      } else {
        bool = false;
      }
      localBuilder.b(bool);
      localBuilder.c(((List)localObject2).contains("red-eye"));
      localObject1 = new ArrayList(0);
      if (((List)localObject2).contains("off")) {
        ((ArrayList)localObject1).add(Integer.valueOf(1));
      }
      if (((List)localObject2).contains("auto")) {
        ((ArrayList)localObject1).add(Integer.valueOf(2));
      }
      if (((List)localObject2).contains("on")) {
        ((ArrayList)localObject1).add(Integer.valueOf(3));
      }
      localBuilder.d(a((ArrayList)localObject1));
    }
    return localBuilder.a();
  }
  
  public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
  {
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
    for (;;)
    {
      int i;
      try
      {
        bool = this.jdField_d_of_type_Boolean;
        if (!bool) {
          return;
        }
        if (paramArrayOfByte.length == this.jdField_d_of_type_Int)
        {
          if (this.jdField_a_of_type_Boolean) {
            break label173;
          }
          if (this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_d_of_type_Int != 842094169)
          {
            if (this.jdField_a_of_type_OrgChromiumMediaVideoCaptureFormat.jdField_d_of_type_Int != 35) {
              break label173;
            }
            break label167;
            i = (this.g + getDeviceRotation()) % 360;
            if (i >= 285) {
              break label243;
            }
            if (i >= 75) {
              break label179;
            }
            break label243;
            nativeOnFrameAvailable(this.jdField_a_of_type_Long, paramArrayOfByte, this.jdField_d_of_type_Int, getCameraRotation(), bool, i);
          }
        }
        else
        {
          return;
        }
      }
      finally
      {
        this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
        if (paramCamera != null) {
          paramCamera.addCallbackBuffer(paramArrayOfByte);
        }
      }
      label167:
      boolean bool = true;
      continue;
      label173:
      bool = false;
      continue;
      label179:
      if ((i >= 75) && (i < 135))
      {
        i = 90;
      }
      else if ((i >= 135) && (i < 225))
      {
        i = 180;
      }
      else if ((i >= 225) && (i < 285))
      {
        i = 270;
      }
      else
      {
        continue;
        label243:
        i = 0;
      }
    }
  }
  
  public void setPhotoOptions(double paramDouble1, int paramInt1, int paramInt2, double paramDouble2, double paramDouble3, float[] paramArrayOfFloat, boolean paramBoolean1, double paramDouble4, int paramInt3, double paramDouble5, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, boolean paramBoolean4, boolean paramBoolean5, double paramDouble6)
  {
    Camera.Parameters localParameters = a(this.jdField_a_of_type_AndroidHardwareCamera);
    int i;
    if ((localParameters.isZoomSupported()) && (paramDouble1 > 0.0D))
    {
      localObject = localParameters.getZoomRatios();
      i = 1;
      while ((i < ((List)localObject).size()) && (paramDouble1 >= ((Integer)((List)localObject).get(i)).intValue())) {
        i += 1;
      }
      localParameters.setZoom(i - 1);
    }
    if (paramInt1 == 2) {
      localParameters.setFocusMode("fixed");
    } else if (paramInt1 == 3) {
      localParameters.setFocusMode("auto");
    } else if (paramInt1 == 4) {
      localParameters.setFocusMode("continuous-picture");
    }
    if (localParameters.isAutoExposureLockSupported()) {
      if (paramInt2 == 2) {
        localParameters.setAutoExposureLock(true);
      } else if (paramInt2 != 1) {
        localParameters.setAutoExposureLock(false);
      }
    }
    if (paramDouble2 > 0.0D) {
      this.e = ((int)Math.round(paramDouble2));
    }
    if (paramDouble3 > 0.0D) {
      this.f = ((int)Math.round(paramDouble3));
    }
    Object localObject = this.jdField_a_of_type_AndroidHardwareCamera$Area;
    if ((localObject != null) && (!((Camera.Area)localObject).rect.isEmpty()) && (paramDouble1 > 0.0D)) {
      this.jdField_a_of_type_AndroidHardwareCamera$Area = null;
    }
    if ((paramInt1 == 1) || (paramInt2 == 1)) {
      this.jdField_a_of_type_AndroidHardwareCamera$Area = null;
    }
    if ((localParameters.getMaxNumMeteringAreas() <= 0) && (localParameters.getMaxNumFocusAreas() <= 0)) {
      paramInt2 = 0;
    } else {
      paramInt2 = 1;
    }
    if ((paramInt2 != 0) && (paramArrayOfFloat.length > 0))
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
      paramInt2 = Math.round(paramArrayOfFloat[0] * 2000.0F) - 1000;
      i = Math.round(paramArrayOfFloat[1] * 2000.0F) - 1000;
      this.jdField_a_of_type_AndroidHardwareCamera$Area = new Camera.Area(new Rect(Math.max(64536, paramInt2 - 125), Math.max(64536, i - 125), Math.min(1000, paramInt2 + 125), Math.min(1000, i + 125)), 1000);
    }
    paramArrayOfFloat = this.jdField_a_of_type_AndroidHardwareCamera$Area;
    if (paramArrayOfFloat != null)
    {
      localParameters.setFocusAreas(Arrays.asList(new Camera.Area[] { paramArrayOfFloat }));
      localParameters.setMeteringAreas(Arrays.asList(new Camera.Area[] { this.jdField_a_of_type_AndroidHardwareCamera$Area }));
    }
    if (paramBoolean1)
    {
      paramDouble1 = localParameters.getExposureCompensationStep();
      Double.isNaN(paramDouble1);
      localParameters.setExposureCompensation((int)Math.round(paramDouble4 / paramDouble1));
    }
    if (paramInt3 == 4) {
      if (localParameters.getSupportedWhiteBalance() != null)
      {
        localParameters.setWhiteBalance("auto");
        break label623;
      }
    }
    if (paramInt3 == 2) {
      if (localParameters.isAutoWhiteBalanceLockSupported()) {
        localParameters.setAutoWhiteBalanceLock(true);
      } else {}
    }
    label623:
    if (paramDouble6 > 0.0D)
    {
      paramArrayOfFloat = a((int)paramDouble6, localParameters.getSupportedWhiteBalance());
      if (paramArrayOfFloat != null) {
        localParameters.setWhiteBalance(paramArrayOfFloat);
      }
    }
    if (localParameters.getSupportedFlashModes() != null) {
      if ((paramBoolean4) && (paramBoolean5)) {
        localParameters.setFlashMode("torch");
      } else if (paramInt4 != 0) {
        switch (paramInt4)
        {
        default: 
          break;
        case 3: 
          localParameters.setFlashMode("on");
          break;
        case 2: 
          if ((paramBoolean2) && (paramBoolean3)) {
            paramArrayOfFloat = "red-eye";
          } else {
            paramArrayOfFloat = "auto";
          }
          localParameters.setFlashMode(paramArrayOfFloat);
          break;
        case 1: 
          localParameters.setFlashMode("off");
        }
      }
    }
    try
    {
      this.jdField_a_of_type_AndroidHardwareCamera.setParameters(localParameters);
      if (paramInt1 != 3) {
        return;
      }
      this.jdField_a_of_type_AndroidHardwareCamera.autoFocus(new Camera.AutoFocusCallback()
      {
        public void onAutoFocus(boolean paramAnonymousBoolean, Camera paramAnonymousCamera) {}
      });
      return;
    }
    catch (RuntimeException paramArrayOfFloat)
    {
      Log.e("VideoCapture", "setParameters: ", new Object[] { paramArrayOfFloat });
    }
  }
  
  /* Error */
  public boolean startCapture()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 216	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   4: ifnonnull +17 -> 21
    //   7: ldc -124
    //   9: ldc_w 759
    //   12: iconst_0
    //   13: anewarray 85	java/lang/Object
    //   16: invokestatic 141	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   19: iconst_0
    //   20: ireturn
    //   21: aload_0
    //   22: getfield 93	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   25: invokevirtual 648	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   28: aload_0
    //   29: getfield 650	org/chromium/media/VideoCaptureCamera:jdField_d_of_type_Boolean	Z
    //   32: istore_1
    //   33: iload_1
    //   34: ifeq +12 -> 46
    //   37: aload_0
    //   38: getfield 93	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   41: invokevirtual 653	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   44: iconst_1
    //   45: ireturn
    //   46: aload_0
    //   47: getfield 93	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   50: invokevirtual 653	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   53: aload_0
    //   54: aload_0
    //   55: invokespecial 761	org/chromium/media/VideoCaptureCamera:a	(Landroid/hardware/Camera$PreviewCallback;)V
    //   58: aload_0
    //   59: getfield 216	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_AndroidHardwareCamera	Landroid/hardware/Camera;
    //   62: invokevirtual 764	android/hardware/Camera:startPreview	()V
    //   65: aload_0
    //   66: getfield 93	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   69: invokevirtual 648	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   72: aload_0
    //   73: aload_0
    //   74: getfield 658	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_Long	J
    //   77: invokevirtual 768	org/chromium/media/VideoCaptureCamera:nativeOnStarted	(J)V
    //   80: aload_0
    //   81: iconst_1
    //   82: putfield 650	org/chromium/media/VideoCaptureCamera:jdField_d_of_type_Boolean	Z
    //   85: aload_0
    //   86: getfield 93	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   89: invokevirtual 653	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   92: aload_0
    //   93: invokespecial 770	org/chromium/media/VideoCaptureCamera:a	()V
    //   96: iconst_1
    //   97: ireturn
    //   98: astore_2
    //   99: aload_0
    //   100: getfield 93	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   103: invokevirtual 653	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   106: aload_2
    //   107: athrow
    //   108: astore_2
    //   109: new 121	java/lang/StringBuilder
    //   112: dup
    //   113: invokespecial 122	java/lang/StringBuilder:<init>	()V
    //   116: astore_3
    //   117: aload_3
    //   118: ldc_w 772
    //   121: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload_3
    //   126: aload_2
    //   127: invokevirtual 130	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: ldc -124
    //   133: aload_3
    //   134: invokevirtual 136	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   137: iconst_0
    //   138: anewarray 85	java/lang/Object
    //   141: invokestatic 141	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   144: iconst_0
    //   145: ireturn
    //   146: astore_2
    //   147: aload_0
    //   148: getfield 93	org/chromium/media/VideoCaptureCamera:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   151: invokevirtual 653	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   154: aload_2
    //   155: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	156	0	this	VideoCaptureCamera
    //   32	2	1	bool	boolean
    //   98	9	2	localObject1	Object
    //   108	19	2	localRuntimeException	RuntimeException
    //   146	9	2	localObject2	Object
    //   116	18	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   72	85	98	finally
    //   58	65	108	java/lang/RuntimeException
    //   28	33	146	finally
  }
  
  public boolean stopCapture()
  {
    if (this.jdField_a_of_type_AndroidHardwareCamera == null)
    {
      Log.e("VideoCapture", "stopCapture: mCamera is null", new Object[0]);
      return true;
    }
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
    try
    {
      boolean bool = this.jdField_d_of_type_Boolean;
      if (!bool) {
        return true;
      }
      this.jdField_d_of_type_Boolean = false;
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
      this.jdField_a_of_type_AndroidHardwareCamera.stopPreview();
      a(null);
      b();
      return true;
    }
    finally
    {
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
    }
  }
  
  public boolean takePhoto(long paramLong)
  {
    if ((this.jdField_a_of_type_AndroidHardwareCamera != null) && (this.jdField_d_of_type_Boolean)) {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (this.jdField_b_of_type_Long != 0L) {
          return false;
        }
        this.jdField_b_of_type_Long = paramLong;
        this.jdField_a_of_type_AndroidHardwareCamera$Parameters = a(this.jdField_a_of_type_AndroidHardwareCamera);
        Camera.Parameters localParameters = a(this.jdField_a_of_type_AndroidHardwareCamera);
        localParameters.setRotation(getCameraRotation());
        Object localObject2;
        if ((this.e > 0) || (this.f > 0))
        {
          Iterator localIterator = localParameters.getSupportedPictureSizes().iterator();
          ??? = null;
          int i = Integer.MAX_VALUE;
          while (localIterator.hasNext())
          {
            localObject2 = (Camera.Size)localIterator.next();
            int j;
            if (this.e > 0) {
              j = Math.abs(((Camera.Size)localObject2).width - this.e);
            } else {
              j = 0;
            }
            int k;
            if (this.f > 0) {
              k = Math.abs(((Camera.Size)localObject2).height - this.f);
            } else {
              k = 0;
            }
            j += k;
            if (j < i)
            {
              ??? = localObject2;
              i = j;
            }
          }
          if (i != Integer.MAX_VALUE) {
            localParameters.setPictureSize(((Camera.Size)???).width, ((Camera.Size)???).height);
          }
        }
        try
        {
          this.jdField_a_of_type_AndroidHardwareCamera.setParameters(localParameters);
          this.jdField_a_of_type_AndroidHardwareCamera.takePicture(null, null, null, new CrPictureCallback(null));
          return true;
        }
        catch (RuntimeException localRuntimeException)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("setParameters ");
          ((StringBuilder)localObject2).append(localRuntimeException);
          Log.e("VideoCapture", ((StringBuilder)localObject2).toString(), new Object[0]);
          return false;
        }
      }
    }
    Log.e("VideoCapture", "takePhoto: mCamera is null or is not running", new Object[0]);
    return false;
  }
  
  private static class BuggyDeviceHack
  {
    private static final String[] a = { "SAMSUNG-SGH-I747", "ODROID-U2", "XT1092", "XT1095", "XT1096", "XT1097", "SM919", "SM705" };
    
    static int a()
    {
      String[] arrayOfString = a;
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (arrayOfString[i].contentEquals(Build.MODEL)) {
          return 17;
        }
        i += 1;
      }
      return 842094169;
    }
  }
  
  private class CrErrorCallback
    implements Camera.ErrorCallback
  {
    private CrErrorCallback() {}
    
    public void onError(int paramInt, Camera arg2)
    {
      ??? = VideoCaptureCamera.this;
      long l = ???.a;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Error id: ");
      localStringBuilder.append(paramInt);
      ???.nativeOnError(l, localStringBuilder.toString());
      synchronized (VideoCaptureCamera.a(VideoCaptureCamera.this))
      {
        if (VideoCaptureCamera.a(VideoCaptureCamera.this) == 0L) {
          return;
        }
        VideoCaptureCamera.this.nativeOnPhotoTaken(VideoCaptureCamera.this.a, VideoCaptureCamera.a(VideoCaptureCamera.this), new byte[0]);
        VideoCaptureCamera.a(VideoCaptureCamera.this, 0L);
        return;
      }
    }
  }
  
  private class CrPictureCallback
    implements Camera.PictureCallback
  {
    private CrPictureCallback() {}
    
    public void onPictureTaken(byte[] paramArrayOfByte, Camera arg2)
    {
      try
      {
        ???.setParameters(VideoCaptureCamera.a(VideoCaptureCamera.this));
      }
      catch (RuntimeException localRuntimeException)
      {
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("onPictureTaken, setParameters() ");
        localStringBuilder2.append(localRuntimeException);
        Log.e("VideoCapture", localStringBuilder2.toString(), new Object[0]);
      }
      try
      {
        ???.startPreview();
      }
      catch (RuntimeException ???)
      {
        StringBuilder localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("onPictureTaken, startPreview() ");
        localStringBuilder1.append(???);
        Log.e("VideoCapture", localStringBuilder1.toString(), new Object[0]);
      }
      synchronized (VideoCaptureCamera.a(VideoCaptureCamera.this))
      {
        if (VideoCaptureCamera.a(VideoCaptureCamera.this) != 0L) {
          VideoCaptureCamera.this.nativeOnPhotoTaken(VideoCaptureCamera.this.a, VideoCaptureCamera.a(VideoCaptureCamera.this), paramArrayOfByte);
        }
        VideoCaptureCamera.a(VideoCaptureCamera.this, 0L);
        return;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\VideoCaptureCamera.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */