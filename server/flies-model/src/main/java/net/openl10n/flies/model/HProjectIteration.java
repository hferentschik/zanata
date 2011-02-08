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
package net.openl10n.flies.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import net.openl10n.flies.rest.dto.ProjectIteration;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Where;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.security.Restrict;

/**
 * 
 * @see ProjectIteration
 * 
 */
@Entity
@Restrict
public class HProjectIteration extends AbstractSlugEntity
{

   private String name;
   private String description;

   private HIterationProject project;

   private Boolean active = true;

   private HProjectIteration parent;
   private List<HProjectIteration> children;

   private Map<String, HDocument> documents;
   private Map<String, HDocument> allDocuments;

   @Length(max = 20)
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public void setActive(Boolean active)
   {
      this.active = active;
   }

   @NotNull
   public Boolean getActive()
   {
      return active;
   }

   @ManyToOne
   @NotNull
   @NaturalId
   public HIterationProject getProject()
   {
      return project;
   }

   public void setProject(HIterationProject project)
   {
      this.project = project;
   }

   @OneToMany(mappedBy = "parent")
   public List<HProjectIteration> getChildren()
   {
      return children;
   }

   public void setChildren(List<HProjectIteration> children)
   {
      this.children = children;
   }

   @ManyToOne
   @JoinColumn(name = "parentId")
   public HProjectIteration getParent()
   {
      return parent;
   }

   public void setParent(HProjectIteration parent)
   {
      this.parent = parent;
   }

   @OneToMany(mappedBy = "projectIteration", cascade = CascadeType.ALL)
   @MapKey(name = "docId")
   @Where(clause = "obsolete=0")
   public Map<String, HDocument> getDocuments()
   {
      if (documents == null)
         documents = new HashMap<String, HDocument>();
      return documents;
   }

   @OneToMany(mappedBy = "projectIteration", cascade = CascadeType.ALL)
   @MapKey(name = "docId")
   // even obsolete documents
   public Map<String, HDocument> getAllDocuments()
   {
      if (allDocuments == null)
         allDocuments = new HashMap<String, HDocument>();
      return allDocuments;
   }

   public void setAllDocuments(Map<String, HDocument> allDocuments)
   {
      this.allDocuments = allDocuments;
   }

   public void setDocuments(Map<String, HDocument> documents)
   {
      this.documents = documents;
   }

   @Override
   public String toString()
   {
      return super.toString() + "[name=" + name + ",project=" + project + "]";
   }

}