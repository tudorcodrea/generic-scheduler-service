package org.community.scheduler.exception;

/**
 * @author tudor.codrea
 *
 */
public class GenericSchedulerException extends Exception {

	private static final long serialVersionUID = -2415825953340384903L;

	public GenericSchedulerException(String errorMessage) {
		super(errorMessage);
	}
}
