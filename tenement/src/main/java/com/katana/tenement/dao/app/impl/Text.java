package com.katana.tenement.dao.app.impl;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Data
class Person extends Get<Person>{
    private String key;
}
class GetClass<T>{

}
class  Get<T> {
    T get() throws IllegalAccessException, InstantiationException {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType)type;
        Type[] types = parameterizedType.getActualTypeArguments();
        Class clazz = (Class)(types[0]);
        Field[] fields = clazz.getDeclaredFields();
        Object object = clazz.newInstance();
        for(Field field : fields){
            field.setAccessible(true);
            field.set(object,"a");
        }
        return (T)object;
    }
}
public class Text {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
      Person person = new Person();
        System.out.println(person.get());
    }
}
