package com.wangjie.rapidrouter.compiler;

import com.google.auto.service.AutoService;

import com.squareup.javapoet.ClassName;
import com.wangjie.rapidrouter.api.annotations.RRParam;
import com.wangjie.rapidrouter.api.annotations.RRPoint;
import com.wangjie.rapidrouter.api.annotations.RRUri;
import com.wangjie.rapidrouter.api.annotations.RRouter;
import com.wangjie.rapidrouter.compiler.base.BaseAbstractProcessor;
import com.wangjie.rapidrouter.compiler.objs.ParamEntry;
import com.wangjie.rapidrouter.compiler.objs.RouterEntry;
import com.wangjie.rapidrouter.compiler.objs.UriEntry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com
 */
@AutoService(Processor.class)
public class RouterProcessor extends BaseAbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedTypesSet = new HashSet<>();
        supportedTypesSet.add(RRPoint.class.getCanonicalName());
        supportedTypesSet.add(RRouter.class.getCanonicalName());
        supportedTypesSet.add(RRUri.class.getCanonicalName());
        return supportedTypesSet;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        long start = System.currentTimeMillis();
        logger("[process]annotations: " + Arrays.toString(annotations.toArray()));
        try {
            logger("roundEnv.getRootElements(): " + roundEnv.getRootElements());
            RouterEntry routerEntry = new RouterEntry();

            doPointAnnotation(roundEnv.getElementsAnnotatedWith(RRPoint.class), routerEntry);

            for (Element e : roundEnv.getElementsAnnotatedWith(RRouter.class)) {
                doRouterAnnotation(e, routerEntry);
            }

            for (Element e : roundEnv.getElementsAnnotatedWith(RRUri.class)) {
                doUriAnnotation(e, routerEntry);
            }

            if(!routerEntry.getUriEntries().isEmpty()){
                try {
                    logger("RouterConfig generate START...");
                    routerEntry.brewJava().writeTo(filer);
                    logger("RouterConfig generate END...routerEntry: " + routerEntry);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Throwable throwable) {
                    logger("RouterConfig generate FAILED...routerEntry: " + routerEntry);
                    loggerE(throwable);
                }
            }

        } catch (Throwable throwable) {
            if (throwable instanceof RuntimeException) {
                throw throwable;
            } else {
                loggerE(throwable);
            }
        } finally {
            logger("[process] tasks: " + (System.currentTimeMillis() - start) + "ms");
        }

        return true;
    }

    private void doPointAnnotation(Set<? extends Element> eles, RouterEntry routerEntry) {
        int size;
        if (null == eles || 0 == (size = eles.size())) {
            return;
        }
        if (size != 1) {
            throw new RuntimeException("More than one Router Point Class Annotated @RRPoint.");
        }

        Element pointEle = eles.iterator().next();

        String configPackage = elementUtils.getPackageOf(pointEle).toString();
        if (null != configPackage && configPackage.length() > 0) {
            routerEntry.routerConfigPackage = configPackage;
        }
        String configClassName = pointEle.getAnnotation(RRPoint.class).value();
        if (configClassName.length() > 0) {
            routerEntry.routerConfigClassName = configClassName;
        }
    }

    private void doUriAnnotation(Element ele, RouterEntry routerEntry) {
        Element classEle = getElementOwnerElement(ele);
        if (null != classEle && !RRouter.class.getCanonicalName().equals(classEle.toString())) {
            List<UriEntry> uriEntries = routerEntry.getUriEntries();

            RRUri rrUri = ele.getAnnotation(RRUri.class);

            uriEntries.add(parseUriEntry(classEle, rrUri));
        }
    }

    private void doRouterAnnotation(Element ele, RouterEntry routerEntry) {
        Element classEle = getElementOwnerElement(ele);
        List<UriEntry> uriEntries = routerEntry.getUriEntries();

        RRouter rRouter = ele.getAnnotation(RRouter.class);

        for (RRUri rrUri : rRouter.value()) {
            uriEntries.add(parseUriEntry(classEle, rrUri));
        }

    }

    private UriEntry parseUriEntry(Element classEle, RRUri rrUri) {
        UriEntry uriEntry = new UriEntry();
        uriEntry.setRouterTargetClass(classEle);
        uriEntry.setScheme(rrUri.scheme());
        uriEntry.setHost(rrUri.host());

        List<ParamEntry> paramEntries = uriEntry.getParams();

        for (RRParam rrParam : rrUri.params()) {
            ParamEntry paramEntry = new ParamEntry();
            paramEntry.setName(rrParam.name());

            TypeMirror paramTypeMirror = getParamTypeMirror(rrParam);

            if (null == paramTypeMirror) {
                paramEntry.setType(ClassName.get(String.class));
            } else {
                paramEntry.setType(ClassName.get(paramTypeMirror));
            }

            paramEntries.add(paramEntry);
        }
        return uriEntry;
    }

    private static TypeMirror getParamTypeMirror(RRParam rrParam) {
        try {
            rrParam.type();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }

}
