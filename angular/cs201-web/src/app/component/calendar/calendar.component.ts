import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { isSameDay, isSameMonth } from 'date-fns';



@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  view: string = 'month';
  shouldShow: boolean = false;

  viewDate: Date = new Date();

  events: CalendarEvent[] = [
    {
      title: 'Testing Document',
      start: new Date(2018, 10, 4)
    },
    {
      title: 'Peer Review #1',
      start: new Date(2018, 10, 4)
    },
    {
      title: 'Deployment Document',
      start: new Date(2018, 10, 18)
    },
    {
      title: 'Complete Code',
      start: new Date(2018, 10, 19)
    },
    {
      title: 'Peer Review #2',
      start: new Date(2018, 10, 19)
    }
    {
      title: 'Complete Documentation',
      start: new Date(2018, 10, 25)
    },
  ];


  constructor() { }

  ngOnInit() {
  }

  eventClicked({ event }: { event: CalendarEvent }): void {
    console.log('Event clicked', event);
  }

  toggleShow() {
    this.shouldShow = !this.shouldShow;
  }

  activeDayIsOpen: boolean;

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
        this.viewDate = date;
      }
    }
  }

}
