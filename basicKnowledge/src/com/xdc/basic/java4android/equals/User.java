package com.xdc.basic.java4android.equals;

//equals()的重写
public class User
{
	String	name;
	int	   age;

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj instanceof User)
		{
			User user = (User) obj;
			if (this.name.equals(user.name) && this.age == user.age)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}