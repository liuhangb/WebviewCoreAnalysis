package com.tencent.common.threadpool.debug;

public class QBThreadRunInfo
{
  long jdField_a_of_type_Long = 0L;
  Runnable jdField_a_of_type_JavaLangRunnable;
  String jdField_a_of_type_JavaLangString;
  Thread jdField_a_of_type_JavaLangThread;
  boolean jdField_a_of_type_Boolean = false;
  long b = 0L;
  
  public void clear()
  {
    this.jdField_a_of_type_JavaLangRunnable = null;
    this.jdField_a_of_type_JavaLangThread = null;
  }
  
  public QBThreadRunInfo clone()
  {
    QBThreadRunInfo localQBThreadRunInfo = new QBThreadRunInfo();
    localQBThreadRunInfo.jdField_a_of_type_Long = this.jdField_a_of_type_Long;
    localQBThreadRunInfo.b = this.b;
    localQBThreadRunInfo.jdField_a_of_type_JavaLangRunnable = this.jdField_a_of_type_JavaLangRunnable;
    localQBThreadRunInfo.jdField_a_of_type_JavaLangThread = this.jdField_a_of_type_JavaLangThread;
    localQBThreadRunInfo.jdField_a_of_type_JavaLangString = this.jdField_a_of_type_JavaLangString;
    localQBThreadRunInfo.jdField_a_of_type_Boolean = this.jdField_a_of_type_Boolean;
    return localQBThreadRunInfo;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\debug\QBThreadRunInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */