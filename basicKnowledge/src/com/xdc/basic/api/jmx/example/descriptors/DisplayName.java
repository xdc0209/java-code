/**
 * DisplayName.java - This annotation allows to supply
 * a display name for a method in the MBean interface.
 */

package com.xdc.basic.api.jmx.example.descriptors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.management.DescriptorKey;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayName
{
    @DescriptorKey("displayName")
    String value();
}
