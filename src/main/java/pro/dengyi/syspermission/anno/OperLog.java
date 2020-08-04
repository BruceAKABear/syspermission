package pro.dengyi.syspermission.anno;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {

    /**
     * 操作模块名称
     *
     * @return
     */
    String opModule() default "";

    /**
     * 操作类型
     *
     * @return
     */
    String opType() default "";

    /**
     * 操作描述
     *
     * @return
     */
    String opDesc() default "";
}
