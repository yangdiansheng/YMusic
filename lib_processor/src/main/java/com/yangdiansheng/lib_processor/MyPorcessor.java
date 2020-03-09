package com.yangdiansheng.lib_processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.yangdiansheng.lib_annotations.anntations.AppRegisterGenerator;
import com.yangdiansheng.lib_annotations.anntations.EntryGenerator;
import com.yangdiansheng.lib_annotations.anntations.PayEntryGenerator;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class MyPorcessor extends AbstractProcessor {

    //文件相关的辅助类
    private Filer mFiler;
    //元素相关的辅助类
    private Elements mElementUtils;
    //日志相关的辅助类
    private Messager mMessager;

    //被注解处理工具调用 参数提供了很多工具类
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
        printLog("AbstractProcessor:" + "mInit");
    }

    //指定这个注解处理器是注册给哪个注解的
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation: supportAnnotations){
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations(){
        final  Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }

    //相当于每个处理器的主函数 在这里写扫码 评估 处理注解的代码 以及生成的java文件
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateEntryCode(roundEnv);
        generatePayEntryCode(roundEnv);
        generateAppRegisterEntryCode(roundEnv);
//        printLog("AbstractProcessor:" + "process");
//        generateEmptyClass();
        return true;
    }

    /**
     * 创建一个空的java类
     */
    private void generateEmptyClass() {
        //创建类
        TypeSpec generateEmptyClass = TypeSpec.classBuilder("GenerateEmptyClass")
                .addModifiers(Modifier.PUBLIC)
                .build();
        //创建类存放的包名
        JavaFile javaFile = JavaFile.builder("com.yangdiansheng.music.empty", generateEmptyClass).build();

        //创建类
        try {
            javaFile.writeTo(mFiler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printLog(String msg, Object... args) {
        //printLog("Generate file failed, reason: %s", "init");
        mMessager.printMessage(Diagnostic.Kind.WARNING, String.format(msg, args));
    }

    private void printLog(String msg) {
        //printLog("init");
        mMessager.printMessage(Diagnostic.Kind.WARNING, msg);
    }


    /**
     *
     * @param env 代码的运行环境
     * @param visitor
     */
    private void scan(RoundEnvironment env,
                      Class<? extends Annotation> annotation,
                      AnnotationValueVisitor visitor){
        //获取代码中所使用的注解字段
        for(Element element:env.getElementsAnnotatedWith(annotation)){
            final List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            for(AnnotationMirror annotationMirror:annotationMirrors){
                final Map<? extends ExecutableElement,? extends AnnotationValue> elementValue = annotationMirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValue.entrySet()){
                    entry.getValue().accept(visitor,null);
                }
            }
        }
    }

    private void generateEntryCode(RoundEnvironment env){
        final EntryVisitor entryVisitor = new EntryVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(env,EntryGenerator.class,entryVisitor);
    }

    private void generatePayEntryCode(RoundEnvironment env){
        final PayEntryVisitor entryVisitor = new PayEntryVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class,entryVisitor);
    }

    private void generateAppRegisterEntryCode(RoundEnvironment env){
        final AppRegiserVisitor entryVisitor = new AppRegiserVisitor();
        entryVisitor.setFiler(processingEnv.getFiler());
        scan(env,AppRegisterGenerator.class,entryVisitor);
    }


}
