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
package org.apache.olingo.client.core.v3;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.olingo.client.api.v3.ODataClient;
import org.apache.olingo.client.core.AbstractTest;
import org.apache.olingo.commons.api.domain.ODataPrimitiveValue;
import org.apache.olingo.commons.api.domain.ODataValue;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeException;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.geo.Geospatial;
import org.apache.olingo.commons.api.edm.geo.GeospatialCollection;
import org.apache.olingo.commons.api.edm.geo.LineString;
import org.apache.olingo.commons.api.edm.geo.MultiLineString;
import org.apache.olingo.commons.api.edm.geo.MultiPoint;
import org.apache.olingo.commons.api.edm.geo.MultiPolygon;
import org.apache.olingo.commons.api.edm.geo.Point;
import org.apache.olingo.commons.api.edm.geo.Polygon;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrimitiveValueTest extends AbstractTest {

  @Override
  protected ODataClient getClient() {
    return v3Client;
  }

  @Test
  public void int32() throws EdmPrimitiveTypeException {
    final int primitive = -10;
    ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().setType(EdmPrimitiveTypeKind.Int32).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.Int32, value.asPrimitive().getTypeKind());
    assertEquals(Integer.valueOf(primitive), value.asPrimitive().toCastValue(Integer.class));

    value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.Int32).setValue(9).build();
    assertEquals("9", value.asPrimitive().toCastValue(Integer.class).toString());
  }

  @Test
  public void string() throws EdmPrimitiveTypeException {
    final String primitive = RandomStringUtils.random(17);
    ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().buildString(primitive);
    assertEquals(EdmPrimitiveTypeKind.String, value.asPrimitive().getTypeKind());
    assertEquals(primitive, value.toString());

    value = getClient().getObjectFactory().newPrimitiveValueBuilder().setType(EdmPrimitiveTypeKind.String).
            setValue("1126a28b-a4af-4bbd-bf0a-2b2c22635565").build();
    assertEquals("1126a28b-a4af-4bbd-bf0a-2b2c22635565", value.asPrimitive().toCastValue(String.class));
  }

  @Test
  public void decimal() throws EdmPrimitiveTypeException {
    final BigDecimal primitive = new BigDecimal("-79228162514264337593543950335");
    ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().setType(EdmPrimitiveTypeKind.Decimal).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.Decimal, value.asPrimitive().getTypeKind());
    assertEquals(primitive, value.asPrimitive().toCastValue(BigDecimal.class));

    value = getClient().getObjectFactory().newPrimitiveValueBuilder().setType(EdmPrimitiveTypeKind.Decimal).
            setValue(new BigDecimal("-79228162514264337593543950335")).build();
    assertEquals("-79228162514264337593543950335", value.asPrimitive().toCastValue(BigDecimal.class).toString());
  }

  @Test
  public void dateTime() throws EdmPrimitiveTypeException {
    final Calendar expected = Calendar.getInstance();
    expected.clear();
    expected.set(2013, 0, 10, 2, 0, 0);
    expected.set(Calendar.MILLISECOND, 1667673);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.DateTime).setValue(expected).build();
    assertEquals(EdmPrimitiveTypeKind.DateTime, value.asPrimitive().getTypeKind());

    final Calendar actual = value.asPrimitive().toCastValue(Calendar.class);
    assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
    assertEquals(expected.get(Calendar.DATE), actual.get(Calendar.DATE));
    assertEquals(expected.get(Calendar.HOUR), actual.get(Calendar.HOUR));
    assertEquals(expected.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
    assertEquals(expected.get(Calendar.SECOND), actual.get(Calendar.SECOND));
    assertEquals(expected.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));

    // Timestamp
    final Timestamp timestamp = value.asPrimitive().toCastValue(Timestamp.class);
    assertEquals(expected.get(Calendar.MILLISECOND), timestamp.getNanos() / 1000000);

    assertEquals("2013-01-10T02:27:47.673", value.asPrimitive().toString());
  }

  @Test
  public void time() throws EdmPrimitiveTypeException {
    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.Time).setValue(BigDecimal.valueOf(-780670.5063807)).build();
    assertEquals(EdmPrimitiveTypeKind.Time, value.asPrimitive().getTypeKind());
    assertEquals(BigDecimal.valueOf(-780670.5063807), value.asPrimitive().toCastValue(BigDecimal.class));
    assertEquals("-P9DT51M10.5063807S", value.asPrimitive().toString());

    final ODataPrimitiveValue write = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.Time).setValue(BigDecimal.valueOf(-780670.5063807)).build();
    assertEquals("-P9DT51M10.5063807S", write.toString());
  }

  @Test
  public void dateTimeOffset() throws EdmPrimitiveTypeException {
    final Calendar expected = Calendar.getInstance();
    expected.clear();
    expected.setTimeZone(TimeZone.getTimeZone("GMT"));
    expected.set(2013, 0, 10, 2, 0, 0);
    expected.set(Calendar.MILLISECOND, 22);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.DateTimeOffset).setValue(expected).build();
    assertEquals(EdmPrimitiveTypeKind.DateTimeOffset, value.asPrimitive().getTypeKind());

    final Calendar asCalendar = value.asPrimitive().toCastValue(Calendar.class);
    assertEquals(expected.get(Calendar.YEAR), asCalendar.get(Calendar.YEAR));
    assertEquals(expected.get(Calendar.MONTH), asCalendar.get(Calendar.MONTH));
    assertEquals(expected.get(Calendar.DATE), asCalendar.get(Calendar.DATE));
    assertEquals(expected.get(Calendar.HOUR), asCalendar.get(Calendar.HOUR));
    assertEquals(expected.get(Calendar.MINUTE), asCalendar.get(Calendar.MINUTE));
    assertEquals(expected.get(Calendar.SECOND), asCalendar.get(Calendar.SECOND));
    assertEquals(expected.get(Calendar.MILLISECOND), asCalendar.get(Calendar.MILLISECOND));

    final Timestamp asTimestamp = value.asPrimitive().toCastValue(Timestamp.class);
    assertEquals(expected.get(Calendar.MILLISECOND), asTimestamp.getNanos() / 1000000);

    assertEquals("2013-01-10T02:00:00.022Z", value.asPrimitive().toString());

    final ODataValue parsed = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.DateTimeOffset).setValue(value.asPrimitive().toValue()).build();
    assertEquals(22, parsed.asPrimitive().toCastValue(Calendar.class).get(Calendar.MILLISECOND));
  }

  @Test
  public void guid() throws EdmPrimitiveTypeException {
    final UUID primitive = UUID.fromString("1126a28b-a4af-4bbd-bf0a-2b2c22635565");
    ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().setType(EdmPrimitiveTypeKind.Guid).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.Guid, value.asPrimitive().getTypeKind());
    assertEquals(primitive, value.asPrimitive().toCastValue(UUID.class));

    value = getClient().getObjectFactory().newPrimitiveValueBuilder().setType(EdmPrimitiveTypeKind.Guid).
            setValue(UUID.fromString("1126a28b-a4af-4bbd-bf0a-2b2c22635565")).build();
    assertEquals("1126a28b-a4af-4bbd-bf0a-2b2c22635565", value.asPrimitive().toCastValue(UUID.class).toString());
  }

  @Test
  public void binary() throws EdmPrimitiveTypeException {
    final byte[] primitive = UUID.randomUUID().toString().getBytes();
    ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().setType(EdmPrimitiveTypeKind.Binary).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.Binary, value.asPrimitive().getTypeKind());
    assertEquals(
            Base64.encodeBase64String(primitive),
            Base64.encodeBase64String(value.asPrimitive().toCastValue(byte[].class)));

    value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.Binary).setValue("primitive".getBytes()).build();
    assertEquals("primitive", new String(value.asPrimitive().toCastValue(byte[].class)));
  }

  @Test
  public void point() throws EdmPrimitiveTypeException {
    final Point primitive = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    primitive.setX(52.8606);
    primitive.setY(173.334);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeographyPoint).
            setValue(primitive).
            build();
    assertEquals(EdmPrimitiveTypeKind.GeographyPoint, value.asPrimitive().getTypeKind());
    assertEquals(Double.valueOf(primitive.getX()),
            Double.valueOf(value.asPrimitive().toCastValue(Point.class).getX()));
    assertEquals(Double.valueOf(primitive.getY()),
            Double.valueOf(value.asPrimitive().toCastValue(Point.class).getY()));
  }

  @Test
  public void lineString() throws EdmPrimitiveTypeException {
    final List<Point> points = new ArrayList<Point>();
    Point point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(40.5);
    point.setY(40.5);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(30.5);
    point.setY(30.5);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(20.5);
    point.setY(40.5);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(10.5);
    point.setY(30.5);
    points.add(point);

    final LineString primitive = new LineString(Geospatial.Dimension.GEOGRAPHY, null, points);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeographyLineString).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.GeographyLineString, value.asPrimitive().getTypeKind());

    final Iterator<Point> iter = value.asPrimitive().toCastValue(LineString.class).iterator();

    // take the third one and check the point value ...
    iter.next();
    iter.next();
    point = iter.next();

    assertEquals(Double.valueOf(points.get(2).getX()), Double.valueOf(point.getX()));
    assertEquals(Double.valueOf(points.get(2).getY()), Double.valueOf(point.getY()));
  }

  @Test
  public void multiPoint() throws EdmPrimitiveTypeException {
    final List<Point> points = new ArrayList<Point>();
    Point point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(0);
    point.setY(0);
    points.add(point);

    final MultiPoint primitive = new MultiPoint(Geospatial.Dimension.GEOMETRY, null, points);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeometryMultiPoint).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.GeometryMultiPoint, value.asPrimitive().getTypeKind());

    final Iterator<Point> iter = value.asPrimitive().toCastValue(MultiPoint.class).iterator();
    point = iter.next();

    assertEquals(Double.valueOf(points.get(0).getX()), Double.valueOf(point.getX()));
    assertEquals(Double.valueOf(points.get(0).getY()), Double.valueOf(point.getY()));
  }

  @Test
  public void multiLine() throws EdmPrimitiveTypeException {
    final List<LineString> lines = new ArrayList<LineString>();

    // line one ...
    List<Point> points = new ArrayList<Point>();
    Point point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(10);
    point.setY(10);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(20);
    point.setY(20);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(10);
    point.setY(40);
    points.add(point);

    lines.add(new LineString(Geospatial.Dimension.GEOMETRY, null, points));

    // line two ...
    points = new ArrayList<Point>();

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(40);
    point.setY(40);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(30);
    point.setY(30);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(40);
    point.setY(20);
    points.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(30);
    point.setY(10);
    points.add(point);
    lines.add(new LineString(Geospatial.Dimension.GEOMETRY, null, points));

    final MultiLineString primitive = new MultiLineString(Geospatial.Dimension.GEOMETRY, null, lines);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeometryMultiLineString).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.GeometryMultiLineString, value.asPrimitive().getTypeKind());

    final Iterator<LineString> lineIter = value.asPrimitive().toCastValue(MultiLineString.class).iterator();

    // take the second line and check the third point value ...
    lineIter.next();
    final LineString line = lineIter.next();

    final Iterator<Point> pointIter = line.iterator();
    pointIter.next();
    pointIter.next();
    point = pointIter.next();

    assertEquals(Double.valueOf(points.get(2).getX()), Double.valueOf(point.getX()));
    assertEquals(Double.valueOf(points.get(2).getY()), Double.valueOf(point.getY()));
  }

  @Test
  public void polygon() throws EdmPrimitiveTypeException {

    final List<Point> interior = new ArrayList<Point>();
    final List<Point> exterior = new ArrayList<Point>();

    Point point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(5);
    point.setY(15);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(10);
    point.setY(40);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(20);
    point.setY(10);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(10);
    point.setY(5);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(5);
    point.setY(15);
    exterior.add(point);

    final Polygon primitive = new Polygon(Geospatial.Dimension.GEOGRAPHY, null, interior, exterior);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeographyPolygon).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.GeographyPolygon, value.asPrimitive().getTypeKind());

    assertTrue(value.asPrimitive().toCastValue(Polygon.class).getInterior().isEmpty());
    final Iterator<Point> iter = value.asPrimitive().toCastValue(Polygon.class).getExterior().iterator();

    // take the third one ...
    iter.next();
    iter.next();
    point = iter.next();

    assertEquals(Double.valueOf(exterior.get(2).getX()), Double.valueOf(point.getX()));
    assertEquals(Double.valueOf(exterior.get(2).getY()), Double.valueOf(point.getY()));
  }

  @Test
  public void multiPolygon() throws EdmPrimitiveTypeException {
    final List<Polygon> polygons = new ArrayList<Polygon>();

    List<Point> interior = new ArrayList<Point>();
    List<Point> exterior = new ArrayList<Point>();

    // exterior one ...
    Point point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(40);
    point.setY(40);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(20);
    point.setY(45);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(45);
    point.setY(30);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(40);
    point.setY(40);
    exterior.add(point);

    polygons.add(new Polygon(Geospatial.Dimension.GEOMETRY, null, interior, exterior));

    // interior two ...
    interior = new ArrayList<Point>();
    exterior = new ArrayList<Point>();

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(30);
    point.setY(20);
    interior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(20);
    point.setY(25);
    interior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(20);
    point.setY(15);
    interior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(30);
    point.setY(20);
    interior.add(point);

    // exterior two ...
    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(20);
    point.setY(35);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(45);
    point.setY(20);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(30);
    point.setY(5);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(10);
    point.setY(10);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(10);
    point.setY(30);
    exterior.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(20);
    point.setY(35);
    exterior.add(point);

    polygons.add(new Polygon(Geospatial.Dimension.GEOMETRY, null, interior, exterior));

    final MultiPolygon primitive = new MultiPolygon(Geospatial.Dimension.GEOMETRY, null, polygons);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeometryMultiPolygon).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.GeometryMultiPolygon, value.asPrimitive().getTypeKind());

    final Iterator<Polygon> iter = value.asPrimitive().toCastValue(MultiPolygon.class).iterator();

    // second one polygon
    iter.next();
    final Polygon polygon = iter.next();
    Iterator<Point> pointIter = polygon.getInterior().iterator();
    pointIter.next();
    point = pointIter.next();

    // check the second point from interior
    assertEquals(Double.valueOf(interior.get(1).getX()), Double.valueOf(point.getX()));
    assertEquals(Double.valueOf(interior.get(1).getY()), Double.valueOf(point.getY()));

    pointIter = polygon.getExterior().iterator();
    pointIter.next();
    pointIter.next();
    point = pointIter.next();

    // check the third point from exterior
    assertEquals(Double.valueOf(exterior.get(2).getX()), Double.valueOf(point.getX()));
    assertEquals(Double.valueOf(exterior.get(2).getY()), Double.valueOf(point.getY()));
  }

  @Test
  public void geomCollection() throws EdmPrimitiveTypeException {
    final List<Geospatial> collection = new ArrayList<Geospatial>();

    Point point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(1);
    point.setY(2);
    point.setZ(3);
    collection.add(point);

    point = new Point(Geospatial.Dimension.GEOMETRY, null);
    point.setX(4);
    point.setY(5);
    point.setZ(6);
    collection.add(point);

    final GeospatialCollection primitive = new GeospatialCollection(Geospatial.Dimension.GEOMETRY, null, collection);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeometryCollection).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.GeometryCollection, value.asPrimitive().getTypeKind());

    final Iterator<Geospatial> iter = value.asPrimitive().toCastValue(GeospatialCollection.class).iterator();
    iter.next();
    final Point collectedPoint = (Point) iter.next();

    assertTrue(point.getX() == collectedPoint.getX()
            && point.getY() == collectedPoint.getY()
            && point.getZ() == collectedPoint.getZ());
  }

  @Test
  public void geogCollection() throws EdmPrimitiveTypeException {
    final List<Geospatial> collection = new ArrayList<Geospatial>();

    Point point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(1);
    point.setY(2);
    point.setZ(3);
    collection.add(point);

    point = new Point(Geospatial.Dimension.GEOGRAPHY, null);
    point.setX(4);
    point.setY(5);
    point.setZ(6);
    collection.add(point);

    final GeospatialCollection primitive = new GeospatialCollection(Geospatial.Dimension.GEOGRAPHY, null, collection);

    final ODataValue value = getClient().getObjectFactory().newPrimitiveValueBuilder().
            setType(EdmPrimitiveTypeKind.GeographyCollection).
            setValue(primitive).build();
    assertEquals(EdmPrimitiveTypeKind.GeographyCollection, value.asPrimitive().getTypeKind());

    final Iterator<Geospatial> iter = value.asPrimitive().toCastValue(GeospatialCollection.class).iterator();
    iter.next();
    final Point collectedPoint = (Point) iter.next();

    assertTrue(point.getX() == collectedPoint.getX()
            && point.getY() == collectedPoint.getY()
            && point.getZ() == collectedPoint.getZ());
  }
}
