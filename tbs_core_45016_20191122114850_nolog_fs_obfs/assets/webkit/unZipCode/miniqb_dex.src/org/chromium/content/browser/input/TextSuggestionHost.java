package org.chromium.content.browser.input;

import android.content.Context;
import android.view.View;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.WindowAndroidChangedObserver;
import org.chromium.content.browser.WindowEventObserver;
import org.chromium.content.browser.webcontents.WebContentsImpl;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.WebContents;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("content")
public class TextSuggestionHost
  implements WindowAndroidChangedObserver, WindowEventObserver
{
  private long jdField_a_of_type_Long;
  private Context jdField_a_of_type_AndroidContentContext;
  private View jdField_a_of_type_AndroidViewView;
  private SpellCheckPopupWindow jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow;
  private TextSuggestionsPopupWindow jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow;
  private final WebContentsImpl jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl;
  private WindowAndroid jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
  private boolean b;
  private boolean c;
  
  public TextSuggestionHost(WebContents paramWebContents)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl = ((WebContentsImpl)paramWebContents);
  }
  
  private float a()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl.getRenderCoordinates().getContentOffsetYPix();
  }
  
  private void a(Context paramContext, WindowAndroid paramWindowAndroid, View paramView)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = paramWindowAndroid;
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_Long = nativeInit(this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl);
    this.c = true;
  }
  
  private boolean a()
  {
    return this.c;
  }
  
  public static TextSuggestionHost create(Context paramContext, WebContents paramWebContents, WindowAndroid paramWindowAndroid, View paramView)
  {
    paramWebContents = (TextSuggestionHost)WebContentsUserData.fromWebContents(paramWebContents, TextSuggestionHost.class, UserDataFactoryLazyHolder.a());
    if ((!jdField_a_of_type_Boolean) && (paramWebContents == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (paramWebContents.a())) {
      throw new AssertionError();
    }
    paramWebContents.a(paramContext, paramWindowAndroid, paramView);
    return paramWebContents;
  }
  
  @CalledByNative
  private void destroy()
  {
    hidePopups();
    this.jdField_a_of_type_Long = 0L;
  }
  
  public static TextSuggestionHost fromWebContents(WebContents paramWebContents)
  {
    return (TextSuggestionHost)WebContentsUserData.fromWebContents(paramWebContents, TextSuggestionHost.class, null);
  }
  
  private native void nativeApplySpellCheckSuggestion(long paramLong, String paramString);
  
  private native void nativeApplyTextSuggestion(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeDeleteActiveSuggestionRange(long paramLong);
  
  private native long nativeInit(WebContents paramWebContents);
  
  private native void nativeOnNewWordAddedToDictionary(long paramLong, String paramString);
  
  private native void nativeOnSuggestionMenuClosed(long paramLong);
  
  @CalledByNative
  private void showSpellCheckSuggestionMenu(double paramDouble1, double paramDouble2, String paramString, String[] paramArrayOfString)
  {
    if (!this.b)
    {
      onSuggestionMenuClosed(false);
      return;
    }
    hidePopups();
    this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow = new SpellCheckPopupWindow(this.jdField_a_of_type_AndroidContentContext, this, this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid, this.jdField_a_of_type_AndroidViewView);
    SpellCheckPopupWindow localSpellCheckPopupWindow = this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow;
    double d = a();
    Double.isNaN(d);
    localSpellCheckPopupWindow.show(paramDouble1, paramDouble2 + d, paramString, paramArrayOfString);
  }
  
  @CalledByNative
  private void showTextSuggestionMenu(double paramDouble1, double paramDouble2, String paramString, SuggestionInfo[] paramArrayOfSuggestionInfo)
  {
    if (!this.b)
    {
      onSuggestionMenuClosed(false);
      return;
    }
    hidePopups();
    this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow = new TextSuggestionsPopupWindow(this.jdField_a_of_type_AndroidContentContext, this, this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid, this.jdField_a_of_type_AndroidViewView);
    TextSuggestionsPopupWindow localTextSuggestionsPopupWindow = this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow;
    double d = a();
    Double.isNaN(d);
    localTextSuggestionsPopupWindow.show(paramDouble1, paramDouble2 + d, paramString, paramArrayOfSuggestionInfo);
  }
  
  public void applySpellCheckSuggestion(String paramString)
  {
    nativeApplySpellCheckSuggestion(this.jdField_a_of_type_Long, paramString);
  }
  
  public void applyTextSuggestion(int paramInt1, int paramInt2)
  {
    nativeApplyTextSuggestion(this.jdField_a_of_type_Long, paramInt1, paramInt2);
  }
  
  public void deleteActiveSuggestionRange()
  {
    nativeDeleteActiveSuggestionRange(this.jdField_a_of_type_Long);
  }
  
  @VisibleForTesting
  public SuggestionsPopupWindow getSpellCheckPopupWindowForTesting()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow;
  }
  
  @VisibleForTesting
  public SuggestionsPopupWindow getTextSuggestionsPopupWindowForTesting()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow;
  }
  
  @CalledByNative
  public void hidePopups()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow;
    if ((localObject != null) && (((TextSuggestionsPopupWindow)localObject).isShowing()))
    {
      this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow.dismiss();
      this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow = null;
    }
    localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow;
    if ((localObject != null) && (((SpellCheckPopupWindow)localObject).isShowing()))
    {
      this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow.dismiss();
      this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow = null;
    }
  }
  
  public void onAttachedToWindow()
  {
    this.b = true;
  }
  
  public void onDetachedFromWindow()
  {
    this.b = false;
  }
  
  public void onNewWordAddedToDictionary(String paramString)
  {
    nativeOnNewWordAddedToDictionary(this.jdField_a_of_type_Long, paramString);
  }
  
  public void onSuggestionMenuClosed(boolean paramBoolean)
  {
    if (!paramBoolean) {
      nativeOnSuggestionMenuClosed(this.jdField_a_of_type_Long);
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow = null;
    this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow = null;
  }
  
  public void onWindowAndroidChanged(WindowAndroid paramWindowAndroid)
  {
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = paramWindowAndroid;
    paramWindowAndroid = this.jdField_a_of_type_OrgChromiumContentBrowserInputSpellCheckPopupWindow;
    if (paramWindowAndroid != null) {
      paramWindowAndroid.updateWindowAndroid(this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid);
    }
    paramWindowAndroid = this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionsPopupWindow;
    if (paramWindowAndroid != null) {
      paramWindowAndroid.updateWindowAndroid(this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid);
    }
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {}
  
  public void setContainerView(View paramView)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<TextSuggestionHost> a = new WebContentsUserData.UserDataFactory()
    {
      public TextSuggestionHost create(WebContents paramAnonymousWebContents)
      {
        return new TextSuggestionHost(paramAnonymousWebContents);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\TextSuggestionHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */