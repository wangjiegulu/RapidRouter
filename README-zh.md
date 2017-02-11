# RapidRouter

RapidRouter是Android端轻量级的路由框架，目前支持的特性：

- 支持通过URI跳转Activity
- 支持解析URI中参数到Intent的Extra中
- 支持一个Activity正则映射到多个URI
- 支持自定义路由策略
- 支持策略优先级来匹配 Target Activity
- 支持多个Module间的路由映射集成

## 如何使用

> 未上传到Maven中心库，目前的引入方式只能通过源码依赖

### RapidRouter初始化

构建路由配置`RouterConfiguration`类

```java
/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/9/17.
 */
@RRConfig(mappingName = "RapidRouterMappingHaquApp")
public class RouterConfiguration implements RapidRouterConfiguration {
    @NonNull
    @Override
    public RapidRouterStrategy[] configRapidRouterStrategies() {
        return new RapidRouterStrategy[]{
                new RapidRouterStrategySimple(),
                new RapidRouterStrategyRegular()
        };
    }

    @Nullable
    @Override
    public RapidRouterMapping[] configRapidRouterMappings() {
        return new RapidRouterMapping[]{
                new RapidRouterMappingHaquApp()
        };
    }
}
```

1. `RRConfig mappingName`：指定编译时生成的映射类名；

2. `configRapidRouterStrategy`：返回值用于配置路由所支持的所有策略（策略的先后顺序代表优先级），可以设置自定义的策略。

3. `configRapidRouterMappings `：配置编译时生成的映射类（可以设置多个，比如有多个module时）

初始化`RapidRouter`（比如，在`Application`中进行初始化）：

```java
public class RRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RapidRouter.init(new RouterConfiguration());
        RapidRouter.setOnRapidRouterListener(new ThisOnRapidRouterListener());
    }
}
```

`init()`方法用于初始化（策略、映射等）。

`setOnRapidRouterListener`用于设置全局的路由跳转监听

### 路由协议的配置方式

#### 使用 @RRUri 直接配置

```java
/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
@RRUri(uri = "rr://rapidrouter.a",
	params = {
	    		@RRParam(name = "p_name"),
	    		@RRParam(name = "p_age", type = int.class)
	}
)
public class AActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        Log.i(TAG, "p_age: " + intent.getIntExtra("p_age", -1) + ", p_name: " + intent.getStringExtra("p_name"));

    }
}
```

使用`@RRUri`注解来对`AActivity`进行路由配置。协议为`rr://rapidrouter.a`，并有两个参数：`String`类型（默认）的`p_name`和`int`类型的`p_age`。

#### 使用 @RRUri 正则配置

```java
@RRUri(uri = "~((rr)|(sc))://wang.*jie\\.[cx].*", params = {
        @RRParam(name = "paramOfCActivity", type = float.class)
})
public class CActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "paramOfCActivity: " + getIntent().getFloatExtra("paramOfCActivity", -1L));
    }
}
```

如果`uri`中以`~`开头，则`RapidRouter`认为该`uri`表示一个正则，所有满足这个正则的协议都会跳转到这个Activity。

#### 使用 @RRouter 直接配置

使用`@RRouter`可以配置多个Uri。

```java
/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
@RRouter(
        {
                @RRUri(uri = "rr://rapidrouter.a",
                        params = {
                                @RRParam(name = "p_name"),
                                @RRParam(name = "p_age", type = int.class)
                        }
                ),
                @RRUri(uri = "rr://rapidrouter_extra.a",
                        params = {
                                @RRParam(name = "p_name"),
                                @RRParam(name = "p_age", type = int.class)
                        }
                )
        }
)
public class AActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);\

        Intent intent = getIntent();

        Log.i(TAG, "p_age: " + intent.getIntExtra("p_age", -1) + ", p_name: " + intent.getStringExtra("p_name"));
    }
}
```

### 路由跳转方式

基本调用方式：

```java
RapidRouter.with(context)
    .uri("rr://rapidrouter.a?p_name=wangjie&p_age=18")
    .go();
```

跳转设置：

```java
RapidRouter.with(this)
	.intent(intent) // optional
	.uri("rr://rapidrouter.a?p_name=wangjie&p_age=18") // required
	.goBefore((routerStuff -> {...})) // optional
	.goAround((routerStuff -> {...})) // optional
	.goAfter(routerStuff -> {...}) // optional
	.targetNotFound(routerStuff -> {...}) // optional
	.error((routerStuff, throwable) -> {...}) // optional
	.strategies(...) // optional
	.go();
```

- **intent()**：如果设置，则会使用传入的`intent`来进行跳转；如果没有设置，则会自动创建。

- **goBefore()**：`RouterGoBeforeCallback`，在跳转前回调。返回`true`，则拦截掉全局的监听回调；返回`false`，则不拦截；

- **goAfter()**：`RouterGoAfterCallback`，在跳转后回调。返回`true`，则拦截掉全局的监听回调；返回`false`，则不拦截；

- **goAround()**：`RouterGoAroundCallback`，在执行跳转时回调。这个方法会拦截掉真正的跳转逻辑和全局的监听回调，这时可以在这里进行`startActivityForResult`等特别的跳转；

- **targetNotFound()**：`RouterTargetNotFoundCallback`，在路由表中匹配不到Activity时回调。返回`true`，则拦截掉全局的监听回调；返回`false`，则不拦截；

- **error()**：`RouterErrorCallback`，任意阶段出现异常则回调。典型的有`RapidRouterIllegalException`，当uri中携带的参数类型和注解中指定的类型不符时，抛出该异常，catch后可以进行自行处理；返回`true`，则拦截掉全局的监听回调；返回`false`，则不拦截；

- **strategies()**：调用该方法可以重新设置路由策略的优先级，默认优先级为`RapidRouterConfiguration::configRapidRouterStrategy()`中定义的。典型的场景是，一个uri可以同时匹配到多个Activity，这时可以从采取策略的不同来决定跳转到哪个Activity。



