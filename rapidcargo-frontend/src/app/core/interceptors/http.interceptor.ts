import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, finalize } from 'rxjs/operators';
import { LoadingService } from '../services/loading.service';
import { ErrorHandlerService } from '../services/error-handler.service';

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {

  constructor(
    private loadingService: LoadingService,
    private errorHandler: ErrorHandlerService
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<any> {
    this.loadingService.show();

    return next.handle(req).pipe(
      tap(
        (event: any) => {
          if (event instanceof HttpResponse) {
            console.log('Response received:', event);
          }
        },
        (error: any) => {
          if (error instanceof HttpErrorResponse) {
            this.errorHandler.handleError(error);
          }
        }
      ),
      finalize(() => {
        this.loadingService.hide();
      })
    );
  }
}
