package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myAdapter.*;

public class MyTestSuiteMapAdapter 
{
    private MapAdapter map;

    @BeforeClass
    public void BeforeClassMethod()
    {
        System.out.println(MyTestSuiteMapAdapter.class.getName() + " running.");
    }


}
