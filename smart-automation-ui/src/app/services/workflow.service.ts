import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { WorkflowDefinition, WorkflowRun } from "../models/workflow.model";

@Injectable({ providedIn: 'root'})
export class WorkflowService {

    private readonly baseUrl = `${environment.apiBaseUrl}/api/workflows`;

    constructor(private http: HttpClient) {

    }

    create(definition: Partial<WorkflowDefinition>): Observable<WorkflowDefinition> {
        return this.http.post<WorkflowDefinition>(this.baseUrl, definition);
    }

    getAll(): Observable<WorkflowDefinition[]> {
        return this.http.get<WorkflowDefinition[]>(this.baseUrl);
    }

    getById(id: string): Observable<WorkflowDefinition> {
        return this.http.get<WorkflowDefinition>(`${this.baseUrl}/${id}`);
    }

    update(id: string, definition: Partial<WorkflowDefinition>): Observable<WorkflowDefinition> {
        return this.http.put<WorkflowDefinition>(`${this.baseUrl}/${id}`, definition);
    }
    
    delete(id: string): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${id}`);
    }

    trigger(id: string): Observable<WorkflowRun> {
        return this.http.post<WorkflowRun>(`${this.baseUrl}/${id}/trigger`, {});
    }

    setEnabled(id: string, enabled: boolean): Observable<WorkflowDefinition> {
        return this.http.patch<WorkflowDefinition>(`${this.baseUrl}/${id}/status`, {enabled});
    }

    getRuns(workflowId: string): Observable<WorkflowRun[]> {
        return this.http.get<WorkflowRun[]>(`${this.baseUrl}/${workflowId}/runs`);
    }

    getRunById(runId: string): Observable<WorkflowRun> {
        return this.http.get<WorkflowRun>(`${environment.apiBaseUrl}/api/runs/${runId}`);
    }

    searchRuns(filter: {workflowId?: string; status?: string; from?: string; to?: string}): Observable<WorkflowRun[]> {
        let params = new HttpParams();
        if(filter.workflowId) params = params.set('workflowId', filter.workflowId);
        if(filter.status) params = params.set('status', filter.status);
        if(filter.from) params = params.set('from', filter.from);
        if(filter.to) params = params.set('to', filter.to);
        return this.http.get<WorkflowRun[]>(`${environment.apiBaseUrl}/api/runs`, {params});
    }
}