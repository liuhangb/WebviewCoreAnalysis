package com.tencent.tbs.core.partner.testutils;

public abstract class By
{
  public static By Href(String paramString)
  {
    return new TagName(paramString);
  }
  
  public static By className(String paramString)
  {
    return new ClassName(paramString);
  }
  
  public static By cssSelector(String paramString)
  {
    return new CssSelector(paramString);
  }
  
  public static By id(String paramString)
  {
    return new Id(paramString);
  }
  
  public static By name(String paramString)
  {
    return new Name(paramString);
  }
  
  public static By tagName(String paramString)
  {
    return new TagName(paramString);
  }
  
  public static By textContent(String paramString)
  {
    return new Text(paramString);
  }
  
  public static By xpath(String paramString)
  {
    return new Xpath(paramString);
  }
  
  public String getValue()
  {
    return "";
  }
  
  static class ClassName
    extends By
  {
    private final String className;
    
    public ClassName(String paramString)
    {
      this.className = paramString;
    }
    
    public String getValue()
    {
      return this.className;
    }
  }
  
  static class CssSelector
    extends By
  {
    private final String selector;
    
    public CssSelector(String paramString)
    {
      this.selector = paramString;
    }
    
    public String getValue()
    {
      return this.selector;
    }
  }
  
  static class Href
    extends By
  {
    private final String href;
    
    public Href(String paramString)
    {
      this.href = paramString;
    }
    
    public String getValue()
    {
      return this.href;
    }
  }
  
  static class Id
    extends By
  {
    private final String id;
    
    public Id(String paramString)
    {
      this.id = paramString;
    }
    
    public String getValue()
    {
      return this.id;
    }
  }
  
  static class Name
    extends By
  {
    private final String name;
    
    public Name(String paramString)
    {
      this.name = paramString;
    }
    
    public String getValue()
    {
      return this.name;
    }
  }
  
  static class TagName
    extends By
  {
    private final String tagName;
    
    public TagName(String paramString)
    {
      this.tagName = paramString;
    }
    
    public String getValue()
    {
      return this.tagName;
    }
  }
  
  static class Text
    extends By
  {
    private final String textContent;
    
    public Text(String paramString)
    {
      this.textContent = paramString;
    }
    
    public String getValue()
    {
      return this.textContent;
    }
  }
  
  static class Xpath
    extends By
  {
    private final String xpath;
    
    public Xpath(String paramString)
    {
      this.xpath = paramString;
    }
    
    public String getValue()
    {
      return this.xpath;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\By.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */