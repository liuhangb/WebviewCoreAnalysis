package org.chromium.content.browser.accessibility.captioning;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.accessibility.CaptioningManager;
import android.view.accessibility.CaptioningManager.CaptionStyle;
import android.view.accessibility.CaptioningManager.CaptioningChangeListener;
import java.util.Locale;

@TargetApi(19)
public class KitKatCaptioningBridge
  implements SystemCaptioningBridge
{
  private static KitKatCaptioningBridge jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningKitKatCaptioningBridge;
  private final CaptioningManager.CaptioningChangeListener jdField_a_of_type_AndroidViewAccessibilityCaptioningManager$CaptioningChangeListener = new KitKatCaptioningChangeListener(null);
  private final CaptioningManager jdField_a_of_type_AndroidViewAccessibilityCaptioningManager;
  private final CaptioningChangeDelegate jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate = new CaptioningChangeDelegate();
  
  private KitKatCaptioningBridge(Context paramContext)
  {
    this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager = ((CaptioningManager)paramContext.getApplicationContext().getSystemService("captioning"));
  }
  
  private CaptioningStyle a(CaptioningManager.CaptionStyle paramCaptionStyle)
  {
    return CaptioningStyle.createFrom(paramCaptionStyle);
  }
  
  private void a()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.onEnabledChanged(this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager.isEnabled());
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.onFontScaleChanged(this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager.getFontScale());
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.onLocaleChanged(this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager.getLocale());
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.onUserStyleChanged(a(this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager.getUserStyle()));
  }
  
  public static KitKatCaptioningBridge getInstance(Context paramContext)
  {
    if (jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningKitKatCaptioningBridge == null) {
      jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningKitKatCaptioningBridge = new KitKatCaptioningBridge(paramContext);
    }
    return jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningKitKatCaptioningBridge;
  }
  
  public void addListener(SystemCaptioningBridge.SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener)
  {
    if (!this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.hasActiveListener())
    {
      this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager.addCaptioningChangeListener(this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager$CaptioningChangeListener);
      a();
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.addListener(paramSystemCaptioningBridgeListener);
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.notifyListener(paramSystemCaptioningBridgeListener);
  }
  
  public void removeListener(SystemCaptioningBridge.SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.removeListener(paramSystemCaptioningBridgeListener);
    if (!this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.hasActiveListener()) {
      this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager.removeCaptioningChangeListener(this.jdField_a_of_type_AndroidViewAccessibilityCaptioningManager$CaptioningChangeListener);
    }
  }
  
  public void syncToListener(SystemCaptioningBridge.SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener)
  {
    if (!this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.hasActiveListener()) {
      a();
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate.notifyListener(paramSystemCaptioningBridgeListener);
  }
  
  private class KitKatCaptioningChangeListener
    extends CaptioningManager.CaptioningChangeListener
  {
    private KitKatCaptioningChangeListener() {}
    
    public void onEnabledChanged(boolean paramBoolean)
    {
      KitKatCaptioningBridge.a(KitKatCaptioningBridge.this).onEnabledChanged(paramBoolean);
    }
    
    public void onFontScaleChanged(float paramFloat)
    {
      KitKatCaptioningBridge.a(KitKatCaptioningBridge.this).onFontScaleChanged(paramFloat);
    }
    
    public void onLocaleChanged(Locale paramLocale)
    {
      KitKatCaptioningBridge.a(KitKatCaptioningBridge.this).onLocaleChanged(paramLocale);
    }
    
    public void onUserStyleChanged(CaptioningManager.CaptionStyle paramCaptionStyle)
    {
      paramCaptionStyle = KitKatCaptioningBridge.a(KitKatCaptioningBridge.this, paramCaptionStyle);
      KitKatCaptioningBridge.a(KitKatCaptioningBridge.this).onUserStyleChanged(paramCaptionStyle);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\captioning\KitKatCaptioningBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */