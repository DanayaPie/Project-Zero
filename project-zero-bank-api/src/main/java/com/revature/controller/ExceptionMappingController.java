package com.revature.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.dto.ExceptionMessageDTO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.AmountDoesNotExistException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.OverdraftException;

import io.javalin.Javalin;

public class ExceptionMappingController {

	public void mapExceptions(Javalin app) {

		app.exception(UnrecognizedPropertyException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});

		app.exception(InvalidParameterException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});

		app.exception(ClientNotFoundException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
		
		app.exception(AccountNotFoundException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
		
		app.exception(AmountDoesNotExistException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
		
		app.exception(OverdraftException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
	}
}
