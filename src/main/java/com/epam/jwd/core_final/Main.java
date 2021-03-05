package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;

import com.epam.jwd.core_final.exception.DAOException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.ServiceException;



public class Main {

    public static void main(String[] args) throws InvalidStateException, InterruptedException, ServiceException, DAOException {

       Application.start();

    }
}