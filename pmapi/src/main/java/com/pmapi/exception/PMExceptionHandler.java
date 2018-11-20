package com.pmapi.exception;


import java.lang.reflect.UndeclaredThrowableException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pmapi.constants.PMConstants;


@ControllerAdvice
public class PMExceptionHandler implements PMConstants
{

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ExceptionResponse handleCustomException(Exception ex, HttpServletResponse response)
	{
		response.setHeader("Content-Type", "application/json");
		if (ex instanceof PMException)
		{
			response.setStatus(((PMException) ex).getStatus());
			return ((PMException) ex).transformException();
		}
		else if (ex instanceof UndeclaredThrowableException)
		{
			UndeclaredThrowableException unEx = (UndeclaredThrowableException) ex;
			if (unEx.getUndeclaredThrowable() instanceof PMException)
			{
				PMException excep = (PMException) unEx.getUndeclaredThrowable();
				response.setStatus(excep.getStatus());
				return excep.transformException();
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return returnRestError();
			}
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return returnRestError();
		}
	}

	private ExceptionResponse returnRestError()
	{
		ExceptionResponse restError = new ExceptionResponse();
		restError.setErrorCode(TECH_ERROR_CODE);
		restError.setErrorMessage(TECH_ERROR_MESSAGE);
		return restError;
	}
}