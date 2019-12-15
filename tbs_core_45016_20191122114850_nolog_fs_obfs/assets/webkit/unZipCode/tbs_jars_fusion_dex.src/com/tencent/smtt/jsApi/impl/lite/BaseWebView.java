package com.tencent.smtt.jsApi.impl.lite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import com.tencent.common.http.IPostDataBuf;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.smtt.jsApi.impl.LiteJsHelper;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.UserInfo;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.QBUrlUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"NewApi"})
public class BaseWebView
  extends WebView
{
  public static final int NIGHT_MODE_ALPHA = 153;
  private int jdField_a_of_type_Int;
  private Context jdField_a_of_type_AndroidContentContext;
  private Paint jdField_a_of_type_AndroidGraphicsPaint;
  protected LiteJsHelper a;
  protected String a;
  private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap;
  protected boolean a;
  private boolean b = true;
  
  public BaseWebView(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public BaseWebView(Context paramContext, boolean paramBoolean)
  {
    super(paramContext, null, 0, paramBoolean);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  private HashMap<String, String> a(String paramString)
  {
    HashMap localHashMap = new HashMap();
    a(paramString, localHashMap);
    return localHashMap;
  }
  
  private void a(String paramString, Map<String, String> paramMap)
  {
    if (!URLUtil.isNetworkUrl(paramString)) {
      return;
    }
    TbsInfoUtils.getQUA();
    paramMap.put("Q-UA2", TbsInfoUtils.getQUA2());
    if (QBUrlUtils.isQQDomain(paramString, false))
    {
      paramString = GUIDFactory.getInstance().getStrGuid();
      if (!StringUtils.isEmpty(paramString))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Q-GUID : ");
        localStringBuilder.append(paramString);
        LogUtils.d("WebView", localStringBuilder.toString());
        paramMap.put("Q-GUID", paramString);
      }
      paramString = UserInfo.getInstance().getQAuth();
      if (!StringUtils.isEmpty(paramString)) {
        paramMap.put("Q-Auth", paramString);
      }
    }
  }
  
  protected Rect a()
  {
    return null;
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_Boolean = true;
    Object localObject = getParent();
    if ((localObject != null) && ((localObject instanceof ViewGroup))) {
      ((ViewGroup)localObject).removeView(this);
    }
    localObject = this.jdField_a_of_type_ComTencentSmttJsApiImplLiteJsHelper;
    if (localObject != null) {
      ((LiteJsHelper)localObject).onWebViewDestroy();
    }
    super.destroy();
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    try
    {
      super.dispatchDraw(paramCanvas);
    }
    catch (Exception localException)
    {
      LogUtils.e("WebView", localException);
    }
    if ((this.b) && (this.jdField_a_of_type_AndroidGraphicsPaint.getAlpha() != 255))
    {
      paramCanvas.save();
      Rect localRect = a();
      if (localRect != null) {
        paramCanvas.clipRect(localRect);
      }
      paramCanvas.drawPaint(this.jdField_a_of_type_AndroidGraphicsPaint);
      paramCanvas.restore();
    }
  }
  
  public LiteJsHelper getJsHelper()
  {
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplLiteJsHelper == null) {
      this.jdField_a_of_type_ComTencentSmttJsApiImplLiteJsHelper = new LiteJsHelper(this, this.jdField_a_of_type_AndroidContentContext, getContext());
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplLiteJsHelper;
  }
  
  public HashMap<String, String> getMetaData()
  {
    return this.jdField_a_of_type_JavaUtilHashMap;
  }
  
  public String getOrigUrlUnSafe()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public String getUrl()
  {
    return getUrlUnSafe();
  }
  
  public String getUrlUnSafe()
  {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      return this.jdField_a_of_type_JavaLangString;
    }
    String str2 = super.getUrl();
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      str1 = this.jdField_a_of_type_JavaLangString;
    }
    return str1;
  }
  
  public void loadUrl(String paramString)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    if (!URLUtil.isJavaScriptUrl(paramString)) {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    if ((URLUtil.isNetworkUrl(paramString)) && (DeviceUtils.getSdkVersion() >= 8))
    {
      super.loadUrl(paramString, a(paramString));
      return;
    }
    super.loadUrl(paramString);
  }
  
  public void loadUrlCompat(String paramString, Map<String, String> paramMap)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    if (URLUtil.isNetworkUrl(paramString))
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      a(paramString, paramMap);
    }
    super.loadUrl(paramString, paramMap);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!hasFocus()) {
      requestFocus();
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void postUrl(String paramString, IPostDataBuf paramIPostDataBuf)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    if (DeviceUtils.getSdkVersion() < 5)
    {
      loadUrl(paramString);
      return;
    }
    postUrl(paramString, paramIPostDataBuf.toByteArray());
  }
  
  public void setJsHelper(LiteJsHelper paramLiteJsHelper)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplLiteJsHelper = paramLiteJsHelper;
  }
  
  public boolean setMetaData(HashMap<String, String> paramHashMap)
  {
    this.jdField_a_of_type_Int -= 1;
    if (this.jdField_a_of_type_Int > 0) {
      return false;
    }
    this.jdField_a_of_type_JavaUtilHashMap = paramHashMap;
    return true;
  }
  
  public void setNightModeEnabled(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public void setOrigUrl(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramString.equals(this.jdField_a_of_type_JavaLangString)) {
        return;
      }
      this.jdField_a_of_type_JavaLangString = paramString;
      return;
    }
  }
  
  public void startLoadMetaData()
  {
    this.jdField_a_of_type_Int += 1;
    this.jdField_a_of_type_JavaUtilHashMap = null;
    loadUrl("javascript:(function(){var m=document.getElementsByTagName('meta'); var rs=[]; for(i=0;i<m.length;i++){if(m[i].name.indexOf('x5')==-1)continue;var data = new Object(); data.name=m[i].name;data.content=m[i].content;rs.push(data)} if(rs.length>0)prompt(JSON.stringify(rs), 'mtt:[\"meta\", \"onFound\"]');}.call())");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\lite\BaseWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */