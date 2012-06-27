/*
 BillingNG, a next-generation billing solution
 Copyright (C) 2012 Brian Cowdery

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

package com.billing.ng.crypto.profile.hash;

/**
 * Interface for cipher profiles. A cipher profile provides the parameters necessary
 * to encrypt and decrypt data and defines how a particular {@link com.billing.ng.crypto.CipherAlgorithm}
 * generates keys.
 *
 * @author Brian Cowdery
 * @since 21/06/12
 */
public interface HashProfile {

    public byte[] digest(String plainText);
    public byte[] digest(String plainText, String salt);

}
