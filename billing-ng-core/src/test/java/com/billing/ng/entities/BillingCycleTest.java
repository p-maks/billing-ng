/*
 BillingNG, a next-generation billing solution
 Copyright (C) 2010 Brian Cowdery

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as
 published by the Free Software Foundation, either version 3 of the
 License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.
 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see http://www.gnu.org/licenses/agpl-3.0.html
 */

package com.billing.ng.entities;

import org.joda.time.DateMidnight;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

/**
 * BillingCycleTest
 *
 * @author Brian Cowdery
 * @since 27-Oct-2010
 */
@Test(groups = {"entity", "quick", "billing"})
public class BillingCycleTest {

    private Calendar calendar = GregorianCalendar.getInstance();

    /* Convenience method to produce a date instance for the given year/month/day */
    private Date getDate(int year, int month, int day) {
        calendar.clear();
        calendar.set(year, month, day);        
        return calendar.getTime();
    }

    @Test
    public void testIsActiveForDate() {
        BillingCycle cycle = new BillingCycle();
        cycle.setBillingPeriod(new BillingPeriod(BillingPeriod.Type.MONTH, 1));

        // cycle between Jan 1st 2010 -> Jan 1st 2011
        cycle.setCycleStartDay(1);
        cycle.setStart(getDate(2010, Calendar.JANUARY, 1));
        cycle.setEnd(getDate(2011, Calendar.JANUARY, 1));

        // active on the start date
        assertTrue(cycle.isActive(new DateMidnight(2010, 1, 1)));

        // active on the end date
        assertTrue(cycle.isActive(new DateMidnight(2011, 1, 1)));

        // not active before start
        assertFalse(cycle.isActive(new DateMidnight(2009, 12, 31)));

        // not active after end
        assertFalse(cycle.isActive(new DateMidnight(2011, 1, 2)));
    }

    @Test
    public void testGetStartInstant() {
        BillingCycle cycle = new BillingCycle();
        cycle.setBillingPeriod(new BillingPeriod(BillingPeriod.Type.MONTH, 1));
        
        // active start date equals cycle start day
        cycle.setCycleStartDay(1);
        cycle.setStart(getDate(2010, Calendar.JANUARY, 1));

        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 1, 1)));

        // active start date before cycle start day
        cycle.setCycleStartDay(15);
        cycle.setStart(getDate(2010, Calendar.JANUARY, 1));

        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 1, 15)));

        // active start date after cycle start day
        cycle.setCycleStartDay(15);
        cycle.setStart(getDate(2010, Calendar.JANUARY, 30));
        
        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 2, 15))); // Jan 15 + 1 month = Feb 15
    }

    @Test
    public void testGetStartInstantOddPeriods() {
        BillingCycle cycle = new BillingCycle();
        cycle.setBillingPeriod(new BillingPeriod(BillingPeriod.Type.DAY, 11));

        cycle.setCycleStartDay(3);

        // active start date before cycle start day
        cycle.setStart(getDate(2010, Calendar.JANUARY, 2));
        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 1, 3)));

        // active start date after cycle start day
        cycle.setStart(getDate(2010, Calendar.JANUARY, 4));
        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 1, 14))); // Jan 11 + 3 = Jan 14

        // active start date 2 periods after cycle start day
        cycle.setStart(getDate(2010, Calendar.JANUARY, 26)); 
        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 2, 5))); // Jan 3rd + 11 + 11 + 11 = Feb 5
    }

    @Test
    public void testGetStartInstantLastDayOfMonth() {
        BillingCycle cycle = new BillingCycle();
        cycle.setBillingPeriod(new BillingPeriod(BillingPeriod.Type.MONTH, 1));

        cycle.setCycleStartDay(BillingCycle.LAST_DAY_OF_MONTH);

        // last day of january (31 days)
        cycle.setStart(getDate(2010, Calendar.JANUARY, 1));
        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 1, 31)));

        // last day of february (28 days)
        cycle.setStart(getDate(2010, Calendar.FEBRUARY, 1));
        assertThat(cycle.getStartInstant(), is(new DateMidnight(2010, 2, 28)));
    }

    @Test
    public void testGetEndInstant() {
        BillingCycle cycle = new BillingCycle();
        cycle.setBillingPeriod(new BillingPeriod(BillingPeriod.Type.MONTH, 1));

        cycle.setEnd(getDate(2099, Calendar.JANUARY, 1));
        cycle.setCycleStartDay(1);

        // explicitly set end date
        assertThat(cycle.getEndInstant(), is(new DateMidnight(2099, 1, 1))); 
    }

    @Test
    public void testGetEndInstantDefault() {
        BillingCycle cycle = new BillingCycle();
        cycle.setBillingPeriod(new BillingPeriod(BillingPeriod.Type.MONTH, 1));

        cycle.setStart(getDate(2010, Calendar.MARCH, 17));
        cycle.setCycleStartDay(17);

        // defaults to the start date with the year value set to the max value
        // basically the cycle never ends (baring the end of the Universe).
        assertThat(cycle.getEndInstant(), is(new DateMidnight(292278993, 3, 17)));
    }
}

