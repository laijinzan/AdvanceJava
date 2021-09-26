package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class XClassLoader extends ClassLoader {

    public static void main(String[] args) {

        try {
            Class<?> clazz = new XClassLoader().findClass("Hello.xlass");
            Method method = clazz.getMethod("hello");
            method.invoke(clazz.newInstance());

        } catch (ClassNotFoundException| NoSuchMethodException | InstantiationException| IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException{

        String fileName = this.getClass().getClassLoader().getResource(name).getPath();
            File file = new File(fileName);
            byte[] bytes = null;

            try(FileInputStream fis = new FileInputStream(file)){
                bytes = new byte[fis.available()];
                fis.read(bytes);

            } catch (IOException e){
              e.printStackTrace();
            }

            for (int i=0; i< bytes.length; i++){
                bytes[i] = (byte)(255 - bytes[i]);
            }

            return defineClass("Hello", bytes, 0, bytes.length);
        }
}
