import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatbotPanel } from './chatbot-panel';

describe('ChatbotPanel', () => {
  let component: ChatbotPanel;
  let fixture: ComponentFixture<ChatbotPanel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatbotPanel],
    }).compileComponents();

    fixture = TestBed.createComponent(ChatbotPanel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
