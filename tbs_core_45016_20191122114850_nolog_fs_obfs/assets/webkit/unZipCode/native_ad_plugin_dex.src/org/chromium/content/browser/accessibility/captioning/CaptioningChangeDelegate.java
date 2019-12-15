package org.chromium.content.browser.accessibility.captioning;

import android.graphics.Color;
import android.graphics.Typeface;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.chromium.base.VisibleForTesting;

public class CaptioningChangeDelegate
{
  @VisibleForTesting
  public static final String DEFAULT_CAPTIONING_PREF_VALUE = "";
  private String jdField_a_of_type_JavaLangString;
  private final Map<SystemCaptioningBridge.SystemCaptioningBridgeListener, Boolean> jdField_a_of_type_JavaUtilMap = new WeakHashMap();
  private boolean jdField_a_of_type_Boolean;
  private String b;
  private String c;
  private String d;
  private String e;
  private String f;
  private String g;
  
  private void a()
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilMap.keySet().iterator();
    while (localIterator.hasNext()) {
      notifyListener((SystemCaptioningBridge.SystemCaptioningBridgeListener)localIterator.next());
    }
  }
  
  public static String androidColorToCssColor(Integer paramInteger)
  {
    if (paramInteger == null) {
      return "";
    }
    Object localObject = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
    double d1 = Color.alpha(paramInteger.intValue());
    Double.isNaN(d1);
    localObject = ((DecimalFormat)localObject).format(d1 / 255.0D);
    return String.format("rgba(%s, %s, %s, %s)", new Object[] { Integer.valueOf(Color.red(paramInteger.intValue())), Integer.valueOf(Color.green(paramInteger.intValue())), Integer.valueOf(Color.blue(paramInteger.intValue())), localObject });
  }
  
  public static String androidFontScaleToPercentage(float paramFloat)
  {
    return new DecimalFormat("#%", new DecimalFormatSymbols(Locale.US)).format(paramFloat);
  }
  
  public void addListener(SystemCaptioningBridge.SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener)
  {
    this.jdField_a_of_type_JavaUtilMap.put(paramSystemCaptioningBridgeListener, null);
  }
  
  public boolean hasActiveListener()
  {
    return this.jdField_a_of_type_JavaUtilMap.isEmpty() ^ true;
  }
  
  public void notifyListener(SystemCaptioningBridge.SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener)
  {
    boolean bool = this.jdField_a_of_type_Boolean;
    if (bool)
    {
      paramSystemCaptioningBridgeListener.onSystemCaptioningChanged(new TextTrackSettings(bool, this.jdField_a_of_type_JavaLangString, this.b, this.c, this.d, this.e, this.f, this.g));
      return;
    }
    paramSystemCaptioningBridgeListener.onSystemCaptioningChanged(new TextTrackSettings());
  }
  
  public void onEnabledChanged(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
    a();
  }
  
  public void onFontScaleChanged(float paramFloat)
  {
    this.g = androidFontScaleToPercentage(paramFloat);
    a();
  }
  
  public void onLocaleChanged(Locale paramLocale) {}
  
  public void onUserStyleChanged(CaptioningStyle paramCaptioningStyle)
  {
    this.e = androidColorToCssColor(paramCaptioningStyle.getForegroundColor());
    this.jdField_a_of_type_JavaLangString = androidColorToCssColor(paramCaptioningStyle.getBackgroundColor());
    this.f = ClosedCaptionEdgeAttribute.fromSystemEdgeAttribute(paramCaptioningStyle.getEdgeType(), androidColorToCssColor(paramCaptioningStyle.getEdgeColor())).getTextShadow();
    paramCaptioningStyle = paramCaptioningStyle.getTypeface();
    this.b = ClosedCaptionFont.fromSystemFont(paramCaptioningStyle).getFontFamily();
    if ((paramCaptioningStyle != null) && (paramCaptioningStyle.isItalic())) {
      this.c = "italic";
    } else {
      this.c = "";
    }
    this.d = "";
    a();
  }
  
  public void removeListener(SystemCaptioningBridge.SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener)
  {
    this.jdField_a_of_type_JavaUtilMap.remove(paramSystemCaptioningBridgeListener);
  }
  
  public static enum ClosedCaptionEdgeAttribute
  {
    private static String jdField_a_of_type_JavaLangString = "silver";
    private static String b = "0.05em";
    private static String c;
    private final String d;
    
    static
    {
      DROP_SHADOW = new ClosedCaptionEdgeAttribute("DROP_SHADOW", 2, "%1$s %2$s %2$s 0.1em");
      RAISED = new ClosedCaptionEdgeAttribute("RAISED", 3, "-%2$s -%2$s 0 %1$s");
      DEPRESSED = new ClosedCaptionEdgeAttribute("DEPRESSED", 4, "%2$s %2$s 0 %1$s");
      jdField_a_of_type_ArrayOfOrgChromiumContentBrowserAccessibilityCaptioningCaptioningChangeDelegate$ClosedCaptionEdgeAttribute = new ClosedCaptionEdgeAttribute[] { NONE, OUTLINE, DROP_SHADOW, RAISED, DEPRESSED };
    }
    
    private ClosedCaptionEdgeAttribute(String paramString)
    {
      this.d = paramString;
    }
    
    public static ClosedCaptionEdgeAttribute fromSystemEdgeAttribute(Integer paramInteger, String paramString)
    {
      if (paramInteger == null) {
        return NONE;
      }
      if ((paramString != null) && (!paramString.isEmpty())) {
        c = paramString;
      } else {
        c = jdField_a_of_type_JavaLangString;
      }
      switch (paramInteger.intValue())
      {
      default: 
        return NONE;
      case 4: 
        return DEPRESSED;
      case 3: 
        return RAISED;
      case 2: 
        return DROP_SHADOW;
      }
      return OUTLINE;
    }
    
    public static void setDefaultEdgeColor(String paramString)
    {
      jdField_a_of_type_JavaLangString = paramString;
    }
    
    public static void setShadowOffset(String paramString)
    {
      b = paramString;
    }
    
    public String getTextShadow()
    {
      return String.format(this.d, new Object[] { c, b });
    }
  }
  
  public static enum ClosedCaptionFont
  {
    private final String jdField_a_of_type_JavaLangString;
    @VisibleForTesting
    final EnumSet<Flags> jdField_a_of_type_JavaUtilEnumSet;
    
    static
    {
      CASUAL = new ClosedCaptionFont("CASUAL", 6, "casual", EnumSet.noneOf(Flags.class));
      CURSIVE = new ClosedCaptionFont("CURSIVE", 7, "cursive", EnumSet.noneOf(Flags.class));
      SANS_SERIF_SMALLCAPS = new ClosedCaptionFont("SANS_SERIF_SMALLCAPS", 8, "sans-serif-smallcaps", EnumSet.of(Flags.SANS_SERIF));
    }
    
    private ClosedCaptionFont(String paramString, EnumSet<Flags> paramEnumSet)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_JavaUtilEnumSet = paramEnumSet;
    }
    
    private static boolean a(Typeface paramTypeface, ClosedCaptionFont paramClosedCaptionFont)
    {
      return Typeface.create(paramClosedCaptionFont.getFontFamily(), paramTypeface.getStyle()).equals(paramTypeface);
    }
    
    public static ClosedCaptionFont fromSystemFont(Typeface paramTypeface)
    {
      if (paramTypeface == null) {
        return DEFAULT;
      }
      ClosedCaptionFont[] arrayOfClosedCaptionFont = values();
      int j = arrayOfClosedCaptionFont.length;
      int i = 0;
      while (i < j)
      {
        ClosedCaptionFont localClosedCaptionFont = arrayOfClosedCaptionFont[i];
        if (a(paramTypeface, localClosedCaptionFont)) {
          return localClosedCaptionFont;
        }
        i += 1;
      }
      return DEFAULT;
    }
    
    public String getFontFamily()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    @VisibleForTesting
    static enum Flags
    {
      private Flags() {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\captioning\CaptioningChangeDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */