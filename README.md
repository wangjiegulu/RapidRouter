# RapidRouter
Router For Android

```java
/**
 * rr://rapidrouter.a?p_name=test&p_age=18
 * rr://rapidrouter_extra.a?p_name=test&p_age=18
 */
@RRouter(
        {
                @RRUri(scheme = "rr",
                        host = "rapidrouter.a",
                        params = {
                                @RRParam(name = "p_name"),
                                @RRParam(name = "p_age", type = int.class)
                        }
                ),
                @RRUri(scheme = "rr",
                        host = "rapidrouter_extra.a",
                        params = {
                                @RRParam(name = "p_name"),
                                @RRParam(name = "p_age", type = int.class)
                        }
                )
        }
)
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Log.i(TAG, "p_age: " + intent.getIntExtra("p_age", -1) + ", p_name: " + intent.getStringExtra("p_name"));

    }
}
```

```java
/**
 * rr://rapidrouter.b?id=112
 */
@RRUri(scheme = "rr", host = "rapidrouter.b", params = {
        @RRParam(name = "id", type = long.class)
})
public class OtherActivity extends BaseActivity{
    private static final String TAG = OtherActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Log.i(TAG, "intent: " + intent.getLongExtra("id", -1L));
    }
}
```

```java
RapidRouter.with(context)
	.uri("rr://rapidrouter.b?id=234")
	.go();
```

```java
RapidRouter.with(this)
    .uri("rr://rapidrouter.a?p_name=wangjie&p_age=18")
    .intent(intent)
    .goBefore(routerStuff -> {...})
    .goAfter(routerStuff -> {...})
    .goAround(routerStuff -> {...})
    .targetNotFound(routerStuff -> {...})
    .error((routerStuff, throwable) -> {...})
    .go();
```
