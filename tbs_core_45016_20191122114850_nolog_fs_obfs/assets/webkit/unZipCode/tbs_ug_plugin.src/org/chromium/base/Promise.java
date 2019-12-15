package org.chromium.base;

import android.os.Handler;
import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Promise<T>
{
  private int jdField_a_of_type_Int = 0;
  private final Handler jdField_a_of_type_AndroidOsHandler = new Handler();
  private Exception jdField_a_of_type_JavaLangException;
  private T jdField_a_of_type_JavaLangObject;
  private final Thread jdField_a_of_type_JavaLangThread = Thread.currentThread();
  private final List<Callback<T>> jdField_a_of_type_JavaUtilList = new LinkedList();
  private final List<Callback<Exception>> jdField_b_of_type_JavaUtilList = new LinkedList();
  private boolean jdField_b_of_type_Boolean;
  
  private void a()
  {
    if (!jdField_a_of_type_Boolean)
    {
      if (this.jdField_a_of_type_JavaLangThread == Thread.currentThread()) {
        return;
      }
      throw new AssertionError("Promise must only be used on a single Thread.");
    }
  }
  
  private void a(Callback<T> paramCallback)
  {
    int i = this.jdField_a_of_type_Int;
    if (i == 1)
    {
      a(paramCallback, this.jdField_a_of_type_JavaLangObject);
      return;
    }
    if (i == 0) {
      this.jdField_a_of_type_JavaUtilList.add(paramCallback);
    }
  }
  
  private <S> void a(final Callback<S> paramCallback, final S paramS)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        paramCallback.onResult(paramS);
      }
    });
  }
  
  private void b(Callback<Exception> paramCallback)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_b_of_type_Boolean)) {
      throw new AssertionError("Do not add an exception handler to a Promise you have called the single argument Promise.then(Callback) on.");
    }
    int i = this.jdField_a_of_type_Int;
    if (i == 2)
    {
      a(paramCallback, this.jdField_a_of_type_JavaLangException);
      return;
    }
    if (i == 0) {
      this.jdField_b_of_type_JavaUtilList.add(paramCallback);
    }
  }
  
  public static <T> Promise<T> fulfilled(T paramT)
  {
    Promise localPromise = new Promise();
    localPromise.fulfill(paramT);
    return localPromise;
  }
  
  public void except(Callback<Exception> paramCallback)
  {
    a();
    b(paramCallback);
  }
  
  public void fulfill(T paramT)
  {
    a();
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int != 0)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Int = 1;
    this.jdField_a_of_type_JavaLangObject = paramT;
    Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
    while (localIterator.hasNext()) {
      a((Callback)localIterator.next(), paramT);
    }
    this.jdField_a_of_type_JavaUtilList.clear();
  }
  
  public T getResult()
  {
    if ((!jdField_a_of_type_Boolean) && (!isFulfilled())) {
      throw new AssertionError();
    }
    return (T)this.jdField_a_of_type_JavaLangObject;
  }
  
  public boolean isFulfilled()
  {
    a();
    return this.jdField_a_of_type_Int == 1;
  }
  
  public boolean isRejected()
  {
    a();
    return this.jdField_a_of_type_Int == 2;
  }
  
  public void reject()
  {
    reject(null);
  }
  
  public void reject(Exception paramException)
  {
    a();
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int != 0)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Int = 2;
    this.jdField_a_of_type_JavaLangException = paramException;
    Iterator localIterator = this.jdField_b_of_type_JavaUtilList.iterator();
    while (localIterator.hasNext()) {
      a((Callback)localIterator.next(), paramException);
    }
    this.jdField_b_of_type_JavaUtilList.clear();
  }
  
  public <R> Promise<R> then(final AsyncFunction<T, R> paramAsyncFunction)
  {
    a();
    final Promise localPromise = new Promise();
    a(new Callback()
    {
      public void onResult(T paramAnonymousT)
      {
        try
        {
          paramAsyncFunction.apply(paramAnonymousT).then(new Callback()new Callback
          {
            public void onResult(R paramAnonymous2R)
            {
              Promise.4.this.a.fulfill(paramAnonymous2R);
            }
          }, new Callback()
          {
            public void onResult(Exception paramAnonymous2Exception)
            {
              Promise.4.this.a.reject(paramAnonymous2Exception);
            }
          });
          return;
        }
        catch (Exception paramAnonymousT)
        {
          localPromise.reject(paramAnonymousT);
        }
      }
    });
    b(new Callback()
    {
      public void onResult(Exception paramAnonymousException)
      {
        localPromise.reject(paramAnonymousException);
      }
    });
    return localPromise;
  }
  
  public <R> Promise<R> then(final Function<T, R> paramFunction)
  {
    a();
    final Promise localPromise = new Promise();
    a(new Callback()
    {
      public void onResult(T paramAnonymousT)
      {
        try
        {
          localPromise.fulfill(paramFunction.apply(paramAnonymousT));
          return;
        }
        catch (Exception paramAnonymousT)
        {
          localPromise.reject(paramAnonymousT);
        }
      }
    });
    b(new Callback()
    {
      public void onResult(Exception paramAnonymousException)
      {
        localPromise.reject(paramAnonymousException);
      }
    });
    return localPromise;
  }
  
  public void then(Callback<T> paramCallback)
  {
    a();
    if (this.jdField_b_of_type_Boolean)
    {
      a(paramCallback);
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_b_of_type_JavaUtilList.size() != 0)) {
      throw new AssertionError("Do not call the single argument Promise.then(Callback) on a Promise that already has a rejection handler.");
    }
    then(paramCallback, new Callback()
    {
      public void onResult(Exception paramAnonymousException)
      {
        throw new Promise.UnhandledRejectionException("Promise was rejected without a rejection handler.", paramAnonymousException);
      }
    });
    this.jdField_b_of_type_Boolean = true;
  }
  
  public void then(Callback<T> paramCallback, Callback<Exception> paramCallback1)
  {
    a();
    a(paramCallback);
    b(paramCallback1);
  }
  
  public static abstract interface AsyncFunction<A, R>
  {
    public abstract Promise<R> apply(A paramA);
  }
  
  public static abstract interface Function<A, R>
  {
    public abstract R apply(A paramA);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L, 2L})
  private static @interface PromiseState {}
  
  public static class UnhandledRejectionException
    extends RuntimeException
  {
    public UnhandledRejectionException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\Promise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */