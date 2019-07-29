import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;

public final class ThirdLineChecker implements MethodInterceptor {
    @NotNull
    private final Counter counter;

    public ThirdLineChecker(@NotNull Counter counter) {
        this.counter = counter;
    }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (counter.getNumber() % 3 == 0) {
            System.out.println("3-fold input:");
        }
        return invocation.proceed();
    }
}
