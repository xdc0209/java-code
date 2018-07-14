package com.roadrantz.security;

import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.roadrantz.domain.Motorist;

public class PasswordEncodingAdvice
{
    private static final Logger LOGGER = Logger.getLogger(PasswordEncodingAdvice.class);

    public PasswordEncodingAdvice()
    {
    }

    public Object encodeMotoristPassword(ProceedingJoinPoint pjp) throws Throwable
    {
        Object[] args = pjp.getArgs();
        if (args.length != 1 || !(args[0] instanceof Motorist))
        {
            return pjp.proceed();
        }

        Motorist motorist = (Motorist) args[0];
        String encodedPassword = passwordEncoder.encodePassword(motorist.getPassword().trim(),
                saltSource.getSalt(null));

        LOGGER.debug("SALT:  " + saltSource.getSalt(null));
        LOGGER.debug("UNENCODED:  " + motorist.getPassword());
        LOGGER.debug("ENCODED:  " + encodedPassword);

        motorist.setPassword(encodedPassword);
        return pjp.proceed(new Object[] { motorist });
    }

    // injected
    private PasswordEncoder passwordEncoder;

    public void setPasswordEncoder(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    private SaltSource saltSource;

    public void setSaltSource(SaltSource saltSource)
    {
        this.saltSource = saltSource;
    }
}
