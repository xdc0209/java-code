/*
 * Hello.java - MBean implementation for the Hello World MBean.
 * This class must implement all the Java methods declared in the
 * HelloMBean interface, with the appropriate behavior for each one.
 */

package com.xdc.basic.api.jmx.example.essential;

public class Hello implements HelloMBean
{
    @Override
    public void sayHello()
    {
        System.out.println("hello, world");
    }

    @Override
    public int add(int x, int y)
    {
        return x + y;
    }

    /*
     * Getter for the Name attribute. The pattern shown here is frequent: the getter returns a private field
     * representing the attribute value. In our case, the attribute value never changes, but for other attributes it
     * might change as the application runs. Consider an attribute representing statistics such as uptime or memory
     * usage, for example. Being read-only just means that it can't be changed through the management interface.
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /*
     * Getter for the CacheSize attribute. The pattern shown here is frequent: the getter returns a private field
     * representing the attribute value, and the setter changes that field.
     */
    @Override
    public int getCacheSize()
    {
        return this.cacheSize;
    }

    /*
     * Setter for the CacheSize attribute. To avoid problems with stale values in multithreaded situations, it is a good
     * idea for setters to be synchronized.
     */
    @Override
    public synchronized void setCacheSize(int size)
    {
        this.cacheSize = size;

        /*
         * In a real application, changing the attribute would typically have effects beyond just modifying the
         * cacheSize field. For example, resizing the cache might mean discarding entries or allocating new ones. The
         * logic for these effects would be here.
         */
        System.out.println("Cache size now " + this.cacheSize);
    }

    private final String     name               = "Reginald";
    private int              cacheSize          = DEFAULT_CACHE_SIZE;
    private static final int DEFAULT_CACHE_SIZE = 200;
}
