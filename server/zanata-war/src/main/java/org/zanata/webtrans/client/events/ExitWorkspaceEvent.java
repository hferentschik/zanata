package org.zanata.webtrans.client.events;

import org.zanata.webtrans.shared.model.PersonId;
import org.zanata.webtrans.shared.rpc.HasExitWorkspaceData;

import com.google.gwt.event.shared.GwtEvent;

public class ExitWorkspaceEvent extends GwtEvent<ExitWorkspaceEventHandler> implements HasExitWorkspaceData
{

   private final PersonId personId;

   /**
    * Handler type.
    */
   private static Type<ExitWorkspaceEventHandler> TYPE;

   /**
    * Gets the type associated with this event.
    * 
    * @return returns the handler type
    */
   public static Type<ExitWorkspaceEventHandler> getType()
   {
      if (TYPE == null)
      {
         TYPE = new Type<ExitWorkspaceEventHandler>();
      }
      return TYPE;
   }

   public ExitWorkspaceEvent(HasExitWorkspaceData data)
   {
      this.personId = data.getPersonId();
   }

   @Override
   protected void dispatch(ExitWorkspaceEventHandler handler)
   {
      handler.onExitWorkspace(this);
   }

   @Override
   public Type<ExitWorkspaceEventHandler> getAssociatedType()
   {
      return getType();
   }

   @Override
   public PersonId getPersonId()
   {
      return personId;
   }

}
