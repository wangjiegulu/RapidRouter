# RapidRouter

> [中文版本](README-zh.md)

RapidRouter is a lightweight router framework for Android, Currently supported features:

- Support for jumping activities through URI.
- Support for parsing parameters from URI to Extras of Intent.
- Supports Activities to be mapped to multiple URIs with a regular expression.
- Support custom routing Strategies.
- Support for mapping target activities by strategic priority.
- Support for mapping and integrate routing between multiple modules.

## How to use

### build.gradle([Check newest version](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.wangjiegulu%20RapidRouter)):
```groovy
compile "com.github.wangjiegulu:rapidrouter:x.x.x"
compile "com.github.wangjiegulu:rapidrouter-api:x.x.x"
annotationProcessor "com.github.wangjiegulu:rapidrouter-compiler:x.x.x"
```


### RapidRouter initialization

Create routing configuration extends `RouterConfiguration` class.

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

1. `RRConfig mappingName`: Specifies the name of the mapping class generated at compile time.

2. `configRapidRouterStrategy()`: The return value is used to configure all the strategies supported by the route (the order of strategies is represent priority), You can also set custom strategies.

3. `configRapidRouterMappings()`: Configure the mapping class generated in compile time (can be set more than one, for example, when there are multiple modules).

`RapidRouter` initialize (for example, initialize in `Application`):

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

`init()` method is used for initialization (strategy, mapping, etc.).

`setOnRapidRouterListener()` is used for set a global route listener.

### The routing protocol configuration mode

#### Direct configuration with @RRUri

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

Routing configuration for `AActivity` use `@RRUri` annotation. Uri is `rr://rapidrouter.a`, and there are two parameters: `p_name` of `String` type (default), and `p_age` of `int` type.

#### Regular expression configuration use @RRUri

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

If `uri` begins with `~` , then `RapidRouter` considers that `uri` represent a regular expression, and all protocols that match this regular expression will jump to this activity.

#### Configuration with @RRouter

Can be configured multiple Uris use `@RRouter`.

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

### Route Jump Mode

Basic invoke:

```java
RapidRouter.with(context)
    .uri("rr://rapidrouter.a?p_name=wangjie&p_age=18")
    .go();
```

Jump configuration:

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

- **intent()**: If set, it will be jumped with the specified `intent`; if not, it will be created automatically.

- **goBefore()**: `RouterGoBeforeCallback`, Callback before jump, Return `true`, then intercept the global listener callback; return` false`, it does not intercept;

- **goAfter()**: `RouterGoAfterCallback`, Callback after jump, Return `true`, then intercept the global listener callback; return` false`, it does not intercept;

- **goAround()**: `RouterGoAroundCallback`, Callback when jumping. This method will intercept the real jump logic and global listener callback, you can do some special jump logic instead original logic here, such as `startActivityForResult()`.

- **targetNotFound()**: `RouterTargetNotFoundCallback`, Callback when the uri can not match any activity. Return `true`, then intercept the global listener callback; return` false`, it does not intercept;

- **error()**: `RouterErrorCallback`, Callback when an exception is thrown any time. Typically a `RapidRouterIllegalException` is thrown when the type of parameters in the uri does not match the type specified in the annotation, catch it and handled by yourself; Return `true`, then intercept the global listener callback; return` false`, it does not intercept;

- **strategies()**: This method can be used to reset the priority of the routing strategies. The default priority is defined in `RapidRouterConfiguration :: configRapidRouterStrategy ()`.
The typical case is that a uri can be matched to multiple activities, this time from the use of different strategies to decide to jump to which Activity.


License
=======

    Copyright 2017 Wang Jie

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


