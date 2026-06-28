import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ConnectorMetadata, ConnectorOperation } from "../models/workflow.model";
import { CONNECTOR_REGISTRY } from "../shared/connector-registry";


@Injectable({ providedIn: 'root'})
export class ConnectorService {

    private readonly baseUrl = `${environment.apiBaseUrl}/api/connectors`;

    constructor(private http: HttpClient) {

    }

    getAll(): Observable<ConnectorMetadata[]> {
        return this.http.get<ConnectorMetadata[]>(this.baseUrl);
    }

    getLocalRegistry(): ConnectorMetadata[] {
        return CONNECTOR_REGISTRY;
    }

    getOperations(connectorType: string): Observable<ConnectorOperation[]> {
        return this.http.get<ConnectorOperation[]>(`${this.baseUrl}/${connectorType}/operations`);
    }

    filterConnectors(connectors: ConnectorMetadata[], query: string): ConnectorMetadata[] {
        if(!query || query.trim()) return connectors;
        const q = query.toLowerCase().trim();
        return connectors.filter(
            (c) => c.name.toLowerCase().includes(q) || c.category.toLowerCase().includes(q)
        );
    }

}