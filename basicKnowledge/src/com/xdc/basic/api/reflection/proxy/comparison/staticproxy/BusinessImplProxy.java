package com.xdc.basic.api.reflection.proxy.comparison.staticproxy;

class BusinessImplProxy implements Business
{
    private BusinessImpl businessImpl;

    public BusinessImplProxy()
    {
        super();
    }

    public BusinessImplProxy(BusinessImpl businessImpl)
    {
        super();
        this.businessImpl = businessImpl;
    }

    @Override
    public void doAction()
    {
        if (businessImpl == null)
        {
            businessImpl = new BusinessImpl();
        }

        doBefore();
        businessImpl.doAction();
        doAfter();
    }

    @Override
    public void doAction2()
    {
        if (businessImpl == null)
        {
            businessImpl = new BusinessImpl();
        }

        doBefore();
        businessImpl.doAction2();
        doAfter();

    }

    private void doBefore()
    {
        System.out.println("前置处理！");
    }

    private void doAfter()
    {
        System.out.println("后置处理！");
    }
}