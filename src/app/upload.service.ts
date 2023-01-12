import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Order, OrderSummary } from "./models";

@Injectable()
export class UploadService {

  constructor(private httpClient: HttpClient) { }

  createOrder(order: Order) : Promise<any> {
    const httpHeaders = new HttpHeaders()
                  .set("Content-Type", "multipart/form-data")
                  .set("Accept", "application/json")
    return firstValueFrom(
        this.httpClient.post<any>("/api/order", order, {headers:httpHeaders})
    )
  }

  getOrders(email:string) : Promise<OrderSummary[]> {
    return firstValueFrom(
      this.httpClient.get<OrderSummary[]>(`/api/order/${email}/all`)
    )
  }

}