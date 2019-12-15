package org.chromium.ui.display;

import android.annotation.SuppressLint;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("ui")
public class DisplayAndroidManager
{
  private static DisplayAndroidManager jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager;
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private SparseArray<DisplayAndroid> jdField_a_of_type_AndroidUtilSparseArray;
  private DisplayListenerBackend jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager$DisplayListenerBackend;
  private int b = 1073741823;
  
  public static Display a(Context paramContext)
  {
    return ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
  }
  
  static DisplayAndroidManager a()
  {
    if (jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager == null)
    {
      jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager = new DisplayAndroidManager();
      jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager.c();
    }
    return jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager;
  }
  
  private void a(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    nativeSetPrimaryDisplayId(this.jdField_a_of_type_Long, this.jdField_a_of_type_Int);
    int i = 0;
    while (i < this.jdField_a_of_type_AndroidUtilSparseArray.size())
    {
      a((DisplayAndroid)this.jdField_a_of_type_AndroidUtilSparseArray.valueAt(i));
      i += 1;
    }
  }
  
  private static Context b()
  {
    return ContextUtils.getApplicationContext();
  }
  
  private static DisplayManager b()
  {
    return (DisplayManager)b().getSystemService("display");
  }
  
  private DisplayAndroid b(Display paramDisplay)
  {
    int i = paramDisplay.getDisplayId();
    PhysicalDisplayAndroid localPhysicalDisplayAndroid = new PhysicalDisplayAndroid(paramDisplay);
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidUtilSparseArray.get(i) != null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidUtilSparseArray.put(i, localPhysicalDisplayAndroid);
    localPhysicalDisplayAndroid.a(paramDisplay);
    return localPhysicalDisplayAndroid;
  }
  
  private void c()
  {
    this.jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
    Display localDisplay1;
    if (Build.VERSION.SDK_INT >= 17)
    {
      this.jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager$DisplayListenerBackend = new DisplayListenerBackendImpl(null);
      Display localDisplay2 = b().getDisplay(0);
      localDisplay1 = localDisplay2;
      if (localDisplay2 == null) {
        localDisplay1 = a(b());
      }
    }
    else
    {
      this.jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager$DisplayListenerBackend = new DisplayListenerAPI16(null);
      localDisplay1 = a(b());
    }
    this.jdField_a_of_type_Int = localDisplay1.getDisplayId();
    b(localDisplay1);
    this.jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager$DisplayListenerBackend.startListening();
  }
  
  private native void nativeRemoveDisplay(long paramLong, int paramInt);
  
  private native void nativeSetPrimaryDisplayId(long paramLong, int paramInt);
  
  private native void nativeUpdateDisplay(long paramLong, int paramInt1, int paramInt2, int paramInt3, float paramFloat, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean);
  
  @CalledByNative
  private static void onNativeSideCreated(long paramLong)
  {
    a().a(paramLong);
  }
  
  DisplayAndroid a(Display paramDisplay)
  {
    int i = paramDisplay.getDisplayId();
    DisplayAndroid localDisplayAndroid2 = (DisplayAndroid)this.jdField_a_of_type_AndroidUtilSparseArray.get(i);
    DisplayAndroid localDisplayAndroid1 = localDisplayAndroid2;
    if (localDisplayAndroid2 == null) {
      localDisplayAndroid1 = b(paramDisplay);
    }
    return localDisplayAndroid1;
  }
  
  void a()
  {
    this.jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager$DisplayListenerBackend.startAccurateListening();
  }
  
  void a(DisplayAndroid paramDisplayAndroid)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeUpdateDisplay(l, paramDisplayAndroid.getDisplayId(), paramDisplayAndroid.getDisplayWidth(), paramDisplayAndroid.getDisplayHeight(), paramDisplayAndroid.getDipScale(), paramDisplayAndroid.a(), paramDisplayAndroid.b(), paramDisplayAndroid.c(), paramDisplayAndroid.getIsWideColorGamut());
  }
  
  void b()
  {
    this.jdField_a_of_type_OrgChromiumUiDisplayDisplayAndroidManager$DisplayListenerBackend.stopAccurateListening();
  }
  
  private class DisplayListenerAPI16
    implements ComponentCallbacks, DisplayAndroidManager.DisplayListenerBackend
  {
    private int jdField_a_of_type_Int;
    
    private DisplayListenerAPI16() {}
    
    public void onConfigurationChanged(Configuration paramConfiguration)
    {
      ((PhysicalDisplayAndroid)DisplayAndroidManager.a(DisplayAndroidManager.this).get(DisplayAndroidManager.a(DisplayAndroidManager.this))).a(DisplayAndroidManager.a(DisplayAndroidManager.a()));
    }
    
    public void onLowMemory() {}
    
    public void startAccurateListening()
    {
      this.jdField_a_of_type_Int += 1;
      if (this.jdField_a_of_type_Int > 1) {
        return;
      }
      ThreadUtils.postOnUiThreadDelayed(new Runnable()
      {
        public void run()
        {
          jdField_this.onConfigurationChanged(null);
          if (DisplayAndroidManager.DisplayListenerAPI16.a(jdField_this) < 1) {
            return;
          }
          ThreadUtils.postOnUiThreadDelayed(this, 500L);
        }
      }, 500L);
    }
    
    public void startListening()
    {
      DisplayAndroidManager.a().registerComponentCallbacks(this);
    }
    
    public void stopAccurateListening()
    {
      this.jdField_a_of_type_Int -= 1;
      if (!jdField_a_of_type_Boolean)
      {
        if (this.jdField_a_of_type_Int >= 0) {
          return;
        }
        throw new AssertionError();
      }
    }
  }
  
  private static abstract interface DisplayListenerBackend
  {
    public abstract void startAccurateListening();
    
    public abstract void startListening();
    
    public abstract void stopAccurateListening();
  }
  
  @SuppressLint({"NewApi"})
  private class DisplayListenerBackendImpl
    implements DisplayManager.DisplayListener, DisplayAndroidManager.DisplayListenerBackend
  {
    private DisplayListenerBackendImpl() {}
    
    public void onDisplayAdded(int paramInt) {}
    
    public void onDisplayChanged(int paramInt)
    {
      PhysicalDisplayAndroid localPhysicalDisplayAndroid = (PhysicalDisplayAndroid)DisplayAndroidManager.a(DisplayAndroidManager.this).get(paramInt);
      Display localDisplay = DisplayAndroidManager.a().getDisplay(paramInt);
      if ((localPhysicalDisplayAndroid != null) && (localDisplay != null)) {
        localPhysicalDisplayAndroid.a(localDisplay);
      }
    }
    
    public void onDisplayRemoved(int paramInt)
    {
      if (paramInt == DisplayAndroidManager.a(DisplayAndroidManager.this)) {
        return;
      }
      if ((DisplayAndroid)DisplayAndroidManager.a(DisplayAndroidManager.this).get(paramInt) == null) {
        return;
      }
      if (DisplayAndroidManager.a(DisplayAndroidManager.this) != 0L)
      {
        DisplayAndroidManager localDisplayAndroidManager = DisplayAndroidManager.this;
        DisplayAndroidManager.a(localDisplayAndroidManager, DisplayAndroidManager.a(localDisplayAndroidManager), paramInt);
      }
      DisplayAndroidManager.a(DisplayAndroidManager.this).remove(paramInt);
    }
    
    public void startAccurateListening() {}
    
    public void startListening()
    {
      DisplayAndroidManager.a().registerDisplayListener(this, null);
    }
    
    public void stopAccurateListening() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\display\DisplayAndroidManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */