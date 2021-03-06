/* 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.fit.proxy.v3;

import org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.Car;
import org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.Computer;
import org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.Customer;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This is the unit test class to check basic feed operations.
 */
public class EntitySetTestITCase extends AbstractTestITCase {

  @Test
  public void count() {
    assertNotNull(container.getMessage());
    assertTrue(10 <= container.getMessage().count());

    assertTrue(container.getCustomer().count() > 0);
  }

  @Test
  public void execute() {
    int count = 0;
    for (Customer customer : container.getCustomer().execute()) {
      assertNotNull(customer);
      count++;
    }
    assertTrue(count < 10);
  }

  @Test
  public void readEntitySetWithNextLink() {
    int count = 0;
    for (Customer customer : container.getCustomer().execute()) {
      assertNotNull(customer);
      count++;
    }
    assertEquals(2, count);

    int iterating = 0;
    for (Customer customer : container.getCustomer()) {
      assertNotNull(customer);
      iterating++;
    }
    assertTrue(count < iterating);
  }

  @Test
  public void readODataEntitySet() throws IOException {
    assertTrue(container.getCar().count() >= 10);

    final Iterable<Car> car = container.getCar().execute();
    assertNotNull(car);

    final Iterator<Car> itor = car.iterator();

    int count = 0;
    while (itor.hasNext()) {
      assertNotNull(itor.next());
      count++;
    }
    assertTrue(count >= 10);
  }

  @Test
  public void readEntitySetIterator() {
    int count = 0;
    for (Computer computer : container.getComputer()) {
      assertNotNull(computer);
      count++;
    }
    assertTrue(count >= 10);
  }
}
