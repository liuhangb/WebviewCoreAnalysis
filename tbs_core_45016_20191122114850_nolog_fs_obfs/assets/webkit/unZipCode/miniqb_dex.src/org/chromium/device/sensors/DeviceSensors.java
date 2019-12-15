package org.chromium.device.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.chromium.base.CollectionUtil;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("device")
class DeviceSensors
  implements SensorEventListener
{
  static final Set<Integer> jdField_a_of_type_JavaUtilSet = CollectionUtil.newHashSet(new Integer[] { Integer.valueOf(15) });
  static final Set<Integer> jdField_b_of_type_JavaUtilSet = CollectionUtil.newHashSet(new Integer[] { Integer.valueOf(11) });
  static final Set<Integer> jdField_c_of_type_JavaUtilSet = CollectionUtil.newHashSet(new Integer[] { Integer.valueOf(1), Integer.valueOf(2) });
  static final Set<Integer> jdField_d_of_type_JavaUtilSet = CollectionUtil.newHashSet(new Integer[] { Integer.valueOf(11) });
  static final Set<Integer> jdField_e_of_type_JavaUtilSet = CollectionUtil.newHashSet(new Integer[] { Integer.valueOf(1), Integer.valueOf(2) });
  static final Set<Integer> jdField_f_of_type_JavaUtilSet = CollectionUtil.newHashSet(new Integer[] { Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(4) });
  private long jdField_a_of_type_Long;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  final List<Set<Integer>> jdField_a_of_type_JavaUtilList = CollectionUtil.newArrayList(new Set[] { jdField_a_of_type_JavaUtilSet, jdField_b_of_type_JavaUtilSet, jdField_c_of_type_JavaUtilSet });
  private SensorManagerProxy jdField_a_of_type_OrgChromiumDeviceSensorsDeviceSensors$SensorManagerProxy;
  boolean jdField_a_of_type_Boolean;
  private double[] jdField_a_of_type_ArrayOfDouble;
  private float[] jdField_a_of_type_ArrayOfFloat;
  private final Object jdField_b_of_type_JavaLangObject = new Object();
  final List<Set<Integer>> jdField_b_of_type_JavaUtilList = CollectionUtil.newArrayList(new Set[] { jdField_d_of_type_JavaUtilSet, jdField_e_of_type_JavaUtilSet });
  boolean jdField_b_of_type_Boolean;
  private float[] jdField_b_of_type_ArrayOfFloat;
  boolean jdField_c_of_type_Boolean;
  private float[] jdField_c_of_type_ArrayOfFloat;
  boolean jdField_d_of_type_Boolean;
  boolean jdField_e_of_type_Boolean;
  boolean jdField_f_of_type_Boolean;
  @VisibleForTesting
  final Set<Integer> jdField_g_of_type_JavaUtilSet = new HashSet();
  boolean jdField_g_of_type_Boolean;
  Set<Integer> jdField_h_of_type_JavaUtilSet;
  Set<Integer> i;
  
  private Handler a()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_AndroidOsHandler == null)
      {
        localObject2 = new HandlerThread("DeviceMotionAndOrientation");
        ((HandlerThread)localObject2).start();
        this.jdField_a_of_type_AndroidOsHandler = new Handler(((HandlerThread)localObject2).getLooper());
      }
      Object localObject2 = this.jdField_a_of_type_AndroidOsHandler;
      return (Handler)localObject2;
    }
  }
  
  private SensorManagerProxy a()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumDeviceSensorsDeviceSensors$SensorManagerProxy;
    if (localObject != null) {
      return (SensorManagerProxy)localObject;
    }
    ThreadUtils.assertOnUiThread();
    localObject = (SensorManager)ContextUtils.getApplicationContext().getSystemService("sensor");
    if (localObject != null) {
      this.jdField_a_of_type_OrgChromiumDeviceSensorsDeviceSensors$SensorManagerProxy = new SensorManagerProxyImpl((SensorManager)localObject);
    }
    return this.jdField_a_of_type_OrgChromiumDeviceSensorsDeviceSensors$SensorManagerProxy;
  }
  
  private void a()
  {
    if (this.jdField_c_of_type_ArrayOfFloat == null) {
      this.jdField_c_of_type_ArrayOfFloat = new float[9];
    }
    if (this.jdField_a_of_type_ArrayOfDouble == null) {
      this.jdField_a_of_type_ArrayOfDouble = new double[3];
    }
    if (this.jdField_b_of_type_ArrayOfFloat == null) {
      this.jdField_b_of_type_ArrayOfFloat = new float[4];
    }
  }
  
  private void a(int paramInt, boolean paramBoolean)
  {
    boolean bool2 = true;
    boolean bool1 = true;
    if (paramInt != 4)
    {
      switch (paramInt)
      {
      default: 
        return;
      case 2: 
        this.jdField_b_of_type_Boolean = paramBoolean;
        if ((paramBoolean) && (this.jdField_h_of_type_JavaUtilSet == jdField_c_of_type_JavaUtilSet)) {
          paramBoolean = bool1;
        } else {
          paramBoolean = false;
        }
        this.jdField_c_of_type_Boolean = paramBoolean;
        return;
      }
      this.jdField_a_of_type_Boolean = paramBoolean;
      return;
    }
    this.jdField_d_of_type_Boolean = paramBoolean;
    if ((paramBoolean) && (this.i == jdField_e_of_type_JavaUtilSet)) {
      paramBoolean = bool2;
    } else {
      paramBoolean = false;
    }
    this.jdField_e_of_type_Boolean = paramBoolean;
  }
  
  private void a(Iterable<Integer> paramIterable)
  {
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      Integer localInteger = (Integer)paramIterable.next();
      if (this.jdField_g_of_type_JavaUtilSet.contains(localInteger))
      {
        a().unregisterListener(this, localInteger.intValue());
        this.jdField_g_of_type_JavaUtilSet.remove(localInteger);
      }
    }
  }
  
  private void a(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    if (paramArrayOfFloat1 != null)
    {
      if (paramArrayOfFloat2 == null) {
        return;
      }
      if (!SensorManager.getRotationMatrix(this.jdField_c_of_type_ArrayOfFloat, null, paramArrayOfFloat1, paramArrayOfFloat2)) {
        return;
      }
      a(this.jdField_c_of_type_ArrayOfFloat, this.jdField_a_of_type_ArrayOfDouble);
      double d1 = Math.toDegrees(this.jdField_a_of_type_ArrayOfDouble[0]);
      double d2 = Math.toDegrees(this.jdField_a_of_type_ArrayOfDouble[1]);
      double d3 = Math.toDegrees(this.jdField_a_of_type_ArrayOfDouble[2]);
      if (this.jdField_c_of_type_Boolean) {
        a(d1, d2, d3);
      }
      if (this.jdField_e_of_type_Boolean) {
        b(d1, d2, d3);
      }
      return;
    }
  }
  
  private boolean a(int paramInt1, int paramInt2)
  {
    SensorManagerProxy localSensorManagerProxy = a();
    if (localSensorManagerProxy == null) {
      return false;
    }
    return localSensorManagerProxy.registerListener(this, paramInt1, paramInt2, a());
  }
  
  private boolean a(Set<Integer> paramSet, int paramInt, boolean paramBoolean)
  {
    paramSet = new HashSet(paramSet);
    paramSet.removeAll(this.jdField_g_of_type_JavaUtilSet);
    if (paramSet.isEmpty()) {
      return true;
    }
    Iterator localIterator = paramSet.iterator();
    boolean bool1 = false;
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      boolean bool2 = a(localInteger.intValue(), paramInt);
      if ((!bool2) && (paramBoolean))
      {
        a(paramSet);
        return false;
      }
      if (bool2)
      {
        this.jdField_g_of_type_JavaUtilSet.add(localInteger);
        bool1 = true;
      }
    }
    return bool1;
  }
  
  @VisibleForTesting
  public static double[] a(float[] paramArrayOfFloat, double[] paramArrayOfDouble)
  {
    if (paramArrayOfFloat.length != 9) {
      return paramArrayOfDouble;
    }
    if (paramArrayOfFloat[8] > 0.0F)
    {
      paramArrayOfDouble[0] = Math.atan2(-paramArrayOfFloat[1], paramArrayOfFloat[4]);
      paramArrayOfDouble[1] = Math.asin(paramArrayOfFloat[7]);
      paramArrayOfDouble[2] = Math.atan2(-paramArrayOfFloat[6], paramArrayOfFloat[8]);
    }
    else
    {
      double d2;
      double d1;
      if (paramArrayOfFloat[8] < 0.0F)
      {
        paramArrayOfDouble[0] = Math.atan2(paramArrayOfFloat[1], -paramArrayOfFloat[4]);
        paramArrayOfDouble[1] = (-Math.asin(paramArrayOfFloat[7]));
        d2 = paramArrayOfDouble[1];
        if (paramArrayOfDouble[1] >= 0.0D) {
          d1 = -3.141592653589793D;
        } else {
          d1 = 3.141592653589793D;
        }
        paramArrayOfDouble[1] = (d2 + d1);
        paramArrayOfDouble[2] = Math.atan2(paramArrayOfFloat[6], -paramArrayOfFloat[8]);
      }
      else
      {
        float f1 = paramArrayOfFloat[6];
        d1 = -1.5707963267948966D;
        if (f1 > 0.0F)
        {
          paramArrayOfDouble[0] = Math.atan2(-paramArrayOfFloat[1], paramArrayOfFloat[4]);
          paramArrayOfDouble[1] = Math.asin(paramArrayOfFloat[7]);
          paramArrayOfDouble[2] = -1.5707963267948966D;
        }
        else if (paramArrayOfFloat[6] < 0.0F)
        {
          paramArrayOfDouble[0] = Math.atan2(paramArrayOfFloat[1], -paramArrayOfFloat[4]);
          paramArrayOfDouble[1] = (-Math.asin(paramArrayOfFloat[7]));
          d2 = paramArrayOfDouble[1];
          if (paramArrayOfDouble[1] >= 0.0D) {
            d1 = -3.141592653589793D;
          } else {
            d1 = 3.141592653589793D;
          }
          paramArrayOfDouble[1] = (d2 + d1);
          paramArrayOfDouble[2] = -1.5707963267948966D;
        }
        else
        {
          paramArrayOfDouble[0] = Math.atan2(paramArrayOfFloat[3], paramArrayOfFloat[0]);
          if (paramArrayOfFloat[7] > 0.0F) {
            d1 = 1.5707963267948966D;
          }
          paramArrayOfDouble[1] = d1;
          paramArrayOfDouble[2] = 0.0D;
        }
      }
    }
    if (paramArrayOfDouble[0] < 0.0D) {
      paramArrayOfDouble[0] += 6.283185307179586D;
    }
    return paramArrayOfDouble;
  }
  
  private void b()
  {
    this.jdField_c_of_type_ArrayOfFloat = null;
    this.jdField_a_of_type_ArrayOfDouble = null;
    this.jdField_b_of_type_ArrayOfFloat = null;
  }
  
  @CalledByNative
  static DeviceSensors create()
  {
    return new DeviceSensors();
  }
  
  private native void nativeGotAcceleration(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
  
  private native void nativeGotAccelerationIncludingGravity(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
  
  private native void nativeGotOrientation(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
  
  private native void nativeGotOrientationAbsolute(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
  
  private native void nativeGotRotationRate(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
  
  protected void a(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Long != 0L) {
        nativeGotOrientation(this.jdField_a_of_type_Long, paramDouble1, paramDouble2, paramDouble3);
      }
      return;
    }
  }
  
  @VisibleForTesting
  void a(int paramInt, float[] paramArrayOfFloat)
  {
    Object localObject;
    switch (paramInt)
    {
    default: 
      
    case 15: 
      if (this.jdField_b_of_type_Boolean)
      {
        a(paramArrayOfFloat, this.jdField_a_of_type_ArrayOfDouble);
        paramArrayOfFloat = this.jdField_a_of_type_ArrayOfDouble;
        a(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
        return;
      }
      break;
    case 11: 
      if ((this.jdField_d_of_type_Boolean) && (this.i == jdField_d_of_type_JavaUtilSet))
      {
        a(paramArrayOfFloat, this.jdField_a_of_type_ArrayOfDouble);
        localObject = this.jdField_a_of_type_ArrayOfDouble;
        b(localObject[0], localObject[1], localObject[2]);
        paramInt = 1;
      }
      else
      {
        paramInt = 0;
      }
      if ((this.jdField_b_of_type_Boolean) && (this.jdField_h_of_type_JavaUtilSet == jdField_b_of_type_JavaUtilSet))
      {
        if (paramInt == 0) {
          a(paramArrayOfFloat, this.jdField_a_of_type_ArrayOfDouble);
        }
        paramArrayOfFloat = this.jdField_a_of_type_ArrayOfDouble;
        a(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
        return;
      }
      break;
    case 10: 
      if (this.jdField_a_of_type_Boolean)
      {
        c(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
        return;
      }
      break;
    case 4: 
      if (this.jdField_a_of_type_Boolean)
      {
        e(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
        return;
      }
      break;
    case 2: 
      if ((this.jdField_c_of_type_Boolean) || (this.jdField_e_of_type_Boolean))
      {
        if (this.jdField_a_of_type_ArrayOfFloat == null) {
          this.jdField_a_of_type_ArrayOfFloat = new float[3];
        }
        localObject = this.jdField_a_of_type_ArrayOfFloat;
        System.arraycopy(paramArrayOfFloat, 0, localObject, 0, localObject.length);
        return;
      }
      break;
    case 1: 
      if (this.jdField_a_of_type_Boolean) {
        d(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
      }
      if ((this.jdField_c_of_type_Boolean) || (this.jdField_e_of_type_Boolean)) {
        a(paramArrayOfFloat, this.jdField_a_of_type_ArrayOfFloat);
      }
      break;
    }
  }
  
  public void a(float[] paramArrayOfFloat, double[] paramArrayOfDouble)
  {
    int k = paramArrayOfFloat.length;
    int j = 0;
    if (k > 4)
    {
      System.arraycopy(paramArrayOfFloat, 0, this.jdField_b_of_type_ArrayOfFloat, 0, 4);
      SensorManager.getRotationMatrixFromVector(this.jdField_c_of_type_ArrayOfFloat, this.jdField_b_of_type_ArrayOfFloat);
    }
    else
    {
      SensorManager.getRotationMatrixFromVector(this.jdField_c_of_type_ArrayOfFloat, paramArrayOfFloat);
    }
    a(this.jdField_c_of_type_ArrayOfFloat, paramArrayOfDouble);
    while (j < 3)
    {
      paramArrayOfDouble[j] = Math.toDegrees(paramArrayOfDouble[j]);
      j += 1;
    }
  }
  
  @VisibleForTesting
  protected boolean a(int paramInt)
  {
    if (this.jdField_f_of_type_Boolean) {
      return false;
    }
    Object localObject = this.jdField_h_of_type_JavaUtilSet;
    if (localObject != null) {
      return a((Set)localObject, paramInt, true);
    }
    a();
    localObject = this.jdField_a_of_type_JavaUtilList.iterator();
    while (((Iterator)localObject).hasNext())
    {
      this.jdField_h_of_type_JavaUtilSet = ((Set)((Iterator)localObject).next());
      if (a(this.jdField_h_of_type_JavaUtilSet, paramInt, true)) {
        return true;
      }
    }
    this.jdField_f_of_type_Boolean = true;
    this.jdField_h_of_type_JavaUtilSet = null;
    b();
    return false;
  }
  
  protected void b(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Long != 0L) {
        nativeGotOrientationAbsolute(this.jdField_a_of_type_Long, paramDouble1, paramDouble2, paramDouble3);
      }
      return;
    }
  }
  
  @VisibleForTesting
  protected boolean b(int paramInt)
  {
    if (this.jdField_g_of_type_Boolean) {
      return false;
    }
    Object localObject = this.i;
    if (localObject != null) {
      return a((Set)localObject, paramInt, true);
    }
    a();
    localObject = this.jdField_b_of_type_JavaUtilList.iterator();
    while (((Iterator)localObject).hasNext())
    {
      this.i = ((Set)((Iterator)localObject).next());
      if (a(this.i, paramInt, true)) {
        return true;
      }
    }
    this.jdField_g_of_type_Boolean = true;
    this.i = null;
    b();
    return false;
  }
  
  protected void c(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Long != 0L) {
        nativeGotAcceleration(this.jdField_a_of_type_Long, paramDouble1, paramDouble2, paramDouble3);
      }
      return;
    }
  }
  
  protected void d(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Long != 0L) {
        nativeGotAccelerationIncludingGravity(this.jdField_a_of_type_Long, paramDouble1, paramDouble2, paramDouble3);
      }
      return;
    }
  }
  
  protected void e(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_Long != 0L) {
        nativeGotRotationRate(this.jdField_a_of_type_Long, paramDouble1, paramDouble2, paramDouble3);
      }
      return;
    }
  }
  
  @CalledByNative
  public int getNumberActiveDeviceMotionSensors()
  {
    HashSet localHashSet = new HashSet(jdField_f_of_type_JavaUtilSet);
    localHashSet.removeAll(this.jdField_g_of_type_JavaUtilSet);
    return jdField_f_of_type_JavaUtilSet.size() - localHashSet.size();
  }
  
  @CalledByNative
  public int getOrientationSensorTypeUsed()
  {
    if (this.jdField_f_of_type_Boolean) {
      return 0;
    }
    Set localSet = this.jdField_h_of_type_JavaUtilSet;
    if (localSet == jdField_a_of_type_JavaUtilSet) {
      return 3;
    }
    if (localSet == jdField_b_of_type_JavaUtilSet) {
      return 1;
    }
    if (localSet == jdField_c_of_type_JavaUtilSet) {
      return 2;
    }
    if (jdField_h_of_type_Boolean) {
      return 0;
    }
    throw new AssertionError();
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    a(paramSensorEvent.sensor.getType(), paramSensorEvent.values);
  }
  
  @CalledByNative
  public boolean start(long paramLong, int paramInt1, int paramInt2)
  {
    Object localObject1 = this.jdField_b_of_type_JavaLangObject;
    if (paramInt1 != 4) {
      switch (paramInt1)
      {
      }
    }
    try
    {
      Log.e("DeviceSensors", "Unknown event type: %d", new Object[] { Integer.valueOf(paramInt1) });
      return false;
    }
    finally
    {
      boolean bool;
      Object localObject2;
      for (;;) {}
    }
    bool = a(paramInt2);
    break label95;
    bool = a(jdField_f_of_type_JavaUtilSet, paramInt2, false);
    break label95;
    bool = b(paramInt2);
    label95:
    if (bool)
    {
      this.jdField_a_of_type_Long = paramLong;
      a(paramInt1, true);
    }
    return bool;
    throw ((Throwable)localObject2);
  }
  
  @CalledByNative
  public void stop(int paramInt)
  {
    HashSet localHashSet1 = new HashSet();
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      if ((this.jdField_b_of_type_Boolean) && (paramInt != 2)) {
        localHashSet1.addAll(this.jdField_h_of_type_JavaUtilSet);
      }
      if ((this.jdField_d_of_type_Boolean) && (paramInt != 4)) {
        localHashSet1.addAll(this.i);
      }
      if ((this.jdField_a_of_type_Boolean) && (paramInt != 1)) {
        localHashSet1.addAll(jdField_f_of_type_JavaUtilSet);
      }
      HashSet localHashSet2 = new HashSet(this.jdField_g_of_type_JavaUtilSet);
      localHashSet2.removeAll(localHashSet1);
      a(localHashSet2);
      a(paramInt, false);
      if (this.jdField_g_of_type_JavaUtilSet.isEmpty()) {
        this.jdField_a_of_type_Long = 0L;
      }
      return;
    }
  }
  
  static abstract interface SensorManagerProxy
  {
    public abstract boolean registerListener(SensorEventListener paramSensorEventListener, int paramInt1, int paramInt2, Handler paramHandler);
    
    public abstract void unregisterListener(SensorEventListener paramSensorEventListener, int paramInt);
  }
  
  static class SensorManagerProxyImpl
    implements DeviceSensors.SensorManagerProxy
  {
    private final SensorManager a;
    
    SensorManagerProxyImpl(SensorManager paramSensorManager)
    {
      this.a = paramSensorManager;
    }
    
    public boolean registerListener(SensorEventListener paramSensorEventListener, int paramInt1, int paramInt2, Handler paramHandler)
    {
      List localList = this.a.getSensorList(paramInt1);
      if (localList.isEmpty()) {
        return false;
      }
      return this.a.registerListener(paramSensorEventListener, (Sensor)localList.get(0), paramInt2, paramHandler);
    }
    
    public void unregisterListener(SensorEventListener paramSensorEventListener, int paramInt)
    {
      List localList = this.a.getSensorList(paramInt);
      if (localList.isEmpty()) {
        return;
      }
      try
      {
        this.a.unregisterListener(paramSensorEventListener, (Sensor)localList.get(0));
        return;
      }
      catch (IllegalArgumentException paramSensorEventListener) {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\sensors\DeviceSensors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */