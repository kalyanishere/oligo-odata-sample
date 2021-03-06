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
package org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice;

//CHECKSTYLE:OFF (Maven checkstyle)
import org.apache.olingo.ext.proxy.api.PersistenceManager;
import org.apache.olingo.ext.proxy.api.OperationType;
import org.apache.olingo.ext.proxy.api.ComplexCollection;
import org.apache.olingo.ext.proxy.api.ComplexType;
import org.apache.olingo.ext.proxy.api.EntityCollection;
import org.apache.olingo.ext.proxy.api.EntityType;
import org.apache.olingo.ext.proxy.api.PrimitiveCollection;
import org.apache.olingo.ext.proxy.api.EdmStreamValue;
import java.io.Serializable;
import java.io.InputStream;
//CHECKSTYLE:ON (Maven checkstyle)

@org.apache.olingo.ext.proxy.api.annotations.Namespace("Microsoft.Test.OData.Services.AstoriaDefaultService")
@org.apache.olingo.ext.proxy.api.annotations.EntityContainer(name = "DefaultContainer",
  namespace = "Microsoft.Test.OData.Services.AstoriaDefaultService",
  isDefaultEntityContainer = true)
public interface DefaultContainer extends PersistenceManager {

    Customer getCustomer();

    Login getLogin();

    OrderLine getOrderLine();

    ComputerDetail getComputerDetail();

    Product getProduct();

    Message getMessage();

    ProductDetail getProductDetail();

    ProductPhoto getProductPhoto();

    Order getOrder();

    Computer getComputer();

    MappedEntityType getMappedEntityType();

    PageView getPageView();

    Driver getDriver();

    AllGeoCollectionTypesSet getAllGeoCollectionTypesSet();

    Car getCar();

    CustomerInfo getCustomerInfo();

    License getLicense();

    ProductReview getProductReview();

    LastLogin getLastLogin();

    MessageAttachment getMessageAttachment();

    AllGeoTypesSet getAllGeoTypesSet();

    PersonMetadata getPersonMetadata();

    RSAToken getRSAToken();

    Person getPerson();





  Operations operations();

  public interface Operations extends org.apache.olingo.ext.proxy.api.Operations {
        
    @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "GetSpecificCustomer",
                    type = OperationType.FUNCTION,
                    isComposable = false,
                    referenceType = org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection.class,                    returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Customer)")
    org.apache.olingo.ext.proxy.api.StructuredCollectionInvoker<org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection> getSpecificCustomer(
        @org.apache.olingo.ext.proxy.api.annotations.Parameter(name = "Name", type = "Edm.String", nullable = true) java.lang.String name
    );

          
    @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "InStreamErrorGetCustomer",
                    type = OperationType.FUNCTION,
                    isComposable = false,
                    referenceType = org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection.class,                    returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.Customer)")
    org.apache.olingo.ext.proxy.api.StructuredCollectionInvoker<org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.CustomerCollection> inStreamErrorGetCustomer(
    );

          
    @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "GetPrimitiveString",
                    type = OperationType.FUNCTION,
                    isComposable = false,
                    referenceType = java.lang.String.class,                    returnType = "Edm.String")
    org.apache.olingo.ext.proxy.api.Invoker<java.lang.String> getPrimitiveString(
    );

          
    @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "EntityProjectionReturnsCollectionOfComplexTypes",
                    type = OperationType.FUNCTION,
                    isComposable = false,
                    referenceType = org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetailsCollection.class,                    returnType = "Collection(Microsoft.Test.OData.Services.AstoriaDefaultService.ContactDetails)")
    org.apache.olingo.ext.proxy.api.StructuredCollectionInvoker<org.apache.olingo.fit.proxy.v3.staticservice.microsoft.test.odata.services.astoriadefaultservice.types.ContactDetailsCollection> entityProjectionReturnsCollectionOfComplexTypes(
    );

          
    @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "GetArgumentPlusOne",
                    type = OperationType.FUNCTION,
                    isComposable = false,
                    referenceType = java.lang.Integer.class,                    returnType = "Edm.Int32")
    org.apache.olingo.ext.proxy.api.Invoker<java.lang.Integer> getArgumentPlusOne(
        @org.apache.olingo.ext.proxy.api.annotations.Parameter(name = "arg1", type = "Edm.Int32", nullable = false) java.lang.Integer arg1
    );

          
    @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "GetCustomerCount",
                    type = OperationType.FUNCTION,
                    isComposable = false,
                    referenceType = java.lang.Integer.class,                    returnType = "Edm.Int32")
    org.apache.olingo.ext.proxy.api.Invoker<java.lang.Integer> getCustomerCount(
    );

    
        
    @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "ResetDataSource",
                    type = OperationType.ACTION)
    org.apache.olingo.ext.proxy.api.Invoker<Void> resetDataSource(
    );
  
      }

  <NE extends EntityType<?>> NE newEntityInstance(Class<NE> ref);

  <T extends EntityType<?>, NEC extends EntityCollection<T, ?, ?>> NEC newEntityCollection(Class<NEC> ref);

  <NE extends ComplexType<?>> NE newComplexInstance(Class<NE> ref);

  <T extends ComplexType<?>, NEC extends ComplexCollection<T, ?, ?>> NEC newComplexCollection(Class<NEC> ref);

  <T extends Serializable, NEC extends PrimitiveCollection<T>> NEC newPrimitiveCollection(Class<T> ref);

  EdmStreamValue newEdmStreamValue(String contentType, InputStream stream);
}
