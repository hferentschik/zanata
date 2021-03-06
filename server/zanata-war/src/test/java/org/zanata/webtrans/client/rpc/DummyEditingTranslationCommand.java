/**
 * 
 */
package org.zanata.webtrans.client.rpc;

import org.zanata.webtrans.shared.rpc.EditingTranslationAction;
import org.zanata.webtrans.shared.rpc.EditingTranslationResult;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;

final class DummyEditingTranslationCommand implements Command
{
   // private final EditingTranslationAction action;
   private final AsyncCallback<EditingTranslationResult> callback;

   DummyEditingTranslationCommand(EditingTranslationAction action, AsyncCallback<EditingTranslationResult> callback)
   {
      // this.action = action;
      this.callback = callback;
   }

   @Override
   public void execute()
   {
      EditingTranslationResult result = new EditingTranslationResult(true);
      callback.onSuccess(result);
   }

}