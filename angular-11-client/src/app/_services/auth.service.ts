import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from "rxjs/operators";

const AUTH_API = 'http://localhost:8080/api/auth/';

// 這是不是其實也不需要 因為預設就是這樣 但如果有其他type的話就需要
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  token$ = new BehaviorSubject<any>([]);
  user$ = new BehaviorSubject<any>([]);

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions).pipe(
      tap((value)=>{
        this.token$.next( "token:"+value.accessToken);
        this.user$.next("user:"+value);
      })
    );
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username,
      email,
      password
    }, httpOptions);
  }
}
