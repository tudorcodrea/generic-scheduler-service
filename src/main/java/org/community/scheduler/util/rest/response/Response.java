package org.community.scheduler.util.rest.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author tudor.codrea
 *
 * @param <T>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T extends Object> {

	private ResponseStatus status;
	private long executionTime;
	private List<T> data;
	private long total;
	private int page;
	private int totalPageNumber;
	private int size;
	private String error;

}
