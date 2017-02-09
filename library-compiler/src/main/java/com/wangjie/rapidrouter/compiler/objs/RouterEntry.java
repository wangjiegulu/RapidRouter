package com.wangjie.rapidrouter.compiler.objs;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.wangjie.rapidrouter.compiler.constants.GuessClass;
import com.wangjie.rapidrouter.compiler.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class RouterEntry {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS");

    private List<UriEntry> uriEntries = new ArrayList<>();

    public List<UriEntry> getUriEntries() {
        return uriEntries;
    }

//    public static final String ROUTER_CONFIG_CLASS_NAME = "XRapidRouterConfig";

    public String routerConfigPackage;
    public String routerConfigClassName;

    public JavaFile brewJava() throws Throwable {
        if (null == routerConfigPackage || routerConfigPackage.length() == 0
                || null == routerConfigClassName || routerConfigClassName.length() == 0) {
            throw new RuntimeException("Have no Router Point Class  Annotated @RRPoint!");
        }

        TypeSpec.Builder result = TypeSpec.classBuilder(routerConfigClassName)
                .addModifiers(Modifier.PUBLIC)
                .superclass( // extends RapidRouterConfig
                        ClassName.bestGuess(GuessClass.BASE_ROUTER_CONFIG)
                );

        // calcRouterMapper method
        TypeName stringTypeName = ClassName.get(String.class);
        ClassName hashMapClassName = ClassName.get(HashMap.class);
        TypeName routerTargetTypeName = ClassName.bestGuess(GuessClass.ROUTER_TARGET);

        TypeName mapperTypeName = ParameterizedTypeName.get(hashMapClassName, stringTypeName,
                ParameterizedTypeName.get(hashMapClassName, stringTypeName, routerTargetTypeName)
        );

        MethodSpec.Builder calcRouterMapperMethodBuilder = MethodSpec.methodBuilder("calcRouterMapper")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(mapperTypeName, "routerMapper")
                .returns(mapperTypeName)
                .addStatement("$T<$T, $T> params = null", hashMapClassName, stringTypeName, ClassName.get(Class.class));

        for (UriEntry uriEntry : uriEntries) {
            calcRouterMapperMethodBuilder.addCode("// " + uriEntry.getRouterTargetClass() + "\n");

            List<ParamEntry> paramEntries = uriEntry.getParams();
            if (null == paramEntries || paramEntries.size() <= 0) {
                calcRouterMapperMethodBuilder.addStatement(
                        "getEnsureMap($L, $S).put($S, new $T($T.class, null))",
                        "routerMapper", uriEntry.getScheme(), uriEntry.getHost(), routerTargetTypeName, ClassName.get(uriEntry.getRouterTargetClass().asType()));
            } else {
                calcRouterMapperMethodBuilder.addStatement("$L = new $T<>()",
                        "params", hashMapClassName);
                for (ParamEntry paramEntry : paramEntries) {
                    LogUtil.logger("paramEntry: " + paramEntry);
                    calcRouterMapperMethodBuilder.addStatement("params.put($S, $T.class)", paramEntry.getName(), paramEntry.getType());
                }

                calcRouterMapperMethodBuilder.addStatement(
                        "getEnsureMap($L, $S).put($S, new $T($T.class, $L))",
                        "routerMapper", uriEntry.getScheme(), uriEntry.getHost(), routerTargetTypeName, ClassName.get(uriEntry.getRouterTargetClass().asType()), "params");

            }
        }

        calcRouterMapperMethodBuilder.addStatement("return $L", "routerMapper");

        result.addMethod(calcRouterMapperMethodBuilder.build());

        return JavaFile.builder(routerConfigPackage, result.build())
                .addFileComment("GENERATED CODE BY RapidRouter. DO NOT MODIFY! $S",
                        DATE_FORMAT.format(new Date(System.currentTimeMillis()))
                )
                .skipJavaLangImports(true)
                .build();
    }
}
