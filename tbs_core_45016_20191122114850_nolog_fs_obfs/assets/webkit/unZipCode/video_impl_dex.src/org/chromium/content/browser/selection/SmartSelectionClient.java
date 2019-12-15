package org.chromium.content.browser.selection;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.view.textclassifier.TextClassifier;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.SelectionClient;
import org.chromium.content_public.browser.SelectionClient.Result;
import org.chromium.content_public.browser.SelectionClient.ResultCallback;
import org.chromium.content_public.browser.SelectionMetricsLogger;
import org.chromium.content_public.browser.WebContents;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("content")
public class SmartSelectionClient
  extends SelectionClient
{
  private long jdField_a_of_type_Long;
  private SmartSelectionMetricsLogger jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger;
  private SmartSelectionProvider jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider;
  private SelectionClient.ResultCallback jdField_a_of_type_OrgChromiumContent_publicBrowserSelectionClient$ResultCallback;
  
  private SmartSelectionClient(SelectionClient.ResultCallback paramResultCallback, WebContents paramWebContents, WindowAndroid paramWindowAndroid)
  {
    if ((!jdField_a_of_type_Boolean) && (Build.VERSION.SDK_INT < 26)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider = new SmartSelectionProvider(paramResultCallback, paramWindowAndroid);
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserSelectionClient$ResultCallback = paramResultCallback;
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger = SmartSelectionMetricsLogger.create((Context)paramWindowAndroid.getContext().get());
    this.jdField_a_of_type_Long = nativeInit(paramWebContents);
  }
  
  private void a(int paramInt)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L)
    {
      onSurroundingTextReceived(paramInt, "", 0, 0);
      return;
    }
    nativeRequestSurroundingText(l, 240, paramInt);
  }
  
  private boolean a(String paramString, int paramInt1, int paramInt2)
  {
    return (!TextUtils.isEmpty(paramString)) && (paramInt1 >= 0) && (paramInt1 < paramInt2) && (paramInt2 <= paramString.length());
  }
  
  public static SmartSelectionClient create(SelectionClient.ResultCallback paramResultCallback, WebContents paramWebContents)
  {
    WindowAndroid localWindowAndroid = paramWebContents.getTopLevelNativeWindow();
    if ((Build.VERSION.SDK_INT >= 26) && (localWindowAndroid != null)) {
      return new SmartSelectionClient(paramResultCallback, paramWebContents, localWindowAndroid);
    }
    return null;
  }
  
  private native void nativeCancelAllRequests(long paramLong);
  
  private native long nativeInit(WebContents paramWebContents);
  
  private native void nativeRequestSurroundingText(long paramLong, int paramInt1, int paramInt2);
  
  @CalledByNative
  private void onNativeSideDestroyed(long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (paramLong != this.jdField_a_of_type_Long)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Long = 0L;
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider.cancelAllRequests();
  }
  
  @CalledByNative
  private void onSurroundingTextReceived(int paramInt1, String paramString, int paramInt2, int paramInt3)
  {
    if (!a(paramString, paramInt2, paramInt3))
    {
      this.jdField_a_of_type_OrgChromiumContent_publicBrowserSelectionClient$ResultCallback.onClassified(new SelectionClient.Result());
      return;
    }
    switch (paramInt1)
    {
    default: 
      if (jdField_a_of_type_Boolean) {
        return;
      }
      break;
    case 1: 
      this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider.sendSuggestAndClassifyRequest(paramString, paramInt2, paramInt3, null);
      return;
    case 0: 
      this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider.sendClassifyRequest(paramString, paramInt2, paramInt3, null);
      return;
    }
    throw new AssertionError("Unexpected callback data");
  }
  
  public void cancelAllRequests()
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeCancelAllRequests(l);
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider.cancelAllRequests();
  }
  
  public TextClassifier getCustomTextClassifier()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider.getCustomTextClassifier();
  }
  
  public SelectionMetricsLogger getSelectionMetricsLogger()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionMetricsLogger;
  }
  
  public TextClassifier getTextClassifier()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider.getTextClassifier();
  }
  
  public void onSelectionChanged(String paramString) {}
  
  public void onSelectionEvent(int paramInt, float paramFloat1, float paramFloat2) {}
  
  public boolean requestSelectionPopupUpdates(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void selectWordAroundCaretAck(boolean paramBoolean, int paramInt1, int paramInt2) {}
  
  public void setTextClassifier(TextClassifier paramTextClassifier)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionSmartSelectionProvider.setTextClassifier(paramTextClassifier);
  }
  
  public void showUnhandledTapUIIfNeeded(int paramInt1, int paramInt2) {}
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L})
  private static @interface RequestType {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\SmartSelectionClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */