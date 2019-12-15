package org.chromium.tencent.android_webview;

import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwScrollOffsetManager.Delegate;
import org.chromium.tencent.widget.OverScrollBrand;

public class TencentAwScrollOffsetManager
  extends AwScrollOffsetManager
{
  private OverScrollBrand mOverScrollBrand = null;
  
  public TencentAwScrollOffsetManager(AwScrollOffsetManager.Delegate paramDelegate)
  {
    super(paramDelegate);
  }
  
  public void bindOverScrollBrand(OverScrollBrand paramOverScrollBrand)
  {
    this.mOverScrollBrand = paramOverScrollBrand;
  }
  
  protected int clampVerticalScroll(int paramInt)
  {
    OverScrollBrand localOverScrollBrand = this.mOverScrollBrand;
    if ((localOverScrollBrand != null) && (localOverScrollBrand.isOverScrollMode())) {
      return this.mOverScrollBrand.clampVerticalScroll(paramInt);
    }
    return super.clampVerticalScroll(paramInt);
  }
  
  public boolean pageDown(boolean paramBoolean)
  {
    OverScrollBrand localOverScrollBrand = this.mOverScrollBrand;
    if ((localOverScrollBrand != null) && (localOverScrollBrand.isOverScrollMode())) {
      return true;
    }
    return super.pageDown(paramBoolean);
  }
  
  public boolean pageUp(boolean paramBoolean)
  {
    OverScrollBrand localOverScrollBrand = this.mOverScrollBrand;
    if ((localOverScrollBrand != null) && (localOverScrollBrand.isOverScrollMode())) {
      return true;
    }
    return super.pageUp(paramBoolean);
  }
  
  protected void scrollNativeTo(int paramInt1, int paramInt2)
  {
    OverScrollBrand localOverScrollBrand = this.mOverScrollBrand;
    if ((localOverScrollBrand != null) && (localOverScrollBrand.isOverScrollMode())) {
      return;
    }
    super.scrollNativeTo(paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\android_webview\TencentAwScrollOffsetManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */