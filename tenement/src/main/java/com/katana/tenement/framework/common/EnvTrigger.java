package com.katana.tenement.framework.common;

import org.springframework.core.env.Environment;

import java.util.function.Supplier;


public class EnvTrigger<T> {

    private final static Environment environment = SpringUtils.getBean(Environment.class);

    private Supplier<T> devSupplier;

    private Supplier<T> prodSupplier;


    public EnvTrigger prod(Supplier<T> supplier) {
        this.prodSupplier = supplier;
        return this;
    }

    public EnvTrigger dev(Supplier<T> supplier) {
        this.devSupplier = supplier;
        return this;
    }

    public T exec() {
        return environment.acceptsProfiles("test", "dev") ? devSupplier.get() :
                environment.acceptsProfiles("prod") ? prodSupplier.get() : null;
    }

}
