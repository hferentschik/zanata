/*
 * Copyright 2010, Red Hat, Inc. and individual contributors as indicated by the
 * @author tags. See the copyright.txt file in the distribution for a full
 * listing of individual contributors.
 * 
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */
package org.zanata.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
public class HApplicationConfiguration extends ModelEntityBase
{

   public static String KEY_HOST             = "flies.host";
   public static String KEY_REGISTER         = "flies.register";
   public static String KEY_DOMAIN           = "flies.email.domain";
   public static String KEY_ADMIN_EMAIL      = "flies.admin.email";
   public static String KEY_HOME_CONTENT     = "flies.home.content";
   public static String KEY_HELP_CONTENT     = "flies.help.content";
   public static String KEY_LOGINCONFIG_URL  = "zanata.login-config.url";

   private String key;
   private String value;

   public HApplicationConfiguration()
   {
   }

   public HApplicationConfiguration(String key, String value)
   {
      this.key = key;
      this.value = value;
   }

   @NaturalId
   @NotEmpty
   @Length(max = 255)
   @Column(name = "config_key", nullable = false)
   public String getKey()
   {
      return key;
   }

   public void setKey(String key)
   {
      this.key = key;
   }

   @NotNull
   @Type(type = "text")
   @Column(name = "config_value", nullable = false)
   public String getValue()
   {
      return value;
   }

   public void setValue(String value)
   {
      this.value = value;
   }
}
