/*
 * Copyright (C) 2012 Mobs and Geeks
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.szcomtop.meal.verification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When used, the field cannot be empty.
 *
 * @author mars
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
    public int order();
    public boolean trim()       default true;
    public String message()     default "This field is required.";
    public int messageResId()   default 0;
}
