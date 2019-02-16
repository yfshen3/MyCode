package org.lf.admin.api.baseapi.core;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PageBaseUrl {

    String path();

}
