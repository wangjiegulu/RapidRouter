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

    public String routerMappingPackage;
    public String routerMappingClassName;

    public JavaFile brewJava() throws Throwable {
        if (null == routerMappingPackage || routerMappingPackage.length() == 0
                || null == routerMappingClassName || routerMappingClassName.length() == 0) {
            throw new RuntimeException("Have no Router Point Class  Annotated @RRPoint!");
        }

        TypeSpec.Builder result = TypeSpec.classBuilder(routerMappingClassName)
                .addModifiers(Modifier.PUBLIC)
                .superclass( // extends RapidRouterMapping
                        ClassName.bestGuess(GuessClass.BASE_ROUTER_MAPPING)
                );

        // calcSimpleRouterMapper method
        TypeName stringTypeName = ClassName.get(String.class);
        ClassName hashMapClassName = ClassName.get(HashMap.class);
        TypeName routerTargetTypeName = ClassName.bestGuess(GuessClass.ROUTER_TARGET);

        TypeName simpleMapperTypeName = ParameterizedTypeName.get(hashMapClassName, stringTypeName,
                ParameterizedTypeName.get(hashMapClassName, stringTypeName, routerTargetTypeName)
        );

        TypeName regMapperTypeName = ParameterizedTypeName.get(hashMapClassName, stringTypeName, routerTargetTypeName);

        // calcSimpleRouterMapper method
        // public HashMap<String, HashMap<String, RouterTarget>> calcSimpleRouterMapper(HashMap<String, HashMap<String, RouterTarget>> routerMapper) {
        MethodSpec.Builder calcSimpleMapperMethodBuilder = MethodSpec.methodBuilder("calcSimpleRouterMapper")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(simpleMapperTypeName, "routerMapper")
                .returns(simpleMapperTypeName)
                .addStatement("$T<$T, $T> params", hashMapClassName, stringTypeName, ClassName.get(Class.class));

        // calcRegRouterMapper method
        // public HashMap<String, RouterTarget> calcRegRouterMapper(HashMap<String, RouterTarget> routerMapper) {
        MethodSpec.Builder calcRegMapperMethodBuilder = MethodSpec.methodBuilder("calcRegRouterMapper")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(regMapperTypeName, "routerMapper")
                .returns(regMapperTypeName)
                .addStatement("$T<$T, $T> params", hashMapClassName, stringTypeName, ClassName.get(Class.class));


        for (UriEntry uriEntry : uriEntries) {
            String uriRegular = uriEntry.getUriRegular();
            if (null == uriRegular || 0 == uriRegular.length()) {
                onSimpleMapperMethodStatement(hashMapClassName, routerTargetTypeName, calcSimpleMapperMethodBuilder, uriEntry);
            } else {
                onRegularMapperMethodStatement(hashMapClassName, routerTargetTypeName, calcRegMapperMethodBuilder, uriEntry);
            }
        }

        calcSimpleMapperMethodBuilder.addStatement("return $L", "routerMapper");
        calcRegMapperMethodBuilder.addStatement("return $L", "routerMapper");

        result.addMethod(calcSimpleMapperMethodBuilder.build());
        result.addMethod(calcRegMapperMethodBuilder.build());

        return JavaFile.builder(routerMappingPackage, result.build())
                .addFileComment("GENERATED CODE BY RapidRouter. DO NOT MODIFY! $S",
                        DATE_FORMAT.format(new Date(System.currentTimeMillis()))
                )
                .skipJavaLangImports(true)
                .build();
    }

    private void onSimpleMapperMethodStatement(ClassName hashMapClassName, TypeName routerTargetTypeName, MethodSpec.Builder calcSimpleMapperMethodBuilder, UriEntry uriEntry) {
        calcSimpleMapperMethodBuilder.addCode("// " + uriEntry.getRouterTargetClass() + "\n");

        List<ParamEntry> paramEntries = uriEntry.getParams();
        int paramSize = null == paramEntries ? 0 : paramEntries.size();
        if (paramSize <= 0) {
            calcSimpleMapperMethodBuilder.addStatement(
                    "getEnsureMap($L, $S).put($S, new $T($T.class, null))",
                    "routerMapper", uriEntry.getScheme(), uriEntry.getHost(), routerTargetTypeName, ClassName.get(uriEntry.getRouterTargetClass().asType()));
        } else {
            calcSimpleMapperMethodBuilder.addStatement("$L = new $T<>(" + paramSize + ")",
                    "params", hashMapClassName);
            for (ParamEntry paramEntry : paramEntries) {
                LogUtil.logger("[Simple]paramEntry: " + paramEntry);
                calcSimpleMapperMethodBuilder.addStatement("params.put($S, $T.class)", paramEntry.getName(), paramEntry.getType());
            }

            calcSimpleMapperMethodBuilder.addStatement(
                    "getEnsureMap($L, $S).put($S, new $T($T.class, $L))",
                    "routerMapper", uriEntry.getScheme(), uriEntry.getHost(), routerTargetTypeName, ClassName.get(uriEntry.getRouterTargetClass().asType()), "params");

        }
    }

    private void onRegularMapperMethodStatement(ClassName hashMapClassName, TypeName routerTargetTypeName, MethodSpec.Builder calcRegMapperMethodBuilder, UriEntry uriEntry) {
        calcRegMapperMethodBuilder.addCode("// " + uriEntry.getRouterTargetClass() + "\n");

        List<ParamEntry> paramEntries = uriEntry.getParams();
        int paramSize = null == paramEntries ? 0 : paramEntries.size();
        if (paramSize <= 0) {
            calcRegMapperMethodBuilder.addStatement(
                    "$L.put($S, new $T($T.class, null))",
                    "routerMapper", uriEntry.getUriRegular(), routerTargetTypeName, ClassName.get(uriEntry.getRouterTargetClass().asType()));
        } else {
            calcRegMapperMethodBuilder.addStatement("$L = new $T<>(" + paramSize + ")",
                    "params", hashMapClassName);
            for (ParamEntry paramEntry : paramEntries) {
                LogUtil.logger("[Reg]paramEntry: " + paramEntry);
                calcRegMapperMethodBuilder.addStatement("params.put($S, $T.class)", paramEntry.getName(), paramEntry.getType());
            }

            calcRegMapperMethodBuilder.addStatement(
                    "$L.put($S, new $T($T.class, $L))",
                    "routerMapper", uriEntry.getUriRegular(), routerTargetTypeName, ClassName.get(uriEntry.getRouterTargetClass().asType()), "params");

        }
    }


}
