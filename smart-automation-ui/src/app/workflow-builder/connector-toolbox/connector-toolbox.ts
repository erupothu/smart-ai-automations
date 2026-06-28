import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ConnectorMetadata, ConnectorOperation } from '../../models/workflow.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDividerModule } from '@angular/material/divider';
import { catchError, of } from 'rxjs';
import { ConnectorService } from '../../services/connector.service';
import { A11yModule } from "@angular/cdk/a11y";


const CATEGORY_ORDER = [
  'Productivity', 'Utility', 'communication', 'DevOps', 'Design', 'Database', 'AI', 'Monitoring', 'Custom'
];

export interface ConnectorGroup {
  category: string;
  connectors: ConnectorMetadata[];
}

@Component({
  selector: 'app-connector-toolbox',
  standalone: true,
  imports: [CommonModule, FormsModule, MatFormFieldModule, MatInputModule, MatIconModule,
    MatListModule, MatExpansionModule, MatChipsModule, MatProgressSpinnerModule, MatTooltipModule, MatDividerModule, A11yModule],
  templateUrl: './connector-toolbox.html',
  styleUrl: './connector-toolbox.scss',
})
export class ConnectorToolbox implements OnInit {
  @Output() connectorSelected = new EventEmitter<ConnectorMetadata>();
  @Output() connectorDragStart = new EventEmitter<ConnectorMetadata>();

  searchQuery = '';
  allConnectors: ConnectorMetadata[] = [];
  loading = false;

  expandedtype: string | null = null;
  constructor(private connectorService: ConnectorService) {

  }
  ngOnInit(): void {
    this.loading = true;
    this.connectorService.getAll().pipe(catchError(() => of(null)))
    .subscribe(connectors => {
      if(connectors && connectors.length > 0) {
        this.allConnectors = connectors;
      } else {
        this.allConnectors = this.connectorService.getLocalRegistry();
      }
      this.loading = false;
    })
  }

  get filteredConnectors(): ConnectorMetadata[] {
    return this.connectorService.filterConnectors(this.allConnectors, this.searchQuery);
  }

  get groupedConnectors(): ConnectorGroup[] {
    const filtered = this.filteredConnectors;
    const map = new Map<string, ConnectorMetadata[]>();
    for(const c of filtered) {
      const list = map.get(c.category) ?? [];
      list.push(c);
      map.set(c.category, list);
    }
    const groups: ConnectorGroup[] = [];
    for(const cat of CATEGORY_ORDER) {
      if(map.has(cat)) {
        groups.push({ category: cat, connectors: map.get(cat)! });
        map.delete(cat);
      }
    }

    for(const [cat, connectors] of map.entries()) {
      groups.push({category: cat, connectors})
    }
    return groups;
  }

  hastrigger(connector: ConnectorMetadata): boolean {
    return (connector.triggerOperations?.length ?? 0) > 0;
  }

  hasAction(connector: ConnectorMetadata): boolean {
    return (connector.actionOperations?.length ?? 0) > 0;
  }

  isTriggerOnly(connector: ConnectorMetadata): boolean {
    return this.hastrigger(connector) && !this.hasAction(connector);
  }

  isExpanded(connector: ConnectorMetadata): boolean {
    return this.expandedtype == connector.type;
  }

  toggleExpand(connector: ConnectorMetadata, event: Event): void {
    event.stopPropagation();
    this.expandedtype = this.expandedtype === connector.type ? null : connector.type;
    this.connectorSelected.emit(connector);
  }

  onConnectorKeydown(event: KeyboardEvent, connector: ConnectorMetadata): void {
    if(event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
      this.toggleExpand(connector, event);
    }
  }

  onDragStart(event: DragEvent, connector: ConnectorMetadata): void {
    if(!event.dataTransfer) return;
    event.dataTransfer.setData('text/plain', connector.type);
    event.dataTransfer.setData('application/json', JSON.stringify(connector));
    event.dataTransfer.effectAllowed = 'copy';
    this.connectorDragStart.emit(connector);
  }

  trackByType(_: number, connector: ConnectorMetadata): string {
    return connector.type;
  }

  trackByCategory(_: number, group: ConnectorGroup): string {
    return group.category;
  }

  trackByOpId(_: number, op: ConnectorOperation): string {
    return op.operationId;
  }

}
