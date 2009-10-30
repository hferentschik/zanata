package org.fedorahosted.flies.webtrans.editor.table;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import org.fedorahosted.flies.gwt.auth.AuthenticationError;
import org.fedorahosted.flies.gwt.auth.AuthorizationError;
import org.fedorahosted.flies.gwt.model.DocumentId;
import org.fedorahosted.flies.gwt.model.TransUnit;
import org.fedorahosted.flies.gwt.rpc.FliesSecurityException;
import org.fedorahosted.flies.gwt.rpc.GetTransUnits;
import org.fedorahosted.flies.gwt.rpc.GetTransUnitsResult;
import org.fedorahosted.flies.gwt.rpc.UpdateTransUnit;
import org.fedorahosted.flies.gwt.rpc.UpdateTransUnitResult;
import org.fedorahosted.flies.webtrans.client.DocumentSelectionEvent;
import org.fedorahosted.flies.webtrans.client.DocumentSelectionHandler;
import org.fedorahosted.flies.webtrans.client.NotificationEvent;
import org.fedorahosted.flies.webtrans.client.WorkspaceContext;
import org.fedorahosted.flies.webtrans.client.NotificationEvent.Severity;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.gen2.table.client.MutableTableModel;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.client.TableModelHelper.SerializableResponse;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class TableEditorTableModel extends MutableTableModel<TransUnit> {

	private final DispatchAsync dispatcher;
	private final EventBus eventBus;
	
	private DocumentId documentId;
	private final WorkspaceContext workspaceContext;
	
	@Inject
	public TableEditorTableModel(WorkspaceContext workspaceContext, DispatchAsync dispatcher, EventBus eventBus) {
		this.dispatcher = dispatcher;
		this.eventBus = eventBus;
		this.workspaceContext = workspaceContext;
	}
	
	@Override
	protected boolean onRowInserted(int beforeRow) {
		return false;
	}

	@Override
	protected boolean onRowRemoved(int row) {
		return true;
	}

	@Override
	protected boolean onSetRowValue(int row, TransUnit rowValue) {
		dispatcher.execute(
				new UpdateTransUnit(rowValue.getId(), workspaceContext.getLocaleId(), rowValue.getTarget()), 
				new AsyncCallback<UpdateTransUnitResult>() {
					@Override
					public void onFailure(Throwable caught) {
						eventBus.fireEvent(new NotificationEvent(Severity.Error, "Failed to update TransUnit"));
					}
					
					@Override
					public void onSuccess(UpdateTransUnitResult result) {
					}
				});
		return true;
	}

	@Override
	public void requestRows(
			final Request request,
			final Callback<TransUnit> callback) {
		int numRows = request.getNumRows();
		int startRow = request.getStartRow();
		Log.debug("Table requesting" + numRows + " starting from "+ startRow);
		
		if(documentId == null){
			callback.onFailure(new RuntimeException("No DocumentId"));
			return;
		}
		
		dispatcher.execute(new GetTransUnits(documentId, workspaceContext.getLocaleId(), startRow, numRows), new AsyncCallback<GetTransUnitsResult>() {
			@Override
			public void onSuccess(GetTransUnitsResult result) {
				SerializableResponse<TransUnit> response = new SerializableResponse<TransUnit>(
						result.getUnits());
				Log.debug("Got " + result.getUnits().size() +" rows back");
				callback.onRowsReady(request, response);
				setRowCount(result.getTotalCount());
			}
			@Override
			public void onFailure(Throwable caught) {
				if(caught instanceof AuthenticationError) {
					eventBus.fireEvent( new NotificationEvent(Severity.Error, "Not logged in!"));
				}
				else if(caught instanceof AuthorizationError) {
					eventBus.fireEvent( new NotificationEvent(Severity.Error, "Failed to load data from Server"));
				}
				else {
					eventBus.fireEvent( new NotificationEvent(Severity.Error, "An unknown error occured"));
				}
			}
		});
	}
	
	public DocumentId getDocumentId() {
		return documentId;
	}

	public void setDocumentId(DocumentId documentId) {
		this.documentId = documentId;
		this.setRowCount(UNKNOWN_ROW_COUNT);
	}
}