package edu.school21.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;

import java.net.URL;
import java.util.*;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Class curClass = getClassForWork("edu.school21.models");
        Field[] fields = curClass.getDeclaredFields();
        Method[] methods = curClass.getDeclaredMethods();
        printFields(fields);
        printMethods(methods);
        Object obj = createObject(curClass);
        updateField(obj);
        executeMethod(obj);
    }

    private static Class getClassForWork(String classPackage) {
        try {
            System.out.println("Classes:");
            Class[] allClasses = getClasses(classPackage);
            for (Class elem : allClasses) {
                System.out.println("  - " + elem.getSimpleName());
            }
            System.out.println("---------------------");
            System.out.println("Enter class name:");

            String enteredClass = scanner.next();
            for (Class elem : allClasses) {
                if (elem.getSimpleName().equals(enteredClass)) {
                    return elem;
                }
            }
            System.err.println("This class does not exist: " + enteredClass);
            System.exit(1);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[0]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    private static void printFields(Field[] fields) {
        System.out.println("---------------------");
        System.out.println("fields:");
        for (Field field : fields) {
            System.out.println("\t"
                    + getLastFromType(field.getAnnotatedType().getType().getTypeName())
                    + " " + field.getName());
        }
    }

    private static void printMethods(Method[] methods) {
        System.out.println("methods:");
        for (Method method : methods) {

            System.out.println("\t"
                    + getLastFromType(method.getReturnType().toString())
                    + " " + method.getName()
                    + "(" + getParametersType(method) + ")");
        }
    }

    private static String getParametersType(Method method) {
        StringBuilder result = new StringBuilder();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            String space = ", ";
            if (i + 1 == parameters.length) {
                space = "";
            }
            String[] parameterTypeArr = parameters[i].getType().toString().split(" ");
            if (parameterTypeArr.length > 1) {
                result.append(getLastFromType(parameterTypeArr[1])).append(space);
            } else {
                result.append(getLastFromType(parameterTypeArr[0])).append(space);
            }
        }
        return result.toString();
    }

    private static String getLastFromType(String string) {
        String[] temp = string.split("\\.");
        return temp[temp.length - 1];
    }

    private static Object createObject(Class curClass) {
        System.out.println("---------------------");
        System.out.println("Let’s create an object.");
        Object obj;
        try {
            obj = curClass.newInstance();
            Field[] objFields = obj.getClass().getDeclaredFields();
            for (Field field : objFields) {
                System.out.println(field.getName() + ":");
                changeField(obj, field);
            }
            System.out.println("Object created: " + obj);
            return obj;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void changeField(Object obj, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        switch (getLastFromType(field.getType().getName())) {
            case "String":
                field.set(obj, getStringFromUser());
                break;
            case "Integer":
                field.set(obj, getIntegerFromUser());
                break;
            case "Double":
                field.set(obj, getDoubleFromUser());
                break;
            case "Boolean":
                field.set(obj, getBooleanFromUser());
                break;
            case "Long":
                field.set(obj, getLongFromUser());
                break;
        }
    }

    private static String getStringFromUser() {
        return scanner.next();
    }

    private static Integer getIntegerFromUser() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private static Double getDoubleFromUser() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private static Boolean getBooleanFromUser() {
        try {
            return scanner.nextBoolean();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private static Long getLongFromUser() {
        try {
            return scanner.nextLong();
        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateField(Object obj) {
        System.out.println("---------------------");
        System.out.println("Enter name of the field for changing:");
        String fieldToChangeName = scanner.next();
        try {
            Field field = obj.getClass().getDeclaredField(fieldToChangeName);
            System.out.println("Enter " + getLastFromType(field.getType().getName()) + " value");
            changeField(obj, field);
            System.out.println("Object updated: " + obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void executeMethod(Object obj) {
        System.out.println("---------------------");
        System.out.println("Enter name of the method for call:");
        String methodName = scanner.next();
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(normalizeMethod(methodName))) {
                Class<?>[] parametersTypes = method.getParameterTypes();
                if (parametersTypes.length > 0) {
                    Object[] parameters = new Object[parametersTypes.length];
                    for (int i = 0; i < parametersTypes.length; i++) {
                        System.out.println("Enter " + getLastFromType(parametersTypes[i].getName()) + " value:");
                        switch (getLastFromType(parametersTypes[i].getName())) {
                            case "String":
                                parameters[i] = scanner.next();
                                break;
                            case "Integer":
                                parameters[i] = scanner.nextInt();
                                break;
                            case "Double":
                                parameters[i] = scanner.nextDouble();
                                break;
                            case "Boolean":
                                parameters[i] = scanner.nextBoolean();
                                break;
                            case "Long":
                                parameters[i] = scanner.nextLong();
                                break;
                            default:
                                System.err.println("This type of parameter is not declared");
                                System.exit(-1);
                        }
                    }
                    try {
                        if (getLastFromType(method.getReturnType().getName()).equals("void")) {
                            method.invoke(obj, parameters);
                        } else {
                            System.out.println("Method returned:");
                            System.out.println(method.invoke(obj, parameters));
                        }

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        if (getLastFromType(method.getReturnType().getName()).equals("void")) {
                            method.invoke(obj);
                        } else {
                            System.out.println("Method returned:");
                            System.out.println(method.invoke(obj));
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }

    }

    private static String normalizeMethod(String str) {
        if (str.contains("(")) {
            return str.substring(0, str.indexOf('('));
        } else {
            return str;
        }
    }
}
