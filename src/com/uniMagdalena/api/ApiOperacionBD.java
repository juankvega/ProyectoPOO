
package com.uniMagdalena.api;

import java.util.List;

public interface ApiOperacionBD<T, ID> 
{
  public int getSerial();
  
  public T insertInto(T obj, String ruta);
  
  public List<T> selectFrom();
  
  public int numRows();
  
  public Boolean deleteFrom(ID codigo);
  
  public T updateSet(ID codigo, T obj, String ruta);
  
  public T getOne(ID codigo);
}
