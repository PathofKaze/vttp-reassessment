export interface Order{
    name: string
    email: string
    phone: number
    title: string
    description: string
    image: null
}

export interface OrderSummary{
    postingid: string
    postingdate: string
    name: string
    email: string
    phone: number
    title: string
    description: string
    imageurl: string
}
