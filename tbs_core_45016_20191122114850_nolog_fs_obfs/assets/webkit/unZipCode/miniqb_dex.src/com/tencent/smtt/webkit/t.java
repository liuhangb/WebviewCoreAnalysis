package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class t
{
  private final float jdField_a_of_type_Float = 1.0F;
  private int jdField_a_of_type_Int = 0;
  private TextView jdField_a_of_type_AndroidWidgetTextView = null;
  private WebViewChromiumExtension jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension = null;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
  private String jdField_a_of_type_JavaLangString = "";
  private boolean jdField_a_of_type_Boolean = false;
  private final int jdField_b_of_type_Int = 144;
  private String jdField_b_of_type_JavaLangString = null;
  private final int jdField_c_of_type_Int = 32;
  private String jdField_c_of_type_JavaLangString = null;
  private final int jdField_d_of_type_Int = 24;
  private String jdField_d_of_type_JavaLangString = null;
  private String e = null;
  private String f = null;
  private String g = null;
  private String h = "function findNodeInArray(arr, value) {\n    for (var i = 0; i < arr.length; i++)if (value === arr[i])return true;\n    return false;\n}\nfunction findIndexInArray(arr, value) {\n    for (var i = 0; i < arr.length; i++)if (value === arr[i])return i;\n    return -1;\n}\nfunction isNodeValid(node) {\n    var str = node.nodeValue;\n    str = str.replace(/[ | ]*\\n/g, '\\n');\n    str = str.replace(/\\n[\\s| | ]*\\r/g, '\\n');\n    str = str.replace(/ /ig, '');\n    str = str.replace(/^[\\s　]+|[\\s　]+$/g, \"\");\n    str = str.replace(/[\\r\\n]/g, \"\");\n    return str.length > 0;\n}\nfunction getForeignText() {\n    var b, c, d, e, a = document.createTreeWalker(document.getElementsByTagName('html')[0], NodeFilter.SHOW_TEXT, null, !1);\n    for (mttTextNodes = [], c = 0, d = {script: 1, style: 1, noscript: 1, embed: 1, object: 1}; b = a.nextNode();) {\n        e = b.parentNode.nodeName.toLowerCase(),\n        !d[e] && isNodeValid(b) && !findNodeInArray(mttTranslatedTextNodes, b) && (mttTextNodes.push(b),\n            mttOriginStr = 0 == c ? b.nodeValue.trim() : mttOriginStr.concat(mttSeparator + b.nodeValue.trim()), c++);\n    }\n    return mttOriginStr;\n}\nfunction switchToTranslatedLan() {\n    var b, c;\n    if (!(mttTranslatedStr.length <= 0)) {\n        b = mttTranslatedStr.split(mttSeparator);\n        for (c = 0; c < b.length && c < mttTextNodes.length; c++) {\n            mttTextNodes[c].nodeValue = b[c];\n            mttTranslatedTextNodes.push(mttTextNodes[c]);\n        }\n    }\n}\nfunction registerNodeInsertListener() {\n    document.addEventListener(\"DOMNodeInserted\", function (e) {\n        if (lastTimerId > 0)\n            clearTimeout(lastTimerId);\n        lastTimerId = setTimeout(translatePage, translateFireTimeout);\n    });\n}\nfunction translatePage() {\n    nodeValueChangeObserver.disconnect();\n    getForeignText();\n    mttTranslatedStr = wxTranslateHelper.getTranslateResult(mttOriginStr);\n    switchToTranslatedLan();\n    mttTextNodes = [];\n    mttOriginStr = '';\n    mttTranslatedStr = '';\n    lastTimerId = 0;\n    if (!hasRegisterInsertListener) {\n        registerNodeInsertListener();\n        hasRegisterInsertListener = true;\n    }\n    for (var j = 0; j < mttTranslatedTextNodes.length; j++) {\n        nodeValueChangeObserver.observe(mttTranslatedTextNodes[j], nodeValueChangeConfig);\n    }\n}\nvar mttTextNodes = [], mttTranslatedTextNodes = [], mttSeparator = '〖〗', mttOriginStr = '', mttTranslatedStr = '', lastTimerId = 0, translateFireTimeout = 1000, hasRegisterInsertListener = false;\n\nvar nodeValueChangeObserver = new MutationObserver(function (mutations) {\n    var tmpIndexArr = [], tmpArr = [];\n    for (var i = 0; i < mutations.length; i++) {\n        if (mutations[i].oldValue != mutations[i].target.nodeValue) {\n            var index = findIndexInArray(mttTranslatedTextNodes, mutations[i].target);\n            if (index >= 0) {\n                tmpIndexArr.push(index);\n            }\n        }\n    }\n    if (tmpIndexArr.length > 0) {\n        for (var j = 0; j < mttTranslatedTextNodes.length; j++) {\n            if (!findNodeInArray(tmpIndexArr, j)) {\n                tmpArr.push(mttTranslatedTextNodes[j]);\n            }\n        }\n        mttTranslatedTextNodes = tmpArr;\n        if (lastTimerId > 0)\n            clearTimeout(lastTimerId);\n        lastTimerId = setTimeout(translatePage, translateFireTimeout);\n    }\n});\nvar nodeValueChangeConfig = {subtree: true, characterData: true, characterDataOldValue: true};";
  private String i = "function isNodeValid(node) {\n    var str = node.nodeValue;\n    str = str.replace(/[ | ]*\\n/g, '\\n');\n    str = str.replace(/\\n[\\s| | ]*\\r/g, '\\n');\n    str = str.replace(/ /ig, '');\n    str = str.replace(/^[\\s　]+|[\\s　]+$/g, \"\");\n    str = str.replace(/[\\r\\n]/g, \"\");\n    return str.length > 0;\n}\nfunction getWebpageText() {\n    var wholeText = '';\n    var badNodes = {script: 1, style: 1, noscript: 1, embed: 1, object: 1};\n    var tmpParentName;\n    var n, count = 0, walk = document.createTreeWalker(document.getElementsByTagName('html')[0], NodeFilter.SHOW_TEXT, null, false);\n    while (n = walk.nextNode()) {\n        tmpParentName = n.parentNode.nodeName.toLowerCase();\n        if (!badNodes[tmpParentName] && isNodeValid(n)) {\n            if (count == 0) {\n                wholeText = n.nodeValue.trim();\n            } else {\n                wholeText = wholeText.concat(\",\" + n.nodeValue.trim());\n            }\n            count++;\n        }\n    }\n    return wholeText;\n}";
  
  public t(WebViewChromiumExtension paramWebViewChromiumExtension, TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension = paramWebViewChromiumExtension;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    paramTencentWebViewProxy.addJavascriptInterface(this, "wxTranslateHelper");
  }
  
  private boolean a(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = paramString1.replaceAll("-", "_").toLowerCase();
      paramString2 = paramString2.replaceAll("-", "_").toLowerCase();
      if (paramString1.length() > paramString2.length()) {
        return paramString1.startsWith(paramString2);
      }
      if (paramString1.length() < paramString2.length()) {
        return paramString2.startsWith(paramString1);
      }
      boolean bool = paramString1.equals(paramString2);
      return bool;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    return false;
  }
  
  private void b()
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.evaluateJavascript(this.h, null);
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.evaluateJavascript("translatePage()", new ValueCallback()
    {
      public void a(String paramAnonymousString)
      {
        t.a(t.this);
        if (t.a(t.this).getWebViewClientExtension() != null)
        {
          paramAnonymousString = new Bundle();
          t.a(t.this).getWebViewClientExtension().onMiscCallBack("onTranslateFinish", paramAnonymousString);
        }
      }
    });
    if (this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.getWebViewClientExtension() != null)
    {
      Bundle localBundle = new Bundle();
      this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.getWebViewClientExtension().onMiscCallBack("onTranslateStart", localBundle);
    }
  }
  
  private void c()
  {
    if ((a()) && (this.jdField_a_of_type_AndroidWidgetTextView == null))
    {
      this.jdField_a_of_type_AndroidWidgetTextView = new TextView(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealContext());
      this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
      this.jdField_a_of_type_AndroidWidgetTextView.setPadding(32, 24, 32, 24);
      if (this.jdField_a_of_type_Int >= 50)
      {
        this.jdField_a_of_type_AndroidWidgetTextView.setText(this.f);
      }
      else
      {
        StringBuilder localStringBuilder;
        if ((!TextUtils.isEmpty(this.jdField_c_of_type_JavaLangString)) && (!TextUtils.isEmpty(this.e)))
        {
          localObject = this.jdField_a_of_type_AndroidWidgetTextView;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(this.e);
          localStringBuilder.append("");
          localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
          ((TextView)localObject).setText(localStringBuilder.toString());
        }
        else if (TextUtils.isEmpty(this.jdField_c_of_type_JavaLangString))
        {
          localObject = this.jdField_a_of_type_AndroidWidgetTextView;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(this.e);
          localStringBuilder.append("，结果仅供参考");
          ((TextView)localObject).setText(localStringBuilder.toString());
        }
        else
        {
          localObject = this.jdField_a_of_type_AndroidWidgetTextView;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(this.jdField_d_of_type_JavaLangString);
          localStringBuilder.append("");
          localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
          ((TextView)localObject).setText(localStringBuilder.toString());
        }
      }
      this.jdField_a_of_type_AndroidWidgetTextView.setTextColor(-7829368);
      this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(16.0F);
      if ((!TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString)) && ((this.jdField_b_of_type_JavaLangString.contains("mp.weixin.qq.com/mp/appmsg/show")) || (this.jdField_b_of_type_JavaLangString.contains("mp.weixin.qq.com/s")))) {
        this.jdField_a_of_type_AndroidWidgetTextView.setBackgroundColor(-328966);
      } else {
        this.jdField_a_of_type_AndroidWidgetTextView.setBackgroundColor(-855310);
      }
      this.jdField_a_of_type_AndroidWidgetTextView.setLineSpacing(0.0F, 1.0F);
      Object localObject = new FrameLayout.LayoutParams(-1, -2);
      ((FrameLayout.LayoutParams)localObject).gravity = 48;
      ((FrameLayout)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getParent()).addView(this.jdField_a_of_type_AndroidWidgetTextView, (ViewGroup.LayoutParams)localObject);
      this.jdField_a_of_type_AndroidWidgetTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
      {
        public void onGlobalLayout()
        {
          int i = t.a(t.this).getHeight();
          FrameLayout.LayoutParams localLayoutParams1 = (FrameLayout.LayoutParams)t.a(t.this).getLayoutParams();
          FrameLayout.LayoutParams localLayoutParams2 = (FrameLayout.LayoutParams)t.a(t.this).getRealWebView().getLayoutParams();
          if (i < 144)
          {
            localLayoutParams1.height = 144;
            localLayoutParams2.topMargin = 144;
          }
          else
          {
            localLayoutParams2.topMargin = i;
          }
          t.a(t.this).setLayoutParams(localLayoutParams1);
          t.a(t.this).getRealWebView().setLayoutParams(localLayoutParams2);
          try
          {
            if (Build.VERSION.SDK_INT >= 16) {
              t.a(t.this).getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
            return;
          }
          catch (Exception localException) {}
        }
      });
    }
  }
  
  public int a(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1))
    {
      Log.e("odin_trans", " need not translate:not get web content");
      return 1;
    }
    if (!TextUtils.isEmpty(this.g))
    {
      if (this.g.equalsIgnoreCase(paramString2)) {
        return 1;
      }
      return 0;
    }
    paramString1 = SmttServiceProxy.getInstance().detectLanguage(paramString1, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl());
    if (TextUtils.isEmpty(paramString1)) {
      return 2;
    }
    if ("forbidden_url".equalsIgnoreCase(paramString1)) {
      return -8;
    }
    if (a(paramString1, paramString2)) {
      return 1;
    }
    return 0;
  }
  
  public String a()
  {
    return this.i;
  }
  
  public void a()
  {
    this.g = "en";
    if (a()) {
      b();
    }
  }
  
  public void a(String paramString)
  {
    this.g = paramString;
  }
  
  public boolean a()
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    try
    {
      if ("com.tencent.mm".equalsIgnoreCase(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext().getApplicationInfo().packageName))
      {
        boolean bool3 = this.jdField_a_of_type_Boolean;
        bool1 = bool2;
        if (bool3) {
          bool1 = true;
        }
      }
      return bool1;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public boolean a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.jdField_a_of_type_Boolean = true;
    this.jdField_b_of_type_JavaLangString = paramString1;
    this.jdField_a_of_type_JavaLangString = paramString2;
    this.jdField_c_of_type_JavaLangString = paramString3;
    this.jdField_d_of_type_JavaLangString = paramString4;
    this.f = paramString5;
    return true;
  }
  
  public void b(String paramString)
  {
    if (a()) {
      b();
    }
  }
  
  @JavascriptInterface
  public String getTranslateResult(String paramString)
  {
    if ((a()) && (!TextUtils.isEmpty(paramString)))
    {
      paramString = SmttServiceProxy.getInstance().translate(paramString, this.g, this.jdField_a_of_type_JavaLangString, this.jdField_b_of_type_JavaLangString);
      if (paramString != null)
      {
        String str = paramString.getString("translateResult");
        this.e = paramString.getString("brandWording");
        this.jdField_a_of_type_Int = paramString.getInt("errorPercent", 0);
        return str;
      }
      this.jdField_a_of_type_Int = 100;
      return "";
    }
    return "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */