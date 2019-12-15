package com.tencent.smtt.webkit.nativewidget;

import android.util.Log;
import android.util.Pair;
import android.webkit.JavascriptInterface;
import com.tencent.smtt.webkit.nativewidget.map.ITencentMap;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class NativeWidgetManager
{
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private Map<Pair<String, String>, INativeWidget> jdField_a_of_type_JavaUtilMap = null;
  
  public NativeWidgetManager(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
  }
  
  @JavascriptInterface
  public ITencentMap createMapContext(String paramString)
  {
    if (this.jdField_a_of_type_JavaUtilMap == null) {
      return null;
    }
    paramString = new Pair("tencentmap", paramString);
    paramString = (INativeWidget)this.jdField_a_of_type_JavaUtilMap.get(paramString);
    if ((paramString != null) && ((paramString instanceof ITencentMap))) {
      return (ITencentMap)paramString;
    }
    return null;
  }
  
  public INativeWidget createNativeWidget(INativeWidgetHost paramINativeWidgetHost, Map<String, String> paramMap)
  {
    String str = "";
    paramINativeWidgetHost = "";
    if (paramMap != null)
    {
      str = (String)paramMap.get("id");
      paramINativeWidgetHost = (String)paramMap.get("widgetname");
    }
    if (paramINativeWidgetHost == null)
    {
      Log.e("native-widget", "createNativeWidget, widgetName is null!");
      return null;
    }
    if (this.jdField_a_of_type_JavaUtilMap == null) {
      this.jdField_a_of_type_JavaUtilMap = new HashMap();
    }
    paramINativeWidgetHost = new Pair(paramINativeWidgetHost, str);
    this.jdField_a_of_type_JavaUtilMap.put(paramINativeWidgetHost, null);
    return null;
  }
  
  public void destroyNativeWidget(Map<String, String> paramMap)
  {
    if (this.jdField_a_of_type_JavaUtilMap == null) {
      return;
    }
    String str1 = "";
    String str2 = "";
    if (paramMap != null)
    {
      str1 = (String)paramMap.get("id");
      str2 = (String)paramMap.get("widgetname");
    }
    paramMap = new Pair(str2, str1);
    this.jdField_a_of_type_JavaUtilMap.remove(paramMap);
  }
  
  public void onAttachedToWindow()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilMap;
    if (localObject == null) {
      return;
    }
    localObject = ((Map)localObject).entrySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      ((INativeWidget)((Map.Entry)((Iterator)localObject).next()).getValue()).onAttachedToWindow();
    }
  }
  
  public void onDetachedFromWindow()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilMap;
    if (localObject == null) {
      return;
    }
    localObject = ((Map)localObject).entrySet().iterator();
    while (((Iterator)localObject).hasNext()) {
      ((INativeWidget)((Map.Entry)((Iterator)localObject).next()).getValue()).onDetachedFromWindow();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\nativewidget\NativeWidgetManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */