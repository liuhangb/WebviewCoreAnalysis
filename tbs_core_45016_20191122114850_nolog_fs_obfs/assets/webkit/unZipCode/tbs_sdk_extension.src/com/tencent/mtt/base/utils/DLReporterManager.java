package com.tencent.mtt.base.utils;

import android.util.SparseArray;

public class DLReporterManager
{
  private static SparseArray<DLReporter> jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
  private static OnReportListener jdField_a_of_type_ComTencentMttBaseUtilsDLReporterManager$OnReportListener;
  
  public static void addReporter(DLReporter paramDLReporter)
  {
    try
    {
      jdField_a_of_type_AndroidUtilSparseArray.put(paramDLReporter.taskId, paramDLReporter);
      return;
    }
    finally
    {
      paramDLReporter = finally;
      throw paramDLReporter;
    }
  }
  
  public static void addStepForAllReporter(String paramString)
  {
    try
    {
      int j = jdField_a_of_type_AndroidUtilSparseArray.size();
      int i = 0;
      while (i < j)
      {
        DLReporter localDLReporter = (DLReporter)jdField_a_of_type_AndroidUtilSparseArray.valueAt(i);
        if (localDLReporter != null) {
          localDLReporter.addStep(paramString);
        }
        i += 1;
      }
      return;
    }
    finally {}
  }
  
  public static void clearReport(DLReporter paramDLReporter)
  {
    if (paramDLReporter != null) {}
    try
    {
      jdField_a_of_type_AndroidUtilSparseArray.remove(paramDLReporter.taskId);
    }
    finally {}
  }
  
  public static DLReporter getReporter(int paramInt)
  {
    try
    {
      DLReporter localDLReporter = (DLReporter)jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
      return localDLReporter;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void report(DLReporter paramDLReporter, boolean paramBoolean)
  {
    if (paramDLReporter != null) {
      try
      {
        paramDLReporter.b();
        if (jdField_a_of_type_ComTencentMttBaseUtilsDLReporterManager$OnReportListener != null) {
          jdField_a_of_type_ComTencentMttBaseUtilsDLReporterManager$OnReportListener.onReport(paramDLReporter);
        }
        jdField_a_of_type_AndroidUtilSparseArray.remove(paramDLReporter.taskId);
      }
      finally {}
    }
  }
  
  public static void setOnReportListener(OnReportListener paramOnReportListener)
  {
    try
    {
      jdField_a_of_type_ComTencentMttBaseUtilsDLReporterManager$OnReportListener = paramOnReportListener;
      return;
    }
    finally
    {
      paramOnReportListener = finally;
      throw paramOnReportListener;
    }
  }
  
  public static abstract interface OnReportListener
  {
    public abstract void onReport(DLReporter paramDLReporter);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLReporterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */