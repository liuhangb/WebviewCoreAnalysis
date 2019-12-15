package org.chromium.ui.base;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;
import android.text.style.UpdateAppearance;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.metrics.RecordUserAction;
import org.chromium.tencent.ui.base.PartnerClipboardProxy;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("ui")
public class Clipboard
  implements ClipboardManager.OnPrimaryClipChangedListener
{
  @SuppressLint({"StaticFieldLeak"})
  private static Clipboard jdField_a_of_type_OrgChromiumUiBaseClipboard;
  private final ClipboardManager jdField_a_of_type_AndroidContentClipboardManager = (ClipboardManager)ContextUtils.getApplicationContext().getSystemService("clipboard");
  private final Context jdField_a_of_type_AndroidContentContext = ContextUtils.getApplicationContext();
  
  private Clipboard()
  {
    this.jdField_a_of_type_AndroidContentClipboardManager.addPrimaryClipChangedListener(this);
  }
  
  private static boolean a()
  {
    return X5ApiCompatibilityUtils.isHTMLClipboardSupported();
  }
  
  private boolean a(Spanned paramSpanned)
  {
    Class[] arrayOfClass = new Class[3];
    arrayOfClass[0] = CharacterStyle.class;
    arrayOfClass[1] = ParagraphStyle.class;
    arrayOfClass[2] = UpdateAppearance.class;
    int j = arrayOfClass.length;
    int i = 0;
    while (i < j)
    {
      Class localClass = arrayOfClass[i];
      if (paramSpanned.nextSpanTransition(-1, paramSpanned.length(), localClass) < paramSpanned.length()) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  @CalledByNative
  private void clear()
  {
    if (PartnerClipboardProxy.getInstance().isListenerSeted())
    {
      PartnerClipboardProxy.getInstance().clear(this.jdField_a_of_type_AndroidContentContext);
      return;
    }
    setPrimaryClipNoException(ClipData.newPlainText(null, null));
  }
  
  @CalledByNative
  private String getCoercedText()
  {
    try
    {
      if (PartnerClipboardProxy.getInstance().isListenerSeted()) {
        return PartnerClipboardProxy.getInstance().getCoercedText(this.jdField_a_of_type_AndroidContentContext);
      }
      String str = this.jdField_a_of_type_AndroidContentClipboardManager.getPrimaryClip().getItemAt(0).coerceToText(this.jdField_a_of_type_AndroidContentContext).toString();
      return str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return null;
  }
  
  @CalledByNative
  private String getHTMLText()
  {
    try
    {
      String str = clipDataToHtmlText(this.jdField_a_of_type_AndroidContentClipboardManager.getPrimaryClip());
      return str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return null;
  }
  
  @CalledByNative
  public static Clipboard getInstance()
  {
    if (jdField_a_of_type_OrgChromiumUiBaseClipboard == null) {
      jdField_a_of_type_OrgChromiumUiBaseClipboard = new Clipboard();
    }
    return jdField_a_of_type_OrgChromiumUiBaseClipboard;
  }
  
  private native long nativeInit();
  
  private native void nativeOnPrimaryClipChanged(long paramLong);
  
  @CalledByNative
  private void setHTMLText(String paramString1, String paramString2)
  {
    if (a())
    {
      setPrimaryClipNoException(ClipData.newHtmlText("html", paramString2, paramString1));
      return;
    }
    setText(paramString2);
  }
  
  public String clipDataToHtmlText(ClipData paramClipData)
  {
    ClipDescription localClipDescription = paramClipData.getDescription();
    if (localClipDescription.hasMimeType("text/html")) {
      return paramClipData.getItemAt(0).getHtmlText();
    }
    if (localClipDescription.hasMimeType("text/plain"))
    {
      paramClipData = paramClipData.getItemAt(0).getText();
      if (!(paramClipData instanceof Spanned)) {
        return null;
      }
      paramClipData = (Spanned)paramClipData;
      if (a(paramClipData)) {
        return ApiCompatibilityUtils.toHtml(paramClipData, 0);
      }
    }
    return null;
  }
  
  public void onPrimaryClipChanged()
  {
    RecordUserAction.record("MobileClipboardChanged");
    long l = nativeInit();
    if (l != 0L) {
      nativeOnPrimaryClipChanged(l);
    }
  }
  
  public void setPrimaryClipNoException(ClipData paramClipData)
  {
    try
    {
      this.jdField_a_of_type_AndroidContentClipboardManager.setPrimaryClip(paramClipData);
      return;
    }
    catch (Exception paramClipData) {}
  }
  
  @CalledByNative
  public void setText(String paramString)
  {
    if (PartnerClipboardProxy.getInstance().isListenerSeted())
    {
      PartnerClipboardProxy.getInstance().setText(this.jdField_a_of_type_AndroidContentContext, paramString);
      return;
    }
    setPrimaryClipNoException(ClipData.newPlainText("text", paramString));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\Clipboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */