package org.chromium.policy;

import android.os.Bundle;
import org.chromium.base.ThreadUtils;

public abstract class PolicyProvider
{
  private int jdField_a_of_type_Int = -1;
  private CombinedPolicyProvider jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider;
  
  public void destroy() {}
  
  public void notifySettingsAvailable(Bundle paramBundle)
  {
    ThreadUtils.assertOnUiThread();
    this.jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider.a(this.jdField_a_of_type_Int, paramBundle);
  }
  
  public abstract void refresh();
  
  final void setManagerAndSource(CombinedPolicyProvider paramCombinedPolicyProvider, int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int >= 0)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (paramInt < 0)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Int = paramInt;
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider != null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumPolicyCombinedPolicyProvider = paramCombinedPolicyProvider;
    startListeningForPolicyChanges();
  }
  
  protected void startListeningForPolicyChanges() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\policy\PolicyProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */