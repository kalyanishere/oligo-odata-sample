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
package org.apache.olingo.client.core.uri;

import org.apache.olingo.client.api.uri.CommonFilterArgFactory;
import org.apache.olingo.client.api.uri.FilterArg;
import org.apache.olingo.commons.api.edm.constants.ODataServiceVersion;

/**
 * OData filter arguments factory.
 */
public abstract class AbstractFilterArgFactory implements CommonFilterArgFactory {

  private final ODataServiceVersion version;

  public AbstractFilterArgFactory(final ODataServiceVersion version) {
    this.version = version;
  }

  @Override
  public FilterArg _null() {
    return new FilterConst("null");
  }

  @Override
  public FilterArg property(final String propertyPath) {
    return new FilterProperty(propertyPath);
  }

  @Override
  public FilterArg literal(final Object value) {
    return new FilterLiteral(version, value);
  }

  @Override
  public FilterArg add(final FilterArg first, final FilterArg second) {
    return new FilterOp("add", first, second);
  }

  @Override
  public FilterArg sub(final FilterArg first, final FilterArg second) {
    return new FilterOp("add", first, second);
  }

  @Override
  public FilterArg mul(final FilterArg first, final FilterArg second) {
    return new FilterOp("mul", first, second);
  }

  @Override
  public FilterArg div(final FilterArg first, final FilterArg second) {
    return new FilterOp("div", first, second);
  }

  @Override
  public FilterArg mod(final FilterArg first, final FilterArg second) {
    return new FilterOp("mod", first, second);
  }

  @Override
  public FilterArg endswith(final FilterArg first, final FilterArg second) {
    return new FilterFunction("endswith", first, second);
  }

  @Override
  public FilterArg startswith(final FilterArg first, final FilterArg second) {
    return new FilterFunction("startswith", first, second);
  }

  @Override
  public FilterArg length(final FilterArg param) {
    return new FilterFunction("length", param);
  }

  @Override
  public FilterArg indexof(final FilterArg first, final FilterArg second) {
    return new FilterFunction("indexof", first, second);
  }

  @Override
  public FilterArg replace(final FilterArg first, final FilterArg second, final FilterArg third) {
    return new FilterFunction("replace", first, second, third);
  }

  @Override
  public FilterArg substring(final FilterArg arg, final FilterArg pos) {
    return new FilterFunction("substring", arg, pos);
  }

  @Override
  public FilterArg substring(final FilterArg arg, final FilterArg pos, final FilterArg length) {
    return new FilterFunction("substring", arg, pos, length);
  }

  @Override
  public FilterArg tolower(final FilterArg param) {
    return new FilterFunction("tolower", param);
  }

  @Override
  public FilterArg toupper(final FilterArg param) {
    return new FilterFunction("toupper", param);
  }

  @Override
  public FilterArg trim(final FilterArg param) {
    return new FilterFunction("trim", param);
  }

  @Override
  public FilterArg concat(final FilterArg first, final FilterArg second) {
    return new FilterFunction("concat", first, second);
  }

  @Override
  public FilterArg day(final FilterArg param) {
    return new FilterFunction("day", param);
  }

  @Override
  public FilterArg hour(final FilterArg param) {
    return new FilterFunction("hour", param);
  }

  @Override
  public FilterArg minute(final FilterArg param) {
    return new FilterFunction("minute", param);
  }

  @Override
  public FilterArg month(final FilterArg param) {
    return new FilterFunction("month", param);
  }

  @Override
  public FilterArg second(final FilterArg param) {
    return new FilterFunction("second", param);
  }

  @Override
  public FilterArg year(final FilterArg param) {
    return new FilterFunction("year", param);
  }

  @Override
  public FilterArg round(final FilterArg param) {
    return new FilterFunction("round", param);
  }

  @Override
  public FilterArg floor(final FilterArg param) {
    return new FilterFunction("floor", param);
  }

  @Override
  public FilterArg ceiling(final FilterArg param) {
    return new FilterFunction("ceiling", param);
  }

  @Override
  public FilterArg isof(final FilterArg type) {
    return new FilterFunction("isof", type);
  }

  @Override
  public FilterArg isof(final FilterArg expression, final FilterArg type) {
    return new FilterFunction("isof", expression, type);
  }
}
