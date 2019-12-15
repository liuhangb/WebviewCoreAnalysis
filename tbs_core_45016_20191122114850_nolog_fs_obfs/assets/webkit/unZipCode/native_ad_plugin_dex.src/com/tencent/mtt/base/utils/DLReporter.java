package com.tencent.mtt.base.utils;

import java.util.HashMap;
import java.util.Map;

public class DLReporter
{
  public static final String FLAG_ERROR = "-1";
  public static final String FLAG_OK = "0";
  public static final String FLAG_TIMEOUT = "1";
  private long jdField_a_of_type_Long = -1L;
  private String jdField_a_of_type_JavaLangString;
  private final StringBuilder jdField_a_of_type_JavaLangStringBuilder = new StringBuilder();
  private final StringBuilder b = new StringBuilder();
  public String name = "";
  public int taskId;
  
  public DLReporter(String paramString)
  {
    this.name = paramString;
    a();
  }
  
  DLReporter a()
  {
    try
    {
      this.jdField_a_of_type_Long = System.currentTimeMillis();
      StringBuilder localStringBuilder1 = this.jdField_a_of_type_JavaLangStringBuilder;
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("(BEGIN_");
      localStringBuilder2.append(this.jdField_a_of_type_Long);
      localStringBuilder2.append(")");
      localStringBuilder1.append(localStringBuilder2.toString());
      return this;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public DLReporter addDLReporter(DLReporter paramDLReporter)
  {
    try
    {
      StringBuilder localStringBuilder1 = this.jdField_a_of_type_JavaLangStringBuilder;
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("<");
      localStringBuilder2.append(paramDLReporter.jdField_a_of_type_JavaLangStringBuilder);
      localStringBuilder2.append(">");
      localStringBuilder1.append(localStringBuilder2.toString());
      localStringBuilder1 = this.b;
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("<");
      localStringBuilder2.append(paramDLReporter.b);
      localStringBuilder2.append(">");
      localStringBuilder1.append(localStringBuilder2.toString());
      return this;
    }
    finally
    {
      paramDLReporter = finally;
      throw paramDLReporter;
    }
  }
  
  public DLReporter addDownPath(String paramString)
  {
    try
    {
      this.b.append(paramString);
      return this;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public DLReporter addStep(String paramString)
  {
    try
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append(paramString);
      return this;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  DLReporter b()
  {
    try
    {
      StringBuilder localStringBuilder1 = this.jdField_a_of_type_JavaLangStringBuilder;
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("(END_");
      localStringBuilder2.append(System.currentTimeMillis());
      localStringBuilder2.append(")");
      localStringBuilder1.append(localStringBuilder2.toString());
      return this;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public long calDeltTime()
  {
    try
    {
      if (this.jdField_a_of_type_Long == -1L)
      {
        a();
        return 0L;
      }
      long l1 = System.currentTimeMillis();
      long l2 = this.jdField_a_of_type_Long;
      return l1 - l2;
    }
    finally {}
  }
  
  public void setId(int paramInt)
  {
    try
    {
      this.taskId = paramInt;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public DLReporter setReportFlag(String paramString)
  {
    try
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      return this;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public Map<String, String> toEventParam()
  {
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("task_id", String.valueOf(this.taskId));
      localHashMap.put("file_name", this.name);
      localHashMap.put("flag", this.jdField_a_of_type_JavaLangString);
      localHashMap.put("step", this.jdField_a_of_type_JavaLangStringBuilder.toString());
      localHashMap.put("path", this.b.toString());
      return localHashMap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */