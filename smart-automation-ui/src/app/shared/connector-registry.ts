import { ConnectorMetadata } from "../models/workflow.model";

export const CONNECTOR_REGISTRY: ConnectorMetadata[] = [
    {
        type: 'SCHEDULE', name: 'Schedule', icon: 'schedule', category: 'utility',
        triggerOperations: [
            { operationId: 'cron_trigger', name: 'Cron Trigger', description: 'trigger on cron schedule', configSchema: {}},
            { operationId: 'interval_trigger', name: 'Interval Trigger', description: 'trigger at fixed interval', configSchema: {}}
        ],
        actionOperations: [],
        requiresAuth: false,
    }
    
]