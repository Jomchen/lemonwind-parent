package com.lemonwind.test.typetest.entity;

import java.util.List;
import java.util.Map;

public class CommonBean<T> extends CommonEntity<JoUser> {

  private List<JoUser> list;
  private Map<String, JoUser> map;
  private JoUser[] array;
  private List<String>[] listArray;
  private Map.Entry<String, Integer> mapEntry;
  private List<T> genericList;
  private T[] genericArray;

  public CommonBean() {
  }

  public CommonBean(List<JoUser> list, Map<String, JoUser> map, JoUser[] array,
                    List<String>[] listArray, Map.Entry<String, Integer> mapEntry, List<T> genericList, T[] genericArray) {
    this.list = list;
    this.map = map;
    this.array = array;
    this.listArray = listArray;
    this.mapEntry = mapEntry;
    this.genericList = genericList;
    this.genericArray = genericArray;
  }

  public List<JoUser> getList() {
    return list;
  }

  public void setList(List<JoUser> list) {
    this.list = list;
  }

  public Map<String, JoUser> getMap() {
    return map;
  }

  public void setMap(Map<String, JoUser> map) {
    this.map = map;
  }

  public JoUser[] getArray() {
    return array;
  }

  public void setArray(JoUser[] array) {
    this.array = array;
  }

  public List<String>[] getListArray() {
    return listArray;
  }

  public void setListArray(List<String>[] listArray) {
    this.listArray = listArray;
  }

  public Map.Entry<String, Integer> getMapEntry() {
    return mapEntry;
  }

  public void setMapEntry(Map.Entry<String, Integer> mapEntry) {
    this.mapEntry = mapEntry;
  }

  public List<T> getGenericList() {
    return genericList;
  }

  public void setGenericList(List<T> genericList) {
    this.genericList = genericList;
  }

  public T[] getGenericArray() {
    return genericArray;
  }

  public void setGenericArray(T[] genericArray) {
    this.genericArray = genericArray;
  }

}
