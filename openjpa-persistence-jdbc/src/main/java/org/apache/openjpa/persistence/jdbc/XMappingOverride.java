/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openjpa.persistence.jdbc;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.persistence.Column;

/**
 * <p>Allows override of complex embedded or superclass mappings.</p>
 *
 * @author Abe White
 * @since 0.4.0, 1.1.0
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface XMappingOverride {

    String name() default "";

    Column[] columns() default {};

    XJoinColumn[] joinColumns() default {};

    ElementColumn[] elementColumns() default {};

    ElementJoinColumn[] elementJoinColumns() default {};

    KeyColumn[] keyColumns() default {};

    KeyJoinColumn[] keyJoinColumns() default {};

    ContainerTable containerTable() default @ContainerTable(specified = false);
}
