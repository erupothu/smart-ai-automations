import { Routes } from '@angular/router';
import { WorkflowCanvas } from './workflow-builder/workflow-canvas/workflow-canvas';
import { ExecutionDetail } from './dashboard/execution-detail/execution-detail';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },
    {
        path: 'dashboard',
        loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent)
    },
    {
        path: 'workflow/new',
        loadComponent: () => import('./workflow-builder/workflow-canvas/workflow-canvas').then((m) => m.WorkflowCanvas)
    },
    {
        path: 'workflow/:id',
        loadComponent: () => import('./workflow-builder/workflow-canvas/workflow-canvas').then((m) => WorkflowCanvas)
    },
    {
        path: 'workflow/:id/runs',
        loadComponent: () => import('./dashboard/execution-detail/execution-detail').then((m) => ExecutionDetail)
    },
    {
        path: 'credentials',
        loadComponent: () => import('./credentials/credential-manager/credential-manager/credential-manager').then((m) => Credential)
    }
];
