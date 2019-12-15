package org.chromium.policy;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("policy::android")
public class CombinedPolicyProvider
{
  private static CombinedPolicyProvider jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider;
  private long jdField_a_of_type_Long;
  private final List<PolicyProvider> jdField_a_of_type_JavaUtilList = new ArrayList();
  private PolicyConverter jdField_a_of_type_OrgChromiumPolicyPolicyConverter;
  private final List<Bundle> b = new ArrayList();
  private final List<PolicyChangeListener> c = new ArrayList();
  
  public static CombinedPolicyProvider a()
  {
    if (jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider == null) {
      jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider = new CombinedPolicyProvider();
    }
    return jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider;
  }
  
  private void a(long paramLong, PolicyConverter paramPolicyConverter)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_OrgChromiumPolicyPolicyConverter = paramPolicyConverter;
    if (paramLong != 0L)
    {
      paramPolicyConverter = this.jdField_a_of_type_JavaUtilList.iterator();
      while (paramPolicyConverter.hasNext()) {
        ((PolicyProvider)paramPolicyConverter.next()).refresh();
      }
    }
  }
  
  @CalledByNative
  public static CombinedPolicyProvider linkNative(long paramLong, PolicyConverter paramPolicyConverter)
  {
    ThreadUtils.assertOnUiThread();
    a().a(paramLong, paramPolicyConverter);
    return a();
  }
  
  void a(int paramInt, Bundle paramBundle)
  {
    this.b.set(paramInt, paramBundle);
    paramBundle = this.b.iterator();
    while (paramBundle.hasNext()) {
      if ((Bundle)paramBundle.next() == null) {
        return;
      }
    }
    if (this.jdField_a_of_type_Long == 0L) {
      return;
    }
    paramBundle = this.b.iterator();
    while (paramBundle.hasNext())
    {
      Bundle localBundle = (Bundle)paramBundle.next();
      Iterator localIterator = localBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        this.jdField_a_of_type_OrgChromiumPolicyPolicyConverter.a(str, localBundle.get(str));
      }
    }
    nativeFlushPolicies(this.jdField_a_of_type_Long);
  }
  
  public void a(PolicyProvider paramPolicyProvider)
  {
    this.jdField_a_of_type_JavaUtilList.add(paramPolicyProvider);
    this.b.add(null);
    paramPolicyProvider.setManagerAndSource(this, this.jdField_a_of_type_JavaUtilList.size() - 1);
    if (this.jdField_a_of_type_Long != 0L) {
      paramPolicyProvider.refresh();
    }
  }
  
  @VisibleForTesting
  protected native void nativeFlushPolicies(long paramLong);
  
  @VisibleForTesting
  @CalledByNative
  void refreshPolicies()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_JavaUtilList.size() != this.b.size())) {
      throw new AssertionError();
    }
    int i = 0;
    while (i < this.b.size())
    {
      this.b.set(i, null);
      i += 1;
    }
    Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
    while (localIterator.hasNext()) {
      ((PolicyProvider)localIterator.next()).refresh();
    }
  }
  
  public static abstract interface PolicyChangeListener
  {
    public abstract void terminateIncognitoSession();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\policy\CombinedPolicyProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */