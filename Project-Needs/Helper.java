	public static String getdata(int r, int c) throws InvalidFormatException, IOException
	{
		String data=null;
		FileInputStream fis=new FileInputStream(".//Input data//Club_Admin_Book1.xlsx");
		Workbook w=WorkbookFactory.create(fis);
		Cell cel=w.getSheet("Sheet1").getRow(r).getCell(c);
		
		try
		{
			int i=(int) cel.getNumericCellValue();
			data=String.valueOf(i).toString();
		}
		catch(Exception e)
		{
			data=cel.getStringCellValue();
		}
		return data;
		
	}
	
	
	public static String Generate_Name()
	{
		String people[]={"ram","kevin","john","siva","peter","ravi","robin","roman","mohan","Smith","Johnson","Jones","Wilson","Anderson",
				"White","Walker","Scott","Nelson","Phillips","Edwards","Ronald","Donna","Helen","George","Charles","Sharon"};
		
	    List<String> names=Arrays.asList(people);
	    Collections.shuffle(names);
	    Random r=new Random();
	    int index=r.nextInt(names.size());
	    String anyname=names.get(index);
	    return anyname;
	}
	

	@AfterMethod
	public void Tear(ITestResult testresult, Method method)
	{
		String testname=method.getName();
		try
		{
			if(testresult.getStatus()==ITestResult.FAILURE)
			{
				System.out.println(testresult.getStatus());
				TakeScreenShots(testname);
			}
		}
		catch(Exception e)
		{
			Assert.fail("Failed to take screenshot");
		}
		finally
		{
			System.out.println(""+ testname + " Test has been completed successfully.");
			driver.quit();
		}
	}
	
	public void TakeScreenShots(String Testcase) throws IOException
	{
		File scrshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrshot, new File("C:\\Quicx_scrnshots\\Club_admin\\"+ Testcase +".png"+""));
	}
