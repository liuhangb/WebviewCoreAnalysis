package com.tencent.tbs.core.partner.testutils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.util.ArrayList;
import java.util.Iterator;

public class WebUtils
{
  private static WebUtils instance;
  private String injectJs = "/**\n * Used by the web methods.\n * \n * @author Renas Reda, renas.reda@robotium.com\n * \n */\n\nfunction allWebElements() {\n\tfor (var key in document.all){\n\t\ttry{\n\t\t\tpromptElement(document.all[key]);\t\t\t\n\t\t}catch(ignored){}\n\t}\n\tfinished();\n}\n\nfunction allTexts() {\n\tvar range = document.createRange();\n\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, null, false); \n\twhile(n=walk.nextNode()){\n\t\ttry{\n\t\t\tpromptText(n, range);\n\t\t}catch(ignored){}\n\t} \n\tfinished();\n}\n\nfunction clickElement(element){\n\tvar e = document.createEvent('MouseEvents');\n\te.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);\n\telement.dispatchEvent(e);\n//element.addEbentListener(\"vlivk\",function(){alert(1)})\n}\n\nfunction id(id, click) {\n\tvar element = document.getElementById(id);\n\tif(element != null){ \n\n\t\tif(click == 'true'){\n\t\t\tclickElement(element);\n\t\t}\n\t\telse{\n\t\t\tpromptElement(element);\n\t\t}\n\t} \n\telse {\n\t\tfor (var key in document.all){\n\t\t\ttry{\n\t\t\t\telement = document.all[key];\n\t\t\t\tif(element.id == id) {\n\t\t\t\t\tif(click == 'true'){\n\t\t\t\t\t\tclickElement(element);\n\t\t\t\t\t\treturn;\n\t\t\t\t\t}\n\t\t\t\t\telse{\n\t\t\t\t\t\tpromptElement(element);\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t} catch(ignored){}\t\t\t\n\t\t}\n\t}\n\tfinished(); \n}\n\nfunction xpath(xpath, click) {\n\tvar elements = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); \n\n\tif (elements){\n\t\tvar element = elements.iterateNext();\n\t\twhile(element) {\n\t\t\tif(click == 'true'){\n\t\t\t\tclickElement(element);\n\t\t\t\treturn;\n\t\t\t}\n\t\t\telse{\n\t\t\t\tpromptElement(element);\n\t\t\t\telement = elements.iterateNext();\n\t\t\t}\n\t\t}\n\t\tfinished();\n\t}\n}\n\nfunction cssSelector(cssSelector, click) {\n\tvar elements = document.querySelectorAll(cssSelector);\n\tfor (var key in elements) {\n\t\tif(elements != null){ \n\t\t\ttry{\n\t\t\t\tif(click == 'true'){\n\t\t\t\t\tclickElement(elements[key]);\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\tpromptElement(elements[key]);\n\t\t\t\t}\t\n\t\t\t}catch(ignored){}  \n\t\t}\n\t}\n\tfinished(); \n}\n\nfunction name(name, click) {\n\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n\twhile(n=walk.nextNode()){\n\t\ttry{\n\t\t\tvar attributeName = n.getAttribute('name');\n\t\t\tif(attributeName != null && attributeName.trim().length>0 && attributeName == name){\n\t\t\t\tif(click == 'true'){\n\t\t\t\t\tclickElement(n);\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\tpromptElement(n);\n\t\t\t\t}\t\n\t\t\t}\n\t\t}catch(ignored){} \n\t} \n\tfinished();\n}\n\nfunction className(nameOfClass, click) {\n\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n\twhile(n=walk.nextNode()){\n\t\ttry{\n\t\t\tvar className = n.className; \n\t\t\tif(className != null && className.trim().length>0 && className == nameOfClass) {\n\t\t\t\tif(click == 'true'){\n\t\t\t\t\tclickElement(n);\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\tpromptElement(n);\n\t\t\t\t}\t\n\t\t\t}\n\t\t}catch(ignored){} \n\t} \n\tfinished(); \n}\n\nfunction textContent(text, click) {\n\tvar range = document.createRange();\n\tvar walk=document.createTreeWalker(document.body,NodeFilter.SHOW_TEXT,null,false); \n\twhile(n=walk.nextNode()){ \n\t\ttry{\n\t\t\tvar textContent = n.textContent; \n\t\t\tif(textContent.trim() == text.trim()){  \n\t\t\t\tif(click == 'true'){\n\t\t\t\t\tclickElement(n);\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\tpromptText(n, range);\n\t\t\t\t}\n\t\t\t}\n\t\t}catch(ignored){} \n\t} \n\tfinished();  \n}\n\nfunction tagName(tagName, click) {\n\tvar elements = document.getElementsByTagName(tagName);\n\tfor (var key in elements) {\n\t\tif(elements != null){ \n\t\t\ttry{\n\t\t\t\tif(click == 'true'){\n\t\t\t\t\tclickElement(elements[key]);\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\tpromptElement(elements[key]);\n\t\t\t\t}\t\n\t\t\t}catch(ignored){}  \n\t\t}\n\t}\n\tfinished();\n}\n\nfunction enterTextById(id, text) {\n\tvar element = document.getElementById(id);\n\tif(element != null){\n\t\telement.value = text;\n\t\telement.focus();\n\t\telement.select();\n\t}\n\n\tfinished(); \n}\n\nfunction enterTextByXpath(xpath, text) {\n\tvar element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;\n\tif(element != null){\n\t\telement.value = text;\n\t\telement.focus();\n        element.select();\n\t\t}\n\n\tfinished(); \n}\n\nfunction enterTextByCssSelector(cssSelector, text) {\n\tvar element = document.querySelector(cssSelector);\n\tif(element != null){\n\t\telement.value = text;\n\t\telement.focus();\n\t\telement.select();\n\t\t}\n\tfinished(); \n}\n\nfunction enterTextByName(name, text) {\n\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n\twhile(n=walk.nextNode()){\n\t\tvar attributeName = n.getAttribute('name');\n\t\tif(attributeName != null && attributeName.trim().length>0 && attributeName == name) {\n\t\t\tn.value=text;\n\t\t    n.focus();\n\t\t    n.select();\n\t\t\t}\n\t} \n\tfinished();\n}\n\nfunction enterTextByClassName(name, text) {\n\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n\twhile(n=walk.nextNode()){\n\t\tvar className = n.className; \n\t\tif(className != null && className.trim().length>0 && className == name) {\n\t\t\tn.value=text;\n\t\t    n.focus();\n\t\t    n.select();\n\t\t\t}\n\t}\n\tfinished();\n}\n\nfunction enterTextByTextContent(textContent, text) {\n\tvar walk=document.createTreeWalker(document.body,NodeFilter.SHOW_TEXT, null, false); \n\twhile(n=walk.nextNode()){ \n\t\tvar textValue = n.textContent; \n\t\tif(textValue == textContent) {\n\t\t\tn.parentNode.value = text;\n\t\t    n.parentNode.focus();\n\t\t    n.parentNode.select();\n\t\t\t}\n\t}\n\tfinished();\n}\n\nfunction enterTextByTagName(tagName, text) {\n\tvar elements = document.getElementsByTagName(tagName);\n\tif(elements != null){\n\t\telements[0].value = text;\n\t\telements[0].focus();\n\t\telements[0].select();\n\t}\n\tfinished();\n}\n\nfunction promptElement(element) {\n\tvar id = element.id;\n\tvar text = element.innerText;\n\tif(text.trim().length == 0){\n\t\ttext = element.value;\n\t}\n\tvar name = element.getAttribute('name');\n\tvar className = element.className;\n\tvar tagName = element.tagName;\n\tvar attributes = \"\";\n\tvar htmlAttributes = element.attributes;\n\tfor (var i = 0, htmlAttribute; htmlAttribute = htmlAttributes[i]; i++){\n\t\tattributes += htmlAttribute.name + \"::\" + htmlAttribute.value;\n\t\tif (i + 1 < htmlAttributes.length) {\n\t\t\tattributes += \"#$\";\n\t\t}\n\t}\n\n\tvar rect = element.getBoundingClientRect();\n\tif(rect.width > 0 && rect.height > 0 && rect.left >= 0 && rect.top >= 0){\n\t\tprompt(id + ';,' + text + ';,' + name + \";,\" + className + \";,\" + tagName + \";,\" + rect.left + ';,' + rect.top + ';,' + rect.width + ';,' + rect.height + ';,' + attributes);\n\t}\n}\n\nfunction promptText(element, range) {\t\n\tvar text = element.textContent;\n\tif(text.trim().length>0) {\n\t\trange.selectNodeContents(element);\n\t\tvar rect = range.getBoundingClientRect();\n\t\tif(rect.width > 0 && rect.height > 0 && rect.left >= 0 && rect.top >= 0){\n\t\t\tvar id = element.parentNode.id;\n\t\t\tvar name = element.parentNode.getAttribute('name');\n\t\t\tvar className = element.parentNode.className;\n\t\t\tvar tagName = element.parentNode.tagName;\n\t\t\tprompt(id + ';,' + text + ';,' + name + \";,\" + className + \";,\" + tagName + \";,\" + rect.left + ';,' + rect.top + ';,' + rect.width + ';,' + rect.height);\n\t\t}\n\t}\n}\n\nfunction finished(){\n\tprompt('robotium-finished');\n}\n";
  Context mContext = null;
  IX5WebChromeClient originalWebChromeClient = null;
  RobotiumWebClient robotiumWebCLient;
  WebElementCreator webElementCreator;
  TencentWebViewProxy webView;
  
  public WebUtils(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.mContext = paramTencentWebViewProxy.getContext();
    this.webView = paramTencentWebViewProxy;
    this.webElementCreator = new WebElementCreator();
    this.robotiumWebCLient = new RobotiumWebClient(this.webElementCreator);
  }
  
  private ArrayList<TextView> createAndReturnTextViewsFromWebElements(boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean)
    {
      Iterator localIterator = this.webElementCreator.getWebElementsFromWebViews().iterator();
      while (localIterator.hasNext())
      {
        WebElement localWebElement = (WebElement)localIterator.next();
        if (isWebElementSufficientlyShown(localWebElement)) {
          localArrayList.add(new RobotiumTextView(this.mContext, localWebElement.getText(), localWebElement.getLocationX(), localWebElement.getLocationY()));
        }
      }
    }
    return localArrayList;
  }
  
  private boolean executeJavaScriptFunction(final String paramString)
  {
    final Object localObject = this.webView;
    if (localObject == null) {
      return false;
    }
    localObject = setWebFrame(prepareForStartOfJavascriptExecution((TencentWebViewProxy)localObject));
    TencentWebViewProxy localTencentWebViewProxy = this.webView;
    if (localTencentWebViewProxy != null) {
      localTencentWebViewProxy.getRealWebView().post(new Runnable()
      {
        public void run()
        {
          TencentWebViewProxy localTencentWebViewProxy = WebUtils.this.webView;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("javascript:");
          localStringBuilder.append(localObject);
          localStringBuilder.append(paramString);
          localTencentWebViewProxy.loadUrl(localStringBuilder.toString());
        }
      });
    }
    return true;
  }
  
  private IX5WebChromeClient getCurrentWebChromeClient()
  {
    return this.webView.getWebChromeClient();
  }
  
  private String getJavaScriptAsString()
  {
    return this.injectJs;
  }
  
  private ArrayList<WebElement> getWebElements(boolean paramBoolean1, boolean paramBoolean2)
  {
    ArrayList localArrayList = new ArrayList();
    WebElement localWebElement;
    if (paramBoolean1)
    {
      localObject = this.webElementCreator.getWebElementsFromWebViews().iterator();
      while (((Iterator)localObject).hasNext())
      {
        localWebElement = (WebElement)((Iterator)localObject).next();
        if (!TextUtils.isEmpty(localWebElement.getId())) {
          if (!paramBoolean2) {
            localArrayList.add(localWebElement);
          } else if (isWebElementSufficientlyShown(localWebElement)) {
            localArrayList.add(localWebElement);
          }
        }
      }
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("element-num:");
    ((StringBuilder)localObject).append(localArrayList.size());
    Log.e("for-test", ((StringBuilder)localObject).toString());
    localObject = localArrayList.iterator();
    while (((Iterator)localObject).hasNext())
    {
      localWebElement = (WebElement)((Iterator)localObject).next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("element detail:id:");
      localStringBuilder.append(localWebElement.getId());
      localStringBuilder.append("class_name: ");
      localStringBuilder.append(localWebElement.getClassName());
      localStringBuilder.append("text_content: ");
      localStringBuilder.append(localWebElement.getText());
      localStringBuilder.append("name:");
      localStringBuilder.append(localWebElement.getName());
      Log.e("for-test", localStringBuilder.toString());
    }
    return localArrayList;
  }
  
  private String prepareForStartOfJavascriptExecution(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.webElementCreator.prepareForStart();
    IX5WebChromeClient localIX5WebChromeClient = getCurrentWebChromeClient();
    if ((localIX5WebChromeClient != null) && (!localIX5WebChromeClient.getClass().isAssignableFrom(RobotiumWebClient.class))) {
      this.originalWebChromeClient = localIX5WebChromeClient;
    }
    this.robotiumWebCLient.enableJavascriptAndSetRobotiumWebClient(paramTencentWebViewProxy, this.originalWebChromeClient);
    return getJavaScriptAsString();
  }
  
  private String setWebFrame(String paramString)
  {
    return paramString;
  }
  
  public void clickOnWebElement(By paramBy)
  {
    executeJavaScript(paramBy, true);
  }
  
  public void enterTextIntoWebElement(By paramBy, String paramString)
  {
    StringBuilder localStringBuilder;
    if ((paramBy instanceof By.Id))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("enterTextById(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\");");
      executeJavaScriptFunction(localStringBuilder.toString());
      return;
    }
    if ((paramBy instanceof By.Xpath))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("enterTextByXpath('");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("', '");
      localStringBuilder.append(paramString);
      localStringBuilder.append("');");
      executeJavaScriptFunction(localStringBuilder.toString());
      return;
    }
    if ((paramBy instanceof By.CssSelector))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("enterTextByCssSelector(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\");");
      executeJavaScriptFunction(localStringBuilder.toString());
      return;
    }
    if ((paramBy instanceof By.Name))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("enterTextByName(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\");");
      executeJavaScriptFunction(localStringBuilder.toString());
      return;
    }
    if ((paramBy instanceof By.ClassName))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("enterTextByClassName(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\");");
      executeJavaScriptFunction(localStringBuilder.toString());
      return;
    }
    if ((paramBy instanceof By.Text))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("enterTextByTextContent(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\");");
      executeJavaScriptFunction(localStringBuilder.toString());
      return;
    }
    if ((paramBy instanceof By.TagName))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("enterTextByTagName(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\");");
      executeJavaScriptFunction(localStringBuilder.toString());
    }
  }
  
  public boolean executeJavaScript(By paramBy, boolean paramBoolean)
  {
    StringBuilder localStringBuilder;
    if ((paramBy instanceof By.Id))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("id('");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("', '");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("');");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    if ((paramBy instanceof By.Xpath))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("xpath('");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("', '");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("');");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    if ((paramBy instanceof By.CssSelector))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("cssSelector(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("\");");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    if ((paramBy instanceof By.Name))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("name(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("\");");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    if ((paramBy instanceof By.ClassName))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("className(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("\");");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    if ((paramBy instanceof By.Text))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("textContent(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("\");");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    if ((paramBy instanceof By.TagName))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("tagName(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("\");");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    if ((paramBy instanceof By.Href))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("href(\"");
      localStringBuilder.append(paramBy.getValue());
      localStringBuilder.append("\", \"");
      localStringBuilder.append(String.valueOf(paramBoolean));
      localStringBuilder.append("\");");
      return executeJavaScriptFunction(localStringBuilder.toString());
    }
    return false;
  }
  
  public ArrayList<TextView> getTextViewsFromWebView()
  {
    ArrayList localArrayList = createAndReturnTextViewsFromWebElements(executeJavaScriptFunction("allTexts();"));
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext()) {
      TextView localTextView = (TextView)localIterator.next();
    }
    executeJavaScript(new By.Id("tech"), true);
    return localArrayList;
  }
  
  public ArrayList<WebElement> getWebElements(By paramBy, boolean paramBoolean)
  {
    return getWebElements(executeJavaScript(paramBy, false), paramBoolean);
  }
  
  public ArrayList<WebElement> getWebElements(boolean paramBoolean)
  {
    return getWebElements(executeJavaScriptFunction("allWebElements();"), paramBoolean);
  }
  
  public final boolean isWebElementSufficientlyShown(WebElement paramWebElement)
  {
    int[] arrayOfInt = new int[2];
    TencentWebViewProxy localTencentWebViewProxy = this.webView;
    if ((localTencentWebViewProxy != null) && (paramWebElement != null))
    {
      localTencentWebViewProxy.getRealWebView().getLocationOnScreen(arrayOfInt);
      if (arrayOfInt[1] + this.webView.getRealWebView().getHeight() > paramWebElement.getLocationY()) {
        return true;
      }
    }
    return false;
  }
  
  public String splitNameByUpperCase(String paramString)
  {
    paramString = paramString.split("(?=\\p{Upper})");
    StringBuilder localStringBuilder1 = new StringBuilder();
    int j = paramString.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramString[i];
      if (localStringBuilder1.length() > 0)
      {
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append(" ");
        localStringBuilder2.append(((String)localObject).toLowerCase());
        localStringBuilder1.append(localStringBuilder2.toString());
      }
      else
      {
        localStringBuilder1.append(((String)localObject).toLowerCase());
      }
      i += 1;
    }
    return localStringBuilder1.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\WebUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */