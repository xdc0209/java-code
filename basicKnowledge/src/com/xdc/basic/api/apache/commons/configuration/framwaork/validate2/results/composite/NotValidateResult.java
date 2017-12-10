package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.composite;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;

public class NotValidateResult extends ValidateResult
{
    @SuppressWarnings("unused")
    private ValidateResult validateResult;

    public NotValidateResult(String value)
    {
        // validator和detail字段对于组合类校验结果是无用的，只是无奈于依赖继承体系，强加不需要的属性。
        // 也可以调整继承体系为：ValidateResult(AtomicValidateResult, CompositeValidateResult(AndValidateResult, OrValidateResult, NotValidateResult))
        // 调整后的继承体系增加了继承体系的复杂性，权衡利弊，还是选择接受两个无用的字段。
        super(value, null, null, null);
    }

    public void setValidateResult(ValidateResult validateResult)
    {
        this.validateResult = validateResult;
    }
}
