package org.chromium.base;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.os.StrictMode.VmPolicy;
import java.io.Closeable;

public final class StrictModeContext
  implements Closeable
{
  private final StrictMode.ThreadPolicy jdField_a_of_type_AndroidOsStrictMode$ThreadPolicy;
  private final StrictMode.VmPolicy jdField_a_of_type_AndroidOsStrictMode$VmPolicy;
  
  private StrictModeContext(StrictMode.ThreadPolicy paramThreadPolicy)
  {
    this(paramThreadPolicy, null);
  }
  
  private StrictModeContext(StrictMode.ThreadPolicy paramThreadPolicy, StrictMode.VmPolicy paramVmPolicy)
  {
    this.jdField_a_of_type_AndroidOsStrictMode$ThreadPolicy = paramThreadPolicy;
    this.jdField_a_of_type_AndroidOsStrictMode$VmPolicy = paramVmPolicy;
  }
  
  private StrictModeContext(StrictMode.VmPolicy paramVmPolicy)
  {
    this(null, paramVmPolicy);
  }
  
  public static StrictModeContext allowAllVmPolicies()
  {
    StrictMode.VmPolicy localVmPolicy = StrictMode.getVmPolicy();
    StrictMode.setVmPolicy(StrictMode.VmPolicy.LAX);
    return new StrictModeContext(localVmPolicy);
  }
  
  public static StrictModeContext allowDiskReads()
  {
    return new StrictModeContext(StrictMode.allowThreadDiskReads());
  }
  
  public static StrictModeContext allowDiskWrites()
  {
    return new StrictModeContext(StrictMode.allowThreadDiskWrites());
  }
  
  public static StrictModeContext allowSlowCalls()
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.getThreadPolicy();
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(localThreadPolicy).permitCustomSlowCalls().build());
    return new StrictModeContext(localThreadPolicy);
  }
  
  public void close()
  {
    Object localObject = this.jdField_a_of_type_AndroidOsStrictMode$ThreadPolicy;
    if (localObject != null) {
      StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)localObject);
    }
    localObject = this.jdField_a_of_type_AndroidOsStrictMode$VmPolicy;
    if (localObject != null) {
      StrictMode.setVmPolicy((StrictMode.VmPolicy)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\StrictModeContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */