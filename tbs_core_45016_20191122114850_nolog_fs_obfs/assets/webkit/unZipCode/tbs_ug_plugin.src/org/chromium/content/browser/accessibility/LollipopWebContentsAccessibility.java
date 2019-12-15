package org.chromium.content.browser.accessibility;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableString;
import android.text.style.LocaleSpan;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;
import android.view.accessibility.AccessibilityNodeInfo.RangeInfo;
import java.util.Locale;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("content")
@TargetApi(21)
public class LollipopWebContentsAccessibility
  extends KitKatWebContentsAccessibility
{
  private static SparseArray<AccessibilityNodeInfo.AccessibilityAction> jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
  private BroadcastReceiver jdField_a_of_type_AndroidContentBroadcastReceiver;
  private String jdField_a_of_type_JavaLangString;
  
  LollipopWebContentsAccessibility(WebContents paramWebContents)
  {
    super(paramWebContents);
  }
  
  private void b()
  {
    if (!a()) {
      return;
    }
    try
    {
      IntentFilter localIntentFilter = new IntentFilter("android.intent.action.LOCALE_CHANGED");
      X5ApiCompatibilityUtils.x5RegisterReceiver(this.jdField_a_of_type_AndroidContentContext.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver, localIntentFilter);
      this.jdField_a_of_type_JavaLangString = Locale.getDefault().toLanguageTag();
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
  }
  
  protected CharSequence a(String paramString1, boolean paramBoolean, String paramString2)
  {
    paramString1 = super.a(paramString1, paramBoolean, paramString2);
    if ((!paramString2.isEmpty()) && (!paramString2.equals(this.jdField_a_of_type_JavaLangString)))
    {
      if ((paramString1 instanceof SpannableString)) {
        paramString1 = (SpannableString)paramString1;
      } else {
        paramString1 = new SpannableString(paramString1);
      }
      paramString1.setSpan(new LocaleSpan(Locale.forLanguageTag(paramString2)), 0, paramString1.length(), 0);
      return paramString1;
    }
    return paramString1;
  }
  
  protected void a()
  {
    super.a();
    this.jdField_a_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        LollipopWebContentsAccessibility.a(LollipopWebContentsAccessibility.this, Locale.getDefault().toLanguageTag());
      }
    };
    if (this.jdField_a_of_type_AndroidViewViewGroup.isAttachedToWindow()) {
      b();
    }
  }
  
  protected void a(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt)
  {
    AccessibilityNodeInfo.AccessibilityAction localAccessibilityAction2 = (AccessibilityNodeInfo.AccessibilityAction)jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
    AccessibilityNodeInfo.AccessibilityAction localAccessibilityAction1 = localAccessibilityAction2;
    if (localAccessibilityAction2 == null)
    {
      localAccessibilityAction1 = new AccessibilityNodeInfo.AccessibilityAction(paramInt, null);
      jdField_a_of_type_AndroidUtilSparseArray.put(paramInt, localAccessibilityAction1);
    }
    paramAccessibilityNodeInfo.addAction(localAccessibilityAction1);
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    b();
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (!a()) {
      return;
    }
    try
    {
      X5ApiCompatibilityUtils.x5UnRegisterReceiver(this.jdField_a_of_type_AndroidContentContext.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver);
      return;
    }
    catch (Exception localException) {}
  }
  
  protected void setAccessibilityEventCollectionInfo(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2, boolean paramBoolean) {}
  
  protected void setAccessibilityEventCollectionItemInfo(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  protected void setAccessibilityEventHeadingFlag(AccessibilityEvent paramAccessibilityEvent, boolean paramBoolean) {}
  
  protected void setAccessibilityEventLollipopAttributes(AccessibilityEvent paramAccessibilityEvent, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt1, int paramInt2) {}
  
  protected void setAccessibilityEventRangeInfo(AccessibilityEvent paramAccessibilityEvent, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {}
  
  protected void setAccessibilityNodeInfoCollectionInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    paramAccessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(paramInt1, paramInt2, paramBoolean));
  }
  
  protected void setAccessibilityNodeInfoCollectionItemInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    paramAccessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean));
  }
  
  protected void setAccessibilityNodeInfoLollipopAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt1, int paramInt2)
  {
    paramAccessibilityNodeInfo.setCanOpenPopup(paramBoolean1);
    paramAccessibilityNodeInfo.setContentInvalid(paramBoolean2);
    paramAccessibilityNodeInfo.setDismissable(paramBoolean2);
    paramAccessibilityNodeInfo.setMultiLine(paramBoolean4);
    paramAccessibilityNodeInfo.setInputType(paramInt1);
    paramAccessibilityNodeInfo.setLiveRegion(paramInt2);
  }
  
  protected void setAccessibilityNodeInfoRangeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramAccessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(paramInt, paramFloat1, paramFloat2, paramFloat3));
  }
  
  protected void setAccessibilityNodeInfoViewIdResourceName(AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString)
  {
    paramAccessibilityNodeInfo.setViewIdResourceName(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\LollipopWebContentsAccessibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */