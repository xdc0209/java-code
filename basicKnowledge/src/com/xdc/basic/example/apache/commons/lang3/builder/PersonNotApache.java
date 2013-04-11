package com.xdc.basic.example.apache.commons.lang3.builder;

public class PersonNotApache
{
	String	name;
	int	    age;
	boolean	smoker;

	public PersonNotApache(String name, int age, boolean smoker)
	{
		super();
		this.name = name;
		this.age = age;
		this.smoker = smoker;
	}

	@Override
	public String toString()
	{
		// eclipse 自动生成的
		return "PersonNotApache [name=" + name + ", age=" + age + ", smoker=" + smoker + "]";
	}

	public String toString2()
	{
		// 普通的方式，这种写法比eclipse生成的效率要高
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append(" [name=").append(name);
		sb.append(", age=").append(age);
		sb.append(", smoker=").append(smoker);
		sb.append("]");

		return sb.toString();
	}
}
