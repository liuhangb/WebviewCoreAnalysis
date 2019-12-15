package com.tencent.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainMatcher
{
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private ArrayList<Pattern> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  private HashSet<String> jdField_a_of_type_JavaUtilHashSet = new HashSet();
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
  
  private boolean a(String paramString)
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
  
  private boolean b(String paramString)
  {
    return (paramString.length() > 3) && (paramString.startsWith("*")) && (!paramString.substring(1).contains("*"));
  }
  
  public void addDomain(String paramString)
  {
    if (b(paramString))
    {
      this.b.add(paramString.substring(1));
      return;
    }
    if (a(paramString))
    {
      paramString = a(paramString);
      this.jdField_a_of_type_JavaUtilArrayList.add(Pattern.compile(paramString));
      return;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_JavaUtilHashtable.put(paramString, "");
      return;
    }
  }
  
  public void addDomainList(ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() == 0) {
        return;
      }
      int i = 0;
      while (i < paramArrayList.size())
      {
        addDomain(((String)paramArrayList.get(i)).toLowerCase());
        i += 1;
      }
      return;
    }
  }
  
  public void clear()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_JavaUtilHashtable.clear();
      this.jdField_a_of_type_JavaUtilArrayList.clear();
      this.b.clear();
      this.jdField_a_of_type_JavaUtilHashSet.clear();
      return;
    }
  }
  
  public boolean isContainsDomain(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    paramString = paramString.toLowerCase();
    if (this.jdField_a_of_type_JavaUtilHashSet.contains(paramString)) {
      return true;
    }
    try
    {
      int i;
      if (this.jdField_a_of_type_JavaUtilHashSet.size() >= 50)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilHashSet.iterator();
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
        this.jdField_a_of_type_JavaUtilHashSet.add(paramString);
        return true;
      }
      if (!this.b.isEmpty())
      {
        i = 0;
        while (i < this.b.size())
        {
          if (paramString.endsWith((String)this.b.get(i)))
          {
            this.jdField_a_of_type_JavaUtilHashSet.add(paramString);
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
          this.jdField_a_of_type_JavaUtilHashSet.add(paramString);
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
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      Object localObject3 = this.jdField_a_of_type_JavaUtilHashtable.keySet().iterator();
      Object localObject4;
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (String)((Iterator)localObject3).next();
        String str = (String)this.jdField_a_of_type_JavaUtilHashtable.get(localObject4);
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("<");
        localStringBuilder2.append((String)localObject4);
        localStringBuilder2.append(",");
        localStringBuilder2.append(str);
        localStringBuilder2.append(">\t");
        localStringBuilder1.append(localStringBuilder2.toString());
      }
      localStringBuilder1.append("\r\n2> PatternList:\r\n");
      ??? = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (((Iterator)???).hasNext())
      {
        localObject3 = (Pattern)((Iterator)???).next();
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append(((Pattern)localObject3).toString());
        ((StringBuilder)localObject4).append("\t");
        localStringBuilder1.append(((StringBuilder)localObject4).toString());
      }
      localStringBuilder1.append("\r\n3> RegressionList:\r\n");
      ??? = this.b.iterator();
      while (((Iterator)???).hasNext())
      {
        localObject3 = (String)((Iterator)???).next();
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append((String)localObject3);
        ((StringBuilder)localObject4).append("\t");
        localStringBuilder1.append(((StringBuilder)localObject4).toString());
      }
      return localStringBuilder1.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\DomainMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */