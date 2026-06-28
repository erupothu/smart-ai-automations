import { Type } from "@angular/core";

export type NodeType = 'TRIGGER' | 'ACTION';
export type RunStatus = 'RUNNING' | 'COMPLETED' | 'FAILED' | 'TIMED_OUT';
export type NodeStatus = 'PENDING' | 'RUNNING' | 'COMPLETED' | 'FAILED' | 'TIMED_OUT';
export type AuthType = 'OAUTH2' | 'API_KEY' | 'BASIC' | 'BEARER' | 'CUSTOM' | 'NONE';

export interface Position {
    x: number;
    y: number;
}

export interface WorkflowNode {
    nodeId: string;
    type: NodeType;
    connectorType: string;
    operation: string;
    config: Record<string, unknown>;
    credentialId: string | null;
    position: Position;
}

export interface WorkflowEdge {
    edgeId: string;
    sourceNodeId: string;
    targetNodeId: string;
}

export interface WorkflowDefinition {
    id?: string;
    name: string;
    description: string;
    version: number;
    enabled: boolean;
    createdAt?: string;
    updatedAt?: string;
    nodes: WorkflowNode[];
    edges: WorkflowEdge[];
}

export interface NodeExecution {
    nodeId: string;
    connectorType: string;
    operation: string;
    status: NodeStatus;
    startedAt: string;
    completedAt: string | null;
    duration: number;
    input: unknown;
    output: unknown;
    error: string | null;
}

export interface WorkflowRun {
    runId: string;
    workflowId: string;
    workflowVersion: number;
    status: RunStatus;
    startedAt: string;
    completedAt: string | null;
    currentNodeId: string | null;
    nodeExecution: NodeExecution[];
    triggerData: undefined
}

export interface Credential {
    id: string;
    name: string;
    connectorType: string;
    authType: AuthType;
    encryptedData: string;
    createdAt: string;
    updatedAt: string;
}

export interface CustomMcpConfig {
    id: string;
    name: string;
    endpointUrl: string;
    authType: AuthType;
    headers: Record<string, string>;
    requestMapping: unknown;
    responseMapping: unknown;
    credentialId: string | null;
    createdAt: string;
}

export interface ConnectorOperation {
    operationId: string;
    name: string;
    description: string;
    configSchema: unknown;
}

export interface ConnectorMetadata {
    type: string;
    name: string;
    icon: string;
    category: string;
    triggerOperations: ConnectorOperation[];
    actionOperations: ConnectorOperation[];
    requiresAuth: boolean;
}

export interface Chatmessage {
    role: 'user' | 'assistant';
    content: string;
    timestamp: Date;
}

export interface ChatResponse {
    message: string;
    sessionId: string;
}