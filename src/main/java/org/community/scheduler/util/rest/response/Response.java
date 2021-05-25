package org.community.scheduler.util.rest.response;

import java.util.List;

/**
 * @author tudor.codrea
 *
 * @param <T>
 */
public class Response<T extends Object> {

	private ResponseStatus status;
	private long executionTime;
	private List<T> data;
	private long total;
	private int page;
	private int totalPageNumber;
	private int size;
	private String error;
	
	public Response() {
		
	}
	
	public Response(ResponseStatus status, long executionTime, List<T> data, long total, int page, int totalPageNumber,
			int size, String error) {
		super();
		this.status = status;
		this.executionTime = executionTime;
		this.data = data;
		this.total = total;
		this.page = page;
		this.totalPageNumber = totalPageNumber;
		this.size = size;
		this.error = error;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
