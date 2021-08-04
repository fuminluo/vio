package priv.rabbit.vio.reflection;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MyMetaObject {

    private final Object originalObject;
    private final ObjectWrapper objectWrapper;
    private final ObjectFactory objectFactory;
    private final ObjectWrapperFactory objectWrapperFactory;
    private final ReflectorFactory reflectorFactory;

    private boolean isCollection;

    MyMetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        this.originalObject = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;

        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } /*else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        }*/ else if (object instanceof Map) {
            this.objectWrapper = new MyMapWrapper(this, (Map) object);
        } else if (object instanceof Collection) {
            this.objectWrapper = new MyCollectionWrapper(this, (Collection) object);
        } else {
            this.objectWrapper = new MyBeanWrapper(this, object);
        }
    }

    public static MyMetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        if (object == null) {
            //TODO
            return null;
            //return SystemMetaObject.NULL_META_OBJECT;
        } else {
            return new MyMetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
        }
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    public String findProperty(String propName, boolean useCamelCaseMapping) {
        return objectWrapper.findProperty(propName, useCamelCaseMapping);
    }

    public String[] getGetterNames() {
        return objectWrapper.getGetterNames();
    }

    public String[] getSetterNames() {
        return objectWrapper.getSetterNames();
    }

    public Class<?> getSetterType(String name) {
        return objectWrapper.getSetterType(name);
    }

    public Class<?> getGetterType(String name) {
        return objectWrapper.getGetterType(name);
    }

    public boolean hasSetter(String name) {
        return objectWrapper.hasSetter(name);
    }

    public boolean hasGetter(String name) {
        return objectWrapper.hasGetter(name);
    }

    public Object getValue(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MyMetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            if (metaValue == null) {
                return null;
            } else {
                if (isCollection) {
                    if (metaValue.originalObject instanceof List) {
                        List<Object> objectList = new ArrayList<>();
                        for (Object obj : (List) (metaValue.originalObject)) {
                            System.out.println("** IndexedName = " + prop.getIndexedName() + ", Children = " + prop.getChildren());
                            MyMetaObject metaObject = MyMetaObject.forObject(obj, objectFactory, objectWrapperFactory, reflectorFactory);
                            objectList.add(metaObject.getValue(prop.getChildren()));
                        }
                        return objectList;
                    } else if (metaValue.originalObject instanceof Object[]) {
                        return (Object[]) (metaValue.originalObject);
                    } else if (metaValue.originalObject instanceof char[]) {
                        return ((char[]) (metaValue.originalObject));
                    } else if (metaValue.originalObject instanceof boolean[]) {
                        return ((boolean[]) (metaValue.originalObject));
                    } else if (metaValue.originalObject instanceof byte[]) {
                        return ((byte[]) (metaValue.originalObject));
                    } else if (metaValue.originalObject instanceof double[]) {
                        return ((double[]) (metaValue.originalObject));
                    } else if (metaValue.originalObject instanceof float[]) {
                        return ((float[]) (metaValue.originalObject));
                    } else if (metaValue.originalObject instanceof int[]) {
                        return ((int[]) (metaValue.originalObject));
                    } else if (metaValue.originalObject instanceof long[]) {
                        return ((long[]) (metaValue.originalObject));
                    } else if (metaValue.originalObject instanceof short[]) {
                        return ((short[]) (metaValue.originalObject));
                    } else {
                        throw new ReflectionException("The '" + prop.getName() + "' property of " + metaValue.originalObject + " is not a List or Array.");
                    }
                }
                return metaValue.getValue(prop.getChildren());
            }
        } else {
            //System.out.println("## IndexedName = " + prop.getIndexedName());
            return objectWrapper.get(prop);
        }

    }

    public void setValue(String name, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MyMetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            //TODO
            if (metaValue == null) {
                if (value == null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    //metaValue = objectWrapper.instantiatePropertyValue(name, prop, objectFactory);
                }
            }
            metaValue.setValue(prop.getChildren(), value);
        } else {
            objectWrapper.set(prop, value);
        }
    }

    public MyMetaObject metaObjectForProperty(String name) {
        Object value = getValue(name);
        if (value instanceof Collection) {
            isCollection = true;
        }
        return MyMetaObject.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public ObjectWrapper getObjectWrapper() {
        return objectWrapper;
    }

    public boolean isCollection() {
        return objectWrapper.isCollection();
    }

    public void add(Object element) {
        objectWrapper.add(element);
    }

    public <E> void addAll(List<E> list) {
        objectWrapper.addAll(list);
    }

}
