package org.fedorahosted.flies.gwt.model;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DocumentId implements Identifier<Long>, Serializable, IsSerializable{

	private static final long serialVersionUID = 6291339842619640513L;

	private long id;
	
	@SuppressWarnings("unused")
	private DocumentId() {
	}
	
	public DocumentId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return String.valueOf(id);
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	@Override
	public Long getValue(){
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj == null) return false;
		if(obj instanceof DocumentId) {
			return ((DocumentId) obj).id == id;
		}
		return false;
	}
}