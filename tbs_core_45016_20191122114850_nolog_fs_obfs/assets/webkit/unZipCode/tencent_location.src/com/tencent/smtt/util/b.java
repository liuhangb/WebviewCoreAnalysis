package com.tencent.smtt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class b
{
  private ArrayList<Pattern> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private Hashtable<String, String> jdField_a_of_type_JavaUtilHashtable = new Hashtable();
  private ArrayList<String> b = new ArrayList();
  
  private String a(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int k = paramString.length();
    int i = 0;
    while (i < k)
    {
      char c = paramString.charAt(i);
      int j = i + 3;
      if ((j < k) && (c == '('))
      {
        int m = i + 1;
        if (paramString.charAt(m) == '.')
        {
          int n = i + 2;
          if ((paramString.charAt(n) == '*') && (paramString.charAt(j) == ')'))
          {
            localStringBuffer.append(c);
            localStringBuffer.append(paramString.charAt(m));
            localStringBuffer.append(paramString.charAt(n));
            localStringBuffer.append(paramString.charAt(j));
            i = j;
            break label170;
          }
        }
      }
      if (c == '*')
      {
        localStringBuffer.append('.');
        localStringBuffer.append(c);
      }
      else
      {
        localStringBuffer.append(c);
      }
      label170:
      i += 1;
    }
    return localStringBuffer.toString();
  }
  
  private boolean b(String paramString)
  {
    int i = 0;
    while (i < paramString.length())
    {
      int j = paramString.charAt(i);
      if (((j < 97) || (j > 122)) && (j != 46) && (j != 45) && ((j < 48) || (j > 57)) && ((j < 65) || (j > 90)) && ((j < 19968) || (j > 40959))) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private boolean c(String paramString)
  {
    return (paramString.length() > 3) && (paramString.startsWith("*")) && (!paramString.substring(1).contains("*"));
  }
  
  public void a()
  {
    this.jdField_a_of_type_JavaUtilHashtable.clear();
    this.jdField_a_of_type_JavaUtilArrayList.clear();
    this.b.clear();
    this.jdField_a_of_type_JavaUtilHashMap.clear();
  }
  
  public void a(String paramString)
  {
    if (c(paramString))
    {
      this.b.add(paramString.substring(1));
      return;
    }
    if (b(paramString))
    {
      paramString = a(paramString);
      this.jdField_a_of_type_JavaUtilArrayList.add(Pattern.compile(paramString));
      return;
    }
    this.jdField_a_of_type_JavaUtilHashtable.put(paramString, "");
  }
  
  public void a(List<String> paramList)
  {
    if (paramList != null)
    {
      if (paramList.size() == 0) {
        return;
      }
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        String str = (String)paramList.next();
        if (str != null) {
          a(str.toLowerCase(Locale.ENGLISH));
        }
      }
      return;
    }
  }
  
  public boolean a(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    paramString = paramString.toLowerCase();
    if (this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString)) {
      return true;
    }
    try
    {
      int i;
      if (this.jdField_a_of_type_JavaUtilHashMap.size() >= 50)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilHashMap.keySet().iterator();
        i = 0;
        while ((localIterator.hasNext()) && (i < 10) && (localIterator.next() != null))
        {
          localIterator.remove();
          i += 1;
        }
      }
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if (this.jdField_a_of_type_JavaUtilHashtable.containsKey(paramString))
      {
        this.jdField_a_of_type_JavaUtilHashMap.put(paramString, "");
        return true;
      }
      if (!this.b.isEmpty())
      {
        i = 0;
        while (i < this.b.size())
        {
          if (paramString.endsWith((String)this.b.get(i)))
          {
            this.jdField_a_of_type_JavaUtilHashMap.put(paramString, "");
            return true;
          }
          i += 1;
        }
      }
      i = 0;
      while (i < this.jdField_a_of_type_JavaUtilArrayList.size())
      {
        if (((Pattern)this.jdField_a_of_type_JavaUtilArrayList.get(i)).matcher(paramString).find())
        {
          this.jdField_a_of_type_JavaUtilHashMap.put(paramString, "");
          return true;
        }
        i += 1;
      }
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("\r\n1> DomainTable:\r\n");
    Iterator localIterator = this.jdField_a_of_type_JavaUtilHashtable.keySet().iterator();
    Object localObject1;
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject1 = (String)localIterator.next();
      localObject2 = (String)this.jdField_a_of_type_JavaUtilHashtable.get(localObject1);
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("<");
      localStringBuilder2.append((String)localObject1);
      localStringBuilder2.append(",");
      localStringBuilder2.append((String)localObject2);
      localStringBuilder2.append(">\t");
      localStringBuilder1.append(localStringBuilder2.toString());
    }
    localStringBuilder1.append("\r\n2> PatternList:\r\n");
    localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Pattern)localIterator.next();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(((Pattern)localObject1).toString());
      ((StringBuilder)localObject2).append("\t");
      localStringBuilder1.append(((StringBuilder)localObject2).toString());
    }
    localStringBuilder1.append("\r\n3> RegressionList:\r\n");
    localIterator = this.b.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (String)localIterator.next();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\t");
      localStringBuilder1.append(((StringBuilder)localObject2).toString());
    }
    return localStringBuilder1.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */