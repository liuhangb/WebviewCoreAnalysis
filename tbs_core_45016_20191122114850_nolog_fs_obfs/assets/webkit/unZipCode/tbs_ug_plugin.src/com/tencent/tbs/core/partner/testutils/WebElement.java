package com.tencent.tbs.core.partner.testutils;

import java.util.Hashtable;

public class WebElement
{
  private Hashtable<String, String> attributes;
  private String className;
  private String id;
  private int locationX = 0;
  private int locationY = 0;
  private String name;
  private String tagName;
  private String text;
  
  public WebElement(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Hashtable<String, String> paramHashtable)
  {
    setId(paramString1);
    setTextContent(paramString2);
    setName(paramString3);
    setClassName(paramString4);
    setTagName(paramString5);
    setAttributes(paramHashtable);
  }
  
  public String getAttribute(String paramString)
  {
    if (paramString != null) {
      return (String)this.attributes.get(paramString);
    }
    return null;
  }
  
  public String getClassName()
  {
    return this.className;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void getLocationOnScreen(int[] paramArrayOfInt)
  {
    paramArrayOfInt[0] = this.locationX;
    paramArrayOfInt[1] = this.locationY;
  }
  
  public int getLocationX()
  {
    return this.locationX;
  }
  
  public int getLocationY()
  {
    return this.locationY;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getTagName()
  {
    return this.tagName;
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public void setAttributes(Hashtable<String, String> paramHashtable)
  {
    this.attributes = paramHashtable;
  }
  
  public void setClassName(String paramString)
  {
    this.className = paramString;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setLocationX(int paramInt)
  {
    this.locationX = paramInt;
  }
  
  public void setLocationY(int paramInt)
  {
    this.locationY = paramInt;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setTagName(String paramString)
  {
    this.tagName = paramString;
  }
  
  public void setTextContent(String paramString)
  {
    this.text = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\WebElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */