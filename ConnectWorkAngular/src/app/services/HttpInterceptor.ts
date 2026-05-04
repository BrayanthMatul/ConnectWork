import { Injectable, inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { LoginServicio } from './LoginServicio';

export const jwtHttpInterceptor: HttpInterceptorFn = (req, next) => {
  const loginServicio = inject(LoginServicio);
  const token = loginServicio.getToken();

  // Si existe token, adjuntalo al header Authorization
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(req);
};
