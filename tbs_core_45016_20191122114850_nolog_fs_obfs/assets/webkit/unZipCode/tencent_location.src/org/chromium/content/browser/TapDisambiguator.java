package org.chromium.content.browser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.ImeEventObserver;
import org.chromium.content_public.browser.WebContents;

@JNINamespace("content")
public class TapDisambiguator
  implements ImeEventObserver
{
  private long jdField_a_of_type_Long;
  private PopupZoomer jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer;
  private final WebContents jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents;
  private boolean b;
  
  public TapDisambiguator(WebContents paramWebContents)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents = paramWebContents;
  }
  
  private void a(Context paramContext, final ViewGroup paramViewGroup)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer = new PopupZoomer(paramContext, paramViewGroup, new PopupZoomer.OnVisibilityChangedListener()new PopupZoomer.OnTapListener
    {
      public void onPopupZoomerHidden(final PopupZoomer paramAnonymousPopupZoomer)
      {
        paramViewGroup.post(new Runnable()
        {
          public void run()
          {
            if (TapDisambiguator.1.this.a.indexOfChild(paramAnonymousPopupZoomer) != -1)
            {
              TapDisambiguator.1.this.a.removeView(paramAnonymousPopupZoomer);
              TapDisambiguator.1.this.a.invalidate();
            }
          }
        });
      }
      
      public void onPopupZoomerShown(final PopupZoomer paramAnonymousPopupZoomer)
      {
        paramViewGroup.post(new Runnable()
        {
          public void run()
          {
            if (TapDisambiguator.1.this.a.indexOfChild(paramAnonymousPopupZoomer) == -1) {
              TapDisambiguator.1.this.a.addView(paramAnonymousPopupZoomer);
            }
          }
        });
      }
    }, new PopupZoomer.OnTapListener()
    {
      public void onResolveTapDisambiguation(long paramAnonymousLong, float paramAnonymousFloat1, float paramAnonymousFloat2, boolean paramAnonymousBoolean)
      {
        if (TapDisambiguator.a(TapDisambiguator.this) == 0L) {
          return;
        }
        paramViewGroup.requestFocus();
        TapDisambiguator localTapDisambiguator = TapDisambiguator.this;
        TapDisambiguator.a(localTapDisambiguator, TapDisambiguator.a(localTapDisambiguator), paramAnonymousLong, paramAnonymousFloat1, paramAnonymousFloat2, paramAnonymousBoolean);
      }
    });
    this.jdField_a_of_type_Long = nativeInit(this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents);
    this.b = true;
  }
  
  private boolean a()
  {
    return this.b;
  }
  
  public static TapDisambiguator create(Context paramContext, WebContents paramWebContents, ViewGroup paramViewGroup)
  {
    paramWebContents = (TapDisambiguator)WebContentsUserData.fromWebContents(paramWebContents, TapDisambiguator.class, UserDataFactoryLazyHolder.a());
    if ((!jdField_a_of_type_Boolean) && (paramWebContents == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (paramWebContents.a())) {
      throw new AssertionError();
    }
    paramWebContents.a(paramContext, paramViewGroup);
    return paramWebContents;
  }
  
  @CalledByNative
  private static Rect createRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return new Rect(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @CalledByNative
  private void destroy()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  public static TapDisambiguator fromWebContents(WebContents paramWebContents)
  {
    return (TapDisambiguator)WebContentsUserData.fromWebContents(paramWebContents, TapDisambiguator.class, null);
  }
  
  @CalledByNative
  private void hidePopup()
  {
    hidePopup(false);
  }
  
  private native long nativeInit(WebContents paramWebContents);
  
  private native void nativeResolveTapDisambiguation(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  @CalledByNative
  private void showPopup(Rect paramRect, Bitmap paramBitmap)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer.a(paramBitmap);
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer.a(paramRect);
  }
  
  public void backButtonPressed()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer.backButtonPressed();
  }
  
  public void hidePopup(boolean paramBoolean)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer.hide(paramBoolean);
  }
  
  public boolean isPopupZoomerView(View paramView)
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer == paramView;
  }
  
  public boolean isShowing()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer.isShowing();
  }
  
  public void onBeforeSendKeyEvent(KeyEvent paramKeyEvent) {}
  
  public void onImeEvent()
  {
    hidePopup(true);
  }
  
  public void onNodeAttributeUpdated(boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void setLastTouch(float paramFloat1, float paramFloat2)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer.setLastTouch(paramFloat1, paramFloat2);
  }
  
  public void showPopup(Rect paramRect)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserPopupZoomer.a(paramRect);
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<TapDisambiguator> a = new WebContentsUserData.UserDataFactory()
    {
      public TapDisambiguator create(WebContents paramAnonymousWebContents)
      {
        return new TapDisambiguator(paramAnonymousWebContents);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\TapDisambiguator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */