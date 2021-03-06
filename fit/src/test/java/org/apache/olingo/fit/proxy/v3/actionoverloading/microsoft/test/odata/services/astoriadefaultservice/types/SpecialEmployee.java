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
package org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types;
//CHECKSTYLE:OFF (Maven checkstyle)
import org.apache.olingo.ext.proxy.api.annotations.Key;
import org.apache.olingo.ext.proxy.api.OperationType;
import org.apache.olingo.commons.api.edm.constants.EdmContentKind;
import org.apache.olingo.client.api.edm.ConcurrencyMode;
import java.util.concurrent.Future;
//CHECKSTYLE:ON (Maven checkstyle)


@org.apache.olingo.ext.proxy.api.annotations.Namespace("Microsoft.Test.OData.Services.AstoriaDefaultService")
@org.apache.olingo.ext.proxy.api.annotations.EntityType(name = "SpecialEmployee",
        openType = false,
        hasStream = false,
        isAbstract = false,
        baseType = "Microsoft.Test.OData.Services.AstoriaDefaultService.Employee")
public interface SpecialEmployee 
  extends org.apache.olingo.ext.proxy.api.Annotatable,
  org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.Employee   {

  @Override
  SpecialEmployee load();

  @Override
  Future<? extends SpecialEmployee> loadAsync();

  @Override
  SpecialEmployee refs();

  @Override
  SpecialEmployee expand(String... expand);

  @Override
  SpecialEmployee select(String... select);

    

    @Key
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "PersonId", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.Integer getPersonId();

    void setPersonId(java.lang.Integer _personId);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "Name", 
                type = "Edm.String", 
                nullable = true,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.String getName();

    void setName(java.lang.String _name);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "ManagersPersonId", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.Integer getManagersPersonId();

    void setManagersPersonId(java.lang.Integer _managersPersonId);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "Salary", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.Integer getSalary();

    void setSalary(java.lang.Integer _salary);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "Title", 
                type = "Edm.String", 
                nullable = true,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.String getTitle();

    void setTitle(java.lang.String _title);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "CarsVIN", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.Integer getCarsVIN();

    void setCarsVIN(java.lang.Integer _carsVIN);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "Bonus", 
                type = "Edm.Int32", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.Integer getBonus();

    void setBonus(java.lang.Integer _bonus);
    
    
    @org.apache.olingo.ext.proxy.api.annotations.Property(name = "IsFullyVested", 
                type = "Edm.Boolean", 
                nullable = false,
                defaultValue = "",
                maxLenght = Integer.MAX_VALUE,
                fixedLenght = false,
                precision = 0,
                scale = 0,
                unicode = true,
                collation = "",
                srid = "",
                concurrencyMode = ConcurrencyMode.None,
                fcSourcePath = "",
                fcTargetPath = "",
                fcContentKind = EdmContentKind.text,
                fcNSPrefix = "",
                fcNSURI = "",
                fcKeepInContent = false)
    java.lang.Boolean getIsFullyVested();

    void setIsFullyVested(java.lang.Boolean _isFullyVested);
    

    @org.apache.olingo.ext.proxy.api.annotations.NavigationProperty(name = "PersonMetadata", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.PersonMetadata", 
                targetSchema = "Microsoft.Test.OData.Services.AstoriaDefaultService", 
                targetContainer = "DefaultContainer", 
                targetEntitySet = "PersonMetadata",
                containsTarget = false)
    org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.PersonMetadataCollection getPersonMetadata();

    void setPersonMetadata(org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.PersonMetadataCollection _personMetadata);
    
    @org.apache.olingo.ext.proxy.api.annotations.NavigationProperty(name = "Manager", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Employee", 
                targetSchema = "Microsoft.Test.OData.Services.AstoriaDefaultService", 
                targetContainer = "DefaultContainer", 
                targetEntitySet = "Person",
                containsTarget = false)
    org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.Employee getManager();

    void setManager(org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.Employee _manager);
    
    @org.apache.olingo.ext.proxy.api.annotations.NavigationProperty(name = "Car", 
                type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Car", 
                targetSchema = "Microsoft.Test.OData.Services.AstoriaDefaultService", 
                targetContainer = "DefaultContainer", 
                targetEntitySet = "Car",
                containsTarget = false)
    org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.Car getCar();

    void setCar(org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.Car _car);
    


        @Override
        Operations operations();

    interface Operations extends org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.Employee.Operations{
    
          
      @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "UpdatePersonInfo",
                    type = OperationType.ACTION)
      org.apache.olingo.ext.proxy.api.Invoker<Void> updatePersonInfo(
            );

          
      @org.apache.olingo.ext.proxy.api.annotations.Operation(name = "IncreaseEmployeeSalary",
                    type = OperationType.ACTION,
                    referenceType = java.lang.Integer.class,                    returnType = "Edm.Int32")
      org.apache.olingo.ext.proxy.api.Invoker<java.lang.Integer> increaseEmployeeSalary(
            );

        }
    Annotations annotations();

    interface Annotations            extends org.apache.olingo.fit.proxy.v3.actionoverloading.microsoft.test.odata.services.astoriadefaultservice.types.Employee.Annotations{

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "PersonId",
                   type = "Edm.Int32")
        org.apache.olingo.ext.proxy.api.Annotatable getPersonIdAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "Name",
                   type = "Edm.String")
        org.apache.olingo.ext.proxy.api.Annotatable getNameAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "ManagersPersonId",
                   type = "Edm.Int32")
        org.apache.olingo.ext.proxy.api.Annotatable getManagersPersonIdAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "Salary",
                   type = "Edm.Int32")
        org.apache.olingo.ext.proxy.api.Annotatable getSalaryAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "Title",
                   type = "Edm.String")
        org.apache.olingo.ext.proxy.api.Annotatable getTitleAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "CarsVIN",
                   type = "Edm.Int32")
        org.apache.olingo.ext.proxy.api.Annotatable getCarsVINAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "Bonus",
                   type = "Edm.Int32")
        org.apache.olingo.ext.proxy.api.Annotatable getBonusAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForProperty(name = "IsFullyVested",
                   type = "Edm.Boolean")
        org.apache.olingo.ext.proxy.api.Annotatable getIsFullyVestedAnnotations();



        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForNavigationProperty(name = "PersonMetadata", 
                  type = "Microsoft.Test.OData.Services.AstoriaDefaultService.PersonMetadata")
        org.apache.olingo.ext.proxy.api.Annotatable getPersonMetadataAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForNavigationProperty(name = "Manager", 
                  type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Employee")
        org.apache.olingo.ext.proxy.api.Annotatable getManagerAnnotations();

        @org.apache.olingo.ext.proxy.api.annotations.AnnotationsForNavigationProperty(name = "Car", 
                  type = "Microsoft.Test.OData.Services.AstoriaDefaultService.Car")
        org.apache.olingo.ext.proxy.api.Annotatable getCarAnnotations();
    }

}
