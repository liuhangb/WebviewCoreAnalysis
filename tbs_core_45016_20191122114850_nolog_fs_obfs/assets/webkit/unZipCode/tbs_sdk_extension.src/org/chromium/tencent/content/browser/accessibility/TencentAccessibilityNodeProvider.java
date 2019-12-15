package org.chromium.tencent.content.browser.accessibility;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

public abstract class TencentAccessibilityNodeProvider
{
  private static final String TAG = "TencentAccessibilityNodeProvider";
  private AccessibilityNodeProviderAdapter mAccessibilityNodeProviderAdapter = null;
  
  public TencentAccessibilityNodeProvider()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      this.mAccessibilityNodeProviderAdapter = new AccessibilityNodeProviderAdapter();
    }
  }
  
  public void addExtraDataToAccessibilityNodeInfo(int paramInt, AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString, Bundle paramBundle) {}
  
  public abstract AccessibilityNodeInfo createAccessibilityNodeInfo(int paramInt);
  
  public abstract List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String paramString, int paramInt);
  
  public AccessibilityNodeInfo findFocus(int paramInt)
  {
    return null;
  }
  
  protected AccessibilityNodeProvider getTencentAccessibilityNodeProvider()
  {
    return this.mAccessibilityNodeProviderAdapter;
  }
  
  public abstract boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle);
  
  public class AccessibilityNodeProviderAdapter
    extends AccessibilityNodeProvider
  {
    public AccessibilityNodeProviderAdapter() {}
    
    public void addExtraDataToAccessibilityNodeInfo(int paramInt, AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString, Bundle paramBundle)
    {
      TencentAccessibilityNodeProvider.this.addExtraDataToAccessibilityNodeInfo(paramInt, paramAccessibilityNodeInfo, paramString, paramBundle);
    }
    
    public AccessibilityNodeInfo createAccessibilityNodeInfo(int paramInt)
    {
      return TencentAccessibilityNodeProvider.this.createAccessibilityNodeInfo(paramInt);
    }
    
    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String paramString, int paramInt)
    {
      return TencentAccessibilityNodeProvider.this.findAccessibilityNodeInfosByText(paramString, paramInt);
    }
    
    public AccessibilityNodeInfo findFocus(int paramInt)
    {
      return TencentAccessibilityNodeProvider.this.findFocus(paramInt);
    }
    
    public boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle)
    {
      return TencentAccessibilityNodeProvider.this.performAction(paramInt1, paramInt2, paramBundle);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\content\browser\accessibility\TencentAccessibilityNodeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */