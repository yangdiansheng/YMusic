package com.yangdiansheng.lib_processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

public final class AppRegiserVisitor extends SimpleAnnotationValueVisitor8<Void,Void> {

    private Filer mFiler = null;
    private TypeMirror mTypeMirror = null;
    private String mPackageName = null;

    public void setFiler(Filer mFiler) {
        this.mFiler = mFiler;
    }

    @Override
    public Void visitString(String s, Void aVoid) {
        mPackageName = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror t, Void aVoid) {
        mTypeMirror = t;
        generateJavaCode();
        return aVoid;
    }

    private void generateJavaCode(){
        final TypeSpec targetActivty =
                TypeSpec.classBuilder("AppRegisterActivity")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .superclass(TypeName.get(mTypeMirror))
                .build();
        final JavaFile javaFile = JavaFile.builder(mPackageName+".wxapi",targetActivty)
                .addFileComment("微信广播接收器")
                .build();
        try{
            javaFile.writeTo(mFiler);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
