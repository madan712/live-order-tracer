package com.silverbars;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.silverbars.controller.ApplicationControllerTest;
import com.silverbars.service.ApplicationServiceTest;
import com.silverbars.util.OrderValidatorTest;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationControllerTest.class, ApplicationServiceTest.class, OrderValidatorTest.class })
public class AllTests {

}
