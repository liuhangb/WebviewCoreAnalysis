package com.tencent.smtt.net;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class m
{
  static Pattern a;
  public int a;
  public String a;
  public String b;
  public String c;
  public String d;
  
  static
  {
    jdField_a_of_type_JavaUtilRegexPattern = Pattern.compile("(?:(http|https|file)\\:\\/\\/)?(?:([-A-Za-z0-9$_.+!*'(),;?&=]+(?:\\:[-A-Za-z0-9$_.+!*'(),;?&=]+)?)@)?([-a-zA-Z0-9 -퟿豈-﷏ﷰ-￯%_]+(?:\\.[-a-zA-Z0-9 -퟿豈-﷏ﷰ-￯%_]+)*|\\[[0-9a-fA-F:\\.]+\\])?(?:\\:([0-9]*))?(\\/?[^#]*)?.*", 2);
  }
  
  public m(String paramString)
    throws ParseException
  {
    Object localObject;
    if (paramString != null)
    {
      this.jdField_a_of_type_JavaLangString = "";
      this.b = "";
      this.jdField_a_of_type_Int = -1;
      this.c = "/";
      this.d = "";
      localObject = null;
      try
      {
        paramString = jdField_a_of_type_JavaUtilRegexPattern.matcher(paramString);
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        paramString = (String)localObject;
      }
      if ((paramString != null) && (paramString.matches()))
      {
        localObject = paramString.group(1);
        if (localObject != null) {
          this.jdField_a_of_type_JavaLangString = ((String)localObject).toLowerCase();
        }
        localObject = paramString.group(2);
        if (localObject != null) {
          this.d = ((String)localObject);
        }
        localObject = paramString.group(3);
        if (localObject != null) {
          this.b = ((String)localObject);
        }
        localObject = paramString.group(4);
        if ((localObject == null) || (((String)localObject).length() <= 0)) {}
      }
    }
    try
    {
      this.jdField_a_of_type_Int = Integer.parseInt((String)localObject);
    }
    catch (NumberFormatException paramString)
    {
      for (;;) {}
    }
    throw new ParseException("Bad port");
    paramString = paramString.group(5);
    if ((paramString != null) && (paramString.length() > 0)) {
      if (paramString.charAt(0) == '/')
      {
        this.c = paramString;
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("/");
        ((StringBuilder)localObject).append(paramString);
        this.c = ((StringBuilder)localObject).toString();
      }
    }
    if ((this.jdField_a_of_type_Int == 443) && (this.jdField_a_of_type_JavaLangString.equals(""))) {
      this.jdField_a_of_type_JavaLangString = "https";
    } else if (this.jdField_a_of_type_Int == -1) {
      if (this.jdField_a_of_type_JavaLangString.equals("https")) {
        this.jdField_a_of_type_Int = 443;
      } else {
        this.jdField_a_of_type_Int = 80;
      }
    }
    if (this.jdField_a_of_type_JavaLangString.equals("")) {
      this.jdField_a_of_type_JavaLangString = "http";
    }
    return;
    throw new ParseException("Bad address");
    throw new NullPointerException();
  }
  
  public String toString()
  {
    Object localObject2 = "";
    Object localObject1;
    if ((this.jdField_a_of_type_Int == 443) || (!this.jdField_a_of_type_JavaLangString.equals("https")))
    {
      localObject1 = localObject2;
      if (this.jdField_a_of_type_Int != 80)
      {
        localObject1 = localObject2;
        if (!this.jdField_a_of_type_JavaLangString.equals("http")) {}
      }
    }
    else
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(":");
      ((StringBuilder)localObject1).append(Integer.toString(this.jdField_a_of_type_Int));
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    localObject2 = "";
    if (this.d.length() > 0)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(this.d);
      ((StringBuilder)localObject2).append("@");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
    localStringBuilder.append("://");
    localStringBuilder.append((String)localObject2);
    localStringBuilder.append(this.b);
    localStringBuilder.append((String)localObject1);
    localStringBuilder.append(this.c);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */