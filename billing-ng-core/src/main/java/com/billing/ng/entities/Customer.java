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

import com.billing.ng.util.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Map;

/**
 * Customer
 *
 * http://velocity.apache.org/engine/releases/velocity-1.6.4/user-guide.html
 *
 * @author Brian Cowdery
 * @since 16-Aug-2010
 */
@Entity
public class Customer extends User {

    public static final String DEFAULT_NUMBER_PATTERN = "${customer.id}-${customer.userName}";

    @Column
    private String number;

    @Column
    private Map<String, String> attributes;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;        
    }
}
