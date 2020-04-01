package Demo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass2
{
	@Test(priority=1)
	public void class2_Method1()
	{
		Assert.assertTrue(true);
	}
	@Test(priority=2)
	public void class2_Method2()
	{
		Assert.assertTrue(true);

	}
	@Test(priority=3)
	public void class2_Method3()
	{
		Assert.assertTrue(true);

	}
	@Test(priority=4)
	public void class2_Method4()
	{
		Assert.assertTrue(false);

	}
	@Test()
	public void class2_Method5()
	{
		Assert.assertTrue(true);

	}
	@Test(priority=6)
	public void class2_Method6()
	{
		Assert.assertTrue(true);

	}


}
