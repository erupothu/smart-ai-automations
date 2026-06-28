import {Component, OnInit, OnDestroy} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import {MatTableModule} from '@angular/material/table';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatChipsModule} from '@angular/material/chips';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { Subject, forkJoin, of } from 'rxjs';
import { catchError, take, takeUntil } from 'rxjs/operators';
import { WorkflowService } from '../services/workflow.service';
import { RunStatus, WorkflowDefinition, WorkflowRun } from '../models/workflow.model';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

export interface WorflowSummary {
  workflow: WorkflowDefinition,
  lastRunStatus: RunStatus | null;
  nextScheduledRun: string | null;
  executionCount: number;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, MatTableModule, MatButtonModule, 
    MatIconModule, MatCardModule, MatTooltipModule, MatChipsModule, MatSnackBarModule,
     MatProgressSpinnerModule, MatSlideToggleModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  summaries: WorflowSummary[] = [];
  loading = true;
  triggeringId: string | null = null;
  displayedColumns = [ 'name', 'lastRunStatus', 'nextScheduleRun', 'executionCount', 'enabled', 'actions'];

  private destroy$ = new Subject<void>();

  constructor(private workflowService: WorkflowService, private snackbar: MatSnackBar) { }

  ngOnInit(): void {
    this.loadWorkflows();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadWorkflows(): void {
    this.loading = true;
    this.workflowService.getAll()
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: workflows => {
        if(!workflows || workflows.length === 0) {
          this.summaries = [];
          this.loading = false;
          return;
        }

        const runRequests = workflows.map((wf) =>
          this.workflowService.getRuns(wf.id!).pipe(
            catchError(() => of([] as WorkflowRun[]))
          )
        );

        forkJoin(runRequests)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (allRuns) => {
            this.summaries = workflows.map((wf, idx) => {
              const runs = allRuns[idx];
              const sortedRuns = [...runs].sort((a, b) => new Date(b.startedAt).getTime() - new Date(a.startedAt).getTime());
              const lastRun = sortedRuns.length > 0 ? sortedRuns[0] : null;
              const nextScheduled = this.getNextScheduledRun(wf);
              return {
                workflow: wf,
                lastRunStatus: lastRun?.status ?? null,
                nextScheduledRun: nextScheduled,
                executionCount: runs.length
              };
            });
            this.loading = false;
          },
          error: () => {
            this.snackbar.open('Failed to load execution Data', 'Close', {duration: 3000});
            this.summaries = workflows.map((wf) => ({
              workflow: wf,
                lastRunStatus: null,
                nextScheduledRun: null,
                executionCount: 0
            }));
            this.loading = false;
          }
        });
      },
      error: () => {
        this.snackbar.open('Failed to load Workflows', 'Close', {duration: 3000});
        this.loading = false;
      }
    });
  }

  getStatusClass(status: RunStatus): string {
    switch(status) {
      case 'COMPLETED': return 'status-completed';
      case 'FAILED': return 'status-failed';
      case 'RUNNING': return 'status-running';
      case 'TIMED_OUT': return 'status-timed-out';
      default: return '';
    }
  }

  toggleEnabled(row: WorflowSummary): void {
    const wf = row.workflow;
    this.workflowService.setEnabled(wf.id!, !wf.enabled)
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (updated) => {
        row.workflow = updated;
      },
      error: () => this.snackbar.open('Failed to update workflow status', 'Close', {duration: 30000}),
    })
  }

  triggerWorkflow(id: string): void {
    this.triggeringId = id;
    this.workflowService.trigger(id)
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (run) => {
        this.snackbar.open('Workflow triggered, waiting for completion', 'Close', {duration: 3000}),
        this.pollRunStatus(id, run.runId);
      },
      error: () => {
        this.triggeringId = null;
        this.snackbar.open('Failed to trigger workflow', 'Close', {duration: 3000});
      }
    })
  }

  private pollRunStatus(workflowId: string, runId: string): void {
    const poll = setInterval(() => {
      this.workflowService.getRunById(runId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (run) => {
          if(run.status != 'RUNNING') {
            clearInterval(poll);
            this.triggeringId = null;
            const statusMsg = run.status === 'COMPLETED' ? 'workflow completed successfuly' : `workflow ${run.status}`;
            this.snackbar.open(statusMsg, 'Close', {duration: 3000});
            this.loadWorkflows();
          }
        },
        error: () => {
          clearInterval(poll);
          this.triggeringId = null;
          this.loadWorkflows();
        }
      });
    }, 2000);
  }

  deleteWorkflow(id: string): void {
    this.workflowService.delete(id)
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: () => this.loadWorkflows(),
      error: () => this.snackbar.open('Failed to delete workflow', 'Close', {duration: 2000}),
    });
  }

  private getNextScheduledRun(wf: WorkflowDefinition): string | null {
    if(!wf.enabled) return null;
    const triggerNode = wf.nodes.find((n) => n.type === 'TRIGGER');
    if(!triggerNode || triggerNode.connectorType !== 'SCHEDULE') return null;
    const cron = triggerNode.config?.['cronExpression'] as string | undefined;
    if(!cron) return null;

    try {
      const parts = cron.trim().split(/\s+/);
      if(parts.length !== 6) return null;
      const now = new Date();
      // const sec = this.parseCronField(parts[0], 0, 59);
      const next = new Date(now);
      return next.toISOString();
    } catch (error) {
      return null;
    }
  }

}