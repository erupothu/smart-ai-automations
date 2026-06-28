import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";


@Injectable({ providedIn: 'root'})
export class CredentialService {
    private readonly baseUrl = `${environment.apiBaseUrl}/api/credentials`;

    constructor(private http: HttpClient) {}

    create(credential: Partial<Credential>): Observable<Credential> {
        return this.http.post<Credential>(this.baseUrl, credential);
    }

    getAll(): Observable<Credential[]> {
        return this.http.get<Credential[]>(this.baseUrl);
    }

    getById(id: string): Observable<Credential> {
        return this.http.get<Credential>(`${this.baseUrl}/${id}`);
    }

    update(id: string, credential: Partial<Credential>): Observable<Credential> {
        return this.http.put<Credential>(`${this.baseUrl}/${id}`, credential);
    }

    delete(id: string): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }
}