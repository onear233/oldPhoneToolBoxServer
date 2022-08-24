package live.midreamsheep.frame.scanner.annotation.scan;

import live.midreamsheep.optb.ApplicationStarter;
import live.midreamsheep.optb.executes.ExecuteHandlerInter;
import live.midreamsheep.optb.executes.ExecuteInit;
import live.midreamsheep.optb.executes.ExecutesController;
import live.midreamsheep.frame.scanner.annotation.handler.ExecuteHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ScannerProcessor {
    public static void process(Class<?> clazz) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scan scan = clazz.getAnnotation(Scan.class);
        if (scan == null) {
            System.err.println("没有找到Scan注解");
            System.exit(1);
        }
        String path = scan.value();
        for (String s : getClassName(path, true)) {
            injectHandler(s);
        }

    }

    /**
     * 获取某包下所有类
     * @param packageName 包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName, boolean childPackage) throws IOException {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                fileNames = getClassNameByFile(url.getPath(), null);
            } else if (type.equals("jar")) {
                fileNames = getClassNameByJar(url.getPath(), childPackage);
            }
        } else {
            fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     * @param filePath 文件路径
     * @param className 类名集合
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : Objects.requireNonNull(childFiles)) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName));
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     * @param jarPath jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) throws IOException {
        List<String> myClassName = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (!entryName.endsWith(".class")) {
                    continue;
                }
                if (childPackage) {
                    if (!entryName.startsWith(packagePath)) {
                        continue;
                    }
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                    continue;
                }
                int index = entryName.lastIndexOf("/");
                String myPackagePath;
                if (index != -1) {
                    myPackagePath = entryName.substring(0, index);
                } else {
                    myPackagePath = entryName;
                }
                if (myPackagePath.equals(packagePath)) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     * @param urls URL集合
     * @param packagePath 包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) throws IOException {
        List<String> myClassName = new ArrayList<String>();
        if (urls != null) {
            for (URL url : urls) {
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }
    private static void injectHandler(String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName(className);
        ExecuteHandler annotation = aClass.getAnnotation(ExecuteHandler.class);
        if(annotation==null){
            return;
        }
        //判断aClass是否实现了ExecuteHandlerInter接口
        if(!ExecuteHandlerInter.class.isAssignableFrom(aClass)){
            System.err.println("类"+className+"没有实现ExecuteHandlerInter接口");
            System.exit(1);
        }
        String value = annotation.value();
        Object o = aClass.getConstructor().newInstance();
        Object proxyInstance = Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), (proxy, method, args) -> {
            if(!ApplicationStarter.isFix) {
                System.out.println("执行" + className + "的方法" + method.getName() + "开始 key是" + value);
                Object invoke = method.invoke(o, args);
                System.out.println("执行" + className + "的方法" + method.getName() + "结束 key是" + value);
                return invoke;
            }
            return null;
        });
        if(o instanceof ExecuteInit init){
            init.init(value, (ExecuteHandlerInter) proxyInstance);
        }
        ExecutesController.putExecuteHandlers(value, (ExecuteHandlerInter) proxyInstance);
    }
}
