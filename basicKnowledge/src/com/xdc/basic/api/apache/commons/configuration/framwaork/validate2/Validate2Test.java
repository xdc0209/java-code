package com.xdc.basic.api.apache.commons.configuration.framwaork.validate2;

import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.results.atomic.ValidateResult;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.Validator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.DoubleValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.EnumStringValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.NotEmptyStringValidator;
import com.xdc.basic.api.apache.commons.configuration.framwaork.validate2.validators.atomic.RegexValidator;

public class Validate2Test
{
    public static void main(String[] args)
    {
        final Validator validator = Validators.and(new NotEmptyStringValidator(),
                Validators.and(new DoubleValidator(10.0, 30.0),
                        Validators.or(new EnumStringValidator("16.0", "20.0", "25.0"), new RegexValidator("[0-9\\.]*")),
                        Validators.not(new EnumStringValidator("15.0", "20.0", "25.0"))));

        Thread thread1 = new Thread(new Runnable()
        {
            public void run()
            {
                ValidateResult validateResult = validator.validate("17.0");
                System.out.println(validateResult);
                System.out.println(ValidateResult.isPassed(validateResult));
                System.out.println();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable()
        {
            public void run()
            {
                ValidateResult validateResult = validator.validate("asdfg");
                System.out.println(validateResult);
                System.out.println(ValidateResult.isPassed(validateResult));
                System.out.println();
            }
        });
        thread2.start();

        System.out.println();
    }
}
