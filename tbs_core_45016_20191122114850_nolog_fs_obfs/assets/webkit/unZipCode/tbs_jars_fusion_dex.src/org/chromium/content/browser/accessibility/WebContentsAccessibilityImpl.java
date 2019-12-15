package org.chromium.content.browser.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.WindowEventObserver;
import org.chromium.content.browser.webcontents.WebContentsImpl;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.AccessibilitySnapshotCallback;
import org.chromium.content_public.browser.AccessibilitySnapshotNode;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsAccessibility;
import org.chromium.tencent.SmttServiceClientProxy;
import org.chromium.tencent.content.browser.accessibility.TencentAccessibilityNodeProvider;

@JNINamespace("content")
public class WebContentsAccessibilityImpl
  extends TencentAccessibilityNodeProvider
  implements AccessibilityManager.AccessibilityStateChangeListener, WindowEventObserver, WebContentsAccessibility
{
  private static boolean l = false;
  protected int a;
  protected long a;
  protected Context a;
  private Rect jdField_a_of_type_AndroidGraphicsRect;
  private View jdField_a_of_type_AndroidViewView;
  protected ViewGroup a;
  protected AccessibilityManager a;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  private final String jdField_a_of_type_JavaLangString = "TALKBACK";
  private final WebContentsImpl jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl;
  private boolean jdField_a_of_type_Boolean;
  private int[] jdField_a_of_type_ArrayOfInt = new int[2];
  protected int b;
  private String jdField_b_of_type_JavaLangString;
  protected int c;
  private boolean c;
  private int jdField_d_of_type_Int = -1;
  private boolean jdField_d_of_type_Boolean;
  private int jdField_e_of_type_Int;
  private boolean jdField_e_of_type_Boolean;
  private int jdField_f_of_type_Int;
  private boolean jdField_f_of_type_Boolean;
  private int jdField_g_of_type_Int;
  private boolean jdField_g_of_type_Boolean;
  private boolean h;
  private boolean i;
  private boolean j;
  private boolean k;
  
  protected WebContentsAccessibilityImpl(WebContents paramWebContents)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl = ((WebContentsImpl)paramWebContents);
  }
  
  private Bundle a(AccessibilityEvent paramAccessibilityEvent)
  {
    Bundle localBundle2 = (Bundle)paramAccessibilityEvent.getParcelableData();
    Bundle localBundle1 = localBundle2;
    if (localBundle2 == null)
    {
      localBundle1 = new Bundle();
      paramAccessibilityEvent.setParcelableData(localBundle1);
    }
    return localBundle1;
  }
  
  private AccessibilityEvent a(int paramInt1, int paramInt2)
  {
    if (isAccessibilityEnabled())
    {
      if (!d()) {
        return null;
      }
      this.jdField_a_of_type_AndroidViewViewGroup.postInvalidate();
      AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(paramInt2);
      localAccessibilityEvent.setPackageName(this.jdField_a_of_type_AndroidContentContext.getPackageName());
      if (Build.VERSION.SDK_INT >= 16) {
        localAccessibilityEvent.setSource(this.jdField_a_of_type_AndroidViewViewGroup, paramInt1);
      }
      if (!nativePopulateAccessibilityEvent(this.jdField_a_of_type_Long, localAccessibilityEvent, paramInt1, paramInt2))
      {
        localAccessibilityEvent.recycle();
        return null;
      }
      return localAccessibilityEvent;
    }
    return null;
  }
  
  private AccessibilityNodeInfo a(int paramInt)
  {
    AccessibilityNodeInfo localAccessibilityNodeInfo1 = AccessibilityNodeInfo.obtain(this.jdField_a_of_type_AndroidViewViewGroup);
    AccessibilityNodeInfo localAccessibilityNodeInfo2 = AccessibilityNodeInfo.obtain(this.jdField_a_of_type_AndroidViewViewGroup);
    this.jdField_a_of_type_AndroidViewViewGroup.onInitializeAccessibilityNodeInfo(localAccessibilityNodeInfo2);
    Object localObject = new Rect();
    localAccessibilityNodeInfo2.getBoundsInParent((Rect)localObject);
    localAccessibilityNodeInfo1.setBoundsInParent((Rect)localObject);
    localAccessibilityNodeInfo2.getBoundsInScreen((Rect)localObject);
    localAccessibilityNodeInfo1.setBoundsInScreen((Rect)localObject);
    localObject = this.jdField_a_of_type_AndroidViewViewGroup.getParentForAccessibility();
    if ((localObject instanceof View)) {
      localAccessibilityNodeInfo1.setParent((View)localObject);
    }
    if (Build.VERSION.SDK_INT >= 16) {
      localAccessibilityNodeInfo1.setVisibleToUser(localAccessibilityNodeInfo2.isVisibleToUser());
    }
    localAccessibilityNodeInfo1.setEnabled(localAccessibilityNodeInfo2.isEnabled());
    localAccessibilityNodeInfo1.setPackageName(localAccessibilityNodeInfo2.getPackageName());
    localAccessibilityNodeInfo1.setClassName(localAccessibilityNodeInfo2.getClassName());
    if ((d()) && (Build.VERSION.SDK_INT >= 16)) {
      localAccessibilityNodeInfo1.addChild(this.jdField_a_of_type_AndroidViewViewGroup, paramInt);
    }
    return localAccessibilityNodeInfo1;
  }
  
  private void a(int paramInt)
  {
    this.jdField_e_of_type_Int = paramInt;
    if (this.jdField_e_of_type_Int == 0)
    {
      this.jdField_f_of_type_Int = -1;
      this.jdField_g_of_type_Int = -1;
    }
    if ((nativeIsEditableText(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int)) && (nativeIsFocused(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int)))
    {
      this.jdField_f_of_type_Int = nativeGetEditableTextSelectionStart(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int);
      this.jdField_g_of_type_Int = nativeGetEditableTextSelectionEnd(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int);
    }
  }
  
  private void a(int paramInt1, int paramInt2)
  {
    if (paramInt1 == -1)
    {
      this.jdField_a_of_type_AndroidViewViewGroup.sendAccessibilityEvent(paramInt2);
      return;
    }
    AccessibilityEvent localAccessibilityEvent = a(paramInt1, paramInt2);
    if (localAccessibilityEvent != null)
    {
      ViewGroup localViewGroup = this.jdField_a_of_type_AndroidViewViewGroup;
      localViewGroup.requestSendAccessibilityEvent(localViewGroup, localAccessibilityEvent);
    }
  }
  
  private void a(Context paramContext, ViewGroup paramViewGroup, String paramString)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
    this.jdField_b_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager = ((AccessibilityManager)this.jdField_a_of_type_AndroidContentContext.getSystemService("accessibility"));
    this.k = true;
  }
  
  @TargetApi(23)
  private void a(ViewStructure paramViewStructure, AccessibilitySnapshotNode paramAccessibilitySnapshotNode, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    paramViewStructure.setClassName(paramAccessibilitySnapshotNode.jdField_b_of_type_JavaLangString);
    if (paramAccessibilitySnapshotNode.jdField_g_of_type_Boolean) {
      paramViewStructure.setText(paramAccessibilitySnapshotNode.jdField_a_of_type_JavaLangString, paramAccessibilitySnapshotNode.jdField_g_of_type_Int, paramAccessibilitySnapshotNode.h);
    } else {
      paramViewStructure.setText(paramAccessibilitySnapshotNode.jdField_a_of_type_JavaLangString);
    }
    RenderCoordinates localRenderCoordinates = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.getRenderCoordinates();
    int i1 = (int)localRenderCoordinates.fromLocalCssToPix(paramAccessibilitySnapshotNode.jdField_a_of_type_Int);
    boolean bool2 = (int)localRenderCoordinates.fromLocalCssToPix(paramAccessibilitySnapshotNode.jdField_b_of_type_Int);
    boolean bool1 = (int)localRenderCoordinates.fromLocalCssToPix(paramAccessibilitySnapshotNode.jdField_c_of_type_Int);
    int n = (int)localRenderCoordinates.fromLocalCssToPix(paramAccessibilitySnapshotNode.jdField_d_of_type_Int);
    Rect localRect = new Rect(i1, bool2, i1 + bool1, bool2 + n);
    boolean bool3 = paramAccessibilitySnapshotNode.jdField_a_of_type_Boolean;
    bool2 = false;
    if (bool3)
    {
      localRect.offset(0, (int)localRenderCoordinates.getContentOffsetYPix());
      if (!paramBoolean) {
        localRect.offset(-(int)localRenderCoordinates.getScrollXPix(), -(int)localRenderCoordinates.getScrollYPix());
      }
    }
    paramViewStructure.setDimens(localRect.left, localRect.top, 0, 0, bool1, n);
    paramViewStructure.setChildCount(paramAccessibilitySnapshotNode.jdField_a_of_type_JavaUtilArrayList.size());
    bool1 = bool2;
    if (paramAccessibilitySnapshotNode.jdField_b_of_type_Boolean)
    {
      float f1 = localRenderCoordinates.fromLocalCssToPix(paramAccessibilitySnapshotNode.jdField_a_of_type_Float);
      paramBoolean = paramAccessibilitySnapshotNode.jdField_c_of_type_Boolean;
      if (paramAccessibilitySnapshotNode.jdField_d_of_type_Boolean) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      if (paramAccessibilitySnapshotNode.jdField_e_of_type_Boolean) {
        n = 4;
      } else {
        n = 0;
      }
      if (paramAccessibilitySnapshotNode.jdField_f_of_type_Boolean) {
        i1 = 8;
      } else {
        i1 = 0;
      }
      paramViewStructure.setTextStyle(f1, paramAccessibilitySnapshotNode.jdField_e_of_type_Int, paramAccessibilitySnapshotNode.jdField_f_of_type_Int, paramBoolean | bool1 | n | i1);
      bool1 = bool2;
    }
    while (bool1 < paramAccessibilitySnapshotNode.jdField_a_of_type_JavaUtilArrayList.size())
    {
      a(paramViewStructure.asyncNewChild(bool1), (AccessibilitySnapshotNode)paramAccessibilitySnapshotNode.jdField_a_of_type_JavaUtilArrayList.get(bool1), true);
      int m;
      bool1 += true;
    }
    paramViewStructure.asyncCommit();
  }
  
  private static boolean a(int paramInt)
  {
    if (paramInt != 4) {
      switch (paramInt)
      {
      default: 
        return false;
      }
    }
    return true;
  }
  
  private boolean a(int paramInt, String paramString, boolean paramBoolean)
  {
    paramInt = nativeFindElementType(this.jdField_a_of_type_Long, paramInt, paramString, paramBoolean);
    if (paramInt == 0) {
      return false;
    }
    d(paramInt);
    nativeScrollToMakeNodeVisible(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int);
    return true;
  }
  
  private boolean a(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    if (paramInt2 != this.jdField_c_of_type_Int) {
      return false;
    }
    a(paramInt1);
    return nativeNextAtGranularity(this.jdField_a_of_type_Long, this.jdField_e_of_type_Int, paramBoolean, paramInt2, this.jdField_f_of_type_Int);
  }
  
  @CalledByNative
  private void addAccessibilityNodeInfoActions(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, boolean paramBoolean10, boolean paramBoolean11, boolean paramBoolean12, boolean paramBoolean13, boolean paramBoolean14)
  {
    a(paramAccessibilityNodeInfo, 1024);
    a(paramAccessibilityNodeInfo, 2048);
    a(paramAccessibilityNodeInfo, 256);
    a(paramAccessibilityNodeInfo, 512);
    a(paramAccessibilityNodeInfo, 16908342);
    a(paramAccessibilityNodeInfo, 16908348);
    if ((paramBoolean8) && (paramBoolean9))
    {
      a(paramAccessibilityNodeInfo, 2097152);
      a(paramAccessibilityNodeInfo, 32768);
      if (paramBoolean14)
      {
        a(paramAccessibilityNodeInfo, 131072);
        a(paramAccessibilityNodeInfo, 65536);
        a(paramAccessibilityNodeInfo, 16384);
      }
    }
    if (paramBoolean1) {
      a(paramAccessibilityNodeInfo, 4096);
    }
    if (paramBoolean2) {
      a(paramAccessibilityNodeInfo, 8192);
    }
    if (paramBoolean3) {
      a(paramAccessibilityNodeInfo, 16908344);
    }
    if (paramBoolean4) {
      a(paramAccessibilityNodeInfo, 16908346);
    }
    if (paramBoolean5) {
      a(paramAccessibilityNodeInfo, 16908345);
    }
    if (paramBoolean6) {
      a(paramAccessibilityNodeInfo, 16908347);
    }
    if (paramBoolean10) {
      if (paramBoolean11) {
        a(paramAccessibilityNodeInfo, 2);
      } else {
        a(paramAccessibilityNodeInfo, 1);
      }
    }
    if (this.jdField_b_of_type_Int == paramInt) {
      a(paramAccessibilityNodeInfo, 128);
    } else {
      a(paramAccessibilityNodeInfo, 64);
    }
    if (paramBoolean7) {
      a(paramAccessibilityNodeInfo, 16);
    }
    if (paramBoolean12) {
      a(paramAccessibilityNodeInfo, 262144);
    }
    if (paramBoolean13) {
      a(paramAccessibilityNodeInfo, 524288);
    }
  }
  
  @CalledByNative
  private void addAccessibilityNodeInfoChild(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt)
  {
    if (Build.VERSION.SDK_INT < 16) {
      return;
    }
    paramAccessibilityNodeInfo.addChild(this.jdField_a_of_type_AndroidViewViewGroup, paramInt);
  }
  
  @CalledByNative
  private void announceLiveRegionText(String paramString)
  {
    this.jdField_a_of_type_AndroidViewViewGroup.announceForAccessibility(paramString);
  }
  
  private void b()
  {
    Runnable localRunnable = this.jdField_a_of_type_JavaLangRunnable;
    if (localRunnable != null)
    {
      this.jdField_a_of_type_AndroidViewViewGroup.removeCallbacks(localRunnable);
      this.jdField_a_of_type_JavaLangRunnable = null;
    }
    this.jdField_a_of_type_AndroidViewViewGroup.sendAccessibilityEvent(2048);
  }
  
  private void b(int paramInt)
  {
    if (paramInt == this.jdField_b_of_type_Int)
    {
      a(paramInt, 65536);
      this.jdField_b_of_type_Int = -1;
    }
    d(paramInt);
  }
  
  private boolean b()
  {
    return this.k;
  }
  
  private boolean b(int paramInt)
  {
    if (nativeIsSlider(this.jdField_a_of_type_Long, paramInt)) {
      return nativeAdjustSlider(this.jdField_a_of_type_Long, paramInt, true);
    }
    return nativeScroll(this.jdField_a_of_type_Long, paramInt, 0);
  }
  
  private boolean b(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    if (paramInt2 != this.jdField_c_of_type_Int) {
      return false;
    }
    a(paramInt1);
    return nativePreviousAtGranularity(this.jdField_a_of_type_Long, this.jdField_e_of_type_Int, paramBoolean, paramInt2, this.jdField_g_of_type_Int);
  }
  
  private void c()
  {
    if (!a())
    {
      if (!this.jdField_g_of_type_Boolean) {
        return;
      }
      this.jdField_a_of_type_Long = nativeInit(this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl);
      a();
    }
    if (c()) {
      nativeDisable(this.jdField_a_of_type_Long);
    }
    nativeEnable(this.jdField_a_of_type_Long);
  }
  
  private void c(int paramInt)
  {
    a(paramInt, 2048);
  }
  
  private boolean c()
  {
    if (a()) {
      return nativeIsEnabled(this.jdField_a_of_type_Long);
    }
    return false;
  }
  
  private boolean c(int paramInt)
  {
    if (nativeIsSlider(this.jdField_a_of_type_Long, paramInt)) {
      return nativeAdjustSlider(this.jdField_a_of_type_Long, paramInt, false);
    }
    return nativeScroll(this.jdField_a_of_type_Long, paramInt, 1);
  }
  
  public static WebContentsAccessibilityImpl create(Context paramContext, ViewGroup paramViewGroup, WebContents paramWebContents, String paramString)
  {
    paramWebContents = (WebContentsAccessibilityImpl)WebContentsUserData.fromWebContents(paramWebContents, WebContentsAccessibilityImpl.class, UserDataFactoryLazyHolder.a());
    if ((!jdField_b_of_type_Boolean) && ((paramWebContents == null) || (paramWebContents.b()))) {
      throw new AssertionError();
    }
    paramWebContents.a(paramContext, paramViewGroup, paramString);
    return paramWebContents;
  }
  
  private void d()
  {
    if ((a()) && (c())) {
      nativeDisable(this.jdField_a_of_type_Long);
    }
  }
  
  private boolean d()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl;
    boolean bool = true;
    if (localObject == null) {
      return true;
    }
    localObject = ((WebContentsImpl)localObject).getRenderCoordinates();
    if (((RenderCoordinates)localObject).getContentWidthCss() == 0.0D)
    {
      if (((RenderCoordinates)localObject).getContentHeightCss() != 0.0D) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  private boolean d(int paramInt)
  {
    if (paramInt == this.jdField_b_of_type_Int) {
      return false;
    }
    this.jdField_b_of_type_Int = paramInt;
    this.jdField_a_of_type_AndroidGraphicsRect = null;
    this.jdField_c_of_type_Int = this.jdField_b_of_type_Int;
    this.jdField_e_of_type_Int = 0;
    this.jdField_f_of_type_Int = -1;
    this.jdField_g_of_type_Int = nativeGetTextLength(this.jdField_a_of_type_Long, paramInt);
    paramInt = this.jdField_b_of_type_Int;
    if (paramInt == this.jdField_a_of_type_Int) {
      nativeSetAccessibilityFocus(this.jdField_a_of_type_Long, -1);
    } else if (nativeIsAutofillPopupNode(this.jdField_a_of_type_Long, paramInt)) {
      this.jdField_a_of_type_AndroidViewView.requestFocus();
    } else {
      nativeSetAccessibilityFocus(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int);
    }
    a(this.jdField_b_of_type_Int, 32768);
    return true;
  }
  
  private void e()
  {
    if (!l) {}
    try
    {
      localList1 = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.getEnabledAccessibilityServiceList(1);
      if (localList1 == null) {
        break label196;
      }
      if (localList1.isEmpty()) {
        return;
      }
      localList2 = SmttServiceClientProxy.getInstance().getAccessibilityBlackList();
      str = "";
      m = 0;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        List localList1;
        List localList2;
        String str;
        int m;
        AccessibilityServiceInfo localAccessibilityServiceInfo;
        Object localObject2;
        label196:
        label197:
        continue;
        n += 1;
        continue;
        int n = 0;
        continue;
        m += 1;
        Object localObject1 = localObject2;
      }
    }
    if (m < localList1.size())
    {
      localAccessibilityServiceInfo = (AccessibilityServiceInfo)localList1.get(m);
      localObject2 = str;
      if (localList2 == null) {
        break label218;
      }
      n = 0;
      if (n >= localList2.size()) {
        break label213;
      }
      if ((localAccessibilityServiceInfo.getId() != null) && (localAccessibilityServiceInfo.getId().contains((CharSequence)localList2.get(n))))
      {
        n = 1;
        localObject2 = str;
        if (n != 0) {
          break label218;
        }
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(str);
        ((StringBuilder)localObject2).append(localAccessibilityServiceInfo.getId());
        ((StringBuilder)localObject2).append("|");
        localObject2 = ((StringBuilder)localObject2).toString();
        break label218;
      }
    }
    else
    {
      if (!str.isEmpty())
      {
        SmttServiceClientProxy.getInstance().reportAccessibilityEnableCause(str);
        break label197;
        return;
      }
      l = true;
      return;
    }
  }
  
  private boolean e()
  {
    e();
    if ((!this.jdField_g_of_type_Boolean) && (f()))
    {
      this.jdField_g_of_type_Boolean = true;
      this.j = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isTouchExplorationEnabled();
    }
    else if (!g())
    {
      this.jdField_g_of_type_Boolean = false;
      this.j = false;
    }
    if (!h()) {
      return false;
    }
    return this.jdField_g_of_type_Boolean;
  }
  
  private boolean f()
  {
    boolean bool2 = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isEnabled();
    boolean bool1 = false;
    if (!bool2) {
      return false;
    }
    for (;;)
    {
      int n;
      int i1;
      int i2;
      try
      {
        List localList1 = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.getEnabledAccessibilityServiceList(1);
        if (localList1 != null)
        {
          if (localList1.isEmpty()) {
            return false;
          }
          List localList2 = SmttServiceClientProxy.getInstance().getAccessibilityBlackList();
          n = 0;
          m = 0;
          if (n < localList1.size())
          {
            AccessibilityServiceInfo localAccessibilityServiceInfo = (AccessibilityServiceInfo)localList1.get(n);
            i1 = m;
            if (localList2 == null) {
              break label178;
            }
            i2 = 0;
            i1 = m;
            if (i2 >= localList2.size()) {
              break label178;
            }
            if ((localAccessibilityServiceInfo.getId() != null) && (localAccessibilityServiceInfo.getId().contains((CharSequence)localList2.get(i2))))
            {
              i1 = m + 1;
              break label178;
            }
          }
          else
          {
            n = localList1.size();
            if (n > m) {
              bool1 = true;
            }
            return bool1;
          }
        }
        else
        {
          return false;
        }
      }
      catch (Throwable localThrowable)
      {
        return false;
      }
      i2 += 1;
      continue;
      label178:
      n += 1;
      int m = i1;
    }
  }
  
  @CalledByNative
  private void finishGranularityMove(String paramString, boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2)
  {
    AccessibilityEvent localAccessibilityEvent2 = a(this.jdField_c_of_type_Int, 8192);
    if (localAccessibilityEvent2 == null) {
      return;
    }
    AccessibilityEvent localAccessibilityEvent1 = a(this.jdField_c_of_type_Int, 131072);
    if (localAccessibilityEvent1 == null)
    {
      localAccessibilityEvent2.recycle();
      return;
    }
    if (paramBoolean2) {
      this.jdField_g_of_type_Int = paramInt2;
    } else {
      this.jdField_g_of_type_Int = paramInt1;
    }
    if (!paramBoolean1) {
      this.jdField_f_of_type_Int = this.jdField_g_of_type_Int;
    }
    if ((nativeIsEditableText(this.jdField_a_of_type_Long, this.jdField_c_of_type_Int)) && (nativeIsFocused(this.jdField_a_of_type_Long, this.jdField_c_of_type_Int))) {
      nativeSetSelection(this.jdField_a_of_type_Long, this.jdField_c_of_type_Int, this.jdField_f_of_type_Int, this.jdField_g_of_type_Int);
    }
    localAccessibilityEvent2.setFromIndex(this.jdField_f_of_type_Int);
    localAccessibilityEvent2.setToIndex(this.jdField_f_of_type_Int);
    localAccessibilityEvent2.setItemCount(paramString.length());
    if ((paramBoolean2) && (nativeIsEditableText(this.jdField_a_of_type_Long, this.jdField_c_of_type_Int)))
    {
      localAccessibilityEvent1.setFromIndex(paramInt1 - 1);
      localAccessibilityEvent1.setToIndex(paramInt2 - 1);
    }
    else
    {
      localAccessibilityEvent1.setFromIndex(paramInt1);
      localAccessibilityEvent1.setToIndex(paramInt2);
    }
    localAccessibilityEvent1.setItemCount(paramString.length());
    if (Build.VERSION.SDK_INT >= 16) {
      localAccessibilityEvent1.setMovementGranularity(this.jdField_e_of_type_Int);
    }
    localAccessibilityEvent1.setContentDescription(paramString);
    if (Build.VERSION.SDK_INT >= 16) {
      if (paramBoolean2) {
        localAccessibilityEvent1.setAction(256);
      } else {
        localAccessibilityEvent1.setAction(512);
      }
    }
    paramString = this.jdField_a_of_type_AndroidViewViewGroup;
    paramString.requestSendAccessibilityEvent(paramString, localAccessibilityEvent2);
    paramString = this.jdField_a_of_type_AndroidViewViewGroup;
    paramString.requestSendAccessibilityEvent(paramString, localAccessibilityEvent1);
  }
  
  public static WebContentsAccessibilityImpl fromWebContents(WebContents paramWebContents)
  {
    return (WebContentsAccessibilityImpl)WebContentsUserData.fromWebContents(paramWebContents, WebContentsAccessibilityImpl.class, null);
  }
  
  private boolean g()
  {
    return this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isEnabled();
  }
  
  @CalledByNative
  private int getAccessibilityServiceEventTypeMask()
  {
    Iterator localIterator = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.getEnabledAccessibilityServiceList(-1).iterator();
    int m = 0;
    while (localIterator.hasNext())
    {
      AccessibilityServiceInfo localAccessibilityServiceInfo = (AccessibilityServiceInfo)localIterator.next();
      if (localAccessibilityServiceInfo != null) {
        m |= localAccessibilityServiceInfo.eventTypes;
      }
    }
    return m;
  }
  
  @CalledByNative
  private int getAccessibilityServiceFeedbackTypeMask()
  {
    Iterator localIterator = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.getEnabledAccessibilityServiceList(-1).iterator();
    int m = 0;
    while (localIterator.hasNext())
    {
      AccessibilityServiceInfo localAccessibilityServiceInfo = (AccessibilityServiceInfo)localIterator.next();
      if (localAccessibilityServiceInfo != null) {
        m |= localAccessibilityServiceInfo.feedbackType;
      }
    }
    return m;
  }
  
  @CalledByNative
  private int getAccessibilityServiceFlagsMask()
  {
    Iterator localIterator = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.getEnabledAccessibilityServiceList(-1).iterator();
    int m = 0;
    while (localIterator.hasNext())
    {
      AccessibilityServiceInfo localAccessibilityServiceInfo = (AccessibilityServiceInfo)localIterator.next();
      if (localAccessibilityServiceInfo != null) {
        m |= localAccessibilityServiceInfo.flags;
      }
    }
    return m;
  }
  
  private boolean h()
  {
    boolean bool2 = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isEnabled();
    boolean bool1 = false;
    if (!bool2) {
      return false;
    }
    for (;;)
    {
      int n;
      int i1;
      int i2;
      try
      {
        List localList1 = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.getEnabledAccessibilityServiceList(-1);
        if (localList1 != null)
        {
          if (localList1.isEmpty()) {
            return false;
          }
          List localList2 = SmttServiceClientProxy.getInstance().getAccessibilityBlackList();
          n = 0;
          m = 0;
          if (n < localList1.size())
          {
            AccessibilityServiceInfo localAccessibilityServiceInfo = (AccessibilityServiceInfo)localList1.get(n);
            i1 = m;
            if (localList2 == null) {
              break label178;
            }
            i2 = 0;
            i1 = m;
            if (i2 >= localList2.size()) {
              break label178;
            }
            if ((localAccessibilityServiceInfo.getId() != null) && (localAccessibilityServiceInfo.getId().contains((CharSequence)localList2.get(i2))))
            {
              i1 = m + 1;
              break label178;
            }
          }
          else
          {
            n = localList1.size();
            if (n > m) {
              bool1 = true;
            }
            return bool1;
          }
        }
        else
        {
          return false;
        }
      }
      catch (Throwable localThrowable)
      {
        return false;
      }
      i2 += 1;
      continue;
      label178:
      n += 1;
      int m = i1;
    }
  }
  
  @CalledByNative
  private void handleCheckStateChanged(int paramInt)
  {
    a(paramInt, 1);
  }
  
  @CalledByNative
  private void handleClicked(int paramInt)
  {
    a(paramInt, 1);
  }
  
  @CalledByNative
  private void handleContentChanged(int paramInt)
  {
    int m = nativeGetRootId(this.jdField_a_of_type_Long);
    if (m != this.jdField_a_of_type_Int)
    {
      this.jdField_a_of_type_Int = m;
      b();
      return;
    }
    c(paramInt);
  }
  
  @CalledByNative
  private void handleEditableTextChanged(int paramInt)
  {
    a(paramInt, 16);
  }
  
  @CalledByNative
  private void handleFocusChanged(int paramInt)
  {
    if ((!this.h) && (this.jdField_b_of_type_Int == -1)) {
      return;
    }
    a(paramInt, 8);
    d(paramInt);
  }
  
  @CalledByNative
  private void handleHover(int paramInt)
  {
    if (this.jdField_d_of_type_Int == paramInt) {
      return;
    }
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    a(paramInt, 128);
    int m = this.jdField_d_of_type_Int;
    if (m != -1) {
      a(m, 256);
    }
    this.jdField_d_of_type_Int = paramInt;
  }
  
  @CalledByNative
  private void handleNavigate()
  {
    this.jdField_b_of_type_Int = -1;
    this.jdField_a_of_type_AndroidGraphicsRect = null;
    this.jdField_c_of_type_Boolean = false;
    b();
  }
  
  @CalledByNative
  private void handlePageLoaded(int paramInt)
  {
    if (!this.h) {
      return;
    }
    if (this.jdField_c_of_type_Boolean) {
      return;
    }
    b(paramInt);
  }
  
  @CalledByNative
  private void handleScrollPositionChanged(int paramInt)
  {
    a(paramInt, 4096);
  }
  
  @CalledByNative
  private void handleScrolledToAnchor(int paramInt)
  {
    d(paramInt);
  }
  
  @CalledByNative
  private void handleSliderChanged(int paramInt)
  {
    a(paramInt, 4096);
  }
  
  @CalledByNative
  private void handleTextSelectionChanged(int paramInt)
  {
    a(paramInt, 8192);
  }
  
  private native boolean nativeAdjustSlider(long paramLong, int paramInt, boolean paramBoolean);
  
  private native void nativeBlur(long paramLong);
  
  private native void nativeClick(long paramLong, int paramInt);
  
  private native void nativeDisable(long paramLong);
  
  private native void nativeEnable(long paramLong);
  
  private native int nativeFindElementType(long paramLong, int paramInt, String paramString, boolean paramBoolean);
  
  private native void nativeFocus(long paramLong, int paramInt);
  
  private native int nativeGetEditableTextSelectionEnd(long paramLong, int paramInt);
  
  private native int nativeGetEditableTextSelectionStart(long paramLong, int paramInt);
  
  private native int nativeGetIdForElementAfterElementHostingAutofillPopup(long paramLong);
  
  private native int nativeGetRootId(long paramLong);
  
  private native int nativeGetTextLength(long paramLong, int paramInt);
  
  private native long nativeInit(WebContents paramWebContents);
  
  private native boolean nativeIsAutofillPopupNode(long paramLong, int paramInt);
  
  private native boolean nativeIsEditableText(long paramLong, int paramInt);
  
  private native boolean nativeIsEnabled(long paramLong);
  
  private native boolean nativeIsFocused(long paramLong, int paramInt);
  
  private native boolean nativeIsNodeValid(long paramLong, int paramInt);
  
  private native boolean nativeIsSlider(long paramLong, int paramInt);
  
  private native boolean nativeNextAtGranularity(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
  
  private native void nativeOnAutofillPopupDismissed(long paramLong);
  
  private native void nativeOnAutofillPopupDisplayed(long paramLong);
  
  private native boolean nativePopulateAccessibilityEvent(long paramLong, AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2);
  
  private native boolean nativePopulateAccessibilityNodeInfo(long paramLong, AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt);
  
  private native boolean nativePreviousAtGranularity(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
  
  private native boolean nativeScroll(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeScrollToMakeNodeVisible(long paramLong, int paramInt);
  
  private native void nativeSetAccessibilityFocus(long paramLong, int paramInt);
  
  private native void nativeSetSelection(long paramLong, int paramInt1, int paramInt2, int paramInt3);
  
  private native void nativeSetTextFieldValue(long paramLong, int paramInt, String paramString);
  
  private native void nativeShowContextMenu(long paramLong, int paramInt);
  
  @CalledByNative
  private void notifyFrameInfoInitialized()
  {
    if (this.jdField_e_of_type_Boolean) {
      return;
    }
    this.jdField_e_of_type_Boolean = true;
    b();
    if (!this.h) {
      return;
    }
    int m = this.jdField_b_of_type_Int;
    if (m != -1) {
      b(m);
    }
  }
  
  @CalledByNative
  private boolean onHoverEvent(int paramInt)
  {
    if (!isAccessibilityEnabled()) {
      return false;
    }
    if (paramInt == 10)
    {
      this.jdField_a_of_type_Boolean = false;
      paramInt = this.jdField_d_of_type_Int;
      if (paramInt != -1)
      {
        a(paramInt, 256);
        this.jdField_d_of_type_Int = -1;
      }
      if (this.jdField_d_of_type_Boolean) {
        nativeScrollToMakeNodeVisible(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int);
      }
      this.jdField_d_of_type_Boolean = false;
      return true;
    }
    this.jdField_a_of_type_Boolean = true;
    this.jdField_c_of_type_Boolean = true;
    return true;
  }
  
  @CalledByNative
  private void sendDelayedWindowContentChangedEvent()
  {
    if (this.jdField_a_of_type_JavaLangRunnable != null) {
      return;
    }
    this.jdField_a_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        WebContentsAccessibilityImpl.a(WebContentsAccessibilityImpl.this);
      }
    };
    this.jdField_a_of_type_AndroidViewViewGroup.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 500L);
  }
  
  @CalledByNative
  private void setAccessibilityEventBooleanAttributes(AccessibilityEvent paramAccessibilityEvent, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    paramAccessibilityEvent.setChecked(paramBoolean1);
    paramAccessibilityEvent.setEnabled(paramBoolean2);
    paramAccessibilityEvent.setPassword(paramBoolean3);
    paramAccessibilityEvent.setScrollable(paramBoolean4);
  }
  
  @CalledByNative
  private void setAccessibilityEventClassName(AccessibilityEvent paramAccessibilityEvent, String paramString)
  {
    paramAccessibilityEvent.setClassName(paramString);
  }
  
  @CalledByNative
  private void setAccessibilityEventListAttributes(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2)
  {
    paramAccessibilityEvent.setCurrentItemIndex(paramInt1);
    paramAccessibilityEvent.setItemCount(paramInt2);
  }
  
  @CalledByNative
  private void setAccessibilityEventScrollAttributes(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramAccessibilityEvent.setScrollX(paramInt1);
    paramAccessibilityEvent.setScrollY(paramInt2);
    if (Build.VERSION.SDK_INT >= 15)
    {
      paramAccessibilityEvent.setMaxScrollX(paramInt3);
      paramAccessibilityEvent.setMaxScrollY(paramInt4);
    }
  }
  
  @CalledByNative
  private void setAccessibilityEventSelectionAttrs(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    paramAccessibilityEvent.setFromIndex(paramInt1);
    paramAccessibilityEvent.setToIndex(paramInt2);
    paramAccessibilityEvent.setItemCount(paramInt3);
    paramAccessibilityEvent.getText().add(paramString);
  }
  
  @CalledByNative
  private void setAccessibilityEventTextChangedAttrs(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2)
  {
    paramAccessibilityEvent.setFromIndex(paramInt1);
    paramAccessibilityEvent.setAddedCount(paramInt2);
    paramAccessibilityEvent.setRemovedCount(paramInt3);
    paramAccessibilityEvent.setBeforeText(paramString1);
    paramAccessibilityEvent.getText().add(paramString2);
  }
  
  @CalledByNative
  private void setAccessibilityNodeInfoBooleanAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, boolean paramBoolean10)
  {
    paramAccessibilityNodeInfo.setCheckable(paramBoolean1);
    paramAccessibilityNodeInfo.setChecked(paramBoolean2);
    paramAccessibilityNodeInfo.setClickable(paramBoolean3);
    paramAccessibilityNodeInfo.setEnabled(paramBoolean4);
    paramAccessibilityNodeInfo.setFocusable(paramBoolean5);
    paramAccessibilityNodeInfo.setFocused(paramBoolean6);
    paramAccessibilityNodeInfo.setPassword(paramBoolean7);
    paramAccessibilityNodeInfo.setScrollable(paramBoolean8);
    paramAccessibilityNodeInfo.setSelected(paramBoolean9);
    paramAccessibilityNodeInfo.setVisibleToUser(paramBoolean10);
    try
    {
      paramAccessibilityNodeInfo.setMovementGranularities(7);
      if (this.jdField_b_of_type_Int == paramInt)
      {
        paramAccessibilityNodeInfo.setAccessibilityFocused(true);
        return;
      }
      paramAccessibilityNodeInfo.setAccessibilityFocused(false);
      return;
    }
    catch (Throwable paramAccessibilityNodeInfo) {}
  }
  
  @CalledByNative
  private void setAccessibilityNodeInfoClassName(AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString)
  {
    paramAccessibilityNodeInfo.setClassName(paramString);
  }
  
  @CalledByNative
  private void setAccessibilityNodeInfoLocation(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean)
  {
    Rect localRect = new Rect(paramInt4, paramInt5, paramInt4 + paramInt6, paramInt5 + paramInt7);
    if (paramBoolean) {
      localRect.offset(0, (int)this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.getRenderCoordinates().getContentOffsetYPix());
    }
    paramAccessibilityNodeInfo.setBoundsInParent(localRect);
    localRect = new Rect(paramInt2, paramInt3, paramInt6 + paramInt2, paramInt7 + paramInt3);
    a(localRect);
    paramAccessibilityNodeInfo.setBoundsInScreen(localRect);
    if ((paramInt1 == this.jdField_b_of_type_Int) && (paramInt1 != this.jdField_a_of_type_Int))
    {
      paramAccessibilityNodeInfo = this.jdField_a_of_type_AndroidGraphicsRect;
      if (paramAccessibilityNodeInfo == null)
      {
        this.jdField_a_of_type_AndroidGraphicsRect = localRect;
        return;
      }
      if (!paramAccessibilityNodeInfo.equals(localRect))
      {
        this.jdField_a_of_type_AndroidGraphicsRect = localRect;
        b(paramInt1);
      }
    }
  }
  
  @CalledByNative
  private void setAccessibilityNodeInfoParent(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt)
  {
    if (Build.VERSION.SDK_INT < 16) {
      return;
    }
    paramAccessibilityNodeInfo.setParent(this.jdField_a_of_type_AndroidViewViewGroup, paramInt);
  }
  
  @SuppressLint({"NewApi"})
  @CalledByNative
  private void setAccessibilityNodeInfoText(AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2)
  {
    paramAccessibilityNodeInfo.setText(a(paramString1, paramBoolean2, paramString2));
  }
  
  protected CharSequence a(String paramString1, boolean paramBoolean, String paramString2)
  {
    if (paramBoolean)
    {
      paramString1 = new SpannableString(paramString1);
      paramString1.setSpan(new URLSpan(""), 0, paramString1.length(), 0);
      return paramString1;
    }
    return paramString1;
  }
  
  protected void a()
  {
    this.jdField_b_of_type_Int = -1;
    this.jdField_c_of_type_Int = -1;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_Int = -1;
  }
  
  protected void a(Rect paramRect)
  {
    RenderCoordinates localRenderCoordinates = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.getRenderCoordinates();
    paramRect.offset(-(int)localRenderCoordinates.getScrollX(), -(int)localRenderCoordinates.getScrollY());
    paramRect.left = ((int)localRenderCoordinates.fromLocalCssToPix(paramRect.left));
    paramRect.top = ((int)localRenderCoordinates.fromLocalCssToPix(paramRect.top));
    paramRect.bottom = ((int)localRenderCoordinates.fromLocalCssToPix(paramRect.bottom));
    paramRect.right = ((int)localRenderCoordinates.fromLocalCssToPix(paramRect.right));
    paramRect.offset(0, (int)localRenderCoordinates.getContentOffsetYPix());
    int[] arrayOfInt = new int[2];
    this.jdField_a_of_type_AndroidViewViewGroup.getLocationOnScreen(arrayOfInt);
    paramRect.offset(arrayOfInt[0], arrayOfInt[1]);
    int m = arrayOfInt[1] + (int)localRenderCoordinates.getContentOffsetYPix();
    int n = this.jdField_a_of_type_AndroidViewViewGroup.getHeight() + m;
    if (paramRect.top < m) {
      paramRect.top = m;
    }
    if (paramRect.bottom > n) {
      paramRect.bottom = n;
    }
  }
  
  protected void a(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt)
  {
    if (paramInt > 2097152) {
      return;
    }
    paramAccessibilityNodeInfo.addAction(paramInt);
  }
  
  protected boolean a()
  {
    return this.jdField_a_of_type_Long != 0L;
  }
  
  public AccessibilityNodeInfo createAccessibilityNodeInfo(int paramInt)
  {
    if (!isAccessibilityEnabled()) {
      return null;
    }
    int m = nativeGetRootId(this.jdField_a_of_type_Long);
    if (paramInt == -1) {
      return a(m);
    }
    if (!d()) {
      return null;
    }
    AccessibilityNodeInfo localAccessibilityNodeInfo = AccessibilityNodeInfo.obtain(this.jdField_a_of_type_AndroidViewViewGroup);
    localAccessibilityNodeInfo.setPackageName(this.jdField_a_of_type_AndroidContentContext.getPackageName());
    if (Build.VERSION.SDK_INT >= 16) {
      localAccessibilityNodeInfo.setSource(this.jdField_a_of_type_AndroidViewViewGroup, paramInt);
    }
    if (paramInt == m) {
      localAccessibilityNodeInfo.setParent(this.jdField_a_of_type_AndroidViewViewGroup);
    }
    if (nativePopulateAccessibilityNodeInfo(this.jdField_a_of_type_Long, localAccessibilityNodeInfo, paramInt)) {
      return localAccessibilityNodeInfo;
    }
    localAccessibilityNodeInfo.recycle();
    return null;
  }
  
  public void disableAccessibilityIfNecessary()
  {
    if (!h()) {
      d();
    }
  }
  
  public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String paramString, int paramInt)
  {
    return new ArrayList();
  }
  
  public AccessibilityNodeProvider getAccessibilityNodeProvider()
  {
    if (this.i) {
      return null;
    }
    if (!c())
    {
      if (!this.jdField_g_of_type_Boolean) {
        return null;
      }
      c();
    }
    return getTencentAccessibilityNodeProvider();
  }
  
  @CalledByNative
  protected int getAccessibilityServiceCapabilitiesMask()
  {
    return 0;
  }
  
  public boolean isAccessibilityEnabled()
  {
    return (a()) && ((this.jdField_f_of_type_Boolean) || (this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isEnabled()));
  }
  
  public boolean isTouchExplorationEnabled()
  {
    return this.j;
  }
  
  protected native boolean nativeAreInlineTextBoxesLoaded(long paramLong, int paramInt);
  
  protected native int[] nativeGetCharacterBoundingBoxes(long paramLong, int paramInt1, int paramInt2, int paramInt3);
  
  protected native String nativeGetSupportedHtmlElementTypes(long paramLong);
  
  protected native void nativeLoadInlineTextBoxes(long paramLong, int paramInt);
  
  public void onAccessibilityStateChanged(boolean paramBoolean)
  {
    setState(paramBoolean);
  }
  
  public void onAttachedToWindow()
  {
    this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.addAccessibilityStateChangeListener(this);
    startAccessibilityIfNecessary();
  }
  
  public void onAutofillPopupAccessibilityFocusCleared()
  {
    if (isAccessibilityEnabled())
    {
      int m = nativeGetIdForElementAfterElementHostingAutofillPopup(this.jdField_a_of_type_Long);
      if (m == 0) {
        return;
      }
      d(m);
      nativeScrollToMakeNodeVisible(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int);
    }
  }
  
  public void onAutofillPopupDismissed()
  {
    if (isAccessibilityEnabled())
    {
      nativeOnAutofillPopupDismissed(this.jdField_a_of_type_Long);
      this.jdField_a_of_type_AndroidViewView = null;
    }
  }
  
  public void onAutofillPopupDisplayed(View paramView)
  {
    if (isAccessibilityEnabled())
    {
      this.jdField_a_of_type_AndroidViewView = paramView;
      nativeOnAutofillPopupDisplayed(this.jdField_a_of_type_Long);
    }
  }
  
  public void onDetachedFromWindow()
  {
    this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.removeAccessibilityStateChangeListener(this);
  }
  
  @CalledByNative
  protected void onNativeObjectDestroyed()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  @TargetApi(23)
  public void onProvideVirtualStructure(final ViewStructure paramViewStructure, final boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    if (this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.isIncognito())
    {
      paramViewStructure.setChildCount(0);
      return;
    }
    paramViewStructure.setChildCount(1);
    paramViewStructure = paramViewStructure.asyncNewChild(0);
    this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.requestAccessibilitySnapshot(new AccessibilitySnapshotCallback()
    {
      public void onAccessibilitySnapshot(AccessibilitySnapshotNode paramAnonymousAccessibilitySnapshotNode)
      {
        paramViewStructure.setClassName("");
        paramViewStructure.setHint(WebContentsAccessibilityImpl.a(WebContentsAccessibilityImpl.this));
        if (paramAnonymousAccessibilitySnapshotNode == null)
        {
          paramViewStructure.asyncCommit();
          return;
        }
        WebContentsAccessibilityImpl.a(WebContentsAccessibilityImpl.this, paramViewStructure, paramAnonymousAccessibilitySnapshotNode, paramBoolean);
      }
    });
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {}
  
  public boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    if (isAccessibilityEnabled())
    {
      if (!nativeIsNodeValid(this.jdField_a_of_type_Long, paramInt1)) {
        return false;
      }
      boolean bool;
      switch (paramInt2)
      {
      default: 
        return false;
      case 16908348: 
        nativeShowContextMenu(this.jdField_a_of_type_Long, paramInt1);
        return true;
      case 16908347: 
        return nativeScroll(this.jdField_a_of_type_Long, paramInt1, 5);
      case 16908346: 
        return nativeScroll(this.jdField_a_of_type_Long, paramInt1, 3);
      case 16908345: 
        return nativeScroll(this.jdField_a_of_type_Long, paramInt1, 4);
      case 16908344: 
        return nativeScroll(this.jdField_a_of_type_Long, paramInt1, 2);
      case 16908342: 
        nativeScrollToMakeNodeVisible(this.jdField_a_of_type_Long, paramInt1);
        return true;
      case 2097152: 
        if (!nativeIsEditableText(this.jdField_a_of_type_Long, paramInt1)) {
          return false;
        }
        if (paramBundle == null) {
          return false;
        }
        paramBundle = paramBundle.getString("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE");
        if (paramBundle == null) {
          return false;
        }
        nativeSetTextFieldValue(this.jdField_a_of_type_Long, paramInt1, paramBundle);
        nativeSetSelection(this.jdField_a_of_type_Long, paramInt1, paramBundle.length(), paramBundle.length());
        return true;
      case 262144: 
      case 524288: 
        nativeClick(this.jdField_a_of_type_Long, paramInt1);
        return true;
      case 131072: 
        if (!nativeIsEditableText(this.jdField_a_of_type_Long, paramInt1)) {
          return false;
        }
        int m;
        if (paramBundle != null)
        {
          paramInt2 = paramBundle.getInt("ACTION_ARGUMENT_SELECTION_START_INT");
          m = paramBundle.getInt("ACTION_ARGUMENT_SELECTION_END_INT");
        }
        else
        {
          paramInt2 = 0;
          m = 0;
        }
        nativeSetSelection(this.jdField_a_of_type_Long, paramInt1, paramInt2, m);
        return true;
      case 65536: 
        paramBundle = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl;
        if (paramBundle != null)
        {
          paramBundle.cut();
          return true;
        }
        return false;
      case 32768: 
        paramBundle = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl;
        if (paramBundle != null)
        {
          paramBundle.paste();
          return true;
        }
        return false;
      case 16384: 
        paramBundle = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl;
        if (paramBundle != null)
        {
          paramBundle.copy();
          return true;
        }
        return false;
      case 8192: 
        return c(paramInt1);
      case 4096: 
        return b(paramInt1);
      case 2048: 
        if (paramBundle == null) {
          return false;
        }
        paramBundle = paramBundle.getString("ACTION_ARGUMENT_HTML_ELEMENT_STRING");
        if (paramBundle == null) {
          return false;
        }
        return a(paramInt1, paramBundle.toUpperCase(Locale.US), false);
      case 1024: 
        if (paramBundle == null) {
          return false;
        }
        paramBundle = paramBundle.getString("ACTION_ARGUMENT_HTML_ELEMENT_STRING");
        if (paramBundle == null) {
          return false;
        }
        return a(paramInt1, paramBundle.toUpperCase(Locale.US), true);
      case 512: 
        if (paramBundle == null) {
          return false;
        }
        paramInt2 = paramBundle.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
        bool = paramBundle.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN");
        if (!a(paramInt2)) {
          return false;
        }
        return b(paramInt2, bool, paramInt1);
      case 256: 
        if (paramBundle == null) {
          return false;
        }
        paramInt2 = paramBundle.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
        bool = paramBundle.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN");
        if (!a(paramInt2)) {
          return false;
        }
        return a(paramInt2, bool, paramInt1);
      case 128: 
        a(paramInt1, 65536);
        if (this.jdField_b_of_type_Int == paramInt1)
        {
          this.jdField_b_of_type_Int = -1;
          this.jdField_a_of_type_AndroidGraphicsRect = null;
        }
        return true;
      case 64: 
        if (!d(paramInt1)) {
          return true;
        }
        if (!this.jdField_a_of_type_Boolean)
        {
          nativeScrollToMakeNodeVisible(this.jdField_a_of_type_Long, this.jdField_b_of_type_Int);
          return true;
        }
        this.jdField_d_of_type_Boolean = true;
        return true;
      case 16: 
        nativeClick(this.jdField_a_of_type_Long, paramInt1);
        return true;
      case 2: 
        nativeBlur(this.jdField_a_of_type_Long);
        return true;
      }
      nativeFocus(this.jdField_a_of_type_Long, paramInt1);
      return true;
    }
    return false;
  }
  
  public boolean performAction(int paramInt, Bundle paramBundle)
  {
    return false;
  }
  
  public void refreshState()
  {
    setState(this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isEnabled());
  }
  
  @VisibleForTesting
  public void setAccessibilityEnabledForTesting()
  {
    this.jdField_f_of_type_Boolean = true;
  }
  
  @CalledByNative
  protected void setAccessibilityEventCollectionInfo(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    paramAccessibilityEvent = a(paramAccessibilityEvent);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.CollectionInfo.rowCount", paramInt1);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.CollectionInfo.columnCount", paramInt2);
    paramAccessibilityEvent.putBoolean("AccessibilityNodeInfo.CollectionInfo.hierarchical", paramBoolean);
  }
  
  @CalledByNative
  protected void setAccessibilityEventCollectionItemInfo(AccessibilityEvent paramAccessibilityEvent, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramAccessibilityEvent = a(paramAccessibilityEvent);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.CollectionItemInfo.rowIndex", paramInt1);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.CollectionItemInfo.rowSpan", paramInt2);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.CollectionItemInfo.columnIndex", paramInt3);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.CollectionItemInfo.columnSpan", paramInt4);
  }
  
  @CalledByNative
  protected void setAccessibilityEventHeadingFlag(AccessibilityEvent paramAccessibilityEvent, boolean paramBoolean)
  {
    a(paramAccessibilityEvent).putBoolean("AccessibilityNodeInfo.CollectionItemInfo.heading", paramBoolean);
  }
  
  @CalledByNative
  protected void setAccessibilityEventLollipopAttributes(AccessibilityEvent paramAccessibilityEvent, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt1, int paramInt2)
  {
    paramAccessibilityEvent = a(paramAccessibilityEvent);
    paramAccessibilityEvent.putBoolean("AccessibilityNodeInfo.canOpenPopup", paramBoolean1);
    paramAccessibilityEvent.putBoolean("AccessibilityNodeInfo.contentInvalid", paramBoolean2);
    paramAccessibilityEvent.putBoolean("AccessibilityNodeInfo.dismissable", paramBoolean3);
    paramAccessibilityEvent.putBoolean("AccessibilityNodeInfo.multiLine", paramBoolean4);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.inputType", paramInt1);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.liveRegion", paramInt2);
  }
  
  @CalledByNative
  protected void setAccessibilityEventRangeInfo(AccessibilityEvent paramAccessibilityEvent, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramAccessibilityEvent = a(paramAccessibilityEvent);
    paramAccessibilityEvent.putInt("AccessibilityNodeInfo.RangeInfo.type", paramInt);
    paramAccessibilityEvent.putFloat("AccessibilityNodeInfo.RangeInfo.min", paramFloat1);
    paramAccessibilityEvent.putFloat("AccessibilityNodeInfo.RangeInfo.max", paramFloat2);
    paramAccessibilityEvent.putFloat("AccessibilityNodeInfo.RangeInfo.current", paramFloat3);
  }
  
  @CalledByNative
  protected void setAccessibilityNodeInfoCollectionInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt1, int paramInt2, boolean paramBoolean) {}
  
  @CalledByNative
  protected void setAccessibilityNodeInfoCollectionItemInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {}
  
  @CalledByNative
  protected void setAccessibilityNodeInfoKitKatAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, boolean paramBoolean3) {}
  
  @CalledByNative
  protected void setAccessibilityNodeInfoLollipopAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt1, int paramInt2) {}
  
  @CalledByNative
  protected void setAccessibilityNodeInfoOAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, boolean paramBoolean) {}
  
  @CalledByNative
  protected void setAccessibilityNodeInfoRangeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {}
  
  @CalledByNative
  protected void setAccessibilityNodeInfoViewIdResourceName(AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString) {}
  
  public void setObscuredByAnotherView(boolean paramBoolean)
  {
    if (paramBoolean != this.i)
    {
      this.i = paramBoolean;
      this.jdField_a_of_type_AndroidViewViewGroup.sendAccessibilityEvent(2048);
    }
  }
  
  public void setShouldFocusOnPageLoad(boolean paramBoolean)
  {
    this.h = paramBoolean;
  }
  
  public void setState(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      this.jdField_g_of_type_Boolean = false;
      this.j = false;
      return;
    }
    this.jdField_g_of_type_Boolean = true;
    this.j = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.isTouchExplorationEnabled();
  }
  
  @CalledByNative
  boolean shouldExposePasswordText()
  {
    ContentResolver localContentResolver = this.jdField_a_of_type_AndroidContentContext.getContentResolver();
    int m = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    boolean bool1 = false;
    if (m >= 26)
    {
      if (Settings.System.getInt(localContentResolver, "show_password", 1) == 1) {
        bool1 = true;
      }
      return bool1;
    }
    bool1 = bool2;
    if (Settings.Secure.getInt(localContentResolver, "speak_password", 0) == 1) {
      bool1 = true;
    }
    return bool1;
  }
  
  @CalledByNative
  boolean shouldRespectDisplayedPasswordText()
  {
    return Build.VERSION.SDK_INT >= 26;
  }
  
  public void startAccessibilityIfNecessary()
  {
    if (e()) {
      c();
    }
  }
  
  public boolean supportsAction(int paramInt)
  {
    return false;
  }
  
  private static class Factory
    implements WebContentsUserData.UserDataFactory<WebContentsAccessibilityImpl>
  {
    public WebContentsAccessibilityImpl create(WebContents paramWebContents)
    {
      if (Build.VERSION.SDK_INT >= 26) {
        return new OWebContentsAccessibility(paramWebContents);
      }
      if (Build.VERSION.SDK_INT >= 21) {
        return new LollipopWebContentsAccessibility(paramWebContents);
      }
      if (Build.VERSION.SDK_INT >= 19) {
        return new KitKatWebContentsAccessibility(paramWebContents);
      }
      return new WebContentsAccessibilityImpl(paramWebContents);
    }
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<WebContentsAccessibilityImpl> a = new WebContentsAccessibilityImpl.Factory(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\WebContentsAccessibilityImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */