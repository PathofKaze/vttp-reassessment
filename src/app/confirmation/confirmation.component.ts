import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderSummary } from '../models';
import { UploadService } from '../upload.service';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class ConfirmationComponent implements OnInit {

  posting_id: string = ""
  orders: OrderSummary[] = []

  constructor(private activatedRoute: ActivatedRoute, private uploadSvc: UploadService) { }

  ngOnInit(): void {
    this.posting_id = this.activatedRoute.snapshot.params['posting_id']
    this.uploadSvc.getOrders(this.posting_id)
    .then(result=> {
      this.orders = result
    })
    .catch(error=>{
      console.error(">>> error: ", error)
    })
  }

}